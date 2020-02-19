package com.process.common;


import com.emk.util.Utils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.SendMailUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2019/8/23.
 */
@Controller
@RequestMapping("/emailController")
public class EmailController {

    @Autowired
    private SystemService systemService;

    /**
     * 发送邮件
     *
     * @return
     */
    @RequestMapping(params = "sendEmail")
    @ResponseBody
    public AjaxJson doSendEmail(String title, String content, String userId,HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "发送邮件成功";
        try{
            TSUser receviceUser = systemService.get(TSUser.class,userId);
            if(Utils.notEmpty(receviceUser.getEmail())){
                SendMailUtil.sendCommonMail(receviceUser.getEmail(),title,content);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "发送邮件失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

}
