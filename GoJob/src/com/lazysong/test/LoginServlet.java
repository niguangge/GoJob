package com.lazysong.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lazysong.test.beans.Company;
import com.lazysong.test.beans.Industry_category;
import com.lazysong.test.beans.Mark_com;
import com.lazysong.test.beans.Mark_info;
import com.lazysong.test.beans.Place;
import com.lazysong.test.beans.Post_information;
import com.lazysong.test.beans.Resume;
import com.lazysong.test.beans.User;

public class LoginServlet extends HttpServlet {

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
			sql = "insert into MARK_INFO values(\"" + user_id + "\","
					+ post_id + ")";
			updateFlag = true;
			break;
		case RequestCode.UNMARK_POST: // UNMARK_POST
			user_id = request.getParameter("USER_ID");
			post_id = request.getParameter("POST_ID");
			sql = "delete from MARK_INFO where USER_ID="+user_id+"&POST_ID="+post_id;
			updateFlag = true;
			break;
		case RequestCode.HIDE_POST:// HIDE_POST
			user_id = request.getParameter("USER_ID");
			post_id = request.getParameter("POST_ID");
			sql = "insert into HIDE values(\"" + user_id + "\","
					+ post_id + ")";
			updateFlag = true;
			break;
		case RequestCode.CAT_USER:// CAT_USER
			user_id = request.getParameter("USER_ID");
			sql = rm.getSearchSql("USER", "USER_ID", user_id);
			tableType = new User();
			break;
		case RequestCode.EDIT_USER:// EDIT_USER
			break;
		case RequestCode.CAT_RESUME:// CAT_RESUME
			user_id = request.getParameter("USER_ID");
			sql = rm.getSearchSql("RESUME", "USER_ID", user_id);
			tableType = new Resume();
			break;
		case RequestCode.EDIT_RESUME:// EDIT_RESUME
			break;
		case RequestCode.CAT_MARK_INFO:// CAT_MARK
			user_id = request.getParameter("USER_ID");
			sql = rm.getSearchSql("MARK_INFO", "USER_ID", user_id);
			tableType = new Mark_info();
			break;
		case RequestCode.CAT_MARK_COM:// CAT_WATCH
			user_id = request.getParameter("USER_ID");
			sql = rm.getSearchSql("MARK_COM","USER_ID", user_id);
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
			sql = "delete from MARK_COM where USER_ID="+user_id+"&COMPANY_ID="+company_id;
			updateFlag = true;
			break;
		case RequestCode.GET_RECOMAND:// GET_RECOMAND
			break;
		case RequestCode.SEARCH_BY_PLACES:// GET_RECOMAND
			String place_name = request.getParameter("PLACE_NAME");
			if (place_name == null) {
				nullParament = "PLACE_NAME";
			}
			sql = rm.getSearchSql("PLACE", "PLACE_NAME", place_name);
			tableType = new Place();
			break;
		case RequestCode.SEARCH_BY_COMPANY:// GET_RECOMAND
			String company_name = request.getParameter("COMPANY_NAME");
			if (company_name == null) {
				nullParament = "COMPANY_NAME";
			}
			sql = rm.getSearchSql("COMPANY", "COMPANY_NAME", company_name);
			tableType = new Company();
			break;
		case RequestCode.SEARCH_BY_INDUSTRY:// GET_RECOMAND
			String industry_name = request.getParameter("CATEGORY_NAME");
			if (industry_name == null) {
				nullParament = "CATEGORY_NAME";
			}
			sql = rm.getSearchSql("INDUSTRY_CATEGORY", "CATEGORY_NAME", industry_name);
			tableType = new Industry_category();
			break;
		case RequestCode.SEARCH_BY_POST_ID:// GET_RECOMAND
			post_id = request.getParameter("POST_ID");
			if (post_id == null) {
				nullParament = "POST_ID";
			}
			sql = rm.getSearchSql("POST_INFORMATION", "POST_ID", post_id);
			tableType = new Post_information();
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
			//out.println(sql);
			result = rm.getSearchResult(tableType, sql, judgeName);
			out.println(result);
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

	// public void doPost(HttpServletRequest request, HttpServletResponse
	// response)
	// throws ServletException, IOException {
	//
	// response.setContentType("text/html");
	// response.setCharacterEncoding("UTF-8");
	// String limit = request.getParameter("limit");
	// PrintWriter out = response.getWriter();
	// if (limit != null && !limit.isEmpty())
	// out.println(new Demo().query(limit));
	// else
	// out.println(new Demo().query("10"));
	// out.flush();
	// out.close();
	// }

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
