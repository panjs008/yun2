package com.emk.storage.payover.entity;

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
 * @Description: 结算表
 * @author onlineGenerator
 * @date 2019-09-11 16:12:08
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_pay_over", schema = "")
@SuppressWarnings("serial")
public class EmkPayOverEntity implements java.io.Serializable {
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
	/**订单号*/
	@Excel(name="订单号",width=15)
	private String orderNo;
	/**结算日期*/
	@Excel(name="结算日期",width=15)
	private String overDate;
	/**订单金额*/
	@Excel(name="订单金额",width=15)
	private String orderMoney;
	/**叫布费用*/
	@Excel(name="叫布费用",width=15)
	private String jbMoney;
	/**物料费用*/
	@Excel(name="物料费用",width=15)
	private String wlMoney;
	/**发货总金额*/
	@Excel(name="发货总金额",width=15)
	private String fhMoney;
	@Excel(name="退货总金额",width=15)
	private String thMoney;
	/**付款总金额*/
	@Excel(name="付款总金额",width=15)
	private String fkMoney;
	
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
	 *@return: java.lang.String  订单号
	 */

	@Column(name ="ORDER_NO",nullable=true,length=32)
	public String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单号
	 */
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算日期
	 */

	@Column(name ="OVER_DATE",nullable=true,length=32)
	public String getOverDate(){
		return this.overDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算日期
	 */
	public void setOverDate(String overDate){
		this.overDate = overDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单金额
	 */

	@Column(name ="ORDER_MONEY",nullable=true,length=32)
	public String getOrderMoney(){
		return this.orderMoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单金额
	 */
	public void setOrderMoney(String orderMoney){
		this.orderMoney = orderMoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  叫布费用
	 */

	@Column(name ="JB_MONEY",nullable=true,length=32)
	public String getJbMoney(){
		return this.jbMoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  叫布费用
	 */
	public void setJbMoney(String jbMoney){
		this.jbMoney = jbMoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  物料费用
	 */

	@Column(name ="WL_MONEY",nullable=true,length=32)
	public String getWlMoney(){
		return this.wlMoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  物料费用
	 */
	public void setWlMoney(String wlMoney){
		this.wlMoney = wlMoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发货总金额
	 */

	@Column(name ="FH_MONEY",nullable=true,length=32)
	public String getFhMoney(){
		return this.fhMoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发货总金额
	 */
	public void setFhMoney(String fhMoney){
		this.fhMoney = fhMoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  付款总金额
	 */

	@Column(name ="FK_MONEY",nullable=true,length=32)
	public String getFkMoney(){
		return this.fkMoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  付款总金额
	 */
	public void setFkMoney(String fkMoney){
		this.fkMoney = fkMoney;
	}

	@Column(name ="th_money",nullable=true,length=32)
	public String getThMoney() {
		return thMoney;
	}

	public void setThMoney(String thMoney) {
		this.thMoney = thMoney;
	}
}
