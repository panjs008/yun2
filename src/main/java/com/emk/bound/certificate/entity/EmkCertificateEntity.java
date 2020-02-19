package com.emk.bound.certificate.entity;

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
 * @Description: 证件表
 * @author onlineGenerator
 * @date 2019-12-10 16:52:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_certificate", schema = "")
@SuppressWarnings("serial")
public class EmkCertificateEntity implements java.io.Serializable {
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
	/**证件名称*/
	@Excel(name="证件名称",width=15)
	private String certName;
	/**证件号码*/
	@Excel(name="证件号码",width=15)
	private String certNo;
	/**发证机关*/
	@Excel(name="发证机关",width=15)
	private String certSignDept;
	/**发证日期*/
	@Excel(name="发证日期",width=15)
	private String certSignDate;
	/**到期日期*/
	@Excel(name="到期日期",width=15)
	private String certLimitDate;
	/**备注*/
	@Excel(name="备注",width=15)
	private String remark;
	/**照片*/
	@Excel(name="照片",width=15)
	private String certImage;
	/**照片URL*/
	@Excel(name="照片URL",width=15)
	private String certImageUrl;
	/**供应商ID*/
	@Excel(name="供应商ID",width=15)
	private String gysId;

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
	 *@return: java.lang.String  证件名称
	 */

	@Column(name ="CERT_NAME",nullable=true,length=56)
	public String getCertName(){
		return this.certName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  证件名称
	 */
	public void setCertName(String certName){
		this.certName = certName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  证件号码
	 */

	@Column(name ="CERT_NO",nullable=true,length=56)
	public String getCertNo(){
		return this.certNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  证件号码
	 */
	public void setCertNo(String certNo){
		this.certNo = certNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发证机关
	 */

	@Column(name ="CERT_SIGN_DEPT",nullable=true,length=256)
	public String getCertSignDept(){
		return this.certSignDept;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发证机关
	 */
	public void setCertSignDept(String certSignDept){
		this.certSignDept = certSignDept;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发证日期
	 */

	@Column(name ="CERT_SIGN_DATE",nullable=true,length=32)
	public String getCertSignDate(){
		return this.certSignDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发证日期
	 */
	public void setCertSignDate(String certSignDate){
		this.certSignDate = certSignDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到期日期
	 */

	@Column(name ="CERT_LIMIT_DATE",nullable=true,length=32)
	public String getCertLimitDate(){
		return this.certLimitDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到期日期
	 */
	public void setCertLimitDate(String certLimitDate){
		this.certLimitDate = certLimitDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="REMARK",nullable=true,length=56)
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
	 *@return: java.lang.String  照片
	 */

	@Column(name ="CERT_IMAGE",nullable=true,length=56)
	public String getCertImage(){
		return this.certImage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  照片
	 */
	public void setCertImage(String certImage){
		this.certImage = certImage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  照片URL
	 */

	@Column(name ="CERT_IMAGE_URL",nullable=true,length=256)
	public String getCertImageUrl(){
		return this.certImageUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  照片URL
	 */
	public void setCertImageUrl(String certImageUrl){
		this.certImageUrl = certImageUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商ID
	 */

	@Column(name ="GYS_ID",nullable=true,length=32)
	public String getGysId(){
		return this.gysId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商ID
	 */
	public void setGysId(String gysId){
		this.gysId = gysId;
	}
}
