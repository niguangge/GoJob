package com.lazysong.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.lazysong.test.beans.Company;
import com.lazysong.test.beans.Hide;
import com.lazysong.test.beans.Industry_category;
import com.lazysong.test.beans.Mark_com;
import com.lazysong.test.beans.Mark_info;
import com.lazysong.test.beans.Place;
import com.lazysong.test.beans.Post_information;
import com.lazysong.test.beans.Resume;
import com.lazysong.test.beans.User;
import com.lazysong.test.beans.Willings;

public class RequestMethods {
	public static final String url = "jdbc:mysql://192.168.220.128/GoJob";// 用于本地虚拟机测试
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "1";
	public Connection conn = null;
	public PreparedStatement pst = null;
	ResultSet ret = null;
	String result = "";
	Gson gson = new Gson();

	public RequestMethods() {

		try {
			Class.forName(name);
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			// pst = conn.prepareStatement(sql);// 准备执行语句
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 指定连接类型
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
			this.ret.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// public String get_places() {
	// result = getSearchSql("PLACE");
	// return result;
	// }

	public String getSearchSql(String table, String type, String value) {
		// 获取table表type列中值为value的行。
		String sql = "select * from " + table + " where " + type + "=\""
				+ value + "\"";
		return sql;
	}

	public String getSearchSql(String table) {
		// 获取table表type列中值为value的行。
		String sql = "select * from " + table;
		return sql;
	}

	public String getSearchResult(Object tableType, String sql, String judgeName) {
		ArrayList list = new ArrayList();
		try {
			pst = conn.prepareStatement(sql);
			ret = pst.executeQuery();
			if (judgeName != null) {
				if (ret.next()) {
					return judgeName + true;
				} else {
					return judgeName + false;
				}
			}
			result = getResultSet(tableType, list, ret);
			// while (ret.next()) {
			// Place p = new Place();
			// p.setPlace_id(ret.getInt("PLACE_ID"));
			// p.setPlace_name(ret.getString("PLACE_NAME"));
			// list.add(p);
			//
			// }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getResultSet(Object tableType, ArrayList list, ResultSet ret) {
		// TODO Auto-generated method stub=
		String tablename = tableType.getClass().getName()
				.replace("com.lazysong.test.beans.", "");
		switch (tablename) {
		case "Company":
			getCompanyList(tableType, list, ret);
			break;
		case "Hide":
			getHideList(tableType, list, ret);
			break;
		case "Industry_category":
			getIndustry_categoryList(tableType, list, ret);
			break;
		case "Mark_com":
			getMark_comList(tableType, list, ret);
			break;
		case "Mark_info":
			getMark_infoList(tableType, list, ret);
			break;
		case "Place":
			getPlaceList(tableType, list, ret);
			break;
		case "Post_information":
			getPost_informationList(tableType, list, ret);
			break;
		case "Resume":
			getResumeList(tableType, list, ret);
			break;
		case "User":
			getUserList(tableType, list, ret);
			break;
		case "Wilings":
			getWillingsList(tableType, list, ret);
			break;
		default:
			break;
		}
		// try {
		// p.setPlace_id(ret.getInt("PLACE_ID"));
		// p.setPlace_name(ret.getString("PLACE_NAME"));
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// List.add(p);
		result = gson.toJson(list);
		return result;
	}

	public void getWillingsList(Object tableType, ArrayList list,
			ResultSet ret) {
		// TODO Auto-generated method stub
		try {
			while (ret.next()) {
				Willings p = new Willings();
				p.setUser_id(ret.getString("USER_ID"));
				p.setWilling_no(ret.getByte("WILLING_NO"));
				p.setSalary_month(ret.getString("SALARY_MONTH"));
				p.setWork_place(ret.getInt("WORK_PLACE"));
				p.setCategory_id(ret.getInt("CATEGORY_ID"));
				p.setSalary_month(ret.getString("SALARY_MONTH"));
				p.setPosition_type(ret.getString("POSITION_TYPE"));
				
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getResumeList(Object tableType, ArrayList list, ResultSet ret) {
		// TODO Auto-generated method stub
		try {
			while (ret.next()) {
				Resume p = new Resume();
				p.setUser_id(ret.getString("USER_ID"));
				p.setResume_no(ret.getByte("RESUME_NO"));
				p.setEducation_experience(ret.getString("EDUCATION_EXPERIENCE"));
				p.setMajor(ret.getString("MAJOR"));
				p.setJob_experience(ret.getString("JOB_EXPERIENCE"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getPost_informationList(Object tableType, ArrayList list,
			ResultSet ret) {
		// TODO Auto-generated method stub
		try {
			while (ret.next()) {
				Post_information p = new Post_information();
				p.setPost_id(ret.getLong("POST_ID"));
				p.setCompany_name(ret.getString("COMPANY_NAME"));
				p.setSalary_month(ret.getString("SALARY_MONTH"));
				p.setWork_place(ret.getString("WORK_PLACE"));
				p.setPost_date(ret.getDate("POST_DATE"));
				p.setWork_type(ret.getString("WORK_TYPE"));
				p.setExperience_requirement(ret.getString("EXPERIENCE_REQUIREMENT"));
				p.setEducation_requirement(ret.getString("EDUCATION_REQUIREMENT"));
				p.setPosition_count(ret.getInt("POSITION_COUNT"));
				p.setPostition_type(ret.getString("POSTITION_TYPE"));
				p.setCategory_name(ret.getString("CATEGORY_NAME"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getMark_infoList(Object tableType, ArrayList list,
			ResultSet ret) {
		// TODO Auto-generated method stub
		try {
			while (ret.next()) {
				Mark_info p = new Mark_info();
				p.setUser_id(ret.getString("USER_ID"));
				p.setPost_id(ret.getLong("POST_ID"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getMark_comList(Object tableType, ArrayList list,
			ResultSet ret) {
		// TODO Auto-generated method stub
		try {
			while (ret.next()) {
				Mark_com p = new Mark_com();
				p.setUser_id(ret.getString("USER_ID"));
				p.setCompany_id(ret.getInt("COMPANY_ID"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getIndustry_categoryList(Object tableType, ArrayList list,
			ResultSet ret) {
		// TODO Auto-generated method stub
		try {
			while (ret.next()) {
				Industry_category p = new Industry_category();
				p.setCategory_id(ret.getInt("CATEGORY_ID"));
				p.setCategory_name(ret.getString("CATEGORY_NAME"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getHideList(Object tableType, ArrayList list, ResultSet ret) {
		// TODO Auto-generated method stub
		try {
			while (ret.next()) {
				Hide p = new Hide();
				p.setUser_id(ret.getString("USER_ID"));
				p.setPost_id(ret.getInt("POST_ID"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getCompanyList(Object tableType, ArrayList list, ResultSet ret) {
		try {
			while (ret.next()) {
				Company p = new Company();
				p.setCompany_id(ret.getInt("COMPANY_ID"));
				p.setCompany_name(ret.getString("COMPANY_NAME"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getPlaceList(Object tableType, ArrayList list, ResultSet ret) {
		try {
			while (ret.next()) {
				Place p = new Place();
				p.setPlace_id(ret.getInt("PLACE_ID"));
				p.setPlace_name(ret.getString("PLACE_NAME"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getUserList(Object tableType, ArrayList list, ResultSet ret) {
		try {
			while (ret.next()) {
				User p = new User();
				p.setUser_id(ret.getString("USER_ID"));
				p.setPassword(ret.getString("PASSWORD"));
				p.setNickname(ret.getString("NICKNAME"));
				p.setSign(ret.getString("SIGN"));
				p.setImg_name(ret.getString("IMG_NAME"));
				p.setSex(ret.getByte("SEX"));
				p.setBirthday(ret.getDate("BIRTHDAY"));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
