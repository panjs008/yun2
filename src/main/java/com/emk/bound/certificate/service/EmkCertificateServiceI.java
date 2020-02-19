package com.emk.bound.certificate.service;
import com.emk.bound.certificate.entity.EmkCertificateEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkCertificateServiceI extends CommonService{
	
 	public void delete(EmkCertificateEntity entity) throws Exception;
 	
 	public Serializable save(EmkCertificateEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkCertificateEntity entity) throws Exception;
 	
}
