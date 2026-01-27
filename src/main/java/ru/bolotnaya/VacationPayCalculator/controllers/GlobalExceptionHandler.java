package ru.bolotnaya.VacationPayCalculator.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder("Пожалуйста, проверьте входные данные: ");
        ex.getConstraintViolations().forEach(violation ->
                errorMessage.append(violation.getPropertyPath()).append(" -> ").append(violation.getMessage()).append("; ")
        );
        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        Map<String, String> error = new HashMap<>();

        if (ex.getRequiredType() == LocalDate.class) {
            error.put("Ошибка", "Неверный формат даты");
            error.put("Детали ошибки", "Дата ухода в отпуск '" + ex.getName() + "' должна быть в формате ГГГГ-ММ-ДД, например, 2026-01-23");
            error.put("Полученные данные", String.valueOf(ex.getValue()));
        } else {
            error.put("Ошибка", "Неверный тип параметра");
            error.put("Детали ошибки", "Параметр '" + ex.getName() + "' имеет неверный тип");
            error.put("Ожидание", ex.getRequiredType().getSimpleName());
            error.put("Полученные данные", String.valueOf(ex.getValue()));
        }

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParameterException(
            MissingServletRequestParameterException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("Ошибка", "Отсутствие параметра");
        error.put("Детали ошибки", "Параметр '"+ex.getParameterName()+"' обязательный");

        return ResponseEntity.badRequest().body(error);
    }
}
