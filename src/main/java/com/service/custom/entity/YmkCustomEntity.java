package com.service.custom.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ymk_custom", schema = "")
public class YmkCustomEntity implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;

    @Excel(name = "省")
    private String shengFen;
    @Excel(name = "城市")
    private String chengShi;
    @Excel(name = "片区")
    private String pianQu;
    @Excel(name = "客户编码")
    private String cusNum;
    @Excel(name = "客户名称")
    private String cusName;
    @Excel(name = "客户简称")
    private String cusCode;
    @Excel(name = "客户类型")
    private String cusType;
    @Excel(name = "客户类型名称")
    private String cusTypeName;
    @Excel(name = "客户来源")
    private String cusFrom;
    @Excel(name = "客户地址")
    private String address;
    @Excel(name = "联系人电话")
    private String telphone;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "状态")
    private String status;              //0 激活、1 暂停、2 禁止
    @Excel(name = "办公电话")
    private String workphone;
    private String businesseDeptName;
    private String businesseDeptId;
    @Excel(name = "业务员")
    private String businesser;
    private String businesserName;
    @Excel(name = "业务跟单员")
    private String tracer;
    private String tracerName;
    @Excel(name = "生产跟单员")
    private String developer;
    private String developerName;
    @Excel(name = "业务类型")
    private String businessType;
    @Excel(name = "主营产品")
    private String mainContent;
    @Excel(name = "潜在业务量/年")
    private String qzywl;
    @Excel(name = "币种")
    private String bz;
    @Excel(name = "建立商业关系时间")
    private String relationDate;
    @Excel(name = "国家")
    private String guoJia;
    @Excel(name = "主联系人")
    private String zlxr;
    @Excel(name = "业务联系人二")
    private String ywlxr2;
    @Excel(name = "业务联系人二邮箱")
    private String ywlxr2Email;
    @Excel(name = "业务联系人二电话")
    private String ywlxr2Telphone;
    @Excel(name = "业务联系人三")
    private String ywlxr3;
    @Excel(name = "业务联系人三邮箱")
    private String ywlxr3Email;
    @Excel(name = "业务联系人三电话")
    private String ywlxr3Telphone;
    @Excel(name = "业务联系人四")
    private String ywlxr4;
    @Excel(name = "业务联系人四邮箱")
    private String ywlxr4Email;
    @Excel(name = "业务联系人四电话")
    private String ywlxr4Telphone;
    @Excel(name = "业务联系人五")
    private String ywlxr5;
    @Excel(name = "业务联系人五邮箱")
    private String ywlxr5Email;
    @Excel(name = "业务联系人五电话")
    private String ywlxr5Telphone;

    @Excel(name = "邮箱")
    private String email;
    @Excel(name = "档案编号")
    private String daanNum;

    @Excel(name = "纳税人识别号")
    private String taxpayerNum;
    @Excel(name = "开户行")
    private String bankName;
    @Excel(name = "开户账号 ")
    private String bankAccount;
    @Excel(name = "税号")
    private String suiNum;

    private String cusLevel;
    private String departId;
    private String orgCode;

    private String cusZk;
    private String bcqkMoney;


    @Column(name ="cus_zk",nullable=true,length=32)
    public String getCusZk() {
        return cusZk;
    }

    public void setCusZk(String cusZk) {
        this.cusZk = cusZk;
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

    @Formula("(select p.name from t_s_category p where p.code = sheng_fen)")
    @Column(name = "sheng_fen", nullable = true, length = 50)
    public String getShengFen() {
        return this.shengFen;
    }

    public void setShengFen(String shengFen) {
        this.shengFen = shengFen;
    }

    @Formula("(select p.name from t_s_category p where p.code = cheng_shi)")
    @Column(name = "cheng_shi", nullable = true, length = 50)
    public String getChengShi() {
        return this.chengShi;
    }

    public void setChengShi(String chengShi) {
        this.chengShi = chengShi;
    }

    @Formula("(select p.name from t_s_category p where p.code = pian_qu)")
    @Column(name = "pian_qu", nullable = true, length = 50)
    public String getPianQu() {
        return this.pianQu;
    }

    public void setPianQu(String pianQu) {
        this.pianQu = pianQu;
    }

    @Column(name = "status", nullable = true, length = 50)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "workphone", nullable = true, length = 50)
    public String getWorkphone() {
        return this.workphone;
    }

    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }

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

    @Column(name = "cus_code", nullable = true, length = 32)
    public String getCusCode() {
        return this.cusCode;
    }

    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }

    @Column(name = "CUS_TYPE", nullable = true, length = 32)
    public String getCusType() {
        return this.cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    @Column(name = "CUS_FROM", nullable = true, length = 32)
    public String getCusFrom() {
        return this.cusFrom;
    }

    public void setCusFrom(String cusFrom) {
        this.cusFrom = cusFrom;
    }

    @Column(name = "ADDRESS", nullable = true, length = 32)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "TELPHONE", nullable = true, length = 32)
    public String getTelphone() {
        return this.telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    @Column(name = "REMARK", nullable = true, length = 32)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "businesse_dept_name", nullable = true, length = 32)
    public String getBusinesseDeptName() {
        return this.businesseDeptName;
    }

    public void setBusinesseDeptName(String businesseDeptName) {
        this.businesseDeptName = businesseDeptName;
    }

    @Column(name = "businesse_dept_id", nullable = true, length = 32)
    public String getBusinesseDeptId() {
        return this.businesseDeptId;
    }

    public void setBusinesseDeptId(String businesseDeptId) {
        this.businesseDeptId = businesseDeptId;
    }

    @Column(name = "businesser", nullable = true, length = 32)
    public String getBusinesser() {
        return this.businesser;
    }

    public void setBusinesser(String businesser) {
        this.businesser = businesser;
    }

    @Column(name = "businesser_name", nullable = true, length = 32)
    public String getBusinesserName() {
        return this.businesserName;
    }

    public void setBusinesserName(String businesserName) {
        this.businesserName = businesserName;
    }

    @Column(name = "tracer", nullable = true, length = 32)
    public String getTracer() {
        return this.tracer;
    }

    public void setTracer(String tracer) {
        this.tracer = tracer;
    }

    @Column(name = "tracer_name", nullable = true, length = 32)
    public String getTracerName() {
        return this.tracerName;
    }

    public void setTracerName(String tracerName) {
        this.tracerName = tracerName;
    }

    @Column(name = "developer", nullable = true, length = 32)
    public String getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    @Column(name = "developer_name", nullable = true, length = 32)
    public String getDeveloperName() {
        return this.developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    @Column(name = "business_type", nullable = true, length = 32)
    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }


    @Column(name = "qzywl", nullable = true, length = 32)
    public String getQzywl() {
        return this.qzywl;
    }

    public void setQzywl(String qzywl) {
        this.qzywl = qzywl;
    }


    @Column(name = "relation_date", nullable = true, length = 32)
    public String getRelationDate() {
        return this.relationDate;
    }

    public void setRelationDate(String relationDate) {
        this.relationDate = relationDate;
    }

    @Column(name = "guo_jia", nullable = true, length = 32)
    public String getGuoJia() {
        return this.guoJia;
    }

    public void setGuoJia(String guoJia) {
        this.guoJia = guoJia;
    }

    @Column(name = "zlxr", nullable = true, length = 32)
    public String getZlxr() {
        return this.zlxr;
    }

    public void setZlxr(String zlxr) {
        this.zlxr = zlxr;
    }

    @Column(name = "ywlxr2", nullable = true, length = 32)
    public String getYwlxr2() {
        return this.ywlxr2;
    }

    public void setYwlxr2(String ywlxr2) {
        this.ywlxr2 = ywlxr2;
    }

    @Column(name = "ywlxr3", nullable = true, length = 32)
    public String getYwlxr3() {
        return this.ywlxr3;
    }

    public void setYwlxr3(String ywlxr3) {
        this.ywlxr3 = ywlxr3;
    }

    @Column(name = "email", nullable = true, length = 32)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "daan_num", nullable = true, length = 32)
    public String getDaanNum() {
        return this.daanNum;
    }

    public void setDaanNum(String daanNum) {
        this.daanNum = daanNum;
    }

    @Column(name = "ywlxr4", nullable = true, length = 32)
    public String getYwlxr4() {
        return ywlxr4;
    }

    public void setYwlxr4(String ywlxr4) {
        this.ywlxr4 = ywlxr4;
    }

    @Column(name = "ywlxr5", nullable = true, length = 32)
    public String getYwlxr5() {
        return ywlxr5;
    }

    public void setYwlxr5(String ywlxr5) {
        this.ywlxr5 = ywlxr5;
    }

    @Column(name = "cus_type_name", nullable = true, length = 32)
    public String getCusTypeName() {
        return cusTypeName;
    }

    public void setCusTypeName(String cusTypeName) {
        this.cusTypeName = cusTypeName;
    }

    @Column(name = "main_content", nullable = true, length = 32)
    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    @Column(name = "ywlxr2_email", nullable = true, length = 32)
    public String getYwlxr2Email() {
        return ywlxr2Email;
    }

    public void setYwlxr2Email(String ywlxr2Email) {
        this.ywlxr2Email = ywlxr2Email;
    }

    @Column(name = "ywlxr2_telphone", nullable = true, length = 32)
    public String getYwlxr2Telphone() {
        return ywlxr2Telphone;
    }

    public void setYwlxr2Telphone(String ywlxr2Telphone) {
        this.ywlxr2Telphone = ywlxr2Telphone;
    }

    @Column(name = "ywlxr3_email", nullable = true, length = 32)
    public String getYwlxr3Email() {
        return ywlxr3Email;
    }

    public void setYwlxr3Email(String ywlxr3Email) {
        this.ywlxr3Email = ywlxr3Email;
    }

    @Column(name = "ywlxr3_telphone", nullable = true, length = 32)
    public String getYwlxr3Telphone() {
        return ywlxr3Telphone;
    }

    public void setYwlxr3Telphone(String ywlxr3Telphone) {
        this.ywlxr3Telphone = ywlxr3Telphone;
    }

    @Column(name = "ywlxr4_email", nullable = true, length = 32)
    public String getYwlxr4Email() {
        return ywlxr4Email;
    }

    public void setYwlxr4Email(String ywlxr4Email) {
        this.ywlxr4Email = ywlxr4Email;
    }

    @Column(name = "ywlxr4_telphone", nullable = true, length = 32)
    public String getYwlxr4Telphone() {
        return ywlxr4Telphone;
    }

    public void setYwlxr4Telphone(String ywlxr4Telphone) {
        this.ywlxr4Telphone = ywlxr4Telphone;
    }

    @Column(name = "ywlxr5_email", nullable = true, length = 32)
    public String getYwlxr5Email() {
        return ywlxr5Email;
    }

    public void setYwlxr5Email(String ywlxr5Email) {
        this.ywlxr5Email = ywlxr5Email;
    }

    @Column(name = "ywlxr5_telphone", nullable = true, length = 32)
    public String getYwlxr5Telphone() {
        return ywlxr5Telphone;
    }

    public void setYwlxr5Telphone(String ywlxr5Telphone) {
        this.ywlxr5Telphone = ywlxr5Telphone;
    }

    @Column(name = "bz", nullable = true, length = 32)
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Column(name = "TAXPAYER_NUM", nullable = true, length = 32)
    public String getTaxpayerNum() {
        return this.taxpayerNum;
    }

    public void setTaxpayerNum(String taxpayerNum) {
        this.taxpayerNum = taxpayerNum;
    }

    @Column(name = "BANK_NAME", nullable = true, length = 256)
    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "BANK_ACCOUNT", nullable = true, length = 56)
    public String getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "sui_num", nullable = true, length = 56)
    public String getSuiNum() {
        return suiNum;
    }

    public void setSuiNum(String suiNum) {
        this.suiNum = suiNum;
    }

    @Column(name = "cus_level", nullable = true, length = 56)
    public String getCusLevel() {
        return cusLevel;
    }

    public void setCusLevel(String cusLevel) {
        this.cusLevel = cusLevel;
    }

    @Column(name = "bcqk_money", nullable = true, length = 50)
    public String getBcqkMoney() {
        return bcqkMoney;
    }

    public void setBcqkMoney(String bcqkMoney) {
        this.bcqkMoney = bcqkMoney;
    }
}
