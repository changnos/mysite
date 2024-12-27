package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import mysite.controller.action.board.DeleteAction;
import mysite.controller.action.board.ListAction;
import mysite.controller.action.board.ModifyAction;
import mysite.controller.action.board.ModifyFormAction;
import mysite.controller.action.board.ViewAction;
import mysite.controller.action.board.WriteAction;
import mysite.controller.action.board.WriteFormAction;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of("writeform", new WriteFormAction(), "write", new WriteAction(),
			"view", new ViewAction(),
			"delete", new DeleteAction(),
			"modifyForm", new ModifyFormAction(),
			"modify", new ModifyAction()
			);

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}

}
