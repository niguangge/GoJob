package com.lazysong.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lazysong.test.beans.BaseUser;
import com.lazysong.test.beans.Company;
import com.lazysong.test.beans.Count;
import com.lazysong.test.beans.Industry_category;
import com.lazysong.test.beans.Mark_com;
import com.lazysong.test.beans.Mark_info;
import com.lazysong.test.beans.Place;
import com.lazysong.test.beans.Post_information;
import com.lazysong.test.beans.Resume;
import com.lazysong.test.beans.User;

public class LoginServlet extends HttpServlet {

	// private static final String "" = null;

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String limit = request.getParameter("limit");
		String result;
		if (request.getParameter("requestcode") == null) {
			return;
		}
		int requestCode = Integer.parseInt(request.getParameter("requestcode"));
		PrintWriter out = response.getWriter();
		String sql = null;
		RequestMethods rm = new RequestMethods();// 建立RM对象，同时建立与数据库的连接
		Object tableType = null;// 声明所需集合类型
		String judgeName = null;// 此值为null时，说明result需返回详细结果
		// 此值非空时，标明是哪个case需要判断结果集是否存在,以便输出RequestCode中对应字符串
		String nullParament = null;// 当某参数为空时,令此字符串等于该参数的名字,便于输出
		String exceptionName = null;// 异常名称,便于输出
		boolean updateFlag = false;
		switch (requestCode) {
		case RequestCode.USER_EXISTS:
			// USER_EXISTS
			String user_id = request.getParameter("USER_ID");
			if (user_id == null) {
				nullParament = "USER_ID";
			}
			sql = rm.getSearchSql("USER", "USER_ID", user_id);
			judgeName = ResultCode.USER_EXISTS;
			tableType = new User();
			break;
		case RequestCode.UPLOAD_USER: // UPLOAD_USER
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String userstr = request.getParameter("USER_INFO");
			User user = gson.fromJson(userstr, User.class);
			// JSONObject jsonObj = JSONObject.fromObject(json);
			user_id = null;
			String password = null;
			String nickname = null;
			String sign = null;
			String imgname = null;
			int sex = 0;
			String birthday = null;
			if (user.getUser_id() == null) {
				nullParament = "USER_ID";
			} else {
				user_id = user.getUser_id();
			}
			if (user.getPassword() == null) {
				password = "1";
			} else {
				password = user.getPassword();
			}
			if (user.getNickname() == null) {
				nickname = "1";
			} else {
				nickname = user.getNickname();
				nickname = new String(nickname.getBytes("iso-8859-1"), "utf-8");
			}
			if (user.getSign() == null) {
				sign = "1";
			} else {
				sign = user.getSign();
			}
			if (user.getImg_name() == null) {
				imgname = "1";
			} else {
				imgname = user.getImg_name();
			}
			if (user.getBirthday() == null) {
				birthday = "2017-03-13";
			} else {
				birthday = user.getBirthday().toString();
			}

			sql = "insert into USER values('" + user_id + "','" + password
					+ "','" + nickname + "','" + sign + "','" + imgname + "',"
					+ sex + ",'" + birthday + "') "	;
			updateFlag = true;
			break;

		case RequestCode.CAT_BY_CATEGRY: // CAT_BY_CATEGRY
			String cate = request.getParameter("CATEGORY");
			String catevalue = request.getParameter("CATE_VALUE");
			if (cate == null) {
				nullParament = "CATEGORY";
			} else if (catevalue == null) {
				nullParament = "CATE_VALUE";
			} else if (cate.equals("COMPANY_NAME") || cate.equals("PLACE_NAME")
					|| cate.equals("CATEGORY_NAME")) {
				cate = new String(cate.getBytes("iso-8859-1"), "utf-8");
				catevalue = new String(catevalue.getBytes("iso-8859-1"),
						"utf-8");

				sql = rm.getSearchSql("POST_INFORMATION", cate, catevalue);
				tableType = new Post_information();
			} else {
				exceptionName = "Unsupport category to query";
			}
			break;
		case RequestCode.SEARCH: // SEARCH
			String keyword = request.getParameter("KEYWORD");
			if (keyword == null) {
				nullParament = "KEYWORD";
			} else {
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
				tableType = new Post_information();
				sql = "select * from POST_INFORMATION where COMPANY_NAME like \'%"
						+ keyword + "%\'";
				result = rm.getSearchResult(tableType, sql, judgeName);
				out.println("result by COMPANY_NAME");
				out.println(result);
				sql = "select * from POST_INFORMATION where WORK_PLACE like \'%"
						+ keyword + "%\'";
				result = rm.getSearchResult(tableType, sql, judgeName);
				out.println("result by WORK_PLACE");
				out.println(result);
				sql = "select * from POST_INFORMATION where CATEGORY_NAME like \'%"
						+ keyword + "%\'";
				result = rm.getSearchResult(tableType, sql, judgeName);
				out.println("result by CATEGORY_NAME");
				out.println(result);
				return;
			}
			break;
		case RequestCode.GET_PLACES: // GET_PLACES
			sql = rm.getSearchSql("PLACE");
			tableType = new Place();
			break;
		case RequestCode.GET_INDUSTRY: // GET_INDUSTRY
			sql = rm.getSearchSql("INDUSTRY_CATEGORY");
			tableType = new Industry_category();
			break;
		case RequestCode.GET_COMPANY: // GET_COMPANY
			sql = rm.getSearchSql("COMPANY");
			tableType = new Company();
			break;
		case RequestCode.MARK_POST: // MARK_POST
			user_id = request.getParameter("USER_ID");
			String post_id = request.getParameter("POST_ID");
			sql = "insert into MARK_INFO values(\"" + user_id + "\"," + post_id
					+ ")";
			updateFlag = true;
			break;
		case RequestCode.UNMARK_POST: // UNMARK_POST
			user_id = request.getParameter("USER_ID");
			post_id = request.getParameter("POST_ID");
			sql = "delete from MARK_INFO where USER_ID='" + user_id
					+ "'and POST_ID=" + post_id;
			System.out.println(sql);
			updateFlag = true;
			break;
		case RequestCode.HIDE_POST:// HIDE_POST
			user_id = request.getParameter("USER_ID");
			post_id = request.getParameter("POST_ID");
			sql = "insert into HIDE values(\"" + user_id + "\"," + post_id
					+ ")";
			updateFlag = true;
			break;
		case RequestCode.CAT_USER:// CAT_USER
			user_id = request.getParameter("USER_ID");
			sql = rm.getSearchSql("USER", "USER_ID", user_id);
			tableType = new User();
			break;
		case RequestCode.EDIT_USER:// EDIT_USER
			gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			userstr = request.getParameter("USER_INFO");
			user = gson.fromJson(userstr, User.class);
			// JSONObject jsonObj = JSONObject.fromObject(json);
			user_id = null;
			password = null;
			nickname = null;
			sign = null;
			imgname = null;
			sex = 0;
			birthday = null;
			sql="update USER set ";
			if (user.getUser_id() == null) {
				nullParament = "USER_ID";
			} else {
				user_id = user.getUser_id();
			}
			if (user.getPassword() == null) {
			} else {
				password = user.getPassword();
				sql+="PASSWORD='"+password+"',";
			}
			if (user.getNickname() == null) {
			} else {
				nickname = user.getNickname();
				nickname = new String(nickname.getBytes("iso-8859-1"), "utf-8");
				sql+="NICKNAME='"+nickname+"',";
			}
			if (user.getSign() == null) {
			} else {
				sign = user.getSign();
				sql+="SIGN='"+sign+"',";
			}
			if (user.getImg_name() == null) {
			} else {
				imgname = user.getImg_name();
				sql+="IMG_NAME='"+imgname+"',";
			}
				sex = user.getSex();
				sql+="SEX='"+sex+"',";
			if (user.getBirthday() == null) {
			} else {
				birthday = user.getBirthday().toString();
				sql+="BIRTHDAY='"+birthday+"',";
			}
			if(sql.endsWith(",")){
				sql=sql.substring(0,sql.length()-1);
			}
			sql+=" where USER_ID='"+user_id+"'";
			System.out.println(sql);
			updateFlag = true;
			break;
		case RequestCode.CAT_RESUME:// CAT_RESUME
			user_id = request.getParameter("USER_ID");
			sql = rm.getSearchSql("RESUME", "USER_ID", user_id);
			tableType = new Resume();
			break;
		case RequestCode.EDIT_RESUME:// EDIT_RESUME
			gson = new GsonBuilder().create();
			userstr = request.getParameter("RESUME");
			Resume resume = gson.fromJson(userstr, Resume.class);
			// JSONObject jsonObj = JSONObject.fromObject(json);
			user_id = null;
			byte resume_no = 0;
			String education_experience=null;
			String major=null;
			String job_experience=null;
			sql="update RESUME set ";
			if (resume.getUser_id() == null) {
				nullParament = "USER_ID";
			} else {
				user_id = resume.getUser_id();
			}
			if (resume.getResume_no() == 0) {
				nullParament = "RESUME_NO";
			} else {
				resume_no = resume.getResume_no();
			}
			if (resume.getEducation_experience() == null) {
			} else {
				education_experience = resume.getEducation_experience();
				education_experience = new String(education_experience.getBytes("iso-8859-1"), "utf-8");
				sql+="EDUCATION_EXPERIENCE='"+education_experience+"',";
			}
			if (resume.getMajor() == null) {
			} else {
				sign = resume.getMajor();
				sql+="MAJOR='"+major+"',";
			}
			if (resume.getJob_experience() == null) {
			} else {
				job_experience = resume.getJob_experience();
				job_experience = new String(job_experience.getBytes("iso-8859-1"), "utf-8");
				sql+="JOB_EXPERIENCE='"+job_experience+"',";
			}
			if(sql.endsWith(",")){
				sql=sql.substring(0,sql.length()-1);
			}
			sql+=" where USER_ID='"+user_id+"' and RESUME_NO="+resume_no;
			System.out.println(sql);
			updateFlag = true;
			break;
		case RequestCode.CAT_MARK_INFO:// CAT_MARK
			user_id = request.getParameter("USER_ID");
			// sql = rm.getSearchSql("MARK_INFO", "USER_ID", user_id);
			sql = "select * from POST_INFORMATION "
					+ "where POST_ID=any(select POST_ID from MARK_INFO "
					+ "where USER_ID='" + user_id + "')";
			tableType = new Post_information();
			break;
		case RequestCode.CAT_MARK_COM:// CAT_WATCH
			user_id = request.getParameter("USER_ID");
			sql = rm.getSearchSql("MARK_COM", "USER_ID", user_id);
			tableType = new Mark_com();
			break;
		case RequestCode.CAT_WILLING:// CAT_WILLING
			user_id = request.getParameter("USER_ID");
			sql = rm.getSearchSql("MARK_INFO", "USER_ID", user_id);
			tableType = new Mark_info();
			break;
		case RequestCode.EDIT_WILLING:// EDIT_WILLING
			break;
		case RequestCode.WATCH_CMP:// WATCH_CMP
			user_id = request.getParameter("USER_ID");
			String company_id = request.getParameter("COMPANY_ID");
			sql = "insert into MARK_COM values(\"" + user_id + "\","
					+ company_id + ")";
			updateFlag = true;
			break;
		case RequestCode.UNWATCH_CMP:// UNWATCH_CMP
			user_id = request.getParameter("USER_ID");
			company_id = request.getParameter("COMPANY_ID");
			sql = "delete from MARK_COM where USER_ID=" + user_id
					+ "&COMPANY_ID=" + company_id;
			updateFlag = true;
			break;
		case RequestCode.GET_RECOMAND:// GET_RECOMAND
			break;
		case RequestCode.SEARCH_BY_PLACES:// GET_RECOMAND
			String place_name = request.getParameter("PLACE_NAME");
			if (place_name == null) {
				nullParament = "PLACE_NAME";
			}
			// sql = rm.getSearchSql("PLACE", "PLACE_NAME", place_name);
			place_name = new String(place_name.getBytes("iso-8859-1"), "utf-8");
			sql = "select * from POST_INFORMATION where WORK_PLACE like \'%"
					+ place_name + "%\'";
			tableType = new Post_information();
			break;
		case RequestCode.SEARCH_BY_COMPANY:// GET_RECOMAND
			String company_name = request.getParameter("COMPANY_NAME");
			if (company_name == null) {
				nullParament = "COMPANY_NAME";
			}
			company_name = new String(company_name.getBytes("iso-8859-1"),
					"utf-8");
			sql = "select * from POST_INFORMATION where COMPANY_NAME like \'%"
					+ company_name + "%\'";
			tableType = new Post_information();
			break;
		case RequestCode.SEARCH_BY_INDUSTRY:// GET_RECOMAND
			String industry_name = request.getParameter("CATEGORY_NAME");
			if (industry_name == null) {
				nullParament = "CATEGORY_NAME";
			}
			industry_name = new String(industry_name.getBytes("iso-8859-1"),
					"utf-8");
			sql = "select * from POST_INFORMATION where CATEGORY_NAME like \'%"
					+ industry_name + "%\'";
			tableType = new Post_information();
			break;
		case RequestCode.SEARCH_BY_POST_ID:// GET_RECOMAND
			post_id = request.getParameter("POST_ID");
			if (post_id == null) {
				nullParament = "POST_ID";
			}
			sql = rm.getSearchSql("POST_INFORMATION", "POST_ID", post_id);
			tableType = new Post_information();
			break;
		case RequestCode.QUERY_COUNT_MARK_COMPANY:
			company_id = request.getParameter("COMPANY_ID");
			sql = "select count(*) from MARK_COM where COMPANY_ID in ('"
					+ company_id + "')";
			tableType = new Count();
			break;
		case RequestCode.QUERY_COUNT_MARK_POST:
			post_id = request.getParameter("POST_ID");
			sql = "select count(*) from MARK_INFO where POST_ID in ('"
					+ post_id + "')";
			tableType = new Count();
			break;
		case RequestCode.SEARCH_BY_WILLINGS:// ??
			user_id = request.getParameter("USER_ID");
			if (user_id == null) {
				nullParament = "USER_ID";
			}
			sql = "select * from POST_INFORMATION "
					+ "where CATEGORY_NAME=any(select CATEGORY_NAME from INDUSTRY_CATEGORY "
					+ "where CATEGORY_ID =any(select CATEGORY_ID from WILLINGS where USER_ID="
					+ user_id
					+ ")) and "
					+ "WORK_PLACE=any(select PLACE_NAME from PLACE where PLACE_ID=any(select WORK_PLACE from WILLINGS "
					+ "where USER_ID=" + user_id + "))";
			tableType = new Post_information();
			break;
		case RequestCode.SEARCH_BY_MULTI_VALUE:
			String placeNameList = request.getParameter("PLACE_NAME");
			String industryNameList = request.getParameter("INDUSTRY_NAME");
			placeNameList = new String(placeNameList.getBytes("iso-8859-1"),
					"utf-8");
			industryNameList = new String(
					industryNameList.getBytes("iso-8859-1"), "utf-8");
			sql = "select * from POST_INFORMATION where WORK_PLACE in ('"
					+ placeNameList + "') and CATEGORY_NAME in ('"
					+ industryNameList + "')";
			tableType = new Post_information();
			break;
		case RequestCode.ADD_WILLINGS:
			user_id = request.getParameter("USER_ID");
			String willing_no = request.getParameter("WILLING_NO");
			String work_place = request.getParameter("WORK_PLACE");
			String category_id = request.getParameter("CATEGORY_ID");
			sql = "insert into WILLINGS (USER_ID,WILLING_NO,WORK_PLACE,CATEGORY_ID) values(\""
					+ user_id
					+ "\","
					+ willing_no
					+ ","
					+ work_place
					+ ","
					+ category_id + ")";
			updateFlag = true;
			System.out.println(sql);
			break;
		case RequestCode.SEARCH_BY_WORK_TYPE:
			String position_type = request.getParameter("POSITION_TYPE");
			if (position_type == null) {
				nullParament = "POSITION_TYPE";
			}
			position_type = new String(position_type.getBytes("iso-8859-1"),
					"utf-8");
			sql = "select * from POST_INFORMATION where POSITION_TYPE like \'%"
					+ position_type + "%\'";
			tableType = new Post_information();
			break;
		case RequestCode.TEST_JSON:
			// Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
			// .create();
			// String userstr = request.getParameter("USER_INFO");
			// BaseUser user = gson.fromJson(userstr, BaseUser.class);
			// // JSONObject jsonObj = JSONObject.fromObject(json);
			// user_id=null;
			// String password=null;
			// String nickname=null;
			// String sign=null;
			// String imgname=null;
			// int sex=0;
			// String birthday=null;
			// if(user.getUserid()==null){
			// nullParament="USER_ID";
			// }else{
			// user_id=user.getUserid();
			// }
			// if(user.getPassword()==null){
			// password="1";
			// }else{
			// password=user.getPassword();
			// }
			// if(user.getNickname()==null){
			// nickname="1";
			// }else{
			// nickname=user.getNickname();
			// }
			// if(user.getSign()==null){
			// sign="1";
			// }else{
			// sign=user.getSign();
			// }
			// if(user.getImgName()==null){
			// imgname="1";
			// }else{
			// imgname=user.getImgName();
			// }
			// if(user.getBirthday()==null){
			// birthday="2017-03-13";
			// }else{
			// birthday=user.getBirthday().toString();
			// }
			//
			// sql = "insert into USER values('" + user_id + "','"
			// + password + "','" + nickname + "','"
			// + sign + "','" + imgname + "',"
			// + sex + ",'" + birthday + "')";
			// updateFlag = true;
			// break;
		case 1000:
			sql = "select * from PLACE where PLACE_NAME=\'苏州\'";
			tableType = new Place();
			break;
		default:
			out.println("Error requestcode");
			return;
		}
		if (exceptionName != null) {
			out.println(exceptionName);
		} else if (nullParament != null) {
			out.println(nullParament + ": No parament input");
		} else if (updateFlag == true) {
			if (rm.getUpdateResult(sql) == 0)
				out.println("Update fail");
			else
				out.println("Update success");

		} else {
			if (limit != null)// 在sql语句添加limit
				sql = sql + " limit " + limit;
			else
				sql = sql + " limit 10";// 到这步SQL语句生成完毕
			System.out.println(sql);
			result = rm.getSearchResult(tableType, sql, judgeName);
			out.println(result);
			System.out.println(result);
			out.flush();
			out.close();
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	// public void doPost(HttpServletRequest request, HttpServletResponse
	// response)
	// throws ServletException, IOException {
	//
	// response.setContentType("text/html");
	// response.setCharacterEncoding("UTF-8");
	// String limit = request.getParameter("limit");
	// if (request.getParameter("requestcode") == null) {
	// return;
	// }
	// int requestCode = Integer.parseInt(request.getParameter("requestcode"));
	// PrintWriter out = response.getWriter();
	// String sql = null;
	// RequestMethods rm = new RequestMethods();
	// out.println(ResultCode.USER_UNEXISTS);
	// switch (requestCode) {
	// case RequestCode.USER_EXISTS: // USER_EXISTS
	// String user_id = request.getParameter("userid");
	// if (rm.user_exists(user_id))
	// out.println(ResultCode.USER_EXISTS);
	// else
	// out.println(ResultCode.USER_UNEXISTS);
	// break;
	// case RequestCode.UPLOAD_USER: // UPLOAD_USER
	// break;
	// case RequestCode.CAT_BY_CATEGRY: // CAT_BY_CATEGRY
	// break;
	// case RequestCode.SEARCH: // SEARCH
	// break;
	// case RequestCode.GET_PLACES: // GET_PLACES
	// String result = rm.get_places();
	// out.println(result);
	// break;
	// case RequestCode.GET_INDUSTRY: // GET_INDUSTRY
	// break;
	// case RequestCode.GET_COMPANY: // GET_COMPANY
	// break;
	// case RequestCode.MARK_POST: // MARK_POST
	// break;
	// case RequestCode.UNMARK_POST: // UNMARK_POST
	// break;
	// case RequestCode.HIDE_POST:// HIDE_POST
	// break;
	// case RequestCode.CAT_USER:// CAT_USER
	// break;
	// case RequestCode.EDIT_USER:// EDIT_USER
	// break;
	// case RequestCode.CAT_RESUME:// CAT_RESUME
	// break;
	// case RequestCode.EDIT_RESUME:// EDIT_RESUME
	// break;
	// case RequestCode.CAT_MARK:// CAT_MARK
	// break;
	// case RequestCode.CAT_WATCH:// CAT_WATCH
	// break;
	// case RequestCode.CAT_WILLING:// CAT_WILLING
	// break;
	// case RequestCode.EDIT_WILLING:// EDIT_WILLING
	// break;
	// case RequestCode.WATCH_CMP:// WATCH_CMP
	// break;
	// case RequestCode.UNWATCH_CMP:// UNWATCH_CMP
	// break;
	// case RequestCode.GET_RECOMAND:// GET_RECOMAND
	// break;
	// default:
	// out.println("Error requestcode");
	// break;
	// }
	// // 关闭RequestMethods的实例化对象
	// rm.close();
	// // if (limit != null && !limit.isEmpty())
	// // out.println(new Demo().query(requestCode,limit));
	// // else
	// // out.println(new Demo().query(requestCode,"10"));
	// out.flush();
	// out.close();
	// }

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
