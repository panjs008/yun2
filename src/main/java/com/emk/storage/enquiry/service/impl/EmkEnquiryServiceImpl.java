package com.emk.storage.enquiry.service.impl;

import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.enquiry.service.EmkEnquiryServiceI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("emkEnquiryService")
@Transactional
public class EmkEnquiryServiceImpl
        extends CommonServiceImpl
        implements EmkEnquiryServiceI {
    public void delete(EmkEnquiryEntity entity)
            throws Exception {
        super.delete(entity);

        doDelBus(entity);
    }

    public Serializable save(EmkEnquiryEntity entity)
            throws Exception {
        Serializable t = super.save(entity);

        doAddBus(entity);
        return t;
    }

    public void saveOrUpdate(EmkEnquiryEntity entity)
            throws Exception {
        super.saveOrUpdate(entity);

        doUpdateBus(entity);
    }

    private void doAddBus(EmkEnquiryEntity t)
            throws Exception {
    }

    private void doUpdateBus(EmkEnquiryEntity t)
            throws Exception {
    }

    private void doDelBus(EmkEnquiryEntity t)
            throws Exception {
    }

    private Map<String, Object> populationMap(EmkEnquiryEntity t) {
        Map<String, Object> map = new HashMap();
        map.put("id", t.getId());
        map.put("create_name", t.getCreateName());
        map.put("create_by", t.getCreateBy());
        map.put("create_date", t.getCreateDate());
        map.put("sys_org_code", t.getSysOrgCode());
        map.put("enquiry_no", t.getEnquiryNo());
        map.put("kd_date", t.getKdDate());
        map.put("businesser", t.getBusinesser());
        map.put("businesser_name", t.getBusinesserName());
        map.put("cus_num", t.getCusNum());
        map.put("cus_name", t.getCusName());
        map.put("gyzl", t.getGyzl());
        map.put("pro_type", t.getProType());
        map.put("pro_type_name", t.getProTypeName());
        map.put("custom_sample_url", t.getCustomSampleUrl());
        map.put("custom_sample", t.getCustomSample());
        map.put("sample_no", t.getSampleNo());
        map.put("sample_no_desc", t.getSampleNoDesc());

        map.put("ys_date", t.getYsDate());
        map.put("level_days", t.getLevelDays());
        map.put("remark", t.getRemark());
        map.put("tracer", t.getTracer());
        map.put("tracer_name", t.getTracerName());
        return map;
    }

    public String replaceVal(String sql, EmkEnquiryEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{sys_org_code}", String.valueOf(t.getSysOrgCode()));
        sql = sql.replace("#{enquiry_no}", String.valueOf(t.getEnquiryNo()));
        sql = sql.replace("#{kd_date}", String.valueOf(t.getKdDate()));
        sql = sql.replace("#{businesser}", String.valueOf(t.getBusinesser()));
        sql = sql.replace("#{businesser_name}", String.valueOf(t.getBusinesserName()));
        sql = sql.replace("#{cus_num}", String.valueOf(t.getCusNum()));
        sql = sql.replace("#{cus_name}", String.valueOf(t.getCusName()));
        sql = sql.replace("#{gyzl}", String.valueOf(t.getGyzl()));
        sql = sql.replace("#{pro_type}", String.valueOf(t.getProType()));
        sql = sql.replace("#{pro_type_name}", String.valueOf(t.getProTypeName()));
        sql = sql.replace("#{custom_sample_url}", String.valueOf(t.getCustomSampleUrl()));
        sql = sql.replace("#{custom_sample}", String.valueOf(t.getCustomSample()));
        sql = sql.replace("#{sample_no}", String.valueOf(t.getSampleNo()));
        sql = sql.replace("#{sample_no_desc}", String.valueOf(t.getSampleNoDesc()));

        sql = sql.replace("#{ys_date}", String.valueOf(t.getYsDate()));
        sql = sql.replace("#{level_days}", String.valueOf(t.getLevelDays()));
        sql = sql.replace("#{remark}", String.valueOf(t.getRemark()));
        sql = sql.replace("#{tracer}", String.valueOf(t.getTracer()));
        sql = sql.replace("#{tracer_name}", String.valueOf(t.getTracerName()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    private void executeJavaExtend(String cgJavaType, String cgJavaValue, Map<String, Object> data)
            throws Exception {
        if (StringUtil.isNotEmpty(cgJavaValue)) {
            Object obj = null;
            try {
                if ("class".equals(cgJavaType)) {
                    obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
                } else if ("spring".equals(cgJavaType)) {
                    obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
                }
                if ((obj instanceof CgformEnhanceJavaInter)) {
                    CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
                    javaInter.execute("emk_enquiry", data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("执行JAVA增强出现异常！");
            }
        }
    }
}
