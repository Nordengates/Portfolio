package aa4_woodshops;

public class WoodFriend extends ClienteRegistrado {
    private String codigoSocio;

    public WoodFriend(String NIF, String nombre, String codigoSocio) {
        super(NIF, nombre);
        this.codigoSocio = codigoSocio;
    }

    public String getCodigoSocio() {
        return codigoSocio;
    }

    public void setCodigoSocio(String codigoSocio) {
        this.codigoSocio = codigoSocio;
    }

    @Override
    public String toString() {
        return "WoodFriend [NIF: " + getNIF() + ", Nombre: " + getNombre() + ", Código Socio: " + codigoSocio + "]";
    }
}
