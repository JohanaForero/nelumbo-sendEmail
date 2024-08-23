package com.forero.send_email.domain.exception;

public class MailExceptionDomain extends RuntimeException {
    private final CodeException codeException;

    public MailExceptionDomain(final CodeException codeException, final Exception exception, final String... fields) {
        super(getMessage(codeException, fields), exception);
        this.codeException = codeException;
    }

    private static String getMessage(final CodeException codeException, final String... fields) {
        return fields.length > 0
                ? String.format(codeException.getMessageFormat(), (Object[]) fields)
                : codeException.getMessageFormat();
    }

    public CodeException getCodeException() {
        return this.codeException;
    }

}
