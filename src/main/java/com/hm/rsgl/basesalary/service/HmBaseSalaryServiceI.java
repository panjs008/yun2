package com.hm.rsgl.basesalary.service;
import com.hm.rsgl.basesalary.entity.HmBaseSalaryEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface HmBaseSalaryServiceI extends CommonService{
	
 	public void delete(HmBaseSalaryEntity entity) throws Exception;
 	
 	public Serializable save(HmBaseSalaryEntity entity) throws Exception;
 	
 	public void saveOrUpdate(HmBaseSalaryEntity entity) throws Exception;
 	
}
