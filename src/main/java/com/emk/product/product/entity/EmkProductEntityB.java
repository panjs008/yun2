package com.emk.product.product.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "emk_product", schema = "")
public class EmkProductEntityB implements Serializable {
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
    private String proZnNameB;
    @Excel(name = "商品编号")
    private String proNumB;
    @Excel(name = "条码编号")
    private String barCode;
    private String proZjm;
    @Excel(name = "规格")
    private String standardsB;
    @Excel(name = "型号")
    private String brandB;
    @Excel(name = "单位")
    private String unitB;
    private String unitCodeB;
    @Excel(name="零售价",width=15)
    private String sellPrice;
    @Excel(name="成本价",width=15)
    private String costPrice;
    private String departId;
    private String orgCode;
    private String flag;
    private String a01a01a01B;

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

    @Column(name = "PRO_TYPE", nullable = true, length = 32)
    public String getProType() {
        return this.proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    @Column(name = "pro_type_name", nullable = true, length = 32)
    public String getProTypeName() {
        return this.proTypeName;
    }

    public void setProTypeName(String proTypeName) {
        this.proTypeName = proTypeName;
    }
    @Column(name = "PRO_NUM", nullable = true, length = 32)
    public String getProNumB() {
        return this.proNumB;
    }

    public void setProNumB(String proNum) {
        this.proNumB = proNum;
    }

    @Column(name = "BRAND", nullable = true, length = 32)
    public String getBrandB() {
        return this.brandB;
    }

    public void setBrandB(String brand) {
        this.brandB = brand;
    }

    @Column(name = "UNIT", nullable = true, length = 32)
    public String getUnitB() {
        return this.unitB;
    }
    public void setUnitB(String unit) {
        this.unitB = unit;
    }

    @Column(name = "standards", nullable = true, length = 32)
    public String getStandardsB() {
        return standardsB;
    }

    public void setStandardsB(String standards) {
        this.standardsB = standards;
    }

    @Column(name ="unit_code",nullable=true,length=32)
    public String getUnitCodeB() {
        return unitCodeB;
    }

    public void setUnitCodeB(String unitCode) {
        this.unitCodeB = unitCode;
    }

    @Column(name ="pro_zn_name",nullable=true,length=32)
    public String getProZnNameB() {
        return proZnNameB;
    }

    public void setProZnNameB(String proZnNameB) {
        this.proZnNameB = proZnNameB;
    }

    @Column(name ="bar_code",nullable=true,length=32)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name ="pro_zjm",nullable=true,length=32)
    public String getProZjm() {
        return proZjm;
    }

    public void setProZjm(String proZjm) {
        this.proZjm = proZjm;
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

    @Column(name ="flag",nullable=true,length=32)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name ="a01a01a01",nullable=true,length=32)
    public String getA01a01a01B() {
        return a01a01a01B;
    }

    public void setA01a01a01B(String a01a01a01B) {
        this.a01a01a01B = a01a01a01B;
    }
}
