package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
			int gNo = Integer.parseInt(request.getParameter("gno"));
			int oNo = Integer.parseInt(request.getParameter("ono"));
			int depth = Integer.parseInt(request.getParameter("depth"));

			UserVo userVo = (UserVo) session.getAttribute("authUser");
			Long userId = userVo.getId();

			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserId(userId);
			vo.setgNo(gNo);
			vo.setoNo(oNo);
			vo.setDepth(depth);

			new BoardDao().insert(vo);
			response.sendRedirect(request.getContextPath() + "/board?p=1");
		}
	}

}
