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

import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("/member/update")
// 컨텍스트 매개변수로 어노테이션으로 정의 할 필요 없음
//@WebServlet(
//		urlPatterns={"/member/update"},
//		initParams={
//				@WebInitParam(name="driver", value="com.mysql.jdbc.Driver"),
//				@WebInitParam(name="url", value="jdbc:mysql://localhost/studydb"),
//				@WebInitParam(name="username", value="study"),
//				@WebInitParam(name="password", value="study")
//		}
//)
//@SuppressWarnings("serial")
public class MemberUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ServletContext sc = this.getServletContext();
			
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
			request.setAttribute("member", memberDao.selectOne(Integer.parseInt(request.getParameter("no"))));
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// CharacterEncodingFilter에서 처리
//		request.setCharacterEncoding("UTF-8");
		
		try {
			ServletContext sc = this.getServletContext();
			
			Member member = new Member().setNo(Integer.parseInt(request.getParameter("no")))
															.setName(request.getParameter("name"))
															.setEmail(request.getParameter("email"));
			
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
			memberDao.update(member);
			
			response.sendRedirect("list");
					
		} catch(Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} 
	}
}
