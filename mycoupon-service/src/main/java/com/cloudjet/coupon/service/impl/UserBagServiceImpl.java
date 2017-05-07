package com.cloudjet.coupon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.webbeans.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.cloudjet.coupon.core.MqSetting;
import com.cloudjet.coupon.core.SysConfigSetting;
import com.cloudjet.coupon.entity.CouponConvertUserEntity;
import com.cloudjet.coupon.entity.CouponInfoEntity;
import com.cloudjet.coupon.entity.CouponPackageEntity;
import com.cloudjet.coupon.entity.CouponShopInfoEntity;
import com.cloudjet.coupon.entity.InfoChannelDetailEntity;
import com.cloudjet.coupon.entity.InfoChannelEntity;
import com.cloudjet.coupon.entity.InfoCodeEntity;
import com.cloudjet.coupon.entity.InfoCodeMsgTagEntity;
import com.cloudjet.coupon.entity.InfoCodePlanEntity;
import com.cloudjet.coupon.entity.InfoScopeEntity;
import com.cloudjet.coupon.entity.UserBagEntity;
import com.cloudjet.coupon.entity.UserBagLogEntity;
import com.cloudjet.coupon.entity.UserBagOrderEntity;
import com.cloudjet.coupon.entity.UserShareEntity;
import com.cloudjet.coupon.entity.dto.ConvertCodeDetailEntity;
import com.cloudjet.coupon.entity.dto.CouponInfoDetailEntity;
import com.cloudjet.coupon.entity.dto.SendRecordDeatilEntity;
import com.cloudjet.coupon.entity.dto.UserBagFindEntity;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.mapper.CouponConvertCodeMapper;
import com.cloudjet.coupon.mapper.CouponConvertUserMapper;
import com.cloudjet.coupon.mapper.CouponInfoMapper;
import com.cloudjet.coupon.mapper.CouponPackageInfoMapper;
import com.cloudjet.coupon.mapper.CouponPackageMapper;
import com.cloudjet.coupon.mapper.CouponShopInfoMapper;
import com.cloudjet.coupon.mapper.InfoCodeMapper;
import com.cloudjet.coupon.mapper.InfoCodeMsgTagMapper;
import com.cloudjet.coupon.mapper.InfoCodePlanMapper;
import com.cloudjet.coupon.mapper.InfoScopeMapper;
import com.cloudjet.coupon.mapper.InfoUserSourceDetailMapper;
import com.cloudjet.coupon.mapper.InfoUserSourceLevelMapper;
import com.cloudjet.coupon.mapper.SendRecordMapper;
import com.cloudjet.coupon.mapper.UserBagLogMapper;
import com.cloudjet.coupon.mapper.UserBagMapper;
import com.cloudjet.coupon.mapper.UserBagOrderMapper;
import com.cloudjet.coupon.mapper.UserShareMapper;
import com.cloudjet.coupon.model.ChannelDetailModel;
import com.cloudjet.coupon.model.ChannelModel;
import com.cloudjet.coupon.model.ConvertCodeModel.ConvertTypeEnum;
import com.cloudjet.coupon.model.ConvertSuccessModel;
import com.cloudjet.coupon.model.CoupnConvertDetailModel.ConvertCodeStatus;
import com.cloudjet.coupon.model.CouponInfoModel;
import com.cloudjet.coupon.model.CouponInfoModel.CouponInfoStatus;
import com.cloudjet.coupon.model.CouponInfoModel.CouponInfoType;
import com.cloudjet.coupon.model.CouponInfoModel.CouponIsCode;
import com.cloudjet.coupon.model.CouponInfoModel.CouponIsShare;
import com.cloudjet.coupon.model.CouponPackageModel.CouponPackageStatus;
import com.cloudjet.coupon.model.CouponShopInfoModel;
import com.cloudjet.coupon.model.InfoCodeModel.CodeMsgStatus;
import com.cloudjet.coupon.model.InfoConditionModel;
import com.cloudjet.coupon.model.InfoScopeModel;
import com.cloudjet.coupon.model.InfoSendRecordModel;
import com.cloudjet.coupon.model.JphUserResponse;
import com.cloudjet.coupon.model.PackageInitModel.PackageIsShare;
import com.cloudjet.coupon.model.PackagePullModel;
import com.cloudjet.coupon.model.UserBagLogModel.UserBagLogType;
import com.cloudjet.coupon.model.UserBagModel;
import com.cloudjet.coupon.model.UserBagModel.UserBagStatus;
import com.cloudjet.coupon.model.UserBagPageModel;
import com.cloudjet.coupon.model.UserPackagePullModel;
import com.cloudjet.coupon.model.UserSourceModel.UserSourceType;
import com.cloudjet.coupon.request.CouponVerifyRequest;
import com.cloudjet.coupon.request.ShareUserBagModel;
import com.cloudjet.coupon.res.model.MsgResponse;
import com.cloudjet.coupon.res.model.ShareCodeResModel;
import com.cloudjet.coupon.res.model.UserBagResModel.CodeMsgStatusEnum;
import com.cloudjet.coupon.service.UserBagService;
import com.cloudjet.coupon.util.CouponEncryptUtil;
import com.cloudjet.coupon.util.DateUtil;
import com.cloudjet.coupon.util.HttpHelper;
import com.cloudjet.coupon.util.PageResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service
public class UserBagServiceImpl implements UserBagService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserBagServiceImpl.class);

	@Autowired
	private  UserBagMapper userBagMapper;
	
	@Autowired
	private  UserBagOrderMapper userBagOrderMapper;
		
	@Autowired
	private InfoScopeMapper infoScopeMapper;
	
	@Autowired
	private  UserShareMapper userShareMapper;
	
	@Autowired
	private CouponInfoMapper couponInfoMapper;
	
	@Autowired
	private UserBagLogMapper userBagLogMapper;
	
	@Autowired
	private InfoUserSourceDetailMapper infoUserSourceDetailMapper;
	
	@Autowired
	private InfoUserSourceLevelMapper infoUserSourceLevelMapper;
	
	@Autowired
	private InfoCodeMapper infoCodeMapper;
	
	@Autowired
	private InfoCodePlanMapper infoCodePlanMapper;
	
	@Autowired
	private InfoCodeMsgTagMapper infoCodeMsgTagMapper;
	
	@Autowired
	private CouponPackageMapper couponPackageMapper;
	
	@Autowired
	private CouponPackageInfoMapper couponPackageInfoMapper;
	
	@Autowired
	private CouponShopInfoMapper couponShopInfoMapper;
	
	@Autowired
	private CouponConvertUserMapper couponConvertUserMapper;
	
	@Autowired
	private MqSetting mqSetting;
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private SendRecordMapper sendRecordMapper;
	
	@Autowired
	private CouponConvertCodeMapper couponConvertCodeMapper;
	
	@Autowired
	private SysConfigSetting sysConfigSetting;
		
	@Override
	public PageResult<UserBagModel> queryList( UserBagPageModel userBagPageModel) {
		
		PageResult<UserBagModel> page = new PageResult<UserBagModel>();
		List<UserBagModel> list = Lists.newArrayList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", (userBagPageModel.getPageNo()-1) * userBagPageModel.getPageSize());
		params.put("limit", userBagPageModel.getPageSize());
		params.put("tel", userBagPageModel.getTel());
		params.put("platCode", userBagPageModel.getPlatCode());
		//此处要判断线上券线下券（未过期的）
		params.put("costType", userBagPageModel.getCostType());
		
		//查询一共多少张优惠券
		int total = userBagMapper.findTotal(params);
		int totalPage = (total + userBagPageModel.getPageSize() - 1) / userBagPageModel.getPageSize();
		
		page.setTotalResults(total);
		page.setHasNext(userBagPageModel.getPageNo()+1 <=  totalPage);
		
		//分页查询
		List<UserBagFindEntity> userBagFinds = userBagMapper.queryBags(params);
		if (!CollectionUtils.isEmpty(userBagFinds)) {
			
			userBagFinds.forEach(userBagFind ->{
				
				UserBagModel ubm = new UserBagModel();
				ubm.setBeginTime(userBagFind.getBeginTime());
				ubm.setCpName(userBagFind.getCpName());
				ubm.setCpId(userBagFind.getCpId());
				ubm.setDueTime(userBagFind.getDueTime());
				ubm.setMemo(userBagFind.getMemo());
				ubm.setRule(userBagFind.getRule());
				ubm.setType(userBagFind.getType());
				ubm.setCouponType(userBagFind.getCouponType());
				ubm.setPar(userBagFind.getPar());
				ubm.setStartFee(userBagFind.getStartFee());
				ubm.setEndFee(userBagFind.getEndFee());	
				ubm.setUserBagId(userBagFind.getUserBagId());
				
				if (StringUtils.isNotBlank(userBagFind.getCpCode())) {
					ubm.setCpCode(userBagFind.getCpCode());
				}
				
				if (StringUtils.isNotBlank(userBagFind.getRichText())) {
					ubm.setRichText(userBagFind.getRichText());
				}
				
				if (StringUtils.isNotBlank(userBagFind.getUrl())) {
					ubm.setUrl(userBagFind.getUrl());
				}
				
				if (StringUtils.isNotBlank(userBagFind.getUrlButton())) {
					ubm.setUrlButton(userBagFind.getUrlButton());
				}
				
				ubm.setCpCode(userBagFind.getCpCode());
				
				list.add(ubm);
			});
		}
		
		page.setData(list);
		return page;
	}

	@Override
	@Transactional
	public void alterStatus(String orderNo,String tel) {

		//根据订单号找到用户券包信息
		List<UserBagEntity> ubes = userBagMapper.findUserBags(orderNo);
		
		if (!CollectionUtils.isEmpty(ubes)) {
			
			ubes.forEach(ube ->{
				
				//校验查询到的用户手机号和当前手机号是否匹配
				if (!StringUtils.equals(ube.getUserTel(),tel)) {
					logger.error("订单手机号与用户号码不匹配，当前用户号码 tel={}",tel);
					throw new CouponException("S20");
				}
				
				//只有当券状态为已使用（未付款）才能更改为已使用（付款）
				if (ube.getStatus() != UserBagStatus.unpay.ordinal()) {
					logger.error("券状态非已使用（未付款），参数订单号={}"+orderNo+",参数手机号={}"+tel);
					throw new CouponException("S21");
				}
				
				//修改券状态为已经付款
				userBagMapper.update(ube.getId(),UserBagStatus.cost.ordinal());
					
				//添加券包日志状态，为已付款。
				UserBagLogEntity userBagLogEntity = new UserBagLogEntity();
				userBagLogEntity.setType(UserBagLogType.cost.ordinal());				
				userBagLogEntity.setUserBagId(ube.getId());
				userBagLogMapper.add(userBagLogEntity);
			
			});			
		}
	
	}


	/**
	 * 旧方法
	 * */
	@Override
	@Transactional
	public void pullUserPackage(UserPackagePullModel userPackagePullModel) throws CouponException {
		
		List<String> cpIds = userPackagePullModel.getCpIds();
		String userTel = userPackagePullModel.getTel();
	
		HashSet<String> set= Sets.newHashSet();//使用set集合校验cpIds集合是否有重复元素
		cpIds.forEach(cpId ->{
			set.add(cpId);
		});
		
		//优惠券ID是否有重复
		if (cpIds.size() != set.size()) {
			logger.error("券礼包领取失败，优惠券id重复");
			throw new CouponException("S22");
		}
		
		//遍历cpId集合，领取。
		cpIds.forEach(cpId ->{
			
			CouponInfoEntity cie = couponInfoMapper.query(cpId);//查询券
			if (null == cie) {
				logger.error("当前用户 tel={}",userTel+":优惠券不存在，领取失败");
				throw new CouponException("S14");
			}
			
			int count = userBagMapper.countSingel(cpId,userTel);
			if (!(count == 0 || count < cie.getLimitNum())) {
				logger.error("当前用户 tel={}",userTel+":领取超出限制");
				throw new CouponException("S01");
			}
			
			int stock = cie.getStock();//库存
			if (stock <= 0) {
				logger.error("当前库存={}",stock+":优惠券库存不足");
				throw new CouponException("S15");
			}
			
			//领取优惠券插入券包
			//扣除库存
			couponInfoMapper.editStock(cpId);
					
			//插入用户卡券包  
			UserBagEntity ube = new UserBagEntity();
			ube.setInfoId(cpId);
			ube.setUserTel(userTel); 
			ube.setStatus(UserBagStatus.get.ordinal());
			userBagMapper.add(ube);
			
			//判断是否是券码券
			if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，是否发送短信提醒	
				
				//1、绑定的券码是否还有剩余，选一个券码
				InfoCodeEntity infoCodeEntity = infoCodeMapper.queryCodeId(cie.getId());
				if (infoCodeEntity == null) {
					logger.error("code码当前库存={}",":库存不足");
					throw new CouponException("S32");
				}
				
				//2、code与用户券包绑定
				infoCodeMapper.bindBagId(ube.getId(), infoCodeEntity.getId());
				
				//3、找到这个券码对应的券码批次，判断该批次是否发短信
				InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(infoCodeEntity.getCodePlanId());
				if (StringUtils.equals(infoCodePlanEntity.getIsMsg(),Integer.toString(CodeMsgStatus.yes.ordinal()))) {
					//4、发短信
					this.codeSend(cie, userTel,infoCodeEntity,infoCodePlanEntity);
				}
			}
			
			//插入券包日志表
			UserBagLogEntity uble = new UserBagLogEntity();
			uble.setUserBagId(ube.getId());
			uble.setType(UserBagLogType.get.ordinal());
			userBagLogMapper.add(uble);
		
		});
		
	}



	@Override
	@Transactional
	public ShareCodeResModel pullCoupon(String userTel, String cpId, String shareCode) throws CouponException {
		
		CouponInfoEntity cie = couponInfoMapper.query(cpId);
		
		if (null == cie) {
			logger.error("当前用户 tel={}",userTel+":优惠券不存在，领取失败");
			throw new CouponException("S14");
		}
		
		if (cie.getStatus() != CouponInfoStatus.send.ordinal()) {
			logger.error("当前券cpId={}",cpId+":优惠券不是发布状态，领取失败");
			throw new CouponException("S16");
		}
		
		if (new Date().getTime() < cie.getBeginTime().getTime()) {
			logger.error("非优惠券领取时间");
			throw new CouponException("S53");
		}
		
		if (new Date().getTime() >= cie.getEndTime().getTime()) {
			logger.error("超过优惠券领取截止时间");
			throw new CouponException("S46");
		}
		
		if (new Date().getTime() >= cie.getDueTime().getTime()) {
			
			logger.error("超过优惠券使用时间");
			throw new CouponException("S17");
		}
		
		int count = userBagMapper.countSingel(cpId,userTel);
		if (!(count == 0 || count < cie.getLimitNum())) {
			//领取超出限制
			logger.error("当前用户 tel={}",userTel+":领取超出限制");
			throw new CouponException("S01");
		}
		
		int stock = cie.getStock();//库存
		if (stock <= 0) {
			logger.error("当前库存={}",stock+":优惠券库存不足");
			throw new CouponException("S15");
		}
		//判断用户领取类型
		if (cie.getUserSourceType() == UserSourceType.common.ordinal()) {//普通类型
			//领取优惠券插入券包
			//扣除库存
			couponInfoMapper.editStock(cpId);
					
			//插入用户卡券包  
			UserBagEntity ube = new UserBagEntity();
			ube.setInfoId(cpId);
			ube.setUserTel(userTel); 
			ube.setStatus(UserBagStatus.get.ordinal());
			userBagMapper.add(ube);
			
			//判断是否是券码券
			if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，是否发送短信提醒		
				//1、绑定的券码是否还有剩余，选一个券码
				InfoCodeEntity infoCodeEntity = infoCodeMapper.queryCodeId(cie.getId());
				if (infoCodeEntity == null) {
					logger.error("code码当前库存={}",":库存不足");
					throw new CouponException("S32");
				}
				
				//2、code与用户券包绑定
				infoCodeMapper.bindBagId(ube.getId(), infoCodeEntity.getId());
				
				//3、找到这个券码对应的券码批次，判断该批次是否发短信
				InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(infoCodeEntity.getCodePlanId());
				if (StringUtils.equals(infoCodePlanEntity.getIsMsg(),Integer.toString(CodeMsgStatus.yes.ordinal()))) {
					//4、发短信
					this.codeSend(cie, userTel,infoCodeEntity,infoCodePlanEntity);
				}
			}
			//插入券包日志表
			UserBagLogEntity uble = new UserBagLogEntity();
			uble.setUserBagId(ube.getId());
			uble.setType(UserBagLogType.get.ordinal());
			userBagLogMapper.add(uble);
		} else if (cie.getUserSourceType() == UserSourceType.level.ordinal()) {
			
			//远程调用接口，找出对应等级
			Map<String, Object> params = Maps.newHashMap();
			params.put("mobilephoneNo",userTel);
					
			String resStr = HttpHelper.httpPostParam(sysConfigSetting.getLevelURL(), params ,"UTF-8");
			JSONObject jsonObject = JSON.parseObject(resStr);
			JphUserResponse levelRes = JSONObject.toJavaObject(jsonObject, JphUserResponse.class);
			
			String level = null;
			if (!StringUtils.equals("200", levelRes.getCode())) {
				logger.error("用户"+userTel+"={}",resStr);
			} else {
				level = levelRes.getUserLevel();
			} 
							
			List<String> levels = infoUserSourceLevelMapper.queryLevels(cpId);//这张优惠券对应的领取等级区间
			//等级用户
			if (levels.contains(level)) {//是否符合等级
				//扣除库存
				couponInfoMapper.editStock(cpId);
						
				//插入用户卡券包 
				UserBagEntity ube = new UserBagEntity();
				ube.setInfoId(cpId);
				ube.setUserTel(userTel); 
				ube.setStatus(UserBagStatus.get.ordinal());
				userBagMapper.add(ube);
				//判断是否是券码券
				if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，是否发送短信提醒		
					//1、绑定的券码是否还有剩余，选一个券码
					InfoCodeEntity infoCodeEntity = infoCodeMapper.queryCodeId(cie.getId());
					if (infoCodeEntity == null) {
						logger.error("code码当前库存={}",":库存不足");
						throw new CouponException("S32");
					}
					
					//2、code与用户券包绑定
					infoCodeMapper.bindBagId(ube.getId(), infoCodeEntity.getId());
					
					//3、找到这个券码对应的券码批次，判断该批次是否发短信
					InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(infoCodeEntity.getCodePlanId());
					if (StringUtils.equals(infoCodePlanEntity.getIsMsg(),Integer.toString(CodeMsgStatus.yes.ordinal()))) {
						//4、发短信
						this.codeSend(cie, userTel,infoCodeEntity,infoCodePlanEntity);
					}
				}
				//差不券包日志表
				UserBagLogEntity uble = new UserBagLogEntity();
				uble.setUserBagId(ube.getId());
				uble.setType(UserBagLogType.get.ordinal());
						
				userBagLogMapper.add(uble);
			} else {
				logger.error("用户等级不符合，领取失败,当前用户 tel={}",userTel);
				throw new CouponException("S18");
			}
			
		} else if (cie.getUserSourceType() == UserSourceType.importUser.ordinal()) {
			
			List<String> user =  infoUserSourceDetailMapper.queryUserByTel(userTel,cpId);
			if (!CollectionUtils.isEmpty(user)) {
				//扣除库存
				couponInfoMapper.editStock(cpId);
						
				//插入用户卡券包  
				UserBagEntity ube = new UserBagEntity();
				ube.setInfoId(cpId);
				ube.setUserTel(userTel); 
				ube.setStatus(UserBagStatus.get.ordinal());
				userBagMapper.add(ube);
				
				//判断是否是券码券
				if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，是否发送短信提醒		
					//1、绑定的券码是否还有剩余，选一个券码
					InfoCodeEntity infoCodeEntity = infoCodeMapper.queryCodeId(cie.getId());
					if (infoCodeEntity == null) {
						logger.error("code码当前库存={}",":库存不足");
						throw new CouponException("S32");
					}
					
					//2、code与用户券包绑定
					infoCodeMapper.bindBagId(ube.getId(), infoCodeEntity.getId());
					
					//3、找到这个券码对应的券码批次，判断该批次是否发短信
					InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(infoCodeEntity.getCodePlanId());
					if (StringUtils.equals(infoCodePlanEntity.getIsMsg(),Integer.toString(CodeMsgStatus.yes.ordinal()))){
						//4、发短信
						this.codeSend(cie, userTel,infoCodeEntity,infoCodePlanEntity);
					}
				}
				
				//插入券包日志表
				UserBagLogEntity uble = new UserBagLogEntity();
				uble.setUserBagId(ube.getId());
				uble.setType(UserBagLogType.get.ordinal());
						
				userBagLogMapper.add(uble);
			}else{
				logger.error("用户未导入，领取失败,当前用户 tel={}",userTel);
				throw new CouponException("S181");
			}
					
		}
		
		ShareCodeResModel scrm = new ShareCodeResModel();
		
		if (cie.getIsShare() == CouponIsShare.yes.ordinal()) {
			scrm.setIsShare(CouponIsShare.yes.ordinal());
			scrm.setEncrypShareCode(CouponEncryptUtil.encrypt(userTel+"-"+cpId+"-0-"+System.currentTimeMillis()));
			
			if (!StringUtil.isBlank(shareCode)) {
				//券为分享券，且是分享出去后，由用户领取，则插入分享用户领取记录表，发出去消息通知
				//1.拆解shareCode 分享用户手机号-券(包)id-0(券)1(包)-分享时间戳
				//15136219337-0e663c7cdd8711e68d38ecf4bbd34bf4-0-1486532838432
				String shareCodeStr = CouponEncryptUtil.decode(shareCode);
				String[]  shareCodeArrays = StringUtils.split(shareCodeStr, "-");
				//2.发送消息到消息队列
				ShareUserBagModel subm = new ShareUserBagModel();
				
				//消息记录店铺
				List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(cpId);
				List<String> platCodes = Lists.newArrayList();
				csies.forEach(csim ->{
					platCodes.add(csim.getPlatCode());
				});
				subm.setShareTel(shareCodeArrays[0]);
				subm.setCpId(shareCodeArrays[1]);
				subm.setType(shareCodeArrays[2]);
				subm.setPullTel(userTel);
				subm.setShareTime(new Date(Long.valueOf(shareCodeArrays[3])));
				subm.setPlatCodes(platCodes);
				
				Message msg = new Message(mqSetting.getTopic(),mqSetting.getTagsPull(),JSON.toJSONString(subm).getBytes());
				// 设置代表消息的业务关键属性，请尽可能全局唯一
		        // 以方便您在无法正常收到消息情况下，可通过MQ 控制台查询消息并补发
		        // 注意：不设置也不会影响消息正常收发
		        msg.setKey(mqSetting.getMqkey());
		        SendResult sendResult = producer.send(msg);
				System.out.println("send success: " + sendResult.getMessageId());
				
				//3插入分享表
				UserShareEntity userShareEntity = new UserShareEntity();
				userShareEntity.setShareTel(shareCodeArrays[0]);
				userShareEntity.setCpId(shareCodeArrays[1]);
				userShareEntity.setType(shareCodeArrays[2]);
				userShareEntity.setPullTel(userTel);
				userShareEntity.setShareTime(new Date(Long.valueOf(shareCodeArrays[3])));
				userShareMapper.add(userShareEntity);
			}
		} else {
			scrm.setIsShare(CouponIsShare.no.ordinal());
		}
		
		return scrm;
	}

	//券码券发送消息
	public void codeSend(CouponInfoEntity cie,String tel,InfoCodeEntity ice,InfoCodePlanEntity icpe){
		
		//4.发送消息
		Map<String, Object> params = Maps.newHashMap();
		StringBuffer buffer = new StringBuffer();
		buffer.append(cie.getCpName()+",券已经到账,");
	
		if (StringUtils.isNotBlank(icpe.getMsgTemplate())) {
			buffer.append(icpe.getParam());
		}
		
		buffer.append(ice.getCpCode());
		buffer.append(" 。请于" +DateUtil.getTimeDay(cie.getDueTime())+"前使用。");
		
		if (StringUtils.isNotBlank(icpe.getMsgTemplate())) {
			buffer.append(icpe.getMsgTemplate());
		}
		
		if (StringUtils.isNotBlank(icpe.getMsgUrl())) {
			buffer.append("。"+icpe.getMsgUrl()); 
		}
		
		params.put("channel", icpe.getMsgTag());
		params.put("text",buffer.toString());
		params.put("mobile", tel);
		
		String resStr = HttpHelper.httpPostParam(sysConfigSetting.getMsgURL(), params ,"UTF-8");
		JSONObject jsonObject = JSON.parseObject(resStr);
		MsgResponse msgRes = JSONObject.toJavaObject(jsonObject, MsgResponse.class);

		//记录短信是否发送成功
		InfoCodeMsgTagEntity infoCodeMsgTagEntity = new InfoCodeMsgTagEntity();
		infoCodeMsgTagEntity.setCpCode(ice.getCpCode());
		infoCodeMsgTagEntity.setInfoId(cie.getId());
		infoCodeMsgTagEntity.setTel(tel);
		
		if (!StringUtils.equals("200", msgRes.getResultCode())) {
			infoCodeMsgTagEntity.setStatus(CodeMsgStatusEnum.fail.ordinal());
			logger.error("code码用户"+tel+"={}",resStr);
		} else {
			infoCodeMsgTagEntity.setStatus(CodeMsgStatusEnum.success.ordinal());
		}
		
		infoCodeMsgTagMapper.save(infoCodeMsgTagEntity);
	}

	@Override
	@Transactional
	public ShareCodeResModel packagePull(PackagePullModel packagePullModel) throws CouponException {
		
		//领取券包要求券包状态是已绑定
		CouponPackageEntity couponPackageEntity = couponPackageMapper.query(packagePullModel.getPackageId());
		if (couponPackageEntity.getStatus() != CouponPackageStatus.bind.ordinal()) {
			logger.error("券包状态不是已绑定 PackageId={}",packagePullModel.getPackageId());
			throw new CouponException("S41");
		}
		
		//1、判断券包是否绑定优惠券
		List<CouponInfoDetailEntity> cides = couponPackageInfoMapper.queryInfoDetail(packagePullModel.getPackageId());
		if (CollectionUtils.isEmpty(cides)) {
			logger.error("券包未绑定优惠券 PackageId={}",packagePullModel.getPackageId());
			throw new CouponException("S36");
		}
		
		//2、判断券包领取时间
		CouponPackageEntity cpe = couponPackageMapper.query(packagePullModel.getPackageId());
		if (new Date().getTime() >= cpe.getEndTime().getTime()) {
			logger.error("超过券包领取截止时间,券包id={}",packagePullModel.getPackageId());
			throw new CouponException("S38");
		}

		//3、校验优惠券是否能领取，插入用户券包
		for (CouponInfoDetailEntity cide:cides) {
			String userTel = packagePullModel.getTel();
			int count = userBagMapper.countSingel(cide.getCpId(),userTel);//已领取优惠券数量
			CouponInfoEntity cie = couponInfoMapper.query(cide.getCpId());//查询券详情
			if (null == cie) {
				logger.error("当前用户 tel={}",userTel+":优惠券不存在，领取失败");
				throw new CouponException("S14");
			}
			
			if (cie.getStatus() != CouponInfoStatus.send.ordinal()) {
				logger.error("当前券cpId={}",cide.getCpId()+":优惠券不是发布状态，领取失败");
				throw new CouponException("S16");
			}
			
			
			if (new Date().getTime() < cie.getBeginTime().getTime()) {
				logger.error("非优惠券领取时间");
				throw new CouponException("S53");
			}
			
			if (new Date().getTime() >= cie.getEndTime().getTime()) {
				logger.error("超过优惠券领取截止时间");
				throw new CouponException("S46");
			}
			
			if (new Date().getTime() >= cie.getDueTime().getTime()) {
				logger.error("超过优惠券使用时间");
				throw new CouponException("S17");
			}
			
			if (!(count == 0 || count < cie.getLimitNum())) {
				//领取超出限制
				logger.error("当前用户 tel={}",userTel+":领取超出限制");
				throw new CouponException("S37");
			}
			
			int stock = cie.getStock();//库存
			if (stock <= 0) {
				logger.error("当前库存={}",stock+":优惠券库存不足");
				throw new CouponException("S15");
			}
			
			//领取成功，扣除库存
			couponInfoMapper.editStock(cide.getCpId());
					
			//插入用户卡券包  
			UserBagEntity ube = new UserBagEntity();
			ube.setInfoId(cide.getCpId());
			ube.setUserTel(userTel); 
			ube.setStatus(UserBagStatus.get.ordinal());
			userBagMapper.add(ube);
			
			//判断是否是券码券
			if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，是否发送短信提醒		
				//1、绑定的券码是否还有剩余，选一个券码
				InfoCodeEntity infoCodeEntity = infoCodeMapper.queryCodeId(cie.getId());
				if (infoCodeEntity == null) {
					logger.error("code码当前库存={}",":库存不足");
					throw new CouponException("S32");
				}
				
				//2、code与用户券包绑定
				infoCodeMapper.bindBagId(ube.getId(), infoCodeEntity.getId());
				
				//3、找到这个券码对应的券码批次，判断该批次是否发短信
				InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(infoCodeEntity.getCodePlanId());
				if (StringUtils.equals(infoCodePlanEntity.getIsMsg(),Integer.toString(CodeMsgStatus.yes.ordinal()))) {
					//4、发短信
					this.codeSend(cie, userTel,infoCodeEntity,infoCodePlanEntity);
				}
			}
			
			//插入券包日志表
			UserBagLogEntity uble = new UserBagLogEntity();
			uble.setUserBagId(ube.getId());
			uble.setType(UserBagLogType.get.ordinal());
			userBagLogMapper.add(uble);

		}
		
		ShareCodeResModel scrm = new ShareCodeResModel();
		
		if (cpe.getIsShare() == PackageIsShare.yes.ordinal()) {
			scrm.setIsShare(PackageIsShare.yes.ordinal());
			scrm.setEncrypShareCode(CouponEncryptUtil.encrypt(packagePullModel.getTel()+"-"+packagePullModel.getPackageId()+"-1-"+System.currentTimeMillis()));
		
			if (!StringUtil.isBlank(packagePullModel.getShareCode())) {
				//券为分享券，且是分享出去后，由用户领取，则插入分享用户领取记录表，发出去消息通知
				//1.拆解shareCode 分享用户手机号-券(包)id-0(券)1(包)-分享时间戳
				//15136219337-0e663c7cdd8711e68d38ecf4bbd34bf4-0-1486532838432
				String shareCodeStr = CouponEncryptUtil.decode(packagePullModel.getShareCode());
				String[]  shareCodeArrays = StringUtils.split(shareCodeStr, "-");
				//2.发送消息到消息队列
				ShareUserBagModel subm = new ShareUserBagModel();
						
				//消息记录店铺
				CouponPackageEntity cp = couponPackageMapper.query(shareCodeArrays[1]);
				List<String> platCodes = Lists.newArrayList();
				platCodes.add(cp.getPlatCode());
				subm.setShareTel(shareCodeArrays[0]);
				subm.setCpId(shareCodeArrays[1]);
				subm.setType(shareCodeArrays[2]);
				subm.setPullTel(packagePullModel.getTel());
				subm.setShareTime(new Date(Long.valueOf(shareCodeArrays[3])));
				subm.setPlatCodes(platCodes);
				
				Message msg = new Message(mqSetting.getTopic(),mqSetting.getTagsPull(),JSON.toJSONString(subm).getBytes());
				// 设置代表消息的业务关键属性，请尽可能全局唯一
		        // 以方便您在无法正常收到消息情况下，可通过MQ 控制台查询消息并补发
		        // 注意：不设置也不会影响消息正常收发
		        msg.setKey(mqSetting.getMqkey());
		        SendResult sendResult = producer.send(msg);
				System.out.println("send success: " + sendResult.getMessageId());
				
				//3插入分享表
				UserShareEntity userShareEntity = new UserShareEntity();
				userShareEntity.setShareTel(shareCodeArrays[0]);
				userShareEntity.setCpId(shareCodeArrays[1]);
				userShareEntity.setType(shareCodeArrays[2]);
				userShareEntity.setPullTel(packagePullModel.getTel());
				userShareEntity.setShareTime(new Date(Long.valueOf(shareCodeArrays[3])));
				userShareMapper.add(userShareEntity);
			}
		} else {
			scrm.setIsShare(PackageIsShare.no.ordinal());
		}
		
		return scrm;
	}
	
	@Override
	public CouponInfoModel queryBagCoupon(String userBagId ,String tel) throws CouponException{
			
		CouponInfoModel couponInfoModel = new CouponInfoModel();
		//查看券详细信息
		CouponInfoDetailEntity cid= userBagMapper.queryCoupon(userBagId, tel);
		
		if (null != cid) {
			couponInfoModel.setCpId(cid.getCpId());
			couponInfoModel.setCpName(cid.getCpName());
			couponInfoModel.setPreWay(cid.getPreWay());
			couponInfoModel.setCheckNo(cid.getCheckNo());
			couponInfoModel.setStartTime(cid.getStartTime());
			couponInfoModel.setEndTime(cid.getEndTime());
			couponInfoModel.setCirculation(cid.getCirculation());
			couponInfoModel.setLimitNum(cid.getLimitNum());
			couponInfoModel.setStock(cid.getStock());
			couponInfoModel.setType(cid.getType());
			couponInfoModel.setBeginTime(cid.getBeginTime());
			couponInfoModel.setDueTime(cid.getDueTime());
			couponInfoModel.setStatus(cid.getStatus());
			couponInfoModel.setIsCode(cid.getIsCode());
			couponInfoModel.setIsBindCode(cid.getIsBindCode());
			couponInfoModel.setCostType(cid.getCostType());
			couponInfoModel.setRule(cid.getRule());
			couponInfoModel.setMemo(cid.getMemo());
			couponInfoModel.setCreateTime(cid.getCreateTime());
			couponInfoModel.setUpdateTime(cid.getUpdateTime());
			couponInfoModel.setRichText(cid.getRichText());
			couponInfoModel.setUrl(cid.getUrl());
			couponInfoModel.setUrlButton(cid.getUrlButton());
			couponInfoModel.setUserSourceType(cid.getUserSourceType());
			couponInfoModel.setIsShare(cid.getIsShare());
			couponInfoModel.setShareCode(CouponEncryptUtil.encrypt(tel+"-"+cid.getCpId()+"-0-"+System.currentTimeMillis())); 
			couponInfoModel.setCodeType(cid.getCodeType());
			
			//商铺信息
			List<CouponShopInfoModel> csims = Lists.newArrayList();
			List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(cid.getCpId()); 
			if (!CollectionUtils.isEmpty(csies)) {
				csies.forEach(csie ->{
					CouponShopInfoModel csim = new CouponShopInfoModel();
					csim.setInfoId(csie.getInfoId());
					csim.setPlatCode(csie.getPlatCode());
					csim.setShopName(csie.getShopName());
					
					csims.add(csim);
				});
				couponInfoModel.setCouponShopInfoModels(csims);
			}
			
			//查询优惠券使用范围表  
			if (CouponInfoType.goods.ordinal() == couponInfoModel.getType()) {  //商品优惠券
				//关联的商品InfoScopeModel
				List<InfoScopeEntity> ises = infoScopeMapper.queryAll(cid.getCpId());
				InfoScopeModel ism = new InfoScopeModel();
				List<String> params = Lists.newArrayList();
				
				if (!CollectionUtils.isEmpty(ises)) {
				
					ises.forEach(ise ->{
						params.add(ise.getParam());
						ism.setType(ise.getScopeType());
					});
				}
					ism.setNos(params);
					couponInfoModel.setInfoScope(ism);	
			}
					
			//优惠券使用条件
			InfoConditionModel infoConditionModel = new InfoConditionModel();
			infoConditionModel.setType(cid.getConditionType());
			infoConditionModel.setPar((cid.getPar()));
			infoConditionModel.setStartFee(cid.getStartFee());
			infoConditionModel.setEndFee(cid.getEndFee());
			couponInfoModel.setInfoCondition(infoConditionModel);
			
			//优惠券渠道
			List<InfoChannelEntity> ices = couponInfoMapper.queryChannels(cid.getCpId());
				
			if (!CollectionUtils.isEmpty(ices)) {
					
				List<ChannelModel> cm = Lists.newArrayList();
				
				ices.forEach(ice ->{
					
					ChannelModel channelModel = new ChannelModel();
					channelModel.setChannelNo(ice.getChannelNo());
					channelModel.setChannelName(ice.getChannelName());
					
					List<ChannelDetailModel> cdm = Lists.newArrayList();
					//优惠券渠道详情
					List<InfoChannelDetailEntity> icdes = couponInfoMapper.queryChannelDetails(ice.getId());
					if (!CollectionUtils.isEmpty(icdes)) {
						icdes.forEach(icde ->{	
							ChannelDetailModel channelDetailModel = new ChannelDetailModel();
							channelDetailModel.setInfoChannelId(icde.getInfoChannelId());
							channelDetailModel.setChannelDetailName(icde.getChannelDetailName());
							channelDetailModel.setChannelDetailNo(icde.getChannelDetailNo());
							cdm.add(channelDetailModel);
						});
					}

					channelModel.setChannelDetails(cdm);
					cm.add(channelModel);
				});
				couponInfoModel.setChannels(cm);
			}
			
			//查询出优惠券发放记录  操作人日志
			List<InfoSendRecordModel> isrms = Lists.newArrayList();		
		
			List<SendRecordDeatilEntity> srdes = sendRecordMapper.sendRecordDetail(cid.getCpId());	
			srdes.forEach(srde ->{
				InfoSendRecordModel infoSendRecordModel = new InfoSendRecordModel();
				infoSendRecordModel.setCpId(cid.getCpId());
				infoSendRecordModel.setCount(srde.getCount());
				infoSendRecordModel.setSendTime(srde.getCreateTime());
				infoSendRecordModel.setSendType(srde.getSendType());
				infoSendRecordModel.setUserScopeType(srde.getUserSouceType());
				infoSendRecordModel.setOperatorName(srde.getOperatorName());
				infoSendRecordModel.setOperatorTel(srde.getOperatorTel());
				
				isrms.add(infoSendRecordModel);
			});
			
				couponInfoModel.setInfoSendRecordModels(isrms);
		}

		return couponInfoModel;
	}

	@Override
	@Transactional
	public ConvertSuccessModel convertCoupon(String code, String tel) throws CouponException {

		ConvertSuccessModel csm = new ConvertSuccessModel();
		
		//1、校验券码是否存在，兑换码状态
		ConvertCodeDetailEntity ccce= couponConvertCodeMapper.query(code);	
		if (null == ccce) {
			logger.error("兑换码错误");
			throw new CouponException("S44");
		}
	
		if (ccce.getStatus() != ConvertCodeStatus.init.ordinal() ) {
			logger.error("兑换码已经被兑换");
			throw new CouponException("S45");
		}
		
		//2、领取券或者券包
		if (ccce.getType() == ConvertTypeEnum.coupon.ordinal()) {//领取券
			
			CouponInfoEntity cie = couponInfoMapper.query(ccce.getInfoId());
			if (null == cie) {
				logger.error("优惠券不存在，领取失败");
				throw new CouponException("S14");
			}
			
			if (cie.getStatus() != CouponInfoStatus.send.ordinal()) {
				logger.error("优惠券不是发布状态，领取失败");
				throw new CouponException("S16");
			}
			
			if (new Date().getTime() < cie.getBeginTime().getTime()) {
				logger.error("非优惠券领取时间");
				throw new CouponException("S53");
			}
			
			if (new Date().getTime() >= cie.getEndTime().getTime()) {
				logger.error("超过优惠券领取截止时间");
				throw new CouponException("S46");
			}
			
			if (new Date().getTime() >= cie.getDueTime().getTime()) {
				
				logger.error("超过优惠券使用时间");
				throw new CouponException("S17");
			}
			
			
			//已领取优惠券数量，校验领取限制
			int count = userBagMapper.countSingel(ccce.getInfoId(),tel);
			if (!(count == 0 || count < cie.getLimitNum())) {
				logger.error("当前用户 tel={}",tel+":领取超出限制");
				throw new CouponException("S37");
			}
			
			//校验库存
			int stock = cie.getStock();
			if (stock <= 0) {
				logger.error("当前库存={}",stock+":优惠券库存不足");
				throw new CouponException("S15");
			}
			
			//领取成功，扣除库存
			couponInfoMapper.editStock(ccce.getInfoId());
					
			//插入用户卡券包  
			UserBagEntity ube = new UserBagEntity();
			ube.setInfoId(ccce.getInfoId());
			ube.setUserTel(tel); 
			ube.setStatus(UserBagStatus.get.ordinal());
			userBagMapper.add(ube);
					
			//判断是否是券码券
			if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，是否发送短信提醒		
				//1、绑定的券码是否还有剩余，选一个券码
				InfoCodeEntity infoCodeEntity = infoCodeMapper.queryCodeId(cie.getId());
				if (infoCodeEntity == null) {
					logger.error("code码当前库存={}",":库存不足");
					throw new CouponException("S32");
				}
				
				//2、code与用户券包绑定
				infoCodeMapper.bindBagId(ube.getId(), infoCodeEntity.getId());
				
				//3、找到这个券码对应的券码批次，判断该批次是否发短信
				InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(infoCodeEntity.getCodePlanId());
				if (StringUtils.equals(infoCodePlanEntity.getIsMsg(),Integer.toString(CodeMsgStatus.yes.ordinal()))) {
					//4、发短信
					this.codeSend(cie, tel ,infoCodeEntity,infoCodePlanEntity);
				}
			}
			
			//插入券包日志表
			UserBagLogEntity uble = new UserBagLogEntity();
			uble.setUserBagId(ube.getId());
			uble.setType(UserBagLogType.get.ordinal());
			userBagLogMapper.add(uble);

			//更新兑换码状态，绑定用户的券包
			couponConvertCodeMapper.updateCode(code);
			
			//记录兑换码用户表
			CouponConvertUserEntity ccue = new CouponConvertUserEntity();
			ccue.setCodeId(ccce.getId());
			ccue.setUserBagId(ube.getId());
			couponConvertUserMapper.save(ccue);
			
			csm.setInfoId(ccce.getInfoId());
			csm.setType(ConvertTypeEnum.coupon.ordinal());
			
		} else {//领取券包，校验领取券包的逻辑
			
			//领取券包要求券包状态是已绑定
			CouponPackageEntity couponPackageEntity = couponPackageMapper.query(ccce.getInfoId());
			if (couponPackageEntity.getStatus() != CouponPackageStatus.bind.ordinal()) {
				logger.error("券包状态不是已绑定 PackageId={}",ccce.getInfoId());
				throw new CouponException("S41");
			}
			
			//1、判断券包是否绑定优惠券
			List<CouponInfoDetailEntity> cides = couponPackageInfoMapper.queryInfoDetail(ccce.getInfoId());
			if (CollectionUtils.isEmpty(cides)) {
				logger.error("券包未绑定优惠券 PackageId={}",ccce.getInfoId());
				throw new CouponException("S36");
			}
			
			//2、判断券包领取时间
			CouponPackageEntity cpe = couponPackageMapper.query(ccce.getInfoId());
			if (new Date().getTime() >= cpe.getEndTime().getTime()) {
				logger.error("超过券包领取截止时间,券包id={}",ccce.getInfoId());
				throw new CouponException("S38");
			}

			//3、校验优惠券是否能领取，插入用户券包
			for (CouponInfoDetailEntity cide:cides) {
				int count = userBagMapper.countSingel(cide.getCpId(),tel);//已领取优惠券数量
				CouponInfoEntity cie = couponInfoMapper.query(cide.getCpId());//查询券详情
				if (null == cie) {
					logger.error("当前用户 tel={}",tel+":优惠券不存在，领取失败");
					throw new CouponException("S14");
				}
				
				if (cie.getStatus() != CouponInfoStatus.send.ordinal()) {
					logger.error("当前券cpId={}",cide.getCpId()+":优惠券不是发布状态，领取失败");
					throw new CouponException("S16");
				}
				
				if (new Date().getTime() < cie.getBeginTime().getTime()) {
					logger.error("非优惠券领取时间");
					throw new CouponException("S53");
				}
				
				if (new Date().getTime() >= cie.getEndTime().getTime()) {
					logger.error("超过优惠券领取截止时间");
					throw new CouponException("S46");
				}
				
				if (new Date().getTime() >= cie.getDueTime().getTime()) {
					
					logger.error("超过优惠券使用时间");
					throw new CouponException("S17");
				}
				
				if (!(count == 0 || count < cie.getLimitNum())) {
					//领取超出限制
					logger.error("当前用户 tel={}",tel+":领取超出限制");
					throw new CouponException("S37");
				}
				
				int stock = cie.getStock();//库存
				if (stock <= 0) {
					logger.error("当前库存={}",stock+":优惠券库存不足");
					throw new CouponException("S15");
				}
				
				//领取成功，扣除库存
				couponInfoMapper.editStock(cide.getCpId());
						
				//插入用户卡券包  
				UserBagEntity ube = new UserBagEntity();
				ube.setInfoId(cide.getCpId());
				ube.setUserTel(tel); 
				ube.setStatus(UserBagStatus.get.ordinal());
				userBagMapper.add(ube);
				
				//判断是否是券码券
				if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，是否发送短信提醒		
					//1、绑定的券码是否还有剩余，选一个券码
					InfoCodeEntity infoCodeEntity = infoCodeMapper.queryCodeId(cie.getId());
					if (infoCodeEntity == null) {
						logger.error("code码当前库存={}",":库存不足");
						throw new CouponException("S32");
					}
					
					//2、code与用户券包绑定
					infoCodeMapper.bindBagId(ube.getId(), infoCodeEntity.getId());
					
					//3、找到这个券码对应的券码批次，判断该批次是否发短信
					InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(infoCodeEntity.getCodePlanId());
					if (StringUtils.equals(infoCodePlanEntity.getIsMsg(),Integer.toString(CodeMsgStatus.yes.ordinal()))) {
						//4、发短信
						this.codeSend(cie, tel, infoCodeEntity,infoCodePlanEntity);
					}
				}
				
				//插入券包日志表
				UserBagLogEntity uble = new UserBagLogEntity();
				uble.setUserBagId(ube.getId());
				uble.setType(UserBagLogType.get.ordinal());
				userBagLogMapper.add(uble);
				
				//更新兑换码状态，绑定用户的券包
				couponConvertCodeMapper.updateCode(code);
				
				//记录兑换码用户
				CouponConvertUserEntity ccue = new CouponConvertUserEntity();
				ccue.setCodeId(ccce.getId());
				ccue.setUserBagId(ube.getId());
				couponConvertUserMapper.save(ccue);
				
				csm.setInfoId(ccce.getInfoId());
				csm.setType(ConvertTypeEnum.couponPackage.ordinal());
			}
						
		}

		return csm;
	}

	
	@Override
	@Transactional
	public void verifyCoupon(CouponVerifyRequest couponVerifyRequest) throws CouponException {
		
		List<String> userbagIds = couponVerifyRequest.getUserBagIds();
		
		userbagIds.forEach(userBagId->{
			
			
			//更新用户券包状态
			userBagMapper.update(userBagId, UserBagStatus.cost.ordinal());
			//更新用户订单券
			UserBagOrderEntity uboe = new UserBagOrderEntity();
			uboe.setUserBagId(userBagId);
			uboe.setOrderNo(couponVerifyRequest.getOrderNo());
			userBagOrderMapper.add(uboe);
			//更新券包日志
			//添加券包日志状态，为已付款。
			UserBagLogEntity userBagLogEntity = new UserBagLogEntity();
			userBagLogEntity.setType(UserBagLogType.cost.ordinal());				
			userBagLogEntity.setUserBagId(userBagId);
			userBagLogMapper.add(userBagLogEntity);
			
		});
		
	}

}

