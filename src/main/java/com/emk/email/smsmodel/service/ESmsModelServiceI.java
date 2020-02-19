package com.emk.email.smsmodel.service;
import com.emk.email.smsmodel.entity.ESmsModelEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ESmsModelServiceI extends CommonService{
	
 	public void delete(ESmsModelEntity entity) throws Exception;
 	
 	public Serializable save(ESmsModelEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ESmsModelEntity entity) throws Exception;
 	
}
