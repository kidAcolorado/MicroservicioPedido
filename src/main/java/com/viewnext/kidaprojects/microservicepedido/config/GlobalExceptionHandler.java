package com.viewnext.kidaprojects.microservicepedido.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.viewnext.kidaprojects.microservicepedido.exception.StockInsuficienteException;
import com.viewnext.kidaprojects.microservicepedido.exception.UnknownErrorException;

/**
 * Clase {@code GlobalExceptionHandler} que proporciona un controlador de excepciones globales
 * para manejar errores relacionados con solicitudes en formato JSON y argumentos inválidos.
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 3 de Octubre de 2023
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_FORMAT = "Error de formato en la solicitud JSON.";
    private static final String INVALID_ARGUMENT = "Argumento inválido para la solicitud.";

    /**
     * Maneja la excepción {@code HttpMessageNotReadableException} que se produce cuando
     * una solicitud no se puede leer debido a un formato JSON no válido.
     *
     * @param ex La excepción {@code HttpMessageNotReadableException} que se produjo.
     * @return Una respuesta HTTP con un estado de error 400 (Bad Request) y un mensaje de error.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_FORMAT);
    }

    /**
     * Maneja la excepción {@code NumberFormatException} que se produce cuando se intenta
     * convertir una cadena en un número y el formato es inválido.
     *
     * @param ex La excepción {@code NumberFormatException} que se produjo.
     * @return Una respuesta HTTP con un estado de error 400 (Bad Request) y un mensaje de error.
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ARGUMENT);
    }
    
    /**
     * Maneja la excepción {@code StockInsuficienteException} que se produce cuando no hay suficiente stock para un pedido.
     *
     * @param ex La excepción {@code StockInsuficienteException} que se produjo.
     * @return Una respuesta HTTP con un estado de error 409 (Conflict) y un mensaje de error.
     */
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<String> handleStockInsuficienteException(StockInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Stock insuficiente");
    }

    /**
     * Maneja la excepción {@code UnknownErrorException} que se produce cuando ocurre un error desconocido al crear un pedido.
     *
     * @param ex La excepción {@code UnknownErrorException} que se produjo.
     * @return Una respuesta HTTP con un estado de error 500 (Internal Server Error) y un mensaje de error.
     */
    @ExceptionHandler(UnknownErrorException.class)
    public ResponseEntity<String> handleUnknownErrorException(UnknownErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
    
    
}
