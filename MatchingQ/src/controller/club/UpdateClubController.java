package controller.club;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import controller.Controller;
import controller.user.UserSessionUtils;
import model.service.ClubManager;
import model.Club;

public class UpdateClubController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateClubController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
			
			if (request.getMethod().equals("GET")) {
				
				String cCode =request.getParameter("club_code");
				String preId = request.getParameter("president_code");
				String curUserId = request.getParameter("stuId");
				
				ClubManager manager = ClubManager.getInstance();
				Club club = manager.findClub(cCode);
				request.setAttribute("club", club);
				request.setAttribute("curUserId", curUserId);
				
				HttpSession session = request.getSession();

				
				if ((UserSessionUtils.isLoginUser("admin", session)) 	// �α����� ����ڰ� ������	
						   || 												// �Ǵ� 
						((!UserSessionUtils.isLoginUser("admin", session) &&  // �α����� ����ڰ� �����ڰ� �ƴϰ� 
						  (UserSessionUtils.isLoginUser(preId, session))))) 
				{
					return "/club/updateForm.jsp";
				} 
					
					request.setAttribute("updateFailed", true);
					request.setAttribute("exception", 
							new IllegalStateException("���Ƹ��� ����� ������ �ܿ� ������ �� �����ϴ�."));            
					return "/club/view.jsp";
			}
	    		// GET request: Ŀ�´�Ƽ ���� form ��û	
	    		
	    		Club club = new Club(
	    				request.getParameter("club_code"),
	    	    		request.getParameter("club_name"),
	    	    		request.getParameter("president_code"), request.getParameter("president_phone"),
	    	    		request.getParameter("professor_code"), request.getParameter("professor_phone"),
	    	    		request.getParameter("club_place"), request.getParameter("club_time"),
	    	    		request.getParameter("activity"), request.getParameter("purpose"),
	    	    		request.getParameter("category"),  request.getParameter("description")); // �����Ϸ��� Ŀ�´�Ƽ ���� �˻�
				request.setAttribute("club", club);	
				
				ClubManager manager = new ClubManager();
				manager.updateClub(club);
				return "redirect:/club/list";   // �˻��� ������ update form���� ����     		
    }
}
