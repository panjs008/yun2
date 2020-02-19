package com.emk.product.productprice.controller;
import com.emk.product.product.entity.EmkProductEntity;
import com.emk.product.productprice.entity.EmkProductPriceEntity;
import com.emk.product.productprice.service.EmkProductPriceServiceI;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
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
 * @Description: 零售价表
 * @author onlineGenerator
 * @date 2019-12-23 22:25:10
 * @version V1.0   
 *
 */
@Api(value="EmkProductPrice",description="零售价表",tags="emkProductPriceController")
@Controller
@RequestMapping("/emkProductPriceController")
public class EmkProductPriceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkProductPriceController.class);

	@Autowired
	private EmkProductPriceServiceI emkProductPriceService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 零售价表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/product/productprice/emkProductPriceList");
	}
	@RequestMapping(params = "select")
	public ModelAndView select(HttpServletRequest request) {
		return new ModelAndView("com/emk/product/productprice/emkProductPriceList-select");
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
	public void datagrid(EmkProductPriceEntity emkProductPrice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkProductPriceEntity.class, dataGrid);
		//查询条件组装器
		TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkProductPrice, request.getParameterMap());
		try{
		//自定义追加查询条件
			//cq.add(Restrictions.sqlRestriction("({alias}.product_id='' or {alias}.product_id is null)"));
			if(Utils.notEmpty(request.getSession().getAttribute("productId"))){
				cq.eq("productId",request.getSession().getAttribute("productId").toString());
			}else{
				cq.add(Restrictions.sqlRestriction("({alias}.product_id='-1')"));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkProductPriceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除零售价表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkProductPriceEntity emkProductPrice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkProductPrice = systemService.getEntity(EmkProductPriceEntity.class, emkProductPrice.getId());
		message = "零售价表删除成功";
		try{
			emkProductPriceService.delete(emkProductPrice);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售价表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除零售价表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "零售价表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkProductPriceEntity emkProductPrice = systemService.getEntity(EmkProductPriceEntity.class, 
				id
				);
				emkProductPriceService.delete(emkProductPrice);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "零售价表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加零售价表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkProductPriceEntity emkProductPrice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "零售价表添加成功";
		try{
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
			emkProductPrice.setOrgCode(tsDepart.getOrgCode());
			emkProductPrice.setDepartId(tsDepart.getId());

			/*Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(price_no, 3)),0)+1 AS signed) orderNum from emk_product_price where org_code=? and (product_id is null or product_id ='')",user.getCurrentDepart().getOrgCode().substring(0,3));
			emkProductPrice.setPriceNo(String.format("%03d", Integer.valueOf(orderNum.get("orderNum").toString())));*/

			emkProductPriceService.save(emkProductPrice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售价表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "setPriceV")
	@ResponseBody
	public AjaxJson setPriceV(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try{
			Map p = ParameterUtil.getParamMaps(request.getParameterMap());
			String proNum = p.get("proNum").toString();
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			EmkProductEntity emkProductEntity = systemService.singleResult("from EmkProductEntity where proNum='"+proNum+"'" +
					" and orgCode='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"'");
			if(Utils.notEmpty(emkProductEntity)){
				String productId = emkProductEntity.getId();
				request.getSession().setAttribute("productId",productId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新零售价表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkProductPriceEntity emkProductPrice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "零售价表更新成功";
		EmkProductPriceEntity t = emkProductPriceService.get(EmkProductPriceEntity.class, emkProductPrice.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(emkProductPrice, t);
			emkProductPriceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "零售价表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 零售价表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkProductPriceEntity emkProductPrice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkProductPrice.getId())) {
			emkProductPrice = emkProductPriceService.getEntity(EmkProductPriceEntity.class, emkProductPrice.getId());
			req.setAttribute("emkProductPricePage", emkProductPrice);
		}
		return new ModelAndView("com/emk/product/productprice/emkProductPrice-add");
	}
	/**
	 * 零售价表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkProductPriceEntity emkProductPrice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkProductPrice.getId())) {
			emkProductPrice = emkProductPriceService.getEntity(EmkProductPriceEntity.class, emkProductPrice.getId());
			req.setAttribute("emkProductPricePage", emkProductPrice);
		}
		return new ModelAndView("com/emk/product/productprice/emkProductPrice-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkProductPriceController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkProductPriceEntity emkProductPrice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkProductPriceEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkProductPrice, request.getParameterMap());
		List<EmkProductPriceEntity> emkProductPrices = this.emkProductPriceService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"零售价表");
		modelMap.put(NormalExcelConstants.CLASS,EmkProductPriceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("零售价表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkProductPrices);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkProductPriceEntity emkProductPrice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"零售价表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkProductPriceEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("零售价表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<EmkProductPriceEntity> listEmkProductPriceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),EmkProductPriceEntity.class,params);
				for (EmkProductPriceEntity emkProductPrice : listEmkProductPriceEntitys) {
					emkProductPriceService.save(emkProductPrice);
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
	@ApiOperation(value="零售价表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkProductPriceEntity>> list() {
		List<EmkProductPriceEntity> listEmkProductPrices=emkProductPriceService.getList(EmkProductPriceEntity.class);
		return Result.success(listEmkProductPrices);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取零售价表信息",notes="根据ID获取零售价表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkProductPriceEntity task = emkProductPriceService.get(EmkProductPriceEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取零售价表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建零售价表")
	public ResponseMessage<?> create(@ApiParam(name="零售价表对象")@RequestBody EmkProductPriceEntity emkProductPrice, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkProductPriceEntity>> failures = validator.validate(emkProductPrice);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkProductPriceService.save(emkProductPrice);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("零售价表信息保存失败");
		}
		return Result.success(emkProductPrice);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新零售价表",notes="更新零售价表")
	public ResponseMessage<?> update(@ApiParam(name="零售价表对象")@RequestBody EmkProductPriceEntity emkProductPrice) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkProductPriceEntity>> failures = validator.validate(emkProductPrice);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkProductPriceService.saveOrUpdate(emkProductPrice);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新零售价表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新零售价表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除零售价表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkProductPriceService.deleteEntityById(EmkProductPriceEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("零售价表删除失败");
		}

		return Result.success();
	}
}
