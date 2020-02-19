package com.emk.bound.stroagecheckdetail.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 盘点明细
 * @author onlineGenerator
 * @date 2020-02-17 21:33:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_stroage_check_detail", schema = "")
@SuppressWarnings("serial")
public class EmkStroageCheckDetailEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**所属部门*/
	private String sysOrgCode;
	/**部门ID*/
	@Excel(name="部门ID",width=15)
	private String departId;
	/**部门代码*/
	@Excel(name="部门代码",width=15)
	private String orgCode;
	/**盘点表ID*/
	@Excel(name="盘点表ID",width=15)
	private String checkId;
	/**仓库ID*/
	@Excel(name="仓库ID",width=15)
	private String storageId;
	/**仓库名称*/
	@Excel(name="仓库名称",width=15)
	private String storageName;
	/**产品编号*/
	@Excel(name="产品编号",width=15)
	private String proNum;
	/**产品名称*/
	@Excel(name="产品名称",width=15)
	private String proName;
	/**规格*/
	@Excel(name="规格",width=15)
	private String guig;
	/**型号*/
	@Excel(name="型号",width=15)
	private String brand;
	/**颜色*/
	@Excel(name="颜色",width=15)
	private String color;
	/**尺码*/
	@Excel(name="尺码",width=15)
	private String size;
	/**单位*/
	@Excel(name="单位",width=15)
	private String unit;
	/**账面数量*/
	@Excel(name="账面数量",width=15)
	private String billTotal;
	/**盘点数量*/
	@Excel(name="盘点数量",width=15)
	private String checkTotal;
	/**盈亏数量*/
	@Excel(name="盈亏数量",width=15)
	private String winTotal;
	/**成本价*/
	@Excel(name="成本价",width=15)
	private String costPrice;
	/**金额*/
	@Excel(name="金额",width=15)
	private String money;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */

	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门ID
	 */

	@Column(name ="DEPART_ID",nullable=true,length=32)
	public String getDepartId(){
		return this.departId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门ID
	 */
	public void setDepartId(String departId){
		this.departId = departId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门代码
	 */

	@Column(name ="ORG_CODE",nullable=true,length=256)
	public String getOrgCode(){
		return this.orgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门代码
	 */
	public void setOrgCode(String orgCode){
		this.orgCode = orgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  盘点表ID
	 */

	@Column(name ="CHECK_ID",nullable=true,length=32)
	public String getCheckId(){
		return this.checkId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盘点表ID
	 */
	public void setCheckId(String checkId){
		this.checkId = checkId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库ID
	 */

	@Column(name ="STORAGE_ID",nullable=true,length=32)
	public String getStorageId(){
		return this.storageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库ID
	 */
	public void setStorageId(String storageId){
		this.storageId = storageId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库名称
	 */

	@Column(name ="STORAGE_NAME",nullable=true,length=32)
	public String getStorageName(){
		return this.storageName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库名称
	 */
	public void setStorageName(String storageName){
		this.storageName = storageName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产品编号
	 */

	@Column(name ="PRO_NUM",nullable=true,length=32)
	public String getProNum(){
		return this.proNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品编号
	 */
	public void setProNum(String proNum){
		this.proNum = proNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产品名称
	 */

	@Column(name ="PRO_NAME",nullable=true,length=56)
	public String getProName(){
		return this.proName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品名称
	 */
	public void setProName(String proName){
		this.proName = proName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格
	 */

	@Column(name ="GUIG",nullable=true,length=56)
	public String getGuig(){
		return this.guig;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格
	 */
	public void setGuig(String guig){
		this.guig = guig;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  型号
	 */

	@Column(name ="BRAND",nullable=true,length=32)
	public String getBrand(){
		return this.brand;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  型号
	 */
	public void setBrand(String brand){
		this.brand = brand;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  颜色
	 */

	@Column(name ="COLOR",nullable=true,length=32)
	public String getColor(){
		return this.color;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  颜色
	 */
	public void setColor(String color){
		this.color = color;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  尺码
	 */

	@Column(name ="SIZE",nullable=true,length=32)
	public String getSize(){
		return this.size;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  尺码
	 */
	public void setSize(String size){
		this.size = size;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */

	@Column(name ="UNIT",nullable=true,length=32)
	public String getUnit(){
		return this.unit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnit(String unit){
		this.unit = unit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账面数量
	 */

	@Column(name ="BILL_TOTAL",nullable=true,length=32)
	public String getBillTotal(){
		return this.billTotal;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账面数量
	 */
	public void setBillTotal(String billTotal){
		this.billTotal = billTotal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  盘点数量
	 */

	@Column(name ="CHECK_TOTAL",nullable=true,length=32)
	public String getCheckTotal(){
		return this.checkTotal;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盘点数量
	 */
	public void setCheckTotal(String checkTotal){
		this.checkTotal = checkTotal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  盈亏数量
	 */

	@Column(name ="WIN_TOTAL",nullable=true,length=32)
	public String getWinTotal(){
		return this.winTotal;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盈亏数量
	 */
	public void setWinTotal(String winTotal){
		this.winTotal = winTotal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成本价
	 */

	@Column(name ="COST_PRICE",nullable=true,length=32)
	public String getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  成本价
	 */
	public void setCostPrice(String costPrice){
		this.costPrice = costPrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  金额
	 */

	@Column(name ="MONEY",nullable=true,length=32)
	public String getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  金额
	 */
	public void setMoney(String money){
		this.money = money;
	}
}
