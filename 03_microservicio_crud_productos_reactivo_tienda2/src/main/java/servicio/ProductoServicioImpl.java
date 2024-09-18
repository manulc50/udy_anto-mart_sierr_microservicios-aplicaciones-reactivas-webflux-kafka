package servicio;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import modelo.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ProductoServicioImpl implements ProductoServicio {
	private static final List<Producto> PRODUCTOS = new ArrayList<>(List.of(new Producto(100,"Azucar","Alimentación",1.10,20),
			new Producto(111,"Pan","Alimentación",1.3,10),
			new Producto(112,"Esponja","Limpieza",2,20),
			new Producto(113,"Sofá","Hogar",80,4),
			new Producto(114,"Jarrón","Hogar",40,10),
			new Producto(115,"Harina","Alimentación",3,30),
			new Producto(116,"Fregona","Limpieza",3.40,6),
			new Producto(117,"Cubo","Limpieza",2.5,12)));
	
	@Override
	public Flux<Producto> obtenerProductos() {
		return Flux.fromIterable(PRODUCTOS)
				.delayElements(Duration.ofMillis(1700));
	}

	@Override
	public Flux<Producto> obtenerProductosPorCategoria(String categoria) {
		return obtenerProductos()
				.filter(producto -> producto.getCategoria().equals(categoria));
	}

	@Override
	public Mono<Producto> obtenerProductoPorCodigo(int codProducto) {
		return obtenerProductos()
				.filter(producto -> producto.getCodProducto() == codProducto)
				.next();
				//.defaultIfEmpty(new Producto());
				//.switchIfEmpty(Mono.just(new Producto()));
	}

	@Override
	public Mono<Boolean> crearProducto(Producto producto) {
		return obtenerProductoPorCodigo(producto.getCodProducto())
				.map(prod -> false)
				.switchIfEmpty(Mono.fromCallable(() -> PRODUCTOS.add(producto)));
	}

	@Override
	public Mono<Producto> actualizarPrecioProducto(int codProducto, double precio) {
		return obtenerProductoPorCodigo(codProducto)
				.doOnNext(producto -> producto.setPrecioUnitario(precio));
	}

	@Override
	public Mono<Producto> eliminarProducto(int codProducto) {
		return obtenerProductoPorCodigo(codProducto)
				.doOnNext(producto -> PRODUCTOS.removeIf(p -> p.getCodProducto() == producto.getCodProducto()));
	}

}
