package com.emk.bound.storageconnact.controller;
import com.emk.bound.storageconnact.entity.EmkStorageConnactDetailEntity;
import com.emk.bound.storageconnact.entity.EmkStorageConnactEntity;
import com.emk.bound.storageconnact.service.EmkStorageConnactServiceI;

import java.lang.reflect.Method;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.product.product.entity.EmkProductEntity;
import com.emk.storage.storage.entity.EmkStorageEntity;
import com.emk.storage.storagelog.entity.EmkStorageLogEntity;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import com.emk.util.DateUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypeA;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;

import java.io.OutputStream;

import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.GsonUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: 库存组装表
 * @author onlineGenerator
 * @date 2020-02-11 20:12:11
 * @version V1.0   
 *
 */
@Api(value="EmkStorageConnact",description="库存组装表",tags="emkStorageConnactController")
@Controller
@RequestMapping("/emkStorageConnactController")
public class EmkStorageConnactController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkStorageConnactController.class);

	@Autowired
	private EmkStorageConnactServiceI emkStorageConnactService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	
	/**
	 * 库存组装表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/storageconnact/emkStorageConnactList");
	}

	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		request.setAttribute("markDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show from hm_category where PARENT_CODE='A01A09' and type='8' and org_code=? and code!='A01A09A13' order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		request.setAttribute("categoryEntities",categoryEntities);

		categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		request.setAttribute("productcategoryEntities",categoryEntities);
		String columns = "";
		for(Map m : categoryEntities){
			if(Utils.notEmpty(m)){
				columns += m.get("code")+",";
			}
		}
		if(columns.indexOf(",")>0){
			columns = columns.substring(0,columns.length()-1);
		}
		request.setAttribute("columns",columns);
		if (Utils.notEmpty(map.get("inStorageId"))) {
			List<EmkStorageConnactDetailEntity> rkglMxEntities = systemService.findHql("from EmkStorageConnactDetailEntity where inStorageId=? ",  map.get("inStorageId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/storageconnact/orderMxList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(EmkStorageConnactEntity emkStorageConnact,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkStorageConnactEntity.class, dataGrid);
		//查询条件组装器
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if(!user.getUserName().equals("admin")){
			cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkStorageConnact, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkStorageConnactService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除库存组装表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkStorageConnactEntity emkStorageConnact, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkStorageConnact = systemService.getEntity(EmkStorageConnactEntity.class, emkStorageConnact.getId());
		message = "库存组装表删除成功";
		try{
			emkStorageConnactService.delete(emkStorageConnact);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "库存组装表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除库存组装表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "库存组装表删除成功";
		try{
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			for(String id:ids.split(",")){
				EmkStorageConnactEntity t = systemService.getEntity(EmkStorageConnactEntity.class, id);
				EmkStorageLogEntity storageLogEntity = new EmkStorageLogEntity();
				String remark = "",stroageId = "" , stroageName = "",type = "";
				if("0".equals(t.getType())){
					remark = "删除库存组合单";
					stroageId = t.getInStorageId();
					stroageName = t.getInStorageName();
					type = "8";
				}else{
					remark = "删除库存拆卸单";
					stroageId = t.getOutStorageId();
					stroageName = t.getOutStorageName();
					type = "9";
				}
				EmkStorageEntity storageEntity = findStorageAndSaveLog(stroageId,stroageName, t, orgCode,storageLogEntity,remark,type);
				List<EmkStorageEntity> storageEntityList = new ArrayList<>();

				if(Utils.notEmpty(storageEntity)){
					systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?) where id=? ",t.getTotal(),storageEntity.getId());
					//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
					saveOldLog1(storageLogEntity,storageEntity);
					updateKccb(storageEntity,orgCode);
				}

				List<EmkStorageConnactDetailEntity> mInStorageDetailEntityList = systemService.findHql("from EmkStorageConnactDetailEntity where inStorageId=?",t.getId());
				for(EmkStorageConnactDetailEntity emkMInStorageDetailEntity : mInStorageDetailEntityList){
					storageLogEntity = new EmkStorageLogEntity();
					if("0".equals(t.getType())){
						storageEntity = findStorageAndSaveLog(emkMInStorageDetailEntity,t.getOutStorageId(),t.getOutStorageName(),t,orgCode,storageLogEntity,remark,"8");
					}else{
						storageEntity = findStorageAndSaveLog(emkMInStorageDetailEntity,t.getInStorageId(),t.getInStorageName(),t,orgCode,storageLogEntity,remark,"9");
					}
					if(Utils.notEmpty(storageEntity)){
						systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
						//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
						saveOldLog1(storageLogEntity,storageEntity);
						storageEntityList.add(storageEntity);
					}
				}
				systemService.executeSql("delete from emk_storage_connact_detail where in_storage_id=?", id);
				emkStorageConnactService.delete(t);

				for(EmkStorageEntity emkStorageEntity : storageEntityList){
					updateKccb(emkStorageEntity,orgCode);
				}
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "库存组装表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 添加库存组装表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkStorageConnactEntity emkStorageConnact, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "库存组装表添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			Class c = Class.forName(EmkStorageConnactDetailEntity.class.getName());
			Method show = null;

			emkStorageConnact.setOrgCode(tsDepart.getOrgCode());
			emkStorageConnact.setDepartId(tsDepart.getId());
			emkStorageConnactService.save(emkStorageConnact);

			//判断在商品管理是否有存在，不存在则创建
			EmkProductEntity emkProductEntity = saveEmkProductEntity(emkStorageConnact,orgCode,tsDepart);
			//查询库存数据和保存库存数据日记
			EmkStorageLogEntity storageLogEntity = new EmkStorageLogEntity();
			EmkStorageEntity storageEntity = findStorageAndSaveLog(emkStorageConnact.getInStorageId(),emkStorageConnact.getInStorageName(),emkStorageConnact,orgCode,storageLogEntity,"录入库存组合单","6");

			if(storageEntity == null){
				storageEntity = saveNewStorage(emkStorageConnact,emkStorageConnact.getInStorageId(),emkStorageConnact.getInStorageName(),emkProductEntity,storageLogEntity);
			}else{
				systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",emkStorageConnact.getTotal(),storageEntity.getId());
				saveOldLog1(storageLogEntity,storageEntity);
			}
			updateKccb(storageEntity,orgCode);


			List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='8' and org_code=? order by order_num asc",orgCode);
			EmkStorageConnactDetailEntity emkMInStorageDetailEntity = null;
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);

				for (int i = 0; i < rows; i++) {
					emkMInStorageDetailEntity = new EmkStorageConnactDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						emkMInStorageDetailEntity.setDepartId(emkStorageConnact.getDepartId());
						emkMInStorageDetailEntity.setOrgCode(emkStorageConnact.getOrgCode());
						emkMInStorageDetailEntity.setInStorageId(emkStorageConnact.getId());
						emkMInStorageDetailEntity.setType("0");
						for(Map category : categoryEntities){
							if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
								String m0 = "setA"+category.get("code").toString().substring(1);
								show = c.getMethod(m0,String.class);
								show.invoke(emkMInStorageDetailEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
							}
						}
						systemService.save(emkMInStorageDetailEntity);

						//判断在商品管理是否有存在，不存在则创建
						emkProductEntity = saveEmkProductEntity(emkMInStorageDetailEntity,orgCode,tsDepart);

						//查询库存数据和保存库存数据日记
						storageLogEntity = new EmkStorageLogEntity();
						storageEntity = findStorageAndSaveLog(emkMInStorageDetailEntity,emkStorageConnact.getOutStorageId(),emkStorageConnact.getOutStorageName(),emkStorageConnact,orgCode,storageLogEntity,"录入库存组合单","6");

						if(storageEntity == null){
								storageEntity = saveNewStorage(emkMInStorageDetailEntity,emkStorageConnact,emkStorageConnact.getOutStorageId(),emkStorageConnact.getOutStorageName(),emkProductEntity,storageLogEntity);
						}else{
							systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?) where id=? ",emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
							saveOldLog1(storageLogEntity,storageEntity);
						}
						systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "库存组装表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加库存拆卸
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd2")
	@ResponseBody
	public AjaxJson doAdd2(EmkStorageConnactEntity emkStorageConnact, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "库存拆卸添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			Class c = Class.forName(EmkStorageConnactDetailEntity.class.getName());
			Method show = null;

			emkStorageConnact.setOrgCode(tsDepart.getOrgCode());
			emkStorageConnact.setDepartId(tsDepart.getId());
			emkStorageConnactService.save(emkStorageConnact);

			//判断在商品管理是否有存在，不存在则创建
			EmkProductEntity emkProductEntity = saveEmkProductEntity(emkStorageConnact,orgCode,tsDepart);
			//查询库存数据和保存库存数据日记
			EmkStorageLogEntity storageLogEntity = new EmkStorageLogEntity();
			EmkStorageEntity storageEntity = findStorageAndSaveLog(emkStorageConnact.getOutStorageId(),emkStorageConnact.getOutStorageName(),emkStorageConnact,orgCode,storageLogEntity,"录入库存拆卸单","7");

			if(storageEntity == null){
				storageEntity = saveNewStorage(emkStorageConnact,emkStorageConnact.getOutStorageId(),emkStorageConnact.getOutStorageName(),emkProductEntity,storageLogEntity);
			}else{
				systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?) where id=? ",emkStorageConnact.getTotal(),storageEntity.getId());
				saveOldLog1(storageLogEntity,storageEntity);
			}
			updateKccb(storageEntity,orgCode);


			List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='8' and org_code=? order by order_num asc",orgCode);
			EmkStorageConnactDetailEntity emkMInStorageDetailEntity = null;
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);

				for (int i = 0; i < rows; i++) {
					emkMInStorageDetailEntity = new EmkStorageConnactDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						emkMInStorageDetailEntity.setDepartId(emkStorageConnact.getDepartId());
						emkMInStorageDetailEntity.setOrgCode(emkStorageConnact.getOrgCode());
						emkMInStorageDetailEntity.setInStorageId(emkStorageConnact.getId());
						emkMInStorageDetailEntity.setType("0");
						for(Map category : categoryEntities){
							if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
								String m0 = "setA"+category.get("code").toString().substring(1);
								show = c.getMethod(m0,String.class);
								show.invoke(emkMInStorageDetailEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
							}
						}
						systemService.save(emkMInStorageDetailEntity);

						//判断在商品管理是否有存在，不存在则创建
						emkProductEntity = saveEmkProductEntity(emkMInStorageDetailEntity,orgCode,tsDepart);

						//查询库存数据和保存库存数据日记
						storageLogEntity = new EmkStorageLogEntity();
						storageEntity = findStorageAndSaveLog(emkMInStorageDetailEntity,emkStorageConnact.getInStorageId(),emkStorageConnact.getInStorageName(),emkStorageConnact,orgCode,storageLogEntity,"录入库存拆卸单","7");

						if(storageEntity == null){
							storageEntity = saveNewStorage(emkMInStorageDetailEntity,emkStorageConnact,emkStorageConnact.getInStorageId(),emkStorageConnact.getInStorageName(),emkProductEntity,storageLogEntity);
							updateKccb(storageEntity,orgCode);
						}else{
							systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
							updateKccb(storageEntity,orgCode);
							storageLogEntity.setProNum(storageEntity.getA01a09a08());
							saveOldLog1(storageLogEntity,storageEntity);
						}
						systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "库存拆卸添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新库存组装表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkStorageConnactEntity emkStorageConnact, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "库存组装表更新成功";
		EmkStorageConnactEntity t = emkStorageConnactService.get(EmkStorageConnactEntity.class, emkStorageConnact.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(emkStorageConnact, t);
			emkStorageConnactService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "库存组装表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	void saveOldLog1(EmkStorageLogEntity storageLogEntity,EmkStorageEntity storageEntity){
		Map esM = systemService.findOneForJdbc("select * from emk_storage where id=?",storageEntity.getId());
		if(Utils.notEmpty(storageEntity.getA01a09a02())){
			storageLogEntity.setPreTotal(storageEntity.getA01a09a02());
			storageLogEntity.setNextTotal(esM.get("a01a09a02").toString());
			int total  = Integer.parseInt(Utils.notEmpty(esM.get("a01a09a02")) ? esM.get("a01a09a02").toString():"0")-
					Integer.parseInt(Utils.notEmpty(storageLogEntity.getPreTotal()) ? storageLogEntity.getPreTotal():"0");
			storageLogEntity.setTotal(String.valueOf(total));
			systemService.save(storageLogEntity);
		}

	}

	void saveOldLog2(EmkStorageLogEntity newStorageLogEntity,EmkStorageEntity oldstorageEntity,EmkStorageConnactDetailEntity old){
		newStorageLogEntity.setId(null);
		newStorageLogEntity.setPreTotal(oldstorageEntity.getA01a09a02());
		newStorageLogEntity.setTotal(old.getA01a09a02());
		int nextTotal  = Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getPreTotal()) ? newStorageLogEntity.getPreTotal():"0")+
				Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getTotal()) ? newStorageLogEntity.getTotal():"0");
		newStorageLogEntity.setNextTotal(String.valueOf(nextTotal));
		systemService.save(newStorageLogEntity);
	}

	EmkProductEntity saveEmkProductEntity(EmkStorageConnactEntity emkStorageConnactEntity,String orgCode,TSDepart tsDepart){
		String hql = "from EmkProductEntity where 1=1 and proZnName='"+emkStorageConnactEntity.getProZnNameB()+"'";
		if(Utils.notEmpty(emkStorageConnactEntity.getBrandB())){
			hql += " and brand='"+emkStorageConnactEntity.getBrandB()+"'";
		}
		if(Utils.notEmpty(emkStorageConnactEntity.getProNumB())){
			hql += " and proNum='"+emkStorageConnactEntity.getProNumB()+"'";
		}
		if(Utils.notEmpty(emkStorageConnactEntity.getUnitB())){
			hql += " and unit='"+emkStorageConnactEntity.getUnitB()+"'";
		}

		if(Utils.notEmpty(emkStorageConnactEntity.getA01a01a01B())){
			hql += " and a01a01a01='"+emkStorageConnactEntity.getA01a01a01B()+"'";
		}else{
			hql += " and (a01a01a01 is null or a01a01a01 = '') ";
		}
		hql += " and orgCode='"+orgCode+"'";
		EmkProductEntity emkProductEntity = systemService.singleResult(hql);
		if(Utils.isEmpty(emkProductEntity)){
			emkProductEntity = new EmkProductEntity();
			emkProductEntity.setProType("qt");
			emkProductEntity.setProTypeName("其他");
			emkProductEntity.setProZnName(emkStorageConnactEntity.getProZnNameB());
			if(Utils.notEmpty(emkStorageConnactEntity.getProNumB())){
				emkProductEntity.setProNum(emkStorageConnactEntity.getProNumB());
				emkProductEntity.setFlag("0");
			}else {
				saveProNum(emkProductEntity,orgCode);
				emkProductEntity.setFlag("1");
			}
			emkProductEntity.setProZjm(PinyinUtil.getPinYinHeadChar(emkProductEntity.getProZnName()));
			emkProductEntity.setBrand(emkStorageConnactEntity.getBrandB());
			emkProductEntity.setA01a01a01(emkStorageConnactEntity.getA01a01a01B());
			emkProductEntity.setUnit(emkStorageConnactEntity.getUnitB());
			Map type = systemService.findOneForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='units' and typename=?" +
					"    and org_code='"+orgCode+"'",emkProductEntity.getUnit());
			if(Utils.notEmpty(type)){
				emkProductEntity.setUnitCode(type.get("typecode").toString());
			}else{
				Map typegroup = systemService.findOneForJdbc("select id from t_s_typegroup t2  where typegroupcode='units' and org_code='"+orgCode+"'");
				if(Utils.notEmpty(typegroup)){
					TSTypeA tsType = new TSTypeA();
					tsType.setTypename(emkProductEntity.getUnit());
					tsType.setTypecode(PinyinUtil.getPinYinHeadChar(tsType.getTypename()));
					tsType.setTypegroupid(typegroup.get("id").toString());
					systemService.save(tsType);
				}
			}
			emkProductEntity.setOrgCode(tsDepart.getOrgCode());
			emkProductEntity.setDepartId(tsDepart.getId());
			systemService.save(emkProductEntity);
		}
		emkStorageConnactEntity.setProNumB(emkProductEntity.getProNum());
		return emkProductEntity;

	}

	EmkProductEntity saveEmkProductEntity(EmkStorageConnactDetailEntity emkMOutStorageDetailEntity,String orgCode,TSDepart tsDepart){
		String hql = "from EmkProductEntity where 1=1 and proZnName='"+emkMOutStorageDetailEntity.getA01a09a07()+"'";
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a10())){
			hql += " and brand='"+emkMOutStorageDetailEntity.getA01a09a10()+"'";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a08())){
			hql += " and proNum='"+emkMOutStorageDetailEntity.getA01a09a08()+"'";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a11())){
			hql += " and unit='"+emkMOutStorageDetailEntity.getA01a09a11()+"'";
		}

		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a06())){
			hql += " and a01a01a01='"+emkMOutStorageDetailEntity.getA01a09a06()+"'";
		}else{
			hql += " and (a01a01a01 is null or a01a01a01 = '') ";
		}
		hql += " and orgCode='"+orgCode+"'";
		EmkProductEntity emkProductEntity = systemService.singleResult(hql);
		if(Utils.isEmpty(emkProductEntity)){
			emkProductEntity = new EmkProductEntity();
			emkProductEntity.setProType("qt");
			emkProductEntity.setProTypeName("其他");
			emkProductEntity.setProZnName(emkMOutStorageDetailEntity.getA01a09a07());
			if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a08())){
				emkProductEntity.setProNum(emkMOutStorageDetailEntity.getA01a09a08());
				emkProductEntity.setFlag("0");
			}else {
				saveProNum(emkProductEntity,orgCode);
				emkProductEntity.setFlag("1");
			}
			emkProductEntity.setProZjm(PinyinUtil.getPinYinHeadChar(emkProductEntity.getProZnName()));
			emkProductEntity.setBrand(emkMOutStorageDetailEntity.getA01a09a10());
			emkProductEntity.setA01a01a01(emkMOutStorageDetailEntity.getA01a09a06());
			emkProductEntity.setUnit(emkMOutStorageDetailEntity.getA01a09a11());
			Map type = systemService.findOneForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='units' and typename=?" +
					"    and org_code='"+orgCode+"'",emkProductEntity.getUnit());
			if(Utils.notEmpty(type)){
				emkProductEntity.setUnitCode(type.get("typecode").toString());
			}else{
				Map typegroup = systemService.findOneForJdbc("select id from t_s_typegroup t2  where typegroupcode='units' and org_code='"+orgCode+"'");
				if(Utils.notEmpty(typegroup)){
					TSTypeA tsType = new TSTypeA();
					tsType.setTypename(emkProductEntity.getUnit());
					tsType.setTypecode(PinyinUtil.getPinYinHeadChar(tsType.getTypename()));
					tsType.setTypegroupid(typegroup.get("id").toString());
					systemService.save(tsType);
				}
			}
			emkProductEntity.setSellPrice(emkMOutStorageDetailEntity.getA01a09a21());
			emkProductEntity.setCostPrice(emkMOutStorageDetailEntity.getA01a09a12());
			emkProductEntity.setOrgCode(tsDepart.getOrgCode());
			emkProductEntity.setDepartId(tsDepart.getId());
			systemService.save(emkProductEntity);
		}
		emkMOutStorageDetailEntity.setA01a09a08(emkProductEntity.getProNum());
		return emkProductEntity;

	}

	EmkStorageEntity findStorageAndSaveLog(EmkStorageConnactDetailEntity emkMOutStorageDetailEntity,String stroageId,String stroageName,EmkStorageConnactEntity t,String orgCode,
										   EmkStorageLogEntity storageLogEntity,String remark,String type){
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",orgCode);
		String hql = "from EmkStorageEntity where 1=1 ";
		if(Utils.notEmpty(stroageId)){
			hql += " and storageId='"+stroageId+"'";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a07())){
			hql += " and a01a09a07='"+emkMOutStorageDetailEntity.getA01a09a07()+"'";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a08())){
			hql += " and a01a09a08='"+emkMOutStorageDetailEntity.getA01a09a08()+"'";
		}else{
			hql += " and flag=1";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a10())){
			hql += " and a01a09a10='"+emkMOutStorageDetailEntity.getA01a09a10()+"'";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a11())){
			hql += " and a01a09a11='"+emkMOutStorageDetailEntity.getA01a09a11()+"'";
		}else{
			hql += " and a01a09a11 is null";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a05())){
			hql += " and a01a09a05='"+emkMOutStorageDetailEntity.getA01a09a05()+"'";
		}else{
			hql += " and a01a09a05 is null";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a04())){
			hql += " and a01a09a04='"+emkMOutStorageDetailEntity.getA01a09a04()+"'";
		}else{
			hql += " and a01a09a04 is null";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a06())){
			hql += " and a01a09a06='"+emkMOutStorageDetailEntity.getA01a09a06()+"'";
		}else{
			hql += " and a01a09a06 is null";
		}
		hql += " and orgCode='"+orgCode+"'";
		EmkStorageEntity storageEntity = systemService.singleResult(hql);

		storageLogEntity.setRkNo(t.getConnactNo());
		storageLogEntity.setType("2");
		storageLogEntity.setProNum(emkMOutStorageDetailEntity.getA01a09a08());
		storageLogEntity.setProZnName(emkMOutStorageDetailEntity.getA01a09a07());
		storageLogEntity.setMarkDate(DateUtil.getLogTime());
		//storageLogEntity.setRemark("编辑采购入库单");
		storageLogEntity.setRemark(remark);
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setStorageId(stroageId);
		storageLogEntity.setStorageName(stroageName);
		storageLogEntity.setType(type);
		return storageEntity;
	}

	EmkStorageEntity findStorageAndSaveLog(String stroageId,String stroageName,EmkStorageConnactEntity t,String orgCode,
										   EmkStorageLogEntity storageLogEntity,String remark,String type){
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",orgCode);
		String hql = "from EmkStorageEntity where 1=1 ";
		if(Utils.notEmpty(stroageId)){
			hql += " and storageId='"+stroageId+"'";
		}
		if(Utils.notEmpty(t.getProZnNameB())){
			hql += " and a01a09a07='"+t.getProZnNameB()+"'";
		}
		if(Utils.notEmpty(t.getProNumB())){
			hql += " and a01a09a08='"+t.getProNumB()+"'";
		}else{
			hql += " and flag=1";
		}
		if(Utils.notEmpty(t.getBrandB())){
			hql += " and a01a09a10='"+t.getBrandB()+"'";
		}
		if(Utils.notEmpty(t.getUnitB())){
			hql += " and a01a09a11='"+t.getUnitB()+"'";
		}else{
			hql += " and a01a09a11 is null";
		}
		/*if(Utils.notEmpty(t.getA01a09a05())){
			hql += " and a01a09a05='"+emkMOutStorageDetailEntity.getA01a09a05()+"'";
		}else{
			hql += " and a01a09a05 is null";
		}*/
		hql += " and a01a09a05 is null";

		/*if(Utils.notEmpty(t.getA01a09a04())){
			hql += " and a01a09a04='"+emkMOutStorageDetailEntity.getA01a09a04()+"'";
		}else{
			hql += " and a01a09a04 is null";
		}*/
		hql += " and a01a09a04 is null";

		if(Utils.notEmpty(t.getA01a01a01B())){
			hql += " and a01a09a06='"+t.getA01a01a01B()+"'";
		}else{
			hql += " and a01a09a06 is null";
		}
		hql += " and orgCode='"+orgCode+"'";
		EmkStorageEntity storageEntity = systemService.singleResult(hql);

		storageLogEntity.setRkNo(t.getConnactNo());
		storageLogEntity.setProNum(t.getProNumB());
		storageLogEntity.setProZnName(t.getProZnNameB());
		storageLogEntity.setMarkDate(DateUtil.getLogTime());
		storageLogEntity.setRemark(remark);
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setStorageId(stroageId);
		storageLogEntity.setStorageName(stroageName);
		storageLogEntity.setType(type);
		return storageEntity;
	}


	EmkStorageEntity findOldStorage(EmkStorageConnactEntity oldT,String stroageId,String stroageName,EmkStorageConnactDetailEntity old,String orgCode){
		String hql = "from EmkStorageEntity where 1=1 ";
		if(Utils.notEmpty(stroageId)){
			hql += " and storageId='"+stroageId+"'";
		}
		if(Utils.notEmpty(old.getA01a09a07())){
			hql += " and a01a09a07='"+old.getA01a09a07()+"'";
		}
		if(Utils.notEmpty(old.getA01a09a08())){
			hql += " and a01a09a08='"+old.getA01a09a08()+"'";
		}else{
			hql += " and flag=1";
		}
		if(Utils.notEmpty(old.getA01a09a10())){
			hql += " and a01a09a10='"+old.getA01a09a10()+"'";
		}
		if(Utils.notEmpty(old.getA01a09a05())){
			hql += " and a01a09a05='"+old.getA01a09a05()+"'";
		}
		if(Utils.notEmpty(old.getA01a09a04())){
			hql += " and a01a09a04='"+old.getA01a09a04()+"'";
		}
		if(Utils.notEmpty(old.getA01a09a06())){
			hql += " and a01a09a06='"+old.getA01a09a06()+"'";
		}
		hql += " and orgCode='"+orgCode+"'";
		EmkStorageEntity oldstorageEntity = systemService.singleResult(hql);
		return oldstorageEntity;
	}

	EmkStorageEntity saveNewStorage(EmkStorageConnactEntity t,String stroageId,String stroageName,EmkProductEntity emkProductEntity,EmkStorageLogEntity storageLogEntity){
		EmkStorageEntity storageEntity = new EmkStorageEntity();
		storageEntity.setId(null);
		storageEntity.setStorageName(stroageName);
		storageEntity.setStorageId(stroageId);
		storageEntity.setDepartId(t.getDepartId());
		storageEntity.setOrgCode(t.getOrgCode());
		storageEntity.setA01a09a08(emkProductEntity.getProNum());
		storageEntity.setA01a09a02(t.getTotal());
		storageEntity.setA01a09a07(t.getProZnNameB());
		storageEntity.setA01a09a06(t.getA01a01a01B());
		storageEntity.setA01a09a10(t.getBrandB());
		storageEntity.setA01a09a11(t.getUnitB());
		if(Utils.notEmpty(t.getProNumB())){
			storageEntity.setFlag("0");
		}else{
			storageEntity.setFlag("1");
		}
		storageEntity.setProZjm(PinyinUtil.getPinYinHeadChar(storageEntity.getA01a09a07()));
		systemService.save(storageEntity);

		if("1".equals(t.getType())){
			systemService.executeSql("update emk_storage set a01a09a02=-a01a09a02 where id=? ",storageEntity.getId());
		}
		systemService.executeSql("update emk_storage set a01a09a13=truncate(a01a09a03/a01a09a02,2) where id=? ",storageEntity.getId());
		storageLogEntity.setPreTotal("0");
		storageLogEntity.setNextTotal(t.getTotal());
		storageLogEntity.setTotal(t.getTotal());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageEntity.setStorageName(stroageName);
		storageEntity.setStorageId(stroageId);
		systemService.save(storageLogEntity);
		return storageEntity;
	}

	EmkStorageEntity saveNewStorage(EmkStorageConnactDetailEntity emkMOutStorageDetailEntity,EmkStorageConnactEntity t,String stroageId,String stroageName,EmkProductEntity emkProductEntity,EmkStorageLogEntity storageLogEntity){
		EmkStorageEntity storageEntity = new EmkStorageEntity();
		MyBeanUtils.copyBeanNotNull2Bean(emkMOutStorageDetailEntity,storageEntity);
		storageEntity.setId(null);
		storageEntity.setStorageName(stroageName);
		storageEntity.setStorageId(stroageId);
		storageEntity.setA01a09a08(emkProductEntity.getProNum());
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a08())){
			storageEntity.setFlag("0");
		}else{
			storageEntity.setFlag("1");
		}
		systemService.save(storageEntity);
		if("0".equals(t.getType())){
			systemService.executeSql("update emk_storage set a01a09a02=-a01a09a02 where id=? ",storageEntity.getId());
		}

		systemService.executeSql("update emk_storage set a01a09a13=truncate(a01a09a03/a01a09a02,2) where id=? ",storageEntity.getId());
		storageLogEntity.setPreTotal("0");
		storageLogEntity.setNextTotal(emkMOutStorageDetailEntity.getA01a09a02());
		storageLogEntity.setTotal(emkMOutStorageDetailEntity.getA01a09a02());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageEntity.setStorageName(stroageName);
		storageEntity.setStorageId(stroageId);
		systemService.save(storageLogEntity);
		return storageEntity;
	}

	void saveOldStorage(EmkStorageEntity oldstorageEntity,EmkStorageConnactDetailEntity old,EmkStorageLogEntity storageLogEntity){
		systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",
				old.getA01a09a02(),oldstorageEntity.getId());
		systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", oldstorageEntity.getId());

		EmkStorageLogEntity newStorageLogEntity = new EmkStorageLogEntity();
		MyBeanUtils.copyBeanNotNull2Bean(storageLogEntity,newStorageLogEntity);
		newStorageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
		newStorageLogEntity.setStorageId(oldstorageEntity.getStorageId());
		newStorageLogEntity.setStorageName(oldstorageEntity.getStorageName());
		newStorageLogEntity.setDepartId(oldstorageEntity.getDepartId());
		newStorageLogEntity.setOrgCode(oldstorageEntity.getOrgCode());

		saveOldLog2(newStorageLogEntity,oldstorageEntity,old);
	}

	/**
	 * 库存组装表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkStorageConnactEntity emkStorageConnact, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
		TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='库存组合单号前缀' and departId='"+tsDepart.getId()+"'");
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));

		Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(connact_no, 4)),0)+1 AS signed) orderNum from emk_storage_connact where org_code=? and type=0",orgCode);
		emkStorageConnact.setConnactNo(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
		req.setAttribute("emkStorageConnactPage", emkStorageConnact);

		return new ModelAndView("com/emk/bound/storageconnact/emkStorageConnact-add");
	}

	@RequestMapping(params = "goAdd2")
	public ModelAndView goAdd2(EmkStorageConnactEntity emkStorageConnact, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
		TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='库存拆卸单号前缀' and departId='"+tsDepart.getId()+"'");
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));

		Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(connact_no, 4)),0)+1 AS signed) orderNum from emk_storage_connact where org_code=? and type=0",orgCode);
		emkStorageConnact.setConnactNo(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
		req.setAttribute("emkStorageConnactPage", emkStorageConnact);

		return new ModelAndView("com/emk/bound/storageconnact/emkStorageConnact-add2");
	}
	/**
	 * 库存组装表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkStorageConnactEntity emkStorageConnact, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkStorageConnact.getId())) {
			emkStorageConnact = emkStorageConnactService.getEntity(EmkStorageConnactEntity.class, emkStorageConnact.getId());
			req.setAttribute("emkStorageConnactPage", emkStorageConnact);
		}
		return new ModelAndView("com/emk/bound/storageconnact/emkStorageConnact-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkStorageConnactController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkStorageConnactEntity emkStorageConnact,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkStorageConnactEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkStorageConnact, request.getParameterMap());
		List<EmkStorageConnactEntity> emkStorageConnacts = this.emkStorageConnactService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"库存组装表");
		modelMap.put(NormalExcelConstants.CLASS,EmkStorageConnactEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("库存组装表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkStorageConnacts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkStorageConnactEntity emkStorageConnact,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"库存组装表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkStorageConnactEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("库存组装表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<EmkStorageConnactEntity> listEmkStorageConnactEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkStorageConnactEntity.class,params);
				for (EmkStorageConnactEntity emkStorageConnact : listEmkStorageConnactEntitys) {
					emkStorageConnactService.save(emkStorageConnact);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="库存组装表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkStorageConnactEntity>> list() {
		List<EmkStorageConnactEntity> listEmkStorageConnacts=emkStorageConnactService.getList(EmkStorageConnactEntity.class);
		return Result.success(listEmkStorageConnacts);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取库存组装表信息",notes="根据ID获取库存组装表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkStorageConnactEntity task = emkStorageConnactService.get(EmkStorageConnactEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取库存组装表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建库存组装表")
	public ResponseMessage<?> create(@ApiParam(name="库存组装表对象")@RequestBody EmkStorageConnactEntity emkStorageConnact, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkStorageConnactEntity>> failures = validator.validate(emkStorageConnact);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkStorageConnactService.save(emkStorageConnact);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("库存组装表信息保存失败");
		}
		return Result.success(emkStorageConnact);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新库存组装表",notes="更新库存组装表")
	public ResponseMessage<?> update(@ApiParam(name="库存组装表对象")@RequestBody EmkStorageConnactEntity emkStorageConnact) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkStorageConnactEntity>> failures = validator.validate(emkStorageConnact);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkStorageConnactService.saveOrUpdate(emkStorageConnact);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新库存组装表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新库存组装表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除库存组装表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkStorageConnactService.deleteEntityById(EmkStorageConnactEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("库存组装表删除失败");
		}

		return Result.success();
	}
}
