package com.khs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.khs.common.OracleConn;


public class FileDao {
	private final Connection conn = OracleConn.in().getConn();
	public int filedel(String no) {
		int rs = 0;
		String sql ="DELETE FROM thumb WHERE fileup_seqno = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			stmt.executeUpdate();
			sql = "DELETE FROM fileup WHERE seqno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			rs = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
