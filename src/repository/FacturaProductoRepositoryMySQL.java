package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Factura_Producto;
import utils.FacturaProductoFormatter;
import utils.MotorDB;

public class FacturaProductoRepositoryMySQL implements FacturaProductoRepository {

	private Connection connection = ConnectionFactory.getInstance().connect(MotorDB.MYSQL);

	@Override
	public void crearTabla() {
		String sql = "CREATE TABLE factura_producto("
				+ "idFactura INT NOT NULL,"
				+ "idProducto INT NOT NULL,"
				+ "cantidad INT,"
				+ "PRIMARY KEY(idFactura, idProducto)"
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
	public void insertar(Factura_Producto fp) {
		String sql = "INSERT INTO "
				+ "factura_producto(idFactura, idProducto, cantidad)"
				+ "values (?, ?, ?)";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, fp.getIdFactura());
			q.setInt(2,fp.getIdProducto());
			q.setInt(3,fp.getCantidad());
			q.executeUpdate();
		} catch(SQLException e) {
			if(e.getErrorCode() == 30000) {
				actualizar(fp);
				return;
			}
		}
		ConnectionFactory.getInstance().disconnect();

	}

	@Override
	public void insertar_lote(List<Factura_Producto> fps) {
		
		for(Factura_Producto fp : fps) {
			String sql = "INSERT INTO "
					+ "factura_producto(idFactura, idProducto, cantidad)"
					+ "values (?, ?, ?)";
			try {
				PreparedStatement q = connection.prepareStatement(sql);
				q.setInt(1, fp.getIdFactura());
				q.setInt(2,fp.getIdProducto());
				q.setInt(3,fp.getCantidad());
				q.executeUpdate();
			} catch(SQLException e) {
				
			}
		}
		ConnectionFactory.getInstance().disconnect();
	}

	@Override
	public void actualizar(Factura_Producto fp) {
		String sql = "UPDATE factura_producto"
				+ "SET cantidad = ?"
				+ "WHERE idProducto = ? AND idFactura = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1,fp.getCantidad());
			q.setInt(2,fp.getIdProducto());
			q.setInt(3, fp.getIdFactura());	
			q.executeUpdate();
		} catch(SQLException e) {
			
		}
		ConnectionFactory.getInstance().disconnect();
	}

	@Override
	public void eliminar(Factura_Producto fp) {
	
		String sql = "DELETE FROM factura_producto "
				+ "WHERE idFactura = ? AND idProducto = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, fp.getIdFactura());
			q.setInt(2, fp.getIdProducto());
			q.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Factura_Producto obtenerPorId(int idFactura, int idProducto) {
		Factura_Producto fp = null;
		
		String sql = "SELECT * FROM factura_producto "
				+ "WHERE idFactura = ? AND idProducto = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, idFactura);
			q.setInt(1, idProducto);
			ResultSet rs = q.executeQuery();
			if (rs.next()) {
				FacturaProductoFormatter fpf = FacturaProductoFormatter.getInstance();
				fp = fpf.format(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return fp;
	}

	@Override
	public List<Factura_Producto> obtenerTodos() {
		List<Factura_Producto> lista = new ArrayList<Factura_Producto>();
		
		String sql = "SELECT * FROM factura_producto";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			ResultSet rs = q.executeQuery();
			FacturaProductoFormatter fpf = FacturaProductoFormatter.getInstance();
			while (rs.next()) {
				lista.add(fpf.format(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		
		return lista;
	}

}
