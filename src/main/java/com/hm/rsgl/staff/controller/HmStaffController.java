package com.hm.rsgl.staff.controller;
import com.emk.util.*;
import com.hm.rsgl.basesalary.entity.HmBaseSalaryEntity;
import com.hm.rsgl.salary.entity.HmSalaryEntity;
import com.hm.rsgl.staff.entity.*;
import com.hm.rsgl.staff.service.HmStaffServiceI;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeecg.demo.entity.TSDocument;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
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
 * @Description: 人员信息表
 * @author onlineGenerator
 * @date 2019-06-21 20:28:18
 * @version V1.0   
 *
 */
@Api(value="HmStaff",description="人员信息表",tags="hmStaffController")
@Controller
@RequestMapping("/hmStaffController")
public class HmStaffController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HmStaffController.class);

	@Autowired
	private HmStaffServiceI hmStaffService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;


	@RequestMapping(params = "hmStaffSelect0")
	public ModelAndView proSelect(HttpServletRequest request) {
		//request.getSession().setAttribute("staffSelectArr","");
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-select");
	}
	@RequestMapping(params = "forDept")
	public ModelAndView forDept(HttpServletRequest request) {
		//request.getSession().setAttribute("staffSelectArr","");
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forDept");
	}
	@RequestMapping(params = "hmStaffSelectForBc")
	public ModelAndView proSelectBc(HttpServletRequest request) {
		//request.getSession().setAttribute("staffSelectArr","");
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-selectBc");
	}

	@RequestMapping(params = "hmStaffSelect1")
	public ModelAndView proSelect1(HttpServletRequest request) {
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-select1");
	}
	@RequestMapping(params = "hmStaffSelect2")
	public ModelAndView proSelect2(HttpServletRequest request) {
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-select2");
	}
	@RequestMapping(params = "hmStaffSelect3")
	public ModelAndView proSelect3(HttpServletRequest request) {
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-select3");
	}

	@RequestMapping(params = "hmStaffSelect4")
	public ModelAndView proSelect4(HttpServletRequest request) {
		//request.getSession().setAttribute("staffSelectArr","");
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-select4");
	}

	@RequestMapping(params = "select")
	public ModelAndView select(HttpServletRequest request) {
		//request.getSession().setAttribute("staffSelectArr","");
		return new ModelAndView("com/hm/rsgl/staff/select");
	}

	@RequestMapping(params = "forLeave")
	public ModelAndView selectForLeave(HttpServletRequest request) {
		//request.getSession().setAttribute("staffSelectArr","");
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forLeave");
	}

	@RequestMapping(params = "forOld")
	public ModelAndView forOld(HttpServletRequest request) {
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forOld");
	}

	@RequestMapping(params = "forHmc")
	public ModelAndView forHmc(HttpServletRequest request) {
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forHmc");
	}

	@RequestMapping(params = "forZbbReport")
	public ModelAndView forZbbReport(HttpServletRequest request) {
		Map tj = systemService.findOneForJdbc("select count(0) rs from hm_staff  where 1=1 and state=0");
		//List<Map<String, Object>>  orgList = systemService.findForJdbc("select org_code orgCode,CONCAT(substr(departname,1,2),'<br/>',substr(departname,3,2),'<br/>',substr(departname,5,2)) departname from t_s_depart where LENGTH(org_code)=12 and departname !=''",null);
		List<Map<String, Object>>  orgList = systemService.findForJdbc("select org_code orgCode,CONCAT(substr(departname,1,2),'<br/>',substr(departname,3,2),'<br/>',substr(departname,5,2)) departname from t_s_depart where LENGTH(org_code)=9 and departname !=''",null);
		request.setAttribute("orgList",orgList);
		request.setAttribute("rs",tj.get("rs"));
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forZbbReport");
	}

	@RequestMapping(params = "forAnsily")
	public ModelAndView forAnsily(HttpServletRequest request) {
		List<Map<String, Object>>  typeList = systemService.findForJdbc("SELECT t2.typecode, t2.typename FROM t_s_typegroup t1 LEFT JOIN t_s_type t2 ON t2.typegroupid = t1.ID WHERE t1.typegroupcode = ?","yglb");
		request.setAttribute("typeList",typeList);
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forAnsily");
	}

	@RequestMapping(params = "forYgdtReport")
	public ModelAndView forYgdtReport(HttpServletRequest request) {
		request.setAttribute("month",DateUtil.format(new Date(),"yyyy-MM"));
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forYgdtReport");
	}

	@RequestMapping(params = "forYgdtMx")
	public ModelAndView forYgdtMx(HttpServletRequest request) {
		List<Map<String, Object>>  typeList = systemService.findForJdbc("SELECT t2.typecode, t2.typename FROM t_s_typegroup t1 LEFT JOIN t_s_type t2 ON t2.typegroupid = t1.ID WHERE t1.typegroupcode = ?","yglb");
		request.setAttribute("typeList",typeList);
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forYgdtMx");
	}
	@RequestMapping(params = "forLzYgdtMx")
	public ModelAndView forLzYgdtMx(HttpServletRequest request) {
		List<Map<String, Object>>  typeList = systemService.findForJdbc("SELECT t2.typecode, t2.typename FROM t_s_typegroup t1 LEFT JOIN t_s_type t2 ON t2.typegroupid = t1.ID WHERE t1.typegroupcode = ?","yglb");
		request.setAttribute("typeList",typeList);
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList-forLzYgdtMx");
	}
	/**
	 * 人员信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/hm/rsgl/staff/hmStaffList");
	}

	@RequestMapping(params = "uploadVi")
	public ModelAndView uploadView(HttpServletRequest request) {
		return new ModelAndView("com/hm/rsgl/staff/uploadVi");
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
	public void datagrid(HmStaffEntity hmStaff,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(HmStaffEntity.class, dataGrid);
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hmStaff, request.getParameterMap());
		try{
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			if(Utils.notEmpty(param.get("deptId"))){
				TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"id",param.get("deptId"));
				if(tsDepart.getOrgCode().length()==3){
					cq.eq("orgCode",tsDepart.getOrgCode());
				}else{
					cq.eq("deptCode",tsDepart.getOrgCode());
				}
			}else{
				cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			}
			//自定义追加查询条件
			/*if(Utils.notEmpty(param.get("orgCode")) &&  !"A01".equals(param.get("orgCode"))){
				cq.add(Restrictions.sqlRestriction("({alias}.dept_code='"+param.get("orgCode")+"' or {alias}.work_code='"+param.get("orgCode")+"' or {alias}.group_code='"+param.get("orgCode")+"')"));
			}*/
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.hmStaffService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 总报表历史查询
	 *
	 * @return
	 */
	@RequestMapping(params = "listAllByJdbc")
	public void listAllByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql = "",countSql = "";
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		String month = "";

		List<Map<String, Object>>  orgList = systemService.findForJdbc("select org_code from t_s_depart where LENGTH(org_code)=9 and departname !=''",null);
		sql +=" select h.dept_name deptName,h.dept_code deptCode,h.work_name workName,h.work_code workCode,(select count(1) from hm_staff a where a.dept_code=h.dept_code and state=0) bmrs \n";
		for(Map orgCode : orgList){
			sql += ",sum(CASE h.work_code WHEN '"+orgCode.get("org_code")+"'  THEN 1 ELSE 0 END ) "+orgCode.get("org_code")+" \n";
		}
		sql+=" from hm_staff h where 1=1 and state=0 ";

		if(Utils.notEmpty(param.get("deptName"))){
			sql += " and h.dept_name like '%"+param.get("deptName")+"%'";
		}
		if(Utils.notEmpty(param.get("workName"))){
			sql += " and h.work_name like '%"+param.get("workName")+"%'";
		}
		/*if(Utils.notEmpty(param.get("groupName"))){
			sql += " and h.group_name like '%"+param.get("groupName")+"%'";
		}*/

		sql += "  GROUP BY h.dept_code  ";

		countSql = "SELECT count(1) FROM ("+sql+") t9 ";

		sql += " limit ";
		if(dataGrid.getPage()==1){
			sql += " 0, "+dataGrid.getRows();
		}else{
			sql += (dataGrid.getPage()-1)*dataGrid.getRows()+","+dataGrid.getRows();
		}

		this.systemService.listAllByJdbc(dataGrid, sql, countSql);
		dataGrid.setFooter("deptName:总人数,bmrs:bmrs");

		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 本月员工动态
	 *
	 * @return
	 */
	@RequestMapping(params = "listAllYgdtByJdbc")
	public void listAllYgdtByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql = "",countSql = "";
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		String month = "";
		if(Utils.notEmpty(param.get("month"))){
			month = param.get("month");
		}else{
			month = DateUtil.format(new Date(),"yyyy-MM");
		}
		sql +=" select deptName,workName,workCode,groupCode,sex,groupName,sum(rzrs) rzrs,sum(sqrs) sqrs,sum(ylrs) ylrs,sum(zlrs) zlrs from (select h.dept_name deptName,h.work_name workName,h.work_code workCode,h.group_code groupCode,h.sex,h.group_name groupName,count(0) rzrs,0 sqrs,0 ylrs,0 zlrs\n" +
				" from hm_staff h where 1=1 and state=0 ";
		sql += "	and left(h.rz_date,7)='"+month+"'\n" ;
		if(Utils.notEmpty(param.get("deptName"))){
			sql += " and h.dept_name like '%"+param.get("deptName")+"%'";
		}
		if(Utils.notEmpty(param.get("workName"))){
			sql += " and h.work_name like '%"+param.get("workName")+"%'";
		}
		if(Utils.notEmpty(param.get("groupName"))){
			sql += " and h.group_name like '%"+param.get("groupName")+"%'";
		}
		sql += " GROUP BY h.dept_code,h.work_code,h.group_code,h.sex\n" +
				"UNION \n" +
				"select h.dept_name deptName,h.work_name workName,h.work_code workCode,h.group_code groupCode,h.sex,h.group_name groupName,0 rzrs,\n" +
				"sum(CASE WHEN h.state=0  THEN 1 ELSE 0 END ) sqrs,\n" +
				"sum(CASE WHEN h.state=1  THEN 1 ELSE 0 END ) ylrs,\n" +
				"sum(CASE WHEN h.state=1 and h.leave_type=1  THEN 1 ELSE 0 END ) zlrs\n" +
				" from hm_leave_staff h where 1=1 and left(h.leave_date,7)='"+month+"'\n";
		if(Utils.notEmpty(param.get("deptName"))){
			sql += " and h.dept_name like '%"+param.get("deptName")+"%'";
		}
		if(Utils.notEmpty(param.get("workName"))){
			sql += " and h.work_name like '%"+param.get("workName")+"%'";
		}
		if(Utils.notEmpty(param.get("groupName"))){
			sql += " and h.group_name like '%"+param.get("groupName")+"%'";
		}
		sql +=" GROUP BY h.dept_code,h.work_code,h.group_code,h.sex) t GROUP BY t.workCode,t.groupCode,t.sex asc";

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
	 * 明细分析
	 *
	 * @return
	 */
	@RequestMapping(params = "listAllAnsilyByJdbc")
	public void listAllAnsilyByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql = "",countSql = "";
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		String month = "";

		List<Map<String, Object>>  typeList = systemService.findForJdbc("SELECT t2.typecode, t2.typename FROM t_s_typegroup t1 LEFT JOIN t_s_type t2 ON t2.typegroupid = t1.ID WHERE t1.typegroupcode = ?","yglb");
		sql +=" select * from (select h.dept_name deptName,h.work_code workCode,h.work_name workName,h.group_name groupName,'男' sex \n";
		for(Map type : typeList){
			sql += ",sum(CASE WHEN h.sex='男' and h.yglb='"+type.get("typecode")+"' THEN 1 ELSE 0 END ) 'A"+type.get("typecode")+"' \n";
		}
		sql+=" from hm_staff h where 1=1 and state=0 and h.sex='男' ";
		if(Utils.notEmpty(param.get("deptCode"))){
			sql += " and h.dept_code = '"+param.get("deptCode")+"'";
		}
		if(Utils.notEmpty(param.get("workName"))){
			sql += " and h.work_name like '%"+param.get("workName")+"%'";
		}
		if(Utils.notEmpty(param.get("groupName"))){
			sql += " and h.group_name like '%"+param.get("groupName")+"%'";
		}
		sql += "  group by h.work_code,h.sex  ";

		sql +=" union select h.dept_name deptName,h.work_code workCode,h.work_name workName,h.group_name groupName,'女' sex \n";
		for(Map type : typeList){
			sql += ",sum(CASE WHEN h.sex='女' and h.yglb='"+type.get("typecode")+"' THEN 1 ELSE 0 END ) 'A"+type.get("typecode")+"' \n";
		}
		sql+=" from hm_staff h where 1=1 and state=0 and h.sex='女' ";
		if(Utils.notEmpty(param.get("deptCode"))){
			sql += " and h.dept_code = '"+param.get("deptCode")+"'";
		}
		if(Utils.notEmpty(param.get("workName"))){
			sql += " and h.work_name like '%"+param.get("workName")+"%'";
		}
		if(Utils.notEmpty(param.get("groupName"))){
			sql += " and h.group_name like '%"+param.get("groupName")+"%'";
		}
		sql += "  group by h.work_code,h.sex) t order by t.workCode ";


		countSql = "SELECT count(1) FROM ("+sql+") t9 ";

		sql += " limit ";
		if(dataGrid.getPage()==1){
			sql += " 0,1000";
		}else{
			sql += (dataGrid.getPage()-1)*dataGrid.getRows()+","+dataGrid.getRows();
		}
		this.systemService.listAllByJdbc(dataGrid, sql, countSql);
		List<Map<String, Object>> results = dataGrid.getResults();
		int d1 = 0,d2 = 0,d3 = 0,d4 = 0,d5 = 0,d6 = 0,d7 = 0,zj=0;
		for(Map<String, Object> m : results){
			d1 += Integer.valueOf(Utils.notEmpty(m.get("A01")) ? m.get("A01").toString():"0");
			d2 += Integer.valueOf(Utils.notEmpty(m.get("A02")) ? m.get("A02").toString():"0");
			d3 += Integer.valueOf(Utils.notEmpty(m.get("A03")) ? m.get("A03").toString():"0");
			d4 += Integer.valueOf(Utils.notEmpty(m.get("A04")) ? m.get("A04").toString():"0");
			d5 += Integer.valueOf(Utils.notEmpty(m.get("A05")) ? m.get("A05").toString():"0");
			d6 += Integer.valueOf(Utils.notEmpty(m.get("A06")) ? m.get("A06").toString():"0");
			d7 += Integer.valueOf(Utils.notEmpty(m.get("A07")) ? m.get("A07").toString():"0");
			zj += d1+d2+d3+d4+d5+d6+d7;
		}
		dataGrid.setFooter("deptName:合计,A01:"+d1+",A02:"+d2+",A03:"+d3+",A04:"+d4+",A05:"+d5+",A06:"+d6+",A07:"+d7+",createName:"+zj);

		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 本月员工动态明细
	 *
	 * @return
	 */
	@RequestMapping(params = "listAllYgdtMxByJdbc")
	public void listAllYgdtMxByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql = "",countSql = "";
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		String month = "";
		if(Utils.notEmpty(param.get("month"))){
			month = param.get("month");
		}else{
			month = DateUtil.format(new Date(),"yyyy-MM");
		}
		List<Map<String, Object>>  typeList = systemService.findForJdbc("SELECT t2.typecode, t2.typename FROM t_s_typegroup t1 LEFT JOIN t_s_type t2 ON t2.typegroupid = t1.ID WHERE t1.typegroupcode = ?","yglb");
		sql +=" select * from (select h.dept_name deptName,h.work_code,h.work_name workName,h.group_name groupName,'男' sex \n";
		for(Map type : typeList){
			sql += ",sum(CASE WHEN h.sex='男' and h.yglb='"+type.get("typecode")+"' THEN 1 ELSE 0 END ) 'A"+type.get("typecode")+"' \n";
		}
		sql+=" from hm_staff h where 1=1 and state=0 and h.sex='男' ";
		sql += "	and left(h.rz_date,7)='"+month+"'\n" ;
		if(Utils.notEmpty(param.get("groupCode"))){
			sql += " and h.group_code = '"+param.get("groupCode")+"'";
		}
		sql += "  group by h.work_code,h.sex  ";

		sql +=" union select h.dept_name deptName,h.work_code,h.work_name workName,h.group_name groupName,'女' sex \n";
		for(Map type : typeList){
			sql += ",sum(CASE WHEN h.sex='女' and h.yglb='"+type.get("typecode")+"' THEN 1 ELSE 0 END ) 'A"+type.get("typecode")+"' \n";
		}
		sql+=" from hm_staff h where 1=1 and state=0 and h.sex='女' ";
		sql += "	and left(h.rz_date,7)='"+month+"'\n" ;
		if(Utils.notEmpty(param.get("groupCode"))){
			sql += " and h.group_code = '"+param.get("groupCode")+"'";
		}

		sql += "  group by h.work_code,h.sex) t order by t.work_code ";


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
	 * 本月离职员工动态明细
	 *
	 * @return
	 */
	@RequestMapping(params = "listAllLzYgdtMxByJdbc")
	public void listAllLzYgdtMxByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql = "",countSql = "";
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		String month = "";
		if(Utils.notEmpty(param.get("month"))){
			month = param.get("month");
		}else{
			month = DateUtil.format(new Date(),"yyyy-MM");
		}
		List<Map<String, Object>>  typeList = systemService.findForJdbc("SELECT t2.typecode, t2.typename FROM t_s_typegroup t1 LEFT JOIN t_s_type t2 ON t2.typegroupid = t1.ID WHERE t1.typegroupcode = ?","yglb");
		sql +=" select * from (select h.dept_name deptName,h.work_code,h.work_name workName,h.group_name groupName,'男' sex \n";
		for(Map type : typeList){
			sql += ",sum(CASE WHEN h.sex='男' and h.yglb='"+type.get("typecode")+"' THEN 1 ELSE 0 END ) 'A"+type.get("typecode")+"' \n";
		}
		sql+=" from hm_leave_staff h where 1=1 and h.sex='男' ";
		sql += "	and left(h.leave_date,7)='"+month+"'\n" ;
		if(Utils.notEmpty(param.get("groupCode"))){
			sql += " and h.group_code = '"+param.get("groupCode")+"'";
		}
		if("0".equals(param.get("type"))){
			sql += " and h.state=0 ";
		}
		if("1".equals(param.get("type"))){
			sql += " and h.state=1 ";
		}
		if("2".equals(param.get("type"))){
			sql += " and h.state=1 and h.leave_type=1 ";
		}
		sql += "  group by h.work_code,h.sex  ";

		sql +=" union select h.dept_name deptName,h.work_code,h.work_name workName,h.group_name groupName,'女' sex \n";
		for(Map type : typeList){
			sql += ",sum(CASE WHEN h.sex='女' and h.yglb='"+type.get("typecode")+"' THEN 1 ELSE 0 END ) 'A"+type.get("typecode")+"' \n";
		}
		sql+=" from hm_leave_staff h where 1=1 and state=0 and h.sex='女' ";
		sql += "	and left(h.leave_date,7)='"+month+"'\n" ;
		if(Utils.notEmpty(param.get("groupCode"))){
			sql += " and h.group_code = '"+param.get("groupCode")+"'";
		}
		if("0".equals(param.get("type"))){
			sql += " and h.state=0 ";
		}
		if("1".equals(param.get("type"))){
			sql += " and h.state=1 ";
		}
		if("2".equals(param.get("type"))){
			sql += " and h.state=1 and h.leave_type=1 ";
		}
		sql += "  group by h.work_code,h.sex) t order by t.work_code ";


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
	 * 删除人员信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(HmStaffEntity hmStaff, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		hmStaff = systemService.getEntity(HmStaffEntity.class, hmStaff.getId());
		message = "人员信息表删除成功";
		try{
			hmStaffService.delete(hmStaff);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "人员信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除人员信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "人员信息表删除成功";
		try{
			for(String id:ids.split(",")){
				HmStaffEntity hmStaff = systemService.getEntity(HmStaffEntity.class, 
				id
				);
				hmStaffService.delete(hmStaff);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "人员信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加人员信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(HmStaffEntity hmStaff, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "人员信息表添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",orgCode);
			hmStaff.setDepartId(tsDepart.getId());
			hmStaff.setOrgCode(tsDepart.getOrgCode());
			tsDepart = systemService.findUniqueByProperty(TSDepart.class,"id",map.get("orgIds"));
			hmStaff.setDeptName(tsDepart.getDepartname());
			hmStaff.setDeptCode(tsDepart.getOrgCode());
			hmStaff.setZjm(ChineseToEnglish.getPinYinHeadChar(hmStaff.getRealName()));
			hmStaffService.save(hmStaff);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "人员信息表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新人员信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(HmStaffEntity hmStaff, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "人员信息表更新成功";
		HmStaffEntity t = hmStaffService.get(HmStaffEntity.class, hmStaff.getId());
		try {
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"id",map.get("orgIds"));
			hmStaff.setDeptName(tsDepart.getDepartname());
			hmStaff.setDeptCode(tsDepart.getOrgCode());
			hmStaff.setZjm(ChineseToEnglish.getPinYinHeadChar(hmStaff.getRealName()));

			MyBeanUtils.copyBeanNotNull2Bean(hmStaff, t);
			hmStaffService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "人员信息表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	public String setSession(String p,HttpServletRequest request){
		String strResult = "";
		Map param = ParameterUtil.getParamMaps(request.getParameterMap());
		String gdNamesCodeS = request.getSession().getAttribute(p+"S").toString();
		if(Utils.notEmpty(gdNamesCodeS)){
			String[] a = gdNamesCodeS.split(",");
			String[] b = param.get(p).toString().split(",");

			List<String> la = new ArrayList(Arrays.asList(a));
			List<String> lb = new ArrayList(Arrays.asList(b));
			la.removeAll(lb);
			la.addAll(lb);
			for(int i=0;i<la.size();i++){
				strResult+=lb.get(i)+",";
			}
			if(strResult.indexOf(",")>0){
				strResult = strResult.substring(0,strResult.length()-1);
			}
			request.getSession().setAttribute(p+"S",strResult);
		}else{
			request.getSession().setAttribute(p+"S",param.get(p));
		}
		return strResult;
	}
	/**
	 * 缓存选中人员
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "getStaffSelectArr")
	@ResponseBody
	public Object getStaffSelectArr(HmStaffEntity hmStaff, HttpServletRequest request) {
		Map data = new HashMap();
		/*Map data = new HashMap();
		String str = setSession("gdNamesCode",request);
		data.put("gdNamesCode",str);*/
		/*str = setSession("gdNames",request);
		data.put("gdNames",str);

		str = setSession("workCode",request);
		data.put("workCode",str);
		str = setSession("workName",request);
		data.put("workName",str);

		str = setSession("groupCode",request);
		data.put("groupCode",str);
		str = setSession("groupName",request);
		data.put("groupName",str);*/


		/*request.getSession().setAttribute("gdNamesS",param.get("gdNames"));
		request.getSession().setAttribute("workCodeS",param.get("workCode"));
		request.getSession().setAttribute("workNameS",param.get("workName"));
		request.getSession().setAttribute("groupCodeS",param.get("groupCode"));
		request.getSession().setAttribute("groupNameS",param.get("groupName"));*/
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		String gdNamesCodeS = request.getSession().getAttribute(param.get("r")+"S").toString();
		data.put("gdNamesCode",gdNamesCodeS);
		Map staff = systemService.findOneForJdbc("SELECT GROUP_CONCAT(h.real_name) gdNames,GROUP_CONCAT(h.sex) sexs,GROUP_CONCAT(h.sex) sexs,GROUP_CONCAT(h.typename) jobs,GROUP_CONCAT(h.yglb) yglbs  FROM " +
				"	(select h0.real_name,h0.sex,t2.typename,h0.yglb from hm_staff h0" +
				" LEFT JOIN t_s_type t2 ON t2.typecode=h0.job LEFT JOIN t_s_typegroup t1 ON t1.ID=t2.typegroupid WHERE t1.typegroupcode='job' and  FIND_IN_SET(h0.work_no,?) " +
				"	order by FIND_IN_SET(h0.work_no,?) ) h",gdNamesCodeS,gdNamesCodeS);
		data.put("gdNames",staff.get("gdNames"));
		data.put("sexs",staff.get("sexs"));
		data.put("jobs",staff.get("jobs"));
		data.put("yglbs",staff.get("yglbs"));

		if("gdNamesCode".equals(param.get("r"))){
			staff = systemService.findOneForJdbc("SELECT GROUP_CONCAT(t.work_name) workName,GROUP_CONCAT(t.work_code) workCode FROM (SELECT work_code,work_name FROM hm_staff WHERE FIND_IN_SET(work_no,?) GROUP BY work_name) t",gdNamesCodeS);
			data.put("workName",staff.get("workName"));
			data.put("workCode",staff.get("workCode"));

			staff = systemService.findOneForJdbc("SELECT GROUP_CONCAT(t.group_name) groupName,GROUP_CONCAT(t.group_code) groupCode FROM (SELECT group_code,group_name FROM hm_staff WHERE FIND_IN_SET(work_no,?) GROUP BY group_name) t",gdNamesCodeS);
			data.put("groupName",staff.get("groupName"));
			data.put("groupCode",staff.get("groupCode"));
		}
		return data;
	}

	/**
	 * 缓存选中人员
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "getStaffSelectArr2")
	@ResponseBody
	public Object getStaffSelectArr2(HmStaffEntity hmStaff, HttpServletRequest request) {
		Map data = new HashMap();
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		String gdNamesCodeS = request.getSession().getAttribute(param.get("r")+"S").toString();
		data.put("gdNamesCode",gdNamesCodeS);
		Map staff = systemService.findOneForJdbc("SELECT GROUP_CONCAT(h.real_name) gdNames,GROUP_CONCAT(IFNULL(h.zcb,0)) zcbs,GROUP_CONCAT(IFNULL(h.jiab,0)) jiabs FROM (select h0.real_name,h0.work_no,t1.zcb,t1.jiab from hm_staff h0" +
				"	LEFT JOIN hm_tsjsb t1 ON t1.work_no=h0.work_no \n" +
				" 	WHERE FIND_IN_SET(h0.work_no,?) order by FIND_IN_SET(h0.work_no,?)) h",gdNamesCodeS,gdNamesCodeS);
		data.put("gdNames",staff.get("gdNames"));
		data.put("zcbs",staff.get("zcbs"));
		data.put("jiabs",staff.get("jiabs"));

		staff = systemService.findOneForJdbc("SELECT GROUP_CONCAT(IFNULL(t2.zcb,0)) tyzcbs,GROUP_CONCAT(IFNULL(t2.jiab,0)) tyjiabs FROM (select h0.* from hm_staff h0\n" +
				" WHERE FIND_IN_SET(h0.work_no,?) order by FIND_IN_SET(h0.work_no,?)) h \n" +
				"	LEFT JOIN (SELECT * FROM hm_gbbz  GROUP BY yglx,sex) t2 ON t2.yglx=h.yglb AND t2.sex=h.sex ",gdNamesCodeS,gdNamesCodeS);
		data.put("tyzcbs",staff.get("tyzcbs"));
		data.put("tyjiabs",staff.get("tyjiabs"));

		staff = systemService.findOneForJdbc("SELECT GROUP_CONCAT(t.work_name) workName,GROUP_CONCAT(t.work_code) workCode FROM (SELECT work_code,work_name FROM hm_staff WHERE FIND_IN_SET(work_no,?) GROUP BY work_name) t",gdNamesCodeS);
		data.put("workName",staff.get("workName"));
		data.put("workCode",staff.get("workCode"));

		staff = systemService.findOneForJdbc("SELECT GROUP_CONCAT(t.group_name) groupName,GROUP_CONCAT(t.group_code) groupCode FROM (SELECT group_code,group_name FROM hm_staff WHERE FIND_IN_SET(work_no,?) GROUP BY group_name) t",gdNamesCodeS);
		data.put("groupName",staff.get("groupName"));
		data.put("groupCode",staff.get("groupCode"));

		return data;
	}

	/**
	 * 获取缓存选中人员
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "getSessionStaff")
	@ResponseBody
	public Object getSessionStaff(HmStaffEntity hmStaff, HttpServletRequest request) {
		Map data = new HashMap();
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
		String gdNamesCodeS = request.getSession().getAttribute(param.get("r")+"S").toString();
		return gdNamesCodeS;
	}
	/**
	 * 保存选中人员到session
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doSaveSession")
	@ResponseBody
	public AjaxJson doSaveSession(HmStaffEntity hmStaff, HttpServletRequest request) {
		String message = null;
		AjaxJson z = new AjaxJson();
		message = "保存成功";
		try{
			Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
			String checkIds = request.getSession().getAttribute(param.get("r")).toString();
			String ids = param.get("ids");
			if(Utils.notEmpty(checkIds) || Utils.notEmpty(ids)){
				String[] a = checkIds.split(",");
				String[] b = ids.split(",");
				List<String> delList = new ArrayList<>();
				List<String> addList = new ArrayList<>();

				for(int i = 0 ; i < b.length ; i++){
					int flag = 0;
					for(int j =0 ; j < a.length ; j++){
						if(a[j].equals(b[i])){
							flag = 1;
						}
					}
					if(flag == 0){
						addList.add(b[i]);
					}
				}

				for(String str : addList){
					checkIds += str + ",";
				}
				request.getSession().setAttribute(param.get("r"),checkIds);
			}else{
				request.getSession().setAttribute(param.get("r"),param.get("ids"));
			}

		}catch(Exception e){
			e.printStackTrace();
			message = "保存失败";
			throw new BusinessException(e.getMessage());
		}
		z.setMsg(message);
		return z;
	}
	/**
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doCancelSession")
	@ResponseBody
	public AjaxJson doCancelSession(HmStaffEntity hmStaff, HttpServletRequest request) {
		String message = null;
		AjaxJson z = new AjaxJson();
		message = "取消成功";
		try{
			Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
			if(Utils.notEmpty(param.get("r"))){
				String checkIds = request.getSession().getAttribute(param.get("r")).toString();
				if(Utils.notEmpty(checkIds)){
					checkIds = checkIds.replaceAll(param.get("workNo"),"");
					String[] a = checkIds.split(",");
					checkIds = "";
					for(int i = 0; i < a.length ; i++){
						if(Utils.notEmpty(a[i])){
							checkIds += a[i] + ",";
						}
					}
					request.getSession().setAttribute(param.get("r"),checkIds);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "取消失败";
			throw new BusinessException(e.getMessage());
		}
		z.setMsg(message);
		return z;
	}
	@RequestMapping(params = "getDept", method = {RequestMethod.POST})
	@ResponseBody
	public AjaxJson getCity(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		Map param = ParameterUtil.getParamMaps(request.getParameterMap());
		try {
			List<Map<String, Object>> codeList = systemService.findForJdbc("select org_code code,departname name from t_s_depart where parent_org_code=? order by code asc", param.get("code"));
			String dataItems = "";
			try {
				for (Map map : codeList) {
					dataItems = dataItems + map.get("code") + "," + map.get("name") + ";";
				}
				if (dataItems.indexOf(";") > 0) {
					dataItems = dataItems.substring(0, dataItems.length() - 1);
				}
				j.setObj(dataItems);
			} catch (Exception e) {
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		return j;
	}

	/**
	 * 人员信息表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HmStaffEntity hmStaff, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
		req.setAttribute("orgCode",orgCode);
		Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(work_no,4)),0)+1 AS signed) orderNum from hm_staff where org_code=?",orgCode);

		req.setAttribute("workNo",String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
		if (StringUtil.isNotEmpty(hmStaff.getId())) {
			hmStaff = hmStaffService.getEntity(HmStaffEntity.class, hmStaff.getId());
			req.setAttribute("hmStaffPage", hmStaff);
		}
		return new ModelAndView("com/hm/rsgl/staff/hmStaff-add");
	}
	/**
	 * 人员信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(HmStaffEntity hmStaff, HttpServletRequest req) {

		if (StringUtil.isNotEmpty(hmStaff.getId())) {
			hmStaff = hmStaffService.getEntity(HmStaffEntity.class, hmStaff.getId());
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",hmStaff.getDeptCode());
			req.setAttribute("orgIds",tsDepart.getId());
			req.setAttribute("hmStaffPage", hmStaff);
		}
		return new ModelAndView("com/hm/rsgl/staff/hmStaff-update");
	}

	@RequestMapping(params = "goApproval")
	public ModelAndView goApproval(HmSalaryEntity hmSalary, HttpServletRequest req) {
		return new ModelAndView("com/hm/rsgl/staff/hmStaff-approval");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","hmStaffController");
		return new ModelAndView("common/upload/pub_excel_upload2");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HmStaffEntity hmStaff, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(HmStaffEntity.class, dataGrid);
		Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());

		Date date = new Date();//获取当前时间
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -5);
		String fiveYearBefore = sdf.format(calendar.getTime());

		calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -3);
		String threeYearBefore = sdf.format(calendar.getTime());

		calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		String oneYearBefore = sdf.format(calendar.getTime());
		if(Utils.notEmpty(param.get("monthV"))){
			param.put("month","0");
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hmStaff, request.getParameterMap());
		if("0".equals(param.get("month"))){
			cq.add(Restrictions.sqlRestriction("({alias}.rz_date>='"+threeYearBefore+"' and {alias}.rz_date<'"+oneYearBefore+"')"));
		}
		if("1".equals(param.get("month"))){
			cq.add(Restrictions.sqlRestriction("({alias}.rz_date>='"+fiveYearBefore+"' and {alias}.rz_date<'"+threeYearBefore+"')"));
		}
		if("2".equals(param.get("month"))){
			cq.add(Restrictions.sqlRestriction("({alias}.rz_date<'"+fiveYearBefore+"' )"));
		}
		List<HmStaffEntity> hmStaffs = this.hmStaffService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"人员信息表");
		modelMap.put(NormalExcelConstants.CLASS,HmStaffEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("人员信息表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,hmStaffs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(HmStaffEntity hmStaff,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"人员信息表");
    	modelMap.put(NormalExcelConstants.CLASS,HmStaffEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("人员信息表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	public void saveDept(TSDepart tsDepart,String col,String val,String deptName){
		TSDepart paretDept = systemService.findUniqueByProperty(TSDepart.class, col, val);
		String localMaxCode  = getMaxLocalCode(paretDept.getOrgCode());
		tsDepart.setOrgCode(YouBianCodeUtil.getSubYouBianCode(paretDept.getOrgCode(), localMaxCode));
		tsDepart.setParentOrgCode(paretDept.getOrgCode());
		tsDepart.setDepartname(deptName);
		tsDepart.setTSPDepart(paretDept);
		systemService.save(tsDepart);
	}

	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response, TSDocument document,String filesavename) {
		AjaxJson j = new AjaxJson();

		File newfile = new File(request.getRealPath("/")+filesavename);
		try {
			int result = 0;
			int index = 1;
			HSSFWorkbook wb = null;
			String cellValue = "";
			try {
				TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
				wb = WebFileUtils.createHSSFWorkBook(newfile);
				if (wb==null) {
					logger.error("传入文件无法识别，请检查文件类型！！");
					j.setMsg("文件导入失败！");
				}
				HSSFSheet sheet = wb.getSheetAt(0);
				DecimalFormat df = new DecimalFormat("0");
				HSSFCell cell = null;
				int counter = 0;
				HSSFRow row = null;
				logger.info("执行导入："+newfile.getName());
				List<String> itemValue = null;
				HmStaffEntity hmStaffEntity = null;
				Map orderNum = null;
				TSDepart tsDepart = null;
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);

					itemValue = new ArrayList<String>();
					for(int z = 0; z <= 26 ; z++){
						cell = row.getCell(z);
						if(null == cell){
							itemValue.add(cellValue);
							continue;
						}
						switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								cellValue =cell.getRichStringCellValue().getString().trim();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
									SimpleDateFormat sdf = null;
									if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
											.getBuiltinFormat("h:mm")) {
										sdf = new SimpleDateFormat("HH:mm");
									} else {// 日期
										sdf = new SimpleDateFormat("yyyy-MM-dd");
									}
									Date date = cell.getDateCellValue();
									cellValue = sdf.format(date);
								} else if (cell.getCellStyle().getDataFormat() == 58) {
									// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									double value = cell.getNumericCellValue();
									Date date = org.apache.poi.ss.usermodel.DateUtil
											.getJavaDate(value);
									cellValue = sdf.format(date);
								} else {
									double value = cell.getNumericCellValue();
									CellStyle style = cell.getCellStyle();
									DecimalFormat format = new DecimalFormat();
									String temp = style.getDataFormatString();
									// 单元格设置成常规
									if (temp.equals("General")) {
										format.applyPattern("#");
									}
									cellValue = format.format(value);
								}
								//cellValue = df.format(cell.getNumericCellValue()).toString();
								break;

							default:
								cellValue = "";
						}
						itemValue.add(cellValue);
						cellValue = "";
					}
					hmStaffEntity = new HmStaffEntity();
					if(Utils.notEmpty(itemValue.get(0))){
						//this.systemService.executeSql("delete from hm_staff where REAL_NAME=?",itemValue.get(0));
						Map staff = systemService.findOneForJdbc("select id from hm_staff where REAL_NAME=? limit 0,1",itemValue.get(0));
						if(Utils.notEmpty(staff)){
							HmStaffEntity t = systemService.get(HmStaffEntity.class,staff.get("id").toString());
							hmStaffEntity = t;
						}else{
							orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(work_no,4)),0)+1 AS signed) orderNum from hm_staff");
							hmStaffEntity.setWorkNo(String.format("%04d", orderNum.get("orderNum").toString()));
						}

						hmStaffEntity.setRealName(itemValue.get(0));
						hmStaffEntity.setDeptName(itemValue.get(1));
						hmStaffEntity.setState("0");

						Map org = systemService.findOneForJdbc("select org_code from t_s_depart where departname=? and parent_org_code='A01' limit 0,1",hmStaffEntity.getDeptName());
						if(Utils.notEmpty(org)){
							hmStaffEntity.setDeptCode(org.get("org_code").toString());

						}else{
							tsDepart = new TSDepart();
							saveDept(tsDepart,"id","402880e447e99cf10147e9a03b320003",hmStaffEntity.getDeptName());



						}

						hmStaffEntity.setRzDate(itemValue.get(11));
						hmStaffEntity.setZzDate(itemValue.get(12));
						hmStaffEntity.setLzDate(itemValue.get(13));
						hmStaffEntity.setTelphone(itemValue.get(14));
						hmStaffEntity.setIdCard(itemValue.get(15));
						if(Utils.notEmpty(hmStaffEntity.getIdCard())){
							hmStaffEntity.setBirthDay(Utils.getBirthday(hmStaffEntity.getIdCard()));
						}
						hmStaffEntity.setWorkYear(itemValue.get(17));
						hmStaffEntity.setSleepDays(itemValue.get(18));
						hmStaffEntity.setMz(itemValue.get(19));

						hmStaffEntity.setEmail(itemValue.get(26));

						systemService.saveOrUpdate(hmStaffEntity);
					}
				}

				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				e.printStackTrace();
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage(),e);

			}

		} catch (Exception e) {
			j.setMsg("文件导入失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}finally{
			newfile.delete();
		}
		return j;
	}

	private synchronized String getMaxLocalCode(String parentCode){
		if(oConvertUtils.isEmpty(parentCode)){
			parentCode = "";
		}
		int localCodeLength = parentCode.length() + YouBianCodeUtil.zhanweiLength;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT org_code FROM t_s_depart");

		if(ResourceUtil.getJdbcUrl().indexOf(JdbcDao.DATABSE_TYPE_SQLSERVER)!=-1){
			sb.append(" where LEN(org_code) = ").append(localCodeLength);
		}else{
			sb.append(" where LENGTH(org_code) = ").append(localCodeLength);
		}

		if(oConvertUtils.isNotEmpty(parentCode)){
			sb.append(" and  org_code like '").append(parentCode).append("%'");
		} else {

			sb.append(" and LEFT(org_code,1)='A'");

		}

		sb.append(" ORDER BY org_code DESC");
		List<Map<String, Object>> objMapList = systemService.findForJdbc(sb.toString(), 1, 1);
		String returnCode = null;
		if(objMapList!=null && objMapList.size()>0){
			returnCode = (String)objMapList.get(0).get("org_code");
		}

		return returnCode;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="人员信息表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<HmStaffEntity>> list() {
		List<HmStaffEntity> listHmStaffs=hmStaffService.getList(HmStaffEntity.class);
		return Result.success(listHmStaffs);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取人员信息表信息",notes="根据ID获取人员信息表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		HmStaffEntity task = hmStaffService.get(HmStaffEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取人员信息表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建人员信息表")
	public ResponseMessage<?> create(@ApiParam(name="人员信息表对象")@RequestBody HmStaffEntity hmStaff, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<HmStaffEntity>> failures = validator.validate(hmStaff);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			hmStaffService.save(hmStaff);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("人员信息表信息保存失败");
		}
		return Result.success(hmStaff);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新人员信息表",notes="更新人员信息表")
	public ResponseMessage<?> update(@ApiParam(name="人员信息表对象")@RequestBody HmStaffEntity hmStaff) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<HmStaffEntity>> failures = validator.validate(hmStaff);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			hmStaffService.saveOrUpdate(hmStaff);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新人员信息表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新人员信息表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除人员信息表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			hmStaffService.deleteEntityById(HmStaffEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("人员信息表删除失败");
		}

		return Result.success();
	}
}
