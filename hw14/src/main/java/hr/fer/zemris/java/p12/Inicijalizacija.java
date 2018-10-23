package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * ServletContextListener used to initialize the context Uses
 * {@link DAOProvider} to get {@link DAO} and using its methods creates the
 * tables and fills them with data.
 * 
 * @author MC - webapp-baza predavanje
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			// kad bi bio u resources koristi ResourceBundle
			Properties properties = new Properties();
			String fileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
			InputStream fis = Files.newInputStream(Paths.get(fileName));
			properties.load(fis);

			String dbHost = properties.getProperty("host");
			String dbPort = properties.getProperty("port");
			String dbName = properties.getProperty("name");
			String dbUser = properties.getProperty("user");
			String dbPass = properties.getProperty("password");

			String dbConnectionURL = "jdbc:derby://" + dbHost + ":" + dbPort + "/" + dbName + ";user=" + dbUser
					+ ";password=" + dbPass;

			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			cpds.setJdbcUrl(dbConnectionURL);
			try {
				DAO dao = DAOProvider.getDao();
				dao.createTables(cpds);
				if (dao.isEmpty(cpds, "POLLS")) {
					dao.insertData(cpds, sce.getServletContext().getRealPath("/WEB-INF/Polls.txt"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}