package com.emk.storage.file.controller;
import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.storage.file.entity.EmkFileEntity;
import com.emk.storage.file.service.EmkFileServiceI;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.util.DateUtil;
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
 * @Description: 文件管理
 * @author onlineGenerator
 * @date 2019-09-30 09:51:30
 * @version V1.0   
 *
 */
@Api(value="EmkFile",description="文件管理",tags="emkFileController")
@Controller
@RequestMapping("/emkFileController")
public class EmkFileController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkFileController.class);

	@Autowired
	private EmkFileServiceI emkFileService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 文件管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/storage/file/emkFileList");
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
	public void datagrid(EmkFileEntity emkFile,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkFileEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkFile, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = ResourceUtil.getSessionUser();
			if(user.getUserKey().equals("工厂")){
				EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
				cq.eq("gysCode",factoryArchivesEntity.getCompanyCode());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkFileService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除文件管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkFileEntity emkFile, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkFile = systemService.getEntity(EmkFileEntity.class, emkFile.getId());
		message = "文件管理删除成功";
		try{
			emkFileService.delete(emkFile);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文件管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除文件管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "文件管理删除成功";
		try{
			for(String id:ids.split(",")){
				EmkFileEntity emkFile = systemService.getEntity(EmkFileEntity.class, 
				id
				);
				emkFileService.delete(emkFile);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "文件管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加文件管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkFileEntity emkFile, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "文件管理添加成功";
		try{
			emkFileService.save(emkFile);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文件管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新文件管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkFileEntity emkFile, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "文件管理更新成功";
		EmkFileEntity t = emkFileService.get(EmkFileEntity.class, emkFile.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(emkFile, t);
			emkFileService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "文件管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 文件管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkFileEntity emkFile, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
		req.setAttribute("factoryArchivesEntity", factoryArchivesEntity);

		if (StringUtil.isNotEmpty(emkFile.getId())) {
			emkFile = emkFileService.getEntity(EmkFileEntity.class, emkFile.getId());
			req.setAttribute("emkFilePage", emkFile);
		}
		return new ModelAndView("com/emk/storage/file/emkFile-add");
	}
	/**
	 * 文件管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkFileEntity emkFile, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkFile.getId())) {
			emkFile = emkFileService.getEntity(EmkFileEntity.class, emkFile.getId());
			req.setAttribute("emkFilePage", emkFile);
		}
		return new ModelAndView("com/emk/storage/file/emkFile-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkFileController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkFileEntity emkFile,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkFileEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkFile, request.getParameterMap());
		List<EmkFileEntity> emkFiles = this.emkFileService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"文件管理");
		modelMap.put(NormalExcelConstants.CLASS,EmkFileEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("文件管理列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkFiles);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkFileEntity emkFile,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"文件管理");
    	modelMap.put(NormalExcelConstants.CLASS,EmkFileEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("文件管理列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkFileEntity> listEmkFileEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkFileEntity.class,params);
				for (EmkFileEntity emkFile : listEmkFileEntitys) {
					emkFileService.save(emkFile);
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
	@ApiOperation(value="文件管理列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkFileEntity>> list() {
		List<EmkFileEntity> listEmkFiles=emkFileService.getList(EmkFileEntity.class);
		return Result.success(listEmkFiles);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取文件管理信息",notes="根据ID获取文件管理信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkFileEntity task = emkFileService.get(EmkFileEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取文件管理信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建文件管理")
	public ResponseMessage<?> create(@ApiParam(name="文件管理对象")@RequestBody EmkFileEntity emkFile, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkFileEntity>> failures = validator.validate(emkFile);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkFileService.save(emkFile);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("文件管理信息保存失败");
		}
		return Result.success(emkFile);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新文件管理",notes="更新文件管理")
	public ResponseMessage<?> update(@ApiParam(name="文件管理对象")@RequestBody EmkFileEntity emkFile) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkFileEntity>> failures = validator.validate(emkFile);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkFileService.saveOrUpdate(emkFile);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新文件管理信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新文件管理信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除文件管理")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkFileService.deleteEntityById(EmkFileEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("文件管理删除失败");
		}

		return Result.success();
	}
}
