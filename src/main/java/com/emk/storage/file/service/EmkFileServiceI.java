package com.emk.storage.file.service;
import com.emk.storage.file.entity.EmkFileEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkFileServiceI extends CommonService{
	
 	public void delete(EmkFileEntity entity) throws Exception;
 	
 	public Serializable save(EmkFileEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkFileEntity entity) throws Exception;
 	
}
