package com.cloudjet.coupon.sdk.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpHelper {
	
	
	private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);
	
	public static JSONObject httpGet(String url) throws OApiException{
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
        		setSocketTimeout(20000).setConnectTimeout(20000).build();
        httpGet.setConfig(requestConfig);

        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

            	logger.warn("request url failed, http code={}, url={}", response.getStatusLine().getStatusCode(), url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                return result;
            }
        } catch (IOException e) {
        	logger.error("request url={}, exception, msg={}", url, e.getMessage());
        	logger.error("Http Error ==> ", e);
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
	
	
	public static JSONObject httpPost(String url, Object data) throws OApiException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
        		setSocketTimeout(2000000).setConnectTimeout(2000000).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");

        try {
        	StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
            httpPost.setEntity(requestEntity);
            
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                logger.warn("request url failed, http code={}, url={}", response.getStatusLine().getStatusCode(), url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                return result;
            }
        } catch (IOException e) {
        	logger.error("request url={}, exception, msg={}", url, e.getMessage());
        	logger.error("Http Error ==> ", e);
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
	
	public static String httpPostParam(String url, Map<String, Object> params, String encoding){
		String body = "";  
		//创建httpclient对象  
		CloseableHttpClient httpClient = HttpClients.createDefault();
		 CloseableHttpResponse response = null;
		try {
			 //创建post方式请求对象
			HttpPost httpPost = new HttpPost(url);
			 //装填参数  
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        if(params != null){  
	            for (Entry<String, Object> entry : params.entrySet()) {  
	                nvps.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));  
	            }  
	        }  
	        //设置参数到请求对象中  
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
			 //设置header信息  
	        //指定报文头【Content-type】、【User-Agent】  
//	        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
//	        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	        
	        //执行请求操作，并拿到结果（同步阻塞）  
	        response = httpClient.execute(httpPost);
	      //获取结果实体  
	        HttpEntity entity = response.getEntity();  
	        if (entity != null) {  
	            //按指定编码转换结果实体为String类型  
	            body = EntityUtils.toString(entity, encoding);  
	        }
	        EntityUtils.consume(entity);  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//释放链接  
	        try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} 
        return body;
    }
		
}
