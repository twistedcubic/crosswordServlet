package crossword;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import crossword.Crossword.PuzzleNodeCoordinates;

@WebServlet("/crossword")
public class CrosswordServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4973253881670428154L;
	private static final Logger logger = Logger.getLogger("CrosswordServlet");
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	
	} 
	
	 @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// run jar, put result in response
			
			response.setContentType("application/json");
			//get the session corresponding to the this request, depending on 
			//user data such as IP probably. If no session associated with this 
			//user yet, create a new session for this user.
			//HttpSession curSession = request.getSession();
			
			/*
			 * rowCoordinatesList, colCoordinatesList,
					labelList, horIntWordMap, verIntWordMap
			 */
			
			PrintWriter responseWriter = response.getWriter();
			
			//get parameters from request, in particular the string to parse
			String[] wordsAr = request.getParameterValues("words");
			
			logger.info("inputStr: " + Arrays.toString(wordsAr));
			
			if(wordsAr == null || wordsAr.length == 0) return;
			
			List<PuzzleNodeCoordinates> coordinatesList 
				= Crossword.processSet(Arrays.asList(wordsAr));
			
			/*Gson gson = new GsonBuilder().create();
			java.lang.reflect.Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
			String json = gson.toJson( , listType);*/
			
			String parsedJson = new Gson().toJson(coordinatesList);			
			responseWriter.write(parsedJson);
			
		}
	
}
