package com.emk.caiwu.yspay.entity;

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
 * @Description: 运输费用申请单
 * @author onlineGenerator
 * @date 2019-03-17 21:32:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_ys_pay", schema = "")
@SuppressWarnings("serial")
public class EmkYsPayEntity implements java.io.Serializable {
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
	/**业务员*/
	@Excel(name="业务员",width=15)
	private String businesser;
	/**业务员ID*/
	@Excel(name="业务员ID",width=15)
	private String businesserName;

	@Excel(name="客户代码",width=15)
	private String cusNum;
	/**客户名称*/
	@Excel(name="客户名称",width=15)
	private String cusName;
	/**付款单号*/
	@Excel(name="付款单号",width=15)
	private String yfCheckNo;
	/**付款日期*/
	@Excel(name="付款日期",width=15)
	private String yftzDate;
	/**应付金额*/
	@Excel(name="付款金额",width=15)
	private Double money;

	private Double yfMoney;	//应付
	private Double sfMoney;	//实付
	private Double qkMoney;

	private String paytype;	//付款方式
	private Double clearWs;	//抹零
	private String bankAccount;	//付款账号
	private String bankType;
	private String outItem;
	private String marker;
	private String markerId;
	private String type;		//0 进货

	/**
	 * 仓库ID
	 */
	private String storageId;

	/**
	 * 仓库名称
	 */
	@Excel(name = "仓库名称")
	private String storageName;
	private String remark;
	private String state;
	private String departId;
	private String orgCode;

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

	@Column(name ="CUS_NUM",nullable=true,length=32)
	public String getCusNum(){
		return this.cusNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户代码
	 */
	public void setCusNum(String cusNum){
		this.cusNum = cusNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户名称
	 */

	@Column(name ="CUS_NAME",nullable=true,length=56)
	public String getCusName(){
		return this.cusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户名称
	 */
	public void setCusName(String cusName){
		this.cusName = cusName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应付通知单号
	 */

	@Column(name ="YF_CHECK_NO",nullable=true,length=32)
	public String getYfCheckNo(){
		return this.yfCheckNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应付通知单号
	 */
	public void setYfCheckNo(String yfCheckNo){
		this.yfCheckNo = yfCheckNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应付通知单日期
	 */

	@Column(name ="YFTZ_DATE",nullable=true,length=32)
	public String getYftzDate(){
		return this.yftzDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应付通知单日期
	 */
	public void setYftzDate(String yftzDate){
		this.yftzDate = yftzDate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  应付金额
	 */

	@Column(name ="MONEY",nullable=true,length=32)
	public Double getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  应付金额
	 */
	public void setMoney(Double money){
		this.money = money;
	}

	@Column(name = "STATE", nullable = true, length = 32)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  仓库ID
	 */

	@Column(name ="storage_id",nullable=true,length=32)
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  仓库名称
	 */

	@Column(name = "STORAGE_NAME", nullable = true, length = 32)
	public String getStorageName() {
		return this.storageName;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String  仓库名称
	 */
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	@Column(name = "paytype", nullable = true, length = 32)
	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	@Column(name = "clear_ws", nullable = true, length = 32)
	public Double getClearWs() {
		return clearWs;
	}

	public void setClearWs(Double clearWs) {
		this.clearWs = clearWs;
	}

	@Column(name = "bank_account", nullable = true, length = 32)
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "bank_type", nullable = true, length = 32)
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	@Column(name = "out_item", nullable = true, length = 32)
	public String getOutItem() {
		return outItem;
	}

	public void setOutItem(String outItem) {
		this.outItem = outItem;
	}

	@Column(name = "marker", nullable = true, length = 32)
	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	@Column(name = "remark", nullable = true, length = 32)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "yf_money", nullable = true, length = 32)
	public Double getYfMoney() {
		return yfMoney;
	}

	public void setYfMoney(Double yfMoney) {
		this.yfMoney = yfMoney;
	}

	@Column(name = "sf_money", nullable = true, length = 32)
	public Double getSfMoney() {
		return sfMoney;
	}

	public void setSfMoney(Double sfMoney) {
		this.sfMoney = sfMoney;
	}

	@Column(name = "qk_money", nullable = true, length = 32)
	public Double getQkMoney() {
		return qkMoney;
	}

	public void setQkMoney(Double qkMoney) {
		this.qkMoney = qkMoney;
	}

	@Column(name = "marker_id", nullable = true, length = 32)
	public String getMarkerId() {
		return markerId;
	}

	public void setMarkerId(String markerId) {
		this.markerId = markerId;
	}

	@Column(name = "type", nullable = true, length = 32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
