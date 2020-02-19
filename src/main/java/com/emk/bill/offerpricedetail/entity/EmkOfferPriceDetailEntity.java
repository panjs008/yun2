package com.emk.bill.offerpricedetail.entity;

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
@Table(name = "emk_offer_price_detail", schema = "")
public class EmkOfferPriceDetailEntity implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "方案ID")
    private String offerPriceId;

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

    @Column(name = "OFFER_PRICE_ID", nullable = true, length = 32)
    public String getOfferPriceId() {
        return this.offerPriceId;
    }

    public void setOfferPriceId(String offerPriceId) {
        this.offerPriceId = offerPriceId;
    }

}
