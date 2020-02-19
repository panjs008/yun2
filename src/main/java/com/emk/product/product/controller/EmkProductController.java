package com.emk.product.product.controller;

import com.emk.bound.minstoragedetail.entity.EmkMInStorageDetailEntity;
import com.emk.product.hs.entity.EmkProductHsEntity;
import com.emk.product.product.entity.*;
import com.emk.product.product.service.EmkProductServiceI;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.emk.product.productprice.entity.EmkProductPriceEntity;
import com.emk.product.producttype.entity.EmkProductTypeEntity;
import com.emk.storage.storage.entity.EmkStorageEntity;
import com.emk.storage.storagelog.entity.EmkStorageLogEntity;
import com.emk.storage.storageset.entity.EmkStorageSetEntity;
import com.emk.storage.storagesetposition.entity.EmkStorageSetPositionEntity;
import com.emk.util.*;
import com.emk.util.excel.ExcelUtil;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
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
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/emkProductController")
public class EmkProductController
        extends BaseController {
    private static final Logger logger = Logger.getLogger(EmkProductController.class);
    @Autowired
    private EmkProductServiceI emkProductService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("categoryEntities",categoryEntities);

        return new ModelAndView("com/emk/product/product/emkProductList");
    }

    @RequestMapping(params = "list1")
    public ModelAndView list1(HttpServletRequest request) {
        return new ModelAndView("com/emk/product/product/emkProductList1");
    }

    @RequestMapping(params = "list2")
    public ModelAndView list2(HttpServletRequest request) {
        return new ModelAndView("com/emk/product/product/emkProductList2");
    }

    @RequestMapping(params = "priceMxList")
    public ModelAndView priceMxList(HttpServletRequest request) {
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        if (Utils.notEmpty(map.get("productId"))) {
            List<EmkProductPriceEntity> emkProductPriceEntityList = systemService.findHql("from EmkProductPriceEntity where productId=? ",  map.get("productId"));
            request.setAttribute("priceList", emkProductPriceEntityList);
        }
        return new ModelAndView("com/emk/product/product/priceMxList");
    }

    @RequestMapping(params = "orderMxList")
    public ModelAndView orderMxList(HttpServletRequest request) {
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type,is_show " +
                "   from hm_category where PARENT_CODE='A01A19' and org_code=? order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("categoryEntities",categoryEntities);
        if (Utils.notEmpty(map.get("productId"))) {
            List<EmkProductStorageEntityB> emkProductStorageEntityList = systemService.findHql("from EmkProductStorageEntityB where productId=? ",  map.get("productId"));
            request.setAttribute("storageList", emkProductStorageEntityList);

            List<EmkStorageSetEntity> storageSetEntities = systemService.findHql("from EmkStorageSetEntity where sysOrgCode=?",user.getCurrentDepart().getOrgCode().substring(0,3));
            request.setAttribute("storageSetEntities",storageSetEntities);
        }
        return new ModelAndView("com/emk/product/product/orderMxList");
    }

    @RequestMapping(params = "proSelect")
    public ModelAndView proSelect(HttpServletRequest request) {
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        request.setAttribute("categoryEntities",categoryEntities);
        return new ModelAndView("com/emk/product/product/emkProductList-select");
    }
    @RequestMapping(params = "proForOne")
    public ModelAndView proSelectForOne(HttpServletRequest request) {
        return new ModelAndView("com/emk/product/product/emkProductList-select2");
    }
    @RequestMapping(params = "proSelect3")
    public ModelAndView proSelect3(HttpServletRequest request) {
        return new ModelAndView("com/emk/product/product/emkProductList-select3");
    }
    @RequestMapping(params = "datagrid")
    public void datagrid(EmkProductEntity emkProduct, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkProductEntity.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        if(!user.getUserName().equals("admin")){
            cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
        }
        HqlGenerateUtil.installHql(cq, emkProduct, request.getParameterMap());


        cq.add();
        emkProductService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    @RequestMapping(params = "datagridB")
    public void datagridB(EmkProductEntityB emkProduct, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkProductEntityB.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        if(!user.getUserName().equals("admin")){
            cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
        }
        HqlGenerateUtil.installHql(cq, emkProduct, request.getParameterMap());

        cq.add();
        emkProductService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    @RequestMapping(params = "datagridSelect")
    public void datagridSelect(EmkProductEntityA emkProduct, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(EmkProductEntityA.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));

        HqlGenerateUtil.installHql(cq, emkProduct, request.getParameterMap());


        cq.add();
        emkProductService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(EmkProductEntity emkProduct, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkProduct = systemService.getEntity(EmkProductEntity.class, emkProduct.getId());
        message = "删除成功";
        try {
            emkProductService.delete(emkProduct);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "getProductInfo")
    @ResponseBody
    public AjaxJson getProductInfo(EmkProductEntity emkProduct, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        emkProduct = systemService.findUniqueByProperty(EmkProductEntity.class,"proNum",emkProduct.getProNum());
        try {
            j.setObj(emkProduct);
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除失败";
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
        message = "删除成功";
        try {
            TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
            for (String id : ids.split(",")) {
                EmkProductEntity emkProduct = systemService.getEntity(EmkProductEntity.class, id);
                List<EmkStorageEntity> emkStorageEntityList = systemService.findHql("from EmkStorageEntity where orgCode=? and a01a09a08=?",
                        user.getCurrentDepart().getOrgCode().substring(0,3),emkProduct.getProNum());
                /*EmkStorageEntity emkStorageEntity = systemService.singleResult("from EmkStorageEntity where " +
                        "   orgCode='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"' and a01a09a08='"+emkProduct.getProNum()+"'");*/
                if(Utils.notEmpty(emkStorageEntityList)){
                    j.setMsg("存在库存，无法删除");
                    j.setSuccess(false);
                    return j;
                }
                if(Utils.notEmpty(emkProduct.getProImageUrl())){
                    String url = emkProduct.getProImageUrl().substring(emkProduct.getProImageUrl().indexOf("imp/"));
                    WebFileUtils.delete(request.getRealPath("/")+url);
                }
                systemService.executeSql("delete from emk_product_price where product_id=?",id);
                emkProductService.delete(emkProduct);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doProductTypeDel")
    @ResponseBody
    public AjaxJson doProductTypeDel(String id, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除商品类别成功";
        try {
            EmkProductTypeEntity emkProductTypeEntity = systemService.getEntity(EmkProductTypeEntity.class, Integer.parseInt(id));
            emkProductService.delete(emkProductTypeEntity);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除商品类别失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(EmkProductEntity emkProduct, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "商品信息添加成功";
        try {
            TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
            TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
            Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
            EmkProductEntity emkProductEntity = systemService.singleResult("from EmkProductEntity where proNum='"+emkProduct.getProNum()+"' and orgCode='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"'");
            if(Utils.notEmpty(emkProductEntity)){
                j.setMsg("商品编号已存在，请重新录入");
                j.setSuccess(false);
                return j;
            }
            emkProductEntity = systemService.singleResult("from EmkProductEntity where proZnName='"+emkProduct.getProZnName()+"' " +
                    "and brand='"+emkProduct.getBrand()+"' and (a01a01a01='"+emkProduct.getA01a01a01()+"' or a01a01a01 is null) and (a01a01a02='"+emkProduct.getA01a01a02()+"' or a01a01a02 is null)");
            if(Utils.notEmpty(emkProductEntity)){
                j.setMsg("商品信息已存在，请重新录入");
                j.setSuccess(false);
                return j;
            }
            Map type = systemService.findOneForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='units' and typecode=?" +
                    "    and org_code='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"'",emkProduct.getUnitCode());
            if(Utils.notEmpty(type)){
                emkProduct.setUnit(type.get("typename").toString());
            }
            emkProduct.setOrgCode(tsDepart.getOrgCode());
            emkProduct.setDepartId(tsDepart.getId());
            //emkProduct.setProZjm(PinyinUtil.getPinYinHeadChar(emkProduct.getProZnName()));

            Map orderNum = null;
            emkProductService.save(emkProduct);
            String dataRows = map.get("orderMxListID");
            //保存价格数据
            if (Utils.notEmpty(dataRows)) {
                int rows = Integer.parseInt(dataRows);
                EmkProductPriceEntity emkProductPriceEntity = null;
                for (int i = 0; i < rows; i++) {
                    emkProductPriceEntity = new EmkProductPriceEntity();
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].priceName"))) {
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].priceNo"))){
                            emkProductPriceEntity.setPriceNo(map.get("orderMxList["+i+"].priceNo"));
                        }else{
                            orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(price_no, 3)),0)+1 AS signed) orderNum from emk_product_price where org_code=? and product_id=?",user.getCurrentDepart().getOrgCode().substring(0,3),emkProduct.getId());
                            emkProductPriceEntity.setPriceNo(String.format("%03d", Integer.valueOf(orderNum.get("orderNum").toString())));
                        }
                        emkProductPriceEntity.setPriceName(map.get("orderMxList["+i+"].priceName"));
                        emkProductPriceEntity.setPrice(map.get("orderMxList["+i+"].price"));
                        emkProductPriceEntity.setProductId(emkProduct.getId());
                        emkProductPriceEntity.setUnit1(map.get("orderMxList["+i+"].unit1"));
                        emkProductPriceEntity.setHsPercentage(map.get("orderMxList["+i+"].hsPercentage"));
                        emkProductPriceEntity.setUnit2(map.get("orderMxList["+i+"].unit2"));
                        emkProductPriceEntity.setHsPercentage2(map.get("orderMxList["+i+"].hsPercentage2"));
                        emkProductPriceEntity.setPriceT(map.get("orderMxList["+i+"].priceT"));
                        emkProductPriceEntity.setPriceT2(map.get("orderMxList["+i+"].priceT2"));

                        emkProductPriceEntity.setDepartId(tsDepart.getId());
                        emkProductPriceEntity.setOrgCode(tsDepart.getOrgCode());
                        systemService.save(emkProductPriceEntity);
                    }
                }
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "商品信息添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(EmkProductEntity emkProduct, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "商品信息更新成功";
        Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
        EmkProductEntity emkProductEntity = systemService.singleResult("from EmkProductEntity where proNum='"+emkProduct.getProNum()+"' and orgCode='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"'");
        EmkProductEntity t = emkProductService.get(EmkProductEntity.class, emkProduct.getId());
        if(!emkProduct.getProNum().equals(t.getProNum())){
            if(Utils.notEmpty(emkProductEntity)){
                j.setMsg("商品编号已存在，请重新录入");
                j.setSuccess(false);
                return j;
            }
        }

        if(!map.get("oProName").equals(emkProduct.getProZnName()) || !map.get("oBrand").equals(emkProduct.getBrand())
                    || !map.get("oa01a01a01").equals(emkProduct.getA01a01a01()) || !map.get("oa01a01a02").equals(emkProduct.getA01a01a02())){
            emkProductEntity = systemService.singleResult("from EmkProductEntity where proZnName='"+emkProduct.getProZnName()+"' " +
                    "and brand='"+emkProduct.getBrand()+"' and (a01a01a01='"+emkProduct.getA01a01a01()+"' or a01a01a01 is null) and (a01a01a02='"+emkProduct.getA01a01a02()+"' or a01a01a02 is null)");
            if(Utils.notEmpty(emkProductEntity)){
                j.setMsg("商品信息已存在，请重新录入");
                j.setSuccess(false);
                return j;
            }
        }

        String dataRows = map.get("orderMxListID");
        //emkProduct.setProZjm(ChineseToEnglish.getPinYinHeadChar(emkProduct.getProZnName()));
        Map type = systemService.findOneForJdbc("select typecode,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='units' and typecode=?" +
                "    and org_code='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"'",emkProduct.getUnitCode());
        if(Utils.notEmpty(type)){
            emkProduct.setUnit(type.get("typename").toString());
        }

        try {
            MyBeanUtils.copyBeanNotNull2Bean(emkProduct, t);
            emkProductService.saveOrUpdate(t);

            //保存价格数据
            if (Utils.notEmpty(dataRows)) {
                systemService.executeSql("delete from emk_product_price where product_id = ? ",t.getId());
                int rows = Integer.parseInt(dataRows);
                EmkProductPriceEntity emkProductPriceEntity = null;
                for (int i = 0; i < rows; i++) {
                    emkProductPriceEntity = new EmkProductPriceEntity();
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].priceName"))) {
                        if(Utils.notEmpty(map.get("orderMxList["+i+"].priceNo"))){
                            emkProductPriceEntity.setPriceNo(map.get("orderMxList["+i+"].priceNo"));
                        }else{
                            Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(price_no, 3)),0)+1 AS signed) orderNum from emk_product_price where org_code=? and product_id=?",user.getCurrentDepart().getOrgCode().substring(0,3),emkProduct.getId());
                            emkProductPriceEntity.setPriceNo(String.format("%03d", Integer.valueOf(orderNum.get("orderNum").toString())));
                        }
                        emkProductPriceEntity.setPriceName(map.get("orderMxList["+i+"].priceName"));
                        emkProductPriceEntity.setPrice(map.get("orderMxList["+i+"].price"));
                        emkProductPriceEntity.setProductId(emkProduct.getId());
                        emkProductPriceEntity.setUnit1(map.get("orderMxList["+i+"].unit1"));
                        emkProductPriceEntity.setHsPercentage(map.get("orderMxList["+i+"].hsPercentage"));
                        emkProductPriceEntity.setUnit2(map.get("orderMxList["+i+"].unit2"));
                        emkProductPriceEntity.setHsPercentage2(map.get("orderMxList["+i+"].hsPercentage2"));
                        emkProductPriceEntity.setPriceT(map.get("orderMxList["+i+"].priceT"));
                        emkProductPriceEntity.setPriceT2(map.get("orderMxList["+i+"].priceT2"));
                        emkProductPriceEntity.setDepartId(tsDepart.getId());
                        emkProductPriceEntity.setOrgCode(tsDepart.getOrgCode());
                        systemService.save(emkProductPriceEntity);
                    }
                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "商品信息更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    EmkStorageEntity findStorageAndSaveLog(EmkProductStorageEntity emkProductStorageEntity,String orgCode,String proName,String proNum, EmkStorageLogEntity storageLogEntity, String remark,String type){
        systemService.executeSql("delete from  emk_storage where a01a09a02=0 and org_code=?",orgCode);

        String hql = "from EmkStorageEntity where 1=1 ";
        if(Utils.notEmpty(emkProductStorageEntity.getStorageId())){
            hql += " and storageId='"+emkProductStorageEntity.getStorageId()+"'";
        }
        if(Utils.notEmpty(emkProductStorageEntity.getPositionId())){
            hql += " and a01a09a05='"+emkProductStorageEntity.getPositionId()+"'";
        }else{
            hql += " and (a01a09a05 is null or a01a09a05 = '')";
        }
        hql += " and a01a09a07='"+proName+"'";
        if(Utils.notEmpty(proNum)){
            hql += " and a01a09a08='"+proNum+"'";
        }else{
            //hql += " and flag=1";
            hql += " and (a01a09a08 is null or a01a09a08 = '')";
        }
        if(Utils.notEmpty(emkProductStorageEntity.getA01a19a01())){
            hql += " and a01a09a06='"+emkProductStorageEntity.getA01a19a01()+"'";
        }else{
            hql += " and (a01a09a06 is null or a01a09a06 = '')";
        }
        if(Utils.notEmpty(emkProductStorageEntity.getA01a19a02())){
            hql += " and a01a09a24='"+emkProductStorageEntity.getA01a19a02()+"'";
        }else{
            hql += " and (a01a09a24 is null or a01a09a24 = '')";
        }
        if(Utils.notEmpty(emkProductStorageEntity.getA01a19a03())){
            hql += " and a01a09a14='"+emkProductStorageEntity.getA01a19a03()+"'";
        }else{
            hql += " and (a01a09a14 is null or a01a09a14 = '')";
        }
        if(Utils.notEmpty(emkProductStorageEntity.getA01a19a04())){
            hql += " and a01a09a22='"+emkProductStorageEntity.getA01a19a04()+"'";
        }else{
            hql += " and (a01a09a22 is null or a01a09a22 = '')";
        }
        if(Utils.notEmpty(emkProductStorageEntity.getA01a19a05())){
            hql += " and a01a09a23='"+emkProductStorageEntity.getA01a19a05()+"'";
        }else{
            hql += " and (a01a09a23 is null or a01a09a23 = '')";
        }
        hql += " and orgCode='"+orgCode+"'";
        EmkStorageEntity storageEntity = systemService.singleResult(hql);
        storageLogEntity.setRkNo("");
        storageLogEntity.setType(type);
        storageLogEntity.setProZnName(proName);
        storageLogEntity.setProNum(proNum);
        storageLogEntity.setMarkDate(DateUtil.getLogTime());
        storageLogEntity.setRemark(remark);
        storageLogEntity.setOrgCode(emkProductStorageEntity.getOrgCode());
        storageLogEntity.setDepartId(emkProductStorageEntity.getDepartId());
        storageLogEntity.setStorageId(emkProductStorageEntity.getStorageId());
        storageLogEntity.setStorageName(emkProductStorageEntity.getStorageName());
        return storageEntity;
    }

    EmkStorageEntity findOldStorage(EmkProductStorageEntity oldT,String proName,String proNum,String orgCode){
        String hql = "from EmkStorageEntity where 1=1 ";
        if(Utils.notEmpty(oldT.getStorageId())){
            hql += " and storageId='"+oldT.getStorageId()+"'";
        }
        if(Utils.notEmpty(oldT.getPositionId())){
            hql += " and a01a09a05='"+oldT.getPositionId()+"'";
        }else{
            hql += " and (a01a09a05 is null or a01a09a05 = '')";
        }
        if(Utils.notEmpty(proName)){
            hql += " and a01a09a07='"+proName+"'";
        }
        if(Utils.notEmpty(proNum)){
            hql += " and a01a09a08='"+proNum+"'";
        }else{
            //hql += " and flag=1";
            hql += " and (a01a09a08 is null or a01a09a08 = '')";
        }
        if(Utils.notEmpty(oldT.getA01a19a01())){
            hql += " and a01a09a06='"+oldT.getA01a19a01()+"'";
        }else{
            hql += " and (a01a09a06 is null or a01a09a06 = '')";
        }
        if(Utils.notEmpty(oldT.getA01a19a02())){
            hql += " and a01a09a24='"+oldT.getA01a19a02()+"'";
        }else{
            hql += " and (a01a09a24 is null or a01a09a24 = '')";
        }
        if(Utils.notEmpty(oldT.getA01a19a03())){
            hql += " and a01a09a14='"+oldT.getA01a19a03()+"'";
        }else{
            hql += " and (a01a09a14 is null or a01a09a14 = '')";
        }
        if(Utils.notEmpty(oldT.getA01a19a04())){
            hql += " and a01a09a22='"+oldT.getA01a19a04()+"'";
        }else{
            hql += " and (a01a09a22 is null or a01a09a22 = '')";
        }
        if(Utils.notEmpty(oldT.getA01a19a05())){
            hql += " and a01a09a23='"+oldT.getA01a19a05()+"'";
        }else{
            hql += " and (a01a09a23 is null or a01a09a23 = '')";
        }

        hql += " and orgCode='"+orgCode+"'";
        EmkStorageEntity oldstorageEntity = systemService.singleResult(hql);
        return oldstorageEntity;
    }

    EmkStorageEntity saveNewStorage(EmkProductEntity emkProductEntity,EmkProductStorageEntity t,EmkStorageLogEntity storageLogEntity){
        EmkStorageEntity storageEntity = new EmkStorageEntity();
        storageEntity.setId(null);
        storageEntity.setStorageName(t.getStorageName());
        storageEntity.setStorageId(t.getStorageId());
        storageEntity.setA01a09a02(t.getTotal());
        storageEntity.setA01a09a05(t.getPositionId());
        storageEntity.setA01a09a06(t.getA01a19a01());
        storageEntity.setA01a09a24(t.getA01a19a02());
        storageEntity.setA01a09a14(t.getA01a19a03());
        storageEntity.setA01a09a22(t.getA01a19a04());
        storageEntity.setA01a09a23(t.getA01a19a05());

        storageEntity.setA01a09a08(emkProductEntity.getProNum());
        storageEntity.setA01a09a07(emkProductEntity.getProZnName());
        storageEntity.setA01a09a11(emkProductEntity.getUnit());
        storageEntity.setA01a09a10(emkProductEntity.getBrand());
        storageEntity.setA01a09a12(emkProductEntity.getCostPrice());
        storageEntity.setDepartId(emkProductEntity.getDepartId());
        storageEntity.setOrgCode(emkProductEntity.getOrgCode());
        storageEntity.setA01a09a21(emkProductEntity.getSellPrice());
        storageEntity.setProZjm(PinyinUtil.getPinYinHeadChar(storageEntity.getA01a09a07()));

        storageEntity.setFlag("0");

        systemService.save(storageEntity);
        storageLogEntity.setPreTotal("0");
        storageLogEntity.setNextTotal(t.getTotal());
        storageLogEntity.setTotal(t.getTotal());
        storageLogEntity.setProNum(storageEntity.getA01a09a08());
        storageLogEntity.setDepartId(t.getDepartId());
        storageLogEntity.setOrgCode(t.getOrgCode());
        systemService.save(storageLogEntity);
        return  storageEntity;
    }
    void updateStorage(EmkStorageEntity storageEntity,EmkProductStorageEntity emkProductStorageEntity,EmkStorageLogEntity storageLogEntity){
        systemService.executeSql("update emk_storage set a01a09a02=? where id=? ",
                emkProductStorageEntity.getTotal(),storageEntity.getId());
        storageLogEntity.setProNum(storageEntity.getA01a09a08());

        storageLogEntity.setPreTotal(storageEntity.getA01a09a02());
        storageLogEntity.setNextTotal(emkProductStorageEntity.getTotal());
        int total  = Integer.parseInt(Utils.notEmpty(storageLogEntity.getNextTotal()) ? storageLogEntity.getNextTotal():"0")-
                Integer.parseInt(Utils.notEmpty(storageLogEntity.getPreTotal()) ? storageLogEntity.getPreTotal():"0");
        storageLogEntity.setTotal(String.valueOf(total));
        systemService.save(storageLogEntity);
    }
    void saveOldStorage(EmkStorageEntity oldstorageEntity,EmkProductStorageEntity old,EmkStorageLogEntity storageLogEntity){
        systemService.executeSql("update emk_storage set a01a09a02=? where id=? ",
                old.getTotal(),oldstorageEntity.getId());
        EmkStorageLogEntity newStorageLogEntity = new EmkStorageLogEntity();
        MyBeanUtils.copyBeanNotNull2Bean(storageLogEntity,newStorageLogEntity);
        newStorageLogEntity.setProNum(oldstorageEntity.getA01a09a08());
        newStorageLogEntity.setStorageId(oldstorageEntity.getStorageId());
        newStorageLogEntity.setStorageName(oldstorageEntity.getStorageName());
        newStorageLogEntity.setDepartId(oldstorageEntity.getDepartId());
        newStorageLogEntity.setOrgCode(oldstorageEntity.getOrgCode());
        saveOldLog2(newStorageLogEntity,oldstorageEntity,old);
    }
    void saveOldLog1(EmkStorageLogEntity storageLogEntity,EmkStorageEntity storageEntity){
        if(Utils.notEmpty(storageEntity)){
            Map esM = systemService.findOneForJdbc("select * from emk_storage where id=?",storageEntity.getId());
            if(Utils.notEmpty(esM)){
                storageLogEntity.setPreTotal(storageEntity.getA01a09a02());
                storageLogEntity.setNextTotal(esM.get("a01a09a02").toString());
                int total  = Integer.parseInt(Utils.notEmpty(esM.get("a01a09a02")) ? esM.get("a01a09a02").toString():"0")-
                        Integer.parseInt(Utils.notEmpty(storageLogEntity.getPreTotal()) ? storageLogEntity.getPreTotal():"0");
                storageLogEntity.setTotal(String.valueOf(total));
                systemService.save(storageLogEntity);
            }
        }

    }
    void saveOldLog2(EmkStorageLogEntity newStorageLogEntity,EmkStorageEntity oldstorageEntity,EmkProductStorageEntity old){
        newStorageLogEntity.setId(null);
        newStorageLogEntity.setPreTotal(oldstorageEntity.getA01a09a02());
        newStorageLogEntity.setNextTotal(old.getTotal());
        int total  = Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getNextTotal()) ? newStorageLogEntity.getNextTotal():"0")-
                Integer.parseInt(Utils.notEmpty(newStorageLogEntity.getPreTotal()) ? newStorageLogEntity.getPreTotal():"0");
        newStorageLogEntity.setTotal(String.valueOf(total));
        systemService.save(newStorageLogEntity);
    }
    @RequestMapping(params = "doStorage")
    @ResponseBody
    public AjaxJson doStorage(EmkProductEntity emkProduct,HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "修改库存成功";
        Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
        List<EmkProductStorageEntity> emkProductStorageEntityList = systemService.findHql("from EmkProductStorageEntity where productId=?",map.get("productId"));
        Map<String,EmkProductStorageEntity> detailMap = new HashMap<>();
        for(EmkProductStorageEntity emkProductStorageEntity : emkProductStorageEntityList){
            detailMap.put(emkProductStorageEntity.getId(),emkProductStorageEntity);
        }
        try {
            EmkProductEntity t = emkProductService.get(EmkProductEntity.class, map.get("productId"));
            MyBeanUtils.copyBeanNotNull2Bean(emkProduct,t);
            systemService.saveOrUpdate(t);
            TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
            String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
            TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
            String dataRows = map.get("orderMxListID");
            EmkProductStorageEntity emkProductStorageEntity = null;
            EmkProductStorageEntity old = null;
            EmkStorageSetEntity emkStorageSet = null;
            EmkStorageSetPositionEntity emkStorageSetPositionEntity = null;
            Class c = Class.forName(EmkProductStorageEntity.class.getName());
            Method show = null;

            if (Utils.notEmpty(dataRows)) {
                emkProductStorageEntity = new EmkProductStorageEntity();
                EmkStorageLogEntity storageLogEntity = null;
                List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A19'  and org_code=? order by order_num asc",orgCode);

                systemService.executeSql("delete from emk_product_storage where product_id = ? ", t.getId());
                int rows = Integer.parseInt(dataRows);
                for (int i = 0; i < rows; i++) {
                    if (Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
                        emkStorageSet = systemService.get(EmkStorageSetEntity.class,map.get("orderMxList["+i+"].storageSetId"));
                        if(Utils.notEmpty(emkStorageSet)){
                            emkProductStorageEntity.setStorageId(emkStorageSet.getId());
                            emkProductStorageEntity.setStorageName(emkStorageSet.getStorageName());
                        }
                        if (Utils.notEmpty(map.get("orderMxList["+i+"].storageSetPosiId"))) {
                            emkStorageSetPositionEntity = systemService.get(EmkStorageSetPositionEntity.class,map.get("orderMxList["+i+"].storageSetPosiId"));
                            if(Utils.notEmpty(emkStorageSetPositionEntity)){
                                emkProductStorageEntity.setPositionId(emkStorageSetPositionEntity.getId());
                                emkProductStorageEntity.setPositionName(emkStorageSetPositionEntity.getPositionName());
                            }
                        }
                        for(Map category : categoryEntities){
                            if(Utils.notEmpty(map.get("orderMxList["+i+"]."+category.get("code")))){
                                String m0 = "setA"+category.get("code").toString().substring(1);
                                show = c.getMethod(m0,String.class);
                                show.invoke(emkProductStorageEntity,String.valueOf(map.get("orderMxList["+i+"]."+category.get("code"))));
                            }
                        }
                        emkProductStorageEntity.setTotal(map.get("orderMxList["+i+"].total"));
                        emkProductStorageEntity.setProductId(t.getId());
                        emkProductStorageEntity.setDepartId(tsDepart.getId());
                        emkProductStorageEntity.setOrgCode(tsDepart.getOrgCode());
                        systemService.save(emkProductStorageEntity);
                        if (Utils.notEmpty(map.get("orderMxList[" + i + "].id"))) {
                            old = detailMap.get(map.get("orderMxList[" + i + "].id"));
                        }
                        //查询库存数据和保存库存数据日记
                        storageLogEntity = new EmkStorageLogEntity();
                        EmkStorageEntity storageEntity = findStorageAndSaveLog(emkProductStorageEntity,orgCode,t.getProZnName(),t.getProNum(),storageLogEntity,user.getRealName()+"修改库存商品数","5");
                        /* if(storageEntity == null){
                            storageEntity = saveNewStorage(t,emkProductStorageEntity,storageLogEntity);
                        }else{
                            systemService.executeSql("update emk_storage set a01a09a02=(a01a09a02+?) where id=? ",emkProductStorageEntity.getTotal(),storageEntity.getId());
                            storageLogEntity.setProNum(storageEntity.getA01a09a08());
                            saveOldLog1(storageLogEntity,storageEntity);
                        }
                        updateKccb(storageEntity,orgCode);*/

                        if(Utils.isEmpty(storageEntity)){
                            storageEntity = saveNewStorage(t,emkProductStorageEntity,storageLogEntity);
                            systemService.executeSql("update emk_storage set a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ",storageEntity.getId());

                        }else{
                            updateStorage(storageEntity,emkProductStorageEntity,storageLogEntity);
                        }
                        systemService.executeSql("update emk_product_storage set stro_id=? where id=? ",storageEntity.getId(),emkProductStorageEntity.getId());

                    }
                }
            }

            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "录入期初库存失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(EmkProductEntity emkProduct, HttpServletRequest req) {
        TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
        saveProNum(emkProduct,orgCode);

        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        req.setAttribute("categoryEntities",categoryEntities);
        req.setAttribute("emkProductPage", emkProduct);

        return new ModelAndView("com/emk/product/product/emkProduct-add");
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(EmkProductEntity emkProduct, HttpServletRequest req) {
        TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        req.setAttribute("categoryEntities",categoryEntities);
        if (StringUtil.isNotEmpty(emkProduct.getId())) {
            emkProduct = emkProductService.getEntity(EmkProductEntity.class, emkProduct.getId());
            req.setAttribute("emkProductPage", emkProduct);

            Map protype = systemService.findOneForJdbc("select * from emk_product_type where id=?", emkProduct.getProType());
            if(protype != null){
                req.setAttribute("proTypeName", protype.get("content"));

            }
        }
        return new ModelAndView("com/emk/product/product/emkProduct-update");
    }
    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(EmkProductEntity emkProduct, HttpServletRequest req) {
        TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);

        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        req.setAttribute("categoryEntities",categoryEntities);
        if (StringUtil.isNotEmpty(emkProduct.getId())) {
            saveProNum(emkProduct,orgCode);
            req.setAttribute("proNum", emkProduct.getProNum());

            emkProduct = emkProductService.getEntity(EmkProductEntity.class, emkProduct.getId());
            req.setAttribute("emkProductPage", emkProduct);

            Map protype = systemService.findOneForJdbc("select * from emk_product_type where id=?", emkProduct.getProType());
            if(protype != null){
                req.setAttribute("proTypeName", protype.get("content"));
            }
        }
        return new ModelAndView("com/emk/product/product/emkProduct-update2");
    }
    @RequestMapping(params = "goUpdateStorage")
    public ModelAndView goUpdateStorage(EmkProductEntity emkProduct, HttpServletRequest req) {
        TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);

        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        req.setAttribute("categoryEntities",categoryEntities);
        if (StringUtil.isNotEmpty(emkProduct.getId())) {
            saveProNum(emkProduct,orgCode);
            req.setAttribute("proNum", emkProduct.getProNum());

            emkProduct = emkProductService.getEntity(EmkProductEntity.class, emkProduct.getId());
            req.setAttribute("emkProductPage", emkProduct);

            Map protype = systemService.findOneForJdbc("select * from emk_product_type where id=?", emkProduct.getProType());
            if(protype != null){
                req.setAttribute("proTypeName", protype.get("content"));
            }
        }
        return new ModelAndView("com/emk/product/product/emkProduct-updateStorage");
    }
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/emk/product/product/uploadView");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(EmkProductEntity emkProduct, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        StringBuffer sql = new StringBuffer();
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);

        Map param = ParameterUtil.getParamMaps(request.getParameterMap());
        sql.append(" select ep.*,\n" +
                "(select t2.typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where t1.typegroupcode='units' and t2.typecode=ep.unit and t1.org_code='A01') unitName,\n" +
                "case ep.a01a01a08 \n" +
                "    when 0 then '不积分'\n" +
                "    when 1 then '按金额积分'\n" +
                "    when 2 then '按数量积分'\n" +
                "   else '' end pointsTypeName,\n" +
                "case ep.a01a01a10 \n" +
                "    when 0 then '无提成'\n" +
                "    when 1 then '按销售额提成' \n" +
                "    when 2 then '按固定金额提成' \n" +
                "    when 3 then '按利润提成' \n" +
                "   else '' end percentageName from emk_product ep where 1=1 \n");
        if(Utils.notEmpty(param.get("proZnName"))){
            sql.append(" and ep.pro_zn_name like '%"+param.get("proZnName")+"%'");
        }
        if(Utils.notEmpty(param.get("proNum"))){
            sql.append(" and ep.pro_num like '%"+param.get("proNum")+"%'");
        }
        sql.append(" and org_code='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"'");

        String savepath = request.getRealPath("/")+"export/product/";
        File file = new File(savepath);
        if(!file.exists()) {
            file.mkdirs();
        }
        savepath = request.getRealPath("/")+"export/product/商品信息.xls";
        Map titleMap = new HashMap();
        List<String> headList = new ArrayList<String>();
        List<String> fieldList = new ArrayList<String>();

        headList.add("类别代码");
        fieldList.add("pro_type");
        headList.add("类别名称");
        fieldList.add("pro_type_name");
        headList.add("商品名称");
        fieldList.add("pro_zn_name");
        headList.add("商品编号");
        fieldList.add("pro_num");
        headList.add("条码编号");
        fieldList.add("bar_code");
        headList.add("规格");
        fieldList.add("standards");
        headList.add("型号");
        fieldList.add("brand");
        headList.add("单位");
        fieldList.add("unitName");
        headList.add("单位重量");
        fieldList.add("unit_wight");
        headList.add("零售价");
        fieldList.add("sell_price");
        headList.add("成本价");
        fieldList.add("cost_price");
        headList.add("备注");
        fieldList.add("remark");

        List<Map<String,Object>> dataList = this.systemService.findForJdbc(sql.toString());
        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        for(Map codeMap : categoryEntities){
            headList.add(codeMap.get("name").toString());
            fieldList.add(codeMap.get("code").toString());
        }
        try {
            ExcelUtil.createExcelFormate(savepath,"商品信息",headList, fieldList, dataList);
            WebFileUtils.downLoad(savepath, response, false);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            file.delete();
        }
        return null;
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(EmkProductEntity emkProduct, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        StringBuffer sql = new StringBuffer();
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);

        String savepath = request.getRealPath("/")+"export/product/";
        File file = new File(savepath);
        if(!file.exists()) {
            file.mkdirs();
        }
        savepath = request.getRealPath("/")+"export/product/商品信息导入模板.xls";
        sql.append(" select ep.*,\n" +
                "(select t2.typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where t1.typegroupcode='units' and t2.typecode=ep.unit and t1.org_code='A01') unitName,\n" +
                "case ep.a01a01a08 \n" +
                "    when 0 then '不积分'\n" +
                "    when 1 then '按金额积分'\n" +
                "    when 2 then '按数量积分'\n" +
                "   else '' end pointsTypeName,\n" +
                "case ep.a01a01a10 \n" +
                "    when 0 then '无提成'\n" +
                "    when 1 then '按销售额提成' \n" +
                "    when 2 then '按固定金额提成' \n" +
                "    when 3 then '按利润提成' \n" +
                "   else '' end percentageName from emk_product ep where 1=1 \n");

        sql.append(" and org_code='"+user.getCurrentDepart().getOrgCode().substring(0,3)+"' limit 0,2");

        savepath = request.getRealPath("/")+"export/product/商品信息导入模板.xls";
        Map titleMap = new HashMap();
        List<String> headList = new ArrayList<String>();
        List<String> fieldList = new ArrayList<String>();

        headList.add("类别名称");
        fieldList.add("pro_type_name");
        headList.add("商品名称");
        fieldList.add("pro_zn_name");
        headList.add("条码编号");
        fieldList.add("bar_code");
        headList.add("规格");
        fieldList.add("standards");
        headList.add("型号");
        fieldList.add("brand");
        headList.add("单位");
        fieldList.add("unitName");
        headList.add("单位重量");
        fieldList.add("unit_wight");
        headList.add("零售价");
        fieldList.add("sell_price");
        headList.add("成本价");
        fieldList.add("cost_price");
        headList.add("备注");
        fieldList.add("remark");

        List<Map<String, Object>> categoryEntities = this.systemService.findForJdbc("select lower(code) code,queryed,name,lower(PARENT_CODE) parentCode,required,column_type from hm_category where PARENT_CODE='A01A01' and org_code=? and is_show=0 order by order_num asc",user.getCurrentDepart().getOrgCode().substring(0,3));
        for(Map codeMap : categoryEntities){
            headList.add(codeMap.get("name").toString());
            fieldList.add(codeMap.get("code").toString());
        }
        try {
            List<Map<String,Object>> dataList = this.systemService.findForJdbc(sql.toString());
            ExcelUtil.createExcel(savepath,headList, fieldList, dataList);
            WebFileUtils.downLoad(savepath, response, false);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            file.delete();
        }
        return "jeecgExcelView";
    }

    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(EmkProductEntity emkProduct,String fileName,String fileNameUrl,HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "商品信息文件导入成功";
        File newfile = null;
        HSSFWorkbook wb = null;
        String cellValue = "";
        EmkProductEntity orderMxEntity = null;
        try {
            TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
            String orgCode = user.getCurrentDepart().getOrgCode().substring(0,3);
            TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
            String savepath = request.getRealPath("/")+"imp/product/";
            newfile =  new File(savepath+fileName);
            wb = WebFileUtils.createHSSFWorkBook(newfile);
            HSSFSheet sheet = wb.getSheetAt(0);
            DecimalFormat df = new DecimalFormat("0");
            HSSFCell cell = null;
            int counter = 0;
            HSSFRow row = null;
            logger.info("执行导入："+newfile.getName());
            List<String> itemValue = null;
            List<String> itemValue0 = new ArrayList<String>();
            List<String> codeList = new ArrayList<String>();

            List<EmkProductTypeEntity> emkProductTypeEntityList = systemService.findHql("from EmkProductTypeEntity where orgCode=?",orgCode);
            Map<String,String> productTypeMap = new HashMap();
            EmkProductPriceEntity productPriceEntity = null;
            for(EmkProductTypeEntity emkProductTypeEntity : emkProductTypeEntityList){
                productTypeMap.put(emkProductTypeEntity.getContent(),emkProductTypeEntity.getId().toString());
            }
            Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(pro_num, 4)),0)+1 AS signed) orderNum from emk_product where org_code=?",orgCode);
            List<EmkProductPriceEntity> emkProductPriceEntityList = systemService.findHql("from EmkProductPriceEntity where orgCode=? and (productId is null or productId ='') order by priceNo asc",orgCode);
            row = sheet.getRow(0);
            for(int z = 10; z <= 29 ; z++){
                cell = row.getCell(z);
                if(cell == null){
                    itemValue0.add(cellValue);
                    continue;
                }
                switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_STRING:
                        cellValue =cell.getRichStringCellValue().getString().trim();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        cellValue = df.format(cell.getNumericCellValue()).toString();
                        break;
                    default:
                        cellValue = "";
                }
                itemValue0.add(cellValue);
                cellValue = "";
            }
            for(int z = 0; z < itemValue0.size() ; z++){
                Map code = systemService.findOneForJdbc("select lower(code) code from hm_category where PARENT_CODE='A01A01' and  name=? and org_code=?",itemValue0.get(z),orgCode);
                if(code != null){
                    codeList.add(code.get("code").toString());
                }
            }
            int num = Integer.valueOf(orderNum.get("orderNum").toString());
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                itemValue = new ArrayList<String>();
                for(int z = 0; z <= 29 ; z++){
                    cell = row.getCell(z);
                    if(cell == null){
                        itemValue.add(cellValue);
                        continue;
                    }
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            cellValue =cell.getRichStringCellValue().getString().trim();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            cellValue = df.format(cell.getNumericCellValue()).toString();
                            break;
                        default:
                            cellValue = "";
                    }
                    itemValue.add(cellValue);
                    cellValue = "";
                }
                if(Utils.notEmpty(itemValue.get(1))){
                    if(Utils.notEmpty(itemValue.get(1))){
                        this.systemService.executeSql("delete from emk_product where org_code=? and pro_zn_name=? and brand=? and (a01a01a01=? or a01a01a01 is null ) and (a01a01a02=? or a01a01a02 is null)",
                                orgCode,itemValue.get(1),itemValue.get(4),itemValue.get(10),itemValue.get(11));

                        orderMxEntity = new EmkProductEntity();
                        orderMxEntity.setProType(productTypeMap.get(itemValue.get(0)));
                        orderMxEntity.setProTypeName(itemValue.get(0));
                        orderMxEntity.setProZnName(itemValue.get(1));
                        orderMxEntity.setProNum(String.format("%04d",num));
                        orderMxEntity.setBarCode(itemValue.get(2));
                        orderMxEntity.setProZjm(ChineseToEnglish.getPinYinHeadChar(orderMxEntity.getProZnName()));
                        orderMxEntity.setStandards(itemValue.get(3));
                        orderMxEntity.setBrand(itemValue.get(4));
                        orderMxEntity.setUnit(itemValue.get(5));
                        orderMxEntity.setUnitWight(itemValue.get(6));
                        orderMxEntity.setSellPrice(itemValue.get(7));
                        orderMxEntity.setCostPrice(itemValue.get(8));
                        orderMxEntity.setRemark(itemValue.get(9));
                        orderMxEntity.setOrgCode(orgCode);
                        orderMxEntity.setDepartId(tsDepart.getId());
                        this.systemService.save(orderMxEntity);

                        for(EmkProductPriceEntity emkProductPriceEntity : emkProductPriceEntityList){
                            productPriceEntity = new EmkProductPriceEntity();
                            MyBeanUtils.copyBeanNotNull2Bean(emkProductPriceEntity,productPriceEntity);
                            productPriceEntity.setProductId(orderMxEntity.getId());
                            productPriceEntity.setId(null);
                            productPriceEntity.setDepartId(tsDepart.getId());
                            productPriceEntity.setOrgCode(orgCode);
                            systemService.save(productPriceEntity);
                        }
                        String sql = "update emk_product set  ";
                        int  codei = 0;

                        for(int z = 0 ; z < codeList.size() ; z++){
                            sql += codeList.get(z)+"='"+itemValue.get(z+9)+"',";
                        }
                        if(itemValue.size()>1){
                            sql = sql.substring(0,sql.length()-1);
                            sql += " where id='"+orderMxEntity.getId()+"'";
                            this.systemService.executeSql(sql);
                        }
                        num++;
                    }
                }
                j.setSuccess(true);
            }
        } catch (Exception e) {
            message = "商品信息文件导入失败";
            j.setSuccess(false);
            logger.error(ExceptionUtil.getExceptionMessage(e));
        }finally{
            newfile.delete();
        }
        systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        j.setMsg(message);
        return j;
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public List<EmkProductEntity> list() {
        List<EmkProductEntity> listEmkProducts = emkProductService.getList(EmkProductEntity.class);
        return listEmkProducts;
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        EmkProductEntity task = emkProductService.get(EmkProductEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody EmkProductEntity emkProduct, UriComponentsBuilder uriBuilder) {
        Set<ConstraintViolation<EmkProductEntity>> failures = validator.validate(emkProduct, new Class[0]);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }
        try {
            emkProductService.save(emkProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        String id = emkProduct.getId();
        URI uri = uriBuilder.path("/rest/emkProductController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.PUT}, consumes = "application/json")
    public ResponseEntity<?> update(@RequestBody EmkProductEntity emkProduct) {
        Set<ConstraintViolation<EmkProductEntity>> failures = validator.validate(emkProduct, new Class[0]);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }
        try {
            emkProductService.saveOrUpdate(emkProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = {org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        emkProductService.deleteEntityById(EmkProductEntity.class, id);
    }
}
