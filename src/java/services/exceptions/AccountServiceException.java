package services.exceptions;

public class AccountServiceException extends Exception {
    public AccountServiceException(Throwable throwable) {
        super(throwable);
    }
}