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
			new Producto(101,"Leche","Alimentación",1.20,15),
			new Producto(102,"Jabón","Limpieza",0.89,30),
			new Producto(103,"Mesa","Hogar",125,4),
			new Producto(104,"Televisión","Hogar",650,10),
			new Producto(105,"Huevos","Alimentación",2.20,30),
			new Producto(106,"Fregona","Limpieza",3.40,6),
			new Producto(107,"Detergente","Limpieza",8.7,12)));
	
	@Override
	public Flux<Producto> obtenerProductos() {
		return Flux.fromIterable(PRODUCTOS)
				.delayElements(Duration.ofMillis(1500));
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
