package spms.servlets;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AppInitServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("AppInitServlet 준비...");
		super.init(config); // 기존 HttpServet에서 정의한 기능은 그대로 상속
		
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			Connection  conn = DriverManager.getConnection(
											sc.getInitParameter("url"),
											sc.getInitParameter("username"),
											sc.getInitParameter("password"));
			sc.setAttribute("conn", conn);
			 
		} catch(Throwable e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void destroy() {
		System.out.println("AppInitServlet 마무리...");
		super.destroy();  // 기존 HttpServet에서 정의한 기능은 그대로 상속
		Connection conn = (Connection)this.getServletContext().getAttribute("conn");
		
		try {
			if(conn != null && conn.isClosed() == false) {
				conn.close();
			} 
		} catch(Exception e) { }
	}
	
	
}
