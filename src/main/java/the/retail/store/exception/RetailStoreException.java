package the.retail.store.exception;

public class RetailStoreException extends RuntimeException{

	public RetailStoreException(String message) {
        super(message);
    }
	
	public RetailStoreException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
