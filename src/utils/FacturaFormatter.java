package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.csv.CSVRecord;

import model.Factura;

public class FacturaFormatter {

	private static FacturaFormatter ff = new FacturaFormatter();
	
	private FacturaFormatter() {
		
	}

	public static FacturaFormatter getInstance() {
		return ff;
	}
	
	public Factura format(ResultSet rs) {
		Factura f = new Factura();
		try {
			f.setId(rs.getInt("id"));
			f.setIdCliente(rs.getInt("idCliente"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

	public Factura format(CSVRecord r) {
		Factura f = new Factura();
		try {
			int idF = Integer.parseInt(r.get("idFactura"));
			int idC = Integer.parseInt(r.get("idCliente"));
			f.setId(idF);
			f.setIdCliente(idC);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return f;
		}
	
		return f;
	}
}
