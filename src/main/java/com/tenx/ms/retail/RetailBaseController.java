package com.tenx.ms.retail;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tenx.ms.commons.rest.APIError;
import com.tenx.ms.commons.rest.SystemError;
import com.tenx.ms.retail.exception.RetailDeleteConstraintException;
import com.tenx.ms.retail.exception.UniqueKeyViolationException;


public class RetailBaseController {
	@ExceptionHandler(UniqueKeyViolationException.class)
	public ResponseEntity<SystemError> handleIOException(UniqueKeyViolationException ex, HttpServletRequest request) {
		return	new ResponseEntity<SystemError>(new SystemError(ex.getMessage()
				, HttpStatus.PRECONDITION_FAILED.value()
				, ex), HttpStatus.PRECONDITION_FAILED);
	}
	
	@ExceptionHandler(RetailDeleteConstraintException.class)
	public ResponseEntity<APIError> handleDeleteException(RetailDeleteConstraintException ex, HttpServletRequest request) {
		return new ResponseEntity<APIError>(new APIError(ex.getMessage(), HttpStatus.PRECONDITION_FAILED.value(), ex)
				, HttpStatus.PRECONDITION_FAILED);
	}
}
