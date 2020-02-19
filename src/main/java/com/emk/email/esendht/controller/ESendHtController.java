package com.emk.email.esendht.controller;
import com.emk.email.esendht.entity.ESendHtEntity;
import com.emk.email.esendht.entity.ESendHtEntityA;
import com.emk.email.esendht.service.ESendHtServiceI;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.email.esendmodel.entity.ESendModelEntity;
import com.emk.email.smsmodel.entity.ESmsModelEntity;
import com.emk.email.smsrecord.entity.ESmsRecordEntity;
import com.emk.util.DateUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import com.emk.util.WebFileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
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
 * @Description: 邮箱数据
 * @author onlineGenerator
 * @date 2019-10-29 23:23:12
 * @version V1.0   
 *
 */
@Api(value="ESendHt",description="邮箱数据",tags="eSendHtController")
@Controller
@RequestMapping("/eSendHtController")
public class ESendHtController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ESendHtController.class);

	@Autowired
	private ESendHtServiceI eSendHtService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 邮箱数据列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/email/esendht/eSendHtList");
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
	public void datagrid(ESendHtEntity eSendHt,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ESendHtEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, eSendHt, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.eSendHtService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除邮箱数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ESendHtEntity eSendHt, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		eSendHt = systemService.getEntity(ESendHtEntity.class, eSendHt.getId());
		message = "邮箱数据删除成功";
		try{
			eSendHtService.delete(eSendHt);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "邮箱数据删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除邮箱数据
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "邮箱数据删除成功";
		try{
			for(String id:ids.split(",")){
				ESendHtEntity eSendHt = systemService.getEntity(ESendHtEntity.class, 
				id
				);
				eSendHtService.delete(eSendHt);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "邮箱数据删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 批量发送邮箱数据
	 *
	 * @return
	 */
	@RequestMapping(params = "doSendEmail")
	@ResponseBody
	public AjaxJson doSendEmail(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "发送邮箱数据成功";
		try{
			List<ESendModelEntity> modelList = systemService.findHql("from ESendModelEntity");
			List<ESmsModelEntity> smsmodelList = systemService.findHql("from ESmsModelEntity");
			ESmsModelEntity eSmsModelEntity = smsmodelList.get(0);

			Map<String,ESendModelEntity> modelMap = new HashMap<>();
			for(ESendModelEntity eSendModelEntity : modelList){
				modelMap.put(eSendModelEntity.getMbbh(),eSendModelEntity);
			}
		/*	CriteriaQuery cq = new CriteriaQuery(ESendHtEntity.class);
			cq.add(Restrictions.sqlRestriction("( FIND_IN_SET({alias}.id,'"+ids+"'))"));
			cq.add((Criterion) Projections.groupProperty("manger"));
			cq.add();
			List<ESendHtEntity> eSendHtEntityList = systemService.getListByCriteriaQuery(cq, false);*/
			String sql = "select mbbh,manger,email,manger_telphone from e_send_ht where FIND_IN_SET(id,?) group by manger ";
			List<Map<String, Object>> eSendHtEntityList = systemService.findForJdbc(sql,ids);

			CriteriaQuery cq = new CriteriaQuery(ESendHtEntity.class);
			cq.add(Restrictions.sqlRestriction("( FIND_IN_SET({alias}.id,'"+ids+"'))"));
			cq.add();
			List<ESendHtEntity> subeSendHtEntityList = systemService.getListByCriteriaQuery(cq, false);

			List<Map> dataList = new ArrayList<>();
			Map data = null;
			List<ESendHtEntity> subList = null;
			ESmsRecordEntity eSmsRecordEntity = null;
			for(Map eSendHtEntity : eSendHtEntityList) {
				subList = new ArrayList<>();
				for (ESendHtEntity subeSendHtEntity : subeSendHtEntityList) {
					if(subeSendHtEntity.getManger().equals(eSendHtEntity.get("manger").toString())){
						subList.add(subeSendHtEntity);
					}
				}
				eSendHtEntity.put("subList", subList);
				dataList.add(eSendHtEntity);
			}
			String sendUrl = Utils.getProperties_1("sysConfig.properties","sendUrl");
			for(Map d : dataList){
				if(Utils.notEmpty(modelMap.get(d.get("mbbh")))){
					ESendModelEntity eSendModelEntity = modelMap.get(d.get("mbbh"));
					String content = eSendModelEntity.getContent();
					eSmsRecordEntity = new ESmsRecordEntity();
					content += "<html><style>\n" +
							"    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}\n" +
							"    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}\n" +
							"</style></style><a></a>" +
							"<div class='table-c' style='margin-top:5px;'>" +
							"    <table id='mxtb' width='90%' border='0' cellspacing='0' cellpadding='0'>" +
							"        <tr bgcolor='#F8F8F8' style='height: 32px;' >" +
							"            <td align='center'  width='120'>操作</td>" +
							"            <td align='center'  width='120'>同事编号</td>" +
							"            <td align='center'  width='120'>同事姓名</td>" +
							"            <td align='center'  width='120'>续签新合同期限</td>" +
							"        </tr>";
					List<ESendHtEntity> sub = (List<ESendHtEntity>) d.get("subList");
					for(ESendHtEntity eSendHtEntity : sub){
						content += "      <tr style='height: 32px;' >" +
								"            <td align='center'  width='120'><a href='"+sendUrl+"&state=1&mbbh="+eSendHtEntity.getMbbh()+"&workNo="+eSendHtEntity.getWorkNo()+"&manger="+eSendHtEntity.getManger()+"'>确认续签</a> | <a href='"+sendUrl+"&state=2&mbbh="+eSendHtEntity.getMbbh()+"&workNo="+eSendHtEntity.getWorkNo()+"&manger="+eSendHtEntity.getManger()+"''>拒绝</a></td>" +
								"            <td align='center'  width='120'>"+eSendHtEntity.getWorkNo()+"</td>" +
								"            <td align='center'  width='120'>"+eSendHtEntity.getUserName()+"</td>" +
								"            <td align='center'  width='120'>"+eSendHtEntity.getXqqx()+"</td>" +
								"        </tr>";
					}

					content += "</table></div></html>";
					SendMailUtil.sendCommonMail(d.get("email").toString(),eSendModelEntity.getTitle(),content);
					Utils.sendSMS(eSmsModelEntity.getContent().replaceAll("xxx",d.get("manger").toString()),d.get("manger_telphone").toString());
					eSmsRecordEntity.setRealName(d.get("manger").toString());
					eSmsRecordEntity.setTelphone(d.get("manger_telphone").toString());
					eSmsRecordEntity.setContent(eSmsModelEntity.getContent().replaceAll("xxx",d.get("manger").toString()));
					eSmsRecordEntity.setSendTime(DateUtil.getCurrentTimeString(null));
					eSmsRecordEntity.setState("0");
					systemService.save(eSmsRecordEntity);
					for(ESendHtEntity eSendHtEntity : sub){
						eSendHtEntity.setSendState("1");
						eSendHtEntity.setSendTime(DateUtil.getCurrentTimeString(null));
					}
				}
			}

			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "发送邮箱数据失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加邮箱数据
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ESendHtEntity eSendHt, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "邮箱数据添加成功";
		try{
			eSendHtService.save(eSendHt);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "邮箱数据添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新邮箱数据
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ESendHtEntity eSendHt, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "邮箱数据更新成功";
		ESendHtEntity t = eSendHtService.get(ESendHtEntity.class, eSendHt.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(eSendHt, t);
			eSendHtService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "邮箱数据更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新邮箱数据
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate2")
	@ResponseBody
	public AjaxJson doUpdate2(ESendHtEntity eSendHt, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "续签确认提交成功";
		ESendHtEntity t = eSendHtService.get(ESendHtEntity.class, eSendHt.getId());
		try {
			if("0".equals(t.getState())){
				eSendHt.setReplyTime(DateUtil.getCurrentTimeString(null));
				MyBeanUtils.copyBeanNotNull2Bean(eSendHt, t);
				eSendHtService.saveOrUpdate(t);
			}else{
				j.setSuccess(false);
				j.setMsg("续签确认已提交无法修改");
				return j;
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "续签确认提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 邮箱数据新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ESendHtEntity eSendHt, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(eSendHt.getId())) {
			eSendHt = eSendHtService.getEntity(ESendHtEntity.class, eSendHt.getId());
			req.setAttribute("eSendHtPage", eSendHt);
		}
		return new ModelAndView("com/emk/email/esendht/eSendHt-add");
	}
	/**
	 * 邮箱数据编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(ESendHtEntity eSendHt, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(eSendHt.getId())) {
			eSendHt = eSendHtService.getEntity(ESendHtEntity.class, eSendHt.getId());
			req.setAttribute("eSendHtPage", eSendHt);
		}
		return new ModelAndView("com/emk/email/esendht/eSendHt-update");
	}

	/**
	 * 邮箱数据编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goApproval")
	public ModelAndView goApproval(ESendHtEntity eSendHt, HttpServletRequest req) {
		eSendHt = systemService.singleResult("from ESendHtEntity where workNo='"+eSendHt.getWorkNo()+"' and manger='"+eSendHt.getManger()+"'");
		req.setAttribute("eSendHtPage", eSendHt);
		return new ModelAndView("com/emk/email/esendht/eSendHt-approval");
	}

	/**
	 * 邮箱数据编拒绝
	 *
	 * @return
	 */
	@RequestMapping(params = "goCancel")
	public ModelAndView goCancel(ESendHtEntity eSendHt, HttpServletRequest req) {
		eSendHt = systemService.singleResult("from ESendHtEntity where workNo='"+eSendHt.getWorkNo()+"' and manger='"+eSendHt.getManger()+"'");
		try {
			if("0".equals(eSendHt.getState())){
				eSendHt.setReplyTime(DateUtil.getCurrentTimeString(null));
				eSendHt.setState("2");
				systemService.saveOrUpdate(eSendHt);
				req.setAttribute("msg","不与其续签，请在2019年11月15日之前，登陆mypo.mars.com提交该同事的非自愿离职请求");
			}else{
				req.setAttribute("msg","续签结果已提交无法在线修改，请与MyP&O热线联系");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return new ModelAndView("com/emk/email/esendht/eSendHt-cancel");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","eSendHtController");
		return new ModelAndView("com/emk/email/esendht/uploadView");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(ESendHtEntityA eSendHt,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(ESendHtEntityA.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, eSendHt, request.getParameterMap());
		List<ESendHtEntityA> eSendHts = this.eSendHtService.getListByCriteriaQuery(cq,false);

		modelMap.put(NormalExcelConstants.FILE_NAME,"邮箱数据");
		modelMap.put(NormalExcelConstants.CLASS,ESendHtEntityA.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("邮箱数据列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,eSendHts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ESendHtEntity eSendHt,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"邮箱数据");
    	modelMap.put(NormalExcelConstants.CLASS,ESendHtEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("邮箱数据列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(ESendHtEntity eSendHt,String fileName,String fileNameUrl,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		String message = "文件导入成功";
		File newfile = null;
		HSSFWorkbook wb = null;
		String cellValue = "";

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
			ESendHtEntity eSendHtEntity = null;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				itemValue = new ArrayList<String>();
				for(int z = 0; z <= 6 ; z++){
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
				if(Utils.notEmpty(itemValue.get(0))){
					//systemService.save(emkSizeTotalEntity);
					eSendHtEntity = systemService.singleResult("from ESendHtEntity where workNo='"+itemValue.get(0)+"' and manger='"+itemValue.get(4)+"'");
					if(Utils.isEmpty(eSendHtEntity)){
						eSendHt = new ESendHtEntity();
						eSendHt.setWorkNo(itemValue.get(0));
						eSendHt.setUserName(itemValue.get(1));
						eSendHt.setXqqx(itemValue.get(2));
						eSendHt.setMbbh(itemValue.get(3));
						eSendHt.setManger(itemValue.get(4));
						eSendHt.setEmail(itemValue.get(5));
						eSendHt.setMangerTelphone(itemValue.get(6));
						eSendHt.setSendState("0");
						eSendHt.setState("0");
						systemService.save(eSendHt);
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
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="邮箱数据列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<ESendHtEntity>> list() {
		List<ESendHtEntity> listESendHts=eSendHtService.getList(ESendHtEntity.class);
		return Result.success(listESendHts);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取邮箱数据信息",notes="根据ID获取邮箱数据信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		ESendHtEntity task = eSendHtService.get(ESendHtEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取邮箱数据信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建邮箱数据")
	public ResponseMessage<?> create(@ApiParam(name="邮箱数据对象")@RequestBody ESendHtEntity eSendHt, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<ESendHtEntity>> failures = validator.validate(eSendHt);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			eSendHtService.save(eSendHt);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("邮箱数据信息保存失败");
		}
		return Result.success(eSendHt);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新邮箱数据",notes="更新邮箱数据")
	public ResponseMessage<?> update(@ApiParam(name="邮箱数据对象")@RequestBody ESendHtEntity eSendHt) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<ESendHtEntity>> failures = validator.validate(eSendHt);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			eSendHtService.saveOrUpdate(eSendHt);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新邮箱数据信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新邮箱数据信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除邮箱数据")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			eSendHtService.deleteEntityById(ESendHtEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("邮箱数据删除失败");
		}

		return Result.success();
	}
}
