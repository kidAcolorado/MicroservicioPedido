package com.viewnext.kidaprojects.microservicepedido.service;



import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.viewnext.kidaprojects.microservicepedido.exception.StockInsuficienteException;
import com.viewnext.kidaprojects.microservicepedido.exception.UnknownErrorException;
import com.viewnext.kidaprojects.microservicepedido.model.Pedido;
import com.viewnext.kidaprojects.microservicepedido.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * La clase {@code PedidoServiceImpl} implementa la interfaz {@code PedidoService} y proporciona
 * métodos para gestionar pedidos y consultar información relacionada con los pedidos.
 *
 * <p>El autor de esta clase es Víctor Colorado "Kid A".</p>
 *
 * @version 1.0
 * @since 04 de Octubre de 2023
 */
@Service
public class PedidoServiceImpl implements PedidoService{

	@Autowired
	private PedidoRepository pedidoRepository;
	
	private WebClient pedidoWebClient;
	
	public PedidoServiceImpl(WebClient pedidoWebClient) {
		this.pedidoWebClient = pedidoWebClient;
	}
	
	

	/**
	 * Recupera una lista de todos los pedidos disponibles en la base de datos.
	 *
	 * @return Una lista de objetos {@code Pedido} que representan los pedidos.
	 * @throws EntityNotFoundException Si no se encuentra ningún pedido en la base de datos.
	 */
	@Override
	public List<Pedido> showAll() throws EntityNotFoundException{
		List<Pedido> listaPedidos = pedidoRepository.findAll();
		
		if(listaPedidos.isEmpty()) {
			throw new EntityNotFoundException();
		}
		
		return listaPedidos;
	}
	
	/**
	 * Actualiza el stock de un producto en el sistema mediante una solicitud HTTP PUT a un servicio externo.
	 *
	 * @param codigo    El código del producto que se va a actualizar.
	 * @param cantidad  La cantidad de stock que se va a agregar o restar al producto.
	 * @throws StockInsuficienteException Si la actualización del stock resulta en una cantidad insuficiente.
	 * @throws EntityNotFoundException   Si no se encuentra el producto en el sistema.
	 * @throws UnknownErrorException     Si ocurre un error desconocido durante la actualización del stock.
	 */
	private void updateStock(int codigo, int cantidad) throws StockInsuficienteException, EntityNotFoundException, UnknownErrorException {
	    try {
	        // Realizar la solicitud HTTP
	        pedidoWebClient.put()
	            .uri(uriBuilder -> uriBuilder
	                .path("/producto")
	                .queryParam("codigo", codigo)
	                .queryParam("cantidad", cantidad)
	                .build())
	            .retrieve()
	            .toBodilessEntity()
	            .block();

	        // No es necesario verificar el código de estado aquí

	    } catch (WebClientResponseException e) {
	        // Manejar excepciones de WebClientResponseException
	        if (e.getStatusCode() == HttpStatus.CONFLICT) {
	            throw new StockInsuficienteException();
	        } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	            throw new EntityNotFoundException();
	        } else {
	            // Manejar otros códigos de estado o excepciones si es necesario
	            throw new UnknownErrorException();
	        }
	    }
	}

	/**
	 * Obtiene el precio de un producto a través de una solicitud HTTP GET a un servicio externo.
	 *
	 * @param codigo El código del producto del cual se desea obtener el precio.
	 * @return El precio del producto.
	 * @throws EntityNotFoundException Si el producto no se encuentra en el sistema.
	 * @throws UnknownErrorException  Si ocurre un error desconocido al obtener el precio.
	 */
	private double obtenerPrecio(int codigo) throws EntityNotFoundException, UnknownErrorException {
	    try {
	        ResponseEntity<Double> respuestaPrecio = pedidoWebClient.get()
	                .uri("/producto/precio/{codigo}", codigo)
	                .retrieve()
	                .toEntity(Double.class)
	                .block();

	        return respuestaPrecio.getBody();

	    } catch (WebClientResponseException e) {
	        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	            throw new EntityNotFoundException();
	        }
	        throw new UnknownErrorException();
	    }
	}

	/**
	 * Crea un nuevo pedido con el código y cantidad especificados, actualiza el stock del producto
	 * y almacena el pedido en la base de datos.
	 *
	 * @param codigo   El código del producto para el pedido.
	 * @param cantidad La cantidad de unidades del producto para el pedido.
	 * @return El pedido creado y almacenado en la base de datos.
	 * @throws StockInsuficienteException Si no hay suficiente stock para el producto.
	 * @throws EntityNotFoundException   Si el producto o el precio no se encuentran en el sistema.
	 * @throws UnknownErrorException     Si ocurre un error desconocido al crear el pedido.
	 */
	@Transactional
	@Override
	public Pedido darDeAltaPedido(int codigo, int cantidad) throws StockInsuficienteException, EntityNotFoundException, UnknownErrorException{
	    updateStock(codigo, cantidad);
	    double precio = obtenerPrecio(codigo);
	    
	    Pedido pedido = new Pedido();
	    pedido.setCodigo(codigo);
	    pedido.setUnidades(cantidad);
	    pedido.setTotal(cantidad * precio);
	    pedido.setFecha(LocalDateTime.now());
	    
	    return pedidoRepository.save(pedido);
	}

	
	
	

}
