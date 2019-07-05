package servlet;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import entity.Goods;
import mysql.DataBase;

public class GoodsServlet extends HttpServlet {
	public static List<Goods> goods=new ArrayList<Goods>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		if("/show.goods".equals(path)){
			show(req,resp);
		}
		
	}
	protected void show(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		DataBase db=new DataBase();
		ResultSet rs = db.getData("SELECT * FROM goods");
		try {
			while(rs.next()) {
				Goods g=new Goods();
				g.setGoodsname(rs.getString(2));
				g.setPrice(rs.getDouble(3));
				goods.add(g);
				
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
		req.setAttribute("goods", goods);
		req.getRequestDispatcher("list.jsp").forward(req, resp);
		goods.clear();
		db.close();
	}
}
