package nerdinary.hackathon.domain.login.service;

import nerdinary.hackathon.global.exception.CustomException;
import nerdinary.hackathon.global.exception.ErrorCode;

public class UserException extends CustomException {
	public UserException(ErrorCode errorCode) {
		super(errorCode);
	}
}
