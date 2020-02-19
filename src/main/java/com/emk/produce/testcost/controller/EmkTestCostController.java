package com.emk.produce.testcost.controller;

import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.caiwu.financecheck.entity.EmkFinanceCheckEntity;
import com.emk.caiwu.yfcheck.entity.EmkFinanceYfCheckEntity;
import com.emk.produce.testcost.entity.EmkTestCostDetailEntity;
import com.emk.produce.testcost.entity.EmkTestCostEntity;
import com.emk.produce.testcost.service.EmkTestCostServiceI;

import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.util.ApprovalUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;


import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.jeecgframework.core.util.ExceptionUtil;

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
 * @Description: 测试费用付款申请
 * @author onlineGenerator
 * @date 2018-10-27 10:49:20
 * @version V1.0   
 *
 */
@Api(value="EmkTestCost",description="测试费用付款申请",tags="emkTestCostController")
@Controller
@RequestMapping("/emkTestCostController")
public class EmkTestCostController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkTestCostController.class);

	@Autowired
	private EmkTestCostServiceI emkTestCostService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 测试费用付款申请列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/produce/testcost/emkTestCostList");
	}

	@RequestMapping(params = "detailMxList")
	public ModelAndView detailMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		List<Map<String, Object>> list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='testtype'");
		request.setAttribute("testTypeList", list);
		if (Utils.notEmpty(map.get("costId"))) {
			List<EmkTestCostDetailEntity> emkTestCostDetailEntities = systemService.findHql("from EmkTestCostDetailEntity where testCostId=?", map.get("costId"));
			request.setAttribute("emkTestCostDetailEntities", emkTestCostDetailEntities);
		}
		return new ModelAndView("com/emk/produce/testcost/detailMxList");
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
	public void datagrid(EmkTestCostEntity emkTestCost,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkTestCostEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkTestCost, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			/*Map roleMap = (Map) request.getSession().getAttribute("ROLE");
			if(roleMap != null){
				if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("ywgdy")|| roleMap.get("rolecode").toString().contains("scgdy")){
					cq.eq("createBy",user.getUserName());
				}
			}*/
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkTestCostService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除测试费用付款申请
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkTestCostEntity emkTestCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkTestCost = systemService.getEntity(EmkTestCostEntity.class, emkTestCost.getId());
		message = "测试费用付款申请删除成功";
		try{
			emkTestCostService.delete(emkTestCost);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "测试费用付款申请删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除测试费用付款申请
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "测试费用付款申请删除成功";
		try{
			for(String id:ids.split(",")){
				EmkTestCostEntity emkTestCost = systemService.getEntity(EmkTestCostEntity.class, 
				id
				);
				systemService.executeSql("delete from emk_test_cost_detail where test_cost_id=?",id);
				emkTestCostService.delete(emkTestCost);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "测试费用付款申请删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加测试费用付款申请
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkTestCostEntity emkTestCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "测试费用付款申请添加成功";
		try{
			emkTestCost.setState("0");
			Map orderNum = this.systemService.findOneForJdbc("select CAST(ifnull(max(right(COST_NO, 3)),0)+1 AS signed) orderNum from emk_test_cost");
			emkTestCost.setCostNo("CSFY" + emkTestCost.getCusNum() + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));

			emkTestCostService.save(emkTestCost);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "测试费用付款申请添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新测试费用付款申请
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkTestCostEntity emkTestCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "测试费用付款申请更新成功";
		Map<String,String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		EmkTestCostEntity t = emkTestCostService.get(EmkTestCostEntity.class, map.get("testCostId"));
		try {
			emkTestCost.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkTestCost, t);
			emkTestCostService.saveOrUpdate(t);
			//保存明细数据
			String dataRows = (String) map.get("orderMxListIDSR");
			if (Utils.notEmpty(dataRows)) {
				systemService.executeSql("delete from emk_test_cost_detail where test_cost_id = ? ",t.getId());

				int rows = Integer.parseInt(dataRows);
				EmkTestCostDetailEntity emkTestCostDetailEntity = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].testNo00"))){
						emkTestCostDetailEntity = new EmkTestCostDetailEntity();
						emkTestCostDetailEntity.setTestCostId(t.getId());
						emkTestCostDetailEntity.setTestNo(map.get("orderMxList["+i+"].testNo00"));
						emkTestCostDetailEntity.setOrderNo(map.get("orderMxList["+i+"].orderNo00"));
						emkTestCostDetailEntity.setSampleNo(map.get("orderMxList["+i+"].sampleNo00"));
						emkTestCostDetailEntity.setProduceNum(map.get("orderMxList["+i+"].produceNum00"));
						emkTestCostDetailEntity.setTestType(map.get("orderMxList["+i+"].testType00"));
						emkTestCostDetailEntity.setTestContent(map.get("orderMxList["+i+"].testContent00"));
						emkTestCostDetailEntity.setTestResult(map.get("orderMxList["+i+"].testResult00"));
						emkTestCostDetailEntity.setTestMoney(map.get("orderMxList["+i+"].testMoney00"));
						systemService.save(emkTestCostDetailEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "测试费用付款申请更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 测试费用付款申请新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkTestCostEntity emkTestCost, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));

		if (StringUtil.isNotEmpty(emkTestCost.getId())) {
			emkTestCost = emkTestCostService.getEntity(EmkTestCostEntity.class, emkTestCost.getId());
			req.setAttribute("emkTestCostPage", emkTestCost);
		}
		return new ModelAndView("com/emk/produce/testcost/emkTestCost-add");
	}
	/**
	 * 测试费用付款申请编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkTestCostEntity emkTestCost, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkTestCost.getId())) {
			emkTestCost = emkTestCostService.getEntity(EmkTestCostEntity.class, emkTestCost.getId());
			req.setAttribute("emkTestCostPage", emkTestCost);

			try {
				Map countMap = MyBeanUtils.culBeanCounts(emkTestCost);
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
		return new ModelAndView("com/emk/produce/testcost/emkTestCost-update");
	}

	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkTestCostEntity emkTestCost, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkTestCost.getId())) {
			emkTestCost = emkTestCostService.getEntity(EmkTestCostEntity.class, emkTestCost.getId());
			req.setAttribute("emkTestCostPage", emkTestCost);

			try {
				Map countMap = MyBeanUtils.culBeanCounts(emkTestCost);
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
		return new ModelAndView("com/emk/produce/testcost/emkTestCost-update2");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkTestCostController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkTestCostEntity emkTestCost,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkTestCostEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkTestCost, request.getParameterMap());
		List<EmkTestCostEntity> emkTestCosts = this.emkTestCostService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"测试费用付款申请");
		modelMap.put(NormalExcelConstants.CLASS,EmkTestCostEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("测试费用付款申请列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkTestCosts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkTestCostEntity emkTestCost,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"测试费用付款申请");
    	modelMap.put(NormalExcelConstants.CLASS,EmkTestCostEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("测试费用付款申请列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkTestCostEntity> listEmkTestCostEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkTestCostEntity.class,params);
				for (EmkTestCostEntity emkTestCost : listEmkTestCostEntitys) {
					emkTestCostService.save(emkTestCost);
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
	@ApiOperation(value="测试费用付款申请列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkTestCostEntity>> list() {
		List<EmkTestCostEntity> listEmkTestCosts=emkTestCostService.getList(EmkTestCostEntity.class);
		return Result.success(listEmkTestCosts);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取测试费用付款申请信息",notes="根据ID获取测试费用付款申请信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkTestCostEntity task = emkTestCostService.get(EmkTestCostEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取测试费用付款申请信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建测试费用付款申请")
	public ResponseMessage<?> create(@ApiParam(name="测试费用付款申请对象")@RequestBody EmkTestCostEntity emkTestCost, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkTestCostEntity>> failures = validator.validate(emkTestCost);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkTestCostService.save(emkTestCost);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("测试费用付款申请信息保存失败");
		}
		return Result.success(emkTestCost);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新测试费用付款申请",notes="更新测试费用付款申请")
	public ResponseMessage<?> update(@ApiParam(name="测试费用付款申请对象")@RequestBody EmkTestCostEntity emkTestCost) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkTestCostEntity>> failures = validator.validate(emkTestCost);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkTestCostService.saveOrUpdate(emkTestCost);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新测试费用付款申请信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新测试费用付款申请信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除测试费用付款申请")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkTestCostService.deleteEntityById(EmkTestCostEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("测试费用付款申请删除失败");
		}

		return Result.success();
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkTestCostEntity emkTestCostEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "测试费用付款申请表提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			if (Utils.isEmpty(emkTestCostEntity.getId())) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkTestCostEntity testCostEntity = systemService.getEntity(EmkTestCostEntity.class, id);
					if (!testCostEntity.getState().equals("0")) {
						message = "存在已提交的测试费用付款申请表，请重新选择在提交！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkTestCostEntity.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkTestCostEntity t = emkTestCostService.get(EmkTestCostEntity.class, id);
					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
					if(Utils.isEmpty(b)){
						//type 工单类型，workNum 单号，formId 对应表单ID，bpmName 节点名称，bpmNode 节点代码，advice 处理意见，user 用户对象
						b = new EmkApprovalEntity();
						EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
						ApprovalUtil.saveApproval(b,16,t.getCostNo(),t.getId(),user);
						systemService.save(b);
						ApprovalUtil.saveApprovalDetail(approvalDetailEntity,b.getId(),"【生产跟单员】费用付款申请","testcostTask","提交",user);
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
						if (task1.getTaskDefinitionKey().equals("testcostTask")) {
							taskService.complete(task1.getId(), variables);
							t.setState("1");
							b.setStatus(1);
							saveApprvoalDetail(approvalDetail,"重新提交的费用付款申请","testTask",0,"重新提交费用付款申请");
							saveSmsAndEmailForMany("业务跟单员","重新提交的费用付款申请","您有【"+b.getCreateName()+"】重新提交的费用付款申请，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
						}
						if (task1.getTaskDefinitionKey().equals("ywgdfhTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(61);
								approvalDetail.setBpmName("业务跟单复核");
								t.setState("61");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForOne("业务跟单复核","您有【"+user.getRealName()+"】审核通过的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"业务跟单复核","ywgdfhTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"ywgdfhTask","testcostTask","费用付款申请");
								t.setState("0");
								b.setStatus(0);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("业务跟单复核","您有【"+user.getRealName()+"】回退的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("ywjlshTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(3);
								approvalDetail.setBpmName("业务经理审核");
								t.setState("3");
								approvalDetail.setApproveStatus(0);
								bpmUser = systemService.get(TSUser.class,b.getCommitId());
								saveSmsAndEmailForOne("业务经理审核","您有【"+user.getRealName()+"】审核通过的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}else{
								saveApprvoalDetail(approvalDetail,"业务经理审核","ywjlshTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"ywjlshTask","ywgdfhTask","费用付款申请");
								t.setState("64");
								b.setStatus(64);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("业务经理审核","您有【"+user.getRealName()+"】回退的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("cwTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(25);
								approvalDetail.setBpmName("财务审核");
								t.setState("25");
								approvalDetail.setApproveStatus(0);
								bpmUser = systemService.get(TSUser.class,b.getCommitId());

								saveSmsAndEmailForOne("财务审核","您有【"+user.getRealName()+"】审核通过的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"财务审核","cwTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"cwTask","ywjlshTask","费用付款申请");
								t.setState("4");
								b.setStatus(4);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("财务审核","您有【"+user.getRealName()+"】回退的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("cwjlshTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(26);
								approvalDetail.setBpmName("财务经理审核");
								t.setState("26");
								approvalDetail.setApproveStatus(0);
								bpmUser = systemService.get(TSUser.class,b.getCommitId());

								saveSmsAndEmailForOne("财务经理审核","您有【"+user.getRealName()+"】审核通过的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
								//生成应付核准单
								EmkFinanceYfCheckEntity emkFinanceYfCheckEntity = new EmkFinanceYfCheckEntity();
								MyBeanUtils.copyBeanNotNull2Bean(t,emkFinanceYfCheckEntity);
								emkFinanceYfCheckEntity.setId(null);
								emkFinanceYfCheckEntity.setState("0");
								Map<String, Object> orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(yhz_check_no, 6)),0)+1 AS signed) orderNum from emk_finance_yf_check");
								systemService.save(emkFinanceYfCheckEntity);
							}else{
								saveApprvoalDetail(approvalDetail,"财务经理审核","cwjlshTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"cwjlshTask","cwTask","费用付款申请");
								t.setState("27");
								b.setStatus(27);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("财务经理审核","您有【"+user.getRealName()+"】回退的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("zjlpfTask")){
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(48);
								approvalDetail.setBpmName("总经理批复");
								t.setState("48");
								approvalDetail.setApproveStatus(0);
								bpmUser = systemService.get(TSUser.class,b.getCommitId());

								saveSmsAndEmailForOne("总经理批复","您有【"+user.getRealName()+"】审核通过的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}else{
								backProcess(task1.getProcessInstanceId(),"zjlpfTask","fkhzdTask","总经理批复");
								b.setBpmStatus("1");
								EmkFinanceYfCheckEntity emkFinanceYfCheck = systemService.findUniqueByProperty(EmkFinanceYfCheckEntity.class,"testNo",t.getCostNo());
								emkFinanceYfCheck.setState("0");
								saveSmsAndEmailForOne("总经理批复","您有【"+user.getRealName()+"】回退的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("cwzzTask")){
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(2);
								approvalDetail.setBpmName("财务转账");
								t.setState("2");
								approvalDetail.setApproveStatus(0);

								EmkFinanceYfCheckEntity emkFinanceYfCheck = systemService.findUniqueByProperty(EmkFinanceYfCheckEntity.class,"testNo",t.getCostNo());
								emkFinanceYfCheck.setState("2");
								bpmUser = systemService.get(TSUser.class,b.getCommitId());
								saveSmsAndEmailForOne("财务转账","您有【"+user.getRealName()+"】审核通过的费用付款申请，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						systemService.saveOrUpdate(approvalDetail);
					}else {
						ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("testcost", "emkTestCostEntity", variables);
						task = taskService.createTaskQuery().taskAssignee(id).list();
						Task task1 = task.get(task.size() - 1);
						taskService.complete(task1.getId(), variables);

						saveSmsAndEmailForMany("业务跟单员","【生产跟单员】费用付款申请","您有【"+b.getCreateName()+"】提交的费用付款申请，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
						t.setState("1");
						b.setStatus(1);
						b.setBpmStatus("0");
						b.setProcessName("【生产跟单员】费用付款申请");
					}

					systemService.saveOrUpdate(b);
					systemService.saveOrUpdate(t);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "测试费用付款申请表提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="goWork")
	public ModelAndView goWork(EmkTestCostEntity emkTestCostEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkTestCostEntity.getId())) {
			emkTestCostEntity = emkTestCostService.getEntity(EmkTestCostEntity.class, emkTestCostEntity.getId());
			req.setAttribute("emkTestCostPage", emkTestCostEntity);
		}
		return new ModelAndView("com/emk/produce/testcost/emkTestCost-work");
	}

	@RequestMapping(params="goTime")
	public ModelAndView goTime(EmkTestCostEntity emkTestCostEntity, HttpServletRequest req, DataGrid dataGrid) {
		EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkTestCostEntity.getId());
		if(Utils.notEmpty(approvalEntity)){
			List<EmkApprovalDetailEntity> approvalDetailEntityList = systemService.findHql("from EmkApprovalDetailEntity where approvalId=? order by approveDate asc",approvalEntity.getId());
			req.setAttribute("approvalDetailEntityList", approvalDetailEntityList);
			req.setAttribute("approvalEntity", approvalEntity);
			req.setAttribute("createDate", org.jeecgframework.core.util.DateUtils.date2Str(approvalEntity.getCreateDate(), org.jeecgframework.core.util.DateUtils.datetimeFormat));
		}
		return new ModelAndView("com/emk/produce/testcost/time");
	}
}
