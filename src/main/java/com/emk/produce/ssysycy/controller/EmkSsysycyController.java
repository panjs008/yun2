package com.emk.produce.ssysycy.controller;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.produce.ssysycy.entity.EmkSsysycyDetailEntity;
import com.emk.produce.ssysycy.entity.EmkSsysycyEntity;
import com.emk.produce.ssysycy.service.EmkSsysycyServiceI;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.storage.sampledetail.entity.EmkSampleDetailEntity;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
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
 * @Description: 试身样色样船样进度
 * @author onlineGenerator
 * @date 2018-11-03 13:07:21
 * @version V1.0   
 *
 */
@Api(value="EmkSsysycy",description="试身样色样船样进度",tags="emkSsysycyController")
@Controller
@RequestMapping("/emkSsysycyController")
public class EmkSsysycyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkSsysycyController.class);

	@Autowired
	private EmkSsysycyServiceI emkSsysycyService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 试身样色样船样进度列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/produce/ssysycy/emkSsysycyList");
	}

	@RequestMapping(params = "detailMxList")
	public ModelAndView detailMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		/*List<Map<String, Object>> list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
		request.setAttribute("colorList", list);*/
		List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(remark,',',typecode) typecode,typecode code,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
		String color = JSONHelper.collection2json(list);
		request.setAttribute("color", "'"+color+ "'");
		if (Utils.notEmpty(map.get("ssyId"))) {
			List<EmkSsysycyDetailEntity> emkSsysycyDetailEntities = systemService.findHql("from EmkSsysycyDetailEntity where ssyId=?", map.get("ssyId"));
			request.setAttribute("emkSsysycyDetailEntities", emkSsysycyDetailEntities);
		}
		return new ModelAndView("com/emk/produce/ssysycy/detailMxList");
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
	public void datagrid(EmkSsysycyEntity emkSsysycy,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkSsysycyEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkSsysycy, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			Map roleMap = (Map) request.getSession().getAttribute("ROLE");
			if(roleMap != null){
				if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("ywgdy")|| roleMap.get("rolecode").toString().contains("scgdy")){
					cq.eq("createBy",user.getUserName());
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkSsysycyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除试身样色样船样进度
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkSsysycyEntity emkSsysycy, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkSsysycy = systemService.getEntity(EmkSsysycyEntity.class, emkSsysycy.getId());
		message = "试身样色样船样进度删除成功";
		try{
			emkSsysycyService.delete(emkSsysycy);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "试身样色样船样进度删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除试身样色样船样进度
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "试身样色样船样进度删除成功";
		try{
			for(String id:ids.split(",")){
				EmkSsysycyEntity emkSsysycy = systemService.getEntity(EmkSsysycyEntity.class, 
				id
				);
				emkSsysycyService.delete(emkSsysycy);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "试身样色样船样进度删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加试身样色样船样进度
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkSsysycyEntity emkSsysycy, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "试身样色样船样进度添加成功";
		try{
			emkSsysycyService.save(emkSsysycy);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "试身样色样船样进度添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新试身样色样船样进度
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkSsysycyEntity emkSsysycy, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "试身样色样船样进度更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		EmkSsysycyEntity t = emkSsysycyService.get(EmkSsysycyEntity.class, map.get("ssyId"));
		try {
			emkSsysycy.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkSsysycy, t);
			emkSsysycyService.saveOrUpdate(t);
			//保存明细数据
			String dataRows = (String) map.get("orderMxListIDSR");
			if (Utils.notEmpty(dataRows)) {
				systemService.executeSql("delete from emk_ssysycy_detail where ssy_id = ? ",t.getId());

				int rows = Integer.parseInt(dataRows);
				EmkSsysycyDetailEntity emkSsysycyDetailEntity = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))){
						emkSsysycyDetailEntity = new EmkSsysycyDetailEntity();
						emkSsysycyDetailEntity.setSsyId(t.getId());
						emkSsysycyDetailEntity.setOrderNo(map.get("orderMxList["+i+"].orderNo00"));
						emkSsysycyDetailEntity.setCusNum(map.get("orderMxList["+i+"].cusNum00"));
						emkSsysycyDetailEntity.setProduceHtNum(map.get("orderMxList["+i+"].produceHtNum00"));
						emkSsysycyDetailEntity.setGysCode(map.get("orderMxList["+i+"].gysCode00"));
						emkSsysycyDetailEntity.setGyzl(map.get("orderMxList["+i+"].gyzl00"));
						emkSsysycyDetailEntity.setProTypeName(map.get("orderMxList["+i+"].proTypeName00"));
						emkSsysycyDetailEntity.setSampleNo(map.get("orderMxList["+i+"].sampleNo00"));
						emkSsysycyDetailEntity.setSampleNoDesc(map.get("orderMxList["+i+"].sampleDesc00"));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
							String[] colorArr = map.get("orderMxList["+i+"].color").split(",");
							emkSsysycyDetailEntity.setColor(colorArr[1]);
						}
//						emkSsysycyDetailEntity.setColor(map.get("orderMxList["+i+"].color"));
						emkSsysycyDetailEntity.setSumTotal(map.get("orderMxList["+i+"].signTotal00"));
						emkSsysycyDetailEntity.setSsyzt(map.get("orderMxList["+i+"].ssyzt00"));
						emkSsysycyDetailEntity.setSsyDate(map.get("orderMxList["+i+"].ssyDate00"));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelSsy00"))){
							emkSsysycyDetailEntity.setLeavelSsy(Integer.parseInt(map.get("orderMxList["+i+"].leavelSsy00")));
						}

						emkSsysycyDetailEntity.setCqyzt(map.get("orderMxList["+i+"].cqyzt00"));
						emkSsysycyDetailEntity.setCqyDate(map.get("orderMxList["+i+"].cqyDate00"));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelCq00"))){
							emkSsysycyDetailEntity.setLeavelCq(Integer.parseInt(map.get("orderMxList["+i+"].leavelCq00")));
						}

						emkSsysycyDetailEntity.setSyzt(map.get("orderMxList["+i+"].syzt00"));
						emkSsysycyDetailEntity.setSyDate(map.get("orderMxList["+i+"].syDate00"));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelSy00"))){
							emkSsysycyDetailEntity.setLeavelSy(Integer.parseInt(map.get("orderMxList["+i+"].leavelSy00")));
						}

						emkSsysycyDetailEntity.setCyzt(map.get("orderMxList["+i+"].cyzt00"));
						emkSsysycyDetailEntity.setCyDate(map.get("orderMxList["+i+"].cyDate00"));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelCy00"))){
							emkSsysycyDetailEntity.setLeavelCy(Integer.parseInt(map.get("orderMxList["+i+"].leavelCy00")));
						}
						emkSsysycyDetailEntity.setSortDesc(String.valueOf(i+1));
						emkSsysycyDetailEntity.setOutDate(map.get("orderMxList["+i+"].outDate00"));

						systemService.save(emkSsysycyDetailEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "试身样色样船样进度更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 试身样色样船样进度新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkSsysycyEntity emkSsysycy, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));

		if (StringUtil.isNotEmpty(emkSsysycy.getId())) {
			emkSsysycy = emkSsysycyService.getEntity(EmkSsysycyEntity.class, emkSsysycy.getId());
			req.setAttribute("emkSsysycyPage", emkSsysycy);
		}
		return new ModelAndView("com/emk/produce/ssysycy/emkSsysycy-add");
	}
	/**
	 * 试身样色样船样进度编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkSsysycyEntity emkSsysycy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkSsysycy.getId())) {
			emkSsysycy = emkSsysycyService.getEntity(EmkSsysycyEntity.class, emkSsysycy.getId());
			req.setAttribute("emkSsysycyPage", emkSsysycy);
		}
		return new ModelAndView("com/emk/produce/ssysycy/emkSsysycy-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkSsysycyController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkSsysycyEntity emkSsysycy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkSsysycyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkSsysycy, request.getParameterMap());
		List<EmkSsysycyEntity> emkSsysycys = this.emkSsysycyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"试身样色样船样进度");
		modelMap.put(NormalExcelConstants.CLASS,EmkSsysycyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("试身样色样船样进度列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkSsysycys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkSsysycyEntity emkSsysycy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"试身样色样船样进度");
    	modelMap.put(NormalExcelConstants.CLASS,EmkSsysycyEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("试身样色样船样进度列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkSsysycyEntity> listEmkSsysycyEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkSsysycyEntity.class,params);
				for (EmkSsysycyEntity emkSsysycy : listEmkSsysycyEntitys) {
					emkSsysycyService.save(emkSsysycy);
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
	@ApiOperation(value="试身样色样船样进度列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkSsysycyEntity>> list() {
		List<EmkSsysycyEntity> listEmkSsysycys=emkSsysycyService.getList(EmkSsysycyEntity.class);
		return Result.success(listEmkSsysycys);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取试身样色样船样进度信息",notes="根据ID获取试身样色样船样进度信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkSsysycyEntity task = emkSsysycyService.get(EmkSsysycyEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取试身样色样船样进度信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建试身样色样船样进度")
	public ResponseMessage<?> create(@ApiParam(name="试身样色样船样进度对象")@RequestBody EmkSsysycyEntity emkSsysycy, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkSsysycyEntity>> failures = validator.validate(emkSsysycy);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkSsysycyService.save(emkSsysycy);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("试身样色样船样进度信息保存失败");
		}
		return Result.success(emkSsysycy);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新试身样色样船样进度",notes="更新试身样色样船样进度")
	public ResponseMessage<?> update(@ApiParam(name="试身样色样船样进度对象")@RequestBody EmkSsysycyEntity emkSsysycy) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkSsysycyEntity>> failures = validator.validate(emkSsysycy);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkSsysycyService.saveOrUpdate(emkSsysycy);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新试身样色样船样进度信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新试身样色样船样进度信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除试身样色样船样进度")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkSsysycyService.deleteEntityById(EmkSsysycyEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("试身样色样船样进度删除失败");
		}

		return Result.success();
	}
}
