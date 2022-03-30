package model.service;

import model.dao.ReviewDAO;
import model.dao.TeamProjectDAO;
import model.*;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewAnalysis {
	private static ReviewDAO dao;
	static List<Keyword> keywordList;
	public ReviewAnalysis() {}

	public ReviewAnalysis(ReviewDAO dao) throws SQLException {
		super();
		this.dao = dao;
		
	}

	
	
	//����Ͻ� �޼ҵ�. ��õ �˰���. 
	//static �Ϸ��� �ߴ�
	public static int registerKeywordMatching(Review review) throws Exception{
		//��Ī �˰���
		keywordList = dao.findKeywordList(); //keyword���� ��ϵǾ� �ִ� keyword����Ʈ�� �ҷ��´�.
		for(Keyword key : keywordList) //keyword����Ʈ���� keyword�� �ϳ��� �ҷ��´�.
			for(String keyword : key.getValue()) { //keyword�� value�κ�(ex. "[����,������,������..]")�� ����Ʈ���� �ϳ��� �̴´�. �̸� split �صξ���.
				//System.out.println("registerKeywordMatching ���� Ű����: " + keyword); //���� ����, ������, ������.. ����� keyword
				List<KeywordMatching> matching = dao.findMatchingByReview(review, key.getKeyId(), keyword);
				if(matching != null)
					for(KeywordMatching value : matching) {
						System.out.println("���� ���� ����: " + keyword);
						System.out.println("registerKeywordMatching: " +  value.getKeyId());
						System.out.println("registerKeywordMatching: " +  value.getProfId());
						System.out.println("registerKeywordMatching: " +  value.getCourseId());
						
						if(dao.findMatchingCount(value.getKeyId(), value.getProfId(), value.getCourseId()) == 0) {
							System.out.println("create�Ϸ� ��");
							dao.createKeywordMatching(value.getKeyId(), value.getProfId(), value.getCourseId());
						}
						else {
							System.out.println("update�Ϸ� ��");
							value = dao.findKeywordMatching(value.getKeyId(), value.getProfId(), value.getCourseId()); //db�� count �����ֱ�
							dao.updateKeywordMatching(value.getKeyId(), value.getProfId(), value.getCourseId(), value.getCount());
							
						}
					}
			}
		//���߿� class ���� ��ոŰܼ� �ִ� �˰��� �ʿ��� ��
		return 0;
	}
	public static ProfDTO recommendProfs(int keyId) throws Exception{
		//List<ProfDTO> profs= new ArrayList<ProfDTO>();
		List<KeywordMatching> keywordList = dao.findKeywordMatching();
		int max = 0;
		int profId = 0;
		String profName = "";
		
		System.out.println("����� ��õ�� ����");
		for(KeywordMatching key : keywordList) {
			if(key.getKeyId() == keyId)
				if(key.getCount() > max) {
					max = key.getCount();
					profId = key.getProfId();
				}		
		}
		System.out.println(profId);
		profName = dao.findProfName(profId);
		System.out.println(profName);
		ProfDTO prof = new ProfDTO(String.valueOf(profId), profName);
		//���߿� class ���� ��ոŰܼ� �ִ� �˰��� �ʿ��� ��
		return prof;
	}
	
	public static List<Review> recommendReviews(StudentDTO user) throws Exception{
		//�ش�Ǵ� ���並 ��õ��
		List<Review> reviewList = dao.findReviewList();
		List<Review> resultList = new ArrayList<Review>();
		
		System.out.println("����� ������ ��õ�� ����");
		//��Ī �˰���
		for(Review review : reviewList) {
			int same = 0;
			
			if(review.getProject().equals(user.getdProject()))
				same++;
			if(review.getTeamProject().equals(user.getdTeamProject()))
				same++;
			if(review.getNumberOfExam().equals(user.getdNumberOfExam()))
				same++;
			if(review.getCreditRate().equals(user.getdCreditRate()))
				same++;
			if(review.getAttendance().equals(user.getdAttendance()))
				same++;
			
			if(same >= 4) {
				resultList.add(review);
				System.out.println(review.toString());
			}
			
		}

		return resultList;
	}
	

}
