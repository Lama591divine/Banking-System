package com.github.lama591divine;

import com.github.lama591divine.dto.badStatus.ErrorDto;
import com.github.lama591divine.dto.badStatus.NotFoundResponse;
import com.github.lama591divine.dto.badStatus.NotValidResponse;
import com.github.lama591divine.exception.AccountNotFoundException;
import com.github.lama591divine.exception.UserNotFoundException;
import com.github.lama591divine.exception.ValidationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundResponse getUserNotFoundResponse(UserNotFoundException e) {
        return new NotFoundResponse(e.getMessage(), e.getLogin());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundResponse getAccountNotFoundResponse(AccountNotFoundException e) {
        return new NotFoundResponse(e.getMessage(), e.getId());
    }

    @ExceptionHandler({
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            ValidationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public NotValidResponse handleBadRequestExceptions(Exception e) {
        return new NotValidResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleAnyException(Exception ex) {
        return new ErrorDto("INTERNAL_ERROR", ex.getMessage());
    }
}
