package model;

import java.sql.Connection; //classe do jdbc responsavel pela conecção com o banco de dados.
import java.sql.DriverManager; // java sql driver manager

public class DAO {
	private Connection con;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String ulr = "jdbc:mysql://localhost:3306/CarometroDb";
	private String user = "root";
	private String password  = "109081";
	
	// Ciando o método connectiom, chamo private Connection e adiciono uma variavel de referencia que no caso é connectio com c min.
	public Connection conection() {    
		// Tratando exessões com o try(digito try depois ctrl+space e seleciono cath
		try {
			Class.forName(driver);			
			con = DriverManager.getConnection(ulr,user,password); //DriverManager é responsável pelo tipo de banco de dados
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
