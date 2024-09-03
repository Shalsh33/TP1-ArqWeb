package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.csv.CSVRecord;

import model.Factura_Producto;

public class FacturaProductoFormatter {
	private static FacturaProductoFormatter fpf = new FacturaProductoFormatter();
	
	private FacturaProductoFormatter() {
		
	}

	public static FacturaProductoFormatter getInstance() {
		return fpf;
	}
	
	public Factura_Producto format(ResultSet rs) {
		Factura_Producto fp = new Factura_Producto();
		try {
			fp.setIdFactura(rs.getInt("idFactura"));
			fp.setIdProducto(rs.getInt("idProducto"));
			fp.setCantidad(rs.getInt("cantidad"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fp;
	}

	public Factura_Producto format(CSVRecord r) {
		Factura_Producto fp = new Factura_Producto();
		try {
			int idP = Integer.parseInt(r.get("idProducto"));
			int idF = Integer.parseInt(r.get("idFactura"));
			int cantidad = Integer.parseInt(r.get("cantidad"));
			fp.setIdProducto(idP);
			fp.setIdFactura(idF);
			fp.setCantidad(cantidad);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return fp;
		}
	
		return fp;
	}
	
}
