package com.emk.email.smsmodel.controller;
import com.emk.email.smsmodel.entity.ESmsModelEntity;
import com.emk.email.smsmodel.service.ESmsModelServiceI;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.jeecgframework.core.util.ExceptionUtil;

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
import java.util.Set;
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
 * @Description: 短信模板
 * @author onlineGenerator
 * @date 2019-11-01 11:17:40
 * @version V1.0   
 *
 */
@Api(value="ESmsModel",description="短信模板",tags="eSmsModelController")
@Controller
@RequestMapping("/eSmsModelController")
public class ESmsModelController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ESmsModelController.class);

	@Autowired
	private ESmsModelServiceI eSmsModelService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 短信模板列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/email/smsmodel/eSmsModelList");
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
	public void datagrid(ESmsModelEntity eSmsModel,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ESmsModelEntity.class, dataGrid);
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, eSmsModel, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.eSmsModelService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除短信模板
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ESmsModelEntity eSmsModel, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		eSmsModel = systemService.getEntity(ESmsModelEntity.class, eSmsModel.getId());
		message = "短信模板删除成功";
		try{
			eSmsModelService.delete(eSmsModel);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信模板删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除短信模板
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "短信模板删除成功";
		try{
			for(String id:ids.split(",")){
				ESmsModelEntity eSmsModel = systemService.getEntity(ESmsModelEntity.class, 
				id
				);
				eSmsModelService.delete(eSmsModel);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "短信模板删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加短信模板
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ESmsModelEntity eSmsModel, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "短信模板添加成功";
		try{
			eSmsModel.setStatus("0");
			if(eSmsModel.getContent().length()>50){
				message = "短信内容超出50个字符";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}
			eSmsModelService.save(eSmsModel);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信模板添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新短信模板
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ESmsModelEntity eSmsModel, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "短信模板更新成功";
		if(eSmsModel.getContent().length()>50){
			message = "短信内容超出50个字符";
			j.setMsg(message);
			j.setSuccess(false);
			return j;
		}
		ESmsModelEntity t = eSmsModelService.get(ESmsModelEntity.class, eSmsModel.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(eSmsModel, t);
			eSmsModelService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "短信模板更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 提交短信模板
	 *
	 * @return
	 */
	@RequestMapping(params = "doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "短信模板提交成功";
		try{
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			systemService.executeSql("update e_sms_model set status=0 where create_name=?",user.getRealName());
			ESmsModelEntity t = eSmsModelService.get(ESmsModelEntity.class, ids);
			t.setStatus("1");
			systemService.saveOrUpdate(t);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信模板提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 审核短信模板
	 *
	 * @return
	 */
	@RequestMapping(params = "doApproval")
	@ResponseBody
	public AjaxJson doApproval(String id,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "短信模板审核成功";
		try{
			ESmsModelEntity t = eSmsModelService.get(ESmsModelEntity.class, id);
			t.setStatus("2");
			systemService.saveOrUpdate(t);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信模板审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 短信模板新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ESmsModelEntity eSmsModel, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(eSmsModel.getId())) {
			eSmsModel = eSmsModelService.getEntity(ESmsModelEntity.class, eSmsModel.getId());
			req.setAttribute("eSmsModelPage", eSmsModel);
		}
		return new ModelAndView("com/emk/email/smsmodel/eSmsModel-add");
	}
	/**
	 * 短信模板编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ESmsModelEntity eSmsModel, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(eSmsModel.getId())) {
			eSmsModel = eSmsModelService.getEntity(ESmsModelEntity.class, eSmsModel.getId());
			req.setAttribute("eSmsModelPage", eSmsModel);
		}
		return new ModelAndView("com/emk/email/smsmodel/eSmsModel-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","eSmsModelController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ESmsModelEntity eSmsModel,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ESmsModelEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, eSmsModel, request.getParameterMap());
		List<ESmsModelEntity> eSmsModels = this.eSmsModelService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"短信模板");
		modelMap.put(NormalExcelConstants.CLASS,ESmsModelEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("短信模板列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,eSmsModels);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ESmsModelEntity eSmsModel,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"短信模板");
    	modelMap.put(NormalExcelConstants.CLASS,ESmsModelEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("短信模板列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<ESmsModelEntity> listESmsModelEntitys = ExcelImportUtil.importExcel(file.getInputStream(),ESmsModelEntity.class,params);
				for (ESmsModelEntity eSmsModel : listESmsModelEntitys) {
					eSmsModelService.save(eSmsModel);
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
	@ApiOperation(value="短信模板列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<ESmsModelEntity>> list() {
		List<ESmsModelEntity> listESmsModels=eSmsModelService.getList(ESmsModelEntity.class);
		return Result.success(listESmsModels);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取短信模板信息",notes="根据ID获取短信模板信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		ESmsModelEntity task = eSmsModelService.get(ESmsModelEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取短信模板信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建短信模板")
	public ResponseMessage<?> create(@ApiParam(name="短信模板对象")@RequestBody ESmsModelEntity eSmsModel, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<ESmsModelEntity>> failures = validator.validate(eSmsModel);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			eSmsModelService.save(eSmsModel);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("短信模板信息保存失败");
		}
		return Result.success(eSmsModel);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新短信模板",notes="更新短信模板")
	public ResponseMessage<?> update(@ApiParam(name="短信模板对象")@RequestBody ESmsModelEntity eSmsModel) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<ESmsModelEntity>> failures = validator.validate(eSmsModel);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			eSmsModelService.saveOrUpdate(eSmsModel);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新短信模板信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新短信模板信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除短信模板")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			eSmsModelService.deleteEntityById(ESmsModelEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("短信模板删除失败");
		}

		return Result.success();
	}
}
