package com.emk.storage.outorder.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.storage.formaterial.entity.EmkFormaterialEntity;
import com.emk.storage.outorder.entity.EmkOutOrderEntity;
import com.emk.storage.outorder.service.EmkOutOrderServiceI;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.util.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
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
 * @Description: 工厂出货表
 * @author onlineGenerator
 * @date 2019-08-29 23:38:28
 * @version V1.0   
 *
 */
@Api(value="EmkOutOrder",description="工厂出货表",tags="emkOutOrderController")
@Controller
@RequestMapping("/emkOutOrderController")
public class EmkOutOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkOutOrderController.class);

	@Autowired
	private EmkOutOrderServiceI emkOutOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 工厂出货表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/storage/outorder/emkOutOrderList");
	}

	/**
	 * 报单打印预览 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "printPreview")
	public ModelAndView printPreview(HttpServletRequest req) {
		Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
		EmkOutOrderEntity enquiryEntity = systemService.get(EmkOutOrderEntity.class,map.get("id"));
		List<EmkEnquiryDetailEntity> emkEnquiryDetailEntityList = systemService.findHql("from EmkEnquiryDetailEntity where enquiryId=? order by colorValue asc",enquiryEntity.getId());
		req.setAttribute("enquiryEntity",enquiryEntity);
		req.setAttribute("emkEnquiryDetailEntityList",emkEnquiryDetailEntityList);
		EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",enquiryEntity.getId());
		req.setAttribute("emkSizePage", emkSizeEntity);
		List<Map> dataList = new ArrayList<>();
		List<EmkEnquiryDetailEntity> subdataList = new ArrayList<>();
		int i= 0;
		Map b = null;

		for(EmkEnquiryDetailEntity enquiryDetailEntity : emkEnquiryDetailEntityList){
			if(i == 23){
				b = new HashMap();
				subdataList.add(enquiryDetailEntity);
				b.put("subdataList",subdataList);
				dataList.add(b);
				i = 0;
				subdataList = new ArrayList<>();
			}else{
				subdataList.add(enquiryDetailEntity);
				i++;
			}
		}
		if(subdataList.size()>0){
			b = new HashMap();
			b.put("subdataList",subdataList);
			dataList.add(b);
		}
		req.setAttribute("dataList",dataList);
		return new ModelAndView("com/emk/storage/outorder/printPreview");

	}


	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(po_number,'-',color,'-',nc_size) typecode from emk_po_color ");
		String color = JSONHelper.collection2json(list);
		request.setAttribute("color", "'"+color+ "'");
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("proOrderId"))) {
			List<EmkEnquiryDetailEntity> emkEnquiryDetailEntityList = systemService.findHql("from EmkEnquiryDetailEntity where enquiryId = ? order by colorValue asc",map.get("proOrderId"));
			request.setAttribute("emkProOrderDetailEntities", emkEnquiryDetailEntityList);
			EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("proOrderId"));
			request.setAttribute("emkSizePage", emkSizeEntity);
		}
		return new ModelAndView("com/emk/storage/outorder/orderMxList");
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
	public void datagrid(EmkOutOrderEntity emkOutOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkOutOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkOutOrder, request.getParameterMap());
		try{
			//自定义追加查询条件
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			if(user.getUserKey().equals("工厂")) {
				EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
				cq.eq("gysCode",factoryArchivesEntity.getCompanyCode());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkOutOrderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除工厂出货表
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkOutOrderEntity emkOutOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkOutOrder = systemService.getEntity(EmkOutOrderEntity.class, emkOutOrder.getId());
		message = "工厂出货表删除成功";
		try{
			emkOutOrderService.delete(emkOutOrder);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工厂出货表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除工厂出货表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "工厂出货表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkOutOrderEntity emkOutOrder = systemService.getEntity(EmkOutOrderEntity.class, 
				id
				);
				if (!emkOutOrder.getState().equals("0")) {
					message = "存在已提交的工厂出货，请重新选择！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}

				if("0".equals(emkOutOrder.getState())){
					systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))",id);
					systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",id);

					systemService.executeSql("delete from emk_size where form_id=?", id);
					emkOutOrderService.delete(emkOutOrder);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}

			}
		}catch(Exception e){
			e.printStackTrace();
			message = "工厂出货表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 追回工厂出货表
	 *
	 * @return
	 */
	@RequestMapping(params = "doBack")
	@ResponseBody
	public AjaxJson doBack(EmkOutOrderEntity emkOutOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		EmkOutOrderEntity emkOutOrderEntity = emkOutOrderService.get(EmkOutOrderEntity.class, emkOutOrder.getId());
		message = "工厂出货表追回成功";
		try{
			EmkEnquiryEntity t = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkOutOrderEntity.getOrderNo());
			if(Integer.valueOf(t.getState().toString())>9) {
				message = "出货已通过审核，无法追回";
				j.setMsg(message);
				j.setSuccess(false);
				return  j;
			}else{
				Map<String, Object> variables = new HashMap();
				variables.put("optUser", t.getId());
				EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
				List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
				TSUser makerUser = systemService.findUniqueByProperty(TSUser.class,"userName",t.getCreateBy());
				TSUser bpmUser = null;
				bpmUser = systemService.get(TSUser.class,b.getCommitId());

				EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(),bpmUser,b,"追回");
				saveApprvoalDetail(approvalDetail,"验货","yanhuoTask",1,"追回");
				if (task.size() > 0) {
					Task task1 = (Task) task.get(task.size() - 1);
					if (task1.getTaskDefinitionKey().equals("yanhuoTask")) {
						backProcess(task1.getProcessInstanceId(),"yanhuoTask","outhuoTask","工厂出货");
						t.setState("10");
						b.setStatus(10);
						b.setBpmStatus("1");
						approvalDetail.setApproveStatus(1);
						emkOutOrderEntity.setState("0");
					}
				}
				systemService.save(approvalDetail);
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工厂出货表追回失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加工厂出货表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkOutOrderEntity emkOutOrder,EmkSizeEntity emkSize, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "工厂出货表添加成功";
		try{
			emkOutOrder.setState("0");
			emkOutOrder.setType("0");
			/*List<EmkOutOrderEntity> list = systemService.findHql("from EmkOutOrderEntity where state=0");
			if(list.size()>0){
				j.setMsg("存在未提交的出货，无法录入新的数据");
				j.setSuccess(false);
				return j;
			}*/
			String eNo = "";
			if(emkOutOrder.getOrderNo().contains("出货")){
				eNo = emkOutOrder.getOrderNo().substring(0,emkOutOrder.getOrderNo().indexOf("-出货"));
			}else{
				eNo = emkOutOrder.getOrderNo();
			}
			EmkOutOrderEntity emkOutOrderEntity = systemService.findUniqueByProperty(EmkOutOrderEntity.class,"orderNo",eNo);
			Map<String, Object> orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no,2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+eNo+"-出货%'");
			//Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no,2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+emkOutOrder.getOrderNo()+"-出货%'");
			EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkOutOrder.getOrderNo());
			if(Integer.valueOf(emkEnquiryEntity.getState().toString())<7){
				j.setMsg("存在工厂未确认的订单，无法录入新的数据");
				j.setSuccess(false);
				return j;
			}

			/*if(!emkEnquiryEntity.getState().equals("7") && !emkEnquiryEntity.getState().equals("10")){
				emkOutOrder.setOrderNo(emkEnquiryEntity.getEnquiryNo()+"-出货"+String.format("%02d", Integer.valueOf(orderNum.get("orderNum").toString())));
			}else{
				emkOutOrder.setOrderId(emkEnquiryEntity.getId());
			}*/
			if(orderNum.get("orderNum").toString().equals("1") && Utils.isEmpty(emkOutOrderEntity)){
				emkOutOrder.setOrderId(emkEnquiryEntity.getId());
			}else{
				emkOutOrder.setOrderNo(eNo+"-出货"+String.format("%02d", Integer.valueOf(orderNum.get("orderNum").toString())));
			}
			emkOutOrderService.save(emkOutOrder);
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			emkSize.setFormId(emkOutOrder.getId());

			systemService.save(emkSize);
			Class c = Class.forName(EmkSizeTotalEntity.class.getName());
			String dataRows = (String) map.get("dataRowsVal");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkEnquiryDetailEntity orderMxEntity = null;
				EmkSizeTotalEntity emkSizeTotalEntity = null;
				String m0 = "";
				Method show = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
						orderMxEntity = new EmkEnquiryDetailEntity();
						orderMxEntity.setEnquiryId(emkOutOrder.getId());
						if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
							String[] colorArr = map.get("orderMxList["+i+"].color").split("-");
							orderMxEntity.setColorValue(colorArr[0]);
							orderMxEntity.setColor(colorArr[1]);
							orderMxEntity.setSize(colorArr[2]);
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
							orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].total")));
						}
						Map price = systemService.findOneForJdbc("select ifnull(t1.price,0) price from emk_enquiry t LEFT JOIN emk_enquiry_detail t1 on t1.enquiry_id=t.id " +
								"where t.enquiry_no=? and t1.color_value=? and t1.color=? and t1.size=?",eNo,orderMxEntity.getColorValue(),orderMxEntity.getColor(),orderMxEntity.getSize());


						if(Utils.isEmpty(price)){
							/*systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))",emkOutOrder.getId());
							systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",emkOutOrder.getId());
							systemService.executeSql("delete from emk_size where form_id=?", orderMxEntity.getId());
							emkOutOrderService.delete(emkOutOrder);
							message = orderMxEntity.getColorValue()+"-"+orderMxEntity.getColor()+"-"+orderMxEntity.getSize()+",报单中没有价格，请重新导入";
							j.setSuccess(false);
							j.setMsg(message);
							return j;*/
							if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
								orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
							}
							if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
								orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
							}
						}else{
							orderMxEntity.setPrice(Double.valueOf(price.get("price").toString()));
							orderMxEntity.setMoney(orderMxEntity.getTotal()*orderMxEntity.getPrice());
						}

						orderMxEntity.setSortDesc(String.valueOf(i+1));
						systemService.save(orderMxEntity);

						emkSizeTotalEntity = new EmkSizeTotalEntity();
						for(int z = 1 ; z < 23 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(map.get("orderMxList["+i+"].total"+(char)(z+64))) ? map.get("orderMxList["+i+"].total"+(char)(z+64)):"");
						}
						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工厂出货表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新工厂出货表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkOutOrderEntity emkOutOrder,EmkSizeEntity emkSize, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "工厂出货表更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkOutOrder.getOrderNo());
		emkOutOrder.setOrderId(emkEnquiryEntity.getId());
		EmkOutOrderEntity t = emkOutOrderService.get(EmkOutOrderEntity.class, emkOutOrder.getId());
		EmkSizeEntity t2 = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",t.getId());

		try {
			if(!t.getState().equals("0") && !t.getState().equals("3")){
				j.setMsg("出货已提交，无法进行修改");
				j.setSuccess(false);
				return j;
			}
			MyBeanUtils.copyBeanNotNull2Bean(emkOutOrder, t);
			emkOutOrderService.saveOrUpdate(t);

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
			String dataRows = (String) map.get("dataRowsVal");
			if (Utils.notEmpty(dataRows)) {
				String eNo = "";
				if(emkOutOrder.getOrderNo().contains("出货")){
					eNo = emkOutOrder.getOrderNo().substring(0,emkOutOrder.getOrderNo().indexOf("-出货"));
				}else{
					eNo = emkOutOrder.getOrderNo();
				}
				Class c = Class.forName(EmkSizeTotalEntity.class.getName());
				systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))", t.getId());
				systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",t.getId());

				int rows = Integer.parseInt(dataRows);
				EmkEnquiryDetailEntity orderMxEntity = null;
				EmkSizeTotalEntity emkSizeTotalEntity = null;
				String m0 = "";
				Method show = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
						orderMxEntity = new EmkEnquiryDetailEntity();
						orderMxEntity.setEnquiryId(emkOutOrder.getId());
						if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
							String[] colorArr = map.get("orderMxList["+i+"].color").split("-");
							orderMxEntity.setColorValue(colorArr[0]);
							orderMxEntity.setColor(colorArr[1]);
							orderMxEntity.setSize(colorArr[2]);
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
							orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].total")));
						}
						/*if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
							orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
						}*/
						Map price = systemService.findOneForJdbc("select ifnull(t1.price,0) price from emk_enquiry t LEFT JOIN emk_enquiry_detail t1 on t1.enquiry_id=t.id " +
								"where t.enquiry_no=? and t1.color_value=? and t1.color=? and t1.size=?",eNo,orderMxEntity.getColorValue(),orderMxEntity.getColor(),orderMxEntity.getSize());

						if(Utils.isEmpty(price)){
							if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
								orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
							}
							if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
								orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
							}
						}else{
							orderMxEntity.setPrice(Double.valueOf(price.get("price").toString()));
							orderMxEntity.setMoney(orderMxEntity.getTotal()*orderMxEntity.getPrice());
						}
						/*if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
							orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
						}*/
						orderMxEntity.setSortDesc(String.valueOf(i+1));

						systemService.save(orderMxEntity);
						emkSizeTotalEntity = new EmkSizeTotalEntity();
						for(int z = 1 ; z < 23 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(map.get("orderMxList["+i+"].total"+(char)(z+64))) ? map.get("orderMxList["+i+"].total"+(char)(z+64)):"");
						}
						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "工厂出货表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkOutOrderEntity emkOutOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "出货提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if ((Utils.isEmpty(emkOutOrder.getId()))) {
				for (String id : map.get("ids").split(",")) {
					EmkOutOrderEntity outOrderEntity = systemService.getEntity(EmkOutOrderEntity.class, id);
					if(!outOrderEntity.getCreateBy().equals(user.getUserName())){
						message = "只能由创建人提交出货！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
					if (!outOrderEntity.getState().equals("0") && !outOrderEntity.getState().equals("3")) {
						message = "存在已提交的出货，请重新选择在提交！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkOutOrder.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").split(",")) {
					emkOutOrder = systemService.get(EmkOutOrderEntity.class,id);
					String eNo = "";
					if(emkOutOrder.getOrderNo().contains("出货")){
						eNo = emkOutOrder.getOrderNo().substring(0,emkOutOrder.getOrderNo().indexOf("-出货"));
					}else{
						eNo = emkOutOrder.getOrderNo();
					}
					EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",eNo);
					EmkEnquiryEntity emkEnquiryEntity2 = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkOutOrder.getOrderNo());

					//Map<String, Object> orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no,2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+eNo+"-出货%'");
//					Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no,2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+emkOutOrder.getOrderNo()+"%'");
					EmkEnquiryEntity t = null;
					if(Utils.notEmpty(emkEnquiryEntity2)){
						if(emkEnquiryEntity2.getState().equals("10")){
							t = systemService.get(EmkEnquiryEntity.class,emkEnquiryEntity2.getId());
						}else{
							if(eNo.equals(emkOutOrder.getOrderNo())){
								t = systemService.get(EmkEnquiryEntity.class,emkOutOrder.getOrderId());
							}else{
								t = new EmkEnquiryEntity();
								MyBeanUtils.copyBeanNotNull2Bean(emkEnquiryEntity,t);
								t.setEnquiryNo(emkOutOrder.getOrderNo());
								t.setState("9");
								t.setId(null);
								systemService.save(t);
								emkOutOrder.setOrderId(t.getId());

								EmkApprovalEntity approvalEntity = new EmkApprovalEntity();
								EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
								ApprovalUtil.saveApproval(approvalEntity,0,t.getEnquiryNo(),t.getId(),user);
								systemService.save(approvalEntity);
								ApprovalUtil.saveApprovalDetail(approvalDetailEntity,approvalEntity.getId(),"报单","billTask","提交",user);
								systemService.save(approvalDetailEntity);

								variables.put("optUser", t.getId());
								ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("femk", "emkEnquiryEntity", variables);
								List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								Task task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);
							}
						}
					}else{
						if(eNo.equals(emkOutOrder.getOrderNo())){
							t = systemService.get(EmkEnquiryEntity.class,emkOutOrder.getOrderId());
						}else{
							t = new EmkEnquiryEntity();
							MyBeanUtils.copyBeanNotNull2Bean(emkEnquiryEntity,t);
							t.setEnquiryNo(emkOutOrder.getOrderNo());
							t.setState("9");
							t.setId(null);
							systemService.save(t);
							emkOutOrder.setOrderId(t.getId());

							EmkApprovalEntity approvalEntity = new EmkApprovalEntity();
							EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
							ApprovalUtil.saveApproval(approvalEntity,0,t.getEnquiryNo(),t.getId(),user);
							systemService.save(approvalEntity);
							ApprovalUtil.saveApprovalDetail(approvalDetailEntity,approvalEntity.getId(),"报单","billTask","提交",user);
							systemService.save(approvalDetailEntity);

							variables.put("optUser", t.getId());
							ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("femk", "emkEnquiryEntity", variables);
							List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							Task task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);
						}
					}

					variables.put("optUser", t.getId());
					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
					List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();

					if (task.size() > 0) {
						//保存审批意见
						EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(),user,b,map.get("advice"));
						TSUser makerUser = systemService.findUniqueByProperty(TSUser.class,"userName",t.getCreateBy());
						TSUser bpmUser = null;
						if (task.size() > 0) {
							bpmUser = systemService.get(TSUser.class,b.getBpmSherId());
						}else{
							bpmUser = systemService.get(TSUser.class,b.getCommitId());
						}
						Task task1 = (Task)task.get(task.size() - 1);
						if (task1.getTaskDefinitionKey().equals("outhuoTask")) {
							taskService.complete(task1.getId(), variables);
							t.setState("9");
							b.setStatus(9);
							String title = "";
							if(emkOutOrder.getState().equals("10")){
								title = "重新提交出货";
							}else{
								title = "提交出货";
							}
							saveApprvoalDetail(approvalDetail,"出货","outhuoTask",0,title);

							String userKey = "";
							Map userM = null;
							userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='普通用户' or t.userkey='管理员'");
							if(Utils.notEmpty(userM)){
								saveSmsAndEmailForMany("普通用户","出货","您有【"+b.getCreateName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
								saveSmsAndEmailForMany("管理员","出货","您有【"+b.getCreateName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
								b.setNextBpmSherId(userM.get("userNames").toString());
								b.setNextBpmSher(userM.get("realNames").toString());
							}
						}
						systemService.save(approvalDetail);
						emkOutOrder.setState("1");
						systemService.saveOrUpdate(t);
						systemService.saveOrUpdate(b);
					}else{
						message = "出货提交异常";
						j.setMsg(message);
						j.setSuccess(false);
						return j;
					}

				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "出货提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 工厂出货表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkOutOrderEntity emkOutOrder, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
		req.setAttribute("factoryArchivesEntity", factoryArchivesEntity);

		if (StringUtil.isNotEmpty(emkOutOrder.getId())) {
			emkOutOrder = emkOutOrderService.getEntity(EmkOutOrderEntity.class, emkOutOrder.getId());
			req.setAttribute("emkOutOrderPage", emkOutOrder);
		}
		return new ModelAndView("com/emk/storage/outorder/emkOutOrder-add");
	}
	/**
	 * 工厂出货表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkOutOrderEntity emkOutOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkOutOrder.getId())) {
			emkOutOrder = emkOutOrderService.getEntity(EmkOutOrderEntity.class, emkOutOrder.getId());
			req.setAttribute("emkOutOrderPage", emkOutOrder);
		}
		return new ModelAndView("com/emk/storage/outorder/emkOutOrder-update");
	}

	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkOutOrderEntity emkOutOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkOutOrder.getId())) {
			emkOutOrder = emkOutOrderService.findUniqueByProperty(EmkOutOrderEntity.class,"orderId",emkOutOrder.getId());
			req.setAttribute("emkOutOrderPage", emkOutOrder);

		}
		return new ModelAndView("com/emk/storage/outorder/emkOutOrder-update2");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		return new ModelAndView("com/emk/storage/outorder/uploadView");

	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkOutOrderEntity emkOutOrder,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkOutOrderEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkOutOrder, request.getParameterMap());
		List<EmkOutOrderEntity> emkOutOrders = this.emkOutOrderService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"工厂出货表");
		modelMap.put(NormalExcelConstants.CLASS,EmkOutOrderEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("工厂出货表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkOutOrders);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkOutOrderEntity emkOutOrder,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"工厂出货表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkOutOrderEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("工厂出货表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="工厂出货表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkOutOrderEntity>> list() {
		List<EmkOutOrderEntity> listEmkOutOrders=emkOutOrderService.getList(EmkOutOrderEntity.class);
		return Result.success(listEmkOutOrders);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取工厂出货表信息",notes="根据ID获取工厂出货表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkOutOrderEntity task = emkOutOrderService.get(EmkOutOrderEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取工厂出货表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建工厂出货表")
	public ResponseMessage<?> create(@ApiParam(name="工厂出货表对象")@RequestBody EmkOutOrderEntity emkOutOrder, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkOutOrderEntity>> failures = validator.validate(emkOutOrder);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkOutOrderService.save(emkOutOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("工厂出货表信息保存失败");
		}
		return Result.success(emkOutOrder);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新工厂出货表",notes="更新工厂出货表")
	public ResponseMessage<?> update(@ApiParam(name="工厂出货表对象")@RequestBody EmkOutOrderEntity emkOutOrder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkOutOrderEntity>> failures = validator.validate(emkOutOrder);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkOutOrderService.saveOrUpdate(emkOutOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新工厂出货表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新工厂出货表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除工厂出货表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkOutOrderService.deleteEntityById(EmkOutOrderEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("工厂出货表删除失败");
		}

		return Result.success();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(EmkOutOrderEntity orderEntity,String fileName,String fileNameUrl,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		String message = "文件导入成功";
		File newfile = null;
		HSSFWorkbook wb = null;
		String cellValue = "";
		EmkEnquiryDetailEntity orderMxEntity = null;
		EmkSizeTotalEntity emkSizeTotalEntity = null;
		String orderNo = orderEntity.getOrderNo();
		try {
			String savepath = request.getRealPath("/")+"imp/order/";
			newfile =  new File(savepath+fileName);
			wb = WebFileUtils.createHSSFWorkBook(newfile);
			HSSFSheet sheet = wb.getSheetAt(0);
			DecimalFormat df = new DecimalFormat("0");
			HSSFCell cell = null;
			int counter = 0;
			HSSFRow row = null;
			logger.info("执行导入："+newfile.getName());
			List<String> itemValue = null;
			Map orderNum = null;
			TSDepart tsDepart = null;
			String m0 = "";
			String eNo = "";
			Method show = null;

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				itemValue = new ArrayList<String>();
				for(int z = 0; z <= 28 ; z++){
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
				if(i == 0 ){
					List<EmkOutOrderEntity> list = systemService.findHql("from EmkOutOrderEntity where state=0");
					if(list.size()>0){
						j.setMsg("存在未提交的出货，无法录入新的数据");
						j.setSuccess(false);
						return j;
					}
					if(orderEntity.getOrderNo().contains("出货")){
						eNo = orderEntity.getOrderNo().substring(0,orderEntity.getOrderNo().indexOf("-出货"));
					}else{
						eNo = orderEntity.getOrderNo();
					}
					//Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no,2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+emkOutOrder.getOrderNo()+"-出货%'");
					orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no, 2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+eNo+"-出货"+"%'");
					EmkOutOrderEntity emkOutOrderEntity = systemService.findUniqueByProperty(EmkOutOrderEntity.class,"orderNo",eNo);
					EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",orderEntity.getOrderNo());
					if(Integer.valueOf(emkEnquiryEntity.getState().toString())<7){
						j.setMsg("存在工厂未确认的订单，无法录入新的数据");
						j.setSuccess(false);
						return j;
					}
					if(orderNum.get("orderNum").toString().equals("1") && Utils.isEmpty(emkOutOrderEntity)){
						orderEntity.setOrderId(emkEnquiryEntity.getId());
					}else{
						orderEntity.setOrderNo(eNo+"-出货"+String.format("%02d", Integer.valueOf(orderNum.get("orderNum").toString())));
					}
					//EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",orderEntity.getOrderNo());
					orderEntity.setState("0");
					orderEntity.setType("0");

					TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
					Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
					emkOutOrderService.save(orderEntity);

					EmkSizeEntity emkSize = new EmkSizeEntity();
					Class c = Class.forName(EmkSizeEntity.class.getName());
					emkSize.setFormId(orderEntity.getId());
					for(int z = 1 ; z < 23 ; z++){
						m0 = "setSize"+(char)(z+64);
						show = c.getMethod(m0,String.class);
						show.invoke(emkSize,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
					}
					systemService.save(emkSize);


				}else{
					if(Utils.notEmpty(itemValue.get(0))){
						orderMxEntity = new EmkEnquiryDetailEntity();
						orderMxEntity.setEnquiryId(orderEntity.getId());
						orderMxEntity.setColorValue(itemValue.get(0));
						orderMxEntity.setColor(itemValue.get(1));
						orderMxEntity.setSize(itemValue.get(2));

						orderMxEntity.setSortDesc(String.valueOf(i+1));
						emkSizeTotalEntity = new EmkSizeTotalEntity();
						Class c = Class.forName(EmkSizeTotalEntity.class.getName());
						int total = 0;
						for(int z = 1 ; z < 23 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							total += Integer.valueOf(Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"0");
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
						}
						orderMxEntity.setTotal(total);
						Map price = systemService.findOneForJdbc("select ifnull(t1.price,0) price from emk_enquiry t LEFT JOIN emk_enquiry_detail t1 on t1.enquiry_id=t.id " +
								"where t.enquiry_no=? and t1.color_value=? and t1.color=? and t1.size=?",eNo,orderMxEntity.getColorValue(),orderMxEntity.getColor(),orderMxEntity.getSize());
						if(Utils.isEmpty(price)){
							/*systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))",orderEntity.getId());
							systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",orderEntity.getId());
							systemService.executeSql("delete from emk_size where form_id=?", orderEntity.getId());

							emkOutOrderService.delete(orderEntity);

							message = orderMxEntity.getColorValue()+"-"+orderMxEntity.getColor()+"-"+orderMxEntity.getSize()+",报单中没有价格，请重新导入";
							j.setSuccess(false);
							j.setMsg(message);
							return j;*/

							if(Utils.notEmpty(itemValue.get(27))){
								orderMxEntity.setPrice(Double.parseDouble(itemValue.get(27)));
								orderMxEntity.setMoney(total*orderMxEntity.getPrice());
							}
						}else{
							orderMxEntity.setPrice(Double.valueOf(price.get("price").toString()));
							orderMxEntity.setMoney(total*orderMxEntity.getPrice());
						}
						systemService.save(orderMxEntity);

						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}

				j.setSuccess(true);
			}
		} catch (Exception e) {
			message = "文件导入失败";
			j.setSuccess(false);
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}finally{
			newfile.delete();
		}
		j.setMsg(message);
		return j;
	}
}
