package com.emk.storage.pay.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntityE;
import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.storage.finishorder.entity.EmkFinishOrderEntity;
import com.emk.storage.outorder.entity.EmkOutOrderEntity;
import com.emk.storage.pay.entity.EmkPayEntity;
import com.emk.storage.pay.service.EmkPayServiceI;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.util.ApprovalUtil;
import com.emk.util.DateUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
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

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;

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
 * @Description: 付款信息表
 * @author onlineGenerator
 * @date 2019-09-08 15:15:38
 * @version V1.0   
 *
 */
@Api(value="EmkPay",description="付款信息表",tags="emkPayController")
@Controller
@RequestMapping("/emkPayController")
public class EmkPayController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkPayController.class);

	@Autowired
	private EmkPayServiceI emkPayService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;


	/**
	 * 报单打印预览 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "printPreview")
	public ModelAndView printPreview(HttpServletRequest req) {
		Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
		EmkPayEntity emkPayEntity = systemService.get(EmkPayEntity.class,map.get("id"));
		req.setAttribute("emkPayEntity",emkPayEntity);
		String str = emkPayEntity.getOrderNo();
		String compareStr = "-";
		int indexStart = 0;
		int compareStrLength = compareStr.length();
		int count = 0;
		while(true){
			int tm = str.indexOf(compareStr,indexStart);
			if( tm >= 0){
				count ++;
				indexStart = tm+compareStrLength;
			}else{
				break;
			}
		}
		if(count>2){
			str = str.substring(0,str.lastIndexOf("-"));
		}

		StringBuilder sql = new StringBuilder();
		sql.append("select price,sum(total) total,color_value,color,size  \n");
		for(int z = 1 ; z < 23 ; z++) {
			String ab = String.valueOf((char) (z + 64));
			sql.append(" ,sum(total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
		}
		sql.append(" from (select sum(total) total,t.color_value,t.color,t.size,t.price,t.money	\n");

		for(int z = 1 ; z < 23 ; z++) {
			String ab = String.valueOf((char) (z + 64));
			sql.append(" ,sum(t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
		}
		sql.append("	from emk_enquiry_detail t \n");
		sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
		sql.append("	left join emk_out_order t3 on t3.id=t.enquiry_id	\n" );
		sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.color_value,t.color,t.size \n");

		sql.append(" union select -t.total,t.color_value,t.color,t.size,t.price,t.money \n");
		for(int z = 1 ; z < 23 ; z++) {
			String ab = String.valueOf((char) (z + 64));
			sql.append(" ,sum(-t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
		}
		sql.append("	from emk_enquiry_detail t \n");
		sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
		sql.append("	left join emk_cancel_order t3 on t3.id=t.enquiry_id	\n" );
		sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.color_value,t.color,t.size");
		sql.append("	) a group by color_value asc,color,size");

		List<Map<String, Object>> emkOutOrderEntityList = systemService.findForJdbc(sql.toString());

		req.setAttribute("emkProOrderDetailEntities", emkOutOrderEntityList);
		EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("proOrderId"));
		req.setAttribute("emkSizePage", emkSizeEntity);

		double a1=0,a2 =0;
		Map p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_enquiry t3 on t3.id=t.enquiry_id where t3.enquiry_no like '%"+str+"%' and  t3.state!=0");
		req.setAttribute("orderMoney", p.get("money"));
		p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_formaterail_detail t left join emk_formaterial t3 on t3.id=t.formaterial_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
		req.setAttribute("setjbMoney", p.get("money"));
		a1 += Double.parseDouble(p.get("money").toString());
		p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_formaterail_other_detail t left join emk_formaterial_other t3 on t3.id=t.formaterail_other_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
		req.setAttribute("wlMoney", p.get("money"));
		a1 += Double.parseDouble(p.get("money").toString());
		p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_out_order t3 on t3.id=t.enquiry_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
		req.setAttribute("fhMoney", p.get("money"));
		a2 += Double.parseDouble(p.get("money").toString());
		p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_cancel_order t3 on t3.id=t.enquiry_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
		req.setAttribute("thMoney", p.get("money"));
		a2 -= Double.parseDouble(p.get("money").toString());
		p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_pay t where t.order_no like '%"+str+"%' and (t.state=4 or t.state=2)");
		req.setAttribute("fkMoney", p.get("money"));
		double wfk = a2-a1-Double.valueOf(p.get("money").toString()) ;
		req.setAttribute("wfk", wfk);

		List<EmkPayEntity> payEntityList = systemService.findHql("from EmkPayEntity where orderId=?",emkPayEntity.getOrderId());
		req.setAttribute("payEntityList", payEntityList);

		sql = new StringBuilder();
		sql.append(" select t.total,t.color_num,t.pm,t.price,t.money	\n");
		for(int z = 1 ; z < 23 ; z++) {
			String ab = String.valueOf((char) (z + 64));
			sql.append(" ,sum(t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
		}
		sql.append("	from emk_formaterail_detail t \n");
		sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
		sql.append("	left join emk_formaterial t3 on t3.id=t.formaterial_id	\n" );
		sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.color_num,t.pm \n");
		List<Map<String, Object>> formatrailEntityList = systemService.findForJdbc(sql.toString());
		req.setAttribute("formatrailEntityList", formatrailEntityList);

		sql = new StringBuilder();
		sql.append(" select t.total,t.wllx,t.price,t.money	from emk_formaterail_other_detail t \n");
		sql.append("	left join emk_formaterial_other t3 on t3.id=t.formaterail_other_id	\n" );
		sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.wllx \n");
		List<Map<String, Object>> otherformatrailEntityList = systemService.findForJdbc(sql.toString());
		req.setAttribute("otherformatrailEntityList", otherformatrailEntityList);

		return new ModelAndView("com/emk/storage/pay/printPreview");

	}


	/**
	 * 付款信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/storage/pay/emkPayList");
	}
	@RequestMapping(params = "formaterialMxList")
	public ModelAndView formaterialMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("orderNo"))) {
			String str = map.get("orderNo").toString();
			String compareStr = "-";
			int indexStart = 0;
			int compareStrLength = compareStr.length();
			int count = 0;
			while (true) {
				int tm = str.indexOf(compareStr, indexStart);
				if (tm >= 0) {
					count++;
					indexStart = tm + compareStrLength;
				} else {
					break;
				}
			}
			if (count > 2) {
				str = str.substring(0, str.lastIndexOf("-"));
			}
			StringBuilder sql = new StringBuilder();
			sql = new StringBuilder();
			sql.append(" select t.total,t.color_num,t.pm,t.price,t.money	\n");
			for(int z = 1 ; z < 23 ; z++) {
				String ab = String.valueOf((char) (z + 64));
				sql.append(" ,sum(t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
			}
			sql.append("	from emk_formaterail_detail t \n");
			sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
			sql.append("	left join emk_formaterial t3 on t3.id=t.formaterial_id	\n" );
			sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.color_num,t.pm \n");
			List<Map<String, Object>> formatrailEntityList = systemService.findForJdbc(sql.toString());

			request.setAttribute("formatrailEntityList", formatrailEntityList);
		}
		return new ModelAndView("com/emk/storage/pay/formaterialMxList");
	}
	@RequestMapping(params = "otherMxList")
	public ModelAndView otherMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("orderNo"))) {
			String str = map.get("orderNo").toString();
			String compareStr = "-";
			int indexStart = 0;
			int compareStrLength = compareStr.length();
			int count = 0;
			while (true) {
				int tm = str.indexOf(compareStr, indexStart);
				if (tm >= 0) {
					count++;
					indexStart = tm + compareStrLength;
				} else {
					break;
				}
			}
			if (count > 2) {
				str = str.substring(0, str.lastIndexOf("-"));
			}
			StringBuilder sql = new StringBuilder();
			sql = new StringBuilder();
			sql.append(" select t.total,t.wllx,t.price,t.money	from emk_formaterail_other_detail t \n");
			sql.append("	left join emk_formaterial_other t3 on t3.id=t.formaterail_other_id	\n" );
			sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.wllx \n");
			List<Map<String, Object>> otherformatrailEntityList = systemService.findForJdbc(sql.toString());

			request.setAttribute("otherEntityList", otherformatrailEntityList);
		}
		return new ModelAndView("com/emk/storage/pay/otherMxList");
	}
	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("orderNo"))) {
			String str = map.get("orderNo").toString();
			String compareStr = "-";
			int indexStart = 0;
			int compareStrLength = compareStr.length();
			int count = 0;
			while(true){
				int tm = str.indexOf(compareStr,indexStart);
				if( tm >= 0){
					count ++;
					indexStart = tm+compareStrLength;
				}else{
					break;
				}
			}
			if(count>2){
				str = str.substring(0,str.lastIndexOf("-"));
			}
			StringBuilder sql = new StringBuilder();
			sql.append("select price,sum(total) total,color_value,color,size  \n");
			for(int z = 1 ; z < 23 ; z++) {
				String ab = String.valueOf((char) (z + 64));
				sql.append(" ,sum(total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
			}
			sql.append(" from (select sum(total) total,t.color_value,t.color,t.size,t.price,t.money	\n");

			for(int z = 1 ; z < 23 ; z++) {
				String ab = String.valueOf((char) (z + 64));
				sql.append(" ,sum(t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
			}
			sql.append("	from emk_enquiry_detail t \n");
			sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
			sql.append("	left join emk_out_order t3 on t3.id=t.enquiry_id	\n" );
			sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.color_value,t.color,t.size \n");

			sql.append(" union select sum(-t.total) total,t.color_value,t.color,t.size,t.price,t.money \n");
			for(int z = 1 ; z < 23 ; z++) {
				String ab = String.valueOf((char) (z + 64));
				sql.append(" ,sum(-t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
			}
			sql.append("	from emk_enquiry_detail t \n");
			sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
			sql.append("	left join emk_cancel_order t3 on t3.id=t.enquiry_id	\n" );
			sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.color_value,t.color,t.size");
			sql.append("	) a group by color_value asc,color,size");

			List<Map<String, Object>> emkOutOrderEntityList = systemService.findForJdbc(sql.toString());

			request.setAttribute("emkProOrderDetailEntities", emkOutOrderEntityList);

			/*EmkFinishOrderEntity emkFinishOrderEntity = systemService.findUniqueByProperty(EmkFinishOrderEntity.class,"orderNo",map.get("orderNo"));
			List<EmkEnquiryDetailEntity> emkEnquiryDetailEntityList = systemService.findHql("from EmkEnquiryDetailEntity where enquiryId = ?",emkFinishOrderEntity.getId());
			request.setAttribute("emkProOrderDetailEntities", emkEnquiryDetailEntityList);*/
			EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("proOrderId"));
			request.setAttribute("emkSizePage", emkSizeEntity);
		}
		return new ModelAndView("com/emk/storage/pay/orderMxList");
	}

	@RequestMapping(params = "payList")
	public ModelAndView payList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		String str = map.get("orderNo").toString();
		String compareStr = "-";
		int indexStart = 0;
		int compareStrLength = compareStr.length();
		int count = 0;
		while (true) {
			int tm = str.indexOf(compareStr, indexStart);
			if (tm >= 0) {
				count++;
				indexStart = tm + compareStrLength;
			} else {
				break;
			}
		}
		if (count > 2) {
			str = str.substring(0, str.lastIndexOf("-"));
		}

		double a1=0,a2 =0;
		if(Utils.notEmpty(str)){
			Map p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_enquiry t3 on t3.id=t.enquiry_id where t3.enquiry_no like '%"+str+"%' and  t3.state!=0");
			request.setAttribute("orderMoney", p.get("money"));
			p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_formaterail_detail t left join emk_formaterial t3 on t3.id=t.formaterial_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
			request.setAttribute("setjbMoney", p.get("money"));
			a1 += Double.parseDouble(p.get("money").toString());
			p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_formaterail_other_detail t left join emk_formaterial_other t3 on t3.id=t.formaterail_other_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
			request.setAttribute("wlMoney", p.get("money"));
			a1 += Double.parseDouble(p.get("money").toString());

			p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_out_order t3 on t3.id=t.enquiry_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
			request.setAttribute("fhMoney", p.get("money"));
			a2 += Double.parseDouble(p.get("money").toString());

			p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_cancel_order t3 on t3.id=t.enquiry_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
			request.setAttribute("thMoney", p.get("money"));
			a2 -= Double.parseDouble(p.get("money").toString());

			p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_pay t where t.order_no like '%"+str+"%' and (t.state=4 or t.state=2)");
			request.setAttribute("fkMoney", p.get("money"));

			double wfk = a2-a1-Double.valueOf(p.get("money").toString()) ;
			request.setAttribute("wfk", wfk);
		}

		if (Utils.notEmpty(map.get("proOrderId"))) {
			List<EmkPayEntity> payEntityList = systemService.findHql("from EmkPayEntity where orderId=?",map.get("proOrderId"));
			request.setAttribute("payEntityList", payEntityList);
		}
		return new ModelAndView("com/emk/storage/pay/payList");
	}

	@RequestMapping(params = "finishOrder")
	@ResponseBody
	public AjaxJson finishOrder(EmkPayEntity emkPay, HttpServletRequest request) {
		List<Map<String, Object>> emkEnquiryDetailEntityList = null;
		//EmkFinishOrderEntity emkFinishOrderEntity = systemService.findUniqueByProperty(EmkFinishOrderEntity.class,"orderNo",emkPay.getOrderNo());
		String str = emkPay.getOrderNo();
		String compareStr = "-";
		int indexStart = 0;
		int compareStrLength = compareStr.length();
		int count = 0;
		while(true){
			int tm = str.indexOf(compareStr,indexStart);
			if( tm >= 0){
				count ++;
				indexStart = tm+compareStrLength;
			}else{
				break;
			}
		}
		if(count>2){
			str = str.substring(0,str.lastIndexOf("-"));
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select price,sum(total) total,color_value,color,size  \n");
		for(int z = 1 ; z < 23 ; z++) {
			String ab = String.valueOf((char) (z + 64));
			sql.append(" ,sum(total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
		}
		sql.append(" from (select sum(t.total) total,t.color_value,t.color,t.size,t.price,t.money	\n");

		for(int z = 1 ; z < 23 ; z++) {
			String ab = String.valueOf((char) (z + 64));
			sql.append(" ,sum(t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
		}
		sql.append("	from emk_enquiry_detail t \n");
		sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
		sql.append("	left join emk_out_order t3 on t3.id=t.enquiry_id	\n" );
		sql.append("	where t3.order_no like '%"+str+"%'and (t3.state=4 or t3.state=2)  group by t.color_value,t.color,t.size \n");

		sql.append(" union select sum(-t.total) total,t.color_value,t.color,t.size,t.price,t.money \n");
		for(int z = 1 ; z < 23 ; z++) {
			String ab = String.valueOf((char) (z + 64));
			sql.append(" ,sum(-t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
		}
		sql.append("	from emk_enquiry_detail t \n");
		sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
		sql.append("	left join emk_cancel_order t3 on t3.id=t.enquiry_id	\n" );
		sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.color_value,t.color,t.size");
		sql.append("	) a group by color_value,color,size");

		List<Map<String, Object>> emkOutOrderEntityList = systemService.findForJdbc(sql.toString());

		sql = new StringBuilder();
		sql.append(" select t.total,t.color_num,t.pm,t.price,t.money	\n");
		for(int z = 1 ; z < 23 ; z++) {
			String ab = String.valueOf((char) (z + 64));
			sql.append(" ,sum(t2.total_"+ab.toLowerCase()+") total_"+ab.toLowerCase());
		}
		sql.append("	from emk_formaterail_detail t \n");
		sql.append(" 	left JOIN emk_size_total t2 on t2.p_id=t.id \n");
		sql.append("	left join emk_formaterial t3 on t3.id=t.formaterial_id	\n" );
		sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.color_num,t.pm \n");
		List<Map<String, Object>> formatrailEntityList = systemService.findForJdbc(sql.toString());

		sql = new StringBuilder();
		sql.append(" select t.total,t.wllx,t.price,t.money	from emk_formaterail_other_detail t \n");
		sql.append("	left join emk_formaterial_other t3 on t3.id=t.formaterail_other_id	\n" );
		sql.append("	where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2) group by t.wllx \n");
		List<Map<String, Object>> otherformatrailEntityList = systemService.findForJdbc(sql.toString());

		Map data = new HashMap();
		data.put("emkOutOrderEntityList",emkOutOrderEntityList);
		data.put("formatrailEntityList",formatrailEntityList);
		data.put("otherEntityList",otherformatrailEntityList);

		AjaxJson j = new AjaxJson();
		j.setObj(data);
		return j;

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
	public void datagrid(EmkPayEntity emkPay,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkPayEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkPay, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkPayService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除付款信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkPayEntity emkPay, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkPay = systemService.getEntity(EmkPayEntity.class, emkPay.getId());
		message = "付款信息表删除成功";
		try{
			emkPayService.delete(emkPay);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "付款信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除付款信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "付款信息表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkPayEntity emkPay = systemService.getEntity(EmkPayEntity.class, 
				id
				);
				if (!emkPay.getState().equals("0")) {
					message = "存在已提交的付款，请重新选择！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
				emkPayService.delete(emkPay);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "付款信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加付款信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkPayEntity emkPay, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "付款信息表添加成功";
		try{
			emkPay.setState("0");
			EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkPay.getOrderNo());
			if("2".equals(emkEnquiryEntity.getState())){
				j.setSuccess(false);
				j.setMsg("订单"+emkPay.getOrderNo()+"的已结单，无法录入付款信息");
				return j;
			}
			/*EmkPayEntity t = systemService.findUniqueByProperty(EmkPayEntity.class,"orderNo",emkPay.getOrderNo());
			if(Utils.notEmpty(t)){
				j.setSuccess(false);
				j.setMsg("已存在"+emkPay.getOrderNo()+"的订单付款信息，请重新录入");
				return j;
			}*/
			emkPay.setOrderId(emkEnquiryEntity.getId());
			emkPayService.save(emkPay);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "付款信息表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新付款信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkPayEntity emkPay, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "付款信息表更新成功";
		EmkPayEntity t = emkPayService.get(EmkPayEntity.class, emkPay.getId());
		try {
			if(!t.getState().equals("0") && !t.getState().equals("3")){
				j.setMsg("付款已提交，无法进行修改");
				j.setSuccess(false);
				return j;
			}
			MyBeanUtils.copyBeanNotNull2Bean(emkPay, t);
			emkPayService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "付款信息表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkPayEntity emkPay, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "付款信息提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if ((Utils.isEmpty(emkPay.getId()))) {
				for (String id : map.get("ids").split(",")) {
					EmkPayEntity payEntity = systemService.getEntity(EmkPayEntity.class, id);
					if(!payEntity.getCreateBy().equals(user.getUserName())){
						message = "只能由创建人提交付款信息！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
					if (!payEntity.getState().equals("0") && !payEntity.getState().equals("3")) {
						message = "存在已提交的付款信息，请重新选择在提交！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkPay.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").split(",")) {
					emkPay = systemService.get(EmkPayEntity.class,id);
					EmkEnquiryEntity t = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkPay.getOrderNo());

					variables.put("optUser", t.getId());
					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());

					List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();

					if (task.size() > 0) {
						TSUser makerUser = systemService.findUniqueByProperty(TSUser.class,"userName",t.getCreateBy());
						TSUser bpmUser = systemService.get(TSUser.class,b.getCommitId());

						//保存审批意见
						EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(),user,b,map.get("advice"));
						Task task1 = (Task)task.get(task.size() - 1);
						if (task1.getTaskDefinitionKey().equals("fkTask")) {
							taskService.complete(task1.getId(), variables);
							t.setState("15");
							b.setStatus(15);
							String title = "";
							if(emkPay.getState().equals("16")){
								title = "重新提交付款信息";
							}else{
								title = "提交付款信息";
							}
							saveApprvoalDetail(approvalDetail,"付款","fkTask",0,title);

							String userKey = "";
							/*Map userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='工厂'");
							saveSmsAndEmailForMany("工厂","付款信息","您有【"+b.getCreateName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
							if(Utils.notEmpty(userM)){
								b.setNextBpmSherId(userM.get("userNames").toString());
								b.setNextBpmSher(userM.get("realNames").toString());
							}*/
							Map userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t left join t_s_depart d on d.id=t.departid where t.userkey='工厂' and d.departname=?",t.getGys());
							if(Utils.notEmpty(userM)){
								saveSmsAndEmailForGc(user,t.getGys(),"付款","您有【"+user.getRealName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。");
								b.setNextBpmSherId(userM.get("userNames").toString());
								b.setNextBpmSher(userM.get("realNames").toString());
							}
						}
						systemService.save(approvalDetail);
						emkPay.setState("1");
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "报单提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 付款信息表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkPayEntity emkPay, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkPay.getId())) {
			emkPay = emkPayService.getEntity(EmkPayEntity.class, emkPay.getId());
			req.setAttribute("emkPayPage", emkPay);
		}
		return new ModelAndView("com/emk/storage/pay/emkPay-add");
	}
	/**
	 * 付款信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkPayEntity emkPay, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkPay.getId())) {
			emkPay = emkPayService.getEntity(EmkPayEntity.class, emkPay.getId());
			req.setAttribute("emkPayPage", emkPay);
		}
		return new ModelAndView("com/emk/storage/pay/emkPay-update");
	}
	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkPayEntity emkPay, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkPay.getId())) {
			Map pay = systemService.findOneForJdbc("select * from emk_pay where order_id=? order by create_date asc limit 0,1",emkPay.getId());
			if(Utils.notEmpty(pay)){
				emkPay = emkPayService.get(EmkPayEntity.class,pay.get("id").toString());
				req.setAttribute("emkPayPage", emkPay);
			}
		}
		return new ModelAndView("com/emk/storage/pay/emkPay-update2");
	}
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkPayController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkPayEntity emkPay,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkPayEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkPay, request.getParameterMap());
		List<EmkPayEntity> emkPays = this.emkPayService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"付款信息表");
		modelMap.put(NormalExcelConstants.CLASS,EmkPayEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("付款信息表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkPays);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkPayEntity emkPay,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"付款信息表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkPayEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("付款信息表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkPayEntity> listEmkPayEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkPayEntity.class,params);
				for (EmkPayEntity emkPay : listEmkPayEntitys) {
					emkPayService.save(emkPay);
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
	@ApiOperation(value="付款信息表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkPayEntity>> list() {
		List<EmkPayEntity> listEmkPays=emkPayService.getList(EmkPayEntity.class);
		return Result.success(listEmkPays);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取付款信息表信息",notes="根据ID获取付款信息表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkPayEntity task = emkPayService.get(EmkPayEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取付款信息表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建付款信息表")
	public ResponseMessage<?> create(@ApiParam(name="付款信息表对象")@RequestBody EmkPayEntity emkPay, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkPayEntity>> failures = validator.validate(emkPay);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkPayService.save(emkPay);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("付款信息表信息保存失败");
		}
		return Result.success(emkPay);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新付款信息表",notes="更新付款信息表")
	public ResponseMessage<?> update(@ApiParam(name="付款信息表对象")@RequestBody EmkPayEntity emkPay) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkPayEntity>> failures = validator.validate(emkPay);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkPayService.saveOrUpdate(emkPay);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新付款信息表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新付款信息表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除付款信息表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkPayService.deleteEntityById(EmkPayEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("付款信息表删除失败");
		}

		return Result.success();
	}
}
