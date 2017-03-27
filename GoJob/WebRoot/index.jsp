<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import = "com.lazysong.test.*" %>
<%@ page import="java.sql.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my JSP page. <br>
    <%
	    /* Demo demo = new Demo();
		String result = demo.test();
		out.println(result); */
		
		/* String driver = "com.mysql.jdbc.Driver";

// URL指向要访问的数据库名test1

String url = "jdbc:mysql://192.168.18.233:3306/python";

// MySQL配置时的用户名

String user = "root";

// Java连接MySQL配置时的密码

String password = "";

try {

// 1 加载驱动程序

Class.forName(driver);

// 2 连接数据库

Connection conn = DriverManager.getConnection(url, user, password);

// 3 用来执行SQL语句

Statement statement = conn.createStatement();

// 要执行的SQL语句

String sql = "select * from user";

ResultSet rs = statement.executeQuery(sql);
String userid = null;
String nickname=null;
while (rs.next()) { 
userid = rs.getString("userid");	
nickname = rs.getString("nickname"); 
out.println(userid+"\t"+nickname); 
}	
rs.close();
conn.close();
} catch (ClassNotFoundException e) {
System.out.println("Sorry,can`t find the Driver!");
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
} catch (Exception e) {
e.printStackTrace();
} */

%>
  </body>
</html>
