package com.emk.storage.outorder.service;
import com.emk.storage.outorder.entity.EmkOutOrderEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkOutOrderServiceI extends CommonService{
	
 	public void delete(EmkOutOrderEntity entity) throws Exception;
 	
 	public Serializable save(EmkOutOrderEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkOutOrderEntity entity) throws Exception;
 	
}
