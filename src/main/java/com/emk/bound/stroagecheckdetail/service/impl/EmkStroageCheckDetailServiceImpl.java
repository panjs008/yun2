package com.emk.bound.stroagecheckdetail.service.impl;
import com.emk.bound.stroagecheckdetail.service.EmkStroageCheckDetailServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.emk.bound.stroagecheckdetail.entity.EmkStroageCheckDetailEntity;
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

@Service("emkStroageCheckDetailService")
@Transactional
public class EmkStroageCheckDetailServiceImpl extends CommonServiceImpl implements EmkStroageCheckDetailServiceI {

	
 	public void delete(EmkStroageCheckDetailEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(EmkStroageCheckDetailEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(EmkStroageCheckDetailEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(EmkStroageCheckDetailEntity t) throws Exception{
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
	private void doUpdateBus(EmkStroageCheckDetailEntity t) throws Exception{
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
	private void doDelBus(EmkStroageCheckDetailEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(EmkStroageCheckDetailEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("sys_org_code", t.getSysOrgCode());
		map.put("depart_id", t.getDepartId());
		map.put("org_code", t.getOrgCode());
		map.put("check_id", t.getCheckId());
		map.put("storage_id", t.getStorageId());
		map.put("storage_name", t.getStorageName());
		map.put("pro_num", t.getProNum());
		map.put("pro_name", t.getProName());
		map.put("guig", t.getGuig());
		map.put("brand", t.getBrand());
		map.put("color", t.getColor());
		map.put("size", t.getSize());
		map.put("unit", t.getUnit());
		map.put("bill_total", t.getBillTotal());
		map.put("check_total", t.getCheckTotal());
		map.put("win_total", t.getWinTotal());
		map.put("cost_price", t.getCostPrice());
		map.put("money", t.getMoney());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,EmkStroageCheckDetailEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{depart_id}",String.valueOf(t.getDepartId()));
 		sql  = sql.replace("#{org_code}",String.valueOf(t.getOrgCode()));
 		sql  = sql.replace("#{check_id}",String.valueOf(t.getCheckId()));
 		sql  = sql.replace("#{storage_id}",String.valueOf(t.getStorageId()));
 		sql  = sql.replace("#{storage_name}",String.valueOf(t.getStorageName()));
 		sql  = sql.replace("#{pro_num}",String.valueOf(t.getProNum()));
 		sql  = sql.replace("#{pro_name}",String.valueOf(t.getProName()));
 		sql  = sql.replace("#{guig}",String.valueOf(t.getGuig()));
 		sql  = sql.replace("#{brand}",String.valueOf(t.getBrand()));
 		sql  = sql.replace("#{color}",String.valueOf(t.getColor()));
 		sql  = sql.replace("#{size}",String.valueOf(t.getSize()));
 		sql  = sql.replace("#{unit}",String.valueOf(t.getUnit()));
 		sql  = sql.replace("#{bill_total}",String.valueOf(t.getBillTotal()));
 		sql  = sql.replace("#{check_total}",String.valueOf(t.getCheckTotal()));
 		sql  = sql.replace("#{win_total}",String.valueOf(t.getWinTotal()));
 		sql  = sql.replace("#{cost_price}",String.valueOf(t.getCostPrice()));
 		sql  = sql.replace("#{money}",String.valueOf(t.getMoney()));
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
					javaInter.execute("emk_stroage_check_detail",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}