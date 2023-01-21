package com.EarthSandwich.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerLogger {

	public class ControllerConfig {
		private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

		@ExceptionHandler
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		public void handle(HttpMessageNotReadableException e) {
			log.warn("Returning HTTP 400 Bad Request", e);
			throw e;
		}
	}
}
