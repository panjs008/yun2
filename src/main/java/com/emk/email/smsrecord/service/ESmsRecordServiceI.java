package com.emk.email.smsrecord.service;
import com.emk.email.smsrecord.entity.ESmsRecordEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ESmsRecordServiceI extends CommonService{
	
 	public void delete(ESmsRecordEntity entity) throws Exception;
 	
 	public Serializable save(ESmsRecordEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ESmsRecordEntity entity) throws Exception;
 	
}
