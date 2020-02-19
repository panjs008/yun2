package com.emk.system.sysparam.entity;

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
 * @Description: 参数表
 * @author onlineGenerator
 * @date 2019-12-08 13:14:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_sys_param", schema = "")
@SuppressWarnings("serial")
public class EmkSysParamEntity implements java.io.Serializable {
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
	/**参数名称*/
	@Excel(name="参数名称",width=15)
	private String paramName;
	/**参数值*/
	@Excel(name="参数值",width=15)
	private String paramValue;
	/**备注*/
	@Excel(name="备注",width=15)
	private String remark;
	/**省份*/
	@Excel(name="省份",width=15)
	private String shengFen;
	/**城市*/
	@Excel(name="城市",width=15)
	private String chengShi;
	/**片区*/
	@Excel(name="片区",width=15)
	private String pianQu;
	private String departId;

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

	@Column(name ="param_name",nullable=true,length=256)
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参数值
	 */

	@Column(name ="param_value",nullable=true,length=256)
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
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
	 *@return: java.lang.String  省份
	 */

	@Column(name ="SHENG_FEN",nullable=true,length=32)
	public String getShengFen(){
		return this.shengFen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省份
	 */
	public void setShengFen(String shengFen){
		this.shengFen = shengFen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市
	 */

	@Column(name ="CHENG_SHI",nullable=true,length=32)
	public String getChengShi(){
		return this.chengShi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市
	 */
	public void setChengShi(String chengShi){
		this.chengShi = chengShi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  片区
	 */

	@Column(name ="PIAN_QU",nullable=true,length=32)
	public String getPianQu(){
		return this.pianQu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  片区
	 */
	public void setPianQu(String pianQu){
		this.pianQu = pianQu;
	}

	@Column(name ="depart_id",nullable=true,length=32)
	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}
}
