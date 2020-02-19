package com.emk.email.esendht.service;
import com.emk.email.esendht.entity.ESendHtEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ESendHtServiceI extends CommonService{
	
 	public void delete(ESendHtEntity entity) throws Exception;
 	
 	public Serializable save(ESendHtEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ESendHtEntity entity) throws Exception;
 	
}
