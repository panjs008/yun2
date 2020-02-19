package com.emk.storage.sampledetail.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.storage.sampledetail.entity.EmkSampleDetailEntity;
import com.emk.storage.sampledetail.service.EmkSampleDetailServiceI;
import com.emk.util.ParameterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

@Api(value = "EmkSampleDetail", description = "样品单明细", tags = "emkSampleDetailController")
@Controller
@RequestMapping("/emkSampleDetailController")
public class EmkSampleDetailController extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkSampleDetailController.class);
    @Autowired
    private EmkSampleDetailServiceI emkSampleDetailService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {

        return new ModelAndView("com/emk/storage/sampledetail/emkSampleDetailList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkSampleDetailEntity emkSampleDetail, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkSampleDetailEntity.class, dataGrid);

        HqlGenerateUtil.installHql(cq, emkSampleDetail, request.getParameterMap());


        cq.add();
        this.emkSampleDetailService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkSampleDetailEntity emkSampleDetail, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkSampleDetail = (EmkSampleDetailEntity) this.systemService.getEntity(EmkSampleDetailEntity.class, emkSampleDetail.getId());
        message = "样品单明细删除成功";
        try {
            this.emkSampleDetailService.delete(emkSampleDetail);
            this.systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "样品单明细删除失败";
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
        message = "样品单明细删除成功";
        try {
            for (String id : ids.split(",")) {
                EmkSampleDetailEntity emkSampleDetail = (EmkSampleDetailEntity) this.systemService.getEntity(EmkSampleDetailEntity.class, id);


                this.emkSampleDetailService.delete(emkSampleDetail);
                this.systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "样品单明细删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkSampleDetailEntity emkSampleDetail, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "样品单明细添加成功";
        try {
            Map map = ParameterUtil.getParamMaps(request.getParameterMap());
            emkSampleDetail.setProId(emkSampleDetail.getId());
            this.emkSampleDetailService.save(emkSampleDetail);
            this.systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "样品单明细添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkSampleDetailEntity emkSampleDetail, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "样品单明细更新成功";
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());

        EmkSampleDetailEntity t = (EmkSampleDetailEntity) this.emkSampleDetailService.get(EmkSampleDetailEntity.class, map.get("detailId").toString());
        try {
            emkSampleDetail.setProId(emkSampleDetail.getId());
            MyBeanUtils.copyBeanNotNull2Bean(emkSampleDetail, t);
            this.emkSampleDetailService.saveOrUpdate(t);
            this.systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "样品单明细更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkSampleDetailEntity emkSampleDetail, HttpServletRequest req, String sampleType) {

        return new ModelAndView("com/emk/storage/sampledetail/emkSampleDetail-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkSampleDetailEntity emkSampleDetail, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkSampleDetail.getId())) {
            emkSampleDetail = (EmkSampleDetailEntity) this.emkSampleDetailService.getEntity(EmkSampleDetailEntity.class, emkSampleDetail.getId());
            req.setAttribute("emkSampleDetailPage", emkSampleDetail);
        }
        return new ModelAndView("com/emk/storage/sampledetail/emkSampleDetail-update");
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "emkSampleDetailController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkSampleDetailEntity emkSampleDetail, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkSampleDetailEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkSampleDetail, request.getParameterMap());
        List<EmkSampleDetailEntity> emkSampleDetails = this.emkSampleDetailService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "样品单明细");
        modelMap.put("entity", EmkSampleDetailEntity.class);
        modelMap.put("params", new ExportParams("样品单明细列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkSampleDetails);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkSampleDetailEntity emkSampleDetail, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "样品单明细");
        modelMap.put("entity", EmkSampleDetailEntity.class);
        modelMap.put("params", new ExportParams("样品单明细列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "样品单明细列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkSampleDetailEntity>> list() {
        List<EmkSampleDetailEntity> listEmkSampleDetails = this.emkSampleDetailService.getList(EmkSampleDetailEntity.class);
        return Result.success(listEmkSampleDetails);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取样品单明细信息", notes = "根据ID获取样品单明细信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkSampleDetailEntity task = (EmkSampleDetailEntity) this.emkSampleDetailService.get(EmkSampleDetailEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取样品单明细信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建样品单明细")
    public ResponseMessage<?> create(@ApiParam(name = "样品单明细对象") @RequestBody EmkSampleDetailEntity emkSampleDetail, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkSampleDetailEntity>> failures = this.validator.validate(emkSampleDetail, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            this.emkSampleDetailService.save(emkSampleDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("样品单明细信息保存失败");
        }
        return Result.success(emkSampleDetail);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新样品单明细", notes = "更新样品单明细")
    public ResponseMessage<?> update(@ApiParam(name = "样品单明细对象") @RequestBody EmkSampleDetailEntity emkSampleDetail) {
        Set<ConstraintViolation<EmkSampleDetailEntity>> failures = this.validator.validate(emkSampleDetail, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            this.emkSampleDetailService.saveOrUpdate(emkSampleDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新样品单明细信息失败");
        }
        return Result.success("更新样品单明细信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除样品单明细")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            this.emkSampleDetailService.deleteEntityById(EmkSampleDetailEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("样品单明细删除失败");
        }
        return Result.success();
    }
}
