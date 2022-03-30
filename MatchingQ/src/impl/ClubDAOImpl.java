package impl;

import java.util.*;
import model.*;

public interface ClubDAOImpl {
	
	public List<Club> getClubList();				// ��ü ���Ƹ� ������ ȹ��
	public int insertClub(Club club);			// ���Ƹ� ���� �߰�
	public int updateClub(Club club);			// ���Ƹ� ���� ����
	public int deleteClub(String club_code);				// ���Ƹ� ���� ����
	public Club getClubByName(String club_name);		// ���Ƹ� ������ ���Ƹ������� ã��
	public Club getClubByCode(String club_code);		// ���Ƹ� ������ ���Ƹ��ڵ�� ã��
	
}
