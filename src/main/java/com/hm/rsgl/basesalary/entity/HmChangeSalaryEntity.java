package com.hm.rsgl.basesalary.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 薪酬调整管理
 * @author onlineGenerator
 * @date 2019-09-10 20:15:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "hm_change_salary", schema = "")
@SuppressWarnings("serial")
public class HmChangeSalaryEntity implements java.io.Serializable {
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
	/**工号*/
	private String workNo;
	private String xclb;

	/**部门*/
	@Excel(name="部门",width=15)
	private String deptName;
	/**部门代码*/
	private String deptCode;
	/**车间*/
	@Excel(name="车间",width=15)
	private String workName;
	/**车间代码*/
	private String workCode;
	/**班组*/
	@Excel(name="班组",width=15)
	private String groupName;
	/**班组代码*/
	private String groupCode;
	/**a01a02a01*/
	@Excel(name="a01a02a01",width=15)
	private String a01a02a01;
	/**a01a02a02*/
	@Excel(name="a01a02a02",width=15)
	private String a01a02a02;
	/**a01a02a03*/
	@Excel(name="a01a02a03",width=15)
	private String a01a02a03;
	/**a01a02a04*/
	@Excel(name="a01a02a04",width=15)
	private String a01a02a04;
	/**a01a02a05*/
	@Excel(name="a01a02a05",width=15)
	private String a01a02a05;
	/**a01a02a06*/
	@Excel(name="a01a02a06",width=15)
	private String a01a02a06;
	/**a01a02a07*/
	@Excel(name="a01a02a07",width=15)
	private String a01a02a07;
	/**a01a02a08*/
	@Excel(name="a01a02a08",width=15)
	private String a01a02a08;
	/**a01a02a09*/
	@Excel(name="a01a02a09",width=15)
	private String a01a02a09;
	/**a01a02a10*/
	@Excel(name="a01a02a10",width=15)
	private String a01a02a10;
	/**a01a02a11*/
	@Excel(name="a01a02a11",width=15)
	private String a01a02a11;
	/**a01a02a12*/
	@Excel(name="a01a02a12",width=15)
	private String a01a02a12;
	/**a01a02a13*/
	@Excel(name="a01a02a13",width=15)
	private String a01a02a13;
	/**a01a02a14*/
	@Excel(name="a01a02a14",width=15)
	private String a01a02a14;
	/**a01a02a15*/
	@Excel(name="a01a02a15",width=15)
	private String a01a02a15;
	/**a01a02a16*/
	@Excel(name="a01a02a16",width=15)
	private String a01a02a16;
	/**a01a02a17*/
	@Excel(name="a01a02a17",width=15)
	private String a01a02a17;
	/**a01a02a18*/
	@Excel(name="a01a02a18",width=15)
	private String a01a02a18;
	/**a01a02a19*/
	@Excel(name="a01a02a19",width=15)
	private String a01a02a19;
	/**a01a02a20*/
	@Excel(name="a01a02a20",width=15)
	private String a01a02a20;

	private String month;
	private String excetuDate;
	private String reason;
	private String fileName;
	private String saveFileName;
	private String fileNameUrl;

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
	 *@return: java.lang.String  工号
	 */

	@Column(name ="WORK_NO",nullable=true,length=32)
	public String getWorkNo(){
		return this.workNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工号
	 */
	public void setWorkNo(String workNo){
		this.workNo = workNo;
	}
	@Column(name ="xclb",nullable=true,length=32)
	public String getXclb() {
		return xclb;
	}

	public void setXclb(String xclb) {
		this.xclb = xclb;
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
	 *@return: java.lang.String  车间
	 */

	@Column(name ="WORK_NAME",nullable=true,length=32)
	public String getWorkName(){
		return this.workName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车间
	 */
	public void setWorkName(String workName){
		this.workName = workName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车间代码
	 */

	@Column(name ="WORK_CODE",nullable=true,length=32)
	public String getWorkCode(){
		return this.workCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车间代码
	 */
	public void setWorkCode(String workCode){
		this.workCode = workCode;
	}
	@Column(name ="GROUP_NAME",nullable=true,length=32)
	public String getGroupName(){
		return this.groupName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班组
	 */
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	@Column(name ="GROUP_CODE",nullable=true,length=32)
	public String getGroupCode(){
		return this.groupCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  班组代码
	 */
	public void setGroupCode(String groupCode){
		this.groupCode = groupCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a01
	 */

	@Column(name ="A01A02A01",nullable=true,length=32)
	public String getA01a02a01(){
		return this.a01a02a01;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a01
	 */
	public void setA01a02a01(String a01a02a01){
		this.a01a02a01 = a01a02a01;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a02
	 */

	@Column(name ="A01A02A02",nullable=true,length=32)
	public String getA01a02a02(){
		return this.a01a02a02;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a02
	 */
	public void setA01a02a02(String a01a02a02){
		this.a01a02a02 = a01a02a02;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a03
	 */

	@Column(name ="A01A02A03",nullable=true,length=32)
	public String getA01a02a03(){
		return this.a01a02a03;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a03
	 */
	public void setA01a02a03(String a01a02a03){
		this.a01a02a03 = a01a02a03;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a04
	 */

	@Column(name ="A01A02A04",nullable=true,length=32)
	public String getA01a02a04(){
		return this.a01a02a04;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a04
	 */
	public void setA01a02a04(String a01a02a04){
		this.a01a02a04 = a01a02a04;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a05
	 */

	@Column(name ="A01A02A05",nullable=true,length=32)
	public String getA01a02a05(){
		return this.a01a02a05;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a05
	 */
	public void setA01a02a05(String a01a02a05){
		this.a01a02a05 = a01a02a05;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a06
	 */

	@Column(name ="A01A02A06",nullable=true,length=32)
	public String getA01a02a06(){
		return this.a01a02a06;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a06
	 */
	public void setA01a02a06(String a01a02a06){
		this.a01a02a06 = a01a02a06;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a07
	 */

	@Column(name ="A01A02A07",nullable=true,length=32)
	public String getA01a02a07(){
		return this.a01a02a07;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a07
	 */
	public void setA01a02a07(String a01a02a07){
		this.a01a02a07 = a01a02a07;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a08
	 */

	@Column(name ="A01A02A08",nullable=true,length=32)
	public String getA01a02a08(){
		return this.a01a02a08;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a08
	 */
	public void setA01a02a08(String a01a02a08){
		this.a01a02a08 = a01a02a08;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a09
	 */

	@Column(name ="A01A02A09",nullable=true,length=32)
	public String getA01a02a09(){
		return this.a01a02a09;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a09
	 */
	public void setA01a02a09(String a01a02a09){
		this.a01a02a09 = a01a02a09;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a10
	 */

	@Column(name ="A01A02A10",nullable=true,length=32)
	public String getA01a02a10(){
		return this.a01a02a10;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a10
	 */
	public void setA01a02a10(String a01a02a10){
		this.a01a02a10 = a01a02a10;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a11
	 */

	@Column(name ="A01A02A11",nullable=true,length=32)
	public String getA01a02a11(){
		return this.a01a02a11;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a11
	 */
	public void setA01a02a11(String a01a02a11){
		this.a01a02a11 = a01a02a11;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a12
	 */

	@Column(name ="A01A02A12",nullable=true,length=32)
	public String getA01a02a12(){
		return this.a01a02a12;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a12
	 */
	public void setA01a02a12(String a01a02a12){
		this.a01a02a12 = a01a02a12;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a13
	 */

	@Column(name ="A01A02A13",nullable=true,length=32)
	public String getA01a02a13(){
		return this.a01a02a13;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a13
	 */
	public void setA01a02a13(String a01a02a13){
		this.a01a02a13 = a01a02a13;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a14
	 */

	@Column(name ="A01A02A14",nullable=true,length=32)
	public String getA01a02a14(){
		return this.a01a02a14;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a14
	 */
	public void setA01a02a14(String a01a02a14){
		this.a01a02a14 = a01a02a14;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a15
	 */

	@Column(name ="A01A02A15",nullable=true,length=32)
	public String getA01a02a15(){
		return this.a01a02a15;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a15
	 */
	public void setA01a02a15(String a01a02a15){
		this.a01a02a15 = a01a02a15;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a16
	 */

	@Column(name ="A01A02A16",nullable=true,length=32)
	public String getA01a02a16(){
		return this.a01a02a16;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a16
	 */
	public void setA01a02a16(String a01a02a16){
		this.a01a02a16 = a01a02a16;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a17
	 */

	@Column(name ="A01A02A17",nullable=true,length=32)
	public String getA01a02a17(){
		return this.a01a02a17;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a17
	 */
	public void setA01a02a17(String a01a02a17){
		this.a01a02a17 = a01a02a17;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a18
	 */

	@Column(name ="A01A02A18",nullable=true,length=32)
	public String getA01a02a18(){
		return this.a01a02a18;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a18
	 */
	public void setA01a02a18(String a01a02a18){
		this.a01a02a18 = a01a02a18;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a19
	 */

	@Column(name ="A01A02A19",nullable=true,length=32)
	public String getA01a02a19(){
		return this.a01a02a19;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a19
	 */
	public void setA01a02a19(String a01a02a19){
		this.a01a02a19 = a01a02a19;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  a01a02a20
	 */

	@Column(name ="A01A02A20",nullable=true,length=32)
	public String getA01a02a20(){
		return this.a01a02a20;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  a01a02a20
	 */
	public void setA01a02a20(String a01a02a20){
		this.a01a02a20 = a01a02a20;
	}

	@Column(name ="month",nullable=true,length=32)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name ="excetu_date",nullable=true,length=32)
	public String getExcetuDate() {
		return excetuDate;
	}

	public void setExcetuDate(String excetuDate) {
		this.excetuDate = excetuDate;
	}

	@Column(name ="reason",nullable=true,length=256)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name ="file_name",nullable=true,length=256)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name ="file_name_url",nullable=true,length=256)
	public String getFileNameUrl() {
		return fileNameUrl;
	}

	public void setFileNameUrl(String fileNameUrl) {
		this.fileNameUrl = fileNameUrl;
	}

	@Column(name ="save_file_name",nullable=true,length=32)
	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
}
