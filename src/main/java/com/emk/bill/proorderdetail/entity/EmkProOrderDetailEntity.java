package com.emk.bill.proorderdetail.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.emk.bound.minstoragedetail.entity.EmkMInStorageDetailEntity;
import com.emk.bound.minstoragedetail.entity.EmkMInStorageDetailEntityA;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntityD;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "emk_pro_order_detail", schema = "")
public class EmkProOrderDetailEntity implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "订单ID")
    private String proOrderId;
    /**a01a09a01*/
    @Excel(name="a01a09a01",width=15)
    private String a01a09a01;
    /**a01a09a02*/
    @Excel(name="a01a09a02",width=15)
    private String a01a09a02;
    /**a01a09a03*/
    @Excel(name="a01a09a03",width=15)
    private String a01a09a03;
    /**a01a09a04*/
    @Excel(name="a01a09a04",width=15)
    private String a01a09a04;
    /**a01a09a05*/
    @Excel(name="a01a09a05",width=15)
    private String a01a09a05;
    /**a01a09a06*/
    @Excel(name="a01a09a06",width=15)
    private String a01a09a06;
    /**a01a09a07*/
    @Excel(name="a01a09a07",width=15)
    private String a01a09a07;
    /**a01a09a08*/
    @Excel(name="a01a09a08",width=15)
    private String a01a09a08;
    /**a01a09a09*/
    @Excel(name="a01a09a09",width=15)
    private String a01a09a09;
    /**a01a09a10*/
    @Excel(name="a01a09a10",width=15)
    private String a01a09a10;
    /**a01a09a11*/
    @Excel(name="a01a09a11",width=15)
    private String a01a09a11;
    /**a01a09a12*/
    @Excel(name="a01a09a12",width=15)
    private String a01a09a12;
    /**a01a09a13*/
    @Excel(name="a01a09a13",width=15)
    private String a01a09a13;
    /**a01a09a14*/
    @Excel(name="a01a09a14",width=15)
    private String a01a09a14;
    /**a01a09a15*/
    @Excel(name="a01a09a15",width=15)
    private String a01a09a15;
    /**a01a09a16*/
    @Excel(name="a01a09a16",width=15)
    private String a01a09a16;
    /**a01a09a17*/
    @Excel(name="a01a09a17",width=15)
    private String a01a09a17;
    /**a01a09a18*/
    @Excel(name="a01a09a18",width=15)
    private String a01a09a18;
    /**a01a09a19*/
    @Excel(name="a01a09a19",width=15)
    private String a01a09a19;
    /**a01a09a20*/
    @Excel(name="a01a09a20",width=15)
    private String a01a09a20;
    @Excel(name="a01a09a21",width=15)
    private String a01a09a21;
    @Excel(name="a01a09a22",width=15)
    private String a01a09a22;
    @Excel(name="a01a09a23",width=15)
    private String a01a09a23;
    @Excel(name="a01a09a24",width=15)
    private String a01a09a24;
    @Excel(name="a01a09a25",width=15)
    private String a01a09a25;
    @Excel(name="a01a09a26",width=15)
    private String a01a09a26;
    @Excel(name="a01a09a27",width=15)
    private String a01a09a27;
    @Excel(name="a01a09a28",width=15)
    private String a01a09a28;

    private String departId;
    private String orgCode;
    private String priceNo;
    private String priceId;
    private String stoId;

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

    @Column(name ="a01a09a01",nullable=true,length=32)
    public String getA01a09a01() {
        return a01a09a01;
    }

    public void setA01a09a01(String a01a09a01) {
        this.a01a09a01 = a01a09a01;
    }

    @Column(name ="a01a09a02",nullable=true,length=32)
    public String getA01a09a02() {
        return a01a09a02;
    }

    public void setA01a09a02(String a01a09a02) {
        this.a01a09a02 = a01a09a02;
    }

    @Column(name ="a01a09a03",nullable=true,length=32)
    public String getA01a09a03() {
        return a01a09a03;
    }

    public void setA01a09a03(String a01a09a03) {
        this.a01a09a03 = a01a09a03;
    }

    @Column(name ="a01a09a04",nullable=true,length=32)
    public String getA01a09a04() {
        return a01a09a04;
    }

    public void setA01a09a04(String a01a09a04) {
        this.a01a09a04 = a01a09a04;
    }

    @Column(name ="a01a09a05",nullable=true,length=32)
    public String getA01a09a05() {
        return a01a09a05;
    }

    public void setA01a09a05(String a01a09a05) {
        this.a01a09a05 = a01a09a05;
    }

    @Column(name ="a01a09a06",nullable=true,length=32)
    public String getA01a09a06() {
        return a01a09a06;
    }

    public void setA01a09a06(String a01a09a06) {
        this.a01a09a06 = a01a09a06;
    }

    @Column(name ="a01a09a07",nullable=true,length=32)
    public String getA01a09a07() {
        return a01a09a07;
    }

    public void setA01a09a07(String a01a09a07) {
        this.a01a09a07 = a01a09a07;
    }

    @Column(name ="a01a09a08",nullable=true,length=32)
    public String getA01a09a08() {
        return a01a09a08;
    }

    public void setA01a09a08(String a01a09a08) {
        this.a01a09a08 = a01a09a08;
    }

    @Column(name ="a01a09a09",nullable=true,length=32)
    public String getA01a09a09() {
        return a01a09a09;
    }

    public void setA01a09a09(String a01a09a09) {
        this.a01a09a09 = a01a09a09;
    }

    @Column(name ="a01a09a10",nullable=true,length=32)
    public String getA01a09a10() {
        return a01a09a10;
    }

    public void setA01a09a10(String a01a09a10) {
        this.a01a09a10 = a01a09a10;
    }

    @Column(name ="a01a09a11",nullable=true,length=32)
    public String getA01a09a11() {
        return a01a09a11;
    }

    public void setA01a09a11(String a01a09a11) {
        this.a01a09a11 = a01a09a11;
    }

    @Column(name ="a01a09a12",nullable=true,length=32)
    public String getA01a09a12() {
        return a01a09a12;
    }

    public void setA01a09a12(String a01a09a12) {
        this.a01a09a12 = a01a09a12;
    }

    @Column(name ="a01a09a13",nullable=true,length=32)
    public String getA01a09a13() {
        return a01a09a13;
    }

    public void setA01a09a13(String a01a09a13) {
        this.a01a09a13 = a01a09a13;
    }

    @Column(name ="a01a09a14",nullable=true,length=32)
    public String getA01a09a14() {
        return a01a09a14;
    }

    public void setA01a09a14(String a01a09a14) {
        this.a01a09a14 = a01a09a14;
    }

    @Column(name ="a01a09a15",nullable=true,length=32)
    public String getA01a09a15() {
        return a01a09a15;
    }

    public void setA01a09a15(String a01a09a15) {
        this.a01a09a15 = a01a09a15;
    }

    @Column(name ="a01a09a16",nullable=true,length=32)
    public String getA01a09a16() {
        return a01a09a16;
    }

    public void setA01a09a16(String a01a09a16) {
        this.a01a09a16 = a01a09a16;
    }

    @Column(name ="a01a09a17",nullable=true,length=32)
    public String getA01a09a17() {
        return a01a09a17;
    }

    public void setA01a09a17(String a01a09a17) {
        this.a01a09a17 = a01a09a17;
    }

    @Column(name ="a01a09a18",nullable=true,length=32)
    public String getA01a09a18() {
        return a01a09a18;
    }

    public void setA01a09a18(String a01a09a18) {
        this.a01a09a18 = a01a09a18;
    }

    @Column(name ="a01a09a19",nullable=true,length=32)
    public String getA01a09a19() {
        return a01a09a19;
    }

    public void setA01a09a19(String a01a09a19) {
        this.a01a09a19 = a01a09a19;
    }

    @Column(name ="a01a09a20",nullable=true,length=32)
    public String getA01a09a20() {
        return a01a09a20;
    }

    public void setA01a09a20(String a01a09a20) {
        this.a01a09a20 = a01a09a20;
    }

    @Column(name ="a01a09a21",nullable=true,length=32)
    public String getA01a09a21() {
        return a01a09a21;
    }

    public void setA01a09a21(String a01a09a21) {
        this.a01a09a21 = a01a09a21;
    }

    @Column(name ="a01a09a22",nullable=true,length=32)
    public String getA01a09a22() {
        return a01a09a22;
    }

    public void setA01a09a22(String a01a09a22) {
        this.a01a09a22 = a01a09a22;
    }

    @Column(name ="a01a09a23",nullable=true,length=32)
    public String getA01a09a23() {
        return a01a09a23;
    }

    public void setA01a09a23(String a01a09a23) {
        this.a01a09a23 = a01a09a23;
    }

    @Column(name ="a01a09a24",nullable=true,length=32)
    public String getA01a09a24() {
        return a01a09a24;
    }

    public void setA01a09a24(String a01a09a24) {
        this.a01a09a24 = a01a09a24;
    }

    @Column(name ="a01a09a25",nullable=true,length=32)
    public String getA01a09a25() {
        return a01a09a25;
    }

    public void setA01a09a25(String a01a09a25) {
        this.a01a09a25 = a01a09a25;
    }

    @Column(name ="a01a09a26",nullable=true,length=32)
    public String getA01a09a26() {
        return a01a09a26;
    }

    public void setA01a09a26(String a01a09a26) {
        this.a01a09a26 = a01a09a26;
    }

    @Column(name ="a01a09a27",nullable=true,length=32)
    public String getA01a09a27() {
        return a01a09a27;
    }

    public void setA01a09a27(String a01a09a27) {
        this.a01a09a27 = a01a09a27;
    }

    @Column(name ="a01a09a28",nullable=true,length=32)
    public String getA01a09a28() {
        return a01a09a28;
    }

    public void setA01a09a28(String a01a09a28) {
        this.a01a09a28 = a01a09a28;
    }

    @Column(name ="price_no",nullable=true,length=32)
    public String getPriceNo() {
        return priceNo;
    }

    public void setPriceNo(String priceNo) {
        this.priceNo = priceNo;
    }

    @Column(name ="price_id",nullable=true,length=32)
    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    @Column(name ="sto_id",nullable=true,length=32)
    public String getStoId() {
        return stoId;
    }

    public void setStoId(String stoId) {
        this.stoId = stoId;
    }
}
