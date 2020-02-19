package com.emk.storage.payover.service;
import com.emk.storage.payover.entity.EmkPayOverEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkPayOverServiceI extends CommonService{
	
 	public void delete(EmkPayOverEntity entity) throws Exception;
 	
 	public Serializable save(EmkPayOverEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkPayOverEntity entity) throws Exception;
 	
}
