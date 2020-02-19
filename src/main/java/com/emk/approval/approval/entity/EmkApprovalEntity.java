package com.emk.approval.approval.entity;

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
 * @Description: 审批业务表
 * @author onlineGenerator
 * @date 2019-01-26 19:12:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_approval", schema = "")
@SuppressWarnings("serial")
public class EmkApprovalEntity implements java.io.Serializable {
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
	/**申请人ID*/
	@Excel(name="申请人ID",width=15)
	private String commitId;
	/**提交时间*/
	@Excel(name="提交时间",width=15)
	private String commitTime;
	/**工单类型*/
	@Excel(name="工单类型",width=15)
	private Integer type;								// 0 订单
	/**当前节点名称*/
	@Excel(name="当前节点名称",width=15)
	private String processName;
	/**当前节点代码*/
	@Excel(name="当前节点代码",width=15)
	private String processNode;
	/**状态*/
	@Excel(name="状态",width=15)
	private Integer status;						 // 0 创建 1 提交 2 已完成 3 提交叫布 4 核实通过 5 回退叫布 6 提交其他物料发放 7 工厂确认通过 8 回退其他物料发放 9 提交出货 10 回退出货
																							// 11 出货通过退货 12 提交退货 13 回退退货 14 退货通过 15 提交付款 16 回退付款 17 工厂确认付款 18 出货通过付款
																							// 24 采购员通过 25 拣货核实
	/**审核人*/
	@Excel(name="审核人",width=15)
	private String bpmSher;
	/**审核状态*/
	@Excel(name="审核状态",width=15)
	private String bpmStatus;
	/**审核人ID*/
	@Excel(name="审核人ID",width=15)
	private String bpmSherId;
	/**下一环节处理人*/
	@Excel(name="下一环节处理人",width=15)
	private String nextBpmSher;
	/**下一环节处理人ID*/
	@Excel(name="下一环节处理人ID",width=15)
	private String nextBpmSherId;
	/**审核时间*/
	@Excel(name="审核时间",width=15)
	private String bpmDate;
	/**单号*/
	@Excel(name="单号",width=15)
	private String workNum;
	/**表单ID*/
	@Excel(name="表单ID",width=15)
	private String formId;
	private String state1;
	private String state2;
	private String state3;
	
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
	 *@return: java.lang.String  申请人ID
	 */

	@Column(name ="COMMIT_ID",nullable=true,length=32)
	public String getCommitId(){
		return this.commitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请人ID
	 */
	public void setCommitId(String commitId){
		this.commitId = commitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提交时间
	 */

	@Column(name ="COMMIT_TIME",nullable=true,length=32)
	public String getCommitTime(){
		return this.commitTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提交时间
	 */
	public void setCommitTime(String commitTime){
		this.commitTime = commitTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  工单类型
	 */

	@Column(name ="TYPE",nullable=true,length=2)
	public Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  工单类型
	 */
	public void setType(Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  当前节点名称
	 */
	@Column(name ="PROCESS_NAME",nullable=true,length=32)
	public String getProcessName(){
		return this.processName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  当前节点名称
	 */
	public void setProcessName(String processName){
		this.processName = processName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  当前节点代码
	 */
	@Formula("(select CONCAT(p.NAME_,'-',p.TASK_DEF_KEY_) from act_ru_task p where p.ASSIGNEE_ = form_id limit 0,1)")
	@Column(name ="PROCESS_NODE",nullable=true,length=32)
	public String getProcessNode(){
		return this.processNode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  当前节点代码
	 */
	public void setProcessNode(String processNode){
		this.processNode = processNode;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */

	@Column(name ="STATUS",nullable=true,length=2)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setStatus(Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */

	@Column(name ="BPM_SHER",nullable=true,length=32)
	public String getBpmSher(){
		return this.bpmSher;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setBpmSher(String bpmSher){
		this.bpmSher = bpmSher;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核状态
	 */

	@Column(name ="BPM_STATUS",nullable=true,length=32)
	public String getBpmStatus(){
		return this.bpmStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核状态
	 */
	public void setBpmStatus(String bpmStatus){
		this.bpmStatus = bpmStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人ID
	 */

	@Column(name ="BPM_SHER_ID",nullable=true,length=32)
	public String getBpmSherId(){
		return this.bpmSherId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人ID
	 */
	public void setBpmSherId(String bpmSherId){
		this.bpmSherId = bpmSherId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核时间
	 */

	@Column(name ="BPM_DATE",nullable=true,length=32)
	public String getBpmDate(){
		return this.bpmDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核时间
	 */
	public void setBpmDate(String bpmDate){
		this.bpmDate = bpmDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单号
	 */

	@Column(name ="WORK_NUM",nullable=true,length=32)
	public String getWorkNum(){
		return this.workNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单号
	 */
	public void setWorkNum(String workNum){
		this.workNum = workNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表单ID
	 */

	@Column(name ="FORM_ID",nullable=true,length=32)
	public String getFormId(){
		return this.formId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表单ID
	 */
	public void setFormId(String formId){
		this.formId = formId;
	}

	@Column(name = "state1", nullable = true, length = 32)
	public String getState1() {
		return state1;
	}

	public void setState1(String state1) {
		this.state1 = state1;
	}

	@Column(name = "state2", nullable = true, length = 32)
	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}

	@Column(name = "state3", nullable = true, length = 32)
	public String getState3() {
		return state3;
	}

	public void setState3(String state3) {
		this.state3 = state3;
	}

	@Column(name = "next_bpm_sher", nullable = true, length = 32)
	public String getNextBpmSher() {
		return nextBpmSher;
	}

	public void setNextBpmSher(String nextBpmSher) {
		this.nextBpmSher = nextBpmSher;
	}

	@Column(name = "next_bpm_sher_id", nullable = true, length = 32)
	public String getNextBpmSherId() {
		return nextBpmSherId;
	}

	public void setNextBpmSherId(String nextBpmSherId) {
		this.nextBpmSherId = nextBpmSherId;
	}

}
