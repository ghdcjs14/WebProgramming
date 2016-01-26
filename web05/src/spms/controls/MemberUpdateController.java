package spms.controls;

import java.util.Map;

import org.apache.catalina.connector.Request;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller{

	MemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {

//		MemberDao memberDao = (MemberDao)model.get("memberDao");
		
		if(model.get("member") == null) {
			model.put("member", memberDao.selectOne(Integer.parseInt(model.get("no").toString())));
			return "/member/MemberUpdateForm.jsp";
			
		} else {
			
			Member member = (Member)model.get("member");
			memberDao.update(member);
			
			return "redirect:list.do";
		}
		
	}
	
}
