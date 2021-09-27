import static spark.Spark.*;
import java.util.*;
import java.sql.*;
public class main{
	
	public static ArrayList<Object> getConnection(String Condition, int a, String b, String c) {
		try{
			String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
            String username = "raghav";
            String password = "1234";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            Statement query = con.createStatement();
            ArrayList<Object> result = new ArrayList<Object>();
            
            switch(Condition) {
            case "create":
            	query.executeUpdate("insert into demo values("+a+",'"+b+"','"+c+"')");
            	result.add("creation successful");
            	break;
            case "read":
            	ResultSet res = query.executeQuery("select * from demo");
            	result.add("[t_id, t_name, t_department] => ");
                while (res.next()){
                    result.add(res.getInt(1)+"  "+res.getString(2)+"  "+res.getString(3));  
                }
                
            	break;
            case "update":
            	query.executeUpdate("UPDATE demo SET t_Name = '"+b+"', t_Department = '"+c+"' WHERE t_id = "+a+";");
            	result.add("update successful");
            	break;
            case "delete":
            	query.executeUpdate("DELETE FROM demo WHERE t_id="+a+";");
            	result.add("deletion successful");
            	break;
            }
            return result;
            
            
       }
        catch(Exception e){
            System.out.println(e);
        }
		return null;
	}
    public static void main(String[] args) {
    	try {
    		get("/:type/:id/:name/:department", (request, response) -> {  
        		String Condition,x="None",y="None",z="None";
        		Condition = request.params(":type");
        		x = request.params(":id");
        		y = request.params(":name");
        		z = request.params(":department");
        		if (x=="None"){
        			ArrayList<Object> res = getConnection(Condition, 0, y, z);
        			return res;
        		}
        		else {
        			int a = Integer.parseInt(x) ;
        			ArrayList<Object> res = getConnection(Condition, a, y, z);
        			return res;
        	        
        		}
        		
        });
    	}
    		catch(Exception e) {
    			System.out.println( "Error: "+e );
    		}
        

        
    }
}

