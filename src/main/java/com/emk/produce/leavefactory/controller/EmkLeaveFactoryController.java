package com.emk.produce.leavefactory.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.produce.leavefactory.entity.EmkLeaveFactoryEntity;
import com.emk.produce.leavefactory.service.EmkLeaveFactoryServiceI;
import com.emk.produce.produce.entity.EmkProduceEntity;
import com.emk.util.ApprovalUtil;
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
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.activiti.engine.task.Task;
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

@Api(value = "EmkLeaveFactory", description = "离厂通知单", tags = "emkLeaveFactoryController")
@Controller
@RequestMapping("/emkLeaveFactoryController")
public class EmkLeaveFactoryController extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkLeaveFactoryController.class);
    @Autowired
    private EmkLeaveFactoryServiceI emkLeaveFactoryService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/emk/produce/leavefactory/emkLeaveFactoryList");
    }

    @RequestMapping(params = "detailMxList")
    public ModelAndView detailMxList(HttpServletRequest request) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        if (Utils.notEmpty(map.get("levalFactoryId"))) {
            List<EmkProOrderDetailEntity> emkProOrderDetailEntities = systemService.findHql("from EmkProOrderDetailEntity where proOrderId=?", map.get("levalFactoryId"));
            request.setAttribute("emkProOrderDetailEntities", emkProOrderDetailEntities);
        }
        return new ModelAndView("com/emk/produce/leavefactory/detailMxList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkLeaveFactoryEntity.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        /*Map roleMap = (Map) request.getSession().getAttribute("ROLE");
        if(roleMap != null){
            if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("ywgdy")|| roleMap.get("rolecode").toString().contains("scgdy")){
                cq.eq("createBy",user.getUserName());
            }
        }*/
        HqlGenerateUtil.installHql(cq, emkLeaveFactory, request.getParameterMap());


        cq.add();
        emkLeaveFactoryService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkLeaveFactory = systemService.getEntity(EmkLeaveFactoryEntity.class, emkLeaveFactory.getId());
        message = "离厂通知单删除成功";
        try {
            emkLeaveFactoryService.delete(emkLeaveFactory);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "离厂通知单删除失败";
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
        message = "离厂通知单删除成功";
        try {
            for (String id : ids.split(",")) {
                EmkLeaveFactoryEntity emkLeaveFactory = systemService.getEntity(EmkLeaveFactoryEntity.class, id);
                systemService.executeSql("delete from emk_pro_order_detail where pro_order_id = ? ",emkLeaveFactory.getId());
                emkLeaveFactoryService.delete(emkLeaveFactory);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "离厂通知单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "离厂通知单添加成功";
        try {
            TSUser user = (TSUser) request.getSession().getAttribute("LOCAL_CLINET_USER");
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(LEAVE_FACTORY_NO, 3)),0)+1 AS signed) orderNum from emk_leave_factory");
            emkLeaveFactory.setLeaveFactoryNo("L" + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));
            emkLeaveFactoryService.save(emkLeaveFactory);

            //保存明细数据
            String dataRows = (String) map.get("orderMxListIDSR");
            if (Utils.notEmpty(dataRows)) {
                systemService.executeSql("delete from emk_pro_order_detail where pro_order_id = ? ",emkLeaveFactory.getId());

                int rows = Integer.parseInt(dataRows);
                EmkProOrderDetailEntity proOrderDetailEntity = null;
                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))){
                        proOrderDetailEntity = new EmkProOrderDetailEntity();
                        proOrderDetailEntity.setProOrderId(emkLeaveFactory.getId());

                        systemService.save(proOrderDetailEntity);
                    }
                }
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "离厂通知单添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "离厂通知单更新成功";
        Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
        EmkLeaveFactoryEntity t = emkLeaveFactoryService.get(EmkLeaveFactoryEntity.class, map.get("levalFactoryId"));
        try {
            if(!t.getState().equals("0")){
                j.setMsg("离厂通知单已提交审核，无法编辑修改");
                j.setSuccess(false);
                return j;
            }
            emkLeaveFactory.setId(null);
            MyBeanUtils.copyBeanNotNull2Bean(emkLeaveFactory, t);
            emkLeaveFactoryService.saveOrUpdate(t);

            //保存明细数据
            String dataRows = (String) map.get("orderMxListIDSR");
            if (Utils.notEmpty(dataRows)) {
                systemService.executeSql("delete from emk_pro_order_detail where pro_order_id = ? ",t.getId());

                int rows = Integer.parseInt(dataRows);
                EmkProOrderDetailEntity proOrderDetailEntity = null;
                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))){
                        proOrderDetailEntity = new EmkProOrderDetailEntity();
                        proOrderDetailEntity.setProOrderId(t.getId());

                        systemService.save(proOrderDetailEntity);
                    }
                }
            }
            if("1".equals(map.get("isPass"))) {
                EmkProduceEntity emkProduceEntity = systemService.get(EmkProduceEntity.class,t.getProduceId());
                List<Task> task = taskService.createTaskQuery().taskAssignee(emkProduceEntity.getId()).list();
                Task task1 = (Task) task.get(task.size() - 1);
                TSUser user = (TSUser) request.getSession().getAttribute("LOCAL_CLINET_USER");
                Map<String, Object> variables = new HashMap();
                EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class, "formId", emkProduceEntity.getId());
                EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(), user, b, "【生产跟单员】离厂通知单");
                if (task1.getTaskDefinitionKey().equals("lctzdTask")) {
                    taskService.complete(task1.getId(), variables);
                    emkProduceEntity.setState("84");
                    b.setStatus(84);
                    saveApprvoalDetail(approvalDetail,"【生产跟单员】离厂通知单","cktzdTask",0,"【生产跟单员】离厂通知单");
                    systemService.saveOrUpdate(approvalDetail);
                    saveSmsAndEmailForMany("财务员","【生产跟单员】离厂通知单","您有【"+b.getCreateName()+"】提交的离厂通知单，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                    t.setState("1");
                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "离厂通知单更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        if (StringUtil.isNotEmpty(emkLeaveFactory.getId())) {
            emkLeaveFactory = emkLeaveFactoryService.getEntity(EmkLeaveFactoryEntity.class, emkLeaveFactory.getId());
            req.setAttribute("emkLeaveFactoryPage", emkLeaveFactory);
        }
        return new ModelAndView("com/emk/produce/leavefactory/emkLeaveFactory-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkLeaveFactory.getId())) {
            emkLeaveFactory = emkLeaveFactoryService.getEntity(EmkLeaveFactoryEntity.class, emkLeaveFactory.getId());
            req.setAttribute("emkLeaveFactoryPage", emkLeaveFactory);
        }
        return new ModelAndView("com/emk/produce/leavefactory/emkLeaveFactory-update");
    }

    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkLeaveFactory.getId())) {
            EmkProduceEntity emkProduceEntity = systemService.get(EmkProduceEntity.class,emkLeaveFactory.getId());
            emkLeaveFactory = systemService.findUniqueByProperty(EmkLeaveFactoryEntity.class,"produceId",emkProduceEntity.getId());
            req.setAttribute("emkLeaveFactoryPage", emkLeaveFactory);
        }

        return new ModelAndView("com/emk/produce/leavefactory/emkLeaveFactory-update2");

    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "emkLeaveFactoryController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkLeaveFactoryEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkLeaveFactory, request.getParameterMap());
        List<EmkLeaveFactoryEntity> emkLeaveFactorys = emkLeaveFactoryService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "离厂通知单");
        modelMap.put("entity", EmkLeaveFactoryEntity.class);
        modelMap.put("params", new ExportParams("离厂通知单列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkLeaveFactorys);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkLeaveFactoryEntity emkLeaveFactory, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "离厂通知单");
        modelMap.put("entity", EmkLeaveFactoryEntity.class);
        modelMap.put("params", new ExportParams("离厂通知单列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "离厂通知单列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkLeaveFactoryEntity>> list() {
        List<EmkLeaveFactoryEntity> listEmkLeaveFactorys = emkLeaveFactoryService.getList(EmkLeaveFactoryEntity.class);
        return Result.success(listEmkLeaveFactorys);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取离厂通知单信息", notes = "根据ID获取离厂通知单信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkLeaveFactoryEntity task = emkLeaveFactoryService.get(EmkLeaveFactoryEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取离厂通知单信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建离厂通知单")
    public ResponseMessage<?> create(@ApiParam(name = "离厂通知单对象") @RequestBody EmkLeaveFactoryEntity emkLeaveFactory, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkLeaveFactoryEntity>> failures = validator.validate(emkLeaveFactory, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkLeaveFactoryService.save(emkLeaveFactory);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("离厂通知单信息保存失败");
        }
        return Result.success(emkLeaveFactory);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新离厂通知单", notes = "更新离厂通知单")
    public ResponseMessage<?> update(@ApiParam(name = "离厂通知单对象") @RequestBody EmkLeaveFactoryEntity emkLeaveFactory) {
        Set<ConstraintViolation<EmkLeaveFactoryEntity>> failures = validator.validate(emkLeaveFactory, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkLeaveFactoryService.saveOrUpdate(emkLeaveFactory);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新离厂通知单信息失败");
        }
        return Result.success("更新离厂通知单信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除离厂通知单")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            emkLeaveFactoryService.deleteEntityById(EmkLeaveFactoryEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("离厂通知单删除失败");
        }
        return Result.success();
    }
}
