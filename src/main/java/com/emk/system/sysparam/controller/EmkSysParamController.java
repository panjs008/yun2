package com.emk.system.sysparam.controller;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import com.emk.system.sysparam.service.EmkSysParamServiceI;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
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
 * @Description: 参数表
 * @author onlineGenerator
 * @date 2019-12-08 13:14:31
 * @version V1.0   
 *
 */
@Api(value="EmkSysParam",description="参数表",tags="emkSysParamController")
@Controller
@RequestMapping("/emkSysParamController")
public class EmkSysParamController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkSysParamController.class);

	@Autowired
	private EmkSysParamServiceI emkSysParamService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 参数表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/system/sysparam/emkSysParamList");
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
	public void datagrid(EmkSysParamEntity emkSysParam,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkSysParamEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkSysParam, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			cq.eq("departId",user.getCurrentDepart().getId());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkSysParamService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除参数表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkSysParamEntity emkSysParam, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkSysParam = systemService.getEntity(EmkSysParamEntity.class, emkSysParam.getId());
		message = "参数表删除成功";
		try{
			emkSysParamService.delete(emkSysParam);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "参数表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除参数表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "参数表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkSysParamEntity emkSysParam = systemService.getEntity(EmkSysParamEntity.class, 
				id
				);
				emkSysParamService.delete(emkSysParam);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "参数表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加参数表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkSysParamEntity emkSysParam, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "参数表添加成功";
		try{
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",orgCode);
			emkSysParam.setDepartId(tsDepart.getId());
			emkSysParamService.save(emkSysParam);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "参数表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新参数表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkSysParamEntity emkSysParam, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "参数表更新成功";
		EmkSysParamEntity t = emkSysParamService.get(EmkSysParamEntity.class, emkSysParam.getId());
		try {
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",orgCode);
			if("公司名称".equals(emkSysParam.getParamName())){
				tsDepart.setDepartname(emkSysParam.getParamValue());
			}
			if("固定电话".equals(emkSysParam.getParamName())){
				tsDepart.setMobile(emkSysParam.getParamValue());
			}
			if("传真号码".equals(emkSysParam.getParamName())){
				tsDepart.setFax(emkSysParam.getParamValue());
			}
			MyBeanUtils.copyBeanNotNull2Bean(emkSysParam, t);
			if(emkSysParam.getParamName().contains("公司地址")){
				TSCategoryEntity categoryEntity = systemService.findUniqueByProperty(TSCategoryEntity.class,"code",emkSysParam.getShengFen());
				t.setParamValue(categoryEntity.getName());

				EmkSysParamEntity t2 = systemService.singleResult("from EmkSysParamEntity where paramName='公司地址（市）' and departId='"+tsDepart.getId()+"'");
				categoryEntity = systemService.findUniqueByProperty(TSCategoryEntity.class,"code",emkSysParam.getChengShi());
				t2.setParamValue(categoryEntity.getName());
				t2.setShengFen(t.getShengFen());
				t2.setChengShi(t.getChengShi());
				t2.setPianQu(t.getPianQu());

				//EmkSysParamEntity t3 = systemService.findUniqueByProperty(EmkSysParamEntity.class,"paramName","公司地址（区、县）");
				EmkSysParamEntity t3 = systemService.singleResult("from EmkSysParamEntity where paramName='公司地址（区、县）' and departId='"+tsDepart.getId()+"'");

				categoryEntity = systemService.findUniqueByProperty(TSCategoryEntity.class,"code",emkSysParam.getPianQu());
				t3.setParamValue(categoryEntity.getName());
				t3.setShengFen(t.getShengFen());
				t3.setChengShi(t.getChengShi());
				t3.setPianQu(t.getPianQu());

				tsDepart.setAddress(t.getParamValue()+t2.getParamValue()+t3.getParamValue());
			}

			emkSysParamService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "参数表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 参数表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkSysParamEntity emkSysParam, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkSysParam.getId())) {
			emkSysParam = emkSysParamService.getEntity(EmkSysParamEntity.class, emkSysParam.getId());
			req.setAttribute("emkSysParamPage", emkSysParam);
		}
		return new ModelAndView("com/emk/system/sysparam/emkSysParam-add");
	}
	/**
	 * 参数表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkSysParamEntity emkSysParam, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkSysParam.getId())) {
			emkSysParam = emkSysParamService.getEntity(EmkSysParamEntity.class, emkSysParam.getId());
			req.setAttribute("emkSysParamPage", emkSysParam);
		}
		return new ModelAndView("com/emk/system/sysparam/emkSysParam-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkSysParamController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkSysParamEntity emkSysParam,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkSysParamEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkSysParam, request.getParameterMap());
		List<EmkSysParamEntity> emkSysParams = this.emkSysParamService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"参数表");
		modelMap.put(NormalExcelConstants.CLASS,EmkSysParamEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("参数表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkSysParams);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkSysParamEntity emkSysParam,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"参数表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkSysParamEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("参数表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkSysParamEntity> listEmkSysParamEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkSysParamEntity.class,params);
				for (EmkSysParamEntity emkSysParam : listEmkSysParamEntitys) {
					emkSysParamService.save(emkSysParam);
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
	@ApiOperation(value="参数表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkSysParamEntity>> list() {
		List<EmkSysParamEntity> listEmkSysParams=emkSysParamService.getList(EmkSysParamEntity.class);
		return Result.success(listEmkSysParams);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取参数表信息",notes="根据ID获取参数表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkSysParamEntity task = emkSysParamService.get(EmkSysParamEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取参数表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建参数表")
	public ResponseMessage<?> create(@ApiParam(name="参数表对象")@RequestBody EmkSysParamEntity emkSysParam, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkSysParamEntity>> failures = validator.validate(emkSysParam);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkSysParamService.save(emkSysParam);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("参数表信息保存失败");
		}
		return Result.success(emkSysParam);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新参数表",notes="更新参数表")
	public ResponseMessage<?> update(@ApiParam(name="参数表对象")@RequestBody EmkSysParamEntity emkSysParam) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkSysParamEntity>> failures = validator.validate(emkSysParam);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkSysParamService.saveOrUpdate(emkSysParam);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新参数表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新参数表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除参数表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkSysParamService.deleteEntityById(EmkSysParamEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("参数表删除失败");
		}

		return Result.success();
	}
}
