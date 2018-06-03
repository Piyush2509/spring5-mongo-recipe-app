package guru.springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by piyush.b.kumar on May 25, 2018.
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WebExchangeBindException.class)
	public String handleNumberFormatException(Exception exception, Model model) {
		log.error("Handling binding exception");
		log.error(exception.getMessage());
		model.addAttribute("exception", exception);
		return "400error";
	}

}
