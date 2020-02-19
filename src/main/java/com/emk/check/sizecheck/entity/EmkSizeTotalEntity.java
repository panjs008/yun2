package com.emk.check.sizecheck.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**
 * @Title: Entity
 * @Description: 尺码数量表
 * @author onlineGenerator
 * @date 2018-09-26 22:35:18
 * @version V1.0
 *
 */
@Entity
@Table(name = "emk_size_total", schema = "")
@SuppressWarnings("serial")
public class EmkSizeTotalEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	private String pId;
	private String totalA;
	private String totalB;
	private String totalC;
	private String totalD;
	private String totalE;
	private String totalF;
	private String totalG;
	private String totalH;
	private String totalI;
	private String totalJ;
	private String totalK;

	private String totalL;
	private String totalM;
	private String totalN;
	private String totalO;
	private String totalP;
	private String totalQ;
	private String totalR;
	private String totalS;
	private String totalT;
	private String totalU;
	private String totalV;

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

	@Column(name ="p_id",nullable=true,length=12)
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	@Column(name ="total_a",nullable=true,length=12)
	public String getTotalA() {
		return totalA;
	}

	public void setTotalA(String totalA) {
		this.totalA = totalA;
	}

	@Column(name ="total_b",nullable=true,length=12)
	public String getTotalB() {
		return totalB;
	}

	public void setTotalB(String totalB) {
		this.totalB = totalB;
	}

	@Column(name ="total_c",nullable=true,length=12)
	public String getTotalC() {
		return totalC;
	}

	public void setTotalC(String totalC) {
		this.totalC = totalC;
	}

	@Column(name ="total_d",nullable=true,length=12)
	public String getTotalD() {
		return totalD;
	}

	public void setTotalD(String totalD) {
		this.totalD = totalD;
	}

	@Column(name ="total_e",nullable=true,length=12)
	public String getTotalE() {
		return totalE;
	}

	public void setTotalE(String totalE) {
		this.totalE = totalE;
	}

	@Column(name ="total_f",nullable=true,length=12)
	public String getTotalF() {
		return totalF;
	}

	public void setTotalF(String totalF) {
		this.totalF = totalF;
	}

	@Column(name ="total_g",nullable=true,length=12)
	public String getTotalG() {
		return totalG;
	}

	public void setTotalG(String totalG) {
		this.totalG = totalG;
	}

	@Column(name ="total_h",nullable=true,length=12)
	public String getTotalH() {
		return totalH;
	}

	public void setTotalH(String totalH) {
		this.totalH = totalH;
	}

	@Column(name ="total_i",nullable=true,length=12)
	public String getTotalI() {
		return totalI;
	}

	public void setTotalI(String totalI) {
		this.totalI = totalI;
	}

	@Column(name ="total_j",nullable=true,length=12)
	public String getTotalJ() {
		return totalJ;
	}

	public void setTotalJ(String totalJ) {
		this.totalJ = totalJ;
	}

	@Column(name ="total_k",nullable=true,length=12)
	public String getTotalK() {
		return totalK;
	}

	@Column(name ="total_l",nullable=true,length=12)
	public String getTotalL() {
		return totalL;
	}

	public void setTotalL(String totalL) {
		this.totalL = totalL;
	}

	public void setTotalK(String totalK) {
		this.totalK = totalK;
	}

	@Column(name ="total_m",nullable=true,length=12)
	public String getTotalM() {
		return totalM;
	}

	public void setTotalM(String totalM) {
		this.totalM = totalM;
	}

	@Column(name ="total_n",nullable=true,length=12)
	public String getTotalN() {
		return totalN;
	}

	public void setTotalN(String totalN) {
		this.totalN = totalN;
	}

	@Column(name ="total_o",nullable=true,length=12)
	public String getTotalO() {
		return totalO;
	}

	public void setTotalO(String totalO) {
		this.totalO = totalO;
	}

	@Column(name ="total_p",nullable=true,length=12)
	public String getTotalP() {
		return totalP;
	}

	public void setTotalP(String totalP) {
		this.totalP = totalP;
	}


	@Column(name ="total_q",nullable=true,length=12)
	public String getTotalQ() {
		return totalQ;
	}

	public void setTotalQ(String totalQ) {
		this.totalQ = totalQ;
	}

	@Column(name ="total_r",nullable=true,length=12)
	public String getTotalR() {
		return totalR;
	}

	public void setTotalR(String totalR) {
		this.totalR = totalR;
	}

	@Column(name ="total_s",nullable=true,length=12)
	public String getTotalS() {
		return totalS;
	}

	public void setTotalS(String totalS) {
		this.totalS = totalS;
	}

	@Column(name ="total_t",nullable=true,length=12)
	public String getTotalT() {
		return totalT;
	}

	public void setTotalT(String totalT) {
		this.totalT = totalT;
	}

	@Column(name ="total_u",nullable=true,length=12)
	public String getTotalU() {
		return totalU;
	}

	public void setTotalU(String totalU) {
		this.totalU = totalU;
	}

	@Column(name ="total_v",nullable=true,length=12)
	public String getTotalV() {
		return totalV;
	}

	public void setTotalV(String totalV) {
		this.totalV = totalV;
	}


}
