package model;

public class Factura_Producto {

	//@JoinColumn(name="idFactura", foreignKey = @ForeignKey(name= "FK_factura",foreignKeyDefinition = "FOREIGN KEY (`idFactura`) REFERENCES `factura` (`id`) ON DELETE CASCADE ON UPDATE CASCADE",value = ConstraintMode.CONSTRAINT))
	//private Factura factura;
	
	//@JoinColumn(name="idProducto", foreignKey = @ForeignKey(name= "FK_producto",foreignKeyDefinition = "FOREIGN KEY (`idProducto`) REFERENCES `producto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE",value = ConstraintMode.CONSTRAINT))
	//private Producto producto;
	
	
	private int idFactura;
	private int idProducto;
	private int cantidad;
	
	public Factura_Producto(int idFactura, int idProducto, int cantidad) {
		super();
		this.idFactura = idFactura;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
	}

	public Factura_Producto(){
		
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public String toString() {
		return "Factura_Producto [idFactura=" + idFactura + ", idProducto=" + idProducto + ", cantidad=" + cantidad
				+ "]";
	}
	
	
	
}
