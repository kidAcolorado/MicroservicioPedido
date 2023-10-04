package com.viewnext.kidaprojects.microservicepedido.exception;

/**
 * La clase {@code UnknownErrorException} es una excepción personalizada que se lanza cuando
 * ocurre un error desconocido al crear un pedido.
 *
 * <p>
 * Esta excepción se utiliza para indicar que se ha producido un error inesperado durante el
 * proceso de creación de un pedido y no se puede determinar la causa específica del error.
 * </p>
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 04 de Octubre de 2023
 */
public class UnknownErrorException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nueva instancia de la excepción {@code UnknownErrorException} con un mensaje
     * predeterminado.
     */
    public UnknownErrorException() {
        super("Error desconocido al crear el pedido");
    }
}
