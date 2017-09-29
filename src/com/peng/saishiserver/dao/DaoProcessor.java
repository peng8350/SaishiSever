package com.peng.saishiserver.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 数据处理者
 * 
 * @author peng 实现ＤＡＯＳＥＲＶＩＣＥ, 填充占位符 增删改 以ＬＩＳＴ＜ＪＡＶＡＢＥＡＮ＞,Javabean的形式返回
 */
public class DaoProcessor {
	private static final boolean AUTO_COMMIT = true;
	public static HikariDataSource ds;


	static{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/saishi");
		config.setUsername("peng");
		config.setPassword("28117896");
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 500);
		config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
		config.setConnectionTestQuery("SELECT 1");
		config.setAutoCommit(false);
		ds = new HikariDataSource(config);
	}

	public Connection getConnection() {
		return getConnection(AUTO_COMMIT);
	}

	public Connection getConnection(boolean autoCommit) {
		try {
			Connection conn = ds.getConnection();
			if (!autoCommit)
				conn.setAutoCommit(autoCommit);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加,删除,修改
	 */
	public boolean Update(String sql, Object[] params) {
		Connection conn = getConnection(false);
		PreparedStatement stmt = null;
		int result = -1;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			result = stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			free(stmt);
			free(conn);
		}
		return result > 0;
	}

	public <T> T fillClass(ResultSet set, Class<T> cls) {
		T t = null;
		try {
			t = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				String key = fields[i].getName();
				Object value = set.getObject(key);
				fields[i].setAccessible(true);
				fields[i].set(t, value);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 查找单个JAVABEAN
	 */
	public <T> T getBean(String sql, Object[] params, Class<T> cls) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		T t = null;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			rs = createResultSet(stmt);
			rs.next();
			if (rs.getRow() != 0) {
				t = fillClass(rs, cls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(rs);
			free(stmt);
			free(conn);
		}
		return t;
	}

	/**
	 * 查找多个ＪＡＶＡＢＥＡＮ
	 */
	public <T> List<T> getListBean(String sql, Object[] params, Class<T> cls) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<T> list = null;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			rs = createResultSet(stmt);
			list = new ArrayList<T>();
			while (rs.next()) {
				list.add(fillClass(rs, cls));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(rs);
			free(stmt);
			free(conn);
		}
		return list;
	}

	/**
	 * 获取单个值
	 */
	public Object getValue(String sql, Object[] params) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			rs = createResultSet(stmt);
			rs.next();
			if (rs.getRow() != 0) {

				return rs.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(rs);
			free(stmt);
			free(conn);
		}
		return 0;
	}

	/**
	 * 获取多个值 这方法一定要一列 比如:select row from...
	 */
	public List<? extends Object> getValues(String sql, Object[] params) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Object> list = null;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			rs = createResultSet(stmt);
			list = new ArrayList<Object>();
			while (rs.next()) {
				list.add(rs.getObject(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(rs);
			free(stmt);
			free(conn);
		}
		return list;
	}

	// 获取数量
	public int getCount(String sql, Object[] params) {

		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = createPreparedStatement(conn, sql, params);
			rs = createResultSet(stmt);
			rs.last();
			return rs.getRow();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(rs);
			free(stmt);
			free(conn);
		}
		return 0;

	}

	public void free(Connection x) {
		if (x != null)
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void free(Statement x) {
		if (x != null)
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void free(PreparedStatement x) {
		if (x != null)
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void free(ResultSet x) {
		if (x != null)
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public PreparedStatement createPreparedStatement(Connection conn,
			String sql, Object[] params) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		if (params != null)
			for (int i = 0; i < params.length; i++)
				stmt.setObject(i + 1, params[i]);
		return stmt;
	}

	public ResultSet createResultSet(PreparedStatement stmt)
			throws SQLException {
		return stmt.executeQuery();
	}

}
