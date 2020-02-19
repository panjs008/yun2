package com.emk.product.product.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "emk_product", schema = "")
public class EmkProductEntityA implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "类别代码")
    private String proType;
    @Excel(name = "类别名称")
    private String proTypeName;
    @Excel(name = "商品名称")
    private String proZnName;
    @Excel(name = "商品编号")
    private String proNum;
    @Excel(name = "条码编号")
    private String barCode;

    private String proZjm;
    @Excel(name = "规格")
    private String standards;
    @Excel(name = "型号")
    private String brand;
    @Excel(name = "单位")
    private String unit;

    /**商品照片*/
    @Excel(name="商品照片",width=15)
    private String proImage;
    private String proImageUrl;

    @Excel(name="单位重量",width=15)
    private String unitWight;
    @Excel(name="零售价",width=15)
    private String sellPrice;
    @Excel(name="成本价",width=15)
    private String costPrice;

    @Excel(name="预警天数",width=15)
    private String warnDays;
    @Excel(name="备注",width=15)
    private String remark;

    private String departId;
    private String orgCode;

    /**a01a01a01*/
    @Excel(name="a01a01a01",width=15)
    private String a01a01a01;
    /**a01a01a02*/
    @Excel(name="a01a01a02",width=15)
    private String a01a01a02;
    /**a01a01a03*/
    @Excel(name="a01a01a03",width=15)
    private String a01a01a03;
    /**a01a01a04*/
    @Excel(name="a01a01a04",width=15)
    private String a01a01a04;
    /**a01a01a05*/
    @Excel(name="a01a01a05",width=15)
    private String a01a01a05;
    /**a01a01a06*/
    @Excel(name="a01a01a06",width=15)
    private String a01a01a06;
    /**a01a01a07*/
    @Excel(name="a01a01a07",width=15)
    private String a01a01a07;
    /**a01a01a08*/
    @Excel(name="a01a01a08",width=15)
    private String a01a01a08;
    /**a01a01a09*/
    @Excel(name="a01a01a09",width=15)
    private String a01a01a09;
    /**a01a01a10*/
    @Excel(name="a01a01a10",width=15)
    private String a01a01a10;
    /**a01a01a11*/
    @Excel(name="a01a01a11",width=15)
    private String a01a01a11;
    /**a01a01a12*/
    @Excel(name="a01a01a12",width=15)
    private String a01a01a12;
    /**a01a01a13*/
    @Excel(name="a01a01a13",width=15)
    private String a01a01a13;
    /**a01a01a14*/
    @Excel(name="a01a01a14",width=15)
    private String a01a01a14;
    /**a01a01a15*/
    @Excel(name="a01a01a15",width=15)
    private String a01a01a15;
    /**a01a01a16*/
    @Excel(name="a01a01a16",width=15)
    private String a01a01a16;
    /**a01a01a17*/
    @Excel(name="a01a01a17",width=15)
    private String a01a01a17;
    /**a01a01a18*/
    @Excel(name="a01a01a18",width=15)
    private String a01a01a18;
    /**a01a01a19*/
    @Excel(name="a01a01a19",width=15)
    private String a01a01a19;
    /**a01a01a20*/
    @Excel(name="a01a01a20",width=15)
    private String a01a01a20;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_DATE", nullable = true, length = 20)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "SYS_ORG_CODE", nullable = true, length = 50)
    public String getSysOrgCode() {
        return this.sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    @Column(name = "PRO_NUM", nullable = true, length = 32)
    public String getProNum() {
        return this.proNum;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    @Column(name = "PRO_TYPE", nullable = true, length = 32)
    public String getProType() {
        return this.proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    @Column(name = "PRO_ZN_NAME", nullable = true, length = 32)
    public String getProZnName() {
        return this.proZnName;
    }

    public void setProZnName(String proZnName) {
        this.proZnName = proZnName;
    }

    @Column(name = "BRAND", nullable = true, length = 32)
    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "UNIT", nullable = true, length = 32)
    //@Formula("(select t2.typename from t_s_type t2 left join t_s_typegroup t1 on t1.ID=t2.typegroupid where t1.typegroupcode='units' and t2.typecode=unit and t1.org_code=org_code)")
    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    @Column(name = "pro_type_name", nullable = true, length = 32)
    public String getProTypeName() {
        return this.proTypeName;
    }

    public void setProTypeName(String proTypeName) {
        this.proTypeName = proTypeName;
    }

    @Column(name = "standards", nullable = true, length = 32)
    public String getStandards() {
        return standards;
    }

    public void setStandards(String standards) {
        this.standards = standards;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a01
     */

    @Column(name ="A01A01A01",nullable=true,length=32)
    public String getA01a01a01(){
        return this.a01a01a01;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a01
     */
    public void setA01a01a01(String a01a01a01){
        this.a01a01a01 = a01a01a01;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a02
     */

    @Column(name ="A01A01A02",nullable=true,length=32)
    public String getA01a01a02(){
        return this.a01a01a02;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a02
     */
    public void setA01a01a02(String a01a01a02){
        this.a01a01a02 = a01a01a02;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a03
     */

    @Column(name ="A01A01A03",nullable=true,length=32)
    public String getA01a01a03(){
        return this.a01a01a03;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a03
     */
    public void setA01a01a03(String a01a01a03){
        this.a01a01a03 = a01a01a03;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a04
     */

    @Column(name ="A01A01A04",nullable=true,length=32)
    public String getA01a01a04(){
        return this.a01a01a04;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a04
     */
    public void setA01a01a04(String a01a01a04){
        this.a01a01a04 = a01a01a04;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a05
     */

    @Column(name ="A01A01A05",nullable=true,length=32)
    public String getA01a01a05(){
        return this.a01a01a05;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a05
     */
    public void setA01a01a05(String a01a01a05){
        this.a01a01a05 = a01a01a05;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a06
     */

    @Column(name ="A01A01A06",nullable=true,length=32)
    public String getA01a01a06(){
        return this.a01a01a06;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a06
     */
    public void setA01a01a06(String a01a01a06){
        this.a01a01a06 = a01a01a06;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a07
     */

    @Column(name ="A01A01A07",nullable=true,length=32)
    public String getA01a01a07(){
        return this.a01a01a07;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a07
     */
    public void setA01a01a07(String a01a01a07){
        this.a01a01a07 = a01a01a07;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a08
     */

    @Column(name ="A01A01A08",nullable=true,length=32)
    public String getA01a01a08(){
        return this.a01a01a08;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a08
     */
    public void setA01a01a08(String a01a01a08){
        this.a01a01a08 = a01a01a08;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a09
     */

    @Column(name ="A01A01A09",nullable=true,length=32)
    public String getA01a01a09(){
        return this.a01a01a09;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a09
     */
    public void setA01a01a09(String a01a01a09){
        this.a01a01a09 = a01a01a09;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a10
     */

    @Column(name ="A01A01A10",nullable=true,length=32)
    public String getA01a01a10(){
        return this.a01a01a10;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a10
     */
    public void setA01a01a10(String a01a01a10){
        this.a01a01a10 = a01a01a10;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a11
     */

    @Column(name ="A01A01A11",nullable=true,length=32)
    public String getA01a01a11(){
        return this.a01a01a11;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a11
     */
    public void setA01a01a11(String a01a01a11){
        this.a01a01a11 = a01a01a11;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a12
     */

    @Column(name ="A01A01A12",nullable=true,length=32)
    public String getA01a01a12(){
        return this.a01a01a12;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a12
     */
    public void setA01a01a12(String a01a01a12){
        this.a01a01a12 = a01a01a12;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a13
     */

    @Column(name ="A01A01A13",nullable=true,length=32)
    public String getA01a01a13(){
        return this.a01a01a13;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a13
     */
    public void setA01a01a13(String a01a01a13){
        this.a01a01a13 = a01a01a13;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a14
     */

    @Column(name ="A01A01A14",nullable=true,length=32)
    public String getA01a01a14(){
        return this.a01a01a14;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a14
     */
    public void setA01a01a14(String a01a01a14){
        this.a01a01a14 = a01a01a14;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a15
     */

    @Column(name ="A01A01A15",nullable=true,length=32)
    public String getA01a01a15(){
        return this.a01a01a15;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a15
     */
    public void setA01a01a15(String a01a01a15){
        this.a01a01a15 = a01a01a15;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a16
     */

    @Column(name ="A01A01A16",nullable=true,length=32)
    public String getA01a01a16(){
        return this.a01a01a16;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a16
     */
    public void setA01a01a16(String a01a01a16){
        this.a01a01a16 = a01a01a16;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a17
     */

    @Column(name ="A01A01A17",nullable=true,length=32)
    public String getA01a01a17(){
        return this.a01a01a17;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a17
     */
    public void setA01a01a17(String a01a01a17){
        this.a01a01a17 = a01a01a17;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a18
     */

    @Column(name ="A01A01A18",nullable=true,length=32)
    public String getA01a01a18(){
        return this.a01a01a18;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a18
     */
    public void setA01a01a18(String a01a01a18){
        this.a01a01a18 = a01a01a18;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a19
     */

    @Column(name ="A01A01A19",nullable=true,length=32)
    public String getA01a01a19(){
        return this.a01a01a19;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a19
     */
    public void setA01a01a19(String a01a01a19){
        this.a01a01a19 = a01a01a19;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  a01a01a20
     */

    @Column(name ="A01A01A20",nullable=true,length=32)
    public String getA01a01a20(){
        return this.a01a01a20;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  a01a01a20
     */
    public void setA01a01a20(String a01a01a20){
        this.a01a01a20 = a01a01a20;
    }
    @Column(name ="pro_zjm",nullable=true,length=32)
    public String getProZjm() {
        return proZjm;
    }

    public void setProZjm(String proZjm) {
        this.proZjm = proZjm;
    }

    @Column(name ="pro_image",nullable=true,length=32)
    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    @Column(name ="pro_image_url",nullable=true,length=32)
    public String getProImageUrl() {
        return proImageUrl;
    }

    public void setProImageUrl(String proImageUrl) {
        this.proImageUrl = proImageUrl;
    }

    @Column(name ="unit_wight",nullable=true,length=32)
    public String getUnitWight() {
        return unitWight;
    }

    public void setUnitWight(String unitWight) {
        this.unitWight = unitWight;
    }

    @Column(name ="sell_price",nullable=true,length=32)
    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Column(name ="cost_price",nullable=true,length=32)
    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    @Column(name ="warn_days",nullable=true,length=32)
    public String getWarnDays() {
        return warnDays;
    }

    public void setWarnDays(String warnDays) {
        this.warnDays = warnDays;
    }

    @Column(name ="remark",nullable=true,length=32)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    @Column(name ="bar_code",nullable=true,length=32)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
