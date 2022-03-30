package impl;

import java.util.*;
import model.*;

public interface TutorDAOImpl {
	public List<Tutor> getTutorList(String subject);						// ��ü Ʃ�� ������ ȹ��
	public int insertTutor(Tutor tutor);					// Ʃ�� ���� �߰�
	public int updateTutor(Tutor tutor);					// Ʃ�� ���� ����
	public int deleteTutor(String student_number);				// Ʃ�� ���� ����	
	public Tutor getTutorByCode(String student_number);			// Ʃ�� ������ ���Ƹ��ڵ�� ã��

}
