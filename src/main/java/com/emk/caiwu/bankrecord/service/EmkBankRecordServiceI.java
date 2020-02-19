package com.emk.caiwu.bankrecord.service;
import com.emk.caiwu.bankrecord.entity.EmkBankRecordEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkBankRecordServiceI extends CommonService{
	
 	public void delete(EmkBankRecordEntity entity) throws Exception;
 	
 	public Serializable save(EmkBankRecordEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkBankRecordEntity entity) throws Exception;
 	
}
