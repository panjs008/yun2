package com.emk.bill.offerprice.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.bill.offerprice.entity.EmkOfferPriceEntity;
import com.emk.bill.offerprice.service.EmkOfferPriceServiceI;
import com.emk.bill.offerpricedetail.entity.EmkOfferPriceDetailEntity;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import com.emk.util.DateUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Api(value = "EmkOfferPrice", description = "报价方案", tags = "emkOfferPriceController")
@Controller
@RequestMapping("/emkOfferPriceController")
public class EmkOfferPriceController
        extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkOfferPriceController.class);
    @Autowired
    private EmkOfferPriceServiceI emkOfferPriceService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A13' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("headcategoryEntities",categoryEntities);
        return new ModelAndView("com/emk/bill/offerprice/emkOfferPriceList");
    }

    @RequestMapping(params = "offerMxList")
    public ModelAndView offerMxList(HttpServletRequest request) {
        request.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show from hm_category where PARENT_CODE='A01A09' and type='5' and org_code=? and code!='A01A09A13' order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("categoryEntities",categoryEntities);

        categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("productcategoryEntities",categoryEntities);
        String columns = "";
        for(Map m : categoryEntities){
            if(Utils.notEmpty(m)){
                columns += m.get("code")+",";
            }
        }
        if(columns.indexOf(",")>0){
            columns = columns.substring(0,columns.length()-1);
        }
        request.setAttribute("columns",columns);
        if (Utils.notEmpty(map.get("offerPriceId"))) {
            List<EmkOfferPriceDetailEntity> rkglMxEntities = systemService.findHql("from EmkOfferPriceDetailEntity where offerPriceId=? ",  map.get("offerPriceId"));
            request.setAttribute("rkglMxList", rkglMxEntities);
        }
        return new ModelAndView("com/emk/bill/offerprice/offerMxList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkOfferPriceEntity emkOfferPrice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkOfferPriceEntity.class, dataGrid);

        HqlGenerateUtil.installHql(cq, emkOfferPrice, request.getParameterMap());


        cq.add();
        this.emkOfferPriceService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkOfferPriceEntity emkOfferPrice, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkOfferPrice = (EmkOfferPriceEntity) this.systemService.getEntity(EmkOfferPriceEntity.class, emkOfferPrice.getId());
        message = "报价方案删除成功";
        try {
            this.emkOfferPriceService.delete(emkOfferPrice);
            this.systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "报价方案删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "报价方案删除成功";
        try {
            for (String id : ids.split(",")) {
                EmkOfferPriceEntity emkOfferPrice = (EmkOfferPriceEntity) this.systemService.getEntity(EmkOfferPriceEntity.class, id);


                this.systemService.executeSql("delete from emk_offer_price_detail where OFFER_PRICE_ID=?", new Object[]{emkOfferPrice.getId()});
                this.emkOfferPriceService.delete(emkOfferPrice);
                this.systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "报价方案删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkOfferPriceEntity emkOfferPrice, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "报价方案添加成功";
        try {
            this.emkOfferPriceService.save(emkOfferPrice);
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            String dataRows = (String) map.get("dataRowsVal");
            if ((dataRows != null) && (!dataRows.isEmpty())) {
                int rows = Integer.parseInt(dataRows);
                for (int i = 0; i < rows; i++) {
                    EmkOfferPriceDetailEntity offerPriceDetailEntity = new EmkOfferPriceDetailEntity();
                    if ((map.get("offerMxList[" + i + "].proName") != null) && (!((String) map.get("offerMxList[" + i + "].proName")).equals(""))) {
                        offerPriceDetailEntity.setOfferPriceId(emkOfferPrice.getId());

                        this.systemService.save(offerPriceDetailEntity);
                    }
                }
            }
            this.systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "报价方案添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkOfferPriceEntity emkOfferPrice, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "报价方案更新成功";
        EmkOfferPriceEntity t = (EmkOfferPriceEntity) this.emkOfferPriceService.get(EmkOfferPriceEntity.class, emkOfferPrice.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(emkOfferPrice, t);
            this.emkOfferPriceService.saveOrUpdate(t);
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            String dataRows = (String) map.get("dataRowsVal");
            this.systemService.executeSql("delete from emk_offer_price_detail where OFFER_PRICE_ID=?", new Object[]{emkOfferPrice.getId()});
            if ((dataRows != null) && (!dataRows.isEmpty())) {
                int rows = Integer.parseInt(dataRows);
                for (int i = 0; i < rows; i++) {
                    EmkOfferPriceDetailEntity offerPriceDetailEntity = new EmkOfferPriceDetailEntity();
                    if ((map.get("offerMxList[" + i + "].proName") != null) && (!((String) map.get("offerMxList[" + i + "].proName")).equals(""))) {
                        offerPriceDetailEntity.setOfferPriceId(emkOfferPrice.getId());

                        this.systemService.save(offerPriceDetailEntity);
                    }
                }
            }
            this.systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "报价方案更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkOfferPriceEntity emkOfferPrice, HttpServletRequest req) {
        TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);

        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A13' and org_code=? and is_show=0 order by order_num asc",orgCode);
        req.setAttribute("headcategoryEntities",categoryEntities);
        TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",orgCode);
        emkOfferPrice.setA01a13a03(DateUtil.format(new Date(), "yyyy-MM-dd"));
        EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='报价单号前缀' and departId='"+tsDepart.getId()+"'");

        Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(A01A13a07, 4)),0)+1 AS signed) orderNum from emk_offer_price where org_code=?",orgCode);
        emkOfferPrice.setA01a13a01(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
        req.setAttribute("emkOfferPricePage", emkOfferPrice);

        return new ModelAndView("com/emk/bill/offerprice/emkOfferPrice-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkOfferPriceEntity emkOfferPrice, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkOfferPrice.getId())) {
            emkOfferPrice = (EmkOfferPriceEntity) this.emkOfferPriceService.getEntity(EmkOfferPriceEntity.class, emkOfferPrice.getId());
            req.setAttribute("emkOfferPricePage", emkOfferPrice);
        }
        return new ModelAndView("com/emk/bill/offerprice/emkOfferPrice-update");
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "emkOfferPriceController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkOfferPriceEntity emkOfferPrice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkOfferPriceEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkOfferPrice, request.getParameterMap());
        List<EmkOfferPriceEntity> emkOfferPrices = this.emkOfferPriceService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "报价方案");
        modelMap.put("entity", EmkOfferPriceEntity.class);
        modelMap.put("params", new ExportParams("报价方案列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkOfferPrices);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkOfferPriceEntity emkOfferPrice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "报价方案");
        modelMap.put("entity", EmkOfferPriceEntity.class);
        modelMap.put("params", new ExportParams("报价方案列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "报价方案列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkOfferPriceEntity>> list() {
        List<EmkOfferPriceEntity> listEmkOfferPrices = this.emkOfferPriceService.getList(EmkOfferPriceEntity.class);
        return Result.success(listEmkOfferPrices);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取报价方案信息", notes = "根据ID获取报价方案信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkOfferPriceEntity task = (EmkOfferPriceEntity) this.emkOfferPriceService.get(EmkOfferPriceEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取报价方案信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建报价方案")
    public ResponseMessage<?> create(@ApiParam(name = "报价方案对象") @RequestBody EmkOfferPriceEntity emkOfferPrice, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkOfferPriceEntity>> failures = this.validator.validate(emkOfferPrice, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            this.emkOfferPriceService.save(emkOfferPrice);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("报价方案信息保存失败");
        }
        return Result.success(emkOfferPrice);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新报价方案", notes = "更新报价方案")
    public ResponseMessage<?> update(@ApiParam(name = "报价方案对象") @RequestBody EmkOfferPriceEntity emkOfferPrice) {
        Set<ConstraintViolation<EmkOfferPriceEntity>> failures = this.validator.validate(emkOfferPrice, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            this.emkOfferPriceService.saveOrUpdate(emkOfferPrice);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新报价方案信息失败");
        }
        return Result.success("更新报价方案信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除报价方案")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            this.emkOfferPriceService.deleteEntityById(EmkOfferPriceEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("报价方案删除失败");
        }
        return Result.success();
    }
}
