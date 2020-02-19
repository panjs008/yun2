package com.hm.rsgl.basesalary.controller;
import com.emk.util.DateUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import com.emk.util.WebFileUtils;
import com.emk.util.excel.ExcelUtil;
import com.hm.rsgl.basesalary.entity.HmBaseSalaryEntity;
import com.hm.rsgl.basesalary.entity.HmChangeSalaryEntity;
import com.hm.rsgl.basesalary.service.HmBaseSalaryServiceI;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hm.rsgl.salary.entity.HmSalaryEntity;
import com.hm.rsgl.staff.entity.HmStaffEntity;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
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
 * @Description: 薪酬预设管理
 * @author onlineGenerator
 * @date 2019-07-08 22:19:21
 * @version V1.0   
 *
 */
@Api(value="HmBaseSalary",description="薪酬预设管理",tags="hmBaseSalaryController")
@Controller
@RequestMapping("/hmBaseSalaryController")
public class HmBaseSalaryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HmBaseSalaryController.class);

	@Autowired
	private HmBaseSalaryServiceI hmBaseSalaryService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 薪酬预设管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("month",DateUtil.format(new Date(),"yyyy-MM"));
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A02' order by order_num asc");
		request.setAttribute("categoryEntities",categoryEntities);
		return new ModelAndView("com/hm/rsgl/basesalary/hmBaseSalaryList");
	}
	/**
	 * 薪酬变动管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "listChange")
	public ModelAndView listChange(HttpServletRequest request) {
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A02' order by order_num asc");
		request.setAttribute("categoryEntities",categoryEntities);
		return new ModelAndView("com/hm/rsgl/basesalary/hmBaseSalaryListChange");
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
	public void datagrid(HmBaseSalaryEntity hmBaseSalary,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(HmBaseSalaryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hmBaseSalary, request.getParameterMap());
		try{
		//自定义追加查询条件
			Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
			String month = "";
			if(Utils.notEmpty(param.get("month"))){
				month = param.get("month");
				cq.eq("month",month);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.hmBaseSalaryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "datagridChange")
	public void datagridChange(HmChangeSalaryEntity hmBaseSalary,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(HmChangeSalaryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hmBaseSalary, request.getParameterMap());
		try{
			//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.hmBaseSalaryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除薪酬预设管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(HmBaseSalaryEntity hmBaseSalary, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		hmBaseSalary = systemService.getEntity(HmBaseSalaryEntity.class, hmBaseSalary.getId());
		message = "薪酬预设管理删除成功";
		try{
			hmBaseSalaryService.delete(hmBaseSalary);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "薪酬预设管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除薪酬预设管理
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "薪酬预设管理删除成功";
		try{
			for(String id:ids.split(",")){
				HmBaseSalaryEntity hmBaseSalary = systemService.getEntity(HmBaseSalaryEntity.class,
				id
				);
				hmBaseSalaryService.delete(hmBaseSalary);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "薪酬预设管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doBatchDelChange")
	@ResponseBody
	public AjaxJson doBatchDelChange(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "薪酬预设管理删除成功";
		try{
			for(String id:ids.split(",")){
				HmChangeSalaryEntity hmBaseSalary = systemService.getEntity(HmChangeSalaryEntity.class,
						id
				);
				hmBaseSalaryService.delete(hmBaseSalary);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "薪酬预设管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加薪酬预设管理
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(HmBaseSalaryEntity hmBaseSalary, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "薪酬预设管理添加成功";
		try{
			this.systemService.executeSql("delete from hm_base_salary where real_name=?",hmBaseSalary.getRealName());
			hmBaseSalaryService.save(hmBaseSalary);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "薪酬预设管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 生成薪酬预设管理
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doRe")
	@ResponseBody
	public AjaxJson doRe(HmBaseSalaryEntity hmBaseSalary, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "生成薪酬预设管理成功";
		try{
			Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
			String month = "";
			if(Utils.notEmpty(param.get("month"))){
				month = param.get("month");
			}else{
				month = DateUtil.format(new Date(),"yyyy-MM");
			}
			//获取上个月日期
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(Utils.parseMonth(month));
			calendar.add(Calendar.MONTH, -1);
			Date date2 = calendar.getTime();
			String dateStr2 = Utils.formatMonth(date2);
			//查询已生成的月薪酬预设数据
			List<HmBaseSalaryEntity> hmBaseSalaryEntityList0 = systemService.findHql("from HmBaseSalaryEntity where month=? ",month);
			Map<String, HmBaseSalaryEntity> baseSalaryEntityHashMap = new HashMap<>();
			for(HmBaseSalaryEntity baseSalaryEntity : hmBaseSalaryEntityList0){
				baseSalaryEntityHashMap.put(baseSalaryEntity.getWorkNo(),baseSalaryEntity);
			}

			Map<String,HmStaffEntity> staffMap = new HashMap();
			List<HmStaffEntity> staffEntityList = systemService.findHql("from HmStaffEntity");
			for(HmStaffEntity hmStaffEntity : staffEntityList){
				staffMap.put(hmStaffEntity.getRealName(),hmStaffEntity);
			}

			//this.systemService.executeSql("delete from hm_base_salary where month=?",month);
			List<HmBaseSalaryEntity> hmBaseSalaryEntityList = systemService.findHql("from HmBaseSalaryEntity where month=?",dateStr2);
			HmBaseSalaryEntity hmBaseSalaryEntity = null;
			for(HmBaseSalaryEntity baseSalaryEntity : hmBaseSalaryEntityList){
				hmBaseSalaryEntity = systemService.singleResult("from HmBaseSalaryEntity where realName='"+baseSalaryEntity.getRealName()+"' and month='"+month+"'");
				if(Utils.isEmpty(hmBaseSalaryEntity)){
					HmStaffEntity staffEntity = staffMap.get(baseSalaryEntity.getRealName().toString());
					if("0".equals(staffEntity.getState())){
						hmBaseSalaryEntity = new HmBaseSalaryEntity();
						MyBeanUtils.copyBeanNotNull2Bean(baseSalaryEntity,hmBaseSalaryEntity);
						hmBaseSalaryEntity.setId(null);
						hmBaseSalaryEntity.setMonth(month);
						systemService.saveOrUpdate(hmBaseSalaryEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "生成薪酬预设管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doAddChange")
	@ResponseBody
	public AjaxJson doAddChange(HmChangeSalaryEntity hmChangeSalaryEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "薪酬调薪添加成功";
		try{
			systemService.save(hmChangeSalaryEntity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "薪酬调薪添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新薪酬预设管理
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(HmBaseSalaryEntity hmBaseSalary, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "薪酬预设管理更新成功";
		Map<String,String> p = ParameterUtil.getParamMaps(request.getParameterMap());
		HmBaseSalaryEntity t = hmBaseSalaryService.get(HmBaseSalaryEntity.class, p.get("bid"));
		try {
			hmBaseSalary.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(hmBaseSalary, t);
			hmBaseSalaryService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "薪酬预设管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doUpdateChange")
	@ResponseBody
	public AjaxJson doUpdateChange(HmChangeSalaryEntity hmChangeSalaryEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "薪酬调薪更新成功";
		HmChangeSalaryEntity t = hmBaseSalaryService.get(HmChangeSalaryEntity.class, hmChangeSalaryEntity.getId());

		try {
			MyBeanUtils.copyBeanNotNull2Bean(hmChangeSalaryEntity, t);
			systemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "薪酬调薪更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 薪酬预设管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HmBaseSalaryEntity hmBaseSalary, HttpServletRequest req) {
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A02' order by order_num asc");
		req.setAttribute("categoryEntities",categoryEntities);
		if (StringUtil.isNotEmpty(hmBaseSalary.getId())) {
			hmBaseSalary = hmBaseSalaryService.getEntity(HmBaseSalaryEntity.class, hmBaseSalary.getId());
			req.setAttribute("hmBaseSalaryPage", hmBaseSalary);
		}
		return new ModelAndView("com/hm/rsgl/basesalary/hmBaseSalary-add");
	}
	/**
	 * 薪酬变动管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAddChange")
	public ModelAndView goAddChange(HmChangeSalaryEntity hmChangeSalaryEntity, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A02' order by order_num asc");
		req.setAttribute("categoryEntities",categoryEntities);
		if (StringUtil.isNotEmpty(hmChangeSalaryEntity.getRealName())) {
//			HmBaseSalaryEntity t = hmBaseSalaryService.findUniqueByProperty(HmBaseSalaryEntity.class,"realName", hmChangeSalaryEntity.getRealName());
			HmBaseSalaryEntity t = systemService.singleResult("from HmBaseSalaryEntity where realName='"+hmChangeSalaryEntity.getRealName()+"' and month='"+hmChangeSalaryEntity.getMonth()+"'");
			req.setAttribute("hmBaseSalaryPage", t);
		}
		return new ModelAndView("com/hm/rsgl/basesalary/hmBaseSalary-addChange");
	}
	/**
	 * 薪酬预设管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(HmBaseSalaryEntity hmBaseSalary, HttpServletRequest req) {
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A02' order by order_num asc");
		req.setAttribute("categoryEntities",categoryEntities);
		if (StringUtil.isNotEmpty(hmBaseSalary.getId())) {
			hmBaseSalary = hmBaseSalaryService.getEntity(HmBaseSalaryEntity.class, hmBaseSalary.getId());
			req.setAttribute("hmBaseSalaryPage", hmBaseSalary);
		}
		return new ModelAndView("com/hm/rsgl/basesalary/hmBaseSalary-update");
	}

	@RequestMapping(params = "goUpdateChange")
	public ModelAndView goUpdateChange(HmChangeSalaryEntity hmBaseSalary, HttpServletRequest req) {
		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A02' order by order_num asc");
		req.setAttribute("categoryEntities",categoryEntities);
		if (StringUtil.isNotEmpty(hmBaseSalary.getId())) {
			hmBaseSalary = hmBaseSalaryService.getEntity(HmChangeSalaryEntity.class, hmBaseSalary.getId());
			req.setAttribute("hmBaseSalaryPage", hmBaseSalary);
		}
		return new ModelAndView("com/hm/rsgl/basesalary/hmBaseSalary-updateChange");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","hmBaseSalaryController");
		return new ModelAndView("common/upload/pub_excel_upload2");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HmBaseSalaryEntity hmBaseSalary,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String savepath = request.getRealPath("/")+"export/model/";
		File file = new File(savepath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		savepath = request.getRealPath("/")+"export/model/薪酬预设表.xls";
		Map titleMap = new HashMap();
		StringBuffer sql = new StringBuffer();
		Map pamp = ParameterUtil.getParamMaps(request.getParameterMap());
		String month = "";
		if(Utils.notEmpty(pamp.get("month"))){
			month = pamp.get("month").toString();
		}else{
			month = DateUtil.format(new Date(),"yyyy-MM");
		}
		sql.append(" select t1.* from hm_base_salary t1 where month=? ");

		List<String> headList = new ArrayList<String>();
		headList.add("月份");
		headList.add("姓名");
		headList.add("部门");
		headList.add("车间");
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("month");
		fieldList.add("real_name");
		fieldList.add("dept_name");
		fieldList.add("work_name");
		List<Map<String,Object>> dataList = systemService.findForJdbc(sql.toString(),month);

		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A02' order by order_num asc");
		for(Map codeMap : categoryEntities){
			headList.add(codeMap.get("name").toString());
			fieldList.add(codeMap.get("code").toString());
		}
		try {
			ExcelUtil.createExcel(savepath,headList, fieldList, dataList);
			WebFileUtils.downLoad(savepath, response, false);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			file.delete();
		}
		return null;
	}
	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(HmBaseSalaryEntity hmBaseSalary,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String savepath = request.getRealPath("/")+"export/model/";
		File file = new File(savepath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		savepath = request.getRealPath("/")+"export/model/薪酬预设表.xls";
		List<String> headList = new ArrayList<String>();
		headList.add("月份");
		headList.add("姓名");
		headList.add("部门");
		headList.add("车间");
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("month");
		fieldList.add("real_name");
		fieldList.add("dept_name");
		fieldList.add("work_name");
		List<Map<String,Object>> dataList = new ArrayList();

		List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A02' order by order_num asc");
		for(Map codeMap : categoryEntities){
			headList.add(codeMap.get("name").toString());
			fieldList.add(codeMap.get("code").toString());
		}
		try {
			ExcelUtil.createExcel(savepath,headList, fieldList, dataList);
			WebFileUtils.downLoad(savepath, response, false);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			file.delete();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public Object importExcel(HttpServletRequest request, HttpServletResponse response) {
		String sussess = null;
		Map retmap = new HashMap();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			File newfile = null;
			HSSFWorkbook wb = null;
			String cellValue = "";
			HmBaseSalaryEntity hmBaseSalaryEntity = null;
			try {
				String savepath = request.getRealPath("/")+"imp/project/";
				String savename = WebFileUtils.saveFile(file, savepath);
				newfile =  new File(savepath+savename);
				wb = WebFileUtils.createHSSFWorkBook(newfile);
				if (wb==null) {
					logger.error("传入文件无法识别，请检查文件类型！！");
					sussess = "false";
				}
				HSSFSheet sheet = wb.getSheetAt(0);
				DecimalFormat df = new DecimalFormat("0");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
				HSSFCell cell = null;
				int counter = 0;
				HSSFRow row = null;
				logger.info("执行导入："+newfile.getName());
				int colorNum1 = 1;
				List<String> itemValue =null;
				List<String> itemValue0 = new ArrayList<String>();
				List<String> codeList = new ArrayList<String>();

				row = sheet.getRow(0);
				for(int z = 0; z <= 26 ; z++){
					cell = row.getCell(z);
					if(cell == null){
						itemValue0.add(cellValue);
						continue;
					}
					switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							cellValue =cell.getRichStringCellValue().getString().trim();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							cellValue = df.format(cell.getNumericCellValue()).toString();
							break;
						default:
							cellValue = "";
					}
					itemValue0.add(cellValue);
					cellValue = "";
				}
				for(int z = 4; z < itemValue0.size() ; z++){
					Map code = systemService.findOneForJdbc("select lower(code) code from hm_category where PARENT_CODE='A01A02' and  name=?",itemValue0.get(z));
					if(code != null){
						codeList.add(code.get("code").toString());
					}
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if(row == null){
						continue;
					}else{
						counter++;
					}
					itemValue = new ArrayList<String>();
					for(int z = 0; z <= 26 ; z++){
						cell = row.getCell(z);
						if(cell == null){
							itemValue.add(cellValue);
							continue;
						}
						switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								cellValue =cell.getRichStringCellValue().getString().trim();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								cellValue = df.format(cell.getNumericCellValue()).toString();
								break;
							default:
								cellValue = "";
						}
						itemValue.add(cellValue);
						cellValue = "";
					}
					if(Utils.notEmpty(itemValue.get(1))){
						hmBaseSalaryEntity = new HmBaseSalaryEntity();
						hmBaseSalaryEntity.setMonth(itemValue.get(0));
						hmBaseSalaryEntity.setRealName(itemValue.get(1));
						hmBaseSalaryEntity.setDeptName(itemValue.get(2));
						hmBaseSalaryEntity.setWorkName(itemValue.get(3));
						this.systemService.executeSql("delete from hm_base_salary where real_name=? and month=?",hmBaseSalaryEntity.getRealName(),hmBaseSalaryEntity.getMonth());
						Map staff = systemService.findOneForJdbc("select * from hm_staff where real_name=? limit 0,1",hmBaseSalaryEntity.getRealName());
						if(Utils.notEmpty(staff)){
							hmBaseSalaryEntity.setWorkNo(staff.get("work_no").toString());
							hmBaseSalaryEntity.setXclb(staff.get("xclb").toString());

							hmBaseSalaryEntity.setDeptName(staff.get("dept_name").toString());
							hmBaseSalaryEntity.setDeptCode(staff.get("dept_code").toString());
							hmBaseSalaryEntity.setWorkName(staff.get("work_name").toString());
							hmBaseSalaryEntity.setWorkCode(staff.get("work_code").toString());
							hmBaseSalaryEntity.setGroupName(staff.get("group_name").toString());
							hmBaseSalaryEntity.setGroupCode(staff.get("group_code").toString());

						}

						this.hmBaseSalaryService.save(hmBaseSalaryEntity);
						String sql = "update hm_base_salary set  ";
						int  codei = 0;

						for(int z = 0 ; z < codeList.size() ; z++){
							sql += codeList.get(z)+"='"+itemValue.get(z+4)+"',";
						}
						if(itemValue.size()>1){
							sql = sql.substring(0,sql.length()-1);
							sql += " where id='"+hmBaseSalaryEntity.getId()+"'";
							this.systemService.executeSql(sql);
						}
					}

				}
				sussess = "true";
			} catch (Exception e) {
				sussess = "false";
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
					newfile.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		retmap.put("sussess",sussess);
		return retmap;
	}


	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="薪酬预设管理列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<HmBaseSalaryEntity>> list() {
		List<HmBaseSalaryEntity> listHmBaseSalarys=hmBaseSalaryService.getList(HmBaseSalaryEntity.class);
		return Result.success(listHmBaseSalarys);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取薪酬预设管理信息",notes="根据ID获取薪酬预设管理信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		HmBaseSalaryEntity task = hmBaseSalaryService.get(HmBaseSalaryEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取薪酬预设管理信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建薪酬预设管理")
	public ResponseMessage<?> create(@ApiParam(name="薪酬预设管理对象")@RequestBody HmBaseSalaryEntity hmBaseSalary, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<HmBaseSalaryEntity>> failures = validator.validate(hmBaseSalary);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			hmBaseSalaryService.save(hmBaseSalary);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("薪酬预设管理信息保存失败");
		}
		return Result.success(hmBaseSalary);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新薪酬预设管理",notes="更新薪酬预设管理")
	public ResponseMessage<?> update(@ApiParam(name="薪酬预设管理对象")@RequestBody HmBaseSalaryEntity hmBaseSalary) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<HmBaseSalaryEntity>> failures = validator.validate(hmBaseSalary);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			hmBaseSalaryService.saveOrUpdate(hmBaseSalary);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新薪酬预设管理信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新薪酬预设管理信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除薪酬预设管理")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			hmBaseSalaryService.deleteEntityById(HmBaseSalaryEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("薪酬预设管理删除失败");
		}

		return Result.success();
	}
}
