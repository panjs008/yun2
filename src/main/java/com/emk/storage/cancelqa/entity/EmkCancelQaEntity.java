package com.emk.storage.cancelqa.entity;

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
 * @Description: 问题反馈表
 * @author onlineGenerator
 * @date 2019-09-05 22:43:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_cancel_qa", schema = "")
@SuppressWarnings("serial")
public class EmkCancelQaEntity implements java.io.Serializable {
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
	/**退货ID*/
	@Excel(name="退货ID",width=15)
	private String cancelId;
	/**问题描述*/
	@Excel(name="问题描述",width=15)
	private String question;
	private String fileName;
	private String saveFileName;
	private String fileNameUrl;
	/**数量统计*/
	@Excel(name="数量统计",width=15)
	private Integer total;
	private String sortDesc;

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
	 *@return: java.lang.String  退货ID
	 */

	@Column(name ="cancel_id",nullable=true,length=32)
	public String getCancelId() {
		return cancelId;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  问题描述
	 */

	@Column(name ="QUESTION",nullable=true,length=256)
	public String getQuestion(){
		return this.question;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题描述
	 */
	public void setQuestion(String question){
		this.question = question;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数量统计
	 */

	@Column(name ="TOTAL",nullable=true,length=32)
	public Integer getTotal(){
		return this.total;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量统计
	 */
	public void setTotal(Integer total){
		this.total = total;
	}

	@Column(name ="file_name",nullable=true,length=32)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name ="file_name_url",nullable=true,length=32)
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

	@Column(name = "sort_desc", nullable = true, scale = 2, length = 32)
	public String getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(String sortDesc) {
		this.sortDesc = sortDesc;
	}
}
