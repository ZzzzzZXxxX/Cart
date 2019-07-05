<%@page import="entity.User"%>
<%@page import="entity.Cart"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 List<Cart> cart=(List<Cart>)request.getAttribute("cart");
 User u = (User)session.getAttribute("user");
 //总计
Double sum=0.0;
for(Cart c:cart){
	sum+=c.getPrice();
}
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>确认订单</h1>
<hr>
用户名：<%=u.getUsername() %>
<br>
电话：<%=u.getPhone() %>
<br>
地址：<%=u.getAddress() %>
<hr>
<table border="1">
	<tr>
		<th>序号</th>
		<th>商品</th>
		<th>数量</th>
		<th>价格</th>
	</tr>
	<%for(int i=1;i<=cart.size();i++){
    		Cart c=cart.get(i-1);
    	%>
    	<tr>
    		<td><%=i %></td>
    		<td><%=c.getGoodsname() %></td>
    		<td><%=c.getNumber() %></td>
    		<td><%=c.getPrice() %></td>
    	</tr>
    	<%} %>
</table>
<hr>
总计：<%=sum %>元
<br>
<a href="<%=request.getContextPath()%>/generate.order">生成订单</a> <a href="<%=request.getContextPath()%>/show.cart");">返回</a>
</body>
</html>