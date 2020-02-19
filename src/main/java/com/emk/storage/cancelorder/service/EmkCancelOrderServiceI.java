package com.emk.storage.cancelorder.service;
import com.emk.storage.cancelorder.entity.EmkCancelOrderEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkCancelOrderServiceI extends CommonService{
	
 	public void delete(EmkCancelOrderEntity entity) throws Exception;
 	
 	public Serializable save(EmkCancelOrderEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkCancelOrderEntity entity) throws Exception;
 	
}
