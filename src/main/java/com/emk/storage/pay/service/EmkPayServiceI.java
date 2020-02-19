package com.emk.storage.pay.service;
import com.emk.storage.pay.entity.EmkPayEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkPayServiceI extends CommonService{
	
 	public void delete(EmkPayEntity entity) throws Exception;
 	
 	public Serializable save(EmkPayEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkPayEntity entity) throws Exception;
 	
}
