package spms.controls;

import java.util.Map;

import bind.DataBinding;
import spms.dao.PostgresqlMemberDao;
import spms.vo.Member;

public class MemberDeleteController implements Controller, DataBinding {

	PostgresqlMemberDao memberDao;
	
	public MemberDeleteController setMemberDao(PostgresqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
//		MemberDao memberDao = (MemberDao)model.get("memberDao");
		memberDao.delete((Integer)model.get("no"));
		
		return "redirect:list.do";
	}
	
}
