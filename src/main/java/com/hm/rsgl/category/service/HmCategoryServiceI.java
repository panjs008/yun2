package com.hm.rsgl.category.service;
import com.hm.rsgl.category.entity.HmCategoryEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface HmCategoryServiceI extends CommonService{
	
 	public void delete(HmCategoryEntity entity) throws Exception;
 	
 	public Serializable save(HmCategoryEntity entity) throws Exception;
 	
 	public void saveOrUpdate(HmCategoryEntity entity) throws Exception;
 	
}
