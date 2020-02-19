/*
package com.emk.bound.muse.controller;

import com.csvreader.CsvReader;
import com.gxedu.customdetail.entity.SCustomDetailEntity;
import com.gxedu.khgl.entity.KhglEntity;
import com.gxedu.saledetail.entity.SSaleDetailEntity;
import com.gxedu.util.ParameterUtil;
import com.gxedu.util.WebFileUtils;
import com.gxedu.util.excel.ExcelUtil;
import com.gxedu.xhd.entity.XhdEntity;
import com.gxedu.xhd.service.XhdServiceI;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDocument;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * @Title: Controller  
 * @Description: 销货单
 * @author onlineGenerator
 * @date 2017-10-24 14:48:50
 * @version V1.0   
 *
 *//*

@Controller
@RequestMapping("/xhdController")
public class XhdController extends BaseController {
	*/
/**
	 * Logger for this class
	 *//*

	private static final Logger logger = Logger.getLogger(XhdController.class);

	@Autowired
	private XhdServiceI xhdService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	*/
/**
	 * 销货单查询列表 页面跳转
	 * 
	 * @return
	 *//*

	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/gxedu/xhd/xhdList");
	}

	*/
/**
	 * 销货单费用结算明细表列表 页面跳转
	 *
	 * @return
	 *//*

	@RequestMapping(params = "jsmxlist1")
	public ModelAndView jsmxlist(HttpServletRequest request) {
		return new ModelAndView("com/gxedu/xhd/xhdList-jsmx1");
	}

	@RequestMapping(params = "jsmxlist2")
	public ModelAndView jsmxlist2(HttpServletRequest request) {
		return new ModelAndView("com/gxedu/xhd/xhdList-jsmx2");
	}

	*/
/**
	 * 销货单草稿列表 页面跳转
	 *
	 * @return
	 *//*

	@RequestMapping(params = "list2")
	public ModelAndView list2(HttpServletRequest request) {
		return new ModelAndView("com/gxedu/xhd/xhdList2");
	}

	@RequestMapping(params = "sellMxList")
	public ModelAndView sellMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		List<SSaleDetailEntity> saleDetailList = systemService.findHql("from SSaleDetailEntity where saleId=?",map.get("sellId").toString());
		request.setAttribute("saleDetailList",saleDetailList);
		return new ModelAndView("com/gxedu/xhd/sCustomDetailList");
	}

	@RequestMapping(params = "uploadV")
	public ModelAndView uploadView(HttpServletRequest request) {
		return new ModelAndView("com/gxedu/xhd/uploadV");
	}
	*/
/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 *//*

	@RequestMapping(params = "jsmxdatagrid")
	public void jsmxdatagrid(XhdEntity xhd, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		try{
				String sql = "",countsql = "";
			if(map.get("isHj").equals("否")){
				sql = "SELECT t1.id,t1.date,t1.djbh,t1.khmc,t1.xm,(t1.jing_val+t1.jian_val+t1.huo_val) taoshu,t1.zy,t1.jing_val,t1.jian_val,t1.huo_val,\n" +
						"  (SELECT ROUND(SUM(p.heji), 1) FROM s_sale_detail p WHERE p.sale_id = t1.id) heji FROM\n" +
						"  xhd t1 LEFT JOIN khgl t2 ON t1.kh_code = t2.id WHERE 1=1 ";
				countsql = " SELECT COUNT(1) FROM xhd t1 LEFT JOIN khgl t2 ON t1.kh_code = t2.id WHERE  1=1   AND state IN (1,2)   ";
			}else{
				sql = "SELECT ifnull(FORMAT(t3.total*(t1.jing_val+t1.jian_val+t1.huo_val), 3),0) t3total,ifnull(t3.price,0) t3price, ifnull(ROUND(t3.total*(t1.jing_val+t1.jian_val+t1.huo_val)*t3.price, 1),0) t3hj,\n" +
						"t4.`total` t4total,t4.`price` t4price,t4.`total`*t4.`price` t4hj,t1.id,t1.date,t1.djbh,(t1.jing_val+t1.jian_val+t1.huo_val) taoshu,t1.khmc,t1.xm,IFNULL(type1.typename,'')  zy,t1.jing_val,t1.jian_val,t1.huo_val,\n" +
						"  (SELECT ROUND(SUM(p.heji), 1) FROM s_sale_detail p WHERE p.sale_id = t1.id) heji FROM\n" +
						"  xhd t1 LEFT JOIN khgl t2 ON t1.kh_code = t2.id \n " +
						" left join(SELECT ty1.typename,ty1.typecode FROM t_s_type ty1 LEFT JOIN t_s_typegroup ty2 ON ty2.`ID`=ty1.`typegroupid` WHERE ty2.`typegroupcode`='zy') type1 on type1.typecode=t1.zy" +
						"  LEFT JOIN s_sale_detail t3 ON t3.sale_id=t1.id AND t3.brand_code='折A1'\n" +
						"  LEFT JOIN s_sale_detail t4 ON t4.sale_id=t1.id AND t4.brand_code='精'\n" +
						"  WHERE t2.is_za1 = '是' \n" +
						"  ";
				countsql = " SELECT COUNT(1) FROM xhd t1 LEFT JOIN khgl t2 ON t1.kh_code = t2.id WHERE  t2.is_za1 = '是' ";

			}

			if(map.get("date_begin") != null && !map.get("date_begin").toString().isEmpty()){
				sql += " and t1.date >= '"+map.get("date_begin")+"'  ";
				countsql += " and t1.date >= '"+map.get("date_begin")+"'  ";

			}
			if(map.get("date_end") != null  && !map.get("date_end").toString().isEmpty()){
				sql += " and t1.date  <= '"+map.get("date_end")+" 59:59:59'  ";
				countsql += " and t1.date  <= '"+map.get("date_end")+" 59:59:59'  ";

			}
			if(map.get("djbh") != null && !map.get("djbh").toString().isEmpty()){
				sql += " and djbh like '%"+map.get("djbh")+"%'";
				countsql += " and djbh like '%"+map.get("djbh")+"%'";

			}
			if(map.get("khmc") != null && !map.get("khmc").toString().isEmpty()){
				*/
/*sql += " and t1.khmc like '%"+map.get("khmc")+"%'";
				countsql += " and t1.khmc like '%"+map.get("khmc")+"%'";*//*

				sql += " and t1.khmc = '"+map.get("khmc")+"'";
				countsql += " and t1.khmc = '"+map.get("khmc")+"'";

			}
			if(map.get("xm") != null && !map.get("xm").toString().isEmpty()){
				sql += " and t1.xm like '%"+map.get("xm")+"%'";
				countsql += " and t1.xm like '%"+map.get("xm")+"%'";

			}
			sql += " and state in (1,2) order by djbh asc ";
//				String countsql = "SELECT count(1) FROM ("+sql+") t9 ";

				//sql += " GROUP BY DATE DESC,djbh DESC limit ";
				if(dataGrid.getPage()==1){
					sql += " limit 0, "+dataGrid.getRows();
				}else{
					sql += "limit "+(dataGrid.getPage()-1)*dataGrid.getRows()+","+dataGrid.getRows();
				}

			this.systemService.listAllByJdbc(dataGrid, sql, countsql);
			TagUtil.datagrid(response, dataGrid);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@RequestMapping(params = "datagrid")
	public void datagrid(XhdEntity xhd, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(XhdEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, xhd, request.getParameterMap());
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		try{
		//自定义追加查询条件
			if(map.get("ids") != null){
				List<String> idsList = new ArrayList<String>();
				idsList.add("1");
				idsList.add("2");
				cq.in("state",idsList.toArray());
			}
			// criteria.setProjection(Projections.groupProperty("name"));
			//cq.setProjection("djbh");
			*/
/*Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("djbh", "desc");
			cq.setGroupBy(map2);*//*


		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.xhdService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	*/
/**
	 * 删除销货单
	 * 
	 * @return
	 *//*

	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(XhdEntity xhd, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		xhd = systemService.getEntity(XhdEntity.class, xhd.getId());
		message = "销货单删除成功";
		try{
			xhd.setState("6");
			xhdService.saveOrUpdate(xhd);
			//xhdService.delete(xhd);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "销货单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	*/
/**
	 * 批量删除销货单
	 * 
	 * @return
	 *//*

	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销货单删除成功";
		try{
			for(String id:ids.split(",")){
				XhdEntity xhd = systemService.getEntity(XhdEntity.class, 
				id
				);
				xhd.setState("6");
				xhdService.saveOrUpdate(xhd);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "销货单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	*/
/**
	 * 添加销货单打印预览
	 *
	 * @param ids
	 * @return
	 *//*

	@RequestMapping(params = "doPrintData")
	@ResponseBody
	public AjaxJson doPrintData(XhdEntity xhd, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销货单添加成功";
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		try{
			Map  khglEntity = systemService.findOneForJdbc("select * from khgl where id=?",map.get("khmc"));
			//map.put("khCode",map.get("khmc"));
			map.put("customName",khglEntity.get("khmc"));
			request.getSession().setAttribute("printData",map);
			j.setSuccess(true);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "销货单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	*/
/**
	 * 补单销货单打印预览
	 *
	 * @param ids
	 * @return
	 *//*

	@RequestMapping(params = "doPrintData2")
	@ResponseBody
	public AjaxJson doPrintData2(XhdEntity xhd, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销货单添加成功";
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		try{
			request.getSession().setAttribute("printData",map);
			j.setSuccess(true);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "销货单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	*/
/**
	 * 添加销货单
	 * 
	 * @param ids
	 * @return
	 *//*

	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(XhdEntity xhd, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销货单添加成功";
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		try{
			//xhd.setKprbh(map.get("realName").toString());
			//xhd.setUserAccount(map.get("userName").toString());
			xhd.setState("0");
			xhdService.save(xhd);
			String[] brandCodeItem  = map.get("brandCode").toString().split(",");
			String[] totalItem = map.get("total").toString().split(",");
			String[] priceItem = map.get("price").toString().split(",");
			String[] taoshuItem = map.get("taoshu").toString().split(",");
			String[] remarkItem = map.get("remark").toString().split(",");
			String[] hejiItem = map.get("heji").toString().split(",");
			for(int i = 0 ; i < brandCodeItem.length ; i++) {
				SSaleDetailEntity saleDetailEntity = new SSaleDetailEntity();
				saleDetailEntity.setSaleId(xhd.getId());
				saleDetailEntity.setBrandCode(brandCodeItem[i]);
				saleDetailEntity.setTotal(totalItem[i]);
				saleDetailEntity.setPrice(priceItem[i]);
				saleDetailEntity.setTaoshu(taoshuItem[i]);
				saleDetailEntity.setHeji(hejiItem[i]);
				if(remarkItem != null && remarkItem.length>0){
					saleDetailEntity.setRemark(remarkItem[i]);
				}
				systemService.save(saleDetailEntity);
			}
			j.setSuccess(true);
			*/
/*String[] brandCodeItem  = map.get("brandCode").toString().split(",");
			String[] totalItem = map.get("total").toString().split(",");
			String[] priceItem = map.get("price").toString().split(",");
			Map  khglEntity = systemService.findOneForJdbc("select * from khgl where id=?",xhd.getKhCode());
			String[] pmItem = khglEntity.get("pm").toString().split(",");
			List<String> pmArr = new ArrayList<String>();

			for(int i = 0 ; i < pmItem.length ; i++){
				String[] pmItemSub = pmItem[i].split("、");
				pmArr.add(pmItemSub[0]);
				pmArr.add(pmItemSub[1]);
			}
			for(int i = 0 ; i < brandCodeItem.length ; i++){
				SSaleDetailEntity saleDetailEntity = new SSaleDetailEntity();
				saleDetailEntity.setSaleId(xhd.getId());
				saleDetailEntity.setBrandCode(brandCodeItem[i]);
				saleDetailEntity.setTotal(totalItem[i]);
				saleDetailEntity.setPrice(priceItem[i]);
				boolean flag = false;
				for(int z = 0 ; z < pmArr.size() ; z++){
					if(pmArr.get(z).equals(brandCodeItem[i])){
						flag = true;
						break;
					}
				}
				if(!flag){
					systemService.save(saleDetailEntity);
				}
				*//*
*/
/*if(!khglEntity.get("pm").toString().contains(brandCodeItem[i])){
					systemService.save(saleDetailEntity);
				}*//*
*/
/*
			}
			for(String pm : pmItem){
				String[] pmItemSub = pm.split("、");
				String price = "";
				double total = 0;
				for(int i = 0 ; i < brandCodeItem.length ; i++){
					if(brandCodeItem[i].equals(pmItemSub[1])){
						price = priceItem[i];
					}
					if(brandCodeItem[i].equals(pmItemSub[0]) || brandCodeItem[i].equals(pmItemSub[1]) ){
						total += Double.parseDouble(totalItem[i]);
					}

					*//*
*/
/*if(pm.contains(brandCodeItem[i])){
						total += Double.parseDouble(totalItem[i]);
					}*//*
*/
/*
				}

				SSaleDetailEntity saleDetailEntity = new SSaleDetailEntity();
				saleDetailEntity.setSaleId(xhd.getId());
				saleDetailEntity.setBrandCode(pmItemSub[1]);
				saleDetailEntity.setTotal(String.valueOf(total));
				saleDetailEntity.setPrice(String.valueOf(price));

				systemService.save(saleDetailEntity);
			}*//*

			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "销货单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	*/
/**
	 * 销货单保存草稿
	 *
	 * @param ids
	 * @return
	 *//*

	@RequestMapping(params = "doBcOrder")
	@ResponseBody
	public AjaxJson doBcOrder(XhdEntity xhdEntity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销货单保存草稿成功";
		//Map map = (Map)request.getSession().getAttribute("printData");
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		double heji = 0,zongji=0;
		int taoshu=0,jingVal=0,jianVal=0,huoVal=0;
		DecimalFormat df0 = new DecimalFormat("######0.000");
		try{
			Map  khglEntity = systemService.findOneForJdbc("select * from khgl where id=?",map.get("khmc"));
			xhdEntity.setState("0");
			xhdEntity.setKhCode(xhdEntity.getKhmc());
			if(khglEntity != null){
				xhdEntity.setKhmc(khglEntity.get("khmc").toString());
			}

			if(xhdEntity.getId() != null && !xhdEntity.getId().isEmpty()){
				systemService.executeSql("delete from s_sale_detail where sale_id=?",xhdEntity.getId());
				XhdEntity t = systemService.get(XhdEntity.class,xhdEntity.getId());
				if(t != null){
					xhdEntity.setState(t.getState());
					MyBeanUtils.copyBeanNotNull2Bean(xhdEntity, t);
					xhdService.saveOrUpdate(t);
				}else{
					xhdService.save(xhdEntity);
				}
			}else{
				xhdService.save(xhdEntity);
			}

			if(map.get("brandCode") != null){
				if(!map.get("jingVal").toString().isEmpty() && map.get("jingVal")!= null){
					jingVal = Integer.parseInt(map.get("jingVal").toString());
				}
				if(!map.get("jianVal").toString().isEmpty() && map.get("jianVal")!= null){
					jianVal = Integer.parseInt(map.get("jianVal").toString());
				}
				if(!map.get("huoVal").toString().isEmpty() && map.get("huoVal")!= null){
					huoVal = Integer.parseInt(map.get("huoVal").toString());
				}
				taoshu = jingVal+jianVal+huoVal;

				String[] brandCodeItem  = map.get("brandCode").toString().split(",");
				String[] totalItem = map.get("total").toString().split(",");
				String[] priceItem = map.get("price").toString().split(",");
				for(int i = 0 ; i < brandCodeItem.length ; i++) {
					SSaleDetailEntity saleDetailEntity = new SSaleDetailEntity();
					saleDetailEntity.setSaleId(xhdEntity.getId());
					saleDetailEntity.setBrandCode(brandCodeItem[i]);
					saleDetailEntity.setTotal(totalItem[i]);
					saleDetailEntity.setPrice(priceItem[i]);
					saleDetailEntity.setTaoshu(String.valueOf(taoshu));

					if(!brandCodeItem[i].equals("精") && !brandCodeItem[i].equals("简")){
						heji = taoshu*Double.parseDouble(totalItem[i])*Double.parseDouble(priceItem[i]);
					}else{
						heji = Double.parseDouble(totalItem[i])*Double.parseDouble(priceItem[i]);
					}
					saleDetailEntity.setHeji(df0.format(heji));

					systemService.save(saleDetailEntity);
				}

			}
			j.setObj(xhdEntity.getId());
			j.setSuccess(true);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "销货单保存草稿失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	*/
/**
	 * 销货单保存打印
	 *
	 * @param ids
	 * @return
	 *//*

	@RequestMapping(params = "doSaveOrder")
	@ResponseBody
	public AjaxJson doSaveOrder(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销货单打印成功";
		Map map = (Map)request.getSession().getAttribute("printData");
		try{
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			Map orderNum = systemService.findOneForJdbc("select count(0)+1 orderNum from xhd where kh_code=? and DATE>=?",map.get("khmc"),DateUtil.getCurrentTimeString("yyyy"));
			Map  khglEntity = systemService.findOneForJdbc("select * from khgl where id=?",map.get("khmc"));
			request.getSession().setAttribute("khmc",map.get("khmc"));
			String djbh = DateUtil.format(new Date(),"yyyy")+khglEntity.get("khid")+String.format("%06d", orderNum.get("orderNum").toString());
			Map sell = systemService.findOneForJdbc("select * from xhd where id=?",map.get("id"));
			if(sell != null){
				systemService.executeSql("update xhd set state=1,djbh=? where id=?",djbh,map.get("id"));
			}else{
				XhdEntity xhd = new XhdEntity();
				double heji = 0,zongji=0;
				int taoshu=0,jingVal=0,jianVal=0,huoVal=0;
				DecimalFormat df0 = new DecimalFormat("######0.000");

				xhd.setState("1");
				xhd.setDate(map.get("date").toString());
				xhd.setDjbh(djbh);
				xhd.setKhCode(map.get("khmc").toString());
				xhd.setZy(map.get("zy").toString());
				if(khglEntity != null){
					xhd.setKhmc(khglEntity.get("khmc").toString());
				}
				xhd.setXm(map.get("xm").toString());
				xhd.setJingVal(map.get("jingVal").toString());
				xhd.setJianVal(map.get("jianVal").toString());
				xhd.setHuoVal(map.get("huoVal").toString());
				xhdService.save(xhd);

				if(map.get("brandCode") != null){
					if(!map.get("jingVal").toString().isEmpty() && map.get("jingVal")!= null){
						jingVal = Integer.parseInt(map.get("jingVal").toString());
					}
					if(!map.get("jianVal").toString().isEmpty() && map.get("jianVal")!= null){
						jianVal = Integer.parseInt(map.get("jianVal").toString());
					}
					if(!map.get("huoVal").toString().isEmpty() && map.get("huoVal")!= null){
						huoVal = Integer.parseInt(map.get("huoVal").toString());
					}
					taoshu = jingVal+jianVal+huoVal;

					String[] brandCodeItem  = map.get("brandCode").toString().split(",");
					String[] totalItem = map.get("total").toString().split(",");
					String[] priceItem = map.get("price").toString().split(",");
					for(int i = 0 ; i < brandCodeItem.length ; i++) {
						SSaleDetailEntity saleDetailEntity = new SSaleDetailEntity();
						saleDetailEntity.setSaleId(xhd.getId());
						saleDetailEntity.setBrandCode(brandCodeItem[i]);
						saleDetailEntity.setTotal(totalItem[i]);
						saleDetailEntity.setPrice(priceItem[i]);
						saleDetailEntity.setTaoshu(String.valueOf(taoshu));

						if(!brandCodeItem[i].equals("精") && !brandCodeItem[i].equals("简")){
							heji = taoshu*Double.parseDouble(totalItem[i])*Double.parseDouble(priceItem[i]);
						}else{
							heji = Double.parseDouble(totalItem[i])*Double.parseDouble(priceItem[i]);
						}
						saleDetailEntity.setHeji(df0.format(heji));

						systemService.save(saleDetailEntity);
					}

				}
			}

			j.setSuccess(true);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "销货单打印成功失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doSaveOrder2")
	@ResponseBody
	public AjaxJson doSaveOrder2(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销货单打印成功";
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		try{
			if(map.get("bjd")==null){
				systemService.executeSql("update xhd set state=2 where id=?",map.get("id"));
			}
			j.setSuccess(true);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "销货单打印成功失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	*/
/**
	 * 更新销货单
	 * 
	 * @param ids
	 * @return
	 *//*

	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(XhdEntity xhd, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "销货单更新成功";
		XhdEntity t = xhdService.get(XhdEntity.class, xhd.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(xhd, t);
			xhdService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "销货单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "importDetail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importDetail(HttpServletRequest request, HttpServletResponse response, TSDocument document, String filesavename) {
		AjaxJson j = new AjaxJson();
		Map param = ParameterUtil.getParamMaps(request.getParameterMap());
		File newfile = new File(request.getRealPath("/")+filesavename);
		try {
			CsvReader r = new CsvReader(request.getRealPath("/")+filesavename,',', Charset.forName("GBK"));
			Map  khglEntity = systemService.findOneForJdbc("select * from khgl where id=?",param.get("customId"));
			List<SCustomDetailEntity> customDetailEntities = null;
			List<Map> dataList = new ArrayList<Map>();
			List<Map> itemArr = new ArrayList();

			Map itemMap = new HashMap();
			String dataItems = "";
			DecimalFormat df0 = new DecimalFormat("######0.00");
			if(khglEntity == null){
				j.setMsg("该客户还未录入品名明细！");
				j.setSuccess(false);
				return  j;
			}else{
				customDetailEntities = systemService.findHql("from SCustomDetailEntity where customId=?",khglEntity.get("id").toString());
			}
			try {
				r.readHeaders();
				while (r.readRecord()) {
					if(r.get("型号").equals("合计") && khglEntity.get("is_za1").equals("是")){
						dataItems += "折A1,"+r.get("折A1")+",";
					}
					if(!r.get("型号").equals("合计") && khglEntity.get("is_za1").equals("否")){
						itemMap = new HashMap();
						itemMap.put("brandCode",r.get("型号"));
						itemMap.put("total",r.get("数量"));
						for(SCustomDetailEntity customDetailEntity : customDetailEntities){
							if(customDetailEntity.getBrandCode().equals(r.get("型号"))){
								itemMap.put("price",customDetailEntity.getTotal());
								itemArr.add(itemMap);
								break;
							}
						}

					}
				}

				if(khglEntity.get("is_za1").equals("否")){
					if(khglEntity.get("pm") != null && !khglEntity.get("pm").toString().isEmpty()){
						String[] pmItem = khglEntity.get("pm").toString().split(",");
						List<String> pmArr = new ArrayList<String>();
						List<Map> hbItmeArr = new ArrayList<Map>();
						for(int i = 0 ; i < pmItem.length ; i++){
							String[] pmItemSub = pmItem[i].split("、");
							pmArr.add(pmItemSub[0]);
							pmArr.add(pmItemSub[1]);
						}
						for(Map pmMap : itemArr){
							boolean flag = false;
							for(int z = 0 ; z < pmArr.size() ; z++){
								if(pmArr.get(z).equals(pmMap.get("brandCode"))){
									flag = true;
									break;
								}
							}
							if(!flag){
								hbItmeArr.add(pmMap);
							}
						}
						for(String pm : pmItem){
							String[] pmItemSub = pm.split("、");
							String price = "";
							int total = 0;
							for(Map pmMap:itemArr){
								for(SCustomDetailEntity customDetailEntity : customDetailEntities){
									if(customDetailEntity.getBrandCode().equals(pmItemSub[1])){
										price = customDetailEntity.getTotal();
										break;
									}
								}

								if(pmMap.get("brandCode").equals(pmItemSub[0]) || pmMap.get("brandCode").equals(pmItemSub[1]) ){
									total += Integer.parseInt(pmMap.get("total").toString());
								}
							}
							if(total>0){
								itemMap = new HashMap();
								itemMap.put("brandCode",pmItemSub[1]);
								itemMap.put("price",price);
								itemMap.put("total",total);
								hbItmeArr.add(itemMap);
							}

						}

						for(SCustomDetailEntity customDetailEntity : customDetailEntities){
							for(Map data : hbItmeArr){
								if(data.get("brandCode").toString().equals(customDetailEntity.getBrandCode())){
									dataItems += data.get("brandCode")+","+data.get("total")+","+data.get("price")+";";
									break;
								}
							}

						}
					}else{
						for(SCustomDetailEntity customDetailEntity : customDetailEntities){
							for(Map data : itemArr){
								if(data.get("brandCode").toString().equals(customDetailEntity.getBrandCode())){
									dataItems += data.get("brandCode")+","+data.get("total")+","+data.get("price")+";";
									break;
								}
							}

						}

					}
				}

				for(SCustomDetailEntity customDetailEntity : customDetailEntities){
					if(khglEntity.get("is_za1").equals("是")){
						if(customDetailEntity.getBrandCode().equals("A1")){
							dataItems += customDetailEntity.getTotal()+";";
						}
					}
					if(customDetailEntity.getBrandCode().equals("精")){
						dataItems += "精,0,"+customDetailEntity.getTotal()+";";
					}
					if(customDetailEntity.getBrandCode().equals("简")){
						dataItems += "简,0,"+customDetailEntity.getTotal()+";";
						break;
					}
				}
				if(dataItems.indexOf(";")>0){
					dataItems = dataItems.substring(0,dataItems.length()-1);
				}
				j.setObj(dataItems);
				j.setMsg("文件导入成功！");
			}  catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}
		}catch (Exception e) {
			j.setMsg("文件导入失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}finally{
			newfile.delete();
		}
		return j;
	}

	@RequestMapping(params = "importDetail2", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importDetail2(HttpServletRequest request, HttpServletResponse response, TSDocument document, String filesavename) {
		AjaxJson j = new AjaxJson();
		Map param = ParameterUtil.getParamMaps(request.getParameterMap());
		File newfile = new File(request.getRealPath("/")+filesavename);
		try {
			Map  khglEntity = systemService.findOneForJdbc("select * from khgl where id=?",param.get("customId"));
			*/
/*double jingVal=0,jianVal=0,huoVal=0,heji=0;
			if(!param.get("jingVal").toString().isEmpty() && param.get("jingVal")!= null){
				jingVal = Double.parseDouble(param.get("jingVal").toString());
			}
			if(!param.get("jianVal").toString().isEmpty() && param.get("jianVal")!= null){
				jianVal = Double.parseDouble(param.get("jianVal").toString());
			}
			if(!param.get("huoVal").toString().isEmpty() && param.get("huoVal")!= null){
				huoVal = Double.parseDouble(param.get("huoVal").toString());
			}
			double taoshu = jingVal+jianVal+huoVal;*//*

			List<SCustomDetailEntity> customDetailEntities = null;
			if(khglEntity == null){
				j.setMsg("该客户还未录入品名明细！");
				j.setSuccess(false);
				return  j;
			}else{
				customDetailEntities = systemService.findHql("from SCustomDetailEntity where customId=?",khglEntity.get("id").toString());
			}
			HSSFWorkbook wb = null;
			List<Map> dataList = new ArrayList<Map>();
			Map itemMap = new HashMap();
			String dataItems = "";
			DecimalFormat df0 = new DecimalFormat("######0.00");
			try {
				wb = WebFileUtils.createHSSFWorkBook(newfile);
				if (wb==null) {
					logger.error("传入文件无法识别，请检查文件类型！！");
					j.setMsg("文件导入失败！");
				}
				//Sheet sheet = wb.getSheetAt(0);
				logger.info("执行导入："+newfile.getName());
				HSSFSheet sheet = wb.getSheetAt(0);
				DecimalFormat df = new DecimalFormat("0");
				HSSFCell cell = null;
				int counter = 0;
				HSSFRow row = null;
				logger.info("执行导入："+newfile.getName());
				List<String> itemValue =null;
				List<Map> itemArr = new ArrayList();
				String cellValue = "";
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					itemValue = new ArrayList<String>();
					for (int z = 0; z <= 2; z++) {
						cell = row.getCell(z);
						if (cell == null) {
							itemValue.add(cellValue);
							continue;
						}
						if (z != 0) {
							switch (cell.getCellType()) {
								case HSSFCell.CELL_TYPE_STRING:
									cellValue = cell.getRichStringCellValue().getString().trim();
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
										cellValue = format.format(value);
									}
									//cellValue = df.format(cell.getNumericCellValue()).toString();
									break;

								default:
									cellValue = "";
							}
						} else {
							switch (cell.getCellType()) {
								case HSSFCell.CELL_TYPE_STRING:
									cellValue = cell.getRichStringCellValue().getString().trim();
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
									cellValue = df.format(cell.getNumericCellValue()).toString();
									break;

								default:
									cellValue = "";
							}
						}

						itemValue.add(cellValue);
					}
					if(itemValue.get(0).equals("合计") && khglEntity.get("is_za1").equals("是")){
						dataItems += "折A1,"+itemValue.get(2)+",";
					}
					if(!itemValue.get(0).equals("合计") && khglEntity.get("is_za1").equals("否")){
						itemMap = new HashMap();
						itemMap.put("brandCode",itemValue.get(0));
						itemMap.put("total",itemValue.get(1));
						//dataItems += itemValue.get(0)+","+itemValue.get(1)+",";
						for(SCustomDetailEntity customDetailEntity : customDetailEntities){
							if(customDetailEntity.getBrandCode().equals(itemValue.get(0))){
								itemMap.put("price",customDetailEntity.getTotal());
								itemArr.add(itemMap);
								//heji = taoshu*Double.parseDouble(customDetailEntity.getTotal())*Double.parseDouble(itemValue.get(1));
								//dataItems += customDetailEntity.getTotal()+","+taoshu+","+df0.format(heji)+";";
								break;
							}
						}

					}

				}
				if(khglEntity.get("is_za1").equals("否")){
					if(khglEntity.get("pm") != null && !khglEntity.get("pm").toString().isEmpty()){
						String[] pmItem = khglEntity.get("pm").toString().split(",");
						List<String> pmArr = new ArrayList<String>();
						List<Map> hbItmeArr = new ArrayList<Map>();
						for(int i = 0 ; i < pmItem.length ; i++){
							String[] pmItemSub = pmItem[i].split("、");
							pmArr.add(pmItemSub[0]);
							pmArr.add(pmItemSub[1]);
						}
						for(Map pmMap : itemArr){
							boolean flag = false;
							for(int z = 0 ; z < pmArr.size() ; z++){
								if(pmArr.get(z).equals(pmMap.get("brandCode"))){
									flag = true;
									break;
								}
							}
							if(!flag){
								hbItmeArr.add(pmMap);
							}
						}
						for(String pm : pmItem){
							String[] pmItemSub = pm.split("、");
							String price = "";
							int total = 0;
							for(Map pmMap:itemArr){
								for(SCustomDetailEntity customDetailEntity : customDetailEntities){
									if(customDetailEntity.getBrandCode().equals(pmItemSub[1])){
										price = customDetailEntity.getTotal();
										break;
									}
								}

								if(pmMap.get("brandCode").equals(pmItemSub[0]) || pmMap.get("brandCode").equals(pmItemSub[1]) ){
									total += Integer.parseInt(pmMap.get("total").toString());
								}
							}
							if(total>0){
								itemMap = new HashMap();
								itemMap.put("brandCode",pmItemSub[1]);
								itemMap.put("price",price);
								itemMap.put("total",total);
								hbItmeArr.add(itemMap);
							}

						}

						//List<Map> newItemArr = new ArrayList<Map>();

						for(SCustomDetailEntity customDetailEntity : customDetailEntities){
							for(Map data : hbItmeArr){
								if(data.get("brandCode").toString().equals(customDetailEntity.getBrandCode())){
									dataItems += data.get("brandCode")+","+data.get("total")+","+data.get("price")+";";
									break;
								}
							}

						}


						*/
/*for(Map data : hbItmeArr){
						*//*
*/
/*heji = taoshu*Double.parseDouble(data.get("total").toString())*Double.parseDouble(data.get("price").toString());
						dataItems += data.get("brandCode")+","+data.get("total")+","+data.get("price")+","+taoshu+","+df0.format(heji)+";";*//*
*/
/*
							dataItems += data.get("brandCode")+","+data.get("total")+","+data.get("price")+";";
						}*//*

					}else{
						*/
/*for (int i = 0; i < itemArr.size(); i++) {
							for (int z = i + 1; z < itemArr.size(); z++) {
								Map data1 = itemArr.get(i);
								Map data2 = itemArr.get(z);
								if(data1.get("brandCode").toString().compareTo(data2.get("brandCode").toString())>0){
									Map tmpMap = itemArr.get(i);
									itemArr.set(i, data2);
									itemArr.set(z, tmpMap);
								}

							}
						}*//*

						for(SCustomDetailEntity customDetailEntity : customDetailEntities){
							for(Map data : itemArr){
								if(data.get("brandCode").toString().equals(customDetailEntity.getBrandCode())){
									dataItems += data.get("brandCode")+","+data.get("total")+","+data.get("price")+";";
									break;
								}
							}

						}
						*/
/*for(Map data : itemArr){
						*//*
*/
/*heji = taoshu*Double.parseDouble(data.get("total").toString())*Double.parseDouble(data.get("price").toString());
						dataItems += data.get("brandCode")+","+data.get("total")+","+data.get("price")+","+taoshu+","+df0.format(heji)+";";*//*
*/
/*
							dataItems += data.get("brandCode")+","+data.get("total")+","+data.get("price")+";";
						}*//*

					}
				}

				for(SCustomDetailEntity customDetailEntity : customDetailEntities){
					if(khglEntity.get("is_za1").equals("是")){
						if(customDetailEntity.getBrandCode().equals("A1")){
							dataItems += customDetailEntity.getTotal()+";";
						}
					}
					if(customDetailEntity.getBrandCode().equals("精")){
						dataItems += "精,0,"+customDetailEntity.getTotal()+";";
					}
					if(customDetailEntity.getBrandCode().equals("简")){
						dataItems += "简,0,"+customDetailEntity.getTotal()+";";
						break;
					}
				}
				if(dataItems.indexOf(";")>0){
					dataItems = dataItems.substring(0,dataItems.length()-1);
				}
				j.setObj(dataItems);
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage(),e);
				e.printStackTrace();
			}


		} catch (Exception e) {
			j.setMsg("文件导入失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}finally{
			newfile.delete();
		}
		return j;
	}

	*/
/**
	 * 销货单新增页面跳转
	 * 
	 * @return
	 *//*

	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(XhdEntity xhd, HttpServletRequest req) {
		List<KhglEntity> khglEntities = systemService.findHql("from KhglEntity");
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		Map param = ParameterUtil.getParamMaps(req.getParameterMap());
		if(param.get("flagtype") != null){
			req.getSession().setAttribute("khmc","");
		}
		if(StringUtil.isNotEmpty(xhd.getId())){
			xhd = xhdService.getEntity(XhdEntity.class, xhd.getId());
			req.setAttribute("xhdPage", xhd);

		}else{

			//Map orderNum = systemService.findOneForJdbc("select count(0)+1 orderNum from xhd");
			req.setAttribute("userNum", user.getUserNum());
			//req.setAttribute("xhdNum", DateUtil.format(new Date(),"yyyy")+String.format("%03d", Integer.parseInt(user.getUserNum()))+String.format("%04d", orderNum.get("orderNum").toString()));
			req.setAttribute("xhdTime", DateUtil.format(new Date(),"yyyy-MM-dd"));


		}
		req.setAttribute("khglEntities", khglEntities);


		return new ModelAndView("com/gxedu/xhd/xhd-add");
	}

	@RequestMapping(params = "jump")
	public Object jump(HttpServletRequest req) {
		Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
		if(map.get("type") != null){
			req.setAttribute("url", "xhdController.do?goPrint2&id="+map.get("id"));
		}else{
			req.setAttribute("url", "xhdController.do?goPrint&showdjbh="+map.get("showdjbh"));
		}
		return "forward:/context/"+map.get("r")+".jsp";
	}

	@RequestMapping(params = "sumPrintSheetFrm")
	public Object sumPrintSheetFrm(HttpServletRequest req) {
		Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
		if(map.get("isHj").equals("否")){
			req.setAttribute("url", "xhdController.do?goPrintView&isHj=否&xm="+map.get("xm")+"&khmc="+map.get("khmc")+"&date_begin="+map.get("date_begin")+"&date_end="+map.get("date_end"));
		}else{
			req.setAttribute("url", "xhdController.do?goPrintView&isHj=是&xm="+map.get("xm")+"&khmc="+map.get("khmc")+"&date_begin="+map.get("date_begin")+"&date_end="+map.get("date_end"));
		}
		return "forward:/context/sumPrintSheetFrm.jsp";
	}

	@RequestMapping(params = "goPrintView")
	public ModelAndView goPrintView(XhdEntity xhd, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		Map<String,String> map= ParameterUtil.getParamMaps(req.getParameterMap());
		String sql = "";
		if(map.get("isHj").equals("否")){
			sql = "SELECT t1.id,t1.date,t1.djbh,t1.khmc,t1.xm,(t1.jing_val+t1.jian_val+t1.huo_val) taoshu,t1.zy,t1.jing_val,t1.jian_val,t1.huo_val,\n" +
					"  (SELECT ROUND(SUM(p.heji), 1) FROM s_sale_detail p WHERE p.sale_id = t1.id) heji FROM\n" +
					"  xhd t1 LEFT JOIN khgl t2 ON t1.kh_code = t2.id WHERE 1=1 ";
		}else{
			sql = "SELECT FORMAT(t3.total*(t1.jing_val+t1.jian_val+t1.huo_val), 3) t3total,t3.price t3price, ROUND(t3.total*(t1.jing_val+t1.jian_val+t1.huo_val)*t3.price, 1) t3hj,\n" +
					"t4.`total` t4total,t4.`price` t4price,t4.`total`*t4.`price` t4hj,t1.id,t1.date,t1.djbh,(t1.jing_val+t1.jian_val+t1.huo_val) taoshu,t1.khmc,t1.xm,IFNULL(type1.typename,'')  zy,t1.jing_val,t1.jian_val,t1.huo_val,\n" +
					"  (SELECT ROUND(SUM(p.heji), 1) FROM s_sale_detail p WHERE p.sale_id = t1.id) heji FROM\n" +
					"  xhd t1 LEFT JOIN khgl t2 ON t1.kh_code = t2.id \n" +
					" left join(SELECT ty1.typename,ty1.typecode FROM t_s_type ty1 LEFT JOIN t_s_typegroup ty2 ON ty2.`ID`=ty1.`typegroupid` WHERE ty2.`typegroupcode`='zy') type1 on type1.typecode=t1.zy" +
					"  LEFT JOIN s_sale_detail t3 ON t3.sale_id=t1.id AND t3.brand_code='折A1'\n" +
					"  LEFT JOIN s_sale_detail t4 ON t4.sale_id=t1.id AND t4.brand_code='精'\n" +
					"  WHERE t2.is_za1 = '是' \n" +
					"  ";
		}

		if(map.get("date_begin") != null && !map.get("date_begin").toString().isEmpty()){
			sql += " and t1.date >= '"+map.get("date_begin")+"'  ";
		}
		if(map.get("date_end") != null  && !map.get("date_end").toString().isEmpty()){
			sql += " and t1.date  <= '"+map.get("date_end")+" 59:59:59'  ";

		}
		if(map.get("djbh") != null && !map.get("djbh").toString().isEmpty()){
			sql += " and djbh like '%"+map.get("djbh")+"%'";
		}
		if(map.get("khmc") != null && !map.get("khmc").toString().isEmpty()){
			//sql += " and t1.khmc like '%"+map.get("khmc")+"%'";
			sql += " and t1.khmc = '"+map.get("khmc")+"'";

		}
		*/
/*if(map.get("xm") != null && !map.get("xm").toString().isEmpty()){
			sql += " and t1.xm like '%"+map.get("xm")+"%'";
		}*//*

		sql += " and state in (1,2) order by djbh asc ";
		List<Map<String, Object>> mxList = systemService.findForJdbc(sql);
		req.setAttribute("mxList",mxList);
		if(map.get("isHj").equals("否")){
			return new ModelAndView("com/gxedu/xhd/printPreview");
		}else{
			return new ModelAndView("com/gxedu/xhd/printPreview2");
		}
	}

	@RequestMapping(params = "goPrint")
	public ModelAndView goPrint(XhdEntity xhd, HttpServletRequest req) {
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		Map map = (Map)req.getSession().getAttribute("printData");
		List<Map> saleDetails = new ArrayList<Map>();
		double heji = 0,zongji=0;
		int taoshu=0,jingVal=0,jianVal=0,huoVal=0;
		if(!map.get("jingVal").toString().isEmpty() && map.get("jingVal")!= null){
			jingVal = Integer.parseInt(map.get("jingVal").toString());
		}
		if(!map.get("jianVal").toString().isEmpty() && map.get("jianVal")!= null){
			jianVal = Integer.parseInt(map.get("jianVal").toString());
		}
		if(!map.get("huoVal").toString().isEmpty() && map.get("huoVal")!= null){
			huoVal = Integer.parseInt(map.get("huoVal").toString());
		}
		Map  khglEntity = systemService.findOneForJdbc("select * from khgl where id=?",map.get("khmc"));
		req.setAttribute("khglEntity",khglEntity);
		Map orderNum = systemService.findOneForJdbc("select count(0)+1 orderNum from xhd where kh_code=? and DATE>=?",map.get("khmc"),DateUtil.getCurrentTimeString("yyyy"));
		//Map orderNum = systemService.findOneForJdbc("select count(0)+1 orderNum from xhd where kh_code=?",map.get("khmc"));
		String djbh = DateUtil.format(new Date(),"yyyy")+khglEntity.get("khid")+String.format("%06d", orderNum.get("orderNum").toString());
		req.setAttribute("djbh",djbh);

		taoshu = jingVal+jianVal+huoVal;
		DecimalFormat df0 = new DecimalFormat("######0.0");
		Map topMap = systemService.findOneForJdbc("select * from s_sale_topinfo  limit 0,1");
		if(topMap != null){
			req.setAttribute("piaotou",topMap.get("topinfo"));
			req.setAttribute("piaowei",topMap.get("remark"));
			req.setAttribute("piaowei_telphone",topMap.get("telphone"));
		}
		*/
/*topMap = systemService.findOneForJdbc("select * from s_sale_topinfo where type='票尾' limit 0,1");
		if(topMap != null){

		}*//*

		if(map.get("brandCode") != null){

			String[] brandCodeItem  = map.get("brandCode").toString().split(",");
			String[] totalItem = map.get("total").toString().split(",");
			String[] priceItem = map.get("price").toString().split(",");
			//String[] taoshuItem = map.get("taoshu").toString().split(",");
			//String[] hejiItem = map.get("heji").toString().split(",");
			for(int i = 0 ; i < brandCodeItem.length ; i++) {
				Map data = new HashMap();
				data.put("brandCode",brandCodeItem[i]);
				data.put("total",totalItem[i]);
				data.put("price",priceItem[i]);
				//data.put("taoshu",taoshuItem[i]);
				data.put("taoshu",taoshu);
				//taoshu = Double.parseDouble(data.get("taoshu").toString());
				if(!brandCodeItem[i].equals("精") && !brandCodeItem[i].equals("简")){
					heji = taoshu*Double.parseDouble(data.get("total").toString())*Double.parseDouble(data.get("price").toString());
				}else{
					heji = Double.parseDouble(data.get("total").toString())*Double.parseDouble(data.get("price").toString());
				}
				data.put("heji",df0.format(heji));

				saleDetails.add(data);
				zongji = zongji+heji;
			}

			req.setAttribute("zongji",df0.format(zongji));
			req.setAttribute("sale",map);
			req.setAttribute("saleDetails",saleDetails);
		}

		return new ModelAndView("com/gxedu/xhd/xhd-print");
	}

	@RequestMapping(params = "goPrint2")
	public ModelAndView goPrint2(XhdEntity xhd, HttpServletRequest req) {
 		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
 		XhdEntity xhdEntity = systemService.get(XhdEntity.class,xhd.getId());
 		List<SSaleDetailEntity> saleDetailEntities = systemService.findHql("from SSaleDetailEntity where saleId=?",xhd.getId());
		Map map = (Map)req.getSession().getAttribute("printData");
		List<Map> saleDetails = new ArrayList<Map>();
		int taoshu=0,jingVal=0,jianVal=0,huoVal=0;
		if(!xhdEntity.getJingVal().isEmpty() && xhdEntity.getJingVal()!= null){
			jingVal = Integer.parseInt(xhdEntity.getJingVal().toString());
		}
		if(!xhdEntity.getJingVal().isEmpty() && xhdEntity.getJingVal()!= null){
			jianVal = Integer.parseInt(xhdEntity.getJingVal());
		}
		if(!xhdEntity.getHuoVal().isEmpty() && xhdEntity.getHuoVal()!= null){
			huoVal = Integer.parseInt(xhdEntity.getHuoVal());
		}

		taoshu = jingVal+jianVal+huoVal;

		String zongji = String.valueOf(systemService.findOneForJdbc("SELECT ROUND(SUM(heji),1) zongji FROM s_sale_detail WHERE sale_id=?",xhd.getId()).get("zongji"));

		Map topMap = systemService.findOneForJdbc("select * from s_sale_topinfo  limit 0,1");
		if(topMap != null){
			req.setAttribute("piaotou",topMap.get("topinfo"));
			req.setAttribute("piaowei",topMap.get("remark"));
			req.setAttribute("piaowei_telphone",topMap.get("telphone"));
		}

		req.setAttribute("zongji",zongji);
		req.setAttribute("userNum",user.getUserNum());

		req.setAttribute("sale",xhdEntity);
		req.setAttribute("saleDetails",saleDetailEntities);

		return new ModelAndView("com/gxedu/xhd/xhd-print2");
	}
	*/
/**
	 * 销货单草稿编辑页面跳转
	 * 
	 * @return
	 *//*

	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(XhdEntity xhd, HttpServletRequest req) {
		List<KhglEntity> khglEntities = systemService.findHql("from KhglEntity");
		Map map = ParameterUtil.getParamMaps(req.getParameterMap());
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if(StringUtil.isNotEmpty(xhd.getId())){
			xhd = xhdService.getEntity(XhdEntity.class, xhd.getId());
			if(xhd.getState().equals(map.get("state"))){
				req.setAttribute("xhdPage", xhd);
			}else{
				req.setAttribute("khglEntities", khglEntities);
				return new ModelAndView("com/gxedu/xhd/info");
			}
		}
		req.setAttribute("khglEntities", khglEntities);
		return new ModelAndView("com/gxedu/xhd/xhd-update");
	}

	*/
/**
	 * 销货单草稿编辑页面跳转
	 *
	 * @return
	 *//*

	@RequestMapping(params = "goJdUpdate")
	public ModelAndView goJdUpdate(XhdEntity xhd, HttpServletRequest req) {
		List<KhglEntity> khglEntities = systemService.findHql("from KhglEntity");
		Map map = ParameterUtil.getParamMaps(req.getParameterMap());
		TSUser user = (TSUser) req.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if(StringUtil.isNotEmpty(xhd.getId())){
			xhd = xhdService.getEntity(XhdEntity.class, xhd.getId());
			if(xhd.getState().equals(map.get("state"))){
				req.setAttribute("xhdPage", xhd);
			}else{
				req.setAttribute("khglEntities", khglEntities);
				return new ModelAndView("com/gxedu/xhd/info");
			}
		}
		req.setAttribute("khglEntities", khglEntities);
		return new ModelAndView("com/gxedu/xhd/xhd-jdupdate");
	}


	
	*/
/**
	 * 导入功能跳转
	 * 
	 * @return
	 *//*

	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","xhdController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	*/
/**
	 * 费用结算明细表1 导出excel
	 * 
	 * @param request
	 * @param response
	 *//*

	@RequestMapping(params = "exportXls")
	public String exportXls(XhdEntity xhd, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		AjaxJson j = new AjaxJson();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String savepath = request.getRealPath("/")+"upload/detail/";
		File file = new File(savepath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		savepath = request.getRealPath("/")+"upload/detail/"+sdf.format(c.getTime())+"-费用结算明细表1.xls";

		Map<String,String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		List<String> headList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();

		headList.add("客户名称");
		fieldList.add("khmc");
		headList.add("项目名称");
		fieldList.add("xm");
		headList.add("专业");
		fieldList.add("zy");
		headList.add("精装");
		fieldList.add("jing_val");
		headList.add("简装");
		fieldList.add("jian_val");
		headList.add("折叠");
		fieldList.add("huo_val");
		headList.add("套数");
		fieldList.add("taoshu");
		headList.add("金额");
		fieldList.add("heji");
		headList.add("日期");
		fieldList.add("date");
		headList.add("单据编号");
		fieldList.add("djbh");

		try {
			String sql = "SELECT t1.id,t1.date,t1.djbh,t1.khmc,t1.xm,(t1.jing_val+t1.jian_val+t1.huo_val) taoshu,t1.zy,t1.jing_val,t1.jian_val,t1.huo_val,\n" +
					"  (SELECT ROUND(SUM(p.heji), 1) FROM s_sale_detail p WHERE p.sale_id = t1.id) heji FROM\n" +
					"  xhd t1 LEFT JOIN khgl t2 ON t1.kh_code = t2.id WHERE  1=1 ";

			if(map.get("date_begin") != null && !map.get("date_begin").toString().isEmpty()){
				sql += " and t1.date >= '"+map.get("date_begin")+"'  ";
			}
			if(map.get("date_end") != null  && !map.get("date_end").toString().isEmpty()){
				sql += " and t1.date  <= '"+map.get("date_end")+" 59:59:59'  ";

			}
			if(map.get("djbh") != null && !map.get("djbh").toString().isEmpty()){
				sql += " and djbh like '%"+map.get("djbh")+"%'";
			}
			if(map.get("khmc") != null && !map.get("khmc").toString().isEmpty()){
				//sql += " and t1.khmc like '%"+map.get("khmc")+"%'";
				sql += " and t1.khmc = '"+map.get("khmc")+"'";

			}
			if(map.get("xm") != null && !map.get("xm").toString().isEmpty()){
				sql += " and t1.xm like '%"+map.get("xm")+"%'";
			}
			sql += " and state in (1,2) order by djbh asc ";
			List<Map<String,Object>> paperList = this.xhdService.findForJdbc(sql.toString());
			ExcelUtil.createExcel(savepath,headList, fieldList, paperList);
			WebFileUtils.downLoad(savepath, response, false);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("导出失败！");
			j.setSuccess(false);
		}
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	*/
/**
	 * 费用结算明细表2 导出excel
	 *
	 * @param request
	 * @param response
	 *//*

	@RequestMapping(params = "exportXls2")
	public String exportXls2(XhdEntity xhd, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		AjaxJson j = new AjaxJson();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String savepath = request.getRealPath("/")+"upload/detail/";
		File file = new File(savepath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		savepath = request.getRealPath("/")+"upload/detail/"+sdf.format(c.getTime())+"-费用结算明细表2.xls";

		Map<String,String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		List<String> headList = new ArrayList<String>();
		List<String> fieldList = new ArrayList<String>();

		headList.add("客户名称");
		fieldList.add("khmc");
		headList.add("项目名称");
		fieldList.add("xm");
		headList.add("专业");
		fieldList.add("zy");
		headList.add("精装");
		fieldList.add("jing_val");
		headList.add("简装");
		fieldList.add("jian_val");
		headList.add("折叠");
		fieldList.add("huo_val");
		headList.add("折合A1张数");
		fieldList.add("t3total");
		headList.add("折合A1单价");
		fieldList.add("t3price");
		headList.add("晒图费用合计");
		fieldList.add("t3hj");
		headList.add("精装本数");
		fieldList.add("t4total");
		headList.add("精装单价");
		fieldList.add("t4price");
		headList.add("装订合计");
		fieldList.add("t4hj");
		headList.add("总计");
		fieldList.add("heji");
		headList.add("套数");
		fieldList.add("taoshu");
		headList.add("日期");
		fieldList.add("date");

		try {
			String sql = "SELECT FORMAT(t3.total*(t1.jing_val+t1.jian_val+t1.huo_val), 3) t3total,t3.price t3price, ROUND(t3.total*(t1.jing_val+t1.jian_val+t1.huo_val)*t3.price, 1) t3hj,\n" +
					"t4.`total` t4total,t4.`price` t4price,t4.`total`*t4.`price` t4hj,t1.id,t1.date,t1.djbh,(t1.jing_val+t1.jian_val+t1.huo_val) taoshu,t1.khmc,t1.xm,t1.zy,t1.jing_val,t1.jian_val,t1.huo_val,\n" +
					"  (SELECT ROUND(SUM(p.heji), 1) FROM s_sale_detail p WHERE p.sale_id = t1.id) heji FROM\n" +
					"  xhd t1 LEFT JOIN khgl t2 ON t1.kh_code = t2.id \n" +
					"  LEFT JOIN s_sale_detail t3 ON t3.sale_id=t1.id AND t3.brand_code='折A1'\n" +
					"  LEFT JOIN s_sale_detail t4 ON t4.sale_id=t1.id AND t4.brand_code='精'\n" +
					"  WHERE t2.is_za1 = '是' \n" +
					"  ";

			if(map.get("date_begin") != null && !map.get("date_begin").toString().isEmpty()){
				sql += " and t1.date >= '"+map.get("date_begin")+"'  ";
			}
			if(map.get("date_end") != null  && !map.get("date_end").toString().isEmpty()){
				sql += " and t1.date  <= '"+map.get("date_end")+" 59:59:59'  ";

			}
			if(map.get("djbh") != null && !map.get("djbh").toString().isEmpty()){
				sql += " and djbh like '%"+map.get("djbh")+"%'";
			}
			if(map.get("khmc") != null && !map.get("khmc").toString().isEmpty()){
				sql += " and t1.khmc like '%"+map.get("khmc")+"%'";
			}
			if(map.get("xm") != null && !map.get("xm").toString().isEmpty()){
				sql += " and t1.xm like '%"+map.get("xm")+"%'";
			}
			sql += " and state in (1,2)  ";
			List<Map<String,Object>> paperList = this.xhdService.findForJdbc(sql.toString());
			ExcelUtil.createExcel(savepath,headList, fieldList, paperList);
			WebFileUtils.downLoad(savepath, response, false);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("导出失败！");
			j.setSuccess(false);
		}
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	*/
/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 *//*

	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(XhdEntity xhd, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"销货单");
    	modelMap.put(NormalExcelConstants.CLASS,XhdEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("销货单列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response, TSDocument document, String filesavename) {
		AjaxJson j = new AjaxJson();

		File newfile = new File(request.getRealPath("/")+filesavename);
		try {
			int result = 0;
			int index = 1;
			Workbook wb = null;
			try {
				wb = WebFileUtils.createWorkBook(newfile);
				if (wb==null) {
					logger.error("传入文件无法识别，请检查文件类型！！");
					j.setMsg("文件导入失败！");
				}
				Sheet sheet = wb.getSheetAt(0);
				logger.info("执行导入："+newfile.getName());
				for (int z = 1; z <= sheet.getLastRowNum(); z++) {
					Row row = sheet.getRow(z);
					XhdEntity gl = new XhdEntity();
					if(row.getCell(0)!=null && !row.getCell(0).equals("")){
						Cell option0 = row.getCell(0);
						Cell option1 = row.getCell(1);
						Cell option2 = row.getCell(2);
						Cell option3 = row.getCell(3);
						Cell option4 = row.getCell(4);
						Cell option5 = row.getCell(5);
						Cell option6 = row.getCell(6);
						Cell option7 = row.getCell(7);
						Cell option8 = row.getCell(8);
						Cell option9 = row.getCell(9);
						Cell option10 = row.getCell(10);


						if(option0 != null){
							if(!option0.toString().isEmpty()){
								option0.setCellType(Cell.CELL_TYPE_STRING);
								gl.setPtxx(option0.getStringCellValue());

								if (option1!= null) {
									if(!option1.toString().isEmpty()){
										option1.setCellType(Cell.CELL_TYPE_STRING);
										gl.setDate(option1.getStringCellValue());
									}
								}
								if (option2!= null) {
									if(!option2.toString().isEmpty()){
										option2.setCellType(Cell.CELL_TYPE_STRING);
										gl.setDjbh(option2.getStringCellValue());
									}
								}
								if (option3!= null) {
									if(!option3.toString().isEmpty()){
										option3.setCellType(Cell.CELL_TYPE_STRING);
										gl.setKhmc(option3.getStringCellValue());
									}

								}
								if (option4!= null) {
									if(!option4.toString().isEmpty()){
										option4.setCellType(Cell.CELL_TYPE_STRING);
										gl.setXm(option4.getStringCellValue());
									}
								}
								if (option5!= null) {
									if(!option5.toString().isEmpty()){
										option5.setCellType(Cell.CELL_TYPE_STRING);
										gl.setZy(option5.getStringCellValue());
									}
								}
								if (option6!= null) {
									if(!option6.toString().isEmpty()){
										option6.setCellType(Cell.CELL_TYPE_STRING);
										gl.setJjh(option6.getStringCellValue());
									}
								}
								if (option7!= null) {
									if(!option7.toString().isEmpty()){
										option7.setCellType(Cell.CELL_TYPE_STRING);
										gl.setBg(option7.getStringCellValue());
									}
								}
								if (option8!= null) {
									if(!option8.toString().isEmpty()){
										option8.setCellType(Cell.CELL_TYPE_STRING);
										gl.setKprbh(option8.getStringCellValue());
									}
								}
								if (option9!= null) {
									if(!option9.toString().isEmpty()){
										option9.setCellType(Cell.CELL_TYPE_STRING);
										gl.setJsr(option9.getStringCellValue());
									}
								}
								if (option10!= null) {
									if(!option10.toString().isEmpty()){
										option10.setCellType(Cell.CELL_TYPE_STRING);
										gl.setPdxx(option10.getStringCellValue());
									}
								}
								this.xhdService.save(gl);
							}
						}
					}

					index++;
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage(),e);
				e.printStackTrace();
			}


		} catch (Exception e) {
			j.setMsg("文件导入失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}finally{
			newfile.delete();
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<XhdEntity> list() {
		List<XhdEntity> listXhds=xhdService.getList(XhdEntity.class);
		return listXhds;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		XhdEntity task = xhdService.get(XhdEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody XhdEntity xhd, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<XhdEntity>> failures = validator.validate(xhd);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			xhdService.save(xhd);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = xhd.getId();
		URI uri = uriBuilder.path("/rest/xhdController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody XhdEntity xhd) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<XhdEntity>> failures = validator.validate(xhd);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			xhdService.saveOrUpdate(xhd);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		xhdService.deleteEntityById(XhdEntity.class, id);
	}
}
*/
