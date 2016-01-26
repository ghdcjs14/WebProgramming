package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

public class LogOutController implements Controller {
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		HttpSession session = (HttpSession)model.get("session");
		if(session.getAttribute("member") == null) {
			return "redirect:../member/list.do";
		} else {
			session.invalidate();
			return "redirect:../auth/login.do";
		}
	}
	
}
