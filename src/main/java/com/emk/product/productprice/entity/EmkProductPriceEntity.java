package com.emk.product.productprice.entity;

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
 * @Description: 零售价表
 * @author onlineGenerator
 * @date 2019-12-23 22:25:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_product_price", schema = "")
@SuppressWarnings("serial")
public class EmkProductPriceEntity implements java.io.Serializable {
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
	/**序号*/
	@Excel(name="序号",width=15)
	private String priceNo;
	/**价格名称*/
	@Excel(name="价格名称",width=15)
	private String priceName;
	/**价格*/
	@Excel(name="价格",width=15)
	private String price;
	private String priceT;
	private String priceT2;

	/**商品ID*/
	@Excel(name="商品ID",width=15)
	private String productId;
	@Excel(name = "辅助单位1")
	private String unit1;
	@Excel(name = "辅助单位2")
	private String unit2;
	private String hsPercentage;
	private String hsPercentage2;

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

	@Column(name ="price_no",nullable=true,length=50)
	public String getPriceNo() {
		return priceNo;
	}

	public void setPriceNo(String priceNo) {
		this.priceNo = priceNo;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  价格名称
	 */

	@Column(name ="price_name",nullable=true,length=32)
	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  价格
	 */

	@Column(name ="PRICE",nullable=true,length=32)
	public String getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  价格
	 */
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品ID
	 */

	@Column(name ="PRODUCT_ID",nullable=true,length=32)
	public String getProductId(){
		return this.productId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品ID
	 */
	public void setProductId(String productId){
		this.productId = productId;
	}

	@Column(name ="unit1",nullable=true,length=32)
	public String getUnit1() {
		return unit1;
	}

	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}

	@Column(name ="unit2",nullable=true,length=32)
	public String getUnit2() {
		return unit2;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	@Column(name ="hs_percentage",nullable=true,length=32)
	public String getHsPercentage() {
		return hsPercentage;
	}

	public void setHsPercentage(String hsPercentage) {
		this.hsPercentage = hsPercentage;
	}

	@Column(name ="hs_percentage2",nullable=true,length=32)
	public String getHsPercentage2() {
		return hsPercentage2;
	}

	public void setHsPercentage2(String hsPercentage2) {
		this.hsPercentage2 = hsPercentage2;
	}

	@Column(name ="price_t",nullable=true,length=32)
	public String getPriceT() {
		return priceT;
	}

	public void setPriceT(String priceT) {
		this.priceT = priceT;
	}

	@Column(name ="price_t2",nullable=true,length=32)
	public String getPriceT2() {
		return priceT2;
	}

	public void setPriceT2(String priceT2) {
		this.priceT2 = priceT2;
	}
}
