package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Cliente;
import utils.ClienteFormatter;
import utils.MotorDB;

public class ClienteRepositoryDerby implements ClienteRepository {

	private Connection connection = ConnectionFactory.getInstance().connect(MotorDB.DERBY);
	
	@Override
	public void crearTabla() {
		String sql = "CREATE TABLE cliente("
				+ "id INT NOT NULL,"
				+ "nombre varchar(500),"
				+ "email varchar(150),"
				+ "PRIMARY KEY(id)"
				+ ")";
		try {
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ConnectionFactory.getInstance().disconnect();



	}

	@Override
	public void insertar(Cliente cliente) {
		String sql = "INSERT INTO "
				+ "cliente(id, nombre, email)"
				+ "values (?, ?, ?)";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, cliente.getId());
			q.setString(2, cliente.getNombre());
			q.setString(3, cliente.getEmail());
			//executeUpdate cuando se devuelve una cantidad de filas afectadas (insert o update)
			q.executeUpdate();
		} catch(SQLException e) {
			if(e.getErrorCode() == 30000) {
				actualizar(cliente);
				return;
			}
		}
		ConnectionFactory.getInstance().disconnect();

	}
	
	public void insertar_lote(List<Cliente> clientes) {
		
		for(Cliente c: clientes) {
			String sql = "INSERT INTO "
					+ "cliente(id, nombre, email)"
					+ "values (?, ?, ?)";
			try {
				PreparedStatement q = connection.prepareStatement(sql);
				q.setInt(1, c.getId());
				q.setString(2, c.getNombre());
				q.setString(3, c.getEmail());
				q.executeUpdate();
			} catch(SQLException e) {
				
				
			}
		}
		ConnectionFactory.getInstance().disconnect();
	}

	@Override
	public void actualizar(Cliente cliente) {
		String sql = "UPDATE cliente "
				+ "SET nombre = ?, "
				+ "email = ? "
				+ "WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setString(1, cliente.getNombre());
			q.setString(2, cliente.getEmail());
			q.setInt(3, cliente.getId());
			//executeUpdate cuando se devuelve una cantidad de filas afectadas (insert o update)
			q.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();

	}

	@Override
	public void eliminar(Cliente cliente) {
		String sql = "DELETE FROM cliente "
				+ "WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, cliente.getId());
			//executeUpdate cuando se devuelve una cantidad de filas afectadas (insert o update)
			q.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Cliente obtenerPorId(int id) {
		Cliente c = null;
		
		String sql = "SELECT * FROM cliente WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, id);
			ResultSet rs = q.executeQuery();
			if (rs.next()) {
				ClienteFormatter cf = ClienteFormatter.getInstance();
				c = cf.format(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return c;
	}

	@Override
	public Cliente obtenerPorNombre(String nombre) {
		Cliente c = null;
		
		String sql = "SELECT * FROM cliente WHERE nombre = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setString(1, nombre);
			ResultSet rs = q.executeQuery();
			if (rs.next()) {
				ClienteFormatter cf = ClienteFormatter.getInstance();
				c = cf.format(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return c;
	}

	@Override
	public List<Cliente> obtenerClientesFacturacion() {
		List<Cliente> lista = new ArrayList<>();
		
		String sql = "SELECT cliente.id, cliente.nombre, cliente.email, "
				+ "SUM(factura_producto.cantidad * producto.valor) as facturacion "
				+ "FROM cliente JOIN factura ON factura.idCliente = cliente.id "
				+ "JOIN factura_producto ON factura_producto.idFactura = factura.id "
				+ "JOIN producto ON factura_producto.idProducto = producto.id "
				+ "GROUP BY cliente.id, cliente.nombre, cliente.email "
				+ "ORDER BY facturacion DESC";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			ResultSet rs = q.executeQuery();
			ClienteFormatter cf = ClienteFormatter.getInstance();
			while (rs.next()) {
				Cliente c = cf.format(rs);
				lista.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return lista;
	}
	
	@Override
	public List<Cliente> obtenerTodos() {
		List<Cliente> lista = new ArrayList<>();
		
		String sql = "SELECT * FROM cliente";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			ResultSet rs = q.executeQuery();
			ClienteFormatter cf = ClienteFormatter.getInstance();
			while (rs.next()) {
				Cliente c = cf.format(rs);
				lista.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return lista;
	}

}
