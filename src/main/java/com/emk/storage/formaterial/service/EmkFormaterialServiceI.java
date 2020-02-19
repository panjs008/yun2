package com.emk.storage.formaterial.service;
import com.emk.storage.formaterial.entity.EmkFormaterialEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkFormaterialServiceI extends CommonService{
	
 	public void delete(EmkFormaterialEntity entity) throws Exception;
 	
 	public Serializable save(EmkFormaterialEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkFormaterialEntity entity) throws Exception;
 	
}
