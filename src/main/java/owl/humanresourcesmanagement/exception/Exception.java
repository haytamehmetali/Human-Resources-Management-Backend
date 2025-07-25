package owl.humanresourcesmanagement.exception;

import lombok.Getter;

@Getter
public class Exception extends RuntimeException {
	private ErrorType errorType;
	
	public Exception(ErrorType errorType) {
		super(errorType.getMessage());
		this.errorType = errorType;
	}
	
	public ErrorType getErrorType() {
		return errorType;
	}
}
