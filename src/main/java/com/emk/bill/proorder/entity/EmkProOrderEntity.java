package com.emk.bill.proorder.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "emk_pro_order", schema = "")
public class EmkProOrderEntity implements Serializable {
    private String id;
    private String createName;
    private String createBy;
    private Date createDate;
    private String sysOrgCode;
    private String state;
    @Excel(name = "客户名称")
    private String cusName;
    @Excel(name = "出货仓库")
    private String storageName;
    private String processName;
    private String formType;
    private String flag;
    private String departId;
    private String orgCode;
    private String total;
    private String money;
    /**业务员*/
    private String businesser;

    private String yfMoney;
    private String yhMoney;
    private String shMoney;
    private String bcqkMoney;
    private String qcqkMoney;
    private String preBill;

    private String ljqkMoney;
    private String bankMoney;
    private String payState;
    private String recevieState;
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  业务员
     */

    @Column(name ="BUSINESSER",nullable=true,length=32)
    public String getBusinesser(){
        return this.businesser;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  业务员
     */
    public void setBusinesser(String businesser){
        this.businesser = businesser;
    }

    @Column(name="total", nullable=true, length=32)
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Column(name = "money", nullable = true, length = 32)
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    /**a01a05a01*/
    @Excel(name="a01a05a01",width=15)
    private String a01a05a01;
    /**a01a05a02*/
    @Excel(name="a01a05a02",width=15)
    private String a01a05a02;
    /**a01a05a03*/
    @Excel(name="a01a05a03",width=15)
    private String a01a05a03;
    /**a01a05a04*/
    @Excel(name="a01a05a04",width=15)
    private String a01a05a04;
    /**a01a05a05*/
    @Excel(name="a01a05a05",width=15)
    private String a01a05a05;
    /**a01a05a06*/
    @Excel(name="a01a05a06",width=15)
    private String a01a05a06;
    /**a01a05a07*/
    @Excel(name="a01a05a07",width=15)
    private String a01a05a07;
    private String a01a05a08;
    /**a01a05a08*/
    @Excel(name="a01a05a09",width=15)
    private String a01a05a09;
    @Excel(name="a01a05a10",width=15)
    private String a01a05a10;
    @Excel(name="a01a05a11",width=15)
    private String a01a05a11;
    @Excel(name="a01a05a12",width=15)
    private String a01a05a12;
    @Excel(name="a01a05a13",width=15)
    private String a01a05a13;
    @Excel(name="a01a05a14",width=15)
    private String a01a05a14;
    @Excel(name="a01a05a15",width=15)
    private String a01a05a15;
    @Excel(name="a01a05a16",width=15)
    private String a01a05a16;
    @Excel(name="a01a05a17",width=15)
    private String a01a05a17;
    @Excel(name="a01a05a18",width=15)
    private String a01a05a18;
    @Excel(name="a01a05a19",width=15)
    private String a01a05a19;
    @Excel(name="a01a05a20",width=15)
    private String a01a05a20;

    private String isInvoice;

    @Column(name ="is_invoice",nullable=true,length=32)
    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
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

    @Column(name = "STATE", nullable = true, length = 32)
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Formula("(select CONCAT(p.NAME_,'-',p.TASK_DEF_KEY_) from act_ru_task p where p.ASSIGNEE_ = id limit 0,1)")
    @Column(name = "process_name", nullable = true, length = 32)
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Column(name = "form_type", nullable = true, length = 32)
    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    @Column(name = "flag", nullable = true, length = 32)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "a01a05a01", nullable = true, length = 32)
    public String getA01a05a01() {
        return a01a05a01;
    }

    public void setA01a05a01(String a01a05a01) {
        this.a01a05a01 = a01a05a01;
    }

    @Column(name = "a01a05a02", nullable = true, length = 32)
    public String getA01a05a02() {
        return a01a05a02;
    }

    public void setA01a05a02(String a01a05a02) {
        this.a01a05a02 = a01a05a02;
    }

    @Column(name = "a01a05a03", nullable = true, length = 32)
    public String getA01a05a03() {
        return a01a05a03;
    }

    public void setA01a05a03(String a01a05a03) {
        this.a01a05a03 = a01a05a03;
    }

    @Column(name = "a01a05a04", nullable = true, length = 32)
    public String getA01a05a04() {
        return a01a05a04;
    }

    public void setA01a05a04(String a01a05a04) {
        this.a01a05a04 = a01a05a04;
    }

    @Column(name = "a01a05a05", nullable = true, length = 32)
    public String getA01a05a05() {
        return a01a05a05;
    }

    public void setA01a05a05(String a01a05a05) {
        this.a01a05a05 = a01a05a05;
    }

    @Column(name = "a01a05a06", nullable = true, length = 32)
    public String getA01a05a06() {
        return a01a05a06;
    }

    public void setA01a05a06(String a01a05a06) {
        this.a01a05a06 = a01a05a06;
    }

    @Column(name = "a01a05a07", nullable = true, length = 32)
    public String getA01a05a07() {
        return a01a05a07;
    }

    public void setA01a05a07(String a01a05a07) {
        this.a01a05a07 = a01a05a07;
    }

    @Column(name = "a01a05a08", nullable = true, length = 32)
    public String getA01a05a08() {
        return a01a05a08;
    }

    public void setA01a05a08(String a01a05a08) {
        this.a01a05a08 = a01a05a08;
    }

    @Column(name = "a01a05a09", nullable = true, length = 32)
    public String getA01a05a09() {
        return a01a05a09;
    }

    public void setA01a05a09(String a01a05a09) {
        this.a01a05a09 = a01a05a09;
    }

    @Column(name = "a01a05a10", nullable = true, length = 32)
    public String getA01a05a10() {
        return a01a05a10;
    }

    public void setA01a05a10(String a01a05a10) {
        this.a01a05a10 = a01a05a10;
    }

    @Column(name = "a01a05a11", nullable = true, length = 32)
    public String getA01a05a11() {
        return a01a05a11;
    }

    public void setA01a05a11(String a01a05a11) {
        this.a01a05a11 = a01a05a11;
    }

    @Column(name = "a01a05a12", nullable = true, length = 32)
    public String getA01a05a12() {
        return a01a05a12;
    }

    public void setA01a05a12(String a01a05a12) {
        this.a01a05a12 = a01a05a12;
    }

    @Column(name = "a01a05a13", nullable = true, length = 32)
    public String getA01a05a13() {
        return a01a05a13;
    }

    public void setA01a05a13(String a01a05a13) {
        this.a01a05a13 = a01a05a13;
    }

    @Column(name = "a01a05a14", nullable = true, length = 32)
    public String getA01a05a14() {
        return a01a05a14;
    }

    public void setA01a05a14(String a01a05a14) {
        this.a01a05a14 = a01a05a14;
    }

    @Column(name = "a01a05a15", nullable = true, length = 32)
    public String getA01a05a15() {
        return a01a05a15;
    }

    public void setA01a05a15(String a01a05a15) {
        this.a01a05a15 = a01a05a15;
    }

    @Column(name = "a01a05a16", nullable = true, length = 32)
    public String getA01a05a16() {
        return a01a05a16;
    }

    public void setA01a05a16(String a01a05a16) {
        this.a01a05a16 = a01a05a16;
    }

    @Column(name = "a01a05a17", nullable = true, length = 32)
    public String getA01a05a17() {
        return a01a05a17;
    }

    public void setA01a05a17(String a01a05a17) {
        this.a01a05a17 = a01a05a17;
    }

    @Column(name = "a01a05a18", nullable = true, length = 32)
    public String getA01a05a18() {
        return a01a05a18;
    }

    public void setA01a05a18(String a01a05a18) {
        this.a01a05a18 = a01a05a18;
    }

    @Column(name = "a01a05a19", nullable = true, length = 32)
    public String getA01a05a19() {
        return a01a05a19;
    }

    public void setA01a05a19(String a01a05a19) {
        this.a01a05a19 = a01a05a19;
    }

    @Column(name = "a01a05a20", nullable = true, length = 32)
    public String getA01a05a20() {
        return a01a05a20;
    }

    public void setA01a05a20(String a01a05a20) {
        this.a01a05a20 = a01a05a20;
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

    @Column(name = "CUS_NAME", nullable = true, length = 32)
    public String getCusName() {
        return this.cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }
    @Column(name = "STORAGE_NAME", nullable = true, length = 256)
    public String getStorageName() {
        return this.storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    @Column(name = "yh_money", nullable = true, length = 32)
    public String getYhMoney() {
        return yhMoney;
    }

    public void setYhMoney(String yhMoney) {
        this.yhMoney = yhMoney;
    }

    @Column(name = "sh_money", nullable = true, length = 32)
    public String getShMoney() {
        return shMoney;
    }

    public void setShMoney(String shMoney) {
        this.shMoney = shMoney;
    }

    @Column(name = "bcq_money", nullable = true, length = 32)
    public String getBcqkMoney() {
        return bcqkMoney;
    }

    public void setBcqkMoney(String bcqkMoney) {
        this.bcqkMoney = bcqkMoney;
    }

    @Column(name = "qcqk_money", nullable = true, length = 32)
    public String getQcqkMoney() {
        return qcqkMoney;
    }

    public void setQcqkMoney(String qcqkMoney) {
        this.qcqkMoney = qcqkMoney;
    }

    @Column(name = "ljqk_money", nullable = true, length = 32)
    public String getLjqkMoney() {
        return ljqkMoney;
    }

    public void setLjqkMoney(String ljqkMoney) {
        this.ljqkMoney = ljqkMoney;
    }

    @Column(name = "bank_money", nullable = true, length = 32)
    public String getBankMoney() {
        return bankMoney;
    }

    public void setBankMoney(String bankMoney) {
        this.bankMoney = bankMoney;
    }

    @Column(name = "yf_money", nullable = true, length = 32)
    public String getYfMoney() {
        return yfMoney;
    }

    public void setYfMoney(String yfMoney) {
        this.yfMoney = yfMoney;
    }


    @Column(name = "pre_bill", nullable = true, length = 32)
    public String getPreBill() {
        return preBill;
    }

    public void setPreBill(String preBill) {
        this.preBill = preBill;
    }

    @Column(name = "pay_state", nullable = true, length = 32)
    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    @Column(name = "recevie_state", nullable = true, length = 32)
    public String getRecevieState() {
        return recevieState;
    }

    public void setRecevieState(String recevieState) {
        this.recevieState = recevieState;
    }
}
