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
		String nullParament = null;
		switch (requestCode) {
		case RequestCode.USER_EXISTS:
			// USER_EXISTS
			String user_id = request.getParameter("USER_ID");
			if (user_id == null) {
				nullParament = ResultCode.USER_EXISTS;
			}
			sql = rm.getSearchSql("USER", "USER_ID", user_id);
			judgeName = ResultCode.USER_EXISTS;
			tableType = new User();
			break;
		case RequestCode.UPLOAD_USER: // UPLOAD_USER
			break;
		case RequestCode.CAT_BY_CATEGRY: // CAT_BY_CATEGRY
			break;
		case RequestCode.SEARCH: // SEARCH
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
			break;
		case RequestCode.UNMARK_POST: // UNMARK_POST
			break;
		case RequestCode.HIDE_POST:// HIDE_POST
			break;
		case RequestCode.CAT_USER:// CAT_USER
			user_id=request.getParameter("USER_ID");
			sql = rm.getSearchSql("USER","USER_ID",user_id);
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
			sql=rm.getSearchSql("MARK_INFO","USER_ID",user_id);
			tableType=new Mark_info();
			break;
		case RequestCode.CAT_MARK_COM:// CAT_WATCH
			sql = rm.getSearchSql("MARK_COM");
			tableType = new Mark_com();
			break;
		case RequestCode.CAT_WILLING:// CAT_WILLING
			break;
		case RequestCode.EDIT_WILLING:// EDIT_WILLING
			break;
		case RequestCode.WATCH_CMP:// WATCH_CMP
			break;
		case RequestCode.UNWATCH_CMP:// UNWATCH_CMP
			break;
		case RequestCode.GET_RECOMAND:// GET_RECOMAND
			break;
		default:
			out.println("Error requestcode");
			return;
		}
		if (limit != null)// 在sql语句添加limit
			sql = sql + " limit " + limit;
		else
			sql = sql + " limit 10";// 到这步SQL语句生成完毕
		if (nullParament == null) {
			result = rm.getSearchResult(tableType, sql, judgeName);
			out.println(result);
			out.flush();
			out.close();
		}else{
			out.println(nullParament+"No parament input");
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
