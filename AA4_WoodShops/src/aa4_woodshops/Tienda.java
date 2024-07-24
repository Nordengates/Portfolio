package aa4_woodshops;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tienda {
    private String nombreTienda;
    private List<Cliente> listaClientes;
    private List<Venta> listaVentas;

    public Tienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
        this.listaClientes = new ArrayList<>();
        this.listaVentas = new ArrayList<>();
    }
    
    public String getNombreTienda() {
        return nombreTienda;
    }
    public void anadirCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public String mostrarClientes() {
        StringBuilder sb = new StringBuilder();
        for (Cliente cliente : listaClientes) {
            sb.append(cliente.toString()).append("\n");
        }
        return sb.toString();
    }

    public void anadirVenta(Venta venta) {
        listaVentas.add(venta);
    }

    public String mostrarResumenTickets(Date fechaInicio, Date fechaFin) {
        StringBuilder sb = new StringBuilder();
        for (Venta venta : listaVentas) {
            if (venta.getFecha().after(fechaInicio) && venta.getFecha().before(fechaFin)) {
                sb.append(venta.imprimirTicket()).append("\n");
            }
        }
        return sb.toString();
    }

    public String mostrarResumenVentas(Date fechaInicio, Date fechaFin) {
        double totalVentas = 0;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();

        for (Venta venta : listaVentas) {
            if (!venta.getFecha().before(fechaInicio) && !venta.getFecha().after(fechaFin)) {
                totalVentas += venta.calcularTotalConDescuento();
            }
        }

        sb.append("Resumen de ventas de '").append(nombreTienda)
          .append("' del ").append(formatoFecha.format(fechaInicio))
          .append(" al ").append(formatoFecha.format(fechaFin)).append(":\n")
          .append("Total de Ventas: ").append(totalVentas);

        return sb.toString();
    }
    
}
