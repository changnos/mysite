package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;

public class BoardDao {

	public BoardVo findById(Long id) {
		BoardVo vo = null;
		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn
						.prepareStatement("select title, contents, user_id, g_no, o_no, depth from board where id=?");
				PreparedStatement pstmt2 = conn
						.prepareStatement("update board set hit=(select hit from board where id=?)+1 where id=?;");) {
			pstmt1.setLong(1, id);
			ResultSet rs1 = pstmt1.executeQuery();

			pstmt2.setLong(1, id);
			pstmt2.setLong(2, id);
			pstmt2.executeUpdate();

			if (rs1.next()) {
				String title = rs1.getString(1);
				String contents = rs1.getString(2);
				Long userId = rs1.getLong(3);
				int gNo = rs1.getInt(4);
				int oNo = rs1.getInt(5);
				int depth = rs1.getInt(6);

				vo = new BoardVo();
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserId(userId);
				vo.setId(id);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);

				return vo;
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return vo;
	}

	public List<BoardVo> findByPage(int p, int pageSize, String kwd) {
		List<BoardVo> result = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select a.id, a.title, a.hit, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.g_no, a.o_no, a.depth, b.name, a.user_id, row_number() over (order by a.g_no asc, a.o_no desc) as idx"
								+ " from board a join user b on a.user_id = b.id where a.title like ? or a.contents like ?"
								+ " order by a.g_no desc, a.o_no asc" + " limit ?, ?");) {
			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setString(2, "%" + kwd + "%");
			pstmt.setInt(3, (p - 1) * pageSize);
			pstmt.setInt(4, pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String regDate = rs.getString(4);
				int gNo = rs.getInt(5);
				int oNo = rs.getInt(6);
				int depth = rs.getInt(7);
				String userName = rs.getString(8);
				Long userId = rs.getLong(9);
				int idx = rs.getInt(10);

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserName(userName);
				vo.setUserId(userId);
				vo.setIdx(idx);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	// 몇페이지까지 있는지구하기
	public int findNumberOfPage(int pageSize, String kwd) {
		int result = 0;
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select ceil(count(*)/?) from board where title like ? or contents like ?");) {
			pstmt.setInt(1, pageSize);
			pstmt.setString(2, "%" + kwd + "%");
			pstmt.setString(3, "%" + kwd + "%");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
				System.out.println("result: " + result);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select a.id, a.title, a.hit, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.g_no, a.o_no, a.depth, b.name, a.user_id "
								+ "from board a join user b on a.user_id = b.id " + "order by g_no desc, o_no asc");
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String regDate = rs.getString(4);
				int gNo = rs.getInt(5);
				int oNo = rs.getInt(6);
				int depth = rs.getInt(7);
				String userName = rs.getString(8);
				Long userId = rs.getLong(9);

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserName(userName);
				vo.setUserId(userId);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public void insert(BoardVo vo) {
		if (vo.getgNo() == 0) {
			try (Connection conn = getConnection();
					PreparedStatement pstmt = conn.prepareStatement(
							"insert into board values(null, ?, ?, 0, now(), (select ifnull(max(a.g_no), 0)+1 from board a), 1, 0, ?)");) {
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getUserId());

				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		} else {
			try (Connection conn = getConnection();
					PreparedStatement pstmt1 = conn
							.prepareStatement("update board set o_no=o_no+1 where g_no=? and o_no>=?");
					PreparedStatement pstmt2 = conn
							.prepareStatement("insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?)");) {
				pstmt1.setInt(1, vo.getgNo());
				pstmt1.setInt(2, vo.getoNo() + 1);

				pstmt2.setString(1, vo.getTitle());
				pstmt2.setString(2, vo.getContents());
				pstmt2.setInt(3, vo.getgNo());
				pstmt2.setInt(4, vo.getoNo() + 1);
				pstmt2.setInt(5, vo.getDepth() + 1);
				pstmt2.setLong(6, vo.getUserId());

				pstmt1.executeUpdate();
				pstmt2.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		}
	}

	public void modify(Long id, String title, String contents) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("update board set title=?, contents=?, reg_date=now() where id=?");) {

			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

	public void deleteById(Long id) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");) {
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.27:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return conn;
	}

}
