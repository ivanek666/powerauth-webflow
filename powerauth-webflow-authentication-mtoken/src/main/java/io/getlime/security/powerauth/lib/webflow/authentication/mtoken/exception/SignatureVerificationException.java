package io.getlime.security.powerauth.lib.webflow.authentication.mtoken.exception;

import io.getlime.security.powerauth.rest.api.base.exception.PowerAuthAuthenticationException;

/**
 * Exception thrown when signature could not be verified.
 *
 * @author Roman Strobl, roman.strobl@lime-company.eu
 */
public class SignatureVerificationException extends PowerAuthAuthenticationException {

    public SignatureVerificationException() {
        super("Unable to verify signature in Mobile Token API component.");
    }
}
