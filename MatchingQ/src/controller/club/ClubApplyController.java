package controller.club;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import controller.Controller;
import controller.study.UpdateTutorController;
import controller.user.UserSessionUtils;
import model.service.ClubManager;
import model.service.TutorTuteeManager;
import model.service.UserManager;
import model.Club;
import model.Tutor;

public class ClubApplyController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(UpdateTutorController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String student_number = request.getParameter("student_number");
		String club_code = request.getParameter("club_code");

	if (request.getMethod().equals("GET")) {	
		// GET request: ȸ������ ���� form ��û	
		// ������ UpdateUserFormController�� ó���ϴ� �۾��� ���⼭ ����

		log.debug("UpdateForm Request : {}", student_number);
		
		//UserManager manager = UserManager.getInstance();
		ClubManager manager = ClubManager.getInstance();
		Club club = manager.findClub(club_code);	// �����Ϸ��� ����� ���� �˻�
		request.setAttribute("club", club);			

		HttpSession session = request.getSession();
		String preId = UserSessionUtils.getLoginUserId(session);
		if (UserSessionUtils.isLoginUser(preId, session) ||
			UserSessionUtils.isLoginUser("admin", session)) {
			// ���� �α����� ����ڰ� ���� ��� ������̰ų� �������� ��� -> ���� ����
			manager.updateMatching(student_number);
			request.setAttribute("student_number", student_number);
			return "/club/applyList";   // �˻��� ����� ������ update form���� ����     
		}    
		
		// else (���� �Ұ����� ���) ����� ���� ȭ������ ���� �޼����� ����
		request.setAttribute("updateFailed", true);
		request.setAttribute("exception", 
				new IllegalStateException("���Ƹ� ������ �����ڿ� ���常 �����մϴ�."));            
		return "/club/applyList.jsp";	// ����� ���� ȭ������ �̵� (forwarding)
    }	
	// POST request (ȸ�������� parameter�� ���۵�)
	Club club = new Club(
			request.getParameter("club_code"),
    		request.getParameter("club_name"),
    		request.getParameter("president_code"), request.getParameter("president_phone"),
    		request.getParameter("professor_code"), request.getParameter("professor_phone"),
    		request.getParameter("club_place"), request.getParameter("club_time"),
    		request.getParameter("activity"), request.getParameter("purpose"),
    		request.getParameter("category"),  request.getParameter("description")); // �����Ϸ��� Ŀ�´�Ƽ ���� �˻�
	request.setAttribute("club", club);
	
	log.debug("Update Club : {}", club);
	
	ClubManager manager = ClubManager.getInstance();
	manager.updateMatching(student_number);			
    return "redirect:/club/view";
}

}
