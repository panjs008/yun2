package com.emk.bound.storageconnact.entity;

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
 * @Description: 库存组装表
 * @author onlineGenerator
 * @date 2020-02-11 20:12:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_storage_connact", schema = "")
@SuppressWarnings("serial")
public class EmkStorageConnactEntity implements java.io.Serializable {
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
	/**入库仓库*/
	@Excel(name="入库仓库",width=15)
	private String inStorageId;
	/**仓库名称*/
	@Excel(name="仓库名称",width=15)
	private String inStorageName;
	/**出货仓库*/
	@Excel(name="出货仓库",width=15)
	private String outStorageId;
	/**仓库名称*/
	@Excel(name="仓库名称",width=15)
	private String outStorageName;
	/**类型*/
	@Excel(name="类型",width=15)
	private String type;
	/**单号*/
	@Excel(name="单号",width=15)
	private String connactNo;
	/**日期*/
	@Excel(name="日期",width=15)
	private String makeDate;
	/**商品名称*/
	@Excel(name="商品名称",width=15)
	private String proZnNameB;
	/**商品ID*/
	@Excel(name="商品ID",width=15)
	private String proId;
	/**规格型号*/
	@Excel(name="规格型号",width=15)
	private String brandB;
	/**数量*/
	@Excel(name="数量",width=15)
	private String total;
	/**单价*/
	@Excel(name="单价",width=15)
	private String price;
	private String money;
	/**备注*/
	@Excel(name="备注",width=15)
	private String remark;
	/**操作员ID*/
	@Excel(name="操作员ID",width=15)
	private String userId;
	/**操作员*/
	@Excel(name="操作员",width=15)
	private String realName;
	private String proNumB;
	private String unitB;
	private String a01a01a01B;


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

	@Column(name ="ORG_CODE",nullable=true,length=32)
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
	 *@return: java.lang.String  入库仓库
	 */

	@Column(name ="IN_STORAGE_ID",nullable=true,length=32)
	public String getInStorageId(){
		return this.inStorageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  入库仓库
	 */
	public void setInStorageId(String inStorageId){
		this.inStorageId = inStorageId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库名称
	 */

	@Column(name ="IN_STORAGE_NAME",nullable=true,length=32)
	public String getInStorageName(){
		return this.inStorageName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库名称
	 */
	public void setInStorageName(String inStorageName){
		this.inStorageName = inStorageName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货仓库
	 */

	@Column(name ="OUT_STORAGE_ID",nullable=true,length=32)
	public String getOutStorageId(){
		return this.outStorageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货仓库
	 */
	public void setOutStorageId(String outStorageId){
		this.outStorageId = outStorageId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库名称
	 */

	@Column(name ="OUT_STORAGE_NAME",nullable=true,length=32)
	public String getOutStorageName(){
		return this.outStorageName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库名称
	 */
	public void setOutStorageName(String outStorageName){
		this.outStorageName = outStorageName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */

	@Column(name ="TYPE",nullable=true,length=32)
	public String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单号
	 */

	@Column(name ="CONNACT_NO",nullable=true,length=32)
	public String getConnactNo(){
		return this.connactNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单号
	 */
	public void setConnactNo(String connactNo){
		this.connactNo = connactNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  日期
	 */

	@Column(name ="MAKE_DATE",nullable=true,length=32)
	public String getMakeDate(){
		return this.makeDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  日期
	 */
	public void setMakeDate(String makeDate){
		this.makeDate = makeDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */

	@Column(name ="PRO_ZN_NAME",nullable=true,length=32)
	public String getProZnNameB(){
		return this.proZnNameB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setProZnNameB(String proZnName){
		this.proZnNameB = proZnName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品ID
	 */

	@Column(name ="PRO_ID",nullable=true,length=32)
	public String getProId(){
		return this.proId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品ID
	 */
	public void setProId(String proId){
		this.proId = proId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格型号
	 */

	@Column(name ="BRAND",nullable=true,length=256)
	public String getBrandB(){
		return this.brandB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格型号
	 */
	public void setBrandB(String brand){
		this.brandB = brand;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */

	@Column(name ="TOTAL",nullable=true,length=32)
	public String getTotal(){
		return this.total;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setTotal(String total){
		this.total = total;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单价
	 */

	@Column(name ="PRICE",nullable=true,length=32)
	public String getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单价
	 */
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="REMARK",nullable=true,length=256)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作员ID
	 */

	@Column(name ="USER_ID",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作员ID
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作员
	 */

	@Column(name ="REAL_NAME",nullable=true,length=32)
	public String getRealName(){
		return this.realName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作员
	 */
	public void setRealName(String realName){
		this.realName = realName;
	}

	@Column(name ="money",nullable=true,length=32)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Column(name ="pro_num",nullable=true,length=32)
	public String getProNumB() {
		return proNumB;
	}

	public void setProNumB(String proNum) {
		this.proNumB = proNum;
	}

	@Column(name ="unit",nullable=true,length=32)
	public String getUnitB() {
		return unitB;
	}

	public void setUnitB(String unit) {
		this.unitB = unit;
	}

	@Column(name ="a01a01a01",nullable=true,length=32)
	public String getA01a01a01B() {
		return a01a01a01B;
	}

	public void setA01a01a01B(String a01a01a01B) {
		this.a01a01a01B = a01a01a01B;
	}

}
