package org.jeecgframework.web.system.controller.core;

import com.emk.bound.certificate.entity.EmkCertificateEntity;
import com.emk.email.smsmodel.entity.ESmsModelEntity;
import com.emk.product.product.entity.EmkProductEntity;
import com.emk.product.producttype.entity.EmkProductTypeEntity;
import com.emk.product.producttype.entity.EmkProductTypeEntityA;
import com.emk.system.sysparam.entity.EmkSysParamEntity;
import com.emk.util.ChineseToEnglish;
import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import com.emk.util.WebFileUtils;
import com.hm.rsgl.category.entity.HmCategoryEntity;
import com.hm.rsgl.staff.entity.HmStaffEntity;
import com.service.custom.entity.YmkCustomEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.DataTables;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
    private static final Logger logger = Logger.getLogger(UserController.class);
    private UserService userService;
    private SystemService systemService;

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(params = "menu")
    public void menu(HttpServletRequest request, HttpServletResponse response) {
        SetListSort sort = new SetListSort();
        TSUser u = ResourceUtil.getSessionUser();

        Set<TSFunction> loginActionlist = new HashSet();
        List<TSRoleUser> rUsers = this.systemService.findByProperty(TSRoleUser.class, "TSUser.id", u.getId());
        for (TSRoleUser ru : rUsers) {
            TSRole role = ru.getTSRole();
            List<TSRoleFunction> roleFunctionList = this.systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
            if (roleFunctionList.size() > 0) {
                for (TSRoleFunction roleFunction : roleFunctionList) {
                    TSFunction function = roleFunction.getTSFunction();
                    loginActionlist.add(function);
                }
            }
        }
        List<TSFunction> bigActionlist = new ArrayList();
        List<TSFunction> smailActionlist = new ArrayList();
        if (loginActionlist.size() > 0) {
            for (TSFunction function : loginActionlist) {
                if (function.getFunctionLevel().shortValue() == 0) {
                    bigActionlist.add(function);
                } else if (function.getFunctionLevel().shortValue() == 1) {
                    smailActionlist.add(function);
                }
            }
        }
        Collections.sort(bigActionlist, sort);
        Collections.sort(smailActionlist, sort);
        String logString = ListtoMenu.getMenu(bigActionlist, smailActionlist);
        try {
            response.getWriter().write(logString);
            response.getWriter().flush();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(params = "user")
    public String user(HttpServletRequest request) {
        //List<TSDepart> departList = this.systemService.getList(TSDepart.class);
        //request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
        //departList.clear();
        return "system/user/userList";
    }

    @RequestMapping(params = "ownerList")
    public String ownerList(HttpServletRequest request) {
        return "system/user/ownerList";
    }

    @RequestMapping(params = "userinfo")
    public String userinfo(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUser();
        request.setAttribute("user", user);
        return "system/user/userinfo";
    }

    @RequestMapping(params = "changepassword")
    public String changepassword(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUser();
        request.setAttribute("user", user);
        return "system/user/changepassword";
    }

    @RequestMapping(params = "changeportrait")
    public String changeportrait(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUser();
        request.setAttribute("user", user);
        return "system/user/changeportrait";
    }

    @RequestMapping(params = "saveportrait")
    @ResponseBody
    public AjaxJson saveportrait(HttpServletRequest request, String fileName) {
        AjaxJson j = new AjaxJson();
        TSUser user = ResourceUtil.getSessionUser();
        user.setPortrait(fileName);
        j.setMsg("修改成功");
        try {
            this.systemService.updateEntitie(user);
        } catch (Exception e) {
            j.setMsg("修改失败");
            e.printStackTrace();
        }
        return j;
    }

    @RequestMapping(params = "savenewpwd")
    @ResponseBody
    public AjaxJson savenewpwd(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TSUser user = ResourceUtil.getSessionUser();
        logger.info("[" + IpUtil.getIpAddr(request) + "][修改密码] start");
        String password = oConvertUtils.getString(request.getParameter("password"));
        String newpassword = oConvertUtils.getString(request.getParameter("newpassword"));
        String pString = PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt());
        if (!pString.equals(user.getPassword())) {
            j.setMsg("原密码不正确");
            j.setSuccess(false);
        } else {
            try {
                user.setPassword(PasswordUtil.encrypt(user.getUserName(), newpassword, PasswordUtil.getStaticSalt()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.systemService.updateEntitie(user);
            j.setMsg("修改成功");
            logger.info("[" + IpUtil.getIpAddr(request) + "][修改密码]修改成功 userId:" + user.getUserName());
        }
        return j;
    }

    @RequestMapping(params = "changepasswordforuser")
    public ModelAndView changepasswordforuser(TSUser user, HttpServletRequest req) {
        logger.info("[" + IpUtil.getIpAddr(req) + "][跳转重置用户密码页面][" + user.getUserName() + "]");
        if (StringUtil.isNotEmpty(user.getId())) {
            user = systemService.getEntity(TSUser.class, user.getId());
            req.setAttribute("user", user);
            idandname(req, user);
        }
        return new ModelAndView("system/user/adminchangepwd");
    }

    @RequestMapping(params = "savenewpwdforuser")
    @ResponseBody
    public AjaxJson savenewpwdforuser(HttpServletRequest req) {
        logger.info("[" + IpUtil.getIpAddr(req) + "][重置密码] start");
        String message = null;
        AjaxJson j = new AjaxJson();
        String id = oConvertUtils.getString(req.getParameter("id"));
        String password = oConvertUtils.getString(req.getParameter("password"));
        if (StringUtil.isNotEmpty(id)) {
            TSUser users = systemService.getEntity(TSUser.class, id);
            if (("admin".equals(users.getUserName())) && (!"admin".equals(ResourceUtil.getSessionUser().getUserName()))) {
                message = "超级管理员[admin]，只有admin本人可操作，其他人无权限!";
                logger.info("[" + IpUtil.getIpAddr(req) + "]" + message);
                j.setMsg(message);
                return j;
            }
            users.setPassword(PasswordUtil.encrypt(users.getUserName(), password, PasswordUtil.getStaticSalt()));
            users.setStatus(Globals.User_Normal);
            users.setActivitiSync(users.getActivitiSync());
            this.systemService.updateEntitie(users);
            message = "用户: " + users.getUserName() + "密码重置成功";
            this.systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            logger.info("[" + IpUtil.getIpAddr(req) + "][重置密码]" + message);
        }
        j.setMsg(message);

        return j;
    }

    @RequestMapping(params = "lock")
    @ResponseBody
    public AjaxJson lock(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String message = null;
        TSUser user = systemService.getEntity(TSUser.class, id);
        if ("admin".equals(user.getUserName())) {
            message = "超级管理员[admin]不可操作";
            j.setMsg(message);
            return j;
        }
        String lockValue = req.getParameter("lockvalue");

        user.setStatus(new Short(lockValue));
        try {
            this.userService.updateEntitie(user);
            if ("0".equals(lockValue)) {
                message = "用户：" + user.getUserName() + "锁定成功!";
            } else if ("1".equals(lockValue)) {
                message = "用户：" + user.getUserName() + "激活成功!";
            }
            this.systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            logger.info("[" + IpUtil.getIpAddr(req) + "][锁定账户]" + message);
        } catch (Exception e) {
            message = "操作失败!";
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "role")
    @ResponseBody
    public List<ComboBox> role(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
        String id = request.getParameter("id");
        List<ComboBox> comboBoxs = new ArrayList();
        List<TSRole> roles = new ArrayList();
        if (StringUtil.isNotEmpty(id)) {
            List<TSRoleUser> roleUser = this.systemService.findByProperty(TSRoleUser.class, "TSUser.id", id);
            if (roleUser.size() > 0) {
                for (TSRoleUser ru : roleUser) {
                    roles.add(ru.getTSRole());
                }
            }
        }
        List<TSRole> roleList = this.systemService.getList(TSRole.class);
        comboBoxs = TagUtil.getComboBox(roleList, roles, comboBox);

        roleList.clear();
        roles.clear();

        return comboBoxs;
    }

    @RequestMapping(params = "depart")
    @ResponseBody
    public List<ComboBox> depart(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
        String id = request.getParameter("id");
        List<ComboBox> comboBoxs = new ArrayList();
        List<TSDepart> departs = new ArrayList();
        if (StringUtil.isNotEmpty(id)) {
            TSUser user = systemService.get(TSUser.class, id);


            List<TSDepart[]> resultList = this.systemService.findHql("from TSDepart d,TSUserOrg uo where d.id=uo.orgId and uo.id=?", new Object[]{id});
            for (TSDepart[] departArr : resultList) {
                departs.add(departArr[0]);
            }
        }
        List<TSDepart> departList = this.systemService.getList(TSDepart.class);
        comboBoxs = TagUtil.getComboBox(departList, departs, comboBox);
        return comboBoxs;
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        String departId = user.getDepartid();
        user.setDepartid(null);
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());

        HqlGenerateUtil.installHql(cq, user);
        //Map roleMap = (Map) request.getSession().getAttribute("ROLE");
        TSUser suser = (TSUser) request.getSession().getAttribute("LOCAL_CLINET_USER");
        if(!suser.getUserName().equals("admin")){
            if(Utils.notEmpty(departId)){
                if (departId.equals(suser.getDepartid())) {
                    cq.add(Restrictions.sqlRestriction("({alias}.create_by='"+suser.getUserName()+"' or username='"+suser.getUserName()+"')"));
                }else{
                    cq.eq("departid",departId);
                }
            }else{
                if (suser.getUserKey().equals("管理员")) {
                    cq.add(Restrictions.sqlRestriction("({alias}.create_by='"+suser.getUserName()+"' or username='"+suser.getUserName()+"')"));
                }
            }
        }else{
            if(Utils.notEmpty(departId)){
                cq.eq("departid",departId);
            }
        }

        Short[] userstate = {Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden};
        cq.in("status", userstate);
        cq.eq("deleteFlag", Globals.Delete_Normal);

        String orgIds = request.getParameter("orgIds");
        List<String> orgIdList = extractIdListByComma(orgIds);
        if (!CollectionUtils.isEmpty(orgIdList)) {
            CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
            subCq.setProjection(Property.forName("tsUser.id"));
            subCq.in("tsDepart.id", orgIdList.toArray());
            subCq.add();

            cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
        }

        if(Utils.notEmpty(map.get("orgCode"))){
            TSDepart depart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",map.get("orgCode"));
            cq.eq("departid",depart.getId());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);

        List<TSUser> cfeList = new ArrayList();
        for (Object o : dataGrid.getResults()) {
            if ((o instanceof TSUser)) {
                TSUser cfe = (TSUser) o;
                if ((cfe.getId() != null) && (!"".equals(cfe.getId()))) {
                    List<TSRoleUser> roleUser = this.systemService.findByProperty(TSRoleUser.class, "TSUser.id", cfe.getId());
                    if (roleUser.size() > 0) {
                        String roleName = "";
                        for (TSRoleUser ru : roleUser) {
                            roleName = roleName + ru.getTSRole().getRoleName() + ",";
                        }
                        roleName = roleName.substring(0, roleName.length() - 1);
                        cfe.setUserKey(roleName);
                    }
                }
                cfeList.add(cfe);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "servicedatagrid")
    public void servicedatagrid(TSServiceUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        try {
            String sql = "";
            String countsql = "";
            sql = "SELECT t2.id,t2.`username` userName,t1.`mobilePhone`,t2.`realname` realName,t2.`userkey`,t4.`cus_name` customName,t4.`id` customId,t4.cus_num customNum FROM t_s_user t1\nLEFT JOIN t_s_base_user t2 ON t1.`id`=t2.`ID`\nLEFT JOIN ymk_custom_contact_user t3 ON t3.`user_id`=t1.`id`\nLEFT JOIN ymk_custom t4 ON t4.`id`=t3.`contact_id` where t2.`userkey`='" + map.get("userkey") + "' ";


            countsql = " SELECT COUNT(0) FROM t_s_user t1\nLEFT JOIN t_s_base_user t2 ON t1.`id`=t2.`ID` WHERE t2.`userkey`='" + map.get("userkey") + "' ";
            if (dataGrid.getPage() == 1) {
                sql = sql + " limit 0, " + dataGrid.getRows();
            } else {
                sql = sql + "limit " + (dataGrid.getPage() - 1) * dataGrid.getRows() + "," + dataGrid.getRows();
            }
            this.systemService.listAllByJdbc(dataGrid, sql, countsql);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(params = "signservicedatagrid")
    public void signservicedatagrid(TSServiceUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        try {
            TSUser cuser = (TSUser) request.getSession().getAttribute("LOCAL_CLINET_USER");

            String sql = "";
            String countsql = "";
            sql = "SELECT t2.id,t2.`username` userName,t1.`mobilePhone`,t2.`realname` realName,t2.`userkey`,t4.`cus_name`,t4.`id` cus_id FROM t_s_user t1\nLEFT JOIN t_s_base_user t2 ON t1.`id`=t2.`ID`\nLEFT JOIN ymk_custom_contact_user t3 ON t3.`user_id`=t1.`id`\nLEFT JOIN ymk_custom t4 ON t4.`id`=t3.`contact_id`\nleft join u_sign_service t5 on t5.`part_b_id`=t4.id\nwhere t5.`arealtioner_id`='" + cuser.getId() + "'";


            countsql = " SELECT count(0) FROM t_s_user t1\nLEFT JOIN t_s_base_user t2 ON t1.`id`=t2.`ID`\nLEFT JOIN ymk_custom_contact_user t3 ON t3.`user_id`=t1.`id`\nLEFT JOIN ymk_custom t4 ON t4.`id`=t3.`contact_id`\nleft join u_sign_service t5 on t5.`part_b_id`=t4.id\nwhere t5.`arealtioner_id`='" + cuser.getId() + "' ";
            if (dataGrid.getPage() == 1) {
                sql = sql + " limit 0, " + dataGrid.getRows();
            } else {
                sql = sql + "limit " + (dataGrid.getPage() - 1) * dataGrid.getRows() + "," + dataGrid.getRows();
            }
            this.systemService.listAllByJdbc(dataGrid, sql, countsql);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(params = "ygservicedatagrid")
    public void ygservicedatagrid(TSServiceUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        try {
            YmkCustomEntity custom = (YmkCustomEntity) request.getSession().getAttribute("custom");
            String sql = "";
            String countsql = "";
            sql = "SELECT t2.id,t2.`username` userName,t1.`mobilePhone`,t2.`realname` realName,t2.`userkey`,t4.`cus_name`,t4.`id` cus_id FROM t_s_user t1\nLEFT JOIN t_s_base_user t2 ON t1.`id`=t2.`ID`\nLEFT JOIN ymk_custom_contact_user t3 ON t3.`user_id`=t1.`id`\nLEFT JOIN ymk_custom_contact t31 ON t31.`id`=t3.`contact_id`\nLEFT JOIN ymk_custom t4 ON t4.`id`=t31.`custom_id`\nWHERE t4.`id`='" + custom.getId() + "' ";


            countsql = " SELECT count(0) FROM t_s_user t1\nLEFT JOIN t_s_base_user t2 ON t1.`id`=t2.`ID`\nLEFT JOIN ymk_custom_contact_user t3 ON t3.`user_id`=t1.`id`\nLEFT JOIN ymk_custom_contact t31 ON t31.`id`=t3.`contact_id`\nLEFT JOIN ymk_custom t4 ON t4.`id`=t31.`custom_id`\nwhere t4.`id`='" + custom.getId() + "'";
            if (dataGrid.getPage() == 1) {
                sql = sql + " limit 0, " + dataGrid.getRows();
            } else {
                sql = sql + "limit " + (dataGrid.getPage() - 1) * dataGrid.getRows() + "," + dataGrid.getRows();
            }
            this.systemService.listAllByJdbc(dataGrid, sql, countsql);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(params = "userdeptdatagrid0")
    public void userdeptdatagrid0(TSServiceUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        try {
            YmkCustomEntity custom = (YmkCustomEntity) request.getSession().getAttribute("custom");
            String sql = "";
            String countsql = "";
            sql = "SELECT t1.`userkey` userkey0,t1.`ID`,t1.`realname` sender,t1.`username` senderUserNames,t3.`org_code` sendDeptCode,t3.`departname` sendDeptName FROM t_s_base_user t1 \nLEFT JOIN t_s_user_org t2 ON t1.`ID`=t2.`user_id`\nLEFT JOIN t_s_depart t3 ON t3.`ID`=t2.`org_id` where t1.userkey !='客户'";


            countsql = " SELECT count(1) FROM t_s_base_user t1 where t1.userkey !='客户'";
            if (dataGrid.getPage() == 1) {
                sql = sql + " limit 0, " + dataGrid.getRows();
            } else {
                sql = sql + "limit " + (dataGrid.getPage() - 1) * dataGrid.getRows() + "," + dataGrid.getRows();
            }
            this.systemService.listAllByJdbc(dataGrid, sql, countsql);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(params = "userdeptdatagrid1")
    public void userdeptdatagrid1(TSServiceUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        try {
            YmkCustomEntity custom = (YmkCustomEntity) request.getSession().getAttribute("custom");
            String sql = "";
            String countsql = "";
            sql = "SELECT t1.`userkey` userkey1,t1.`ID`,t1.`realname` recevier,t1.`username` recevierUserNames,t3.`org_code` recevieDeptCode,t3.`departname` recevieDeptName FROM t_s_base_user t1 \nLEFT JOIN t_s_user_org t2 ON t1.`ID`=t2.`user_id`\nLEFT JOIN t_s_depart t3 ON t3.`ID`=t2.`org_id` where t1.userkey !='客户'";


            countsql = " SELECT count(1) FROM t_s_base_user t1 where t1.userkey !='客户'";
            if (dataGrid.getPage() == 1) {
                sql = sql + " limit 0, " + dataGrid.getRows();
            } else {
                sql = sql + "limit " + (dataGrid.getPage() - 1) * dataGrid.getRows() + "," + dataGrid.getRows();
            }
            this.systemService.listAllByJdbc(dataGrid, sql, countsql);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(params = "userdeptdatagrid2")
    public void userdeptdatagrid2(TSServiceUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        try {
            YmkCustomEntity custom = (YmkCustomEntity) request.getSession().getAttribute("custom");
            String sql = "";
            String countsql = "";
            sql = "SELECT t1.`userkey` userkey2,t1.`ID`,t1.`realname` copyer,t1.`username` copyerUserNames,t3.`org_code` copyDeptCode,t3.`departname` copyDeptName FROM t_s_base_user t1 \nLEFT JOIN t_s_user_org t2 ON t1.`ID`=t2.`user_id`\nLEFT JOIN t_s_depart t3 ON t3.`ID`=t2.`org_id` where t1.userkey !='客户'";


            countsql = " SELECT count(1) FROM t_s_base_user t1 where t1.userkey !='客户'";
            if (dataGrid.getPage() == 1) {
                sql = sql + " limit 0, " + dataGrid.getRows();
            } else {
                sql = sql + "limit " + (dataGrid.getPage() - 1) * dataGrid.getRows() + "," + dataGrid.getRows();
            }
            this.systemService.listAllByJdbc(dataGrid, sql, countsql);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(params = "deleteDialog")
    public String deleteDialog(TSUser user, HttpServletRequest request) {
        request.setAttribute("user", user);
        return "system/user/user-delete";
    }

    @RequestMapping(params = "delete")
    @ResponseBody
    public AjaxJson delete(TSUser user, @RequestParam String deleteType, HttpServletRequest req) {
        if (deleteType.equals("delete")) {
            return del(user, req);
        }
        if (deleteType.equals("deleteTrue")) {
            return trueDel(user, req);
        }
        AjaxJson j = new AjaxJson();

        j.setMsg("删除逻辑参数异常,请重试.");
        return j;
    }

    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(TSUser user, HttpServletRequest req) {
        String message = null;
        AjaxJson j = new AjaxJson();
        if ("admin".equals(user.getUserName())) {
            message = "超级管理员[admin]不可删除";
            j.setMsg(message);
            return j;
        }
        user = systemService.getEntity(TSUser.class, user.getId());
        if (!user.getStatus().equals(Globals.User_ADMIN)) {
            user.setDeleteFlag(Globals.Delete_Forbidden);
            this.userService.updateEntitie(user);
            message = "用户：" + user.getUserName() + "删除成功";
            logger.info("[" + IpUtil.getIpAddr(req) + "][逻辑删除用户]" + message);
        } else {
            message = "超级管理员不可删除";
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "trueDel")
    @ResponseBody
    public AjaxJson trueDel(TSUser user, HttpServletRequest req) {
        String message = null;
        AjaxJson j = new AjaxJson();
        if ("admin".equals(user.getUserName())) {
            message = "超级管理员[admin]不可删除";
            j.setMsg(message);
            return j;
        }
        user = systemService.getEntity(TSUser.class, user.getId());
        String orgCode = user.getUserOrgList().get(0).getTsDepart().getOrgCode();
        String departId = user.getDepartid();
        try {
            if(user.getUserKey().equals("管理员")){
                systemService.executeSql("delete from emk_sys_param where depart_id=?",departId);
                systemService.executeSql("delete from emk_product_type where depart_id=?",departId);
                List<EmkProductEntity> emkProductEntityList = systemService.findHql("from EmkProductEntity where departId=? and proImageUrl != '' and proImageUrl != null",departId);
                for(EmkProductEntity emkProduct : emkProductEntityList){
                    if(Utils.notEmpty(emkProduct.getProImageUrl())){
                        String url = emkProduct.getProImageUrl().substring(emkProduct.getProImageUrl().indexOf("imp/"));
                        WebFileUtils.delete(req.getRealPath("/")+url);
                    }
                }

                systemService.executeSql("delete from emk_product where depart_id=?",departId);
                Map type = systemService.findOneForJdbc("select GROUP_CONCAT(t2.id) typegroupid from t_s_typegroup t2 where t2.depart_id=?",departId);
                if(Utils.notEmpty(type)){
                    systemService.executeSql("delete from t_s_type where FIND_IN_SET(typegroupid,?)",type.get("typegroupid"));
                }
                //删除商品预设价格数据
                systemService.executeSql("delete from emk_product_price where depart_id=?",departId);
                //删除商品期初库存数据
                systemService.executeSql("delete from emk_product_storage where depart_id=?",departId);
                //删除字典数据
                systemService.executeSql("delete from t_s_typegroup where depart_id=?",departId);
                //删除字段表数据
                systemService.executeSql("delete from hm_category where depart_id=?",departId);
                //删除短信模板数据
                systemService.executeSql("delete from e_sms_model where depart_id=?",departId);
                //删除短信记录数据
                systemService.executeSql("delete from e_sms_record where depart_id=?",departId);
                //删除日记数据
                systemService.executeSql("delete from t_s_log where org_code=?",orgCode);
                //删除商品价格数据
                systemService.executeSql("delete from emk_product_price where depart_id=?",departId);
                //删除供应商数据
                systemService.executeSql("delete from emk_factory_archives where depart_id=?",departId);
                //删除客户档案数据
                systemService.executeSql("delete from ymk_custom where org_code=?",orgCode);
                //删除采购入库数据
                systemService.executeSql("delete from emk_m_in_storage where depart_id=?",departId);
                //删除采购入库明细数据
                systemService.executeSql("delete from emk_m_in_storage_detail where depart_id=?",departId);
                //删除订单数据
                systemService.executeSql("delete from emk_pro_order where depart_id=?",departId);
                //删除订单明细数据
                systemService.executeSql("delete from emk_pro_order_detail where depart_id=?",departId);
                //删除销售开单数据
                systemService.executeSql("delete from emk_m_out_storage where depart_id=?",departId);
                //删除销售开单明细数据
                systemService.executeSql("delete from emk_m_out_storage_detail where depart_id=?",departId);
                //删除库存组合单数据
                systemService.executeSql("delete from emk_storage_connact where depart_id=?",departId);
                //删除库存组合明细数据
                systemService.executeSql("delete from emk_storage_connact_detail where depart_id=?",departId);
                //删除退货单数据
                systemService.executeSql("delete from emk_cancel_order where depart_id=?",departId);
                //删除退货单明细数据
                systemService.executeSql("delete from emk_cancel_order_detail where depart_id=?",departId);
                //删除报价单数据
                systemService.executeSql("delete from emk_offer_price where depart_id=?",departId);
                //删除报价单明细数据
                systemService.executeSql("delete from emk_offer_price_detail where depart_id=?",departId);
                //删除库存数据
                systemService.executeSql("delete from emk_storage where depart_id=?",departId);
                //删除库存日记数据
                systemService.executeSql("delete from emk_storage_log where depart_id=?",departId);
                //删除库位数据
                systemService.executeSql("delete from emk_storage_set_position where depart_id=?",departId);
                //删除仓库数据
                systemService.executeSql("delete from emk_storage_set where depart_id=?",departId);
                //删除证照数据
                List<EmkCertificateEntity> emkCertificateEntityList = systemService.findHql("from EmkCertificateEntity where departId=? and certImageUrl != '' and certImageUrl != null",departId);
                for(EmkCertificateEntity emkCertificateEntity : emkCertificateEntityList){
                    if(Utils.notEmpty(emkCertificateEntity.getCertImageUrl())){
                        String url = emkCertificateEntity.getCertImageUrl().substring(emkCertificateEntity.getCertImageUrl().indexOf("imp/"));
                        WebFileUtils.delete(req.getRealPath("/")+url);
                    }
                }
                systemService.executeSql("delete from emk_certificate where depart_id=?",departId);

                //删除现金银行记录数据
                systemService.executeSql("delete from emk_bank_record where depart_id=?",departId);
                //删除员工记录数据
                systemService.executeSql("delete from hm_staff where depart_id=?",departId);
                //删除用户数据
                Map userMap = systemService.findOneForJdbc("select ifnull(GROUP_CONCAT(t2.id),0) ids from t_s_user t2 where t2.create_by=?",user.getUserName());
                if(!"0".equals(userMap.get("ids").toString())){
                    systemService.executeSql("delete from t_s_role_user where FIND_IN_SET(userid,?)",userMap.get("ids"));
                    systemService.executeSql("delete from t_s_user_org where FIND_IN_SET(user_id,?)",userMap.get("ids"));
                    systemService.executeSql("delete from t_s_user where FIND_IN_SET(id,?)",userMap.get("ids"));
                    systemService.executeSql("delete from t_s_base_user where FIND_IN_SET(id,?)",userMap.get("ids"));
                }

                //删除角色数据
                Map roleMap = systemService.findOneForJdbc("select ifnull(GROUP_CONCAT(t2.id),0) ids from t_s_role t2 where t2.create_by=?",user.getUserName());
                if(!"0".equals(roleMap.get("ids").toString())){
                    systemService.executeSql("delete from t_s_role_function where FIND_IN_SET(roleid,?)",roleMap.get("ids"));
                    systemService.executeSql("delete from t_s_role where FIND_IN_SET(id,?)",roleMap.get("ids"));
                }
                //删除部门数据
                //设置为1的时候外键约束是打开的，设置为0的时候外键约束是关闭的;
                systemService.executeSql("SET FOREIGN_KEY_CHECKS=0;");
                systemService.executeSql("delete from t_s_depart where parent_org_code = ? ",orgCode);
                systemService.executeSql("SET FOREIGN_KEY_CHECKS=1;");


            }
            message = this.userService.trueDel(user);
            if(user.getUserKey().equals("管理员")){
                systemService.executeSql("delete from t_s_depart where org_code = ?",orgCode);
            }else{
                orgCode = orgCode.substring(0,3);
                systemService.executeSql("delete from hm_staff where org_code=? and user_name=?",orgCode,user.getUserName());
            }
            logger.info("[" + IpUtil.getIpAddr(req) + "][真实删除用户]" + message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除失败";
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "checkUser")
    @ResponseBody
    public ValidForm checkUser(HttpServletRequest request) {
        ValidForm v = new ValidForm();
        String userName = oConvertUtils.getString(request.getParameter("param"));
        String code = oConvertUtils.getString(request.getParameter("code"));
        List<TSUser> roles = this.systemService.findByProperty(TSUser.class, "userName", userName);
        if ((roles.size() > 0) && (!code.equals(userName))) {
            v.setInfo("用户名已存在");
            v.setStatus("n");
        }
        return v;
    }

    @RequestMapping(params = "checkUserEmail")
    @ResponseBody
    public ValidForm checkUserEmail(HttpServletRequest request) {
        ValidForm validForm = new ValidForm();
        String email = oConvertUtils.getString(request.getParameter("param"));
        String code = oConvertUtils.getString(request.getParameter("code"));
        List<TSUser> userList = this.systemService.findByProperty(TSUser.class, "email", email);
        if ((userList.size() > 0) && (!code.equals(email))) {
            validForm.setInfo("邮箱已绑定相关用户信息");
            validForm.setStatus("n");
        }
        return validForm;
    }

    @RequestMapping(params = "saveUser")
    @ResponseBody
    public AjaxJson saveUser(HttpServletRequest req, TSUser user) {
        String message = null;
        AjaxJson j = new AjaxJson();

        Short logType = Globals.Log_Type_UPDATE;

        String roleid = oConvertUtils.getString(req.getParameter("roleid"));
        String orgid = oConvertUtils.getString(req.getParameter("orgIds"));
        if (StringUtil.isNotEmpty(user.getId())) {
            TSUser users = systemService.getEntity(TSUser.class, user.getId());
            users.setEmail(user.getEmail());
            users.setOfficePhone(user.getOfficePhone());
            users.setMobilePhone(user.getMobilePhone());
            users.setDevFlag(user.getDevFlag());
            users.setRealName(user.getRealName());
            users.setStatus(Globals.User_Normal);
            users.setActivitiSync(user.getActivitiSync());
            users.setStorageId(user.getStorageId());
            users.setStorageName(user.getStorageName());
            this.userService.saveOrUpdate(users, orgid.split(","), roleid.split(","));
            //systemService.executeSql("update t_s_base_user set departid=? where id=?",orgid,users.getId());
            message = "用户: " + users.getUserName() + "更新成功";
        } else {
            TSUser users = systemService.findUniqueByProperty(TSUser.class, "userName", user.getUserName());
            if (users != null) {
                message = "用户: " + users.getUserName() + "已经存在";
            } else {
                user.setPassword(PasswordUtil.encrypt(user.getUserName(), oConvertUtils.getString(req.getParameter("password")), PasswordUtil.getStaticSalt()));
                user.setStatus(Globals.User_Normal);
                user.setDeleteFlag(Globals.Delete_Normal);
                user.setDepartid(orgid);
                this.userService.saveOrUpdate(user, orgid.split(","), roleid.split(","));
                message = "用户: " + user.getUserName() + "添加成功";
                TSUser tuser = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
                String orgCode = tuser.getCurrentDepart().getOrgCode().substring(0,3);
                TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",orgCode);
                HmStaffEntity hmStaff = new HmStaffEntity();
                hmStaff.setDepartId(tsDepart.getId());
                hmStaff.setOrgCode(tsDepart.getOrgCode());
                tsDepart = systemService.findUniqueByProperty(TSDepart.class,"id",orgid);
                hmStaff.setDeptName(tsDepart.getDepartname());
                hmStaff.setDeptCode(tsDepart.getOrgCode());
                hmStaff.setRealName(user.getRealName());
                Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(work_no,4)),0)+1 AS signed) orderNum from hm_staff where org_code=?",orgCode);
                if(Utils.notEmpty(orderNum)){
                    hmStaff.setWorkNo(String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
                }
                hmStaff.setStorageId(user.getStorageId());
                hmStaff.setStorageName(user.getStorageName());
                hmStaff.setUserName(user.getUserName());
                hmStaff.setZjm(PinyinUtil.getPinYinHeadChar(hmStaff.getRealName()));
                systemService.save(hmStaff);
                logType = Globals.Log_Type_INSERT;
            }
        }
        this.systemService.addLog(message, logType, Globals.Log_Leavel_INFO);
        j.setMsg(message);
        logger.info("[" + IpUtil.getIpAddr(req) + "][添加编辑用户]" + message);
        return j;
    }

    private synchronized String getMaxLocalCode(String parentCode){
        if(oConvertUtils.isEmpty(parentCode)){
            parentCode = "";
        }
        int localCodeLength = parentCode.length() + YouBianCodeUtil.zhanweiLength;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT org_code FROM t_s_depart");

        if(ResourceUtil.getJdbcUrl().indexOf(JdbcDao.DATABSE_TYPE_SQLSERVER)!=-1){
            sb.append(" where LEN(org_code) = ").append(localCodeLength);
        }else{
            sb.append(" where LENGTH(org_code) = ").append(localCodeLength);
        }

        if(oConvertUtils.isNotEmpty(parentCode)){
            sb.append(" and  org_code like '").append(parentCode).append("%'");
        } else {

            sb.append(" and LEFT(org_code,1)='A'");

        }

        sb.append(" ORDER BY org_code DESC");
        List<Map<String, Object>> objMapList = systemService.findForJdbc(sb.toString(), 1, 1);
        String returnCode = null;
        if(objMapList!=null && objMapList.size()>0){
            returnCode = (String)objMapList.get(0).get("org_code");
        }

        return returnCode;
    }
    @RequestMapping(params = "saveUserForReg")
    @ResponseBody
    public AjaxJson saveUserForReg(HttpServletRequest req, TSUser user) {
        String message = null;
        AjaxJson j = new AjaxJson();
        Map<String,String> param = ParameterUtil.getParamMaps(req.getParameterMap());
        Short logType = Globals.Log_Type_UPDATE;

        TSDepart depart = systemService.findUniqueByProperty(TSDepart.class,"departname",param.get("cusName"));
        if(Utils.notEmpty(depart)){
            j.setSuccess(false);
            j.setMsg("公司名称已注册过");
            return j;
        }
        if(Utils.isEmpty(param.get("telphone"))){
            j.setSuccess(false);
            j.setMsg("手机号必填");
            return j;
        }
        TSUser users = systemService.findUniqueByProperty(TSUser.class, "userName", user.getUserName());
        if (users != null) {
            message = "注册用户: " + users.getUserName() + "已经存在";
            j.setSuccess(false);
            j.setMsg(message);
            return j;
        } else {
            //初始化部门组织机构数据
            depart = new TSDepart();
            depart.setTSPDepart(null);
            depart.setOrgType("1");
            depart.setDepartname(param.get("cusName"));
            depart.setMobile(param.get("telphone"));
            depart.setAddress(param.get("address"));
            /*TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"parentOrgCode","A01");
            if(Utils.notEmpty(tsDepart)){
                depart.setParentOrgCode(tsDepart.getOrgCode());
                depart.setTSPDepart(tsDepart);
            }*/
            String localMaxCode  = getMaxLocalCode(null);
            depart.setOrgCode(YouBianCodeUtil.getNextYouBianCode(localMaxCode));
            systemService.save(depart);

            //初始化参数表数据
            List<EmkSysParamEntity> emkSysParamEntityList = systemService.findHql("from EmkSysParamEntity where departId='402880e447e99cf10147e9a03b320003'");
            EmkSysParamEntity sysParamEntity = null;
            for(EmkSysParamEntity emkSysParamEntity : emkSysParamEntityList){
                sysParamEntity = new EmkSysParamEntity();
                MyBeanUtils.copyBeanNotNull2Bean(emkSysParamEntity,sysParamEntity);
                sysParamEntity.setId(null);
                if("公司名称".equals(sysParamEntity.getParamName()) || "公司英文名".equals(sysParamEntity.getParamName())
                            || "公司地址（省）".equals(sysParamEntity.getParamName()) || "公司地址（市）".equals(sysParamEntity.getParamName()) || "公司地址（区、县）".equals(sysParamEntity.getParamName())){
                    sysParamEntity.setParamValue("");
                }
                sysParamEntity.setDepartId(depart.getId());
                sysParamEntity.setCreateDate(new Date());
                systemService.save(sysParamEntity);
            }

            //初始化产品类型数据
            List<EmkProductTypeEntity> emkProductTypeEntityList = systemService.findHql("from EmkProductTypeEntity where org_code='A01'");
            EmkProductTypeEntityA emkProductTypeEntity = null;
            for(EmkProductTypeEntity productTypeEntity : emkProductTypeEntityList){
                emkProductTypeEntity = new EmkProductTypeEntityA();
                MyBeanUtils.copyBeanNotNull2Bean(productTypeEntity,emkProductTypeEntity);
                emkProductTypeEntity.setDepartId(depart.getId());
                emkProductTypeEntity.setOrgCode(depart.getOrgCode());
                emkProductTypeEntity.setCreateDate(new Date());
                systemService.save(emkProductTypeEntity);
            }


            //初始化字典数据
            List<TSTypegroup> tsTypegroupList = systemService.findHql("from TSTypegroup where org_code='A01'");
            TSTypegroupA tsTypegroup = null;
            TSTypeA tsTypeA = null;
            for(TSTypegroup typegroup : tsTypegroupList){
                tsTypegroup = new TSTypegroupA();
                MyBeanUtils.copyBeanNotNull2Bean(typegroup,tsTypegroup);
                tsTypegroup.setId(null);
                tsTypegroup.setDepartId(depart.getId());
                tsTypegroup.setOrgCode(depart.getOrgCode());
                tsTypegroup.setCreateDate(new Date());
                systemService.save(tsTypegroup);

                if(!"zjzh".equals(tsTypegroup.getTypegroupcode())){
                    List<TSType> typeList = typegroup.getTSTypes();
                    for(TSType tp : typeList){
                        tsTypeA = new TSTypeA();
                        MyBeanUtils.copyBeanNotNull2Bean(tp,tsTypeA);
                        tsTypeA.setId(null);
                        tsTypeA.setTypegroupid(tsTypegroup.getId());
                        tsTypeA.setCreateDate(new Date());
                        systemService.save(tsTypeA);
                    }
                }


            }

            //初始化字段表数据
            List<HmCategoryEntity> hmCategoryEntityList = systemService.findHql("from HmCategoryEntity where org_code='A01'");
            HmCategoryEntity hmCategoryEntity = null;
            for(HmCategoryEntity categoryEntity : hmCategoryEntityList){
                hmCategoryEntity = new HmCategoryEntity();
                MyBeanUtils.copyBeanNotNull2Bean(categoryEntity,hmCategoryEntity);
                hmCategoryEntity.setId(null);
                hmCategoryEntity.setDepartId(depart.getId());
                hmCategoryEntity.setOrgCode(depart.getOrgCode());
                hmCategoryEntity.setCreateDate(new Date());
                systemService.save(hmCategoryEntity);
            }

            //初始短信模板表数据
            /*List<ESmsModelEntity> eSmsModelEntityList = systemService.findHql("from ESmsModelEntity where org_code='A01'");
            ESmsModelEntity eSmsModelEntity = null;
            for(ESmsModelEntity smsModelEntity : eSmsModelEntityList){
                eSmsModelEntity = new ESmsModelEntity();
                MyBeanUtils.copyBeanNotNull2Bean(smsModelEntity,eSmsModelEntity);
                eSmsModelEntity.setId(null);
                eSmsModelEntity.setDepartId(depart.getId());
                eSmsModelEntity.setOrgCode(depart.getOrgCode());
                eSmsModelEntity.setCreateDate(new Date());
                systemService.save(eSmsModelEntity);
            }*/

            //初始化角色数据
            TSRole tsRole = systemService.findUniqueByProperty(TSRole.class,"roleName","管理员");

            String roleid = tsRole.getId();
            String orgid = depart.getId();
            user.setPassword(PasswordUtil.encrypt(user.getUserName(), oConvertUtils.getString(req.getParameter("password")), PasswordUtil.getStaticSalt()));
            user.setStatus(Globals.User_Normal);
            user.setDevFlag("0");
            user.setUserType("0");
            user.setAddress(param.get("address"));
            user.setRealName(param.get("realName"));
            user.setMobilePhone(param.get("telphone"));
            user.setDeleteFlag(Globals.Delete_Normal);
            this.userService.saveOrUpdate(user, orgid.split(","), roleid.split(","));

            TSUser tuser = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
            String orgCode = tuser.getCurrentDepart().getOrgCode().substring(0,3);
            TSDepart tsDepart = systemService.findUniqueByProperty(TSDepart.class,"orgCode",orgCode);
            HmStaffEntity hmStaff = new HmStaffEntity();
            hmStaff.setDepartId(tsDepart.getId());
            hmStaff.setOrgCode(tsDepart.getOrgCode());
            tsDepart = systemService.findUniqueByProperty(TSDepart.class,"id",orgid);
            hmStaff.setDeptName(tsDepart.getDepartname());
            hmStaff.setDeptCode(tsDepart.getOrgCode());
            hmStaff.setRealName(user.getRealName());
            Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(work_no,4)),0)+1 AS signed) orderNum from hm_staff where org_code=?",orgCode);
            if(Utils.notEmpty(orderNum)){
                hmStaff.setWorkNo(String.format("%04d", Integer.valueOf(orderNum.get("orderNum").toString())));
            }
            hmStaff.setStorageId(user.getStorageId());
            hmStaff.setStorageName(user.getStorageName());
            hmStaff.setUserName(user.getUserName());
            hmStaff.setZjm(PinyinUtil.getPinYinHeadChar(hmStaff.getRealName()));
            message = "注册用户: " + user.getUserName() + "添加成功";
            logType = Globals.Log_Type_INSERT;
        }
        this.systemService.addLog(message, logType, Globals.Log_Leavel_INFO);
        j.setMsg(message);
        logger.info("[" + IpUtil.getIpAddr(req) + "][注册用户]" + message);
        return j;
    }

    private void saveUserOrgList(HttpServletRequest request, TSUser user) {
        String orgIds = oConvertUtils.getString(request.getParameter("orgIds"));

        List<TSUserOrg> userOrgList = new ArrayList();
        List<String> orgIdList = extractIdListByComma(orgIds);
        for (String orgId : orgIdList) {
            TSDepart depart = new TSDepart();
            depart.setId(orgId);

            TSUserOrg userOrg = new TSUserOrg();
            userOrg.setTsUser(user);
            userOrg.setTsDepart(depart);

            userOrgList.add(userOrg);
        }
        if (!userOrgList.isEmpty()) {
            this.systemService.batchSave(userOrgList);
        }
    }

    protected void saveRoleUser(TSUser user, String roleidstr) {
        String[] roleids = roleidstr.split(",");
        for (int i = 0; i < roleids.length; i++) {
            TSRoleUser rUser = new TSRoleUser();
            TSRole role = (TSRole) this.systemService.getEntity(TSRole.class, roleids[i]);
            rUser.setTSRole(role);
            rUser.setTSUser(user);
            this.systemService.save(rUser);
        }
    }

    @RequestMapping(params = "roles")
    public ModelAndView roles(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("system/user/users");
        String ids = oConvertUtils.getString(request.getParameter("ids"));
        mv.addObject("ids", ids);
        return mv;
    }

    @RequestMapping(params = "datagridRole")
    public void datagridRole(TSRole tsRole, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
        TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        cq.eq("orgCode",user.getCurrentDepart().getOrgCode().substring(0,3));
        HqlGenerateUtil.installHql(cq, tsRole);
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(TSUser user, HttpServletRequest req) {
        List<String> orgIdList = new ArrayList();
        TSDepart tsDepart = new TSDepart();
        if (StringUtil.isNotEmpty(user.getId())) {
            user = systemService.getEntity(TSUser.class, user.getId());

            req.setAttribute("user", user);
            idandname(req, user);
            getOrgInfos(req, user);
        }
        req.setAttribute("tsDepart", tsDepart);


        return new ModelAndView("system/user/user");
    }

    @RequestMapping(params = "addorupdateInterfaceUser")
    public ModelAndView addorupdateInterfaceUser(TSUser user, HttpServletRequest req) {
        TSDepart tsDepart = new TSDepart();
        if (StringUtil.isNotEmpty(user.getId())) {
            user = systemService.getEntity(TSUser.class, user.getId());
            req.setAttribute("user", user);
            interfaceroleidandname(req, user);
        } else {
            String roleId = req.getParameter("roleId");
            InterroleEntity role = (InterroleEntity) this.systemService.getEntity(InterroleEntity.class, roleId);
            req.setAttribute("roleId", roleId);
            req.setAttribute("roleName", role.getRoleName());
        }
        req.setAttribute("tsDepart", tsDepart);
        return new ModelAndView("system/user/interfaceUser");
    }

    public void interfaceroleidandname(HttpServletRequest req, TSUser user) {
        List<InterroleUserEntity> roleUsers = this.systemService.findByProperty(InterroleUserEntity.class, "TSUser.id", user.getId());
        String roleId = "";
        String roleName = "";
        if (roleUsers.size() > 0) {
            for (InterroleUserEntity interroleUserEntity : roleUsers) {
                roleId = roleId + interroleUserEntity.getInterroleEntity().getId() + ",";
                roleName = roleName + interroleUserEntity.getInterroleEntity().getRoleName() + ",";
            }
        }
        req.setAttribute("roleId", roleId);
        req.setAttribute("roleName", roleName);
    }

    @RequestMapping(params = "saveInterfaceUser")
    @ResponseBody
    public AjaxJson saveInterfaceUser(HttpServletRequest req, TSUser user) {
        String message = null;
        AjaxJson j = new AjaxJson();

        String roleid = oConvertUtils.getString(req.getParameter("roleid"));
        String password = oConvertUtils.getString(req.getParameter("password"));
        if (StringUtil.isNotEmpty(user.getId())) {
            TSUser users = systemService.getEntity(TSUser.class, user.getId());
            users.setEmail(user.getEmail());
            users.setOfficePhone(user.getOfficePhone());
            users.setMobilePhone(user.getMobilePhone());
            users.setDevFlag(user.getDevFlag());


            users.setRealName(user.getRealName());
            users.setStatus(Globals.User_Normal);
            users.setActivitiSync(user.getActivitiSync());

            users.setUserNameEn(user.getUserNameEn());
            users.setUserType(user.getUserType());

            users.setSex(user.getSex());
            users.setEmpNo(user.getEmpNo());
            users.setCitizenNo(user.getCitizenNo());
            users.setFax(user.getFax());
            users.setAddress(user.getAddress());
            users.setPost(user.getPost());
            users.setMemo(user.getMemo());

            this.systemService.updateEntitie(users);
            List<TSRoleUser> ru = this.systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
            this.systemService.deleteAllEntitie(ru);
            message = "用户: " + users.getUserName() + "更新成功";


            this.systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            TSUser users = systemService.findUniqueByProperty(TSUser.class, "userName", user.getUserName());
            if (users != null) {
                message = "用户: " + users.getUserName() + "已经存在";
            } else {
                user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));


                user.setStatus(Globals.User_Normal);
                user.setDeleteFlag(Globals.Delete_Normal);
                this.systemService.save(user);


                message = "用户: " + user.getUserName() + "添加成功";
                if (StringUtil.isNotEmpty(roleid)) {
                    saveInterfaceRoleUser(user, roleid);
                }
                this.systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }
        }
        j.setMsg(message);
        logger.info("[" + IpUtil.getIpAddr(req) + "][添加编辑用户]" + message);
        return j;
    }

    protected void saveInterfaceRoleUser(TSUser user, String roleidstr) {
        String[] roleids = roleidstr.split(",");
        for (int i = 0; i < roleids.length; i++) {
            InterroleUserEntity rUser = new InterroleUserEntity();
            InterroleEntity role = (InterroleEntity) this.systemService.getEntity(InterroleEntity.class, roleids[i]);
            rUser.setInterroleEntity(role);
            rUser.setTSUser(user);
            this.systemService.save(rUser);
        }
    }

    @RequestMapping(params = "userOrgSelect")
    public ModelAndView userOrgSelect(HttpServletRequest request) {
        List<TSDepart> orgList = new ArrayList();
        String userId = oConvertUtils.getString(request.getParameter("userId"));

        List<Object[]> orgArrList = this.systemService.findHql("from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", new String[]{userId});
        for (Object[] departs : orgArrList) {
            orgList.add((TSDepart) departs[0]);
        }
        request.setAttribute("orgList", orgList);

        TSUser user = systemService.getEntity(TSUser.class, userId);
        request.setAttribute("user", user);

        return new ModelAndView("system/user/userOrgSelect");
    }

    public void idandname(HttpServletRequest req, TSUser user) {
        List<TSRoleUser> roleUsers = this.systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        String roleId = "";
        String roleName = "";
        if (roleUsers.size() > 0) {
            for (TSRoleUser tRoleUser : roleUsers) {
                roleId = roleId + tRoleUser.getTSRole().getId() + ",";
                roleName = roleName + tRoleUser.getTSRole().getRoleName() + ",";
            }
        }
        req.setAttribute("id", roleId);
        req.setAttribute("roleName", roleName);
    }

    public void getOrgInfos(HttpServletRequest req, TSUser user) {
        List<TSUserOrg> tSUserOrgs = this.systemService.findByProperty(TSUserOrg.class, "tsUser.id", user.getId());
        String orgIds = "";
        String departname = "";
        if (tSUserOrgs.size() > 0) {
            for (TSUserOrg tSUserOrg : tSUserOrgs) {
                orgIds = orgIds + tSUserOrg.getTsDepart().getId() + ",";

                departname = tSUserOrg.getTsDepart().getDepartname();
            }
        }
        req.setAttribute("orgIds", orgIds);
        req.setAttribute("departname", departname);
    }

    @RequestMapping(params = "choose")
    public String choose(HttpServletRequest request) {
        List<TSRole> roles = this.systemService.loadAll(TSRole.class);
        request.setAttribute("roleList", roles);
        return "system/membership/checkuser";
    }

    @RequestMapping(params = "chooseUser")
    public String chooseUser(HttpServletRequest request) {
        String departid = request.getParameter("departid");
        String roleid = request.getParameter("roleid");
        request.setAttribute("roleid", roleid);
        request.setAttribute("departid", departid);
        return "system/membership/userlist";
    }

    @RequestMapping(params = "datagridUser")
    public void datagridUser(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String departid = request.getParameter("departid");
        String roleid = request.getParameter("roleid");
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        if (departid.length() > 0) {
            cq.eq("TDepart.departid", Integer.valueOf(oConvertUtils.getInt(departid, 0)));
            cq.add();
        }
        String userid = "";
        if (roleid.length() > 0) {
            List<TSRoleUser> roleUsers = this.systemService.findByProperty(TSRoleUser.class, "TRole.roleid", Integer.valueOf(oConvertUtils.getInt(roleid, 0)));
            if (roleUsers.size() > 0) {
                for (TSRoleUser tRoleUser : roleUsers) {
                    userid = userid + tRoleUser.getTSUser().getId() + ",";
                }
            }
            cq.in("userid", oConvertUtils.getInts(userid.split(",")));
            cq.add();
        }
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "roleDepart")
    public String roleDepart(HttpServletRequest request) {
        List<TSRole> roles = this.systemService.loadAll(TSRole.class);
        request.setAttribute("roleList", roles);
        return "system/membership/roledepart";
    }

    @RequestMapping(params = "chooseDepart")
    public ModelAndView chooseDepart(HttpServletRequest request) {
        String nodeid = request.getParameter("nodeid");
        ModelAndView modelAndView = null;
        if (nodeid.equals("role")) {
            modelAndView = new ModelAndView("system/membership/users");
        } else {
            modelAndView = new ModelAndView("system/membership/departList");
        }
        return modelAndView;
    }

    @RequestMapping(params = "datagridDepart")
    public void datagridDepart(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        String jString = request.getParameter("_dt_json");
        DataTables dataTables = new DataTables(request);
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataTables);
        String username = request.getParameter("userName");
        if (username != null) {
            cq.like("userName", username);
            cq.add();
        }
        DataTableReturn dataTableReturn = this.systemService.getDataTableReturn(cq, true);
        TagUtil.datatable(response, dataTableReturn, "id,userName,mobilePhone,TSDepart_departname");
    }

    @RequestMapping(params = "index")
    public String index() {
        return "bootstrap/main";
    }

    @RequestMapping(params = "main")
    public String main() {
        return "bootstrap/test";
    }

    @RequestMapping(params = "testpage")
    public String testpage(HttpServletRequest request) {
        return "test/test";
    }

    @RequestMapping(params = "addsign")
    public ModelAndView addsign(HttpServletRequest request) {
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        return new ModelAndView("system/user/usersign");
    }

    @RequestMapping(params = "savesign", method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public AjaxJson savesign(HttpServletRequest req) {
        String message = null;
        UploadFile uploadFile = new UploadFile(req);
        String id = uploadFile.get("id");
        TSUser user = systemService.getEntity(TSUser.class, id);
        uploadFile.setRealPath("signatureFile");
        uploadFile.setCusPath("signature");
        uploadFile.setByteField("signature");
        uploadFile.setBasePath("resources");
        uploadFile.setRename(false);
        uploadFile.setObject(user);
        AjaxJson j = new AjaxJson();
        message = user.getUserName() + "设置签名成功";
        this.systemService.uploadFile(uploadFile);
        this.systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        j.setMsg(message);

        return j;
    }

    @RequestMapping(params = "testSearch")
    public void testSearch(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        if (user.getUserName() != null) {
            cq.like("userName", user.getUserName());
        }
        if (user.getRealName() != null) {
            cq.like("realName", user.getRealName());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "changestyle")
    public String changeStyle(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUser();
        if (user == null) {
            return "login/login";
        }
        SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
        request.setAttribute("indexStyle", sysThemesEnum.getStyle());

        return "system/user/changestyle";
    }

    @RequestMapping(params = "savestyle")
    @ResponseBody
    public AjaxJson saveStyle(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(Boolean.FALSE.booleanValue());
        TSUser user = ResourceUtil.getSessionUser();
        if (user != null) {
            String indexStyle = request.getParameter("indexStyle");
            if (StringUtils.isNotEmpty(indexStyle)) {
                Cookie cookie = new Cookie("JEECGINDEXSTYLE", indexStyle);

                cookie.setMaxAge(2592000);
                response.addCookie(cookie);
                logger.debug(" ----- 首页样式: indexStyle ----- " + indexStyle);
                j.setSuccess(Boolean.TRUE.booleanValue());
                j.setMsg("样式修改成功，请刷新页面");
            }
            try {
                ClientManager.getInstance().getClient().getFunctions().clear();
            } catch (Exception localException) {
            }
        } else {
            j.setMsg("请登录后再操作");
        }
        return j;
    }

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "userController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(TSUser tsUser, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        TSUser u = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
        cq.eq("orgCode",u.getCurrentDepart().getOrgCode().substring(0,3));
        HqlGenerateUtil.installHql(cq, tsUser, request.getParameterMap());
        List<TSUser> tsUsers = this.userService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
        for (int i = 0; i < tsUsers.size(); i++) {
            TSUser user = (TSUser) tsUsers.get(i);

            this.systemService.getSession().evict(user);
            String id = user.getId();

            String queryRole = "select * from t_s_role where id in (select roleid from t_s_role_user where userid=:userid)";
            List<TSRole> roles = this.systemService.getSession().createSQLQuery(queryRole).addEntity(TSRole.class).setString("userid", id).list();
            String roleCodes = "";
            for (TSRole role : roles) {
                roleCodes = roleCodes + "," + role.getRoleCode();
            }
            user.setUserKey(roleCodes.replaceFirst(",", ""));
            String queryDept = "select * from t_s_depart where id in (select org_id from t_s_user_org where user_id=:userid)";
            List<TSDepart> departs = this.systemService.getSession().createSQLQuery(queryDept).addEntity(TSDepart.class).setString("userid", id).list();
            String departCodes = "";
            for (TSDepart depart : departs) {
                departCodes = departCodes + "," + depart.getOrgCode();
            }
            user.setDepartid(departCodes.replaceFirst(",", ""));
        }
        modelMap.put("fileName", "用户表");
        modelMap.put("entity", TSUser.class);
        modelMap.put("params", new ExportParams("用户表列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", tsUsers);
        return "jeecgExcelView";
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TSUser tsUser, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put("fileName", "用户表");
        modelMap.put("entity", TSUser.class);
        modelMap.put("params", new ExportParams("用户表列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));

        modelMap.put("data", new ArrayList());
        return "jeecgExcelView";
    }


    @RequestMapping(params = "userSelect")
    public String userSelect() {
        return "system/user/userSelect";
    }

    @RequestMapping(params = "addorupdateMyOrgUser")
    public ModelAndView addorupdateMyOrgUser(TSUser user, HttpServletRequest req) {
        List<String> orgIdList = new ArrayList();
        TSDepart tsDepart = new TSDepart();
        if (StringUtil.isNotEmpty(user.getId())) {
            user = systemService.getEntity(TSUser.class, user.getId());

            req.setAttribute("user", user);
            idandname(req, user);
            getOrgInfos(req, user);
        } else {
            String departid = oConvertUtils.getString(req.getParameter("departid"));
            TSDepart org = (TSDepart) this.systemService.getEntity(TSDepart.class, departid);
            req.setAttribute("orgIds", departid);
            req.setAttribute("departname", org.getDepartname());
        }
        req.setAttribute("tsDepart", tsDepart);
        return new ModelAndView("system/user/myOrgUser");
    }

    @RequestMapping(params = "userOwner")
    public String userOwner() {
        return "system/user/userOwner";
    }

    @RequestMapping(params = "userProjectService")
    public String userProjectService() {
        return "system/user/userProjectService";
    }

    @RequestMapping(params = "userService")
    public String userService() {
        return "system/user/userService";
    }

    @RequestMapping(params = "userSignService")
    public String userSignService() {
        return "system/user/userSignService";
    }

    @RequestMapping(params = "userServiceYg")
    public String userServiceYg() {
        return "system/user/userServiceYg";
    }

    @RequestMapping(params = "userSelect0")
    public String userSelect1() {
        return "system/user/userSelect0";
    }

    @RequestMapping(params = "userSelect2")
    public String userSelect2() {
        return "system/user/userSelect2";
    }

    @RequestMapping(params = "userSelectCw")
    public String userSelectCw() {
        return "system/user/userSelectCw";
    }
    @RequestMapping(params = "userSelectUserKey")
    public String userSelectUserKey() {
        return "system/user/userSelectUserKey";
    }
    @RequestMapping(params = "userdept0")
    public String userdept0() {
        return "system/user/userdept0";
    }

    @RequestMapping(params = "userdept1")
    public String userdept1() {
        return "system/user/userdept1";
    }

    @RequestMapping(params = "userdept2")
    public String userdept2() {
        return "system/user/userdept2";
    }
}
