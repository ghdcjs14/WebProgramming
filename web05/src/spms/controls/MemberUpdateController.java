package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller{

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		if(model.get("member") == null) {
			return "/member/MemberUpdateForm.jsp";
			
		} else {
			MemberDao memberDao = (MemberDao)model.get("memberDao");
			
			Member member = (Member)model.get("member");
			memberDao.update(member);
			
			return "redirect:list.do";
		}
		
	}
	
}
