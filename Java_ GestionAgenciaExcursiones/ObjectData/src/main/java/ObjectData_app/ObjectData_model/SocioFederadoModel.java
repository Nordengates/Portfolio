package ObjectData_app.ObjectData_model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ObjectData_app.ObjectData_model.ObjectData_Hibernate.SocioFederadoHib;

public class SocioFederadoModel extends SocioModel {
    // Propiedades de la sesion.
    static SessionFactory sessionFactory;
    static Session session;

    // Propiedades de la clase
    String NIF;
    String federacion;

    // Constructor
    public SocioFederadoModel(int numeroSocio, String nombre, String NIF, String federacion) {
        super(numeroSocio, nombre);
        this.NIF = NIF;
        this.federacion = federacion;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getFederacion() {
        return federacion;
    }

    public void setFederacion(String federacion) {
        this.federacion = federacion;
    }

    // Método toString
    @Override
    public String toString() {
        return "SocioFederado{" +
                "numeroSocio=" + getNumeroSocio() +
                ", nombre='" + getNombre() + '\'' +
                ", NIF='" + NIF + '\'' +
                ", federacion=" + federacion +
                '}';
    }

    private static void crearSessionHib() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SocioFederadoHib.class)
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    // Metodos propios
    // Crear socio Federado
    public void crearSocioFederado(SocioFederadoModel socio) {
        crearSessionHib();
        try {
            session.beginTransaction();
            SocioFederadoHib socioHib = new SocioFederadoHib(
                    socio.getNumeroSocio(), // Obtenemos el numero de socio desde el objeto socioEstandar columna
                                            // numeroSocio
                    socio.getNombre(), // Obtenemos el nombre de socio desde el objeto socioEstandar //Columna
                    socio.getNIF(), // nombre
                    socio.getFederacion());

            // Lanzamos el comando para insertar el objeto en la base de datos
            session.persist(socioHib);
            // Confirmamos la transacción
            session.getTransaction().commit();
        } catch (Exception e) {
            // En caso de error, realizamos un rollback de la transacción
            session.getTransaction().rollback();
            throw e;
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
    }

    // Obtener socio federado por número de socio.
    public static SocioFederadoModel getSocioPorNumeroSocio(int numeroSocio) {
        crearSessionHib();
        SocioFederadoHib socio;
        try {
            session.beginTransaction();
            // Obtenemos el socio por numero de socio
            socio = session
                    .createQuery("FROM SocioFederadoHib WHERE numeroSocio = :numeroSocio", SocioFederadoHib.class)
                    .setParameter("numeroSocio", numeroSocio)
                    .uniqueResult();
        } catch (Exception e) {
            throw e; // Captura el mensaje de error del DAO y lo envia aguas arriba.
        } finally {
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        if (socio != null) {
            return new SocioFederadoModel(
                socio.getNumeroSocio(),
                socio.getNombre(),
                socio.getNIF(),
                socio.getFederacion());
        } else {
            return null;
        }
    }

    // Metodo para obtener la lista los socios federados.
    public static ArrayList<SocioFederadoModel> obtenerSocios() {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        // Alamacenamos los objetos devuelvos por la base de datos que son de tipo Hib
        List<SocioFederadoHib> sociosFederadosHib = null;
        // Almacenamos los objetos transformados de Hub a Model.
        ArrayList<SocioFederadoModel> sociosFederadosModel = new ArrayList<>();
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            sociosFederadosHib = session.createQuery("FROM SocioFederadoHib", SocioFederadoHib.class).list();
        } catch (Exception e) {
            throw e; // Captura el mensaje de error del DAO y lo envia aguas arriba.
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        //Pasamos los datos de sociosFederadosHib a sociosFederadosModel
        for (SocioFederadoHib socio : sociosFederadosHib) {
            sociosFederadosModel.add(
                new SocioFederadoModel(
                    socio.getNumeroSocio(),
                    socio.getNombre(),
                    socio.getNIF(),
                    socio.getFederacion()
            ));
        }
        //Devolvemos los objetos de tipo sociosFederadosModel al controlador
        return sociosFederadosModel;
    }

    // Método para eliminar socio infantil de la base de datos
    public static boolean eliminarSocioModel(int numeroSocio) {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            // Creamos una consulta DELETE utilizando createMutationQuery()
            session.createMutationQuery("DELETE FROM SocioFederadoHib WHERE numeroSocio = :numeroSocio")
                    .setParameter("numeroSocio", numeroSocio)
                    .executeUpdate();
            // Confirmamos la transacción
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            // En caso de error, realizamos un rollback de la transacción
            session.getTransaction().rollback();
            throw e; 
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
    }

}
