package com.emk.storage.formaterialother.service;
import com.emk.storage.formaterialother.entity.EmkFormaterialOtherEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkFormaterialOtherServiceI extends CommonService{
	
 	public void delete(EmkFormaterialOtherEntity entity) throws Exception;
 	
 	public Serializable save(EmkFormaterialOtherEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkFormaterialOtherEntity entity) throws Exception;
 	
}
