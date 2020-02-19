package com.emk.storage.enquirydetail.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.emk.check.sizecheck.entity.EmkSizeTotalEntityE;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "emk_enquiry_detail", schema = "")
public class EmkEnquiryDetailEntity
        implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "询盘ID")
    private String enquiryId;
    @Excel(name = "款号")
    private String colorValue;
    @Excel(name = "颜色")
    private String color;
    @Excel(name = "内长")
    private String size;
    @Excel(name = "数量")
    private Integer total;
    @Excel(name = "单价")
    private Double price;
    @Excel(name = "金额")
    private Double money;
    private String sortDesc;


    private EmkSizeTotalEntityE emkSizeTotalEntity;


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

    @Column(name = "ENQUIRY_ID", nullable = true, length = 32)
    public String getEnquiryId() {
        return this.enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    @Column(name = "COLOR", nullable = true, length = 32)
    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "COLOR_VALUE", nullable = true, length = 32)
    public String getColorValue() {
        return this.colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }

    @Column(name = "SIZE", nullable = true, length = 32)
    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "TOTAL", nullable = true, length = 32)
    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Column(name = "PRICE", nullable = true, scale = 2, length = 32)
    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "money", nullable = true, scale = 2, length = 32)
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Column(name = "sort_desc", nullable = true, scale = 2, length = 32)
    public String getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(String sortDesc) {
        this.sortDesc = sortDesc;
    }


    @OneToOne(mappedBy="emkEnquiryDetailEntity")
    public EmkSizeTotalEntityE getEmkSizeTotalEntity() {
        return emkSizeTotalEntity;
    }

    public void setEmkSizeTotalEntity(EmkSizeTotalEntityE emkSizeTotalEntity) {
        this.emkSizeTotalEntity = emkSizeTotalEntity;
    }
}
