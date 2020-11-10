package the.retail.store.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RetailShopExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetailShopExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RetailStoreException.class)
    public ResponseEntity<String> handleConflict(RetailStoreException ex) {
        LOGGER.error("SOMETHING WENT BAD!!!");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(Throwable.class)
	public final ResponseEntity<String> handleUncaughtExceptions(Throwable ex) {
		LOGGER.error("Something went bad", ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
