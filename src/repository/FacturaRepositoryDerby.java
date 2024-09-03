package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Factura;
import utils.FacturaFormatter;
import utils.MotorDB;

public class FacturaRepositoryDerby implements FacturaRepository {
	
	private Connection connection = ConnectionFactory.getInstance().connect(MotorDB.DERBY);

	@Override
	public void crearTabla() {

		String sql = "CREATE TABLE factura("
				+ "id INT NOT NULL,"
				+ "idCliente INT,"
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
	public void insertar(Factura factura) {
		String sql = "INSERT INTO "
				+ "factura(id, idCliente)"
				+ "values (?, ?)";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, factura.getId());
			q.setInt(2, factura.getIdCliente());
			q.executeUpdate();
		} catch(SQLException e) {
			if(e.getErrorCode() == 30000) {
				actualizar(factura);
				return;
			}
		}
		ConnectionFactory.getInstance().disconnect();

	}
	
	@Override
	public void insertar_lote(List<Factura> facturas) {

		for(Factura f: facturas) {
			String sql = "INSERT INTO "
					+ "factura(id, idCliente)"
					+ "values (?, ?)";
			try {
				PreparedStatement q = connection.prepareStatement(sql);
				q.setInt(1, f.getId());
				q.setInt(2, f.getIdCliente());
				q.executeUpdate();
			} catch(SQLException e) {
				
			}
			
		}
		ConnectionFactory.getInstance().disconnect();
		
	}

	@Override
	public void actualizar(Factura factura) {
		String sql = "UPDATE factura "
				+ "SET idFactura = ?, "
				+ "WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, factura.getIdCliente());
			q.setInt(2, factura.getId());
			q.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
	}

	@Override
	public void eliminar(Factura factura) {
		String sql = "DELETE FROM factura "
				+ "WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, factura.getId());
			q.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
	}

	@Override
	public Factura obtenerPorId(int id) {
		Factura f = null;
		
		String sql = "SELECT * FROM factura WHERE id = ?";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			q.setInt(1, id);
			ResultSet rs = q.executeQuery();
			if (rs.next()) {
				FacturaFormatter ff = FacturaFormatter.getInstance();
				f = ff.format(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return f;
	}

	@Override
	public List<Factura> obtenerTodos() {
		List<Factura> facturas = new ArrayList<Factura>();
		String sql = "SELECT * FROM factura";
		try {
			PreparedStatement q = connection.prepareStatement(sql);
			ResultSet rs = q.executeQuery();
			FacturaFormatter ff = FacturaFormatter.getInstance();
			while (rs.next()) {
				facturas.add(ff.format(rs));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnectionFactory.getInstance().disconnect();
		return facturas;
	}

}
