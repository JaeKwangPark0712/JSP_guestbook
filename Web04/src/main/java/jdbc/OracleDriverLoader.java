package jdbc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class OracleDriverLoader extends HttpServlet{

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Oracle JDBC Driver successfully loaded!");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
