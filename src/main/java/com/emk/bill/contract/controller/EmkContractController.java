package com.emk.bill.contract.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.bill.contract.entity.EmkContractEntity;
import com.emk.bill.contract.service.EmkContractServiceI;
import com.emk.bill.proorder.entity.EmkProOrderEntity;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;

import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;

import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.activiti.engine.*;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Api(value = "EmkContract", description = "购销合同", tags = "emkContractController")
@Controller
@RequestMapping("/emkContractController")
public class EmkContractController extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkContractController.class);
    @Autowired
    private EmkContractServiceI emkContractService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    @Autowired
    ProcessEngine processEngine;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;


    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/emk/bill/contract/emkContractList");
    }

    @RequestMapping(params = "orderMxList")
    public ModelAndView orderMxList(HttpServletRequest request) {
        List<Map<String, Object>> codeList = systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
        request.setAttribute("categoryEntityList", codeList);
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        if ((map.get("proOrderId") != null) && (!map.get("proOrderId").equals(""))) {
            List<EmkEnquiryDetailEntity> emkProOrderDetailEntities = systemService.findHql("from EmkEnquiryDetailEntity where enquiryId=?", map.get("proOrderId"));
            request.setAttribute("emkProOrderDetailEntities", emkProOrderDetailEntities);
        }
        return new ModelAndView("com/emk/bill/contract/orderMxList");
    }

    @RequestMapping(params = "detailMxList")
    public ModelAndView detailMxList(HttpServletRequest request) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        /*List<Map<String, Object>> list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
        request.setAttribute("colorList", list);*/
        List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(remark,',',typecode) typecode,typecode code,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
        String color = JSONHelper.collection2json(list);
        request.setAttribute("color", "'"+color+ "'");
        if (Utils.notEmpty(map.get("proOrderId"))) {
            List<EmkProOrderDetailEntity> emkProOrderDetailEntities = systemService.findHql("from EmkProOrderDetailEntity where proOrderId=?", map.get("proOrderId"));
            request.setAttribute("emkProOrderDetailEntities", emkProOrderDetailEntities);
            EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("proOrderId"));
            request.setAttribute("emkSizePage", emkSizeEntity);
        }

        return new ModelAndView("com/emk/bill/contract/detailMxList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkContractEntity emkContract, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkContractEntity.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
       /* Map roleMap = (Map) request.getSession().getAttribute("ROLE");
        if(roleMap != null){
            if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("ywgdy")){
                cq.eq("createBy",user.getUserName());
            }
        }*/
        HqlGenerateUtil.installHql(cq, emkContract, request.getParameterMap());


        cq.add();
        emkContractService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkContractEntity emkContract, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkContract = systemService.getEntity(EmkContractEntity.class, emkContract.getId());
        message = "购销合同删除成功";
        try {
            emkContractService.delete(emkContract);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "购销合同删除失败";
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
        message = "购销合同删除成功";
        try {
            for (String id : ids.split(",")) {
                EmkContractEntity emkContract = systemService.getEntity(EmkContractEntity.class, id);
                systemService.executeSql("delete from emk_enquiry_detail where ENQUIRY_ID=?", id);
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_pro_order_detail where PRO_ORDER_ID=?))", emkContract.getId());
                systemService.executeSql("delete from emk_pro_order_detail where PRO_ORDER_ID=?",emkContract.getId());
                emkContractService.delete(emkContract);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "购销合同删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkContractEntity emkContract, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "购销合同添加成功";
        try {
            emkContractService.save(emkContract);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "购销合同添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkContractEntity emkContract,EmkSizeEntity emkSize, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "购销合同更新成功";
        Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
        EmkContractEntity t = emkContractService.get(EmkContractEntity.class, map.get("pactId"));
        EmkSizeEntity t2 = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",t.getId());

        try {
            emkContract.setId(null);
            MyBeanUtils.copyBeanNotNull2Bean(emkContract, t);
            emkContractService.saveOrUpdate(t);

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

            //保存明细数据
            String dataRows = (String) map.get("orderMxListIDSR");
            if (Utils.notEmpty(dataRows)) {
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_pro_order_detail where PRO_ORDER_ID=?))", t.getId());
                systemService.executeSql("delete from emk_pro_order_detail where PRO_ORDER_ID=?",t.getId());

                int rows = Integer.parseInt(dataRows);
                EmkProOrderDetailEntity proOrderDetailEntity = null;
                EmkSizeTotalEntity emkSizeTotalEntity = null;

                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
                        proOrderDetailEntity = new EmkProOrderDetailEntity();
                        proOrderDetailEntity.setProOrderId(t.getId());

                        if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
                            String[] colorArr = map.get("orderMxList["+i+"].color").split(",");
                        }

                        systemService.save(proOrderDetailEntity);
                        if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
                            emkSizeTotalEntity = new EmkSizeTotalEntity();
                            emkSizeTotalEntity.setTotalA(map.get("orderMxList["+i+"].totalA"));
                            emkSizeTotalEntity.setTotalB(map.get("orderMxList["+i+"].totalB"));
                            emkSizeTotalEntity.setTotalC(map.get("orderMxList["+i+"].totalC"));
                            emkSizeTotalEntity.setTotalD(map.get("orderMxList["+i+"].totalD"));
                            emkSizeTotalEntity.setTotalE(map.get("orderMxList["+i+"].totalE"));
                            emkSizeTotalEntity.setTotalF(map.get("orderMxList["+i+"].totalF"));
                            emkSizeTotalEntity.setTotalG(map.get("orderMxList["+i+"].totalG"));
                            emkSizeTotalEntity.setTotalH(map.get("orderMxList["+i+"].totalH"));
                            emkSizeTotalEntity.setTotalI(map.get("orderMxList["+i+"].totalI"));
                            emkSizeTotalEntity.setTotalJ(map.get("orderMxList["+i+"].totalJ"));
                            emkSizeTotalEntity.setTotalK(map.get("orderMxList["+i+"].totalK"));
                            emkSizeTotalEntity.setpId(proOrderDetailEntity.getId());
                            systemService.save(emkSizeTotalEntity);
                        }
                    }
                }
            }

            if("1".equals(map.get("isPass"))){
                EmkProOrderEntity a = systemService.get(EmkProOrderEntity.class, map.get("orderId"));
                EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",a.getId());

                a.setState("46");
                b.setStatus(46);

                systemService.saveOrUpdate(a);
                systemService.saveOrUpdate(b);
                Map<String, Object> variables = new HashMap();
                List<Task> task = taskService.createTaskQuery().taskAssignee(a.getId()).list();
                if(Utils.notEmpty(task)){
                    Task task1 = task.get(task.size() - 1);
                    if(task1.getTaskDefinitionKey().equals("gxhtTask")) {
                        variables.put("optUser",a.getId());
                        taskService.complete(task1.getId(), variables);
                        TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
                        saveSmsAndEmailForMany("业务经理","【业务员】购销合同","您有【"+b.getCreateName()+"】审核过的订单表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                    }
                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "购销合同更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkContractEntity emkContract, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkContract.getId())) {
            emkContract = emkContractService.getEntity(EmkContractEntity.class, emkContract.getId());
            req.setAttribute("emkContractPage", emkContract);
        }
        return new ModelAndView("com/emk/bill/contract/emkContract-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkContractEntity emkContract, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkContract.getId())) {
            emkContract = emkContractService.getEntity(EmkContractEntity.class, emkContract.getId());
            req.setAttribute("emkContractPage", emkContract);

            try {
                Map countMap = MyBeanUtils.culBeanCounts(emkContract);
                int a=0,b=0;
                a = Integer.parseInt(countMap.get("finishColums").toString());
                b = Integer.parseInt(countMap.get("Colums").toString())-4;

                countMap.put("finishColums",a);
                countMap.put("Colums",b);
                req.setAttribute("countMap", countMap);
                DecimalFormat df = new DecimalFormat("#.00");
                req.setAttribute("recent", df.format(a*100/b));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("com/emk/bill/contract/emkContract-update");
    }

    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(EmkContractEntity emkContract, HttpServletRequest req) {
        EmkProOrderEntity proOrderEntity = systemService.get(EmkProOrderEntity.class,emkContract.getId());
        if(StringUtil.isNotEmpty(emkContract.getId())) {
            req.setAttribute("emkContractPage", emkContract);

            try {
                Map countMap = MyBeanUtils.culBeanCounts(emkContract);
                int a=0,b=0;
                a = Integer.parseInt(countMap.get("finishColums").toString());
                b = Integer.parseInt(countMap.get("Colums").toString())-4;

                countMap.put("finishColums",a);
                countMap.put("Colums",b);
                req.setAttribute("countMap", countMap);
                DecimalFormat df = new DecimalFormat("#.00");
                req.setAttribute("recent", df.format(a*100/b));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<TSUser> userList = systemService.findHql("from TSUser where userKey='业务员'");
        req.setAttribute("ywyList", userList);
        userList = systemService.findHql("from TSUser where userKey='业务跟单员'");
        req.setAttribute("ywgdyList", userList);
        userList = systemService.findHql("from TSUser where userKey='生产跟单员'");
        req.setAttribute("scgdyList", userList);
        return new ModelAndView("com/emk/bill/contract/emkContract-update2");
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "emkContractController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkContractEntity emkContract, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkContractEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkContract, request.getParameterMap());
        List<EmkContractEntity> emkContracts = emkContractService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "购销合同");
        modelMap.put("entity", EmkContractEntity.class);
        modelMap.put("params", new ExportParams("购销合同列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkContracts);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkContractEntity emkContract, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "购销合同");
        modelMap.put("entity", EmkContractEntity.class);
        modelMap.put("params", new ExportParams("购销合同列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }


    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "购销合同列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkContractEntity>> list() {
        List<EmkContractEntity> listEmkContracts = emkContractService.getList(EmkContractEntity.class);
        return Result.success(listEmkContracts);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取购销合同信息", notes = "根据ID获取购销合同信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkContractEntity task = emkContractService.get(EmkContractEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取购销合同信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建购销合同")
    public ResponseMessage<?> create(@ApiParam(name = "购销合同对象") @RequestBody EmkContractEntity emkContract, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkContractEntity>> failures = validator.validate(emkContract, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkContractService.save(emkContract);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("购销合同信息保存失败");
        }
        return Result.success(emkContract);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新购销合同", notes = "更新购销合同")
    public ResponseMessage<?> update(@ApiParam(name = "购销合同对象") @RequestBody EmkContractEntity emkContract) {
        Set<ConstraintViolation<EmkContractEntity>> failures = validator.validate(emkContract, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkContractService.saveOrUpdate(emkContract);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新购销合同信息失败");
        }
        return Result.success("更新购销合同信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除购销合同")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            emkContractService.deleteEntityById(EmkContractEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("购销合同删除失败");
        }
        return Result.success();
    }

    @RequestMapping(params="doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(EmkContractEntity emkContractEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "购销合同申请单提交成功";
        try {
            int flag = 0;
            TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
            Map map = ParameterUtil.getParamMaps(request.getParameterMap());
            if ((emkContractEntity.getId() == null) || (emkContractEntity.getId().isEmpty())) {
                for (String id : map.get("ids").toString().split(",")) {
                    EmkContractEntity contractEntity = systemService.getEntity(EmkContractEntity.class, id);
                    if (!contractEntity.getState().equals("0")) {
                        message = "存在已提交的购销合同单，请重新选择在提交购销合同单！";
                        j.setSuccess(false);
                        flag = 1;
                        break;
                    }
                }
            }else{
                map.put("ids", emkContractEntity.getId());
            }
            Map<String, Object> variables = new HashMap();
            if (flag == 0) {
                for (String id : map.get("ids").toString().split(",")) {
                    EmkContractEntity t = emkContractService.get(EmkContractEntity.class, id);
                    t.setState("1");
                    variables.put("optUser", t.getId());

                    List<Task> task = taskService.createTaskQuery().taskAssignee(id).list();
                    if (task.size() > 0) {
                        Task task1 = (Task)task.get(task.size() - 1);
                        if (task1.getTaskDefinitionKey().equals("htTask")) {
                            taskService.complete(task1.getId(), variables);
                        }
                        if (task1.getTaskDefinitionKey().equals("checkTask")) {
                            /*if (emkContractEntity.getIsPass().equals("0")) {
                                variables.put("isPass", emkContractEntity.getIsPass());
                                taskService.complete(task1.getId(), variables);
                                t.setState("2");

                                EmkProOrderEntity proOrderEntity = systemService.findUniqueByProperty(EmkProOrderEntity.class,"orderNo",t.getOrderNo());
                                EmkWorkOrderEntity workOrderEntity = systemService.findUniqueByProperty(EmkWorkOrderEntity.class,"workNo",proOrderEntity.getWorkNo());
                                workOrderEntity.setHtNo(t.getHtNum());
                                workOrderEntity.setHtUser(user.getRealName());
                                workOrderEntity.setHtUserId(user.getUserName());
                                workOrderEntity.setHtAdvice(emkContractEntity.getLeadAdvice());
                                workOrderEntity.setHtDate(DateUtil.getCurrentTimeString(null));
                                systemService.saveOrUpdate(workOrderEntity);

                                variables.put("inputUser", workOrderEntity.getId());
                                task = taskService.createTaskQuery().taskAssignee(workOrderEntity.getId()).list();
                                task1 = task.get(task.size() - 1);
                                taskService.complete(task1.getId(), variables);
                            } else {
                                List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery().taskAssignee(t.getId()).list();

                                List<Task> taskList = taskService.createTaskQuery().taskAssignee(t.getId()).list();
                                if (taskList.size() > 0) {
                                    Task taskH = (Task)taskList.get(taskList.size() - 1);
                                    HistoricTaskInstance historicTaskInstance = hisTasks.get(hisTasks.size() - 2);
                                    FlowUtil.turnTransition(taskH.getId(), historicTaskInstance.getTaskDefinitionKey(), variables);
                                    Map activityMap = systemService.findOneForJdbc("SELECT GROUP_CONCAT(t0.ID_) ids,GROUP_CONCAT(t0.TASK_ID_) taskids FROM act_hi_actinst t0 WHERE t0.ASSIGNEE_=? AND t0.ACT_ID_=? ORDER BY ID_ ASC",  t.getId(), historicTaskInstance.getTaskDefinitionKey());
                                    String[] activitIdArr = activityMap.get("ids").toString().split(",");
                                    String[] taskIdArr = activityMap.get("taskids").toString().split(",");
                                    systemService.executeSql("UPDATE act_hi_taskinst SET  NAME_=CONCAT('【驳回后】','',NAME_) WHERE ASSIGNEE_>=? AND ID_=?",t.getId(), taskIdArr[1]);
                                    systemService.executeSql("delete from act_hi_actinst where ID_>=? and ID_<?", activitIdArr[0], activitIdArr[1] );
                                }
                                t.setState("0");
                            }*/
                        }
                    }else {
                        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("ht", "emkContractEntity", variables);
                        task = taskService.createTaskQuery().taskAssignee(id).list();
                        Task task1 = task.get(task.size() - 1);
                        taskService.complete(task1.getId(), variables);
                    }
                    systemService.saveOrUpdate(t);
                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = "购销合同申请单提交失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params="goWork")
    public ModelAndView goWork(EmkContractEntity emkContractEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkContractEntity.getId())) {
            emkContractEntity = emkContractService.getEntity(EmkContractEntity.class, emkContractEntity.getId());
            req.setAttribute("emkContractPage", emkContractEntity);
        }
        return new ModelAndView("com/emk/bill/contract/emkContract-work");

    }

    @RequestMapping(params="goTime")
    public ModelAndView goTime(EmkContractEntity emkContractEntity, HttpServletRequest req, DataGrid dataGrid) {
        String sql = "";String countsql = "";
        Map map = ParameterUtil.getParamMaps(req.getParameterMap());

        sql = "SELECT DATE_FORMAT(t1.START_TIME_, '%Y-%m-%d %H:%i:%s') startTime,t1.*,CASE \n" +
                " WHEN t1.TASK_DEF_KEY_='htTask' THEN t2.create_name \n" +
                " WHEN t1.TASK_DEF_KEY_='checkTask' THEN t2.leader \n" +
                " END workname FROM act_hi_taskinst t1 \n" +
                " LEFT JOIN emk_contract t2 ON t1.ASSIGNEE_ = t2.id where ASSIGNEE_='" + map.get("id") + "' ";

        countsql = " SELECT COUNT(1) FROM act_hi_taskinst t1 where ASSIGNEE_='" + map.get("id") + "' ";
        if (dataGrid.getPage() == 1) {
            sql = sql + " limit 0, " + dataGrid.getRows();
        } else {
            sql = sql + "limit " + (dataGrid.getPage() - 1) * dataGrid.getRows() + "," + dataGrid.getRows();
        }
        systemService.listAllByJdbc(dataGrid, sql, countsql);
        req.setAttribute("taskList", dataGrid.getResults());
        if (dataGrid.getResults().size() > 0) {
            req.setAttribute("stepProcess", Integer.valueOf(dataGrid.getResults().size() - 1));
        } else {
            req.setAttribute("stepProcess", Integer.valueOf(0));
        }
        emkContractEntity = emkContractService.getEntity(EmkContractEntity.class, emkContractEntity.getId());
        req.setAttribute("emkContract", emkContractEntity);
        return new ModelAndView("com/emk/bill/contract/time");
    }

}
