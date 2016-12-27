package vo;
// default package

import java.util.Date;


/**
 * WebPltmTransCdeVO entity. @author MyEclipse Persistence Tools
 */

public class PltmTransCdeVO  implements java.io.Serializable {


	// Fields    
	private String CPkId;		//����
	private String CTypeId;		//������
	private String CCmpId;		//���չ�˾����
	private String CProdNo;		//��Ʒ����
	private String CDptCde;		//���Ŵ���
	private String CReqType;	//��������
	private String CEfftMrk;	//�Ƿ����ñ�־��1 ����   0 �����ã�
	private String CCdeFrom;	//Դ����
	private String CCdeTo;	    //Ŀ�����
	private String CNmeFrom;		//�ⲿ���ĺ���
	private String CNmeTo;	//ƽ̨���ĺ���
	private String CFromToMrk;	//�ֶ�ת����ʶ
	private String CMemo;		//ע��˵��
	private String CCrtCde;		//�����˴���
	private String CUpdCde;		//�޸��˴���
	private Date TCrtTm;		//����ʱ��
	private Date TUpdTm;		//�޸�ʱ��
	
	public PltmTransCdeVO() {
	}

	public PltmTransCdeVO(String cPkId, String cTypeId, String cCmpId,
			String cProdNo, String cDptCde, String cReqType, String cEfftMrk,
			String CCdeFrom, String CCdeTo, String CNmeFrom, String CNmeTo,
			String CFromToMrk, String cMemo, String cCrtCde, String cUpdCde,
			Date tCrtTm, Date tUpdTm) {
		super();
		this.CPkId = cPkId;
		this.CTypeId = cTypeId;
		this.CCmpId = cCmpId;
		this.CProdNo = cProdNo;
		this.CDptCde = cDptCde;
		this.CReqType = cReqType;
		this.CEfftMrk = cEfftMrk;
		this.CCdeFrom = CCdeFrom;
		this.CCdeTo = CCdeTo;
		this.CNmeFrom = CNmeFrom;
		this.CNmeTo = CNmeTo;
		this.CFromToMrk = CFromToMrk;
		this.CMemo = cMemo;
		this.CCrtCde = cCrtCde;
		this.CUpdCde = cUpdCde;
		this.TCrtTm = tCrtTm;
		this.TUpdTm = tUpdTm;
	}
	
	public String getCPkId() {
		return CPkId;
	}
	public void setCPkId(String cPkId) {
		CPkId = cPkId;
	}
	public String getCTypeId() {
		return CTypeId;
	}
	public void setCTypeId(String cTypeId) {
		CTypeId = cTypeId;
	}
	public String getCCmpId() {
		return CCmpId;
	}
	public void setCCmpId(String cCmpId) {
		CCmpId = cCmpId;
	}
	public String getCProdNo() {
		return CProdNo;
	}
	public void setCProdNo(String cProdNo) {
		CProdNo = cProdNo;
	}
	public String getCDptCde() {
		return CDptCde;
	}
	public void setCDptCde(String cDptCde) {
		CDptCde = cDptCde;
	}
	public String getCReqType() {
		return CReqType;
	}
	public void setCReqType(String cReqType) {
		CReqType = cReqType;
	}
	public String getCEfftMrk() {
		return CEfftMrk;
	}
	public void setCEfftMrk(String cEfftMrk) {
		CEfftMrk = cEfftMrk;
	}

	public String getCCdeFrom() {
		return CCdeFrom;
	}

	public void setCCdeFrom(String cdeFrom) {
		CCdeFrom = cdeFrom;
	}

	public String getCCdeTo() {
		return CCdeTo;
	}
	public void setCCdeTo(String cdeTo) {
		CCdeTo = cdeTo;
	}
	public String getCNmeFrom() {
		return CNmeFrom;
	}
	public void setCNmeFrom(String cNmeFrom) {
		CNmeFrom = cNmeFrom;
	}
	public String getCNmeTo() {
		return CNmeTo;
	}
	public void setCNmeTo(String cNmeTo) {
		CNmeTo = cNmeTo;
	}
	public String getCFromToMrk() {
		return CFromToMrk;
	}
	public void setCFromToMrk(String cFromToMrk) {
		CFromToMrk = cFromToMrk;
	}
	public String getCMemo() {
		return CMemo;
	}
	public void setCMemo(String cMemo) {
		CMemo = cMemo;
	}
	public String getCCrtCde() {
		return CCrtCde;
	}
	public void setCCrtCde(String cCrtCde) {
		CCrtCde = cCrtCde;
	}
	public String getCUpdCde() {
		return CUpdCde;
	}
	public void setCUpdCde(String cUpdCde) {
		CUpdCde = cUpdCde;
	}
	public Date getTCrtTm() {
		return TCrtTm;
	}
	public void setTCrtTm(Date tCrtTm) {
		TCrtTm = tCrtTm;
	}
	public Date getTUpdTm() {
		return TUpdTm;
	}
	public void setTUpdTm(Date tUpdTm) {
		TUpdTm = tUpdTm;
	}

}