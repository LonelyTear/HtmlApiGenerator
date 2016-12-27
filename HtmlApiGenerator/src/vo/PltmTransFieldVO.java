package vo;

import java.util.Date;

public class PltmTransFieldVO {
	
	private String CPkId;		//主键
	private String CCmpId;		//保险公司代码
	private String CProdNo;		//产品代码
	private String CDptCde;		//机构代码
	private String CReqType;	//请求类型
	private String CEfftMrk;	//是否有效
	private String CFieldFrom;	//源字段
	private String CFieldTo;	//目标字段
	private String CFieldDeft;  //默认值 *代表无默认值
	private String CMemo;		//注释说明
	private String CCrtCde;		//创建人代码
	private String CUpdCde;		//修改人代码
	private Date   TCrtTm;		//创建时间
	private Date   TUpdTm;		//修改时间
	private String CFromToMrk;  //字段转换标示 
	private String CTransferMrk;//转义标示
	private String CIsNullableMrk;  //必输标示
	
	
	public PltmTransFieldVO() {
		super();
	}
	
	
	public PltmTransFieldVO(String cCmpId, String cDptCde, String cProdNo,  String cReqType,String cFromToMrk ,String cEfftMrk) {
		super();
		CCmpId = cCmpId;
		CProdNo = cProdNo;
		CDptCde = cDptCde;
		CReqType = cReqType;
		CEfftMrk = cEfftMrk;
		CFromToMrk = cFromToMrk;
	}


	public String getCFromToMrk() {
		return CFromToMrk;
	}
	public void setCFromToMrk(String cFromToMrk) {
		CFromToMrk = cFromToMrk;
	}
	public String getCPkId() {
		return CPkId;
	}
	public void setCPkId(String pkId) {
		CPkId = pkId;
	}
	public String getCCmpId() {
		return CCmpId;
	}
	public void setCCmpId(String cmpId) {
		CCmpId = cmpId;
	}
	public String getCProdNo() {
		return CProdNo;
	}
	public void setCProdNo(String prodNo) {
		CProdNo = prodNo;
	}
	public String getCDptCde() {
		return CDptCde;
	}
	public void setCDptCde(String dptCde) {
		CDptCde = dptCde;
	}
	
	public String getCReqType() {
		return CReqType;
	}
	public void setCReqType(String reqType) {
		CReqType = reqType;
	}
	public String getCEfftMrk() {
		return CEfftMrk;
	}
	public void setCEfftMrk(String efftMrk) {
		CEfftMrk = efftMrk;
	}
	public String getCFieldFrom() {
		return CFieldFrom;
	}
	public void setCFieldFrom(String cFieldFrom) {
		CFieldFrom = cFieldFrom;
	}
	public String getCFieldTo() {
		return CFieldTo;
	}
	public void setCFieldTo(String cFieldTo) {
		CFieldTo = cFieldTo;
	}
	public String getCMemo() {
		return CMemo;
	}
	public void setCMemo(String memo) {
		CMemo = memo;
	}
	public String getCCrtCde() {
		return CCrtCde;
	}
	public void setCCrtCde(String crtCde) {
		CCrtCde = crtCde;
	}
	public String getCUpdCde() {
		return CUpdCde;
	}
	public void setCUpdCde(String updCde) {
		CUpdCde = updCde;
	}
	public Date getTCrtTm() {
		return TCrtTm;
	}
	public void setTCrtTm(Date crtTm) {
		TCrtTm = crtTm;
	}
	public Date getTUpdTm() {
		return TUpdTm;
	}
	public void setTUpdTm(Date updTm) {
		TUpdTm = updTm;
	}
	public String getCTransferMrk() {
		return CTransferMrk;
	}
	public void setCTransferMrk(String transferMrk) {
		CTransferMrk = transferMrk;
	}
	public String getCIsNullableMrk() {
		return CIsNullableMrk;
	}
	public void setCIsNullableMrk(String isNullableMrk) {
		CIsNullableMrk = isNullableMrk;
	}
	
	public String getCFieldDeft() {
		return CFieldDeft;
	}

	public void setCFieldDeft(String cFieldDeft) {
		CFieldDeft = cFieldDeft;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((CFieldFrom == null) ? 0 : CFieldFrom.hashCode());
		result = prime * result
				+ ((CFieldTo == null) ? 0 : CFieldTo.hashCode());
		result = prime * result
				+ ((CIsNullableMrk == null) ? 0 : CIsNullableMrk.hashCode());
		result = prime * result + ((CMemo == null) ? 0 : CMemo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PltmTransFieldVO other = (PltmTransFieldVO) obj;
		if (CFieldFrom == null) {
			if (other.CFieldFrom != null)
				return false;
		} else if (!CFieldFrom.equals(other.CFieldFrom))
			return false;
		if (CFieldTo == null) {
			if (other.CFieldTo != null)
				return false;
		} else if (!CFieldTo.equals(other.CFieldTo))
			return false;
		if (CIsNullableMrk == null) {
			if (other.CIsNullableMrk != null)
				return false;
		} else if (!CIsNullableMrk.equals(other.CIsNullableMrk))
			return false;
		if (CMemo == null) {
			if (other.CMemo != null)
				return false;
		} else if (!CMemo.equals(other.CMemo))
			return false;
		return true;
	}
	
	
}
