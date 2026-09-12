package com.robindrew.taskmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Every validation/not-found failure in this module's managers (CalendarEventManager, LabelCache,
// PersonManager, AddressManager) is signalled as an IllegalArgumentException with a message aimed
// at the caller (e.g. "Label is already a PERSON label: JAN"). Left unhandled, Spring's default
// error response is a 500 with no message body, so the browser has no way to show the real reason
// - this maps it to a 400 whose body is exactly that message.
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
