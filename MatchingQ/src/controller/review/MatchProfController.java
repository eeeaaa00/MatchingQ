package controller.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.ProfDTO;
import model.Review;
import model.StudentDTO;
import model.service.ReviewManager;
import model.service.UserManager;

public class MatchProfController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(MatchProfController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if (request.getMethod().equals("GET")) {	
    		// GET request: ȸ������ ���� form ��û	
    		// ������ UpdateUserFormController�� ó���ϴ� �۾��� ���⼭ ����
    		String updateId = UserSessionUtils.getLoginUserId(request.getSession());

    		log.debug("UpdateForm Request : {}", updateId);
    		
    		UserManager manager = UserManager.getInstance();
			StudentDTO user = manager.findUser(updateId);	// �����Ϸ��� ����� ���� �˻�
			request.setAttribute("user", user);
			request.setAttribute("curUserId", UserSessionUtils.getLoginUserId(request.getSession()));

			HttpSession session = request.getSession();
			if (UserSessionUtils.isLoginUser(updateId, session) ||
				UserSessionUtils.isLoginUser("admin", session)) {
				// ���� �α����� ����ڰ� ���� ��� ������̰ų� �������� ��� -> ���� ����
				
				return "/review/matchingForm.jsp";   // �˻��� ����� ������ update form���� ����     
			}    
			
			// else (���� �Ұ����� ���) ����� ���� ȭ������ ���� �޼����� ����
			request.setAttribute("updateFailed", true);
			request.setAttribute("exception", 
					new IllegalStateException("Ÿ���� ������ ������ �� �����ϴ�."));            
			return "/review/list.jsp";	// ����� ���� ȭ������ �̵� (forwarding)
	    }	
    	// POST request (ȸ�������� parameter�� ���۵�)
    	StudentDTO updateUser = new StudentDTO(
    			request.getParameter("stuId"),
    			request.getParameter("stuName"),
    			request.getParameter("password"),
    			Integer.parseInt(request.getParameter("grade")),
    			request.getParameter("dProject"),
    			request.getParameter("dTeamProject"),
    			request.getParameter("dCreditRate"),
    			request.getParameter("dAttendance"),
    			request.getParameter("dNumberOfExam"));
    	
    	log.debug("Update User : {}", updateUser);
    	request.setAttribute("keyId", request.getParameter("dKeyword"));
    	request.setAttribute("curUserId", UserSessionUtils.getLoginUserId(request.getSession()));
		UserManager manager = UserManager.getInstance();
		manager.update(updateUser);			
		ReviewManager revMan = ReviewManager.getInstance();
		
		
		List<Review> recommendedReview = revMan.getReviewMatching(updateUser);
		ProfDTO recommendedProf = revMan.getProfMatching(Integer.valueOf(request.getParameter("dKeyword")));
		//System.out.println(recommendedReview.toString());
		request.setAttribute("reviewList", recommendedReview);
		request.setAttribute("prof", recommendedProf);
		
		return "/review/matchedlist.jsp";
    }
}
