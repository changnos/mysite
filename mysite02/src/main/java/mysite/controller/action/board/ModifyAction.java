package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.UserVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			Long id = Long.parseLong(request.getParameter("id"));
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");

			System.out.println("id: " + id);
			System.out.println("title: " + title);
			System.out.println("contents: " + contents);

			new BoardDao().modify(id, title + "(수정)", contents);

			response.sendRedirect(
					request.getContextPath() + "/board?a=view&id=" + id + "&p=" + request.getParameter("p"));
		}
	}

}
