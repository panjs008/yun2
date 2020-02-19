package com.emk.bill.proorderdetail.entity;

import com.emk.bound.minstoragedetail.entity.EmkMInStorageDetailEntityA;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class EmkProOrderDetailEntity20200118 implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "订单ID")
    private String proOrderId;
    /**a01a06a01*/
    @Excel(name="a01a06a01",width=15)
    private String a01a06a01;
    /**a01a06a02*/
    @Excel(name="a01a06a02",width=15)
    private String a01a06a02;
    /**a01a06a03*/
    @Excel(name="a01a06a03",width=15)
    private String a01a06a03;
    /**a01a06a04*/
    @Excel(name="a01a06a04",width=15)
    private String a01a06a04;
    /**a01a06a05*/
    @Excel(name="a01a06a05",width=15)
    private String a01a06a05;
    /**a01a06a06*/
    @Excel(name="a01a06a06",width=15)
    private String a01a06a06;
    /**a01a06a07*/
    @Excel(name="a01a06a07",width=15)
    private String a01a06a07;
    /**a01a06a08*/
    @Excel(name="a01a06a08",width=15)
    private String a01a06a08;
    /**a01a06a09*/
    @Excel(name="a01a06a09",width=15)
    private String a01a06a09;
    /**a01a06a10*/
    @Excel(name="a01a06a10",width=15)
    private String a01a06a10;
    /**a01a06a11*/
    @Excel(name="a01a06a11",width=15)
    private String a01a06a11;
    /**a01a06a12*/
    @Excel(name="a01a06a12",width=15)
    private String a01a06a12;
    /**a01a06a13*/
    @Excel(name="a01a06a13",width=15)
    private String a01a06a13;
    /**a01a06a14*/
    @Excel(name="a01a06a14",width=15)
    private String a01a06a14;
    /**a01a06a15*/
    @Excel(name="a01a06a15",width=15)
    private String a01a06a15;
    /**a01a06a16*/
    @Excel(name="a01a06a16",width=15)
    private String a01a06a16;
    /**a01a06a17*/
    @Excel(name="a01a06a17",width=15)
    private String a01a06a17;
    /**a01a06a18*/
    @Excel(name="a01a06a18",width=15)
    private String a01a06a18;
    /**a01a06a19*/
    @Excel(name="a01a06a19",width=15)
    private String a01a06a19;
    /**a01a06a20*/
    @Excel(name="a01a06a20",width=15)
    private String a01a06a20;

    private String departId;
    private String orgCode;

    private EmkMInStorageDetailEntityA emkMInStorageDetailEntityA;


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

    @Column(name = "PRO_ORDER_ID", nullable = true, length = 32)
    public String getProOrderId() {
        return this.proOrderId;
    }

    public void setProOrderId(String proOrderId) {
        this.proOrderId = proOrderId;
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

    @Column(name ="a01a06a01",nullable=true,length=32)
    public String getA01a06a01() {
        return a01a06a01;
    }

    public void setA01a06a01(String a01a06a01) {
        this.a01a06a01 = a01a06a01;
    }

    @Column(name ="a01a06a02",nullable=true,length=32)
    public String getA01a06a02() {
        return a01a06a02;
    }

    public void setA01a06a02(String a01a06a02) {
        this.a01a06a02 = a01a06a02;
    }

    @Column(name ="a01a06a03",nullable=true,length=32)
    public String getA01a06a03() {
        return a01a06a03;
    }

    public void setA01a06a03(String a01a06a03) {
        this.a01a06a03 = a01a06a03;
    }

    @Column(name ="a01a06a04",nullable=true,length=32)
    public String getA01a06a04() {
        return a01a06a04;
    }

    public void setA01a06a04(String a01a06a04) {
        this.a01a06a04 = a01a06a04;
    }

    @Column(name ="a01a06a05",nullable=true,length=32)
    public String getA01a06a05() {
        return a01a06a05;
    }

    public void setA01a06a05(String a01a06a05) {
        this.a01a06a05 = a01a06a05;
    }

    @Column(name ="a01a06a06",nullable=true,length=32)
    public String getA01a06a06() {
        return a01a06a06;
    }

    public void setA01a06a06(String a01a06a06) {
        this.a01a06a06 = a01a06a06;
    }

    @Column(name ="a01a06a07",nullable=true,length=32)
    public String getA01a06a07() {
        return a01a06a07;
    }

    public void setA01a06a07(String a01a06a07) {
        this.a01a06a07 = a01a06a07;
    }

    @Column(name ="a01a06a08",nullable=true,length=32)
    public String getA01a06a08() {
        return a01a06a08;
    }

    public void setA01a06a08(String a01a06a08) {
        this.a01a06a08 = a01a06a08;
    }

    @Column(name ="a01a06a09",nullable=true,length=32)
    public String getA01a06a09() {
        return a01a06a09;
    }

    public void setA01a06a09(String a01a06a09) {
        this.a01a06a09 = a01a06a09;
    }

    @Column(name ="a01a06a10",nullable=true,length=32)
    public String getA01a06a10() {
        return a01a06a10;
    }

    public void setA01a06a10(String a01a06a10) {
        this.a01a06a10 = a01a06a10;
    }

    @Column(name ="a01a06a11",nullable=true,length=32)
    public String getA01a06a11() {
        return a01a06a11;
    }

    public void setA01a06a11(String a01a06a11) {
        this.a01a06a11 = a01a06a11;
    }

    @Column(name ="a01a06a12",nullable=true,length=32)
    public String getA01a06a12() {
        return a01a06a12;
    }

    public void setA01a06a12(String a01a06a12) {
        this.a01a06a12 = a01a06a12;
    }

    @Column(name ="a01a06a13",nullable=true,length=32)
    public String getA01a06a13() {
        return a01a06a13;
    }

    public void setA01a06a13(String a01a06a13) {
        this.a01a06a13 = a01a06a13;
    }

    @Column(name ="a01a06a14",nullable=true,length=32)
    public String getA01a06a14() {
        return a01a06a14;
    }

    public void setA01a06a14(String a01a06a14) {
        this.a01a06a14 = a01a06a14;
    }

    @Column(name ="a01a06a15",nullable=true,length=32)
    public String getA01a06a15() {
        return a01a06a15;
    }

    public void setA01a06a15(String a01a06a15) {
        this.a01a06a15 = a01a06a15;
    }

    @Column(name ="a01a06a16",nullable=true,length=32)
    public String getA01a06a16() {
        return a01a06a16;
    }

    public void setA01a06a16(String a01a06a16) {
        this.a01a06a16 = a01a06a16;
    }

    @Column(name ="a01a06a17",nullable=true,length=32)
    public String getA01a06a17() {
        return a01a06a17;
    }

    public void setA01a06a17(String a01a06a17) {
        this.a01a06a17 = a01a06a17;
    }

    @Column(name ="a01a06a18",nullable=true,length=32)
    public String getA01a06a18() {
        return a01a06a18;
    }

    public void setA01a06a18(String a01a06a18) {
        this.a01a06a18 = a01a06a18;
    }

    @Column(name ="a01a06a19",nullable=true,length=32)
    public String getA01a06a19() {
        return a01a06a19;
    }

    public void setA01a06a19(String a01a06a19) {
        this.a01a06a19 = a01a06a19;
    }

    @Column(name ="a01a06a20",nullable=true,length=32)
    public String getA01a06a20() {
        return a01a06a20;
    }

    public void setA01a06a20(String a01a06a20) {
        this.a01a06a20 = a01a06a20;
    }

    @OneToOne(mappedBy="emkProOrderDetailEntity")
    public EmkMInStorageDetailEntityA getEmkMInStorageDetailEntityA() {
        return emkMInStorageDetailEntityA;
    }

    public void setEmkMInStorageDetailEntityA(EmkMInStorageDetailEntityA emkMInStorageDetailEntityA) {
        this.emkMInStorageDetailEntityA = emkMInStorageDetailEntityA;
    }
}
