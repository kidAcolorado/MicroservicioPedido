package com.viewnext.kidaprojects.microservicepedido.exception;

/**
 * La clase {@code StockInsuficienteException} es una excepción personalizada que se lanza
 * cuando el stock de un producto es insuficiente para realizar un pedido.
 *
 * <p>
 * Esta excepción se utiliza para indicar que no se puede completar un pedido debido a la falta
 * de existencias disponibles.
 * </p>
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 04 de Octubre de 2023
 */
public class StockInsuficienteException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nueva instancia de la excepción {@code StockInsuficienteException} con un mensaje
     * predeterminado.
     */
    public StockInsuficienteException() {
        super("Stock insuficiente. No se puede realizar el pedido");
    }
}

