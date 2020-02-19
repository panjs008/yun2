package com.emk.product.producttype.entity;

import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "emk_product_type", schema = "")
public class EmkProductTypeEntityA
        implements Serializable {
    private Integer id;
    private String createName;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "目录名称")
    private String content;
    @Excel(name = "层级")
    private Integer officeLevel;
    @Excel(name = "是否删除")
    private Integer isDel;
    @Excel(name = "所属部门")
    private String orgId;
    @Excel(name = "备注")
    private String remark;
    private String proCode;
    private String departId;
    private String orgCode;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, length = 20)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
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

    @Column(name = "CONTENT", nullable = true, length = 32)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "OFFICE_LEVEL", nullable = true, length = 6)
    public Integer getOfficeLevel() {
        return this.officeLevel;
    }

    public void setOfficeLevel(Integer officeLevel) {
        this.officeLevel = officeLevel;
    }

    @Column(name = "IS_DEL", nullable = true, scale = 6, length = 32)
    public Integer getIsDel() {
        return this.isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Column(name = "ORG_ID", nullable = true, length = 32)
    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Column(name = "REMARK", nullable = true, length = 56)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "pro_code", nullable = true, length = 56)
    public String getProCode() {
        return this.proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
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
}
