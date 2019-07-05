package mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;


public class DataBase {
	private Connection con=null;
	public DataBase(){
		String url="jdbc:mysql://127.0.0.1:3306/Cart?useUnicode=true&characterEncoding=utf-8";
		String un="root";
		String pwd="12345678";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url, un, pwd);
		}catch(ClassNotFoundException e){
			System.out.println("ClassCastException!!!");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("SQLException");
			e.printStackTrace();
		}
	}
	public ResultSet getData(String sql){
		Statement stm=null;
		try{
			stm = con.createStatement();
			ResultSet result = stm.executeQuery(sql);
			System.out.println("getData！！！");
			return result;
		}catch(SQLException e){
			System.out.println("SQLException!!!");
			e.printStackTrace();
			return null;
		}
	}
	public void setData(String sql){
		Statement stm = null;
		try{
			stm = con.createStatement();
			stm.executeUpdate(sql);
			System.out.println(" setData！！！");
			
		}catch(SQLException e){
			System.out.println("SQLException!!!");
			e.printStackTrace();
			
		}finally {
			if(stm!=null) {
				try {
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void close() {
		try {
            con.close();
            System.out.println("con.close");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	

}
