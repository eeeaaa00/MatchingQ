package model;

// ���������� �����ϱ� ���� DTO
public class ProfDTO {
	private String profId;		// �����ڵ�
	private String profName;		// ������
	
	public ProfDTO(String profId, String profName) {
		this.profId = profId;
		this.profName = profName;
	}
	
	public String getProfId() {
		return profId;
	}
	public void setProfId(String profId) {
		this.profId = profId;
	}
	public String getProfName() {
		return profName;
	}
	public void setProfName(String profName) {
		this.profName = profName;
	}	
	
}
