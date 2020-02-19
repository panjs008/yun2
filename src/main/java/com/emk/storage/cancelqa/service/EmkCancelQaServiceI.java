package com.emk.storage.cancelqa.service;
import com.emk.storage.cancelqa.entity.EmkCancelQaEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkCancelQaServiceI extends CommonService{
	
 	public void delete(EmkCancelQaEntity entity) throws Exception;
 	
 	public Serializable save(EmkCancelQaEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkCancelQaEntity entity) throws Exception;
 	
}
