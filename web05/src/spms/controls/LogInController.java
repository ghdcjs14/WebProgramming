package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import bind.DataBinding;
import spms.annotation.Component;
import spms.dao.PostgresqlMemberDao;
import spms.vo.Member;

@Component("/auth/login.do")
public class LogInController implements Controller, DataBinding {
	
	PostgresqlMemberDao memberDao;
	
	public LogInController setMemberDao(PostgresqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
			"logInfo", spms.vo.Member.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member logInfo = (Member)model.get("logInfo");
		
		if(logInfo.getEmail() == null) {
			return "/auth/LogInForm.jsp";
			
		} else {
//			MemberDao memberDao = (MemberDao)model.get("memberDao");
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
