package com.emk.storage.cancelorder.controller;
import com.emk.caiwu.yfcheck.entity.EmkFinanceYfCheckEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.product.product.entity.EmkProductEntity;
import com.emk.storage.cancelorder.entity.EmkCancelOrderDetailEntity;
import com.emk.storage.cancelorder.entity.EmkCancelOrderEntity;
import com.emk.storage.cancelorder.service.EmkCancelOrderServiceI;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.storage.storage.entity.EmkStorageEntity;
import com.emk.storage.storagelog.entity.EmkStorageLogEntity;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import com.emk.util.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.tools.ant.util.*;
import org.apache.tools.ant.util.DateUtils;
import org.jeecgframework.core.util.*;
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
 * @Description: 退货单
 * @author onlineGenerator
 * @date 2019-09-02 11:50:23
 * @version V1.0   
 *
 */
@Api(value="EmkCancelOrder",description="退货单",tags="emkCancelOrderController")
@Controller
@RequestMapping("/emkCancelOrderController")
public class EmkCancelOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkCancelOrderController.class);

	@Autowired
	private EmkCancelOrderServiceI emkCancelOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 退货单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A16' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		request.setAttribute("headcategoryEntities",categoryEntities);
		return new ModelAndView("com/emk/storage/cancelorder/emkCancelOrderList");
	}

	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		request.setAttribute("kdDate", org.apache.tools.ant.util.DateUtils.format(new Date(), "yyyy-MM-dd"));
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show from hm_category where PARENT_CODE='A01A09' and type='6' and org_code=? and code!='A01A09A13' order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
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
			List<EmkCancelOrderDetailEntity> rkglMxEntities = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=? ",  map.get("inStorageId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/storage/cancelorder/orderMxList");
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
	public void datagrid(EmkCancelOrderEntity emkCancelOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkCancelOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkCancelOrder, request.getParameterMap());
		try{
			//自定义追加查询条件
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			if(!user.getUserName().equals("admin")){
				cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			}

		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkCancelOrderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除退货单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkCancelOrder = systemService.getEntity(EmkCancelOrderEntity.class, emkCancelOrder.getId());
		message = "退货单删除成功";
		try{
			emkCancelOrderService.delete(emkCancelOrder);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "退货单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除退货单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "退货单删除成功";
		try{
			for(String id:ids.split(",")){
				EmkCancelOrderEntity emkCancelOrder = systemService.getEntity(EmkCancelOrderEntity.class,id);
				emkCancelOrderService.delete(emkCancelOrder);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

			}
		}catch(Exception e){
			e.printStackTrace();
			message = "退货单删除失败";
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

	void saveOldLog2(EmkStorageLogEntity newStorageLogEntity,EmkStorageEntity oldstorageEntity,EmkCancelOrderDetailEntity old){
		newStorageLogEntity.setId(null);
		newStorageLogEntity.setPreTotal(oldstorageEntity.getA01a09a02());
		newStorageLogEntity.setTotal(old.getA01a09a02());
		int nextTotal  = Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getPreTotal()) ? newStorageLogEntity.getPreTotal():"0")+
				Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getTotal()) ? newStorageLogEntity.getTotal():"0");
		newStorageLogEntity.setNextTotal(String.valueOf(nextTotal));
		systemService.save(newStorageLogEntity);
	}

	EmkProductEntity saveEmkProductEntity(EmkCancelOrderDetailEntity emkMOutStorageDetailEntity,String orgCode,TSDepart tsDepart){
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

	EmkStorageEntity findStorageAndSaveLog(EmkCancelOrderDetailEntity emkMOutStorageDetailEntity,EmkCancelOrderEntity t,String orgCode,EmkStorageLogEntity storageLogEntity,String remark){
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
			hql += " and flag=1";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a11())){
			hql += " and a01a09a11='"+emkMOutStorageDetailEntity.getA01a09a11()+"'";
		}else{
			hql += " and a01a09a11 is null";
		}
		if(Utils.notEmpty(emkMOutStorageDetailEntity.getA01a09a10())){
			hql += " and a01a09a10='"+emkMOutStorageDetailEntity.getA01a09a10()+"'";
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

		storageLogEntity.setRkNo(t.getA01a16a07());
		storageLogEntity.setType("4");
		storageLogEntity.setProNum(emkMOutStorageDetailEntity.getA01a09a08());
		storageLogEntity.setProZnName(emkMOutStorageDetailEntity.getA01a09a07());
		storageLogEntity.setMarkDate(DateUtil.getLogTime());
		storageLogEntity.setRemark(remark);
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setStorageId(t.getStorageId());
		storageLogEntity.setStorageName(t.getStorageName());
		return storageEntity;
	}

	EmkStorageEntity findOldStorage(EmkCancelOrderEntity oldT,EmkCancelOrderDetailEntity old,String orgCode){
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

	void saveNewStorage(EmkCancelOrderDetailEntity emkMOutStorageDetailEntity,EmkCancelOrderEntity t,EmkProductEntity emkProductEntity,EmkStorageLogEntity storageLogEntity){
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
		systemService.save(storageEntity);
		systemService.executeSql("update emk_storage set a01a09a02=-a01a09a02 where id=? ",storageEntity.getId());

		systemService.executeSql("update emk_storage set a01a09a13=truncate(a01a09a03/a01a09a02,2) where id=? ",storageEntity.getId());
		storageLogEntity.setPreTotal("0");
		storageLogEntity.setNextTotal(emkMOutStorageDetailEntity.getA01a09a02());
		storageLogEntity.setTotal(emkMOutStorageDetailEntity.getA01a09a02());
		storageLogEntity.setDepartId(t.getDepartId());
		storageLogEntity.setOrgCode(t.getOrgCode());
		storageEntity.setStorageName(t.getStorageName());
		storageEntity.setStorageId(t.getStorageId());
		systemService.save(storageLogEntity);
	}

	void saveOldStorage(EmkStorageEntity oldstorageEntity,EmkCancelOrderDetailEntity old,EmkStorageLogEntity storageLogEntity){
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
	 * 添加退货单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkCancelOrderEntity emkCancelOrder,EmkSizeEntity emkSize, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "退货单添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");

			emkCancelOrder.setState("0");
			emkCancelOrder.setPayState("0");
			emkCancelOrder.setRecevieState("0");

			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			Class c = Class.forName(EmkCancelOrderDetailEntity.class.getName());
			Method show = null;

			emkCancelOrder.setOrgCode(tsDepart.getOrgCode());
			emkCancelOrder.setDepartId(tsDepart.getId());
			TSUser tsUser = systemService.findUniqueByProperty(TSUser.class,"userName",map.get("userName"));
			systemService.save(emkCancelOrder);

			EmkFinanceYfCheckEntity emkFinanceYfCheckEntity = new EmkFinanceYfCheckEntity();
			emkFinanceYfCheckEntity.setYfCheckNo(emkCancelOrder.getA01a16a07());
			emkFinanceYfCheckEntity.setState("0");
			emkFinanceYfCheckEntity.setDepartId(tsDepart.getId());
			emkFinanceYfCheckEntity.setOrgCode(tsDepart.getOrgCode());
			emkFinanceYfCheckEntity.setStorageId(emkCancelOrder.getStorageId());
			emkFinanceYfCheckEntity.setStorageName(emkCancelOrder.getStorageName());
			emkFinanceYfCheckEntity.setBankAccount(emkCancelOrder.getBankMoney());
			emkFinanceYfCheckEntity.setBankType(emkCancelOrder.getA01a16a09());
			emkFinanceYfCheckEntity.setGys(emkCancelOrder.getGys());
			emkFinanceYfCheckEntity.setGysCode(emkCancelOrder.getA01a16a06());
			emkFinanceYfCheckEntity.setYfMoney(Double.parseDouble(Utils.notEmpty(emkCancelOrder.getYfMoney()) ? emkCancelOrder.getYfMoney():"0"));
			emkFinanceYfCheckEntity.setMoney(Double.parseDouble(Utils.notEmpty(emkCancelOrder.getMoney()) ? emkCancelOrder.getMoney():"0"));
			emkFinanceYfCheckEntity.setQkMoney(Double.parseDouble(Utils.notEmpty(emkCancelOrder.getYfMoney()) ? emkCancelOrder.getYfMoney():"0"));
			emkFinanceYfCheckEntity.setClearWs(0.0);
			emkFinanceYfCheckEntity.setMarker(user.getRealName());
			emkFinanceYfCheckEntity.setMarkerId(user.getId());
			systemService.save(emkFinanceYfCheckEntity);

			EmkFactoryArchivesEntity emkFactoryArchivesEntity = systemService.singleResult("from EmkFactoryArchivesEntity where companyCode='"+emkCancelOrder.getA01a16a06()+"' and orgCode='"+orgCode+"'");

			List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='6' and org_code=? order by order_num asc",orgCode);
			EmkCancelOrderDetailEntity emkCancelOrderDetailEntity = null;
			EmkProductEntity emkProductEntity = null;
			dataRows = map.get("orderMxListID");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkStorageLogEntity storageLogEntity = null;

				for (int i = 0; i < rows; i++) {
					emkCancelOrderDetailEntity = new EmkCancelOrderDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						emkCancelOrderDetailEntity.setDepartId(emkCancelOrder.getDepartId());
						emkCancelOrderDetailEntity.setOrgCode(emkCancelOrder.getOrgCode());
						emkCancelOrderDetailEntity.setInStorageId(emkCancelOrder.getId());
						for(Map category : categoryEntities){
							if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
								String m0 = "setA"+category.get("code").toString().substring(1);
								show = c.getMethod(m0,String.class);
								show.invoke(emkCancelOrderDetailEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
							}
						}
						systemService.save(emkCancelOrderDetailEntity);
						//判断在商品管理是否有存在，不存在则创建
						emkProductEntity = saveEmkProductEntity(emkCancelOrderDetailEntity,orgCode,tsDepart);
						//查询库存数据和保存库存数据日记
						storageLogEntity = new EmkStorageLogEntity();
						EmkStorageEntity storageEntity = findStorageAndSaveLog(emkCancelOrderDetailEntity,emkCancelOrder,orgCode,storageLogEntity,"录入退货单");
						if(Utils.isEmpty(storageEntity)){
							saveNewStorage(emkCancelOrderDetailEntity,emkCancelOrder,emkProductEntity,storageLogEntity);
						}else{
							systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?) where id=? ",emkCancelOrderDetailEntity.getA01a09a02(),storageEntity.getId());
							systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());

							saveOldLog1(storageLogEntity,storageEntity);
						}
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "退货单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新退货单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkCancelOrderEntity emkCancelOrder,EmkSizeEntity emkSize,HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "退货单更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		String dataRows = map.get("orderMxListID");

		EmkCancelOrderEntity t = systemService.get(EmkCancelOrderEntity.class, map.get("emkMInStorageId"));
		EmkCancelOrderEntity oldT = new EmkCancelOrderEntity();
		MyBeanUtils.copyBeanNotNull2Bean(t,oldT);
		List<EmkCancelOrderDetailEntity> emkCancelOrderDetailEntityList = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?",t.getId());
		Map<String,EmkCancelOrderDetailEntity> detailMap = new HashMap<>();
		for(EmkCancelOrderDetailEntity emkCancelOrderDetailEntity : emkCancelOrderDetailEntityList){
			detailMap.put(emkCancelOrderDetailEntity.getId(),emkCancelOrderDetailEntity);
		}
		try {
			if(!t.getState().equals("0")){
				j.setMsg("退货单在处理中无法进行修改");
				j.setSuccess(false);
				return j;
			}
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));

			emkCancelOrder.setState("0");
			emkCancelOrder.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkCancelOrder, t);
			systemService.saveOrUpdate(t);
			EmkFactoryArchivesEntity emkFactoryArchivesEntity = systemService.singleResult("from EmkFactoryArchivesEntity where companyCode='"+emkCancelOrder.getA01a16a06()+"' and orgCode='"+orgCode+"'");
			if(Utils.notEmpty(emkCancelOrder.getBcqkMoney())){
				emkFactoryArchivesEntity.setBcqkMoney(emkCancelOrder.getBcqkMoney());
			}

			dataRows = map.get("orderMxListID");

			if (Utils.notEmpty(dataRows)) {

				systemService.executeSql("delete from emk_cancel_order_detail where in_storage_id = ? ",t.getId());
				int rows = Integer.parseInt(dataRows);
				EmkStorageLogEntity storageLogEntity = null;
				EmkStorageLogEntity newStorageLogEntity = null;
				EmkProductEntity emkProductEntity = null;

				List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09' and type='0' and org_code=?  order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
				Class c = Class.forName(EmkCancelOrderDetailEntity.class.getName());
				EmkCancelOrderDetailEntity emkCancelOrderDetailEntity = null;
				Method show = null;
				EmkCancelOrderDetailEntity old = null;
				for (int i = 0; i < rows; i++) {
					emkCancelOrderDetailEntity = new EmkCancelOrderDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
						if(Utils.notEmpty(map.get("orderMxList["+i+"].id"))){
							old = detailMap.get(map.get("orderMxList["+i+"].id"));
						}

						emkCancelOrderDetailEntity.setInStorageId(t.getId());
						emkCancelOrderDetailEntity.setDepartId(t.getDepartId());
						emkCancelOrderDetailEntity.setOrgCode(t.getOrgCode());

						for(Map category : categoryEntities){
							if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
								String m0 = "setA"+category.get("code").toString().substring(1);
								show = c.getMethod(m0,String.class);
								show.invoke(emkCancelOrderDetailEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
							}
						}
						systemService.save(emkCancelOrderDetailEntity);
						//判断在商品管理是否有存在，不存在则创建
						emkProductEntity = saveEmkProductEntity(emkCancelOrderDetailEntity,orgCode,tsDepart);
						//查询库存数据和保存库存数据日记
						storageLogEntity = new EmkStorageLogEntity();
						EmkStorageEntity storageEntity = findStorageAndSaveLog(emkCancelOrderDetailEntity,emkCancelOrder,orgCode,storageLogEntity,"编辑销售开单");
						//查询旧库存数据
						EmkStorageEntity oldstorageEntity = findOldStorage(oldT,old,orgCode);
						//变更出货仓库
						if(Utils.notEmpty(emkCancelOrder.getStorageId())){
							if(!emkCancelOrder.getStorageId().equals(oldT.getStorageId())){
								if(Utils.notEmpty(oldstorageEntity)){
									saveOldStorage(oldstorageEntity,old,storageLogEntity);
								}
								if(Utils.notEmpty(storageEntity)){
									systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?+?) where id=? ",
											emkCancelOrderDetailEntity.getA01a09a02(),old.getA01a09a02(),storageEntity.getId());
									systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
									storageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
									saveOldLog1(storageLogEntity,oldstorageEntity);
								}else {
									saveNewStorage(emkCancelOrderDetailEntity,t,emkProductEntity,storageLogEntity);
								}
								j.setSuccess(true);
								j.setMsg(message);
								return j;

							}
						}
						//变更货位
						if(Utils.notEmpty(emkCancelOrderDetailEntity.getA01a09a05())){
							if(!emkCancelOrderDetailEntity.getA01a09a05().equals(old.getA01a09a05())){
								if(Utils.notEmpty(oldstorageEntity)){
									saveOldStorage(oldstorageEntity,old,storageLogEntity);
								}
								if(Utils.notEmpty(storageEntity)){
									systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?+?) where id=? ",
											emkCancelOrderDetailEntity.getA01a09a02(),old.getA01a09a02(),storageEntity.getId());
									systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
								}else {
									saveNewStorage(emkCancelOrderDetailEntity,t,emkProductEntity,storageLogEntity);
								}
								j.setSuccess(true);
								j.setMsg(message);
								return j;

							}
						}
						if(Utils.isEmpty(storageEntity)){
							saveNewStorage(emkCancelOrderDetailEntity,t,emkProductEntity,storageLogEntity);

							if(Utils.notEmpty(oldstorageEntity)){
								saveOldStorage(oldstorageEntity,old,storageLogEntity);
							}

						}else{
							if(Utils.notEmpty(old)){
								systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02-?+?) where id=? ",
										emkCancelOrderDetailEntity.getA01a09a02(),old.getA01a09a02(),storageEntity.getId());
							}else{
								systemService.executeSql("update emk_storage set a01a09a12=? where id=? ",emkCancelOrderDetailEntity.getA01a09a12(),storageEntity.getId());
							}
							systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ", storageEntity.getId());
							storageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
							saveOldLog1(storageLogEntity,oldstorageEntity);
						}
					}
				}
				EmkFinanceYfCheckEntity emkFinanceYfCheckEntity = systemService.singleResult("from EmkFinanceYfCheckEntity where orgCode='"+orgCode+"' and" +
						"	yfCheckNo='"+t.getA01a16a07()+"'");
				emkFinanceYfCheckEntity.setStorageId(t.getStorageId());
				emkFinanceYfCheckEntity.setStorageName(t.getStorageName());
				emkFinanceYfCheckEntity.setBankAccount(t.getBankMoney());
				emkFinanceYfCheckEntity.setBankType(t.getA01a16a09());
				emkFinanceYfCheckEntity.setGys(t.getGys());
				emkFinanceYfCheckEntity.setGysCode(t.getA01a16a06());
				emkFinanceYfCheckEntity.setYfMoney(Double.parseDouble(Utils.notEmpty(t.getYfMoney()) ? t.getYfMoney():"0"));
				emkFinanceYfCheckEntity.setMoney(Double.parseDouble(Utils.notEmpty(t.getMoney()) ? t.getMoney():"0"));
				emkFinanceYfCheckEntity.setQkMoney(Double.parseDouble(Utils.notEmpty(t.getYfMoney()) ? t.getYfMoney():"0"));
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
	 * 退货单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A16' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.setAttribute("headcategoryEntities",categoryEntities);
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
		TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='退货单号前缀' and departId='"+tsDepart.getId()+"'");

		Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(a01a16a07, 4)),0)+1 AS signed) orderNum from emk_cancel_order where org_code=?",orgCode);
		emkCancelOrder.setA01a16a07(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
		req.setAttribute("emkCancelOrderPage", emkCancelOrder);
		systemService.executeSql("delete from emk_storage where a01a09a02=0 and org_code=?",orgCode);

		return new ModelAndView("com/emk/storage/cancelorder/emkCancelOrder-add");
	}

	/**
	 * 退货单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",user.getCurrentDepart().getOrgCode().substring(0,3));

		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A16' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
		req.setAttribute("headcategoryEntities",categoryEntities);

		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkCancelOrder.getId())) {
			emkCancelOrder = systemService.getEntity(EmkCancelOrderEntity.class, emkCancelOrder.getId());
		}

		req.setAttribute("emkCancelOrderPage", emkCancelOrder);
		return new ModelAndView("com/emk/storage/cancelorder/emkCancelOrder-update");
	}
	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkCancelOrder.getId())) {
			emkCancelOrder = emkCancelOrderService.findUniqueByProperty(EmkCancelOrderEntity.class,"orderId",emkCancelOrder.getId());
			req.setAttribute("emkCancelOrderPage", emkCancelOrder);
		}
		return new ModelAndView("com/emk/storage/cancelorder/emkCancelOrder-update2");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		return new ModelAndView("com/emk/storage/cancelorder/uploadView");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkCancelOrderEntity emkCancelOrder,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkCancelOrderEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkCancelOrder, request.getParameterMap());
		List<EmkCancelOrderEntity> emkCancelOrders = this.emkCancelOrderService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"退货单");
		modelMap.put(NormalExcelConstants.CLASS,EmkCancelOrderEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("退货单列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkCancelOrders);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkCancelOrderEntity emkCancelOrder,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"退货单");
    	modelMap.put(NormalExcelConstants.CLASS,EmkCancelOrderEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("退货单列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(EmkCancelOrderEntity emkCancelOrder,String fileName,String fileNameUrl,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		String message = "文件导入成功";
		File newfile = null;
		HSSFWorkbook wb = null;
		String cellValue = "";
		EmkEnquiryDetailEntity orderMxEntity = null;
		EmkSizeTotalEntity emkSizeTotalEntity = null;
		String orderNo = emkCancelOrder.getOrderNo();

		try {
			String savepath = request.getRealPath("/")+"imp/order/";
			newfile =  new File(savepath+fileName);
			wb = WebFileUtils.createHSSFWorkBook(newfile);
			HSSFSheet sheet = wb.getSheetAt(0);
			DecimalFormat df = new DecimalFormat("0");
			HSSFCell cell = null;
			int counter = 0;
			HSSFRow row = null;
			logger.info("执行导入："+newfile.getName());
			List<String> itemValue = null;
			Map orderNum = null;
			TSDepart tsDepart = null;
			String m0 = "";
			Method show = null;

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				itemValue = new ArrayList<String>();
				for(int z = 0; z <= 28 ; z++){
					cell = row.getCell(z);
					if(null == cell){
						itemValue.add(cellValue);
						continue;
					}
					switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							cellValue =cell.getRichStringCellValue().getString().trim();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
								SimpleDateFormat sdf = null;
								if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
										.getBuiltinFormat("h:mm")) {
									sdf = new SimpleDateFormat("HH:mm");
								} else {// 日期
									sdf = new SimpleDateFormat("yyyy-MM-dd");
								}
								Date date = cell.getDateCellValue();
								cellValue = sdf.format(date);
							} else if (cell.getCellStyle().getDataFormat() == 58) {
								// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								double value = cell.getNumericCellValue();
								Date date = org.apache.poi.ss.usermodel.DateUtil
										.getJavaDate(value);
								cellValue = sdf.format(date);
							} else {
								double value = cell.getNumericCellValue();
								CellStyle style = cell.getCellStyle();
								DecimalFormat format = new DecimalFormat();
								String temp = style.getDataFormatString();
								// 单元格设置成常规
								if (temp.equals("General")) {
									format.applyPattern("#");
								}
								cellValue = format.format(value);
							}
							//cellValue = df.format(cell.getNumericCellValue()).toString();
							break;

						default:
							cellValue = "";
					}
					itemValue.add(cellValue);
					cellValue = "";
				}
				if(i == 0 ){
					EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkCancelOrder.getOrderNo());
					if(Integer.valueOf(emkEnquiryEntity.getState().toString())<11){
						j.setMsg("存在未验货的订单，无法录入新的数据");
						j.setSuccess(false);
						return j;
					}
					EmkEnquiryEntity t = new EmkEnquiryEntity();
					t.setState("11");
					MyBeanUtils.copyBeanNotNull2Bean(emkEnquiryEntity,t);
					t.setId(null);
					systemService.save(t);

					emkCancelOrder.setState("0");
					emkCancelOrder.setOrderId(t.getId());

					TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
					Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
					emkCancelOrderService.save(emkCancelOrder);

					EmkSizeEntity emkSize = new EmkSizeEntity();
					Class c = Class.forName(EmkSizeEntity.class.getName());
					emkSize.setFormId(emkCancelOrder.getId());
					for(int z = 1 ; z < 23 ; z++){
						m0 = "setSize"+(char)(z+64);
						show = c.getMethod(m0,String.class);
						show.invoke(emkSize,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
					}
					systemService.save(emkSize);


				}else{
					if(Utils.notEmpty(itemValue.get(0))){
						orderMxEntity = new EmkEnquiryDetailEntity();
						orderMxEntity.setEnquiryId(emkCancelOrder.getId());
						orderMxEntity.setColorValue(itemValue.get(0));
						orderMxEntity.setColor(itemValue.get(1));
						orderMxEntity.setSize(itemValue.get(2));

						orderMxEntity.setSortDesc(String.valueOf(i+1));
						emkSizeTotalEntity = new EmkSizeTotalEntity();
						Class c = Class.forName(EmkSizeTotalEntity.class.getName());
						int total = 0;
						for(int z = 1 ; z < 23 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							total += Integer.valueOf(Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"0");
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
						}
						orderMxEntity.setTotal(total);

						Map price = systemService.findOneForJdbc("select ifnull(t1.price,0) price from emk_enquiry t LEFT JOIN emk_enquiry_detail t1 on t1.enquiry_id=t.id " +
								"where t.enquiry_no=? and t1.color_value=? and t1.color=? and t1.size=?",orderNo,orderMxEntity.getColorValue(),orderMxEntity.getColor(),orderMxEntity.getSize());
						orderMxEntity.setPrice(Double.valueOf(price.get("price").toString()));
						orderMxEntity.setMoney(total*orderMxEntity.getPrice());

						systemService.save(orderMxEntity);

						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}

				j.setSuccess(true);
			}
		} catch (Exception e) {
			message = "文件导入失败";
			j.setSuccess(false);
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}finally{
			newfile.delete();
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="退货单列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkCancelOrderEntity>> list() {
		List<EmkCancelOrderEntity> listEmkCancelOrders=emkCancelOrderService.getList(EmkCancelOrderEntity.class);
		return Result.success(listEmkCancelOrders);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取退货单信息",notes="根据ID获取退货单信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkCancelOrderEntity task = emkCancelOrderService.get(EmkCancelOrderEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取退货单信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建退货单")
	public ResponseMessage<?> create(@ApiParam(name="退货单对象")@RequestBody EmkCancelOrderEntity emkCancelOrder, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkCancelOrderEntity>> failures = validator.validate(emkCancelOrder);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkCancelOrderService.save(emkCancelOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("退货单信息保存失败");
		}
		return Result.success(emkCancelOrder);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新退货单",notes="更新退货单")
	public ResponseMessage<?> update(@ApiParam(name="退货单对象")@RequestBody EmkCancelOrderEntity emkCancelOrder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkCancelOrderEntity>> failures = validator.validate(emkCancelOrder);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkCancelOrderService.saveOrUpdate(emkCancelOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新退货单信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新退货单信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除退货单")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkCancelOrderService.deleteEntityById(EmkCancelOrderEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("退货单删除失败");
		}

		return Result.success();
	}
}
