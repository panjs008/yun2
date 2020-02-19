package com.emk.produce.outforum.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.bill.contract.entity.EmkContractEntity;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.produce.outforum.entity.EmkOutForumEntity;
import com.emk.produce.outforum.service.EmkOutForumServiceI;
import com.emk.produce.produce.entity.EmkProduceEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.storage.price.entity.EmkPriceEntity;
import com.emk.util.ApprovalUtil;
import com.emk.util.FlowUtil;
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

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
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
import org.jeecgframework.core.util.*;
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

@Api(value = "EmkOutForum", description = "出货通知单", tags = "emkOutForumController")
@Controller
@RequestMapping("/emkOutForumController")
public class EmkOutForumController extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkOutForumController.class);
    @Autowired
    private EmkOutForumServiceI emkOutForumService;
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
        return new ModelAndView("com/emk/produce/outforum/emkOutForumList");
    }

    @RequestMapping(params = "detailMxList")
    public ModelAndView detailMxList(HttpServletRequest request) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(remark,',',typecode) typecode,typecode code,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
        String color = JSONHelper.collection2json(list);
        request.setAttribute("color", "'"+color+ "'");
        list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='size'");
        request.setAttribute("sizeList", list);
        if (Utils.notEmpty(map.get("outForumId"))) {
            List<EmkProOrderDetailEntity> emkProOrderDetailEntities = systemService.findHql("from EmkProOrderDetailEntity where proOrderId=?", map.get("outForumId"));
            request.setAttribute("emkProOrderDetailEntities", emkProOrderDetailEntities);

            EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("outForumId"));
            request.setAttribute("emkSizePage", emkSizeEntity);
        }
        return new ModelAndView("com/emk/produce/outforum/detailMxList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkOutForumEntity emkOutForum, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkOutForumEntity.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        Map roleMap = (Map) request.getSession().getAttribute("ROLE");
        /*if(roleMap != null){
            if(roleMap.get("rolecode").contains("ywy") || roleMap.get("rolecode").contains("ywgdy")|| roleMap.get("rolecode").contains("scgdy")){
                cq.eq("createBy",user.getUserName());
            }
        }*/
        HqlGenerateUtil.installHql(cq, emkOutForum, request.getParameterMap());


        cq.add();
        emkOutForumService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkOutForumEntity emkOutForum, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkOutForum = systemService.getEntity(EmkOutForumEntity.class, emkOutForum.getId());
        message = "出货通知单删除成功";
        try {
            emkOutForumService.delete(emkOutForum);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "出货通知单删除失败";
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
        message = "出货通知单删除成功";
        try {
            for (String id : ids.split(",")) {
                EmkOutForumEntity emkOutForum = systemService.getEntity(EmkOutForumEntity.class, id);
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_pro_order_detail where pro_order_id=?))", emkOutForum.getId());
                systemService.executeSql("delete from emk_pro_order_detail where pro_order_id = ? ",emkOutForum.getId());
                emkOutForumService.delete(emkOutForum);

                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "出货通知单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkOutForumEntity emkOutForum,EmkSizeEntity emkSizeEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "出货通知单添加成功";
        try {
            emkOutForum.setState("0");
            TSUser user = (TSUser) request.getSession().getAttribute("LOCAL_CLINET_USER");
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(OUT_FORUM_NO, 3)),0)+1 AS signed) orderNum from emk_out_forum");
            emkOutForum.setOutForumNo("CH" + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", orderNum.get("orderNum").toString()));
            emkOutForumService.save(emkOutForum);

            emkSizeEntity.setFormId(emkOutForum.getId());
            systemService.save(emkSizeEntity);
            //保存明细数据
            String dataRows = (String) map.get("orderMxListIDSR");
            if (Utils.notEmpty(dataRows)) {
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_pro_order_detail where pro_order_id=?))", emkOutForum.getId());
                systemService.executeSql("delete from emk_pro_order_detail where pro_order_id = ? ",emkOutForum.getId());

                EmkSizeTotalEntity emkSizeTotalEntity = null;
                int rows = Integer.parseInt(dataRows);
                EmkProOrderDetailEntity proOrderDetailEntity = null;
                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))){
                        proOrderDetailEntity = new EmkProOrderDetailEntity();
                        proOrderDetailEntity.setProOrderId(emkOutForum.getId());

                        systemService.save(proOrderDetailEntity);

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
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "出货通知单添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkOutForumEntity emkOutForum,EmkSizeEntity emkSize, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "出货通知单更新成功";
        Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

        EmkOutForumEntity t = emkOutForumService.get(EmkOutForumEntity.class, map.get("outForumId"));
        try {
            if(!t.getState().equals("0")){
                j.setMsg("出货通知单已提交审核，无法编辑修改");
                j.setSuccess(false);
                return j;
            }
            emkOutForum.setId(null);
            MyBeanUtils.copyBeanNotNull2Bean(emkOutForum, t);
            emkOutForumService.saveOrUpdate(t);

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

            //保存明细数据
            String dataRows = (String) map.get("orderMxListIDSR");
            if (Utils.notEmpty(dataRows)) {
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_pro_order_detail where pro_order_id=?))", t.getId());
                systemService.executeSql("delete from emk_pro_order_detail where pro_order_id = ? ",t.getId());

                EmkSizeTotalEntity emkSizeTotalEntity = null;
                int rows = Integer.parseInt(dataRows);
                EmkProOrderDetailEntity proOrderDetailEntity = null;
                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))){
                        proOrderDetailEntity = new EmkProOrderDetailEntity();
                        proOrderDetailEntity.setProOrderId(t.getId());

                        if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
                            String[] colorArr = map.get("orderMxList["+i+"].color").split(",");
                        }

                        systemService.save(proOrderDetailEntity);

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
            if("1".equals(map.get("isPass"))) {
                EmkProduceEntity emkProduceEntity = systemService.get(EmkProduceEntity.class,t.getProduceId());
                List<Task> task = taskService.createTaskQuery().taskAssignee(emkProduceEntity.getId()).list();
                Task task1 = (Task) task.get(task.size() - 1);
                TSUser user = (TSUser) request.getSession().getAttribute("LOCAL_CLINET_USER");
                Map<String, Object> variables = new HashMap();
                EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class, "formId", emkProduceEntity.getId());
                EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(), user, b, "【生产跟单员】出口通知单");

                if (task1.getTaskDefinitionKey().equals("cktzdTask")) {
                    taskService.complete(task1.getId(), variables);
                    emkProduceEntity.setState("82");
                    b.setStatus(82);
                    saveApprvoalDetail(approvalDetail,"【生产跟单员】出口通知单","cktzdTask",0,"【生产跟单员】出口通知单");
                    systemService.saveOrUpdate(approvalDetail);
                    saveSmsAndEmailForMany("业务跟单员","【生产跟单员】出口通知单","您有【"+b.getCreateName()+"】提交的订舱通知单，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                    t.setState("1");
                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "出货通知单更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkOutForumEntity emkOutForum, HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        List<Map<String, Object>> codeList = systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
        req.setAttribute("categoryEntityList", codeList);
        if (StringUtil.isNotEmpty(emkOutForum.getId())) {
            emkOutForum = emkOutForumService.getEntity(EmkOutForumEntity.class, emkOutForum.getId());
            req.setAttribute("emkOutForumPage", emkOutForum);
        }
        return new ModelAndView("com/emk/produce/outforum/emkOutForum-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkOutForumEntity emkOutForum, HttpServletRequest req) {
        List<Map<String, Object>> codeList = systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
        req.setAttribute("categoryEntityList", codeList);
        if (StringUtil.isNotEmpty(emkOutForum.getId())) {
            emkOutForum = emkOutForumService.getEntity(EmkOutForumEntity.class, emkOutForum.getId());
            req.setAttribute("emkOutForumPage", emkOutForum);
        }
        return new ModelAndView("com/emk/produce/outforum/emkOutForum-update");
    }

    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(EmkOutForumEntity emkOutForum, HttpServletRequest req) {
        List<Map<String, Object>> codeList = systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
        req.setAttribute("categoryEntityList", codeList);
        if (StringUtil.isNotEmpty(emkOutForum.getId())) {
            EmkProduceEntity emkProduceEntity = systemService.get(EmkProduceEntity.class,emkOutForum.getId());
            emkOutForum = systemService.findUniqueByProperty(EmkOutForumEntity.class,"produceId",emkProduceEntity.getId());
            req.setAttribute("emkOutForumPage", emkOutForum);
        }

        return new ModelAndView("com/emk/produce/outforum/emkOutForum-update2");
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "emkOutForumController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkOutForumEntity emkOutForum, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkOutForumEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkOutForum, request.getParameterMap());
        List<EmkOutForumEntity> emkOutForums = emkOutForumService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "出货通知单");
        modelMap.put("entity", EmkOutForumEntity.class);
        modelMap.put("params", new ExportParams("出货通知单列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkOutForums);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkOutForumEntity emkOutForum, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "出货通知单");
        modelMap.put("entity", EmkOutForumEntity.class);
        modelMap.put("params", new ExportParams("出货通知单列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "出货通知单列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkOutForumEntity>> list() {
        List<EmkOutForumEntity> listEmkOutForums = emkOutForumService.getList(EmkOutForumEntity.class);
        return Result.success(listEmkOutForums);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取出货通知单信息", notes = "根据ID获取出货通知单信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkOutForumEntity task = emkOutForumService.get(EmkOutForumEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取出货通知单信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建出货通知单")
    public ResponseMessage<?> create(@ApiParam(name = "出货通知单对象") @RequestBody EmkOutForumEntity emkOutForum, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkOutForumEntity>> failures = validator.validate(emkOutForum, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkOutForumService.save(emkOutForum);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("出货通知单信息保存失败");
        }
        return Result.success(emkOutForum);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新出货通知单", notes = "更新出货通知单")
    public ResponseMessage<?> update(@ApiParam(name = "出货通知单对象") @RequestBody EmkOutForumEntity emkOutForum) {
        Set<ConstraintViolation<EmkOutForumEntity>> failures = validator.validate(emkOutForum, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkOutForumService.saveOrUpdate(emkOutForum);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新出货通知单信息失败");
        }
        return Result.success("更新出货通知单信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除出货通知单")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            emkOutForumService.deleteEntityById(EmkOutForumEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("出货通知单删除失败");
        }
        return Result.success();
    }

    @RequestMapping(params="doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(EmkOutForumEntity emkOutForum, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "出货通知单提交成功";
        try {
            int flag = 0;
            TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
            Map<String,String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            if ((emkOutForum.getId() == null) || (emkOutForum.getId().isEmpty())) {
                for (String id : map.get("ids").split(",")) {
                    EmkOutForumEntity outForumEntity = systemService.getEntity(EmkOutForumEntity.class, id);
                    if (!outForumEntity.getState().equals("0")) {
                        message = "存在已提交的出货通知单，请重新选择在提交出货通知单！";
                        j.setSuccess(false);
                        flag = 1;
                        break;
                    }
                }
            }else{
                map.put("ids", emkOutForum.getId());
            }
            Map<String, Object> variables = new HashMap();
            if (flag == 0) {
                for (String id : map.get("ids").split(",")) {
                    EmkOutForumEntity t = emkOutForumService.get(EmkOutForumEntity.class, id);
                    t.setState("1");
                    variables.put("optUser", t.getId());

                    List<Task> task = taskService.createTaskQuery().taskAssignee(id).list();
                    if (task.size() > 0) {
                        Task task1 = (Task)task.get(task.size() - 1);
                        if (task1.getTaskDefinitionKey().equals("htTask")) {
                            taskService.complete(task1.getId(), variables);
                        }
                        if (task1.getTaskDefinitionKey().equals("checkTask")) {

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
            message = "出货通知单提交失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params="goWork")
    public ModelAndView goWork(EmkOutForumEntity emkOutForum, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkOutForum.getId())) {
            emkOutForum = emkOutForumService.getEntity(EmkPriceEntity.class, emkOutForum.getId());
            req.setAttribute("emkOutForum", emkOutForum);
        }
        return new ModelAndView("com/emk/produce/outforum/emkOutForum-work");

    }

    @RequestMapping(params="goTime")
    public ModelAndView goTime(EmkOutForumEntity emkOutForum, HttpServletRequest req, DataGrid dataGrid) {
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
            req.setAttribute("stepProcess", dataGrid.getResults().size() - 1);
        } else {
            req.setAttribute("stepProcess", 0);
        }
        emkOutForum = emkOutForumService.getEntity(EmkOutForumEntity.class, emkOutForum.getId());
        req.setAttribute("emkOutForum", emkOutForum);
        return new ModelAndView("com/emk/produce/outforum/time");

    }
}
