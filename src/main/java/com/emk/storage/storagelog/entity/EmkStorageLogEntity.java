package com.emk.storage.storagelog.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.SequenceGenerator;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Entity
 * @Description: 库存日记表
 * @date 2018-03-08 09:58:01
 */
@Entity
@Table(name = "emk_storage_log", schema = "")
@SuppressWarnings("serial")
public class EmkStorageLogEntity implements java.io.Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建人登录名称
     */
    private String createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 所属部门
     */
    private String sysOrgCode;
    /**
     * 商品ID
     */
    @Excel(name = "商品ID")
    private String proId;
    /**
     * 产品编号
     */
    @Excel(name = "产品编号")
    private String proNum;
    /**
     * 中文描述
     */
    @Excel(name = "中文描述")
    private String proZnName;
    /**
     * 入库前数量
     */
    @Excel(name = "入库前数量")
    private String preTotal;
    /**
     * 入库后数量
     */
    @Excel(name = "入库后数量")
    private String nextTotal;
    /**
     * 入库数量
     */
    @Excel(name = "入库数量")
    private String total;
    /**
     * 商品类型
     */
    @Excel(name = "商品类型")
    private String proTypeName;
    /**
     * 入库单号
     */
    @Excel(name = "入库单号")
    private String rkNo;

    @Excel(name = "规格")
    private String standards;
    /**
     * 规格型号
     */
    @Excel(name = "型号")
    private String brand;
    @Excel(name = "单位")
    private String unit;
    private String type;                        // 0 采购入库 1 采购订单入库 2 销售开单出库 3 录入期初库存 4 退货单 5 修改库存 6 库存组合

    private String departId;
    private String orgCode;
    private String remark;
    private String markDate;
    /**
     * 仓库ID
     */
    private String storageId;
    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String storageName;

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

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name = "ID", nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人名称
     */

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人名称
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人登录名称
     */

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人登录名称
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  创建日期
     */

    @Column(name = "CREATE_DATE", nullable = true, length = 20)
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  所属部门
     */

    @Column(name = "SYS_ORG_CODE", nullable = true, length = 50)
    public String getSysOrgCode() {
        return this.sysOrgCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  所属部门
     */
    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  商品ID
     */

    @Column(name = "PRO_ID", nullable = true, length = 32)
    public String getProId() {
        return this.proId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  商品ID
     */
    public void setProId(String proId) {
        this.proId = proId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  产品编号
     */

    @Column(name = "PRO_NUM", nullable = true, length = 32)
    public String getProNum() {
        return this.proNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  产品编号
     */
    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  中文描述
     */

    @Column(name = "PRO_ZN_NAME", nullable = true, length = 32)
    public String getProZnName() {
        return this.proZnName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  中文描述
     */
    public void setProZnName(String proZnName) {
        this.proZnName = proZnName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  入库前数量
     */

    @Column(name = "PRE_TOTAL", nullable = true, length = 32)
    public String getPreTotal() {
        return this.preTotal;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  入库前数量
     */
    public void setPreTotal(String preTotal) {
        this.preTotal = preTotal;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  入库后数量
     */

    @Column(name = "NEXT_TOTAL", nullable = true, length = 32)
    public String getNextTotal() {
        return this.nextTotal;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  入库后数量
     */
    public void setNextTotal(String nextTotal) {
        this.nextTotal = nextTotal;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  入库数量
     */

    @Column(name = "TOTAL", nullable = true, length = 32)
    public String getTotal() {
        return this.total;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  入库数量
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  商品类型
     */

    @Column(name = "PRO_TYPE_NAME", nullable = true, length = 32)
    public String getProTypeName() {
        return this.proTypeName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  商品类型
     */
    public void setProTypeName(String proTypeName) {
        this.proTypeName = proTypeName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  入库单号
     */

    @Column(name = "RK_NO", nullable = true, length = 32)
    public String getRkNo() {
        return this.rkNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  入库单号
     */
    public void setRkNo(String rkNo) {
        this.rkNo = rkNo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  规格型号
     */

    @Column(name = "BRAND", nullable = true, length = 32)
    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "standards", nullable = true, length = 32)
    public String getStandards() {
        return standards;
    }

    public void setStandards(String standards) {
        this.standards = standards;
    }

    @Column(name = "unit", nullable = true, length = 32)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "type", nullable = true, length = 32)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "remark", nullable = true, length = 32)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "mark_date", nullable = true, length = 32)
    public String getMarkDate() {
        return markDate;
    }

    public void setMarkDate(String markDate) {
        this.markDate = markDate;
    }

    @Column(name ="storage_id",nullable=true,length=32)
    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }
    @Column(name = "STORAGE_NAME", nullable = true, length = 256)
    public String getStorageName() {
        return this.storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }
}
