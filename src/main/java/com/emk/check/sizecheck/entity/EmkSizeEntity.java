package com.emk.check.sizecheck.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 尺码表
 * @author onlineGenerator
 * @date 2018-09-26 22:35:18
 * @version V1.0   
 *
 */
@Entity
@Table(name = "emk_size", schema = "")
@SuppressWarnings("serial")
public class EmkSizeEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	private String formId;
	private String sizeA;
	private String sizeB;
	private String sizeC;
	private String sizeD;
	private String sizeE;
	private String sizeF;
	private String sizeG;
	private String sizeH;
	private String sizeI;
	private String sizeJ;
	private String sizeK;
	private String sizeL;
	private String sizeM;
	private String sizeN;
	private String sizeO;
	private String sizeP;
	private String sizeQ;
	private String sizeR;
	private String sizeS;
	private String sizeT;
	private String sizeU;
	private String sizeV;

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

	public void setId(String id) {
		this.id = id;
	}

	@Column(name ="form_id",nullable=false,length=36)
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Column(name ="size_a",nullable=true,length=12)
	public String getSizeA() {
		return sizeA;
	}

	public void setSizeA(String sizeA) {
		this.sizeA = sizeA;
	}

	@Column(name ="size_b",nullable=true,length=12)
	public String getSizeB() {
		return sizeB;
	}

	public void setSizeB(String sizeB) {
		this.sizeB = sizeB;
	}

	@Column(name ="size_c",nullable=true,length=12)
	public String getSizeC() {
		return sizeC;
	}

	public void setSizeC(String sizeC) {
		this.sizeC = sizeC;
	}

	@Column(name ="size_d",nullable=true,length=12)
	public String getSizeD() {
		return sizeD;
	}

	public void setSizeD(String sizeD) {
		this.sizeD = sizeD;
	}

	@Column(name ="size_e",nullable=true,length=12)
	public String getSizeE() {
		return sizeE;
	}

	public void setSizeE(String sizeE) {
		this.sizeE = sizeE;
	}

	@Column(name ="size_f",nullable=true,length=12)
	public String getSizeF() {
		return sizeF;
	}

	public void setSizeF(String sizeF) {
		this.sizeF = sizeF;
	}

	@Column(name ="size_g",nullable=true,length=12)
	public String getSizeG() {
		return sizeG;
	}

	public void setSizeG(String sizeG) {
		this.sizeG = sizeG;
	}

	@Column(name ="size_h",nullable=true,length=12)
	public String getSizeH() {
		return sizeH;
	}

	public void setSizeH(String sizeH) {
		this.sizeH = sizeH;
	}

	@Column(name ="size_i",nullable=true,length=12)
	public String getSizeI() {
		return sizeI;
	}

	public void setSizeI(String sizeI) {
		this.sizeI = sizeI;
	}

	@Column(name ="size_j",nullable=true,length=12)
	public String getSizeJ() {
		return sizeJ;
	}

	public void setSizeJ(String sizeJ) {
		this.sizeJ = sizeJ;
	}

	@Column(name ="size_k",nullable=true,length=12)
	public String getSizeK() {
		return sizeK;
	}

	public void setSizeK(String sizeK) {
		this.sizeK = sizeK;
	}

	@Column(name ="size_l",nullable=true,length=12)
	public String getSizeL() {
		return sizeL;
	}

	public void setSizeL(String sizeL) {
		this.sizeL = sizeL;
	}

	@Column(name ="size_m",nullable=true,length=12)
	public String getSizeM() {
		return sizeM;
	}

	public void setSizeM(String sizeM) {
		this.sizeM = sizeM;
	}

	@Column(name ="size_n",nullable=true,length=12)
	public String getSizeN() {
		return sizeN;
	}

	public void setSizeN(String sizeN) {
		this.sizeN = sizeN;
	}

	@Column(name ="size_o",nullable=true,length=12)
	public String getSizeO() {
		return sizeO;
	}

	public void setSizeO(String sizeO) {
		this.sizeO = sizeO;
	}

	@Column(name ="size_p",nullable=true,length=12)
	public String getSizeP() {
		return sizeP;
	}

	public void setSizeP(String sizeP) {
		this.sizeP = sizeP;
	}

	@Column(name ="size_q",nullable=true,length=12)
	public String getSizeQ() {
		return sizeQ;
	}

	public void setSizeQ(String sizeQ) {
		this.sizeQ = sizeQ;
	}

	@Column(name ="size_r",nullable=true,length=12)
	public String getSizeR() {
		return sizeR;
	}

	public void setSizeR(String sizeR) {
		this.sizeR = sizeR;
	}

	@Column(name ="size_s",nullable=true,length=12)
	public String getSizeS() {
		return sizeS;
	}

	public void setSizeS(String sizeS) {
		this.sizeS = sizeS;
	}

	@Column(name ="size_t",nullable=true,length=12)
	public String getSizeT() {
		return sizeT;
	}

	public void setSizeT(String sizeT) {
		this.sizeT = sizeT;
	}

	@Column(name ="size_u",nullable=true,length=12)
	public String getSizeU() {
		return sizeU;
	}

	public void setSizeU(String sizeU) {
		this.sizeU = sizeU;
	}

	@Column(name ="size_v",nullable=true,length=12)
	public String getSizeV() {
		return sizeV;
	}

	public void setSizeV(String sizeV) {
		this.sizeV = sizeV;
	}
}
