package com.emk.produce.produceschedule.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.bill.proorder.entity.EmkProOrderEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.produce.produce.entity.EmkProduceDetailEntity;
import com.emk.produce.produceschedule.entity.EmkProduceDetailScheduleEntity;
import com.emk.produce.produceschedule.entity.EmkProduceScheduleEntity;
import com.emk.produce.produceschedule.service.EmkProduceScheduleServiceI;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.util.FlowUtil;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import com.emk.workorder.workorder.entity.EmkWorkOrderEntity;
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

@Api(value = "EmkProduceSchedule", description = "订单生产管理", tags = "emkProduceScheduleController")
@Controller
@RequestMapping("/emkProduceScheduleController")
public class EmkProduceScheduleController extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkProduceScheduleController.class);
    @Autowired
    private EmkProduceScheduleServiceI emkProduceScheduleService;
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
        return new ModelAndView("com/emk/produce/produceschedule/emkProduceScheduleList");
    }
    @RequestMapping(params = "detailMxList")
    public ModelAndView detailMxList(HttpServletRequest request) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(remark,',',typecode) typecode,typecode code,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
        String color = JSONHelper.collection2json(list);
        request.setAttribute("color", "'"+color+ "'");
        list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='gylx'");
        request.setAttribute("gylxList", list);
        list = systemService.findForJdbc("select company_code gys from emk_factory_archives t2 where state=2 order by company_code asc ");
        request.setAttribute("gysList", list);
        list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='size'");
        request.setAttribute("sizeList", list);
        if (Utils.notEmpty(map.get("pId"))) {
            List<EmkProduceDetailScheduleEntity> emkProduceDetailEntities = systemService.findHql("from EmkProduceDetailScheduleEntity where pId=?", map.get("pId"));
            request.setAttribute("emkProduceDetailEntities", emkProduceDetailEntities);

            EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("pId"));
            request.setAttribute("emkSizePage", emkSizeEntity);
        }
        return new ModelAndView("com/emk/produce/produceschedule/detailMxList");
    }
    @RequestMapping(params = "orderMxList")
    public ModelAndView orderMxList(HttpServletRequest request) {
        List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
        request.setAttribute("categoryEntityList", codeList);
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        if ((map.get("proOrderId") != null) && (!map.get("proOrderId").equals(""))) {
            List<EmkEnquiryDetailEntity> emkProOrderDetailEntities = this.systemService.findHql("from EmkEnquiryDetailEntity where enquiryId=?", map.get("proOrderId"));
            request.setAttribute("emkProOrderDetailEntities", emkProOrderDetailEntities);
        }
        return new ModelAndView("com/emk/produce/produceschedule/orderMxList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkProduceScheduleEntity emkProduceSchedule, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkProduceScheduleEntity.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
       /* Map roleMap = (Map) request.getSession().getAttribute("ROLE");
        if(roleMap != null){
            if(roleMap.get("rolecode").toString().contains("ywy") || roleMap.get("rolecode").toString().contains("ywgdy")|| roleMap.get("rolecode").toString().contains("scgdy")){
                cq.eq("createBy",user.getUserName());
            }
        }*/
        HqlGenerateUtil.installHql(cq, emkProduceSchedule, request.getParameterMap());


        cq.add();
        this.emkProduceScheduleService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkProduceScheduleEntity emkProduceSchedule, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkProduceSchedule = this.systemService.getEntity(EmkProduceScheduleEntity.class, emkProduceSchedule.getId());
        message = "订单生产管理删除成功";
        try {
            this.emkProduceScheduleService.delete(emkProduceSchedule);
            this.systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单生产管理删除失败";
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
        message = "订单生产管理删除成功";
        try {
            for (String id : ids.split(",")) {
                EmkProduceScheduleEntity emkProduceSchedule = this.systemService.getEntity(EmkProduceScheduleEntity.class, id);
                this.emkProduceScheduleService.delete(emkProduceSchedule);
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_produce_detail where p_id=?))", emkProduceSchedule.getId());
                systemService.executeSql("delete from emk_produce_detail where p_id = ? ",emkProduceSchedule.getId());
                this.systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单生产管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkProduceScheduleEntity emkProduceSchedule,EmkSizeEntity emkSizeEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "订单生产管理添加成功";
        try {
            emkProduceSchedule.setState("0");
            this.emkProduceScheduleService.save(emkProduceSchedule);
            emkSizeEntity.setFormId(emkProduceSchedule.getId());
            systemService.saveOrUpdate(emkSizeEntity);
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

            //保存明细数据
            String dataRows = (String) map.get("orderMxListIDSR");
            if (Utils.notEmpty(dataRows)) {
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_produce_detail where p_id=?))", emkProduceSchedule.getId());
                systemService.executeSql("delete from emk_produce_detail where p_id = ? ",emkProduceSchedule.getId());

                int rows = Integer.parseInt(dataRows);
                EmkProduceDetailScheduleEntity emkProduceDetailEntity = null;
                EmkSizeTotalEntity emkSizeTotalEntity = null;

                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))){
                        emkProduceDetailEntity = new EmkProduceDetailScheduleEntity();
                        emkProduceDetailEntity.setpId(emkProduceSchedule.getId());
                        emkProduceDetailEntity.setOrderNo(map.get("orderMxList["+i+"].orderNo00"));
                        emkProduceDetailEntity.setCusNum(map.get("orderMxList["+i+"].cusNum00"));
                        emkProduceDetailEntity.setProduceHtNum(map.get("orderMxList["+i+"].produceHtNum00"));
                        emkProduceDetailEntity.setGysCode(map.get("orderMxList["+i+"].gysCode00"));
                        emkProduceDetailEntity.setGyzl(map.get("orderMxList["+i+"].gyzl00"));
                        emkProduceDetailEntity.setProTypeName(map.get("orderMxList["+i+"].proTypeName00"));
                        emkProduceDetailEntity.setSampleNo(map.get("orderMxList["+i+"].sampleNo00"));
                        emkProduceDetailEntity.setSampleNoDesc(map.get("orderMxList["+i+"].sampleDesc00"));
                        emkProduceDetailEntity.setQrhtNum(map.get("orderMxList["+i+"].qrhtNum00"));
                        emkProduceDetailEntity.setCustomSampleUrl(map.get("orderMxList["+i+"].customSampleUrl"));
                        emkProduceDetailEntity.setCustomSample(map.get("orderMxList["+i+"].customSample"));

                        emkProduceDetailEntity.setSsyzt(map.get("orderMxList["+i+"].ssyzt00"));
                        emkProduceDetailEntity.setSsyDate(map.get("orderMxList["+i+"].ssyDate00"));
                        emkProduceDetailEntity.setCqyzt(map.get("orderMxList["+i+"].cqyzt00"));
                        emkProduceDetailEntity.setCqyDate(map.get("orderMxList["+i+"].cqyDate00"));
                        emkProduceDetailEntity.setSyzt(map.get("orderMxList["+i+"].syzt00"));
                        emkProduceDetailEntity.setSyDate(map.get("orderMxList["+i+"].syDate00"));
                        emkProduceDetailEntity.setCqhy(map.get("orderMxList["+i+"].cqhy00"));
                        emkProduceDetailEntity.setCqyDate(map.get("orderMxList["+i+"].cqhyDate00"));
                        emkProduceDetailEntity.setTestPass(map.get("orderMxList["+i+"].testPass00"));
                        emkProduceDetailEntity.setTestPassDate(map.get("orderMxList["+i+"].testPassDate00"));

                        emkProduceDetailEntity.setYlblState(map.get("orderMxList["+i+"].ylblState00"));
                        emkProduceDetailEntity.setYlblLimitDate(map.get("orderMxList["+i+"].ylblLimitDate00"));
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelYlblDay00"))){
                            emkProduceDetailEntity.setLeavelYlblDay(Integer.parseInt(map.get("orderMxList["+i+"].leavelYlblDay00")));
                        }

                        emkProduceDetailEntity.setFzblState(map.get("orderMxList["+i+"].fzblState00"));
                        emkProduceDetailEntity.setFzblLimitDate(map.get("orderMxList["+i+"].fzblLimitDate00"));
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelFzblDay00"))){
                            emkProduceDetailEntity.setLeavelFzblDay(Integer.parseInt(map.get("orderMxList["+i+"].leavelFzblDay00")));
                        }

                        emkProduceDetailEntity.setBzblState(map.get("orderMxList["+i+"].bzblState00"));
                        emkProduceDetailEntity.setBzblLimitDate(map.get("orderMxList["+i+"].bzblLimitDate00"));
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelBzblDay00"))){
                            emkProduceDetailEntity.setLeavelBzblDay(Integer.parseInt(map.get("orderMxList["+i+"].leavelBzblDay00")));
                        }

                        emkProduceDetailEntity.setRanState(map.get("orderMxList["+i+"].ranState00"));
                        emkProduceDetailEntity.setRanFinish(map.get("orderMxList["+i+"].ranFinish00"));
                        emkProduceDetailEntity.setRanUnfinish(map.get("orderMxList["+i+"].ranUnfinish00"));

                        emkProduceDetailEntity.setCaiState(map.get("orderMxList["+i+"].caiState00"));
                        emkProduceDetailEntity.setCaiFinish(map.get("orderMxList["+i+"].caiFinish00"));
                        emkProduceDetailEntity.setCaiUnfinish(map.get("orderMxList["+i+"].caiUnfinish00"));

                        emkProduceDetailEntity.setFzState(map.get("orderMxList["+i+"].fzState00"));
                        emkProduceDetailEntity.setFzFinish(map.get("orderMxList["+i+"].fzFinish00"));
                        emkProduceDetailEntity.setFzUnfinish(map.get("orderMxList["+i+"].fzUnfinish00"));

                        emkProduceDetailEntity.setBtState(map.get("orderMxList["+i+"].btState00"));
                        emkProduceDetailEntity.setBtFinish(map.get("orderMxList["+i+"].btFinish00"));
                        emkProduceDetailEntity.setBtUnfinish(map.get("orderMxList["+i+"].btUnfinish00"));

                        emkProduceDetailEntity.setZtState(map.get("orderMxList["+i+"].ztState00"));
                        emkProduceDetailEntity.setZtFinish(map.get("orderMxList["+i+"].ztFinish00"));
                        emkProduceDetailEntity.setZtUnfinish(map.get("orderMxList["+i+"].ztUnfinish00"));

                        emkProduceDetailEntity.setBzState(map.get("orderMxList["+i+"].bzState00"));
                        emkProduceDetailEntity.setBzFinish(map.get("orderMxList["+i+"].bzFinish00"));
                        emkProduceDetailEntity.setBzUnfinish(map.get("orderMxList["+i+"].bzUnfinish00"));

                        emkProduceDetailEntity.setZcrq(map.get("orderMxList["+i+"].zcrq00"));
                        emkProduceDetailEntity.setZcrqDate(map.get("orderMxList["+i+"].zcrqDate00"));

                        emkProduceDetailEntity.setWcrq(map.get("orderMxList["+i+"].wcrq00"));
                        emkProduceDetailEntity.setWcrqDate(map.get("orderMxList["+i+"].wcrqDate00"));

                        emkProduceDetailEntity.setOutDate(map.get("orderMxList["+i+"].outDate00"));
                        emkProduceDetailEntity.setSortDesc(String.valueOf(i+1));

                        emkProduceDetailEntity.setCyzt(map.get("orderMxList["+i+"].cyzt00"));
                        emkProduceDetailEntity.setCyDate(map.get("orderMxList["+i+"].cyDate00"));
                        emkProduceDetailEntity.setRecevieDate(map.get("orderMxList["+i+"].recevieDate00"));
                        emkProduceDetailEntity.setLevealJqDays(map.get("orderMxList["+i+"].levealJqDays00"));

                        systemService.save(emkProduceDetailEntity);

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
                        emkSizeTotalEntity.setpId(emkProduceDetailEntity.getId());
                        systemService.save(emkSizeTotalEntity);
                    }
                }
            }
            this.systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单生产管理添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkProduceScheduleEntity emkProduceSchedule,EmkSizeEntity emkSize, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "订单生产管理更新成功";
        Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
        EmkProduceScheduleEntity t = this.emkProduceScheduleService.get(EmkProduceScheduleEntity.class,map.get("produceId"));
        try {
            emkProduceSchedule.setState("0");
            MyBeanUtils.copyBeanNotNull2Bean(emkProduceSchedule, t);
            this.emkProduceScheduleService.saveOrUpdate(t);

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
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_produce_detail where p_id=?))", t.getId());
                systemService.executeSql("delete from emk_produce_detail where p_id = ? ",t.getId());

                int rows = Integer.parseInt(dataRows);
                EmkProduceDetailScheduleEntity emkProduceDetailEntity = null;
                EmkSizeTotalEntity emkSizeTotalEntity = null;

                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))){
                        emkProduceDetailEntity = new EmkProduceDetailScheduleEntity();
                        emkProduceDetailEntity.setpId(t.getId());
                        emkProduceDetailEntity.setOrderNo(map.get("orderMxList["+i+"].orderNo00"));
                        emkProduceDetailEntity.setCusNum(map.get("orderMxList["+i+"].cusNum00"));
                        emkProduceDetailEntity.setProduceHtNum(map.get("orderMxList["+i+"].produceHtNum00"));
                        emkProduceDetailEntity.setGysCode(map.get("orderMxList["+i+"].gysCode00"));
                        emkProduceDetailEntity.setGyzl(map.get("orderMxList["+i+"].gyzl00"));
                        emkProduceDetailEntity.setProTypeName(map.get("orderMxList["+i+"].proTypeName00"));
                        emkProduceDetailEntity.setSampleNo(map.get("orderMxList["+i+"].sampleNo00"));
                        emkProduceDetailEntity.setSampleNoDesc(map.get("orderMxList["+i+"].sampleDesc00"));
                        emkProduceDetailEntity.setQrhtNum(map.get("orderMxList["+i+"].qrhtNum00"));
                        emkProduceDetailEntity.setCustomSampleUrl(map.get("orderMxList["+i+"].customSampleUrl"));
                        emkProduceDetailEntity.setCustomSample(map.get("orderMxList["+i+"].customSample"));

                        emkProduceDetailEntity.setSsyzt(map.get("orderMxList["+i+"].ssyzt00"));
                        emkProduceDetailEntity.setSsyDate(map.get("orderMxList["+i+"].ssyDate00"));
                        emkProduceDetailEntity.setCqyzt(map.get("orderMxList["+i+"].cqyzt00"));
                        emkProduceDetailEntity.setCqyDate(map.get("orderMxList["+i+"].cqyDate00"));
                        emkProduceDetailEntity.setSyzt(map.get("orderMxList["+i+"].syzt00"));
                        emkProduceDetailEntity.setSyDate(map.get("orderMxList["+i+"].syDate00"));
                        emkProduceDetailEntity.setCqhy(map.get("orderMxList["+i+"].cqhy00"));
                        emkProduceDetailEntity.setCqyDate(map.get("orderMxList["+i+"].cqhyDate00"));
                        emkProduceDetailEntity.setTestPass(map.get("orderMxList["+i+"].testPass00"));
                        emkProduceDetailEntity.setTestPassDate(map.get("orderMxList["+i+"].testPassDate00"));

                        emkProduceDetailEntity.setYlblState(map.get("orderMxList["+i+"].ylblState00"));
                        emkProduceDetailEntity.setYlblLimitDate(map.get("orderMxList["+i+"].ylblLimitDate00"));
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelYlblDay00"))){
                            emkProduceDetailEntity.setLeavelYlblDay(Integer.parseInt(map.get("orderMxList["+i+"].leavelYlblDay00")));
                        }

                        emkProduceDetailEntity.setFzblState(map.get("orderMxList["+i+"].fzblState00"));
                        emkProduceDetailEntity.setFzblLimitDate(map.get("orderMxList["+i+"].fzblLimitDate00"));
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelFzblDay00"))){
                            emkProduceDetailEntity.setLeavelFzblDay(Integer.parseInt(map.get("orderMxList["+i+"].leavelFzblDay00")));
                        }

                        emkProduceDetailEntity.setBzblState(map.get("orderMxList["+i+"].bzblState00"));
                        emkProduceDetailEntity.setBzblLimitDate(map.get("orderMxList["+i+"].bzblLimitDate00"));
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].leavelBzblDay00"))){
                            emkProduceDetailEntity.setLeavelBzblDay(Integer.parseInt(map.get("orderMxList["+i+"].leavelBzblDay00")));
                        }

                        emkProduceDetailEntity.setRanState(map.get("orderMxList["+i+"].ranState00"));
                        emkProduceDetailEntity.setRanFinish(map.get("orderMxList["+i+"].ranFinish00"));
                        emkProduceDetailEntity.setRanUnfinish(map.get("orderMxList["+i+"].ranUnfinish00"));

                        emkProduceDetailEntity.setCaiState(map.get("orderMxList["+i+"].caiState00"));
                        emkProduceDetailEntity.setCaiFinish(map.get("orderMxList["+i+"].caiFinish00"));
                        emkProduceDetailEntity.setCaiUnfinish(map.get("orderMxList["+i+"].caiUnfinish00"));

                        emkProduceDetailEntity.setFzState(map.get("orderMxList["+i+"].fzState00"));
                        emkProduceDetailEntity.setFzFinish(map.get("orderMxList["+i+"].fzFinish00"));
                        emkProduceDetailEntity.setFzUnfinish(map.get("orderMxList["+i+"].fzUnfinish00"));

                        emkProduceDetailEntity.setBtState(map.get("orderMxList["+i+"].btState00"));
                        emkProduceDetailEntity.setBtFinish(map.get("orderMxList["+i+"].btFinish00"));
                        emkProduceDetailEntity.setBtUnfinish(map.get("orderMxList["+i+"].btUnfinish00"));

                        emkProduceDetailEntity.setZtState(map.get("orderMxList["+i+"].ztState00"));
                        emkProduceDetailEntity.setZtFinish(map.get("orderMxList["+i+"].ztFinish00"));
                        emkProduceDetailEntity.setZtUnfinish(map.get("orderMxList["+i+"].ztUnfinish00"));

                        emkProduceDetailEntity.setBzState(map.get("orderMxList["+i+"].bzState00"));
                        emkProduceDetailEntity.setBzFinish(map.get("orderMxList["+i+"].bzFinish00"));
                        emkProduceDetailEntity.setBzUnfinish(map.get("orderMxList["+i+"].bzUnfinish00"));

                        emkProduceDetailEntity.setZcrq(map.get("orderMxList["+i+"].zcrq00"));
                        emkProduceDetailEntity.setZcrqDate(map.get("orderMxList["+i+"].zcrqDate00"));

                        emkProduceDetailEntity.setWcrq(map.get("orderMxList["+i+"].wcrq00"));
                        emkProduceDetailEntity.setWcrqDate(map.get("orderMxList["+i+"].wcrqDate00"));

                        emkProduceDetailEntity.setOutDate(map.get("orderMxList["+i+"].outDate00"));
                        emkProduceDetailEntity.setSortDesc(String.valueOf(i+1));

                        emkProduceDetailEntity.setCyzt(map.get("orderMxList["+i+"].cyzt00"));
                        emkProduceDetailEntity.setCyDate(map.get("orderMxList["+i+"].cyDate00"));
                        emkProduceDetailEntity.setRecevieDate(map.get("orderMxList["+i+"].recevieDate00"));
                        emkProduceDetailEntity.setLevealJqDays(map.get("orderMxList["+i+"].levealJqDays00"));

                        systemService.save(emkProduceDetailEntity);

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
                        emkSizeTotalEntity.setpId(emkProduceDetailEntity.getId());
                        systemService.save(emkSizeTotalEntity);
                    }
                }
            }
            this.systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单生产管理更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkProduceScheduleEntity emkProduceSchedule, HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
        req.setAttribute("categoryEntityList", codeList);
        if (StringUtil.isNotEmpty(emkProduceSchedule.getId())) {
            emkProduceSchedule = this.emkProduceScheduleService.getEntity(EmkProduceScheduleEntity.class, emkProduceSchedule.getId());
            req.setAttribute("emkProduceSchedulePage", emkProduceSchedule);
        }
        return new ModelAndView("com/emk/produce/produceschedule/emkProduceSchedule-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkProduceScheduleEntity emkProduceSchedule, HttpServletRequest req) {
        List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
        req.setAttribute("categoryEntityList", codeList);
        if (StringUtil.isNotEmpty(emkProduceSchedule.getId())) {
            emkProduceSchedule = this.emkProduceScheduleService.getEntity(EmkProduceScheduleEntity.class, emkProduceSchedule.getId());
            req.setAttribute("emkProduceSchedulePage", emkProduceSchedule);
        }
        return new ModelAndView("com/emk/produce/produceschedule/emkProduceSchedule-update");
    }

    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(EmkProduceScheduleEntity emkProduceSchedule, HttpServletRequest req) {
        List<Map<String, Object>> codeList = this.systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", "A03");
        req.setAttribute("categoryEntityList", codeList);
        if (StringUtil.isNotEmpty(emkProduceSchedule.getId())) {
            emkProduceSchedule = this.emkProduceScheduleService.getEntity(EmkProduceScheduleEntity.class, emkProduceSchedule.getId());
            req.setAttribute("emkProduceSchedulePage", emkProduceSchedule);
        }
        return new ModelAndView("com/emk/produce/produceschedule/emkProduceSchedule-update2");
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "emkProduceScheduleController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkProduceScheduleEntity emkProduceSchedule, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkProduceScheduleEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkProduceSchedule, request.getParameterMap());
        List<EmkProduceScheduleEntity> emkProduceSchedules = this.emkProduceScheduleService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "订单生产管理");
        modelMap.put("entity", EmkProduceScheduleEntity.class);
        modelMap.put("params", new ExportParams("订单生产管理列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkProduceSchedules);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkProduceScheduleEntity emkProduceSchedule, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "订单生产管理");
        modelMap.put("entity", EmkProduceScheduleEntity.class);
        modelMap.put("params", new ExportParams("订单生产管理列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "订单生产管理列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkProduceScheduleEntity>> list() {
        List<EmkProduceScheduleEntity> listEmkProduceSchedules = this.emkProduceScheduleService.getList(EmkProduceScheduleEntity.class);
        return Result.success(listEmkProduceSchedules);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取订单生产管理信息", notes = "根据ID获取订单生产管理信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkProduceScheduleEntity task = this.emkProduceScheduleService.get(EmkProduceScheduleEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取订单生产管理信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建订单生产管理")
    public ResponseMessage<?> create(@ApiParam(name = "订单生产管理对象") @RequestBody EmkProduceScheduleEntity emkProduceSchedule, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkProduceScheduleEntity>> failures = this.validator.validate(emkProduceSchedule, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            this.emkProduceScheduleService.save(emkProduceSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("订单生产管理信息保存失败");
        }
        return Result.success(emkProduceSchedule);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新订单生产管理", notes = "更新订单生产管理")
    public ResponseMessage<?> update(@ApiParam(name = "订单生产管理对象") @RequestBody EmkProduceScheduleEntity emkProduceSchedule) {
        Set<ConstraintViolation<EmkProduceScheduleEntity>> failures = this.validator.validate(emkProduceSchedule, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            this.emkProduceScheduleService.saveOrUpdate(emkProduceSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新订单生产管理信息失败");
        }
        return Result.success("更新订单生产管理信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除订单生产管理")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            this.emkProduceScheduleService.deleteEntityById(EmkProduceScheduleEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("订单生产管理删除失败");
        }
        return Result.success();
    }

    @RequestMapping(params="doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(EmkProduceScheduleEntity emkProduceScheduleEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "生产单提交成功";
        try {
            int flag = 0;
            TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
            Map map = ParameterUtil.getParamMaps(request.getParameterMap());
            if ((emkProduceScheduleEntity.getId() == null) || (emkProduceScheduleEntity.getId().isEmpty())) {
                for (String id : map.get("ids").toString().split(",")) {
                    EmkProduceScheduleEntity produceScheduleEntity = systemService.getEntity(EmkProduceScheduleEntity.class, id);
                    if (!produceScheduleEntity.getState().equals("0")) {
                        message = "存在已提交的生产单，请重新选择在提交！";
                        j.setSuccess(false);
                        flag = 1;
                        break;
                    }
                }
            }else{
                map.put("ids", emkProduceScheduleEntity.getId());
            }
            Map<String, Object> variables = new HashMap();
            if (flag == 0) {
                for (String id : map.get("ids").toString().split(",")) {
                    EmkProduceScheduleEntity t = emkProduceScheduleService.get(EmkProduceScheduleEntity.class, id);
                    t.setState("1");
                    variables.put("optUser", t.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(emkProduceScheduleEntity, t);

                    List<Task> task = taskService.createTaskQuery().taskAssignee(id).list();
                    if (task.size() > 0) {
                        Task task1 = (Task)task.get(task.size() - 1);
                        if (task1.getTaskDefinitionKey().equals("produceTask")) {
                            taskService.complete(task1.getId(), variables);
                        }

                        systemService.saveOrUpdate(t);

                    }else {
                        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("produce", "emkProduceScheduleEntity", variables);
                        task = taskService.createTaskQuery().taskAssignee(id).list();
                        Task task1 = task.get(task.size() - 1);
                        taskService.complete(task1.getId(), variables);
                    }

                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = "生产单提交失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params="goWork")
    public ModelAndView goWork(EmkProduceScheduleEntity emkProduceScheduleEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkProduceScheduleEntity.getId())) {
            emkProduceScheduleEntity = emkProduceScheduleService.getEntity(EmkProduceScheduleEntity.class, emkProduceScheduleEntity.getId());
            req.setAttribute("emkProduceSchedule", emkProduceScheduleEntity);
        }
        return new ModelAndView("com/emk/produce/produceschedule/emkProduceSchedule-work");

    }

    @RequestMapping(params="goTime")
    public ModelAndView goTime(EmkProduceScheduleEntity emkProduceScheduleEntity, HttpServletRequest req, DataGrid dataGrid) {
        String sql = "";String countsql = "";
        Map map = ParameterUtil.getParamMaps(req.getParameterMap());

        sql = "SELECT DATE_FORMAT(t1.START_TIME_, '%Y-%m-%d %H:%i:%s') startTime,t1.*,CASE \n" +
                " WHEN t1.TASK_DEF_KEY_='produceTask' THEN t2.create_name \n" +
                " WHEN t1.TASK_DEF_KEY_='checkTask' THEN t2.leader \n" +
                " WHEN t1.TASK_DEF_KEY_='ssyTask' THEN t2.SS_SAMPLE_USER \n" +
                " WHEN t1.TASK_DEF_KEY_='cqyTask' THEN t2.CQ_SAMPLE_USER \n" +
                " WHEN t1.TASK_DEF_KEY_='syTask' THEN t2.CQ_SAMPLE_USER \n" +
                " WHEN t1.TASK_DEF_KEY_='testTask' THEN t2.test_user \n" +
                " WHEN t1.TASK_DEF_KEY_='meetingTask' THEN t2.cqhy_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='ylTask' THEN t2.ylflcg_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='ranTask' THEN t2.ran_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='caiTask' THEN t2.cai_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='fengTask' THEN t2.feng_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='zqjcTask' THEN t2.zqjc_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='btTask' THEN t2.biao_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='ztTask' THEN t2.zhengt_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='cyTask' THEN t2.chuang_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='bzTask' THEN t2.box_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='outTask' THEN t2.out_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='wqTask' THEN t2.wei_user_name \n" +
                " WHEN t1.TASK_DEF_KEY_='skTask' THEN t2.shou_user_name \n" +

                " END workname FROM act_hi_taskinst t1 \n" +
                " LEFT JOIN emk_produce t2 ON t1.ASSIGNEE_ = t2.id where ASSIGNEE_='" + map.get("id") + "' ";
        sql += " order by t1.START_TIME_ desc";

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
        emkProduceScheduleEntity = emkProduceScheduleService.getEntity(EmkProduceScheduleEntity.class, emkProduceScheduleEntity.getId());
        req.setAttribute("emkProduceSchedule", emkProduceScheduleEntity);
        return new ModelAndView("com/emk/produce/produceschedule/time");

    }
}
