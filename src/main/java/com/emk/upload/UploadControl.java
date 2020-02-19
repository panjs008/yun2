package com.emk.upload;

import com.emk.approval.approval.entity.EmkApprovalEntity;

import com.emk.util.WebFileUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 附件上传控制层
 * @author 潘金顺
 * @date 2018-11-30
 */
@Controller
@RequestMapping("/uploadControl")
public class UploadControl extends BaseController {
	private Logger log = Logger.getLogger(getClass());

    @RequestMapping(params = "upload")
    @ResponseBody
    public AjaxJson upload(HttpServletRequest request, HttpServletResponse response, String fileDir) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "文件上传成功";
        try{

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile file = entity.getValue();// 获取上传文件对象
                File newfile = null;
                String savePath = request.getRealPath("/")+"imp/"+fileDir+"/";
                String saveName = WebFileUtils.saveFile(file, savePath);
                newfile =  new File(savePath+saveName);
                Map data = new HashedMap();
                data.put("fileName",file.getOriginalFilename());
                data.put("saveFileName",saveName);
                data.put("savePath",savePath+saveName);
                j.setObj(data);
                j.setSuccess(true);
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "文件上传失败";
            j.setSuccess(false);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }



   /* @SuppressWarnings("unchecked")
    @RequestMapping(value = "/uploadDoc", method = RequestMethod.POST)
    @ResponseBody
    public String uploadDoc(HttpServletRequest request, HttpServletResponse response, String fileDir) {
        BaseJsonResult res = new BaseJsonResult();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            File newfile = null;
            try {
                int result = 0;
                int index = 1;
                HSSFWorkbook wb = null;
                String cellValue = "";
                //String savePath = request.getRealPath("/")+"upload/"+fileDir+"/";
                String savePath = "d://upload/"+fileDir+"/";
                String saveName = WebFileUtils.saveFile(file, savePath);
                PDFConverter pdfConverter = new OpenOfficePDFConverter();
                String fileNameUrl = savePath+saveName;
                newfile =  new File(fileNameUrl);
                String pdfFileUrl = FileUtils.getFilePrefix2(fileNameUrl) + ".pdf";
                pdfConverter.convert2PDF(fileNameUrl,"");
                Map data = new HashedMap();
                data.put("fileName",file.getOriginalFilename());
                data.put("saveFileName",saveName);
                data.put("savePath",pdfFileUrl);
                res.setDatas(data);
                res.succee();
            } catch (Exception e) {
                res.fail("文件导入失败！");
                log.error(e.getMessage(),e);
                e.printStackTrace();
            }
        }
        return res.toString();
    }



    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, String fileDir) {
        BaseJsonResult res = new BaseJsonResult();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            File newfile = null;
            try {
                String savePath = "d://upload/"+fileDir+"/";
                String saveName = WebFileUtils.saveFile(file, savePath);
                String extend = FileUtils.getExtend(file.getOriginalFilename());
                PDFConverter pdfConverter = new OpenOfficePDFConverter();
                String fileNameUrl = savePath+saveName;
                newfile =  new File(fileNameUrl);
                if("txt".equals(extend)){//转odt,避免乱码
                    String path = FileUtils.getFilePrefix2(fileNameUrl) + ".odt";
                    try {
                        FileUtils.copyFile(fileNameUrl, path);
                        fileNameUrl = path;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                String pdfFileUrl = FileUtils.getFilePrefix2(fileNameUrl) + ".pdf";
                if("txt".equals(extend)){
                    pdfConverter.convert2PDF(fileNameUrl,"");
                }else{
                    pdfConverter.convert2PDFNoDelete(fileNameUrl,"");
                }
                Map data = new HashedMap();
                data.put("fileName",file.getOriginalFilename());
                data.put("saveFileName",saveName);
                data.put("savePath",pdfFileUrl);
                res.setDatas(data);
                res.succee();
            } catch (Exception e) {
                res.fail("文件导入失败！");
                log.error(e.getMessage(),e);
                e.printStackTrace();
            }
        }
        return res.toString();
    }*/
}
