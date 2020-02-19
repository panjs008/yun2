package com.emk.bill.offerprice.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "emk_offer_price", schema = "")
public class EmkOfferPriceEntity implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;

    private String departId;
    private String orgCode;
    private String total;
    private String money;
    private String state;
    @Excel(name = "客户名称")
    private String cusName;
    @Excel(name = "出货仓库")
    private String storageName;
    /**业务员*/
    private String businesser;

    /**a01a13a01*/
    @Excel(name="a01a13a01",width=15)
    private String a01a13a01;
    /**a01a13a02*/
    @Excel(name="a01a13a02",width=15)
    private String a01a13a02;
    /**a01a13a03*/
    @Excel(name="a01a13a03",width=15)
    private String a01a13a03;
    /**a01a13a04*/
    @Excel(name="a01a13a04",width=15)
    private String a01a13a04;
    /**a01a13a05*/
    @Excel(name="a01a13a05",width=15)
    private String a01a13a05;
    /**a01a13a06*/
    @Excel(name="a01a13a06",width=15)
    private String a01a13a06;
    /**a01a13a07*/
    @Excel(name="a01a13a07",width=15)
    private String a01a13a07;
    private String a01a13a08;
    /**a01a13a08*/
    @Excel(name="a01a13a09",width=15)
    private String a01a13a09;
    @Excel(name="a01a13a10",width=15)
    private String a01a13a10;
    @Excel(name="a01a13a11",width=15)
    private String a01a13a11;
    @Excel(name="a01a13a12",width=15)
    private String a01a13a12;
    @Excel(name="a01a13a13",width=15)
    private String a01a13a13;
    @Excel(name="a01a13a14",width=15)
    private String a01a13a14;
    @Excel(name="a01a13a15",width=15)
    private String a01a13a15;
    @Excel(name="a01a13a16",width=15)
    private String a01a13a16;
    @Excel(name="a01a13a17",width=15)
    private String a01a13a17;
    @Excel(name="a01a13a18",width=15)
    private String a01a13a18;
    @Excel(name="a01a13a19",width=15)
    private String a01a13a19;
    @Excel(name="a01a13a20",width=15)
    private String a01a13a20;

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

    @Column(name = "a01a13a01", nullable = true, length = 32)
    public String getA01a13a01() {
        return a01a13a01;
    }

    public void setA01a13a01(String a01a13a01) {
        this.a01a13a01 = a01a13a01;
    }

    @Column(name = "a01a13a02", nullable = true, length = 32)
    public String getA01a13a02() {
        return a01a13a02;
    }

    public void setA01a13a02(String a01a13a02) {
        this.a01a13a02 = a01a13a02;
    }

    @Column(name = "a01a13a03", nullable = true, length = 32)
    public String getA01a13a03() {
        return a01a13a03;
    }

    public void setA01a13a03(String a01a13a03) {
        this.a01a13a03 = a01a13a03;
    }

    @Column(name = "a01a13a04", nullable = true, length = 32)
    public String getA01a13a04() {
        return a01a13a04;
    }

    public void setA01a13a04(String a01a13a04) {
        this.a01a13a04 = a01a13a04;
    }

    @Column(name = "a01a13a05", nullable = true, length = 32)
    public String getA01a13a05() {
        return a01a13a05;
    }

    public void setA01a13a05(String a01a13a05) {
        this.a01a13a05 = a01a13a05;
    }

    @Column(name = "a01a13a06", nullable = true, length = 32)
    public String getA01a13a06() {
        return a01a13a06;
    }

    public void setA01a13a06(String a01a13a06) {
        this.a01a13a06 = a01a13a06;
    }

    @Column(name = "a01a13a07", nullable = true, length = 32)
    public String getA01a13a07() {
        return a01a13a07;
    }

    public void setA01a13a07(String a01a13a07) {
        this.a01a13a07 = a01a13a07;
    }

    @Column(name = "a01a13a08", nullable = true, length = 32)
    public String getA01a13a08() {
        return a01a13a08;
    }

    public void setA01a13a08(String a01a13a08) {
        this.a01a13a08 = a01a13a08;
    }

    @Column(name = "a01a13a09", nullable = true, length = 32)
    public String getA01a13a09() {
        return a01a13a09;
    }

    public void setA01a13a09(String a01a13a09) {
        this.a01a13a09 = a01a13a09;
    }

    @Column(name = "a01a13a10", nullable = true, length = 32)
    public String getA01a13a10() {
        return a01a13a10;
    }

    public void setA01a13a10(String a01a13a10) {
        this.a01a13a10 = a01a13a10;
    }

    @Column(name = "a01a13a11", nullable = true, length = 32)
    public String getA01a13a11() {
        return a01a13a11;
    }

    public void setA01a13a11(String a01a13a11) {
        this.a01a13a11 = a01a13a11;
    }

    @Column(name = "a01a13a12", nullable = true, length = 32)
    public String getA01a13a12() {
        return a01a13a12;
    }

    public void setA01a13a12(String a01a13a12) {
        this.a01a13a12 = a01a13a12;
    }

    @Column(name = "a01a13a13", nullable = true, length = 32)
    public String getA01a13a13() {
        return a01a13a13;
    }

    public void setA01a13a13(String a01a13a13) {
        this.a01a13a13 = a01a13a13;
    }

    @Column(name = "a01a13a14", nullable = true, length = 32)
    public String getA01a13a14() {
        return a01a13a14;
    }

    public void setA01a13a14(String a01a13a14) {
        this.a01a13a14 = a01a13a14;
    }

    @Column(name = "a01a13a15", nullable = true, length = 32)
    public String getA01a13a15() {
        return a01a13a15;
    }

    public void setA01a13a15(String a01a13a15) {
        this.a01a13a15 = a01a13a15;
    }

    @Column(name = "a01a13a16", nullable = true, length = 32)
    public String getA01a13a16() {
        return a01a13a16;
    }

    public void setA01a13a16(String a01a13a16) {
        this.a01a13a16 = a01a13a16;
    }

    @Column(name = "a01a13a17", nullable = true, length = 32)
    public String getA01a13a17() {
        return a01a13a17;
    }

    public void setA01a13a17(String a01a13a17) {
        this.a01a13a17 = a01a13a17;
    }

    @Column(name = "a01a13a18", nullable = true, length = 32)
    public String getA01a13a18() {
        return a01a13a18;
    }

    public void setA01a13a18(String a01a13a18) {
        this.a01a13a18 = a01a13a18;
    }

    @Column(name = "a01a13a19", nullable = true, length = 32)
    public String getA01a13a19() {
        return a01a13a19;
    }

    public void setA01a13a19(String a01a13a19) {
        this.a01a13a19 = a01a13a19;
    }

    @Column(name = "a01a13a20", nullable = true, length = 32)
    public String getA01a13a20() {
        return a01a13a20;
    }

    public void setA01a13a20(String a01a13a20) {
        this.a01a13a20 = a01a13a20;
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

    @Column(name="total", nullable=true, length=32)
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Column(name = "money", nullable = true, length = 32)
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Column(name = "CUS_NAME", nullable = true, length = 32)
    public String getCusName() {
        return this.cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }
    @Column(name = "STORAGE_NAME", nullable = true, length = 256)
    public String getStorageName() {
        return this.storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  业务员
     */

    @Column(name ="BUSINESSER",nullable=true,length=32)
    public String getBusinesser(){
        return this.businesser;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  业务员
     */
    public void setBusinesser(String businesser){
        this.businesser = businesser;
    }
}
