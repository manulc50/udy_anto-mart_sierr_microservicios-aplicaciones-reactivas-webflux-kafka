package servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import modelo.Producto;

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
	public List<Producto> obtenerProductos() {
		return PRODUCTOS;
	}

	@Override
	public List<Producto> obtenerProductosPorCategoria(String categoria) {
		return PRODUCTOS.stream()
				.filter(producto -> producto.getCategoria().equals(categoria))
				.toList();
	}

	@Override
	public Producto obtenerProductoPorCodigo(int codProducto) {
		return PRODUCTOS.stream()
				.filter(producto -> producto.getCodProducto() == codProducto)
				.findFirst()
				.orElse(null);
	}

	@Override
	public boolean crearProducto(Producto producto) {
		if(obtenerProductoPorCodigo(producto.getCodProducto()) == null) {
			PRODUCTOS.add(producto);
			return true;
		}
		
		return false;
	}

	@Override
	public Producto actualizarPrecioProducto(int codProducto, double precio) {
		Producto producto = obtenerProductoPorCodigo(codProducto);
		
		if(producto != null)
			producto.setPrecioUnitario(precio);
			
		return producto;
	}

	@Override
	public Producto eliminarProducto(int codProducto) {
		Producto producto = obtenerProductoPorCodigo(codProducto);
		
		if(producto != null)
			PRODUCTOS.removeIf(prod -> prod.getCodProducto() == codProducto);
		
		return producto;
	}

}
