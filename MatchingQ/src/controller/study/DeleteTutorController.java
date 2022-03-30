package controller.study;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.user.DeleteUserController;
import controller.user.UserSessionUtils;
import model.StudentDTO;
import model.Tutor;
import model.service.TutorTuteeManager;
import model.service.UserManager;

public class DeleteTutorController implements Controller  {

	private static final Logger log = LoggerFactory.getLogger(DeleteTutorController.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String deleteId = request.getParameter("student_number");
		System.out.println("������ ���̵�: " + deleteId);
    	log.debug("Delete User : {}", deleteId);

    	TutorTuteeManager manager = TutorTuteeManager.getInstance();		
		HttpSession session = request.getSession();	
	
		if ((UserSessionUtils.isLoginUser("admin", session) && 	// �α����� ����ڰ� �������̰� 	
			 !deleteId.equals("admin"))							// ���� ����� �Ϲ� ������� ���, 
			   || 												// �Ǵ� 
			(!UserSessionUtils.isLoginUser("admin", session) &&  // �α����� ����ڰ� �����ڰ� �ƴϰ� 
			  UserSessionUtils.isLoginUser(deleteId, session))) { // �α����� ����ڰ� ���� ����� ��� (�ڱ� �ڽ��� ����)
				
			manager.removeTutor(deleteId);	// �α����� ����ڰ� ������ 	
			return "redirect:/study/list";		// ����� ����Ʈ�� �̵�								// �α����� ����ڴ� �̹� ������
			
		}
		
		request.setAttribute("updateFailed", true);
		request.setAttribute("exception", 
				new IllegalStateException("Ʃ�� ������ ���ΰ� ������ �ܿ� ������ �� �����ϴ�."));            
		return "/study/view";
	}

}
