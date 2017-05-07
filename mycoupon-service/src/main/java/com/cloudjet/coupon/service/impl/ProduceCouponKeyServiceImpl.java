package com.cloudjet.coupon.service.impl;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudjet.coupon.entity.CouponConvertCodeEntity;
import com.cloudjet.coupon.entity.CouponConvertPlanEntity;
import com.cloudjet.coupon.exception.CouponException;
import com.cloudjet.coupon.mapper.CouponConvertCodeMapper;
import com.cloudjet.coupon.mapper.CouponConvertPlanMapper;
import com.cloudjet.coupon.model.CoupnConvertDetailModel.ConvertCodeStatus;
import com.cloudjet.coupon.service.ProduceCouponKeyService;
import com.cloudjet.coupon.util.CDKeyUtils;
import com.google.common.collect.Sets;

@Service
public class ProduceCouponKeyServiceImpl implements ProduceCouponKeyService {

	private static final Logger logger = LoggerFactory.getLogger(ProduceCouponKeyServiceImpl.class);

	@Autowired
	private CouponConvertCodeMapper couponConvertCodeMapper;

	@Autowired
	private CouponConvertPlanMapper couponConvertPlanMapper;

	@Override
	@Transactional
	public void createConvertCode(int count) throws CouponException{

		// 上限为十万条记录
		if (count > 100000) {
			logger.error("count上限为十万");
			throw new CouponException("S43");
		}

		// 1、插入到批次表
		CouponConvertPlanEntity ccpe = new CouponConvertPlanEntity();
		ccpe.setCount(count);
		couponConvertPlanMapper.save(ccpe);

		// 2、批量插入CDKeyUtils生成的券码，全局唯一
		CouponConvertCodeEntity ccce = null;
		Set<String> dbCodes = Sets.newHashSet();

		while (dbCodes.size() < count) {

			String code = CDKeyUtils.getCDKey();
			ccce = new CouponConvertCodeEntity();
			ccce.setId(UUID.randomUUID().toString().replace("-", ""));
			ccce.setCode(code);
			ccce.setStatus(ConvertCodeStatus.init.ordinal());
			ccce.setConvertId(ccpe.getId());

			try {
				// 插入相同的code，结束当次循环。
				couponConvertCodeMapper.save(ccce);
			} catch (Exception e) {
				continue;
			}
			dbCodes.add(code);
		}

	}

}
