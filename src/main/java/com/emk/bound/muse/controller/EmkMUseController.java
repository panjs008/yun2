package com.emk.bound.muse.controller;
import com.emk.bound.minstoragedetail.entity.EmkMInStorageDetailEntity;
import com.emk.bound.muse.entity.EmkMUseEntity;
import com.emk.bound.muse.service.EmkMUseServiceI;

import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.storage.storage.entity.EmkStorageEntity;
import com.emk.storage.storagelog.entity.EmkStorageLogEntity;
import com.emk.util.DateUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import com.emk.util.WebFileUtils;
import com.emk.util.excel.ExcelUtil;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
import org.hibernate.criterion.Restrictions;
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
 * @Description: 使用登记表
 * @author onlineGenerator
 * @date 2019-11-23 10:15:29
 * @version V1.0   
 *
 */
@Api(value="EmkMUse",description="使用登记表",tags="emkMUseController")
@Controller
@RequestMapping("/emkMUseController")
public class EmkMUseController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkMUseController.class);

	@Autowired
	private EmkMUseServiceI emkMUseService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 使用登记表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/bound/muse/emkMUseList");
	}

	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("useId"))) {
			List<EmkMInStorageDetailEntity> rkglMxEntities = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?", map.get("useId"));
			request.setAttribute("rkglMxList", rkglMxEntities);
		}
		return new ModelAndView("com/emk/bound/muse/orderMxList");
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
	public void datagrid(EmkMUseEntity emkMUse,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkMUseEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkMUse, request.getParameterMap());
		try{
			//自定义追加查询条件
			Map param = ParameterUtil.getParamMaps(request.getParameterMap());
			if(Utils.notEmpty(param.get("proNum"))){
				cq.add(Restrictions.sqlRestriction("(FIND_IN_SET('"+param.get("proNum")+"',(select GROUP_CONCAT(md.pro_num) from emk_m_in_storage_detail md where md.in_storage_id={alias}.id)) )"));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkMUseService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除使用登记表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkMUseEntity emkMUse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkMUse = systemService.getEntity(EmkMUseEntity.class, emkMUse.getId());
		message = "使用登记表删除成功";
		try{
			emkMUseService.delete(emkMUse);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "使用登记表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除使用登记表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "使用登记表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkMUseEntity emkMUse = systemService.getEntity(EmkMUseEntity.class, 
				id
				);
				if (!emkMUse.getState().equals("0")) {
					message = "存在已提交的使用登记表，无法删除！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
				systemService.executeSql("delete from emk_m_in_storage_detail where in_storage_id=?", id);
				emkMUseService.delete(emkMUse);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "使用登记表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加使用登记表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkMUseEntity emkMUse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "使用登记表添加成功";
		try{
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			String dataRows = map.get("orderMxListID");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				String proNumStr = ",";
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].proZnName"))) {
						if(proNumStr.contains(","+map.get("orderMxList["+i+"].proNum").toString()+",") && proNumStr.length()>8){
							message = "产品编号："+map.get("orderMxList["+i+"].proNum")+"，已存在";
							j.setMsg(message);
							j.setSuccess(false);
							return j;
						}
						proNumStr += map.get("orderMxList["+i+"].proNum")+",";
					}
				}
			}

			emkMUse.setState("0");
			emkMUse.setAppler(map.get("realName"));
			emkMUse.setApplerId(map.get("userName"));

			Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(ck_no, 6)),0)+1 AS signed) orderNum from emk_m_use");
			emkMUse.setCkNo("CK" + DateUtils.format(new Date(), "yyyy") + String.format("%06d", Integer.valueOf(orderNum.get("orderNum").toString())));
			Map dict = systemService.findOneForJdbc("select t2.typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid " +
					"where t1.typegroupcode='yymc' and t2.typecode=?  limit 0,1",emkMUse.getHospitalCode());
			emkMUse.setHospitalName(dict.get("typename").toString());
			emkMUseService.save(emkMUse);

			dataRows = map.get("orderMxListID");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				for (int i = 0; i < rows; i++) {
					EmkMInStorageDetailEntity emkMInStorageDetailEntity = new EmkMInStorageDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].proZnName"))) {
						/*emkMInStorageDetailEntity.setProZnName(map.get("orderMxList["+i+"].proZnName"));
						emkMInStorageDetailEntity.setProNum(map.get("orderMxList["+i+"].proNum"));
						emkMInStorageDetailEntity.setStandards(map.get("orderMxList["+i+"].standards"));
						emkMInStorageDetailEntity.setBrand(map.get("orderMxList["+i+"].brand"));
						emkMInStorageDetailEntity.setUnit(map.get("orderMxList["+i+"].unit"));

						emkMInStorageDetailEntity.setPrice(map.get("orderMxList["+i+"].cytj"));
						emkMInStorageDetailEntity.setInStorageId(emkMUse.getId());*/
						systemService.save(emkMInStorageDetailEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "使用登记表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新使用登记表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkMUseEntity emkMUse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "使用登记表更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		String dataRows = map.get("orderMxListID");
		if (Utils.notEmpty(dataRows)) {
			int rows = Integer.parseInt(dataRows);
			String proNumStr = ",";
			for (int i = 0; i < rows; i++) {
				if (Utils.notEmpty(map.get("orderMxList["+i+"].proZnName"))) {
					if(proNumStr.contains(","+map.get("orderMxList["+i+"].proNum").toString()+",") && proNumStr.length()>8){
						message = "产品编号："+map.get("orderMxList["+i+"].proNum")+"，已存在";
						j.setMsg(message);
						j.setSuccess(false);
						return j;
					}
					proNumStr += map.get("orderMxList["+i+"].proNum")+",";
				}
			}
		}
		EmkMUseEntity t = emkMUseService.get(EmkMUseEntity.class, map.get("emkMUseId"));
		try {

			if(!t.getState().equals("0")){
				j.setMsg("使用登记表已提交，无法进行修改");
				j.setSuccess(false);
				return j;
			}
			emkMUse.setAppler(map.get("realName"));
			emkMUse.setApplerId(map.get("userName"));
			Map dict = systemService.findOneForJdbc("select t2.typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid " +
					"where t1.typegroupcode='yymc' and t2.typecode=?  limit 0,1",emkMUse.getHospitalCode());
			emkMUse.setHospitalName(dict.get("typename").toString());
			emkMUse.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkMUse, t);
			emkMUseService.saveOrUpdate(t);

			dataRows = map.get("orderMxListID");
			if (Utils.notEmpty(dataRows)) {
				systemService.executeSql("delete from emk_m_in_storage_detail where in_storage_id = ? ",t.getId());
				int rows = Integer.parseInt(dataRows);
				for (int i = 0; i < rows; i++) {
					EmkMInStorageDetailEntity emkMInStorageDetailEntity = new EmkMInStorageDetailEntity();
					if (Utils.notEmpty(map.get("orderMxList["+i+"].proZnName"))) {
						/*emkMInStorageDetailEntity.setProZnName(map.get("orderMxList["+i+"].proZnName"));
						emkMInStorageDetailEntity.setProNum(map.get("orderMxList["+i+"].proNum"));
						emkMInStorageDetailEntity.setStandards(map.get("orderMxList["+i+"].standards"));
						emkMInStorageDetailEntity.setBrand(map.get("orderMxList["+i+"].brand"));
						emkMInStorageDetailEntity.setUnit(map.get("orderMxList["+i+"].unit"));

						emkMInStorageDetailEntity.setPrice(map.get("orderMxList["+i+"].cytj"));
						emkMInStorageDetailEntity.setInStorageId(t.getId());
						systemService.save(emkMInStorageDetailEntity);*/
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "使用登记表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 *保存客户session
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(params = "setCusSession")
	@ResponseBody
	public AjaxJson setCusSession(EmkMUseEntity emkMUse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "保存成功";
		try {
			Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
			request.getSession().setAttribute("cusNum",param.get("cusNum"));
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 提交使用登记表
	 *
	 * @return
	 */
	@RequestMapping(params = "doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "使用登记表提交成功";
		try{
			EmkMUseEntity t = emkMUseService.getEntity(EmkMUseEntity.class, ids);
			if (!t.getState().equals("0")) {
				message = "存在已提交的使用登记表，请重新选择在提交！";
				j.setSuccess(false);
				return j;
			}
			List<EmkMInStorageDetailEntity> inStorageDetailEntityList = systemService.findHql("from EmkCancelOrderDetailEntity where inStorageId=?",t.getId());
			for(EmkMInStorageDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
				/*EmkStorageEntity storageEntity = systemService.singleResult("from EmkStorageEntity where cusNum='"+t.getCusNum()+"' and proNum='"+inStorageDetailEntity.getProNum()+"'" +
												" and brand='"+inStorageDetailEntity.getBrand()+"' ");
				if(Utils.isEmpty(storageEntity)){
					message = "该客户不存在产品名称："+inStorageDetailEntity.getProZnName()+"，产品编号："+inStorageDetailEntity.getProNum()+"，型号："+inStorageDetailEntity.getBrand()+"，无法出库操作";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}*/
			}
			EmkStorageLogEntity storageLogEntity = null;
			for(EmkMInStorageDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
				/*storageLogEntity = new EmkStorageLogEntity();
				EmkStorageEntity storageEntity = systemService.singleResult("from EmkStorageEntity where cusNum='"+t.getCusNum()+"' and proNum='"+inStorageDetailEntity.getProNum()+"'" +
						" and brand='"+inStorageDetailEntity.getBrand()+"' ");

				MyBeanUtils.copyBeanNotNull2Bean(inStorageDetailEntity,storageLogEntity);
				storageLogEntity.setId(null);
				storageLogEntity.setRkNo(t.getCkNo());
				storageLogEntity.setType("4");

				systemService.delete(storageEntity);*/
				systemService.save(storageLogEntity);

			}
			t.setState("1");
			systemService.saveOrUpdate(t);
		}catch(Exception e){
			e.printStackTrace();
			message = "使用登记表提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 使用登记表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkMUseEntity emkMUse, HttpServletRequest req) {
		req.getSession().setAttribute("cusNum","");
		if (StringUtil.isNotEmpty(emkMUse.getId())) {
			emkMUse = emkMUseService.getEntity(EmkMUseEntity.class, emkMUse.getId());
			req.setAttribute("emkMUsePage", emkMUse);
		}
		return new ModelAndView("com/emk/bound/muse/emkMUse-add");
	}
	/**
	 * 使用登记表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkMUseEntity emkMUse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkMUse.getId())) {
			emkMUse = emkMUseService.getEntity(EmkMUseEntity.class, emkMUse.getId());
			req.setAttribute("emkMUsePage", emkMUse);
			req.getSession().setAttribute("cusNum",emkMUse.getCusNum());
		}
		return new ModelAndView("com/emk/bound/muse/emkMUse-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkMUseController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkMUseEntity emkMUse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		try {
			Map param = ParameterUtil.getParamMaps(request.getParameterMap());
			CriteriaQuery cq = new CriteriaQuery(EmkMUseEntity.class, dataGrid);
			//cq.eq("type","2");
			//cq.addOrder("outDate", SortDirection.desc);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkMUse, request.getParameterMap());
			if(Utils.notEmpty(emkMUse.getId())){
				cq.add(Restrictions.sqlRestriction("(FIND_IN_SET({alias}.id,'"+emkMUse.getId()+"'))"));
			}
			List<EmkMUseEntity> emkMUseEntities = systemService.getListByCriteriaQuery(cq,false);
			String savepath = "c:\\使用登记表\\";
			File file = new File(savepath);
			if(!file.exists())
			{
				file.mkdirs();
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			String path = savepath+"使用登记表"+ DateUtil.getCurrentTimeString("yyyyMMdd")+".xls";
			for(EmkMUseEntity emkMUseEntity : emkMUseEntities){
				List<String> headList = new ArrayList<String>();
				List<String> fieldList = new ArrayList<String>();

				headList.add("产品名称");
				fieldList.add("pro_zn_name");
				headList.add("规格/型号");
				fieldList.add("brand");
				headList.add("单位");
				fieldList.add("unit");
				headList.add("数量");
				fieldList.add("total");
				headList.add("单价（元）");
				fieldList.add("price");
				headList.add("金额（元）");
				fieldList.add("money");
				headList.add("生产企业/许可证号/储运条件");
				fieldList.add("scqy");
				headList.add("注册证号");
				fieldList.add("zc_num");
				headList.add("产品编号");
				fieldList.add("pro_num");
				headList.add("生产日期");
				fieldList.add("sc_date");
				headList.add("有效日期");
				fieldList.add("limit_date");
				headList.add("生产批号");
				fieldList.add("betch");
				headList.add("产品归属");
				fieldList.add("cpgs");
				headList.add("");
				fieldList.add("hospitalName");

				String sql = "select t.pro_zn_name,t.pro_num,CONCAT(t.standards,'/',t.brand) brand,t.betch,t.sc_date,t.limit_date,1 total,t.unit,t.price,t.price money,t.zc_num," +
						"	CONCAT(t.scqy,'/',t.lincese,'/',t.cytj) scqy,'' cpgs,'"+emkMUseEntity.getOperationDate()+","+
							emkMUseEntity.getHospitalName()+","+emkMUseEntity.getPatient()+"' hospitalName from  emk_m_in_storage_detail t \n" +
						"	where t.in_storage_id=? ";
				List<Map<String,Object>> rkglMxEntities = systemService.findForJdbc(sql.toString(),emkMUseEntity.getId());

				sql = "select ifnull(count(t2.pro_num),0) total,FORMAT(ifnull(sum(t2.price),0),2) hj from emk_m_use t \n" +
						"	LEFT JOIN emk_m_in_storage_detail t2 on t2.in_storage_id=t.id where t.id=? ";
				Map hj = systemService.findOneForJdbc(sql,emkMUseEntity.getId());
				ExcelUtil.createUseExcel(workbook,path,emkMUseEntity,headList,fieldList, rkglMxEntities,hj);
			}
			WebFileUtils.downLoad(path, response, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkMUseEntity emkMUse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"使用登记表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkMUseEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("使用登记表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkMUseEntity> listEmkMUseEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkMUseEntity.class,params);
				for (EmkMUseEntity emkMUse : listEmkMUseEntitys) {
					emkMUseService.save(emkMUse);
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
	@ApiOperation(value="使用登记表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkMUseEntity>> list() {
		List<EmkMUseEntity> listEmkMUses=emkMUseService.getList(EmkMUseEntity.class);
		return Result.success(listEmkMUses);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取使用登记表信息",notes="根据ID获取使用登记表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkMUseEntity task = emkMUseService.get(EmkMUseEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取使用登记表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建使用登记表")
	public ResponseMessage<?> create(@ApiParam(name="使用登记表对象")@RequestBody EmkMUseEntity emkMUse, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkMUseEntity>> failures = validator.validate(emkMUse);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkMUseService.save(emkMUse);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("使用登记表信息保存失败");
		}
		return Result.success(emkMUse);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新使用登记表",notes="更新使用登记表")
	public ResponseMessage<?> update(@ApiParam(name="使用登记表对象")@RequestBody EmkMUseEntity emkMUse) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkMUseEntity>> failures = validator.validate(emkMUse);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkMUseService.saveOrUpdate(emkMUse);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新使用登记表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新使用登记表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除使用登记表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkMUseService.deleteEntityById(EmkMUseEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("使用登记表删除失败");
		}

		return Result.success();
	}
}
