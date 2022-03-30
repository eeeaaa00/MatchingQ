package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.StudentDTO;
import model.service.ExistingUserException;
import model.service.UserManager;

public class RegisterUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(RegisterUserController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		StudentDTO user = new StudentDTO(
			request.getParameter("stuId"),
			request.getParameter("stuName"),
			request.getParameter("password"),
			Integer.parseInt(request.getParameter("grade")),
			request.getParameter("dProject"),
			request.getParameter("dTeamProject"),
			request.getParameter("dCreditRate"),
			request.getParameter("dAttendance"),
			request.getParameter("dNumberOfExam"));

        log.debug("Create User : {}", user);

		try {
			UserManager manager = UserManager.getInstance();
			manager.create(user);
	        return "/review/list";		// ���� �� ����� ����Ʈ ȭ������ redirect
	        
		} catch (ExistingUserException e) {		// ���� �߻� �� ȸ������ form���� forwarding
            request.setAttribute("registerFailed", true);
			request.setAttribute("exception", e);
			request.setAttribute("user", user);
			return "/user/registerForm.jsp";
		}
    }
}
