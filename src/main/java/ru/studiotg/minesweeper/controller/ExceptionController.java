package ru.studiotg.minesweeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.studiotg.minesweeper.dto.ErrorDto;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return ResponseEntity.badRequest().body(new ErrorDto(message));
    }

}
