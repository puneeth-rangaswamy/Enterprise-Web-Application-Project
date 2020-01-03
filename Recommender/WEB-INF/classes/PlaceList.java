import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpSession;

@WebServlet("/PlaceList")

public class PlaceList extends HttpServlet {




	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	StringBuilder sb = new StringBuilder();
    BufferedReader reader = request.getReader();
    try {
        String line;
        while ((line = reader.readLine()) != null) {
			sb.append(line).append('\n');
        }
    } finally {
        reader.close();
	}

	MySqlDataStoreUtilities.DeletePlaces();

	JSONArray jsonarray = new JSONArray(sb.toString());
	System.out.println(jsonarray);
for (int i = 0; i < jsonarray.length(); i++) {

    JSONObject jsonobject = jsonarray.getJSONObject(i);
    String formatted_address = "";
	String place_id = "";
	String name = "";
	String image="";
	float rating=0;
	try{
		 formatted_address = jsonobject.getString("formatted_address");
		 place_id = jsonobject.getString("place_id");
		 name = jsonobject.getString("name");
	 image= jsonobject.getString("ImgURL");
	  rating=jsonobject.getFloat("rating");
	}
	catch(Exception e){

	}


	
	if(name!=null && !name.isEmpty() && image!=null && !image.isEmpty() && place_id!=null && !place_id.isEmpty() && formatted_address!=null && !formatted_address.isEmpty()   )
	{
   MySqlDataStoreUtilities.InsertPlaces( place_id,  name,  formatted_address,  image,  rating);
    // HttpSession session = request.getSession(true);
 	// 		session.setAttribute("mode", "search");
 	// 		response.sendRedirect("PlaceList");
	}
}


	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = "";
		String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		String CategoryName = request.getParameter("maker");
		String mode = request.getParameter("mode");
		
		Utilities utility = new Utilities(request, pw);
		User user= utility.getUser();
		String username = user.getName();

		String search = "search";

	
		HashMap<String,Place> allPlaces = new HashMap<String,Place> ();

		if(mode!= null && mode.equals(search)){
		try{


           System.out.println();
			allPlaces = MySqlDataStoreUtilities.getPlaces();
		}
		catch(Exception e)
		{
			
		}
	}

	else{

		allPlaces=MySqlDataStoreUtilities.getPlacesForUser(username);

	}

		HashMap<String, Place> hm = new HashMap<String, Place>();
		if(CategoryName==null){
			hm.putAll(allPlaces);
			name = "";
		}
				
	else{

		for(Map.Entry<String,Place> entry : allPlaces.entrySet())
				{

		hm.put(entry.getValue().getId(),entry.getValue());
				}

	}	


	
		utility.printHtml("Header.html");
		utility.printHtml("landingPageHeader.html");
		pw.print("<div class='landingContent'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Places</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Place> entry : hm.entrySet())
		{
			Place place = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3 id = 'nameid"+i+"'>"+place.getName()+"</h3>");
			pw.print("<strong id = 'addid"+i+"'>"+place.getAddress()+"</strong>");
			pw.print("<h4 id = 'ratingid"+i+"'>"+place.getRating()+"</h4><ul>");
			pw.print("<li id='item'><img id = 'imgid"+i+"' src='"+place.getImage()+"' alt='' /></li>");
			
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+place.getName()+"'>"+
					"<input type='hidden' name='type' value='places'>"+
					"<input type='hidden' name='address' value='"+place.getAddress()+"'>"+
					"<input type='hidden' name='rating' value='"+place.getRating()+"'>"+
					"<input type='hidden' name='imgURL' value='"+place.getImage()+"'>"+
					"<input type='hidden' name='placeID' value='"+place.getId()+"'>"+
				    "<input type='submit' value='View Details and Review' class='btnreview'></form></li>");
			
			
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}
}

