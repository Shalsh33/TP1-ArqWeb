package repository;

import java.util.List;

import model.Factura_Producto;

public interface FacturaProductoRepository {

	public void crearTabla();
	
	public void insertar(Factura_Producto fp);
	
	public void actualizar (Factura_Producto fp);
	
	public void eliminar (Factura_Producto fp);
	
	public Factura_Producto obtenerPorId(int idFactura, int idProducto);
	
	public List<Factura_Producto> obtenerTodos();

	public void insertar_lote(List<Factura_Producto> fps);
	
}
