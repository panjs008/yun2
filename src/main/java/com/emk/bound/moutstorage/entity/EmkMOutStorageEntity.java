package com.emk.bound.moutstorage.entity;

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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 出库申请表
 * @author onlineGenerator
 * @date 2018-09-10 20:31:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_m_out_storage", schema = "")
@SuppressWarnings("serial")
public class EmkMOutStorageEntity implements java.io.Serializable {
	/**业务员*/
	private String businesser;
	/**业务员ID*/
	private String businesserName;

	@Excel(name = "客户名称")
	private String cusName;
	private String total;
	private String money;

	/**类型*/
	private String type;
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

	/**a01a07a01*/
	@Excel(name="a01a07a01",width=15)
	private String a01a07a01;
	/**a01a07a02*/
	@Excel(name="a01a07a02",width=15)
	private String a01a07a02;
	/**a01a07a03*/
	@Excel(name="a01a07a03",width=15)
	private String a01a07a03;
	/**a01a07a04*/
	@Excel(name="a01a07a04",width=15)
	private String a01a07a04;
	/**a01a07a05*/
	@Excel(name="a01a07a05",width=15)
	private String a01a07a05;
	/**a01a07a06*/
	@Excel(name="a01a07a06",width=15)
	private String a01a07a06;
	/**a01a07a07*/
	@Excel(name="a01a07a07",width=15)
	private String a01a07a07;
	private String a01a07a08;
	/**a01a07a08*/
	@Excel(name="a01a07a09",width=15)
	private String a01a07a09;
	@Excel(name="a01a07a10",width=15)
	private String a01a07a10;
	@Excel(name="a01a07a11",width=15)
	private String a01a07a11;
	@Excel(name="a01a07a12",width=15)
	private String a01a07a12;
	@Excel(name="a01a07a13",width=15)
	private String a01a07a13;
	@Excel(name="a01a07a14",width=15)
	private String a01a07a14;
	@Excel(name="a01a07a15",width=15)
	private String a01a07a15;
	@Excel(name="a01a07a16",width=15)
	private String a01a07a16;
	@Excel(name="a01a07a17",width=15)
	private String a01a07a17;
	@Excel(name="a01a07a18",width=15)
	private String a01a07a18;
	@Excel(name="a01a07a19",width=15)
	private String a01a07a19;
	@Excel(name="a01a07a20",width=15)
	private String a01a07a20;

	private String yfMoney;
	private String yhMoney;
	private String shMoney;
	private String bcqkMoney;
	private String qcqkMoney;
	private String preBill;

	private String ljqkMoney;
	private String bankMoney;

	private String isInvoice;

	@Column(name ="is_invoice",nullable=true,length=32)
	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	private String storageId;
	@Excel(name = "仓库名称")
	private String storageName;
	/**
	 * 库位ID
	 */
	private String positionId;
	/**
	 * 库位名称
	 */
	private String positionName;
	/**状态*/
	private String state;
	private String payState;
	private String recevieState;
	private String flag;
	private String proOrderId;
	private String departId;
	private String orgCode;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务员
	 */

	@Column(name ="BUSINESSER",nullable=true,length=32)
	public String getBusinesser(){
		return this.businesser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务员
	 */
	public void setBusinesser(String businesser){
		this.businesser = businesser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务员ID
	 */

	@Column(name ="BUSINESSER_NAME",nullable=true,length=32)
	public String getBusinesserName(){
		return this.businesserName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务员ID
	 */
	public void setBusinesserName(String businesserName){
		this.businesserName = businesserName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  款号
	 */

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
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=true,length=32)
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
	//@Formula("(select p.NAME_ from act_ru_task p where p.ASSIGNEE_ = id)")
	//@Column(name ="CREATE_NAME",nullable=true,length=32)
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

	@Column(name ="CREATE_BY",nullable=true,length=32)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=32)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */

	@Column(name ="SYS_ORG_CODE",nullable=true,length=32)
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
	 *@return: java.lang.String  状态
	 */

	@Column(name ="STATE",nullable=true,length=32)
	public String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setState(String state){
		this.state = state;
	}

	@Column(name="total", nullable=true, length=32)
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}


	@Column(name = "yh_money", nullable = true, length = 32)
	public String getYhMoney() {
		return yhMoney;
	}

	public void setYhMoney(String yhMoney) {
		this.yhMoney = yhMoney;
	}

	@Column(name = "sh_money", nullable = true, length = 32)
	public String getShMoney() {
		return shMoney;
	}

	public void setShMoney(String shMoney) {
		this.shMoney = shMoney;
	}

	@Column(name = "bcq_money", nullable = true, length = 32)
	public String getBcqkMoney() {
		return bcqkMoney;
	}

	public void setBcqkMoney(String bcqkMoney) {
		this.bcqkMoney = bcqkMoney;
	}

	@Column(name = "qcqk_money", nullable = true, length = 32)
	public String getQcqkMoney() {
		return qcqkMoney;
	}

	public void setQcqkMoney(String qcqkMoney) {
		this.qcqkMoney = qcqkMoney;
	}

	@Column(name = "ljqk_money", nullable = true, length = 32)
	public String getLjqkMoney() {
		return ljqkMoney;
	}

	public void setLjqkMoney(String ljqkMoney) {
		this.ljqkMoney = ljqkMoney;
	}

	@Column(name = "bank_money", nullable = true, length = 32)
	public String getBankMoney() {
		return bankMoney;
	}

	public void setBankMoney(String bankMoney) {
		this.bankMoney = bankMoney;
	}

	@Column(name = "yf_money", nullable = true, length = 32)
	public String getYfMoney() {
		return yfMoney;
	}

	public void setYfMoney(String yfMoney) {
		this.yfMoney = yfMoney;
	}

	@Column(name = "pre_bill", nullable = true, length = 32)
	public String getPreBill() {
		return preBill;
	}

	public void setPreBill(String preBill) {
		this.preBill = preBill;
	}

	@Column(name = "a01a07a01", nullable = true, length = 32)
	public String getA01a07a01() {
		return a01a07a01;
	}

	public void setA01a07a01(String a01a07a01) {
		this.a01a07a01 = a01a07a01;
	}

	@Column(name = "a01a07a02", nullable = true, length = 32)
	public String getA01a07a02() {
		return a01a07a02;
	}

	public void setA01a07a02(String a01a07a02) {
		this.a01a07a02 = a01a07a02;
	}

	@Column(name = "a01a07a03", nullable = true, length = 32)
	public String getA01a07a03() {
		return a01a07a03;
	}

	public void setA01a07a03(String a01a07a03) {
		this.a01a07a03 = a01a07a03;
	}

	@Column(name = "a01a07a04", nullable = true, length = 32)
	public String getA01a07a04() {
		return a01a07a04;
	}

	public void setA01a07a04(String a01a07a04) {
		this.a01a07a04 = a01a07a04;
	}

	@Column(name = "a01a07a05", nullable = true, length = 32)
	public String getA01a07a05() {
		return a01a07a05;
	}

	public void setA01a07a05(String a01a07a05) {
		this.a01a07a05 = a01a07a05;
	}

	@Column(name = "a01a07a06", nullable = true, length = 32)
	public String getA01a07a06() {
		return a01a07a06;
	}

	public void setA01a07a06(String a01a07a06) {
		this.a01a07a06 = a01a07a06;
	}

	@Column(name = "a01a07a07", nullable = true, length = 32)
	public String getA01a07a07() {
		return a01a07a07;
	}

	public void setA01a07a07(String a01a07a07) {
		this.a01a07a07 = a01a07a07;
	}

	@Column(name = "a01a07a08", nullable = true, length = 32)
	public String getA01a07a08() {
		return a01a07a08;
	}

	public void setA01a07a08(String a01a07a08) {
		this.a01a07a08 = a01a07a08;
	}

	@Column(name = "a01a07a09", nullable = true, length = 32)
	public String getA01a07a09() {
		return a01a07a09;
	}

	public void setA01a07a09(String a01a07a09) {
		this.a01a07a09 = a01a07a09;
	}

	@Column(name = "a01a07a10", nullable = true, length = 32)
	public String getA01a07a10() {
		return a01a07a10;
	}

	public void setA01a07a10(String a01a07a10) {
		this.a01a07a10 = a01a07a10;
	}

	@Column(name = "a01a07a11", nullable = true, length = 32)
	public String getA01a07a11() {
		return a01a07a11;
	}

	public void setA01a07a11(String a01a07a11) {
		this.a01a07a11 = a01a07a11;
	}

	@Column(name = "a01a07a12", nullable = true, length = 32)
	public String getA01a07a12() {
		return a01a07a12;
	}

	public void setA01a07a12(String a01a07a12) {
		this.a01a07a12 = a01a07a12;
	}

	@Column(name = "a01a07a13", nullable = true, length = 32)
	public String getA01a07a13() {
		return a01a07a13;
	}

	public void setA01a07a13(String a01a07a13) {
		this.a01a07a13 = a01a07a13;
	}

	@Column(name = "a01a07a14", nullable = true, length = 32)
	public String getA01a07a14() {
		return a01a07a14;
	}

	public void setA01a07a14(String a01a07a14) {
		this.a01a07a14 = a01a07a14;
	}

	@Column(name = "a01a07a15", nullable = true, length = 32)
	public String getA01a07a15() {
		return a01a07a15;
	}

	public void setA01a07a15(String a01a07a15) {
		this.a01a07a15 = a01a07a15;
	}

	@Column(name = "a01a07a16", nullable = true, length = 32)
	public String getA01a07a16() {
		return a01a07a16;
	}

	public void setA01a07a16(String a01a07a16) {
		this.a01a07a16 = a01a07a16;
	}

	@Column(name = "a01a07a17", nullable = true, length = 32)
	public String getA01a07a17() {
		return a01a07a17;
	}

	public void setA01a07a17(String a01a07a17) {
		this.a01a07a17 = a01a07a17;
	}

	@Column(name = "a01a07a18", nullable = true, length = 32)
	public String getA01a07a18() {
		return a01a07a18;
	}

	public void setA01a07a18(String a01a07a18) {
		this.a01a07a18 = a01a07a18;
	}

	@Column(name = "a01a07a19", nullable = true, length = 32)
	public String getA01a07a19() {
		return a01a07a19;
	}

	public void setA01a07a19(String a01a07a19) {
		this.a01a07a19 = a01a07a19;
	}

	@Column(name = "a01a07a20", nullable = true, length = 32)
	public String getA01a07a20() {
		return a01a07a20;
	}

	public void setA01a07a20(String a01a07a20) {
		this.a01a07a20 = a01a07a20;
	}

	@Column(name = "money", nullable = true, length = 32)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Column(name = "pay_state", nullable = true, length = 32)
	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	@Column(name = "recevie_state", nullable = true, length = 32)
	public String getRecevieState() {
		return recevieState;
	}

	public void setRecevieState(String recevieState) {
		this.recevieState = recevieState;
	}

	@Column(name ="storage_id",nullable=true,length=32)
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	@Column(name = "STORAGE_NAME", nullable = true, length = 256)
	public String getStorageName() {
		return this.storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  库位ID
	 */

	@Column(name = "POSITION_ID", nullable = true, length = 32)
	public String getPositionId() {
		return this.positionId;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String  库位ID
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  库位名称
	 */

	@Column(name = "POSITION_NAME", nullable = true, length = 32)
	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Column(name = "flag", nullable = true, length = 32)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "pro_order_id", nullable = true, length = 32)
	public String getProOrderId() {
		return proOrderId;
	}

	public void setProOrderId(String proOrderId) {
		this.proOrderId = proOrderId;
	}

	@Column(name ="depart_id",nullable=true,length=32)
	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	@Column(name ="org_code",nullable=true,length=32)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name ="cus_name",nullable=true,length=32)
	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
}
