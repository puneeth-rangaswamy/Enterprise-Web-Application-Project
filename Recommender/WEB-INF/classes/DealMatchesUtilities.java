import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DealMatchesUtilities")

public class DealMatchesUtilities extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

			HashMap<String,Place> productmap=new HashMap<String,Place>();
		try
			{
				
		pw.print("<div id='content'>");
		pw.print("<div class='post'>");
		pw.print("<h2 class='title'>");
		pw.print("<a style='text-align:center;' href='#'>Welcome to BestPlaces </a></h2>");
		
		pw.print("<div class='entry'>");
		pw.print("<br> <br>");
		pw.print("<h2>Check out our most recommended places below!</h2>");
		pw.print("<br> <br>");
		pw.print("<h1>Please Login for more personalized results</h2>");
		
			String line=null;
			String TOMCAT_HOME = System.getProperty("catalina.home");

			productmap=MySqlDataStoreUtilities.getData();
			
			for(Map.Entry<String, Place> entry : productmap.entrySet())
			{
				
// 			if(selectedproducts.size()<2 && !selectedproducts.containsKey(entry.getKey()))
// 			{		
				
				
// 			BufferedReader reader = new BufferedReader(new FileReader (new File(TOMCAT_HOME+"\\webapps\\Recommender\\DealMatches.txt")));
// 			line=reader.readLine().toLowerCase();
// //		

// 			if(line==null)
// 			{
// 				pw.print("<h2 align='center'>No Offers Found</h2>");
// 				break;
// 			}	
// 			else
// 			{	
// 			do {	
			      
// 				  if(line.contains(entry.getKey()))
// 				  {
				 
// 					pw.print("<h2>"+line+"</h2>");
// 					pw.print("<br>");
// 					selectedproducts.put(entry.getKey(),entry.getValue());
// 					break;
// 				  }
				  
// 			    }while((line = reader.readLine()) != null);
			
// 			 }
			 }
			}
			
			catch(Exception e)
			{
			pw.print("<h2 align='center'>No Offers Found</h2>");
			}
		pw.print("</div>");
		pw.print("</div>");
		pw.print("<div class='post'>");
		pw.print("<h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Recommended Places</a>");
		pw.print("</h2>");
		pw.print("<div class='entry'>");
		if(productmap.size()==0)
		{
		pw.print("<h2 align='center'>No Recommendations Found</h2>");	
		}
		else
		{
		pw.print("<table id='bestseller'>");
		pw.print("<tr>");
		for(Map.Entry<String, Place> entry : productmap.entrySet()){
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+entry.getValue().getName()+"</h3>");
			pw.print("<strong>"+entry.getValue().getAddress()+"</strong>");
			pw.print("<h4>"+entry.getValue().getRating()+"</h4><ul>");
			pw.print("<li id='item'><img src='"+entry.getValue().getImage()+"' alt='' />");
		pw.print("</li><li>");
		pw.print("<form action='WriteReview' method='post'><input type='submit' class='btnreview' value='WriteReview'>");
		pw.print("<input type='hidden' name='name' value='"+entry.getValue().getName()+"'>");
		pw.print("<input type='hidden' name='address' value='"+entry.getValue().getAddress()+"'>");
		pw.print("<input type='hidden' name='imgURL' value='"+entry.getValue().getImage()+"'>");
		pw.print("<input type='hidden' name='rating' value='"+entry.getValue().getRating()+"'>");
		pw.print("<input type='hidden' name='placeID' value='"+entry.getValue().getId()+"'>");
		pw.print("</form></li></ul></div></td>");
		}
		pw.print("</tr></table>");
		}
		pw.print("</div></div></div>");
		
	}
}
