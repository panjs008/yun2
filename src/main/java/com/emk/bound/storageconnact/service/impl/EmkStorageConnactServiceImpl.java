package com.emk.bound.storageconnact.service.impl;
import com.emk.bound.storageconnact.service.EmkStorageConnactServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.emk.bound.storageconnact.entity.EmkStorageConnactEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("emkStorageConnactService")
@Transactional
public class EmkStorageConnactServiceImpl extends CommonServiceImpl implements EmkStorageConnactServiceI {

	
 	public void delete(EmkStorageConnactEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(EmkStorageConnactEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(EmkStorageConnactEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(EmkStorageConnactEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(EmkStorageConnactEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(EmkStorageConnactEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(EmkStorageConnactEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("sys_org_code", t.getSysOrgCode());
		map.put("depart_id", t.getDepartId());
		map.put("org_code", t.getOrgCode());
		map.put("in_storage_id", t.getInStorageId());
		map.put("in_storage_name", t.getInStorageName());
		map.put("out_storage_id", t.getOutStorageId());
		map.put("out_storage_name", t.getOutStorageName());
		map.put("type", t.getType());
		map.put("connact_no", t.getConnactNo());
		map.put("make_date", t.getMakeDate());
		map.put("pro_zn_name", t.getProZnNameB());
		map.put("pro_id", t.getProId());
		map.put("brand", t.getBrandB());
		map.put("total", t.getTotal());
		map.put("price", t.getPrice());
		map.put("remark", t.getRemark());
		map.put("user_id", t.getUserId());
		map.put("real_name", t.getRealName());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,EmkStorageConnactEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{depart_id}",String.valueOf(t.getDepartId()));
 		sql  = sql.replace("#{org_code}",String.valueOf(t.getOrgCode()));
 		sql  = sql.replace("#{in_storage_id}",String.valueOf(t.getInStorageId()));
 		sql  = sql.replace("#{in_storage_name}",String.valueOf(t.getInStorageName()));
 		sql  = sql.replace("#{out_storage_id}",String.valueOf(t.getOutStorageId()));
 		sql  = sql.replace("#{out_storage_name}",String.valueOf(t.getOutStorageName()));
 		sql  = sql.replace("#{type}",String.valueOf(t.getType()));
 		sql  = sql.replace("#{connact_no}",String.valueOf(t.getConnactNo()));
 		sql  = sql.replace("#{make_date}",String.valueOf(t.getMakeDate()));
 		sql  = sql.replace("#{pro_zn_name}",String.valueOf(t.getProZnNameB()));
 		sql  = sql.replace("#{pro_id}",String.valueOf(t.getProId()));
 		sql  = sql.replace("#{brand}",String.valueOf(t.getBrandB()));
 		sql  = sql.replace("#{total}",String.valueOf(t.getTotal()));
 		sql  = sql.replace("#{price}",String.valueOf(t.getPrice()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{real_name}",String.valueOf(t.getRealName()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute("emk_storage_connact",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}