package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberDeleteController implements Controller{

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao)model.get("memberDao");
		memberDao.delete(((Member)model.get("member")).getNo());
		
		return "redirect:list.do";
	}
	
}
