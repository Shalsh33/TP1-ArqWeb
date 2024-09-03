package main;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import model.Cliente;
import model.Factura;
import model.Factura_Producto;
import model.Producto;
import repository.RepositoryFactory;
import utils.ClienteFormatter;
import utils.FacturaFormatter;
import utils.FacturaProductoFormatter;
import utils.MotorDB;
import utils.ProductoFormatter;

public class Main {

	public static void main(String[] args) {
		RepositoryFactory rf = RepositoryFactory.getInstance();
	
		//inicialización de tablas y carga de datos en MYSQL
		cargarTablaCliente(rf,MotorDB.MYSQL);
		cargarTablaProducto(rf,MotorDB.MYSQL);
		cargarTablaFactura(rf,MotorDB.MYSQL);
		cargarTablaFacturaProducto(rf,MotorDB.MYSQL);
		
		//Inicializacion de tablas y carga de datos en DERBY
		cargarTablaCliente(rf,MotorDB.DERBY);
		cargarTablaProducto(rf,MotorDB.DERBY);
		cargarTablaFactura(rf,MotorDB.DERBY);
		cargarTablaFacturaProducto(rf,MotorDB.DERBY);
		
		
		
		System.out.println("Producto con mayor recaudación: " + rf.getProductoRepository(MotorDB.MYSQL).obtenerProductoMayorRecaudacion());
		for(Cliente c: rf.getClienteRepository(MotorDB.MYSQL).obtenerClientesFacturacion() ) {
			System.out.println(c);
		}
		System.out.println("-------");
		System.out.println("Producto con mayor recaudación: " + rf.getProductoRepository(MotorDB.DERBY).obtenerProductoMayorRecaudacion());
		for(Cliente c: rf.getClienteRepository(MotorDB.DERBY).obtenerClientesFacturacion() ) {
			System.out.println(c);
		}
		
	}

	private static void cargarTablaCliente(RepositoryFactory rf,MotorDB tipo) {
		
		rf.getClienteRepository(tipo).crearTabla();
		
		try {
			CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(new File("").getAbsolutePath() + "/src/CSV/clientes.csv")); 
			ClienteFormatter cf = ClienteFormatter.getInstance();
			List<Cliente> clientes = new ArrayList<Cliente>();
			for(CSVRecord row: parser) { 
				clientes.add(cf.format(row));
			} 
			rf.getClienteRepository(tipo).insertar_lote(clientes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void cargarTablaFactura(RepositoryFactory rf, MotorDB tipo) {
		
		rf.getFacturaRepository(tipo).crearTabla();
		
		try {
			CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(new File("").getAbsolutePath() + "/src/CSV/facturas.csv")); 
			FacturaFormatter ff = FacturaFormatter.getInstance();
			List<Factura> facturas = new ArrayList<Factura>();
			for(CSVRecord row: parser) { 
				facturas.add(ff.format(row));
			} 
			rf.getFacturaRepository(tipo).insertar_lote(facturas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void cargarTablaProducto(RepositoryFactory rf, MotorDB tipo) {
		
		rf.getProductoRepository(tipo).crearTabla();
		
		try {
			CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(new File("").getAbsolutePath() + "/src/CSV/productos.csv")); 
			ProductoFormatter pf = ProductoFormatter.getInstance();
			List<Producto> productos = new ArrayList<Producto>();
			for(CSVRecord row: parser) { 
				productos.add(pf.format(row));
			} 
			rf.getProductoRepository(tipo).insertar_lote(productos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

private static void cargarTablaFacturaProducto(RepositoryFactory rf, MotorDB tipo) {
		
		rf.getFacturaProductoRepository(tipo).crearTabla();
		
		try {
			CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(new File("").getAbsolutePath() + "/src/CSV/facturas-productos.csv")); 
			FacturaProductoFormatter fpf = FacturaProductoFormatter.getInstance();
			List<Factura_Producto> fps = new ArrayList<Factura_Producto>();
			// abrir y cerrar tantas conexiones no es más costoso que hacerlo todo de una en el repository?
			for(CSVRecord row: parser) { 
				fps.add(fpf.format(row));
			} 
			rf.getFacturaProductoRepository(tipo).insertar_lote(fps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
