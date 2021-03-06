package crossword;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import crossword.Crossword.PuzzleNodeCoordinates;

@WebServlet("/crossword")
public class CrosswordServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4973253881670428154L;
	private static final Logger logger = Logger.getLogger("CrosswordServlet");
	private static final Pattern COMMA_PATTERN = Pattern.compile("\\s*,\\s*");
	
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
			String words = request.getParameter("words");
			
			//System.out.println("processing Get request! words: "+words);
			
			if(words == null || words.length() == 0) { 				
				return;
			}
			String[] wordsAr = COMMA_PATTERN.split(words);
			
			//logger.info("inputStr: " + Arrays.toString(wordsAr));
			
			List<PuzzleNodeCoordinates> coordinatesList 
				= Crossword.processSet(Arrays.asList(wordsAr));
			//get max row and column numbers to place web row words and hints accordingly.
			int rowMax = 0;
			int colMax = 0;
			for(PuzzleNodeCoordinates co : coordinatesList) {
				int row = co.row();
				int col = co.col();
				if(row > rowMax) {
					rowMax = row;
				}
				if(col > colMax) {
					colMax = col;
				}
			}
			
			/*Gson gson = new GsonBuilder().create();
			java.lang.reflect.Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
			String json = gson.toJson( , listType);*/
			//System.out.println("coordinates obtained! " + coordinatesList);
			
			String parsedJson = new Gson().toJson(coordinatesList);
			String resultJson = "{"
					+ "\"rowMax\": " + rowMax
					+ ", \"colMax\": " + colMax
					+ ", \"coordinates\": "+parsedJson
					+ "}";
			//logger.info("resultJson: " +resultJson);
			responseWriter.write(resultJson);
			
		}
	
}
