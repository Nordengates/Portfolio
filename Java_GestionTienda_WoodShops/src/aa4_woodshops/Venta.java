package aa4_woodshops;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Venta {
    private String numeroTicket;
    private Date fecha;
    private ClienteRegistrado cliente;
    private List<DetalleVenta> detalles;
    private Tienda tienda; // Añado referencia a Tienda

    public Venta(String numeroTicket, Date fecha, Tienda tienda) {
        this.numeroTicket = numeroTicket;
        this.fecha = fecha;
        this.detalles = new ArrayList<>();
        this.tienda = tienda; // Inicializo la tienda en el constructor
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
    }

public String imprimirTicket() {
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    StringBuilder sb = new StringBuilder();
    sb.append("Ticket No: ").append(numeroTicket)
      .append("\n Fecha: ").append(formatoFecha.format(getFecha()))
      .append("\n - Detalles -\n")
      .append("\n Cliente: ").append(cliente != null ? cliente.getNombre() : " Anónimo ");

    for (DetalleVenta detalle : detalles) {
        sb.append(detalle).append("\n");
    }

    sb.append("Total: ").append(calcularTotalConDescuento());
    return sb.toString();
    }


    double calcularTotalConDescuento() {
        double total = 0;
        for (DetalleVenta detalle : detalles) {
            total += detalle.calcularSubtotal();
        }
        if (cliente instanceof ClienteProfesional) {
            total -= total * ((ClienteProfesional) cliente).getDescuento();
        }
        return total;
    }

    public double getDescuentoAplicado() {
        if (cliente instanceof ClienteProfesional) {
            return ((ClienteProfesional) cliente).getDescuento();
        }
        return 0;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public Tienda getTienda() {
        return tienda;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }
  
    public void setCliente(ClienteRegistrado cliente) {
        this.cliente = cliente;
    }

    public ClienteRegistrado getCliente() {
        return cliente;
    }
    
    public List<DetalleVenta> getDetalles() {
        return detalles;
    }      

}
