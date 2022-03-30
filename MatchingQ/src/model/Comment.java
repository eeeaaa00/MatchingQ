package model;

import java.sql.Date;

/**
 * ���� ��������Ʈ�� ��� ������ ���� �ʿ��� ������ Ŭ����. COMMENTTABLE ���̺�� ������
 */
public class Comment {
	private int		teamId;
	private StudentDTO		s;
	private String	comments;
	private Date	commentDate;
	
	public Comment() { }		// �⺻ ������
	
	public Comment(int teamId, StudentDTO s, String comments, Date commentDate) {
		this.teamId = teamId;
		this.s = s;
		this.comments = comments;
		this.commentDate = commentDate;
	}
	
	public Comment(int teamId, String comments, Date commentDate) {
		this.teamId = teamId;
		this.comments = comments;
		this.commentDate = commentDate;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	@Override
	public String toString() {
		return "Comment [teamId=" + teamId + ", stu_id=" + s.getStuId() + ", comments=" + comments + ", commentDate=" + commentDate + "]";
	}	
}
