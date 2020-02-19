package com.emk.bound.moutstorage.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.bound.minstoragedetail.entity.EmkMInStorageDetailEntity;
import com.emk.bound.moutstorage.entity.EmkMOutStorageDetailEntity;
import com.emk.bound.moutstorage.entity.EmkMOutStorageEntity;
import com.emk.bound.moutstorage.service.EmkMOutStorageServiceI;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.caiwu.bankrecord.entity.EmkBankRecordEntity;
import com.emk.caiwu.bankrecord.entity.EmkBankRecordLogEntity;
import com.emk.caiwu.yspay.entity.EmkYsPayEntity;
import com.emk.product.product.entity.EmkProductEntity;
import com.emk.storage.storage.entity.EmkStorageEntity;
import com.emk.storage.storagelog.entity.EmkStorageLogEntity;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import com.emk.util.*;

import com.emk.util.excel.ExcelUtil;
import com.service.custom.entity.YmkCustomEntity;
import com.service.custom.entity.YmkCustomEntityA;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tools.ant.util.DateUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSTypeA;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Title: Controller
 * @Description: 销售单
 * @author onlineGenerator
 * @date 2018-09-10 20:31:03
 * @version V1.0
 *
 */
@Api(value="EmkMOutStorage",description="销售单",tags="emkMOutStorageController")
@Controller
@RequestMapping("/emkMOutStorageController")
public class EmkMOutStorageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkMOutStorageController.class);

	@Autowired
	private EmkMOutStorageServiceI emkMOutStorageService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;


	/**
	 * 销售单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A07' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		request.setAttribute("headcategoryEntities",categoryEntities);
		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorageList");
	}

	@RequestMapping(params = "saleMxList")
	public ModelAndView saleMxList(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/moutstorage/saleMxList");
	}

	@RequestMapping(params = "list2")
	public ModelAndView list2(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorageList2");
	}

	@RequestMapping(params = "ckMxList")
	public ModelAndView ckMxList(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/moutstorage/ckMxList");
	}
	@RequestMapping(params = "useMxList")
	public ModelAndView useMxList(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/moutstorage/useMxList");
	}
	@RequestMapping(params = "list3")
	public ModelAndView list3(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorageList3");
	}

	@RequestMapping(params="emkMOutStorageDetailList")
	public ModelAndView rkglMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		request.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (Utils.notEmpty(map.get("inStorageId"))){
			List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?", map.get("inStorageId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorageDetailList");
	}

	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		request.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show from hm_category where PARENT_CODE='A01A09'  and type='2' and code!='A01A09A13' and code!='A01A09A05' and org_code=? order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		request.setAttribute("categoryEntities",categoryEntities);

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
		if (Utils.notEmpty(map.get("outStorageId"))) {
			List<EmkMOutStorageDetailEntity> emkProOrderDetailEntityList = systemService.findHql("from EmkMOutStorageDetailEntity where outStorageId=? ", map.get("outStorageId"));
			request.setAttribute("rkglMxList", emkProOrderDetailEntityList);
		}
		return new ModelAndView("com/emk/bound/moutstorage/orderMxList");
	}

	@RequestMapping(params = "orderMxList2")
	public ModelAndView orderMxList2(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("inStorageId"))) {
			List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?", map.get("inStorageId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/moutstorage/orderMxList2");
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
	public void datagrid(EmkMOutStorageEntity emkMOutStorage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkMOutStorageEntity.class, dataGrid);
		//查询条件组装器
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if(!user.getUserName().equals("admin")){
			cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkMOutStorage, request.getParameterMap());
		try{
			Map param = ParameterUtil.getParamMaps(request.getParameterMap());
			/*Map roleMap = (Map) request.getSession().getAttribute("ROLE");
			if(Utils.notEmpty(param.get("flag"))){
				cq.notEq("type","2");
			}
			if(roleMap != null){
				if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("cky")){
					cq.eq("createBy",user.getUserName());
				}
			}
			if(Utils.notEmpty(param.get("proNum"))){
				cq.add(Restrictions.sqlRestriction("(FIND_IN_SET('"+param.get("proNum")+"',(select GROUP_CONCAT(md.pro_num) from emk_m_in_storage_detail md where md.in_storage_id={alias}.id)) )"));
			}*/
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkMOutStorageService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 销售出库、出库明细查询
	 *
	 * @return
	 */
	@RequestMapping(params = "listUseByJdbc")
	public void listUseByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		String sql = "" ,countSql = "";
		TSUser user = ResourceUtil.getSessionUser();

		sql = "select t2.ck_no ckNo,t2.hospital_name hospitalName,t2.patient,t2.appler,t2.operation_date operationDate,t2.operation_dc operationDc,t2.cus_name cusName," +
				"	t2.kp_unit kpUnit,t2.kp_money kpMoney,t2.kp_num kpNum,t2.kp_num,t2.cjhk_money cjhkMoney,t.pro_zn_name proZnName,t.standards,t.brand,t.betch,t.sc_date scDate,t.limit_date limitDate," +
				"	t.zc_num,t.scqy,t.lincese,t.cytj from emk_m_in_storage_detail t	" +
				"	left join emk_m_use t2 on t2.id=t.in_storage_id \n" +
				"	where 1=1 ";

		if(Utils.notEmpty(map.get("ckNo"))){
			sql += " and t2.ck_no like '%"+map.get("ckNo")+"%'";
		}
		if(Utils.notEmpty(map.get("hospitalName"))){
			sql += " and t2.hospital_name like '%"+map.get("hospitalName")+"%'";
		}
		if(Utils.notEmpty(map.get("operationDc"))){
			sql += " and t2.operation_dc like '%"+map.get("operationDc")+"%'";
		}
		if(Utils.notEmpty(map.get("patient"))){
			sql += " and t2.patient like '%"+map.get("patient")+"%'";
		}
		if(Utils.notEmpty(map.get("kpNum"))){
			sql += " and t2.kp_num like '%"+map.get("kpNum")+"%'";
		}
		if(Utils.notEmpty(map.get("proNum"))){
			sql += " and t.pro_num like '%"+map.get("proNum")+"%'";
		}
		if(Utils.notEmpty(map.get("appler"))){
			sql += " and t2.appler like '%"+map.get("appler")+"%'";
		}
		if(Utils.notEmpty(map.get("cusName"))){
			sql += " and t2.cus_name like '%"+map.get("cusName")+"%'";
		}
		if(Utils.notEmpty(map.get("cusName"))){
			sql += " and t2.cus_name like '%"+map.get("cusName")+"%'";
		}
		if(Utils.notEmpty(map.get("proZnName"))){
			sql += " and t.pro_zn_name like '%"+map.get("proZnName")+"%'";
		}
		if(Utils.notEmpty(map.get("standards"))){
			sql += " and t.standards like '%"+map.get("standards")+"%'";
		}
		if(Utils.notEmpty(map.get("brand"))){
			sql += " and t.brand like '%"+map.get("brand")+"%'";
		}
		if(Utils.notEmpty(map.get("operationDate_begin"))){
			sql += " and t2.operation_date >= '"+map.get("operationDate_begin")+" 00:00:00'";
		}
		if(Utils.notEmpty(map.get("operationDate_end"))){
			sql += " and t2.operation_date <= '"+map.get("operationDate_end")+" 23:59:59'";
		}
		sql += " and t2.ck_no is not null and t2.ck_no != ''";
		//sql += " group by t.pro_zn_name,t.brand,t.betch ";

		countSql = "SELECT count(1) FROM ("+sql+") t9 ";

		sql += " limit ";
		if(dataGrid.getPage()==1){
			sql += " 0, "+dataGrid.getRows();
		}else{
			sql += (dataGrid.getPage()-1)*dataGrid.getRows()+","+dataGrid.getRows();
		}

		this.systemService.listAllByJdbc(dataGrid, sql, countSql);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 销售出库、出库明细查询
	 *
	 * @return
	 */
	@RequestMapping(params = "listAllByJdbc")
	public void listAllByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		String sql = "" ,countSql = "";
		TSUser user = ResourceUtil.getSessionUser();

		sql = "select t2.ck_no ckNo,t2.storage_name storageName,t2.type,t2.appler,t2.out_date outDate,t2.fh_date fhDate,t2.cus_name cusName,t.pro_num proNum,t.pro_zn_name proZnName,t.standards,t.brand,t.betch,t.sc_date scDate,t.limit_date limitDate," +
				"	t.zc_num,t.scqy,t.lincese,t.cytj from emk_m_in_storage_detail t	" +
				"	left join emk_m_out_storage t2 on t2.id=t.in_storage_id \n" +
				"	where 1=1 ";
		if(Utils.notEmpty(map.get("type"))){
			sql += " and FIND_IN_SET(t2.type,'"+map.get("type")+"')";
		}
		if(Utils.notEmpty(map.get("ckNo"))){
			sql += " and t2.ck_no like '%"+map.get("ckNo")+"%'";
		}
		if(Utils.notEmpty(map.get("proNum"))){
			sql += " and t.pro_num like '%"+map.get("proNum")+"%'";
		}
		if(Utils.notEmpty(map.get("appler"))){
			sql += " and t2.appler like '%"+map.get("appler")+"%'";
		}
		if(Utils.notEmpty(map.get("cusName"))){
			sql += " and t2.cus_name like '%"+map.get("cusName")+"%'";
		}
		if(Utils.notEmpty(map.get("cusName"))){
			sql += " and t2.cus_name like '%"+map.get("cusName")+"%'";
		}
		if(Utils.notEmpty(map.get("proZnName"))){
			sql += " and t.pro_zn_name like '%"+map.get("proZnName")+"%'";
		}
		if(Utils.notEmpty(map.get("standards"))){
			sql += " and t.standards like '%"+map.get("standards")+"%'";
		}
		if(Utils.notEmpty(map.get("brand"))){
			sql += " and t.brand like '%"+map.get("brand")+"%'";
		}
		if(Utils.notEmpty(map.get("outDate_begin"))){
			sql += " and t2.out_date >= '"+map.get("outDate_begin")+" 00:00:00'";
		}
		if(Utils.notEmpty(map.get("outDate_end"))){
			sql += " and t2.out_date <= '"+map.get("outDate_end")+" 23:59:59'";
		}
		sql += " and t2.ck_no is not null and t2.ck_no != ''";
		//sql += " group by t.pro_zn_name,t.brand,t.betch ";

		countSql = "SELECT count(1) FROM ("+sql+") t9 ";

		sql += " limit ";
		if(dataGrid.getPage()==1){
			sql += " 0, "+dataGrid.getRows();
		}else{
			sql += (dataGrid.getPage()-1)*dataGrid.getRows()+","+dataGrid.getRows();
		}

		this.systemService.listAllByJdbc(dataGrid, sql, countSql);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除销售单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkMOutStorage = systemService.getEntity(EmkMOutStorageEntity.class, emkMOutStorage.getId());
		message = "销售单删除成功";
		try{
			emkMOutStorageService.delete(emkMOutStorage);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "销售单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除销售单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		 String message = "销售开单删除成功";

		 try{
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			for(String id:ids.split(",")){
				EmkMOutStorageEntity emkMOutStorage = systemService.getEntity(EmkMOutStorageEntity.class, id);
				if("1".equals(emkMOutStorage.getState())){
					j.setMsg("销售开单已审核，无法删除");
					j.setSuccess(false);
					return j;
				}
				if("1".equals(emkMOutStorage.getPayState())){
					j.setMsg("销售开单已收款，无法删除");
					j.setSuccess(false);
					return j;
				}
				if("1".equals(emkMOutStorage.getRecevieState())){
					j.setMsg("销售开单已发货，无法删除");
					j.setSuccess(false);
					return j;
				}
				EmkStorageLogEntity storageLogEntity = null;
				List<EmkMOutStorageDetailEntity> mOutStorageDetailEntities = systemService.findHql("from EmkMOutStorageDetailEntity where outStorageId=?",emkMOutStorage.getId());
				for(EmkMOutStorageDetailEntity emkMOutStorageDetailEntity : mOutStorageDetailEntities){
					String hql = "from EmkStorageEntity where 1=1 ";
					if(Utils.notEmpty(emkMOutStorage.getStorageId())){
						hql += " and storageId='"+emkMOutStorage.getStorageId()+"'";
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
					if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a05())){
						hql += " and a01a09a05='"+emkMOutStorageDetailEntity.getA01a09a05()+"'";
					}
					if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a04())){
						hql += " and a01a09a04='"+emkMOutStorageDetailEntity.getA01a09a04()+"'";
					}
					if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a06())){
						hql += " and a01a09a06='"+emkMOutStorageDetailEntity.getA01a09a06()+"'";
					}
					hql += " and orgCode='"+orgCode+"'";
					EmkStorageEntity storageEntity = systemService.singleResult(hql);

					storageLogEntity = new EmkStorageLogEntity();
					storageLogEntity.setRkNo(emkMOutStorage.getA01a07a01());
					storageLogEntity.setType("2");
					storageLogEntity.setProNum(emkMOutStorageDetailEntity.getA01a09a08());
					storageLogEntity.setProZnName(emkMOutStorageDetailEntity.getA01a09a07());
					storageLogEntity.setMarkDate(DateUtil.getLogTime());
					storageLogEntity.setRemark("删除销售开单");
					storageLogEntity.setOrgCode(orgCode);
					storageLogEntity.setDepartId(tsDepart.getId());
					storageLogEntity.setStorageId(emkMOutStorage.getStorageId());
					storageLogEntity.setStorageName(emkMOutStorage.getStorageName());

					if(Utils.notEmpty(storageEntity)){
						systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",emkMOutStorageDetailEntity.getA01a09a02(),storageEntity.getId());
						systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
						saveOldLog1(storageLogEntity,storageEntity);
					}
				}
				systemService.executeSql("delete from emk_m_out_storage_detail where out_storage_id=?", id);

				emkMOutStorageService.delete(emkMOutStorage);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "销售单删除失败";
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

	void saveOldLog2(EmkStorageLogEntity newStorageLogEntity,EmkStorageEntity oldstorageEntity,EmkMOutStorageDetailEntity old){
		newStorageLogEntity.setId(null);
		newStorageLogEntity.setPreTotal(oldstorageEntity.getA01a09a02());
		newStorageLogEntity.setTotal(old.getA01a09a02());
		int nextTotal  = Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getPreTotal()) ? newStorageLogEntity.getPreTotal():"0")+
				Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getTotal()) ? newStorageLogEntity.getTotal():"0");
		newStorageLogEntity.setNextTotal(String.valueOf(nextTotal));
		systemService.save(newStorageLogEntity);
	}

	EmkProductEntity saveEmkProductEntity(EmkMOutStorageDetailEntity emkMOutStorageDetailEntity,String orgCode,TSDepart tsDepart){
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

	EmkStorageEntity findStorageAndSaveLog(EmkMOutStorageDetailEntity emkMOutStorageDetailEntity,EmkMOutStorageEntity t,String orgCode,EmkStorageLogEntity storageLogEntity,String remark){
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",orgCode);
		String hql = "from EmkStorageEntity where 1=1 ";
		if(Utils.notEmpty(t.getStorageId())){
			hql += " and storageId='"+t.getStorageId()+"'";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a07())){
			hql += " and a01a09a07='"+emkMOutStorageDetailEntity.getA01a09a07()+"'";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a08())){
			hql += " and a01a09a08='"+emkMOutStorageDetailEntity.getA01a09a08()+"'";
		}else{
			//hql += " and flag=1";
			hql += " and (a01a09a08 is null or a01a09a08 = '')";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a05())){
			hql += " and a01a09a05='"+emkMOutStorageDetailEntity.getA01a09a05()+"'";
		}else{
			hql += " and (a01a09a05 is null or a01a09a05 = '')";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a04())){
			hql += " and a01a09a04='"+emkMOutStorageDetailEntity.getA01a09a04()+"'";
		}else{
			hql += " and (a01a09a04 is null or a01a09a04 = '')";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a06())){
			hql += " and a01a09a06='"+emkMOutStorageDetailEntity.getA01a09a06()+"'";
		}else{
			hql += " and (a01a09a06 is null or a01a09a06 = '')";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a24())){
			hql += " and a01a09a24='"+emkMOutStorageDetailEntity.getA01a09a24()+"'";
		}else{
			hql += " and (a01a09a24 is null or a01a09a24 = '')";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a14())){
			hql += " and a01a09a14='"+emkMOutStorageDetailEntity.getA01a09a14()+"'";
		}else{
			hql += " and (a01a09a14 is null or a01a09a14 = '')";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a22())){
			hql += " and a01a09a22='"+emkMOutStorageDetailEntity.getA01a09a22()+"'";
		}else{
			hql += " and (a01a09a22 is null or a01a09a22 = '')";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a23())){
			hql += " and a01a09a23='"+emkMOutStorageDetailEntity.getA01a09a23()+"'";
		}else{
			hql += " and (a01a09a23 is null or a01a09a23 = '')";
		}
		hql += " and orgCode='"+orgCode+"'";
		EmkStorageEntity storageEntity = systemService.singleResult(hql);

		storageLogEntity.setRkNo(t.getA01a07a01());
		storageLogEntity.setType("2");
		storageLogEntity.setProNum(emkMOutStorageDetailEntity.getA01a09a08());
		storageLogEntity.setProZnName(emkMOutStorageDetailEntity.getA01a09a07());
		storageLogEntity.setMarkDate(DateUtil.getLogTime());
		//storageLogEntity.setRemark("编辑采购入库单");
		storageLogEntity.setRemark(remark);
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setStorageId(t.getStorageId());
		storageLogEntity.setStorageName(t.getStorageName());
		return storageEntity;
	}

	EmkStorageEntity findOldStorage(EmkMOutStorageEntity oldT,EmkMOutStorageDetailEntity old,String orgCode){
		String hql = "from EmkStorageEntity where 1=1 ";
		if(Utils.notEmpty(oldT.getStorageId())){
			hql += " and storageId='"+oldT.getStorageId()+"'";
		}
		if(Utils.notEmpty(old.getA01a09a07())){
			hql += " and a01a09a07='"+old.getA01a09a07()+"'";
		}
		if(Utils.notEmpty(old.getA01a09a08())){
			hql += " and a01a09a08='"+old.getA01a09a08()+"'";
		}else{
			//hql += " and flag=1";
			hql += " and (a01a09a08 is null or a01a09a08 = '')";
		}
		if(Utils.notEmpty(old.getA01a09a05())){
			hql += " and a01a09a05='"+old.getA01a09a05()+"'";
		}else{
			hql += " and (a01a09a05 is null or a01a09a05 = '')";
		}
		if(Utils.notEmpty(old.getA01a09a04())){
			hql += " and a01a09a04='"+old.getA01a09a04()+"'";
		}else{
			hql += " and (a01a09a04 is null or a01a09a04 = '')";
		}
		if(Utils.notEmpty(old.getA01a09a06())){
			hql += " and a01a09a06='"+old.getA01a09a06()+"'";
		}else{
			hql += " and (a01a09a06 is null or a01a09a06 = '')";
		}
		if(Utils.notEmpty(old.getA01a09a24())){
			hql += " and a01a09a24='"+old.getA01a09a24()+"'";
		}else{
			hql += " and (a01a09a24 is null or a01a09a24 = '')";
		}
		if(Utils.notEmpty(old.getA01a09a14())){
			hql += " and a01a09a14='"+old.getA01a09a14()+"'";
		}else{
			hql += " and (a01a09a14 is null or a01a09a14 = '')";
		}
		if(Utils.notEmpty(old.getA01a09a22())){
			hql += " and a01a09a22='"+old.getA01a09a22()+"'";
		}else{
			hql += " and (a01a09a22 is null or a01a09a22 = '')";
		}
		if(Utils.notEmpty(old.getA01a09a23())){
			hql += " and a01a09a23='"+old.getA01a09a23()+"'";
		}else{
			hql += " and (a01a09a23 is null or a01a09a23 = '')";
		}
		hql += " and orgCode='"+orgCode+"'";
		EmkStorageEntity oldstorageEntity = systemService.singleResult(hql);
		return oldstorageEntity;
	}

	void saveNewStorage(EmkMOutStorageDetailEntity emkMOutStorageDetailEntity,EmkMOutStorageEntity t,EmkProductEntity emkProductEntity,EmkStorageLogEntity storageLogEntity){
		EmkStorageEntity storageEntity = new EmkStorageEntity();
		MyBeanUtils.copyBeanNotNull2Bean(emkMOutStorageDetailEntity,storageEntity);
		storageEntity.setId(null);
		storageEntity.setStorageName(t.getStorageName());
		storageEntity.setStorageId(t.getStorageId());
		storageEntity.setA01a09a08(emkProductEntity.getProNum());
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a08())){
			storageEntity.setFlag("0");
		}else{
			storageEntity.setFlag("1");
		}
		storageEntity.setProZjm(PinyinUtil.getPinYinHeadChar(storageEntity.getA01a09a07()));
		systemService.save(storageEntity);
		systemService.executeSql("update emk_storage set a01a09a02=-a01a09a02 where id=? ",storageEntity.getId());

		systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ",storageEntity.getId());
		storageLogEntity.setPreTotal("0");
		storageLogEntity.setNextTotal(emkMOutStorageDetailEntity.getA01a09a02());
		storageLogEntity.setTotal(emkMOutStorageDetailEntity.getA01a09a02());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageEntity.setStorageName(t.getStorageName());
		storageEntity.setStorageId(t.getStorageId());
		systemService.save(storageLogEntity);
	}

	void saveOldStorage(EmkStorageEntity oldstorageEntity,EmkMOutStorageDetailEntity old,EmkStorageLogEntity storageLogEntity){
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
	 * 添加销售单
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");

			emkMOutStorage.setState("0");
			emkMOutStorage.setPayState("0");
			emkMOutStorage.setRecevieState("0");

			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			//判断是否存在客户，如果没有则创建
			YmkCustomEntityA ymkCustom = systemService.singleResult("from YmkCustomEntityA where cusName='"+emkMOutStorage.getCusName()+"' and orgCode='"+orgCode+"'");
			if(Utils.isEmpty(ymkCustom)){
				ymkCustom = new YmkCustomEntityA();
				ymkCustom.setStatus("0");
				Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(daan_num, 3)),0)+1 AS signed) orderNum from ymk_custom where org_code=? ",user.getCurrentDepart().getOrgCode());
				ymkCustom.setDaanNum( "KHDA1" + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
				ymkCustom.setCusName(emkMOutStorage.getCusName());
				ymkCustom.setCusNum( String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
				ymkCustom.setCusCode(ChineseToEnglish.getPinYinHeadChar(ymkCustom.getCusName()));
				ymkCustom.setZlxr(emkMOutStorage.getA01a07a08());
				ymkCustom.setAddress(emkMOutStorage.getA01a07a07());
				ymkCustom.setWorkphone(emkMOutStorage.getA01a07a09());
				ymkCustom.setDepartId(tsDepart.getId());
				ymkCustom.setOrgCode(tsDepart.getOrgCode());
				ymkCustom.setBcqkMoney("0");

				systemService.save(ymkCustom);
				emkMOutStorage.setA01a07a02(ymkCustom.getCusNum());
			}
			Class c = Class.forName(EmkMOutStorageDetailEntity.class.getName());

			Method show = null;

			emkMOutStorage.setOrgCode(tsDepart.getOrgCode());
			emkMOutStorage.setDepartId(tsDepart.getId());
			TSUser tsUser = systemService.findUniqueByProperty(TSUser.class,"userName",map.get("userName"));

			emkMOutStorageService.save(emkMOutStorage);

			EmkYsPayEntity emkFinanceYfCheckEntity = new EmkYsPayEntity();
			emkFinanceYfCheckEntity.setYfCheckNo(emkMOutStorage.getA01a07a01());
			emkFinanceYfCheckEntity.setState("0");
			emkFinanceYfCheckEntity.setDepartId(tsDepart.getId());
			emkFinanceYfCheckEntity.setOrgCode(tsDepart.getOrgCode());
			emkFinanceYfCheckEntity.setStorageId(emkMOutStorage.getStorageId());
			emkFinanceYfCheckEntity.setStorageName(emkMOutStorage.getStorageName());
			emkFinanceYfCheckEntity.setBankAccount(emkMOutStorage.getBankMoney());
			emkFinanceYfCheckEntity.setBankType(emkMOutStorage.getA01a07a06());
			emkFinanceYfCheckEntity.setCusName(emkMOutStorage.getCusName());
			emkFinanceYfCheckEntity.setCusNum(emkMOutStorage.getA01a07a02());
			emkFinanceYfCheckEntity.setYfMoney(Double.parseDouble(Utils.notEmpty(emkMOutStorage.getYfMoney()) ? emkMOutStorage.getYfMoney():"0"));
			emkFinanceYfCheckEntity.setMoney(Double.parseDouble(Utils.notEmpty(emkMOutStorage.getMoney()) ? emkMOutStorage.getMoney():"0"));
			emkFinanceYfCheckEntity.setQkMoney(Double.parseDouble(Utils.notEmpty(emkMOutStorage.getYfMoney()) ? emkMOutStorage.getYfMoney():"0"));
			emkFinanceYfCheckEntity.setClearWs(0.0);
			emkFinanceYfCheckEntity.setMarker(user.getRealName());
			emkFinanceYfCheckEntity.setMarkerId(user.getId());
			systemService.save(emkFinanceYfCheckEntity);

			YmkCustomEntity ymkCustomEntity = systemService.singleResult("from YmkCustomEntity where cusNum='"+emkMOutStorage.getA01a07a02()+"' and orgCode='"+orgCode+"'");
			if(Utils.notEmpty(emkMOutStorage.getBcqkMoney())){
				ymkCustomEntity.setBcqkMoney(emkMOutStorage.getBcqkMoney());
			}

			List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and org_code=? order by order_num asc",orgCode);
			EmkMOutStorageDetailEntity emkMOutStorageDetailEntity = null;
			EmkProductEntity emkProductEntity = null;
			dataRows = map.get("orderMxListID");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkStorageLogEntity storageLogEntity = null;

				for (int i = 0; i < rows; i++) {
					emkMOutStorageDetailEntity = new EmkMOutStorageDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						emkMOutStorageDetailEntity.setDepartId(emkMOutStorage.getDepartId());
						emkMOutStorageDetailEntity.setOrgCode(emkMOutStorage.getOrgCode());
						emkMOutStorageDetailEntity.setOutStorageId(emkMOutStorage.getId());
						emkMOutStorageDetailEntity.setPriceId(map.get("orderMxList["+i+"].priceId"));
						emkMOutStorageDetailEntity.setPriceNo(map.get("orderMxList["+i+"].priceNo"));
						emkMOutStorageDetailEntity.setStoId(map.get("orderMxList["+i+"].stoId"));

						for(Map category : categoryEntities){
							if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
								String m0 = "setA"+category.get("code").toString().substring(1);
								show = c.getMethod(m0,String.class);
								show.invoke(emkMOutStorageDetailEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
							}
						}
						systemService.save(emkMOutStorageDetailEntity);
						//判断在商品管理是否有存在，不存在则创建
						emkProductEntity = saveEmkProductEntity(emkMOutStorageDetailEntity,orgCode,tsDepart);
						//查询库存数据和保存库存数据日记
						storageLogEntity = new EmkStorageLogEntity();
						EmkStorageEntity storageEntity = findStorageAndSaveLog(emkMOutStorageDetailEntity,emkMOutStorage,orgCode,storageLogEntity,"录入销售开单");
						if(Utils.isEmpty(storageEntity)){
							saveNewStorage(emkMOutStorageDetailEntity,emkMOutStorage,emkProductEntity,storageLogEntity);
						}else{
							systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?) where id=? ",emkMOutStorageDetailEntity.getA01a09a02(),storageEntity.getId());
							//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
							systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());

							saveOldLog1(storageLogEntity,storageEntity);
						}
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新销售单
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");
			EmkMOutStorageEntity t = systemService.get(EmkMOutStorageEntity.class, map.get("emkMOutStorageId"));
			EmkMOutStorageEntity oldT = new EmkMOutStorageEntity();
			MyBeanUtils.copyBeanNotNull2Bean(t,oldT);
			List<EmkMOutStorageDetailEntity> emkMOutStorageDetailEntityList = systemService.findHql("from EmkMOutStorageDetailEntity where outStorageId=?",t.getId());
			Map<String,EmkMOutStorageDetailEntity> detailMap = new HashMap<>();
			for(EmkMOutStorageDetailEntity e : emkMOutStorageDetailEntityList){
				detailMap.put(e.getId(),e);
			}

			if(!t.getState().equals("0")){
				j.setMsg("销售开单已审核无法进行修改");
				j.setSuccess(false);
				return j;
			}
			emkMOutStorage.setState("0");
			emkMOutStorage.setPayState("0");
			emkMOutStorage.setRecevieState("0");

			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			emkMOutStorage.setState("0");
			emkMOutStorage.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkMOutStorage, t);
			systemService.saveOrUpdate(t);
			//判断是否存在客户，如果没有则创建
			YmkCustomEntityA ymkCustom = systemService.singleResult("from YmkCustomEntityA where cusName='"+emkMOutStorage.getCusName()+"' and orgCode='"+orgCode+"'");
			if(Utils.isEmpty(ymkCustom)){
				ymkCustom = new YmkCustomEntityA();
				ymkCustom.setStatus("0");
				Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(daan_num, 3)),0)+1 AS signed) orderNum from ymk_custom where org_code=? ",user.getCurrentDepart().getOrgCode());
				ymkCustom.setDaanNum( "KHDA1" + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
				ymkCustom.setCusName(emkMOutStorage.getCusName());
				ymkCustom.setCusNum( String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
				ymkCustom.setCusCode(ChineseToEnglish.getPinYinHeadChar(ymkCustom.getCusName()));
				ymkCustom.setZlxr(emkMOutStorage.getA01a07a08());
				ymkCustom.setAddress(emkMOutStorage.getA01a07a07());
				ymkCustom.setWorkphone(emkMOutStorage.getA01a07a09());
				ymkCustom.setDepartId(tsDepart.getId());
				ymkCustom.setOrgCode(tsDepart.getOrgCode());
				ymkCustom.setBcqkMoney("0");

				systemService.save(ymkCustom);
				emkMOutStorage.setA01a07a02(ymkCustom.getCusNum());
			}
			Class c = Class.forName(EmkMOutStorageDetailEntity.class.getName());

			Method show = null;

			YmkCustomEntity ymkCustomEntity = systemService.singleResult("from YmkCustomEntity where cusNum='"+emkMOutStorage.getA01a07a02()+"' and orgCode='"+orgCode+"'");
			if(Utils.notEmpty(emkMOutStorage.getBcqkMoney())){
				ymkCustomEntity.setBcqkMoney(emkMOutStorage.getBcqkMoney());
			}

			List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and org_code=? order by order_num asc",orgCode);
			EmkMOutStorageDetailEntity emkMOutStorageDetailEntity = null;
			List<EmkProductEntity> emkProductEntityList = null;
			EmkStorageLogEntity storageLogEntity = null;
			EmkStorageLogEntity newStorageLogEntity = null;
			EmkProductEntity emkProductEntity = null;
			dataRows = map.get("orderMxListID");
			if (Utils.notEmpty(dataRows)) {

				systemService.executeSql("delete from emk_m_out_storage_detail where out_storage_id = ? ",t.getId());
				int rows = Integer.parseInt(dataRows);
				EmkMOutStorageDetailEntity old = null;

				for (int i = 0; i < rows; i++) {
					emkMOutStorageDetailEntity = new EmkMOutStorageDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						if(Utils.notEmpty(map.get("orderMxList["+i+"].id"))){
							old = detailMap.get(map.get("orderMxList["+i+"].id"));
						}
						emkMOutStorageDetailEntity.setDepartId(t.getDepartId());
						emkMOutStorageDetailEntity.setOrgCode(t.getOrgCode());
						emkMOutStorageDetailEntity.setOutStorageId(t.getId());
						emkMOutStorageDetailEntity.setPriceId(map.get("orderMxList["+i+"].priceId"));
						emkMOutStorageDetailEntity.setPriceNo(map.get("orderMxList["+i+"].priceNo"));
						emkMOutStorageDetailEntity.setStoId(map.get("orderMxList["+i+"].stoId"));

						for(Map category : categoryEntities){
							if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
								String m0 = "setA"+category.get("code").toString().substring(1);
								show = c.getMethod(m0,String.class);
								show.invoke(emkMOutStorageDetailEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
							}
						}
						systemService.save(emkMOutStorageDetailEntity);
						//判断在商品管理是否有存在，不存在则创建
						emkProductEntity = saveEmkProductEntity(emkMOutStorageDetailEntity,orgCode,tsDepart);
						//查询库存数据和保存库存数据日记
						storageLogEntity = new EmkStorageLogEntity();
						EmkStorageEntity storageEntity = findStorageAndSaveLog(emkMOutStorageDetailEntity,emkMOutStorage,orgCode,storageLogEntity,"编辑销售开单");
						//查询旧库存数据
						EmkStorageEntity oldstorageEntity = findOldStorage(oldT,old,orgCode);
						//变更出货仓库
						if(Utils.notEmpty(emkMOutStorage.getStorageId())){
							if(!emkMOutStorage.getStorageId().equals(oldT.getStorageId())){
								if(Utils.notEmpty(oldstorageEntity)){
									saveOldStorage(oldstorageEntity,old,storageLogEntity);
								}
								if(Utils.notEmpty(storageEntity)){
									systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?+?) where id=? ",
											emkMOutStorageDetailEntity.getA01a09a02(),old.getA01a09a02(),storageEntity.getId());
									systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
									storageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
									saveOldLog1(storageLogEntity,oldstorageEntity);
								}else {
									saveNewStorage(emkMOutStorageDetailEntity,t,emkProductEntity,storageLogEntity);
								}
								j.setSuccess(true);
								j.setMsg(message);
								return j;

							}
						}
						//变更货位
						if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a05())){
							if(!emkMOutStorageDetailEntity.getA01a09a05().equals(old.getA01a09a05())){
								if(Utils.notEmpty(oldstorageEntity)){
									saveOldStorage(oldstorageEntity,old,storageLogEntity);
								}
								if(Utils.notEmpty(storageEntity)){
									systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?+?) where id=? ",
											emkMOutStorageDetailEntity.getA01a09a02(),old.getA01a09a02(),storageEntity.getId());
									systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
								}else {
									saveNewStorage(emkMOutStorageDetailEntity,t,emkProductEntity,storageLogEntity);
								}
								j.setSuccess(true);
								j.setMsg(message);
								return j;

							}
						}
						if(Utils.isEmpty(storageEntity)){
							saveNewStorage(emkMOutStorageDetailEntity,t,emkProductEntity,storageLogEntity);

							if(Utils.notEmpty(oldstorageEntity)){
								saveOldStorage(oldstorageEntity,old,storageLogEntity);
								/*systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",
										old.getA01a09a02(),oldstorageEntity.getId());
								systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", oldstorageEntity.getId());

								newStorageLogEntity = new EmkStorageLogEntity();
								MyBeanUtils.copyBeanNotNull2Bean(storageLogEntity,newStorageLogEntity);
								newStorageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
								saveOldLog2(newStorageLogEntity,oldstorageEntity,old);*/
							}

						}else{
							if(Utils.notEmpty(old)){
								systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?+?) where id=? ",
										emkMOutStorageDetailEntity.getA01a09a02(),old.getA01a09a02(),storageEntity.getId());
							}else{
								systemService.executeSql("update emk_storage set a01a09a12=? where id=? ",emkMOutStorageDetailEntity.getA01a09a12(),storageEntity.getId());
							}
							systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
							storageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
							saveOldLog1(storageLogEntity,oldstorageEntity);
						}
						systemService.save(storageLogEntity);
					}
				}
				EmkYsPayEntity emkFinanceYfCheckEntity = systemService.singleResult("from EmkYsPayEntity where orgCode='"+orgCode+"' and" +
						"	yfCheckNo='"+t.getA01a07a01()+"'");
				emkFinanceYfCheckEntity.setStorageId(t.getStorageId());
				emkFinanceYfCheckEntity.setStorageName(t.getStorageName());
				emkFinanceYfCheckEntity.setBankAccount(t.getBankMoney());
				emkFinanceYfCheckEntity.setBankType(t.getA01a07a06());
				emkFinanceYfCheckEntity.setCusName(t.getCusName());
				emkFinanceYfCheckEntity.setCusNum(t.getA01a07a02());

				emkFinanceYfCheckEntity.setYfMoney(Double.parseDouble(Utils.notEmpty(t.getYfMoney()) ? t.getYfMoney():"0"));
				emkFinanceYfCheckEntity.setMoney(Double.parseDouble(Utils.notEmpty(t.getMoney()) ? t.getMoney():"0"));
				emkFinanceYfCheckEntity.setQkMoney(Double.parseDouble(Utils.notEmpty(t.getYfMoney()) ? t.getYfMoney():"0"));
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doApproval")
	@ResponseBody
	public AjaxJson doApproval(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销售开单审核成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if(Utils.isEmpty(emkMOutStorage.getId())){
				for (String id : map.get("ids").toString().split(",")) {
					EmkMOutStorageEntity inStorageEntity = systemService.getEntity(EmkMOutStorageEntity.class, id);
					if (!inStorageEntity.getState().equals("0")) {
						message = "存在已审核的销售开单，请重新选择销售开单！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkMOutStorage.getId());
			}
			if (flag == 0) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkMOutStorageEntity t = systemService.get(EmkMOutStorageEntity.class, id);
					t.setState("1");
				}
			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "销售开单审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doCancelApproval")
	@ResponseBody
	public AjaxJson doCancelApproval(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销售开单反审核成功";
		try {
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			for (String id : map.get("ids").toString().split(",")) {
				EmkMOutStorageEntity t = systemService.get(EmkMOutStorageEntity.class, id);
				t.setState("0");
			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "销售开单反审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doPay")
	@ResponseBody
	public AjaxJson doPay(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销售开单收款成功";
		try {
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			EmkMOutStorageEntity t = systemService.get(EmkMOutStorageEntity.class, map.get("id"));
			if("1".equals(t.getPayState())){
				j.setMsg("销售开单已收款");
				j.setSuccess(false);
				return j;
			}
			if("1".equals(map.get("payState"))){
				EmkBankRecordEntity bankRecordEntity = systemService.singleResult("from EmkBankRecordEntity where bankType='"+t.getBankMoney()+"' and orgCode='"+orgCode+"' and remark='期初余额'");
				if(Utils.isEmpty(bankRecordEntity)){
					j.setMsg("资金账户不存在");
					j.setSuccess(false);
					return j;
				}
				EmkBankRecordEntity emkBankRecordEntity = new EmkBankRecordEntity();
				MyBeanUtils.copyBeanNotNull2Bean(bankRecordEntity,emkBankRecordEntity);
				emkBankRecordEntity.setId(null);
				double blance = Double.parseDouble(bankRecordEntity.getBalance())+Double.parseDouble(t.getShMoney());
				emkBankRecordEntity.setBalance(String.valueOf(blance));
				emkBankRecordEntity.setOutcome(t.getShMoney());
				emkBankRecordEntity.setIncome("");
				emkBankRecordEntity.setDealDate(DateUtil.getCurrentTimeString(null));
				emkBankRecordEntity.setRemark("销售开单收款");
				bankRecordEntity.setBalance(emkBankRecordEntity.getBalance());
				systemService.save(emkBankRecordEntity);
				EmkBankRecordLogEntity emkBankRecordLogEntity = new EmkBankRecordLogEntity();
				saveBankRecord(String.valueOf(blance),bankRecordEntity,emkBankRecordLogEntity);
				systemService.save(emkBankRecordLogEntity);
				t.setPayState(map.get("payState"));
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			message = "销售开单收款失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doRecevie")
	@ResponseBody
	public AjaxJson doRecevie(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销售开单发货成功";
		try {
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			EmkMOutStorageEntity t = systemService.get(EmkMOutStorageEntity.class, map.get("id"));
			t.setRecevieState(map.get("recevieState"));

			List<EmkMInStorageDetailEntity> inStorageDetailEntityList = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?",t.getId());

			EmkStorageLogEntity storageLogEntity = null;
			for(EmkMInStorageDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
				//保存库存数据和日记

			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "销售开单发货失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doPrintPDF")
	public String doPrintPDF(String ids, EmkMOutStorageEntity emkMOutStorage, HttpServletRequest request, HttpServletResponse response) {
		String message = null;
		try {
			for (String id : ids.split(",")) {
				String fileName = "c:\\PDF\\"+ DateUtil.format(new Date(),"yyyyMMddHHmmss")+".pdf";
				File file = new File(fileName);
				File dir = file.getParentFile();
				if (!dir.exists()) {
					dir.mkdirs();
				}
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				emkMOutStorage =  systemService.get(EmkMOutStorageEntity.class, id);
				List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?",id);
				new createPdf(file).generateSalePDF(emkMOutStorage,rkglMxEntities,findProcessList(id));
				String fFileName = "c:\\PDF\\F"+DateUtil.format(new Date(),"yyyyMMddHHmmss")+".pdf";
				WaterMark.waterMark(fileName,fFileName, "销售单");
				file.delete();
				WebFileUtils.downLoad(fFileName,response,false);
				file = new File(fFileName);
				file.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "导出PDF失败";
			throw new BusinessException(e.getMessage());
		}
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@RequestMapping(params = "doPrintPDFForOut")
	public String doPrintPDFForOut(String ids, EmkMOutStorageEntity emkMOutStorage, HttpServletRequest request, HttpServletResponse response) {
		String message = null;
		try {
			for (String id : ids.split(",")) {
				String fileName = "c:\\PDF\\"+ DateUtil.format(new Date(),"yyyyMMddHHmmss")+".pdf";
				File file = new File(fileName);
				File dir = file.getParentFile();
				if (!dir.exists()) {
					dir.mkdirs();
				}
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				emkMOutStorage =  systemService.get(EmkMOutStorageEntity.class, id);
				List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?",id);
				new createPdf(file).generateCkdPDF(emkMOutStorage,rkglMxEntities,findProcessList(id));
				String fFileName = "c:\\PDF\\F"+DateUtil.format(new Date(),"yyyyMMddHHmmss")+".pdf";
				WaterMark.waterMark(fileName,fFileName, "出库单");
				file.delete();
				WebFileUtils.downLoad(fFileName,response,false);
				file = new File(fFileName);
				file.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "导出PDF失败";
			throw new BusinessException(e.getMessage());
		}
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 销售单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest req) {
		req.getSession().setAttribute("storageId","");
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A07' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.setAttribute("headcategoryEntities",categoryEntities);
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
		TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='销售订单前缀' and departId='"+tsDepart.getId()+"'");
		Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(a01a07a01, 4)),0)+1 AS signed) orderNum from emk_m_out_storage where org_code=?",orgCode);
		emkMOutStorage.setA01a07a01(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",orgCode);

		req.setAttribute("emkMOutStoragePage", emkMOutStorage);
		req.getSession().setAttribute("orderFinish", "");
		req.getSession().setAttribute("productId","");

		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-add");
	}
	@RequestMapping(params = "goAdd0")
	public ModelAndView goAdd2(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		req.getSession().setAttribute("storageName","");

		if (StringUtil.isNotEmpty(emkMOutStorage.getId())) {
			emkMOutStorage = emkMOutStorageService.getEntity(EmkMOutStorageEntity.class, emkMOutStorage.getId());
			req.setAttribute("emkMOutStoragePage", emkMOutStorage);
		}
		req.getSession().setAttribute("orderFinish", "");

		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-add0");
	}
	/**
	 * 销售单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A07' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.setAttribute("headcategoryEntities",categoryEntities);

		if (StringUtil.isNotEmpty(emkMOutStorage.getId())) {
			emkMOutStorage = emkMOutStorageService.getEntity(EmkMOutStorageEntity.class, emkMOutStorage.getId());
		}
		req.setAttribute("emkMOutStoragePage", emkMOutStorage);
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.getSession().setAttribute("productId","");

		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-update");
	}
	@RequestMapping(params = "goUpdate0")
	public ModelAndView goUpdate0(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkMOutStorage.getId())) {
			emkMOutStorage = emkMOutStorageService.getEntity(EmkMOutStorageEntity.class, emkMOutStorage.getId());
			req.getSession().setAttribute("storageName",emkMOutStorage.getStorageName());
		}
		req.setAttribute("emkMOutStoragePage", emkMOutStorage);

		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-update0");
	}
	@RequestMapping(params="goUpdate2")
	public ModelAndView goUpdate2(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest req) {
		emkMOutStorage = emkMOutStorageService.getEntity(EmkMOutStorageEntity.class, emkMOutStorage.getId());
		req.setAttribute("emkMOutStoragePage", emkMOutStorage);
		if("2".equals(emkMOutStorage.getType())){
			return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-update2");
		}else{
			return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-update3");
		}
	}

	@RequestMapping(params = "goPay")
	public ModelAndView goPay(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkMOutStorage.getId())) {
			emkMOutStorage = systemService.getEntity(EmkMOutStorageEntity.class, emkMOutStorage.getId());
			req.setAttribute("emkMOutStoragePage", emkMOutStorage);
		}
		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-pay");
	}
	@RequestMapping(params = "goRecevie")
	public ModelAndView goRecevie(EmkMOutStorageEntity emkMOutStorage, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkMOutStorage.getId())) {
			emkMOutStorage = systemService.getEntity(EmkMOutStorageEntity.class, emkMOutStorage.getId());
			req.setAttribute("emkMOutStoragePage", emkMOutStorage);
		}
		return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-recevie");
	}

	@RequestMapping(params = "getProductInfo")
	@ResponseBody
	public AjaxJson getProductInfo(EmkMInStorageDetailEntity emkMInStorageDetailEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
//		emkMInStorageDetailEntity = systemService.singleResult("from EmkCancelOrderDetailEntity where proNum='"+emkMInStorageDetailEntity.getProNum()+"' and inStorageId='"+emkMInStorageDetailEntity.getInStorageId()+"'");
		try {
			if(Utils.notEmpty(emkMInStorageDetailEntity)){
				systemService.saveOrUpdate(emkMInStorageDetailEntity);
				j.setSuccess(true);
			}else{
				j.setSuccess(false);
			}
			j.setObj(emkMInStorageDetailEntity);
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 打印 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "jump")
	public Object jump(HttpServletRequest req) {
		Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
		req.setAttribute("url", map.get("url")+"&ckNo="+map.get("ckNo"));
		return "forward:/context/"+map.get("r")+".jsp";
	}
	/**
	 * 打印预览 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "printPreview")
	public ModelAndView printPreview(HttpServletRequest req) {
		Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
		EmkMOutStorageEntity emkMOutStorage = systemService.findUniqueByProperty(EmkMOutStorageEntity.class,"ckNo",map.get("ckNo"));
		req.setAttribute("emkMOutStorage",emkMOutStorage);

		List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=? ",  emkMOutStorage.getId());
		req.setAttribute("rkglMxEntities",rkglMxEntities);

		List<Map> dataList = new ArrayList<>();
		List<EmkMInStorageDetailEntity> subdataList = new ArrayList<>();
		int i= 0;
		Map b = null;

		for(EmkMInStorageDetailEntity mInStorageDetailEntity : rkglMxEntities){
			if(i == 21){
				b = new HashMap();
				subdataList.add(mInStorageDetailEntity);
				b.put("subdataList",subdataList);
				dataList.add(b);
				i = 0;
				subdataList = new ArrayList<>();
			}else{
				subdataList.add(mInStorageDetailEntity);
				i++;
			}
		}
		if(subdataList.size()>0){
			b = new HashMap();
			b.put("subdataList",subdataList);
			dataList.add(b);
		}
		req.setAttribute("dataList",dataList);
		return new ModelAndView("com/emk/bound/moutstorage/printPreview");

	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkMOutStorageController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkMOutStorageEntity emkMOutStorage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		/*CriteriaQuery cq = new CriteriaQuery(EmkMOutStorageEntity.class, dataGrid);
		cq.eq("type","2");
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkMOutStorage, request.getParameterMap());
		List<EmkMOutStorageEntity> emkMOutStorages = this.emkMOutStorageService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"销售单");
		modelMap.put(NormalExcelConstants.CLASS,EmkMOutStorageEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("销售单列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkMOutStorages);*/
		try {
			Map param = ParameterUtil.getParamMaps(request.getParameterMap());
			CriteriaQuery cq = new CriteriaQuery(EmkMOutStorageEntity.class, dataGrid);
			//cq.eq("type","2");
			cq.add(Restrictions.sqlRestriction("(FIND_IN_SET({alias}.type,'"+emkMOutStorage.getType()+"'))"));
			cq.addOrder("outDate", SortDirection.desc);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkMOutStorage, request.getParameterMap());
			if(Utils.notEmpty(emkMOutStorage.getId())){
				cq.add(Restrictions.sqlRestriction("(FIND_IN_SET({alias}.id,'"+emkMOutStorage.getId()+"'))"));
			}
			List<EmkMOutStorageEntity> emkMOutStorages = emkMOutStorageService.getListByCriteriaQuery(cq,false);
			String savepath = "c:\\销售出库单\\";
			File file = new File(savepath);
			if(!file.exists())
			{
				file.mkdirs();
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			String path = savepath+"销售出库单"+DateUtil.getCurrentTimeString("yyyyMMdd")+".xls";
			for(EmkMOutStorageEntity emkMOutStorageEntity : emkMOutStorages){
				List<String> headList = new ArrayList<String>();
				List<String> fieldList = new ArrayList<String>();

				headList.add("产品名称");
				fieldList.add("pro_zn_name");
			/*headList.add("产品编号");
			fieldList.add("pro_num");*/
				headList.add("规格");
				fieldList.add("standards");
				headList.add("型号");
				fieldList.add("brand");
				headList.add("生产批号");
				fieldList.add("betch");
				headList.add("生产日期");
				fieldList.add("sc_date");
				headList.add("有效日期");
				fieldList.add("limit_date");
				headList.add("单位");
				fieldList.add("unit");
				headList.add("数量");
				fieldList.add("total");
				headList.add("销售价");
				fieldList.add("xsj");
				headList.add("销售金额");
				fieldList.add("xsje");
				headList.add("注册证号");
				fieldList.add("zc_num");
				headList.add("生产企业");
				fieldList.add("scqy");
				headList.add("许可证号");
				fieldList.add("lincese");
				headList.add("储运条件");
				fieldList.add("cytj");

				String sql = "select t.pro_zn_name,t.standards,t.brand,t.betch,t.sc_date,t.limit_date,sum(1) total,t.unit,'' xsj,'' xsje,t.zc_num,t.scqy,t.lincese," +
						"	t.cytj,GROUP_CONCAT(t.pro_num) pro_num from  emk_m_in_storage_detail t	\n" +
						"	where t.in_storage_id=? group by t.pro_zn_name,t.brand,t.betch";
				List<Map<String,Object>> rkglMxEntities = systemService.findForJdbc(sql.toString(),emkMOutStorageEntity.getId());
				ExcelUtil.createSaleExcel(workbook,path,emkMOutStorageEntity,headList,fieldList, rkglMxEntities);
			}
			WebFileUtils.downLoad(path, response, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkMOutStorageEntity emkMOutStorage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"销售单");
    	modelMap.put(NormalExcelConstants.CLASS,EmkMOutStorageEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("销售单列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkMOutStorageEntity> listEmkMOutStorageEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkMOutStorageEntity.class,params);
				for (EmkMOutStorageEntity emkMOutStorage : listEmkMOutStorageEntitys) {
					emkMOutStorageService.save(emkMOutStorage);
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
	@ApiOperation(value="销售单列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkMOutStorageEntity>> list() {
		List<EmkMOutStorageEntity> listEmkMOutStorages=emkMOutStorageService.getList(EmkMOutStorageEntity.class);
		return Result.success(listEmkMOutStorages);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取销售单信息",notes="根据ID获取销售单信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkMOutStorageEntity task = emkMOutStorageService.get(EmkMOutStorageEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取销售单信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建销售单")
	public ResponseMessage<?> create(@ApiParam(name="销售单对象")@RequestBody EmkMOutStorageEntity emkMOutStorage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkMOutStorageEntity>> failures = validator.validate(emkMOutStorage);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkMOutStorageService.save(emkMOutStorage);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("销售单信息保存失败");
		}
		return Result.success(emkMOutStorage);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新销售单",notes="更新销售单")
	public ResponseMessage<?> update(@ApiParam(name="销售单对象")@RequestBody EmkMOutStorageEntity emkMOutStorage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkMOutStorageEntity>> failures = validator.validate(emkMOutStorage);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkMOutStorageService.saveOrUpdate(emkMOutStorage);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新销售单信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新销售单信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除销售单")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkMOutStorageService.deleteEntityById(EmkMOutStorageEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("销售单删除失败");
		}

		return Result.success();
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkMOutStorageEntity emkMOutStorageEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销售单提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if (Utils.isEmpty(emkMOutStorageEntity.getId())) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkMOutStorageEntity outStorageEntity = systemService.getEntity(EmkMOutStorageEntity.class, id);
					if (!outStorageEntity.getState().equals("0")) {
						message = "存在已提交的销售单，请重新选择在提交销售单！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkMOutStorageEntity.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkMOutStorageEntity t = emkMOutStorageService.get(EmkMOutStorageEntity.class, id);

					variables.put("inputUser", t.getId());
					message = "操作成功";

					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
					if(Utils.isEmpty(b)){
						b = new EmkApprovalEntity();
						EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
//						ApprovalUtil.saveApproval(b,6,t.getCkNo(),t.getId(),user);
						systemService.save(b);
						ApprovalUtil.saveApprovalDetail(approvalDetailEntity,b.getId(),"销售单","outstorageTask","提交",user);
						systemService.save(approvalDetailEntity);
					}

					List<Task> task = taskService.createTaskQuery().taskAssignee(id).list();
					TSUser makerUser = systemService.findUniqueByProperty(TSUser.class,"userName",t.getCreateBy());
					TSUser bpmUser = null;
					if (task.size() > 0) {
						bpmUser = systemService.get(TSUser.class,b.getBpmSherId());
					}else{
						bpmUser = systemService.get(TSUser.class,b.getCommitId());
					}
					//保存审批意见
					EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(),user,b,map.get("advice"));

					if (task.size() > 0) {
						Task task1 = (Task)task.get(task.size() - 1);
						if (task1.getTaskDefinitionKey().equals("outstorageTask")) {
							List<EmkMInStorageDetailEntity> inStorageDetailEntityList = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?",t.getId());
							for(EmkMInStorageDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
								/*Map storageEntity = systemService.findOneForJdbc("select s.* from emk_storage s where  FIND_IN_SET(s.storage_name,(SELECT GROUP_CONCAT(ss.storage_name) from emk_storage_set ss  \n" +
												"LEFT JOIN emk_stroage_custom sc on ss.id=sc.stroage_id where sc.cus_num=?)) and s.pro_num=?  and s.brand=? and cus_num is null",
										t.getCusNum(),inStorageDetailEntity.getProNum(),inStorageDetailEntity.getBrand());
								if(Utils.isEmpty(storageEntity)){
									message = "该客户对应的仓库不存在产品名称："+inStorageDetailEntity.getProZnName()+"，产品编号："+inStorageDetailEntity.getProNum()+"，型号："+inStorageDetailEntity.getBrand()+"，无法出库操作";
									j.setMsg(message);
									j.setSuccess(false);
									return j;
								}*/
							}

							t.setState("1");
							b.setStatus(1);
							taskService.complete(task1.getId(), variables);

							b.setProcessName("销售申请单");
							saveApprvoalDetail(approvalDetail,"重新提交的销售申请单","outstorageTask",0,"重新提交销售申请单");
							saveSmsAndEmailForMany("采购员","采购员审核","您有【"+b.getCreateName()+"】重新提交的销售申请单，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
						}
						if (task1.getTaskDefinitionKey().equals("checkTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("type","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(24);
								approvalDetail.setBpmName("采购员审核");
								t.setState("24");
								approvalDetail.setApproveStatus(0);

								b.setProcessName("采购员审核");
								saveSmsAndEmailForMany("仓库员","采购员审核","您有【"+b.getCreateName()+"】审核过的销售单，单号："+b.getWorkNum()+"，请及时处理。",user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"采购员审核","checkTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"checkTask","outstorageTask","销售单");
								systemService.executeSql("delete from act_hi_actinst  where proc_inst_id_=? and act_id_=? ",task1.getProcessInstanceId(),"isPass");

								t.setState("0");
								b.setStatus(0);
								b.setBpmStatus("1");
								approvalDetail.setApproveStatus(1);
								saveSmsAndEmailForOne("采购员审核","您有【"+user.getRealName()+"】回退的销售单，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("jhhsTask")) {
							List<EmkMInStorageDetailEntity> inStorageDetailEntityList = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=? and (flag!=1 or flag = '' or flag is null)",t.getId());
							if(inStorageDetailEntityList.size()==0){
								taskService.complete(task1.getId(), variables);
								b.setStatus(25);
								approvalDetail.setBpmName("拣货核实");
								t.setState("25");
								approvalDetail.setApproveStatus(0);

								b.setProcessName("拣货核实");
							}else{
								message = "存在拣货未核实的产品，请确认后在操作";
								j.setMsg(message);
								j.setSuccess(false);
								return j;
							}
						}
						if(task1.getTaskDefinitionKey().equals("ckTask")) {
							EmkStorageLogEntity storageLogEntity = new EmkStorageLogEntity();
							List<EmkMInStorageDetailEntity> inStorageDetailEntityList = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?",t.getId());
							for(EmkMInStorageDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
								/*Map storageEntity = systemService.findOneForJdbc("select s.* from emk_storage s where  FIND_IN_SET(s.storage_name,(SELECT GROUP_CONCAT(ss.storage_name) from emk_storage_set ss  \n" +
												"LEFT JOIN emk_stroage_custom sc on ss.id=sc.stroage_id where sc.cus_num=?)) and s.pro_num=?  and s.brand=?",
										t.getCusNum(),inStorageDetailEntity.getProNum(),inStorageDetailEntity.getBrand());
								if(Utils.isEmpty(storageEntity)){
									message = "该客户对应的仓库不存在产品名称："+inStorageDetailEntity.getProZnName()+"，产品编号："+inStorageDetailEntity.getProNum()+"，型号："+inStorageDetailEntity.getBrand()+"，无法出库操作";
									j.setMsg(message);
									j.setSuccess(false);
									return j;
								}*/
							}

							for(EmkMInStorageDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
								storageLogEntity = new EmkStorageLogEntity();

								/*EmkStorageEntity storageEntity = systemService.singleResult("from EmkStorageEntity where proNum='"+inStorageDetailEntity.getProNum()+"'" +
										" and  brand='"+inStorageDetailEntity.getBrand()+"'");

								MyBeanUtils.copyBeanNotNull2Bean(inStorageDetailEntity,storageLogEntity);
								storageLogEntity.setId(null);
								storageLogEntity.setRkNo(t.getCkNo());
								storageLogEntity.setType(t.getType());*/

								systemService.save(storageLogEntity);

							}
							t.setState("2");
							b.setStatus(2);
							approvalDetail.setBpmName("仓库员出库");

							taskService.complete(task1.getId(), variables);

							saveSmsAndEmailForOne("仓库员出库","您有【"+user.getRealName()+"】仓库员已出库，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());

						}
						systemService.save(approvalDetail);

					}else {
						EmkStorageLogEntity storageLogEntity = new EmkStorageLogEntity();
						/*List<EmkCancelOrderDetailEntity> inStorageDetailEntityList = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?",t.getId());
						for(EmkCancelOrderDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
							Map storageEntity = systemService.findOneForJdbc("select s.* from emk_storage s where  FIND_IN_SET(s.storage_name,(SELECT GROUP_CONCAT(ss.storage_name) from emk_storage_set ss  \n" +
											"LEFT JOIN emk_stroage_custom sc on ss.id=sc.stroage_id where sc.cus_num=?)) and s.pro_num=?  and s.brand=? and cus_num is null",
									t.getCusNum(),inStorageDetailEntity.getProNum(),inStorageDetailEntity.getBrand());
							if(Utils.isEmpty(storageEntity)){
								message = "该客户对应的仓库不存在产品名称："+inStorageDetailEntity.getProZnName()+"，产品编号："+inStorageDetailEntity.getProNum()+"，型号："+inStorageDetailEntity.getBrand()+"，无法出库操作";
								j.setMsg(message);
								j.setSuccess(false);
								return j;
							}
						}*/
						ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("sale", "emkMOutStorage", variables);
						task = taskService.createTaskQuery().taskAssignee(id).list();
						Task task1 = task.get(task.size() - 1);
						taskService.complete(task1.getId(), variables);

						saveSmsAndEmailForMany("采购员","采购员审核","您有【"+b.getCreateName()+"】提交的销售单，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
						t.setState("1");
						b.setStatus(1);
						b.setBpmStatus("0");
						b.setProcessName("销售申请单");

					}
					systemService.saveOrUpdate(t);
					systemService.saveOrUpdate(b);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "销售单提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}



	@RequestMapping(params="goWork")
	public ModelAndView goWork(EmkMOutStorageEntity emkMOutStorageEntity, HttpServletRequest req) {
		emkMOutStorageEntity = emkMOutStorageService.getEntity(EmkMOutStorageEntity.class, emkMOutStorageEntity.getId());
		req.setAttribute("emkOutStoragePage", emkMOutStorageEntity);
		if("2".equals(emkMOutStorageEntity.getType())){
			return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-work");
		}else{
			return new ModelAndView("com/emk/bound/moutstorage/emkMOutStorage-workck");
		}
	}

	@RequestMapping(params="goTime")
	public ModelAndView goTime(EmkMOutStorageEntity emkMOutStorageEntity, HttpServletRequest req, DataGrid dataGrid) {
		EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkMOutStorageEntity.getId());
		if(Utils.notEmpty(approvalEntity)){
			List<EmkApprovalDetailEntity> approvalDetailEntityList = systemService.findHql("from EmkApprovalDetailEntity where approvalId=? order by approveDate asc",approvalEntity.getId());
			req.setAttribute("approvalDetailEntityList", approvalDetailEntityList);
			req.setAttribute("approvalEntity", approvalEntity);
			emkMOutStorageEntity = emkMOutStorageService.getEntity(EmkMOutStorageEntity.class, emkMOutStorageEntity.getId());
			req.setAttribute("emkMOutStoragePage", emkMOutStorageEntity);

			req.setAttribute("createDate", org.jeecgframework.core.util.DateUtils.date2Str(approvalEntity.getCreateDate(), org.jeecgframework.core.util.DateUtils.datetimeFormat));
		}

		return new ModelAndView("com/emk/bound/moutstorage/time");
	}
}
