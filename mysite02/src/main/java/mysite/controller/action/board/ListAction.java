package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String kwd = request.getParameter("kwd");
		if (kwd != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("kwd", kwd);
		}

		int p = Integer.parseInt(request.getParameter("p"));
		List<BoardVo> list = new BoardDao().findByPage(p, 5, (kwd != null ? kwd : ""));
		request.setAttribute("list", list);

		int numberOfPage = new BoardDao().findNumberOfPage(5, (kwd != null ? kwd : ""));
		request.setAttribute("numberOfPage", numberOfPage);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
