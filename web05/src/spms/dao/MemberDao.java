package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import spms.vo.Member;

public class MemberDao {
	Connection connection;
	// ServletContext 영역에 접근 할 수 없어 setter 메서드 작성
	// 작업에 필요한 객체를 외부로부터 주입 받는 것을(의존성 주입, DI)
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public List<Member> selectList() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
							" select MNO, MNAME, EMAIL, CRE_DATE " +
							" from MEMBERS " + 
							" order by MNO asc");
			
			ArrayList<Member> members = new ArrayList<Member>();
			
			while(rs.next()) {
				members.add(new Member()
											.setNo(rs.getInt("MNO"))
											.setName(rs.getString("MNAME"))
											.setEmail(rs.getString("EMAIL"))
											.setCreatedDate(rs.getDate("CRE_DATE")));
			}
			return members;
		} catch(Exception e) {
			throw e;
		} finally {
			try { if(rs != null) rs.close(); } catch(Exception e) {}
			try { if(stmt != null) stmt.close(); } catch(Exception e) {}
		}
	}
	
	public int insert(Member member) throws Exception {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
							"INSERT INTO MEMBERS( EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE ) " 
							+ " VALUES ( ?, ?, ?, NOW(), NOW() ) ");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			
			return stmt.executeUpdate();
			
		} catch(Exception e) {
			throw e;
		} finally {
			try { if(stmt != null) stmt.close(); } catch(Exception e) {}
		}
	}
	
	public int delete(int no) throws Exception {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
							" DELETE FROM MEMBERS WHERE MNO = ? ");
			stmt.setInt(1, no);
			
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try { if(stmt != null) stmt.close(); } catch(Exception e) {}
		}
	}
	
	public Member selectOne(int no) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
							" SELECT MNO, MNAME, EMAIL, CRE_DATE "
							+ " FROM MEMBERS " 
							+ " WHERE MNO = ? ");
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			
			rs.next();
			
			return new Member().setNo(rs.getInt("MNO"))
											.setName(rs.getString("MNAME"))
											.setEmail(rs.getString("EMAIL"))
											.setCreatedDate(rs.getDate("CRE_DATE"));
			
		} catch (Exception e) {
			throw e;
		} finally {
			try { if(rs != null) rs.close(); } catch(Exception e) {}
			try { if(stmt != null) stmt.close(); } catch(Exception e) {}
		}
	}
	
	public int update(Member member) throws Exception {
		PreparedStatement stmt = null;
		
		try {
			 stmt = connection.prepareStatement(
					 				" UPDATE MEMBERS SET EMAIL = ?, MNAME = ?, MOD_DATE = NOW() "
					 				+ " WHERE MNO = ? ");
			 stmt.setString(1, member.getEmail());
			 stmt.setString(2, member.getName());
			 stmt.setInt(3, member.getNo());
			 
			 return stmt.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			try { if(stmt != null) stmt.close(); } catch(Exception e) {}
		}
	}
	
	public Member exist(String email, String password) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
								" select MNAME, EMAIL from MEMBERS where EMAIL = ? and PWD = ? ");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return new Member().setName(rs.getString("MNAME"))
											  .setEmail(rs.getString("EMAIL"));
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try { if(rs!=null) rs.close(); } catch(Exception e) {}
			try { if(stmt!=null) stmt.close(); } catch(Exception e) {}
		}
	}
}
