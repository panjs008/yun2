package com.emk.email.esendht.entity;

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
 * @Description: 邮箱数据
 * @author onlineGenerator
 * @date 2019-10-29 23:23:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "e_send_ht", schema = "")
@SuppressWarnings("serial")
public class ESendHtEntity implements java.io.Serializable {
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
	/**同事编号*/
	@Excel(name="同事编号",width=15)
	private String workNo;
	/**同事姓名*/
	@Excel(name="同事姓名",width=15)
	private String userName;
	/**续签期限*/
	@Excel(name="续签期限",width=15)
	private String xqqx;
	/**模板编号*/
	@Excel(name="模板编号",width=15)
	private String mbbh;
	/**经理姓名*/
	@Excel(name="经理姓名",width=15)
	private String manger;
	/**经理姓名*/
	@Excel(name="经理电话",width=15)
	private String mangerTelphone;
	/**经理邮箱*/
	@Excel(name="经理邮箱",width=15)
	private String email;
	/**发送状态*/
	@Excel(name="发送状态",width=15)
	private String sendState;
	/**电话*/
	@Excel(name="电话",width=15)
	private String telphone;
	/**状态*/
	@Excel(name="状态",width=15)
	private String state;
	/**备注*/
	@Excel(name="备注",width=15)
	private String remark;
	/**发送时间*/
	@Excel(name="发送时间",width=15)
	private String sendTime;
	/**回复时间*/
	@Excel(name="回复时间",width=15)
	private String replyTime;
	@Excel(name="职业危害",width=15)
	private String zywh;
	@Excel(name="上岗信息",width=15)
	private String sgxx;
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
	 *@return: java.lang.String  同事编号
	 */

	@Column(name ="WORK_NO",nullable=true,length=32)
	public String getWorkNo(){
		return this.workNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  同事编号
	 */
	public void setWorkNo(String workNo){
		this.workNo = workNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  同事姓名
	 */

	@Column(name ="USER_NAME",nullable=true,length=32)
	public String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  同事姓名
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  续签期限
	 */

	@Column(name ="XQQX",nullable=true,length=32)
	public String getXqqx(){
		return this.xqqx;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  续签期限
	 */
	public void setXqqx(String xqqx){
		this.xqqx = xqqx;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模板编号
	 */

	@Column(name ="MBBH",nullable=true,length=32)
	public String getMbbh(){
		return this.mbbh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板编号
	 */
	public void setMbbh(String mbbh){
		this.mbbh = mbbh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经理姓名
	 */

	@Column(name ="MANGER",nullable=true,length=32)
	public String getManger(){
		return this.manger;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经理姓名
	 */
	public void setManger(String manger){
		this.manger = manger;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经理邮箱
	 */

	@Column(name ="EMAIL",nullable=true,length=56)
	public String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经理邮箱
	 */
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送状态
	 */

	@Column(name ="SEND_STATE",nullable=true,length=32)
	public String getSendState(){
		return this.sendState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送状态
	 */
	public void setSendState(String sendState){
		this.sendState = sendState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */

	@Column(name ="TELPHONE",nullable=true,length=32)
	public String getTelphone(){
		return this.telphone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setTelphone(String telphone){
		this.telphone = telphone;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */

	@Column(name ="STATE",nullable=true,length=32)
	public String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="REMARK",nullable=true,length=256)
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
	 *@return: java.lang.String  发送时间
	 */

	@Column(name ="SEND_TIME",nullable=true,length=32)
	public String getSendTime(){
		return this.sendTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送时间
	 */
	public void setSendTime(String sendTime){
		this.sendTime = sendTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回复时间
	 */

	@Column(name ="REPLY_TIME",nullable=true,length=32)
	public String getReplyTime(){
		return this.replyTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回复时间
	 */
	public void setReplyTime(String replyTime){
		this.replyTime = replyTime;
	}

	@Column(name ="manger_telphone",nullable=true,length=32)
	public String getMangerTelphone() {
		return mangerTelphone;
	}

	public void setMangerTelphone(String mangerTelphone) {
		this.mangerTelphone = mangerTelphone;
	}

	@Column(name ="zywh",nullable=true,length=32)
	public String getZywh() {
		return zywh;
	}

	public void setZywh(String zywh) {
		this.zywh = zywh;
	}

	@Column(name ="sgxx",nullable=true,length=32)
	public String getSgxx() {
		return sgxx;
	}

	public void setSgxx(String sgxx) {
		this.sgxx = sgxx;
	}
}
