package com.emk.email.esendmodel.service;
import com.emk.email.esendmodel.entity.ESendModelEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ESendModelServiceI extends CommonService{
	
 	public void delete(ESendModelEntity entity) throws Exception;
 	
 	public Serializable save(ESendModelEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ESendModelEntity entity) throws Exception;
 	
}
