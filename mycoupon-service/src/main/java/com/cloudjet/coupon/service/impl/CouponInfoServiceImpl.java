package com.cloudjet.coupon.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloudjet.coupon.entity.CouponConvertPlanEntity;
import com.cloudjet.coupon.entity.CouponConvertUserEntity;
import com.cloudjet.coupon.entity.CouponInfoEntity;
import com.cloudjet.coupon.entity.CouponPackageEntity;
import com.cloudjet.coupon.entity.CouponPackageInfoEntity;
import com.cloudjet.coupon.entity.CouponShopInfoEntity;
import com.cloudjet.coupon.entity.InfoChannelDetailEntity;
import com.cloudjet.coupon.entity.InfoChannelEntity;
import com.cloudjet.coupon.entity.InfoCodeEntity;
import com.cloudjet.coupon.entity.InfoCodeMsgTagEntity;
import com.cloudjet.coupon.entity.InfoCodePlanEntity;
import com.cloudjet.coupon.entity.InfoCodeTestEntity;
import com.cloudjet.coupon.entity.InfoConditionEntity;
import com.cloudjet.coupon.entity.InfoOperatorEntity;
import com.cloudjet.coupon.entity.InfoScopeEntity;
import com.cloudjet.coupon.entity.InfoUserSourceDetailEntity;
import com.cloudjet.coupon.entity.InfoUserSourceEntity;
import com.cloudjet.coupon.entity.InfoUserSourceLevelEntity;
import com.cloudjet.coupon.entity.LogoEntity;
import com.cloudjet.coupon.entity.SendRecordEntity;
import com.cloudjet.coupon.entity.UserBagEntity;
import com.cloudjet.coupon.entity.UserBagLogEntity;
import com.cloudjet.coupon.entity.dto.CodeListEntity;
import com.cloudjet.coupon.entity.dto.CodeMsgDetailEntity;
import com.cloudjet.coupon.entity.dto.CouponConvertListEntity;
import com.cloudjet.coupon.entity.dto.CouponInfoCheckedListEntity;
import com.cloudjet.coupon.entity.dto.CouponInfoDetailEntity;
import com.cloudjet.coupon.entity.dto.InfoCodeMsgEntity;
import com.cloudjet.coupon.entity.dto.SendRecordDeatilEntity;
import com.cloudjet.coupon.entity.dto.UserBagResEntity;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.mapper.CouponConvertCodeMapper;
import com.cloudjet.coupon.mapper.CouponConvertPlanMapper;
import com.cloudjet.coupon.mapper.CouponConvertUserMapper;
import com.cloudjet.coupon.mapper.CouponInfoMapper;
import com.cloudjet.coupon.mapper.CouponPackageInfoMapper;
import com.cloudjet.coupon.mapper.CouponPackageMapper;
import com.cloudjet.coupon.mapper.CouponShopInfoMapper;
import com.cloudjet.coupon.mapper.InfoChannelDetailMapper;
import com.cloudjet.coupon.mapper.InfoChannelMapper;
import com.cloudjet.coupon.mapper.InfoCodeMapper;
import com.cloudjet.coupon.mapper.InfoCodeMsgTagMapper;
import com.cloudjet.coupon.mapper.InfoCodePlanMapper;
import com.cloudjet.coupon.mapper.InfoCodeTestMapper;
import com.cloudjet.coupon.mapper.InfoConditionMapper;
import com.cloudjet.coupon.mapper.InfoOperatorMapper;
import com.cloudjet.coupon.mapper.InfoScopeMapper;
import com.cloudjet.coupon.mapper.InfoUserSourceDetailMapper;
import com.cloudjet.coupon.mapper.InfoUserSourceLevelMapper;
import com.cloudjet.coupon.mapper.InfoUserSourceMapper;
import com.cloudjet.coupon.mapper.JphUserMapper;
import com.cloudjet.coupon.mapper.LogoMapper;
import com.cloudjet.coupon.mapper.SendRecordMapper;
import com.cloudjet.coupon.mapper.UserBagLogMapper;
import com.cloudjet.coupon.mapper.UserBagMapper;
import com.cloudjet.coupon.model.BindCpModel;
import com.cloudjet.coupon.model.ChannelDetailModel;
import com.cloudjet.coupon.model.ChannelModel;
import com.cloudjet.coupon.model.CodeListParamsModel;
import com.cloudjet.coupon.model.ConvertCodeModel;
import com.cloudjet.coupon.model.ConvertCodeModel.ConvertTypeEnum;
import com.cloudjet.coupon.model.CouponCopyParamsModel;
import com.cloudjet.coupon.model.CouponInfoModel;
import com.cloudjet.coupon.model.CouponInfoModel.CouponInfoStatus;
import com.cloudjet.coupon.model.CouponInfoModel.CouponInfoType;
import com.cloudjet.coupon.model.CouponInfoModel.CouponIsBindCode;
import com.cloudjet.coupon.model.CouponInfoModel.CouponIsCode;
import com.cloudjet.coupon.model.CouponPackageModel;
import com.cloudjet.coupon.model.CouponPackageModel.CouponPackageStatus;
import com.cloudjet.coupon.model.CouponPageModel;
import com.cloudjet.coupon.model.CouponShopInfoModel;
import com.cloudjet.coupon.model.InfoCodeModel;
import com.cloudjet.coupon.model.InfoCodeModel.CodeMsgStatus;
import com.cloudjet.coupon.model.InfoCodeModel.CodePlanStatus;
import com.cloudjet.coupon.model.InfoConditionModel;
import com.cloudjet.coupon.model.InfoOperatorModel;
import com.cloudjet.coupon.model.InfoOperatorModel.InfoOperatorType;
import com.cloudjet.coupon.model.InfoScopeModel;
import com.cloudjet.coupon.model.InfoSendRecordModel;
import com.cloudjet.coupon.model.InfoSendRecordModel.SendType;
import com.cloudjet.coupon.model.LogoModel;
import com.cloudjet.coupon.model.LogoModel.LogoTypeEnum;
import com.cloudjet.coupon.model.PackageEditModel;
import com.cloudjet.coupon.model.PackageInitModel;
import com.cloudjet.coupon.model.PackageListParamsModel;
import com.cloudjet.coupon.model.PageResult;
import com.cloudjet.coupon.model.UserBagLogModel.UserBagLogType;
import com.cloudjet.coupon.model.UserBagModel.UserBagStatus;
import com.cloudjet.coupon.model.UserSourceModel;
import com.cloudjet.coupon.model.UserSourceModel.UserSourceType;
import com.cloudjet.coupon.model.UserSourceModel.UserSourceWay;
import com.cloudjet.coupon.request.ConvertCodeListParamsModel;
import com.cloudjet.coupon.request.SelectCouponParamsModel;
import com.cloudjet.coupon.request.SendCouponPullModel;
import com.cloudjet.coupon.request.SendMsgParamsModel;
import com.cloudjet.coupon.request.SetMsgParamsModel;
import com.cloudjet.coupon.request.UserBagParamsModel;
import com.cloudjet.coupon.res.model.CodeListResModel;
import com.cloudjet.coupon.res.model.CodeMsgModel;
import com.cloudjet.coupon.res.model.ConvertCodeListResModel;
import com.cloudjet.coupon.res.model.MsgResponse;
import com.cloudjet.coupon.res.model.UserBagResModel;
import com.cloudjet.coupon.res.model.UserBagResModel.CodeMsgStatusEnum;
import com.cloudjet.coupon.service.CouponInfoService;
import com.cloudjet.coupon.util.DateUtil;
import com.cloudjet.coupon.util.HttpHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


@Service
public class CouponInfoServiceImpl implements CouponInfoService{
	
	private static final Logger logger = LoggerFactory.getLogger(CouponInfoServiceImpl.class);
	
	@Autowired
	private CouponConvertUserMapper couponConvertUserMapper;
	
	@Autowired
	private CouponInfoMapper couponInfoMapper;
	
	@Autowired
	private InfoConditionMapper infoConditionMapper;
	
	@Autowired
	private InfoScopeMapper infoScopeMapper;
		
	@Autowired
	private InfoChannelMapper infoChannelMapper;
	
	@Autowired
	private InfoChannelDetailMapper infoChannelDetailMapper;

	@Autowired
	private InfoOperatorMapper infoOperatorMapper;
	
	@Autowired
	private InfoUserSourceMapper infoUserSourceMapper;
	
	@Autowired
	private InfoUserSourceDetailMapper infoUserSourceDetailMapper;
	
	@Autowired
	private InfoUserSourceLevelMapper infoUserSourceLevelMapper;
	
	@Autowired
	private UserBagMapper userBagMapper;
	
	@Autowired
	private UserBagLogMapper userBagLogMapper;
	
	@Autowired
	private JphUserMapper jphUserMapper;
	
	@Autowired
	private SendRecordMapper sendRecordMapper;
	
	@Autowired
	private InfoCodeMapper infoCodeMapper;
	
	@Autowired
	private InfoCodePlanMapper infoCodePlanMapper;
	
	@Autowired
	private CouponShopInfoMapper couponShopInfoMapper;
	
	@Autowired
	private CouponPackageMapper couponPackageMapper;

	@Autowired
	private CouponConvertCodeMapper couponConvertCodeMapper;

	@Autowired
	private CouponConvertPlanMapper couponConvertPlanMapper;
	
	@Autowired
	private CouponPackageInfoMapper couponPackageInfoMapper;
	
	@Autowired
	private LogoMapper logoMapper;
	
	@Autowired
	private InfoCodeTestMapper infoCodeTestMapper;
	
	@Autowired
	private InfoCodeMsgTagMapper infoCodeMsgTagMapper;
	
	private static final String msgURL = "http://promotion.izhuan100.com/wechat/sms/send.do";
	
	@Override
	@Transactional
	public String create(CouponInfoModel couponInfo) throws CouponException{
	
			//插入优惠券主表
			//创建时两个字段不维护,值为null。is_bind_code(绑定时候维护)、user_source_type(发布时候维护)
			CouponInfoEntity cie = new CouponInfoEntity();
			cie.setCpName(couponInfo.getCpName());
			cie.setPreWay(couponInfo.getPreWay());
			cie.setCheckNo(couponInfo.getCheckNo());
			cie.setStartTime(couponInfo.getStartTime());
			cie.setEndTime(couponInfo.getEndTime());
			cie.setCirculation(couponInfo.getCirculation());
			cie.setStock(couponInfo.getCirculation());
			cie.setLimitNum(couponInfo.getLimitNum());
			cie.setBeginTime(couponInfo.getBeginTime());
			cie.setDueTime(couponInfo.getDueTime());
			cie.setType(couponInfo.getType());
			cie.setCostType(couponInfo.getCostType());
			cie.setStatus(CouponInfoStatus.init.ordinal());//初始化
			cie.setIsCode(couponInfo.getIsCode());//默认不是券码券
			cie.setIsShare(couponInfo.getIsShare());
			cie.setMemo(couponInfo.getMemo());
			cie.setRule(couponInfo.getRule());
			cie.setRichText(couponInfo.getRichText());
			cie.setUrl(couponInfo.getUrl());
			cie.setUrlButton(couponInfo.getUrlButton());
			cie.setMeanwhile(couponInfo.getMeanwhile());
			cie.setMeantime(couponInfo.getMeantime());
			cie.setChannelText(couponInfo.getChannelText());
			
			couponInfoMapper.add(cie);
			
			//插入券logo
			List<LogoModel> logos = couponInfo.getLogos();
			if (!CollectionUtils.isEmpty(logos)) {
				logos.forEach(logo ->{
					LogoEntity logoEntity = new LogoEntity();
					logoEntity.setInfoId(cie.getId());
					logoEntity.setLogo(logo.getLogo());
					logoEntity.setType(LogoTypeEnum.coupon.ordinal());
					
					logoMapper.save(logoEntity);
				});
			}

			//插入商铺信息表，检验是否重复
			List<CouponShopInfoModel> csims = couponInfo.getCouponShopInfoModels();
			Set<String> copyShop = Sets.newHashSet();
			csims.forEach(csim ->{
				copyShop.add(csim.getPlatCode());
			});
			if (copyShop.size() != csims.size()) {
	 			logger.error("绑定的商铺重复={}");
	 			throw new CouponException("S48");
			}
			
			csims.forEach(csim ->{
				CouponShopInfoEntity couponShopInfoEntity = new CouponShopInfoEntity();
				couponShopInfoEntity.setInfoId(cie.getId());
				couponShopInfoEntity.setPlatCode(csim.getPlatCode());
				couponShopInfoEntity.setShopName(csim.getShopName());
				
				couponShopInfoMapper.add(couponShopInfoEntity);
			});
			
			//插入优惠券条件表
			InfoConditionEntity ice = new InfoConditionEntity();
			ice.setInfoId(cie.getId());
			ice.setType(couponInfo.getInfoCondition().getType());
			ice.setPar(couponInfo.getInfoCondition().getPar());
			ice.setStartFee(couponInfo.getInfoCondition().getStartFee());
			ice.setEndFee(couponInfo.getInfoCondition().getEndFee());
			infoConditionMapper.add(ice);
			
			//插入优惠券使用范围表  商品优惠券采用插入
			if (CouponInfoType.goods.ordinal() == couponInfo.getType()) {  //商品优惠券
				//插入条件的参数
				if (!CollectionUtils.isEmpty(couponInfo.getInfoScope().getNos())) {
					couponInfo.getInfoScope().getNos().forEach(no ->{
						InfoScopeEntity ise = new InfoScopeEntity();
						ise.setInfoId(cie.getId());
						ise.setScopeType(couponInfo.getInfoScope().getType());
						ise.setParam(no);
						infoScopeMapper.add(ise);
						
					});
				}
			} else if (CouponInfoType.category.ordinal() == couponInfo.getType()) { //品类优惠券
				//插入条件的参数
				if (!CollectionUtils.isEmpty(couponInfo.getInfoScope().getNos())) {
					couponInfo.getInfoScope().getNos().forEach(no ->{
						InfoScopeEntity ise = new InfoScopeEntity();
						ise.setInfoId(cie.getId());
						ise.setScopeType(couponInfo.getInfoScope().getType());
						ise.setParam(no);
						infoScopeMapper.add(ise);
						
					});
				}
			}
			
			
			//插入优惠券渠道
			List<ChannelModel> channelModels = couponInfo.getChannels();
			
			if (!CollectionUtils.isEmpty(channelModels)) {
				
				channelModels.forEach(channelModel ->{
					
					InfoChannelEntity infoChannelEntity = new InfoChannelEntity();
					infoChannelEntity.setInfoId(cie.getId());
					infoChannelEntity.setChannelNo(channelModel.getChannelNo());
					infoChannelEntity.setChannelName(channelModel.getChannelName());
					
					infoChannelMapper.add(infoChannelEntity);
					List<ChannelDetailModel> channelDetailModels =  channelModel.getChannelDetails();
					
					channelDetailModels.forEach(channelDetailModel ->{
						
						InfoChannelDetailEntity infoChannelDetailEntity = new InfoChannelDetailEntity();
						infoChannelDetailEntity.setInfoChannelId(infoChannelEntity.getId());
						infoChannelDetailEntity.setChannelDetailNo(channelDetailModel.getChannelDetailNo());
						infoChannelDetailEntity.setChannelDetailName(channelDetailModel.getChannelDetailName());
						
						infoChannelDetailMapper.add(infoChannelDetailEntity);
					});
					
				});
				
			}
			
			//优惠券操作人表
			InfoOperatorModel iom = couponInfo.getOperator();
			InfoOperatorEntity infoOperatorEntity = new InfoOperatorEntity();
			infoOperatorEntity.setInfoId(cie.getId());
			infoOperatorEntity.setOperatorId(iom.getUserId());
			infoOperatorEntity.setOperatorName(iom.getName());
			infoOperatorEntity.setOperatorTel(iom.getTel());
			infoOperatorEntity.setType(InfoOperatorType.init.ordinal());
			
			infoOperatorMapper.add(infoOperatorEntity);
			
			return cie.getId();
	}
	
	@Override
	@Transactional
	public void delete(String cpId, InfoOperatorModel infoOperator) throws CouponException {
		
		CouponInfoEntity cife = couponInfoMapper.query(cpId);
		
		if (cife != null) {
			
			if (cife.getStatus() == CouponInfoStatus.init.ordinal()) {
				//删除优惠劵
				couponInfoMapper.delete(cpId);
				
				//添加一条记录到cp_info_operator
				InfoOperatorEntity infoOpeatorEntity = new InfoOperatorEntity();
				infoOpeatorEntity.setInfoId(cpId);
				infoOpeatorEntity.setOperatorId((infoOperator.getUserId()));
				infoOpeatorEntity.setOperatorName(infoOperator.getName());
				infoOpeatorEntity.setOperatorTel(infoOperator.getTel());
				infoOpeatorEntity.setType(InfoOperatorType.delete.ordinal());
				infoOperatorMapper.add(infoOpeatorEntity);
				
				//级联删除和这张优惠劵有关的数据
				infoConditionMapper.delete(cpId);
				infoScopeMapper.delete(cpId);
				infoChannelDetailMapper.delete(cpId);
				infoChannelMapper.delete(cpId);
				couponShopInfoMapper.delete(cpId);
				logoMapper.delete(cpId);
				
			} else {
	 			logger.error("优惠券已经发布，不能删除={}",cpId);
	 			throw new CouponException("S02");
			}
			
		} else { 
 			logger.error("优惠券不存在，删除异常={}",cpId);
 			throw new CouponException("S05");
		}
		
	}


	@Override
	@Transactional
	public void send(UserSourceModel userSource) throws CouponException {
		
		CouponInfoEntity cie = couponInfoMapper.query(userSource.getCpId());
		
		if (null == cie) {
 			logger.error("优惠券不存在，发布异常={}");
 			throw new CouponException("S09");
		}
		
		if (cie.getStatus() != CouponInfoStatus.init.ordinal()) {			
 			logger.error("优惠券发布失败={}");
 			throw new CouponException("S04");
		}
		
		if (cie.getIsCode() == CouponIsCode.yes.ordinal()) {//是否是券码类型的券
			
			//是否绑定优惠券
			if (cie.getIsBindCode() == null) {
 				logger.error("优惠券没和券码绑定，发布失败={}");
 	 			throw new CouponException("S31");	
 			}
 			
			//是否发送测试短信
			InfoCodeMsgEntity infoCodeMsgEntity = infoCodeMapper.queryCodeMsg(cie.getId());
			int num = infoCodeTestMapper.queryCount(infoCodeMsgEntity.getCodePlanId());
			
			if ((Integer.parseInt(infoCodeMsgEntity.getIsMsg()) == CodeMsgStatus.yes.ordinal()) && num < 3) {
 				logger.error("没发送3条测试短信，发布失败={}");
 	 			throw new CouponException("S51");	
			}
		}	
		InfoOperatorModel infoOperator = userSource.getOperator();
		
		//更新优惠券为已使用，即发放状态
		couponInfoMapper.updateStatus(userSource.getCpId(), CouponInfoStatus.send.ordinal());
	
		//更新操作人表
		InfoOperatorEntity infoOperatorEntity = new InfoOperatorEntity();
		infoOperatorEntity.setInfoId(userSource.getCpId());
		infoOperatorEntity.setOperatorId(infoOperator.getUserId());
		infoOperatorEntity.setOperatorName(infoOperator.getName());
		infoOperatorEntity.setOperatorTel(infoOperator.getTel());
		infoOperatorEntity.setType(InfoOperatorType.send.ordinal());
		infoOperatorMapper.add(infoOperatorEntity);
	
		userSource.getOperator().setId(infoOperatorEntity.getId());
	}
	
	@Override
	@Transactional
	public void freeze(String cpId, InfoOperatorModel infoOperator) throws CouponException {

		CouponInfoEntity cife = couponInfoMapper.query(cpId);
		if (cife !=null) {
			if (cife.getStatus() == CouponInfoStatus.send.ordinal()) {
				//冻结优惠劵，更新状态
				couponInfoMapper.updateStatus(cpId, CouponInfoStatus.freeze.ordinal());		
				
				//更新操作人表
				InfoOperatorEntity infoOperatorEntity = new InfoOperatorEntity();
				infoOperatorEntity.setInfoId(cpId);
				infoOperatorEntity.setOperatorId(infoOperator.getUserId());
				infoOperatorEntity.setOperatorName(infoOperator.getName());
				infoOperatorEntity.setOperatorTel(infoOperator.getTel());
				infoOperatorEntity.setType(InfoOperatorType.freeze.ordinal());
				infoOperatorMapper.add(infoOperatorEntity);
			} else {
	 			logger.error("优惠券未发布，冻结异常={}");
	 			throw new CouponException("S06");
			}
		} else {
 			logger.error("优惠券不存在，冻结异常={}",cpId);
 			throw new CouponException("S07");
		}
		
	}

	@Override
	public PageResult<CouponInfoModel> queryList(CouponPageModel couponPageModel) throws CouponException{
		PageResult<CouponInfoModel> page = new PageResult<CouponInfoModel>();
		List<CouponInfoModel> list = Lists.newArrayList();
		
		Map<String, Object> params = Maps.newHashMap();
		params.put("start", (couponPageModel.getPageNo()-1)*couponPageModel.getPageSize());
		params.put("limit", couponPageModel.getPageSize());
		params.put("cpName", couponPageModel.getCpName());
		params.put("createTime_left", couponPageModel.getCreateTime_left());
		params.put("createTime_right", couponPageModel.getCreateTime_right());
		params.put("beginTime", couponPageModel.getBeginTime());
		params.put("dueTime", couponPageModel.getDueTime());
		params.put("type", couponPageModel.getType());
		params.put("par", couponPageModel.getPar());
		params.put("status", couponPageModel.getStatus());
		params.put("platCode", couponPageModel.getPlatCode());
		params.put("costType", couponPageModel.getCostType());
		
		//查询卡券
		List<CouponInfoDetailEntity> cies = couponInfoMapper.queryList(params);
		if (!CollectionUtils.isEmpty(cies)) {
			
			cies.forEach(cie -> {
				
				CouponInfoModel cim = new CouponInfoModel();
				
				cim.setCpId(cie.getCpId());
				cim.setCpName(cie.getCpName());
				cim.setPreWay(cie.getPreWay());
				cim.setCheckNo(cie.getCheckNo());
				cim.setStartTime(cie.getStartTime());
				cim.setEndTime(cie.getEndTime());
				cim.setCirculation(cie.getCirculation());
				cim.setLimitNum(cie.getLimitNum());
				cim.setStock(cie.getStock());
				cim.setType(cie.getType());
				cim.setBeginTime(cie.getBeginTime());
				cim.setDueTime(cie.getDueTime());
				cim.setStatus(cie.getStatus());
				cim.setIsCode(cie.getIsCode());
				cim.setIsBindCode(cie.getIsBindCode());
				cim.setCostType(cie.getCostType());
				cim.setRule(cie.getRule());
				cim.setMemo(cie.getMemo());
				cim.setCreateTime(cie.getCreateTime());
				cim.setUpdateTime(cie.getUpdateTime());
				cim.setRichText(cie.getRichText());
				cim.setUrl(cie.getUrl());
				cim.setUrlButton(cie.getUrlButton());
				cim.setUserSourceType(cie.getUserSourceType());
				cim.setIsShare(cie.getIsShare());
				cim.setMeanwhile(cie.getMeanwhile());
				cim.setMeantime(cie.getMeantime());
				cim.setChannelText(cie.getChannelText());
				
				//查询卡券使用条件、查询卡券面额
				InfoConditionModel infoConditionModel = new InfoConditionModel();
				infoConditionModel.setType(cie.getConditionType());
				infoConditionModel.setPar((cie.getPar()));
				infoConditionModel.setStartFee(cie.getStartFee());
				infoConditionModel.setEndFee(cie.getEndFee());
				cim.setInfoCondition(infoConditionModel);
			
				//查询logo
				List<LogoModel> logos = Lists.newArrayList();
				List<LogoEntity> logoEntitys = logoMapper.select(cie.getCpId()); 
				if (!CollectionUtils.isEmpty(logoEntitys)) {
					logoEntitys.forEach(logoEntity ->{
						LogoModel logo = new LogoModel();
						logo.setId(logoEntity.getId());
						logo.setInfoId(logoEntity.getInfoId());
						logo.setLogo(logoEntity.getLogo());
						logo.setType(logoEntity.getType());
						
						logos.add(logo);
					});
					cim.setLogos(logos);
				}
				
				//插入商铺信息表
				List<CouponShopInfoModel> csims = Lists.newArrayList();
				List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(cie.getCpId()); 
				if (!CollectionUtils.isEmpty(csies)) {
					csies.forEach(csie ->{
						CouponShopInfoModel csim = new CouponShopInfoModel();
						csim.setInfoId(csie.getInfoId());
						csim.setPlatCode(csie.getPlatCode());
						csim.setShopName(csie.getShopName());
						
						csims.add(csim);
					});
					cim.setCouponShopInfoModels(csims);
				}
				
				//查询卡券发放数目、使用数目
				List<UserBagEntity> ubes = userBagMapper.selectByCouponId(cie.getCpId());
				if (!CollectionUtils.isEmpty(ubes)) {
					cim.setGrantNum(ubes.size());
					int usedCount = 0;	//统计使用的卡券数量
					for (UserBagEntity ube:ubes) {
						if (ube.getStatus() == UserBagStatus.cost.ordinal() || ube.getStatus() == UserBagStatus.unpay.ordinal()) {
							usedCount++;
						}
					}
					cim.setUsedNum(usedCount);
				} else {
				 	cim.setGrantNum(0);
					cim.setUsedNum(0);
				}
					list.add(cim);
			});
		}

		page.setData(list);
		//计算分页
		int total = couponInfoMapper.selectCount(params);
		int totalPage = (total + couponPageModel.getPageSize() - 1) / couponPageModel.getPageSize();
		page.setTotalResults(total);
		page.setHasNext(couponPageModel.getPageNo()+1 <=  totalPage);
		return page;
	}

	@Override
	public CouponInfoModel queryDetail(String cpId) throws CouponException{
		
		CouponInfoModel couponInfoModel = new CouponInfoModel();
		//查看券详细信息
		CouponInfoDetailEntity cid= couponInfoMapper.queryDetail(cpId);
		
		if (null !=cid) {
			couponInfoModel.setCpId(cpId);
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
			couponInfoModel.setMeanwhile(cid.getMeanwhile());
			couponInfoModel.setMeantime(cid.getMeantime());
			couponInfoModel.setChannelText(cid.getChannelText());
			
			//查询logo
			List<LogoModel> logos = Lists.newArrayList();
			List<LogoEntity> logoEntitys = logoMapper.select(cid.getCpId()); 
			if (!CollectionUtils.isEmpty(logoEntitys)) {
				logoEntitys.forEach(logoEntity ->{
					LogoModel logo = new LogoModel();
					logo.setId(logoEntity.getId());
					logo.setInfoId(logoEntity.getInfoId());
					logo.setLogo(logoEntity.getLogo());
					logo.setType(logoEntity.getType());
					
					logos.add(logo);
				});
				couponInfoModel.setLogos(logos);
			}
			
			//商铺信息
			List<CouponShopInfoModel> csims = Lists.newArrayList();
			List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(cpId); 
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
			if (CouponInfoType.goods.ordinal() == couponInfoModel.getType() || 
					CouponInfoType.category.ordinal() == couponInfoModel.getType()) {  //商品优惠券
				//关联的商品InfoScopeModel
				List<InfoScopeEntity> ises = infoScopeMapper.queryAll(cpId);
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
			List<InfoChannelEntity> ices = couponInfoMapper.queryChannels(cpId);
				
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
		
			List<SendRecordDeatilEntity> srdes = sendRecordMapper.sendRecordDetail(cpId);	
			srdes.forEach(srde ->{
				InfoSendRecordModel infoSendRecordModel = new InfoSendRecordModel();
				infoSendRecordModel.setCpId(cpId);
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
	public List<CouponInfoModel> queryCheckedList(SelectCouponParamsModel selectCouponParamsModel) throws CouponException{
		
		List<CouponInfoModel> list = Lists.newArrayList();
		
		Map<String, Object> params = Maps.newHashMap();
		params.put("costType", selectCouponParamsModel.getCostType());
		params.put("isCode", selectCouponParamsModel.getIsCode());
		params.put("type", selectCouponParamsModel.getType());
		
		//查询未发布的优惠券
		List<CouponInfoCheckedListEntity> cies = couponInfoMapper.queryCheckedList(params);
		
		if (!CollectionUtils.isEmpty(cies)) {
			cies.forEach(cie -> {
				
				CouponInfoModel cim = new CouponInfoModel();
				
				cim.setCpId(cie.getId());
				cim.setCpName(cie.getCpName());
				cim.setPreWay(cie.getPreWay());
				cim.setCheckNo(cie.getCheckNo());
				cim.setStartTime(cie.getStartTime());
				cim.setEndTime(cie.getEndTime());
				cim.setCirculation(cie.getCirculation());
				cim.setLimitNum(cie.getLimitNum());
				cim.setStock(cie.getStock());
				cim.setType(cie.getType());
				cim.setBeginTime(cie.getBeginTime());
				cim.setDueTime(cie.getDueTime());
				cim.setStatus(cie.getStatus());
				cim.setIsCode(cie.getIsCode());
				cim.setIsBindCode(cie.getIsBindCode());
				cim.setCostType(cie.getCostType());
				cim.setRule(cie.getRule());
				cim.setMemo(cie.getMemo());
				cim.setCreateTime(cie.getCreateTime());
				cim.setUpdateTime(cie.getUpdateTime());
				cim.setRichText(cie.getRichText());
				cim.setUrl(cie.getUrl());
				cim.setUrlButton(cie.getUrlButton());
				cim.setUserSourceType(cie.getUserSourceType());
				cim.setIsShare(cie.getIsShare());
				
				//condition条件
				InfoConditionModel icm = new InfoConditionModel();
				icm.setPar(cie.getPar());
				icm.setType(cie.getConditionType());
				icm.setStartFee(cie.getStartFee());
				icm.setEndFee(cie.getEndFee());
				
				cim.setInfoCondition(icm);
				
				list.add(cim);
			});
		}
		return list;
	}
	

	@Override
	@Transactional
	public void reissue(UserSourceModel userSource) throws CouponException {
		CouponInfoEntity cie = couponInfoMapper.query(userSource.getCpId());	
		if (null == cie) {
 			logger.error("优惠券不存在，补发异常={}");
 			throw new CouponException("S09");
		}
		
		if (cie.getStatus() != CouponInfoStatus.send.ordinal()) {
 			logger.error("优惠券未发布，补发异常={}");
 			throw new CouponException("S08");
		}
			
		if (new Date().getTime() >= cie.getDueTime().getTime()) {
			logger.error("超过优惠券领取截止时间");
			throw new CouponException("S17");
		}
			
		//判断库存
		int stock = cie.getStock();
		if (stock < userSource.getParams().size()) {
			logger.error("优惠券库存不足，当前库存={}",stock);
			throw new CouponException("S15");
		} else {
			//更新主表的库存
			stock = stock - userSource.getParams().size();
			couponInfoMapper.updateStock(userSource.getCpId(), stock);
		}
							
		InfoOperatorModel infoOperator = userSource.getOperator();
		//即发放状态
		//更新操作人表  补发状态
		InfoOperatorEntity infoOperatorEntity = new InfoOperatorEntity();
		infoOperatorEntity.setInfoId(userSource.getCpId());
		infoOperatorEntity.setOperatorId(infoOperator.getUserId());
		infoOperatorEntity.setOperatorName(infoOperator.getName());
		infoOperatorEntity.setOperatorTel(infoOperator.getTel());
		infoOperatorEntity.setType(InfoOperatorType.reissue.ordinal());
		infoOperatorMapper.add(infoOperatorEntity);
				
		//插入用户选择资源表
		InfoUserSourceEntity iuse = new InfoUserSourceEntity();
		iuse.setInfoId(userSource.getCpId());
		iuse.setType(UserSourceType.importUser.ordinal());
		iuse.setWay(UserSourceWay.provide.ordinal());		
		infoUserSourceMapper.add(iuse);
				
		List<InfoUserSourceDetailEntity> iusdes = Lists.newArrayList();
		userSource.getParams().forEach(tel ->{
			InfoUserSourceDetailEntity iusde = new InfoUserSourceDetailEntity();
			iusde.setUserSourceId(iuse.getId());
			iusde.setTel(tel);
			iusde.setId(UUID.randomUUID().toString().replace("-", ""));
			iusdes.add(iusde);
		});
					
		infoUserSourceDetailMapper.batchSave(iusdes);
													
		userSource.getParams().forEach(tel ->{

			UserBagEntity ube = new UserBagEntity();
			ube.setInfoId(userSource.getCpId());
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
					this.codeSend(cie, tel,infoCodeEntity,infoCodePlanEntity);
				}
			}
			//差不券包日志表
			UserBagLogEntity uble = new UserBagLogEntity();
			uble.setUserBagId(ube.getId());
			uble.setType(UserBagLogType.get.ordinal());
					
			userBagLogMapper.add(uble);
					
		});
		
		
		//插入优惠券统计发放表
		SendRecordEntity sre = new SendRecordEntity();
		sre.setInfoId(userSource.getCpId());
		sre.setType(SendType.reissue.ordinal());
		sre.setUserScope(UserSourceType.importUser.ordinal());
		sre.setCount(userSource.getParams().size());
		sre.setInfoOperatorId(infoOperatorEntity.getId());
		
		sendRecordMapper.add(sre);		
	}
	
	
	@Override
	@Transactional
	public void edit(CouponInfoModel couponInfo) throws CouponException {
		
		CouponInfoEntity cieQuery = couponInfoMapper.query(couponInfo.getCpId());
		if (null == cieQuery) {
 			logger.error("优惠券不存在，编辑异常={}");
 			throw new CouponException("S03");
		}
		
		//删除和cpId的关联表
		infoConditionMapper.delete(couponInfo.getCpId());
		infoScopeMapper.delete(couponInfo.getCpId());
		infoChannelDetailMapper.delete(couponInfo.getCpId());
		infoChannelMapper.delete(couponInfo.getCpId());
		couponShopInfoMapper.delete(couponInfo.getCpId());
		logoMapper.delete(couponInfo.getCpId());
		
		//更新优惠券主表,check_no没有,pre_way没有
		Map<String, Object> params = Maps.newHashMap();
		
		params.put("cpId", couponInfo.getCpId());
		params.put("cpName", couponInfo.getCpName());
		params.put("preWay", couponInfo.getPreWay());
		params.put("checkNo", couponInfo.getCheckNo());
		params.put("startTime", couponInfo.getStartTime());
		params.put("endTime", couponInfo.getEndTime());
		params.put("circulation", couponInfo.getCirculation());
		params.put("stock",couponInfo.getCirculation());
		params.put("limitNum", couponInfo.getLimitNum());
		params.put("beginTime", couponInfo.getBeginTime());
		params.put("dueTime", couponInfo.getDueTime());
		params.put("type", couponInfo.getType());
		params.put("costType", couponInfo.getCostType());
		params.put("isCode", couponInfo.getIsCode());
		params.put("status", couponInfo.getStatus());
		params.put("isCode", couponInfo.getIsCode());
		params.put("rule", couponInfo.getRule());
		params.put("memo", couponInfo.getMemo());
		params.put("url", couponInfo.getUrl());
		params.put("urlButton", couponInfo.getUrlButton());
		params.put("richText", couponInfo.getRichText());
		params.put("isShare", couponInfo.getIsShare());
		params.put("meanwhile", couponInfo.getMeanwhile());
		params.put("meantime", couponInfo.getMeantime());
		params.put("channelText", couponInfo.getChannelText());
		
		//判断状态是否一致，防止编辑和发布同时操作
		if (couponInfo.getStatus() == cieQuery.getStatus()) {
			//只更新这一张表
			couponInfoMapper.updateAll(params);
		} else {
 			logger.error("编辑异常，优惠券状态不一致={}");
 			throw new CouponException("S10");
		}

		//插入券logo
		List<LogoModel> logos = couponInfo.getLogos();
		if (!CollectionUtils.isEmpty(logos)) {
			logos.forEach(logo ->{
				LogoEntity logoEntity = new LogoEntity();
				logoEntity.setInfoId(couponInfo.getCpId());
				logoEntity.setLogo(logo.getLogo());
				logoEntity.setType(LogoTypeEnum.coupon.ordinal());
				
				logoMapper.save(logoEntity);
			});
		}

		//商铺信息
		List<CouponShopInfoModel> csims = couponInfo.getCouponShopInfoModels();
		Set<String> copyShop = Sets.newHashSet();
		csims.forEach(csim ->{
			copyShop.add(csim.getPlatCode());
		});
		if (copyShop.size() != csims.size()) {
 			logger.error("绑定的商铺重复={}");
 			throw new CouponException("S48");
		}
		csims.forEach(csim ->{
			CouponShopInfoEntity couponShopInfoEntity = new CouponShopInfoEntity();
			couponShopInfoEntity.setInfoId(couponInfo.getCpId());
			couponShopInfoEntity.setPlatCode(csim.getPlatCode());
			couponShopInfoEntity.setShopName(csim.getShopName());
			
			couponShopInfoMapper.add(couponShopInfoEntity);
		});
		
		//插入优惠券条件表
		InfoConditionEntity ice = new InfoConditionEntity();
		ice.setInfoId(couponInfo.getCpId());
		ice.setType(couponInfo.getInfoCondition().getType());
		ice.setPar(couponInfo.getInfoCondition().getPar());
		ice.setStartFee(couponInfo.getInfoCondition().getStartFee());
		ice.setEndFee(couponInfo.getInfoCondition().getEndFee());
		infoConditionMapper.add(ice);
		
		//插入优惠券使用范围表  商品优惠券采用插入
		if (CouponInfoType.goods.ordinal() == couponInfo.getType()) {  //商品优惠券
			//插入条件的参数
			if (!CollectionUtils.isEmpty(couponInfo.getInfoScope().getNos())) {
				couponInfo.getInfoScope().getNos().forEach(no ->{
					InfoScopeEntity ise = new InfoScopeEntity();
					ise.setInfoId(couponInfo.getCpId());
					ise.setScopeType(couponInfo.getInfoScope().getType());
					ise.setParam(no);
					infoScopeMapper.add(ise);
				});
			}
		} else if (CouponInfoType.category.ordinal() == couponInfo.getType()) { //品类优惠券
			//插入条件的参数
			if (!CollectionUtils.isEmpty(couponInfo.getInfoScope().getNos())) {
				couponInfo.getInfoScope().getNos().forEach(no ->{
					InfoScopeEntity ise = new InfoScopeEntity();
					ise.setInfoId(couponInfo.getCpId());
					ise.setScopeType(couponInfo.getInfoScope().getType());
					ise.setParam(no);
					infoScopeMapper.add(ise);
					
				});
			}
		}
		
		//插入优惠券渠道
		List<ChannelModel> channelModels = couponInfo.getChannels();
		if (!CollectionUtils.isEmpty(channelModels)) {
			
			channelModels.forEach(channelModel ->{
				
				InfoChannelEntity infoChannelEntity = new InfoChannelEntity();
				infoChannelEntity.setInfoId(couponInfo.getCpId());
				infoChannelEntity.setChannelNo(channelModel.getChannelNo());
				infoChannelEntity.setChannelName(channelModel.getChannelName());
				
				infoChannelMapper.add(infoChannelEntity);
				List<ChannelDetailModel> channelDetailModels =  channelModel.getChannelDetails();
				
				channelDetailModels.forEach(channelDetailModel ->{
					
					InfoChannelDetailEntity infoChannelDetailEntity = new InfoChannelDetailEntity();
					infoChannelDetailEntity.setInfoChannelId(infoChannelEntity.getId());
					infoChannelDetailEntity.setChannelDetailNo(channelDetailModel.getChannelDetailNo());
					infoChannelDetailEntity.setChannelDetailName(channelDetailModel.getChannelDetailName());
					
					infoChannelDetailMapper.add(infoChannelDetailEntity);
				});
			});
		}
		
		//优惠券操作人表,添加一条编辑记录
		InfoOperatorModel iom = couponInfo.getOperator();
		InfoOperatorEntity infoOperatorEntity = new InfoOperatorEntity();
		infoOperatorEntity.setInfoId(couponInfo.getCpId());
		infoOperatorEntity.setOperatorId(iom.getUserId());
		infoOperatorEntity.setOperatorName(iom.getName());
		infoOperatorEntity.setOperatorTel(iom.getTel());
		infoOperatorEntity.setType(InfoOperatorType.edit.ordinal());
		
		infoOperatorMapper.add(infoOperatorEntity);	
	}

	@Override
	@Transactional
	public void sendMsg(UserSourceModel userSource) throws CouponException {
		
		logger.error("消息队列发放优惠券当前请求参数={}",JSON.toJSONString(userSource),"UTF-8");
		
		CouponInfoEntity cie = couponInfoMapper.query(userSource.getCpId());
		
		//更新优惠券的用户属性
		couponInfoMapper.updateUserSourceType(userSource.getCpId(), userSource.getType());
		//消息处理		
		//插入用户选择资源表
		InfoUserSourceEntity iuse = new InfoUserSourceEntity();
		iuse.setInfoId(userSource.getCpId());
		iuse.setType(userSource.getType());
		iuse.setWay(userSource.getWay());
				
		infoUserSourceMapper.add(iuse);
		
		if (userSource.getType() == UserSourceType.importUser.ordinal()) { //用户导入
				
			List<InfoUserSourceDetailEntity> iusdes = Lists.newArrayList();
			userSource.getParams().forEach(tel ->{
				InfoUserSourceDetailEntity iusde = new InfoUserSourceDetailEntity();
				iusde.setUserSourceId(iuse.getId());
				iusde.setTel(tel);
				iusde.setId(UUID.randomUUID().toString().replace("-", ""));
				iusdes.add(iusde);
			});
			
			infoUserSourceDetailMapper.batchSave(iusdes);
			
			//插入优惠券统计发放表
			SendRecordEntity sre = new SendRecordEntity();
			sre.setInfoId(userSource.getCpId());
			sre.setType(SendType.send.ordinal());
			sre.setUserScope(UserSourceType.importUser.ordinal());
			sre.setCount(userSource.getParams().size());
			sre.setInfoOperatorId(userSource.getOperator().getId());
			
			sendRecordMapper.add(sre);
			
		} else if(userSource.getType() == UserSourceType.level.ordinal()) { //等级导入
			
			logger.error("消息队列发放优惠券当前请求参数={等级发放}");
			
			List<InfoUserSourceLevelEntity> iusles = Lists.newArrayList();
			userSource.getParams().forEach(level ->{
				InfoUserSourceLevelEntity iusle = new InfoUserSourceLevelEntity();
				iusle.setUserSourceId(iuse.getId());
				iusle.setLevel(level);
				iusle.setId(UUID.randomUUID().toString().replace("-", ""));
				iusles.add(iusle);
			});
			infoUserSourceLevelMapper.batchSave(iusles);
			
			//插入优惠券统计发放表
			SendRecordEntity sre = new SendRecordEntity();
			sre.setInfoId(userSource.getCpId());
			sre.setType(SendType.send.ordinal());
			sre.setUserScope(UserSourceType.level.ordinal());
			String cpId = userSource.getCpId();
			sre.setCount(jphUserMapper.selectCount(cpId));
			sre.setInfoOperatorId(userSource.getOperator().getId());
			
			sendRecordMapper.add(sre);
		} else if (userSource.getType() == UserSourceType.common.ordinal()) {//一般用户
			//插入优惠券统计发放表
			SendRecordEntity sre = new SendRecordEntity();
			sre.setInfoId(userSource.getCpId());
			sre.setType(SendType.send.ordinal());
			sre.setUserScope(UserSourceType.common.ordinal());
			sre.setInfoOperatorId(userSource.getOperator().getId());
			sre.setCount(0);
			
			sendRecordMapper.add(sre);
		}
		
		if (UserSourceWay.provide.ordinal() == userSource.getWay()) {//平台发放，插入用户卡券包
			
			//如果是导入用户
			if (userSource.getType() == UserSourceType.importUser.ordinal()) {
				
				//判断库存
				int stock = cie.getStock();
				if (stock < userSource.getParams().size()) {
					logger.error("优惠券库存不足，当前库存={}",stock);
					throw new CouponException("S15");
				} else {
					//更新主表的库存
					stock = stock - userSource.getParams().size();
					couponInfoMapper.updateStock(userSource.getCpId(), stock);
				}
				
				userSource.getParams().forEach(tel ->{
					UserBagEntity ube = new UserBagEntity();
					ube.setInfoId(userSource.getCpId());
					ube.setUserTel(tel);
					ube.setStatus(UserBagStatus.get.ordinal());
					userBagMapper.add(ube);
					
					//判断是否是券码券
					if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，发送短信提醒
						
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
							this.codeSend(cie, tel,infoCodeEntity,infoCodePlanEntity);
						}
						
					}
					//插入券包日志表
					UserBagLogEntity uble = new UserBagLogEntity();
					uble.setUserBagId(ube.getId());
					uble.setType(UserBagLogType.get.ordinal());
					
					userBagLogMapper.add(uble);
					
				});
				
			}else if(userSource.getType() == UserSourceType.level.ordinal()){//如果是等级用户
				List<String> tels = jphUserMapper.selectTels(userSource.getParams());
				
				//判断库存
				int stock = cie.getStock();
				if (stock < tels.size()) {
					logger.error("优惠券库存不足，当前库存={}",stock);
					throw new CouponException("S15");
				} else {
					//更新主表的库存
					stock = stock - tels.size();
					couponInfoMapper.updateStock(userSource.getCpId(), stock);
				}
				
				tels.forEach(tel ->{
					UserBagEntity ube = new UserBagEntity();
					ube.setInfoId(userSource.getCpId());
					ube.setUserTel(tel);
					ube.setStatus(UserBagStatus.get.ordinal());
					userBagMapper.add(ube);
					//判断是否是券码券
					if (CouponIsCode.yes.ordinal() == cie.getIsCode()) {//券码券，发送短信提醒
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
					//差不券包日志表
					UserBagLogEntity uble = new UserBagLogEntity();
					uble.setUserBagId(ube.getId());
					uble.setType(UserBagLogType.get.ordinal());
					
					userBagLogMapper.add(uble);
				});
				
			}
		}	
			
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
		
		params.put("text",buffer.toString());
		params.put("mobile", tel);
		params.put("channel", icpe.getMsgTag());
		
		String resStr = HttpHelper.httpPostParam(msgURL, params ,"UTF-8");
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
	public void bindingCoupon(BindCpModel bindCpModel) throws CouponException{
		
		//校验店铺号
		List<String> platCodes = couponShopInfoMapper.selectPlatCodes(bindCpModel.getCpId());
		if (!platCodes.contains(bindCpModel.getPlatCode())) {
			logger.error("该优惠券商铺号不包含当前的商铺号={}",bindCpModel.getPlatCode());
			throw new CouponException("S30");	
		}
				
		//优惠券绑定code码。
		List<String> codePlanIds = bindCpModel.getCodePlanIds();
		codePlanIds.forEach(codePlanId ->{
			
			InfoCodePlanEntity infoCodePlanEntity = new InfoCodePlanEntity();
			infoCodePlanEntity.setCpId(bindCpModel.getCpId());
			infoCodePlanEntity.setStatus(CodePlanStatus.bind.ordinal());
			infoCodePlanEntity.setId(codePlanId);
			
			infoCodePlanMapper.update(infoCodePlanEntity);	
		});
		
		//维护优惠券表的字段，isCode，is_bind_code
		couponInfoMapper.updateCodeStatus(bindCpModel.getCpId(),CouponIsBindCode.yes.ordinal());
	}

	@Override
	@Transactional
	public void importCode(InfoCodeModel infoCodeModel) throws CouponException{
		
		//1.校验list中code是否重复
		List<String> infoCodes = infoCodeModel.getInfoCodes();
        String code = "";
        for (int i = 0; i < infoCodes.size() - 1; i++) {
        	code = infoCodes.get(i);
            for (int j = i + 1; j < infoCodes.size(); j++) {
                if (StringUtils.equals(code, infoCodes.get(j))) {
						logger.error("本批导入的券码重复 infoCode={}",code);
						throw new CouponException("S23");	
                }
            }
        }
        		
		//2.插入批次表
        InfoCodePlanEntity infoCodePlanEntity = new InfoCodePlanEntity();
        infoCodePlanEntity.setCount(infoCodes.size());
        infoCodePlanEntity.setPlatCode(infoCodeModel.getPlatCode());
        infoCodePlanEntity.setStatus(CodePlanStatus.init.ordinal());
        infoCodePlanEntity.setIsMsg(infoCodeModel.getMsgStatus());
        infoCodePlanEntity.setShopName(infoCodeModel.getShopName());
        infoCodePlanEntity.setType(infoCodeModel.getType());
        infoCodePlanMapper.add(infoCodePlanEntity);
        
      
        infoCodes.forEach(infoCode ->{
        	 //校验插入是否重复
        	 InfoCodeEntity icpe =  infoCodeMapper.queryDistinct(infoCodeModel.getPlatCode(),infoCode);
        	 if (null != icpe) {
				logger.error("本批和前几批导入的券码重复 infoCode={}",infoCode);
				throw new CouponException("S25");	
        	 }
        	  //3.插入code码表 
        	 InfoCodeEntity infoCodeEntity = new InfoCodeEntity(); 
        	 infoCodeEntity.setCodePlanId(infoCodePlanEntity.getId());
        	 infoCodeEntity.setStatus(CodePlanStatus.init.ordinal());
        	 infoCodeEntity.setCpCode(infoCode);
        	 infoCodeMapper.add(infoCodeEntity);
        });	
	}

	@Override
	public PageResult<CodeListResModel> queryCodeList(CodeListParamsModel codeListParamsModel) throws CouponException {
		
		PageResult<CodeListResModel> page = new PageResult<CodeListResModel>();
		Map<String, Object> params = Maps.newHashMap();
	
		//是否分页
		if (!codeListParamsModel.isIspage()) {
			params.put("start",null);
			params.put("limit",null);	
		} else {
			params.put("start", (codeListParamsModel.getPageNo()-1)*codeListParamsModel.getPageSize());
			params.put("limit", codeListParamsModel.getPageSize());	
		}
		
		params.put("platCode", codeListParamsModel.getPlatCode());
		params.put("codePlanStatus", codeListParamsModel.getCodePlanStatus());	
		
		
		List<CodeListEntity> cles = infoCodePlanMapper.queryCodeList(params);
		List<CodeListResModel> clrms = Lists.newArrayList();
				
		if (!CollectionUtils.isEmpty(cles)) {
			
			cles.forEach(cle ->{
				
				CodeListResModel clrm = new CodeListResModel();
				clrm.setCount(cle.getCount());
				clrm.setCpId(cle.getCpId());
				clrm.setCreateTime(cle.getCreateTime());
				clrm.setPlatCode(cle.getPlatCode());
				clrm.setCodePlanStatus(cle.getCodePlanStatus());
				clrm.setCpName(cle.getCpName());
				clrm.setCodeId(cle.getId());
				clrm.setShopName(cle.getShopName());
				clrm.setIsMsg(cle.getIsMsg());
				clrm.setType(cle.getType());
				
				clrms.add(clrm);
			});	
					
		}
		page.setData(clrms);
		
		//计算分页
		int total = infoCodePlanMapper.queryCodeListCount(params);
		page.setTotalResults(total);
		if (codeListParamsModel.isIspage() == false) {
			page.setHasNext(false);
		} else {
			int totalPage = (total + codeListParamsModel.getPageSize() - 1) / codeListParamsModel.getPageSize();
			page.setHasNext(codeListParamsModel.getPageNo()+1 <=  totalPage);
		}
		return page;		
	}

	@Override
	@Transactional
	public void unbindingCoupon(String codePlanId,String platCode) throws CouponException {
		
		//已发布的优惠券不能取消绑定。
		InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(codePlanId);
		
		if (infoCodePlanEntity == null) {
			logger.error("券码不存在,券码id={}",codePlanId);
			throw new CouponException("S26");	
		}
		
		if (infoCodePlanEntity.getStatus() != CodePlanStatus.bind.ordinal()) {
			logger.error("券码未绑定优惠券,券码id={}",codePlanId);
			throw new CouponException("S28");	
		}
		
		CouponInfoEntity cife = couponInfoMapper.query(infoCodePlanEntity.getCpId());	
		
		if (null == cife) {
	 		logger.error("优惠券不存在，cpId={}",infoCodePlanEntity.getCpId());
	 		throw new CouponException("S03");	
		}
		
		if (cife.getStatus() == CouponInfoStatus.send.ordinal()) {
	 		logger.error("优惠券已发布，券码取消绑定异常={}",codePlanId);
	 		throw new CouponException("S29");
		}
		
		//校验店铺号
		List<String> platCodes = couponShopInfoMapper.selectPlatCodes(cife.getId());
		if (!platCodes.contains(platCode)) {
			logger.error("该优惠券商铺号不包含当前的商铺号={}",platCode);
			throw new CouponException("S30");	
		}
		
		//维护优惠券表的字段，isCode，is_bind_code
		couponInfoMapper.updateCodeStatus(cife.getId(),CouponIsBindCode.no.ordinal());
		
		//清空优惠券券码对应的cpId，状态设置为初始化（未绑定）	
		infoCodePlanMapper.unbind(codePlanId);		
	}
		
	@Override
	@Transactional
	public void deleteCode(String codePlanId,String platCode) throws CouponException {
	
		//已经绑定优惠券的券码,不能删除
		InfoCodePlanEntity infoCodePlanEntity = infoCodePlanMapper.query(codePlanId);
		if (infoCodePlanEntity == null) {
			logger.error("券码不存在,券码id={}",codePlanId);
			throw new CouponException("S26");	
		}
		
		if (infoCodePlanEntity.getStatus() != CodePlanStatus.init.ordinal()) {
			logger.error("券码已经绑定优惠券,券码id={}",codePlanId);
			throw new CouponException("S27");	
		}
		
		//校验店铺号
		if (!StringUtils.equals(platCode, infoCodePlanEntity.getPlatCode())) {
			logger.error("当前商铺号不是券码对应的商铺号,当前商铺号={}",platCode);
			throw new CouponException("S30");	
		}
		
    	//1.删除code码表       	 
    	infoCodeMapper.delete(codePlanId);	
    	//2.删除code_plan表
        infoCodePlanMapper.delete(codePlanId);
	}

	@Override
	@Transactional
	public void createPackage(PackageInitModel packageInitModel) throws CouponException {
		
		//插入券包表
		CouponPackageEntity couponPackageEntity = new CouponPackageEntity();
		couponPackageEntity.setName(packageInitModel.getName());
		couponPackageEntity.setStatus(CouponPackageStatus.init.ordinal());
		couponPackageEntity.setStartTime(packageInitModel.getStartTime());
		couponPackageEntity.setEndTime(packageInitModel.getEndTime());
		couponPackageEntity.setPlatCode(packageInitModel.getPlatCode());
		couponPackageEntity.setIsShare(packageInitModel.getIsShare());
		couponPackageEntity.setShopName(packageInitModel.getShopName());
		couponPackageEntity.setRichText(packageInitModel.getRichText());
		couponPackageEntity.setChannelText(packageInitModel.getChannelText());
		
		couponPackageMapper.add(couponPackageEntity);
		
		//插入券包logo
		List<LogoModel> logos = packageInitModel.getLogos();
		if (!CollectionUtils.isEmpty(logos)) {
			logos.forEach(logo ->{
				LogoEntity logoEntity = new LogoEntity();
				logoEntity.setInfoId(couponPackageEntity.getId());
				logoEntity.setLogo(logo.getLogo());
				logoEntity.setType(LogoTypeEnum.couponPackage.ordinal());
				
				logoMapper.save(logoEntity);
			});
		}
	}
	

	@Override
	public PageResult<CouponPackageModel> queryPackageList(PackageListParamsModel packageListParamsModel)
			throws CouponException {
		PageResult<CouponPackageModel> page = new PageResult<CouponPackageModel>();
		Map<String, Object> params = Maps.newHashMap();
	
		//是否分页
		if (!packageListParamsModel.isIspage()) {
			params.put("start",null);
			params.put("limit",null);	
		} else {
			params.put("start", (packageListParamsModel.getPageNo()-1)*packageListParamsModel.getPageSize());
			params.put("limit", packageListParamsModel.getPageSize());	
		}
		
		params.put("platCode",packageListParamsModel.getPlatCode());
		params.put("name", packageListParamsModel.getName());
		params.put("status", packageListParamsModel.getStatus());	
		
		List<CouponPackageEntity> ples = couponPackageMapper.queryPackageList(params);
		List<CouponPackageModel> cpms = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(ples)) {
			
			ples.forEach(ple ->{
				CouponPackageModel cpm = new CouponPackageModel();
				cpm.setName(ple.getName());
				cpm.setPackageId(ple.getId());
				cpm.setStatus(ple.getStatus());
				cpm.setStartTime(ple.getStartTime());
				cpm.setEndTime(ple.getEndTime());
				cpm.setPlatCode(ple.getPlatCode());
				cpm.setIsShare(ple.getIsShare());
				cpm.setShopName(ple.getShopName());
				cpm.setRichText(ple.getRichText());
				cpm.setChannelText(ple.getChannelText());
				
				//查询logo
				List<LogoModel> logos = Lists.newArrayList();
				List<LogoEntity> logoEntitys = logoMapper.select(ple.getId()); 
				if (!CollectionUtils.isEmpty(logoEntitys)) {
					logoEntitys.forEach(logoEntity ->{
						LogoModel logo = new LogoModel();
						logo.setId(logoEntity.getId());
						logo.setInfoId(logoEntity.getInfoId());
						logo.setLogo(logoEntity.getLogo());
						logo.setType(logoEntity.getType());
						
						logos.add(logo);
					});
					cpm.setLogos(logos);
				}
				
				cpms.add(cpm);
			});	
		}
		page.setData(cpms);
		
		//计算分页
		int total =  couponPackageMapper.queryPackageListCount(params);
		page.setTotalResults(total);
		if (packageListParamsModel.isIspage()) {
			page.setHasNext(false);
		} else {
			int totalPage = (total + packageListParamsModel.getPageSize() - 1) / packageListParamsModel.getPageSize();
			page.setHasNext(packageListParamsModel.getPageNo()+1 <=  totalPage);
		}
		return page;	
	}

	@Override
	@Transactional
	public void editPackageCoupon(PackageEditModel packageEditModel) throws CouponException {
		
		//1、编辑券包
		Map<String, Object> params = Maps.newHashMap();
		params.put("packageId", packageEditModel.getPackageId());
		params.put("name", packageEditModel.getName());
		params.put("startTime", packageEditModel.getStartTime());
		params.put("endTime",packageEditModel.getEndTime());
		params.put("isShare",packageEditModel.getIsShare());
		params.put("platCode",packageEditModel.getPlatCode());
		params.put("shopName",packageEditModel.getShopName());
		params.put("richText", packageEditModel.getRichText());
		params.put("channelText", packageEditModel.getChannelText());
		
		couponPackageMapper.updatePackage(params);

		//2、校验券包导入的优惠券是否有重复
		if (packageEditModel.getStatus() == CouponPackageStatus.bind.ordinal()) {
			
			List<String> infoIds = packageEditModel.getInfoIds();	
			if (CollectionUtils.isEmpty(infoIds)) {
				logger.error("券包未绑定优惠券");
				throw new CouponException("S40");		
			}
			String repeatId = "";
	        for (int i = 0; i < infoIds.size() - 1; i++) {
	        	repeatId = infoIds.get(i);
	            for (int j = i + 1; j < infoIds.size(); j++) {
	                if (StringUtils.equals(repeatId, infoIds.get(j))) {
							logger.error("券包导入的优惠券重复 infoId={}",repeatId);
							throw new CouponException("S32");	
	                }
	            }
	        }
	        
	        //3、券包重新绑定优惠券
	        couponPackageInfoMapper.delete(packageEditModel.getPackageId());
	        infoIds.forEach(infoId ->{
	        	CouponPackageInfoEntity cpie = new CouponPackageInfoEntity();
	        	cpie.setInfoId(infoId);
	        	cpie.setPackageId(packageEditModel.getPackageId());
	        	
	        	couponPackageInfoMapper.add(cpie);
	        });
	        
	        //4、修改券包状态为已绑定
	        couponPackageMapper.updateStatus(CouponPackageStatus.bind.ordinal(),packageEditModel.getPackageId());
	        
	        //5、券包logo
	        logoMapper.delete(packageEditModel.getPackageId());
	        
			//插入券logo
			List<LogoModel> logos = packageEditModel.getLogos();
			if (!CollectionUtils.isEmpty(logos)) {
				logos.forEach(logo ->{
					LogoEntity logoEntity = new LogoEntity();
					logoEntity.setInfoId(packageEditModel.getPackageId());
					logoEntity.setLogo(logo.getLogo());
					logoEntity.setType(LogoTypeEnum.couponPackage.ordinal());
					
					logoMapper.save(logoEntity);
				});
			}

		}
	}

	@Override
	@Transactional
	public void freezePackageCoupon(String packageId) throws CouponException {
		//将券包状态设置为冻结状态
		couponPackageMapper.updateStatus(CouponPackageStatus.freeze.ordinal(), packageId);
	}
	
	@Override
	@Transactional
	public CouponPackageModel packageDetail(String packageId) throws CouponException {
		CouponPackageModel couponPackageModel = new CouponPackageModel();
		
		CouponPackageEntity ple = couponPackageMapper.query(packageId);
		couponPackageModel.setPackageId(ple.getId());
		couponPackageModel.setName(ple.getName());
		couponPackageModel.setStatus(ple.getStatus());
		couponPackageModel.setStartTime(ple.getStartTime());
		couponPackageModel.setEndTime(ple.getEndTime());
		couponPackageModel.setPlatCode(ple.getPlatCode());
		couponPackageModel.setIsShare(ple.getIsShare());
		couponPackageModel.setShopName(ple.getShopName());
		couponPackageModel.setRichText(ple.getRichText());
		couponPackageModel.setChannelText(ple.getChannelText());
		
		//券包logo
		List<LogoModel> logos = Lists.newArrayList();
		List<LogoEntity> logoEntitys = logoMapper.select(packageId); 
		if (!CollectionUtils.isEmpty(logoEntitys)) {
			logoEntitys.forEach(logoEntity ->{
				LogoModel logo = new LogoModel();
				logo.setId(logoEntity.getId());
				logo.setInfoId(logoEntity.getInfoId());
				logo.setLogo(logoEntity.getLogo());
				logo.setType(logoEntity.getType());
				
				logos.add(logo);
			});
			couponPackageModel.setLogos(logos);
		}
		
		//关联的优惠券详情
		List<CouponInfoDetailEntity> cies = couponPackageInfoMapper.queryInfoDetail(packageId);
		List<CouponInfoModel> couponInfoModels = Lists.newArrayList();
		
		if (!CollectionUtils.isEmpty(cies)) {
			
			cies.forEach(cie -> {
				
				CouponInfoModel cim = new CouponInfoModel();
				
				cim.setCpId(cie.getCpId());
				cim.setCpName(cie.getCpName());
				cim.setPreWay(cie.getPreWay());
				cim.setCheckNo(cie.getCheckNo());
				cim.setStartTime(cie.getStartTime());
				cim.setEndTime(cie.getEndTime());
				cim.setCirculation(cie.getCirculation());
				cim.setLimitNum(cie.getLimitNum());
				cim.setStock(cie.getStock());
				cim.setType(cie.getType());
				cim.setBeginTime(cie.getBeginTime());
				cim.setDueTime(cie.getDueTime());
				cim.setStatus(cie.getStatus());
				cim.setIsCode(cie.getIsCode());
				cim.setIsBindCode(cie.getIsBindCode());
				cim.setCostType(cie.getCostType());
				cim.setRule(cie.getRule());
				cim.setMemo(cie.getMemo());
				cim.setCreateTime(cie.getCreateTime());
				cim.setUpdateTime(cie.getUpdateTime());
				cim.setRichText(cie.getRichText());
				cim.setUrl(cie.getUrl());
				cim.setUrlButton(cie.getUrlButton());
				cim.setUserSourceType(cie.getUserSourceType());
				cim.setChannelText(cie.getChannelText());
				
				//查询卡券使用条件、查询卡券面额
				InfoConditionModel infoConditionModel = new InfoConditionModel();
				infoConditionModel.setType(cie.getConditionType());
				infoConditionModel.setPar((cie.getPar()));
				infoConditionModel.setStartFee(cie.getStartFee());
				infoConditionModel.setEndFee(cie.getEndFee());
				cim.setInfoCondition(infoConditionModel);
			
				//查询logo
				List<LogoModel> couponLogos = Lists.newArrayList();
				List<LogoEntity> couponLogoEntitys = logoMapper.select(cie.getCpId()); 
				if (!CollectionUtils.isEmpty(couponLogoEntitys)) {
					logoEntitys.forEach(couponLogoEntity ->{
						LogoModel logo = new LogoModel();
						logo.setId(couponLogoEntity.getId());
						logo.setInfoId(couponLogoEntity.getInfoId());
						logo.setLogo(couponLogoEntity.getLogo());
						logo.setType(couponLogoEntity.getType());
						
						couponLogos.add(logo);
					});
					cim.setLogos(couponLogos);
				}
				
				//插入商铺信息表
				List<CouponShopInfoModel> csims = Lists.newArrayList();
				List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(cie.getCpId()); 
				if (!CollectionUtils.isEmpty(csies)) {
					csies.forEach(csie ->{
						CouponShopInfoModel csim = new CouponShopInfoModel();
						csim.setInfoId(csie.getInfoId());
						csim.setPlatCode(csie.getPlatCode());
						csim.setShopName(csie.getShopName());
						
						csims.add(csim);
					});
					cim.setCouponShopInfoModels(csims);
				}	
				couponInfoModels.add(cim);
			});
			couponPackageModel.setCouponInfoModels(couponInfoModels);
		}
		return couponPackageModel;
	}

	@Override
	@Transactional
	public void isSendMsg(String codePlanId,Integer isMsg) throws CouponException{
		//更新券码批次表中isMsg状态
		infoCodePlanMapper.updateMsgStatus(codePlanId, isMsg.toString());
	}

	@Override
	@Transactional
	public String copyCreate(CouponCopyParamsModel couponCopyParamsModel) throws CouponException {
		
		CouponInfoDetailEntity couponInfo = couponInfoMapper.queryDetail(couponCopyParamsModel.getCpId());
		
		//插入优惠券主表
		CouponInfoEntity cie = new CouponInfoEntity();
		cie.setCpName(couponInfo.getCpName()+"副本");
		cie.setPreWay(couponInfo.getPreWay());
		cie.setCheckNo(couponInfo.getCheckNo());
		cie.setStartTime(couponInfo.getStartTime());
		cie.setEndTime(couponInfo.getEndTime());
		cie.setCirculation(couponInfo.getCirculation());
		cie.setStock(couponInfo.getCirculation());
		cie.setLimitNum(couponInfo.getLimitNum());
		cie.setBeginTime(couponInfo.getBeginTime());
		cie.setDueTime(couponInfo.getDueTime());
		cie.setType(couponInfo.getType());
		cie.setCostType(couponInfo.getCostType());
		cie.setStatus(CouponInfoStatus.init.ordinal());//初始化
		cie.setIsCode(couponInfo.getIsCode());//默认不是券码券
		cie.setMemo(couponInfo.getMemo());
		cie.setRule(couponInfo.getRule());
		cie.setRichText(couponInfo.getRichText());
		cie.setUrl(couponInfo.getUrl());
		cie.setUrlButton(couponInfo.getUrlButton());
		cie.setIsShare(couponInfo.getIsShare());
		cie.setMeanwhile(couponInfo.getMeanwhile());
		cie.setMeantime(couponInfo.getMeantime());
		cie.setChannelText(couponInfo.getChannelText());
		
		couponInfoMapper.add(cie);
		
		//插入优惠券条件表
		InfoConditionEntity infoConditionEntity = new InfoConditionEntity();
		infoConditionEntity.setInfoId(cie.getId());
		infoConditionEntity.setType(couponInfo.getConditionType());
		infoConditionEntity.setPar(couponInfo.getPar());
		infoConditionEntity.setStartFee(couponInfo.getStartFee());
		infoConditionEntity.setEndFee(couponInfo.getEndFee());
	
		infoConditionMapper.add(infoConditionEntity);
		
		//插入商铺信息表
		List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(couponCopyParamsModel.getCpId());
		csies.forEach(csie ->{
			CouponShopInfoEntity couponShopInfoEntity = new CouponShopInfoEntity();
			couponShopInfoEntity.setInfoId(cie.getId());
			couponShopInfoEntity.setPlatCode(csie.getPlatCode());
			couponShopInfoEntity.setShopName(csie.getShopName());
			
			couponShopInfoMapper.add(couponShopInfoEntity);
		});
				
		//插入优惠券使用范围表  商品优惠券采用插入
		if (CouponInfoType.goods.ordinal() == couponInfo.getType()) {  //商品优惠券
			//插入条件的参数
			List<InfoScopeEntity> ises = infoScopeMapper.queryAll(couponCopyParamsModel.getCpId());	
			if (!CollectionUtils.isEmpty(ises)) {
				ises.forEach(ise ->{
					InfoScopeEntity iseCopy = new InfoScopeEntity();
					iseCopy.setInfoId(cie.getId());
					iseCopy.setScopeType(ise.getScopeType());
					iseCopy.setParam(ise.getParam());
					infoScopeMapper.add(iseCopy);
				});
			}
		} else if (CouponInfoType.category.ordinal() == couponInfo.getType()) { //品类优惠券
			//插入条件的参数
			List<InfoScopeEntity> ises = infoScopeMapper.queryAll(couponCopyParamsModel.getCpId());	
			if (!CollectionUtils.isEmpty(ises)) {
				ises.forEach(ise ->{
					InfoScopeEntity iseCopy = new InfoScopeEntity();
					iseCopy.setInfoId(cie.getId());
					iseCopy.setScopeType(ise.getScopeType());
					iseCopy.setParam(ise.getParam());
					infoScopeMapper.add(iseCopy);
				});
			}
		}
		
		//优惠券渠道
		List<InfoChannelEntity> ices = couponInfoMapper.queryChannels(couponCopyParamsModel.getCpId());
			
		if (!CollectionUtils.isEmpty(ices)) {
			ices.forEach(ice ->{
				InfoChannelEntity infoChannelEntity = new InfoChannelEntity();
				infoChannelEntity.setChannelNo(ice.getChannelNo());
				infoChannelEntity.setChannelName(ice.getChannelName());
				infoChannelEntity.setInfoId(cie.getId());
				infoChannelMapper.add(infoChannelEntity);
				
				//优惠券渠道详情
				List<InfoChannelDetailEntity> icdes = couponInfoMapper.queryChannelDetails(ice.getId());
				if (!CollectionUtils.isEmpty(icdes)) {
					icdes.forEach(icde ->{	
						InfoChannelDetailEntity infoChannelDetailEntity = new InfoChannelDetailEntity();
						infoChannelDetailEntity.setInfoChannelId(infoChannelEntity.getId());
						infoChannelDetailEntity.setChannelDetailName(icde.getChannelDetailName());
						infoChannelDetailEntity.setChannelDetailNo(icde.getChannelDetailNo());
						
						infoChannelDetailMapper.add(infoChannelDetailEntity);
					});
				}
			});
		}
		
		//优惠券操作人表
		InfoOperatorEntity infoOperatorEntity = new InfoOperatorEntity();
		infoOperatorEntity.setInfoId(cie.getId());
		infoOperatorEntity.setOperatorId(couponCopyParamsModel.getUserId());
		infoOperatorEntity.setOperatorName(couponCopyParamsModel.getOperatorName());
		infoOperatorEntity.setOperatorTel(couponCopyParamsModel.getOperatorTel());
		infoOperatorEntity.setType(InfoOperatorType.init.ordinal());
		
		infoOperatorMapper.add(infoOperatorEntity);
		
		return cie.getId();
	}

	@Override
	public void deletePackage(String packageId) throws CouponException {
		//级联删除券包
		couponPackageInfoMapper.delete(packageId);
		couponPackageMapper.delete(packageId);
		logoMapper.delete(packageId);
	}

	@Override
	public List<UserBagResModel> queryUserBag(UserBagParamsModel userBagParamsModel) throws CouponException {
	
		List<UserBagResModel> list = Lists.newArrayList();
		
		Map<String, Object> params = Maps.newHashMap();
		params.put("tel", userBagParamsModel.getTel());
		params.put("cpName", userBagParamsModel.getCpName());
		
		//查询用户券包信息，是否是券码，是否发送短信
		List<UserBagResEntity> ubres = userBagMapper.findUserBagRes(params);
		
		if (CollectionUtils.isEmpty(ubres)) {
			logger.error("用户不存在或券包无该优惠券");
			throw new CouponException("S42");	
		}
		
		ubres.forEach(ubre ->{
			UserBagResModel userBagResModel = new UserBagResModel();	
			userBagResModel.setCpId(ubre.getCpId());
			userBagResModel.setCpName(ubre.getCpName());
			userBagResModel.setCreateTime(ubre.getCreateTime());
			userBagResModel.setBeginTime(ubre.getBeginTime());
			userBagResModel.setDueTime(ubre.getDueTime());
			userBagResModel.setIsCode(ubre.getIsCode());
			userBagResModel.setIsBindCode(ubre.getIsBindCode());
			userBagResModel.setIsMsg(ubre.getIsMsg());
			userBagResModel.setStatus(ubre.getStatus());
			userBagResModel.setCode(ubre.getCode());
			userBagResModel.setMsgStatus(ubre.getMsgStatus());
			
			list.add(userBagResModel);
		});
		
		return list;
	}

	@Override
	public CodeMsgModel queryCodeMsg(String codePlanId) throws CouponException {
		
		//1、根据批次Id找到绑定的优惠券信息
		CodeMsgDetailEntity cmde = couponInfoMapper.queryByCodePlanId(codePlanId);		
		
		CodeMsgModel codeMsgModel = new CodeMsgModel();
		codeMsgModel.setCpCode("ExampleCode");
		codeMsgModel.setCpName(cmde.getCpName());
		codeMsgModel.setDueTime(cmde.getDueTime());
		codeMsgModel.setMsgTemplate(cmde.getMsgTemplate());
		codeMsgModel.setMsgUrl(cmde.getMsgUrl());
		codeMsgModel.setParam(cmde.getParam());
		codeMsgModel.setMsgTag(cmde.getMsgTag());
		
		return codeMsgModel;
	}

	@Override
	@Transactional
	public void setMsg(SetMsgParamsModel setMsgParamsModel) throws CouponException {
		
		InfoCodePlanEntity infoCodePlanEntity = new InfoCodePlanEntity();
		infoCodePlanEntity.setId(setMsgParamsModel.getCodePlanId());
		infoCodePlanEntity.setMsgTemplate(setMsgParamsModel.getMsgTemplate());
		infoCodePlanEntity.setMsgUrl(setMsgParamsModel.getMsgUrl());
		infoCodePlanEntity.setParam(setMsgParamsModel.getParam());
		infoCodePlanEntity.setMsgTag(setMsgParamsModel.getMsgTag());
		
		infoCodePlanMapper.update(infoCodePlanEntity);
	}

	
	@Override
	public void codeSendMsg(SendMsgParamsModel sendMsgParamsModel) throws CouponException {
		
		//1、根据批次找到短信模板内容
		CodeMsgDetailEntity cmde = couponInfoMapper.queryByCodePlanId(sendMsgParamsModel.getCodePlanId());		
		
		//2.发送测试短信
		Map<String, Object> params = Maps.newHashMap();
		StringBuffer buffer = new StringBuffer();
		buffer.append(cmde.getCpName()+",券已经到账,");
		buffer.append(cmde.getParam());
		buffer.append("ExampleCode");
		buffer.append("。请于" +DateUtil.getTimeDay(cmde.getDueTime())+"前使用。");
	
		if (StringUtils.isNotBlank(cmde.getMsgTemplate())) {
			buffer.append(cmde.getMsgTemplate());
		}
		
		if (StringUtils.isNotBlank(cmde.getMsgUrl())) {
			buffer.append("。"+cmde.getMsgUrl());
		}
		
		params.put("text",buffer.toString());
		params.put("mobile", sendMsgParamsModel.getTel());
		params.put("channel", cmde.getMsgTag());
		
		String resStr = HttpHelper.httpPostParam(msgURL, params ,"UTF-8");
		JSONObject jsonObject = JSON.parseObject(resStr);
		MsgResponse msgRes = JSONObject.toJavaObject(jsonObject, MsgResponse.class);
		if (!StringUtils.equals("200", msgRes.getResultCode())) {
			logger.error("code码用户"+sendMsgParamsModel.getTel()+"={}",resStr);
		}
		
		//记录测试的发送短消息
		InfoCodeTestEntity infoCodeTestEntity = new InfoCodeTestEntity();
		infoCodeTestEntity.setCodePlanId(sendMsgParamsModel.getCodePlanId());
		infoCodeTestEntity.setTel(sendMsgParamsModel.getTel());
		
		infoCodeTestMapper.save(infoCodeTestEntity);
	}

	@Override
	public List<CouponShopInfoModel> queryCouponShop(String cpId) throws CouponException {
		//商铺信息
		List<CouponShopInfoModel> csims = Lists.newArrayList();
		List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(cpId); 
		if (!CollectionUtils.isEmpty(csies)) {
			csies.forEach(csie ->{
				CouponShopInfoModel csim = new CouponShopInfoModel();
				csim.setInfoId(csie.getInfoId());
				csim.setPlatCode(csie.getPlatCode());
				csim.setShopName(csie.getShopName());
				
				csims.add(csim);
			});
		}
		return csims;
	}

	@Override
	public String queryPackageShop(String packageId) throws CouponException {
		//券包信息查询
		CouponPackageEntity couponPackageEntity = couponPackageMapper.query(packageId);
		String platCode = couponPackageEntity.getPlatCode();
		
		return platCode;
	}

	@Override
	@Transactional
	public void sendPullCoupon(SendCouponPullModel sendCouponPull) throws CouponException {
		//区分类型开始发券（包）
		if (StringUtils.equals(sendCouponPull.getType(), "0")) {//券包
			
			//领取券包要求券包状态是已绑定
			CouponPackageEntity couponPackageEntity = couponPackageMapper.query(sendCouponPull.getCpId());
			if (couponPackageEntity.getStatus()!= CouponPackageStatus.bind.ordinal()) {
				logger.error("券包状态不是已绑定 PackageId={}",sendCouponPull.getCpId());
				throw new CouponException("S41");
			}
			
			//1、判断券包是否绑定优惠券
			List<CouponInfoDetailEntity> cides = couponPackageInfoMapper.queryInfoDetail(sendCouponPull.getCpId());
			if (CollectionUtils.isEmpty(cides)) {
				logger.error("券包未绑定优惠券 PackageId={}",sendCouponPull.getCpId());
				throw new CouponException("S36");
			}
			
			//2、判断券包领取时间
			CouponPackageEntity cpe = couponPackageMapper.query(sendCouponPull.getCpId());
			if (new Date().getTime() >= cpe.getEndTime().getTime()) {
				logger.error("超过券包领取截止时间,券包id={}",sendCouponPull.getCpId());
				throw new CouponException("S38");
			}
			
			//3、校验优惠券是否能领取，插入用户券包
			for (CouponInfoDetailEntity cide:cides) {
				String userTel = sendCouponPull.getUserTel();
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
				
				if (!(count == 0 || count < cie.getLimitNum())) {
					//领取超出限制
					logger.error("当前用户 tel={}",userTel+":领取超出限制");
				}else{
					
					int stock = cie.getStock();//库存
					if (stock <= 0) {
						logger.error("当前库存={}",stock+":优惠券库存不足");
						throw new CouponException("S15");
					}
					
					//更新操作人表  消息推送
					InfoOperatorEntity infoOperatorEntity = new InfoOperatorEntity();
					infoOperatorEntity.setInfoId(sendCouponPull.getCpId());
					infoOperatorEntity.setOperatorName("msgShareAdmin");
					infoOperatorEntity.setOperatorTel(sendCouponPull.getUserTel());
					infoOperatorEntity.setType(InfoOperatorType.msg.ordinal());
					infoOperatorMapper.add(infoOperatorEntity);
					
					//插入用户选择资源表
					InfoUserSourceEntity iuse = new InfoUserSourceEntity();
					iuse.setInfoId(sendCouponPull.getCpId());
					iuse.setType(UserSourceType.msg.ordinal());
					iuse.setWay(UserSourceWay.provide.ordinal());		
					infoUserSourceMapper.add(iuse);
							
					InfoUserSourceDetailEntity iusde = new InfoUserSourceDetailEntity();
					iusde.setUserSourceId(iuse.getId());
					iusde.setTel(sendCouponPull.getCpId());
					iusde.setId(UUID.randomUUID().toString().replace("-", ""));
								
					infoUserSourceDetailMapper.add(iusde);
					
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
							this.codeSend(cie, sendCouponPull.getUserTel(),infoCodeEntity,infoCodePlanEntity);
						}
					}
					
					//插入券包日志表
					UserBagLogEntity uble = new UserBagLogEntity();
					uble.setUserBagId(ube.getId());
					uble.setType(UserBagLogType.get.ordinal());
					userBagLogMapper.add(uble);
					
					//插入优惠券统计发放表
					SendRecordEntity sre = new SendRecordEntity();
					sre.setInfoId(sendCouponPull.getCpId());
					sre.setType(SendType.send.ordinal());
					sre.setUserScope(UserSourceType.msg.ordinal());
					sre.setCount(1);
					sre.setInfoOperatorId(infoOperatorEntity.getId());
					sendRecordMapper.add(sre);
					
				}

			}
		} else { //优惠券
			
			CouponInfoEntity cie = couponInfoMapper.query(sendCouponPull.getCpId());
			int count = userBagMapper.countSingel(sendCouponPull.getCpId(),sendCouponPull.getUserTel());//已领取优惠券数量
			
			if (null == cie) {
	 			logger.error("优惠券不存在，补发异常={}");
	 			throw new CouponException("S09");
			}
			
			if(cie.getStatus() != CouponInfoStatus.send.ordinal()) {
	 			logger.error("优惠券未发布，补发异常={}");
	 			throw new CouponException("S08");
			}
				
			if(new Date().getTime() >= cie.getDueTime().getTime()) {
				logger.error("超过优惠券领取截止时间");
				throw new CouponException("S17");
			}
			
			if (!(count == 0 || count < cie.getLimitNum())) {
				//领取超出限制
				logger.error("当前用户 tel={}",sendCouponPull.getUserTel()+":领取超出限制");
			}else{
			
			//判断库存
			int stock = cie.getStock();
			if (stock <= 0) {
				logger.error("优惠券库存不足，当前库存={}",stock);
				throw new CouponException("S15");
			} else {
				//更新主表的库存
				couponInfoMapper.editStock(sendCouponPull.getCpId());
			}
								
			//即发放状态
			//更新操作人表  消息推送
			InfoOperatorEntity infoOperatorEntity = new InfoOperatorEntity();
			infoOperatorEntity.setInfoId(sendCouponPull.getCpId());
			infoOperatorEntity.setOperatorName("msgShareAdmin");
			infoOperatorEntity.setOperatorTel(sendCouponPull.getUserTel());
			infoOperatorEntity.setType(InfoOperatorType.msg.ordinal());
			infoOperatorMapper.add(infoOperatorEntity);
					
			//插入用户选择资源表
			InfoUserSourceEntity iuse = new InfoUserSourceEntity();
			iuse.setInfoId(sendCouponPull.getCpId());
			iuse.setType(UserSourceType.msg.ordinal());
			iuse.setWay(UserSourceWay.provide.ordinal());		
			infoUserSourceMapper.add(iuse);
					
			InfoUserSourceDetailEntity iusde = new InfoUserSourceDetailEntity();
			iusde.setUserSourceId(iuse.getId());
			iusde.setTel(sendCouponPull.getCpId());
			iusde.setId(UUID.randomUUID().toString().replace("-", ""));
						
			infoUserSourceDetailMapper.add(iusde);
														
			UserBagEntity ube = new UserBagEntity();
			ube.setInfoId(sendCouponPull.getCpId());
			ube.setUserTel(sendCouponPull.getUserTel());
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
					this.codeSend(cie, sendCouponPull.getUserTel(),infoCodeEntity,infoCodePlanEntity);
				}
			}
			//插入券包日志表
			UserBagLogEntity uble = new UserBagLogEntity();
			uble.setUserBagId(ube.getId());
			uble.setType(UserBagLogType.get.ordinal());
						
			userBagLogMapper.add(uble);
						
			
			//插入优惠券统计发放表
			SendRecordEntity sre = new SendRecordEntity();
			sre.setInfoId(sendCouponPull.getCpId());
			sre.setType(SendType.send.ordinal());
			sre.setUserScope(UserSourceType.msg.ordinal());
			sre.setCount(1);
			sre.setInfoOperatorId(infoOperatorEntity.getId());
			
			sendRecordMapper.add(sre);		
		 }
		}
	}

	@Override
	public PageResult<ConvertCodeListResModel> convertCodeList(ConvertCodeListParamsModel convertCodeListParamsModel)
			throws CouponException {
		PageResult<ConvertCodeListResModel> page = new PageResult<ConvertCodeListResModel>();
		Map<String, Object> params = Maps.newHashMap();
	
		//是否分页
		if (!convertCodeListParamsModel.isIspage()) {
			params.put("start",null);
			params.put("limit",null);	
		} else {
			params.put("start", (convertCodeListParamsModel.getPageNo()-1)*convertCodeListParamsModel.getPageSize());
			params.put("limit", convertCodeListParamsModel.getPageSize());	
		}
		
		List<CouponConvertListEntity> ccpes = couponConvertPlanMapper.queryCodeList(params);
		
		List<ConvertCodeListResModel> cclrms = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(ccpes)) {
			
			ccpes.forEach(ccpe ->{
				ConvertCodeListResModel cclrm = new ConvertCodeListResModel();
				cclrm.setConvertPlanId(ccpe.getId());
				cclrm.setCount(ccpe.getCount());
				cclrm.setType(ccpe.getType());
				cclrm.setInfoId(ccpe.getInfoId());
				
				if( ccpe.getType() != null && ccpe.getType() == ConvertTypeEnum.coupon.ordinal()){
					cclrm.setCpName(ccpe.getCpName());
				} else {
					cclrm.setCpName(ccpe.getPackageName());
				}
	
				cclrms.add(cclrm);
			});	
		}
		page.setData(cclrms);
		
		//计算分页
		int total =  couponConvertPlanMapper.queryCodeListCount();
		page.setTotalResults(total);
		if (convertCodeListParamsModel.isIspage()) {
			page.setHasNext(false);
		} else {
			int totalPage = (total + convertCodeListParamsModel.getPageSize() - 1) / convertCodeListParamsModel.getPageSize();
			page.setHasNext(convertCodeListParamsModel.getPageNo()+1 <=  totalPage);
		}
		return page;	
	}

	@Override
	@Transactional
	public void bindConvertCode(ConvertCodeModel convertCodeModel) throws CouponException {
		
		//1、绑定优惠券或者券包
		couponConvertPlanMapper.updateInfoId
			(convertCodeModel.getConvertPlanId(), convertCodeModel.getInfoId(), convertCodeModel.getType());	
		
	}
	
	@Override
	public CouponInfoModel convertCodeDetail(String code) throws CouponException {
		
		//关联的优惠券详情
		CouponInfoModel cim = new CouponInfoModel();
		CouponInfoDetailEntity cie = couponConvertCodeMapper.queryCoupon(code);

		cim.setCpId(cie.getCpId());
		cim.setCpName(cie.getCpName());
		cim.setPreWay(cie.getPreWay());
		cim.setCheckNo(cie.getCheckNo());
		cim.setStartTime(cie.getStartTime());
		cim.setEndTime(cie.getEndTime());
		cim.setCirculation(cie.getCirculation());
		cim.setLimitNum(cie.getLimitNum());
		cim.setStock(cie.getStock());
		cim.setType(cie.getType());
		cim.setBeginTime(cie.getBeginTime());
		cim.setDueTime(cie.getDueTime());
		cim.setStatus(cie.getStatus());
		cim.setIsCode(cie.getIsCode());
		cim.setIsBindCode(cie.getIsBindCode());
		cim.setCostType(cie.getCostType());
		cim.setRule(cie.getRule());
		cim.setMemo(cie.getMemo());
		cim.setCreateTime(cie.getCreateTime());
		cim.setUpdateTime(cie.getUpdateTime());
		cim.setRichText(cie.getRichText());
		cim.setUrl(cie.getUrl());
		cim.setUrlButton(cie.getUrlButton());
		cim.setUserSourceType(cie.getUserSourceType());
		
		//查询卡券使用条件、查询卡券面额
		InfoConditionModel infoConditionModel = new InfoConditionModel();
		infoConditionModel.setType(cie.getConditionType());
		infoConditionModel.setPar((cie.getPar()));
		infoConditionModel.setStartFee(cie.getStartFee());
		infoConditionModel.setEndFee(cie.getEndFee());
		cim.setInfoCondition(infoConditionModel);
			
		//查询logo
		List<LogoModel> logos = Lists.newArrayList();
		List<LogoEntity> logoEntitys = logoMapper.select(cie.getCpId()); 
		if (!CollectionUtils.isEmpty(logoEntitys)) {
			logoEntitys.forEach(logoEntity ->{
				LogoModel logo = new LogoModel();
				logo.setId(logoEntity.getId());
				logo.setInfoId(logoEntity.getInfoId());
				logo.setLogo(logoEntity.getLogo());
				logo.setType(logoEntity.getType());
				
				logos.add(logo);
			});
			cim.setLogos(logos);
		}
		
		//插入商铺信息表
		List<CouponShopInfoModel> csims = Lists.newArrayList();
		List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(cie.getCpId()); 
		if (!CollectionUtils.isEmpty(csies)) {
			csies.forEach(csie ->{
				CouponShopInfoModel csim = new CouponShopInfoModel();
				csim.setInfoId(csie.getInfoId());
				csim.setPlatCode(csie.getPlatCode());
				csim.setShopName(csie.getShopName());
				
				csims.add(csim);
			});
			cim.setCouponShopInfoModels(csims);
		}	

		return cim;
	}

	@Override
	@Transactional
	public void unBindConvertCode(String convertPlanId, String infoId) throws CouponException {
	
		//1、首先查询一下type
		CouponConvertPlanEntity ccpe = couponConvertPlanMapper.query(convertPlanId);
		
		if (ccpe.getType() == ConvertTypeEnum.coupon.ordinal()) {//解除绑定优惠券
			
			CouponInfoEntity cife = couponInfoMapper.query(infoId);	
							
			if (null == cife) {
		 		logger.error("优惠券不存在，cpId={}"+ infoId);
		 		throw new CouponException("S03");	
			}
			
			if (cife.getStatus() == CouponInfoStatus.send.ordinal()) {
		 		logger.error("优惠券已发布，取消绑定异常={}");
		 		throw new CouponException("S29");
			}	
		}
					
		//2、取消绑定优惠券、券包
		couponConvertPlanMapper.updateInfoId(convertPlanId, null ,null);	
	}

	@Override
	@Transactional
	public void deleteConvertCode(String convertPlanId) throws CouponException {
		
		//1、查询兑换码详情，兑换优惠券的检验，兑换券包的不校验。
		CouponConvertPlanEntity ccpe = couponConvertPlanMapper.query(convertPlanId);
	
		if ( StringUtils.isEmpty(ccpe.getInfoId()) ) {//兑换码未绑定券、券包，直接级联删除
			couponConvertPlanMapper.delete(convertPlanId);
			couponConvertCodeMapper.delete(convertPlanId);
		} else if (ccpe.getType() == ConvertTypeEnum.coupon.ordinal()) {//删除优惠券兑换码
		
			//已绑定优惠券，判断是否为发布状态
			CouponInfoEntity cife = couponInfoMapper.query(ccpe.getInfoId());	
			
			if (null == cife) {
		 		logger.error("优惠券不存在，cpId={}"+ ccpe.getInfoId());
		 		throw new CouponException("S03");	
			}
			
			if (cife.getStatus() == CouponInfoStatus.send.ordinal()) {
		 		logger.error("优惠券已发布，取消绑定异常={}");
		 		throw new CouponException("S29");
			}
		} else {//删除券包兑换码
			//是否有人已经领取
			List<CouponConvertUserEntity> ccue =  couponConvertUserMapper.queryCodeUser(convertPlanId);
			if (!CollectionUtils.isEmpty(ccue)) {
		 		logger.error("兑换码已被使用，删除异常={}");
		 		throw new CouponException("S47");
			}
		}

			//级联删除
			couponConvertPlanMapper.delete(convertPlanId);
			couponConvertCodeMapper.delete(convertPlanId);
		}
		

	@Override
	public CouponPackageModel convertPackageDetail(String code) throws CouponException {
		
		CouponPackageEntity ple = couponConvertCodeMapper.queryPackage(code);
		
		CouponPackageModel couponPackageModel = new CouponPackageModel();
		couponPackageModel.setPackageId(ple.getId());
		couponPackageModel.setName(ple.getName());
		couponPackageModel.setStatus(ple.getStatus());
		couponPackageModel.setStartTime(ple.getStartTime());
		couponPackageModel.setEndTime(ple.getEndTime());
		couponPackageModel.setPlatCode(ple.getPlatCode());
		couponPackageModel.setIsShare(ple.getIsShare());
		couponPackageModel.setShopName(ple.getShopName());
		
		//券包logo
		List<LogoModel> logos = Lists.newArrayList();
		List<LogoEntity> logoEntitys = logoMapper.select(ple.getId()); 
		if (!CollectionUtils.isEmpty(logoEntitys)) {
			logoEntitys.forEach(logoEntity ->{
				LogoModel logo = new LogoModel();
				logo.setId(logoEntity.getId());
				logo.setInfoId(logoEntity.getInfoId());
				logo.setLogo(logoEntity.getLogo());
				logo.setType(logoEntity.getType());
				
				logos.add(logo);
			});
			couponPackageModel.setLogos(logos);
		}
		
		//关联的优惠券详情
		List<CouponInfoDetailEntity> cies = couponPackageInfoMapper.queryInfoDetail(ple.getId());
		List<CouponInfoModel> couponInfoModels = Lists.newArrayList();
		
		if (!CollectionUtils.isEmpty(cies)) {
			
			cies.forEach(cie -> {
				
				CouponInfoModel cim = new CouponInfoModel();
				
				cim.setCpId(cie.getCpId());
				cim.setCpName(cie.getCpName());
				cim.setPreWay(cie.getPreWay());
				cim.setCheckNo(cie.getCheckNo());
				cim.setStartTime(cie.getStartTime());
				cim.setEndTime(cie.getEndTime());
				cim.setCirculation(cie.getCirculation());
				cim.setLimitNum(cie.getLimitNum());
				cim.setStock(cie.getStock());
				cim.setType(cie.getType());
				cim.setBeginTime(cie.getBeginTime());
				cim.setDueTime(cie.getDueTime());
				cim.setStatus(cie.getStatus());
				cim.setIsCode(cie.getIsCode());
				cim.setIsBindCode(cie.getIsBindCode());
				cim.setCostType(cie.getCostType());
				cim.setRule(cie.getRule());
				cim.setMemo(cie.getMemo());
				cim.setCreateTime(cie.getCreateTime());
				cim.setUpdateTime(cie.getUpdateTime());
				cim.setRichText(cie.getRichText());
				cim.setUrl(cie.getUrl());
				cim.setUrlButton(cie.getUrlButton());
				cim.setUserSourceType(cie.getUserSourceType());
				
				//查询卡券使用条件、查询卡券面额
				InfoConditionModel infoConditionModel = new InfoConditionModel();
				infoConditionModel.setType(cie.getConditionType());
				infoConditionModel.setPar((cie.getPar()));
				infoConditionModel.setStartFee(cie.getStartFee());
				infoConditionModel.setEndFee(cie.getEndFee());
				cim.setInfoCondition(infoConditionModel);
			
				//插入商铺信息表
				List<CouponShopInfoModel> csims = Lists.newArrayList();
				List<CouponShopInfoEntity> csies = couponShopInfoMapper.select(cie.getCpId()); 
				if (!CollectionUtils.isEmpty(csies)) {
					csies.forEach(csie ->{
						CouponShopInfoModel csim = new CouponShopInfoModel();
						csim.setInfoId(csie.getInfoId());
						csim.setPlatCode(csie.getPlatCode());
						csim.setShopName(csie.getShopName());
						
						csims.add(csim);
					});
					cim.setCouponShopInfoModels(csims);
				}	
				couponInfoModels.add(cim);
			});
			couponPackageModel.setCouponInfoModels(couponInfoModels);
		}
		return couponPackageModel;
	}
	
}




