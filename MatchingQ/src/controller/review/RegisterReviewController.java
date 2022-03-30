package controller.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.Review;
import model.service.ReviewManager;

public class RegisterReviewController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(RegisterReviewController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      	if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/login/form";		// login form ��û���� redirect
        }
    	
      	try {
      		System.out.println(request.getParameter("classId"));
      		int classId = Integer.valueOf(request.getParameter("classId"));
      	}catch(Exception e) {
      		System.out.print("classId�� ã�� �� �����ϴ�");
      		return "redirect:/review/list";	
      	}
    	Review review = new Review(
    			//���� ������ ������ �͵��̶� dto�� �־�� �� �Ķ���Ͱ� �ȸ���. �ܷ�Ű �̿� ��� ����. + �� ���� �ν��� null�� ��. trim()�� ���� ������ �־ ��.
    		0, //revid
    		Integer.valueOf(request.getParameter("semester")), //openSemester
			request.getParameter("lecturetotal"),
			request.getParameter("dproject"),
			request.getParameter("dteamproject"),
			request.getParameter("dcreditRate"),
			request.getParameter("dAttendance"),
			request.getParameter("dnumberOfExam"),
			request.getParameter("totalReview"),
			Integer.valueOf(request.getParameter("classId"))
			//9595
			);
		
        log.debug("Create Review : {}", review);

		try {
			ReviewManager manager = ReviewManager.getInstance();
			manager.create(review);
			manager.registerKeywordMatching(review);
			
	        return "redirect:/review/list";		// ���� �� ����� ����Ʈ ȭ������ redirect
	        
		} catch (Exception e) {		
			e.printStackTrace();
//            request.setAttribute("registerFailed", true);
//			request.setAttribute("exception", e);
//			request.setAttribute("review", review);
			return "/review/registerForm.jsp";
		}
      	//return "redirect:/review/list";	
    }
}
