package com.emk.email.esendht.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

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
public class ESendHtEntityA implements java.io.Serializable {
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
	private String sendState;
	@Excel(name="发送状态",width=15)
	private String sendState2;
	/**发送时间*/
	@Excel(name="发送时间",width=15)
	private String sendTime;
	/**状态*/
	private String state;
	@Excel(name="回复状态",width=15)
	private String state2;
	/**回复时间*/
	@Excel(name="回复时间",width=15)
	private String replyTime;
	/**电话*/
	@Excel(name="同事电话",width=15)
	private String telphone;
	@Excel(name="职业危害",width=15)
	private String zywh;
	@Excel(name="上岗证",width=15)
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
		return xqqx;
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
		return sendState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送状态
	 */
	public void setSendState(String sendState){
		this.sendState = sendState;
	}

	public String getSendState2() {
		if("0".equals(sendState)){
			return "创建";
		}else if("1".equals(sendState)){
			return "已发送";
		}
		return "";
	}

	public void setSendState2(String sendState2) {
		this.sendState2 = sendState2;
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
		return state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setState(String state){
		this.state = state;
	}

	public String getState2() {
		if("0".equals(state)){
			return "创建";
		}else if("1".equals(state)){
			return "已确认";
		}else if("2".equals(state)){
			return "拒绝";
		}
		return "";
	}

	public void setState2(String state2) {
		this.state2 = state2;
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
