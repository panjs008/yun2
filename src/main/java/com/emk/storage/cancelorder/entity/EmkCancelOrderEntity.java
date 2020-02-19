package com.emk.storage.cancelorder.entity;

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
 * @Description: 退货表
 * @author onlineGenerator
 * @date 2019-09-02 11:50:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_cancel_order", schema = "")
@SuppressWarnings("serial")
public class EmkCancelOrderEntity implements java.io.Serializable {
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

	/**订单号*/
	@Excel(name="订单号",width=15)
	private String orderNo;
	private String orderId;

	/**退货日期*/
	@Excel(name="退货日期",width=15)
	private String cancelDate;
	/**出货批次*/
	@Excel(name="出货批次",width=15)
	private String betch;
	/**状态*/
	@Excel(name="状态",width=15)
	private String state;
	private String payState;
	private String recevieState;
	private String departId;
	private String orgCode;
	private String lxr;
	private String telphone;
	private String address;
	private String sendDate;
	private String yfMoney;
	private String yhMoney;
	private String shMoney;
	private String bcqkMoney;
	private String qcqkMoney;
	private String preBill;

	private String ljqkMoney;
	private String bankMoney;

	private String total;
	private String money;
	private String gys;
	/**业务员*/
	private String businesser;
	/**业务员ID*/
	private String businesserName;
	private String storageId;
	@Excel(name = "仓库名称")
	private String storageName;
	/**a01a16a01*/
	@Excel(name="a01a16a01",width=15)
	private String a01a16a01;
	/**a01a16a02*/
	@Excel(name="a01a16a02",width=15)
	private String a01a16a02;
	/**a01a16a03*/
	@Excel(name="a01a16a03",width=15)
	private String a01a16a03;
	/**a01a16a04*/
	@Excel(name="a01a16a04",width=15)
	private String a01a16a04;
	/**a01a16a05*/
	@Excel(name="a01a16a05",width=15)
	private String a01a16a05;
	/**a01a16a06*/
	@Excel(name="a01a16a06",width=15)
	private String a01a16a06;
	/**a01a16a07*/
	@Excel(name="a01a16a07",width=15)
	private String a01a16a07;
	private String a01a16a08;
	/**a01a16a08*/
	@Excel(name="a01a16a09",width=15)
	private String a01a16a09;
	@Excel(name="a01a16a10",width=15)
	private String a01a16a10;
	@Excel(name="a01a16a11",width=15)
	private String a01a16a11;
	@Excel(name="a01a16a12",width=15)
	private String a01a16a12;
	@Excel(name="a01a16a13",width=15)
	private String a01a16a13;
	@Excel(name="a01a16a14",width=15)
	private String a01a16a14;
	@Excel(name="a01a16a15",width=15)
	private String a01a16a15;
	@Excel(name="a01a16a16",width=15)
	private String a01a16a16;
	@Excel(name="a01a16a17",width=15)
	private String a01a16a17;
	@Excel(name="a01a16a18",width=15)
	private String a01a16a18;
	@Excel(name="a01a16a19",width=15)
	private String a01a16a19;
	@Excel(name="a01a16a20",width=15)
	private String a01a16a20;

	private String isInvoice;

	@Column(name ="is_invoice",nullable=true,length=32)
	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

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
	 *@return: java.lang.String  订单号
	 */

	@Column(name ="ORDER_NO",nullable=true,length=32)
	public String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单号
	 */
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  退货日期
	 */

	@Column(name ="CANCEL_DATE",nullable=true,length=32)
	public String getCancelDate(){
		return this.cancelDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  退货日期
	 */
	public void setCancelDate(String cancelDate){
		this.cancelDate = cancelDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货批次
	 */

	@Column(name ="BETCH",nullable=true,length=32)
	public String getBetch(){
		return this.betch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货批次
	 */
	public void setBetch(String betch){
		this.betch = betch;
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

	@Column(name ="order_id",nullable=true,length=32)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	@Column(name = "a01a16a01", nullable = true, length = 32)
	public String getA01a16a01() {
		return a01a16a01;
	}

	public void setA01a16a01(String a01a16a01) {
		this.a01a16a01 = a01a16a01;
	}

	@Column(name = "a01a16a02", nullable = true, length = 32)
	public String getA01a16a02() {
		return a01a16a02;
	}

	public void setA01a16a02(String a01a16a02) {
		this.a01a16a02 = a01a16a02;
	}

	@Column(name = "a01a16a03", nullable = true, length = 32)
	public String getA01a16a03() {
		return a01a16a03;
	}

	public void setA01a16a03(String a01a16a03) {
		this.a01a16a03 = a01a16a03;
	}

	@Column(name = "a01a16a04", nullable = true, length = 32)
	public String getA01a16a04() {
		return a01a16a04;
	}

	public void setA01a16a04(String a01a16a04) {
		this.a01a16a04 = a01a16a04;
	}

	@Column(name = "a01a16a05", nullable = true, length = 32)
	public String getA01a16a05() {
		return a01a16a05;
	}

	public void setA01a16a05(String a01a16a05) {
		this.a01a16a05 = a01a16a05;
	}

	@Column(name = "a01a16a06", nullable = true, length = 32)
	public String getA01a16a06() {
		return a01a16a06;
	}

	public void setA01a16a06(String a01a16a06) {
		this.a01a16a06 = a01a16a06;
	}

	@Column(name = "a01a16a07", nullable = true, length = 32)
	public String getA01a16a07() {
		return a01a16a07;
	}

	public void setA01a16a07(String a01a16a07) {
		this.a01a16a07 = a01a16a07;
	}

	@Column(name = "a01a16a08", nullable = true, length = 32)
	public String getA01a16a08() {
		return a01a16a08;
	}

	public void setA01a16a08(String a01a16a08) {
		this.a01a16a08 = a01a16a08;
	}

	@Column(name = "a01a16a09", nullable = true, length = 32)
	public String getA01a16a09() {
		return a01a16a09;
	}

	public void setA01a16a09(String a01a16a09) {
		this.a01a16a09 = a01a16a09;
	}

	@Column(name = "a01a16a10", nullable = true, length = 32)
	public String getA01a16a10() {
		return a01a16a10;
	}

	public void setA01a16a10(String a01a16a10) {
		this.a01a16a10 = a01a16a10;
	}

	@Column(name = "a01a16a11", nullable = true, length = 32)
	public String getA01a16a11() {
		return a01a16a11;
	}

	public void setA01a16a11(String a01a16a11) {
		this.a01a16a11 = a01a16a11;
	}

	@Column(name = "a01a16a12", nullable = true, length = 32)
	public String getA01a16a12() {
		return a01a16a12;
	}

	public void setA01a16a12(String a01a16a12) {
		this.a01a16a12 = a01a16a12;
	}

	@Column(name = "a01a16a13", nullable = true, length = 32)
	public String getA01a16a13() {
		return a01a16a13;
	}

	public void setA01a16a13(String a01a16a13) {
		this.a01a16a13 = a01a16a13;
	}

	@Column(name = "a01a16a14", nullable = true, length = 32)
	public String getA01a16a14() {
		return a01a16a14;
	}

	public void setA01a16a14(String a01a16a14) {
		this.a01a16a14 = a01a16a14;
	}

	@Column(name = "a01a16a15", nullable = true, length = 32)
	public String getA01a16a15() {
		return a01a16a15;
	}

	public void setA01a16a15(String a01a16a15) {
		this.a01a16a15 = a01a16a15;
	}

	@Column(name = "a01a16a16", nullable = true, length = 32)
	public String getA01a16a16() {
		return a01a16a16;
	}

	public void setA01a16a16(String a01a16a16) {
		this.a01a16a16 = a01a16a16;
	}

	@Column(name = "a01a16a17", nullable = true, length = 32)
	public String getA01a16a17() {
		return a01a16a17;
	}

	public void setA01a16a17(String a01a16a17) {
		this.a01a16a17 = a01a16a17;
	}

	@Column(name = "a01a16a18", nullable = true, length = 32)
	public String getA01a16a18() {
		return a01a16a18;
	}

	public void setA01a16a18(String a01a16a18) {
		this.a01a16a18 = a01a16a18;
	}

	@Column(name = "a01a16a19", nullable = true, length = 32)
	public String getA01a16a19() {
		return a01a16a19;
	}

	public void setA01a16a19(String a01a16a19) {
		this.a01a16a19 = a01a16a19;
	}

	@Column(name = "a01a16a20", nullable = true, length = 32)
	public String getA01a16a20() {
		return a01a16a20;
	}

	public void setA01a16a20(String a01a16a20) {
		this.a01a16a20 = a01a16a20;
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

	@Column(name = "lxr", nullable = true, length = 32)
	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	@Column(name = "telphone", nullable = true, length = 32)
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@Column(name = "send_date", nullable = true, length = 32)
	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "address", nullable = true, length = 32)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	@Column(name="total", nullable=true, length=32)
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Column(name="money", nullable=true, length=32)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Column(name = "STORAGE_NAME", nullable = true, length = 256)
	public String getStorageName() {
		return this.storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	@Column(name = "storage_id", nullable = true, length = 256)
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	@Column(name = "gys", nullable = true, length = 256)
	public String getGys() {
		return gys;
	}

	public void setGys(String gys) {
		this.gys = gys;
	}

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
}
