package controller.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.ClassDTO;
import model.Review;
import model.service.ReviewManager;
import model.service.TeamProjectrManager;

public class ListViewController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/login/form";		// login form ��û���� redirect
        }
    	
    	System.out.println("���� ListViewController : execute");
    	ReviewManager manager = ReviewManager.getInstance();
		TeamProjectrManager tManager = new TeamProjectrManager();  //@�̰� ������ teamProjectmanager������ public���� �ٲ���
		
		//������ ������ ������ ��Ÿ���� ���� classDto ������.
    	int classId = Integer.valueOf(request.getParameter("classId"));
    	ClassDTO choosedClass = (ClassDTO)tManager.findClass(classId);
    	
    	//������ �����̸��� ���� �������� ��Ÿ���� ���� ���������� ������ �˻�
    	String courseTitle = request.getParameter("courseTitle");
    	List<Review> reviewList = manager.SearchReviewListForName(courseTitle);
    	
    	
    	request.setAttribute("curUserId", 
    			UserSessionUtils.getLoginUserId(request.getSession()));	
		request.setAttribute("choosedClass", choosedClass);	
		request.setAttribute("reviewList", reviewList);
		//System.out.println(choosedClass);
		// ����� ����Ʈ ȭ������ �̵�(forwarding)
		return "/review/view.jsp";        
	}

}
