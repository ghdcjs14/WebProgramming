package spms.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.PostgresqlMemberDao;
import spms.vo.Member;

// 프런트 컨트롤러 적용
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request에서 파라미터 꺼낼때 기본적으로 ISO-8859-1로 UTF-8로 인코딩 해줘야함
		// CharacterEncodingFilter에서 처리
//		request.setCharacterEncoding("UTF-8");
		try {
			ServletContext sc = this.getServletContext();
			
			PostgresqlMemberDao memberDao = (PostgresqlMemberDao)sc.getAttribute("memberDao");
			
			Member member = (Member)request.getAttribute("member");
			memberDao.insert(member);
			
			// 결과를 출력하지 않고 리다이렉트로 보낸다.
//			response.sendRedirect("list");
			
			// 프런트 컨트롤러 사용
			request.setAttribute("viewUrl", "redirect:list.do");
			
			
			// Redirect  사용으로 결과 페이지 필요없음
			/*
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원등록결과</title>");
			// <head> 태그 안에 meta 태그로 Refresh 설정 추가
			out.println("<meta http-equiv='Refresh' content='1; url=list'>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>등록 성공입니다!</p>");
			out.println("</body></html>");
			*/
			
			// 1초 뒤에 상대주소 list로 새로고침(Refresh)
//			response.addHeader("Refresh","1; url=list");
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	
}
