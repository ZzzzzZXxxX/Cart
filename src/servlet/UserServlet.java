package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import entity.User;
import mysql.DataBase;


public class UserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		if("/login.user".equals(path)){
			login(req,resp);
		}
		if("/register.user".equals(path)){
			register(req,resp);
		}
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		if("/logout.user".equals(path)){
			logout(req,resp);
		}
		if("/check.user".equals(path)){
			check(req,resp);
		}
	}
	
	protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String phone=req.getParameter("phone");
		String address=req.getParameter("address");
		DataBase db=new DataBase();
		ResultSet rs = db.getData("SELECT * FROM user where un='"+username+"'");
		try {
			if(rs.next()) {
				req.setAttribute("msg", "用户名已注册，请重新注册！！！");
				req.getRequestDispatcher("/register.jsp").forward(req, resp);
				rs.close();
				db.close();
				return;
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		String sql="insert into user(un,pwd,phone,addr) values('"+username+"','"+password+"','"+phone+"','"+address+"')";
		db.setData(sql);
		resp.sendRedirect(req.getContextPath()+"/login.jsp");
		db.close();
	}
	protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		HttpSession session=req.getSession();
		DataBase db=new DataBase();
		ResultSet rs = db.getData("SELECT * FROM user where un='"+username+"' and pwd='"+password+"'");
		try {
			if(rs.next()) {
				User u=new User();
				u.setUsername(rs.getString(1));
				u.setPassword(rs.getString(2));
				u.setPhone(rs.getString(3));
				u.setAddress(rs.getString(4));
				session.setAttribute("user", u);
				resp.sendRedirect(req.getContextPath()+"/show.goods");
				return;
			}
			req.setAttribute("msg", "用户名或密码错误！！！");
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
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
		db.close();

	}
	
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		session.invalidate();
		resp.sendRedirect(req.getContextPath()+"/show.goods");
	}
	protected void check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u=(User)req.getSession().getAttribute("user");
		if(u==null) {
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
			return;
		}
		String tag = req.getParameter("tag");
		if("AddCart".equals(tag)) {
			String index=req.getParameter("index");
			resp.sendRedirect(req.getContextPath()+"/add.cart?index="+index);
		}
		else{
			resp.sendRedirect(req.getContextPath()+"/show.cart");
		}
	}
}
