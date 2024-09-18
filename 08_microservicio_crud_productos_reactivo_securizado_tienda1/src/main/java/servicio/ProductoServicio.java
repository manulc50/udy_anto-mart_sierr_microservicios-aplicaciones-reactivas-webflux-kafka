package servicio;

import modelo.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoServicio {
	Flux<Producto> obtenerProductos();
	Flux<Producto> obtenerProductosPorCategoria(String categoria);
	Mono<Producto> obtenerProductoPorCodigo(int codProducto);
	Mono<Boolean> crearProducto(Producto producto);
	Mono<Producto> actualizarPrecioProducto(int codProducto, double precio);
	Mono<Producto> eliminarProducto(int codProducto);
}
