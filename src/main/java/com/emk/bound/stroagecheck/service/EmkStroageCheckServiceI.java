package com.emk.bound.stroagecheck.service;
import com.emk.bound.stroagecheck.entity.EmkStroageCheckEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkStroageCheckServiceI extends CommonService{
	
 	public void delete(EmkStroageCheckEntity entity) throws Exception;
 	
 	public Serializable save(EmkStroageCheckEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkStroageCheckEntity entity) throws Exception;
 	
}
