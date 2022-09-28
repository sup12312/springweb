package com.khs.dao;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.khs.dto.Board;
import com.khs.dto.Cre;
import com.khs.dto.Reply;
import com.khs.dto.Thumb;
import com.khs.dto.Upload;

import oracle.jdbc.internal.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class BoardDaoimp implements BoardDao {
	
	@Autowired
	private DataSource db;
	
	
	public List<Board> boardlist(Cre c) {
		String sql = "CALL b_list(?,?,?,?,?)";
		List<Board> board = new ArrayList<>();
		try {
			Connection conn = db.getConnection();
			CallableStatement stmt = conn.prepareCall(sql);
			String cate = c.getCategory();
			String key = c.getKeyword();
			stmt.setInt(1, c.getCpage());
			stmt.setInt(2, c.getRow());
			stmt.setString(3, cate);
			stmt.setString(4, key);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.executeQuery();
			ResultSet rs =(ResultSet)stmt.getObject(5);
			while(rs.next()) {
				Board b = new Board();
				b.setNo(rs.getString("rownum"));
				b.setSeqno(rs.getString("seqno"));
				b.setTitle(rs.getString("title"));
				b.setCount(rs.getString("count"));
				b.setName(rs.getString("name"));
				b.setWdate(rs.getString("wdate"));
				board.add(b);
			}
			
			conn.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return board;
		/*
		String sql = "SELECT * FROM (SELECT rownum ro, seqno ,title , wda , count ,name FROM";
		sql+=" (SELECT seqno,title,to_char(b.wdate,'yyyy-mm-dd') wda";
		sql+=",count,name FROM board b,member m where b.id = m.id AND rownum <= ? AND b.isdel='n' AND open='y' ORDER BY seqno DESC))";
		sql+=" WHERE 1=1";
		sql+=" AND ro > ?";
		PreparedStatement stmt;
		List<Board> board = new ArrayList<>();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, (c.getCpage()*c.getRow()));
			stmt.setInt(2, ((c.getCpage()-1)*c.getRow()));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Board b = new Board();
				b.setNo(rs.getString("ro"));
				b.setTitle(rs.getString("title"));
				b.setCount(rs.getString("count"));
				b.setWdate(rs.getString("wda"));
				b.setName(rs.getString("name"));
				b.setSeqno(rs.getString("seqno"));
				board.add(b);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return board;
		*/
	}
	
	public Board boardDetail(String no) {
		String sql = "CALL p_boarddetail(?,?,?,?)";
		Board b = new Board();
		try {
			Connection conn = db.getConnection();
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, Integer.parseInt(no));
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.executeQuery();
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			rs.next();
			b.setTitle(rs.getString("title"));
			b.setContent(rs.getString("content"));
			b.setWdate(rs.getString("wdate"));
			b.setCount(rs.getString("count"));
			b.setName(rs.getString("name"));
			b.setId(rs.getString("id"));
			b.setOpen(rs.getString("open"));
			b.setNo(no);
			
			List<Reply> re = new ArrayList<>();
			rs = (ResultSet)stmt.getObject(3);
			while(rs.next()) {
				Reply r = new Reply();
				r.setContent(rs.getString("content"));
				r.setId(rs.getString("id"));
				r.setName(rs.getString("name"));
				r.setWdate(rs.getString("wdate"));
				re.add(r);
			}
			b.setRefly(re);
			
			//첨부파일 저장
			rs = (ResultSet)stmt.getObject(4);
			List<Upload> up = new ArrayList<Upload>();
			while(rs.next()) {
				Upload u = new Upload();
				u.setFilename(rs.getString("filename"));
				u.setSavefilename(rs.getString("savefile"));
				u.setFilepath(rs.getString("filepath"));
				u.setFilesize(rs.getString("filesize"));
				u.setFiletype(rs.getString("filetype"));
				u.setSeqno(rs.getString("no"));
				Thumb t = new Thumb();
				t.setSeqno(rs.getString("t_seqno"));
				t.setFilename(rs.getString("t_upfile"));
				t.setFilepath(rs.getString("t_filepath"));
				u.setThumb(t);
				up.add(u);
			}
			b.setUp(up);
			
			conn.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}
	public void replyinsert(Reply r) {
		String sql = "INSERT INTO reply(seqno,board_seqno,content,id)";
		       sql+=" VALUES ( reply_seq.nextval,?,?,?)";
		try {
			Connection conn = db.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, r.getBoardNo());
			stmt.setString(2, r.getContent());
			stmt.setString(3, r.getId());
			stmt.executeUpdate();
			
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public String boardinsert(Board bo, Upload u) {
		
		String sql = "CALL p_boardinsert(?,?,?)";
		String no = null;
		try {
			Connection conn = db.getConnection();
			CallableStatement stmt = conn.prepareCall(sql);
			StructDescriptor sd = StructDescriptor.createDescriptor("BOARD_O",conn);
			Object[] o = {bo.getTitle(),bo.getContent(),bo.getOpen(),bo.getId()};
			STRUCT s = new STRUCT(sd,conn,o);
			stmt.setObject(1, s);
			ArrayDescriptor ad = ArrayDescriptor.createDescriptor("FILE_ARR",conn);
			ARRAY ho = null;
			if(u.getFilename() != null ) {
				Object[] ot = null;
				if(u.getThumb() != null) {
					ot = new Object[]{u.getThumb().getFilename(),u.getThumb().getFilepath(),u.getThumb().getFilesize()};
				}
				StructDescriptor sdt = StructDescriptor.createDescriptor("FILE_O",conn);
				STRUCT[] st = new STRUCT[1];
				Object[] ob = {u.getFilename(),u.getSavefilename(),u.getFilesize(),u.getFiletype(),u.getFilepath(),ot};
				st[0] = new STRUCT(sdt,conn,ob);
				ho = new ARRAY(ad,conn,st);
			}else {
				ho = new ARRAY(ad,conn,null);
			}
			stmt.setArray(2, ho);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.executeQuery();
			no = Integer.toString(stmt.getInt(3));
			
			stmt.close();
			conn.close();
		} catch (SQLException e1) {
				e1.printStackTrace();
		}
		return no;
	}
	public Map<String,String> boarddelete(String no) {
		String sql="call p_boarddelete(?,?,?,?)";
		Map<String,String> m = new HashMap<>();
		try {
			Connection conn = db.getConnection();
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, no);
			stmt.registerOutParameter(2, OracleTypes.VARCHAR);;
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);
			stmt.executeUpdate();
			String s = stmt.getString(2);
			String p = stmt.getString(3);
			String t = stmt.getString(4);
			if(t != null) {
				m.put("t", t);
			}else {
				m.put("t", "0");
			}
			if(s != null) {
				m.put("s", s);
				m.put("p", p);
			}else {
				m.put("s", "0");
				m.put("p", "0");
			}
			
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*
		String sql = "DELETE FROM reply WHERE board_seqno=?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			stmt.executeUpdate();
			sql="SELECT nvl(max(seqno),0) seqno FROM fileup WHERE board_seqno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			ResultSet rs =stmt.executeQuery();
			rs.next();
			String seq = rs.getString("seqno");
			System.out.println(seq);
			sql = "DELETE FROM thumb WHERE fileup_seqno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, seq);
			stmt.executeUpdate();
			sql = "DELETE FROM fileup WHERE seqno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, seq);
			stmt.executeUpdate();
			sql = "DELETE FROM board WHERE seqno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			stmt.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		return m;
	}
	public String modify(Board b, Upload u) {
		String sql="call p_boarupdate(?,?,?,?)";
		try {
			Connection conn = db.getConnection();
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(1, b.getTitle());
			stmt.setString(2, b.getContent());
			stmt.setString(3, b.getOpen());
			stmt.setString(4, b.getSeqno());
			stmt.executeUpdate();
			//첨부파일
			if(u.getFilename() != null) {
				
				u.setSeqno(fileinsert(u,b.getSeqno()));;
				String type = u.getFiletype();
				//썸네일
				if(type.substring(0,type.indexOf("/")).equals("image")) {
					thumbinsert(u);
					
				}
			}
			
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b.getSeqno();
	}
	public String fileinsert(Upload u,String no) {
		String fno = null;
		//파일저장
		String sql = "INSERT INTO fileup(seqno,upfile,savefile,filesize,filetype,filepath,board_seqno)";
			   sql+=" VALUES (fileup_seqno.nextval,?,?,?,?,?,?)";	
		try {
			Connection conn = db.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getFilename());
			stmt.setString(2, u.getSavefilename());
			stmt.setString(3, u.getFilesize());
			stmt.setString(4, u.getFiletype());
			stmt.setString(5, u.getFilepath());
			stmt.setString(6, no);
			stmt.executeUpdate();
			
			//파일업시퀀스 넘버 저장
			sql = "SELECT MAX(seqno) as seqno FROM fileup";
			ResultSet rs =stmt.executeQuery(sql);
			rs.next();
			fno = rs.getString("seqno");
			
			
			conn.close();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fno; 
	}
	public void thumbinsert(Upload u) {
		//썸네일저장
		Thumb t =u.getThumb();
		String sql = "INSERT INTO thumb(seqno,upfile,filesize,filepath,fileup_seqno)";
		       sql +=" VALUES (thumb_seqno.nextval,?,?,?,?)";
		PreparedStatement stmt;
		try {
			Connection conn = db.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, t.getFilename());
			stmt.setString(2, t.getFilesize());
			stmt.setString(3, t.getFilepath());
			stmt.setString(4, u.getSeqno());
			stmt.executeUpdate();
			
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getT(Cre c) {
		String sql="CALL p_total(?,?,?,?,?)";
		int s = 0;
		try {
			Connection conn = db.getConnection();
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, c.getCpage());
			stmt.setInt(2, c.getRow());
			stmt.setString(3, c.getCategory());
			stmt.setString(4, c.getKeyword());
			stmt.registerOutParameter(5, OracleTypes.INTEGER);
			stmt.executeQuery();
			s = stmt.getInt(5);
			
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
}
