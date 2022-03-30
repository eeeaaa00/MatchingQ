package model.service;

import java.sql.SQLException;
import java.util.List;

import model.StudentDTO;
import model.dao.StudentDAO;

/**
 * ����� ���� API�� ����ϴ� �����ڵ��� ���� �����ϰ� �Ǵ� Ŭ����.
 * UserDAO�� �̿��Ͽ� �����ͺ��̽��� ������ ���� �۾��� �����ϵ��� �ϸ�,
 * �����ͺ��̽��� �����͵��� �̿��Ͽ� �����Ͻ� ������ �����ϴ� ������ �Ѵ�.
 * �����Ͻ� ������ ������ ��쿡�� �����Ͻ� �������� �����ϴ� Ŭ������ 
 * ������ �� �� �ִ�.
 */
public class UserManager {
	private static UserManager userMan = new UserManager();
	private StudentDAO userDAO;

	private UserManager() {
		try {
			userDAO = new StudentDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static UserManager getInstance() {
		return userMan;
	}
	
	public int create(StudentDTO user) throws SQLException, ExistingUserException {
		if (userDAO.existingUser(user.getStuId()) == true) {
			throw new ExistingUserException(user.getStuId() + "�� �����ϴ� ���̵��Դϴ�.");
		}
		return userDAO.create(user);
	}

	public int update(StudentDTO user) throws SQLException {
		return userDAO.update(user);
	}	

	public int remove(String userId) throws SQLException {
		return userDAO.remove(userId);
	}

	public StudentDTO findUser(String userId)
		throws SQLException, UserNotFoundException {
		StudentDTO user = userDAO.findUser(userId);
		if (user == null) {
			throw new UserNotFoundException(userId + "�� �������� �ʴ� ���̵��Դϴ�.");
		}
		return user;
	}

	public List<StudentDTO> findUserList() throws SQLException {
			return userDAO.findUserList();
	}
	
	public List<StudentDTO> findUserList(int currentPage, int countPerPage)
		throws SQLException {
		return userDAO.findUserList(currentPage, countPerPage);
	}

	public boolean login(String userId, String password)
		throws SQLException, UserNotFoundException, PasswordMismatchException {
		StudentDTO user = findUser(userId);

		if (!user.matchStuPassword(password)) {
			throw new PasswordMismatchException("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		}
		return true;
	}
	
	public StudentDAO getUserDAO() {
		return this.userDAO;
	}
}
