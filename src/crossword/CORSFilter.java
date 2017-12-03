package crossword;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;


/**
 * Filter for cross-origin resource sharing.
 * @author yihed
 */
@WebFilter(filterName = "CORSFilter",
urlPatterns = {"/*"})
public class CORSFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException{
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		
		next.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig config){		
	}
	
	@Override
	public void destroy(){		
	}
}

