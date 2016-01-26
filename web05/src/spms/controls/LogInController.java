package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

public class LogInController implements Controller {
	
	MemberDao memberDao;
	
	public LogInController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		if(model.get("member") == null) {
			return "/auth/LogInForm.jsp";
			
		} else {
//			MemberDao memberDao = (MemberDao)model.get("memberDao");
			Member logInfo = (Member)model.get("member");
			Member member = memberDao.exist(logInfo.getEmail()
											, logInfo.getPassword());
			if(member != null) {
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";
			}
			
		}
	}
}
