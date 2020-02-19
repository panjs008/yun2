package com.emk.caiwu.bankrecord.entity;

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
 * @Description: 现金银行
 * @author onlineGenerator
 * @date 2019-12-30 17:14:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_bank_record", schema = "")
@SuppressWarnings("serial")
public class EmkBankRecordEntity implements java.io.Serializable {
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
	/**交易日期*/
	@Excel(name="交易日期",width=15)
	private String dealDate;
	/**仓库ID*/
	@Excel(name="仓库ID",width=15)
	private String storageId;
	/**仓库名称*/
	@Excel(name="仓库名称",width=15)
	private String storageName;
	/**账户*/
	@Excel(name="账户",width=15)
	private String bankAccount;
	/**摘要*/
	@Excel(name="摘要",width=15)
	private String remark;
	/**收入*/
	@Excel(name="收入",width=15)
	private String income;
	/**支出*/
	@Excel(name="支出",width=15)
	private String outcome;
	/**余额*/
	@Excel(name="余额",width=15)
	private String balance;
	/**转账人*/
	@Excel(name="转账人",width=15)
	private String transfer;
	/**转账人ID*/
	@Excel(name="转账人ID",width=15)
	private String transferId;
	/**备注*/
	@Excel(name="备注",width=15)
	private String beizhu;
	private String bankType;

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
	 *@return: java.lang.String  交易日期
	 */

	@Column(name ="DEAL_DATE",nullable=true,length=32)
	public String getDealDate(){
		return this.dealDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  交易日期
	 */
	public void setDealDate(String dealDate){
		this.dealDate = dealDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库ID
	 */

	@Column(name ="STORAGE_ID",nullable=true,length=32)
	public String getStorageId(){
		return this.storageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库ID
	 */
	public void setStorageId(String storageId){
		this.storageId = storageId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库名称
	 */

	@Column(name ="STORAGE_NAME",nullable=true,length=56)
	public String getStorageName(){
		return this.storageName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库名称
	 */
	public void setStorageName(String storageName){
		this.storageName = storageName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账户
	 */

	@Column(name ="BANK_ACCOUNT",nullable=true,length=32)
	public String getBankAccount(){
		return this.bankAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账户
	 */
	public void setBankAccount(String bankAccount){
		this.bankAccount = bankAccount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */

	@Column(name ="REMARK",nullable=true,length=256)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收入
	 */

	@Column(name ="INCOME",nullable=true,length=32)
	public String getIncome(){
		return this.income;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收入
	 */
	public void setIncome(String income){
		this.income = income;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支出
	 */

	@Column(name ="OUTCOME",nullable=true,length=32)
	public String getOutcome(){
		return this.outcome;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支出
	 */
	public void setOutcome(String outcome){
		this.outcome = outcome;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  余额
	 */

	@Column(name ="BALANCE",nullable=true,length=32)
	public String getBalance(){
		return this.balance;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  余额
	 */
	public void setBalance(String balance){
		this.balance = balance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  转账人
	 */

	@Column(name ="TRANSFER",nullable=true,length=32)
	public String getTransfer(){
		return this.transfer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  转账人
	 */
	public void setTransfer(String transfer){
		this.transfer = transfer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  转账人ID
	 */

	@Column(name ="TRANSFER_ID",nullable=true,length=32)
	public String getTransferId(){
		return this.transferId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  转账人ID
	 */
	public void setTransferId(String transferId){
		this.transferId = transferId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="BEIZHU",nullable=true,length=256)
	public String getBeizhu(){
		return this.beizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBeizhu(String beizhu){
		this.beizhu = beizhu;
	}

	@Column(name ="bank_type",nullable=true,length=256)
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
}
