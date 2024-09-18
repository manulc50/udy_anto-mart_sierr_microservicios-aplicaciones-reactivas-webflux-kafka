package servicio;

import java.time.Duration;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import modelo.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import repositorio.ProductoRepositorio;

@RequiredArgsConstructor
@Service
public class ProductoServicioImpl implements ProductoServicio {
	private final ProductoRepositorio productoRepositorio;
	
	@Override
	public Flux<Producto> obtenerProductos() {
		return productoRepositorio.findAll()
				.delayElements(Duration.ofMillis(500));
	}

	@Override
	public Flux<Producto> obtenerProductosPorCategoria(String categoria) {
		return productoRepositorio.findByCategoria(categoria);
	}

	@Override
	public Mono<Producto> obtenerProductoPorCodigo(int codProducto) {
		return productoRepositorio.findById(codProducto);
	}

	@Override
	public Mono<Boolean> crearProducto(Producto producto) {
		return obtenerProductoPorCodigo(producto.getCodProducto())
				.map(prod -> false)
				.switchIfEmpty(productoRepositorio.save(producto).thenReturn(true));
	}

	@Override
	public Mono<Producto> actualizarPrecioProducto(int codProducto, double precio) {
		return obtenerProductoPorCodigo(codProducto)
				.doOnNext(producto -> producto.setPrecioUnitario(precio))
				.flatMap(producto -> productoRepositorio.save(producto));
	}

	@Override
	public Mono<Producto> eliminarProducto(int codProducto) {
		return obtenerProductoPorCodigo(codProducto)
				.flatMap(producto -> productoRepositorio.delete(producto)
						.thenReturn(producto));
	}

}
