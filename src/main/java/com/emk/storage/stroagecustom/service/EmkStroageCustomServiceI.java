package com.emk.storage.stroagecustom.service;
import com.emk.storage.stroagecustom.entity.EmkStroageCustomEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkStroageCustomServiceI extends CommonService{
	
 	public void delete(EmkStroageCustomEntity entity) throws Exception;
 	
 	public Serializable save(EmkStroageCustomEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkStroageCustomEntity entity) throws Exception;
 	
}
