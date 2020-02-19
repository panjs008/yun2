package com.emk.produce.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.produce.produce.entity.EmkProduceEntity;
import com.emk.produce.test.entity.EmkTestEntity;
import com.emk.produce.test.service.EmkTestServiceI;
import com.emk.produce.testcost.entity.EmkTestCostDetailEntity;
import com.emk.produce.testcost.entity.EmkTestCostEntity;
import com.emk.produce.testprogress.entity.EmkTestProgressEntity;
import com.emk.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.activiti.engine.runtime.ProcessInstance;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Api(value = "EmkTest", description = "测试申请表", tags = "emkTestController")
@Controller
@RequestMapping("/emkTestController")
public class EmkTestController extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkTestController.class);
    @Autowired
    private EmkTestServiceI emkTestService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;


    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/emk/produce/test/emkTestList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkTestEntity emkTest, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkTestEntity.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        /*Map roleMap = (Map) request.getSession().getAttribute("ROLE");
        if(roleMap != null){
            if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("ywgdy")|| roleMap.get("rolecode").toString().contains("scgdy")){
                cq.eq("createBy",user.getUserName());
            }
        }*/
        HqlGenerateUtil.installHql(cq, emkTest, request.getParameterMap());


        cq.add();
        emkTestService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkTestEntity emkTest, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkTest = systemService.getEntity(EmkTestEntity.class, emkTest.getId());
        message = "测试申请表删除成功";
        try {
            emkTestService.delete(emkTest);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "测试申请表删除失败";
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
        message = "测试申请表删除成功";
        try {
            for (String id : ids.split(",")) {
                EmkTestEntity emkTest = systemService.getEntity(EmkTestEntity.class, id);
                emkTestService.delete(emkTest);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "测试申请表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkTestEntity emkTest, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "测试申请表添加成功";
        try {
            emkTest.setState("0");
            Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(cssqdh, 3)),0)+1 AS signed) orderNum from emk_test");
            emkTest.setCssqdh("CS" + emkTest.getCusNum() + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));

            emkTestService.save(emkTest);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "测试申请表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkTestEntity emkTest, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "测试申请表更新成功";
        EmkTestEntity t = emkTestService.get(EmkTestEntity.class, emkTest.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(emkTest, t);
            emkTestService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "测试申请表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doGenerate")
    @ResponseBody
    public AjaxJson doGenerate(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "测试申请表添加成功";
        try {
            String[] idArr = ids.split(",");
            for(String id : ids.split(",")){
                EmkTestEntity t = emkTestService.get(EmkTestEntity.class, id);
                if(!t.getState().equals("63")){
                    j.setSuccess(false);
                    j.setMsg("存在还未审核通过的测试表，请重新选择");
                    return j;
                }
            }
            EmkTestCostEntity emkTestCost = new EmkTestCostEntity();
            EmkTestEntity t = emkTestService.get(EmkTestEntity.class, idArr[0]);
            MyBeanUtils.copyBeanNotNull2Bean(t,emkTestCost);
            emkTestCost.setId(null);
            Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(COST_NO, 3)),0)+1 AS signed) orderNum from emk_test_cost");
            emkTestCost.setCostNo("CSFY" + emkTestCost.getCusNum() + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));
            emkTestCost.setState("0");
            if(Utils.notEmpty(t.getProduceId())){
                emkTestCost.setFormType("2");
            }else{
                emkTestCost.setFormType("1");
            }
            emkTestService.save(emkTestCost);

            EmkTestProgressEntity emkTestProgressEntity = new EmkTestProgressEntity();
            MyBeanUtils.copyBeanNotNull2Bean(t,emkTestProgressEntity);
            emkTestProgressEntity.setUpdateDate(DateUtil.getCurrentTimeString(null));
            emkTestProgressEntity.setId(null);
            emkTestService.save(emkTestProgressEntity);

            EmkTestCostDetailEntity emkTestCostDetailEntity = null;
            EmkTestCostDetailEntity emkTestCostDetailEntity2 = null;

            for(String id : ids.split(",")){
                t = emkTestService.get(EmkTestEntity.class, id);
                emkTestCostDetailEntity = new EmkTestCostDetailEntity();
                emkTestCostDetailEntity2 = new EmkTestCostDetailEntity();

                MyBeanUtils.copyBeanNotNull2Bean(t,emkTestCostDetailEntity);
                MyBeanUtils.copyBeanNotNull2Bean(t,emkTestCostDetailEntity2);

                emkTestCostDetailEntity.setId(null);
                emkTestCostDetailEntity.setTestCostId(emkTestCost.getId());
                systemService.saveOrUpdate(emkTestCostDetailEntity);

                emkTestCostDetailEntity2.setId(null);
                emkTestCostDetailEntity2.setTestCostId(emkTestProgressEntity.getId());
                systemService.saveOrUpdate(emkTestCostDetailEntity2);

                EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
                t.setState("2");
                b.setStatus(2);
                List<Task> task = taskService.createTaskQuery().taskAssignee(id).list();
                if (task.size() > 0) {
                    Map<String, Object> variables = new HashMap();
                    Task task1 = (Task) task.get(task.size() - 1);
                    taskService.complete(task1.getId(), variables);
                }

                EmkProduceEntity emkProduceEntity = systemService.findUniqueByProperty(EmkProduceEntity.class,"productHtNum",t.getProduceNum());
                if(Utils.notEmpty(emkProduceEntity)){
                    b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkProduceEntity.getId());

                    task = taskService.createTaskQuery().taskAssignee(emkProduceEntity.getId()).list();
                    if (task.size() > 0) {
                        Map<String, Object> variables = new HashMap();
                        Task task1 = (Task) task.get(task.size() - 1);
                        if (task1.getTaskDefinitionKey().equals("csTask")) {
                            taskService.complete(task1.getId(), variables);
                            emkProduceEntity.setState("73");
                            b.setStatus(73);
                        }
                    }
                }

            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "测试申请表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "dowaLoadFile")
    public ModelAndView dowaLoadFile(EmkTestEntity emkTest, HttpServletRequest request, HttpServletResponse response) {
        String message = null;
        message = "文件下载成功";
        try {
            String filePath = "";
            String file = "";
            Map map = ParameterUtil.getParamMaps(request.getParameterMap());
            file = emkTest.getTestContentUrl();
            if ((file != null) && (!file.isEmpty())) {
                filePath = request.getRealPath("/") + file;
                WebFileUtils.downLoad(filePath, response, false);
            } else {
                emkTest = emkTestService.getEntity(EmkTestEntity.class, emkTest.getId());
                request.setAttribute("emkTestPage", emkTest);
                return new ModelAndView("com/emk/produce/test/emkTest-update");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "文件下载失败";
            throw new BusinessException(e.getMessage());
        }
        return null;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkTestEntity emkTest, HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        if (StringUtil.isNotEmpty(emkTest.getId())) {
            emkTest = emkTestService.getEntity(EmkTestEntity.class, emkTest.getId());
            req.setAttribute("emkTestPage", emkTest);
        }
        return new ModelAndView("com/emk/produce/test/emkTest-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkTestEntity emkTest, HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        if (StringUtil.isNotEmpty(emkTest.getId())) {
            emkTest = emkTestService.getEntity(EmkTestEntity.class, emkTest.getId());
            req.setAttribute("emkTestPage", emkTest);

            try {
                Map countMap = MyBeanUtils.culBeanCounts(emkTest);
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
        return new ModelAndView("com/emk/produce/test/emkTest-update");
    }

    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(EmkTestEntity emkTest, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkTest.getId())) {
            emkTest = emkTestService.getEntity(EmkTestEntity.class, emkTest.getId());
            req.setAttribute("emkTestPage", emkTest);

            try {
                Map countMap = MyBeanUtils.culBeanCounts(emkTest);
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
        return new ModelAndView("com/emk/produce/test/emkTest-update2");
    }

    @RequestMapping(params = "goUpdate3")
    public ModelAndView goUpdate3(EmkTestEntity emkTest, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkTest.getId())) {
            EmkProduceEntity emkProduceEntity = systemService.get(EmkProduceEntity.class,emkTest.getId());
            emkTest = emkTestService.findUniqueByProperty(EmkTestEntity.class,"produceId",emkTest.getId());
            req.setAttribute("emkTestPage", emkTest);
        }
        return new ModelAndView("com/emk/produce/test/emkTest-update3");
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "emkTestController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkTestEntity emkTest, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkTestEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkTest, request.getParameterMap());
        List<EmkTestEntity> emkTests = emkTestService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "测试申请表");
        modelMap.put("entity", EmkTestEntity.class);
        modelMap.put("params", new ExportParams("测试申请表列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkTests);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkTestEntity emkTest, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "测试申请表");
        modelMap.put("entity", EmkTestEntity.class);
        modelMap.put("params", new ExportParams("测试申请表列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }


    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "测试申请表列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkTestEntity>> list() {
        List<EmkTestEntity> listEmkTests = emkTestService.getList(EmkTestEntity.class);
        return Result.success(listEmkTests);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取测试申请表信息", notes = "根据ID获取测试申请表信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkTestEntity task = emkTestService.get(EmkTestEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取测试申请表信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建测试申请表")
    public ResponseMessage<?> create(@ApiParam(name = "测试申请表对象") @RequestBody EmkTestEntity emkTest, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkTestEntity>> failures = validator.validate(emkTest, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkTestService.save(emkTest);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("测试申请表信息保存失败");
        }
        return Result.success(emkTest);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新测试申请表", notes = "更新测试申请表")
    public ResponseMessage<?> update(@ApiParam(name = "测试申请表对象") @RequestBody EmkTestEntity emkTest) {
        Set<ConstraintViolation<EmkTestEntity>> failures = validator.validate(emkTest, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkTestService.saveOrUpdate(emkTest);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新测试申请表信息失败");
        }
        return Result.success("更新测试申请表信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除测试申请表")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            emkTestService.deleteEntityById(EmkTestEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("测试申请表删除失败");
        }
        return Result.success();
    }

    @RequestMapping(params="doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(EmkTestEntity emkTestEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "测试申请表提交成功";
        try {
            int flag = 0;
            TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            if (Utils.isEmpty(emkTestEntity.getId())) {
                for (String id : map.get("ids").toString().split(",")) {
                    EmkTestEntity testEntity = systemService.getEntity(EmkTestEntity.class, id);
                    if (!testEntity.getState().equals("0")) {
                        message = "存在已提交的测试申请表，请重新选择在提交！";
                        j.setSuccess(false);
                        flag = 1;
                        break;
                    }
                }
            }else{
                map.put("ids", emkTestEntity.getId());
            }
            Map<String, Object> variables = new HashMap();
            if (flag == 0) {
                for (String id : map.get("ids").toString().split(",")) {
                    EmkTestEntity t = emkTestService.get(EmkTestEntity.class, id);
                    EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
                    if(Utils.isEmpty(b)){
                        //type 工单类型，workNum 单号，formId 对应表单ID，bpmName 节点名称，bpmNode 节点代码，advice 处理意见，user 用户对象
                        b = new EmkApprovalEntity();
                        EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
                        ApprovalUtil.saveApproval(b,15,t.getCssqdh(),t.getId(),user);
                        systemService.save(b);
                        ApprovalUtil.saveApprovalDetail(approvalDetailEntity,b.getId(),"测试申请表","testTask","提交",user);
                        systemService.save(approvalDetailEntity);
                    }
                    t.setState("1");
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
                        if (task1.getTaskDefinitionKey().equals("testTask")) {
                            taskService.complete(task1.getId(), variables);
                            t.setState("1");
                            b.setStatus(1);
                            saveApprvoalDetail(approvalDetail,"重新提交的测试申请表","testTask",0,"重新提交测试申请表");
                            saveSmsAndEmailForMany("业务跟单员","重新提交的测试申请表","您有【"+b.getCreateName()+"】重新提交的测试申请表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                        }
                        if (task1.getTaskDefinitionKey().equals("checkTask")) {
                            if (map.get("isPass").equals("0")) {
                                variables.put("isPass","0");
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(61);
                                approvalDetail.setBpmName("业务跟单审核");
                                t.setState("61");
                                approvalDetail.setApproveStatus(0);
                                saveSmsAndEmailForOne("业务跟单审核","您有【"+user.getRealName()+"】审核通过的测试申请表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());

                            }else{
                                saveApprvoalDetail(approvalDetail,"业务跟单审核","testTask",1,map.get("advice"));
                                backProcess(task1.getProcessInstanceId(),"checkTask","testTask","测试申请表");
                                t.setState("0");
                                b.setStatus(0);
                                b.setBpmStatus("1");
                                saveSmsAndEmailForOne("业务跟单审核","您有【"+user.getRealName()+"】回退的测试申请表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                        }
                        if (task1.getTaskDefinitionKey().equals("csTask")) {
                            if (map.get("isPass").equals("0")) {
                                variables.put("isPass","0");
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(62);
                                approvalDetail.setBpmName("【生产跟单员】安排测试");
                                t.setState("62");
                                approvalDetail.setApproveStatus(0);

                                bpmUser = systemService.findUniqueByProperty(TSUser.class,"userName",map.get("userName").toString());
                                b.setNextBpmSher(map.get("realName"));
                                b.setNextBpmSherId(map.get("userName"));
                                saveSmsAndEmailForOne("【生产跟单员】安排测试","您有【"+user.getRealName()+"】审核通过的测试申请表，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                        }
                        if (task1.getTaskDefinitionKey().equals("gxjdTask")) {
                            if (map.get("isPass").equals("0")) {
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(65);
                                approvalDetail.setBpmName("【测试员】更新进度");
                                t.setState("65");
                                approvalDetail.setApproveStatus(0);

                                bpmUser = systemService.get(TSUser.class,b.getCommitId());
                                saveSmsAndEmailForOne("【测试员】更新进度","您有【"+user.getRealName()+"】完成更新测试进度，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                        }
                        if (task1.getTaskDefinitionKey().equals("bgTask")) {
                            if (map.get("isPass").equals("0")) {
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(63);
                                approvalDetail.setBpmName("【生产跟单员】上传报告");
                                t.setState("63");
                                approvalDetail.setApproveStatus(0);

                                t.setFileName(map.get("fileName"));
                                t.setFileNameUrl(map.get("fileNameUrl"));
                                bpmUser = systemService.get(TSUser.class,b.getCommitId());
                                saveSmsAndEmailForOne("【生产跟单员】上传报告","您有【"+user.getRealName()+"】完成上传报告，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                        }
                        systemService.saveOrUpdate(approvalDetail);
                    }else {
                        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("test", "emkTestEntity", variables);
                        task = taskService.createTaskQuery().taskAssignee(id).list();
                        Task task1 = task.get(task.size() - 1);
                        taskService.complete(task1.getId(), variables);

                        saveSmsAndEmailForMany("业务跟单员","【生产跟单员】测试申请表","您有【"+b.getCreateName()+"】提交的测试申请表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                        t.setState("1");
                        b.setStatus(1);
                        b.setBpmStatus("0");
                        b.setProcessName("【生产跟单员】测试申请表");
                    }

                    systemService.saveOrUpdate(t);
                    systemService.saveOrUpdate(b);
                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = "测试申请表提交失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params="goWork")
    public ModelAndView goWork(EmkTestEntity emkTestEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkTestEntity.getId())) {
            emkTestEntity = emkTestService.getEntity(EmkTestEntity.class, emkTestEntity.getId());
            req.setAttribute("emkTestPage", emkTestEntity);
        }
        return new ModelAndView("com/emk/produce/test/emkTest-work");
    }

    @RequestMapping(params="goTime")
    public ModelAndView goTime(EmkTestEntity emkTestEntity, HttpServletRequest req, DataGrid dataGrid) {
        EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkTestEntity.getId());
        if(Utils.notEmpty(approvalEntity)){
            List<EmkApprovalDetailEntity> approvalDetailEntityList = systemService.findHql("from EmkApprovalDetailEntity where approvalId=? order by approveDate asc",approvalEntity.getId());
            req.setAttribute("approvalDetailEntityList", approvalDetailEntityList);
            req.setAttribute("approvalEntity", approvalEntity);
            req.setAttribute("createDate", org.jeecgframework.core.util.DateUtils.date2Str(approvalEntity.getCreateDate(), org.jeecgframework.core.util.DateUtils.datetimeFormat));
        }
        return new ModelAndView("com/emk/produce/test/time");
    }
}
