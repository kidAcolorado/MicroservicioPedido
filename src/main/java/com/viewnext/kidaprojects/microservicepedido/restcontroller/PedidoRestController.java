package com.viewnext.kidaprojects.microservicepedido.restcontroller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.kidaprojects.microservicepedido.exception.StockInsuficienteException;
import com.viewnext.kidaprojects.microservicepedido.exception.UnknownErrorException;
import com.viewnext.kidaprojects.microservicepedido.model.Pedido;
import com.viewnext.kidaprojects.microservicepedido.service.PedidoService;


import jakarta.persistence.EntityNotFoundException;


/**
 * El controlador REST para gestionar pedidos y productos.
 * Proporciona endpoints para obtener la lista de pedidos y dar de alta nuevos pedidos.
 * También maneja excepciones relacionadas con productos y pedidos.
 *
 * @author Víctor Colorado "Kid A"
 * @since 4 de Octubre de 2023
 */
@RestController
public class PedidoRestController {

	@Autowired
	private PedidoService service;
	
	 private static final String PEDIDO_NOT_FOUND = "Pedido/s no encontrado";
	 private static final String PRODUCTO_NOT_FOUND = "Producto/s no encontrado";
	
	 /**
	     * Obtiene la lista de todos los pedidos existentes.
	     *
	     * @return ResponseEntity con la lista de pedidos si se encuentra, o un mensaje de error si no.
	     */
	    @GetMapping(value = "pedido", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<?> showAllPedidos() {
	        try {
	            List<Pedido> listaPedidos = service.showAll();
	            return ResponseEntity.ok(listaPedidos);
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PEDIDO_NOT_FOUND);
	        }
	    }

	    /**
	     * Da de alta un nuevo pedido con el código y cantidad especificados.
	     *
	     * @param codigo   El código del producto para el pedido.
	     * @param cantidad La cantidad de unidades del producto para el pedido.
	     * @return ResponseEntity con el pedido creado si tiene éxito, o un mensaje de error si ocurre algún problema.
	     */
	    @PostMapping(value = "pedido", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<?> darDeAltaPedido(@RequestParam("codigo") int codigo, @RequestParam("cantidad") int cantidad) {
	        try {
	            Pedido pedido = service.darDeAltaPedido(codigo, cantidad);
	            return ResponseEntity.ok(pedido);
	        } catch (StockInsuficienteException e) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCTO_NOT_FOUND);
	        } catch (UnknownErrorException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	        }
	    }
	}