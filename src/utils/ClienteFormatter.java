package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.csv.CSVRecord;

import model.Cliente;

public class ClienteFormatter {
	
	private static ClienteFormatter cf = new ClienteFormatter();
	
	private ClienteFormatter() {
		
	}

	public static ClienteFormatter getInstance() {
		return cf;
	}
	
	public Cliente format(ResultSet rs) {
		Cliente c = new Cliente();
		try {
			c.setId(rs.getInt("id"));
			c.setNombre(rs.getString("nombre"));
			c.setEmail(rs.getString("email"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public Cliente format(CSVRecord r) {
		Cliente c = new Cliente();
		try {
			int id = Integer.parseInt(r.get("idCliente"));
			c.setId(id);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return c;
		}
		c.setNombre(r.get("nombre")); 
		c.setEmail(r.get("email")); 
		return c;
	}
	
}
