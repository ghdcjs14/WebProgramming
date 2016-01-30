package spms.controls;

import java.util.Map;

import bind.DataBinding;
import spms.annotation.Component;
import spms.dao.PostgresqlMemberDao;
import spms.vo.Member;

@Component("/member/update.do")
public class MemberUpdateController implements Controller, DataBinding {

	PostgresqlMemberDao memberDao;
	
	public MemberUpdateController setMemberDao(PostgresqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class,
			"member", spms.vo.Member.class
		};
	}

 

	@Override
	public String execute(Map<String, Object> model) throws Exception {

//		MemberDao memberDao = (MemberDao)model.get("memberDao");
		Member member = (Member)model.get("member");
		
		if(member.getEmail() == null) {
			model.put("member", memberDao.selectOne(Integer.parseInt(model.get("no").toString())));
			return "/member/MemberUpdateForm.jsp";
			
		} else {
			
			memberDao.update(member);
			return "redirect:list.do";
		}
		
	}
	
}
