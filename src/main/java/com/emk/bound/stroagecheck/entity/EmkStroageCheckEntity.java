package com.emk.bound.stroagecheck.entity;

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
 * @Description: 盘点表
 * @author onlineGenerator
 * @date 2020-02-17 21:33:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_stroage_check", schema = "")
@SuppressWarnings("serial")
public class EmkStroageCheckEntity implements java.io.Serializable {
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
	/**盘点单号*/
	@Excel(name="盘点单号",width=15)
	private String checkNo;
	/**盘点日期*/
	@Excel(name="盘点日期",width=15)
	private String checkDate;
	/**部门ID*/
	@Excel(name="部门ID",width=15)
	private String departId;
	/**部门代码*/
	@Excel(name="部门代码",width=15)
	private String orgCode;
	/**备注*/
	@Excel(name="备注",width=15)
	private String remark;
	/**库存ID*/
	@Excel(name="库存ID",width=15)
	private String storageId;
	/**库存名称*/
	@Excel(name="库存名称",width=15)
	private String storageName;
	/**金额*/
	@Excel(name="金额",width=15)
	private String money;
	
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
	 *@return: java.lang.String  盘点单号
	 */

	@Column(name ="CHECK_NO",nullable=true,length=32)
	public String getCheckNo(){
		return this.checkNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盘点单号
	 */
	public void setCheckNo(String checkNo){
		this.checkNo = checkNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  盘点日期
	 */

	@Column(name ="CHECK_DATE",nullable=true,length=32)
	public String getCheckDate(){
		return this.checkDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盘点日期
	 */
	public void setCheckDate(String checkDate){
		this.checkDate = checkDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门ID
	 */

	@Column(name ="DEPART_ID",nullable=true,length=32)
	public String getDepartId(){
		return this.departId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门ID
	 */
	public void setDepartId(String departId){
		this.departId = departId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门代码
	 */

	@Column(name ="ORG_CODE",nullable=true,length=32)
	public String getOrgCode(){
		return this.orgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门代码
	 */
	public void setOrgCode(String orgCode){
		this.orgCode = orgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="REMARK",nullable=true,length=512)
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
	 *@return: java.lang.String  库存ID
	 */

	@Column(name ="STORAGE_ID",nullable=true,length=32)
	public String getStorageId(){
		return this.storageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库存ID
	 */
	public void setStorageId(String storageId){
		this.storageId = storageId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库存名称
	 */

	@Column(name ="STORAGE_NAME",nullable=true,length=256)
	public String getStorageName(){
		return this.storageName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库存名称
	 */
	public void setStorageName(String storageName){
		this.storageName = storageName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  金额
	 */

	@Column(name ="MONEY",nullable=true,length=32)
	public String getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  金额
	 */
	public void setMoney(String money){
		this.money = money;
	}
}
