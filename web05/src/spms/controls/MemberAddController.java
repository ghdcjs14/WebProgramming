package spms.controls;

import java.util.Map;

import bind.DataBinding;
import spms.dao.PostgresqlMemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller, DataBinding{

	PostgresqlMemberDao memberDao;
	
	public MemberAddController setMemberDao(PostgresqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				"member", spms.vo.Member.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member)model.get("member");
		
//		if(model.get("member") == null) { //입력 폼 요청할 때
		if(member.getEmail() == null) { // 데이터 바인딩 사용으로 member객체가 무조건 있다고 고려하여 작
			return  "/member/MemberForm.jsp";
			
		} else { // 회원 등록을 요청할 때
			memberDao.insert(member);
			return "redirect:list.do";
		}
	}
	
}
