package com.hm.rsgl.staff.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import com.emk.util.Utils;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 人员信息表
 * @author onlineGenerator
 * @date 2019-06-22 09:53:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "hm_staff", schema = "")
@SuppressWarnings("serial")
public class HmStaffEntity implements java.io.Serializable {
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
	/**姓名*/
	@Excel(name="姓名",width=15)
	private String realName;
	/**部门*/
	@Excel(name="部门",width=15)
	private String deptName;
	/**部门代码*/
	@Excel(name="部门代码",width=15)
	private String deptCode;

	/**性别*/
	@Excel(name="性别",width=15)
	private String sex;
	/**入职日期*/
	@Excel(name="入职日期",width=15)
	private String rzDate;
	/**转正日期*/
	@Excel(name="转正日期",width=15)
	private String zzDate;
	/**离职日期*/
	@Excel(name="离职日期",width=15)
	private String lzDate;
	/**手机*/
	@Excel(name="手机",width=15)
	private String telphone;
	/**身份证号码*/
	@Excel(name="身份证号码",width=15)
	private String idCard;
	/**出生日期*/
	@Excel(name="出生日期",width=15)
	private String birthDay;
	/**工龄*/
	@Excel(name="工龄",width=15)
	private String workYear;
	/**月休天数*/
	@Excel(name="月休天数",width=15)
	private String sleepDays;
	/**民族*/
	@Excel(name="民族",width=15)
	private String mz;

	/**工号*/
	@Excel(name="工号",width=15)
	private String workNo;
	@Excel(name="电子邮件",width=15)
	private String email;
	private String state;

	private String isSale;
	private String salary;
	private String remark;
	private String storageId;
	@Excel(name = "仓库名称")
	private String storageName;
	private String address;
	private String zjm;
	private String departId;
	private String orgCode;
	private String userName;

	@Column(name ="email",nullable=true,length=50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name ="lz_date",nullable=true,length=50)
	public String getLzDate() {
		return lzDate;
	}

	public void setLzDate(String lzDate) {
		this.lzDate = lzDate;
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
	 *@return: java.lang.String  姓名
	 */

	@Column(name ="REAL_NAME",nullable=true,length=32)
	public String getRealName(){
		return this.realName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setRealName(String realName){
		this.realName = realName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */

	@Column(name ="DEPT_NAME",nullable=true,length=32)
	public String getDeptName(){
		return this.deptName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptName(String deptName){
		this.deptName = deptName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门代码
	 */

	@Column(name ="DEPT_CODE",nullable=true,length=32)
	public String getDeptCode(){
		return this.deptCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门代码
	 */
	public void setDeptCode(String deptCode){
		this.deptCode = deptCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */

	@Column(name ="SEX",nullable=true,length=32)
	public String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  入职日期
	 */

	@Column(name ="RZ_DATE",nullable=true,length=32)
	public String getRzDate(){
		return this.rzDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  入职日期
	 */
	public void setRzDate(String rzDate){
		this.rzDate = rzDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  转正日期
	 */

	@Column(name ="ZZ_DATE",nullable=true,length=32)
	public String getZzDate(){
		return this.zzDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  转正日期
	 */
	public void setZzDate(String zzDate){
		this.zzDate = zzDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机
	 */

	@Column(name ="TELPHONE",nullable=true,length=32)
	public String getTelphone(){
		return this.telphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setTelphone(String telphone){
		this.telphone = telphone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证号码
	 */

	@Column(name ="ID_CARD",nullable=true,length=32)
	public String getIdCard(){
		return this.idCard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号码
	 */
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出生日期
	 */

	@Column(name ="BIRTH_DAY",nullable=true,length=32)
	public String getBirthDay(){
		return this.birthDay;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出生日期
	 */
	public void setBirthDay(String birthDay){
		this.birthDay = birthDay;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工龄
	 */

	@Column(name ="WORK_YEAR",nullable=true,length=32)
	//@Formula("(select TIMESTAMPDIFF(year,p.rz_date,current_date) from hm_staff p where p.id = id)")
	public String getWorkYear(){
		return this.workYear;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工龄
	 */
	public void setWorkYear(String workYear){
		this.workYear = workYear;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  月休天数
	 */

	@Column(name ="SLEEP_DAYS",nullable=true,length=32)
	public String getSleepDays(){
		return this.sleepDays;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月休天数
	 */
	public void setSleepDays(String sleepDays){
		this.sleepDays = sleepDays;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  民族
	 */

	@Column(name ="MZ",nullable=true,length=32)
	public String getMz(){
		return this.mz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  民族
	 */
	public void setMz(String mz){
		this.mz = mz;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工号
	 */

	@Column(name ="WORK_NO",nullable=true,length=32)
	public String getWorkNo(){
		return this.workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	@Column(name ="state",nullable=true,length=32)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name ="is_sale",nullable=true,length=32)
	public String getIsSale() {
		return isSale;
	}

	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}

	@Column(name ="salary",nullable=true,length=32)
	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	@Column(name ="remark",nullable=true,length=32)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name ="address",nullable=true,length=32)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name ="zjm",nullable=true,length=32)
	public String getZjm() {
		return zjm;
	}

	public void setZjm(String zjm) {
		this.zjm = zjm;
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

	@Column(name ="user_name",nullable=true,length=32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
