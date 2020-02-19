package com.emk.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.emk.bound.minstorage.entity.EmkMInStorageEntity;
import com.emk.bound.minstoragedetail.entity.EmkMInStorageDetailEntity;
import com.emk.bound.moutstorage.entity.EmkMOutStorageEntity;
import com.emk.bound.muse.entity.EmkMUseEntity;
import com.emk.util.Utils;
import com.emk.util.excel.annotation.Excel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xslf.usermodel.VerticalAlignment;


@Deprecated 
public final class ExcelUtil {

	private ExcelUtil() {
	}

	/** Excel 文件要存放的位置 */
	public static String outputFile = "D:/excel_demo.xls";


	/**
	 * 标题列表
	 */
	private static String[] HEAD_LIST = { "序号", "名字", "年龄", "备注" };

	private static String[] VALUE_LIST = { "01", "陈铭明", "20", "1983-01-01",
			"........." };

	/**
	 * 字段列表
	 */
	private static String[] FIELD_LIST = { "index", "name", "age", "content" };

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// ------------------------------------------------------------
		// List<String[]> list = new ArrayList<String[]>();
		// list.add(VALUE_LIST);
		// list.add(VALUE_LIST);
		// list.add(VALUE_LIST);
		// createExcel(outputFile, HEAD_LIST, list);

		// ------------------------------------------------------------
		 List<Map<String, Object>> dataList = new ArrayList<Map<String,
		 Object>>();
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("index", "001");
		 map.put("name", "张三");
		 map.put("age", "22");
		 map.put("content", "大家好");
		 dataList.add(map);
		 dataList.add(map);
		 dataList.add(map);
		//
		createExcel(outputFile,HEAD_LIST, FIELD_LIST,dataList);
		/*public static void createAbExcel(String excel_name,String sheetstr, String[] headList,
				String[] fieldList, List<Map<String, Object>> dataList,String sheetstr2,  String[] headList2,
				String[] fieldList2, List<Map<String, Object>> dataList2)*/
		// -------------------------------------------------------------
		// readExcel(null);
		// --------------------------------------------------------------
		//new ExcelUtil().testReadExcel();
	}

	/**
	 * 使用举例
	 * 
	 */
	public void testCreateExcel() {

		List<Map<String, Object>> dataList = getDataList();
		List<String> headList = getHeadList();
		List<String> fieldList = getFieldList();

		try {
			createExcel("TEST01.xls", headList, fieldList, dataList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 使用举例
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("static-access")
	public void testReadExcel() throws Exception {
		String excelUrl = "C:/javadeveloper/workspace/Mybatis_one/src/测试台账2003.xls";
		List<String[]> list = this.readExcel(excelUrl);
		for (String[] str : list) {
			for (String s : str) {
				//System.out.print(s + " | ");
			}
			//org.jeecgframework.core.util.LogUtil.info("");
		}
	}

	/**
	 * 测试举例
	 * 
	 * @return
	 */
	private List<Map<String, Object>> getDataList() {
		/**
		 * 数据集合
		 */
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 单行数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", "001");
		map.put("name", "张三");
		map.put("age", "22");
		map.put("content", "大家好");
		map.put("date", new Date());
		dataList.add(map);
		dataList.add(map);
		dataList.add(map);

		return dataList;
	}

	/**
	 * 测试举例
	 * 
	 * @return
	 */
	private List<String> getHeadList() {
		List<String> headList = new ArrayList<String>();
		headList.add("序号");
		headList.add("名字");
		headList.add("年龄");
		headList.add("出生");
		headList.add("备注");

		return headList;
	}

	/**
	 * 测试举例
	 * 
	 * @return
	 */
	private List<String> getFieldList() {
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("index");
		fieldList.add("name");
		fieldList.add("age");
		fieldList.add("date");
		fieldList.add("content");

		return fieldList;
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, String[] headList,
			String[] fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle setBorder = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		//setBorder.setFont(font);
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder.setWrapText(true);//设置自动换行
		setBorder.setAlignment(CellStyle.ALIGN_LEFT);//水平居中
		setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		sheet.setDefaultColumnWidth(20);
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(20);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			font.setFontHeightInPoints((short) 20);//设置字体大小
			setBorder.setFont(font);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(setBorder);

			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			row.setHeightInPoints(20);

			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				font.setFontHeightInPoints((short) 9);//设置字体大小
				setBorder.setFont(font);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(setBorder);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList[i])));
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	public static void createAbExcel(String excel_name,String sheetName1,String sheetName2,List<String> headList,
									 List<String> fieldList, List<Map<String, Object>> dataList,List<String> headList2,
									 List<String> fieldList2, List<Map<String, Object>> dataList2)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = workbook.createCellStyle(); // 样式对象
		//生成一个字体
	/*	HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
		//把字体应用到当前的样式
		style.setFont(font);*/

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet(sheetName1);

		sheet.setDefaultColumnWidth(20);
		//sheet.setDefaultRowHeightInPoints(20);
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		//row.setHeight((short) (16 * 20));

		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(style); // 样式，居中
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
			if(i == 0){
				sheet.setColumnWidth(i, 20 * 512);
			}
			if(i == 3){
				sheet.setColumnWidth(i, 20 * 512);
			}

		}
		// ===============================================================
	/*	font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);         //字体增粗
		//把字体应用到当前的样式
		style.setFont(font);*/
		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellStyle(style); // 样式，居中
				cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
			}
			// ===============================================================
		}
		if(sheetName2 != null && !sheetName2.isEmpty()){
			sheet = workbook.createSheet(sheetName2);
			// 在索引0的位置创建行（最顶端的行）
			sheet.setDefaultColumnWidth(10);
			//sheet.setDefaultRowHeightInPoints(20);
			row = sheet.createRow(0);
		/*	font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
			font.setFontHeightInPoints((short)12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
			style.setFont(font);*/
			// ===============================================================
			for (int i = 0; i < headList2.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style); // 样式，居中
				// 在单元格中输入一些内容
				cell.setCellValue(headList2.get(i));
				if(i == 0){
					sheet.setColumnWidth(i, 15 * 512);
				}
				if(i == 1){
					sheet.setColumnWidth(i, 30 * 512);
				}
			}
			// ===============================================================
			/*font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
			font.setFontHeightInPoints((short)12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);         //字体增粗
			//把字体应用到当前的样式
			style.setFont(font);*/
			for (int n = 0; n < dataList2.size(); n++) {
				// 在索引1的位置创建行（最顶端的行）
				HSSFRow row_value = sheet.createRow(n + 1);
				Map<String, Object> dataMap = dataList2.get(n);
				if(dataMap.get("optionitem") != null && !dataMap.get("optionitem").toString().isEmpty()){
					String[] item = dataMap.get("optionitem").toString().split("##");
					for(int i = 0 ; i < item.length ; i++){
						dataMap.put("option"+i,item[i]);
					}
				}
				// ===============================================================
				for (int i = 0; i < fieldList2.size(); i++) {

					// 在索引0的位置创建单元格（左上端）
					HSSFCell cell = row_value.createCell(i);
					// 定义单元格为字符串类型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// 在单元格中输入一些内容
					cell.setCellValue(objToString(dataMap.get(fieldList2.get(i))));
				}
				// ===============================================================
			}
		}


		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, List<String> headList,
								   List<String> fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = workbook.createCellStyle(); // 样式对象
		//生成一个字体
	/*	HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
		//把字体应用到当前的样式
		style.setFont(font);*/

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet("商品信息");

		sheet.setDefaultColumnWidth(10);
		//sheet.setDefaultRowHeightInPoints(20);
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		//row.setHeight((short) (16 * 20));

		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(style); // 样式，居中
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));

		}
		// ===============================================================
	/*	font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);         //字体增粗
		//把字体应用到当前的样式
		style.setFont(font);*/
		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellStyle(style); // 样式，居中
				cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcelFormate(String excel_name,String title, List<String> headList,
								   List<String> fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();

		HSSFCellStyle headstyle = workbook.createCellStyle(); // 样式对象
		headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(30);
		HSSFCell cell = row.createCell(0);
		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(headstyle); // 样式，居中
		HSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 20);//设置字体大小
		headstyle.setFont(font);
		headstyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		headstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中

		HSSFCellStyle setBorder = workbook.createCellStyle();
		font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 9);//设置字体大小
		setBorder.setFont(font);
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder.setWrapText(true);//设置自动换行
		setBorder.setAlignment(CellStyle.ALIGN_LEFT);//水平居中
		setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		sheet.setDefaultColumnWidth(10);

		CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,headList.size()-1);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);
		cell.setCellValue(title);
		cell.setCellStyle(headstyle);

		// 在索引0的位置创建行（最顶端的行）
		row = sheet.createRow(1);
		row.setHeightInPoints(20);
		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			cell = row.createCell(i);
			// 定义单元格为字符串类型
			font.setFontHeightInPoints((short) 20);//设置字体大小
			setBorder.setFont(font);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(setBorder);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 2);
			row_value.setHeightInPoints(20);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				cell = row_value.createCell(i);
				if(cell != null){
					// 定义单元格为字符串类型
					font.setFontHeightInPoints((short) 9);//设置字体大小
					setBorder.setFont(font);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(setBorder);
					// 在单元格中输入一些内容
					cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
				}

			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * 导出入库单
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createInStorageExcel(HSSFWorkbook workbook,String excel_name, EmkMInStorageEntity emkMInStorageEntity, List<String> headList,
											List<String> fieldList, List<Map<String, Object>>  dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		//HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFCellStyle headstyle = workbook.createCellStyle(); // 样式对象
		headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

		HSSFSheet sheet = workbook.createSheet("");
		sheet.setColumnWidth(0,3000);
		sheet.setColumnWidth(1,2500);
		sheet.setColumnWidth(2,2500);
		sheet.setColumnWidth(3,2500);
		sheet.setColumnWidth(4,2500);
		sheet.setColumnWidth(5,2500);
		sheet.setColumnWidth(6,1000);
		sheet.setColumnWidth(7,1000);
		sheet.setColumnWidth(8,2000);
		sheet.setColumnWidth(9,2500);
		sheet.setColumnWidth(10,2500);
		sheet.setColumnWidth(11,3000);
		sheet.setColumnWidth(12,2500);
		sheet.setColumnWidth(13,2000);


		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(30);
		HSSFCell cell = row.createCell(0);
		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(headstyle); // 样式，居中
		HSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 20);//设置字体大小
		headstyle.setFont(font);
		headstyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		headstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中

		HSSFCellStyle setBorder = workbook.createCellStyle();
		font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 9);//设置字体大小
		setBorder.setFont(font);
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder.setWrapText(true);//设置自动换行
		setBorder.setAlignment(CellStyle.ALIGN_LEFT);//水平居中
		setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中

		HSSFCellStyle setBorder2 = workbook.createCellStyle();
		setBorder2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder2.setWrapText(true);//设置自动换行
		setBorder2.setAlignment(CellStyle.ALIGN_LEFT);//上下居中
		setBorder2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中


		//创建合并单元格对象
		CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);
		cell.setCellValue("入库单");
		cell.setCellStyle(headstyle);

		row = sheet.createRow(1);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("仓库名称：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue(emkMInStorageEntity.getStorageName());
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(1,1,1,3);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(4);
		cell.setCellValue("采购员：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(1,1,5,9);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(10);
		cell.setCellValue("单据编码：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(1,1,11,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		row = sheet.createRow(2);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("申请人：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(2,2,1,3);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(4);
		cell.setCellValue("入库日期：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(2,2,5,9);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(10);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(2,2,11,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		// ===============================================================
		row = sheet.createRow(3);
		row.setHeightInPoints(20);

		for (int i = 0; i < headList.size(); i++) {
			// 在索引0的位置创建单元格（左上端）
			cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
			cell.setCellStyle(setBorder);

		}
		// ===============================================================

		int z = 4,z2=5;
		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(z);
			row_value.setHeightInPoints(25);

			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				cell = row_value.createCell(i);
				cell.setCellStyle(setBorder);

				if(cell != null){
					// 定义单元格为字符串类型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// 在单元格中输入一些内容
					cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
				}
			}
			row_value = sheet.createRow(z2);
			row_value.setHeightInPoints(25);
			cell = row_value.createCell(0);
			cell.setCellValue("产品编码：");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(1);
			cell.setCellValue(dataMap.get("pro_num").toString());
			cell.setCellStyle(setBorder2);
			cell = row_value.createCell(2);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(3);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(4);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(5);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(6);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(7);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(8);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(9);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(10);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(11);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(12);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(13);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			callRangeAddress = new CellRangeAddress(z2,z2,1,13);//起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeAddress);

			z = z +2;
			z2 = z2 +2;
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * 导出销售出库单
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createSaleExcel(HSSFWorkbook workbook,String excel_name, EmkMOutStorageEntity emkMOutStorage,List<String> headList,
									   List<String> fieldList, List<Map<String, Object>>  dataList)
			throws Exception {
		// 创建新的Excel 工作簿

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFCellStyle headstyle = workbook.createCellStyle(); // 样式对象
		headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

		HSSFSheet sheet = workbook.createSheet("");
		sheet.setColumnWidth(0,3000);
		sheet.setColumnWidth(1,2500);
		sheet.setColumnWidth(2,2500);
		sheet.setColumnWidth(3,2500);
		sheet.setColumnWidth(4,2500);
		sheet.setColumnWidth(5,2500);
		sheet.setColumnWidth(6,1000);
		sheet.setColumnWidth(7,1000);
		sheet.setColumnWidth(8,2000);
		sheet.setColumnWidth(9,2500);
		sheet.setColumnWidth(10,2500);
		sheet.setColumnWidth(11,3000);
		sheet.setColumnWidth(12,2500);
		sheet.setColumnWidth(13,2000);


		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(30);
		HSSFCell cell = row.createCell(0);
		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(headstyle); // 样式，居中
		HSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 20);//设置字体大小
		headstyle.setFont(font);
		headstyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		headstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中

		HSSFCellStyle setBorder = workbook.createCellStyle();
		font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 9);//设置字体大小
		setBorder.setFont(font);
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder.setWrapText(true);//设置自动换行
		setBorder.setAlignment(CellStyle.ALIGN_LEFT);//水平居中
		setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中

		HSSFCellStyle setBorder2 = workbook.createCellStyle();
		setBorder2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder2.setWrapText(true);//设置自动换行
		setBorder2.setAlignment(CellStyle.ALIGN_LEFT);//上下居中
		setBorder2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中


		//创建合并单元格对象
		CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);
		cell.setCellValue("销售出库单");
		cell.setCellStyle(headstyle);

		row = sheet.createRow(1);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("客户：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(1,1,1,3);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(4);
		cell.setCellValue("供货单位：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(1,1,5,9);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(10);
		cell.setCellValue("单据编码：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(1,1,11,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		row = sheet.createRow(2);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("联系人：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(2,2,1,3);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(4);
		cell.setCellValue("发货仓库：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue(emkMOutStorage.getStorageName());
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(2,2,5,9);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(10);
		cell.setCellValue("出库类型：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		if(emkMOutStorage.getType().equals("2")){
			cell.setCellValue("销售出库");
		}
		if(emkMOutStorage.getType().equals("1")){
			cell.setCellValue("临时出库");
		}
		if(emkMOutStorage.getType().equals("3")){
			cell.setCellValue("正常出库");
		}
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(2,2,11,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		row = sheet.createRow(3);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("联系电话：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(3,3,1,3);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(4);
		cell.setCellValue("发货日期：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(3,3,5,9);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(10);
		cell.setCellValue("支付方式：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellValue("电汇");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(3,3,11,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		row = sheet.createRow(4);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("联系地址：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(4);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(4,4,1,9);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(10);
		cell.setCellValue("备注：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(4,4,11,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		// ===============================================================
		row = sheet.createRow(5);
		row.setHeightInPoints(20);

		for (int i = 0; i < headList.size(); i++) {
			// 在索引0的位置创建单元格（左上端）
			cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
			cell.setCellStyle(setBorder);

		}
		// ===============================================================

		int z = 6,z2=7;
		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(z);
			row_value.setHeightInPoints(25);

			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				cell = row_value.createCell(i);
				cell.setCellStyle(setBorder);

				if(cell != null){
					// 定义单元格为字符串类型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// 在单元格中输入一些内容
					cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
				}
			}
			row_value = sheet.createRow(z2);
			row_value.setHeightInPoints(25);
			cell = row_value.createCell(0);
			cell.setCellValue("产品编码：");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(1);
			cell.setCellValue(dataMap.get("pro_num").toString());
			cell.setCellStyle(setBorder2);
			cell = row_value.createCell(2);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(3);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(4);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(5);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(6);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(7);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(8);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(9);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(10);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(11);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(12);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			cell = row_value.createCell(13);
			cell.setCellValue("");
			cell.setCellStyle(setBorder);
			callRangeAddress = new CellRangeAddress(z2,z2,1,13);//起始行,结束行,起始列,结束列
			sheet.addMergedRegion(callRangeAddress);

			z = z +2;
			z2 = z2 +2;
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * 导出使用登记表
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createUseExcel(HSSFWorkbook workbook,String excel_name, EmkMUseEntity emkMUseEntity, List<String> headList,
									  List<String> fieldList, List<Map<String, Object>>  dataList,Map hj)
			throws Exception {
		// 创建新的Excel 工作簿
		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFCellStyle headstyle = workbook.createCellStyle(); // 样式对象
		headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

		HSSFSheet sheet = workbook.createSheet(emkMUseEntity.getCkNo());
		sheet.setColumnWidth(0,4000);
		sheet.setColumnWidth(1,5500);
		sheet.setColumnWidth(2,1000);
		sheet.setColumnWidth(3,1000);
		sheet.setColumnWidth(4,2500);
		sheet.setColumnWidth(5,2500);
		sheet.setColumnWidth(6,5500);
		sheet.setColumnWidth(7,4000);
		sheet.setColumnWidth(8,2500);
		sheet.setColumnWidth(9,2500);
		sheet.setColumnWidth(10,2500);
		sheet.setColumnWidth(11,2500);
		sheet.setColumnWidth(12,2500);
		sheet.setColumnWidth(13,4000);


		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(30);
		HSSFCell cell = row.createCell(0);
		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(headstyle); // 样式，居中
		HSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 20);//设置字体大小
		headstyle.setFont(font);
		headstyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		headstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中

		HSSFCellStyle setBorder = workbook.createCellStyle();
		font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 9);//设置字体大小
		setBorder.setFont(font);
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder.setWrapText(true);//设置自动换行
		setBorder.setAlignment(CellStyle.ALIGN_LEFT);//水平居中
		setBorder.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中

		HSSFCellStyle setBorder2 = workbook.createCellStyle();
		setBorder2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder2.setWrapText(true);//设置自动换行
		setBorder2.setAlignment(CellStyle.ALIGN_LEFT);//上下居中
		setBorder2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中


		//创建合并单元格对象
		CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);
		cell.setCellValue("销售清单");
		cell.setCellStyle(headstyle);

		row = sheet.createRow(1);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("购货单位：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue(emkMUseEntity.getCusName());
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(4);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(1,1,1,5);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(6);
		cell.setCellValue("联系人/联系电话：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(7);
		cell.setCellValue(Utils.notEmpty(emkMUseEntity.getZlxr()) ? emkMUseEntity.getZlxr():""+"/"+(Utils.notEmpty(emkMUseEntity.getWorkphone()) ? emkMUseEntity.getWorkphone():""));
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(10);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(1,1,7,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);


		row = sheet.createRow(2);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("地址：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue(emkMUseEntity.getQyzcAddress());
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(4);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(2,2,1,6);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(7);
		cell.setCellValue("电话：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(10);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(2,2,8,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		row = sheet.createRow(3);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("开户行和账号：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue(Utils.notEmpty(emkMUseEntity.getBankName()) ? emkMUseEntity.getBankName():""+" " +(Utils.notEmpty(emkMUseEntity.getBankAccount()) ? emkMUseEntity.getBankAccount():""));
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(4);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(3,3,1,6);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(10);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(3,3,7,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		row = sheet.createRow(4);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("税号：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue(emkMUseEntity.getSuiNum());
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(4);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);

		callRangeAddress = new CellRangeAddress(4,4,1,6);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(7);
		cell.setCellValue("发货日期：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(10);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(4,4,8,10);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(4,4,11,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		row = sheet.createRow(5);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("收货地址：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue(emkMUseEntity.getSuiNum());
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(4);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);

		callRangeAddress = new CellRangeAddress(5,5,1,6);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		cell = row.createCell(7);
		cell.setCellValue("收货人/电话：");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(10);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(5,5,8,10);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		callRangeAddress = new CellRangeAddress(4,4,11,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		// ===============================================================
		row = sheet.createRow(6);
		row.setHeightInPoints(20);

		for (int i = 0; i < headList.size(); i++) {
			// 在索引0的位置创建单元格（左上端）
			cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
			cell.setCellStyle(setBorder);

		}
		// ===============================================================

		int z = 7;
		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(z);
			row_value.setHeightInPoints(25);

			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				cell = row_value.createCell(i);
				cell.setCellStyle(setBorder);

				if(cell != null){
					// 定义单元格为字符串类型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// 在单元格中输入一些内容
					cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
				}
			}
			z = z +1;
			// ===============================================================
		}
		callRangeAddress = new CellRangeAddress(7,dataList.size()+6,13,13);//起始行,结束行,起始列,结束列
		sheet.addMergedRegion(callRangeAddress);

		row = sheet.createRow(dataList.size()+7);
		row.setHeightInPoints(20);
		cell = row.createCell(0);
		cell.setCellValue("合计");
		cell.setCellStyle(setBorder);
		cell = row.createCell(1);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(2);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(3);
		cell.setCellValue(hj.get("total").toString());
		cell.setCellStyle(setBorder);
		cell = row.createCell(4);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(5);
		cell.setCellValue(hj.get("hj").toString());
		cell.setCellStyle(setBorder);

		cell = row.createCell(6);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(7);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(8);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(9);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(10);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(11);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(12);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);
		cell = row.createCell(13);
		cell.setCellValue("");
		cell.setCellStyle(setBorder);


		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws HSSFWorkbook
	 */
	public static HSSFWorkbook createExcel(List<String> headList,
			List<String> fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
			}
			// ===============================================================
		}
		return workbook;
	}

	private static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			if (obj instanceof String) {
				return (String) obj;
			} else if (obj instanceof Date) {
				return null;// DateUtil.dateToString((Date)
							// obj,DateUtil.DATESTYLE_SHORT_EX);
			} else {
				return obj.toString();
			}
		}
	}

	/**
	 * 
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题部分
	 * @param valueList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void bulidExcel(String excel_name, String[] headList,
			List<String[]> valueList) throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================

		for (int n = 0; n < valueList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			String[] valueArray = valueList.get(n);
			// ===============================================================
			for (int i = 0; i < valueArray.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(valueArray[i]);
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excel_name) throws Exception {
		// 结果集
		List<String[]> list = new ArrayList<String[]>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream(
				excel_name));

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if(hssfrow!=null){
			int col = hssfrow.getPhysicalNumberOfCells();
			// 单行数据
			String[] arrayString = new String[col];
			for (int i = 0; i < col; i++) {
				HSSFCell cell = hssfrow.getCell(i);
				if (cell == null) {
					arrayString[i] = "";
				} else if (cell.getCellType() == 0) {
					// arrayString[i] = new Double(cell.getNumericCellValue()).toString();
					if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) { 
						  if (HSSFDateUtil.isCellDateFormatted(cell)) {    
						    Date d = cell.getDateCellValue();    
//						    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");    
						     DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						    arrayString[i] = formater.format(d);   
						   } else {    
						       arrayString[i] = new BigDecimal(cell.getNumericCellValue()).longValue()+"";    
						}
					}
				} else {// 如果EXCEL表格中的数据类型为字符串型
					arrayString[i] = cell.getStringCellValue().trim();
				}
			}
			list.add(arrayString);
		}
		}
		return list;
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcelByList(String excel_name)
			throws Exception {
		// 结果集
		List<List<Object>> list = new ArrayList<List<Object>>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream(
				excel_name));

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				List<Object> arrayString = new ArrayList<Object>();
				for (int i = 0; i < col; i++) {
					HSSFCell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					} else if (cell.getCellType() == 0) {
						arrayString.add(new Double(cell.getNumericCellValue())
								.toString());
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString.add(cell.getStringCellValue().trim());
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcelByInputStream(
			InputStream inputstream) throws Exception {
		// 结果集
		List<List<Object>> list = new ArrayList<List<Object>>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(inputstream);

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数

		// //org.jeecgframework.core.util.LogUtil.info("excel行数： "+hssfsheet.getPhysicalNumberOfRows());
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				List<Object> arrayString = new ArrayList<Object>();
				for (int i = 0; i < col; i++) {
					HSSFCell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					} else if (cell.getCellType() == 0) {
						arrayString.add(new Double(cell.getNumericCellValue())
								.toString());
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString.add(cell.getStringCellValue().trim());
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

	/**
	 * 导入 excel
	 * 
	 * @param file : Excel文件
	 * @param pojoClass : 对应的导入对象 (每行记录)
	 * @return
	 */
	@Deprecated
	public static Collection importExcel(File file, Class pojoClass) {
		try {
			// 将传入的File构造为FileInputStream;
			FileInputStream in = new FileInputStream(file);
			return importExcelByIs(in, pojoClass);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 导入 excel
	 * 
	 * @param inputstream : 文件输入流
	 * @param pojoClass : 对应的导入对象 (每行记录)
	 * @return
	 */
	@Deprecated
	public static Collection importExcelByIs(InputStream inputstream, Class pojoClass) {
		Collection dist = new ArrayList<Object>();
		try {
			// 得到目标目标类的所有的字段列表
			Field filed[] = pojoClass.getDeclaredFields();
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
			Map<String, Method> fieldSetMap = new HashMap<String, Method>();
			Map<String, Method> fieldSetConvertMap = new HashMap<String, Method>();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				// 得到单个字段上的Annotation
				Excel excel = f.getAnnotation(Excel.class);
				// 如果标识了Annotationd的话
				if (excel != null) {
					// 构造设置了Annotation的字段的Setter方法
					String fieldname = f.getName();
					String setMethodName = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
					// 构造调用的method，
					Method setMethod = pojoClass.getMethod(setMethodName, new Class[] { f.getType() });
					// 将这个method以Annotaion的名字为key来存入。
					// 对于重名将导致 覆盖 失败，对于此处的限制需要
					fieldSetMap.put(excel.exportName(), setMethod);
					if (excel.importConvertSign() == 1) {
						//----------------------------------------------------------------

						// 用get/setXxxxConvert方法名的话， 由于直接使用了数据库绑定的Entity对象，注入会有冲突
						StringBuffer setConvertMethodName = new StringBuffer("convertSet");
						setConvertMethodName.append(fieldname.substring(0, 1).toUpperCase());
						setConvertMethodName.append(fieldname.substring(1));

						//----------------------------------------------------------------
						Method getConvertMethod = pojoClass.getMethod(setConvertMethodName.toString(), new Class[] { String.class });
						fieldSetConvertMap.put(excel.exportName(), getConvertMethod);
					}
				}
			}
			// 将传入的File构造为FileInputStream;
			// // 得到工作表
			HSSFWorkbook book = new HSSFWorkbook(inputstream);
			// // 得到第一页
			HSSFSheet sheet = book.getSheetAt(0);
			// // 得到第一面的所有行
			Iterator<Row> row = sheet.rowIterator();
			// 得到第一行，也就是标题行
			Row title = row.next();
			// 得到第一行的所有列
			Iterator<Cell> cellTitle = title.cellIterator();
			// 将标题的文字内容放入到一个map中。
			Map titlemap = new HashMap();
			// 从标题第一列开始
			int i = 0;
			// 循环标题所有的列
			while (cellTitle.hasNext()) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				titlemap.put(i, value);
				i = i + 1;
			}
			// 用来格式化日期的DateFormat
			// SimpleDateFormat sf;
			while (row.hasNext()) {
				// 标题下的第一行
				Row rown = row.next();
				// 行的所有列
				Iterator<Cell> cellbody = rown.cellIterator();
				// 得到传入类的实例
				Object tObject = pojoClass.newInstance();
				int k = 0;
				// 遍历一行的列
				while (cellbody.hasNext()) {
					Cell cell = cellbody.next();
					// 这里得到此列的对应的标题
					String titleString = (String) titlemap.get(k);
					// 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
					if (fieldSetMap.containsKey(titleString)) {
						Method setMethod = (Method) fieldSetMap.get(titleString);
						// 得到setter方法的参数
						Type[] ts = setMethod.getGenericParameterTypes();
						// 只要一个参数
						String xclass = ts[0].toString();
						// 判断参数类型
						if (Cell.CELL_TYPE_STRING == cell.getCellType()
								&& fieldSetConvertMap.containsKey(titleString)) {
							// 目前只支持从String转换
							fieldSetConvertMap.get(titleString).invoke(tObject, cell.getStringCellValue());
						} else {
							if (xclass.equals("class java.lang.String")) {
								// 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了：
								cell.setCellType(Cell.CELL_TYPE_STRING);
								setMethod.invoke(tObject, cell.getStringCellValue());
							} else if (xclass.equals("class java.util.Date")) {

								Date cellDate = null;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									// 日期格式
									cellDate = cell.getDateCellValue();
								} else {	// 全认为是  Cell.CELL_TYPE_STRING: 如果不是 yyyy-mm-dd hh:mm:ss 的格式就不对(wait to do:有局限性)
									cellDate = stringToDate(cell.getStringCellValue());
								}
			                    setMethod.invoke(tObject,cellDate);

								//// --------------------------------------------------------------------------------------------
								//String cellValue = cell.getStringCellValue();
								//Date theDate = stringToDate(cellValue);
								//setMethod.invoke(tObject, theDate);

								//// --------------------------------------------------------------------------------------------
							} else if (xclass.equals("class java.lang.Boolean")) {
								boolean valBool;
								if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
									valBool = cell.getBooleanCellValue();
								} else {// 全认为是  Cell.CELL_TYPE_STRING
									valBool = cell.getStringCellValue().equalsIgnoreCase("true") 
											|| (!cell.getStringCellValue().equals("0"));
								}
								setMethod.invoke(tObject, valBool);
							} else if (xclass.equals("class java.lang.Integer")) {
								Integer valInt;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valInt = (new Double(cell.getNumericCellValue())).intValue();
								} else {// 全认为是  Cell.CELL_TYPE_STRING
									valInt = new Integer(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valInt);
							} else if (xclass.equals("class java.lang.Long")) {
								Long valLong;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valLong = (new Double(cell.getNumericCellValue())).longValue();
								} else {// 全认为是  Cell.CELL_TYPE_STRING
									valLong = new Long(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valLong);
							} else if (xclass.equals("class java.math.BigDecimal")) {
								BigDecimal valDecimal;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valDecimal = new BigDecimal(cell.getNumericCellValue());
								} else {// 全认为是  Cell.CELL_TYPE_STRING
									valDecimal = new BigDecimal(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valDecimal);
								//// ----------------------------------------------------------------

								//setMethod.invoke(tObject, new BigDecimal(cell.getStringCellValue()));

								//// ----------------------------------------------------------------

							}
						}
					}
					// 下一列
					k = k + 1;
				}
				dist.add(tObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}

	// --------------------------------------------------------------------------------------------

	/**
	 * 字符串转换为Date类型数据（限定格式      YYYY-MM-DD hh:mm:ss）或（YYYY/MM/DD hh:mm:ss）
	 * 
	 * @param cellValue : 字符串类型的日期数据
	 * @return
	 */
	private static Date stringToDate(String cellValue) {
		if (cellValue.length() > 19)
			cellValue = cellValue.substring(0, 19);
		Calendar calendar = Calendar.getInstance();
		String[] dateStr = cellValue.split(" ");
		String[] dateInfo = dateStr[0].split("-");
		if (dateInfo.length != 3) {
			dateInfo = dateStr[0].split("/");	// 让  yyyy/mm/dd 的格式也支持
		}
		if (dateInfo.length == 3) {
			int year = Integer.parseInt(dateInfo[0]);
			int month = Integer.parseInt(dateInfo[1])-1;	// 0~11
			int day = Integer.parseInt(dateInfo[2]);
			calendar.set(year, month, day);
		} else {
			return null;	// 格式不正确
		}
		if (dateStr.length > 1) {// 有时间（限定格式     hh:mm:ss）
			String[] timeStr = dateStr[1].split(":");
			if (timeStr.length == 3) {
				int hour = Integer.parseInt(timeStr[0]);
				int minute = Integer.parseInt(timeStr[1]);
				int second = Integer.parseInt(timeStr[2]);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, second);
			} else {
				return null;	// 格式不正确
			}
		}
		return calendar.getTime();
	}

	// --------------------------------------------------------------------------------------------

}
