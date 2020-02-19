package com.emk.bill.proorderbox.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.emk.check.sizecheck.entity.EmkSizeTotalEntityH;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntityJ;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "emk_pro_order_box", schema = "")
public class EmkProOrderBoxEntity implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "订单ID")
    private String orderId;
    @Excel(name = "颜色名称")
    private String colorName;
    @Excel(name = "尺码")
    private String size;
    @Excel(name = "长度")
    private Double longVal;
    @Excel(name = "宽度")
    private Double widthVal;
    @Excel(name = "高度")
    private Double heightVal;
    @Excel(name = "箱数")
    private Integer total;
    @Excel(name = "总箱数")
    private Integer sumTotal;
    @Excel(name = "总数量")
    private Integer sumZsl;
    @Excel(name = "单件毛重")
    private Double oneWeightMao;
    @Excel(name = "单件净重")
    private Double oneWeightJz;
    @Excel(name = "箱内数量")
    private Integer inboxTotal;
    @Excel(name = "单箱毛重")
    private Double boxWeightMao;
    @Excel(name = "单箱净重")
    private Double boxWeightJz;
    @Excel(name = "单箱体积")
    private Double boxVolume;
    @Excel(name = "总体积")
    private Double sumVolume;
    @Excel(name = "总毛重")
    private Double sumWeightMao;
    @Excel(name = "总净重")
    private Double sumWeightJz;
    @Excel(name = "类型")
    private String boxType;

    @Excel(name = "工艺种类")
    private String gyzl;
    @Excel(name = "款式大类")
    private String proType;
    @Excel(name = "款式大类")
    private String proTypeName;
    @Excel(name = "单价")
    private String price;
    @Excel(name = "金额")
    private String money;

    private EmkSizeTotalEntityJ emkSizeTotalEntity;


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

    @Column(name = "ORDER_ID", nullable = true, length = 32)
    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "color_name", nullable = true, scale = 2, length = 32)
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Column(name = "LONG_VAL", nullable = true, scale = 2, length = 32)
    public Double getLongVal() {
        return this.longVal;
    }

    public void setLongVal(Double longVal) {
        this.longVal = longVal;
    }

    @Column(name = "WIDTH_VAL", nullable = true, scale = 2, length = 32)
    public Double getWidthVal() {
        return this.widthVal;
    }

    public void setWidthVal(Double widthVal) {
        this.widthVal = widthVal;
    }

    @Column(name = "HEIGHT_VAL", nullable = true, scale = 2, length = 32)
    public Double getHeightVal() {
        return this.heightVal;
    }

    public void setHeightVal(Double heightVal) {
        this.heightVal = heightVal;
    }

    @Column(name = "SUM_TOTAL", nullable = true, length = 32)
    public Integer getSumTotal() {
        return this.sumTotal;
    }

    public void setSumTotal(Integer sumTotal) {
        this.sumTotal = sumTotal;
    }

    @Column(name = "ONE_WEIGHT_MAO", nullable = true, scale = 2, length = 32)
    public Double getOneWeightMao() {
        return this.oneWeightMao;
    }

    public void setOneWeightMao(Double oneWeightMao) {
        this.oneWeightMao = oneWeightMao;
    }

    @Column(name = "ONE_WEIGHT_JZ", nullable = true, scale = 2, length = 32)
    public Double getOneWeightJz() {
        return this.oneWeightJz;
    }

    public void setOneWeightJz(Double oneWeightJz) {
        this.oneWeightJz = oneWeightJz;
    }

    @Column(name = "INBOX_TOTAL", nullable = true, length = 32)
    public Integer getInboxTotal() {
        return this.inboxTotal;
    }

    public void setInboxTotal(Integer inboxTotal) {
        this.inboxTotal = inboxTotal;
    }

    @Column(name = "BOX_WEIGHT_MAO", nullable = true, scale = 2, length = 32)
    public Double getBoxWeightMao() {
        return this.boxWeightMao;
    }

    public void setBoxWeightMao(Double boxWeightMao) {
        this.boxWeightMao = boxWeightMao;
    }

    @Column(name = "BOX_WEIGHT_JZ", nullable = true, scale = 2, length = 32)
    public Double getBoxWeightJz() {
        return this.boxWeightJz;
    }

    public void setBoxWeightJz(Double boxWeightJz) {
        this.boxWeightJz = boxWeightJz;
    }

    @Column(name = "BOX_VOLUME", nullable = true, scale = 2, length = 32)
    public Double getBoxVolume() {
        return this.boxVolume;
    }

    public void setBoxVolume(Double boxVolume) {
        this.boxVolume = boxVolume;
    }

    @Column(name = "SUM_VOLUME", nullable = true, scale = 2, length = 32)
    public Double getSumVolume() {
        return this.sumVolume;
    }

    public void setSumVolume(Double sumVolume) {
        this.sumVolume = sumVolume;
    }

    @Column(name = "SUM_WEIGHT_JZ", nullable = true, scale = 2, length = 32)
    public Double getSumWeightJz() {
        return this.sumWeightJz;
    }

    public void setSumWeightJz(Double sumWeightJz) {
        this.sumWeightJz = sumWeightJz;
    }

    @Column(name = "BOX_TYPE", nullable = true, length = 32)
    public String getBoxType() {
        return this.boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    @Column(name = "size", nullable = true, length = 32)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "total", nullable = true, length = 32)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Column(name = "sum_zsl", nullable = true, length = 32)
    public Integer getSumZsl() {
        return sumZsl;
    }

    public void setSumZsl(Integer sumZsl) {
        this.sumZsl = sumZsl;
    }

    @Column(name = "sum_weight_mao", nullable = true, length = 32)
    public Double getSumWeightMao() {
        return sumWeightMao;
    }

    public void setSumWeightMao(Double sumWeightMao) {
        this.sumWeightMao = sumWeightMao;
    }

    @Column(name = "gyzl", nullable = true, length = 32)
    public String getGyzl() {
        return gyzl;
    }

    public void setGyzl(String gyzl) {
        this.gyzl = gyzl;
    }

    @Column(name = "pro_type", nullable = true, length = 32)
    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    @Column(name = "pro_type_name", nullable = true, length = 32)
    public String getProTypeName() {
        return proTypeName;
    }

    public void setProTypeName(String proTypeName) {
        this.proTypeName = proTypeName;
    }

    @Column(name = "price", nullable = true, length = 32)
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column(name = "money", nullable = true, length = 32)
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @OneToOne(mappedBy="emkProOrderBoxEntity")
    public EmkSizeTotalEntityJ getEmkSizeTotalEntity() {
        return emkSizeTotalEntity;
    }

    public void setEmkSizeTotalEntity(EmkSizeTotalEntityJ emkSizeTotalEntity) {
        this.emkSizeTotalEntity = emkSizeTotalEntity;
    }
}
