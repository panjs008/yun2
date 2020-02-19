package com.emk.storage.factoryarchives.entity;

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
 * @Description: 供应商
 * @author onlineGenerator
 * @date 2019-02-22 10:50:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_factory_archives", schema = "")
@SuppressWarnings("serial")
public class EmkFactoryArchivesEntity implements java.io.Serializable {
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

	/**提交时间*/
	@Excel(name="提交时间",width=15)
	private String sumbitDate;
	/**供应商代码*/
	@Excel(name="供应商代码",width=15)
	private String companyCode;
	/**公司名中文*/
	@Excel(name="公司名中文",width=15)
	private String companyNameZn;
	private String zjm;
	/**地址中文*/
	@Excel(name="地址中文",width=15)
	private String addressZn;
	/**主要联系人*/
	@Excel(name="主要联系人",width=15)
	private String primaryContact;
	/**邮箱*/
	@Excel(name="邮箱",width=15)
	private String primaryContactEmail;
	/**电话*/
	@Excel(name="电话",width=15)
	private String primaryContactTel;

	/**省份*/
	@Excel(name="省份",width=15)
	private String shengFen;
	/**城市*/
	@Excel(name="城市",width=15)
	private String chengShi;
	@Excel(name = "片区")
	private String pianQu;
	/**档案编号*/
	@Excel(name="档案编号",width=15)
	private String archivesNo;

	private String state;
	private String formType;
	private String gdhm;
	private String postCode;
	@Excel(name = "开户行")
	private String bankName;
	@Excel(name = "开户账号 ")
	private String bankAccount;

	private String bcqkMoney;

	private String departId;
	private String orgCode;

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
	 *@return: java.lang.String  提交时间
	 */

	@Column(name ="SUMBIT_DATE",nullable=true,length=32)
	public String getSumbitDate(){
		return this.sumbitDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提交时间
	 */
	public void setSumbitDate(String sumbitDate){
		this.sumbitDate = sumbitDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司名中文
	 */

	@Column(name ="COMPANY_NAME_ZN",nullable=true,length=256)
	public String getCompanyNameZn(){
		return this.companyNameZn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司名中文
	 */
	public void setCompanyNameZn(String companyNameZn){
		this.companyNameZn = companyNameZn;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址中文
	 */

	@Column(name ="ADDRESS_ZN",nullable=true,length=512)
	public String getAddressZn(){
		return this.addressZn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址中文
	 */
	public void setAddressZn(String addressZn){
		this.addressZn = addressZn;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主要联系人
	 */

	@Column(name ="PRIMARY_CONTACT",nullable=true,length=56)
	public String getPrimaryContact(){
		return this.primaryContact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主要联系人
	 */
	public void setPrimaryContact(String primaryContact){
		this.primaryContact = primaryContact;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮箱
	 */

	@Column(name ="PRIMARY_CONTACT_EMAIL",nullable=true,length=32)
	public String getPrimaryContactEmail(){
		return this.primaryContactEmail;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮箱
	 */
	public void setPrimaryContactEmail(String primaryContactEmail){
		this.primaryContactEmail = primaryContactEmail;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */

	@Column(name ="PRIMARY_CONTACT_TEL",nullable=true,length=32)
	public String getPrimaryContactTel(){
		return this.primaryContactTel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPrimaryContactTel(String primaryContactTel){
		this.primaryContactTel = primaryContactTel;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商代码
	 */

	@Column(name ="COMPANY_CODE",nullable=true,length=32)
	public String getCompanyCode(){
		return this.companyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商代码
	 */
	public void setCompanyCode(String companyCode){
		this.companyCode = companyCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省份
	 */

	@Column(name ="SHENG_FEN",nullable=true,length=32)
	public String getShengFen(){
		return this.shengFen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省份
	 */
	public void setShengFen(String shengFen){
		this.shengFen = shengFen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市
	 */

	@Column(name ="CHENG_SHI",nullable=true,length=32)
	public String getChengShi(){
		return this.chengShi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市
	 */
	public void setChengShi(String chengShi){
		this.chengShi = chengShi;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  档案编号
	 */

	@Column(name ="ARCHIVES_NO",nullable=true,length=32)
	public String getArchivesNo(){
		return this.archivesNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  档案编号
	 */
	public void setArchivesNo(String archivesNo){
		this.archivesNo = archivesNo;
	}

	@Column(name = "STATE", nullable = true, length = 32)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "form_type", nullable = true, length = 32)
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	@Column(name = "gdhm", nullable = true, length = 32)
	public String getGdhm() {
		return gdhm;
	}

	public void setGdhm(String gdhm) {
		this.gdhm = gdhm;
	}

	@Column(name = "post_code", nullable = true, length = 32)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	@Column(name = "zjm", nullable = true, length = 56)
	public String getZjm() {
		return zjm;
	}

	public void setZjm(String zjm) {
		this.zjm = zjm;
	}

	@Column(name = "bcqk_money", nullable = true, length = 50)
	public String getBcqkMoney() {
		return bcqkMoney;
	}

	public void setBcqkMoney(String bcqkMoney) {
		this.bcqkMoney = bcqkMoney;
	}

	@Column(name = "pian_qu", nullable = true, length = 50)
	public String getPianQu() {
		return this.pianQu;
	}

	public void setPianQu(String pianQu) {
		this.pianQu = pianQu;
	}


}
