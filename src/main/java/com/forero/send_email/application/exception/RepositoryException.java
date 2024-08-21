package com.forero.send_email.application.exception;

import com.forero.send_email.domain.exception.CodeException;

public class RepositoryException extends CoreException {
    public RepositoryException(CodeException codeException, Exception exception, String... fields) {
        super(codeException, exception, fields);
    }
}
