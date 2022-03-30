package model;

import java.sql.Date;

/**
 * ���� ��Ī ������ ���� �ʿ��� ������ Ŭ����. TEAMPROJECTMATCHING ���̺�� ������
 */
public class TeamProjectMatching {
	private int		teamId;
	private StudentDTO		s;
	private Date	joinDate;
	
	public TeamProjectMatching() { }		// �⺻ ������
	
	public TeamProjectMatching(int teamId, StudentDTO s, Date joinDate) {
		this.teamId = teamId;
		this.s = s;
		this.joinDate = joinDate;
	}
	
	public TeamProjectMatching(int teamId, Date joinDate) {
		this.teamId = teamId;
		this.joinDate = joinDate;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public StudentDTO getS() {
		return s;
	}

	public void setS(StudentDTO s) {
		this.s = s;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "TeamPjrectMatching [teamId=" + teamId + ", stu_id=" + s.getStuId() + ", joinDate=" + joinDate + "]";
	}	
}
