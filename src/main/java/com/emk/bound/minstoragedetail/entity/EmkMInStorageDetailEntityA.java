package com.emk.bound.minstoragedetail.entity;

import com.emk.bill.proorderdetail.entity.EmkProOrderDetailEntity;
import com.emk.check.sizecheck.entity.EmkSizeCheckDetailEntity;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 出入库明细
 * @author onlineGenerator
 * @date 2018-09-13 22:20:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_m_in_storage_detail", schema = "")
@SuppressWarnings("serial")
public class EmkMInStorageDetailEntityA implements java.io.Serializable {
	/**主键*/
	private String id;

	/**a01a09a01*/
	@Excel(name="a01a09a01",width=15)
	private String a01a09a01;
	/**a01a09a02*/
	@Excel(name="a01a09a02",width=15)
	private String a01a09a02;
	/**a01a09a03*/
	@Excel(name="a01a09a03",width=15)
	private String a01a09a03;
	/**a01a09a04*/
	@Excel(name="a01a09a04",width=15)
	private String a01a09a04;
	/**a01a09a05*/
	@Excel(name="a01a09a05",width=15)
	private String a01a09a05;
	/**a01a09a06*/
	@Excel(name="a01a09a06",width=15)
	private String a01a09a06;
	/**a01a09a07*/
	@Excel(name="a01a09a07",width=15)
	private String a01a09a07;
	/**a01a09a08*/
	@Excel(name="a01a09a08",width=15)
	private String a01a09a08;
	/**a01a09a09*/
	@Excel(name="a01a09a09",width=15)
	private String a01a09a09;
	/**a01a09a10*/
	@Excel(name="a01a09a10",width=15)
	private String a01a09a10;
	/**a01a09a11*/
	@Excel(name="a01a09a11",width=15)
	private String a01a09a11;
	/**a01a09a12*/
	@Excel(name="a01a09a12",width=15)
	private String a01a09a12;
	/**a01a09a13*/
	@Excel(name="a01a09a13",width=15)
	private String a01a09a13;
	/**a01a09a14*/
	@Excel(name="a01a09a14",width=15)
	private String a01a09a14;
	/**a01a09a15*/
	@Excel(name="a01a09a15",width=15)
	private String a01a09a15;
	/**a01a09a16*/
	@Excel(name="a01a09a16",width=15)
	private String a01a09a16;
	/**a01a09a17*/
	@Excel(name="a01a09a17",width=15)
	private String a01a09a17;
	/**a01a09a18*/
	@Excel(name="a01a09a18",width=15)
	private String a01a09a18;
	/**a01a09a19*/
	@Excel(name="a01a09a19",width=15)
	private String a01a09a19;
	/**a01a09a20*/
	@Excel(name="a01a09a20",width=15)
	private String a01a09a20;

	private String departId;
	private String orgCode;
	private String inStorageId;

	private EmkProOrderDetailEntity emkProOrderDetailEntity;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="p_id")
	public EmkProOrderDetailEntity getEmkProOrderDetailEntity() {
		return emkProOrderDetailEntity;
	}

	public void setEmkProOrderDetailEntity(EmkProOrderDetailEntity emkProOrderDetailEntity) {
		this.emkProOrderDetailEntity = emkProOrderDetailEntity;
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


	@Column(name ="IN_STORAGE_ID",nullable=true,length=32)
	public String getInStorageId(){
		return this.inStorageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  入库ID
	 */
	public void setInStorageId(String inStorageId){
		this.inStorageId = inStorageId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}

	@Column(name ="a01a09a01",nullable=true,length=32)
	public String getA01a09a01() {
		return a01a09a01;
	}

	public void setA01a09a01(String a01a09a01) {
		this.a01a09a01 = a01a09a01;
	}

	@Column(name ="a01a09a02",nullable=true,length=32)
	public String getA01a09a02() {
		return a01a09a02;
	}

	public void setA01a09a02(String a01a09a02) {
		this.a01a09a02 = a01a09a02;
	}

	@Column(name ="a01a09a03",nullable=true,length=32)
	public String getA01a09a03() {
		return a01a09a03;
	}

	public void setA01a09a03(String a01a09a03) {
		this.a01a09a03 = a01a09a03;
	}

	@Column(name ="a01a09a04",nullable=true,length=32)
	public String getA01a09a04() {
		return a01a09a04;
	}

	public void setA01a09a04(String a01a09a04) {
		this.a01a09a04 = a01a09a04;
	}

	@Column(name ="a01a09a05",nullable=true,length=32)
	public String getA01a09a05() {
		return a01a09a05;
	}

	public void setA01a09a05(String a01a09a05) {
		this.a01a09a05 = a01a09a05;
	}

	@Column(name ="a01a09a06",nullable=true,length=32)
	public String getA01a09a06() {
		return a01a09a06;
	}

	public void setA01a09a06(String a01a09a06) {
		this.a01a09a06 = a01a09a06;
	}

	@Column(name ="a01a09a07",nullable=true,length=32)
	public String getA01a09a07() {
		return a01a09a07;
	}

	public void setA01a09a07(String a01a09a07) {
		this.a01a09a07 = a01a09a07;
	}

	@Column(name ="a01a09a08",nullable=true,length=32)
	public String getA01a09a08() {
		return a01a09a08;
	}

	public void setA01a09a08(String a01a09a08) {
		this.a01a09a08 = a01a09a08;
	}

	@Column(name ="a01a09a09",nullable=true,length=32)
	public String getA01a09a09() {
		return a01a09a09;
	}

	public void setA01a09a09(String a01a09a09) {
		this.a01a09a09 = a01a09a09;
	}

	@Column(name ="a01a09a10",nullable=true,length=32)
	public String getA01a09a10() {
		return a01a09a10;
	}

	public void setA01a09a10(String a01a09a10) {
		this.a01a09a10 = a01a09a10;
	}

	@Column(name ="a01a09a11",nullable=true,length=32)
	public String getA01a09a11() {
		return a01a09a11;
	}

	public void setA01a09a11(String a01a09a11) {
		this.a01a09a11 = a01a09a11;
	}

	@Column(name ="a01a09a12",nullable=true,length=32)
	public String getA01a09a12() {
		return a01a09a12;
	}

	public void setA01a09a12(String a01a09a12) {
		this.a01a09a12 = a01a09a12;
	}

	@Column(name ="a01a09a13",nullable=true,length=32)
	public String getA01a09a13() {
		return a01a09a13;
	}

	public void setA01a09a13(String a01a09a13) {
		this.a01a09a13 = a01a09a13;
	}

	@Column(name ="a01a09a14",nullable=true,length=32)
	public String getA01a09a14() {
		return a01a09a14;
	}

	public void setA01a09a14(String a01a09a14) {
		this.a01a09a14 = a01a09a14;
	}

	@Column(name ="a01a09a15",nullable=true,length=32)
	public String getA01a09a15() {
		return a01a09a15;
	}

	public void setA01a09a15(String a01a09a15) {
		this.a01a09a15 = a01a09a15;
	}

	@Column(name ="a01a09a16",nullable=true,length=32)
	public String getA01a09a16() {
		return a01a09a16;
	}

	public void setA01a09a16(String a01a09a16) {
		this.a01a09a16 = a01a09a16;
	}

	@Column(name ="a01a09a17",nullable=true,length=32)
	public String getA01a09a17() {
		return a01a09a17;
	}

	public void setA01a09a17(String a01a09a17) {
		this.a01a09a17 = a01a09a17;
	}

	@Column(name ="a01a09a18",nullable=true,length=32)
	public String getA01a09a18() {
		return a01a09a18;
	}

	public void setA01a09a18(String a01a09a18) {
		this.a01a09a18 = a01a09a18;
	}

	@Column(name ="a01a09a19",nullable=true,length=32)
	public String getA01a09a19() {
		return a01a09a19;
	}

	public void setA01a09a19(String a01a09a19) {
		this.a01a09a19 = a01a09a19;
	}

	@Column(name ="a01a09a20",nullable=true,length=32)
	public String getA01a09a20() {
		return a01a09a20;
	}

	public void setA01a09a20(String a01a09a20) {
		this.a01a09a20 = a01a09a20;
	}
}