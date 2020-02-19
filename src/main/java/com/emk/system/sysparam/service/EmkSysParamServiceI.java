package com.emk.system.sysparam.service;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkSysParamServiceI extends CommonService{
	
 	public void delete(EmkSysParamEntity entity) throws Exception;
 	
 	public Serializable save(EmkSysParamEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkSysParamEntity entity) throws Exception;
 	
}
