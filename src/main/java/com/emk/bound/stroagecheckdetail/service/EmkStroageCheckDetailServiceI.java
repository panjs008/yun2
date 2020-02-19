package com.emk.bound.stroagecheckdetail.service;
import com.emk.bound.stroagecheckdetail.entity.EmkStroageCheckDetailEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkStroageCheckDetailServiceI extends CommonService{
	
 	public void delete(EmkStroageCheckDetailEntity entity) throws Exception;
 	
 	public Serializable save(EmkStroageCheckDetailEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkStroageCheckDetailEntity entity) throws Exception;
 	
}
