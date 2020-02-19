package com.emk.bound.storageconnact.service;
import com.emk.bound.storageconnact.entity.EmkStorageConnactEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkStorageConnactServiceI extends CommonService{
	
 	public void delete(EmkStorageConnactEntity entity) throws Exception;
 	
 	public Serializable save(EmkStorageConnactEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkStorageConnactEntity entity) throws Exception;
 	
}
