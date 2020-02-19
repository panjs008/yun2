package com.process.common;


import com.emk.util.ParameterUtil;
import com.emk.util.Utils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.SendMailUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2019/8/23.
 */
@Controller
@RequestMapping("/dictController")
public class DictController {
    private static final Logger logger = Logger.getLogger(DictController.class);

    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "getDict", method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public AjaxJson getDict(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        Map param = ParameterUtil.getParamMaps(request.getParameterMap());
        try {
            List<Map<String, Object>> codeList = systemService.findForJdbc("select code,name from t_s_category where PARENT_CODE=? order by code asc", param.get("code"));
            String dataItems = "";
            try {
                for (Map map : codeList) {
                    dataItems = dataItems + map.get("code") + "," + map.get("name") + ";";
                }
                if (dataItems.indexOf(";") > 0) {
                    dataItems = dataItems.substring(0, dataItems.length() - 1);
                }
                j.setObj(dataItems);
            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionMessage(e));
        }
        return j;
    }

    @RequestMapping(params = "getColorData")
    @ResponseBody
    public AjaxJson getColorData( HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TSUser user = (TSUser) request.getSession().getAttribute("LOCAL_CLINET_USER");
        List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(remark,',',typecode) typecode,typecode code,typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where typegroupcode='color'");
        Map map = ParameterUtil.getParamMaps(request.getParameterMap());
        j.setObj(list);
        return j;
    }

}
