<%@page import="entity.Order"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 List<Order> order=(List<Order>)request.getAttribute("order");
 int s=0;
 //总计
Double sum=0.0;
for(Order o:order){
	sum+=o.getPrice();
}
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>我的订单</h1>
<hr>
<%=order.get(0).getUsername() %>用户<%=order.get(0).getId() %>订单
<hr>
<table border="1">
	<tr>
		<th>序号</th>
		<th>商品</th>
		<th>数量</th>
		<th>价格</th>
	</tr>
	<% for(int i=1;i<=order.size();i++){
			Order o=order.get(i-1);
	 %>
	 <tr>
	 	<td><%=i %></td>
	 	<td><%=o.getGoodsname() %></td>
	 	<td><%=o.getNumber() %></td>
	 	<td><%=o.getPrice() %></td>
	 </tr>
	 <% s=i;} %>
</table>
<hr>
共<%=s %>条   总计：<%=sum %>元
<br>
<a href="<%=request.getContextPath()%>/showOrders.order">返回</a>
</body>
</html>