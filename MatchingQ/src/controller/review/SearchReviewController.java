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

public class SearchReviewController implements Controller {
	// private static final int countPerPage = 100;	// �� ȭ�鿡 ����� ����� ��
	private static final Logger log = (Logger) LoggerFactory.getLogger(SearchReviewController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
    	if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/user/login/form";		// login form ��û���� redirect
        }

    	//�˻� Ű���带 �޾ƿ�. ������ / ������
    	String keyword = request.getParameter("searchKeyword");
    	if(keyword.equals("")) keyword = "";
    	log.debug("���� SearchReviewController : execute, Ű���� : " + keyword);
    	
    	ReviewManager manager = ReviewManager.getInstance();
    	List<ClassDTO> classList = manager.SearchClassListForName(keyword);
    	
    	
    	//���������� ���� �˻��ϴ� ��. �� ����Ʈ�� ��ġ�� �ʰ� ��� ���ľ� �� �� �𸣰���. contain ����
//		List<ClassDTO> profClassList = manager.SearchClassListForProf(keyword);
//		for (ClassDTO c : profClassList) {
//			if (!classList.contains(c)) {
//	        	classList.add(c);
//	        }
//		}
//		classList.addAll(classList);
		// List<User> userList = manager.findUserList(currentPage, countPerPage);

		// userList ��ü�� ���� �α����� ����� ID�� request�� �����Ͽ� ����
//		if(classList == null)
//			request.setAttribute("classList", profClassList);	
//		else if(profClassList == null)
    	
    	
    	
		request.setAttribute("classList", classList);
		request.setAttribute("curUserId", 
				UserSessionUtils.getLoginUserId(request.getSession()));	
		//System.out.println(classList);
		
//		System.out.println(profClassList);
		// ����� ����Ʈ ȭ������ �̵�(forwarding)
		return "/review/searchedList.jsp";        
    }
}

