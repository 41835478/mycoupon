package com.cloudjet.coupon.service;

import java.util.List;

import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.model.BindCpModel;
import com.cloudjet.coupon.model.CodeListParamsModel;
import com.cloudjet.coupon.model.ConvertCodeModel;
import com.cloudjet.coupon.model.CouponCopyParamsModel;
import com.cloudjet.coupon.model.CouponInfoModel;
import com.cloudjet.coupon.model.CouponPackageModel;
import com.cloudjet.coupon.model.CouponPageModel;
import com.cloudjet.coupon.model.CouponShopInfoModel;
import com.cloudjet.coupon.model.InfoCodeModel;
import com.cloudjet.coupon.model.InfoOperatorModel;
import com.cloudjet.coupon.model.PackageEditModel;
import com.cloudjet.coupon.model.PackageInitModel;
import com.cloudjet.coupon.model.PackageListParamsModel;
import com.cloudjet.coupon.model.PageResult;
import com.cloudjet.coupon.model.UserSourceModel;
import com.cloudjet.coupon.request.ConvertCodeListParamsModel;
import com.cloudjet.coupon.request.SelectCouponParamsModel;
import com.cloudjet.coupon.request.SendCouponPullModel;
import com.cloudjet.coupon.request.SendMsgParamsModel;
import com.cloudjet.coupon.request.SetMsgParamsModel;
import com.cloudjet.coupon.request.UserBagParamsModel;
import com.cloudjet.coupon.res.model.CodeListResModel;
import com.cloudjet.coupon.res.model.CodeMsgModel;
import com.cloudjet.coupon.res.model.ConvertCodeListResModel;
import com.cloudjet.coupon.res.model.UserBagResModel;

public interface CouponInfoService {
	
	
	/**创建优惠券
	 * @param CouponInfoModel couponInfo
	 * @return String cpId
	 * @throws CouponException 
	 */
	String  create(CouponInfoModel couponInfo) throws CouponException;
	
	/**修改优惠券  发布后修改有限制
	 * @param String cpId
	 * @return
	 */
    void  edit(CouponInfoModel couponInfo)throws CouponException;
    
    /**删除优惠券   
	 * @param String cpId
	 * @param infoOperator
	 * @return
	 */
    void  delete(String cpId,InfoOperatorModel infoOperator) throws CouponException;
    
    
    /**发放优惠券
	 * @param String cpId
	 * @return
	 */
    void  send(UserSourceModel userSource) throws CouponException;
    
    /**发放优惠券 订阅消息
	 * @param String cpId
	 * @return
	 */
    void  sendMsg(UserSourceModel userSource) throws CouponException;
    
    /**补发优惠券
	 * @param UserSourceModel userSource
	 * @return
	 */
    void  reissue(UserSourceModel userSource) throws CouponException;
    
    
    /** 下架优惠券
     *  @param String cpId
     *  @param int status
     *  @param infoOperator  操作人信息
     * */
   void freeze(String cpId,InfoOperatorModel infoOperator) throws CouponException; 
    
   /** 查询出优惠券列表
    *  @return 
    */
   PageResult<CouponInfoModel> queryList(CouponPageModel couponPageModel) throws CouponException;
   
   /** 查询出优惠券详情
    * @param cpId
    *  @return 
    */
   CouponInfoModel queryDetail(String cpId) throws CouponException;
   
   /** 查询出未发布的卡券，
    *  @return
    */
   List<CouponInfoModel> queryCheckedList(SelectCouponParamsModel selectCouponParamsModel) throws CouponException;
      
   /**
    * 优惠券绑定code码。优惠券必须是用户领取类型
    * @param BindCpModel
    * */
   void bindingCoupon(BindCpModel bindCpModel) throws CouponException;
   
   /**
    * 导入code码
    * @param InfoCodeModel
    * */
   void importCode(InfoCodeModel infoCodeModel) throws CouponException;
   
   /**
    * 查看券码批次列表
    * @param codeListParamsModel
    * @return
    * @throws CouponException
    */
   PageResult<CodeListResModel> queryCodeList(CodeListParamsModel codeListParamsModel) throws CouponException;
   
   /**
    * 券码取消绑定优惠券，不能取消已绑定的优惠券
    * @param codePlanId
    * @param platCode
    * @throws CouponException
    */
   void unbindingCoupon(String codePlanId,String platCode) throws CouponException;
   
   /**
    * 删除券码,已绑定不能删除
    * @param codePlanId
    * @param platCode
    * @throws CouponException
    */
   void deleteCode(String codePlanId,String platCode) throws CouponException;
   
   /**
    * 创建券包
    * @param packageInitModel
    * @throws CouponException
    */
   void createPackage(PackageInitModel packageInitModel) throws CouponException;
   
   /** 
    * 查看券包列表
    * @param packageListParamsModel
    * @return
    * @throws CouponException
    */
   PageResult<CouponPackageModel> queryPackageList(PackageListParamsModel packageListParamsModel) throws CouponException;
   
   /**
    * 删除券包
    * @param packageId
    * @throws CouponException
    */
   void deletePackage(String packageId) throws CouponException;
      
   /**
    * 编辑券包
    * @param packageEditModel
    * @throws CouponException
    */
   void editPackageCoupon(PackageEditModel packageEditModel) throws CouponException;
   
  
   /**
    * 冻结券包
    * @param packageId
    * @throws CouponException
    */
   void freezePackageCoupon(String packageId) throws CouponException;
   
   /**
    * 查看券包详情
    * @param packageId
    * @return
    * @throws CouponException
    */
   CouponPackageModel packageDetail(String packageId) throws CouponException;
   
   /**
    * 是否发送短信
    * @param codePlanId
    * @param isMsg
    * @throws CouponException
    */
   void isSendMsg(String codePlanId,Integer isMsg) throws CouponException;
   
   /**
    * 复制一张优惠券
    * @param CouponCopyParamsModel
    * @return
    * @throws CouponException
    */
   String copyCreate(CouponCopyParamsModel CouponCopyParamsModel)throws CouponException;

   /**
    * 查询用户券包信息
    * @param userBagParamsModel
    * @return
    * @throws CouponException
    */
   List<UserBagResModel> queryUserBag(UserBagParamsModel userBagParamsModel) throws CouponException;
   
   /**
    * 查询券码短信详情
    * @param codePlanId
    * @return
    * @throws CouponException
    */
   CodeMsgModel queryCodeMsg(String codePlanId) throws CouponException;
   
   /**
    * 短信内容模板设置
    * @param setMsgParamsModel
    * @throws CouponException
    */
   void setMsg(SetMsgParamsModel setMsgParamsModel) throws CouponException;
   
   /**
    * 测试发送短信
    * @param sendMsgParamsModel
    * @throws CouponException
    */
   void codeSendMsg(SendMsgParamsModel sendMsgParamsModel) throws CouponException;
   
   /**
    * 根据优惠券Id查询店铺
    * @param cpId
    * @return
    * @throws CouponException
    */
   List<CouponShopInfoModel> queryCouponShop(String cpId) throws CouponException;
   
   /**
    * 根据券包id查询店铺
    * @param packageId
    * @return
    * @throws CouponException
    */
   String queryPackageShop(String packageId) throws CouponException;
   
   /**
    * 单个用户发送优惠券或者券包
    * @param sendCouponPull
    * @throws CouponException
    */
   void sendPullCoupon(SendCouponPullModel sendCouponPull) throws CouponException;
   
   /**
    * 查看兑换码批次列表
    * @param convertCodeListParamsModel
    * @return
    * @throws CouponException
    */
   PageResult<ConvertCodeListResModel> convertCodeList(ConvertCodeListParamsModel convertCodeListParamsModel) throws CouponException;
	
   /**
    * 兑换码绑定优惠券或券包
    * @param convertCodeModel
    * @throws CouponException
    */
   void bindConvertCode(ConvertCodeModel convertCodeModel) throws CouponException;
   
   /**
    * 兑换码取消绑定券、券包
    * @param convertPlanId
    * @param infoId
    * @throws CouponException
    */
   void unBindConvertCode(String convertPlanId, String infoId) throws CouponException;
   
   /**
    * 兑换码查询优惠券详情
    * @param code
    * @return
    * @throws CouponException
    */
   CouponInfoModel convertCodeDetail(String code) throws CouponException;
   
   /**
    * 兑换码查看券包详情
    * @param packageId
    * @return
    * @throws CouponException
    */
   CouponPackageModel convertPackageDetail(String code) throws CouponException;
   
   /**
    * 删除兑换码
    * @param convertPlanId
    * @throws CouponException
    */
   void deleteConvertCode(String convertPlanId) throws CouponException;

}
