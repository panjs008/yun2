package com.hm.rsgl.staff.service;
import com.hm.rsgl.staff.entity.HmStaffEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface HmStaffServiceI extends CommonService{
	
 	public void delete(HmStaffEntity entity) throws Exception;
 	
 	public Serializable save(HmStaffEntity entity) throws Exception;
 	
 	public void saveOrUpdate(HmStaffEntity entity) throws Exception;
 	
}
