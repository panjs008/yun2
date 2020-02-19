package com.emk.storage.formaterialother.entity;

import com.emk.check.sizecheck.entity.EmkSizeTotalEntityM;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "emk_formaterail_other_detail", schema = "")
public class EmkFormaterailOtherDetailEntity
        implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "物料ID")
    private String formaterailOtherId;
    @Excel(name = "物料名称")
    private String wllx;
    @Excel(name = "单价")
    private Double price;
    @Excel(name = "数量")
    private Double total;
    @Excel(name = "金额")
    private Double money;
    private String sortDesc;

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

    @Column(name = "formaterail_other_id", nullable = true, length = 32)
    public String getFormaterailOtherId() {
        return formaterailOtherId;
    }

    public void setFormaterailOtherId(String formaterailOtherId) {
        this.formaterailOtherId = formaterailOtherId;
    }

    @Column(name = "wllx", nullable = true, length = 32)
    public String getWllx() {
        return wllx;
    }

    public void setWllx(String wllx) {
        this.wllx = wllx;
    }

    @Column(name = "total", nullable = true, length = 32)
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Column(name = "PRICE", nullable = true, scale = 2, length = 32)
    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "sort_desc", nullable = true, scale = 2, length = 32)
    public String getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(String sortDesc) {
        this.sortDesc = sortDesc;
    }

    @Column(name = "money", nullable = true,scale = 2, length = 32)
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

}
