package controller.study;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import controller.Controller;
import controller.user.UserSessionUtils;
import model.service.TutorTuteeManager;
import model.service.UserManager;
import model.Tutor;
import model.StudentDTO;


public class UpdateTutorController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(UpdateTutorController.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String curUserId = request.getParameter("curUserId");
		request.setAttribute("curUserId", curUserId);
		if (request.getMethod().equals("GET")) {	
    		// GET request: ȸ������ ���� form ��û	
    		// ������ UpdateUserFormController�� ó���ϴ� �۾��� ���⼭ ����
    		String updateId = request.getParameter("stuId");

    		log.debug("UpdateForm Request : {}", updateId);
    		
    		TutorTuteeManager manager = TutorTuteeManager.getInstance();
			Tutor tutor = manager.findTutor(updateId);	// �����Ϸ��� ����� ���� �˻�
			request.setAttribute("tutor", tutor);			

			HttpSession session = request.getSession();
			if (UserSessionUtils.isLoginUser(updateId, session) ||
				UserSessionUtils.isLoginUser("admin", session)) {
				// ���� �α����� ����ڰ� ���� ��� ������̰ų� �������� ��� -> ���� ����
				
				return "/study/tutorUpdateForm.jsp";   // �˻��� ����� ������ update form���� ����     
			}    
			
			// else (���� �Ұ����� ���) ����� ���� ȭ������ ���� �޼����� ����
			request.setAttribute("updateFailed", true);
			request.setAttribute("exception", 
					new IllegalStateException("Ÿ���� ������ ������ �� �����ϴ�."));            
			return "/study/view.jsp";	// ����� ���� ȭ������ �̵� (forwarding)
	    }	
    	// POST request (ȸ�������� parameter�� ���۵�)
    	Tutor updateUser = new Tutor(
    			request.getParameter("stuId"),
    			request.getParameter("subject"),
    			request.getParameter("grade"),
    			request.getParameter("year"));
    	
    	log.debug("Update User : {}", updateUser);

    	TutorTuteeManager manager = TutorTuteeManager.getInstance();
		manager.updateTutor(updateUser);			
        return "redirect:/study/list";
	}

}
