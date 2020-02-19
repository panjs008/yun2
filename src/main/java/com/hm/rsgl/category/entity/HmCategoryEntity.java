package com.hm.rsgl.category.entity;

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
 * @Description: 字段表
 * @author onlineGenerator
 * @date 2019-06-22 20:08:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "hm_category", schema = "")
@SuppressWarnings("serial")
public class HmCategoryEntity implements java.io.Serializable {
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
	/**上级code*/
	@Excel(name="上级code",width=15)
	private String parentCode;
	/**是否必填*/
	@Excel(name="是否必填",width=15)
	private String required;
	/**字段类型*/
	@Excel(name="字段类型",width=15)
	private String columnType;
	/**是否查询*/
	@Excel(name="是否查询",width=15)
	private String queryed;
	@Excel(name="是否显示",width=15)
	private String isShow;
	/**序号*/
	@Excel(name="序号",width=15)
	private String orderNum;
	/**类型名称*/
	@Excel(name="类型名称",width=15)
	private String name;
	@Excel(name="显示名称",width=15)
	private String fname;

	/**类型编码*/
	@Excel(name="类型编码",width=15)
	private String code;

	private String departId;
	private String orgCode;

	private String type;

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
	 *@return: java.lang.String  上级code
	 */

	@Column(name ="PARENT_CODE",nullable=true,length=32)
	public String getParentCode(){
		return this.parentCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上级code
	 */
	public void setParentCode(String parentCode){
		this.parentCode = parentCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否必填
	 */

	@Column(name ="REQUIRED",nullable=true,length=32)
	public String getRequired(){
		return this.required;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否必填
	 */
	public void setRequired(String required){
		this.required = required;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  字段类型
	 */

	@Column(name ="COLUMN_TYPE",nullable=true,length=32)
	public String getColumnType(){
		return this.columnType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  字段类型
	 */
	public void setColumnType(String columnType){
		this.columnType = columnType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否查询
	 */

	@Column(name ="QUERYED",nullable=true,length=32)
	public String getQueryed(){
		return this.queryed;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否查询
	 */
	public void setQueryed(String queryed){
		this.queryed = queryed;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  序号
	 */

	@Column(name ="ORDER_NUM",nullable=true,length=32)
	public String getOrderNum(){
		return this.orderNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  序号
	 */
	public void setOrderNum(String orderNum){
		this.orderNum = orderNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型名称
	 */

	@Column(name ="NAME",nullable=true,length=32)
	public String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型编码
	 */

	@Column(name ="CODE",nullable=true,length=32)
	public String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型编码
	 */
	public void setCode(String code){
		this.code = code;
	}

	@Column(name ="is_show",nullable=true,length=32)
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Column(name ="fname",nullable=true,length=32)
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name ="type",nullable=true,length=32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
