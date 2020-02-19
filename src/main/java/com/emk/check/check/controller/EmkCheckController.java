package com.emk.check.check.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.bill.proorderbox.entity.EmkProOrderBoxEntity;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.check.check.entity.EmkCheckEntity;
import com.emk.check.check.service.EmkCheckServiceI;

import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.check.qualitycheck.entity.EmkQualityCheckEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.produce.cargospace.entity.EmkCargoSpaceEntity;
import com.emk.produce.invoices.entity.EmkInvoicesDetailEntity;
import com.emk.produce.outforum.entity.EmkOutForumEntity;
import com.emk.produce.produce.entity.EmkProduceDetailEntity;
import com.emk.produce.produce.entity.EmkProduceEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.util.ApprovalUtil;
import com.emk.util.FlowUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
 * @Description: 验货申请表
 * @author onlineGenerator
 * @date 2018-09-24 17:44:19
 * @version V1.0   
 *
 */
@Api(value="EmkCheck",description="验货申请表",tags="emkCheckController")
@Controller
@RequestMapping("/emkCheckController")
public class EmkCheckController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkCheckController.class);

	@Autowired
	private EmkCheckServiceI emkCheckService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 验货申请表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/check/check/emkCheckList");
	}

	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		request.setAttribute("categoryEntityList", codeList);
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if(Utils.notEmpty(map.get("proOrderId"))) {
			List<EmkEnquiryDetailEntity> emkProOrderDetailEntities = this.systemService.findHql("from EmkEnquiryDetailEntity where enquiryId=?", map.get("proOrderId"));
			request.setAttribute("emkProOrderDetailEntities", emkProOrderDetailEntities);
		}
		return new ModelAndView("com/emk/check/check/orderMxList");
	}

	@RequestMapping(params = "detailMxList")
	public ModelAndView detailMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(remark,',',typecode) typecode,typecode code,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
		String color = JSONHelper.collection2json(list);
		request.setAttribute("color", "'"+color+ "'");
		list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='size'");
		request.setAttribute("sizeList", list);
		if (Utils.notEmpty(map.get("invoicesId"))) {
			List<EmkInvoicesDetailEntity> emkInvoicesDetailEntities = systemService.findHql("from EmkInvoicesDetailEntity where invoicesId=?", map.get("invoicesId"));
			request.setAttribute("emkInvoicesDetailEntities", emkInvoicesDetailEntities);
		}
		return new ModelAndView("com/emk/check/check/detailMxList");
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
	public void datagrid(EmkCheckEntity emkCheck,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkCheckEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkCheck, request.getParameterMap());
		try{
		//自定义追加查询条件
			/*TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			Map roleMap = (Map) request.getSession().getAttribute("ROLE");
			if(roleMap != null){
				if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("ywgdy") || roleMap.get("rolecode").toString().contains("scgdy") || roleMap.get("rolecode").toString().contains("ywjl")){
					cq.eq("createBy",user.getUserName());
				}
			}*/
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkCheckService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除验货申请表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkCheckEntity emkCheck, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkCheck = systemService.getEntity(EmkCheckEntity.class, emkCheck.getId());
		message = "验货申请表删除成功";
		try{
			emkCheckService.delete(emkCheck);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "验货申请表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除验货申请表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "验货申请表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkCheckEntity emkCheck = systemService.getEntity(EmkCheckEntity.class, 
				id
				);
				systemService.executeSql("delete from emk_invoices_detail where invoices_id = ?",id);
				emkCheckService.delete(emkCheck);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "验货申请表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加验货申请表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkCheckEntity emkCheck,EmkSizeEntity emkSize, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "验货申请表添加成功";
		try{
			emkCheck.setState("0");
			emkCheckService.save(emkCheck);
			emkSize.setFormId(emkCheck.getId());
			systemService.save(emkSize);
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = (String) map.get("orderMxListIDSR");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkInvoicesDetailEntity emkInvoicesDetailEntity = null;
				EmkSizeTotalEntity emkSizeTotalEntity = null;
				for (int i = 0; i < rows; i++) {
					emkInvoicesDetailEntity = new EmkInvoicesDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))) {
						emkInvoicesDetailEntity.setInvoicesId(emkCheck.getId());
						systemService.save(emkInvoicesDetailEntity);

						emkSizeTotalEntity = new EmkSizeTotalEntity();
						emkSizeTotalEntity.setTotalA(map.get("orderMxList["+i+"].totalA"));
						emkSizeTotalEntity.setTotalB(map.get("orderMxList["+i+"].totalB"));
						emkSizeTotalEntity.setTotalC(map.get("orderMxList["+i+"].totalC"));
						emkSizeTotalEntity.setTotalD(map.get("orderMxList["+i+"].totalD"));
						emkSizeTotalEntity.setTotalE(map.get("orderMxList["+i+"].totalE"));
						emkSizeTotalEntity.setTotalF(map.get("orderMxList["+i+"].totalF"));
						emkSizeTotalEntity.setTotalG(map.get("orderMxList["+i+"].totalG"));
						emkSizeTotalEntity.setTotalH(map.get("orderMxList["+i+"].totalH"));
						emkSizeTotalEntity.setTotalI(map.get("orderMxList["+i+"].totalI"));
						emkSizeTotalEntity.setTotalJ(map.get("orderMxList["+i+"].totalJ"));
						emkSizeTotalEntity.setTotalK(map.get("orderMxList["+i+"].totalK"));
						emkSizeTotalEntity.setpId(emkInvoicesDetailEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "验货申请表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新验货申请表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkCheckEntity emkCheck, EmkSizeEntity emkSize, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "验货申请表更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		EmkCheckEntity t = emkCheckService.get(EmkCheckEntity.class, map.get("checkId"));
		EmkSizeEntity t2 = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",t.getId());

		try {
			emkCheck.setState("0");
			emkCheck.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkCheck, t);
			emkCheckService.saveOrUpdate(t);

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
			String dataRows = (String) map.get("orderMxListIDSR");
			if (Utils.notEmpty(dataRows)) {
				this.systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_invoices_detail where invoices_id=?))", t.getId());
				systemService.executeSql("delete from emk_invoices_detail where invoices_id = ?",t.getId());
				int rows = Integer.parseInt(dataRows);

				EmkInvoicesDetailEntity emkInvoicesDetailEntity = null;
				EmkSizeTotalEntity emkSizeTotalEntity = null;
				for (int i = 0; i < rows; i++) {
					emkInvoicesDetailEntity = new EmkInvoicesDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))) {
						emkInvoicesDetailEntity.setInvoicesId(t.getId());
						systemService.save(emkInvoicesDetailEntity);

						emkSizeTotalEntity = new EmkSizeTotalEntity();
						emkSizeTotalEntity.setTotalA(map.get("orderMxList["+i+"].totalA"));
						emkSizeTotalEntity.setTotalB(map.get("orderMxList["+i+"].totalB"));
						emkSizeTotalEntity.setTotalC(map.get("orderMxList["+i+"].totalC"));
						emkSizeTotalEntity.setTotalD(map.get("orderMxList["+i+"].totalD"));
						emkSizeTotalEntity.setTotalE(map.get("orderMxList["+i+"].totalE"));
						emkSizeTotalEntity.setTotalF(map.get("orderMxList["+i+"].totalF"));
						emkSizeTotalEntity.setTotalG(map.get("orderMxList["+i+"].totalG"));
						emkSizeTotalEntity.setTotalH(map.get("orderMxList["+i+"].totalH"));
						emkSizeTotalEntity.setTotalI(map.get("orderMxList["+i+"].totalI"));
						emkSizeTotalEntity.setTotalJ(map.get("orderMxList["+i+"].totalJ"));
						emkSizeTotalEntity.setTotalK(map.get("orderMxList["+i+"].totalK"));
						emkSizeTotalEntity.setpId(emkInvoicesDetailEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "验货申请表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 验货申请表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkCheckEntity emkCheck, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute("LOCAL_CLINET_USER");

		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
		Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(check_num, 3)),0)+1 AS signed) orderNum from emk_check");
		req.setAttribute("checkNum","YHBH"  + DateUtils.format(new Date(), "yyMMdd") + String.format("%06d", orderNum.get("orderNum").toString()));
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		req.setAttribute("categoryEntityList", codeList);
		if (StringUtil.isNotEmpty(emkCheck.getId())) {
			emkCheck = emkCheckService.getEntity(EmkCheckEntity.class, emkCheck.getId());
			req.setAttribute("emkCheckPage", emkCheck);
		}
		return new ModelAndView("com/emk/check/check/emkCheck-add");
	}
	/**
	 * 验货申请表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkCheckEntity emkCheck, HttpServletRequest req) {
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		req.setAttribute("categoryEntityList", codeList);
		if (StringUtil.isNotEmpty(emkCheck.getId())) {
			emkCheck = emkCheckService.getEntity(EmkCheckEntity.class, emkCheck.getId());
			req.setAttribute("emkCheckPage", emkCheck);

			try {
				Map countMap = MyBeanUtils.culBeanCounts(emkCheck);
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
			}
		}
		return new ModelAndView("com/emk/check/check/emkCheck-update");
	}
	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkCheckEntity emkCheck, HttpServletRequest req) {
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		req.setAttribute("categoryEntityList", codeList);
		if (StringUtil.isNotEmpty(emkCheck.getId())) {
			emkCheck = emkCheckService.getEntity(EmkCheckEntity.class, emkCheck.getId());
			req.setAttribute("emkCheckPage", emkCheck);
		}
		return new ModelAndView("com/emk/check/check/emkCheck-update2");
	}
	@RequestMapping(params = "goUpdate3")
	public ModelAndView goUpdate3(EmkCheckEntity emkCheck, HttpServletRequest req) {
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		req.setAttribute("categoryEntityList", codeList);
		if (StringUtil.isNotEmpty(emkCheck.getId())) {
			EmkProduceEntity emkProduceEntity  = systemService.get(EmkProduceEntity.class,emkCheck.getId());
			emkCheck = systemService.findUniqueByProperty(EmkCheckEntity.class,"produceId",emkProduceEntity.getId());
			req.setAttribute("emkCheckPage", emkCheck);
		}
		return new ModelAndView("com/emk/check/check/emkCheck-update3");
	}
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkCheckController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkCheckEntity emkCheck,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkCheckEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkCheck, request.getParameterMap());
		List<EmkCheckEntity> emkChecks = this.emkCheckService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"验货申请表");
		modelMap.put(NormalExcelConstants.CLASS,EmkCheckEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("验货申请表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkChecks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkCheckEntity emkCheck,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"验货申请表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkCheckEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("验货申请表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkCheckEntity> listEmkCheckEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkCheckEntity.class,params);
				for (EmkCheckEntity emkCheck : listEmkCheckEntitys) {
					emkCheckService.save(emkCheck);
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
	@ApiOperation(value="验货申请表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkCheckEntity>> list() {
		List<EmkCheckEntity> listEmkChecks=emkCheckService.getList(EmkCheckEntity.class);
		return Result.success(listEmkChecks);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取验货申请表信息",notes="根据ID获取验货申请表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkCheckEntity task = emkCheckService.get(EmkCheckEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取验货申请表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建验货申请表")
	public ResponseMessage<?> create(@ApiParam(name="验货申请表对象")@RequestBody EmkCheckEntity emkCheck, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkCheckEntity>> failures = validator.validate(emkCheck);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkCheckService.save(emkCheck);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("验货申请表信息保存失败");
		}
		return Result.success(emkCheck);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新验货申请表",notes="更新验货申请表")
	public ResponseMessage<?> update(@ApiParam(name="验货申请表对象")@RequestBody EmkCheckEntity emkCheck) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkCheckEntity>> failures = validator.validate(emkCheck);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkCheckService.saveOrUpdate(emkCheck);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新验货申请表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新验货申请表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除验货申请表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkCheckService.deleteEntityById(EmkCheckEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("验货申请表删除失败");
		}

		return Result.success();
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkCheckEntity emkCheck, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "验货申请单提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			if (Utils.isEmpty(emkCheck.getId())) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkCheckEntity checkEntity = systemService.getEntity(EmkCheckEntity.class, id);
					if (!checkEntity.getState().equals("0")) {
						message = "存在已提交的验货申请单，请重新选择在提交验货申请单！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkCheck.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkCheckEntity t = emkCheckService.get(EmkCheckEntity.class, id);
					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
					if(Utils.isEmpty(b)){
						//type 工单类型，workNum 单号，formId 对应表单ID，bpmName 节点名称，bpmNode 节点代码，advice 处理意见，user 用户对象
						b = new EmkApprovalEntity();
						EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
						ApprovalUtil.saveApproval(b,17,t.getCheckNum(),t.getId(),user);
						systemService.save(b);
						ApprovalUtil.saveApprovalDetail(approvalDetailEntity,b.getId(),"【生产跟单员】验货申请表","checkfactoryTask","提交",user);
						systemService.save(approvalDetailEntity);
					}
					t.setState("1");
					variables.put("optUser", t.getId());

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
						if (task1.getTaskDefinitionKey().equals("checkfactoryTask")) {
							taskService.complete(task1.getId(), variables);
							t.setState("1");
							b.setStatus(1);
							saveApprvoalDetail(approvalDetail,"重新提交的验货申请表","checkfactoryTask",0,"重新提交验货申请表");
							saveSmsAndEmailForMany("业务跟单员","重新提交的验货申请表","您有【"+b.getCreateName()+"】重新提交的验货申请表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
						}
						if (task1.getTaskDefinitionKey().equals("ywgdshTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(61);
								approvalDetail.setBpmName("业务跟单复核");
								t.setState("61");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForOne("业务跟单复核","您有【"+user.getRealName()+"】审核通过的验货申请表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"业务跟单复核","ywgdshTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"ywgdshTask","checkfactoryTask","验货申请表");
								t.setState("0");
								b.setStatus(0);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("业务跟单复核","您有【"+user.getRealName()+"】回退的验货申请表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("fpyhyTask")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(66);
								approvalDetail.setBpmName("【质检部经理】分配验货员");
								t.setState("66");
								b.setNextBpmSher(map.get("realName"));
								b.setNextBpmSherId(map.get("userName"));
								approvalDetail.setApproveStatus(0);
								bpmUser = systemService.findUniqueByProperty(TSUser.class,"userName",b.getNextBpmSherId());
								saveSmsAndEmailForOne("【质检部经理】分配验货员","您有【"+user.getRealName()+"】分配的验货申请表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("jsrsTask")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(67);
								approvalDetail.setBpmName("【验货员】执行验货");
								t.setState("67");
								approvalDetail.setApproveStatus(0);
							}
						}
						if (task1.getTaskDefinitionKey().equals("bgTask")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(68);
								approvalDetail.setBpmName("【验货员】上传验货报告");
								t.setState("68");
								approvalDetail.setApproveStatus(0);
								t.setFileName(map.get("fileName"));
								t.setFileNameUrl(map.get("fileNameUrl"));
								saveSmsAndEmailForMany("质检经理","【验货员】上传验货报告","您有【"+b.getCreateName()+"】上传验货报告，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("shbgTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(66);
								approvalDetail.setBpmName("【质检部经理】审核报告");
								t.setState("66");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForOne("【质检部经理】审核报告","您有【"+user.getRealName()+"】审核通过的验货申请表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"【质检部经理】审核报告","shbgTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"shbgTask","bgTask","【验货员】上传验货报告");
								backProcess(task1.getProcessInstanceId(),"bgTask","jsrsTask","【验货员】执行验货");

								t.setState("69");
								b.setStatus(69);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("【质检部经理】审核报告","您有【"+user.getRealName()+"】回退的验货申请表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("jsbgTask")) {
							if (map.get("isPass").equals("0")) {
								if(Utils.notEmpty(t.getProduceId())){
									EmkProduceEntity emkProduceEntity = systemService.get(EmkProduceEntity.class,t.getProduceId());
									EmkApprovalEntity emkApprovalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkProduceEntity.getId());

									EmkCargoSpaceEntity emkCargoSpaceEntity = systemService.findUniqueByProperty(EmkCargoSpaceEntity.class,"produceId",t.getProduceId());

									EmkOutForumEntity emkOutForum = new EmkOutForumEntity();
									Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(OUT_FORUM_NO, 3)),0)+1 AS signed) orderNum from emk_out_forum");
									emkOutForum.setOutForumNo("CH" + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));
									emkOutForum.setProduceId(t.getProduceId());
									emkOutForum.setCargoNo(emkCargoSpaceEntity.getCargoNo());
									emkOutForum.setDcState(emkCargoSpaceEntity.getKdDate());
									emkOutForum.setCwyer(emkCargoSpaceEntity.getCwyer());
									emkOutForum.setState("0");
									systemService.save(emkOutForum);

									emkCargoSpaceEntity.setOutForumNo(emkOutForum.getOutForumNo());

									EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",emkCargoSpaceEntity.getId());
									EmkSizeEntity t1 = new EmkSizeEntity();
									MyBeanUtils.copyBeanNotNull2Bean(emkSizeEntity,t1);
									t1.setId(null);
									t1.setFormId(emkOutForum.getId());
									systemService.save(t1);

									EmkProOrderDetailEntity proOrderDetailEntity = null;
									EmkSizeTotalEntity emkSizeTotalEntity = null;

									List<EmkInvoicesDetailEntity> emkInvoicesDetailEntities = systemService.findHql("from EmkInvoicesDetailEntity where invoicesId=?", t.getId());
									for (EmkInvoicesDetailEntity invoicesDetailEntity : emkInvoicesDetailEntities) {
										proOrderDetailEntity = new EmkProOrderDetailEntity();
										MyBeanUtils.copyBeanNotNull2Bean(invoicesDetailEntity, proOrderDetailEntity);
										proOrderDetailEntity.setId(null);
										proOrderDetailEntity.setProOrderId(emkOutForum.getId());
										systemService.save(proOrderDetailEntity);

										emkSizeTotalEntity = new EmkSizeTotalEntity();
										emkSizeTotalEntity.setId(null);
										emkSizeTotalEntity.setpId(proOrderDetailEntity.getId());
										systemService.save(emkSizeTotalEntity);
									}

									List<Task> task0 = taskService.createTaskQuery().taskAssignee(emkProduceEntity.getId()).list();
									if(Utils.notEmpty(task0)){
										Task task00 = task0.get(task0.size() - 1);
										if(task00.getTaskDefinitionKey().equals("yhsqbTask")) {
											variables.put("optUser", emkProduceEntity.getId());
											taskService.complete(task00.getId(), variables);
										}
									}
								}
								taskService.complete(task1.getId(), variables);
								b.setStatus(2);
								approvalDetail.setBpmName("【生产跟单员】接收报告");
								t.setState("2");
								approvalDetail.setApproveStatus(0);
								bpmUser = systemService.get(TSUser.class,b.getCommitId());
								saveSmsAndEmailForOne("【生产跟单员】接收报告","您有【"+user.getRealName()+"】接收报告，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						systemService.saveOrUpdate(approvalDetail);
					}else {
						ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("checkhuo", "emkCheckEntity", variables);
						task = taskService.createTaskQuery().taskAssignee(id).list();
						Task task1 = task.get(task.size() - 1);
						taskService.complete(task1.getId(), variables);

						saveSmsAndEmailForMany("业务跟单员","【生产跟单员】验货申请表","您有【"+b.getCreateName()+"】提交的验货申请表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
						t.setState("1");
						b.setStatus(1);
						b.setBpmStatus("0");
						b.setProcessName("【生产跟单员】验货申请表");
					}
					systemService.saveOrUpdate(b);
					systemService.saveOrUpdate(t);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "验货申请单提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	@RequestMapping(params="goWork")
	public ModelAndView goWork(EmkCheckEntity emkCheck, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkCheck.getId())) {
			emkCheck = emkCheckService.getEntity(EmkCheckEntity.class, emkCheck.getId());
			req.setAttribute("emkCheckPage", emkCheck);
		}
		return new ModelAndView("com/emk/check/check/emkCheck-work");
	}

	@RequestMapping(params="goTime")
	public ModelAndView goTime(EmkCheckEntity emkCheck, HttpServletRequest req, DataGrid dataGrid) {
		EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkCheck.getId());
		if(Utils.notEmpty(approvalEntity)){
			List<EmkApprovalDetailEntity> approvalDetailEntityList = systemService.findHql("from EmkApprovalDetailEntity where approvalId=? order by approveDate asc",approvalEntity.getId());
			req.setAttribute("approvalDetailEntityList", approvalDetailEntityList);
			req.setAttribute("approvalEntity", approvalEntity);
			req.setAttribute("createDate", org.jeecgframework.core.util.DateUtils.date2Str(approvalEntity.getCreateDate(), org.jeecgframework.core.util.DateUtils.datetimeFormat));
		}
		return new ModelAndView("com/emk/check/check/time");

	}
}
