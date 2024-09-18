package controlador;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import modelo.Producto;
import servicio.ProductoServicio;

@RequiredArgsConstructor
@RestController
@RequestMapping(ProductoControlador.BASE_API_URL)
public class ProductoControlador {
	public static final String BASE_API_URL = "/productos";
	
	private final ProductoServicio productoServicio;
	
	@GetMapping
	public List<Producto> listarProductos(@RequestParam(required = false) String categoria) {
		if(categoria != null)
			return productoServicio.obtenerProductosPorCategoria(categoria);
		
		return productoServicio.obtenerProductos();
	}
	
	@GetMapping("{codigo}")
	public ResponseEntity<Producto> obtenerProductoPorCodigo(@PathVariable("codigo") int codProducto) {
		Producto producto = productoServicio.obtenerProductoPorCodigo(codProducto);
		
		if(producto == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(producto);
	}
	
	@PostMapping
	public ResponseEntity<Void> crearProducto(@RequestBody Producto producto) {
		if(!productoServicio.crearProducto(producto))
			return ResponseEntity.badRequest().build();
		
		URI uri = UriComponentsBuilder.fromUriString(BASE_API_URL + "/{codigo}").build(producto.getCodProducto());
		
		return ResponseEntity.created(uri).build();
	}
	
	@PatchMapping("{codigo}")
	public ResponseEntity<Producto> actualizarPrecioProducto(@PathVariable(value = "codigo") int codProducto,
			@RequestBody Map<String, Double> mapaPrecio) {
		Producto producto = productoServicio.actualizarPrecioProducto(codProducto, mapaPrecio.get("precioUnitario"));
	
		if(producto == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(producto, HttpStatus.OK);
	}
	
	@DeleteMapping("{codigo}")
	public ResponseEntity<Producto> eliminarProducto(@PathVariable(name = "codigo") int codProducto) {
		Producto producto = productoServicio.eliminarProducto(codProducto);
		
		if(producto == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(producto, HttpStatus.OK);
	}
}
