package com.emk.product.product.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "emk_product_storage", schema = "")
public class EmkProductStorageEntity implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;

    private String departId;
    private String orgCode;

    private String storageId;
    @Excel(name = "仓库名称")
    private String storageName;
    /**
     * 库位ID
     */
    private String positionId;
    /**
     * 库位名称
     */
    private String positionName;



    private String productId;

    /**A01a09A01*/
    @Excel(name="A01a19A01",width=15)
    private String a01a19a01;           //颜色
    private String a01a19a02;           //尺码
    private String a01a19a03;           //序列号
    private String a01a19a04;           //生产日期
    private String a01a19a05;           //到期日期
    private String total;
    private String price;

    private String type;
    private String stroId;

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


    @Column(name = "storage_id", nullable = true, length = 256)
    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    @Column(name = "position_id", nullable = true, length = 256)
    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @Column(name = "position_name", nullable = true, length = 256)
    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Column(name = "STORAGE_NAME", nullable = true, length = 256)
    public String getStorageName() {
        return this.storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    @Column(name = "product_id", nullable = true, length = 256)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "a01a19a01", nullable = true, length = 256)
    public String getA01a19a01() {
        return a01a19a01;
    }

    public void setA01a19a01(String a01a19a01) {
        this.a01a19a01 = a01a19a01;
    }

    @Column(name = "a01a19a02", nullable = true, length = 256)
    public String getA01a19a02() {
        return a01a19a02;
    }

    public void setA01a19a02(String a01a19a02) {
        this.a01a19a02 = a01a19a02;
    }

    @Column(name = "a01a19a03", nullable = true, length = 256)
    public String getA01a19a03() {
        return a01a19a03;
    }

    public void setA01a19a03(String a01a19a03) {
        this.a01a19a03 = a01a19a03;
    }

    @Column(name = "a01a19a04", nullable = true, length = 256)
    public String getA01a19a04() {
        return a01a19a04;
    }

    public void setA01a19a04(String a01a19a04) {
        this.a01a19a04 = a01a19a04;
    }

    @Column(name = "a01a19a05", nullable = true, length = 256)
    public String getA01a19a05() {
        return a01a19a05;
    }

    public void setA01a19a05(String a01a19a05) {
        this.a01a19a05 = a01a19a05;
    }

    @Column(name = "type", nullable = true, length = 256)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "total", nullable = true, length = 256)
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Column(name = "stro_id", nullable = true, length = 256)
    public String getStroId() {
        return stroId;
    }

    public void setStroId(String stroId) {
        this.stroId = stroId;
    }

    @Column(name = "price", nullable = true, length = 256)
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
