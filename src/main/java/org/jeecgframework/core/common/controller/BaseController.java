package org.jeecgframework.core.common.controller;

import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.bill.materialcontract.entity.EmkMaterialContractEntity;
import com.emk.bill.proorder.entity.EmkProOrderEntity;
import com.emk.bill.proorderbarcode.entity.EmkProOrderBarcodeEntity;
import com.emk.bill.proorderbox.entity.EmkProOrderBoxEntity;
import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.bound.moutstorage.entity.EmkMOutStorageEntity;
import com.emk.caiwu.bankrecord.entity.EmkBankRecordEntity;
import com.emk.caiwu.bankrecord.entity.EmkBankRecordLogEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.produce.produce.entity.EmkProduceDetailEntity;
import com.emk.produce.produce.entity.EmkProduceEntity;
import com.emk.product.product.entity.EmkProductEntity;
import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.storage.price.entity.EmkPriceEntity;
import com.emk.storage.sampledetail.entity.EmkSampleDetailEntity;

import com.emk.storage.storage.entity.EmkStorageEntity;
import com.emk.storage.storagelog.entity.EmkStorageLogEntity;
import com.emk.util.ApprovalUtil;
import com.emk.util.DateUtil;
import com.emk.util.Utils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.tools.ant.util.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.interceptors.DateConvertEditor;
import org.jeecgframework.core.util.HttpRequest;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.SendMailUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.entity.TSSmsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;


/**
 * 基础控制器，其他控制器需集成此控制器获得initBinder自动转换的功能
 * @author  张代浩
 * 
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {
	@Autowired
	public SystemService systemService;

	@Autowired
	public ProcessEngine processEngine;
	@Autowired
	public TaskService taskService;
	@Autowired
	public HistoryService historyService;

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd hh:mm:ss");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(
//				dateFormat, true));
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

	/**
	 * 分页公共方法(非easyui)
	 * 
	 * @author Alexander
	 * @date 20131022
	 */
	public List<?> pageBaseMethod(HttpServletRequest request,
			DetachedCriteria dc, CommonService commonService, int pageRow) {
		// 当前页
		// 总条数
		// 总页数

		int currentPage = 1;

		int totalRow = 0;
		int totalPage = 0;
		// 获取当前页
		String str_currentPage = request.getParameter("str_currentPage");
		currentPage = str_currentPage == null || "".equals(str_currentPage) ? 1
				: Integer.parseInt(str_currentPage);
		// 获取每页的条数
		String str_pageRow = request.getParameter("str_pageRow");
		pageRow = str_pageRow == null || "".equals(str_pageRow) ? pageRow
				: Integer.parseInt(str_pageRow);

		// 统计的总行数
		dc.setProjection(Projections.rowCount());

		totalRow = Integer.parseInt(commonService.findByDetached(dc).get(0)
				.toString());
		totalPage = (totalRow + pageRow - 1) / pageRow;

		currentPage = currentPage < 1 ? 1 : currentPage;
		currentPage = currentPage > totalPage ? totalPage : currentPage;
		// 清空统计函数
		dc.setProjection(null);
		// dc.setResultTransformer(dc.DISTINCT_ROOT_ENTITY);
		List<?> list = commonService.pageList(dc, (currentPage - 1) * pageRow,
				pageRow);

		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageRow", pageRow);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("totalPage", totalPage);
		return list;
	}

    /**
     * 抽取由逗号分隔的主键列表
     *
     * @param ids
     *            由逗号分隔的多个主键值
     * @return 主键类表
     * @author 张国明 2014-8-21 21:57:16
     */
    protected List<String> extractIdListByComma(String ids) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.hasText(ids)) {
            for (String id : ids.split(",")) {
                if (StringUtils.hasLength(id)) {
                    result.add(id.trim());
                }
            }
        }

        return result;
    }

	//保存报价单数据
	protected  String savePrice(EmkEnquiryEntity t,String userName,String realName){
		try {
			EmkPriceEntity emkPrice = new EmkPriceEntity();
			MyBeanUtils.copyBeanNotNull2Bean(t,emkPrice);
			emkPrice.setId(null);
			emkPrice.setKdDate(DateUtil.getCurrentTimeString("yyyy-MM-dd"));
			emkPrice.setCreateBy(userName);
			emkPrice.setCreateName(realName);
			emkPrice.setCreateDate(new Date());
			emkPrice.setPirceNo(emkPrice.getSampleNo() + DateUtil.format(new Date(), "yyMMdd"));
			emkPrice.setState("0");
			emkPrice.setFormType("1");
			emkPrice.setXpNo(t.getEnquiryNo());
			systemService.save(emkPrice);
			return emkPrice.getPirceNo();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	//单个保存消息发送邮件
	protected void saveSmsAndEmailForOne(String title, String content, TSUser receviceUser, String sendUser) throws Exception {
		try {
			TSSmsEntity smsEntity = new TSSmsEntity();
			smsEntity.setEsReceiver(receviceUser.getUserName());
			smsEntity.setEsTitle(title);
			smsEntity.setEsSender(sendUser);
			smsEntity.setEsStatus("0");
			smsEntity.setEsContent(content);
			systemService.save(smsEntity);
			if(Utils.notEmpty(receviceUser.getEmail())){
                SendMailUtil.sendCommonMail(receviceUser.getEmail(),title,content);
                //HttpRequest.sendMail(title,content,receviceUser.getEmail());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//发送工厂账号邮件
	protected void saveSmsAndEmailForGc(TSUser user,String gys,String title,String content) throws Exception {
		TSDepart depart = systemService.findUniqueByProperty(TSDepart.class,"departname",gys);
		List<TSUser> userList = systemService.findHql("from TSUser where departid=?",depart.getId());
		for(TSUser u : userList){
			saveSmsAndEmailForOne(title,content,u,user.getUserName());
		}
	}
	//多个保存消息发送邮件
	protected void saveSmsAndEmailForMany(String role,String title,String content,String sendUser) throws Exception {
		try {
			List<TSUser> userList = systemService.findHql("from TSUser where userKey=?",role);
			for(TSUser tsUser : userList){
                TSSmsEntity smsEntity = new TSSmsEntity();
                smsEntity.setEsReceiver(tsUser.getUserName());
                smsEntity.setEsTitle(title);
                smsEntity.setEsSender(sendUser);
                smsEntity.setEsStatus("0");
                smsEntity.setEsContent(content);
                systemService.save(smsEntity);
                if(Utils.notEmpty(tsUser.getEmail())){
                    SendMailUtil.sendCommonMail(tsUser.getEmail(),title,content);
                    //HttpRequest.sendMail(title,content,tsUser.getEmail());
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void backProcess(String processId,String taskDef,String taskDef2,String taskDef2Name){
		systemService.executeSql("delete from act_hi_actinst  where proc_inst_id_=? and act_id_=? ",processId,taskDef);
		systemService.executeSql("delete from act_hi_taskinst where proc_inst_id_=? and task_def_key_=? ",processId,taskDef);
		systemService.executeSql("update act_ru_task set name_=?,task_def_key_=? where proc_inst_id_=? ",taskDef2Name,taskDef2,processId);
		systemService.executeSql("update act_ru_execution set rev_=(rev_-1),act_id_=? where id_=?", taskDef2,processId);
	}

	protected void saveApprvoalDetail(EmkApprovalDetailEntity approvalDetailEntity, String bpmName, String bpmNode, int approvalStatus, String approvalAdvice){
		approvalDetailEntity.setBpmName(bpmName);
		approvalDetailEntity.setBpmNode(bpmNode);
		approvalDetailEntity.setApproveStatus(approvalStatus);
		approvalDetailEntity.setApproveAdvice(approvalAdvice);
	}

	//保存订单明细数据
	protected void saveOrderData(EmkProOrderEntity t,Map<String, String> map){
		String dataRows = (String) map.get("orderMxListIDSR");
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_pro_order_detail where PRO_ORDER_ID=?))", t.getId());
			systemService.executeSql("delete from emk_pro_order_detail where PRO_ORDER_ID=?",t.getId());
			int rows = Integer.parseInt(dataRows);
			EmkProOrderDetailEntity proOrderDetailEntity = null;
			EmkSizeTotalEntity emkSizeTotalEntity = null;
			for (int i = 0; i < rows; i++) {
				if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
					proOrderDetailEntity = new EmkProOrderDetailEntity();
					proOrderDetailEntity.setProOrderId(t.getId());
					/*String[] colorArr = map.get("orderMxList["+i+"].color").split(",");
					proOrderDetailEntity.setColor(colorArr[1]);*/

					//proOrderDetailEntity.setSumTotal(map.get("orderMxList["+i+"].sumTotal00").toString());

					systemService.save(proOrderDetailEntity);

					if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
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

		}
		dataRows = map.get("orderMxListID");
		//保存原料面料数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_sample_detail where sample_id = ? and type=0",t.getId());
			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkSampleDetailEntity emkSampleDetailEntity = new EmkSampleDetailEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].proZnName"))) {
					emkSampleDetailEntity.setProZnName(map.get("orderMxList["+i+"].proZnName"));
					emkSampleDetailEntity.setProNum(map.get("orderMxList["+i+"].proNum"));
					emkSampleDetailEntity.setGysCode(map.get("orderMxList["+i+"].gysCode"));
					emkSampleDetailEntity.setBrand(map.get("orderMxList["+i+"].brand"));
					emkSampleDetailEntity.setPrecent(map.get("orderMxList["+i+"].precent"));

					if(Utils.notEmpty(map.get("orderMxList["+i+"].yongliang"))){
						emkSampleDetailEntity.setYongliang(Double.parseDouble(map.get("orderMxList["+i+"].yongliang").toString()));
					}
					emkSampleDetailEntity.setSignPrice(map.get("orderMxList["+i+"].signPrice"));
					emkSampleDetailEntity.setSignTotal(map.get("orderMxList["+i+"].signTotal"));

					emkSampleDetailEntity.setUnit(map.get("orderMxList["+i+"].unit"));
					if(Utils.notEmpty(map.get("orderMxList["+i+"].sunhaoPrecent"))){
						emkSampleDetailEntity.setSunhaoPrecent(Double.parseDouble(map.get("orderMxList["+i+"].sunhaoPrecent").toString()));
					}
//					emkSampleDetailEntity.setSumYongliang(map.get("orderMxList["+i+"].sumYongliang").toString());
					if(Utils.notEmpty(map.get("orderMxList["+i+"].chengben"))){
						emkSampleDetailEntity.setChengben(Double.parseDouble(map.get("orderMxList["+i+"].chengben").toString()));
					}
					emkSampleDetailEntity.setPosition(map.get("orderMxList["+i+"].position").toString());
					emkSampleDetailEntity.setRkState(map.get("orderMxList["+i+"].rkState").toString());

					emkSampleDetailEntity.setSampleId(t.getId());
					emkSampleDetailEntity.setType("0");
					systemService.save(emkSampleDetailEntity);
				}
			}
		}
		dataRows = map.get("orderMxListID2");
		//保存缝制辅料数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_sample_detail where sample_id = ? and type=1",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkSampleDetailEntity emkSampleDetailEntity = new EmkSampleDetailEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].bproZnName"))) {
					emkSampleDetailEntity.setProZnName(map.get("orderMxList["+i+"].bproZnName"));
					emkSampleDetailEntity.setProNum(map.get("orderMxList["+i+"].bproNum"));
					emkSampleDetailEntity.setGysCode(map.get("orderMxList["+i+"].bgysCode"));
					emkSampleDetailEntity.setBrand(map.get("orderMxList["+i+"].bbrand"));
					if(Utils.notEmpty(map.get("orderMxList["+i+"].byongliang"))){
						emkSampleDetailEntity.setYongliang(Double.parseDouble(map.get("orderMxList["+i+"].byongliang").toString()));
					}
					emkSampleDetailEntity.setSignPrice(map.get("orderMxList["+i+"].bsignPrice"));
					emkSampleDetailEntity.setSignTotal(map.get("orderMxList["+i+"].bsignTotal"));

					emkSampleDetailEntity.setUnit(map.get("orderMxList["+i+"].bunit"));

					if(Utils.notEmpty(map.get("orderMxList["+i+"].bsunhaoPrecent"))){
						emkSampleDetailEntity.setSunhaoPrecent(Double.parseDouble(map.get("orderMxList["+i+"].bsunhaoPrecent").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].bchengben"))){
						emkSampleDetailEntity.setChengben(Double.parseDouble(map.get("orderMxList["+i+"].bchengben").toString()));
					}
					emkSampleDetailEntity.setPosition(map.get("orderMxList["+i+"].bposition").toString());
					emkSampleDetailEntity.setRkState(map.get("orderMxList["+i+"].brkState").toString());
					emkSampleDetailEntity.setSampleId(t.getId());
					emkSampleDetailEntity.setType("1");
					systemService.save(emkSampleDetailEntity);
				}
			}
		}
		dataRows = map.get("orderMxListID3");
		//保存包装辅料数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_sample_detail where sample_id = ? and type=2",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkSampleDetailEntity emkSampleDetailEntity = new EmkSampleDetailEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].cproZnName"))) {
					emkSampleDetailEntity.setProZnName(map.get("orderMxList["+i+"].cproZnName"));
					emkSampleDetailEntity.setProNum(map.get("orderMxList["+i+"].cproNum"));
					emkSampleDetailEntity.setGysCode(map.get("orderMxList["+i+"].cgysCode"));
					emkSampleDetailEntity.setBrand(map.get("orderMxList["+i+"].cbrand"));
					if(Utils.notEmpty(map.get("orderMxList["+i+"].cyongliang"))){
						emkSampleDetailEntity.setYongliang(Double.parseDouble(map.get("orderMxList["+i+"].cyongliang").toString()));
					}
					emkSampleDetailEntity.setSignPrice(map.get("orderMxList["+i+"].csignPrice"));
					emkSampleDetailEntity.setSignTotal(map.get("orderMxList["+i+"].csignTotal"));

					emkSampleDetailEntity.setUnit(map.get("orderMxList["+i+"].cunit"));

					if(Utils.notEmpty(map.get("orderMxList["+i+"].csunhaoPrecent"))){
						emkSampleDetailEntity.setSunhaoPrecent(Double.parseDouble(map.get("orderMxList["+i+"].csunhaoPrecent").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].cchengben"))){
						emkSampleDetailEntity.setChengben(Double.parseDouble(map.get("orderMxList["+i+"].cchengben").toString()));
					}
					emkSampleDetailEntity.setPosition(map.get("orderMxList["+i+"].cposition").toString());
					emkSampleDetailEntity.setRkState(map.get("orderMxList["+i+"].crkState").toString());
					emkSampleDetailEntity.setSampleId(t.getId());
					emkSampleDetailEntity.setType("2");
					systemService.save(emkSampleDetailEntity);
				}
			}
		}

		dataRows = map.get("orderMxListIDBarCode");
		//保存洗水标条码数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_pro_order_barcode where order_id = ? and type=0",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkProOrderBarcodeEntity emkProOrderBarcodeEntity = new EmkProOrderBarcodeEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].acolor00"))) {
					emkProOrderBarcodeEntity.setColor(map.get("orderMxList["+i+"].acolor00"));
					emkProOrderBarcodeEntity.setSize(map.get("orderMxList["+i+"].asize00"));
					emkProOrderBarcodeEntity.setCode(map.get("orderMxList["+i+"].acode00"));
					emkProOrderBarcodeEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].asignTotal00").toString()));
					emkProOrderBarcodeEntity.setOrderId(t.getId());
					emkProOrderBarcodeEntity.setType("0");
					systemService.save(emkProOrderBarcodeEntity);
				}
			}
		}
		dataRows = map.get("orderMxListIDBarCode");
		//保存胶袋贴条码数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_pro_order_barcode where order_id = ? and type=1",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkProOrderBarcodeEntity emkProOrderBarcodeEntity = new EmkProOrderBarcodeEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].bcolor00"))) {
					emkProOrderBarcodeEntity.setColor(map.get("orderMxList["+i+"].bcolor00"));
					emkProOrderBarcodeEntity.setSize(map.get("orderMxList["+i+"].bsize00"));
					emkProOrderBarcodeEntity.setCode(map.get("orderMxList["+i+"].bcode00"));
					emkProOrderBarcodeEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].bsignTotal00").toString()));
					emkProOrderBarcodeEntity.setOrderId(t.getId());
					emkProOrderBarcodeEntity.setType("1");
					systemService.save(emkProOrderBarcodeEntity);
				}
			}
		}
		dataRows = map.get("orderMxListIDBarCode");
		//保存箱贴条码数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_pro_order_barcode where order_id = ? and type=2",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkProOrderBarcodeEntity emkProOrderBarcodeEntity = new EmkProOrderBarcodeEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].ccolor00"))) {
					emkProOrderBarcodeEntity.setColor(map.get("orderMxList["+i+"].ccolor00"));
					emkProOrderBarcodeEntity.setSize(map.get("orderMxList["+i+"].csize00"));
					emkProOrderBarcodeEntity.setCode(map.get("orderMxList["+i+"].ccode00"));
					emkProOrderBarcodeEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].csignTotal00").toString()));
					emkProOrderBarcodeEntity.setOrderId(t.getId());
					emkProOrderBarcodeEntity.setType("2");
					systemService.save(emkProOrderBarcodeEntity);
				}
			}
		}

		dataRows = map.get("orderMxListIDBox");
		//保存单色单码装数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_pro_order_box where order_id = ? and box_type=0",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkProOrderBoxEntity emkProOrderBoxEntity = new EmkProOrderBoxEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].acolorName00"))) {
					emkProOrderBoxEntity.setColorName(map.get("orderMxList["+i+"].acolorName00"));
					emkProOrderBoxEntity.setSize(map.get("orderMxList["+i+"].asizeBox00"));
					if(Utils.notEmpty(map.get("orderMxList["+i+"].ainboxTotal00"))){
						emkProOrderBoxEntity.setInboxTotal(Integer.parseInt(map.get("orderMxList["+i+"].ainboxTotal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].atotalBox00"))){
						emkProOrderBoxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].atotalBox00").toString()));
					}

					if(Utils.notEmpty(map.get("orderMxList["+i+"].alongVal00"))){
						emkProOrderBoxEntity.setLongVal(Double.parseDouble(map.get("orderMxList["+i+"].alongVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].awidthVal00"))){
						emkProOrderBoxEntity.setWidthVal(Double.parseDouble(map.get("orderMxList["+i+"].awidthVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].aheightVal00"))){
						emkProOrderBoxEntity.setHeightVal(Double.parseDouble(map.get("orderMxList["+i+"].aheightVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].aboxWeightMao00"))){
						emkProOrderBoxEntity.setBoxWeightMao(Double.parseDouble(map.get("orderMxList["+i+"].aboxWeightMao00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].aboxWeightJz00"))){
						emkProOrderBoxEntity.setBoxWeightJz(Double.parseDouble(map.get("orderMxList["+i+"].aboxWeightJz00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].aboxVolume00"))){
						emkProOrderBoxEntity.setBoxVolume(Double.parseDouble(map.get("orderMxList["+i+"].aboxVolume00").toString()));
					}

					emkProOrderBoxEntity.setBoxType("0");
					emkProOrderBoxEntity.setOrderId(t.getId());

					systemService.save(emkProOrderBoxEntity);
				}
			}
		}

		dataRows = map.get("orderMxListIDBox1");
		//保存单色混码装数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_pro_order_box where order_id = ? and box_type=1",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkProOrderBoxEntity emkProOrderBoxEntity = new EmkProOrderBoxEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].bcolorName00"))) {
					emkProOrderBoxEntity.setColorName(map.get("orderMxList["+i+"].bcolorName00"));
					emkProOrderBoxEntity.setSize(map.get("orderMxList["+i+"].bsizeBox00"));
					if(Utils.notEmpty(map.get("orderMxList["+i+"].binboxTotal00"))){
						emkProOrderBoxEntity.setInboxTotal(Integer.parseInt(map.get("orderMxList["+i+"].binboxTotal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].btotalBox00"))){
						emkProOrderBoxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].btotalBox00").toString()));
					}

					if(Utils.notEmpty(map.get("orderMxList["+i+"].blongVal00"))){
						emkProOrderBoxEntity.setLongVal(Double.parseDouble(map.get("orderMxList["+i+"].blongVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].bwidthVal00"))){
						emkProOrderBoxEntity.setWidthVal(Double.parseDouble(map.get("orderMxList["+i+"].bwidthVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].bheightVal00"))){
						emkProOrderBoxEntity.setHeightVal(Double.parseDouble(map.get("orderMxList["+i+"].bheightVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].bboxWeightMao00"))){
						emkProOrderBoxEntity.setBoxWeightMao(Double.parseDouble(map.get("orderMxList["+i+"].bboxWeightMao00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].bboxWeightJz00"))){
						emkProOrderBoxEntity.setBoxWeightJz(Double.parseDouble(map.get("orderMxList["+i+"].bboxWeightJz00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].bboxVolume00"))){
						emkProOrderBoxEntity.setBoxVolume(Double.parseDouble(map.get("orderMxList["+i+"].bboxVolume00").toString()));
					}

					emkProOrderBoxEntity.setBoxType("1");
					emkProOrderBoxEntity.setOrderId(t.getId());

					systemService.save(emkProOrderBoxEntity);
				}
			}
		}

		dataRows = map.get("orderMxListIDBox2");
		//保存混色单码装数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_pro_order_box where order_id = ? and box_type=2",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkProOrderBoxEntity emkProOrderBoxEntity = new EmkProOrderBoxEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].ccolorName00"))) {
					emkProOrderBoxEntity.setColorName(map.get("orderMxList["+i+"].ccolorName00"));
					emkProOrderBoxEntity.setSize(map.get("orderMxList["+i+"].csizeBox00"));
					if(Utils.notEmpty(map.get("orderMxList["+i+"].cinboxTotal00"))){
						emkProOrderBoxEntity.setInboxTotal(Integer.parseInt(map.get("orderMxList["+i+"].cinboxTotal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].ctotalBox00"))){
						emkProOrderBoxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].ctotalBox00").toString()));
					}

					if(Utils.notEmpty(map.get("orderMxList["+i+"].clongVal00"))){
						emkProOrderBoxEntity.setLongVal(Double.parseDouble(map.get("orderMxList["+i+"].clongVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].cwidthVal00"))){
						emkProOrderBoxEntity.setWidthVal(Double.parseDouble(map.get("orderMxList["+i+"].cwidthVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].cheightVal00"))){
						emkProOrderBoxEntity.setHeightVal(Double.parseDouble(map.get("orderMxList["+i+"].cheightVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].cboxWeightMao00"))){
						emkProOrderBoxEntity.setBoxWeightMao(Double.parseDouble(map.get("orderMxList["+i+"].cboxWeightMao00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].cboxWeightJz00"))){
						emkProOrderBoxEntity.setBoxWeightJz(Double.parseDouble(map.get("orderMxList["+i+"].cboxWeightJz00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].cboxVolume00"))){
						emkProOrderBoxEntity.setBoxVolume(Double.parseDouble(map.get("orderMxList["+i+"].cboxVolume00").toString()));
					}

					emkProOrderBoxEntity.setBoxType("2");
					emkProOrderBoxEntity.setOrderId(t.getId());

					systemService.save(emkProOrderBoxEntity);
				}
			}
		}

		dataRows = map.get("orderMxListIDBox4");
		//保存混色混码装数据
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_pro_order_box where order_id = ? and box_type=3",t.getId());

			int rows = Integer.parseInt(dataRows);
			for (int i = 0; i < rows; i++) {
				EmkProOrderBoxEntity emkProOrderBoxEntity = new EmkProOrderBoxEntity();
				if (Utils.notEmpty(map.get("orderMxList["+i+"].ecolorName00"))) {
					emkProOrderBoxEntity.setColorName(map.get("orderMxList["+i+"].ecolorName00"));
					emkProOrderBoxEntity.setSize(map.get("orderMxList["+i+"].esizeBox00"));
					if(Utils.notEmpty(map.get("orderMxList["+i+"].einboxTotal00"))){
						emkProOrderBoxEntity.setInboxTotal(Integer.parseInt(map.get("orderMxList["+i+"].einboxTotal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].etotalBox00"))){
						emkProOrderBoxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].etotalBox00").toString()));
					}

					if(Utils.notEmpty(map.get("orderMxList["+i+"].elongVal00"))){
						emkProOrderBoxEntity.setLongVal(Double.parseDouble(map.get("orderMxList["+i+"].elongVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].ewidthVal00"))){
						emkProOrderBoxEntity.setWidthVal(Double.parseDouble(map.get("orderMxList["+i+"].ewidthVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].eheightVal00"))){
						emkProOrderBoxEntity.setHeightVal(Double.parseDouble(map.get("orderMxList["+i+"].eheightVal00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].eboxWeightMao00"))){
						emkProOrderBoxEntity.setBoxWeightMao(Double.parseDouble(map.get("orderMxList["+i+"].eboxWeightMao00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].eboxWeightJz00"))){
						emkProOrderBoxEntity.setBoxWeightJz(Double.parseDouble(map.get("orderMxList["+i+"].eboxWeightJz00").toString()));
					}
					if(Utils.notEmpty(map.get("orderMxList["+i+"].eboxVolume00"))){
						emkProOrderBoxEntity.setBoxVolume(Double.parseDouble(map.get("orderMxList["+i+"].eboxVolume00").toString()));
					}

					emkProOrderBoxEntity.setBoxType("3");
					emkProOrderBoxEntity.setOrderId(t.getId());

					systemService.save(emkProOrderBoxEntity);
				}
			}
		}
	}


	//保存采购进度表数据
	protected void saveProduce(EmkProduceEntity t, Map<String, String> map){
		//保存明细数据
		String dataRows = (String) map.get("orderMxListIDSR");
		if (Utils.notEmpty(dataRows)) {
			systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_produce_detail_schedule where p_id=?))", t.getId());
			systemService.executeSql("delete from emk_produce_detail_schedule where p_id = ? ",t.getId());

			int rows = Integer.parseInt(dataRows);
			EmkProduceDetailEntity emkProduceDetailEntity = null;
			EmkSizeTotalEntity emkSizeTotalEntity = null;

			for (int i = 0; i < rows; i++) {
				if (Utils.notEmpty(map.get("orderMxList["+i+"].orderNo00"))){
					emkProduceDetailEntity = new EmkProduceDetailEntity();
					emkProduceDetailEntity.setpId(t.getId());
					emkProduceDetailEntity.setOrderNo(map.get("orderMxList["+i+"].orderNo00"));
					emkProduceDetailEntity.setCusNum(map.get("orderMxList["+i+"].cusNum00"));
					emkProduceDetailEntity.setProduceHtNum(map.get("orderMxList["+i+"].produceHtNum00"));
					emkProduceDetailEntity.setGysCode(map.get("orderMxList["+i+"].gysCode00"));
					emkProduceDetailEntity.setGyzl(map.get("orderMxList["+i+"].gyzl00"));
					emkProduceDetailEntity.setProTypeName(map.get("orderMxList["+i+"].proTypeName00"));
					emkProduceDetailEntity.setSampleNo(map.get("orderMxList["+i+"].sampleNo00"));
					emkProduceDetailEntity.setSampleNoDesc(map.get("orderMxList["+i+"].sampleDesc00"));
//					emkProduceDetailEntity.setColor(map.get("orderMxList["+i+"].color"));
					if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
						String[] colorArr = map.get("orderMxList["+i+"].acolorName00").split(",");
						emkProduceDetailEntity.setColor(colorArr[1]);
					}
					emkProduceDetailEntity.setCustomSampleUrl(map.get("orderMxList["+i+"].customSampleUrl"));
					emkProduceDetailEntity.setCustomSample(map.get("orderMxList["+i+"].customSample"));


					if(Utils.notEmpty(map.get("orderMxList["+i+"].signTotal00"))){
						emkProduceDetailEntity.setSumTotal(Integer.parseInt(map.get("orderMxList["+i+"].signTotal00")));
					}
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
					emkProduceDetailEntity.setLevalZcrq(map.get("orderMxList["+i+"].levalZcrq00"));

					emkProduceDetailEntity.setWcrq(map.get("orderMxList["+i+"].wcrq00"));
					emkProduceDetailEntity.setWcrqDate(map.get("orderMxList["+i+"].wcrqDate00"));
					emkProduceDetailEntity.setLevalWcrq(map.get("orderMxList["+i+"].levalWcrq00"));

					emkProduceDetailEntity.setOutDate(map.get("orderMxList["+i+"].outDate00"));
					emkProduceDetailEntity.setSortDesc(String.valueOf(i+1));

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
	}

	protected List<EmkSampleDetailEntity> saveSampleRequiredDetail(List<EmkSampleDetailEntity> emkSampleDetailEntityList){
		List<EmkSampleDetailEntity> newEmkSampleDetailEntityList = new ArrayList<>();
		EmkStorageLogEntity storageLogEntity = null;
		EmkSampleDetailEntity emkSampleDetailEntity = null;
		for(EmkSampleDetailEntity sampleDetailEntity : emkSampleDetailEntityList){
			EmkStorageEntity storageEntity = systemService.findUniqueByProperty(EmkStorageEntity.class,"proNum",sampleDetailEntity.getProNum());
			if(storageEntity != null){
				storageLogEntity = new EmkStorageLogEntity();
				storageLogEntity.setTotal(sampleDetailEntity.getSignTotal());
				storageLogEntity.setProZnName(sampleDetailEntity.getProZnName());
				storageLogEntity.setRkNo("3");

				emkSampleDetailEntity = new EmkSampleDetailEntity();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(sampleDetailEntity,emkSampleDetailEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}

				systemService.saveOrUpdate(storageLogEntity);
				systemService.saveOrUpdate(storageEntity);
			}else{
				newEmkSampleDetailEntityList.add(sampleDetailEntity);
			}
		}
		return  newEmkSampleDetailEntityList;
	}

	protected List<Map<String, Object>>  findProcessList(String id){
		StringBuilder sql = new StringBuilder();
		sql.append("select DATE_FORMAT(IFNULL(t1.START_TIME_,a.commit_time),'%Y-%m-%d %H:%i:%s') startTime,DATE_FORMAT(t1.END_TIME_,'%Y-%m-%d %H:%i:%s') endTime,b.* from emk_approval a \n" +
				" left join emk_approval_detail b on b.approval_id = a.id \n" +
				" left join act_hi_taskinst t1 on t1.assignee_= a.form_id and t1.task_def_key_=b.bpm_node ");

		sql.append(" where a.form_id=? ");
		sql.append(" order by b.approve_date asc ");
		List<Map<String, Object>> processList = systemService.findForJdbc(sql.toString(),id);
		return processList;
	}

	protected void saveBankRecord(String money,EmkBankRecordEntity t, EmkBankRecordLogEntity emkBankRecordLogEntity){
		MyBeanUtils.copyBeanNotNull2Bean(t, emkBankRecordLogEntity);
		emkBankRecordLogEntity.setId(null);
		emkBankRecordLogEntity.setPreMoney(t.getBalance());
		double m = Double.parseDouble(t.getBalance())-Double.parseDouble(money);
		emkBankRecordLogEntity.setNextMoney(String.valueOf(m));
		emkBankRecordLogEntity.setMoney(money);

	}

	protected void saveProNum(EmkProductEntity emkProductEntity,String orgCode){
		Map orderNum2 = systemService.findOneForJdbc("select CAST(ifnull(max(right(pro_num, 4)),0)+1 AS signed) orderNum from emk_product where org_code=? and length(pro_num)=4 and (pro_num REGEXP '[^0-9.]')=0",orgCode);
		if(Utils.notEmpty(orderNum2.get("orderNum"))){
			emkProductEntity.setProNum(String.format("%04d", Integer.valueOf(orderNum2.get("orderNum").toString())));
		}else{
			orderNum2 = systemService.findOneForJdbc("select CAST(ifnull(max(pro_num),0)+1 AS signed) orderNum from emk_product where org_code=? and length(pro_num)>4 and (pro_num REGEXP '[^0-9.]')=0",orgCode);
			if(Utils.notEmpty(orderNum2.get("orderNum"))){
				emkProductEntity.setProNum(orderNum2.get("orderNum").toString());
			}else{
				emkProductEntity.setProNum("0001");
			}
		}
	}

	protected void updateKccb(EmkStorageEntity storageEntity,String orgCode){
		if(Utils.notEmpty(storageEntity)){
			DecimalFormat df = new DecimalFormat("0.00");
			String sql = "select ifnull(truncate(sum(a01a09a12*a01a09a02),2),0) zj,ifnull(sum(a01a09a02),0) zs from emk_m_in_storage_detail " +
					"	where org_code='"+orgCode+"' and storage_id='"+storageEntity.getStorageId()+"'";

			if(Utils.notEmpty(storageEntity.getA01a09a07())){
				sql += " and a01a09a07='"+storageEntity.getA01a09a07()+"'";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a08())){
				sql += " and a01a09a08='"+storageEntity.getA01a09a08()+"'";
			}else{
				//sql += " and flag=1";
				sql += " and (a01a09a08 is null or a01a09a08 = '')";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a05())){
				sql += " and a01a09a05='"+storageEntity.getA01a09a05()+"'";
			}else{
				sql += " and (a01a09a05 is null or a01a09a05 = '')";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a04())){
				sql += " and a01a09a04='"+storageEntity.getA01a09a04()+"'";
			}else{
				sql += " and (a01a09a04 is null or a01a09a04 = '')";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a06())){
				sql += " and a01a09a06='"+storageEntity.getA01a09a06()+"'";
			}else{
				sql += " and (a01a09a06 is null or a01a09a06 = '')";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a24())){
				sql += " and a01a09a24='"+storageEntity.getA01a09a24()+"'";
			}else{
				sql += " and (a01a09a24 is null or a01a09a24 = '')";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a14())){
				sql += " and a01a09a14='"+storageEntity.getA01a09a14()+"'";
			}else{
				sql += " and (a01a09a14 is null or a01a09a14 = '')";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a22())){
				sql += " and a01a09a22='"+storageEntity.getA01a09a22()+"'";
			}else{
				sql += " and (a01a09a22 is null or a01a09a22 = '')";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a23())){
				sql += " and a01a09a23='"+storageEntity.getA01a09a23()+"'";
			}else{
				sql += " and (a01a09a23 is null or a01a09a23 = '')";
			}
			Map<String, Object> rkcb = systemService.findOneForJdbc(sql);

			sql = "select ifnull(TRUNCATE(sum(price * total), 2),0) zj,ifnull(sum(total), 0) zs  from emk_product_storage where type=1 and org_code='"+orgCode+"'" +
					"	and storage_id='"+storageEntity.getStorageId()+"' ";
			if(Utils.notEmpty(storageEntity.getA01a09a05())){
				sql += " and position_id='"+storageEntity.getA01a09a05()+"'";
			}else{
				sql += " and position_id is null";
			}
			if(Utils.notEmpty(storageEntity.getId())){
				sql += " and stro_id='"+storageEntity.getId()+"'";
			}else{
				sql += " and stro_id is null";
			}

			Map<String, Object> procb = systemService.findOneForJdbc(sql);

			sql = "select ifnull(truncate(sum(price*total),2),0) zj,ifnull(sum(total),0) zs from emk_storage_connact " +
					"	where org_code='"+orgCode+"' and in_storage_id='"+storageEntity.getStorageId()+"'";
			if(Utils.notEmpty(storageEntity.getA01a09a08())){
				sql += " and pro_num='"+storageEntity.getA01a09a08()+"'";
			}else{
				sql += " and pro_num ='' ";
			}
			/*if(Utils.notEmpty(storageEntity.getA01a09a05())){
				sql += " and a01a09a05='"+storageEntity.getA01a09a05()+"'";
			}else{
				sql += " and a01a09a05 is null";
			}*/
			if(Utils.notEmpty(storageEntity.getA01a09a11())){
				sql += " and unit='"+storageEntity.getA01a09a11()+"'";
			}else{
				sql += " and unit ='' ";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a10())){
				sql += " and brand='"+storageEntity.getA01a09a10()+"'";
			}else{
				sql += " and brand ='' ";
			}
			/*if(Utils.notEmpty(storageEntity.getA01a09a04())){
				sql += " and a01a09a04='"+storageEntity.getA01a09a04()+"'";
			}else{
				sql += " and a01a09a04 is null";
			}*/
			if(Utils.notEmpty(storageEntity.getA01a09a06())){
				sql += " and a01a01a01='"+storageEntity.getA01a09a06()+"'";
			}else{
				sql += " and a01a01a01 ='' ";
			}
			sql += " and type=0 ";
			Map<String, Object> zhcb1 = systemService.findOneForJdbc(sql);


			sql = "select ifnull(truncate(sum(a01a09a12*a01a09a02),2),0) zj,ifnull(sum(a01a09a02),0) zs from emk_storage_connact_detail " +
					"	where org_code='"+orgCode+"' and storage_id='"+storageEntity.getStorageId()+"'";
			if(Utils.notEmpty(storageEntity.getA01a09a08())){
				sql += " and a01a09a08='"+storageEntity.getA01a09a08()+"'";
			}else{
				sql += " and a01a09a08 is null";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a05())){
				sql += " and a01a09a05='"+storageEntity.getA01a09a05()+"'";
			}else{
				sql += " and a01a09a05 is null";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a11())){
				sql += " and a01a09a11='"+storageEntity.getA01a09a11()+"'";
			}else{
				sql += " and a01a09a11 is null";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a10())){
				sql += " and a01a09a10='"+storageEntity.getA01a09a10()+"'";
			}else{
				sql += " and a01a09a10 is null";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a04())){
				sql += " and a01a09a04='"+storageEntity.getA01a09a04()+"'";
			}else{
				sql += " and a01a09a04 is null";
			}
			if(Utils.notEmpty(storageEntity.getA01a09a06())){
				sql += " and a01a09a06='"+storageEntity.getA01a09a06()+"'";
			}else{
				sql += " and a01a09a06 is null";
			}
			sql += " and type=0 ";
			Map<String, Object> zhcb2 = systemService.findOneForJdbc(sql);

			double zj = 0,zs = 0 , kccb = 0;
			if(Utils.notEmpty(rkcb)){
				zj += Double.parseDouble(rkcb.get("zj").toString());
				zs += Double.parseDouble(rkcb.get("zs").toString());
			}
			if(Utils.notEmpty(procb)){
				zj += Double.parseDouble(procb.get("zj").toString());
				zs += Double.parseDouble(procb.get("zs").toString());
			}
			if(Utils.notEmpty(zhcb1)){
				zj += Double.parseDouble(zhcb1.get("zj").toString());
				zs += Double.parseDouble(zhcb1.get("zs").toString());
			}
			if(Utils.notEmpty(zhcb2)){
				zj += Double.parseDouble(zhcb2.get("zj").toString());
				zs += Double.parseDouble(zhcb2.get("zs").toString());
			}
			if(zs > 0 ){
				kccb = zj/zs;
				systemService.executeSql("update emk_storage set a01a09a13=?,a01a09a03=truncate(a01a09a02*ifnull(a01a09a13,0),2) where id=? ",df.format(kccb),storageEntity.getId());
			}

		}
	}
}
