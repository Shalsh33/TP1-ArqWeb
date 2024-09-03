package repository;

import java.util.List;

import model.Cliente;

public interface ClienteRepository {

	public void crearTabla();
	
	public void insertar(Cliente cliente);
	
	public void insertar_lote(List<Cliente> clientes);
	
	public void actualizar (Cliente cliente);
	
	public void eliminar (Cliente cliente);
	
	public Cliente obtenerPorId(int id);
	
	public Cliente obtenerPorNombre(String nombre);
	
	public List<Cliente> obtenerClientesFacturacion();
	
	public List<Cliente> obtenerTodos();
	
}
