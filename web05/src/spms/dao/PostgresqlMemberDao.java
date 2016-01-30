package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.annotation.Component;
import spms.vo.Member;

@Component("memberDao")
public class PostgresqlMemberDao implements MemberDao {

	DataSource ds;

	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public List<Member> selectList() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
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
			try { if(conn != null) conn.close(); } catch(Exception e) {}
		}
	}
	
	public int insert(Member member) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(
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
			try { if(conn != null) conn.close(); } catch(Exception e) {}
		}
	}
	
	public int delete(int no) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(
							" DELETE FROM MEMBERS WHERE MNO = ? ");
			stmt.setInt(1, no);
			
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try { if(stmt != null) stmt.close(); } catch(Exception e) {}
			try { if(conn != null) conn.close(); } catch(Exception e) {}
		}
	}
	
	public Member selectOne(int no) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(
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
			try { if(conn != null) conn.close(); } catch(Exception e) {}
		}
	}
	
	public int update(Member member) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			 conn = ds.getConnection();
			 stmt = conn.prepareStatement(
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
			try { if(conn != null) conn.close(); } catch(Exception e) {}
		}
	}
	
	public Member exist(String email, String password) throws Exception {
		Connection conn =null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(
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
			try { if(conn != null) conn.close(); } catch(Exception e) {}
		}
	}
}
