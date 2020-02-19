package com.emk.storage.formaterialother.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.storage.formaterial.entity.EmkFormaterailDetailEntity;
import com.emk.storage.formaterialother.entity.EmkFormaterailOtherDetailEntity;
import com.emk.storage.formaterialother.entity.EmkFormaterialOtherEntity;
import com.emk.storage.formaterialother.service.EmkFormaterialOtherServiceI;

import java.lang.reflect.Method;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.storage.pay.entity.EmkPayEntity;
import com.emk.util.ApprovalUtil;
import com.emk.util.DateUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
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
 * @Description: 其他物料发放表
 * @author onlineGenerator
 * @date 2019-08-28 20:46:23
 * @version V1.0   
 *
 */
@Api(value="EmkFormaterialOther",description="其他物料发放表",tags="emkFormaterialOtherController")
@Controller
@RequestMapping("/emkFormaterialOtherController")
public class EmkFormaterialOtherController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkFormaterialOtherController.class);

	@Autowired
	private EmkFormaterialOtherServiceI emkFormaterialOtherService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 其他物料发放表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/storage/formaterialother/emkFormaterialOtherList");
	}


	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("formaterialOtherId"))) {
			List<EmkFormaterailOtherDetailEntity> emkFormaterailOtherDetailEntityList = systemService.findHql("from EmkFormaterailOtherDetailEntity where formaterailOtherId = ?",map.get("formaterialOtherId"));
			request.setAttribute("emkFormaterailOtherDetailEntityList", emkFormaterailOtherDetailEntityList);
		}
		return new ModelAndView("com/emk/storage/formaterialother/orderMxList");
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
	public void datagrid(EmkFormaterialOtherEntity emkFormaterialOther,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkFormaterialOtherEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkFormaterialOther, request.getParameterMap());
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
		this.emkFormaterialOtherService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除其他物料发放表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkFormaterialOtherEntity emkFormaterialOther, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkFormaterialOther = systemService.getEntity(EmkFormaterialOtherEntity.class, emkFormaterialOther.getId());
		message = "其他物料发放表删除成功";
		try{
			emkFormaterialOtherService.delete(emkFormaterialOther);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "其他物料发放表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除其他物料发放表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "其他物料发放表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkFormaterialOtherEntity emkFormaterialOther = systemService.getEntity(EmkFormaterialOtherEntity.class, 
				id
				);
				if (!emkFormaterialOther.getState().equals("0")) {
					message = "存在已提交的其他物料发放，请重新选择！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
				systemService.executeSql("delete from emk_formaterail_other_detail where formaterail_other_id=?",id);
				emkFormaterialOtherService.delete(emkFormaterialOther);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "其他物料发放表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加其他物料发放表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkFormaterialOtherEntity emkFormaterialOther, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "其他物料发放表添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			EmkFormaterialOtherEntity t = systemService.findUniqueByProperty(EmkFormaterialOtherEntity.class,"orderNo",emkFormaterialOther.getOrderNo());
			if(Utils.notEmpty(t)){
				j.setSuccess(false);
				j.setMsg("已存在"+emkFormaterialOther.getOrderNo()+"的其他物料发放信息，请重新录入");
				return j;
			}else{
				EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkFormaterialOther.getOrderNo());
				emkFormaterialOther.setState("0");
				emkFormaterialOther.setOrderId(emkEnquiryEntity.getId());
				emkFormaterialOtherService.save(emkFormaterialOther);
				String dataRows = (String) map.get("dataRowsVal");
				if (Utils.notEmpty(dataRows)) {
					int rows = Integer.parseInt(dataRows);
					EmkFormaterailOtherDetailEntity orderMxEntity = null;
					for (int i = 0; i < rows; i++) {
						if (Utils.notEmpty(map.get("orderMxList["+i+"].wllx"))){
							orderMxEntity = new EmkFormaterailOtherDetailEntity();
							orderMxEntity.setFormaterailOtherId(emkFormaterialOther.getId());
							orderMxEntity.setSortDesc(String.valueOf(i+1));
							orderMxEntity.setWllx(map.get("orderMxList["+i+"].wllx"));
							if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
								orderMxEntity.setTotal(Double.valueOf(map.get("orderMxList["+i+"].total")));
							}
							if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
								orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
							}
							if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
								orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
							}
							systemService.save(orderMxEntity);
						}
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "其他物料发放表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新其他物料发放表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkFormaterialOtherEntity emkFormaterialOther, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "其他物料发放表更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

		EmkFormaterialOtherEntity t = emkFormaterialOtherService.get(EmkFormaterialOtherEntity.class, emkFormaterialOther.getId());
		if (!t.getState().equals("0") && !t.getState().equals("3")) {
			message = "存在已提交的其他物料发放，请重新选择！";
			j.setMsg(message);
			j.setSuccess(false);
			return j;
		}
		try {
			MyBeanUtils.copyBeanNotNull2Bean(emkFormaterialOther, t);
			emkFormaterialOtherService.saveOrUpdate(t);
			String dataRows = (String) map.get("dataRowsVal");
			if (Utils.notEmpty(dataRows)) {
				systemService.executeSql("delete from emk_formaterail_other_detail where formaterail_other_id=?",t.getId());
				int rows = Integer.parseInt(dataRows);
				EmkFormaterailOtherDetailEntity orderMxEntity = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
						orderMxEntity = new EmkFormaterailOtherDetailEntity();
						orderMxEntity.setFormaterailOtherId(emkFormaterialOther.getId());
						orderMxEntity.setWllx(map.get("orderMxList["+i+"].wllx"));
						orderMxEntity.setSortDesc(String.valueOf(i+1));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
							orderMxEntity.setTotal(Double.valueOf(map.get("orderMxList["+i+"].total")));
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
							orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
							orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
						}
						orderMxEntity.setSortDesc(String.valueOf(i+1));
						systemService.save(orderMxEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "其他物料发放表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkFormaterialOtherEntity emkFormaterialOther, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "其他物料发放提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if ((Utils.isEmpty(emkFormaterialOther.getId()))) {
				for (String id : map.get("ids").split(",")) {
					EmkFormaterialOtherEntity formaterialEntity = systemService.getEntity(EmkFormaterialOtherEntity.class, id);
					if(!formaterialEntity.getCreateBy().equals(user.getUserName())){
						message = "只能由创建人提交其他物料发放！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
					if (!formaterialEntity.getState().equals("0") && !formaterialEntity.getState().equals("3")) {
						message = "存在已提交的其他物料发放，请重新选择在提交！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkFormaterialOther.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").split(",")) {
					emkFormaterialOther = systemService.get(EmkFormaterialOtherEntity.class,id);
					EmkEnquiryEntity t = systemService.get(EmkEnquiryEntity.class,emkFormaterialOther.getOrderId());
					variables.put("optUser", t.getId());
					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());

					List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
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
						if (task1.getTaskDefinitionKey().equals("otherTask")) {
							taskService.complete(task1.getId(), variables);
							t.setState("6");
							b.setStatus(6);
							String title = "";
							if(emkFormaterialOther.getState().equals("3")){
								title = "重新提交其他物料发放";
							}else{
								title = "提交其他物料发放";
							}
							saveApprvoalDetail(approvalDetail,"其他物料发放","otherTask",0,title);

							String userKey = "";
							Map userM = null;

							userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t left join t_s_depart d on d.id=t.departid where t.userkey='工厂' and d.departname=?",t.getGys());
							saveSmsAndEmailForGc(user,t.getGys(),"其他物料发放","您有【"+user.getRealName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。");

							/*userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='工厂'");
							saveSmsAndEmailForMany("工厂","其他物料发放","您有【"+b.getCreateName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());*/
							if(Utils.notEmpty(userM)){
								b.setNextBpmSherId(userM.get("userNames").toString());
								b.setNextBpmSher(userM.get("realNames").toString());
							}
						}else{
							message = "工厂还未核实，不能提交其他物料发放！";
							j.setMsg(message);
							j.setSuccess(false);
							return j;
						}
						systemService.save(approvalDetail);
						emkFormaterialOther.setState("1");
					}
					systemService.saveOrUpdate(t);
					systemService.saveOrUpdate(b);
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

	/**
	 * 其他物料发放表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkFormaterialOtherEntity emkFormaterialOther, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));

		if (StringUtil.isNotEmpty(emkFormaterialOther.getId())) {
			emkFormaterialOther = emkFormaterialOtherService.getEntity(EmkFormaterialOtherEntity.class, emkFormaterialOther.getId());
			req.setAttribute("emkFormaterialOtherPage", emkFormaterialOther);
		}
		return new ModelAndView("com/emk/storage/formaterialother/emkFormaterialOther-add");
	}
	/**
	 * 其他物料发放表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkFormaterialOtherEntity emkFormaterialOther, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkFormaterialOther.getId())) {
			emkFormaterialOther = emkFormaterialOtherService.getEntity(EmkFormaterialOtherEntity.class, emkFormaterialOther.getId());
			req.setAttribute("emkFormaterialOtherPage", emkFormaterialOther);
		}
		return new ModelAndView("com/emk/storage/formaterialother/emkFormaterialOther-update");
	}

	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkFormaterialOtherEntity emkFormaterialOther, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkFormaterialOther.getId())) {
			emkFormaterialOther = emkFormaterialOtherService.findUniqueByProperty(EmkFormaterialOtherEntity.class,"orderId",emkFormaterialOther.getId());
			req.setAttribute("emkFormaterialOtherPage", emkFormaterialOther);
		}
		return new ModelAndView("com/emk/storage/formaterialother/emkFormaterialOther-update2");

	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkFormaterialOtherController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkFormaterialOtherEntity emkFormaterialOther,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkFormaterialOtherEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkFormaterialOther, request.getParameterMap());
		List<EmkFormaterialOtherEntity> emkFormaterialOthers = this.emkFormaterialOtherService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"其他物料发放表");
		modelMap.put(NormalExcelConstants.CLASS,EmkFormaterialOtherEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("其他物料发放表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkFormaterialOthers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkFormaterialOtherEntity emkFormaterialOther,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"其他物料发放表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkFormaterialOtherEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("其他物料发放表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkFormaterialOtherEntity> listEmkFormaterialOtherEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkFormaterialOtherEntity.class,params);
				for (EmkFormaterialOtherEntity emkFormaterialOther : listEmkFormaterialOtherEntitys) {
					emkFormaterialOtherService.save(emkFormaterialOther);
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
	@ApiOperation(value="其他物料发放表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkFormaterialOtherEntity>> list() {
		List<EmkFormaterialOtherEntity> listEmkFormaterialOthers=emkFormaterialOtherService.getList(EmkFormaterialOtherEntity.class);
		return Result.success(listEmkFormaterialOthers);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取其他物料发放表信息",notes="根据ID获取其他物料发放表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkFormaterialOtherEntity task = emkFormaterialOtherService.get(EmkFormaterialOtherEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取其他物料发放表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建其他物料发放表")
	public ResponseMessage<?> create(@ApiParam(name="其他物料发放表对象")@RequestBody EmkFormaterialOtherEntity emkFormaterialOther, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkFormaterialOtherEntity>> failures = validator.validate(emkFormaterialOther);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkFormaterialOtherService.save(emkFormaterialOther);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("其他物料发放表信息保存失败");
		}
		return Result.success(emkFormaterialOther);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新其他物料发放表",notes="更新其他物料发放表")
	public ResponseMessage<?> update(@ApiParam(name="其他物料发放表对象")@RequestBody EmkFormaterialOtherEntity emkFormaterialOther) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkFormaterialOtherEntity>> failures = validator.validate(emkFormaterialOther);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkFormaterialOtherService.saveOrUpdate(emkFormaterialOther);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新其他物料发放表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新其他物料发放表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除其他物料发放表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkFormaterialOtherService.deleteEntityById(EmkFormaterialOtherEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("其他物料发放表删除失败");
		}

		return Result.success();
	}
}
