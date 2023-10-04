package com.viewnext.kidaprojects.microservicepedido.service;



import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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
	 */
	private void updateStock(int codigo, int cantidad) throws StockInsuficienteException, EntityNotFoundException{
	    ResponseEntity<Void> response = pedidoWebClient.put()
	            .uri(uriBuilder -> uriBuilder
	                    .path("/producto")
	                    .queryParam("codigo", codigo)
	                    .queryParam("cantidad", cantidad)
	                    .build())
	            .retrieve()
	            .toBodilessEntity()
	            .block();

	    if(response!=null) {

		    if (response.getStatusCode() == HttpStatus.CONFLICT) {
		        throw new StockInsuficienteException();
		    }

		    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
		        throw new EntityNotFoundException();
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
	private double obtenerPrecio(int codigo) throws EntityNotFoundException, UnknownErrorException{
	    ResponseEntity<Double> respuestaPrecio = pedidoWebClient.get()
	            .uri("/producto/precio/{codigo}", codigo)
	            .retrieve()
	            .toEntity(Double.class)
	            .block();
	    
	   if(respuestaPrecio!=null) {
		   if (respuestaPrecio.getStatusCode().is2xxSuccessful()) {
		        return respuestaPrecio.getBody();
		    }

		    if (respuestaPrecio.getStatusCode() == HttpStatus.NOT_FOUND) {
		        throw new EntityNotFoundException();
		    }
	   }

	    

	    throw new UnknownErrorException();
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
		
			try {
				
				updateStock(codigo, cantidad);
				
				double precio = obtenerPrecio(codigo);
				
				
				Pedido pedido = new Pedido();
				pedido.setCodigo(codigo);
				pedido.setUnidades(cantidad);
				pedido.setTotal(cantidad * precio);
				pedido.setFecha(LocalDateTime.now());
				
				
				
				return pedidoRepository.save(pedido);
				
			} catch (StockInsuficienteException e) {
				throw new StockInsuficienteException();
			} catch(EntityNotFoundException e) {
				throw new EntityNotFoundException();
			} catch (UnknownErrorException e) {
				throw new UnknownErrorException();
			}
		
	}
	
	
	// Metodo que realicé la primera vez pero después descompuse en varios para una mejor lectura
	@Transactional
	public Pedido darDeAltaPedidoTest(int codigo, int cantidad) {
		ResponseEntity<Void> responseEntity = pedidoWebClient.put()
			.uri(uriBuilder -> uriBuilder
					.path("/producto")
					.queryParam("codigo", codigo)
					.queryParam("cantidad", cantidad)
					.build())
			.retrieve()
			.toBodilessEntity()
			.block();
		
		if (responseEntity.getStatusCode() == HttpStatus.CONFLICT) {
		    throw new StockInsuficienteException();
			
		}
		if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
		    throw new EntityNotFoundException();
		}
		
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
		    ResponseEntity<Double> respuestaPrecio = pedidoWebClient.get()
		    		.uri("/producto/precio/{codigo}", codigo)
		    		.retrieve()
		    		.toEntity(Double.class)
		    		.block();
		    		
		    	if(respuestaPrecio.getStatusCode() == HttpStatus.NOT_FOUND) {
				    throw new EntityNotFoundException();
				}
		    	
		    	if(respuestaPrecio.getStatusCode().is2xxSuccessful()) {
		    		Pedido pedido = new Pedido();
		    		double precio = respuestaPrecio.getBody();
		    				    		
		    		pedido.setCodigo(codigo);
		    		pedido.setUnidades(cantidad);
		    		pedido.setTotal(cantidad * precio);
		    		pedido.setFecha(LocalDateTime.now());
		    		
		    		return pedido;
		    	}
		  	
			
		}
		
		throw new UnknownErrorException(); 
				
	}

}
