package com.emk.storage.formaterial.entity;

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
 * @Description: 叫布表
 * @author onlineGenerator
 * @date 2019-08-24 14:00:26
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_formaterial", schema = "")
@SuppressWarnings("serial")
public class EmkFormaterialEntity implements java.io.Serializable {
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
	/**布行名称*/
	@Excel(name="布行名称",width=15)
	private String bhmc;
	/**开单日期*/
	@Excel(name="开单日期",width=15)
	private String kdDate;
	/**单号*/
	@Excel(name="单号",width=15)
	private String orderNo;
	private String orderId;

	/**合计*/
	@Excel(name="合计",width=15)
	private String hj;
	private String state;
	private String fileName;
	private String saveFileName;
	private String fileNameUrl;
	private String gysCode;

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
	 *@return: java.lang.String  布行名称
	 */

	@Column(name ="BHMC",nullable=true,length=256)
	public String getBhmc(){
		return this.bhmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  布行名称
	 */
	public void setBhmc(String bhmc){
		this.bhmc = bhmc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开单日期
	 */

	@Column(name ="KD_DATE",nullable=true,length=32)
	public String getKdDate(){
		return this.kdDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开单日期
	 */
	public void setKdDate(String kdDate){
		this.kdDate = kdDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单号
	 */

	@Column(name ="ORDER_NO",nullable=true,length=32)
	public String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单号
	 */
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合计
	 */
	@Formula("(SELECT round(sum(IFNULL(t1.TOTAL*t1.PRICE,0)),2) FROM emk_formaterail_detail t1 LEFT JOIN emk_size_total t2 ON t1.id=t2.p_id where t1.formaterial_id=id LIMIT 0,1)")
	@Column(name ="HJ",nullable=true,length=32)
	public String getHj(){
		return this.hj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合计
	 */
	public void setHj(String hj){
		this.hj = hj;
	}

	@Column(name ="state",nullable=true,length=32)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	@Column(name ="order_id",nullable=true,length=32)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Formula("(select p.gys_code from emk_enquiry p where p.enquiry_no = order_no limit 0,1)")
	@Column(name = "gys_code", nullable = true, length = 32)
	public String getGysCode() {
		return gysCode;
	}

	public void setGysCode(String gysCode) {
		this.gysCode = gysCode;
	}
}
