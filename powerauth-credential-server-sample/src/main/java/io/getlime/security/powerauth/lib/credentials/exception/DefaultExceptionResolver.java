/*
 * Copyright 2017 Lime - HighTech Solutions s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getlime.security.powerauth.lib.credentials.exception;

import io.getlime.security.powerauth.lib.credentials.model.entity.ErrorModel;
import io.getlime.security.powerauth.lib.nextstep.model.base.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller advice responsible for default exception resolving.
 *
 * @author Roman Strobl
 */
@ControllerAdvice
public class DefaultExceptionResolver {

    /**
     * Handling of unexpected errors.
     * @return Response with error information.
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    Response<ErrorModel> handleDefaultException(Throwable t) {
        Logger.getLogger(this.getClass().getName()).log(
                Level.SEVERE,
                "Error occurred in Credential server",
                t
        );
        ErrorModel error = new ErrorModel(ErrorModel.Code.ERROR_GENERIC, "Unknown Error");
        return new Response<>(Response.Status.ERROR, error);
    }

    /**
     * Hendling of authentication failures.
     * @param ex Authentication faulure exception, with exception details.
     * @return Response with error information.
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody Response<ErrorModel> handleAuthenticationError(AuthenticationFailedException ex) {
        // regular authentication failed error
        ErrorModel error = new ErrorModel(ErrorModel.Code.AUTHENTICATION_FAILED, ex.getMessage());
        return new Response<>(Response.Status.ERROR, error);
    }

    /**
     * Handling of validation errors.
     * @return Response with error information.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Response<ErrorModel> handleDefaultException(MethodArgumentNotValidException ex) {
        ErrorModel error = new ErrorModel();
        error.setCode(ErrorModel.Code.INPUT_INVALID);
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError objError: ex.getBindingResult().getAllErrors()) {
            for (String code: objError.getCodes()) {
                switch (code) {
                    case "username.empty":
                        errorMessages.add("login.username.empty");
                        break;
                    case "password.empty":
                        errorMessages.add("login.password.empty");
                        break;
                    case "username.long":
                        errorMessages.add("login.username.long");
                        break;
                    case "password.long":
                        errorMessages.add("login.password.long");
                        break;
                    case "type.unsupported":
                        errorMessages.add("login.type.unsupported");
                        break;
                    default:
                        break;
                }
            }
        }
        error.setMessage(String.join(" ", errorMessages));
        return new Response<>(Response.Status.ERROR, error);
    }

}
