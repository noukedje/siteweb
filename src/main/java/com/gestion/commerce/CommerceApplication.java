package com.gestion.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommerceApplication {

	public static void main(String[] args) {
		 /*try
	        {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection con=DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/ecommerce","root","Bangoua1980");
	            Statement stmt=con.createStatement();  
	            ResultSet rs=stmt.executeQuery("select * from categorie;");
	            System.out.println("Connected");  
	        }
	        catch(Exception e)
	        {
	            System.out.println(e);
	        }*/
		SpringApplication.run(CommerceApplication.class, args);
	}

}
