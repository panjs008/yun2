package com.emk.bound.muse.service;
import com.emk.bound.muse.entity.EmkMUseEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkMUseServiceI extends CommonService{
	
 	public void delete(EmkMUseEntity entity) throws Exception;
 	
 	public Serializable save(EmkMUseEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkMUseEntity entity) throws Exception;
 	
}
