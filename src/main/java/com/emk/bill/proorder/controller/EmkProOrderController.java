package com.emk.bill.proorder.controller;

import com.alibaba.fastjson.JSONArray;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;

import com.emk.bill.proorder.entity.EmkProOrderEntity;
import com.emk.bill.proorder.service.EmkProOrderServiceI;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;

import com.emk.bound.minstorage.entity.EmkMInStorageEntity;
import com.emk.caiwu.bankrecord.entity.EmkBankRecordEntity;
import com.emk.caiwu.bankrecord.entity.EmkBankRecordLogEntity;
import com.emk.check.sizecheck.entity.*;

import com.emk.product.product.entity.EmkProductEntity;
import com.emk.storage.storagelog.entity.EmkStorageLogEntity;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import com.emk.util.*;
import com.service.custom.entity.YmkCustomEntity;
import com.service.custom.entity.YmkCustomEntityA;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
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
import org.jeecgframework.core.util.*;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSTypeA;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Api(value = "EmkProOrder", description = "订单", tags = "emkProOrderController")
@Controller
@RequestMapping("/emkProOrderController")
public class EmkProOrderController extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkProOrderController.class);
    @Autowired
    private EmkProOrderServiceI emkProOrderService;

    @Autowired
    private Validator validator;


    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A05' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("headcategoryEntities",categoryEntities);

        return new ModelAndView("com/emk/bill/proorder/emkProOrderList");
    }
    @RequestMapping(params = "listForCg")
    public ModelAndView listForCg(HttpServletRequest request) {
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A05' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("headcategoryEntities",categoryEntities);

        return new ModelAndView("com/emk/bill/proorder/emkProOrderListForCg");
    }

    @RequestMapping(params = "detailMxList")
    public ModelAndView detailMxList(HttpServletRequest request) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        /*List<Map<String, Object>> list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
        request.setAttribute("colorList", list);*/
        List<Map<String, Object>> list = systemService.findForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
        String color = JSONHelper.collection2json(list);
        request.setAttribute("color", "'"+color+ "'");
        if (Utils.notEmpty(map.get("proOrderId"))) {
            List<EmkProOrderDetailEntity> emkProOrderDetailEntities = systemService.findHql("from EmkProOrderDetailEntity where proOrderId=?", map.get("proOrderId"));
            request.setAttribute("emkProOrderDetailEntities", emkProOrderDetailEntities);

            EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("proOrderId"));
            request.setAttribute("emkSizePage", emkSizeEntity);
        }
        return new ModelAndView("com/emk/bill/proorder/detailMxList");
    }

    @RequestMapping(params = "orderMxList")
    public ModelAndView orderMxList(HttpServletRequest request) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show  from hm_category where PARENT_CODE='A01A09'  and type='1' and code!='A01A09A13' and code!='A01A09A05' and org_code=? order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("categoryEntities",categoryEntities);

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
        if (Utils.notEmpty(map.get("proOrderId"))) {
            List<EmkProOrderDetailEntity> emkProOrderDetailEntityList = systemService.findHql("from EmkProOrderDetailEntity where proOrderId=? ", map.get("proOrderId"));
            request.setAttribute("rkglMxList", emkProOrderDetailEntityList);
        }
        return new ModelAndView("com/emk/bill/proorder/orderMxList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(EmkProOrderEntity emkProOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkProOrderEntity.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        if(!user.getUserName().equals("admin")){
            cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
        }
        HqlGenerateUtil.installHql(cq, emkProOrder, request.getParameterMap());


        cq.add();
        emkProOrderService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkProOrderEntity emkProOrder, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkProOrder = systemService.getEntity(EmkProOrderEntity.class, emkProOrder.getId());
        message = "订单删除成功";
        try {
            if (!emkProOrder.getState().equals("0")) {
                message = "订单已经提交处理，无法删除";
                j.setMsg(message);
                j.setSuccess(false);
                return j;
            }
            systemService.executeSql("delete from emk_pro_order_detail where PRO_ORDER_ID=?", emkProOrder.getId());
            emkProOrderService.delete(emkProOrder);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单删除失败";
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
        message = "订单删除成功";
        try {
            for (String id : ids.split(",")) {
                EmkProOrderEntity emkProOrder = systemService.getEntity(EmkProOrderEntity.class, id);
                /*if (!emkProOrder.getState().equals("0")) {
                    message = "存在已提交的订单，请重新选择在提交订单！";
                    j.setSuccess(false);
                    j.setMsg(message);
                    return  j;
                }*/

                systemService.executeSql("delete from emk_m_in_storage_detail where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_pro_order_detail where PRO_ORDER_ID=?))", emkProOrder.getId());
                systemService.executeSql("delete from emk_pro_order_detail where PRO_ORDER_ID=?",emkProOrder.getId());

                emkProOrderService.delete(emkProOrder);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     *  缓存订单明细数据
     *
     * @param
     * @return
     */
    @RequestMapping(params = "setOrderList")
    @ResponseBody
    public AjaxJson setOrderList(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "保存成功";
        try {
            Map<String,String> param = ParameterUtil.getParamMaps(request.getParameterMap());
            List<EmkProOrderDetailEntity> emkProOrderDetailEntityList = systemService.findHql("from EmkProOrderDetailEntity where proOrderId=?",param.get("proOrderId"));
            request.getSession().setAttribute("emkProOrderDetailEntityList",emkProOrderDetailEntityList);
            request.getSession().setAttribute("proOrderId",param.get("proOrderId"));

        } catch (Exception e) {
            e.printStackTrace();
            message = "保存失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "setOrderSession")
    @ResponseBody
    public AjaxJson setOrderSession(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "保存成功";
        try {
            request.getSession().setAttribute("emkProOrderDetailEntityList","");
            request.getSession().setAttribute("proOrderId","");

        } catch (Exception e) {
            e.printStackTrace();
            message = "保存失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkProOrderEntity emkProOrder,EmkSizeEntity emkSize, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "订单添加成功";
        try {
            emkProOrder.setState("0");
            emkProOrder.setPayState("0");
            TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
            TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
            String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);

            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            YmkCustomEntityA ymkCustom = systemService.singleResult("from YmkCustomEntityA where cusName='"+emkProOrder.getCusName()+"' and orgCode='"+orgCode+"'");
            if(Utils.isEmpty(ymkCustom)){
                ymkCustom = new YmkCustomEntityA();
                ymkCustom.setStatus("0");
                Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(daan_num, 3)),0)+1 AS signed) orderNum from ymk_custom where org_code=? ",user.getCurrentDepart().getOrgCode());
                ymkCustom.setDaanNum( "KHDA1" + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
                ymkCustom.setCusName(emkProOrder.getCusName());
                ymkCustom.setCusNum( String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
                ymkCustom.setCusCode(ChineseToEnglish.getPinYinHeadChar(ymkCustom.getCusName()));
                ymkCustom.setZlxr(emkProOrder.getA01a05a08());
                ymkCustom.setAddress(emkProOrder.getA01a05a07());
                ymkCustom.setWorkphone(emkProOrder.getA01a05a09());
                ymkCustom.setDepartId(tsDepart.getId());
                ymkCustom.setOrgCode(tsDepart.getOrgCode());
                ymkCustom.setBcqkMoney("0");
                systemService.save(ymkCustom);
                emkProOrder.setA01a05a02(ymkCustom.getCusNum());
            }

            EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='订单前缀' and departId='"+tsDepart.getId()+"'");
            Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(a01a05a01, 4)),0)+1 AS signed) orderNum from emk_pro_order where org_code=?",orgCode);
            emkProOrder.setA01a05a01(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
            emkProOrder.setDepartId(tsDepart.getId());
            emkProOrder.setOrgCode(tsDepart.getOrgCode());
            emkProOrderService.save(emkProOrder);

            YmkCustomEntity ymkCustomEntity = systemService.singleResult("from YmkCustomEntity where cusNum='"+emkProOrder.getA01a05a02()+"' and orgCode='"+orgCode+"'");
            if(Utils.notEmpty(emkProOrder.getBcqkMoney())){
                ymkCustomEntity.setBcqkMoney(emkProOrder.getBcqkMoney());
            }

            List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09'  and type='1' and org_code=?  order by order_num asc",orgCode);
            EmkProOrderDetailEntity emkProOrderDetailEntity = null;
            Class c = Class.forName(EmkProOrderDetailEntity.class.getName());

            Method show = null;
            String dataRows = map.get("orderMxListID");
            List<EmkProductEntity> emkProductEntityList = null;
            EmkProductEntity emkProductEntity = null;
            
            if (Utils.notEmpty(dataRows)) {
                int rows = Integer.parseInt(dataRows);

                for (int i = 0; i < rows; i++) {
                    if(Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
                        emkProOrderDetailEntity = new EmkProOrderDetailEntity();
                        emkProOrderDetailEntity.setDepartId(emkProOrder.getDepartId());
                        emkProOrderDetailEntity.setOrgCode(emkProOrder.getOrgCode());
                        emkProOrderDetailEntity.setProOrderId(emkProOrder.getId());
                        emkProOrderDetailEntity.setPriceId(map.get("orderMxList["+i+"].priceId"));
                        emkProOrderDetailEntity.setPriceNo(map.get("orderMxList["+i+"].priceNo"));
                        emkProOrderDetailEntity.setStoId(map.get("orderMxList["+i+"].stoId"));
                        for (Map category : categoryEntities) {
                            if (Utils.notEmpty(map.get("orderMxList[" + i + "]." + category.get("code")))) {
                                String m0 = "setA" + category.get("code").toString().substring(1);
                                show = c.getMethod(m0, String.class);
                                show.invoke(emkProOrderDetailEntity, String.valueOf(map.get("orderMxList[" + i + "]." + category.get("code"))));
                            }
                        }
                        systemService.save(emkProOrderDetailEntity);

                        //判断在商品管理是否有存在，不存在则创建
                        String hql = "from EmkProductEntity where 1=1 and proZnName='"+emkProOrderDetailEntity.getA01a09a07()+"'";
                        if(Utils.notEmpty(emkProOrderDetailEntity.getA01a09a10())){
                            hql += " and brand='"+emkProOrderDetailEntity.getA01a09a10()+"'";
                        }
                        if(Utils.notEmpty(emkProOrderDetailEntity.getA01a09a08())){
                            hql += " and proNum='"+emkProOrderDetailEntity.getA01a09a08()+"'";
                        }
                        if(Utils.notEmpty(emkProOrderDetailEntity.getA01a09a11())){
                            hql += " and unit='"+emkProOrderDetailEntity.getA01a09a11()+"'";
                        }
                        if(Utils.notEmpty(emkProOrderDetailEntity.getA01a09a06())){
                            hql += " and a01a01a01='"+emkProOrderDetailEntity.getA01a09a06()+"'";
                        }else{
                            hql += " and (a01a01a01 is null or a01a01a01 = '') ";
                        }
                        hql += " and orgCode='"+orgCode+"'";
                        emkProductEntity = systemService.singleResult(hql);
                        if(Utils.isEmpty(emkProductEntity)){
                            emkProductEntity = new EmkProductEntity();
                            emkProductEntity.setProType("qt");
                            emkProductEntity.setProTypeName("其他");
                            emkProductEntity.setProZnName(emkProOrderDetailEntity.getA01a09a07());
                            if(Utils.notEmpty(emkProOrderDetailEntity.getA01a09a08())){
                                emkProductEntity.setProNum(emkProOrderDetailEntity.getA01a09a08());
                                emkProductEntity.setFlag("0");
                            }else {
                                saveProNum(emkProductEntity,orgCode);
                                emkProductEntity.setFlag("1");
                            }
                            emkProductEntity.setProZjm(PinyinUtil.getPinYinHeadChar(emkProductEntity.getProZnName()));
                            emkProductEntity.setBrand(emkProOrderDetailEntity.getA01a09a10());
                            emkProductEntity.setA01a01a01(emkProOrderDetailEntity.getA01a09a06());
                            emkProductEntity.setUnit(emkProOrderDetailEntity.getA01a09a11());

                            Map type = systemService.findOneForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='units' and typename=?" +
                                    "    and org_code='"+orgCode+"'",emkProductEntity.getUnit());
                            if(Utils.notEmpty(type)){
                                emkProductEntity.setUnitCode(type.get("typecode").toString());
                            }else{
                                Map typegroup = systemService.findOneForJdbc("select id from t_s_typegroup t2  where typegroupcode='units' and org_code='"+orgCode+"'");
                                if(Utils.notEmpty(typegroup)){
                                    TSTypeA tsType = new TSTypeA();
                                    tsType.setTypename(emkProductEntity.getUnit());
                                    tsType.setTypecode(PinyinUtil.getPinYinHeadChar(tsType.getTypename()));
                                    tsType.setTypegroupid(typegroup.get("id").toString());
                                    systemService.save(tsType);
                                }
                            }
                            emkProductEntity.setSellPrice(emkProOrderDetailEntity.getA01a09a21());
                            emkProductEntity.setCostPrice(emkProOrderDetailEntity.getA01a09a12());
                            emkProductEntity.setOrgCode(tsDepart.getOrgCode());
                            emkProductEntity.setDepartId(tsDepart.getId());
                            systemService.save(emkProductEntity);
                        }

                    }

                }
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkProOrderEntity emkProOrder,EmkSizeEntity emkSize, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "订单更新成功";
        Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
        EmkProOrderEntity t = emkProOrderService.get(EmkProOrderEntity.class, map.get("proOrderId"));

        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
        String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
        try {
            /*if(!t.getState().equals("0")){
                j.setMsg("订单已提交，请重新选择");
                j.setSuccess(false);
                return j;
            }*/
            emkProOrder.setId(null);
            MyBeanUtils.copyBeanNotNull2Bean(emkProOrder, t);
            emkProOrderService.saveOrUpdate(t);
            YmkCustomEntity ymkCustomEntity = systemService.singleResult("from YmkCustomEntity where cusNum='"+emkProOrder.getA01a05a02()+"' and orgCode='"+orgCode+"'");
            if(Utils.notEmpty(emkProOrder.getBcqkMoney())){
                ymkCustomEntity.setBcqkMoney(emkProOrder.getBcqkMoney());
            }

            List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A09'  and type='1' and org_code=?  order by order_num asc",orgCode);
            EmkProOrderDetailEntity emkProOrderDetailEntity = null;
            Class c = Class.forName(EmkProOrderDetailEntity.class.getName());


            Method show = null;
            String dataRows = map.get("orderMxListID");
            if (Utils.notEmpty(dataRows)) {
                int rows = Integer.parseInt(dataRows);
                systemService.executeSql("delete from emk_pro_order_detail where PRO_ORDER_ID=?",t.getId());

                for (int i = 0; i < rows; i++) {
                    if(Utils.notEmpty(map.get("orderMxList["+i+"].a01a09a07"))) {
                        emkProOrderDetailEntity = new EmkProOrderDetailEntity();
                        emkProOrderDetailEntity.setDepartId(emkProOrder.getDepartId());
                        emkProOrderDetailEntity.setOrgCode(emkProOrder.getOrgCode());
                        emkProOrderDetailEntity.setProOrderId(t.getId());
                        for (Map category : categoryEntities) {
                            if (Utils.notEmpty(map.get("orderMxList[" + i + "]." + category.get("code")))) {
                                String m0 = "setA" + category.get("code").toString().substring(1);
                                show = c.getMethod(m0, String.class);
                                show.invoke(emkProOrderDetailEntity, String.valueOf(map.get("orderMxList[" + i + "]." + category.get("code"))));
                            }
                        }
                        systemService.save(emkProOrderDetailEntity);

                    }

                }
            }

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "订单更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params="doPay")
    @ResponseBody
    public AjaxJson doPay(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "订单付款成功";
        try {
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
            String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
            EmkProOrderEntity t = systemService.get(EmkProOrderEntity.class, map.get("id"));
            if("1".equals(t.getPayState())){
                j.setMsg("订单已付款");
                j.setSuccess(false);
                return j;
            }
            if(Utils.isEmpty(t.getBankMoney())){
                j.setMsg("订单账号不能为空");
                j.setSuccess(false);
                return j;
            }
            if("1".equals(map.get("payState"))){
                EmkBankRecordEntity bankRecordEntity = systemService.singleResult("from EmkBankRecordEntity where bankType='"+t.getBankMoney()+"' and orgCode='"+orgCode+"' and remark='期初余额'");
                EmkBankRecordEntity emkBankRecordEntity = new EmkBankRecordEntity();
                MyBeanUtils.copyBeanNotNull2Bean(bankRecordEntity,emkBankRecordEntity);
                emkBankRecordEntity.setId(null);
                double blance = Double.parseDouble(bankRecordEntity.getBalance())+Double.parseDouble(t.getShMoney());
                emkBankRecordEntity.setBalance(String.valueOf(blance));
                emkBankRecordEntity.setIncome(t.getShMoney());
                emkBankRecordEntity.setDealDate(DateUtil.getCurrentTimeString(null));
                emkBankRecordEntity.setOutcome("");
                emkBankRecordEntity.setRemark("订单付款");
                bankRecordEntity.setBalance(emkBankRecordEntity.getBalance());
                systemService.save(emkBankRecordEntity);
                EmkBankRecordLogEntity emkBankRecordLogEntity = new EmkBankRecordLogEntity();
                saveBankRecord(String.valueOf(blance),bankRecordEntity,emkBankRecordLogEntity);
                systemService.save(emkBankRecordLogEntity);
                t.setPayState(map.get("payState"));
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            message = "订单付款失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params="doRecevie")
    @ResponseBody
    public AjaxJson doRecevie(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "订单收货成功";
        try {
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            EmkProOrderEntity t = systemService.get(EmkProOrderEntity.class, map.get("id"));
            t.setRecevieState(map.get("recevieState"));

            List<EmkProOrderDetailEntity> inStorageDetailEntityList = systemService.findHql("from emkProOrderDetailEntity where inStorageId=?",t.getId());

            EmkStorageLogEntity storageLogEntity = null;
            for(EmkProOrderDetailEntity inStorageDetailEntity : inStorageDetailEntityList){
                //保存库存数据和日记

            }

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = "订单收货失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkProOrderEntity emkProOrder, HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
        TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",orgCode);
        EmkSysParamEntity emkSysParamEntity = systemService.singleResult("from EmkSysParamEntity where paramName='订单前缀' and departId='"+tsDepart.getId()+"'");
        Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(a01a05a01, 4)),0)+1 AS signed) orderNum from emk_pro_order where org_code=?",orgCode);
        emkProOrder.setA01a05a01(emkSysParamEntity.getParamValue()+ DateUtils.format(new Date(), "yyyyMMdd") + String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));

        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A05' and org_code=? and is_show=0 order by order_num asc",orgCode);
        req.setAttribute("headcategoryEntities",categoryEntities);

        req.setAttribute("emkProOrderPage", emkProOrder);

        return new ModelAndView("com/emk/bill/proorder/emkProOrder-add");
    }

    @RequestMapping(params = "goPay")
    public ModelAndView goPay(EmkProOrderEntity emkProOrder, HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        if (StringUtil.isNotEmpty(emkProOrder.getId())) {
            emkProOrder = emkProOrderService.getEntity(EmkProOrderEntity.class, emkProOrder.getId());
            req.setAttribute("emkProOrderPage", emkProOrder);
        }
        return new ModelAndView("com/emk/bill/proorder/emkProOrder-pay");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkProOrderEntity emkProOrder, HttpServletRequest req) {
        TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A05' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        req.setAttribute("headcategoryEntities",categoryEntities);
        if (StringUtil.isNotEmpty(emkProOrder.getId())) {
            emkProOrder = emkProOrderService.getEntity(EmkProOrderEntity.class, emkProOrder.getId());

           /* Calendar cal1 = Calendar.getInstance();
            cal1.setTime(org.jeecgframework.core.util.DateUtils.str2Date(emkProOrder.getRecevieDate(), org.jeecgframework.core.util.DateUtils.date_sdf));
            Calendar cal2 = Calendar.getInstance();
            int day = org.jeecgframework.core.util.DateUtils.dateDiff('d',cal1,cal2);
            if(day<0){
                day = 0;
            }
            req.setAttribute("levelDays",day);

            Map infoMap = systemService.findOneForJdbc("SELECT ifnull(SUM(t.total),0) total,ifnull(ROUND(SUM(t.total*t.price),2),0) sumprice FROM emk_enquiry_detail t WHERE t.enquiry_id=?",emkProOrder.getId());

            emkProOrder.setSumTotal(Integer.parseInt(infoMap.get("total").toString()));
            emkProOrder.setSumMoney(Double.parseDouble(infoMap.get("sumprice").toString()));*/

            req.setAttribute("emkProOrderPage", emkProOrder);

        }
        return new ModelAndView("com/emk/bill/proorder/emkProOrder-update");
    }

    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(EmkProOrderEntity emkProOrder, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkProOrder.getId())) {
            emkProOrder = emkProOrderService.getEntity(EmkProOrderEntity.class, emkProOrder.getId());
            req.setAttribute("emkProOrderPage", emkProOrder);

        }
        return new ModelAndView("com/emk/bill/proorder/emkProOrder-update2");
    }

    @RequestMapping(params = "goTab")
    public ModelAndView goBtn(EmkProOrderEntity emkProOrderEntity, HttpServletRequest req) {
        return new ModelAndView("com/emk/bill/proorder/emkProOrder-tab");
    }
    @RequestMapping(params = "goBase")
    public ModelAndView goBase(EmkProOrderEntity emkProOrderEntity, HttpServletRequest req) {
        req.setAttribute("kdDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        if (StringUtil.isNotEmpty(emkProOrderEntity.getId())) {
            emkProOrderEntity = systemService.getEntity(EmkProOrderEntity.class, emkProOrderEntity.getId());
            req.setAttribute("emkProOrderPage", emkProOrderEntity);
            try {
                Map countMap = MyBeanUtils.culBeanCounts(emkProOrderEntity);
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
        return new ModelAndView("com/emk/bill/proorder/emkProOrder-base");
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "emkProOrderController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkProOrderEntity emkProOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(EmkProOrderEntity.class, dataGrid);
        HqlGenerateUtil.installHql(cq, emkProOrder, request.getParameterMap());
        List<EmkProOrderEntity> emkProOrders = emkProOrderService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        modelMap.put("fileName", "订单");
        modelMap.put("entity", EmkProOrderEntity.class);
        modelMap.put("params", new ExportParams("订单列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", emkProOrders);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkProOrderEntity emkProOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "订单");
        modelMap.put("entity", EmkProOrderEntity.class);
        modelMap.put("params", new ExportParams("订单列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }


    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "订单列表信息", produces = "application/json", httpMethod = "GET")
    public ResponseMessage<List<EmkProOrderEntity>> list() {
        List<EmkProOrderEntity> listEmkProOrders = emkProOrderService.getList(EmkProOrderEntity.class);
        return Result.success(listEmkProOrders);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "根据ID获取订单信息", notes = "根据ID获取订单信息", httpMethod = "GET", produces = "application/json")
    public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
        EmkProOrderEntity task = emkProOrderService.get(EmkProOrderEntity.class, id);
        if (task == null) {
            return Result.error("根据ID获取订单信息为空");
        }
        return Result.success(task);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    @ApiOperation("创建订单")
    public ResponseMessage<?> create(@ApiParam(name = "订单对象") @RequestBody EmkProOrderEntity emkProOrder, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkProOrderEntity>> failures = validator.validate(emkProOrder, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkProOrderService.save(emkProOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("订单信息保存失败");
        }
        return Result.success(emkProOrder);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "更新订单", notes = "更新订单")
    public ResponseMessage<?> update(@ApiParam(name = "订单对象") @RequestBody EmkProOrderEntity emkProOrder) {
        Set<ConstraintViolation<EmkProOrderEntity>> failures = validator.validate(emkProOrder, new Class[0]);
        if (!failures.isEmpty()) {
            return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
        }
        try {
            emkProOrderService.saveOrUpdate(emkProOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新订单信息失败");
        }
        return Result.success("更新订单信息成功");
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除订单")
    public ResponseMessage<?> delete(@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
        logger.info("delete[{}]" + id);
        if (StringUtils.isEmpty(id)) {
            return Result.error("ID不能为空");
        }
        try {
            emkProOrderService.deleteEntityById(EmkProOrderEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("订单删除失败");
        }
        return Result.success();
    }

    @RequestMapping(params="goWork")
    public ModelAndView goWork(EmkProOrderEntity emkProOrderEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(emkProOrderEntity.getId())) {
            emkProOrderEntity = emkProOrderService.getEntity(EmkProOrderEntity.class, emkProOrderEntity.getId());
            req.setAttribute("emkProOrderPage", emkProOrderEntity);
        }
        return new ModelAndView("com/emk/bill/proorder/emkProOrder-work");
    }

    @RequestMapping(params="goTime")
    public ModelAndView goTime(EmkProOrderEntity emkProOrderEntity, HttpServletRequest req, DataGrid dataGrid) {
        EmkApprovalEntity approvalEntity = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",emkProOrderEntity.getId());
        if(Utils.notEmpty(approvalEntity)){
            List<EmkApprovalDetailEntity> approvalDetailEntityList = systemService.findHql("from EmkApprovalDetailEntity where approvalId=? order by approveDate asc",approvalEntity.getId());
            req.setAttribute("approvalDetailEntityList", approvalDetailEntityList);
            req.setAttribute("approvalEntity", approvalEntity);
            req.setAttribute("createDate", org.jeecgframework.core.util.DateUtils.date2Str(approvalEntity.getCreateDate(), org.jeecgframework.core.util.DateUtils.datetimeFormat));
        }

        return new ModelAndView("com/emk/bill/proorder/time");
    }



}
