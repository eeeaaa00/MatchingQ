package model.service;

import java.sql.SQLException;
import java.util.List;

import model.ClassDTO;
import model.ClassRemove;
import model.Comment;
import model.TeamProject;
import model.TeamProjectMatching;
import model.dao.TeamProjectDAO;

/**
 * TeamProject ���� API�� ����ϴ� �����ڵ��� ���� �����ϰ� �Ǵ� Ŭ����.
 * TeamProjectDAO�� �̿��Ͽ� �����ͺ��̽��� ������ ���� �۾��� �����ϵ��� �ϸ�,
 * �����ͺ��̽��� �����͵��� �̿��Ͽ� �����Ͻ� ������ �����ϴ� ������ �Ѵ�.
 * �����Ͻ� ������ ������ ��쿡�� �����Ͻ� �������� �����ϴ� Ŭ������ 
 * ������ �� �� �ִ�.
 */
public class TeamProjectrManager {
	private static TeamProjectrManager teamProMan = new TeamProjectrManager();
	private TeamProjectDAO teamProDAO;

	public TeamProjectrManager() {
		try {
			teamProDAO = new TeamProjectDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static TeamProjectrManager getInstance() {
		return teamProMan;
	}
	
	public int create(TeamProject teamPro, String stuId) throws SQLException {
		return teamProDAO.create(teamPro, stuId);
	}
	
	public int createMatching(TeamProjectMatching teamProMat) throws SQLException {
		return teamProDAO.createMatching(teamProMat);
	}
	
	public int createComment(Comment comm) throws SQLException {
		return teamProDAO.createComment(comm);
	}
	
	public List<TeamProject> findMyTeamProjectList(String stu_id) throws SQLException {
		return teamProDAO.findMyTeamProjectList(stu_id);
	}
	
	public List<Comment> findMyTeamProjectCommentList(int teamId, String stu_id) throws SQLException {
		return teamProDAO.findMyTeamProjectCommentList(teamId, stu_id);
	}

	public TeamProject findTeamProject(int teamId) throws SQLException, UserNotFoundException {
		TeamProject teamPro = teamProDAO.findTeamProject(teamId);				
		return teamPro;
	}

	public List<TeamProject> findTeamProjectList() throws SQLException {
		return teamProDAO.findTeamProjectList();
	}
	
	public List<TeamProject> findTeamProjectList(int courseId, int profId) throws SQLException {
		return teamProDAO.findTeamProjectList(courseId, profId);
	}
	
	public List<TeamProject> findTeamProjectList(int classId) throws SQLException {
		return teamProDAO.findTeamProjectList(classId);
	}
	
	public ClassDTO findClass(int classId) throws SQLException, NotExistingClassException {
		return teamProDAO.findClass(classId);
	}
	
	public int findClass(int courseId, int year, int semester, int division, int profId) throws SQLException, NotExistingClassException {
		String result = teamProDAO.findClass(courseId, year, semester, division, profId);
		if (result == null) {
			throw new NotExistingClassException("�ش� ������ �������� �ʽ��ϴ�.");
		}
		return Integer.parseInt(result);
	}
	
	public int findClass(String courseTitle, int year, int semester, int division, String profName) throws SQLException, NotExistingClassException {
		String result = teamProDAO.findClass(courseTitle, year, semester, division, profName);
		if (result == null) {
			throw new NotExistingClassException("�ش� ������ �������� �ʽ��ϴ�.");
		}
		return Integer.parseInt(result);
	}
	
	public List<ClassDTO> findSemestertList(int courseId, int profId) throws SQLException {
		return teamProDAO.findSemestertList(courseId, profId);
	}

	public List<TeamProjectMatching> findWithTeamProject(int teamId) throws SQLException {
		return teamProDAO.findWithTeamProject(teamId);
	}
	
	public boolean isMyTeamProject(int teamId, String stu_id) throws SQLException {
		return teamProDAO.isMyTeamProject(teamId, stu_id);
	}
	
	public TeamProjectDAO getTeamProjectDAO() {
		return this.teamProDAO;
	}
	
	public List<ClassRemove> SearchClassListForTitle(String lectureTitle) throws SQLException {
		return teamProDAO.SearchClassListForTitle(lectureTitle);
	}
	
	public int remove(String userId, int teamId) throws SQLException {
		return teamProDAO.remove(userId, teamId);
	}
	
	public int removeAll(String userId, int teamId) throws SQLException, ExistingMemberException {
		TeamProject teamPro = teamProDAO.findTeamProject(teamId);
		if (teamPro.getRequestMemberCnt() != 0) {
			throw new ExistingMemberException("������ ������ �־� ��� �� �� �����ϴ�.");
		}else {
			return teamProDAO.removeAll(userId, teamId);
		}
	}
		
}
