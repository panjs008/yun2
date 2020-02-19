package com.emk.produce.outforum.entity;

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
@Table(name = "emk_out_forum", schema = "")
public class EmkOutForumEntity implements Serializable {
    @Excel(name = "业务员")
    private String businesser;
    private String businesserName;
    @Excel(name = "业务跟单员")
    private String tracer;
    private String tracerName;
    @Excel(name = "业务部门")
    private String businesseDeptName;
    private String businesseDeptId;
    @Excel(name = "生产跟单员")
    private String developer;
    private String developerName;
    @Excel(name = "工艺种类")
    private String gyzl;
    private String proType;
    @Excel(name = "款式大类")
    private String proTypeName;
    @Excel(name = "款号")
    private String sampleNo;
    @Excel(name = "描述")
    private String sampleNoDesc;
    @Excel(name = "总数量")
    private Integer total;
    @Excel(name = "总金额")
    private Double sumMoney;
    @Excel(name = "尺码")
    private String size;
    @Excel(name = "单价")
    private Double price;
    @Excel(name = "币种")
    private String bz;
    @Excel(name = "总箱数")
    private Integer sumBoxTotal;
    @Excel(name = "总体积")
    private Double sumBoxVolume;
    @Excel(name = "总净重")
    private Double sumBoxJz;
    @Excel(name = "总毛重")
    private Double sumBoxMao;
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    @Excel(name = "出货通知单号")
    private String outForumNo;
    @Excel(name = "提交日期")
    private String kdDate;
    @Excel(name = "出货日期")
    private String outDate;
    @Excel(name = "订舱通知单号")
    private String cargoNo;
    @Excel(name = "离厂放行条号")
    private String levealFactoryNo;
    @Excel(name = "船务员")
    private String cwyer;
    @Excel(name = "订单号")
    private String orderNo;
    @Excel(name = "生产合同号")
    private String produceNum;
    @Excel(name = "供应商")
    private String gys;
    @Excel(name = "供应商")
    private String gysCode;
    @Excel(name = "cargo_state")
    private String cargoState;
    @Excel(name = "出厂状态")
    private String outFactoryState;
    @Excel(name = "离厂日期")
    private String levalFactoryDate;
    @Excel(name = "预计收款日期")
    private String skDate;
    @Excel(name = "收款方式")
    private String skType;
    @Excel(name = "提单号")
    private String tdNum;
    @Excel(name = "提单状态")
    private String tdState;
    @Excel(name = "客户代码")
    private String cusNum;
    @Excel(name = "客户名称")
    private String cusName;
    @Excel(name = "订舱状态")
    private String dcState;
    private String produceId;

    private String state;
    @Excel(name = "发票编号")
    private String fpbh;
    @Excel(name = "发票日期")
    private String fpDate;
    @Excel(name = "地址")
    private String address;
    @Excel(name = "电话")
    private String telphone;
    @Column(name = "BUSINESSER", nullable = true, length = 32)
    public String getBusinesser() {
        return this.businesser;
    }

    public void setBusinesser(String businesser) {
        this.businesser = businesser;
    }

    @Column(name = "BUSINESSER_NAME", nullable = true, length = 32)
    public String getBusinesserName() {
        return this.businesserName;
    }

    public void setBusinesserName(String businesserName) {
        this.businesserName = businesserName;
    }

    @Column(name = "TRACER", nullable = true, length = 32)
    public String getTracer() {
        return this.tracer;
    }

    public void setTracer(String tracer) {
        this.tracer = tracer;
    }

    @Column(name = "TRACER_NAME", nullable = true, length = 32)
    public String getTracerName() {
        return this.tracerName;
    }

    public void setTracerName(String tracerName) {
        this.tracerName = tracerName;
    }

    @Column(name = "BUSINESSE_DEPT_NAME", nullable = true, length = 32)
    public String getBusinesseDeptName() {
        return this.businesseDeptName;
    }

    public void setBusinesseDeptName(String businesseDeptName) {
        this.businesseDeptName = businesseDeptName;
    }

    @Column(name = "BUSINESSE_DEPT_ID", nullable = true, length = 32)
    public String getBusinesseDeptId() {
        return this.businesseDeptId;
    }

    public void setBusinesseDeptId(String businesseDeptId) {
        this.businesseDeptId = businesseDeptId;
    }

    @Column(name = "DEVELOPER", nullable = true, length = 32)
    public String getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    @Column(name = "DEVELOPER_NAME", nullable = true, length = 32)
    public String getDeveloperName() {
        return this.developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    @Column(name = "GYZL", nullable = true, length = 32)
    public String getGyzl() {
        return this.gyzl;
    }

    public void setGyzl(String gyzl) {
        this.gyzl = gyzl;
    }

    @Column(name = "PRO_TYPE", nullable = true, length = 32)
    public String getProType() {
        return this.proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    @Column(name = "PRO_TYPE_NAME", nullable = true, length = 32)
    public String getProTypeName() {
        return this.proTypeName;
    }

    public void setProTypeName(String proTypeName) {
        this.proTypeName = proTypeName;
    }

    @Column(name = "SAMPLE_NO", nullable = true, length = 32)
    public String getSampleNo() {
        return this.sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    @Column(name = "SAMPLE_NO_DESC", nullable = true, length = 32)
    public String getSampleNoDesc() {
        return this.sampleNoDesc;
    }

    public void setSampleNoDesc(String sampleNoDesc) {
        this.sampleNoDesc = sampleNoDesc;
    }

    @Column(name = "TOTAL", nullable = true, length = 32)
    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Column(name = "SUM_MONEY", nullable = true, scale = 2, length = 32)
    public Double getSumMoney() {
        return this.sumMoney;
    }

    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

    @Column(name = "SIZE", nullable = true, length = 32)
    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "PRICE", nullable = true, scale = 2, length = 32)
    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "BZ", nullable = true, length = 32)
    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Column(name = "SUM_BOX_TOTAL", nullable = true, length = 32)
    public Integer getSumBoxTotal() {
        return this.sumBoxTotal;
    }

    public void setSumBoxTotal(Integer sumBoxTotal) {
        this.sumBoxTotal = sumBoxTotal;
    }

    @Column(name = "SUM_BOX_VOLUME", nullable = true, scale = 2, length = 32)
    public Double getSumBoxVolume() {
        return this.sumBoxVolume;
    }

    public void setSumBoxVolume(Double sumBoxVolume) {
        this.sumBoxVolume = sumBoxVolume;
    }

    @Column(name = "SUM_BOX_JZ", nullable = true, scale = 2, length = 32)
    public Double getSumBoxJz() {
        return this.sumBoxJz;
    }

    public void setSumBoxJz(Double sumBoxJz) {
        this.sumBoxJz = sumBoxJz;
    }

    @Column(name = "SUM_BOX_MAO", nullable = true, scale = 2, length = 32)
    public Double getSumBoxMao() {
        return this.sumBoxMao;
    }

    public void setSumBoxMao(Double sumBoxMao) {
        this.sumBoxMao = sumBoxMao;
    }

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CREATE_NAME", nullable = true, length = 32)
    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_BY", nullable = true, length = 32)
    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_DATE", nullable = true, length = 32)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "SYS_ORG_CODE", nullable = true, length = 32)
    public String getSysOrgCode() {
        return this.sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    @Column(name = "OUT_FORUM_NO", nullable = true, length = 32)
    public String getOutForumNo() {
        return this.outForumNo;
    }

    public void setOutForumNo(String outForumNo) {
        this.outForumNo = outForumNo;
    }

    @Column(name = "KD_DATE", nullable = true, length = 32)
    public String getKdDate() {
        return this.kdDate;
    }

    public void setKdDate(String kdDate) {
        this.kdDate = kdDate;
    }

    @Column(name = "CARGO_NO", nullable = true, length = 32)
    public String getCargoNo() {
        return this.cargoNo;
    }

    public void setCargoNo(String cargoNo) {
        this.cargoNo = cargoNo;
    }

    @Column(name = "LEVEAL_FACTORY_NO", nullable = true, length = 32)
    public String getLevealFactoryNo() {
        return this.levealFactoryNo;
    }

    public void setLevealFactoryNo(String levealFactoryNo) {
        this.levealFactoryNo = levealFactoryNo;
    }

    @Column(name = "CWYER", nullable = true, length = 32)
    public String getCwyer() {
        return this.cwyer;
    }

    public void setCwyer(String cwyer) {
        this.cwyer = cwyer;
    }

    @Column(name = "ORDER_NO", nullable = true, length = 32)
    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "PRODUCE_NUM", nullable = true, length = 32)
    public String getProduceNum() {
        return this.produceNum;
    }

    public void setProduceNum(String produceNum) {
        this.produceNum = produceNum;
    }

    @Column(name = "GYS", nullable = true, length = 32)
    public String getGys() {
        return this.gys;
    }

    public void setGys(String gys) {
        this.gys = gys;
    }

    @Column(name = "GYS_CODE", nullable = true, length = 32)
    public String getGysCode() {
        return this.gysCode;
    }

    public void setGysCode(String gysCode) {
        this.gysCode = gysCode;
    }

    @Column(name = "CARGO_STATE", nullable = true, length = 32)
    public String getCargoState() {
        return this.cargoState;
    }

    public void setCargoState(String cargoState) {
        this.cargoState = cargoState;
    }

    @Column(name = "OUT_FACTORY_STATE", nullable = true, length = 32)
    public String getOutFactoryState() {
        return this.outFactoryState;
    }

    public void setOutFactoryState(String outFactoryState) {
        this.outFactoryState = outFactoryState;
    }

    @Column(name = "LEVAL_FACTORY_DATE", nullable = true, length = 32)
    public String getLevalFactoryDate() {
        return this.levalFactoryDate;
    }

    public void setLevalFactoryDate(String levalFactoryDate) {
        this.levalFactoryDate = levalFactoryDate;
    }

    @Column(name = "SK_DATE", nullable = true, length = 32)
    public String getSkDate() {
        return this.skDate;
    }

    public void setSkDate(String skDate) {
        this.skDate = skDate;
    }

    @Column(name = "SK_TYPE", nullable = true, length = 32)
    public String getSkType() {
        return this.skType;
    }

    public void setSkType(String skType) {
        this.skType = skType;
    }

    @Column(name = "TD_NUM", nullable = true, length = 32)
    public String getTdNum() {
        return this.tdNum;
    }

    public void setTdNum(String tdNum) {
        this.tdNum = tdNum;
    }

    @Column(name = "TD_STATE", nullable = true, length = 32)
    public String getTdState() {
        return this.tdState;
    }

    public void setTdState(String tdState) {
        this.tdState = tdState;
    }

    @Column(name = "CUS_NUM", nullable = true, length = 32)
    public String getCusNum() {
        return this.cusNum;
    }

    public void setCusNum(String cusNum) {
        this.cusNum = cusNum;
    }

    @Column(name = "CUS_NAME", nullable = true, length = 32)
    public String getCusName() {
        return this.cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    @Column(name = "DC_STATE", nullable = true, length = 32)
    public String getDcState() {
        return this.dcState;
    }

    public void setDcState(String dcState) {
        this.dcState = dcState;
    }

    @Column(name = "STATE", nullable = true, length = 32)
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "out_date", nullable = true, length = 32)
    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    @Column(name ="produce_id",nullable=true,length=32)
    public String getProduceId() {
        return produceId;
    }

    public void setProduceId(String produceId) {
        this.produceId = produceId;
    }

    @Column(name = "FPBH", nullable = true, length = 32)
    public String getFpbh() {
        return this.fpbh;
    }

    public void setFpbh(String fpbh) {
        this.fpbh = fpbh;
    }

    @Column(name = "FP_DATE", nullable = true, length = 32)
    public String getFpDate() {
        return this.fpDate;
    }

    public void setFpDate(String fpDate) {
        this.fpDate = fpDate;
    }

    @Column(name = "address", nullable = true, length = 32)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "telphone", nullable = true, length = 32)
    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
