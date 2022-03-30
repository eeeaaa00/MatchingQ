package model;

/**
 * ���� ������ ���� �ʿ��� ������ Ŭ����. CLASS ���̺�� ������
 */
public class ClassDTO {
	private int		classId;
	private	String	courseTitle;
	private int		year;
	private int 	semester;
	private int		division;
	private String	profName;
	
	public ClassDTO() { }		// �⺻ ������
	
	public ClassDTO(int classId, String courseTitle, int year, int semester, int division, String profName) {
		this.classId = classId;
		this.courseTitle = courseTitle;
		this.year = year;
		this.semester = semester;
		this.division = division;
		this.profName = profName;
	}
	
	public ClassDTO(String courseTitle, int year, int semester, int division, String profName) {
		this.courseTitle = courseTitle;
		this.year = year;
		this.semester = semester;
		this.division = division;
		this.profName = profName;
	}
	
	public ClassDTO(int classId, int year, int semester, int division) {
		this.classId = classId;
		this.year = year;
		this.semester = semester;
		this.division = division;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getDivision() {
		return division;
	}

	public void setDivision(int division) {
		this.division = division;
	}

	public String getProfName() {
		return profName;
	}

	public void setProfName(String profName) {
		this.profName = profName;
	}

	@Override
	public String toString() {
		return "Class [classId=" + classId + ", courseTitle=" + courseTitle + ", year=" + year + ", semester=" + semester + ", division="
				+ division + ", profName=" + profName +  "]";
	}	
}
