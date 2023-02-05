package apptive.fruitable.advice;

import apptive.fruitable.board.exception.NoTagsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoTagsException.class)
    public String tagsNoSuch(NoTagsException e) { return e.getMessage(); }
}
