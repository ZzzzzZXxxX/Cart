<%@page import="entity.User"%>
<%@page import="entity.Goods"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
List<Goods> goods=(List<Goods>)request.getAttribute("goods");
User u=(User)session.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>商品列表</h1>
	<%if(u!=null){ %>
	<b style="color: red;">用户<%=u.getUsername() %>在线... </b>
	<a href="<%=request.getContextPath()%>/logout.user">注销</a>
	<a href="<%=request.getContextPath()%>/showOrders.order">我的历史订单</a><%}else{ %>
    <a href="login.jsp">登录</a> 
    <a href="register.jsp">注册</a><%} %>
    <hr>
    
    	<table border="1">
    		<tr>
    			<th>序号</th>
    			<th>商品</th>
    			<th>价格</th>
    			<th>操作</th>
    		</tr>
    		<%
    		for(int i=1;i<=goods.size();i++){
    			Goods g=goods.get(i-1);
    		%>
    		<tr>
    			<td><%=i %></td>
    			<td><%=g.getGoodsname() %></td>
    			<td><%=g.getPrice() %></td>
    			<td><a href="<%=request.getContextPath()%>/check.user?tag=AddCart&index=<%=i %>">加入购物车</a></td>
    		</tr>
    		<% 
    		} 
    		%>
    	</table>
    <% String msg=(String)request.getAttribute("msg");%>
    <h1 style="color: red;"><%=msg!=null?msg:"" %></h1>
    <br>
    <a href="<%=request.getContextPath()%>/check.user?tag=ShowCart">查看购物车</a>
</body>
</html>