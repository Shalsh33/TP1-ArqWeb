package repository;

import utils.MotorDB;

public class RepositoryFactory {

	private static RepositoryFactory instancia = new RepositoryFactory();
	
	private RepositoryFactory() {
		
	}
	
	public static RepositoryFactory getInstance() {
		return instancia;
	}
	
	public ClienteRepository getClienteRepository(MotorDB tipo) {
		if (tipo.equals(MotorDB.DERBY)) {
			return new ClienteRepositoryDerby();
		}
		if (tipo.equals(MotorDB.MYSQL)) {
			return new ClienteRepositoryMySQL();
		}
		
		return null;
	}
	
	public ProductoRepository getProductoRepository(MotorDB tipo) {
		if (tipo.equals(MotorDB.DERBY)) {
			return new ProductoRepositoryDerby();
		}
		if (tipo.equals(MotorDB.MYSQL)) {
			return new ProductoRepositoryMySQL();
		}
		
		return null;
	}
	
	
	public FacturaProductoRepository getFacturaProductoRepository(MotorDB tipo) {
		if (tipo.equals(MotorDB.DERBY)) {
			return new FacturaProductoRepositoryDerby();
		}
		if (tipo.equals(MotorDB.MYSQL)) {
			return new FacturaProductoRepositoryMySQL();
		}
		
		return null;
	}

	public FacturaRepository getFacturaRepository(MotorDB tipo) {
		if (tipo.equals(MotorDB.DERBY)) {
			return new FacturaRepositoryDerby();
		}
		if (tipo.equals(MotorDB.MYSQL)) {
			return new FacturaRepositoryMySQL();
		}
		
		return null;
	}
	
}
