package model;


// �л��� ������ ������ �����ϱ� ���� DTO(Data Transition Object)
public class StudentDTO {
   private String stuId;				// �й�
   private String stuName = null;		// ����
   private String password = null;		// ���
   private int grade;					// �г�
   private String dProject = null;		// ����
   private String dTeamProject = null;	// ����
   private String dCreditRate = null;	// ��������
   private String dAttendance = null;	// ���
   private String dNumberOfExam = null;	// ����Ƚ��
   
   public StudentDTO() { } //�⺻ ������
   
   public StudentDTO(String stuId, String stuName, int grade) {
	   this.stuId = stuId;
	   this.stuName = stuName;
	   this.grade = grade;
   }
   
   public StudentDTO(String stuId, String stuName, String password, int grade, String dProject, String dTeamProject, String dCreditRate, String dAttendance, String dNumberOfExam) {
	   this.stuId = stuId;
	   this.stuName = stuName;
	   this.password = password;
	   this.grade = grade;
	   this.dProject = dProject;
	   this.dTeamProject = dTeamProject;
	   this.dCreditRate = dCreditRate;
	   this.dAttendance = dAttendance;
	   this.dNumberOfExam = dNumberOfExam;
   }
   
   public String getStuId() {
	   return stuId;
   }
   public void setStuId(String stuId) {
	   this.stuId = stuId;
   }
   public String getStuName() {
	   return stuName;
   }
   public void setStuName(String stuName) {
	   this.stuName = stuName;
   }
   public String getPassword() {
	return password;
   }
   public void setPassword(String password) {
	   this.password = password;
   }
   public int getGrade() {
	   return grade;
   }
   public void setGrade(int grade) {
	   this.grade = grade;
   }
   public String getdProject() {
	   return dProject;
   }
   public void setdProject(String dProject) {
	   this.dProject = dProject;
   }
   public String getdTeamProject() {
	   return dTeamProject;
   }
   public void setdTeamProject(String dTeamProject) {
	   this.dTeamProject = dTeamProject;
   }
   public String getdCreditRate() {
	   return dCreditRate;
   }
   public void setdCreditRate(String dCreditRate) {
	   this.dCreditRate = dCreditRate;
   }
   public String getdAttendance() {
	   return dAttendance;
   }
   public void setdAttendance(String dAttendance) {
	   this.dAttendance = dAttendance;
   }
   public String getdNumberOfExam() {
	   return dNumberOfExam;
   }
   public void setdNumberOfExam(String dNumberOfExam) {
	   this.dNumberOfExam = dNumberOfExam;
   }
   
   /* ��й�ȣ �˻� */
	public boolean matchStuPassword(String password) {
		if (password == null) {
			return false;
		}
		return this.password.equals(password);
	}
	
	public boolean isSameStudent(String stuId) {
       return this.stuId.equals(stuId);
   }

	@Override
	public String toString() {
		return "User [stuId=" + stuId + ", password=" + password + ", name=" + stuName + ", grade=" + grade 
				+ ", dProject=" + dProject + ", dTeamProject=" + dTeamProject + ", dCreditRate=" + dCreditRate 
				+ ", dAttendance=" + dAttendance + ", dNumberOfExam=" + dNumberOfExam + "]";
	}	
}
