package repository;

import java.util.List;

import model.Factura;

public interface FacturaRepository {
	
	public void crearTabla();
	
	public void insertar(Factura factura);
	
	public void actualizar (Factura factura);
	
	public void eliminar (Factura factura);
	
	public Factura obtenerPorId(int id);
	
	public List<Factura> obtenerTodos();

	public void insertar_lote(List<Factura> facturas);
}
