package com.emk.product.productprice.service;
import com.emk.product.productprice.entity.EmkProductPriceEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkProductPriceServiceI extends CommonService{
	
 	public void delete(EmkProductPriceEntity entity) throws Exception;
 	
 	public Serializable save(EmkProductPriceEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkProductPriceEntity entity) throws Exception;
 	
}
