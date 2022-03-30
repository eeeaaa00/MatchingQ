package impl;

import java.util.*;
import model.*;

public interface TuteeDAOImpl {
	public List<Tutee> getTuteeList(String project_code);						// ��ü ƩƼ ������ ȹ��
	public int insertTutee(Tutee tutee);					// ƩƼ ���� �߰�
	public int updateTutee(Tutee tutee);					// ƩƼ ���� ����
	public int deleteTutee(String student_number);				// ƩƼ ���� ����	
	public Tutee getTuteeByCode(String student_number);		// ƩƼ ������ ���Ƹ��ڵ�� ã��
	List<Tutee> getTuteeList();
}
