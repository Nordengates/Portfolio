package aa4_woodshops;

public class ClienteProfesional extends ClienteRegistrado {
    private double descuento;

    public ClienteProfesional(String NIF, String nombre, double descuento) {
        super(NIF, nombre);
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Cliente Profesional [NIF: " + getNIF() + ", Nombre: " + getNombre() + ", Descuento: " + descuento + "]";
    }
}