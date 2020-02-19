package com.emk.bound.minstorage.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.bill.proorder.entity.EmkProOrderEntity;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.bound.minstorage.entity.EmkMInStorageEntity;
import com.emk.bound.minstorage.entity.EmkMInStorageEntity2;
import com.emk.bound.minstorage.service.EmkMInStorageServiceI;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.bound.minstoragedetail.entity.EmkMInStorageDetailEntity;
import com.emk.bound.minstoragedetail.entity.EmkMInStorageOrderDetailEntity;
import com.emk.caiwu.bankrecord.entity.EmkBankRecordEntity;
import com.emk.caiwu.bankrecord.entity.EmkBankRecordLogEntity;
import com.emk.caiwu.yfcheck.entity.EmkFinanceYfCheckEntity;
import com.emk.product.product.entity.EmkProductEntity;
import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.storage.storage.entity.EmkStorageEntity;
import com.emk.storage.storagelog.entity.EmkStorageLogEntity;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import com.emk.util.*;

import com.emk.util.excel.ExcelUtil;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tools.ant.util.DateUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.util.*;
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

import java.io.OutputStream;
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
 * @Description: 采购入库申请表
 * @author onlineGenerator
 * @date 2018-09-15 11:33:47
 * @version V1.0   
 *
 */
@Api(value="EmkMInStorage",description="采购入库申请表",tags="emkMInStorageController")
@Controller
@RequestMapping("/emkMInStorageController")
public class EmkMInStorageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkMInStorageController.class);

	@Autowired
	private EmkMInStorageServiceI emkMInStorageService;

	@Autowired
	private Validator validator;


	/**
	 * 采购入库申请表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A03' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		request.setAttribute("headcategoryEntities",categoryEntities);
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorageList");
	}

	@RequestMapping(params = "list2")
	public ModelAndView list2(HttpServletRequest request) {

		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A03' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		request.setAttribute("headcategoryEntities",categoryEntities);
		request.getSession().setAttribute("emkProOrderDetailEntityList","");
		request.getSession().setAttribute("proOrderId","");

		request.getSession().setAttribute("headTb","");
		request.getSession().setAttribute("footTb","");
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorageList2");
	}

	@RequestMapping(params = "listZh")
	public ModelAndView list3(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorageListZh");
	}

	@RequestMapping(params = "listBill")
	public ModelAndView listBill(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorageListBill");
	}

	@RequestMapping(params="emkMInStorageDetailList")
	public ModelAndView rkglMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());

		if (Utils.notEmpty(map.get("inStorageId"))){
			List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",  map.get("inStorageId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorageDetailList");
	}

	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		request.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show from hm_category where PARENT_CODE='A01A09' and type='0' and org_code=? and code!='A01A09A13' order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
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
			List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=? ",  map.get("inStorageId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/minstorage/orderMxList");
	}

	@RequestMapping(params = "orderMxList2ForAdd")
	public ModelAndView orderMxList2ForAdd(HttpServletRequest request) {
		request.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show from hm_category where PARENT_CODE='A01A09' and type='4' and org_code=? and code!='A01A09A13' order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
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
			List<EmkMInStorageOrderDetailEntity> rkglMxEntities = systemService.findHql("from EmkMInStorageOrderDetailEntity where inStorageId=? ",  map.get("inStorageId"));
			request.setAttribute("emkProOrderDetailEntityList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/minstorage/orderMxList2ForAdd");
	}

	@RequestMapping(params = "orderMxLitsForKc")
	public ModelAndView orderMxListForKc(HttpServletRequest request) {
		request.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show from hm_category where PARENT_CODE='A01A09' and type='4' and org_code=? and code!='A01A09A13' order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		request.setAttribute("categoryEntities",categoryEntities);
		categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=?  order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
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
			List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=? ",  map.get("inStorageId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/minstorage/orderMxListForKc");
	}
	@RequestMapping(params = "orderMxList2")
	public ModelAndView orderMxList2(HttpServletRequest request) {
		request.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='0' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
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
		if (Utils.notEmpty(map.get("inStorageId"))) {
			List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=? ",  map.get("inStorageId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/minstorage/orderMxList2");
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
	public void datagrid(EmkMInStorageEntity emkMInStorage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkMInStorageEntity.class, dataGrid);
		//查询条件组装器
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if(!user.getUserName().equals("admin")){
			cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkMInStorage, request.getParameterMap());
		try{
			//自定义追加查询条件
			/*Map roleMap = (Map) request.getSession().getAttribute("ROLE");
			Map param = ParameterUtil.getParamMaps(request.getParameterMap());
			if(roleMap != null){
				if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("cky")){
					cq.eq("createBy",user.getUserName());
				}
			}
			if(Utils.notEmpty(param.get("proNum"))){
				cq.add(Restrictions.sqlRestriction("(FIND_IN_SET('"+param.get("proNum")+"',(select GROUP_CONCAT(md.pro_num) from emk_m_in_storage_detail md where md.in_storage_id={alias}.id)) )"));
			}*/
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		emkMInStorageService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 采购对账单
	 *
	 * @return
	 */
	@RequestMapping(params = "listAllByJdbc")
	public void listAllByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		String sql = "" ,countSql = "";
		TSUser user = ResourceUtil.getSessionUser();

		sql +=" SELECT m.a01a03a10 kdDate, m.a01a03a07 workNo, m.gys, md.a01a09a07 proName, m.a01a03a05 remark, " +
				"	md.a01a09a09 guig,md.a01a09a10 brand, md.a01a09a02 total, md.a01a09a11 unitName, md.a01a09a12 costPrice, " +
				"	CASE WHEN ( md.a01a09a28 IS NULL OR md.a01a09a28 = '' ) THEN md.a01a09a03 ELSE md.a01a09a26 END money FROM " +
				"	emk_m_in_storage m LEFT JOIN emk_m_in_storage_detail md ON m.id = md.in_storage_id" +
				"	where 1=1  ";

		if(Utils.notEmpty(map.get("workNo"))){
			sql += " and m.work_num like '%"+map.get("workNo")+"%'";
		}
		if(Utils.notEmpty(map.get("kdDate_begin"))){
			sql += " and m.a01a03a10 >= '"+map.get("kdDate_begin")+" 00:00:00'";
		}
		if(Utils.notEmpty(map.get("kdDate_end"))){
			sql += " and m.a01a03a10 <= '"+map.get("kdDate_end")+" 23:59:59'";
		}
		sql += " order by m.a01a03a07 asc ";

		countSql = "SELECT count(1) FROM ("+sql+") t9 ";

		sql += " limit ";
		if(dataGrid.getPage()==1){
			sql += " 0, "+dataGrid.getRows();
		}else{
			sql += (dataGrid.getPage()-1)*dataGrid.getRows()+","+dataGrid.getRows();
		}

		this.systemService.listAllByJdbc(dataGrid, sql, countSql);
		List<Map> results = dataGrid.getResults();
		int total = 0;
		double money = 0;
		DecimalFormat df = new DecimalFormat("0.00");

		for(Map he : results){
			total += Integer.parseInt(Utils.notEmpty(he.get("total")) ? he.get("total").toString():"0");
			money += Double.parseDouble(Utils.notEmpty(he.get("money")) ? he.get("money").toString():"0");
		}
		dataGrid.setFooter("kdDate:合计," +"total:" + total+",money:"+df.format(money));
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除采购入库申请表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkMInStorageEntity emkMInStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkMInStorage = systemService.getEntity(EmkMInStorageEntity.class, emkMInStorage.getId());
		message = "采购入库申请表删除成功";
		try{
			emkMInStorageService.delete(emkMInStorage);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "采购入库申请表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除采购入库申请表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购入库申请表删除成功";
		try{
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			List<EmkStorageEntity> storageEntityList = new ArrayList<>();
			for(String id:ids.split(",")){
				EmkMInStorageEntity emkMInStorage = systemService.getEntity(EmkMInStorageEntity.class, id);
				if("1".equals(emkMInStorage.getState())){
					j.setMsg("采购入库已审核，无法删除");
					j.setSuccess(false);
					return j;
				}
				if("1".equals(emkMInStorage.getPayState())){
					j.setMsg("采购入库已付款，无法删除");
					j.setSuccess(false);
					return j;
				}
				if("1".equals(emkMInStorage.getRecevieState())){
					j.setMsg("采购入库已收货，无法删除");
					j.setSuccess(false);
					return j;
				}
				EmkStorageLogEntity storageLogEntity = null;
				EmkStorageEntity storageEntity = null;
				List<EmkMInStorageDetailEntity> mInStorageDetailEntityList = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",emkMInStorage.getId());
				for(EmkMInStorageDetailEntity emkMInStorageDetailEntity : mInStorageDetailEntityList){
					String hql = "from EmkStorageEntity where 1=1 ";
					if(Utils.notEmpty(emkMInStorage.getStorageId())){
						hql += " and storageId='"+emkMInStorage.getStorageId()+"'";
					}
					if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a07())){
						hql += " and a01a09a07='"+emkMInStorageDetailEntity.getA01a09a07()+"'";
					}
					if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a08())){
						hql += " and a01a09a08='"+emkMInStorageDetailEntity.getA01a09a08()+"'";
					}else{
						hql += " and flag=1";
					}
					if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a10())){
						hql += " and a01a09a10='"+emkMInStorageDetailEntity.getA01a09a10()+"'";
					}
					if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a05())){
						hql += " and a01a09a05='"+emkMInStorageDetailEntity.getA01a09a05()+"'";
					}
					if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a04())){
						hql += " and a01a09a04='"+emkMInStorageDetailEntity.getA01a09a04()+"'";
					}
					if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a06())){
						hql += " and a01a09a06='"+emkMInStorageDetailEntity.getA01a09a06()+"'";
					}
					hql += " and orgCode='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"'";
					storageEntity = systemService.singleResult(hql);

					storageLogEntity = new EmkStorageLogEntity();
					storageLogEntity.setRkNo(emkMInStorage.getA01a03a07());
					storageLogEntity.setType("0");
					storageLogEntity.setProNum(emkMInStorageDetailEntity.getA01a09a08());
					storageLogEntity.setProZnName(emkMInStorageDetailEntity.getA01a09a07());
					storageLogEntity.setMarkDate(DateUtil.getLogTime());
					storageLogEntity.setRemark("删除采购入库单");
					storageLogEntity.setOrgCode(orgCode);
					storageLogEntity.setDepartId(tsDepart.getId());
					storageLogEntity.setStorageId(emkMInStorage.getA01a03a11());
					storageLogEntity.setStorageName(emkMInStorage.getStorageName());

					if(Utils.notEmpty(storageEntity)){
						systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?) where id=? ",emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
						//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
						saveOldLog1(storageLogEntity,storageEntity);
						storageEntityList.add(storageEntity);
					}
				}
				systemService.executeSql("delete from emk_m_in_storage_detail where in_storage_id=?", id);
				emkMInStorageService.delete(emkMInStorage);
				for(EmkStorageEntity emkStorageEntity : storageEntityList){
					updateKccb(emkStorageEntity,orgCode);
				}
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "采购入库申请表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除采购入库申请表
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDelForOrder")
	@ResponseBody
	public AjaxJson doBatchDelForOrder(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购订单删除成功";
		try{
			EmkProOrderEntity emkProOrderEntity = null;
			for(String id:ids.split(",")){
				EmkMInStorageEntity emkMInStorage = systemService.getEntity(EmkMInStorageEntity.class, id);
				emkProOrderEntity = systemService.get(EmkProOrderEntity.class,emkMInStorage.getProOrderId());
				emkProOrderEntity.setState("0");
				systemService.executeSql("delete from emk_approval where form_id=?", id);
				systemService.executeSql("delete from emk_m_in_storage_detail where in_storage_id=?", id);
				emkMInStorageService.delete(emkMInStorage);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "采购订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 添加采购入库申请表
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkMInStorageEntity emkMInStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购入库申请表添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");

			emkMInStorage.setState("0");
			emkMInStorage.setPayState("0");
			emkMInStorage.setRecevieState("0");

			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			Class c = Class.forName(EmkMInStorageDetailEntity.class.getName());
			Method show = null;

			emkMInStorage.setOrgCode(tsDepart.getOrgCode());
			emkMInStorage.setDepartId(tsDepart.getId());
			TSUser tsUser = systemService.findUniqueByProperty(TSUser.class,"userName",map.get("userName"));
			/*EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='进货单号前缀' and departId='"+tsDepart.getId()+"'");
			Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(a01a03a07, 4)),0)+1 AS signed) orderNum from emk_m_in_storage where org_code=?",orgCode);
			emkMInStorage.setA01a03a07(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));*/

			emkMInStorageService.save(emkMInStorage);

			EmkFinanceYfCheckEntity emkFinanceYfCheckEntity = new EmkFinanceYfCheckEntity();
			emkFinanceYfCheckEntity.setYfCheckNo(emkMInStorage.getA01a03a07());
			emkFinanceYfCheckEntity.setState("0");
			emkFinanceYfCheckEntity.setDepartId(tsDepart.getId());
			emkFinanceYfCheckEntity.setOrgCode(tsDepart.getOrgCode());
			emkFinanceYfCheckEntity.setStorageId(emkMInStorage.getStorageId());
			emkFinanceYfCheckEntity.setStorageName(emkMInStorage.getStorageName());
			emkFinanceYfCheckEntity.setBankAccount(emkMInStorage.getBankMoney());
			emkFinanceYfCheckEntity.setBankType(emkMInStorage.getA01a03a09());
			emkFinanceYfCheckEntity.setGys(emkMInStorage.getGys());
			emkFinanceYfCheckEntity.setGysCode(emkMInStorage.getA01a03a06());
			emkFinanceYfCheckEntity.setYfMoney(Double.parseDouble(Utils.notEmpty(emkMInStorage.getYfMoney()) ? emkMInStorage.getYfMoney():"0"));
			emkFinanceYfCheckEntity.setMoney(Double.parseDouble(Utils.notEmpty(emkMInStorage.getMoney()) ? emkMInStorage.getMoney():"0"));
			emkFinanceYfCheckEntity.setQkMoney(Double.parseDouble(Utils.notEmpty(emkMInStorage.getYfMoney()) ? emkMInStorage.getYfMoney():"0"));
			emkFinanceYfCheckEntity.setClearWs(0.0);
			emkFinanceYfCheckEntity.setMarker(user.getRealName());
			emkFinanceYfCheckEntity.setMarkerId(user.getId());
			systemService.save(emkFinanceYfCheckEntity);

			EmkFactoryArchivesEntity emkFactoryArchivesEntity = systemService.singleResult("from EmkFactoryArchivesEntity where companyCode='"+emkMInStorage.getA01a03a06()+"' and orgCode='"+orgCode+"'");
			if(Utils.notEmpty(emkMInStorage.getBcqkMoney())){
				emkFactoryArchivesEntity.setBcqkMoney(emkMInStorage.getBcqkMoney());
			}
			/*if(Utils.notEmpty(emkMInStorage.getLjqkMoney())){
				emkFactoryArchivesEntity.setLjqkMoney(emkMInStorage.getLjqkMoney());
			}*/


			List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='0' and org_code=? order by order_num asc",orgCode);
			EmkMInStorageDetailEntity emkMInStorageDetailEntity = null;
			EmkProductEntity emkProductEntity = null;
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkStorageLogEntity storageLogEntity = null;

				for (int i = 0; i < rows; i++) {
					emkMInStorageDetailEntity = new EmkMInStorageDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						emkMInStorageDetailEntity.setDepartId(emkMInStorage.getDepartId());
						emkMInStorageDetailEntity.setOrgCode(emkMInStorage.getOrgCode());
						emkMInStorageDetailEntity.setInStorageId(emkMInStorage.getId());
						emkMInStorageDetailEntity.setStorageId(emkMInStorage.getStorageId());
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
						EmkStorageEntity storageEntity = findStorageAndSaveLog(emkMInStorageDetailEntity,emkMInStorage,orgCode,storageLogEntity,"录入采购入库单","0");

						if(storageEntity == null){
							storageEntity = saveNewStorage(emkMInStorageDetailEntity,emkMInStorage,emkProductEntity,storageLogEntity);
							updateKccb(storageEntity,orgCode);
						}else{
							//systemService.executeSql("update emk_storage set a01a09a13=truncate((a01a09a03+?)/(a01a09a02+?),2) where id=? ",emkMInStorageDetailEntity.getA01a09a03(),emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
							systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
							//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
							systemService.executeSql("update emk_storage set a01a09a09=?,a01a09a10=?,a01a09a12=?,a01a09a14=?,a01a09a21=? where id=? ",
									emkMInStorageDetailEntity.getA01a09a09(),emkMInStorageDetailEntity.getA01a09a10(),emkMInStorageDetailEntity.getA01a09a12(),
									emkMInStorageDetailEntity.getA01a09a14(),emkMInStorageDetailEntity.getA01a09a21(),storageEntity.getId());
							updateKccb(storageEntity,orgCode);
							storageLogEntity.setProNum(storageEntity.getA01a09a08());
							saveOldLog1(storageLogEntity,storageEntity);
						}
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "采购入库申请表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 采购订单
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doFinish")
	@ResponseBody
	public AjaxJson doFinish(EmkMInStorageEntity emkMInStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购订单完成操作成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");
			EmkMInStorageEntity t = emkMInStorageService.get(EmkMInStorageEntity.class, map.get("id"));
			if(t.getState().equals("2")){
				j.setMsg("采购订单已完成");
				j.setSuccess(false);
				return j;
			}
			t.setState("2");


			Class c = Class.forName(EmkMInStorageDetailEntity.class.getName());
			Method show = null;
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);

			List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='0' and org_code=? order by order_num asc",orgCode);
			List<EmkMInStorageDetailEntity> emkMInStorageDetailEntityList = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",t.getId());
			EmkStorageLogEntity storageLogEntity = null;
			EmkProductEntity emkProductEntity = null;
			for(EmkMInStorageDetailEntity emkMInStorageDetailEntity : emkMInStorageDetailEntityList){
				//判断在商品管理是否有存在，不存在则创建
				emkProductEntity = saveEmkProductEntity(emkMInStorageDetailEntity,orgCode,tsDepart);

				//查询库存数据和保存库存数据日记
				storageLogEntity = new EmkStorageLogEntity();
				EmkStorageEntity storageEntity = findStorageAndSaveLog(emkMInStorageDetailEntity,emkMInStorage,orgCode,storageLogEntity,"录入采购订单","1");

				if(storageEntity == null){
					storageEntity = saveNewStorage(emkMInStorageDetailEntity,emkMInStorage,emkProductEntity,storageLogEntity);
				}else{
					//systemService.executeSql("update emk_storage set a01a09a13=truncate((a01a09a03+?)/(a01a09a02+?),2) where id=? ",emkMInStorageDetailEntity.getA01a09a03(),emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
					systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
					//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
				}
				updateKccb(storageEntity,orgCode);
			}

			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "采购订单完成操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	void saveOldLog1(EmkStorageLogEntity storageLogEntity,EmkStorageEntity storageEntity){
		//EmkStorageEntity es = systemService.get(EmkStorageEntity.class,storageEntity.getId());
		Map esM = systemService.findOneForJdbc("select * from emk_storage where id=?",storageEntity.getId());
		storageLogEntity.setPreTotal(storageEntity.getA01a09a02());
		if(Utils.notEmpty(esM.get("a01a09a02"))){
			storageLogEntity.setNextTotal(esM.get("a01a09a02").toString());
			int total  = Integer.parseInt(Utils.notEmpty(esM.get("a01a09a02")) ? esM.get("a01a09a02").toString():"0")-
					Integer.parseInt(Utils.notEmpty(storageLogEntity.getPreTotal()) ? storageLogEntity.getPreTotal():"0");
			storageLogEntity.setTotal(String.valueOf(total));
		}
		systemService.save(storageLogEntity);
	}
	void saveOldLog2(EmkStorageLogEntity newStorageLogEntity,EmkStorageEntity oldstorageEntity,EmkMInStorageDetailEntity old){
		newStorageLogEntity.setId(null);
		newStorageLogEntity.setPreTotal(oldstorageEntity.getA01a09a02());
		newStorageLogEntity.setTotal(old.getA01a09a02());
		int nextTotal  = Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getPreTotal()) ? newStorageLogEntity.getPreTotal():"0")-
				Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getTotal()) ? newStorageLogEntity.getTotal():"0");
		newStorageLogEntity.setNextTotal(String.valueOf(nextTotal));
		systemService.save(newStorageLogEntity);
	}

	EmkProductEntity saveEmkProductEntity(EmkMInStorageDetailEntity emkMInStorageDetailEntity,String orgCode,TSDepart tsDepart){
		String hql = "from EmkProductEntity where 1=1 and proZnName='"+emkMInStorageDetailEntity.getA01a09a07()+"'";
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a10())){
			hql += " and brand='"+emkMInStorageDetailEntity.getA01a09a10()+"'";
		}else{
			hql += " and (brand is null or brand = '')";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a08())){
			hql += " and proNum='"+emkMInStorageDetailEntity.getA01a09a08()+"'";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a11())){
			hql += " and unit='"+emkMInStorageDetailEntity.getA01a09a11()+"'";
		}else{
			hql += " and (unit is null or unit = '')";
		}
		/*if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a12())){
			hql += " and costPrice='"+emkMInStorageDetailEntity.getA01a09a12()+"'";
		}*/

		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a06())){
			hql += " and a01a01a01='"+emkMInStorageDetailEntity.getA01a09a06()+"'";
		}else{
			hql += " and (a01a01a01 is null or a01a01a01 = '') ";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a24())){
			hql += " and a01a01a02='"+emkMInStorageDetailEntity.getA01a09a24()+"'";
		}else{
			hql += " and (a01a01a02 is null or a01a01a02 = '') ";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a14())){
			hql += " and a01a01a05='"+emkMInStorageDetailEntity.getA01a09a14()+"'";
		}else{
			hql += " and (a01a01a05 is null or a01a01a05 = '') ";
		}
		hql += " and orgCode='"+orgCode+"'";
		EmkProductEntity emkProductEntity = systemService.singleResult(hql);
		if(Utils.isEmpty(emkProductEntity)){
			emkProductEntity = new EmkProductEntity();
			emkProductEntity.setProType("qt");
			emkProductEntity.setProTypeName("其他");
			emkProductEntity.setProZnName(emkMInStorageDetailEntity.getA01a09a07());
			if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a08())){
				emkProductEntity.setProNum(emkMInStorageDetailEntity.getA01a09a08());
				emkProductEntity.setFlag("0");
			}else {
				saveProNum(emkProductEntity,orgCode);
				emkProductEntity.setFlag("1");
			}
			emkProductEntity.setProZjm(PinyinUtil.getPinYinHeadChar(emkProductEntity.getProZnName()));
			emkProductEntity.setBrand(emkMInStorageDetailEntity.getA01a09a10());
			emkProductEntity.setA01a01a01(emkMInStorageDetailEntity.getA01a09a06());
			emkProductEntity.setUnit(emkMInStorageDetailEntity.getA01a09a11());

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
			emkProductEntity.setSellPrice(emkMInStorageDetailEntity.getA01a09a21());
			emkProductEntity.setCostPrice(emkMInStorageDetailEntity.getA01a09a12());
			emkProductEntity.setOrgCode(tsDepart.getOrgCode());
			emkProductEntity.setDepartId(tsDepart.getId());
			systemService.save(emkProductEntity);
		}
		emkMInStorageDetailEntity.setA01a09a08(emkProductEntity.getProNum());
		return emkProductEntity;

	}

	EmkStorageEntity findStorageAndSaveLog(EmkMInStorageDetailEntity emkMInStorageDetailEntity,EmkMInStorageEntity t,String orgCode,EmkStorageLogEntity storageLogEntity,String remark,String type){
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",orgCode);

		String hql = "from EmkStorageEntity where 1=1 ";
		if(Utils.notEmpty(t.getStorageId())){
			hql += " and storageId='"+t.getStorageId()+"'";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a07())){
			hql += " and a01a09a07='"+emkMInStorageDetailEntity.getA01a09a07()+"'";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a08())){
			hql += " and a01a09a08='"+emkMInStorageDetailEntity.getA01a09a08()+"'";
		}else{
			//hql += " and flag=1";
			hql += " and (a01a09a08 is null or a01a09a08 = '')";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a05())){
			hql += " and a01a09a05='"+emkMInStorageDetailEntity.getA01a09a05()+"'";
		}else{
			hql += " and (a01a09a05 is null or a01a09a05 = '')";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a04())){
			hql += " and a01a09a04='"+emkMInStorageDetailEntity.getA01a09a04()+"'";
		}else{
			hql += " and (a01a09a04 is null or a01a09a04 = '')";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a06())){
			hql += " and a01a09a06='"+emkMInStorageDetailEntity.getA01a09a06()+"'";
		}else{
			hql += " and (a01a09a06 is null or a01a09a06 = '')";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a24())){
			hql += " and a01a09a24='"+emkMInStorageDetailEntity.getA01a09a24()+"'";
		}else{
			hql += " and (a01a09a24 is null or a01a09a24 = '')";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a14())){
			hql += " and a01a09a14='"+emkMInStorageDetailEntity.getA01a09a14()+"'";
		}else{
			hql += " and (a01a09a14 is null or a01a09a14 = '')";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a22())){
			hql += " and a01a09a22='"+emkMInStorageDetailEntity.getA01a09a22()+"'";
		}else{
			hql += " and (a01a09a22 is null or a01a09a22 = '')";
		}
		if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a23())){
			hql += " and a01a09a23='"+emkMInStorageDetailEntity.getA01a09a23()+"'";
		}else{
			hql += " and (a01a09a23 is null or a01a09a23 = '')";
		}

		hql += " and orgCode='"+orgCode+"'";
		EmkStorageEntity storageEntity = systemService.singleResult(hql);

		storageLogEntity.setRkNo(t.getA01a03a07());
		storageLogEntity.setType(type);
		storageLogEntity.setProNum(emkMInStorageDetailEntity.getA01a09a08());
		storageLogEntity.setProZnName(emkMInStorageDetailEntity.getA01a09a07());
		storageLogEntity.setMarkDate(DateUtil.getLogTime());
		//storageLogEntity.setRemark("编辑采购入库单");
		storageLogEntity.setRemark(remark);
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setStorageId(t.getStorageId());
		storageLogEntity.setStorageName(t.getStorageName());
		return storageEntity;
	}

	EmkStorageEntity findOldStorage(EmkMInStorageEntity oldT,EmkMInStorageDetailEntity old,String orgCode){
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

	EmkStorageEntity saveNewStorage(EmkMInStorageDetailEntity emkMInStorageDetailEntity,EmkMInStorageEntity t,EmkProductEntity emkProductEntity,EmkStorageLogEntity storageLogEntity){
		EmkStorageEntity storageEntity = new EmkStorageEntity();
		MyBeanUtils.copyBeanNotNull2Bean(emkMInStorageDetailEntity,storageEntity);
		storageEntity.setId(null);
		storageEntity.setStorageName(t.getStorageName());
		storageEntity.setStorageId(t.getStorageId());
		storageEntity.setA01a09a08(emkProductEntity.getProNum());
		/*if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a08())){
			storageEntity.setFlag("0");
		}else{
			storageEntity.setFlag("1");
		}*/
		storageEntity.setProZjm(PinyinUtil.getPinYinHeadChar(storageEntity.getA01a09a07()));

		systemService.save(storageEntity);
		//systemService.executeSql("update emk_storage set a01a09a13=truncate(a01a09a03/a01a09a02,2) where id=? ",storageEntity.getId());
		storageLogEntity.setPreTotal("0");
		storageLogEntity.setNextTotal(emkMInStorageDetailEntity.getA01a09a02());
		storageLogEntity.setTotal(emkMInStorageDetailEntity.getA01a09a02());
		storageLogEntity.setProNum(storageEntity.getA01a09a08());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setOrgCode(t.getOrgCode());
		systemService.save(storageLogEntity);
		return  storageEntity;
	}
	void updateStorage(EmkStorageEntity storageEntity,EmkMInStorageDetailEntity emkMInStorageDetailEntity,EmkStorageLogEntity storageLogEntity){
		systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",
				emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
		//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
		storageLogEntity.setProNum(storageEntity.getA01a09a08());
		saveOldLog1(storageLogEntity,storageEntity);
	}
	void saveOldStorage(EmkStorageEntity oldstorageEntity,EmkMInStorageDetailEntity old,EmkStorageLogEntity storageLogEntity){
		systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?) where id=? ",
				old.getA01a09a02(),oldstorageEntity.getId());
		//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", oldstorageEntity.getId());

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
	 * 更新采购入库申请表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkMInStorageEntity emkMInStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购入库申请表更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		String dataRows = map.get("orderMxListID");
		EmkMInStorageEntity t = emkMInStorageService.get(EmkMInStorageEntity.class, map.get("emkMInStorageId"));
		EmkMInStorageEntity oldT = new EmkMInStorageEntity();
		MyBeanUtils.copyBeanNotNull2Bean(t,oldT);
		List<EmkMInStorageDetailEntity> emkMInStorageDetailEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",t.getId());
		Map<String,EmkMInStorageDetailEntity> detailMap = new HashMap<>();
		for(EmkMInStorageDetailEntity emkMInStorageDetailEntity : emkMInStorageDetailEntities){
			detailMap.put(emkMInStorageDetailEntity.getId(),emkMInStorageDetailEntity);
		}
		try {
			if(!t.getState().equals("0")){
				j.setMsg("采购入库单在处理中无法进行修改");
				j.setSuccess(false);
				return j;
			}
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));

			emkMInStorage.setState("0");
			emkMInStorage.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkMInStorage, t);
			emkMInStorageService.saveOrUpdate(t);
			EmkFactoryArchivesEntity emkFactoryArchivesEntity = systemService.singleResult("from EmkFactoryArchivesEntity where companyCode='"+emkMInStorage.getA01a03a06()+"' and orgCode='"+orgCode+"'");
			if(Utils.notEmpty(emkMInStorage.getBcqkMoney())){
				emkFactoryArchivesEntity.setBcqkMoney(emkMInStorage.getBcqkMoney());
			}
			/*if(Utils.notEmpty(emkMInStorage.getLjqkMoney())){
				emkFactoryArchivesEntity.setLjqkMoney(emkMInStorage.getLjqkMoney());
			}*/
			dataRows = map.get("orderMxListID");
			
			if (Utils.notEmpty(dataRows)) {

				systemService.executeSql("delete from emk_m_in_storage_detail where in_storage_id = ? ",t.getId());
				int rows = Integer.parseInt(dataRows);
				EmkStorageLogEntity storageLogEntity = null;
				EmkStorageLogEntity newStorageLogEntity = null;
				EmkProductEntity emkProductEntity = null;

				List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='0' and org_code=?  order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
				Class c = Class.forName(EmkMInStorageDetailEntity.class.getName());
				EmkMInStorageDetailEntity emkMInStorageDetailEntity = null;
				Method show = null;
				EmkMInStorageDetailEntity old = null;
				for (int i = 0; i < rows; i++) {
					emkMInStorageDetailEntity = new EmkMInStorageDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						if(Utils.notEmpty(map.get("orderMxList["+i+"].id"))){
							old = detailMap.get(map.get("orderMxList["+i+"].id"));
						}

						emkMInStorageDetailEntity.setInStorageId(t.getId());
						emkMInStorageDetailEntity.setDepartId(t.getDepartId());
						emkMInStorageDetailEntity.setOrgCode(t.getOrgCode());
						emkMInStorageDetailEntity.setStorageId(t.getStorageId());

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

						//查询库存数据和保存日记
						storageLogEntity = new EmkStorageLogEntity();
						EmkStorageEntity storageEntity = findStorageAndSaveLog(emkMInStorageDetailEntity,t,orgCode,storageLogEntity,"编辑采购入库单","0");

						//查询旧库存数据
						EmkStorageEntity oldstorageEntity = findOldStorage(oldT,old,orgCode);

						//变更进货仓库
						if(Utils.notEmpty(emkMInStorage.getStorageId())){
							if(!emkMInStorage.getStorageId().equals(oldT.getStorageId())){
								if(Utils.notEmpty(oldstorageEntity)){
									saveOldStorage(oldstorageEntity,old,storageLogEntity);
								}
								if(Utils.notEmpty(storageEntity)){
									updateStorage(storageEntity,emkMInStorageDetailEntity,storageLogEntity);
								}else {
									storageEntity = saveNewStorage(emkMInStorageDetailEntity,t,emkProductEntity,storageLogEntity);
								}
								updateKccb(oldstorageEntity,orgCode);
								updateKccb(storageEntity,orgCode);
								j.setSuccess(true);
								j.setMsg(message);
								return j;

							}
						}
						//变更货位
						if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a05())){
							if(!emkMInStorageDetailEntity.getA01a09a05().equals(old.getA01a09a05())){
								if(Utils.notEmpty(oldstorageEntity)){
									saveOldStorage(oldstorageEntity,old,storageLogEntity);
								}
								if(Utils.notEmpty(storageEntity)){
									updateStorage(storageEntity,emkMInStorageDetailEntity,storageLogEntity);
								}else {
									storageEntity = saveNewStorage(emkMInStorageDetailEntity,t,emkProductEntity,storageLogEntity);
								}
								updateKccb(oldstorageEntity,orgCode);
								updateKccb(storageEntity,orgCode);
								j.setSuccess(true);
								j.setMsg(message);
								return j;

							}
						}

						if(Utils.isEmpty(storageEntity)){
							storageEntity = saveNewStorage(emkMInStorageDetailEntity,t,emkProductEntity,storageLogEntity);
							updateKccb(storageEntity,orgCode);

							if(Utils.notEmpty(oldstorageEntity)){
								saveOldStorage(oldstorageEntity,old,storageLogEntity);
								updateKccb(oldstorageEntity,orgCode);
							}
						}else{
							/*systemService.executeSql("update emk_storage set a01a09a13=(a01a09a03+?)/(a01a09a02+?),a01a09a12=? where id=? ",
									emkMInStorageDetailEntity.getA01a09a03(),emkMInStorageDetailEntity.getA01a09a02(),emkMInStorageDetailEntity.getA01a09a12(),storageEntity.getId());*/

							if(Utils.notEmpty(old)){
								systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?-?) where id=? ",
										emkMInStorageDetailEntity.getA01a09a02(),old.getA01a09a02(),storageEntity.getId());
							}
							systemService.executeSql("update emk_storage set a01a09a09=?,a01a09a10=?,a01a09a12=?,a01a09a14=?,a01a09a21=? where id=? ",
									emkMInStorageDetailEntity.getA01a09a09(),emkMInStorageDetailEntity.getA01a09a10(),emkMInStorageDetailEntity.getA01a09a12(),
									emkMInStorageDetailEntity.getA01a09a14(),emkMInStorageDetailEntity.getA01a09a21(),storageEntity.getId());
							//systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2),storage_id=?,storage_name=? where id=? ", storageEntity.getId(),t.getStorageId(),t.getStorageName());
							//systemService.executeSql("update emk_storage set a01a09a12=? where id=? ",emkMInStorageDetailEntity.getA01a09a12(),storageEntity.getId());
							updateKccb(storageEntity,orgCode);
							storageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
							saveOldLog1(storageLogEntity,oldstorageEntity);

						}
					}
				}
			}
			EmkFinanceYfCheckEntity emkFinanceYfCheckEntity = systemService.singleResult("from EmkFinanceYfCheckEntity where orgCode='"+orgCode+"' and" +
					"	yfCheckNo='"+t.getA01a03a07()+"'");
			emkFinanceYfCheckEntity.setStorageId(t.getStorageId());
			emkFinanceYfCheckEntity.setStorageName(t.getStorageName());
			emkFinanceYfCheckEntity.setBankAccount(t.getBankMoney());
			emkFinanceYfCheckEntity.setBankType(t.getA01a03a09());
			emkFinanceYfCheckEntity.setGys(t.getGys());
			emkFinanceYfCheckEntity.setGysCode(t.getA01a03a06());
			emkFinanceYfCheckEntity.setYfMoney(Double.parseDouble(Utils.notEmpty(t.getYfMoney()) ? t.getYfMoney():"0"));
			emkFinanceYfCheckEntity.setMoney(Double.parseDouble(Utils.notEmpty(t.getMoney()) ? t.getMoney():"0"));
			emkFinanceYfCheckEntity.setQkMoney(Double.parseDouble(Utils.notEmpty(t.getYfMoney()) ? t.getYfMoney():"0"));
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "采购入库申请表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doUpdateForKc")
	@ResponseBody
	public AjaxJson doUpdateForKc(EmkMInStorageEntity emkMInStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购入库申请表更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		String dataRows = map.get("orderMxListID");
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));

		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
		EmkMInStorageEntity t = emkMInStorageService.singleResult("from EmkMInStorageEntity where A01A03A07='"+map.get("rkNo")+"' and orgCode='"+orgCode+"'" );
		EmkMInStorageEntity oldT = new EmkMInStorageEntity();
		MyBeanUtils.copyBeanNotNull2Bean(t,oldT);
		try {
			if(Utils.isEmpty(t)){
				emkMInStorage.setState("0");
				t = new EmkMInStorageEntity();
				t.setDepartId(tsDepart.getId());
				t.setOrgCode(orgCode);
				t.setA01a03a07(map.get("rkNo"));
				MyBeanUtils.copyBeanNotNull2Bean(emkMInStorage,t);
				t.setId(null);
				emkMInStorageService.save(t);
			}else {
				emkMInStorage.setId(null);
				MyBeanUtils.copyBeanNotNull2Bean(emkMInStorage,t);
				systemService.saveOrUpdate(t);
			}

			dataRows = map.get("orderMxListID");

			if (Utils.notEmpty(dataRows)) {
				List<EmkMInStorageDetailEntity> emkMInStorageDetailEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",t.getId());
				Map<String,EmkMInStorageDetailEntity> detailMap = new HashMap<>();
				for(EmkMInStorageDetailEntity emkMInStorageDetailEntity : emkMInStorageDetailEntities){
					detailMap.put(emkMInStorageDetailEntity.getId(),emkMInStorageDetailEntity);
				}
				systemService.executeSql("delete from emk_m_in_storage_detail where in_storage_id = ? ",t.getId());
				int rows = Integer.parseInt(dataRows);
				EmkStorageLogEntity storageLogEntity = null;
				List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='0' and org_code=?  order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
				Class c = Class.forName(EmkMInStorageDetailEntity.class.getName());
				EmkMInStorageDetailEntity emkMInStorageDetailEntity = null;
				Method show = null;
				EmkStorageLogEntity newStorageLogEntity = null;
				EmkMInStorageDetailEntity old = null;
				EmkProductEntity emkProductEntity = null;

				for (int i = 0; i < rows; i++) {
					emkMInStorageDetailEntity = new EmkMInStorageDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						if(Utils.notEmpty(map.get("orderMxList["+i+"].id"))){
							old = detailMap.get(map.get("orderMxList["+i+"].id"));
						}

						emkMInStorageDetailEntity.setInStorageId(t.getId());
						emkMInStorageDetailEntity.setDepartId(t.getDepartId());
						emkMInStorageDetailEntity.setOrgCode(t.getOrgCode());

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
						EmkStorageEntity storageEntity = findStorageAndSaveLog(emkMInStorageDetailEntity,emkMInStorage,orgCode,storageLogEntity,"编辑期初库存","5");

						if(Utils.isEmpty(map.get("emkMInStorageId"))){
							if(storageEntity == null){
								storageEntity = saveNewStorage(emkMInStorageDetailEntity,emkMInStorage,emkProductEntity,storageLogEntity);
							}else{
								//systemService.executeSql("update emk_storage set a01a09a13=truncate((a01a09a03+?)/(a01a09a02+?),2) where id=? ",emkMInStorageDetailEntity.getA01a09a03(),emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
								systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",emkMInStorageDetailEntity.getA01a09a02(),storageEntity.getId());
								systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
								storageLogEntity.setProNum(storageEntity.getA01a09a08());
								saveOldLog1(storageLogEntity,storageEntity);
							}
							updateKccb(storageEntity,orgCode);

						}else{
							//查询旧库存数据
							EmkStorageEntity oldstorageEntity = findOldStorage(oldT,old,orgCode);

							//变更进货仓库
							if(Utils.notEmpty(emkMInStorage.getStorageId())){
								if(!emkMInStorage.getStorageId().equals(oldT.getStorageId())){
									if(Utils.notEmpty(oldstorageEntity)){
										saveOldStorage(oldstorageEntity,old,storageLogEntity);
									}
									if(Utils.notEmpty(storageEntity)){
										updateStorage(storageEntity,emkMInStorageDetailEntity,storageLogEntity);
									}else {
										storageEntity = saveNewStorage(emkMInStorageDetailEntity,t,emkProductEntity,storageLogEntity);
									}
									updateKccb(oldstorageEntity,orgCode);
									updateKccb(storageEntity,orgCode);
									j.setSuccess(true);
									j.setMsg(message);
									return j;

								}
							}
							//变更货位
							if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a05())){
								if(!emkMInStorageDetailEntity.getA01a09a05().equals(old.getA01a09a05())){
									if(Utils.notEmpty(oldstorageEntity)){
										saveOldStorage(oldstorageEntity,old,storageLogEntity);
									}
									if(Utils.notEmpty(storageEntity)){
										updateStorage(storageEntity,emkMInStorageDetailEntity,storageLogEntity);
									}else {
										saveNewStorage(emkMInStorageDetailEntity,t,emkProductEntity,storageLogEntity);
									}
									updateKccb(oldstorageEntity,orgCode);
									updateKccb(storageEntity,orgCode);
									j.setSuccess(true);
									j.setMsg(message);
									return j;

								}
							}

							if(Utils.isEmpty(storageEntity)){
								storageEntity = saveNewStorage(emkMInStorageDetailEntity,t,emkProductEntity,storageLogEntity);
								updateKccb(storageEntity,orgCode);

								if(Utils.notEmpty(oldstorageEntity)){
									saveOldStorage(oldstorageEntity,old,storageLogEntity);
									updateKccb(oldstorageEntity,orgCode);
								}
							}else{

								if(Utils.notEmpty(old)){
									systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?-?) where id=? ",
											emkMInStorageDetailEntity.getA01a09a02(),old.getA01a09a02(),storageEntity.getId());
								}else{
									systemService.executeSql("update emk_storage set a01a09a09=?,a01a09a10=?,a01a09a12=?,a01a09a14=?,a01a09a21=? where id=? ",
											emkMInStorageDetailEntity.getA01a09a09(),emkMInStorageDetailEntity.getA01a09a10(),emkMInStorageDetailEntity.getA01a09a12(),
											emkMInStorageDetailEntity.getA01a09a14(),emkMInStorageDetailEntity.getA01a09a21(),storageEntity.getId());
								}
								updateKccb(storageEntity,orgCode);
								storageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
								saveOldLog1(storageLogEntity,oldstorageEntity);

							}
						}

					}
				}
			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "采购入库申请表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加采购订单
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddForOrder")
	@ResponseBody
	public AjaxJson doAddForOrder(EmkMInStorageEntity emkMInStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购订单添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");
			String proOrderId = request.getSession().getAttribute("proOrderId").toString();

			emkMInStorage.setState("0");
			emkMInStorage.setPayState("0");
			emkMInStorage.setRecevieState("0");
			emkMInStorage.setProOrderId(proOrderId);
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);

			Class c = Class.forName(EmkMInStorageOrderDetailEntity.class.getName());

			Method show = null;

			emkMInStorage.setOrgCode(tsDepart.getOrgCode());
			emkMInStorage.setDepartId(tsDepart.getId());
			TSUser tsUser = systemService.findUniqueByProperty(TSUser.class,"userName",map.get("userName"));
			EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='进货单号前缀' and departId='"+tsDepart.getId()+"'");

			Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(a01a03a07, 4)),0)+1 AS signed) orderNum from emk_m_in_storage where org_code=?",orgCode);
			emkMInStorage.setA01a03a07(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));

			emkMInStorageService.save(emkMInStorage);
			EmkFactoryArchivesEntity emkFactoryArchivesEntity = systemService.singleResult("from EmkFactoryArchivesEntity where companyCode='"+emkMInStorage.getA01a03a06()+"' and orgCode='"+orgCode+"'");
			if(Utils.notEmpty(emkMInStorage.getBcqkMoney())){
				emkFactoryArchivesEntity.setBcqkMoney(emkMInStorage.getBcqkMoney());
			}

			List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='4' and org_code=?  order by order_num asc",orgCode);
			EmkMInStorageOrderDetailEntity emkMInStorageDetailEntity = null;
			List<EmkProductEntity> emkProductEntityList = null;
			EmkProductEntity emkProductEntity = null;

			dataRows = map.get("orderMxListID");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkStorageLogEntity storageLogEntity = null;

				for (int i = 0; i < rows; i++) {
					emkMInStorageDetailEntity = new EmkMInStorageOrderDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						emkMInStorageDetailEntity.setDepartId(emkMInStorage.getDepartId());
						emkMInStorageDetailEntity.setOrgCode(emkMInStorage.getOrgCode());
						emkMInStorageDetailEntity.setInStorageId(emkMInStorage.getId());
						for(Map category : categoryEntities){
							if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
								String m0 = "setA"+category.get("code").toString().substring(1);
								show = c.getMethod(m0,String.class);
								show.invoke(emkMInStorageDetailEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
							}
						}
						systemService.save(emkMInStorageDetailEntity);

						//判断在商品管理是否有存在，不存在则创建
						String hql = "from EmkProductEntity where 1=1 and proZnName='"+emkMInStorageDetailEntity.getA01a09a07()+"'";
						if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a10())){
							hql += " and brand='"+emkMInStorageDetailEntity.getA01a09a10()+"'";
						}
						if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a08())){
							hql += " and proNum='"+emkMInStorageDetailEntity.getA01a09a08()+"'";
						}
						if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a11())){
							hql += " and unit='"+emkMInStorageDetailEntity.getA01a09a11()+"'";
						}
						if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a12())){
							hql += " and costPrice='"+emkMInStorageDetailEntity.getA01a09a12()+"'";
						}

						if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a06())){
							hql += " and a01a01a01='"+emkMInStorageDetailEntity.getA01a09a08()+"'";
						}
						hql += " and orgCode='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"'";
						emkProductEntityList = systemService.findHql(hql);
						if(Utils.isEmpty(emkProductEntityList)){
							emkProductEntity = new EmkProductEntity();
							emkProductEntity.setProType("qt");
							emkProductEntity.setProTypeName("其他");
							emkProductEntity.setProZnName(emkMInStorageDetailEntity.getA01a09a07());
							emkProductEntity.setProZjm(PinyinUtil.getPinYinHeadChar(emkProductEntity.getProZnName()));
							if(Utils.notEmpty(emkMInStorageDetailEntity.getA01a09a08())){
								emkProductEntity.setFlag("0");
								emkProductEntity.setProNum(emkMInStorageDetailEntity.getA01a09a08());
							}else {
								saveProNum(emkProductEntity,orgCode);
							}
							emkProductEntity.setBrand(emkMInStorageDetailEntity.getA01a09a10());
							emkProductEntity.setA01a01a01(emkMInStorageDetailEntity.getA01a09a06());
							emkProductEntity.setUnit(emkMInStorageDetailEntity.getA01a09a11());
							emkProductEntity.setCostPrice(emkMInStorageDetailEntity.getA01a09a12());
							emkProductEntity.setOrgCode(tsDepart.getOrgCode());
							emkProductEntity.setDepartId(tsDepart.getId());
							systemService.save(emkProductEntity);
						}
					}
				}
			}
			EmkProOrderEntity emkProOrderEntity = systemService.get(EmkProOrderEntity.class,proOrderId);
			if(Utils.notEmpty(emkProOrderEntity)){
				emkProOrderEntity.setState("1");
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "采购订单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新采购订单
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdateForOrder")
	@ResponseBody
	public AjaxJson doUpdateForOrder(EmkMInStorageEntity emkMInStorage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购订单更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		String dataRows = map.get("orderMxListID");

		EmkMInStorageEntity t = emkMInStorageService.get(EmkMInStorageEntity.class, map.get("emkMInStorageId"));
		try {
			if(!t.getState().equals("0")){
				j.setMsg("采购订单在处理中无法进行修改");
				j.setSuccess(false);
				return j;
			}
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);

			emkMInStorage.setState("0");
			emkMInStorage.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkMInStorage, t);
			emkMInStorageService.saveOrUpdate(t);
			EmkFactoryArchivesEntity emkFactoryArchivesEntity = systemService.singleResult("from EmkFactoryArchivesEntity where companyCode='"+emkMInStorage.getA01a03a06()+"' and orgCode='"+orgCode+"'");
			if(Utils.notEmpty(emkMInStorage.getBcqkMoney())){
				emkFactoryArchivesEntity.setBcqkMoney(emkMInStorage.getBcqkMoney());
			}
			dataRows = map.get("orderMxListID");

			if (Utils.notEmpty(dataRows)) {
				List<EmkMInStorageDetailEntity> emkMInStorageDetailEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",t.getId());
				Map<String,EmkMInStorageDetailEntity> detailMap = new HashMap<>();
				for(EmkMInStorageDetailEntity emkMInStorageDetailEntity : emkMInStorageDetailEntities){
					detailMap.put(emkMInStorageDetailEntity.getId(),emkMInStorageDetailEntity);
				}
				systemService.executeSql("delete from emk_m_in_storage_order_detail where in_storage_id = ? ",t.getId());
				int rows = Integer.parseInt(dataRows);
				EmkStorageLogEntity storageLogEntity = null;
				List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='4' and org_code=? order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
				Class c = Class.forName(EmkMInStorageOrderDetailEntity.class.getName());
				EmkMInStorageOrderDetailEntity emkMInStorageDetailEntity = null;
				Method show = null;
				EmkMInStorageDetailEntity old = null;
				for (int i = 0; i < rows; i++) {
					emkMInStorageDetailEntity = new EmkMInStorageOrderDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						if(Utils.notEmpty(map.get("orderMxList["+i+"].id"))){
							old = detailMap.get(map.get("orderMxList["+i+"].id"));
						}

						emkMInStorageDetailEntity.setInStorageId(t.getId());
						emkMInStorageDetailEntity.setDepartId(t.getDepartId());
						emkMInStorageDetailEntity.setOrgCode(t.getOrgCode());

						for(Map category : categoryEntities){
							if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
								String m0 = "setA"+category.get("code").toString().substring(1);
								show = c.getMethod(m0,String.class);
								show.invoke(emkMInStorageDetailEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
							}
						}
						systemService.save(emkMInStorageDetailEntity);

					}
				}
			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "采购订单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doPrintPDF")
	public String doPrintPDF(String ids,EmkMInStorageEntity emkMInStorage, HttpServletRequest request,HttpServletResponse response) {
		String message = null;
		try {
			for (String id : ids.split(",")) {
				String fileName = "c:\\PDF\\"+DateUtil.format(new Date(),"yyyyMMddHHmmss")+".pdf";
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
				emkMInStorage=  systemService.get(EmkMInStorageEntity.class, id);
				List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",id);
				new createPdf(file).generateRkdPDF(emkMInStorage,rkglMxEntities,findProcessList(id));
				String fFileName = "c:\\PDF\\F"+DateUtil.format(new Date(),"yyyyMMddHHmmss")+".pdf";
				WaterMark.waterMark(fileName,fFileName, "采购入库单");
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
	 * 采购入库申请表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkMInStorageEntity emkMInStorage, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A03' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.setAttribute("headcategoryEntities",categoryEntities);
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
		TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='进货单号前缀' and departId='"+tsDepart.getId()+"'");

		Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(a01a03a07, 4)),0)+1 AS signed) orderNum from emk_m_in_storage where org_code=?",orgCode);
		emkMInStorage.setA01a03a07(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
		req.setAttribute("emkMInStoragePage", emkMInStorage);
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",orgCode);

		return new ModelAndView("com/emk/bound/minstorage/emkMInStorage-add");
	}

	@RequestMapping(params = "goAddForKc")
	public ModelAndView goAddForKc(EmkMInStorageEntity emkMInStorage, HttpServletRequest req) {
		Map map = ParameterUtil.getParamMaps(req.getParameterMap());
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
		emkMInStorage = emkMInStorageService.singleResult("from EmkMInStorageEntity where A01A03A07='KC000001' and orgCode='"+orgCode+"'" );
		req.setAttribute("emkMInStoragePage", emkMInStorage);
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorage-addKc");
	}

	/**
	 *保存窗口高度session
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(params = "setHeightSession")
	@ResponseBody
	public AjaxJson setHeightSession(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "保存成功";
		try {
			Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
			request.getSession().setAttribute("headTb",param.get("headTb"));
			request.getSession().setAttribute("footTb",param.get("footTb"));

		} catch (Exception e) {
			e.printStackTrace();
			message = "保存失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "goAdd2")
	public ModelAndView goAdd2(EmkMInStorageEntity emkMInStorage, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A03' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.setAttribute("headcategoryEntities",categoryEntities);
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		req.getSession().setAttribute("emkProOrderDetailEntityList","");
		req.getSession().setAttribute("proOrderId","");
		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);

		TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='进货单号前缀' and departId='"+tsDepart.getId()+"'");

		Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(a01a03a07, 4)),0)+1 AS signed) orderNum from emk_m_in_storage where org_code=?",orgCode);
		emkMInStorage.setA01a03a07(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
		req.setAttribute("emkMInStoragePage", emkMInStorage);

		//Map qk = systemService.findOneForJdbc("select ifnull(m.bcq_money,0) bcqk from emk_m_in_storage m where m.org_code=? order by m.create_date desc LIMIT 0,1",orgCode);
		//req.setAttribute("qcqkMoney",qk.get("bcqk"));

		if(Utils.notEmpty(req.getSession().getAttribute("emkProOrderDetailEntityList"))){
			List<EmkProOrderDetailEntity> emkProOrderDetailEntityList = (List<EmkProOrderDetailEntity>) req.getSession().getAttribute("emkProOrderDetailEntityList");
		}
		//req.getSession().setAttribute("emkProOrderDetailEntityList","");

		return new ModelAndView("com/emk/bound/minstorage/emkMInStorage-add2");
	}
	@RequestMapping(params = "goPay")
	public ModelAndView goPay(EmkMInStorageEntity emkMInStorage, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkMInStorage.getId())) {
			emkMInStorage = emkMInStorageService.getEntity(EmkMInStorageEntity.class, emkMInStorage.getId());
			req.setAttribute("emkMInStoragePage", emkMInStorage);
		}
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorage-pay");
	}
	@RequestMapping(params = "goRecevie")
	public ModelAndView goRecevie(EmkMInStorageEntity emkMInStorage, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkMInStorage.getId())) {
			emkMInStorage = emkMInStorageService.getEntity(EmkMInStorageEntity.class, emkMInStorage.getId());
			req.setAttribute("emkMInStoragePage", emkMInStorage);
		}
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorage-recevie");
	}
	/**
	 * 采购入库申请表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkMInStorageEntity emkMInStorage, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",user.getCurrentDepart().getOrgCode().substring(0,3));

		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A03' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.setAttribute("headcategoryEntities",categoryEntities);

		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkMInStorage.getId())) {
			emkMInStorage = emkMInStorageService.getEntity(EmkMInStorageEntity.class, emkMInStorage.getId());
		}
		/*try {
			Map countMap = MyBeanUtils.culBeanCounts(emkMInStorage);
			int a=0,b=0;
			a = Integer.parseInt(countMap.get("finishColums").toString());
			b = Integer.parseInt(countMap.get("Colums").toString())-4;

			countMap.put("finishColums",a);
			countMap.put("Colums",b);
			req.setAttribute("countMap", countMap);
			DecimalFormat df = new DecimalFormat("#.00");
			req.setAttribute("recent", df.format(a*100/b));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		req.setAttribute("emkMInStoragePage", emkMInStorage);

		return new ModelAndView("com/emk/bound/minstorage/emkMInStorage-update");
	}

	@RequestMapping(params="goUpdate2")
	public ModelAndView goUpdate2(EmkMInStorageEntity emkMInStorage, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A03' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.setAttribute("headcategoryEntities",categoryEntities);
		req.getSession().setAttribute("emkProOrderDetailEntityList","");
		req.getSession().setAttribute("proOrderId","");

		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkMInStorage.getId())) {
			emkMInStorage = emkMInStorageService.getEntity(EmkMInStorageEntity.class, emkMInStorage.getId());
		}
		req.setAttribute("emkMInStoragePage", emkMInStorage);
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorage-update2");
	}

	/**
	 * 打印 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "jump")
	public Object jump(HttpServletRequest req) {
		Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
		req.setAttribute("url", map.get("url")+"&rkNo="+map.get("rkNo"));
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
		EmkMInStorageEntity emkMInStorage = systemService.findUniqueByProperty(EmkMInStorageEntity.class,"rkNo",map.get("rkNo"));
		req.setAttribute("emkMInStorage",emkMInStorage);

		List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=? ",  emkMInStorage.getId());
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
		return new ModelAndView("com/emk/bound/minstorage/printPreview");

	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkMInStorageController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkMInStorageEntity emkMInStorage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		try {
			Map param = ParameterUtil.getParamMaps(request.getParameterMap());
			CriteriaQuery cq = new CriteriaQuery(EmkMInStorageEntity.class, dataGrid);
			//cq.eq("type","2");
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkMInStorage, request.getParameterMap());
			if(Utils.notEmpty(emkMInStorage.getId())){
				cq.add(Restrictions.sqlRestriction("(FIND_IN_SET({alias}.id,'"+emkMInStorage.getId()+"'))"));
			}
			List<EmkMInStorageEntity> emkMInStorageEntities = systemService.getListByCriteriaQuery(cq,false);
			String savepath = "c:\\采购入库单\\";
			File file = new File(savepath);
			if(!file.exists())
			{
				file.mkdirs();
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			String path = savepath+"采购入库单"+DateUtil.getCurrentTimeString("yyyyMMdd")+".xls";
			for(EmkMInStorageEntity emkMInStorageEntity : emkMInStorageEntities){
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
				List<Map<String,Object>> rkglMxEntities = systemService.findForJdbc(sql.toString(),emkMInStorageEntity.getId());
				ExcelUtil.createInStorageExcel(workbook,path,emkMInStorageEntity,headList,fieldList, rkglMxEntities);
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
	public String exportXlsByT(EmkMInStorageEntity emkMInStorage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"采购入库申请表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkMInStorageEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("采购入库申请表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkMInStorageEntity> listEmkMInStorageEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkMInStorageEntity.class,params);
				for (EmkMInStorageEntity emkMInStorage : listEmkMInStorageEntitys) {
					emkMInStorageService.save(emkMInStorage);
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
	@ApiOperation(value="采购入库申请表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkMInStorageEntity>> list() {
		List<EmkMInStorageEntity> listEmkMInStorages=emkMInStorageService.getList(EmkMInStorageEntity.class);
		return Result.success(listEmkMInStorages);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取采购入库申请表信息",notes="根据ID获取采购入库申请表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkMInStorageEntity task = emkMInStorageService.get(EmkMInStorageEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取采购入库申请表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建采购入库申请表")
	public ResponseMessage<?> create(@ApiParam(name="采购入库申请表对象")@RequestBody EmkMInStorageEntity emkMInStorage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkMInStorageEntity>> failures = validator.validate(emkMInStorage);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkMInStorageService.save(emkMInStorage);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("采购入库申请表信息保存失败");
		}
		return Result.success(emkMInStorage);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新采购入库申请表",notes="更新采购入库申请表")
	public ResponseMessage<?> update(@ApiParam(name="采购入库申请表对象")@RequestBody EmkMInStorageEntity emkMInStorage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkMInStorageEntity>> failures = validator.validate(emkMInStorage);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkMInStorageService.saveOrUpdate(emkMInStorage);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新采购入库申请表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新采购入库申请表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除采购入库申请表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkMInStorageService.deleteEntityById(EmkMInStorageEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("采购入库申请表删除失败");
		}

		return Result.success();
	}

	@RequestMapping(params="doApproval")
	@ResponseBody
	public AjaxJson doApproval(EmkMInStorageEntity2 emkMInStorageEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购入库单审核成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if(Utils.isEmpty(emkMInStorageEntity.getId())){
				for (String id : map.get("ids").toString().split(",")) {
					EmkMInStorageEntity inStorageEntity = systemService.getEntity(EmkMInStorageEntity.class, id);
					if (!inStorageEntity.getState().equals("0")) {
						message = "存在已审核的采购入库单，请重新选择采购入库单！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkMInStorageEntity.getId());
			}
			if (flag == 0) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkMInStorageEntity t = emkMInStorageService.get(EmkMInStorageEntity.class, id);
					t.setState("1");
				}
			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "采购入库单审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doCancelApproval")
	@ResponseBody
	public AjaxJson doCancelApproval(EmkMInStorageEntity2 emkMInStorageEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购入库单反审核成功";
		try {
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			for (String id : map.get("ids").toString().split(",")) {
				EmkMInStorageEntity t = emkMInStorageService.get(EmkMInStorageEntity.class, id);
				t.setState("0");
			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "采购入库单反审核失败";
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
		message = "采购入库单付款成功";
		try {
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			EmkMInStorageEntity t = emkMInStorageService.get(EmkMInStorageEntity.class, map.get("id"));
			if("1".equals(t.getPayState())){
				j.setMsg("采购入库单已付款");
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
				double blance = Double.parseDouble(bankRecordEntity.getBalance())-Double.parseDouble(t.getShMoney());
				emkBankRecordEntity.setBalance(String.valueOf(blance));
				emkBankRecordEntity.setOutcome(t.getShMoney());
				emkBankRecordEntity.setIncome("");
				emkBankRecordEntity.setDealDate(DateUtil.getCurrentTimeString(null));
				emkBankRecordEntity.setRemark("采购入库付款");
				bankRecordEntity.setBalance(emkBankRecordEntity.getBalance());
				systemService.save(emkBankRecordEntity);
				EmkBankRecordLogEntity  emkBankRecordLogEntity = new EmkBankRecordLogEntity();
				saveBankRecord(String.valueOf(blance),bankRecordEntity,emkBankRecordLogEntity);
				systemService.save(emkBankRecordLogEntity);
				t.setPayState(map.get("payState"));
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			message = "采购入库单付款失败";
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
		message = "采购入库单收货成功";
		try {
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			EmkMInStorageEntity t = emkMInStorageService.get(EmkMInStorageEntity.class, map.get("id"));
			t.setRecevieState(map.get("recevieState"));

			List<EmkMInStorageDetailEntity> inStorageDetailEntityList = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",t.getId());

			EmkStorageLogEntity storageLogEntity = null;
			for(EmkMInStorageDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
				//保存库存数据和日记

			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "采购入库单收货失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkMInStorageEntity2 emkMInStorageEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购入库单提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if(Utils.isEmpty(emkMInStorageEntity.getId())){
				for (String id : map.get("ids").toString().split(",")) {
					EmkMInStorageEntity inStorageEntity = systemService.getEntity(EmkMInStorageEntity.class, id);
					if (!inStorageEntity.getState().equals("0")) {
						message = "存在已提交的采购入库单，请重新选择在提交采购入库单！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkMInStorageEntity.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkMInStorageEntity t = emkMInStorageService.get(EmkMInStorageEntity.class, id);

					variables.put("optUser", t.getId());
					message = "操作成功";

					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
					if(Utils.isEmpty(b)){
						//type 工单类型，workNum 单号，formId 对应表单ID，bpmName 节点名称，bpmNode 节点代码，advice 处理意见，user 用户对象
						b = new EmkApprovalEntity();
						EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
						ApprovalUtil.saveApproval(b,5,t.getA01a03a07(),t.getId(),user);
						systemService.save(b);
						ApprovalUtil.saveApprovalDetail(approvalDetailEntity,b.getId(),"采购入库单","instorageTask","提交",user);
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
						if (task1.getTaskDefinitionKey().equals("instorageTask")) {
							List<EmkMInStorageDetailEntity> inStorageDetailEntityList = systemService.findHql("from EmkMInStorageDetailEntity where inStorageId=?",t.getId());
							/*for(EmkMInStorageDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
								EmkStorageEntity storageEntity = systemService.singleResult("from EmkStorageEntity where proNum='"+inStorageDetailEntity.getProNum()+"' and brand='"+inStorageDetailEntity.getBrand()+"' and cusNum is null ");
								if(Utils.notEmpty(storageEntity)){
									message = "已存在产品名称："+inStorageDetailEntity.getProZnName()+"，产品编号："+inStorageDetailEntity.getProNum()+"，型号："+inStorageDetailEntity.getBrand();
									j.setMsg(message);
									j.setSuccess(false);
									return j;
								}
							}*/
							taskService.complete(task1.getId(), variables);
							t.setState("1");
							b.setStatus(1);


							b.setProcessName("采购入库单");

							saveApprvoalDetail(approvalDetail,"重新提交的采购入库单","instorageTask",0,"重新提交采购入库单");
							saveSmsAndEmailForOne("采购入库单","您有【"+user.getRealName()+"】重新提交的采购入库单，单号："+b.getWorkNum()+"，请及时审核。",bpmUser,user.getUserName());

						}
						if (task1.getTaskDefinitionKey().equals("checkTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(24);
								approvalDetail.setBpmName("采购员审核");
								t.setState("24");
								approvalDetail.setApproveStatus(0);
							}else{
								saveApprvoalDetail(approvalDetail,"采购员审核","checkTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"checkTask","instorageTask","采购入库单");
								systemService.executeSql("delete from act_hi_actinst  where proc_inst_id_=? and act_id_=? ",task1.getProcessInstanceId(),"isPass");
								t.setState("0");
								b.setStatus(0);
								b.setBpmStatus("1");
								approvalDetail.setApproveStatus(1);
								saveSmsAndEmailForOne("采购员审核","您有【"+user.getRealName()+"】回退的采购入库单，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("cgyzjTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(40);
								approvalDetail.setBpmName("采购员质检");
								t.setState("40");
								approvalDetail.setApproveStatus(0);

								saveSmsAndEmailForOne("采购员质检","您有【"+user.getRealName()+"】质检通过的采购入库单，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}else{
								saveApprvoalDetail(approvalDetail,"采购员质检","cgyzjTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"cgyzjTask","checkTask","采购员审核");
								systemService.executeSql("delete from act_hi_actinst  where proc_inst_id_=? and act_id_=? ",task1.getProcessInstanceId(),"isPass");
								t.setState("25");
								b.setStatus(25);
								b.setBpmStatus("1");
								approvalDetail.setApproveStatus(1);
							}
						}

						systemService.save(approvalDetail);

					}else {

					}
					systemService.saveOrUpdate(t);
					systemService.saveOrUpdate(b);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "采购入库单提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="goWork")
	public ModelAndView goWork(EmkMInStorageEntity emkMInStorageEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkMInStorageEntity.getId())) {
			emkMInStorageEntity = emkMInStorageService.getEntity(EmkMInStorageEntity.class, emkMInStorageEntity.getId());
			req.setAttribute("emkInStoragePage", emkMInStorageEntity);
		}
		return new ModelAndView("com/emk/bound/minstorage/emkMInStorage-work");
	}

	@RequestMapping(params="goTime")
	public ModelAndView goTime(EmkMInStorageEntity emkMInStorageEntity, HttpServletRequest req, DataGrid dataGrid) {
		EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkMInStorageEntity.getId());
		if(Utils.notEmpty(approvalEntity)){
			List<EmkApprovalDetailEntity> approvalDetailEntityList = systemService.findHql("from EmkApprovalDetailEntity where approvalId=? order by approveDate asc",approvalEntity.getId());
			req.setAttribute("approvalDetailEntityList", approvalDetailEntityList);
			req.setAttribute("approvalEntity", approvalEntity);
			req.setAttribute("createDate", org.jeecgframework.core.util.DateUtils.date2Str(approvalEntity.getCreateDate(), org.jeecgframework.core.util.DateUtils.datetimeFormat));
		}

		return new ModelAndView("com/emk/bound/minstorage/time");
	}
}
