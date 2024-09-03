package repository;

import java.util.List;

import model.Producto;

public interface ProductoRepository {
	
	public void crearTabla();
	
	public void insertar(Producto producto);
	
	public void actualizar (Producto producto);
	
	public void eliminar (Producto producto);
	
	public Producto obtenerPorId(int id);
	
	public Producto obtenerPorNombre(String nombre);
	
	public Producto obtenerProductoMayorRecaudacion();
	
	public List<Producto> obtenerTodos();

	public void insertar_lote(List<Producto> productos);

}
