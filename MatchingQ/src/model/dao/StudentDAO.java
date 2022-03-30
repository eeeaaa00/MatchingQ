package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.StudentDTO;

/**
 * ����� ������ ���� �����ͺ��̽� �۾��� �����ϴ� DAO Ŭ����
 * STUDENT ���̺� ����� ������ �߰�, ����, ����, �˻� ���� 
 */
public class StudentDAO {
	private JDBCUtil jdbcUtil = null;
	
	public StudentDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil ��ü ����
	}
		
	/**
	 * ����� ���� ���̺� ���ο� ����� ����.
	 */
	public int create(StudentDTO student) throws SQLException {
		String sql = "INSERT INTO Student (stuId, name, password, grade, dProject, dTeamproject, dCreditRate, dAttendance, dNumberOfExam) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";		
		Object[] param = new Object[] {student.getStuId(), student.getStuName(),
				student.getPassword(), student.getGrade(), student.getdProject(),
				student.getdTeamProject(), student.getdCreditRate(), 
				student.getdAttendance(), student.getdNumberOfExam()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil �� insert���� �Ű� ���� ����
						
		try {				
			int result = jdbcUtil.executeUpdate();	// insert �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;			
	}

	/**
	 * ������ ����� ������ ����.
	 */
	public int update(StudentDTO student) throws SQLException {
		String sql = "UPDATE STUDENT "
					+ "SET password=?, name=?, grade=?, dProject=?, dTeamProject=?, dAttendance=?, dCreditRate=?, dNumberOfExam=?"
					+ "WHERE stuId=?";
		Object[] param = new Object[] {student.getPassword(), student.getStuName(), student.getGrade(), 
				student.getdProject(), student.getdTeamProject(), student.getdAttendance(), 
				student.getdCreditRate(), student.getdNumberOfExam(), student.getStuId()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil�� update���� �Ű� ���� ����
			
		try {				
			int result = jdbcUtil.executeUpdate();	// update �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;
	}

	/**
	 * ����� ID�� �ش��ϴ� ����ڸ� ����.
	 */
	public int remove(String stuId) throws SQLException {
		String sql = "DELETE FROM STUDENT WHERE stuId=?";		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {stuId});	// JDBCUtil�� delete���� �Ű� ���� ����

		try {				
			int result = jdbcUtil.executeUpdate();	// delete �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;
	}

	/**
	 * �־��� ����� ID�� �ش��ϴ� ����� ������ �����ͺ��̽����� ã�� User ������ Ŭ������ 
	 * �����Ͽ� ��ȯ.
	 */
	public StudentDTO findUser(String stuId) throws SQLException {
        String sql = "SELECT password, name, grade, dProject, dTeamproject, dCreditRate, dAttendance, dNumberOfExam "
        			+ "FROM Student "
        			+ "WHERE stuId=? ";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {stuId});	// JDBCUtil�� query���� �Ű� ���� ����

		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query ����
			if (rs.next()) {			// �л� ���� �߰�
				StudentDTO student = new StudentDTO(		// User ��ü�� �����Ͽ� �л� ������ ����
					stuId,
					rs.getString("name"),
					rs.getString("password"),
					rs.getInt("grade"),
					rs.getString("dProject"),
					rs.getString("dTeamproject"),
					rs.getString("dCreditRate"),
					rs.getString("dAttendance"),
					rs.getString("dNumberOfExam"));
				return student;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}

	/**
	 * ��ü ����� ������ �˻��Ͽ� List�� ���� �� ��ȯ
	 */
	public List<StudentDTO> findUserList() throws SQLException {
        String sql = "SELECT stuId, password, name, grade, dProject, dTeamproject, dCreditRate, dAttendance, dNumberOfExam " 
        		   + "FROM STUDENT "
        		   + "ORDER BY stuId";
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil�� query�� ����
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<StudentDTO> userList = new ArrayList<StudentDTO>();	// User���� ����Ʈ ����
			while (rs.next()) {
				StudentDTO user = new StudentDTO(			// User ��ü�� �����Ͽ� ���� ���� ������ ����
					rs.getString("stuId"),
					rs.getString("name"),
					rs.getString("password"),
					rs.getInt("grade"),
					rs.getString("dProject"),
					rs.getString("dTeamproject"),
					rs.getString("dCreditRate"),
					rs.getString("dAttendance"),
					rs.getString("dNumberOfExam"));
				userList.add(user);				// List�� User ��ü ����
			}		
			return userList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	/**
	 * ��ü ����� ������ �˻��� �� ���� �������� �������� ����� ����� ���� �̿��Ͽ�
	 * �ش��ϴ� ����� �������� List�� �����Ͽ� ��ȯ.
	 */
	public List<StudentDTO> findUserList(int currentPage, int countPerPage) throws SQLException {
        String sql = "SELECT stuId, password, name, grade, dProject, dTeamproject, dCreditRate, dAttendance, dNumberOfExam "
        		   + "FROM STUDENT "
        		   + "ORDER BY stuId";
		jdbcUtil.setSqlAndParameters(sql, null,					// JDBCUtil�� query�� ����
				ResultSet.TYPE_SCROLL_INSENSITIVE,				// cursor scroll ����
				ResultSet.CONCUR_READ_ONLY);						
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();				// query ����			
			int start = ((currentPage-1) * countPerPage) + 1;	// ����� ������ �� ��ȣ ���
			if ((start >= 0) && rs.absolute(start)) {			// Ŀ���� ���� ������ �̵�
				List<StudentDTO> userList = new ArrayList<StudentDTO>();	// User���� ����Ʈ ����
				do {
					StudentDTO user = new StudentDTO(		// User ��ü�� �����Ͽ� ���� ���� ������ ����
						rs.getString("stuId"),
						rs.getString("name"),
						rs.getString("password"),
						rs.getInt("grade"),
						rs.getString("dProject"),
						rs.getString("dTeamproject"),
						rs.getString("dCreditRate"),
						rs.getString("dAttendance"),
						rs.getString("dNumberOfExam"));
					userList.add(user);							// ����Ʈ�� User ��ü ����
				} while ((rs.next()) && (--countPerPage > 0));		
				return userList;							
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}

	/**
	 * �־��� ����� ID�� �ش��ϴ� ����ڰ� �����ϴ��� �˻� 
	 */
	public boolean existingUser(String stuId) throws SQLException {
		String sql = "SELECT count(*) FROM STUDENT WHERE stuId=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {stuId});	// JDBCUtil�� query���� �Ű� ���� ����

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return false;
	}
}
