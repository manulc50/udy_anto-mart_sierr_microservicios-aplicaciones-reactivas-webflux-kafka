package consumidor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import modelo.Pedido;
import servicio.ProductoServicio;

@RequiredArgsConstructor
@Component
public class PedidoConsumidor {
	private final ProductoServicio productoServicio;
	
	@KafkaListener(topics = "${topico}", groupId = "${grupo.id}")
	public void gestinarPedido(Pedido pedido) {
		productoServicio.actualizarStockProducto(pedido.getCodProducto(), pedido.getUnidades())
			.subscribe(producto -> System.out.println("Pedido " + pedido.getCodProducto() + " recibido." +
					"El stock actual del producto " + producto.getCodProducto() + " es " + producto.getStock()));
	}

}
