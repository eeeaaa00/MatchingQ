package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.UserManager;
import model.service.UserNotFoundException;
import model.StudentDTO;

public class ViewUserController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {	
    	// �α��� ���� Ȯ��
    	if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/login/form";		// login form ��û���� redirect
        }
    	
		UserManager manager = UserManager.getInstance();
		String stuId = request.getParameter("stuId");
		
    	StudentDTO user = null;
		try {
			user = manager.findUser(stuId);	// ����� ���� �˻�
		} catch (UserNotFoundException e) {				
	        return "redirect:/review/list";
		}	
		
		request.setAttribute("user", user);		// ����� ���� ����				
		return "/user/view.jsp";				// ����� ���� ȭ������ �̵�
    }
}
