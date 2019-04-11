package auxiliary.loginObject;

public class MemberAuthorization implements java.io.Serializable {

	private long memberID;

	private String bankCard;
	private String idCard;
	private String payPassword;

	public MemberAuthorization() {}

	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
}
