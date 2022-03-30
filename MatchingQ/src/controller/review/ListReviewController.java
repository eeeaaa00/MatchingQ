package controller.review;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import controller.Controller;
import controller.user.UserSessionUtils;
import model.*;
import model.service.ReviewManager;
import model.service.UserManager;
import model.service.UserNotFoundException;

public class ListReviewController implements Controller {
	// private static final int countPerPage = 100;	// �� ȭ�鿡 ����� ����� ��
	private static final Logger log = (Logger) LoggerFactory.getLogger(ListReviewController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
    	if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/login/form";		// login form ��û���� redirect
        }
    	
    	log.debug("���� ListReviewController : execute");
    	UserManager umanager = UserManager.getInstance();
    	ReviewManager manager = ReviewManager.getInstance();
		List<Review> reviewList = manager.findReviewList();
		UserManager usermanager = UserManager.getInstance();
		List<StudentDTO> userList = usermanager.findUserList();
		
		String stuId = request.getParameter("stuId");
		
    	StudentDTO user = null;
		try {
			user = umanager.findUser(stuId);	// ����� ���� �˻�
		} catch (UserNotFoundException e) {				
	        //return "redirect:/review/list";
		}	
		request.setAttribute("user", user);	
		// List<User> userList = manager.findUserList(currentPage, countPerPage);

		//�ֱ� ������ ��� ����.
		request.setAttribute("reviewList", reviewList);				
		//System.out.println(reviewList);
		
		//�α���
		request.setAttribute("userList", userList);	
		request.setAttribute("curUserId", 
		UserSessionUtils.getLoginUserId(request.getSession()));	
		// ����� ����Ʈ ȭ������ �̵�(forwarding)
		return "/review/list.jsp";        
    }
}
