package model.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;
import oracle.net.aso.r;

/**
 * ����� ������ ���� �����ͺ��̽� �۾��� �����ϴ� DAO Ŭ����
 * Community ���̺��� Ŀ�´�Ƽ ������ �߰�, ����, ����, �˻� ���� 
 */
public class ReviewDAO extends TeamProjectDAO{
	private JDBCUtil jdbcUtil = null;
	
	public ReviewDAO() {
		jdbcUtil = new JDBCUtil();
	}

	public void setClassName(Review review, int classid) {
		String sql = "SELECT title FROM course WHERE courseid = (SELECT courseid FROM class WHERE classid = " + classid + ")";
		//System.out.println("setClassName ����: " + sql);
		
		JDBCUtil jdbcUtil2 = new JDBCUtil();
		jdbcUtil2.setSqlAndParameters(sql, null);
		
		try {
			
			ResultSet rs2 = jdbcUtil2.executeQuery();	// query ����
			
			while (rs2.next()) {
				String title = rs2.getString("title");
				review.setClassName(title);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
	//SQLException - �������� �� �̸� / �� �ε���
	public int create(Review review) throws SQLException {
		String sql = "INSERT INTO REVIEW (revid, opensemester, lecturetotal, project, teamproject, creditrate, attendance, numberofexam, lecturereview, classid) " 
					+ "VALUES(revid_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] param = new Object[] {review.getOpenSemester(), review.getLectureTotal(), review.getProject(),
				review.getTeamProject(), review.getCreditRate(), review.getAttendance(), 
				review.getNumberOfExam(), review.getLectureReview(), review.getClassId()};
		
		jdbcUtil.setSqlAndParameters(sql, param);
//		String sql = "INSERT INTO REVIEW ()VALUES(revid_seq.nextval," + review.getOpenSemester() + "," +  review.getLectureTotal() + "," 
//						+  review.getTeamProject() + "," +  review.getCreditRate() + "," +
//						review.getAttendance()+ "," + review.getNumberOfExam()+ "," +
//						review.getLectureReview() + "," + review.getClassId() + ")";
//		
//		System.out.println(sql);
//		jdbcUtil.setSqlAndParameters(sql, null);
		
		try {
			int result = jdbcUtil.executeUpdate();
			return result;
		} catch(Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		
		}
		
		return 0;
	}
	
	//���� ���� ���� �� �ش� �������
	public Review findReview(int rid) throws SQLException {
		String sql = "SELECT * FROM REVIEW WHERE revid = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {rid});
		Review rev = null;
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {						
				rev = new Review(
						rid,
					rs.getInt("opensemester"),
					rs.getString("lecturetotal"),
					rs.getString("project"),
					rs.getString("teamproject"),
					rs.getString("creditrate"),
					rs.getString("attendance"),
					rs.getString("numberofexam"),
					rs.getString("lecturereview"),
					rs.getInt("classid"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return rev;
	}
	
	//���丮��Ʈ
		public List<Review> findReviewList() throws SQLException {
			String sql = "SELECT * FROM REVIEW ORDER BY revid DESC";
			jdbcUtil.setSqlAndParameters(sql, null);
			Review rev = null;
			
			try {
				ResultSet rs = jdbcUtil.executeQuery();	// query ����
				List<Review> reviewList = new ArrayList<Review>();
				
				while (rs.next()) {						
					rev = new Review(
						rs.getInt("revid"),
						rs.getInt("opensemester"),
						rs.getString("lecturetotal"),
						rs.getString("project"),
						rs.getString("teamproject"),
						rs.getString("creditrate"),
						rs.getString("attendance"),
						rs.getString("numberofexam"),
						rs.getString("lecturereview"),
						rs.getInt("classid"));
					setClassName(rev, rs.getInt("classid"));
					rev.setClassDto(findClass(rs.getInt("classid")));
					//System.out.println("�� ��� ���ߴ�" + rev.getClassDto().getCourseTitle());
					reviewList.add(rev);
					
				}
				return reviewList;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jdbcUtil.close();		// resource ��ȯ
			}
			return null;
		}
	public List<Review> findReviewListById(String revid) throws SQLException { // �ڵ屸�� ���ǻ� list�� ����.
			String sql =  "SELECT * FROM REVIEW WHERE revid = " + revid;
			jdbcUtil.setSqlAndParameters(sql, null);
			Review rev = null;
			System.out.println(sql);
			try {
				ResultSet rs = jdbcUtil.executeQuery();	// query ����
				List<Review> reviewList = new ArrayList<Review>();
				
				while (rs.next()) {						
					rev = new Review(
						rs.getInt("revid"),
						rs.getInt("opensemester"),
						rs.getString("lecturetotal"),
						rs.getString("project"),
						rs.getString("teamproject"),
						rs.getString("creditrate"),
						rs.getString("attendance"),
						rs.getString("numberofexam"),
						rs.getString("lecturereview"),
						rs.getInt("classid"));
					setClassName(rev, rs.getInt("classid"));
					reviewList.add(rev);
				}
				return reviewList;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jdbcUtil.close();		// resource ��ȯ
			}
			return null;
		}
	
	/**
	 * �־��� ������ �ش��ϴ� ��ü ���� ������ �����ͺ��̽����� ã�� ClassDTO ������ Ŭ������ 
	 * �����Ͽ� ����Ʈ�� ��ȯ.
	 */
	public List<ClassDTO> SearchClassListForProf(String profName) throws SQLException {
        String sql = "SELECT * " 
        		   + "FROM class "
        		   + "WHERE profid in (SELECT profid FROM professor WHERE name LIKE '%" + profName + "%') ";
//        		   + "GROUP BY courseid, profid";
        jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil�� query�� ����
        System.out.println("prof����: " + sql);
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<ClassDTO> SearchClassList = new ArrayList<ClassDTO>();	// ClassDTO���� ����Ʈ ����
			while (rs.next()) {
				ClassDTO c = new ClassDTO(	// ClassDTO ��ü�� �����Ͽ� class ������ ����
						rs.getInt("classId"),
						findCourseTitle(rs.getInt("courseId")),
						rs.getInt("year"),
						rs.getInt("semester"),
						rs.getInt("division"),
						findProfName(rs.getInt("profid")));	
				
				SearchClassList.add(c);				// List�� ClassDTO ��ü ����
			}		
			return SearchClassList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	public List<Review> SearchReviewListForName(String className) throws SQLException {
		//�׳� �˻�Ű����� profID FIND�غ��� ������ 0�̰� SQL������ or profid='%className%'�ؼ� �˻� ���Ĺ�����
        String sql = "SELECT * " 
        		   + "FROM review "
        		   + "WHERE classid in (SELECT classid FROM course join class on course.courseid = class.courseid WHERE title LIKE '%" +className +"%')";
        System.out.println("����: " + sql);
        //Object[] obj = new Object[] {className};
		jdbcUtil.setSqlAndParameters(sql, null);
		
//		String sql = "SELECT * FROM REVIEW";
//		jdbcUtil.setSqlAndParameters(sql, null);
		Review rev = null;
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();	// query ����
			List<Review> reviewList = new ArrayList<Review>();
			while (rs.next()) {						
				rev = new Review(
					rs.getInt("revid"),
					rs.getInt("opensemester"),
					rs.getString("lecturetotal"),
					rs.getString("project"),
					rs.getString("teamproject"),
					rs.getString("creditrate"),
					rs.getString("attendance"),
					rs.getString("numberofexam"),
					rs.getString("lecturereview"),
					rs.getInt("classid"));
				setClassName(rev, rs.getInt("classid"));
				reviewList.add(rev);
			}
			return reviewList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
public List<ClassDTO> SearchClassListForName(String className) throws SQLException {
        String sql = "SELECT * " 
        		   + "FROM class "
        		   + "WHERE courseid in (SELECT courseid FROM course WHERE title LIKE '%" +className +"%')";
        System.out.println("cname����: " + sql);

		jdbcUtil.setSqlAndParameters(sql, null);
		
		ClassDTO rev = null;
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();	// query ����
			List<ClassDTO> classList = new ArrayList<ClassDTO>();
			//System.out.println(rs);
			while (rs.next()) {						
				rev = new ClassDTO(	// ClassDTO ��ü�� �����Ͽ� class ������ ����
						rs.getInt("classid"),
						findCourseTitle(rs.getInt("courseid")),
						rs.getInt("year"),
						rs.getInt("semester"),
						rs.getInt("division"),
						findProfName(rs.getInt("profid"))
						);	
				classList.add(rev);
			}
			return classList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//jdbcUtil.close();		// resource ��ȯ
		}		
		return null;
	}
	
	//�Է��� matchingkeyword�� ����Ʈ�� ã�ƿ�
	public List<Keyword> findKeywordList() throws SQLException {
		String sql = "SELECT * " + 
				"FROM keyword";
		jdbcUtil.setSqlAndParameters(sql, null);

		
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<Keyword> keyList = new ArrayList<Keyword>();	// Community���� ����Ʈ ����
			while (rs.next()) {	
				Keyword c = new Keyword(	// ClassDTO ��ü�� �����Ͽ� class ������ ����
						rs.getInt("keyId"),
						rs.getString("item"),
						rs.getString("value").split(","));	
				
				keyList.add(c);				
			}
			return keyList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	public KeywordMatching findKeywordMatching(int keyId, int profId, int courseId) throws SQLException {
		String sql = "SELECT * FROM KEYWORDMATCHING WHERE keyId=? and profId=? and courseId=?";
		Object[] param = new Object[] {keyId, profId, courseId};
		jdbcUtil.setSqlAndParameters(sql, param);

		
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			while (rs.next()) {	
				KeywordMatching mat = new KeywordMatching(	
						rs.getInt("keyId"),
						rs.getInt("profId"),
						rs.getInt("courseId"),
						rs.getInt("count"));	
				return mat;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	public List<KeywordMatching> findKeywordMatching() throws SQLException {
		String sql = "SELECT * FROM KEYWORDMATCHING";
		jdbcUtil.setSqlAndParameters(sql, null);

		
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<KeywordMatching> matchingList = new ArrayList<KeywordMatching>();
			while (rs.next()) {	
				KeywordMatching mat = new KeywordMatching(	
						rs.getInt("keyId"),
						rs.getInt("profId"),
						rs.getInt("courseId"),
						rs.getInt("count"));	
				matchingList.add(mat);
			}
			return matchingList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	//�Է��� matchingkeyword�� ����Ʈ�� ã�ƿ�
	public List<KeywordMatching> findMatchingByReview(Review rev, int keyId, String keyword) throws SQLException {
		List<KeywordMatching> keyList = null;
		jdbcUtil = new JDBCUtil();
		String sql = "SELECT c.courseid, c.profid "
				+ "FROM review r join class c on r.classid = c.classid "
				+ "WHERE lecturereview LIKE '%" + keyword + "%'" + " and lecturereview = '" + rev.getLectureReview() + "'";
		//System.out.println("findMatching�� ����: " + sql);
		jdbcUtil.setSqlAndParameters(sql, null);

		
		try {
			ResultSet rs = jdbcUtil.executeQuery();			
			keyList = new ArrayList<KeywordMatching>();	
			while (rs.next()) {	
				KeywordMatching c = new KeywordMatching(	
						keyId,
						rs.getInt("profid"),
						rs.getInt("courseid"));	
				//System.out.println("Ű����: " + c);
				keyList.add(c);				
			}
			//System.out.println(keyList);
			return keyList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return keyList;
	}
	
	public int createKeywordMatching(int keyId, int profId, int courseId) throws SQLException {
		String sql = "INSERT INTO KEYWORDMATCHING VALUES(revid_seq.nextval, ?, ?, ?, ?)";
		System.out.println("CreaeteKeywordMatching: " +  keyId);
		System.out.println("CreaeteKeywordMatching: " +  profId);
		System.out.println("CreaeteKeywordMatching: " +  courseId);
		Object[] param = new Object[] {keyId, profId, courseId, 1};
		
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {
			int result = jdbcUtil.executeUpdate();
			return result;
		} catch(Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		
		}
		return 0;
	}
	
	public int updateKeywordMatching(int keyId, int profId, int courseId, int count) throws SQLException {
		int cnt = count + 1;
		System.out.printf("ī��Ʈ ������Ʈ�ϸ�  %d", cnt);
		String sql = "UPDATE KEYWORDMATCHING SET count=? WHERE keyId=? and profId=? and courseId=?";
		Object[] param = new Object[] {cnt, keyId, profId, courseId};
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {
			int result = jdbcUtil.executeUpdate();
			return result;
		} catch(Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		
		}
		return 0;
	}
	
	public int findMatchingCount(int keyId, int profId, int courseId) throws SQLException {
		  int count = 0;
	      String sql = "SELECT count "
	      			+ "FROM keywordMatching "
	      			+ "WHERE profId=? and keyId=? and courseId=?";              
			jdbcUtil.setSqlAndParameters(sql, new Object[] {profId, keyId, courseId});	// JDBCUtil�� query���� �Ű� ���� ����
			
			try {
				ResultSet rs = jdbcUtil.executeQuery();		// query ����
				if (rs.next()) {						// professor ���� �߰�
					count = rs.getInt("count");	// �������� ����
					System.out.println("ī��Ʈ �Ϸ� ���� ����: " + count);
					
				}
				return count;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jdbcUtil.close();		// resource ��ȯ
			}
			return 0;
	}
}
