package guru.springframework.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Created by piyush.b.kumar on May 25, 2018.
 */
//@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(NumberFormatException.class)
//	public ModelAndView handleNumberFormatException(Exception exception) {
//		log.error("Handling number format exception");
//		log.error(exception.getMessage());
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("400error");
//		modelAndView.addObject("exception", exception);
//		return modelAndView;
//	}

}
