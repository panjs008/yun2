package com.emk.approval.approval.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approval.service.EmkApprovalServiceI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
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
 * @Description: 审批业务表
 * @author onlineGenerator
 * @date 2019-01-22 21:41:10
 * @version V1.0   
 *
 */
@Api(value="EmkApproval",description="审批业务表",tags="emkApprovalController")
@Controller
@RequestMapping("/emkApprovalController")
public class EmkApprovalController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkApprovalController.class);

	@Autowired
	private EmkApprovalServiceI emkApprovalService;

	@Autowired
	private Validator validator;
	


	/**
	 * 审批业务表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/approval/approval/emkApprovalList");
	}
	/**
	 * 历史查询审批业务表列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "history")
	public ModelAndView history(HttpServletRequest request) {
		return new ModelAndView("com/emk/approval/approval/emkApprovalList-history");
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
	public void datagrid(EmkApprovalEntity emkApproval,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkApprovalEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkApproval, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = ResourceUtil.getSessionUser();
			if(user.getUserKey().equals("采购员")){
				cq.add(Restrictions.sqlRestriction("((({alias}.status=1 or {alias}.status=24 or {alias}.status=25) and {alias}.type=5 ) and {alias}.next_bpm_sher_id='"+user.getUserName()+"' ) or (({alias}.status=1) and ({alias}.type=6 or {alias}.type=7))"));
			}
			if(user.getUserKey().equals("仓库员")){
				cq.add(Restrictions.sqlRestriction("(({alias}.status=40 and {alias}.type=5) or (({alias}.status=24 or {alias}.status=25) and ({alias}.type=6 or {alias}.type=7)) )"));
			}
			if(user.getUserKey().equals("业务员")){
				cq.add(Restrictions.sqlRestriction("({alias}.next_bpm_sher_id='"+user.getUserName()+"' )"));
			}
			cq.add(Restrictions.sqlRestriction("({alias}.status!=2)"));

			/*if(user.getUserKey().equals("工厂")){
				EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
				Map d = systemService.findOneForJdbc("select ifnull(GROUP_CONCAT(t.enquiry_no),'') orderNos from emk_enquiry t where t.GYS_CODE=?",factoryArchivesEntity.getCompanyCode());
				cq.add(Restrictions.sqlRestriction("(({alias}.status=1 or {alias}.status=10 or {alias}.status=12 or {alias}.status=15 or {alias}.status=19) and  FIND_IN_SET({alias}.work_num,'"+d.get("orderNos")+"') or (({alias}.status=3 or {alias}.status=6 or {alias}.status=7 ) and FIND_IN_SET('"+user.getUserName()+"',{alias}.next_bpm_sher_id)) )"));
			}else{
				cq.add(Restrictions.sqlRestriction("(({alias}.status=3 or {alias}.status=6 or {alias}.status=7  or {alias}.status=9 or {alias}.status=11" +
						" or {alias}.status=12  or {alias}.status=13  or {alias}.status=14 or {alias}.status=15  or {alias}.status=17 or {alias}.status=18 or {alias}.status=19) and FIND_IN_SET('"+user.getUserName()+"',{alias}.next_bpm_sher_id)  or {alias}.status=1 or {alias}.status=4 or {alias}.status=5 or {alias}.status=8 or {alias}.status=10  or {alias}.status=16)"));
			}*/
			cq.add();
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkApprovalService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 历史查询
	 *
	 * @return
	 */
	@RequestMapping(params = "listAllByJdbc")
	public void listAllByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		String sql = "" ,countSql = "";
		TSUser user = ResourceUtil.getSessionUser();

		sql +=" SELECT DISTINCT t1.id,t1.commit_id commitId,t1.create_name createName,t1.commit_time commitTime,t1.type,t1.work_num workNum, \n";
		sql +="	 t1.status,t1.state1,t1.state2,t1.state3,t1.process_name processName,(select CONCAT(p.NAME_,'-',p.TASK_DEF_KEY_) from act_ru_task p where p.ASSIGNEE_ = t1.form_id limit 0,1) processNode,t1.bpm_sher bpmSher, \n";
		sql +="	 t1.bpm_sher_id bpmSherId,t1.bpm_status bpmStatus,t1.bpm_date bpmDate,t1.form_id formId FROM emk_approval t1 \n";
		sql +="	 LEFT JOIN emk_approval_detail t2 ON t1.id=t2.approval_id \n";
		sql +="  WHERE 1=1 and t2.approve_user_id='"+user.getId()+"' ";

		if(Utils.notEmpty(map.get("workNum"))){
			sql += " and t1.work_num like '%"+map.get("workNum")+"%'";
		}
		if(Utils.notEmpty(map.get("bpmDate_begin"))){
			sql += " and t1.bpm_date >= '"+map.get("bpmDate_begin")+" 00:00:00'";
		}
		if(Utils.notEmpty(map.get("bpmDate_end"))){
			sql += " and t1.bpm_date <= '"+map.get("bpmDate_end")+" 23:59:59'";
		}
		sql += " order by commit_time desc ";

		countSql = "SELECT count(1) FROM ("+sql+") t9 ";

		sql += " limit ";
		if(dataGrid.getPage()==1){
			sql += " 0, "+dataGrid.getRows();
		}else{
			sql += (dataGrid.getPage()-1)*dataGrid.getRows()+","+dataGrid.getRows();
		}

		this.systemService.listAllByJdbc(dataGrid, sql, countSql);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除审批业务表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkApprovalEntity emkApproval, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkApproval = systemService.getEntity(EmkApprovalEntity.class, emkApproval.getId());
		message = "审批业务表删除成功";
		try{
			emkApprovalService.delete(emkApproval);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "审批业务表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除审批业务表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "审批业务表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkApprovalEntity emkApproval = systemService.getEntity(EmkApprovalEntity.class, 
				id
				);
				emkApprovalService.delete(emkApproval);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "审批业务表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加审批业务表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkApprovalEntity emkApproval, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "审批业务表添加成功";
		try{
			emkApprovalService.save(emkApproval);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "审批业务表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新审批业务表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkApprovalEntity emkApproval, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "审批业务表更新成功";
		EmkApprovalEntity t = emkApprovalService.get(EmkApprovalEntity.class, emkApproval.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(emkApproval, t);
			emkApprovalService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "审批业务表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 审批业务表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkApprovalEntity emkApproval, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkApproval.getId())) {
			emkApproval = emkApprovalService.getEntity(EmkApprovalEntity.class, emkApproval.getId());
			req.setAttribute("emkApprovalPage", emkApproval);
		}
		return new ModelAndView("com/emk/approval/approval/emkApproval-add");
	}
	/**
	 * 审批业务表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkApprovalEntity emkApproval, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkApproval.getId())) {
			emkApproval = emkApprovalService.getEntity(EmkApprovalEntity.class, emkApproval.getId());
			req.setAttribute("emkApprovalPage", emkApproval);
		}
		return new ModelAndView("com/emk/approval/approval/emkApproval-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkApprovalController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkApprovalEntity emkApproval,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkApprovalEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkApproval, request.getParameterMap());
		List<EmkApprovalEntity> emkApprovals = this.emkApprovalService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"审批业务表");
		modelMap.put(NormalExcelConstants.CLASS,EmkApprovalEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("审批业务表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkApprovals);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkApprovalEntity emkApproval,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"审批业务表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkApprovalEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("审批业务表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkApprovalEntity> listEmkApprovalEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkApprovalEntity.class,params);
				for (EmkApprovalEntity emkApproval : listEmkApprovalEntitys) {
					emkApprovalService.save(emkApproval);
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
	@ApiOperation(value="审批业务表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkApprovalEntity>> list() {
		List<EmkApprovalEntity> listEmkApprovals=emkApprovalService.getList(EmkApprovalEntity.class);
		return Result.success(listEmkApprovals);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取审批业务表信息",notes="根据ID获取审批业务表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkApprovalEntity task = emkApprovalService.get(EmkApprovalEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取审批业务表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建审批业务表")
	public ResponseMessage<?> create(@ApiParam(name="审批业务表对象")@RequestBody EmkApprovalEntity emkApproval, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkApprovalEntity>> failures = validator.validate(emkApproval);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkApprovalService.save(emkApproval);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("审批业务表信息保存失败");
		}
		return Result.success(emkApproval);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新审批业务表",notes="更新审批业务表")
	public ResponseMessage<?> update(@ApiParam(name="审批业务表对象")@RequestBody EmkApprovalEntity emkApproval) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkApprovalEntity>> failures = validator.validate(emkApproval);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkApprovalService.saveOrUpdate(emkApproval);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新审批业务表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新审批业务表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除审批业务表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkApprovalService.deleteEntityById(EmkApprovalEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("审批业务表删除失败");
		}

		return Result.success();
	}
}
