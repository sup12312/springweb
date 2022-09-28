package com.khs.dao;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.khs.dto.Member;

import oracle.jdbc.internal.OracleResultSet;
import oracle.jdbc.internal.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class MemberDaoimp implements MemberDao {
	//private static final Connection c = OracleConn.in().getConn();
	@Autowired
	private DataSource dbconn;
	
	@Override
	public Map<Integer , String> login(String id,String pw){
		Map<Integer,String> map = new HashMap<>();
		String sql = "select * from member where id = ?";
		try {
			Connection c = dbconn.getConnection();
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("pw").equals(pw)){
					map.put(1, rs.getString("name"));
				}else map.put(2, "null");
			}
			map.put(2, "null");
			
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return map;
	}
	
	@Override
	public int insert(Member member) {
		int rs = 0;
		String email = member.getEid()+"@"+member.getDomain();
		try {
			Connection c = dbconn.getConnection();
			String sql = "call p_insert_m(?,?,?)";
			CallableStatement stmt = c.prepareCall(sql);
			StructDescriptor s = StructDescriptor.createDescriptor("OBJ_M",c);
			Object[] o = {member.getId(),member.getPw(),member.getName(),member.getGender(),email,member.getIntro()};
			STRUCT mem = new STRUCT(s,c,o);
			stmt.setObject(1, mem);
			ArrayDescriptor ad = ArrayDescriptor.createDescriptor("STRING_NT",c);
			ARRAY ho = new ARRAY(ad,c,member.getHobby());
			stmt.setArray(2, ho);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.executeUpdate();
			rs = stmt.getInt(3);
			
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	@Override
	public int check(String id) {
		CallableStatement stmt = null;
		int rs = 0;
		String sql="call p_idcheck(?,?)";
		try {
			Connection c = dbconn.getConnection();
			stmt = c.prepareCall(sql);
			stmt.setString(1, id);
			stmt.registerOutParameter(2, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			rs = stmt.getInt(2);
			
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	@Override
	public List<Member> list(HttpServletRequest req) {
		List<Member> member = new ArrayList<Member>();
		String sql="CALL p_mem(?)";
		try {
			Connection c = dbconn.getConnection();
			CallableStatement stmt = c.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.executeQuery();
			ResultSet rs = (ResultSet)stmt.getObject(1);
			while(rs.next()) {
				Member m = new Member();
				m.setId(rs.getString("id"));
				m.setName(rs.getString("name"));
				m.setGender(rs.getString("gender"));
				//컬렉션 중첩테이블 받기 
				if(rs.getArray("hobby_n") != null) {
					//컬렉션 중첩테이블 데이터 가져오기 임포트 맨처음꺼
					ARRAY h_arr = ((OracleResultSet)rs).getARRAY("hobby_n");
					//데이터가져오기
					String[] s = (String[])h_arr.getArray();
					String str = null;
					//배열을 문자로 변환
					m.setHobby_str(Arrays.toString(s));
				}
				m.setWdate(rs.getString("wdate"));
				member.add(m);
			}
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	
}
