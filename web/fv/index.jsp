<%@page import="java.sql.Connection"%>
<%@page import="org.tch.forecast.validator.db.DatabasePool"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>

<%@page import="java.net.URLEncoder"%>

<html>
  <head>
  <title>Forecaster Validator</title>
  </head>
  <body>
    <h1>Forecaster Validator</h1>
    <% 
    Connection conn = DatabasePool.getConnection();
    PreparedStatement pstmt = null;
    try { 
    
      String userName = (String)session.getAttribute("userName");
      if (userName == null || userName.equals(""))
      {
		System.out.println("being redirected..");
		RequestDispatcher dispatcher =request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
		/*response.sendRedirect("login.jsp");*/
      }else if (!"".equals(userName)) 
      {
        %>
          <p><strong><font color="#CC3333" size="+1">Welcome <%= userName %></font></strong></p>
        <%
      String sql = "select tg.group_label, tc.case_id, tc.case_label, tc.case_description, ts.status_label \n" + 
        "from test_case tc, test_group tg, test_status ts \n" + 
        "where tc.group_code = tg.group_code \n" +
        "  and tc.status_code = ts.status_code \n" +
        "order by tg.group_code, tc.case_id";
      pstmt = conn.prepareStatement(sql);
      ResultSet rset = pstmt.executeQuery();
      String lastGroupLabel = "";
      while (rset.next()) {
        if (!lastGroupLabel.equals(rset.getString(1))) {
          if (!lastGroupLabel.equals("")) {
            %>
            </table> <%
          }
          %>
           <h3><%= rset.getString(1) %></h3>
           <table border="1" cellspacing="0">
             <tr>
               <th>Test Case</th>
               <th>Test Status</th>
               <th>Description</th>
             </tr>
          <%
          lastGroupLabel = rset.getString(1);
        }
        %>
        <tr>
          <td><a href="testCase.jsp?caseId=<%= rset.getString(2) %>&userName=<%= URLEncoder.encode(userName, "UTF-8") %>"><%= rset.getString(3) %></a></td>
          <td bgcolor="<%= rset.getString(5).equals("Fail") ? "#CC3333" : ((rset.getString(5).equals("Pass") || rset.getString(5).equals("Accept")) ? "#99FF99" : (rset.getString(5).equals("Fixed") ? "#FF9933" : "#FFFFFF")) %>"><%= rset.getString(5) %></td>
          <td><%= rset.getString(4) %></td>
        </tr>
        <%
      }
    %>
    </table>
    <br>
    <% 
    }
    %>
    <h3>Login</h3>
    <form>
    Your Name
    <input type="text" name="userName" value="<%= userName %>"/>
    <input type="submit" name="action" value="Login"/>
    </form>
    <%
    } finally {DatabasePool.close(conn); } %>
  </body>
</html>