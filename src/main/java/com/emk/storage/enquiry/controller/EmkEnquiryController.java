package com.emk.storage.enquiry.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.storage.cancelorder.entity.EmkCancelOrderEntity;
import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.enquiry.entity.EmkEnquiryEntityA;
import com.emk.storage.enquiry.service.EmkEnquiryServiceI;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.storage.formaterial.entity.EmkFormaterialEntity;
import com.emk.storage.formaterialother.entity.EmkFormaterialOtherEntity;
import com.emk.storage.outorder.entity.EmkOutOrderEntity;
import com.emk.storage.pay.entity.EmkPayEntity;
import com.emk.storage.payover.entity.EmkPayOverEntity;
import com.emk.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.*;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Api(value = "EmkEnquiry", description = "报单", tags = "emkEnquiryController")
@Controller
@RequestMapping("/emkEnquiryController")
public class EmkEnquiryController extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkEnquiryController.class);
    @Autowired
    private EmkEnquiryServiceI emkEnquiryService;

    @Autowired
    private Validator validator;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/emk/storage/enquiry/emkEnquiryList");
    }
    /**
     * 打印 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "jump")
    public Object jump(HttpServletRequest req) {
        Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
        /*month = DateUtil.getDate2SimpleStr(DateUtil.getDate(month),"yyyy年MM月");
        String date = DateUtil.getDate2SimpleStr(new Date(),"yyyy年MM月dd日");*/
        req.setAttribute("url", map.get("url")+"&id="+map.get("id"));
        return "forward:/context/"+map.get("r")+".jsp";
    }

    /**
     * 报单打印预览 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "printPreview")
    public ModelAndView printPreview(HttpServletRequest req) {
        Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
        EmkEnquiryEntity enquiryEntity = systemService.get(EmkEnquiryEntity.class,map.get("id"));
        List<EmkEnquiryDetailEntity> emkEnquiryDetailEntityList = systemService.findHql("from EmkEnquiryDetailEntity where enquiryId=? order by colorValue asc",enquiryEntity.getId());
        req.setAttribute("enquiryEntity",enquiryEntity);
        req.setAttribute("emkEnquiryDetailEntityList",emkEnquiryDetailEntityList);
        EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",enquiryEntity.getId());
        req.setAttribute("emkSizePage", emkSizeEntity);
        List<Map> dataList = new ArrayList<>();
        List<EmkEnquiryDetailEntity> subdataList = new ArrayList<>();
        int i= 0;
        Map b = null;

        for(EmkEnquiryDetailEntity enquiryDetailEntity : emkEnquiryDetailEntityList){
            if(i == 23){
                b = new HashMap();
                subdataList.add(enquiryDetailEntity);
                b.put("subdataList",subdataList);
                dataList.add(b);
                i = 0;
                subdataList = new ArrayList<>();
            }else{
                subdataList.add(enquiryDetailEntity);
                i++;
            }
        }
        if(subdataList.size()>0){
            b = new HashMap();
            b.put("subdataList",subdataList);
            dataList.add(b);
        }
        req.setAttribute("dataList",dataList);
        return new ModelAndView("com/emk/storage/enquiry/printPreview");

    }

    @RequestMapping(params = "orderMxList")
    public ModelAndView orderMxList(HttpServletRequest request) {
        //List<Map<String, Object>> list = systemService.findForJdbc("select code typecode,name typename from t_s_category where PARENT_CODE=? order by code asc", "A03");
        List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(po_number,'-',color,'-',nc_size) typecode from emk_po_color ");
        String color = JSONHelper.collection2json(list);
        request.setAttribute("color", "'"+color+ "'");
      /*  list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='colorNum'");
        request.setAttribute("colorNumList", list);*/
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        if (Utils.notEmpty(map.get("proOrderId"))) {
            List<EmkEnquiryDetailEntity> emkEnquiryDetailEntityList = systemService.findHql("from EmkEnquiryDetailEntity where enquiryId = ? order by colorValue asc",map.get("proOrderId"));
            request.setAttribute("emkProOrderDetailEntities", emkEnquiryDetailEntityList);
            EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("proOrderId"));
            request.setAttribute("emkSizePage", emkSizeEntity);
        }
        return new ModelAndView("com/emk/storage/enquiry/orderMxList");
    }
    @RequestMapping(params = "photo")
    public ModelAndView photo(HttpServletRequest request) {
        return new ModelAndView("com/emk/storage/enquiry/photo");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkEnquiryEntity emkEnquiry, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkEnquiryEntity.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        if(user.getUserKey().equals("工厂")) {
            EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
            cq.eq("gysCode",factoryArchivesEntity.getCompanyCode());
        }

        HqlGenerateUtil.installHql(cq, emkEnquiry, request.getParameterMap());


        cq.add();
        emkEnquiryService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkEnquiryEntity emkEnquiry, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkEnquiry = systemService.getEntity(EmkEnquiryEntity.class, emkEnquiry.getId());
        message = "报单删除成功";
        try {
            emkEnquiryService.delete(emkEnquiry);
            EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkEnquiry.getId());

            systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",emkEnquiry.getId());
            systemService.executeSql("delete from emk_approval where form_id=?",emkEnquiry.getId());
            systemService.executeSql("delete from emk_approval_detail where approval_id=?",approvalEntity.getId());

            emkEnquiryService.delete(emkEnquiry);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "报单删除失败";
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
        message = "报单删除成功";
            try {
                for (String id : ids.split(",")) {
                   EmkEnquiryEntity emkEnquiry = systemService.getEntity(EmkEnquiryEntity.class, id);
                     if (!emkEnquiry.getState().equals("0")) {
                        message = "存在已提交的报单，请重新选择！";
                        j.setMsg(message);
                        j.setSuccess(false);
                        return j;
                    }
                    if("0".equals(emkEnquiry.getState())){
                        WebFileUtils.delete( request.getRealPath("/")+emkEnquiry.getCustomSampleUrl());

                        EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkEnquiry.getId());

                        systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))",id);
                        systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",id);
                        systemService.executeSql("delete from emk_size where form_id=?", id);
                        systemService.executeSql("delete from emk_approval where form_id=?",id);

                        if(Utils.notEmpty(approvalEntity)){
                            systemService.executeSql("delete from emk_approval_detail where approval_id=?",approvalEntity.getId());
                        }
                        emkEnquiryService.delete(emkEnquiry);
                        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
                  }

                }
            } catch (Exception e) {
            e.printStackTrace();
            message = "报单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkEnquiryEntity emkEnquiry,EmkSizeEntity emkSize, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "报单添加成功";
        try {
            emkEnquiry.setState("0");
            TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            emkEnquiry.setEnquiryNo(emkEnquiry.getKdDate().replaceAll("-","").substring(4,8)+"-" + emkEnquiry.getGysCode()+"-" + emkEnquiry.getSampleNo());
            EmkEnquiryEntity t = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkEnquiry.getEnquiryNo());
            if(Utils.notEmpty(t)){
                j.setMsg("已存在"+emkEnquiry.getEnquiryNo()+"的报单，重新选择日期录入");
                j.setSuccess(false);
                return j;
            }
            emkEnquiryService.save(emkEnquiry);
            emkSize.setFormId(emkEnquiry.getId());
            systemService.save(emkSize);

            //type 工单类型，workNum 单号，formId 对应表单ID，bpmName 节点名称，bpmNode 节点代码，advice 处理意见，user 用户对象
            EmkApprovalEntity approvalEntity = new EmkApprovalEntity();
            EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
            ApprovalUtil.saveApproval(approvalEntity,0,emkEnquiry.getEnquiryNo(),emkEnquiry.getId(),user);
            systemService.save(approvalEntity);
            ApprovalUtil.saveApprovalDetail(approvalDetailEntity,approvalEntity.getId(),"报单","billTask","提交",user);
            systemService.save(approvalDetailEntity);

            Class c = Class.forName(EmkSizeTotalEntity.class.getName());
            String dataRows = (String) map.get("dataRowsVal");
            if (Utils.notEmpty(dataRows)) {
                int rows = Integer.parseInt(dataRows);
                EmkEnquiryDetailEntity orderMxEntity = null;
                EmkSizeTotalEntity emkSizeTotalEntity = null;
                String m0 = "";
                Method show = null;
                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
                        orderMxEntity = new EmkEnquiryDetailEntity();
                        orderMxEntity.setEnquiryId(emkEnquiry.getId());
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
                            String[] colorArr = map.get("orderMxList["+i+"].color").split("-");
                            orderMxEntity.setColorValue(colorArr[0]);
                            orderMxEntity.setColor(colorArr[1]);
                            orderMxEntity.setSize(colorArr[2]);
                        }
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
                            orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].total")));
                        }
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
                            orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
                        }else{
                            systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))",emkEnquiry.getId());
                            systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",emkEnquiry.getId());
                            systemService.executeSql("delete from emk_size where form_id=?", emkEnquiry.getId());
                            systemService.executeSql("delete from emk_size where form_id=?", emkEnquiry.getId());
                            systemService.delete(emkEnquiry);
                            message = orderMxEntity.getColorValue()+"-"+orderMxEntity.getColor()+"-"+orderMxEntity.getSize()+",报单中没有价格，请重新录入";
                            j.setSuccess(false);
                            j.setMsg(message);
                            return j;
                        }
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
                            orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
                        }
                        orderMxEntity.setSortDesc(String.valueOf(i+1));
                        systemService.save(orderMxEntity);

                        emkSizeTotalEntity = new EmkSizeTotalEntity();
                        for(int z = 1 ; z < 23 ; z++){
                            m0 = "setTotal"+(char)(z+64);
                            show = c.getMethod(m0,String.class);
                            show.invoke(emkSizeTotalEntity,Utils.notEmpty(map.get("orderMxList["+i+"].total"+(char)(z+64))) ? map.get("orderMxList["+i+"].total"+(char)(z+64)):"");
                        }
                        emkSizeTotalEntity.setpId(orderMxEntity.getId());
                        systemService.save(emkSizeTotalEntity);
                    }
                }
            }

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "报单添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkEnquiryEntity emkEnquiry,EmkSizeEntity emkSize, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "报单更新成功";
        Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
        EmkEnquiryEntity t = emkEnquiryService.get(EmkEnquiryEntity.class, emkEnquiry.getId());
        EmkSizeEntity t2 = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",t.getId());
        try {
            if(!t.getState().equals("0")){
                j.setMsg("报单已提交，无法进行修改");
                j.setSuccess(false);
                return j;
            }
            emkEnquiry.setState("0");
            MyBeanUtils.copyBeanNotNull2Bean(emkEnquiry, t);
            emkEnquiryService.saveOrUpdate(t);

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

            String dataRows = (String) map.get("dataRowsVal");
            if (Utils.notEmpty(dataRows)) {
                Class c = Class.forName(EmkSizeTotalEntity.class.getName());
                systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))", t.getId());
                systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",t.getId());

                int rows = Integer.parseInt(dataRows);
                EmkEnquiryDetailEntity orderMxEntity = null;
                EmkSizeTotalEntity emkSizeTotalEntity = null;
                String m0 = "";
                Method show = null;
                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
                        orderMxEntity = new EmkEnquiryDetailEntity();
                        orderMxEntity.setEnquiryId(emkEnquiry.getId());
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
                            String[] colorArr = map.get("orderMxList["+i+"].color").split("-");
                            orderMxEntity.setColorValue(colorArr[0]);
                            orderMxEntity.setColor(colorArr[1]);
                            orderMxEntity.setSize(colorArr[2]);
                        }
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
                            orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].total")));
                        }
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
                            orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
                        }
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
                            orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
                        }
                        orderMxEntity.setSortDesc(String.valueOf(i+1));

                        systemService.save(orderMxEntity);
                        emkSizeTotalEntity = new EmkSizeTotalEntity();
                        for(int z = 1 ; z < 23 ; z++){
                            m0 = "setTotal"+(char)(z+64);
                            show = c.getMethod(m0,String.class);
                            show.invoke(emkSizeTotalEntity,Utils.notEmpty(map.get("orderMxList["+i+"].total"+(char)(z+64))) ? map.get("orderMxList["+i+"].total"+(char)(z+64)):"");
                        }
                        emkSizeTotalEntity.setpId(orderMxEntity.getId());
                        systemService.save(emkSizeTotalEntity);
                    }
                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "报单更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doPrintPDF")
    public String doPrintPDF(String ids,EmkEnquiryEntity emkEnquiry, HttpServletRequest request,HttpServletResponse response) {
        String message = null;

        try {
            for (String id : ids.split(",")) {
                String fileName = "c:\\PDF\\"+DateUtil.format(new Date(),"yyyyMMddHHmmss")+".pdf";
                File file = new File(fileName);
                File dir = file.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                emkEnquiry = emkEnquiryService.getEntity(EmkEnquiryEntity.class, id);

                StringBuilder sql = new StringBuilder();
                sql.append("select t.enquiry_id enquiryId,(select typename from t_s_type a2 left join t_s_typegroup a1 on a1.ID=a2.typegroupid where a1.typegroupcode='color' and a2.typecode=t.color) colorName,t.color_value colorVal,t.size,t1.* \n" +
                         " from emk_enquiry_detail t left join emk_size_total t1  on t1.p_id=t.id \n" +
                         " where t.enquiry_id=? order by t.sort_desc asc \n");

                List<Map<String, Object>> emkProOrderDetailEntities = systemService.findForJdbc(sql.toString(),emkEnquiry.getId());

                EmkEnquiryEntityA emkEnquiryEntityA = new EmkEnquiryEntityA();
                MyBeanUtils.copyBeanNotNull2Bean(emkEnquiry,emkEnquiryEntityA);
                if(Utils.notEmpty(emkEnquiry.getCustomSampleUrl())){
                    emkEnquiryEntityA.setCustomSampleUrl(request.getRealPath("/")+emkEnquiry.getCustomSampleUrl());
                }

                EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",emkEnquiry.getId());
                Map type = systemService.findOneForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='gylx' and typecode=?",emkEnquiry.getGyzl());
                emkEnquiryEntityA.setGyzl(type.get("typename").toString());

                new createPdf(file).generateEmkEnquiryPDF(emkEnquiryEntityA,emkProOrderDetailEntities,emkSizeEntity,findProcessList(id));
                String fFileName = "c:\\PDF\\F"+DateUtil.format(new Date(),"yyyyMMddHHmmss")+".pdf";
                WaterMark.waterMark(fileName,fFileName, "报单");
                file.delete();
                WebFileUtils.downLoad(fFileName,response,false);
                file = new File(fFileName);
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "报单导出PDF失败";
            throw new BusinessException(e.getMessage());
        }
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkEnquiryEntity emkEnquiry, HttpServletRequest req) {
      /*  List<Map<String, Object>> list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
        req.setAttribute("colorList", list);
        list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='colorNum'");
        req.setAttribute("colorNumList", list);*/
        req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
        if (StringUtil.isNotEmpty(emkEnquiry.getId())) {
            emkEnquiry = emkEnquiryService.getEntity(EmkEnquiryEntity.class, emkEnquiry.getId());
            req.setAttribute("emkEnquiryPage", emkEnquiry);
        }
        return new ModelAndView("com/emk/storage/enquiry/emkEnquiry-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkEnquiryEntity emkEnquiry, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkEnquiry.getId())) {
            emkEnquiry = emkEnquiryService.getEntity(EmkEnquiryEntity.class, emkEnquiry.getId());
            req.setAttribute("emkEnquiryPage", emkEnquiry);
        }
        return new ModelAndView("com/emk/storage/enquiry/emkEnquiry-update");
    }

    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(EmkEnquiryEntity emkEnquiry, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkEnquiry.getId())) {
            emkEnquiry = emkEnquiryService.getEntity(EmkEnquiryEntity.class, emkEnquiry.getId());
            // 需要对比的源字符串
            String str = emkEnquiry.getEnquiryNo();
            // 需要对比的字符串
            String compareStr = "-";
            //字符串查找初始从0开始查找
            int indexStart = 0;
            //获取查找字符串的长度，这里有个规律：第二次查找出字符串的起始位置等于 第一次ab字符串出现的位置+ab的长度
            int compareStrLength = compareStr.length();
            int count = 0;
            while(true){
                int tm = str.indexOf(compareStr,indexStart);
                if( tm >= 0){
                    count ++;
                    //  没查找一次就从新计算下次开始查找的位置
                    indexStart = tm+compareStrLength;
                }else{
                    //直到没有匹配结果为止
                    break;
                }
            }
            if (count > 2) {
                str = str.substring(0, str.lastIndexOf("-"));
            }
            emkEnquiry = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",str);

            req.setAttribute("emkEnquiryPage", emkEnquiry);
        }
        return new ModelAndView("com/emk/storage/enquiry/emkEnquiry-update2");
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
        return new ModelAndView("com/emk/storage/enquiry/uploadView");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkEnquiryEntity emkEnquiry, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkEnquiryEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkEnquiry, request.getParameterMap());
        List<EmkEnquiryEntity> emkEnquirys = emkEnquiryService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "报单");
        modelMap.put("entity", EmkEnquiryEntity.class);
        modelMap.put("params", new ExportParams("报单列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkEnquirys);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkEnquiryEntity emkEnquiry, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "报单");
        modelMap.put("entity", EmkEnquiryEntity.class);
        modelMap.put("params", new ExportParams("报单列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "报单列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkEnquiryEntity>> list() {
        List<EmkEnquiryEntity> listEmkEnquirys = emkEnquiryService.getList(EmkEnquiryEntity.class);
        return Result.success(listEmkEnquirys);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取报单信息", notes = "根据ID获取报单信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkEnquiryEntity task = emkEnquiryService.get(EmkEnquiryEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取报单信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建报单")
    public ResponseMessage<?> create(@ApiParam(name = "报单对象") @RequestBody EmkEnquiryEntity emkEnquiry, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkEnquiryEntity>> failures = validator.validate(emkEnquiry, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkEnquiryService.save(emkEnquiry);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("报单信息保存失败");
        }
        return Result.success(emkEnquiry);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新报单", notes = "更新报单")
    public ResponseMessage<?> update(@ApiParam(name = "报单对象") @RequestBody EmkEnquiryEntity emkEnquiry) {
        Set<ConstraintViolation<EmkEnquiryEntity>> failures = validator.validate(emkEnquiry, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkEnquiryService.saveOrUpdate(emkEnquiry);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新报单信息失败");
        }
        return Result.success("更新报单信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除报单")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            emkEnquiryService.deleteEntityById(EmkEnquiryEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("报单删除失败");
        }
        return Result.success();
    }

    @RequestMapping(params="doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(EmkEnquiryEntity emkEnquiryEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "报单提交成功";
        try {
            int flag = 0;
            TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

            if ((Utils.isEmpty(emkEnquiryEntity.getId()))) {
                for (String id : map.get("ids").split(",")) {
                    EmkEnquiryEntity enquiryEntity = systemService.getEntity(EmkEnquiryEntity.class, id);
                    if (!enquiryEntity.getState().equals("0")) {
                        message = "存在已提交的报单，请重新选择在提交！";
                        j.setSuccess(false);
                        flag = 1;
                        break;
                    }
                }
            }else{
                map.put("ids", emkEnquiryEntity.getId());
            }
            Map<String, Object> variables = new HashMap();
            if (flag == 0) {
                for (String id : map.get("ids").split(",")) {
                    EmkEnquiryEntity t = emkEnquiryService.get(EmkEnquiryEntity.class, id);
                    variables.put("optUser", t.getId());
                    EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());

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
                        if (task1.getTaskDefinitionKey().equals("billTask")) {
                            taskService.complete(task1.getId(), variables);
                            t.setState("1");
                            b.setStatus(1);
                            saveApprvoalDetail(approvalDetail,"重新提交报单","billTask",0,"重新提交报单");
                            saveSmsAndEmailForMany("工厂","报单审核","您有【"+b.getCreateName()+"】重新提交的报单，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                            systemService.save(approvalDetail);
                        }

                        if (task1.getTaskDefinitionKey().equals("checkTask")) {
                            if(map.get("isPass").equals("0")) {
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(4);
                                approvalDetail.setBpmName("叫布");
                                t.setState("4");
                                approvalDetail.setApproveStatus(0);
                                EmkFormaterialEntity emkFormaterialEntity = systemService.findUniqueByProperty(EmkFormaterialEntity.class,"orderNo",t.getEnquiryNo());
                                emkFormaterialEntity.setState("4");
                                Map userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='普通用户' or t.userkey='管理员'");
                                saveSmsAndEmailForMany("普通用户","叫布","您有【"+b.getCreateName()+"】核实通过的叫布，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                                saveSmsAndEmailForMany("管理员","叫布","您有【"+b.getCreateName()+"】核实通过的叫布，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                            }else{
                                saveApprvoalDetail(approvalDetail,"核实","checkTask",1,"【回退】"+map.get("advice"));
                                backProcess(task1.getProcessInstanceId(),"checkTask","jbTask","叫布");
                                t.setState("5");
                                b.setStatus(5);
                                b.setBpmStatus("1");
                                approvalDetail.setApproveStatus(1);
                                EmkFormaterialEntity emkFormaterial = systemService.findUniqueByProperty(EmkFormaterialEntity.class,"orderId",t.getId());
                                emkFormaterial.setState("3");
                                saveSmsAndEmailForOne("核实","您有【"+user.getRealName()+"】回退的叫布，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                        }
                        if (task1.getTaskDefinitionKey().equals("gcqrTask")) {
                            if(map.get("isPass").equals("0")) {
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(7);
                                approvalDetail.setBpmName("工厂确认");
                                t.setState("7");
                                approvalDetail.setApproveStatus(0);

                                EmkFormaterialOtherEntity emkFormaterialOtherEntity = systemService.findUniqueByProperty(EmkFormaterialOtherEntity.class,"orderNo",t.getEnquiryNo());
                                emkFormaterialOtherEntity.setState("4");

                                //Map userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='普通用户' or t.userkey='管理员'");
                                //saveSmsAndEmailForGc(user,t.getGys(),"工厂确认","您有【"+user.getRealName()+"】工厂确认通过的其他物料发放，单号："+b.getWorkNum()+"，请及时审核。");
                                saveSmsAndEmailForMany("普通用户","工厂确认","您有【"+b.getCreateName()+"】工厂确认通过的其他物料发放，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                                saveSmsAndEmailForMany("管理员","工厂确认","您有【"+b.getCreateName()+"】工厂确认通过的其他物料发放，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                            }else{
                                saveApprvoalDetail(approvalDetail,"工厂确认","gcqrTask",1,"【回退】"+map.get("advice"));
                                backProcess(task1.getProcessInstanceId(),"gcqrTask","otherTask","其他物料发放");
                                t.setState("8");
                                b.setStatus(8);
                                b.setBpmStatus("1");
                                approvalDetail.setApproveStatus(1);
                                EmkFormaterialOtherEntity emkFormaterial = systemService.findUniqueByProperty(EmkFormaterialOtherEntity.class,"orderId",t.getId());
                                emkFormaterial.setState("3");
                                saveSmsAndEmailForOne("工厂确认","您有【"+user.getRealName()+"】回退的其他物料发放，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                        }
                        if (task1.getTaskDefinitionKey().equals("yanhuoTask")) {
                            if(map.get("isPass").equals("0")) {
                                variables.put("isBack",map.get("isBack"));
                                taskService.complete(task1.getId(), variables);
                                approvalDetail.setBpmName("验货");
                                if("0".equals(map.get("isBack").toString())){
                                    b.setStatus(11);
                                    t.setState("11");
                                }else{
                                    b.setStatus(18);
                                    t.setState("18");
                                }

                                EmkOutOrderEntity emkOutOrderEntity = systemService.findUniqueByProperty(EmkOutOrderEntity.class,"orderNo",t.getEnquiryNo());
                                emkOutOrderEntity.setState("4");

                                approvalDetail.setApproveStatus(0);

                                //Map userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='工厂'");
                                //saveSmsAndEmailForMany("工厂","验货","您有【"+b.getCreateName()+"】通过的出货，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

                                //Map userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t left join t_s_depart d on d.id=t.departid where t.userkey='工厂' and d.departname=?",t.getGys());
                                //saveSmsAndEmailForGc(user,t.getGys(),"其他物料发放","您有【"+user.getRealName()+"】通过的出货，单号："+b.getWorkNum()+"，请及时审核。");

                            }else{
                                saveApprvoalDetail(approvalDetail,"验货","yanhuoTask",1,"【回退】"+map.get("advice"));
                                backProcess(task1.getProcessInstanceId(),"yanhuoTask","outhuoTask","工厂出货");
                                t.setState("10");
                                b.setStatus(10);
                                b.setBpmStatus("1");
                                approvalDetail.setApproveStatus(1);
                                EmkOutOrderEntity emkOutOrderEntity = systemService.findUniqueByProperty(EmkOutOrderEntity.class,"orderId",t.getId());
                                emkOutOrderEntity.setState("3");
                                saveSmsAndEmailForOne("验货","您有【"+user.getRealName()+"】回退的出货，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                        }
                        if (task1.getTaskDefinitionKey().equals("gcjsTask")) {
                            Map  userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='普通用户' or t.userkey='管理员'");
                            if(map.get("isPass").equals("0")) {
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(14);
                                approvalDetail.setBpmName("工厂接收");
                                t.setState("14");
                                approvalDetail.setApproveStatus(0);

                                EmkCancelOrderEntity emkCancelOrderEntity = systemService.findUniqueByProperty(EmkCancelOrderEntity.class,"orderNo",t.getEnquiryNo());
                                emkCancelOrderEntity.setState("4");


                                saveSmsAndEmailForMany("普通用户","工厂接收","您有【"+b.getCreateName()+"】通过的退货明细表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                                saveSmsAndEmailForMany("管理员","工厂接收","您有【"+b.getCreateName()+"】通过的退货明细表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

                            }else{
                                saveApprvoalDetail(approvalDetail,"工厂接收","gcjsTask",1,"【回退】"+map.get("advice"));
                                backProcess(task1.getProcessInstanceId(),"gcjsTask","tuihuoTask","退货明细表");
                                t.setState("16");
                                b.setStatus(16);
                                b.setBpmStatus("1");
                                approvalDetail.setApproveStatus(1);
                                EmkCancelOrderEntity emkCancelOrderEntity = systemService.findUniqueByProperty(EmkCancelOrderEntity.class,"orderId",t.getId());
                                emkCancelOrderEntity.setState("3");

                                saveSmsAndEmailForMany("普通用户","工厂接收","您有【"+b.getCreateName()+"】回退的退货明细表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                                saveSmsAndEmailForMany("管理员","工厂接收","您有【"+b.getCreateName()+"】回退的退货明细表，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                            }
                            if(Utils.notEmpty(userM)){
                                b.setNextBpmSherId(userM.get("userNames").toString());
                                b.setNextBpmSher(userM.get("realNames").toString());
                            }
                        }

                        if (task1.getTaskDefinitionKey().equals("fkgcqrTask")) {
                            Map  userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='普通用户' or t.userkey='管理员'");
                            if(map.get("isPass").equals("0")) {
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(17);
                                approvalDetail.setBpmName("工厂确认");
                                t.setState("17");
                                approvalDetail.setApproveStatus(0);

                                EmkPayEntity emkPayEntity = systemService.findUniqueByProperty(EmkPayEntity.class,"orderNo",t.getEnquiryNo());
                                emkPayEntity.setState("4");

                                saveSmsAndEmailForMany("普通用户","工厂确认","您有【"+b.getCreateName()+"】工厂确认通过的付款，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                                saveSmsAndEmailForMany("管理员","工厂确认","您有【"+b.getCreateName()+"】工厂确认通过的付款，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

                            }else{
                                saveApprvoalDetail(approvalDetail,"工厂确认","fkgcqrTask",1,"【回退】"+map.get("advice"));
                                backProcess(task1.getProcessInstanceId(),"fkgcqrTask","fkTask","付款");
                                t.setState("16");
                                b.setStatus(16);
                                b.setBpmStatus("1");
                                approvalDetail.setApproveStatus(1);
                                Map pay = systemService.findOneForJdbc("select id from emk_pay where order_id=? order by CREATE_DATE desc limit 0,1 ",t.getId());
                                if(Utils.notEmpty(pay)){
                                    EmkPayEntity payEntity = systemService.get(EmkPayEntity.class,pay.get("id").toString());
                                    payEntity.setState("3");
                                }
                                saveSmsAndEmailForOne("工厂确认","您有【"+user.getRealName()+"】工厂回退的付款，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                            if(Utils.notEmpty(userM)){
                                b.setNextBpmSherId(userM.get("userNames").toString());
                                b.setNextBpmSher(userM.get("realNames").toString());
                            }
                        }
                        if (task1.getTaskDefinitionKey().equals("cwjsTask")) {
                            variables.put("isFinish",map.get("isPass"));
                            Map  userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='普通用户' or t.userkey='管理员'");
                            if(map.get("isPass").equals("0")) {
                                taskService.complete(task1.getId(), variables);
                                b.setStatus(2);
                                approvalDetail.setBpmName("财务结算");
                                t.setState("2");
                                approvalDetail.setApproveStatus(0);

                                saveSmsAndEmailForMany("普通用户","财务结算","您有【"+b.getCreateName()+"】财务结算完成的付款，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                                saveSmsAndEmailForMany("管理员","财务结算","您有【"+b.getCreateName()+"】财务结算完成的付款，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                                saveSmsAndEmailForMany("工厂","财务结算","您有【"+b.getCreateName()+"】财务结算完成的付款，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());

                                String str = t.getEnquiryNo();
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

                                EmkPayOverEntity emkPayOverEntity = new EmkPayOverEntity();
                                emkPayOverEntity.setOrderNo(t.getEnquiryNo());
                                Map p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_enquiry t3 on t3.id=t.enquiry_id where t3.enquiry_no like '%"+str+"%' and  t3.state!=0");
                                emkPayOverEntity.setOrderMoney(p.get("money").toString());
                                p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_formaterail_detail t left join emk_formaterial t3 on t3.id=t.formaterial_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
                                emkPayOverEntity.setJbMoney(p.get("money").toString());
                                p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_formaterail_other_detail t left join emk_formaterial_other t3 on t3.id=t.formaterail_other_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
                                emkPayOverEntity.setWlMoney(p.get("money").toString());
                                p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_out_order t3 on t3.id=t.enquiry_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
                                emkPayOverEntity.setFhMoney(p.get("money").toString());
                                p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_enquiry_detail t left join emk_cancel_order t3 on t3.id=t.enquiry_id where t3.order_no like '%"+str+"%' and (t3.state=4 or t3.state=2)");
                                emkPayOverEntity.setThMoney(p.get("money").toString());
                                p = systemService.findOneForJdbc("select ifnull(sum(t.money),0) money from emk_pay t where t.order_no like '%"+str+"%' and (t.state=4 or t.state=2)");
                                emkPayOverEntity.setFkMoney(p.get("money").toString());

                                emkPayOverEntity.setOverDate(DateUtil.getCurrentTimeString(null));

                                systemService.save(emkPayOverEntity);

                                systemService.executeSql("update emk_enquiry set state=2 where enquiry_no like '%"+str+"%' and state!=0");
                                systemService.executeSql("update emk_formaterial set state=2 where order_no like '%"+str+"%' and state=3");
                                systemService.executeSql("update emk_formaterial_other set state=2 where order_no like '%"+str+"%' and state=3");
                                systemService.executeSql("update emk_out_order set state=2 where order_no like '%"+str+"%' and state=3");
                                systemService.executeSql("update emk_cancel_order set state=2 where order_no like'%"+str+"%' and state=3");

                            }else{
                                saveApprvoalDetail(approvalDetail,"财务结算","cwjsTask",1,"【回退】"+map.get("advice"));
                                backProcess(task1.getProcessInstanceId(),"cwjsTask","fkgcqrTask","工厂确认");
                                systemService.executeSql("delete from act_hi_actinst  where proc_inst_id_=? and act_id_=? ",task1.getProcessInstanceId(),"exclusivegateway2");
                                backProcess(task1.getProcessInstanceId(),"fkgcqrTask","fkTask","付款");

                                t.setState("19");
                                b.setStatus(19);
                                b.setBpmStatus("1");
                                approvalDetail.setApproveStatus(1);
                                saveSmsAndEmailForOne("财务结算","您有【"+user.getRealName()+"】财务结算未完的付款，单号："+b.getWorkNum()+"，请及时处理。",bpmUser,user.getUserName());
                            }
                            if(Utils.notEmpty(userM)){
                                b.setNextBpmSherId(userM.get("userNames").toString());
                                b.setNextBpmSher(userM.get("realNames").toString());
                            }
                        }
                        systemService.save(approvalDetail);
                    }else {
                        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("femk", "emkEnquiryEntity", variables);
                        task = taskService.createTaskQuery().taskAssignee(id).list();
                        Task task1 = task.get(task.size() - 1);
                        taskService.complete(task1.getId(), variables);
                        //saveSmsAndEmailForMany("工厂","报单审核","您有【"+b.getCreateName()+"】提交的报单，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                        saveSmsAndEmailForGc(user,t.getGys(),"报单审核","您有【"+user.getRealName()+"】提交的报单，单号："+b.getWorkNum()+"，请及时审核。");
                        saveSmsAndEmailForMany("普通用户","叫布","您有【"+b.getCreateName()+"】提交的报单，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                        saveSmsAndEmailForMany("管理员","叫布","您有【"+b.getCreateName()+"】提交的报单，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
                        t.setState("1");
                        b.setStatus(1);
                        b.setBpmStatus("0");
                        b.setProcessName("报单");

                        saveApprvoalDetail(approvalDetail,"提交报单","billTask",0,"提交报单");

                    }

                    systemService.saveOrUpdate(t);
                    systemService.saveOrUpdate(b);
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

    @RequestMapping(params="goWork")
    public ModelAndView goWork(EmkEnquiryEntity emkEnquiryEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkEnquiryEntity.getId())) {
            emkEnquiryEntity = emkEnquiryService.getEntity(EmkEnquiryEntity.class, emkEnquiryEntity.getId());
            req.setAttribute("emkEnquiry", emkEnquiryEntity);
        }
        return new ModelAndView("com/emk/storage/enquiry/emkEnquiry-work");

    }

    @RequestMapping(params="goTime")
    public ModelAndView goTime(EmkEnquiryEntity emkEnquiryEntity, HttpServletRequest req, DataGrid dataGrid) {
        EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkEnquiryEntity.getId());
        if(Utils.notEmpty(approvalEntity)){
            List<EmkApprovalDetailEntity> approvalDetailEntityList = systemService.findHql("from EmkApprovalDetailEntity where approvalId=? order by approveDate asc",approvalEntity.getId());
            req.setAttribute("approvalDetailEntityList", approvalDetailEntityList);
            req.setAttribute("approvalEntity", approvalEntity);
            req.setAttribute("createDate", DateUtils.date2Str(approvalEntity.getCreateDate(),DateUtils.datetimeFormat));
        }



        return new ModelAndView("com/emk/storage/enquiry/time");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(EmkEnquiryEntity emkEnquiry,String fileName,String fileNameUrl,HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "文件导入成功";
        File newfile = null;
        HSSFWorkbook wb = null;
        String cellValue = "";
        EmkEnquiryDetailEntity orderMxEntity = null;
        EmkSizeTotalEntity emkSizeTotalEntity = null;
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
            String m0 = "";
            Method show = null;

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                itemValue = new ArrayList<String>();
                for(int z = 0; z <= 28 ; z++){
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
                if(i == 0 ){
                    emkEnquiry.setState("0");
                    TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
                    Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
                    emkEnquiry.setEnquiryNo(emkEnquiry.getKdDate().replaceAll("-","").substring(4,8)+"-" + emkEnquiry.getGysCode()+"-" + emkEnquiry.getSampleNo());
                    EmkEnquiryEntity t = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkEnquiry.getEnquiryNo());
                    if(Utils.notEmpty(t)){
                        j.setMsg("已存在"+emkEnquiry.getEnquiryNo()+"的报单，重新选择日期录入");
                        j.setSuccess(false);
                        return j;
                    }
                    emkEnquiryService.save(emkEnquiry);

                    EmkSizeEntity emkSize = new EmkSizeEntity();
                    Class c = Class.forName(EmkSizeEntity.class.getName());
                    emkSize.setFormId(emkEnquiry.getId());
                    for(int z = 1 ; z < 23 ; z++){
                        m0 = "setSize"+(char)(z+64);
                        show = c.getMethod(m0,String.class);
                        show.invoke(emkSize,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
                    }
                    systemService.save(emkSize);

                    //type 工单类型，workNum 单号，formId 对应表单ID，bpmName 节点名称，bpmNode 节点代码，advice 处理意见，user 用户对象
                    EmkApprovalEntity approvalEntity = new EmkApprovalEntity();
                    EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
                    ApprovalUtil.saveApproval(approvalEntity,0,emkEnquiry.getEnquiryNo(),emkEnquiry.getId(),user);
                    systemService.save(approvalEntity);
                    ApprovalUtil.saveApprovalDetail(approvalDetailEntity,approvalEntity.getId(),"报单","billTask","提交",user);
                    systemService.save(approvalDetailEntity);
                }else{
                    if(Utils.notEmpty(itemValue.get(0))){
                        orderMxEntity = new EmkEnquiryDetailEntity();
                        orderMxEntity.setEnquiryId(emkEnquiry.getId());
                        orderMxEntity.setColorValue(itemValue.get(0));
                        orderMxEntity.setColor(itemValue.get(1));
                        orderMxEntity.setSize(itemValue.get(2));

                        orderMxEntity.setSortDesc(String.valueOf(i+1));

                        emkSizeTotalEntity = new EmkSizeTotalEntity();
                        Class c = Class.forName(EmkSizeTotalEntity.class.getName());
                        int total = 0;
                        for(int z = 1 ; z < 23 ; z++){
                            m0 = "setTotal"+(char)(z+64);
                            show = c.getMethod(m0,String.class);
                            total += Integer.valueOf(Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"0");
                            show.invoke(emkSizeTotalEntity,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
                        }
                        orderMxEntity.setTotal(total);
                        if(Utils.notEmpty(itemValue.get(27))){
                            orderMxEntity.setPrice(Double.parseDouble(itemValue.get(27)));
                        }else{
                            systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))",emkEnquiry.getId());
                            systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",emkEnquiry.getId());
                            systemService.executeSql("delete from emk_size where form_id=?", emkEnquiry.getId());
                            systemService.executeSql("delete from emk_size where form_id=?", emkEnquiry.getId());
                            systemService.delete(emkEnquiry);
                            message = orderMxEntity.getColorValue()+"-"+orderMxEntity.getColor()+"-"+orderMxEntity.getSize()+",报单中没有价格，请重新录入";
                            j.setSuccess(false);
                            j.setMsg(message);
                            return j;
                        }
                        orderMxEntity.setMoney(total*orderMxEntity.getPrice());
                        systemService.save(orderMxEntity);

                        emkSizeTotalEntity.setpId(orderMxEntity.getId());
                        systemService.save(emkSizeTotalEntity);
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
}
