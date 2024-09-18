package controlador;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import modelo.Pedido;
import servicio.PedidoServicio;

@AllArgsConstructor
@RestController
public class PedidoControlador {
	private final PedidoServicio pedidoServicio;
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("pedidos")
	public void registarPedido(@RequestBody Pedido pedido) {
		pedidoServicio.registrarPedido(pedido);
	}
}
