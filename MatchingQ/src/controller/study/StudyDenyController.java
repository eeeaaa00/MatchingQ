package controller.study;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.Tutor;
import model.service.TutorTuteeManager;

public class StudyDenyController implements Controller {

	private static final Logger log = LoggerFactory.getLogger(UpdateTutorController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String tutorId = request.getParameter("student_number");
		String tuteeId = request.getParameter("tuteeId");
		if (request.getMethod().equals("GET")) {	
    		// GET request: ȸ������ ���� form ��û	
    		// ������ UpdateUserFormController�� ó���ϴ� �۾��� ���⼭ ����

    		log.debug("UpdateForm Request : {}", tutorId);
    		
    		TutorTuteeManager manager = TutorTuteeManager.getInstance();
    		
			Tutor tutor = manager.findTutor(tutorId);	// �����Ϸ��� ����� ���� �˻�
			request.setAttribute("tutor", tutor);			

			HttpSession session = request.getSession();
			if (UserSessionUtils.isLoginUser(tutorId, session) ||
				UserSessionUtils.isLoginUser("admin", session)) {
				// ���� �α����� ����ڰ� ���� ��� ������̰ų� �������� ��� -> ���� ����
				manager.deleteMatching(tuteeId);
				return "/study/view";   // �˻��� ����� ������ update form���� ����     
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
