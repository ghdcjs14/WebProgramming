package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'");
		out.println("</form>");
		out.println("</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request에서 파라미터 꺼낼때 기본적으로 ISO-8859-1로 UTF-8로 인코딩 해줘야함
		// CharacterEncodingFilter에서 처리
//		request.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt =null;
		
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
							sc.getInitParameter("url"),
							sc.getInitParameter("username"),
							sc.getInitParameter("password"));
			stmt = conn.prepareStatement("INSERT INTO MEMBERS ( EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE ) "
					+" VALUES ( ?, ?, ?, NOW(), NOW())");
			stmt.setString(1,  request.getParameter("email"));
			stmt.setString(2,  request.getParameter("password"));
			stmt.setString(3,  request.getParameter("name"));
			stmt.executeUpdate();
			
			// 결과를 출력하지 않고 리다이렉트로 보낸다.
			response.sendRedirect("list");
			
			
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
		} finally {
			try { if(stmt != null) stmt.close(); } catch (Exception e) {}
			try { if(conn != null) conn.close(); } catch (Exception e) {}
		}
	}
	
	
}