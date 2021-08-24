package tacos.controller.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * At start-up, Spring Boot tries to find a mapping for /error. By convention, a
 * URL ending in /error maps to a logical view of the same name: error. In
 * taco-cloud application this view maps in turn to the error.html Thymeleaf
 * template. If no view-resolver mapping for /error can be found, Spring Boot
 * defines its own fall-back error page - “Whitelabel Error Page” (a minimal
 * page with just the HTTP status information and any error details, such as the
 * message from an uncaught exception). For machine clients, it produces a JSON
 * response with details of the error, the HTTP status, and the exception
 * message.<br>
 * <br>
 * <tt>TacoErrorController</tt> runs custom logic when errors occur. Controller
 * handles calls to the /error path. The <tt>handleError</tt> method returns the
 * custom error page.<br>
 * <br>
 * Note: <b>all exceptions are logged by Spring Boot by default</b>, so the
 * errors don’t have to be logged again in custom controller class.<br>
 * <br>
 * 
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@Controller
public class TacoErrorController implements ErrorController {

	private Logger logger = LoggerFactory.getLogger(TacoErrorController.class);

	@RequestMapping("/error")
	public String handleError(HttpServletRequest httpServletRequest) {

		String errorPage = "error/error"; // default error view
		Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		// log error details
		// can be commented out (see TacoErrorController javadoc)
		logger.info("An Error Occured -> \nError status code: "
				+ httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) + "\nCause: "
				+ httpServletRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION) + "\nError request URI: "
				+ httpServletRequest.getAttribute(RequestDispatcher.ERROR_REQUEST_URI) + "\nError message: "
				+ httpServletRequest.getAttribute(RequestDispatcher.ERROR_MESSAGE));

		// If pages specifically for 401, 404 and 500 error types are designed use the
		// HTTP status code of the error to determine a suitable error page to display
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				errorPage = "error/error_500";
			} else if (statusCode == HttpStatus.NOT_FOUND.value()) {
				errorPage = "error/error_404";
			} else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
				errorPage = "error/error_401";
			}
		}

		return errorPage;
	}
}
