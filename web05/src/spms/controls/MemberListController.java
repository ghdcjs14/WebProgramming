package spms.controls;

import java.util.Map;

import spms.dao.PostgresqlMemberDao;

public class MemberListController implements Controller {
	PostgresqlMemberDao memberDao;
	
	public MemberListController setMemberDao(PostgresqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
//		MemberDao memberDao = (MemberDao)model.get("memberDao"); // Dependency Injection으로 주석처
		model.put("members", memberDao.selectList());
		
		return "/member/MemberList.jsp";
	}
	
}
