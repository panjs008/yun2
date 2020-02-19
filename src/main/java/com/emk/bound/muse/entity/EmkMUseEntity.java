package com.emk.bound.muse.entity;

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
 * @Description: 使用登记表
 * @author onlineGenerator
 * @date 2019-11-23 10:15:29
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_m_use", schema = "")
@SuppressWarnings("serial")
public class EmkMUseEntity implements java.io.Serializable {
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
	/**单号*/
	@Excel(name="单号",width=15)
	private String ckNo;
	/**医院名称*/
	@Excel(name="医院名称",width=15)
	private String hospitalName;
	private String hospitalCode;
	/**病人姓名*/
	@Excel(name="病人姓名",width=15)
	private String patient;
	/**手术时间*/
	@Excel(name="手术时间",width=15)
	private String operationDate;
	/**手术医生*/
	@Excel(name="手术医生",width=15)
	private String operationDc;
	/**登记人*/
	@Excel(name="登记人",width=15)
	private String appler;
	/**登记人ID*/
	@Excel(name="登记人ID",width=15)
	private String applerId;
	/**客户编号*/
	@Excel(name="客户编号",width=15)
	private String cusNum;
	/**客户名称*/
	@Excel(name="客户名称",width=15)
	private String cusName;
	private String state;

	@Excel(name = "联系人")
	private String zlxr;
	@Excel(name = "联系人电话")
	private String workphone;

	@Excel(name = "企业注册地址")
	private String qyzcAddress;
	@Excel(name = "收票收件地址")
	private String spAddress;
	@Excel(name = "开户行")
	private String bankName;
	@Excel(name = "开户账号")
	private String bankAccount;
	@Excel(name = "税号")
	private String suiNum;

	@Excel(name="开票单位",width=15)
	private String kpUnit;
	@Excel(name="开票金额",width=15)
	private String kpMoney;
	@Excel(name="发票号码",width=15)
	private String kpNum;
	@Excel(name="厂家回款时间",width=15)
	private String cjhkMoney;

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
	 *@return: java.lang.String  单号
	 */

	@Column(name ="CK_NO",nullable=true,length=32)
	public String getCkNo(){
		return this.ckNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单号
	 */
	public void setCkNo(String ckNo){
		this.ckNo = ckNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  医院名称
	 */

	@Column(name ="HOSPITAL_NAME",nullable=true,length=32)
	public String getHospitalName(){
		return this.hospitalName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  医院名称
	 */
	public void setHospitalName(String hospitalName){
		this.hospitalName = hospitalName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  病人姓名
	 */

	@Column(name ="PATIENT",nullable=true,length=32)
	public String getPatient(){
		return this.patient;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  病人姓名
	 */
	public void setPatient(String patient){
		this.patient = patient;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手术时间
	 */

	@Column(name ="OPERATION_DATE",nullable=true,length=32)
	public String getOperationDate(){
		return this.operationDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手术时间
	 */
	public void setOperationDate(String operationDate){
		this.operationDate = operationDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手术医生
	 */

	@Column(name ="OPERATION_DC",nullable=true,length=32)
	public String getOperationDc(){
		return this.operationDc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手术医生
	 */
	public void setOperationDc(String operationDc){
		this.operationDc = operationDc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  登记人
	 */

	@Column(name ="APPLER",nullable=true,length=32)
	public String getAppler(){
		return this.appler;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  登记人
	 */
	public void setAppler(String appler){
		this.appler = appler;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  登记人ID
	 */

	@Column(name ="APPLER_ID",nullable=true,length=32)
	public String getApplerId(){
		return this.applerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  登记人ID
	 */
	public void setApplerId(String applerId){
		this.applerId = applerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户编号
	 */

	@Column(name ="CUS_NUM",nullable=true,length=32)
	public String getCusNum(){
		return this.cusNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编号
	 */
	public void setCusNum(String cusNum){
		this.cusNum = cusNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户名称
	 */

	@Column(name ="CUS_NAME",nullable=true,length=256)
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


	@Column(name ="kp_unit",nullable=true,length=32)
	public String getKpUnit() {
		return kpUnit;
	}

	public void setKpUnit(String kpUnit) {
		this.kpUnit = kpUnit;
	}

	@Column(name ="kp_money",nullable=true,length=32)
	public String getKpMoney() {
		return kpMoney;
	}

	public void setKpMoney(String kpMoney) {
		this.kpMoney = kpMoney;
	}

	@Column(name ="kp_num",nullable=true,length=32)
	public String getKpNum() {
		return kpNum;
	}

	public void setKpNum(String kpNum) {
		this.kpNum = kpNum;
	}

	@Column(name ="cjhk_money",nullable=true,length=32)
	public String getCjhkMoney() {
		return cjhkMoney;
	}

	public void setCjhkMoney(String cjhkMoney) {
		this.cjhkMoney = cjhkMoney;
	}

	@Column(name ="hospital_code",nullable=true,length=32)
	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	@Column(name = "zlxr", nullable = true, length = 56)
	public String getZlxr() {
		return zlxr;
	}

	public void setZlxr(String zlxr) {
		this.zlxr = zlxr;
	}

	@Column(name = "workphone", nullable = true, length = 56)
	public String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	@Column(name = "qyzc_address", nullable = true, length = 512)
	public String getQyzcAddress() {
		return qyzcAddress;
	}

	public void setQyzcAddress(String qyzcAddress) {
		this.qyzcAddress = qyzcAddress;
	}

	@Column(name = "sp_address", nullable = true, length = 512)
	public String getSpAddress() {
		return spAddress;
	}

	public void setSpAddress(String spAddress) {
		this.spAddress = spAddress;
	}

	@Column(name = "BANK_NAME", nullable = true, length = 256)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_ACCOUNT", nullable = true, length = 56)
	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "sui_num", nullable = true, length = 56)
	public String getSuiNum() {
		return suiNum;
	}

	public void setSuiNum(String suiNum) {
		this.suiNum = suiNum;
	}
}
