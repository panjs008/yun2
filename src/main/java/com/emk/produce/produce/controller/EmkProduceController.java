package com.emk.produce.produce.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.bill.contract.entity.EmkContractEntity;
import com.emk.bill.proorder.entity.EmkProOrderEntity;
import com.emk.bill.proorderbox.entity.EmkProOrderBoxEntity;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.caiwu.financereceivable.entity.EmkFinanceReceivableEntity;
import com.emk.check.check.entity.EmkCheckEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.outforum.tdhdcost.entity.EmkTdhdCostDetailEntity;
import com.emk.produce.cargospace.entity.EmkCargoSpaceEntity;
import com.emk.produce.invoices.entity.EmkInvoicesDetailEntity;
import com.emk.produce.leavefactory.entity.EmkLeaveFactoryEntity;
import com.emk.produce.outforum.entity.EmkOutForumEntity;
import com.emk.produce.produce.entity.EmkProduceDetailEntity;
import com.emk.produce.produce.entity.EmkProduceEntity;
import com.emk.produce.produce.service.EmkProduceServiceI;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.produce.ssysycy.entity.EmkSsysycyDetailEntity;
import com.emk.produce.test.entity.EmkTestEntity;
import com.emk.product.product.entity.EmkProductEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.util.ApprovalUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
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
 * @Description: 采购生产进度管理
 * @author onlineGenerator
 * @date 2018-10-31 14:24:06
 * @version V1.0   
 *
 */
@Api(value="EmkProduce",description="采购生产进度管理",tags="emkProduceController")
@Controller
@RequestMapping("/emkProduceController")
public class EmkProduceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkProduceController.class);

	@Autowired
	private EmkProduceServiceI emkProduceService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	

	/**
	 * 采购生产进度管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/produce/produce/emkProduceList");
	}

	@RequestMapping(params = "detailMxList")
	public ModelAndView detailMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(remark,',',typecode) typecode,typecode code,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
		String color = JSONHelper.collection2json(list);
		request.setAttribute("color", "'"+color+ "'");
		/*List<Map<String, Object>> list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
		request.setAttribute("colorList", list);*/
		list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='gylx'");
		request.setAttribute("gylxList", list);
		list = systemService.findForJdbc("select company_code gys from emk_factory_archives t2 where state=2 order by company_code asc ");
		request.setAttribute("gysList", list);
		list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='size'");
		request.setAttribute("sizeList", list);
		if (Utils.notEmpty(map.get("pId"))) {
			List<EmkProduceDetailEntity> emkProduceDetailEntities = systemService.findHql("from EmkProduceDetailEntity where pId=?", map.get("pId"));
			request.setAttribute("emkProduceDetailEntities", emkProduceDetailEntities);

			EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("pId"));
			request.setAttribute("emkSizePage", emkSizeEntity);
		}
		return new ModelAndView("com/emk/produce/produce/detailMxList");
	}


	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		request.setAttribute("categoryEntityList", codeList);
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if ((map.get("proOrderId") != null) && (!map.get("proOrderId").equals(""))) {
			List<EmkEnquiryDetailEntity> emkProOrderDetailEntities = this.systemService.findHql("from EmkEnquiryDetailEntity where enquiryId=?", map.get("proOrderId"));
			request.setAttribute("emkProOrderDetailEntities", emkProOrderDetailEntities);
		}
		return new ModelAndView("com/emk/produce/produce/orderMxList");
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
	public void datagrid(EmkProduceEntity emkProduce,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkProduceEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkProduce, request.getParameterMap());
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
		this.emkProduceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除采购生产进度管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkProduceEntity emkProduce, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkProduce = systemService.getEntity(EmkProduceEntity.class, emkProduce.getId());
		message = "采购生产进度管理删除成功";
		try{
			emkProduceService.delete(emkProduce);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "采购生产进度管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除采购生产进度管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购生产进度管理删除成功";
		try{
			for(String id:ids.split(",")){
				EmkProduceEntity emkProduce = systemService.getEntity(EmkProduceEntity.class, 
				id
				);
				emkProduceService.delete(emkProduce);
				systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_produce_detail where p_id=?))", emkProduce.getId());
				systemService.executeSql("delete from emk_produce_detail where p_id = ? ",emkProduce.getId());
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "采购生产进度管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加采购生产进度管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkProduceEntity emkProduce,EmkSizeEntity emkSizeEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购生产进度管理添加成功";
		try{
			emkProduce.setState("0");
			emkProduceService.save(emkProduce);
			emkSizeEntity.setFormId(emkProduce.getId());
			systemService.saveOrUpdate(emkSizeEntity);
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			saveProduce(emkProduce,map);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "采购生产进度管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新采购生产进度管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkProduceEntity emkProduce,EmkSizeEntity emkSize, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购生产进度管理更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		EmkProduceEntity t = emkProduceService.get(EmkProduceEntity.class, map.get("produceId"));
		try {
			emkProduce.setState("0");
			emkProduce.setId(null);
			MyBeanUtils.copyBeanNotNull2Bean(emkProduce, t);
			emkProduceService.saveOrUpdate(t);

			EmkSizeEntity t2 = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",t.getId());
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
			saveProduce(t,map);
			if("1".equals(map.get("isPass"))) {
				List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
				Task task1 = (Task)task.get(task.size() - 1);
				TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
				Map<String, Object> variables = new HashMap();
				EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
				EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(),user,b,"重新提交采购生产进度表");

				if (task1.getTaskDefinitionKey().equals("cgscjdbTask")) {
					taskService.complete(task1.getId(), variables);
					t.setState("1");
					b.setStatus(1);
					saveApprvoalDetail(approvalDetail,"重新提交的采购生产进度表","cgscjdbTask",0,"重新提交采购生产进度表");
					systemService.saveOrUpdate(approvalDetail);
					saveSmsAndEmailForMany("业务跟单员","重新提交的采购生产进度表","您有【"+b.getCreateName()+"】重新提交的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
				}
				if (task1.getTaskDefinitionKey().equals("cgTask")) {
					taskService.complete(task1.getId(), variables);
					t.setState("70");
					b.setStatus(70);
					saveApprvoalDetail(approvalDetail,"采购","cgTask",0,"完成采购");
					systemService.saveOrUpdate(approvalDetail);
					saveSmsAndEmailForMany("业务跟单员","采购","您有【"+b.getCreateName()+"】完成采购的生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
				}
				if (task1.getTaskDefinitionKey().equals("ylblTask")) {
					taskService.complete(task1.getId(), variables);
					t.setState("81");
					b.setStatus(81);
					saveApprvoalDetail(approvalDetail,"原料布料","cgTask",0,"完成原料布料");
					systemService.saveOrUpdate(approvalDetail);
					saveSmsAndEmailForMany("染色员","原料布料","您有【"+b.getCreateName()+"】完成原料布料的生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
				}
				if (task1.getTaskDefinitionKey().equals("ranTask")) {
					taskService.complete(task1.getId(), variables);
					t.setState("72");
					b.setStatus(72);
					saveApprvoalDetail(approvalDetail,"染色","ranTask",0,"完成染色");
					systemService.saveOrUpdate(approvalDetail);
					saveSmsAndEmailForMany("裁剪员","染色","您有【"+b.getCreateName()+"】完成染色的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
				}
				if (task1.getTaskDefinitionKey().equals("caiTask")) {
					EmkTestEntity emkTest = new EmkTestEntity();
					List<EmkProduceDetailEntity> produceDetailEntityList = systemService.findHql("from EmkProduceDetailEntity where produceHtNum=?",t.getProductHtNum());
					if(Utils.notEmpty(produceDetailEntityList)){
						MyBeanUtils.copyBeanNotNull2Bean(produceDetailEntityList.get(0),emkTest);
						emkTest.setState("0");
						Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(cssqdh, 3)),0)+1 AS signed) orderNum from emk_test");
						emkTest.setCssqdh("CS" + emkTest.getCusNum() + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));
						emkTest.setProduceNum(t.getProductHtNum());
						emkTest.setId(null);
						emkTest.setProduceId(t.getId());
						systemService.save(emkTest);
					}

					taskService.complete(task1.getId(), variables);
					t.setState("73");
					b.setStatus(73);
					saveApprvoalDetail(approvalDetail,"裁剪","ranTask",0,"完成裁剪");
					systemService.saveOrUpdate(approvalDetail);
					saveSmsAndEmailForMany("测试员","裁剪","您有【"+b.getCreateName()+"】完成裁剪的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
				}
				if (task1.getTaskDefinitionKey().equals("fengTask")) {
					taskService.complete(task1.getId(), variables);
					t.setState("74");
					b.setStatus(74);
					saveApprvoalDetail(approvalDetail,"缝制","ranTask",0,"完成缝制");
					systemService.saveOrUpdate(approvalDetail);
					saveSmsAndEmailForMany("包装员","缝制","您有【"+b.getCreateName()+"】完成缝制的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
				}
				if (task1.getTaskDefinitionKey().equals("bzTask")) {
					taskService.complete(task1.getId(), variables);
					t.setState("75");
					b.setStatus(75);
					saveApprvoalDetail(approvalDetail,"包装","bzTask",0,"完成包装");
					systemService.saveOrUpdate(approvalDetail);
					saveSmsAndEmailForMany("船样员","包装","您有【"+b.getCreateName()+"】完成包装的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "采购生产进度管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 采购生产进度管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkProduceEntity emkProduce, HttpServletRequest req) {
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		req.setAttribute("categoryEntityList", codeList);
		if (StringUtil.isNotEmpty(emkProduce.getId())) {
			emkProduce = emkProduceService.getEntity(EmkProduceEntity.class, emkProduce.getId());
			req.setAttribute("emkProducePage", emkProduce);
		}
		return new ModelAndView("com/emk/produce/produce/emkProduce-add");
	}
	/**
	 * 采购生产进度管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkProduceEntity emkProduce, HttpServletRequest req) {
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		req.setAttribute("categoryEntityList", codeList);
		if (StringUtil.isNotEmpty(emkProduce.getId())) {
			emkProduce = emkProduceService.getEntity(EmkProduceEntity.class, emkProduce.getId());
			req.setAttribute("emkProducePage", emkProduce);
		}
		return new ModelAndView("com/emk/produce/produce/emkProduce-update");
	}

	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkProduceEntity emkProduce, HttpServletRequest req) {
		List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
		req.setAttribute("categoryEntityList", codeList);
		if (StringUtil.isNotEmpty(emkProduce.getId())) {
			emkProduce = emkProduceService.getEntity(EmkProduceEntity.class, emkProduce.getId());
			req.setAttribute("emkProducePage", emkProduce);
		}
		List<TSUser> userList = systemService.findHql("from TSUser where userKey='业务员'");
		req.setAttribute("ywyList", userList);
		userList = systemService.findHql("from TSUser where userKey='业务跟单员'");
		req.setAttribute("ywgdyList", userList);
		userList = systemService.findHql("from TSUser where userKey='生产跟单员'");
		req.setAttribute("scgdyList", userList);
		return new ModelAndView("com/emk/produce/produce/emkProduce-update2");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkProduceController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkProduceEntity emkProduce,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkProduceEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkProduce, request.getParameterMap());
		List<EmkProduceEntity> emkProduces = this.emkProduceService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"采购生产进度管理");
		modelMap.put(NormalExcelConstants.CLASS,EmkProduceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("采购生产进度管理列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkProduces);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkProduceEntity emkProduce,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"采购生产进度管理");
    	modelMap.put(NormalExcelConstants.CLASS,EmkProduceEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("采购生产进度管理列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkProduceEntity> listEmkProduceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkProduceEntity.class,params);
				for (EmkProduceEntity emkProduce : listEmkProduceEntitys) {
					emkProduceService.save(emkProduce);
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
	@ApiOperation(value="采购生产进度管理列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkProduceEntity>> list() {
		List<EmkProduceEntity> listEmkProduces=emkProduceService.getList(EmkProduceEntity.class);
		return Result.success(listEmkProduces);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取采购生产进度管理信息",notes="根据ID获取采购生产进度管理信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkProduceEntity task = emkProduceService.get(EmkProduceEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取采购生产进度管理信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建采购生产进度管理")
	public ResponseMessage<?> create(@ApiParam(name="采购生产进度管理对象")@RequestBody EmkProduceEntity emkProduce, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkProduceEntity>> failures = validator.validate(emkProduce);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkProduceService.save(emkProduce);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("采购生产进度管理信息保存失败");
		}
		return Result.success(emkProduce);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新采购生产进度管理",notes="更新采购生产进度管理")
	public ResponseMessage<?> update(@ApiParam(name="采购生产进度管理对象")@RequestBody EmkProduceEntity emkProduce) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkProduceEntity>> failures = validator.validate(emkProduce);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkProduceService.saveOrUpdate(emkProduce);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新采购生产进度管理信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新采购生产进度管理信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除采购生产进度管理")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkProduceService.deleteEntityById(EmkProduceEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("采购生产进度管理删除失败");
		}

		return Result.success();
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkProduceEntity emkProduce, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "采购生产进度表提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			if (Utils.isEmpty(emkProduce.getId())) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkProduceEntity emkProduceEntity = systemService.getEntity(EmkProduceEntity.class, id);
					if (!emkProduceEntity.getState().equals("0")) {
						message = "存在已提交的采购生产进度表，请重新选择在提交采购生产进度表！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkProduce.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").toString().split(",")) {
					EmkProduceEntity t = emkProduceService.get(EmkProduceEntity.class, id);
					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
					if(Utils.isEmpty(b)){
						//type 工单类型，workNum 单号，formId 对应表单ID，bpmName 节点名称，bpmNode 节点代码，advice 处理意见，user 用户对象
						b = new EmkApprovalEntity();
						EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
						ApprovalUtil.saveApproval(b,19,t.getProductHtNum(),t.getId(),user);
						systemService.save(b);
						ApprovalUtil.saveApprovalDetail(approvalDetailEntity,b.getId(),"【生产跟单员】采购生产进度表","cgscjdbTask","提交",user);
						systemService.save(approvalDetailEntity);
					}
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
						if (task1.getTaskDefinitionKey().equals("cgscjdbTask")) {
							saveProduce(t,map);
							if("1".equals(map.get("isPass"))){
								taskService.complete(task1.getId(), variables);
								t.setState("1");
								b.setStatus(1);
								saveApprvoalDetail(approvalDetail,"重新提交的采购生产进度表","cgscjdbTask",0,"重新提交采购生产进度表");
								saveSmsAndEmailForMany("业务跟单员","重新提交的采购生产进度表","您有【"+b.getCreateName()+"】重新提交的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("ywgdfhTask")) {
							if (map.get("isPass").equals("0")) {
								variables.put("isPass","0");
								taskService.complete(task1.getId(), variables);
								b.setStatus(61);
								approvalDetail.setBpmName("业务跟单复核");
								t.setState("61");
								approvalDetail.setApproveStatus(0);

								saveSmsAndEmailForMany("采购员","业务跟单复核","您有【"+b.getCreateName()+"】审核通过的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"业务跟单复核","ywgdfhTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"ywgdfhTask","cgscjdbTask","【生产跟单员】采购生产进度表");
								t.setState("0");
								b.setStatus(0);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("业务跟单复核","您有【"+user.getRealName()+"】回退的采购生产进度表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("ylblTask")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(71);
								approvalDetail.setBpmName("原料布料");
								t.setState("71");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("染色员","原料布料","您有【"+b.getCreateName()+"】完成原料布料的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("csTask")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(71);
								approvalDetail.setBpmName("测试");
								t.setState("71");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("缝制员","测试","您有【"+b.getCreateName()+"】完成测试的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("cyTask")) {
							if (map.get("isPass").equals("0")) {
								Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(CARGO_NO, 3)),0)+1 AS signed) orderNum from emk_cargo_space");
								EmkCargoSpaceEntity emkCargoSpace = new EmkCargoSpaceEntity();
								emkCargoSpace.setCargoNo("DC" + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));
								emkCargoSpace.setProduceId(t.getId());
								emkCargoSpace.setState("0");
								systemService.save(emkCargoSpace);

								EmkProduceEntity emkProduceEntity = systemService.findUniqueByProperty(EmkProduceEntity.class,"productHtNum",t.getProductHtNum());
								EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",emkProduceEntity.getId());
								EmkSizeEntity t1 = new EmkSizeEntity();
								MyBeanUtils.copyBeanNotNull2Bean(emkSizeEntity,t1);
								t1.setId(null);
								t1.setFormId(emkCargoSpace.getId());
								systemService.save(t1);

								EmkProOrderBoxEntity emkProOrderBoxEntity = null;

								EmkProOrderDetailEntity emkProOrderDetailEntity = null;
								EmkSizeTotalEntity emkSizeTotalEntity = null;
								List<EmkProduceDetailEntity> emkProduceDetailEntities = systemService.findHql("from EmkProduceDetailEntity where produceHtNum=?", t.getProductHtNum());
								int i = 0;
								EmkProOrderBoxEntity proOrderBoxEntity = null;
								for (EmkProduceDetailEntity produceDetailEntity : emkProduceDetailEntities) {
									if(i == 0){
										List<EmkProOrderEntity> emkProOrderEntityList = systemService.findHql("from EmkProOrderEntity where orderNo=?",produceDetailEntity.getOrderNo());
										List<EmkProOrderBoxEntity> emkProOrderBoxEntityList = systemService.findHql("from EmkProOrderBoxEntity where orderId=?",emkProOrderEntityList.get(0).getId());
										proOrderBoxEntity = emkProOrderBoxEntityList.get(0);
									}
									emkProOrderBoxEntity = new EmkProOrderBoxEntity();
									MyBeanUtils.copyBeanNotNull2Bean(proOrderBoxEntity, emkProOrderBoxEntity);
									MyBeanUtils.copyBeanNotNull2Bean(produceDetailEntity, emkProOrderBoxEntity);
									emkProOrderBoxEntity.setId(null);
									emkProOrderBoxEntity.setOrderId(emkCargoSpace.getId());
									emkProOrderBoxEntity.setBoxType("6");
									systemService.save(emkProOrderBoxEntity);

									emkSizeTotalEntity = new EmkSizeTotalEntity();
									MyBeanUtils.copyBeanNotNull2Bean(produceDetailEntity.getEmkSizeTotalEntity(), emkSizeTotalEntity);
									emkSizeTotalEntity.setId(null);
									emkSizeTotalEntity.setpId(emkProOrderBoxEntity.getId());
									systemService.save(emkSizeTotalEntity);
									i++;
								}

								taskService.complete(task1.getId(), variables);
								b.setStatus(76);
								approvalDetail.setBpmName("船样");
								t.setState("76");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("生产跟单员","船样","您有【"+b.getCreateName()+"】完成船样的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("ywgdshTask")) {
							if (map.get("isPass").equals("0")) {
								if(t.getState().equals("77")){
									variables.put("isPass","0");
								}
								if(t.getState().equals("82") || t.getState().equals("64")){
									variables.put("isPass","1");
								}
								taskService.complete(task1.getId(), variables);
								b.setStatus(79);
								approvalDetail.setBpmName("业务跟单审核");
								t.setState("79");
								approvalDetail.setApproveStatus(0);

								saveSmsAndEmailForMany("船务员","业务跟单审核","您有【"+b.getCreateName()+"】审核通过的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"业务跟单审核","ywgdshTask",1,map.get("advice"));
								if(t.getState().equals("77")){
									backProcess(task1.getProcessInstanceId(),"ywgdshTask","dctzdTask","业务跟单审核");
									t.setState("78");
									b.setStatus(78);
									b.setBpmStatus("1");
									EmkCargoSpaceEntity cargoSpaceEntity = systemService.findUniqueByProperty(EmkCargoSpaceEntity.class,"produceId",t.getId());
									cargoSpaceEntity.setState("0");
									saveSmsAndEmailForOne("业务跟单审核","您有【"+user.getRealName()+"】回退的订舱通知单，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
								}
								if(t.getState().equals("82")){
									backProcess(task1.getProcessInstanceId(),"ywgdshTask","cktzdTask","业务跟单审核");
									t.setState("83");
									b.setStatus(83);
									b.setBpmStatus("1");
									EmkOutForumEntity outForumEntity = systemService.findUniqueByProperty(EmkOutForumEntity.class,"produceId",t.getId());
									outForumEntity.setState("0");
									saveSmsAndEmailForOne("业务跟单审核","您有【"+user.getRealName()+"】回退的出口通知单，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
								}
							}
						}
						if (task1.getTaskDefinitionKey().equals("cwyTask")) {
							if (map.get("isPass").equals("0")){
								EmkCheckEntity emkCheck = new EmkCheckEntity();
								Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(check_num, 3)),0)+1 AS signed) orderNum from emk_check");
								emkCheck.setCheckNum("YHBH"  + DateUtils.format(new Date(), "yyMMdd") + String.format("%06d", orderNum.get("orderNum").toString()));
								emkCheck.setProduceId(t.getId());
								systemService.save(emkCheck);

								EmkProduceEntity emkProduceEntity = systemService.findUniqueByProperty(EmkProduceEntity.class,"productHtNum",t.getProductHtNum());
								EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",emkProduceEntity.getId());
								EmkSizeEntity t1 = new EmkSizeEntity();
								MyBeanUtils.copyBeanNotNull2Bean(emkSizeEntity,t1);
								t1.setId(null);
								t1.setFormId(emkCheck.getId());
								systemService.save(t1);

								EmkInvoicesDetailEntity emkInvoicesDetailEntity = null;
								EmkSizeTotalEntity emkSizeTotalEntity = null;
								List<EmkProduceDetailEntity> emkProduceDetailEntities = systemService.findHql("from EmkProduceDetailEntity where produceHtNum=?", t.getProductHtNum());
								for (EmkProduceDetailEntity produceDetailEntity : emkProduceDetailEntities) {
									emkInvoicesDetailEntity = new EmkInvoicesDetailEntity();
									MyBeanUtils.copyBeanNotNull2Bean(produceDetailEntity, emkInvoicesDetailEntity);
									emkInvoicesDetailEntity.setId(null);
									emkInvoicesDetailEntity.setInvoicesId(emkCheck.getId());
									systemService.save(emkInvoicesDetailEntity);

									emkSizeTotalEntity = new EmkSizeTotalEntity();
									MyBeanUtils.copyBeanNotNull2Bean(produceDetailEntity.getEmkSizeTotalEntity(), emkSizeTotalEntity);
									emkSizeTotalEntity.setId(null);
									emkSizeTotalEntity.setpId(emkInvoicesDetailEntity.getId());
									systemService.save(emkSizeTotalEntity);
								}

								taskService.complete(task1.getId(), variables);
								b.setStatus(80);
								approvalDetail.setBpmName("【船务员】执行订舱");
								t.setState("80");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("生产跟单员","【船务员】执行订舱","您有【"+b.getCreateName()+"】完成的执行订舱，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

							}
						}
						if (task1.getTaskDefinitionKey().equals("ywjlzfhTask")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(3);
								approvalDetail.setBpmName("业务经理复核");
								t.setState("3");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("财务员","业务经理复核","您有【"+b.getCreateName()+"】审核通过的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"业务经理复核","ywjlzfhTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"ywjlzfhTask","ywgdshTask","【生产跟单员】采购生产进度表");
								systemService.executeSql("delete from act_hi_actinst  where proc_inst_id_=? and act_id_=? ",task1.getProcessInstanceId(),"isPass");

								t.setState("64");
								b.setStatus(64);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("业务经理复核","您有【"+user.getRealName()+"】回退的采购生产进度表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("cwzfhTask")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(25);
								approvalDetail.setBpmName("财务再复核");
								t.setState("25");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("财务员","财务再复核","您有【"+b.getCreateName()+"】审核通过的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

							}else{
								saveApprvoalDetail(approvalDetail,"财务再复核","cwzfhTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"cwzfhTask","ywjlzfhTask","【生产跟单员】采购生产进度表");
								t.setState("4");
								b.setStatus(4);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("财务再复核","您有【"+user.getRealName()+"】回退的采购生产进度表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("zjlpzTask")) {
							if (map.get("isPass").equals("0")) {
								EmkCargoSpaceEntity emkCargoSpaceEntity = systemService.findUniqueByProperty(EmkCargoSpaceEntity.class,"produceId",t.getId());
								EmkOutForumEntity emkOutForumEntity = systemService.findUniqueByProperty(EmkOutForumEntity.class,"produceId",t.getId());

								EmkLeaveFactoryEntity emkLeaveFactoryEntity = new EmkLeaveFactoryEntity();
								Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(LEAVE_FACTORY_NO, 3)),0)+1 AS signed) orderNum from emk_leave_factory");
								emkLeaveFactoryEntity.setLeaveFactoryNo("L" + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));
								emkLeaveFactoryEntity.setState("0");
								emkLeaveFactoryEntity.setCargoNo(emkCargoSpaceEntity.getCargoNo());
								emkLeaveFactoryEntity.setOutForumNo(emkOutForumEntity.getOutForumNo());
								emkLeaveFactoryEntity.setCwyer(emkCargoSpaceEntity.getCwyer());
								emkLeaveFactoryEntity.setProduceId(t.getId());
								systemService.save(emkLeaveFactoryEntity);

								emkCargoSpaceEntity.setLevealFactoryNo(emkLeaveFactoryEntity.getLeaveFactoryNo());
								emkOutForumEntity.setLevealFactoryNo(emkLeaveFactoryEntity.getLeaveFactoryNo());

							/*	EmkProOrderDetailEntity proOrderDetailEntity = null;
								List<EmkProOrderBoxEntity> emkProOrderBoxEntities = systemService.findHql("from EmkProOrderBoxEntity where orderId=? and boxType=6", emkCargoSpaceEntity.getId());
								for (EmkProOrderBoxEntity proOrderBoxEntity : emkProOrderBoxEntities) {
									proOrderDetailEntity = new EmkProOrderDetailEntity();
									MyBeanUtils.copyBeanNotNull2Bean(proOrderBoxEntity, proOrderDetailEntity);
									proOrderDetailEntity.setId(null);
									proOrderDetailEntity.setProOrderId(emkLeaveFactoryEntity.getId());
									if(Utils.notEmpty(proOrderBoxEntity.getSumWeightMao())){
										proOrderDetailEntity.setSumMao(proOrderBoxEntity.getSumWeightMao().toString());
									}
									if(Utils.notEmpty(proOrderBoxEntity.getSumVolume())){
										proOrderDetailEntity.setSumVol(proOrderBoxEntity.getSumVolume().toString());
									}
									if(Utils.notEmpty(proOrderBoxEntity.getSumWeightJz())){
										proOrderDetailEntity.setSumJz(proOrderBoxEntity.getSumWeightJz().toString());
									}
									systemService.save(proOrderDetailEntity);
								}*/

								taskService.complete(task1.getId(), variables);
								b.setStatus(48);
								approvalDetail.setBpmName("总经理批准");
								t.setState("48");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("财务员","总经理批准","您有【"+b.getCreateName()+"】审核通过的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}else{
								saveApprvoalDetail(approvalDetail,"总经理批准","zjlpzTask",1,map.get("advice"));
								backProcess(task1.getProcessInstanceId(),"zjlpzTask","cwzfhTask","【生产跟单员】采购生产进度表");
								t.setState("27");
								b.setStatus(27);
								b.setBpmStatus("1");
								saveSmsAndEmailForOne("总经理批准","您有【"+user.getRealName()+"】回退的采购生产进度表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("cwzfhTask2")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(86);
								approvalDetail.setBpmName("财务再复核");
								t.setState("86");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("仓库员","财务再复核","您有【"+b.getCreateName()+"】仓库装货放行的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("ckzhfxTask")) {
							if (map.get("isPass").equals("0")) {
								taskService.complete(task1.getId(), variables);
								b.setStatus(85);
								approvalDetail.setBpmName("仓库装货放行");
								t.setState("85");
								approvalDetail.setApproveStatus(0);
								saveSmsAndEmailForMany("生产跟单员","仓库装货放行","您有【"+b.getCreateName()+"】仓库装货放行的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							}
						}
						if (task1.getTaskDefinitionKey().equals("ysyfTask")) {
							if (map.get("isPass").equals("0")) {
								EmkCheckEntity emkCheckEntity = systemService.findUniqueByProperty(EmkCheckEntity.class,"produceId",t.getId());
								EmkCargoSpaceEntity emkCargoSpaceEntity = systemService.findUniqueByProperty(EmkCargoSpaceEntity.class,"produceId",t.getId());
								EmkOutForumEntity emkOutForumEntity = systemService.findUniqueByProperty(EmkOutForumEntity.class,"produceId",t.getId());
								EmkLeaveFactoryEntity emkLeaveFactoryEntity = systemService.findUniqueByProperty(EmkLeaveFactoryEntity.class,"produceId",t.getId());

								EmkFinanceReceivableEntity emkFinanceReceivableEntity = new EmkFinanceReceivableEntity();
								Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(receive_num, 3)),0)+1 AS signed) orderNum from emk_finance_receivable");
								emkFinanceReceivableEntity.setReceiveNum("YF"  + DateUtils.format(new Date(), "yyMMdd") + String.format("%06d", orderNum.get("orderNum").toString()));
								MyBeanUtils.copyBeanNotNull2Bean(emkCheckEntity,emkFinanceReceivableEntity);
								emkFinanceReceivableEntity.setCargoNo(emkCargoSpaceEntity.getCargoNo());
								emkFinanceReceivableEntity.setOutFourmNo(emkOutForumEntity.getOutForumNo());
								emkFinanceReceivableEntity.setLevealFactoryNo(emkLeaveFactoryEntity.getLeaveFactoryNo());
								emkFinanceReceivableEntity.setProduceHtNum(t.getProductHtNum());
								emkFinanceReceivableEntity.setLevealDate(emkLeaveFactoryEntity.getLevalDate());
								emkFinanceReceivableEntity.setCwer(emkCargoSpaceEntity.getCwyer());
								emkFinanceReceivableEntity.setType("1");
								systemService.save(emkFinanceReceivableEntity);

								EmkSizeEntity emkSizeEntity = new EmkSizeEntity();
								EmkSizeEntity sizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",emkCheckEntity.getId());
								MyBeanUtils.copyBeanNotNull2Bean(sizeEntity,emkSizeEntity);
								emkSizeEntity.setId(null);
								emkSizeEntity.setFormId(emkFinanceReceivableEntity.getId());
								systemService.save(emkSizeEntity);

								List<EmkInvoicesDetailEntity> invoicesDetailEntityList = systemService.findHql("from EmkInvoicesDetailEntity where invoicesId=?",emkCheckEntity.getId());
								EmkInvoicesDetailEntity emkInvoicesDetailEntity = null;
								EmkSizeTotalEntity emkSizeTotalEntity = null;

								for(EmkInvoicesDetailEntity invoicesDetailEntity : invoicesDetailEntityList){
									emkInvoicesDetailEntity = new EmkInvoicesDetailEntity();
									MyBeanUtils.copyBeanNotNull2Bean(invoicesDetailEntity,emkInvoicesDetailEntity);
									emkInvoicesDetailEntity.setId(null);
									emkInvoicesDetailEntity.setInvoicesId(emkFinanceReceivableEntity.getId());
									systemService.save(emkInvoicesDetailEntity);

									emkSizeTotalEntity = new EmkSizeTotalEntity();
									emkSizeTotalEntity.setId(null);
									emkSizeTotalEntity.setpId(emkInvoicesDetailEntity.getId());
									systemService.save(emkSizeTotalEntity);
								}

								EmkTdhdCostDetailEntity emkTdhdCostDetailEntity = null;
								List<EmkProOrderDetailEntity> proOrderDetailEntityList = systemService.findHql("from EmkProOrderDetailEntity where proOrderId=?",emkLeaveFactoryEntity.getId());
								for(EmkProOrderDetailEntity emkProOrderDetailEntity : proOrderDetailEntityList){
									emkTdhdCostDetailEntity = new EmkTdhdCostDetailEntity();
									MyBeanUtils.copyBeanNotNull2Bean(emkProOrderDetailEntity,emkTdhdCostDetailEntity);
									emkTdhdCostDetailEntity.setId(null);
									emkTdhdCostDetailEntity.setTdhdCostId(emkFinanceReceivableEntity.getId());
									emkTdhdCostDetailEntity.setCargoNo(emkCargoSpaceEntity.getCargoNo());
									emkTdhdCostDetailEntity.setOutForumNo(emkOutForumEntity.getOutForumNo());


									systemService.save(emkTdhdCostDetailEntity);
								}
								taskService.complete(task1.getId(), variables);
								b.setStatus(2);
								approvalDetail.setBpmName("生成应收应付单");
								t.setState("2");
								approvalDetail.setApproveStatus(0);
								bpmUser = systemService.get(TSUser.class,b.getCommitId());
								saveSmsAndEmailForOne("生成应收应付单","您有【"+user.getRealName()+"】完成的采购生产进度表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
							}
						}
						systemService.saveOrUpdate(approvalDetail);
					}else {
						ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("outPro", "emkProduceEntity", variables);
						task = taskService.createTaskQuery().taskAssignee(id).list();
						Task task1 = task.get(task.size() - 1);
						taskService.complete(task1.getId(), variables);

						saveSmsAndEmailForMany("业务跟单员","【生产跟单员】采购生产进度表","您有【"+b.getCreateName()+"】提交的采购生产进度表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
						t.setState("1");
						b.setStatus(1);
						b.setBpmStatus("0");
						b.setProcessName("【生产跟单员】采购生产进度表");
					}
					systemService.saveOrUpdate(b);
					systemService.saveOrUpdate(t);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "采购生产进度表提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	@RequestMapping(params="goWork")
	public ModelAndView goWork(EmkProduceEntity emkProduce, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkProduce.getId())) {
			emkProduce = systemService.getEntity(EmkProduceEntity.class, emkProduce.getId());
			req.setAttribute("emkProducePage", emkProduce);
		}
		return new ModelAndView("com/emk/produce/produce/emkProduce-work");
	}

	@RequestMapping(params="goTime")
	public ModelAndView goTime(EmkProduceEntity emkProduce, HttpServletRequest req, DataGrid dataGrid) {
		EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkProduce.getId());
		if(Utils.notEmpty(approvalEntity)){
			List<EmkApprovalDetailEntity> approvalDetailEntityList = systemService.findHql("from EmkApprovalDetailEntity where approvalId=? order by approveDate asc",approvalEntity.getId());
			req.setAttribute("approvalDetailEntityList", approvalDetailEntityList);
			req.setAttribute("approvalEntity", approvalEntity);
			req.setAttribute("createDate", org.jeecgframework.core.util.DateUtils.date2Str(approvalEntity.getCreateDate(), org.jeecgframework.core.util.DateUtils.datetimeFormat));
		}
		return new ModelAndView("com/emk/produce/produce/time");
	}
}
