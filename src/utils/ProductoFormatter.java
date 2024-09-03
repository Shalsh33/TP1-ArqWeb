package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.csv.CSVRecord;

import model.Producto;

public class ProductoFormatter {

	private static ProductoFormatter pf = new ProductoFormatter();
	
	private ProductoFormatter() {
		
	}

	public static ProductoFormatter getInstance() {
		return pf;
	}
	
	public Producto format(ResultSet rs) {
		Producto p = new Producto();
		try {
			p.setId(rs.getInt("id"));
			p.setNombre(rs.getString("nombre"));
			p.setValor(rs.getFloat("valor"));
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return p;
	}

	public Producto format(CSVRecord r) {
		Producto p = new Producto();
		
		try {
			int id = Integer.parseInt(r.get("idProducto"));
			float valor = Float.parseFloat(r.get("valor"));
			p.setId(id);
			p.setValor(valor);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		p.setNombre(r.get("nombre")); 
		
		return p;
	}
	
}
