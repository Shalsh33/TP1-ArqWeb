package model;

public class Factura {

	private int id;
	
	//@JoinColumn(name="idCliente", foreignKey = @ForeignKey(name= "FK_cliente",foreignKeyDefinition = "FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE",value = ConstraintMode.CONSTRAINT))
	//private Cliente cliente;
	
	private int idCliente;
	
	public Factura() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", idCliente=" + idCliente + "]";
	}
	
	
	
}
