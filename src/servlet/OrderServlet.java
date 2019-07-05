package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Cart;
import entity.Order;
import entity.User;
import mysql.DataBase;


public class OrderServlet extends HttpServlet {
	public static List<Cart> cart=new ArrayList<Cart>();
	//备份cart 生成订单时用
	public static List<Cart> cart_=new ArrayList<Cart>();
	//订单号 list
	public static List<String> OrderId = new ArrayList<String>();
	//订单详情 List
	public static List<Order> order = new ArrayList<Order>();
	//时间+随机数
	public static String getOrderIdByTime() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate=sdf.format(new Date()); 
		String result="";
		Random random=new Random();
		for(int i=0;i<3;i++) {
			result+=random.nextInt(10);
			
		}
		return newDate+result;
	}
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		if("/confirm.order".equals(path)){
			confirm(req,resp);
		}
		if("/generate.order".equals(path)){
			generate(req,resp);
		}
		if("/showOrders.order".equals(path)){
			showOrders(req,resp);
		}
		if("/detail.order".equals(path)){
			detail(req,resp);
		}
		
	}
	//订单详情
	protected void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String OrderId=req.getParameter("id");
		DataBase db=new DataBase();
		ResultSet rs = db.getData("SELECT * FROM order_ where id='"+OrderId+"'");
		try {
			while(rs.next()) {
				Order o=new Order();
				o.setUsername(rs.getString("un"));
				o.setGoodsname(rs.getString("goodsname"));
				o.setNumber(rs.getInt("number"));
				o.setPrice(rs.getDouble("price"));
				o.setId(OrderId);
				order.add(o);
			}
			
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		req.setAttribute("order", order);
		req.getRequestDispatcher("orderDetail.jsp").forward(req, resp);
		order.clear();
		db.close();
		
	}
	//查询历史订单
	protected void showOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DataBase db=new DataBase();
		User u=(User)req.getSession().getAttribute("user");
		ResultSet rs = db.getData("SELECT distinct id FROM order_ where un='"+u.getUsername()+"' ");
		try {
			while(rs.next()) {
				String id=rs.getString("id");
				OrderId.add(id);
			}
			
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		req.setAttribute("OrderId", OrderId);
		req.getRequestDispatcher("historyOrders.jsp").forward(req, resp);
		OrderId.clear();
		db.close();
	}
	//生成订单
	protected void generate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=getOrderIdByTime();
		User u=(User)req.getSession().getAttribute("user");
		String username=u.getUsername();
		DataBase db=new DataBase();
		for(Cart c:cart_) {
			String sql="insert into order_(id,un,goodsname,number,price) values('"+id+"','"+username+"','"+c.getGoodsname()+"',"+c.getNumber()+","+c.getPrice()+")";
			db.setData(sql);
		}
		db.setData("DELETE FROM cart");
		req.setAttribute("id", id);
		req.getRequestDispatcher("success.jsp").forward(req, resp);
		cart_.clear();
		db.close();
	}
	//确认订单
	protected void confirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u=(User)req.getSession().getAttribute("user");
		String username=u.getUsername();
		DataBase db=new DataBase();
		ResultSet rs = db.getData("SELECT * FROM cart where un='"+username+"'");
		
		try {
			while(rs.next()) {
				Cart c=new Cart();
				c.setGoodsname(rs.getString(1));
				c.setNumber(rs.getInt(2));
				c.setPrice(rs.getDouble(3));
				c.setUsername(username);
				cart.add(c);
				
			}
			
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		req.setAttribute("cart", cart);
		req.getRequestDispatcher("order.jsp").forward(req, resp);
		//备份 cart
		cart_.clear();
		cart_.addAll(cart);
		//清空
		cart.clear();
		db.close();
	}

}
