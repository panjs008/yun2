package com.emk.caiwu.yfcheck.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.bill.materialcontract.entity.EmkMaterialContractEntity;
import com.emk.caiwu.yfcheck.entity.EmkFinanceYfCheckEntity;
import com.emk.caiwu.yfcheck.service.EmkFinanceYfCheckServiceI;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.produce.testcost.entity.EmkTestCostEntity;
import com.emk.util.ApprovalUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.activiti.engine.task.Task;
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
 * @Description: 供应商往来账
 * @author onlineGenerator
 * @date 2018-11-03 21:55:27
 * @version V1.0   
 *
 */
@Api(value="EmkFinanceYfCheck",description="供应商往来账",tags="emkFinanceYfCheckController")
@Controller
@RequestMapping("/emkFinanceYfCheckController")
public class EmkFinanceYfCheckController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkFinanceYfCheckController.class);

	@Autowired
	private EmkFinanceYfCheckServiceI emkFinanceYfCheckService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 供应商往来账列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/caiwu/yfcheck/emkFinanceYfCheckList");
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
	public void datagrid(EmkFinanceYfCheckEntity emkFinanceYfCheck,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkFinanceYfCheckEntity.class, dataGrid);
		//查询条件组装器
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if(!user.getUserName().equals("admin")){
			cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkFinanceYfCheck, request.getParameterMap());
		try{
			//自定义追加查询条件

		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkFinanceYfCheckService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除供应商往来账
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkFinanceYfCheckEntity emkFinanceYfCheck, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkFinanceYfCheck = systemService.getEntity(EmkFinanceYfCheckEntity.class, emkFinanceYfCheck.getId());
		message = "供应商往来账删除成功";
		try{
			emkFinanceYfCheckService.delete(emkFinanceYfCheck);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "供应商往来账删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除供应商往来账
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "供应商往来账删除成功";
		try{
			for(String id:ids.split(",")){
				EmkFinanceYfCheckEntity emkFinanceYfCheck = systemService.getEntity(EmkFinanceYfCheckEntity.class, 
				id
				);
				emkFinanceYfCheckService.delete(emkFinanceYfCheck);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "供应商往来账删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加供应商往来账
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkFinanceYfCheckEntity emkFinanceYfCheck, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "供应商往来账添加成功";
		try{
			emkFinanceYfCheck.setState("0");
			emkFinanceYfCheckService.save(emkFinanceYfCheck);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "供应商往来账添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新供应商往来账
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkFinanceYfCheckEntity emkFinanceYfCheck, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "供应商往来账更新成功";
		EmkFinanceYfCheckEntity t = emkFinanceYfCheckService.get(EmkFinanceYfCheckEntity.class, emkFinanceYfCheck.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(emkFinanceYfCheck, t);
			emkFinanceYfCheckService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "供应商往来账更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 供应商往来账新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkFinanceYfCheckEntity emkFinanceYfCheck, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkFinanceYfCheck.getId())) {
			emkFinanceYfCheck = emkFinanceYfCheckService.getEntity(EmkFinanceYfCheckEntity.class, emkFinanceYfCheck.getId());
			req.setAttribute("emkFinanceYfCheckPage", emkFinanceYfCheck);
		}
		return new ModelAndView("com/emk/caiwu/yfcheck/emkFinanceYfCheck-add");
	}
	/**
	 * 供应商往来账编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkFinanceYfCheckEntity emkFinanceYfCheck, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkFinanceYfCheck.getId())) {
			emkFinanceYfCheck = emkFinanceYfCheckService.getEntity(EmkFinanceYfCheckEntity.class, emkFinanceYfCheck.getId());
			req.setAttribute("emkFinanceYfCheckPage", emkFinanceYfCheck);
		}
		return new ModelAndView("com/emk/caiwu/yfcheck/emkFinanceYfCheck-update");
	}
	@RequestMapping(params = "goUpdate3")
	public ModelAndView goUpdate3(EmkFinanceYfCheckEntity emkFinanceYfCheck, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkFinanceYfCheck.getId())) {
			Map p = ParameterUtil.getParamMaps(req.getParameterMap());
			if(p.get("type").equals("testCost")){
				EmkTestCostEntity a = systemService.get(EmkTestCostEntity.class,emkFinanceYfCheck.getId());
				emkFinanceYfCheck = emkFinanceYfCheckService.findUniqueByProperty(EmkFinanceYfCheckEntity.class,"testNo",a.getCostNo());
			}
			if(p.get("type").equals("materialContract")){
				EmkMaterialContractEntity a = systemService.get(EmkMaterialContractEntity.class,emkFinanceYfCheck.getId());
				emkFinanceYfCheck = emkFinanceYfCheckService.findUniqueByProperty(EmkFinanceYfCheckEntity.class,"ylmldyfyfkNo",a.getPayNo());
			}
			req.setAttribute("emkFinanceYfCheckPage", emkFinanceYfCheck);
		}
		return new ModelAndView("com/emk/caiwu/yfcheck/emkFinanceYfCheck-update3");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkFinanceYfCheckController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkFinanceYfCheckEntity emkFinanceYfCheck,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkFinanceYfCheckEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkFinanceYfCheck, request.getParameterMap());
		List<EmkFinanceYfCheckEntity> emkFinanceYfChecks = this.emkFinanceYfCheckService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"供应商往来账");
		modelMap.put(NormalExcelConstants.CLASS,EmkFinanceYfCheckEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("供应商往来账列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkFinanceYfChecks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkFinanceYfCheckEntity emkFinanceYfCheck,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"供应商往来账");
    	modelMap.put(NormalExcelConstants.CLASS,EmkFinanceYfCheckEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("供应商往来账列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkFinanceYfCheckEntity> listEmkFinanceYfCheckEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkFinanceYfCheckEntity.class,params);
				for (EmkFinanceYfCheckEntity emkFinanceYfCheck : listEmkFinanceYfCheckEntitys) {
					emkFinanceYfCheckService.save(emkFinanceYfCheck);
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
	@ApiOperation(value="供应商往来账列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkFinanceYfCheckEntity>> list() {
		List<EmkFinanceYfCheckEntity> listEmkFinanceYfChecks=emkFinanceYfCheckService.getList(EmkFinanceYfCheckEntity.class);
		return Result.success(listEmkFinanceYfChecks);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取供应商往来账信息",notes="根据ID获取供应商往来账信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkFinanceYfCheckEntity task = emkFinanceYfCheckService.get(EmkFinanceYfCheckEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取供应商往来账信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建供应商往来账")
	public ResponseMessage<?> create(@ApiParam(name="供应商往来账对象")@RequestBody EmkFinanceYfCheckEntity emkFinanceYfCheck, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkFinanceYfCheckEntity>> failures = validator.validate(emkFinanceYfCheck);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkFinanceYfCheckService.save(emkFinanceYfCheck);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("供应商往来账信息保存失败");
		}
		return Result.success(emkFinanceYfCheck);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新供应商往来账",notes="更新供应商往来账")
	public ResponseMessage<?> update(@ApiParam(name="供应商往来账对象")@RequestBody EmkFinanceYfCheckEntity emkFinanceYfCheck) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkFinanceYfCheckEntity>> failures = validator.validate(emkFinanceYfCheck);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkFinanceYfCheckService.saveOrUpdate(emkFinanceYfCheck);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新供应商往来账信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新供应商往来账信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除供应商往来账")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkFinanceYfCheckService.deleteEntityById(EmkFinanceYfCheckEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("供应商往来账删除失败");
		}

		return Result.success();
	}
}
