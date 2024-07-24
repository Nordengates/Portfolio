package aa4_woodshops;

public class DetalleVenta {
    private aa4_woodshops.Producto producto;
    private int cantidad;

    public DetalleVenta(aa4_woodshops.Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public String toString() {
        return "Producto: " + producto.getNombre() + ", Cantidad: " + cantidad + ", Subtotal: " + calcularSubtotal();
    }

 public aa4_woodshops.Producto getProducto() {
        return producto;
    }
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
