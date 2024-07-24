package aa4_woodshops;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * @author Marina Romero Torres
 */

public class AA4_WoodShops {

    private Scanner teclado = new Scanner(System.in);
    private List<Cliente> listaClientes = new ArrayList<>();
    private List<Producto> listaProductos = new ArrayList<>();
    private List<Tienda> listaTiendas = new ArrayList<>();

    public static void main(String[] args) {
        AA4_WoodShops programa = new AA4_WoodShops();
        programa.inicio();
    }

    void inicio() {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("\n¡Bienvenido a WoodShops!");
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Añadir Producto");
            System.out.println("4. Añadir Tienda");
            System.out.println("5. Crear ticket de venta");
            System.out.println("6. Mostrar Resumen de Ventas por Tienda");
            System.out.println("0. Salir de la aplicación");
            opcion = pedirOpcionMenu();
            switch (opcion) {
                case '1':
                    anadirCliente();
                    break;
                case '2':
                    listarClientes();
                    break;
                case '3':
                    anadirProducto();
                    break;
                case '4':
                    anadirTienda();
                    break;
                case '5':
                    crearVenta();
                    break;
                case '6':
                    opcionMostrarResumenVentas();
                    break;
                case '0':
                    salir = true;
                    break;
            }

        } while (!salir);
    }

    private char pedirOpcionMenu() {
        System.out.print("Selecciona una opción: ");
        return teclado.next().charAt(0);
    }

    private void anadirCliente() {
        System.out.println("Añadir nuevo cliente:");
        System.out.print("Ingresa NIF: ");
        String nif = teclado.next();
        System.out.print("Ingresa nombre: ");
        String nombre = teclado.next();

        teclado.nextLine(); 
    
        System.out.println("Selecciona tipo de cliente:");
        System.out.println("1. Cliente profesional");
        System.out.println("2. WoodFriend");
        System.out.println("3. Cliente general");
        System.out.print("Opción: ");
        int tipoCliente = teclado.nextInt();

        teclado.nextLine();
    
        Cliente cliente;
        switch (tipoCliente) {
            case 1:
                System.out.print("Ingresa el % de descuento (0-100): ");
                double descuento = teclado.nextDouble() / 100.0;
                cliente = new ClienteProfesional(nif, nombre, descuento);
                break;
            case 2:
                System.out.print("Ingresa el código de socio: ");
                String codigoSocio = teclado.next();
                cliente = new WoodFriend(nif, nombre, codigoSocio);
                break;
            default:
                cliente = new Cliente(nif, nombre);
                break;
        }
    
        listaClientes.add(cliente);
        System.out.println("Cliente añadido correctamente.");
    }        

    private void listarClientes() {
        System.out.println("Listado de clientes:");
        for (Cliente cliente : listaClientes) {
            System.out.println(cliente.toString());
        }
    }

    private void anadirProducto() {
        System.out.println("Añadir nuevo producto:");
        System.out.print("Ingresa ID del producto: ");
        String idProducto = teclado.next();
        System.out.print("Ingresa nombre del producto: ");
        String nombreProducto = teclado.next();
        System.out.print("Ingresa precio del producto: ");
        double precioProducto = teclado.nextDouble();
        Producto producto = new Producto(idProducto, nombreProducto, precioProducto);
        listaProductos.add(producto);
        System.out.println("Producto añadido exitosamente.");
    }
    private void anadirTienda() {
        System.out.print("Ingresa el nombre de la nueva tienda: ");
        String nombreTienda = teclado.next();
        Tienda nuevaTienda = new Tienda(nombreTienda);
        listaTiendas.add(nuevaTienda);
        System.out.println("Tienda '" + nombreTienda + "' agregada con éxito.");
    }
    
    private void crearVenta() {
        // Primero, selecciona una tienda.
        Tienda tiendaSeleccionada = seleccionarTienda();
    
        System.out.println("Crear una venta:");
        System.out.print("Ingresa número de ticket: ");
        String numeroTicket = teclado.next();
        Date fechaVenta = new Date();
        Venta venta = new Venta(numeroTicket, fechaVenta, tiendaSeleccionada); // Crea la venta con la tienda
    
        boolean anadirProductos = true;
        while (anadirProductos) {
            System.out.print("Ingresa ID de producto: ");
            String idProducto = teclado.next();
            Producto producto = buscarProductoPorId(idProducto);
    
            if (producto != null) {
                System.out.print("Ingresa cantidad: ");
                int cantidad = teclado.nextInt();
                DetalleVenta detalle = new DetalleVenta(producto, cantidad);
                venta.agregarDetalle(detalle);
            } else {
                System.out.println("Producto no encontrado.");
            }
    
            System.out.print("¿Añadir otro producto? (s/n): ");
            anadirProductos = teclado.next().equalsIgnoreCase("s");
        }
    
        System.out.print("Ingresa NIF del cliente (dejar en blanco para cliente anónimo): ");
        teclado.nextLine();
        String nif = teclado.nextLine();
        Cliente cliente = buscarClientePorNIF(nif);
    
        if (cliente instanceof ClienteRegistrado) {
            venta.setCliente((ClienteRegistrado) cliente);
        }
    
        tiendaSeleccionada.anadirVenta(venta); // Añade la venta a la tienda seleccionada
        System.out.println(venta.imprimirTicket());
    }
        
    private Date pedirFecha(String mensaje) {
        Date fecha = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (fecha == null) {
            System.out.print(mensaje + " (formato YYYY-MM-DD): ");
            String input = teclado.next();
            try {
                fecha = dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.println("Formato incorrecto.");
            }
        }
        return fecha;
    }
    
    private Producto buscarProductoPorId(String idProducto) {
        for (Producto producto : listaProductos) {
            if (producto.getIdProducto().equals(idProducto)) {
                return producto;
            }
        }
        return null;
    }

    private Cliente buscarClientePorNIF(String nif) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getNIF().equals(nif)) {
                return cliente;
            }
        }
        return null;
    }

    private void opcionMostrarResumenVentas() {
        Tienda tiendaSeleccionada = seleccionarTienda();
        if (tiendaSeleccionada != null) {
            Date fechaInicio = pedirFecha("Ingresa la fecha de inicio");
            Date fechaFin = pedirFecha("Ingresa la fecha de fin");
            System.out.println(tiendaSeleccionada.mostrarResumenVentas(fechaInicio, fechaFin));
        }
    }

    private Tienda seleccionarTienda() {
        if (listaTiendas.isEmpty()) {
            System.out.println("No hay tiendas disponibles.");
            return null;
        }

        for (int i = 0; i < listaTiendas.size(); i++) {
            System.out.println((i + 1) + ". " + listaTiendas.get(i).getNombreTienda());
        }

        System.out.print("Selecciona una tienda: ");
        int indice = teclado.nextInt() - 1;

        if (indice >= 0 && indice < listaTiendas.size()) {
            return listaTiendas.get(indice);
        } else {
            System.out.println("Selección no válida.");
            return null;
        }
    }

}
