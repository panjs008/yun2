package com.emk.storage.formaterial.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.storage.formaterial.entity.EmkFormaterailDetailEntity;
import com.emk.storage.formaterial.entity.EmkFormaterialEntity;
import com.emk.storage.formaterial.service.EmkFormaterialServiceI;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.util.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.jeecgframework.core.util.*;
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
 * @Description: 叫布表
 * @author onlineGenerator
 * @date 2019-08-24 14:00:26
 * @version V1.0   
 *
 */
@Api(value="EmkFormaterial",description="叫布表",tags="emkFormaterialController")
@Controller
@RequestMapping("/emkFormaterialController")
public class EmkFormaterialController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkFormaterialController.class);

	@Autowired
	private EmkFormaterialServiceI emkFormaterialService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 叫布表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/storage/formaterial/emkFormaterialList");
	}

	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(pm,'-',color_num) typecode from emk_pm_color ");
		String color = JSONHelper.collection2json(list);
		request.setAttribute("color", "'"+color+ "'");

		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("formaterialId"))) {
			List<EmkFormaterailDetailEntity> emkFormaterailDetailEntityList = systemService.findHql("from EmkFormaterailDetailEntity where formaterialId = ?",map.get("formaterialId"));
			request.setAttribute("emkFormaterailDetailEntityList", emkFormaterailDetailEntityList);
			EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("formaterialId"));
			request.setAttribute("emkSizePage", emkSizeEntity);
		}
		return new ModelAndView("com/emk/storage/formaterial/orderMxList");
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
	public void datagrid(EmkFormaterialEntity emkFormaterial,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkFormaterialEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkFormaterial, request.getParameterMap());
		try{
			//自定义追加查询条件
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			if(user.getUserKey().equals("工厂")) {
				EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
				cq.eq("gysCode",factoryArchivesEntity.getCompanyCode());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkFormaterialService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除叫布表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkFormaterialEntity emkFormaterial, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkFormaterial = systemService.getEntity(EmkFormaterialEntity.class, emkFormaterial.getId());
		message = "叫布表删除成功";
		try{
			emkFormaterialService.delete(emkFormaterial);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "叫布表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除叫布表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "叫布表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkFormaterialEntity emkFormaterial = systemService.getEntity(EmkFormaterialEntity.class, 
				id
				);
				if (!emkFormaterial.getState().equals("0")) {
					message = "存在已提交的叫布，请重新选择！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}

				systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_formaterail_detail where formaterial_id=?))",id);
				systemService.executeSql("delete from emk_formaterail_detail where formaterial_id=?",id);
				systemService.executeSql("delete from emk_size where form_id=?", id);
				emkFormaterialService.delete(emkFormaterial);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "叫布表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加叫布表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkFormaterialEntity emkFormaterial,EmkSizeEntity emkSize,  HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "叫布表添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			/*List<EmkFormaterialEntity> list = systemService.findHql("from EmkFormaterialEntity where state=0");
			if(list.size()>0){
				j.setMsg("存在未提交的叫布，无法录入新的数据");
				j.setSuccess(false);
				return j;
			}*/
			/*EmkEnquiryEntity enquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkFormaterial.getOrderNo());
			Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no, 1)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+emkFormaterial.getOrderNo()+"-叫布"+"%'");

			if(!enquiryEntity.getState().equals("1") && !enquiryEntity.getState().equals("5")){
				emkFormaterial.setOrderNo(enquiryEntity.getEnquiryNo()+"-叫布"+orderNum.get("orderNum"));
			}else{
				emkFormaterial.setOrderId(enquiryEntity.getId());
			}*/
			String eNo = "";
			if(emkFormaterial.getOrderNo().contains("叫布")){
				eNo = emkFormaterial.getOrderNo().substring(0,emkFormaterial.getOrderNo().indexOf("-叫布"));
			}else{
				eNo = emkFormaterial.getOrderNo();
			}
			EmkFormaterialEntity emkFormaterialEntity = systemService.findUniqueByProperty(EmkFormaterialEntity.class,"orderNo",eNo);
			Map<String, Object> orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no,2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+eNo+"-出货%'");
			EmkEnquiryEntity enquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkFormaterial.getOrderNo());
			if(orderNum.get("orderNum").toString().equals("1") && Utils.isEmpty(emkFormaterialEntity)){
				emkFormaterial.setOrderId(enquiryEntity.getId());
			}else{
				emkFormaterial.setOrderNo(eNo+"-叫布"+String.format("%02d", Integer.valueOf(orderNum.get("orderNum").toString())));
			}
			emkFormaterial.setState("0");


			emkFormaterialService.save(emkFormaterial);
			emkSize.setFormId(emkFormaterial.getId());
			systemService.save(emkSize);
			Class c = Class.forName(EmkSizeTotalEntity.class.getName());
			String dataRows = (String) map.get("dataRowsVal");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkFormaterailDetailEntity orderMxEntity = null;
				EmkSizeTotalEntity emkSizeTotalEntity = null;
				String m0 = "";
				Method show = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
						orderMxEntity = new EmkFormaterailDetailEntity();
						orderMxEntity.setFormaterialId(emkFormaterial.getId());
						if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
							String[] colorArr = map.get("orderMxList["+i+"].color").split("-");
							orderMxEntity.setPm(colorArr[0]);
							orderMxEntity.setColorNum(colorArr[1]);
						}
						orderMxEntity.setSortDesc(String.valueOf(i+1));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
							orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].total")));
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
							orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
							orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
						}
						systemService.save(orderMxEntity);

						emkSizeTotalEntity = new EmkSizeTotalEntity();
						for(int z = 1 ; z < 17 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(map.get("orderMxList["+i+"].total"+(char)(z+64))) ? map.get("orderMxList["+i+"].total"+(char)(z+64)):"");
						}
						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "叫布表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新叫布表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkFormaterialEntity emkFormaterial,EmkSizeEntity emkSize, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "叫布表更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		EmkFormaterialEntity t = emkFormaterialService.get(EmkFormaterialEntity.class, emkFormaterial.getId());
		if (!t.getState().equals("0")) {
			message = "存在已提交的叫布，请重新选择！";
			j.setMsg(message);
			j.setSuccess(false);
			return j;
		}
		EmkSizeEntity t2 = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",t.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(emkFormaterial, t);
			emkFormaterialService.saveOrUpdate(t);

			emkSize.setId(null);
			if(Utils.notEmpty(t2)){
				MyBeanUtils.copyBeanNotNull2Bean(emkSize, t2);
				systemService.saveOrUpdate(t2);
			}else{
				t2 = new EmkSizeEntity();
				MyBeanUtils.copyBeanNotNull2Bean(emkSize, t2);
				t2.setFormId(t.getId());
				systemService.save(t2);
			}
			Class c = Class.forName(EmkSizeTotalEntity.class.getName());
			String dataRows = (String) map.get("dataRowsVal");
			if (Utils.notEmpty(dataRows)) {
				systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_formaterail_detail where formaterial_id=?))", t.getId());
				systemService.executeSql("delete from emk_formaterail_detail where formaterial_id=?",t.getId());
				int rows = Integer.parseInt(dataRows);
				EmkFormaterailDetailEntity orderMxEntity = null;
				EmkSizeTotalEntity emkSizeTotalEntity = null;
				String m0 = "";
				Method show = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
						orderMxEntity = new EmkFormaterailDetailEntity();
						orderMxEntity.setFormaterialId(emkFormaterial.getId());
						if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
							String[] colorArr = map.get("orderMxList["+i+"].color").split("-");
							orderMxEntity.setPm(colorArr[0]);
							orderMxEntity.setColorNum(colorArr[1]);
						}
						orderMxEntity.setSortDesc(String.valueOf(i+1));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
							orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].total")));
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
							orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
							orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
						}
						orderMxEntity.setSortDesc(String.valueOf(i+1));
						systemService.save(orderMxEntity);

						emkSizeTotalEntity = new EmkSizeTotalEntity();
						for(int z = 1 ; z < 17 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(map.get("orderMxList["+i+"].total"+(char)(z+64))) ? map.get("orderMxList["+i+"].total"+(char)(z+64)):"");
						}
						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "叫布表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkFormaterialEntity emkFormaterialEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "叫布提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if ((Utils.isEmpty(emkFormaterialEntity.getId()))) {
				for (String id : map.get("ids").split(",")) {
					EmkFormaterialEntity formaterialEntity = systemService.getEntity(EmkFormaterialEntity.class, id);
					if(!formaterialEntity.getCreateBy().equals(user.getUserName())){
						message = "只能由创建人提交叫布！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
					if (!formaterialEntity.getState().equals("0") && !formaterialEntity.getState().equals("3")) {
						message = "存在已提交的叫布，请重新选择在提交！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkFormaterialEntity.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").split(",")) {
					emkFormaterialEntity = systemService.get(EmkFormaterialEntity.class,id);
					String eNo = "";
					if(emkFormaterialEntity.getOrderNo().contains("叫布")){
						eNo = emkFormaterialEntity.getOrderNo().substring(0,emkFormaterialEntity.getOrderNo().indexOf("-叫布"));
					}else{
						eNo = emkFormaterialEntity.getOrderNo();
					}
					EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",eNo);
					EmkEnquiryEntity emkEnquiryEntity2 = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkFormaterialEntity.getOrderNo());

					EmkEnquiryEntity t = null;
					if(Utils.notEmpty(emkEnquiryEntity2)) {
						if (emkEnquiryEntity2.getState().equals("5")) {
							t = systemService.get(EmkEnquiryEntity.class,emkEnquiryEntity2.getId());
						}else{
							if(eNo.equals(emkFormaterialEntity.getOrderNo())){
								t = systemService.get(EmkEnquiryEntity.class,emkFormaterialEntity.getOrderId());
							}else{
								t = new EmkEnquiryEntity();
								MyBeanUtils.copyBeanNotNull2Bean(emkEnquiryEntity,t);
								t.setEnquiryNo(emkFormaterialEntity.getOrderNo());
								t.setState("3");
								t.setId(null);
								systemService.save(t);

								EmkApprovalEntity approvalEntity = new EmkApprovalEntity();
								EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
								ApprovalUtil.saveApproval(approvalEntity,0,emkFormaterialEntity.getOrderNo(),t.getId(),user);
								systemService.save(approvalEntity);
								ApprovalUtil.saveApprovalDetail(approvalDetailEntity,approvalEntity.getId(),"报单","billTask","提交",user);
								systemService.save(approvalDetailEntity);

								variables.put("optUser", t.getId());
								ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("femk", "emkEnquiryEntity", variables);
								List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								Task task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);
							}
						}
					}else{
						if(eNo.equals(emkFormaterialEntity.getOrderNo())){
							t = systemService.get(EmkEnquiryEntity.class,emkFormaterialEntity.getOrderId());
						}else{
							t = new EmkEnquiryEntity();
							MyBeanUtils.copyBeanNotNull2Bean(emkEnquiryEntity,t);
							t.setEnquiryNo(emkFormaterialEntity.getOrderNo());
							t.setState("3");
							t.setId(null);
							systemService.save(t);

							EmkApprovalEntity approvalEntity = new EmkApprovalEntity();
							EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
							ApprovalUtil.saveApproval(approvalEntity,0,emkFormaterialEntity.getOrderNo(),t.getId(),user);
							systemService.save(approvalEntity);
							ApprovalUtil.saveApprovalDetail(approvalDetailEntity,approvalEntity.getId(),"报单","billTask","提交",user);
							systemService.save(approvalDetailEntity);

							variables.put("optUser", t.getId());
							ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("femk", "emkEnquiryEntity", variables);
							List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							Task task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);
						}
					}
					//if(!emkEnquiryEntity.getState().equals("1") && !emkEnquiryEntity.getState().equals("5")){
					/*if(emkFormaterialEntity.getOrderNo().contains("叫布")){

					}else{
						t = systemService.get(EmkEnquiryEntity.class,emkFormaterialEntity.getOrderId());
					}*/
					emkFormaterialEntity.setOrderId(t.getId());

					variables.put("optUser", t.getId());
					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());

					List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();

					if (task.size() > 0) {
						TSUser makerUser = systemService.findUniqueByProperty(TSUser.class,"userName",t.getCreateBy());
						TSUser bpmUser = systemService.get(TSUser.class,b.getCommitId());

						//保存审批意见
						EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(),user,b,map.get("advice"));
						Task task1 = (Task)task.get(task.size() - 1);
						if (task1.getTaskDefinitionKey().equals("jbTask")) {
							taskService.complete(task1.getId(), variables);
							t.setState("3");
							b.setStatus(3);
							String title = "";
							if(emkFormaterialEntity.getState().equals("3")){
								title = "重新提交叫布";
							}else{
								title = "提交叫布";
							}
							saveApprvoalDetail(approvalDetail,"叫布","jbTask",0,title);

							String userKey = "";
							Map userM = null;
							if(user.getUserKey().equals("普通用户") || user.getUserKey().equals("管理员")){
								userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t left join t_s_depart d on d.id=t.departid where t.userkey='工厂' and d.departname=?",t.getGys());
								saveSmsAndEmailForGc(user,t.getGys(),"叫布","您有【"+user.getRealName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。");
								//saveSmsAndEmailForMany("工厂","叫布","您有【"+b.getCreateName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}else{
								userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='普通用户' or t.userkey='管理员'");
								saveSmsAndEmailForMany("普通用户","叫布","您有【"+b.getCreateName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
								saveSmsAndEmailForMany("管理员","叫布","您有【"+b.getCreateName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}
							if(Utils.notEmpty(userM)){
								b.setNextBpmSherId(userM.get("userNames").toString());
								b.setNextBpmSher(userM.get("realNames").toString());
							}
						}
						systemService.save(approvalDetail);
						emkFormaterialEntity.setState("1");
					}

				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "报单提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "findOrderList")
	@ResponseBody
	public AjaxJson findOrderList(EmkEnquiryEntity enquiryEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		List<EmkEnquiryEntity> emkEnquiryEntityList = null;
		if(user.getUserKey().equals("工厂")) {
			EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
			emkEnquiryEntityList = systemService.findHql("from EmkEnquiryEntity where state!=2 and gysCode=?", factoryArchivesEntity.getCompanyCode());
		}else{
			if(Utils.notEmpty(param.get("gysCode"))){
				emkEnquiryEntityList = systemService.findHql("from EmkEnquiryEntity where state!=2 and gysCode=?", param.get("gysCode"));
			}else{
				emkEnquiryEntityList = systemService.findHql("from EmkEnquiryEntity where state!=2", null);

			}
		}
		j.setObj(emkEnquiryEntityList);
		return j;
	}

	/**
	 * 叫布表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkFormaterialEntity emkFormaterial, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));

		if (StringUtil.isNotEmpty(emkFormaterial.getId())) {
			emkFormaterial = emkFormaterialService.getEntity(EmkFormaterialEntity.class, emkFormaterial.getId());
			req.setAttribute("emkFormaterialPage", emkFormaterial);
		}
		return new ModelAndView("com/emk/storage/formaterial/emkFormaterial-add");
	}
	/**
	 * 叫布表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkFormaterialEntity emkFormaterial, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkFormaterial.getId())) {
			emkFormaterial = emkFormaterialService.getEntity(EmkFormaterialEntity.class, emkFormaterial.getId());
			req.setAttribute("emkFormaterialPage", emkFormaterial);
		}
		return new ModelAndView("com/emk/storage/formaterial/emkFormaterial-update");
	}
	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkFormaterialEntity emkFormaterial, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkFormaterial.getId())) {
			emkFormaterial = emkFormaterialService.findUniqueByProperty(EmkFormaterialEntity.class,"orderId",emkFormaterial.getId());
			req.setAttribute("emkFormaterialPage", emkFormaterial);
		}
		return new ModelAndView("com/emk/storage/formaterial/emkFormaterial-update2");
	}
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		req.setAttribute("controller_name","emkFormaterialController");
		return new ModelAndView("com/emk/storage/formaterial/uploadView");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkFormaterialEntity emkFormaterial,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkFormaterialEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkFormaterial, request.getParameterMap());
		List<EmkFormaterialEntity> emkFormaterials = this.emkFormaterialService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"叫布表");
		modelMap.put(NormalExcelConstants.CLASS,EmkFormaterialEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("叫布表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkFormaterials);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkFormaterialEntity emkFormaterial,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"叫布表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkFormaterialEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("叫布表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(EmkFormaterialEntity emkFormaterialEntity,HttpServletRequest request,String fileName,String fileNameUrl, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		String message = "文件导入成功";
		File newfile = null;
		HSSFWorkbook wb = null;
		String cellValue = "";
		EmkFormaterailDetailEntity orderMxEntity = null;
		EmkSizeTotalEntity emkSizeTotalEntity = null;

		try {
			String savepath = request.getRealPath("/")+"imp/material/";
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
				for(int z = 0; z <= 25 ; z++){
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
					//emkEnquiry.setState("0");
					Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

					List<EmkFormaterialEntity> list = systemService.findHql("from EmkFormaterialEntity where state=0");
					if(list.size()>0){
						j.setMsg("存在未提交的叫布，无法录入新的数据");
						j.setSuccess(false);
						return j;
					}
					String eNo = "";
					if(emkFormaterialEntity.getOrderNo().contains("叫布")){
						eNo = emkFormaterialEntity.getOrderNo().substring(0,emkFormaterialEntity.getOrderNo().indexOf("-叫布"));
					}else{
						eNo = emkFormaterialEntity.getOrderNo();
					}
					EmkFormaterialEntity emkFormaterialEntity1 = systemService.findUniqueByProperty(EmkFormaterialEntity.class,"orderNo",eNo);
					orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no,2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+eNo+"-出货%'");
					EmkEnquiryEntity enquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkFormaterialEntity.getOrderNo());
					if(orderNum.get("orderNum").toString().equals("1") && Utils.isEmpty(emkFormaterialEntity1)){
						emkFormaterialEntity.setOrderId(enquiryEntity.getId());
					}else{
						emkFormaterialEntity.setOrderNo(eNo+"-叫布"+String.format("%02d", Integer.valueOf(orderNum.get("orderNum").toString())));
					}
					//emkEnquiry.setEnquiryNo(DateUtil.format(new Date(), "MMdd")+"-" + emkEnquiry.getGysCode()+"-" + emkEnquiry.getSampleNo());
					emkFormaterialService.save(emkFormaterialEntity);

					EmkSizeEntity emkSize = new EmkSizeEntity();
					Class c = Class.forName(EmkSizeEntity.class.getName());
					emkSize.setFormId(emkFormaterialEntity.getId());
					for(int z = 1 ; z < 23 ; z++){
						m0 = "setSize"+(char)(z+64);
						show = c.getMethod(m0,String.class);
						show.invoke(emkSize,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
					}
					systemService.save(emkSize);

				}else{
					if(Utils.notEmpty(itemValue.get(0))){
						orderMxEntity = new EmkFormaterailDetailEntity();
						orderMxEntity.setFormaterialId(emkFormaterialEntity.getId());
						orderMxEntity.setPm(itemValue.get(0));
						orderMxEntity.setColorNum(itemValue.get(1));
						Map po = systemService.findOneForJdbc("select * from emk_pm_color where pm=? and color_num=? ",orderMxEntity.getPm(),orderMxEntity.getColorNum());

						orderMxEntity.setSortDesc(String.valueOf(i+1));
						systemService.save(orderMxEntity);

						emkSizeTotalEntity = new EmkSizeTotalEntity();
						Class c = Class.forName(EmkSizeTotalEntity.class.getName());
						for(int z = 1 ; z < 17 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
						}
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
	@ApiOperation(value="叫布表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkFormaterialEntity>> list() {
		List<EmkFormaterialEntity> listEmkFormaterials=emkFormaterialService.getList(EmkFormaterialEntity.class);
		return Result.success(listEmkFormaterials);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取叫布表信息",notes="根据ID获取叫布表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkFormaterialEntity task = emkFormaterialService.get(EmkFormaterialEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取叫布表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建叫布表")
	public ResponseMessage<?> create(@ApiParam(name="叫布表对象")@RequestBody EmkFormaterialEntity emkFormaterial, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkFormaterialEntity>> failures = validator.validate(emkFormaterial);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkFormaterialService.save(emkFormaterial);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("叫布表信息保存失败");
		}
		return Result.success(emkFormaterial);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新叫布表",notes="更新叫布表")
	public ResponseMessage<?> update(@ApiParam(name="叫布表对象")@RequestBody EmkFormaterialEntity emkFormaterial) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkFormaterialEntity>> failures = validator.validate(emkFormaterial);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkFormaterialService.saveOrUpdate(emkFormaterial);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新叫布表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新叫布表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除叫布表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkFormaterialService.deleteEntityById(EmkFormaterialEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("叫布表删除失败");
		}

		return Result.success();
	}
}
