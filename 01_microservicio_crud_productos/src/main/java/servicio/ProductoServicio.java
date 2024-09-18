package servicio;

import java.util.List;

import modelo.Producto;

public interface ProductoServicio {
	List<Producto> obtenerProductos();
	List<Producto> obtenerProductosPorCategoria(String categoria);
	Producto obtenerProductoPorCodigo(int codProducto);
	boolean crearProducto(Producto producto);
	Producto actualizarPrecioProducto(int codProducto, double precio);
	Producto eliminarProducto(int codProducto);
}
