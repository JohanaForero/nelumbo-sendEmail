package com.forero.send_email.application.exception;

import com.forero.send_email.domain.exception.CodeException;

import java.time.Instant;

public class RepositoryException extends CoreException {
    public RepositoryException(final CodeException codeException, final Exception exception, final Instant startDate, final Instant endDate) {
        super(codeException, exception, startDate.toString(), endDate.toString());
    }

    public RepositoryException(final CodeException codeException, final Exception exception, final String... fields) {
        super(codeException, exception, fields);
    }
}
