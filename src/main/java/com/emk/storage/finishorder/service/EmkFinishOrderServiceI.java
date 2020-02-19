package com.emk.storage.finishorder.service;
import com.emk.storage.finishorder.entity.EmkFinishOrderEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkFinishOrderServiceI extends CommonService{
	
 	public void delete(EmkFinishOrderEntity entity) throws Exception;
 	
 	public Serializable save(EmkFinishOrderEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkFinishOrderEntity entity) throws Exception;
 	
}
