package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Producto;
import utils.ProductoFormatter;
import utils.MotorDB;

public class ProductoRepositoryDerby implements ProductoRepository {

	private Connection connection = ConnectionFactory.getInstance().connect(MotorDB.DERBY);
	
	@Override
	public void crearTabla() {
		String sql = "CREATE TABLE producto("
				+ "id INT NOT NULL,"
				+ "nombre varchar(45),"
				+ "valor float,"
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
	public void insertar(Producto producto) {
		String sql = "INSERT INTO "
				+ "producto(id, nombre, valor)"
				+ "values (?, ?, ?)";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, producto.getId());
			q.setString(2, producto.getNombre());
			q.setFloat(3, producto.getValor());
			q.executeUpdate();
		} catch(SQLException e) {
			if(e.getErrorCode() == 30000) {
				actualizar(producto);
				return;
			}
		}
		ConnectionFactory.getInstance().disconnect();

	}

	@Override
	public void insertar_lote(List<Producto> productos) {

		for(Producto p: productos) {
			String sql = "INSERT INTO "
					+ "producto(id, nombre, valor)"
					+ "values (?, ?, ?)";
			try {
				PreparedStatement q = connection.prepareStatement(sql);
				q.setInt(1, p.getId());
				q.setString(2, p.getNombre());
				q.setFloat(3, p.getValor());
				q.executeUpdate();
			} catch(SQLException e) {
				
			}
			
		}
		ConnectionFactory.getInstance().disconnect();
	}

	@Override
	public void actualizar(Producto producto) {
		String sql = "UPDATE producto "
				+ "SET nombre = ?, "
				+ "valor = ? "
				+ "WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setString(1, producto.getNombre());
			q.setFloat(2, producto.getValor());
			q.setInt(3, producto.getId());
			q.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();

	}

	@Override
	public void eliminar(Producto producto) {
		String sql = "DELETE FROM producto "
				+ "WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, producto.getId());
			q.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Producto obtenerPorId(int id) {
		Producto p = null;
		
		String sql = "SELECT * FROM producto WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, id);
			ResultSet rs = q.executeQuery();
			if (rs.next()) {
				ProductoFormatter pf = ProductoFormatter.getInstance();
				p = pf.format(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return p;
	}

	@Override
	public Producto obtenerPorNombre(String nombre) {
		Producto p = null;
		
		String sql = "SELECT * FROM producto WHERE nombre = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setString(1, nombre);
			ResultSet rs = q.executeQuery();
			if (rs.next()) {
				ProductoFormatter pf = ProductoFormatter.getInstance();
				p = pf.format(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return p;
	}

	@Override
	public List<Producto> obtenerTodos() {
		List<Producto> lista = new ArrayList<>();
		
		String sql = "SELECT * FROM producto";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			ResultSet rs = q.executeQuery();
			ProductoFormatter pf = ProductoFormatter.getInstance();
			while (rs.next()) {
				Producto p = pf.format(rs);
				lista.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return lista;
	}

	@Override
	public Producto obtenerProductoMayorRecaudacion() {
		
		Producto p = null;
		String sql = "SELECT producto.id, producto.nombre, producto.valor, SUM(factura_producto.cantidad * producto.valor) as recaudacion "
				+ "FROM producto JOIN factura_producto "
				+ "ON producto.id = factura_producto.idProducto "
				+ "GROUP BY producto.id, producto.nombre, producto.valor "
				+ "ORDER BY recaudacion DESC";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			ResultSet rs = q.executeQuery();
			if (rs.next()) {
				ProductoFormatter pf = ProductoFormatter.getInstance();
				p = pf.format(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		
		return p;
	}
}
