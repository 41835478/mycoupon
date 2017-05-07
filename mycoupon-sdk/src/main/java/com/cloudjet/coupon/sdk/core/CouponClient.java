package com.cloudjet.coupon.sdk.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloudjet.coupon.model.ActivityModel;
import com.cloudjet.coupon.model.BindCpModel;
import com.cloudjet.coupon.model.CodeListParamsModel;
import com.cloudjet.coupon.model.ConvertCodeModel;
import com.cloudjet.coupon.model.ConvertSuccessModel;
import com.cloudjet.coupon.model.CouponCopyParamsModel;
import com.cloudjet.coupon.model.CouponInfoModel;
import com.cloudjet.coupon.model.CouponOrderModel;
import com.cloudjet.coupon.model.CouponOrderResModel;
import com.cloudjet.coupon.model.CouponPackageModel;
import com.cloudjet.coupon.model.CouponPageModel;
import com.cloudjet.coupon.model.InfoCodeModel;
import com.cloudjet.coupon.model.PackageEditModel;
import com.cloudjet.coupon.model.PackageInitModel;
import com.cloudjet.coupon.model.PackageListParamsModel;
import com.cloudjet.coupon.model.PackagePullModel;
import com.cloudjet.coupon.model.PageResult;
import com.cloudjet.coupon.model.ProductModel;
import com.cloudjet.coupon.model.ShopSortModel;
import com.cloudjet.coupon.model.SortDetailModel;
import com.cloudjet.coupon.model.SortProductModel;
import com.cloudjet.coupon.model.UserBagModel;
import com.cloudjet.coupon.model.UserBagPageModel;
import com.cloudjet.coupon.model.UserPackagePullModel;
import com.cloudjet.coupon.model.UserShareModel;
import com.cloudjet.coupon.model.UserSourceModel;
import com.cloudjet.coupon.request.ConvertCodeListParamsModel;
import com.cloudjet.coupon.request.ProductListParamsModel;
import com.cloudjet.coupon.request.ProductSortListModel;
import com.cloudjet.coupon.request.SelectCouponParamsModel;
import com.cloudjet.coupon.request.SendMsgParamsModel;
import com.cloudjet.coupon.request.SetMsgParamsModel;
import com.cloudjet.coupon.request.UserBagParamsModel;
import com.cloudjet.coupon.res.model.CodeListResModel;
import com.cloudjet.coupon.res.model.CodeMsgModel;
import com.cloudjet.coupon.res.model.ConvertCodeListResModel;
import com.cloudjet.coupon.res.model.ProductDetailModel;
import com.cloudjet.coupon.res.model.ShareCodeResModel;
import com.cloudjet.coupon.res.model.UserBagResModel;
import com.cloudjet.coupon.sdk.util.Response;

/**
 * 优惠券接口sdk主类 1.httpconnection设置 2.重复请求 3.连接释放
 * 增加掉线服务不可用处理
 */
public class CouponClient {

	final static Logger log = LoggerFactory.getLogger(CouponClient.class);
	private static PoolingHttpClientConnectionManager connectionManager;
	private String apiScopeName="coupon_api_*";

	private String couponEndPoint = "https://uat-api.izhuan100.com/api";
	private String accessTokenUrl = "https://uat-api.izhuan100.com/emsoauth/oauth2/access_token";
	private String refreshTokenUrl = "https://uat-api.izhuan100.com/emsoauth/oauth2/refresh_token";
	
	private String platFormId;
	private String platFormSecret;
	private RequestConfig requestConfig;

	private Map<String, Object> accessTokenCacheMap=new ConcurrentHashMap<String, Object>();
	
	//最大连接数 200
	private int maxTotal=200;
	//每个route的最大连接数 100
	private int maxPerRoute=100;
	
	public void initAccessTokenCacheMap(){
		JSONObject accessTokenJSONObject=this.retrieveAccessToken(apiScopeName);
		if(accessTokenJSONObject!=null){
			accessTokenCacheMap.put("create_time",new Date());
			accessTokenCacheMap.put("access_token",accessTokenJSONObject.get("access_token"));
			accessTokenCacheMap.put("refresh_token",accessTokenJSONObject.get("refresh_token"));
			accessTokenCacheMap.put("expires_in", accessTokenJSONObject.getInteger("expires_in"));
		}else{
			log.warn("initAccessTokenCacheMap failed.");
		}
	}
	
	/**
	 * 默认测试调用
	 */
	@SuppressWarnings("deprecation")
	public CouponClient(String platFormId, String platFormSecret){
		try{
			this.platFormId = platFormId;
			this.platFormSecret = platFormSecret;
			requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
					.setConnectionRequestTimeout(10000).setStaleConnectionCheckEnabled(true).build();
			if (connectionManager == null) {
				connectionManager = new PoolingHttpClientConnectionManager();
				connectionManager.setMaxTotal(maxTotal);
				connectionManager.setDefaultMaxPerRoute(maxPerRoute);
			}
			//init
			this.initAccessTokenCacheMap();
		}catch (Exception e) {
			log.error("coupon sdk init error。detail:",e);
		}
	}
	
	/**
	 * 正式调用
	 */
	@SuppressWarnings("deprecation")
	public CouponClient(String platFormId, String platFormSecret,String accessTokenUrl,String refreshTokenUrl){
		try{
			this.platFormId = platFormId;
			this.platFormSecret = platFormSecret;
			this.accessTokenUrl=accessTokenUrl;
			this.refreshTokenUrl=refreshTokenUrl;
			requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
					.setConnectionRequestTimeout(10000).setStaleConnectionCheckEnabled(true).build();
			if (connectionManager == null) {
				connectionManager = new PoolingHttpClientConnectionManager();
				connectionManager.setMaxTotal(maxTotal);
				connectionManager.setDefaultMaxPerRoute(maxPerRoute);
			}
			//init
			this.initAccessTokenCacheMap();
		}catch (Exception e) {
			log.error("coupon sdk init error。detail:",e);
		}
	}
	
	public void setCouponEndPoint(String couponEndPoint) {
		this.couponEndPoint = couponEndPoint;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}

	public void setRefreshTokenUrl(String refreshTokenUrl) {
		this.refreshTokenUrl = refreshTokenUrl;
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	public void setPlatFormId(String platFormId) {
		this.platFormId = platFormId;
	}

	public void setPlatFormSecret(String platFormSecret) {
		this.platFormSecret = platFormSecret;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
		connectionManager.setMaxTotal(maxTotal);
	}

	public void setMaxPerRoute(int maxPerRoute) {
		this.maxPerRoute = maxPerRoute;
		connectionManager.setDefaultMaxPerRoute(maxPerRoute);
	}
	
	/**
	 * 获取access_token
	 */
	public void refreshAccessToken() {
		this.initAccessTokenCacheMap();
	}
	
	/**
	 * 获取access_token
	 */
	protected String accessToken(String apiName) throws Exception{
		if(accessTokenCacheMap==null || accessTokenCacheMap.size()==0){
			this.initAccessTokenCacheMap();
			return (accessTokenCacheMap!=null?(String)accessTokenCacheMap.get("access_token"):null);
		}
		try{
			Date createTime=(Date)accessTokenCacheMap.get("create_time");
			int expire_in=(Integer)accessTokenCacheMap.get("expires_in");
			
			Calendar expireCalendar=Calendar.getInstance();
			expireCalendar.setTime(createTime);
			expireCalendar.add(Calendar.SECOND, expire_in-200);
			Calendar now=Calendar.getInstance();
			if(expireCalendar.after(now)){
				log.debug("get access_token from cacheMap:"+accessTokenCacheMap);
				return (String)accessTokenCacheMap.get("access_token");
			}
		}catch(Exception e) {
			log.error("get access_token occurs exception.detail:",e);
			return null;
		}
		//提前500s刷新
		synchronized(accessTokenCacheMap){
			String refresh_token=(String)accessTokenCacheMap.get("refresh_token");
			try{
				JSONObject accessTokenJSONObject=this.retrieveRefreshToken(refresh_token,apiName);
				if(accessTokenJSONObject!=null && StringUtils.isNotBlank(accessTokenJSONObject.getString("access_token"))){
					accessTokenCacheMap.put("create_time",new Date());
					accessTokenCacheMap.put("expires_in", accessTokenJSONObject.getInteger("expires_in"));
					accessTokenCacheMap.put("access_token",accessTokenJSONObject.get("access_token"));
					accessTokenCacheMap.put("refresh_token",accessTokenJSONObject.get("refresh_token"));
					log.debug("get access_token from refreshed:"+accessTokenCacheMap);
					return (String)accessTokenCacheMap.get("access_token");
				}else{
					this.initAccessTokenCacheMap();
					return (accessTokenCacheMap!=null?(String)accessTokenCacheMap.get("access_token"):null);
				}
			}catch (Exception e) {
				log.error("error refresh token,must retrieve access_token.",e);
				this.initAccessTokenCacheMap();
				return (accessTokenCacheMap!=null?(String)accessTokenCacheMap.get("access_token"):null);
			}
		}
	}
	
	/**
	 * 刷新access_token,用于延长有效期
	 */
	@SuppressWarnings("deprecation")
	private JSONObject retrieveRefreshToken(String refresh_token,String apiName) throws Exception {
		final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig).build();
		final HttpPost httpMethod = new HttpPost(refreshTokenUrl + "?randmom=" + System.currentTimeMillis());

		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("refresh_token",refresh_token));
		params.add(new BasicNameValuePair("grant_type", "refresh_token"));
		params.add(new BasicNameValuePair("scope", apiName));
		try{
			httpMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		}catch (UnsupportedEncodingException e) {
			log.error("coupon sdk retrieveRefreshToken param set encode error。detail:",e);
			return null;
		}
		
		CloseableHttpResponse response=null;
		try {
			response = client.execute(httpMethod);
		} catch (IOException e) {
			log.error("coupon sdk retrieveRefreshToken error。detail:",e);
			return null;
		}
		try {
			final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
			log.debug("refresh_token response:" + responseString);
			response.close();
			return JSON.parseObject(responseString);
		}catch (ParseException e) {
			log.error("coupon sdk retrieveRefreshToken parse response error。detail:",e);
			return null;
		}catch (IOException e) {
			log.error("coupon sdk retrieveRefreshToken error。detail:",e);
			return null;
		}finally {
			httpMethod.releaseConnection();
		}
	}
	
	/**
	 * 获取access_token
	 */
	@SuppressWarnings("deprecation")
	protected JSONObject retrieveAccessToken(final String apiName) {
		final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig).build();
		final HttpPost httpMethod = new HttpPost(accessTokenUrl + "?randmom=" + System.currentTimeMillis());

		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("client_id", platFormId));
		params.add(new BasicNameValuePair("client_secret", platFormSecret));
		params.add(new BasicNameValuePair("grant_type", "client_credentials"));
		params.add(new BasicNameValuePair("scope", apiName));
		try {
			httpMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			log.error("coupon sdk retrieveAccessToken param set encode error。detail:",e);
			return null;
		}

		CloseableHttpResponse response=null;
		try {
			response = client.execute(httpMethod);
		} catch (IOException e) {
			log.error("coupon sdk retrieveAccessToken error。detail:",e);
			return null;
		}
		try {
			final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
			log.debug("accessToken response:" + responseString);
			response.close();
			return JSON.parseObject(responseString);
		}catch (ParseException e) {
			log.error("coupon sdk retrieveAccessToken parse response error。detail:",e);
			return null;
		}catch (IOException e) {
			log.error("coupon sdk retrieveAccessToken error。detail:",e);
			return null;
		}finally {
			httpMethod.releaseConnection();
		}
	}
	
	/**
	 * 新建优惠券
	 */
	public Response<String> couponCreate(CouponInfoModel couponInfoModel) throws Exception {
		String apiName = "coupon_api_coupon_create";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSON.toJSONString(couponInfoModel);
			log.info(jsonBody);
			postMethod.setEntity(new StringEntity(jsonBody, "utf-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon create response:" + responseString);
				JSONObject resultObject = JSON.parseObject(responseString);
				if (resultObject != null) {
					return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
					});
				}
				return new Response<String>("error", "创建失败!", null);
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}

	/**
	 * 优惠券列表查询
	 */
	public Response<PageResult<CouponInfoModel>> couponQueryList(CouponPageModel couponQueryModel) throws Exception {
		final String apiName = "coupon_api_coupon_queryList";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();

			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final HttpPost httpMethod = new HttpPost(couponApiUrl);
			httpMethod.setEntity(new StringEntity(JSONObject.toJSONString(couponQueryModel), "UTF-8"));
			httpMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon queryList response:" + responseString);
				if (StringUtils.isNotBlank(responseString)) {
					return JSON.parseObject(responseString, new TypeReference<Response<PageResult<CouponInfoModel>>>() {
					});
				}
				return new Response<PageResult<CouponInfoModel>>("success", "查询成功,无内容!", null);
			} finally {
				response.close();
			}
		}
		return new Response<PageResult<CouponInfoModel>>("error", "无法获取token!", null);
	}

	/**
	 * 查询优惠券（详情）
	 */
	public Response<CouponInfoModel> couponQueryDetail(String cpId) throws Exception {
		final String apiName = "coupon_api_coupon_queryDetail";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&cpId="+cpId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon queryDetail response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<CouponInfoModel>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<CouponInfoModel>("error", "无法获取token!", null);
	}

	/**
	 * 编辑优惠券
	 */
	public Response<CouponInfoModel> couponEdit(CouponInfoModel couponInfo) throws Exception {
		final String apiName = "coupon_api_coupon_edit";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(couponInfo);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon edit response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<CouponInfoModel>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<CouponInfoModel>("error", "无法获取token!", null);
	}

	/**
	 * 优惠券发布
	 */
	public Response<String> couponSend(UserSourceModel userSourceModel) throws Exception {
		final String apiName = "coupon_api_coupon_send";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(userSourceModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon send response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}

	/**
	 * 删除优惠券
	 */
	public Response<String> couponDelete(CouponInfoModel couponInfoModel) throws Exception {
		final String apiName = "coupon_api_coupon_del";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(couponInfoModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon delete response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}

	/**
	 * 优惠券冻结（下架）
	 */
	public Response<String> couponOut(CouponInfoModel couponInfoModel) throws Exception {
		final String apiName = "coupon_api_coupon_out";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(couponInfoModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon out response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 查询未发布优惠券
	 */
	public Response<List<CouponInfoModel>> couponQueryDescList(SelectCouponParamsModel selectCouponParamsModel) throws Exception {
		final String apiName = "coupon_api_coupon_query_unpub";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();

			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final HttpPost httpMethod = new HttpPost(couponApiUrl);
			httpMethod.setEntity(new StringEntity(JSONObject.toJSONString(selectCouponParamsModel), "UTF-8"));
			httpMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api coupon query unpub response:" + responseString);
				if (StringUtils.isNotBlank(responseString)) {
					return JSON.parseObject(responseString, new TypeReference<Response<List<CouponInfoModel>>>() {
					});
				}
				return new Response<List<CouponInfoModel>>("success", "查询成功,无内容!", null);
			} finally {
				response.close();
			}
		}
		return new Response<List<CouponInfoModel>>("error", "无法获取token!", null);
	}
	
	/**
	 * 优惠券补发
	 */
	public Response<String> couponAddToSend(UserSourceModel userSource) throws Exception {
		final String apiName = "coupon_api_coupon_resend";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(userSource);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("bag addToSend response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}

	/**
	 * 查询用户券包信息
	 */
	public Response<List<UserBagResModel>> queryUserBag(UserBagParamsModel userBagParamsModel) throws Exception {
		final String apiName = "coupon_api_coupon_queryUserBag";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();

			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final HttpPost httpMethod = new HttpPost(couponApiUrl);
			httpMethod.setEntity(new StringEntity(JSONObject.toJSONString(userBagParamsModel), "UTF-8"));
			httpMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api queryUserBag response:" + responseString);
				if (StringUtils.isNotBlank(responseString)) {
					return JSON.parseObject(responseString, new TypeReference<Response<List<UserBagResModel>>>() {
					});
				}
				return new Response<List<UserBagResModel>>("success", "查询成功,无内容!", null);
			} finally {
				response.close();
			}
		}
		return new Response<List<UserBagResModel>>("error", "无法获取token!", null);
	}
	
	/**
	 * 下单页面，返回出可以用的优惠券
	 */
	public Response<CouponOrderResModel> orderPlace(CouponOrderModel couponOrderModel) throws Exception {
		final String apiName = "coupon_api_order_place";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(couponOrderModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("order place response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<CouponOrderResModel>>() {
				});
			}catch(Exception e){
				e.printStackTrace();
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<CouponOrderResModel>("error", "无法获取token!", null);
	}

	/**
	 * 去付款订单结算
	 */
	public Response<CouponOrderResModel> orderPayPre(CouponOrderModel couponOrderModel) throws Exception {
		final String apiName = "coupon_api_order_payPre";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(couponOrderModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("order payPre response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<CouponOrderResModel>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<CouponOrderResModel>("error", "无法获取token!", null);
	}
			
	/**
	 * 修改券包状态
	 */
	public Response<String> bagAlterStatus(String orderNo,String tel) throws Exception{
		final String apiName = "coupon_api_bag_alterStatus";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);
			postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("orderNo", orderNo));
			postParameters.add(new BasicNameValuePair("tel", tel));
			postMethod.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("bag_alterStatus response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 查询券包
	 */
	public Response<PageResult<UserBagModel>> bagQueryList(UserBagPageModel userBagPageModel) throws Exception {
		final String apiName = "coupon_api_bag_queryList";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(userBagPageModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("bag queryList response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<PageResult<UserBagModel>>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<PageResult<UserBagModel>>("error", "无法获取token!", null);
	}
		
	/**
	 * 用户大礼包领取（旧）
	 */
	public Response<UserPackagePullModel> bagUserPackagePullModel(UserPackagePullModel userPackagePullModel) throws Exception {
		final String apiName = "coupon_bag_userPackage_pull";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(userPackagePullModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("bag coupon_bag_userPackage_pull response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<UserPackagePullModel>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<UserPackagePullModel>("error", "无法获取token!", null);
	}
	
	/**
	* 领取优惠券(通用)
	*/
	public Response<ShareCodeResModel> pullCoupon(String tel, String cpId, String shareCode) throws Exception {
		final String apiName = "coupon_bag_pull_coupon";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);
			postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("tel", tel));
			postParameters.add(new BasicNameValuePair("cpId", cpId));
			postParameters.add(new BasicNameValuePair("shareCode", shareCode));
			
			postMethod.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon bag pullCoupon response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<ShareCodeResModel>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<ShareCodeResModel>("error", "无法获取token!", null);
	}
	
	/**
	 * 券包领取
	 */
	public Response<ShareCodeResModel> packagePull(PackagePullModel packagePullModel) throws Exception {
		final String apiName = "coupon_api_bag_package_pull";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(packagePullModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("bag coupon_api_bag_package_pull response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<ShareCodeResModel>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<ShareCodeResModel>("error", "无法获取token!", null);
	}
	
	/**
	 * 查询优惠券，券包入口（详情）
	 */
	public Response<CouponInfoModel> queryBagCoupon(String userBagId,String tel) throws Exception {
		final String apiName = "coupon_api_bag_queryBagCoupon";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&userBagId="+userBagId+"&tel="+tel);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon queryDetail response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<CouponInfoModel>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<CouponInfoModel>("error", "无法获取token!", null);
	}


	/**
	 * 导入code码 
	 */
	public Response<String> couponImportCode(InfoCodeModel infoCodeModel) throws Exception {
		final String apiName = "coupon_import_code";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(infoCodeModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon import code:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 优惠券绑定code码。优惠券必须是用户领取类型
	 */
	public Response<String> bindingCoupon(BindCpModel bindCpModel) throws Exception {
		final String apiName = "coupon_binding_coupon";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(bindCpModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon binding coupon:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 券码用户领取
	 */
	public Response<String> pullCodeUser(String userTel, String cpId) throws Exception {
		final String apiName = "coupon_pull_code_user";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);
			postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("userTel", userTel));
			postParameters.add(new BasicNameValuePair("cpId", cpId));
			postMethod.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon pull code user:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 查看code批次列表
	 */
	public Response<PageResult<CodeListResModel>> queryCodeList(CodeListParamsModel codeListParamsModel) throws Exception {
		final String apiName = "coupon_querycodelist";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(codeListParamsModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_querycodelist response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<PageResult<CodeListResModel>>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<PageResult<CodeListResModel>>("error", "无法获取token!", null);
	}
	
	/**
	 * 券码取消绑定优惠券
	 * */
	public Response<String> unbindingCoupon(String codePlanId,String platCode) throws Exception {
		final String apiName = "coupon_unbinding";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);
			postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("codePlanId", codePlanId));
			postParameters.add(new BasicNameValuePair("platCode", platCode));
			postMethod.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon unbinding response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	* 删除券码
	*/
	public Response<String> deleteCode(String codePlanId,String platCode) throws Exception {
		final String apiName = "coupon_delete_code";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);
			postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("codePlanId", codePlanId));
			postParameters.add(new BasicNameValuePair("platCode", platCode));
			postMethod.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon delete code response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 券码类型的券是否发送信息
	 */	
	public Response<String> isSendMsg(String codePlanId,Integer isMsg) throws Exception {
		final String apiName = "coupon_api_isSendMsg";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = 
					new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&codePlanId="+codePlanId+"&isMsg="+isMsg);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_api_isSendMsg:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 查询券码短信详情
	 */
	public Response<CodeMsgModel> queryCodeMsg(String codePlanId) throws Exception {
		final String apiName = "coupon_api_coupon_queryCodeMsg";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&codePlanId="+codePlanId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api coupon queryCodeMsg:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<CodeMsgModel>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<CodeMsgModel>("error", "无法获取token!", null);
	}
	
	/**
	 * 短信内容模板设置
	 */
	public Response<String> setMsg(SetMsgParamsModel setMsgParamsModel) throws Exception {
		final String apiName = "coupon_api_coupon_setMsg";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(setMsgParamsModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api coupon setMsg response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
	
	/**
	 * 测试发送短信
	 */
	public Response<String> codeSendMsg(SendMsgParamsModel sendMsgParamsModel) throws Exception {
		final String apiName = "coupon_api_coupon_codeSendMsg";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(sendMsgParamsModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api coupon codeSendMsg response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 创建券包
	 */
	public Response<String> createPackage(PackageInitModel packageInitModel) throws Exception {
		final String apiName = "coupon_api_create_package";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(packageInitModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_api_create_package response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
	
	
	/**
	 * 删除券包
	 */
	public Response<String> deletePackage(String packageId) throws Exception {
		final String apiName = "coupon_api_delete_package";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&packageId="+packageId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_api_delete_package:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
	
	/**
	 * 编辑券包
	 */
	public Response<String> editPackageCoupon(PackageEditModel packageEditModel) throws Exception {
		
		final String apiName = "coupon_api_edit_package_coupon";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(packageEditModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("bag coupon_api_edit_package_coupon response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
		
	}	
	
	
	/**
	 * 查看券包列表  
	 */
	public Response<PageResult<CouponPackageModel>> queryPackageList(PackageListParamsModel packageListParamsModel) throws Exception {
		
		final String apiName = "coupon_api_query_package_list";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(packageListParamsModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_api_query_package_list response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<PageResult<CouponPackageModel>>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<PageResult<CouponPackageModel>>("error", "无法获取token!", null);
	
	}	
	
	/**
	 * 查看券包详情
	 */	
	public Response<CouponPackageModel> packageDetail(String packageId) throws Exception {
		final String apiName = "coupon_api_package_detail";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&packageId="+packageId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_api_package_detail:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<CouponPackageModel>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<CouponPackageModel>("error", "无法获取token!", null);
	}
	
	/**	
	 * 券包冻结
	 */	
	public Response<String> freezePackage(String packageId) throws Exception {
		final String apiName = "coupon_api_freeze_package";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&packageId="+packageId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_api_freeze_package:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
	
	/**
	 * 输入口令
	 * */
	public Response<ActivityModel> checkWord(String word,String activityId) throws Exception {
		
		final String apiName = "coupon_api_activity_checkword";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);
			postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("word", word));
			postParameters.add(new BasicNameValuePair("activityId", activityId));
			postMethod.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api activity checkword response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<ActivityModel>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<ActivityModel>("error", "无法获取token!", null);
	}	
	
	/**
	 * 复制一张优惠券
	 */
	public Response<String> copyCreate(CouponCopyParamsModel couponCopyParamsModel) throws Exception {
		final String apiName = "coupon_api_copy_create";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(couponCopyParamsModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api copy create response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
	
	/**
	 * 用户领取分享券
	 * */
	public Response<String> userPullShare(UserShareModel userShare) throws Exception {
		final String apiName = "coupon_api_bag_userPullShare";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(userShare);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api bag userPullShare response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
	
	/**
	 * 创建一批兑换码
	 */
	public Response<String> createConvertCode(int count) throws Exception {
		final String apiName = "coupon_api_convert_createConvertCode";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&count="+count);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api convert createConvertCode response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 查看兑换码批次列表
	 */
	public Response<PageResult<ConvertCodeListResModel>> convertCodeList
		(ConvertCodeListParamsModel convertCodeListParamsModel) throws Exception {
		
		final String apiName = "coupon_api_convertCodeList";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(convertCodeListParamsModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api convertCodeList response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<PageResult<ConvertCodeListResModel>>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<PageResult<ConvertCodeListResModel>>("error", "无法获取token!", null);
	}
	
	/**
	 * 兑换码绑定优惠券
	 */
	public Response<String> bindConvertCode(ConvertCodeModel convertCodeModel) throws Exception {
		final String apiName = "coupon_api_bindConvertCode";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(convertCodeModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon edit response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 兑换码查询优惠券详情
	 */
	public Response<String> convertCodeDetail(String code) throws Exception {
		final String apiName = "coupon_api_convertCodeDetail";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&code="+code);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_api_convertCodeDetail response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 兑换码查询券包详情
	 */
	public Response<String> convertPackageDetail(String code) throws Exception {
		final String apiName = "coupon_api_convertPackageDetail";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&code="+code);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_api_convertPackageDetail response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 兑换码兑换优惠券
	 */
	public Response<ConvertSuccessModel> convertCoupon(String code, String tel) throws Exception {
		final String apiName = "coupon_api_bag_convertCoupon";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);
			postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("code", code));
			postParameters.add(new BasicNameValuePair("tel", tel));
			postMethod.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api bag convertCoupon response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<ConvertSuccessModel>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<ConvertSuccessModel>("error", "无法获取token!", null);
	}
	
	/**
	 * 兑换码取消绑定优惠券
	 */
	public Response<String> unBindConvertCode(String convertPlanId, String infoId) throws Exception {
		final String apiName = "coupon_api_unBindConvertCode";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);
			postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("convertPlanId", convertPlanId));
			postParameters.add(new BasicNameValuePair("infoId", infoId));
			postMethod.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api unBindConvertCode response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 删除兑换码
	 */
	public Response<String> deleteConvertCode(String convertPlanId) throws Exception {
		final String apiName = "coupon_api_deleteConvertCode";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&convertPlanId="+convertPlanId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon api deleteConvertCode response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 创建商品分类
	 * */
	public Response<String> createSort(SortProductModel sortProductModel) throws Exception {
		final String apiName = "coupon_product_createSort";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(sortProductModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_product_createSort response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
	
	/**
	 * 编辑分类
	 * */
	public Response<String> editSort(SortDetailModel sortDetailModel) throws Exception {
		final String apiName = "coupon_product_editSort";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(sortDetailModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_product_editSort response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
		
	/**
	 * 查询商铺分类列表
	 * */
	public Response<PageResult<SortDetailModel>> 
		querySortList(ProductSortListModel productSortListModel) throws Exception {
		
		final String apiName = "coupon_product_queryList";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(productSortListModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_product_queryList response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<PageResult<SortDetailModel>>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<PageResult<SortDetailModel>>("error", "无法获取token!", null);
	}
	
	/**
	 * 查询商铺分类
	 */
	public Response<ShopSortModel> querySort(String shopCode) throws Exception {
		final String apiName = "coupon_product_query";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&shopCode="+shopCode);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_product_query response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<ShopSortModel>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<ShopSortModel>("error", "无法获取token!", null);
	}
	
	/**
	 * 删除分类
	 */
	public Response<String> deleteSort(String sortId) throws Exception {
		final String apiName = "coupon_product_delete";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&sortId="+sortId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_product_delete response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
	/**
	 * 查看分类详情
	 */
	public Response<SortDetailModel> querySortDetail(String sortId) throws Exception {
		final String apiName = "coupon_product_detail";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&sortId="+sortId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_product_detail response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<SortDetailModel>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<SortDetailModel>("error", "无法获取token!", null);
	}
	
	/**
	 * 店铺活动商品添加 
	 * */
	public Response<String> productManage(ProductModel productModel) throws Exception {
		final String apiName = "coupon_product_manageProduct";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(productModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_product_manageProduct response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}	
	
	/**
	 * 店铺活动商品查看
	 */
	public Response<PageResult<ProductDetailModel>> productList(ProductListParamsModel productListParamsModel) throws Exception {
		final String apiName = "coupon_product_productList";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			String couponApiUrl = couponEndPoint + "/" + apiName + "?access_token=" + accessToken;
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpPost postMethod = new HttpPost(couponApiUrl);

			String jsonBody = JSONObject.toJSONString(productListParamsModel);
			postMethod.setEntity(new StringEntity(jsonBody, "UTF-8"));
			postMethod.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpResponse response = client.execute(postMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon_product_productList response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<PageResult<ProductDetailModel>>>() {
				});
			} finally {
				response.close();
				postMethod.releaseConnection();
			}
		}
		return new Response<PageResult<ProductDetailModel>>("error", "无法获取token!", null);
	}
	
	/**
	 * 店铺活动商品删除
	 */
	public Response<String> deleteProduct(String shopCode, String productId) throws Exception {
		final String apiName = "coupon_product_deleteProduct";
		String accessToken=this.accessToken(apiScopeName);
		if (StringUtils.isNotBlank(accessToken)) {
			final CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();
			final HttpGet httpMethod = new HttpGet(couponEndPoint + "/"+apiName+"?access_token="+accessToken+"&shopCode="+shopCode+"&productId="+productId);
			
			CloseableHttpResponse response = client.execute(httpMethod);
			try {
				final String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
				log.debug("coupon product deleteProduct response:" + responseString);
				return JSON.parseObject(responseString, new TypeReference<Response<String>>() {
				});
			} finally {
				response.close();
				httpMethod.releaseConnection();
			}
		}
		return new Response<String>("error", "无法获取token!", null);
	}
	
}
