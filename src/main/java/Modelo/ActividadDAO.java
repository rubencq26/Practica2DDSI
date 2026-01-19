/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author rubco
 */
public class ActividadDAO {
    /**
     * Devuelve una actividad que coincida con su clave primaria
     * @param session parametro para usar la sesion abierta
     * @param idAct clave primera de Actividad
     * @return Devuelve una actividad coincidente con la clave primaria 
     */
    public static Actividad buscarId(Session session, String idAct) {
        return session.find(Actividad.class, idAct);
    }
    
    /**
     * Devuelve todas las actividades formateada para que la clave ajena en vez de mostrar el codigo del monitor muestre el nombre
     * @param session parametro para usar la sesion abierta
     * @return devuelve una List<Object> con 8 elementos de actividad
     */
    public static List<Object[]> listarActividades(Session session) {
        Query consulta = session.createNativeQuery("SELECT a.idActividad, a.nombre, a.dia, a.hora, a.descripcion, a.precioBaseMes, m.nombre FROM ACTIVIDAD a INNER JOIN MONITOR m WHERE m.codMonitor = a.monitorResponsable", Object.class);
        System.out.println("Actividades encontrados");
        return consulta.getResultList();
    }
    
    /**
     * Devuelve la lista de todas las actividades en una List<Actividad> con todos sus parametros
     * @param session parametro para usar la sesion abierta
     * @return List<Actividad> con todos los parametros incluidos
     */
    public static List<Actividad> listarActividadesA(Session session) {
        Query consulta = session.createNativeQuery("SELECT * FROM ACTIVIDAD", Actividad.class);
        System.out.println("Actividades encontrados");
        return consulta.getResultList();
    }
    
    
    /**
     * Inserta una actividad pasada por parametros en la base de datos
     * @param session parametro para usar la sesion abierta
     * @param actividad es la actividad a insertar
     * @return devuelve true si se inserta y false sino
     */
    public static boolean insertarActividad(Session session, Actividad actividad) {
        try {
            session.persist(actividad);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    /**
     * Elimina una actividad pasada por parametro de la base de datos
     * @param session parametro para usar la sesion abierta
     * @param actividad Es la actividad a eliminar de la base de datos
     * @return devuelve true si se a eliminado y false sino
     */
    public static boolean eliminarActividad(Session session, Actividad actividad) {
        try {
            // Buscamos el objeto primero para asegurarnos de que existe y está gestionado
            if (actividad != null) {
                session.remove(actividad);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza una actividad pasada por parametro
     * @param session
     * @param actividad
     * @return 
     */
    public static boolean actualizarActividad(Session session, Actividad actividad) {
        try {
            // Buscamos el objeto primero para asegurarnos de que existe y está gestionado
            if (actividad != null) {
                session.merge(actividad);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void darBajaSocio(Session session, String idActividad, String numeroSocio) {

        Actividad actividad = ActividadDAO.buscarId(session, idActividad);
        Socio socio = SocioDAO.buscarId(session, numeroSocio);

        if (actividad != null && socio != null) {

            actividad.getSocioSet().remove(socio);

            socio.getActividadSet().remove(actividad);

            session.merge(actividad);
        }
    }

    public static void inscribirSocio(Session session, String idActividad, String numeroSocio) {
        Actividad actividad = ActividadDAO.buscarId(session, idActividad);
        Socio socio = SocioDAO.buscarId(session, numeroSocio);

        if (actividad != null && socio != null) {
            actividad.getSocioSet().add(socio);
            socio.getActividadSet().add(actividad);
            session.merge(actividad);
        }
    }

    public static List<Object> estadisticasActividad(SessionFactory sessionFactory, String idActividad) {
    List<Object> resultado = new ArrayList<>();
    Session session = null;
    Transaction tr = null;
    try {
        session = sessionFactory.openSession();
        tr = session.beginTransaction();

        StoredProcedureQuery llamada = session.createStoredProcedureQuery("EstadisticasActividad")
                .registerStoredProcedureParameter("p_idActividad", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("r_nSocio", Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("r_edadMedia", Double.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("r_categoria", Character.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("r_ingreso", Double.class, ParameterMode.OUT)
                .setParameter("p_idActividad", idActividad);

        llamada.execute();

   
        Object nSociosObj = llamada.getOutputParameterValue("r_nSocio");
        Object edadMediaObj = llamada.getOutputParameterValue("r_edadMedia");
        Object categoriaObj = llamada.getOutputParameterValue("r_categoria");
        Object ingresoObj = llamada.getOutputParameterValue("r_ingreso");

      
        Integer numSocios = (nSociosObj != null) ? Integer.parseInt(nSociosObj.toString()) : 0;
        Double edadMedia = (edadMediaObj != null) ? Double.parseDouble(edadMediaObj.toString()) : 0.0;
        Character categoria = (categoriaObj != null) ? categoriaObj.toString().charAt(0) : '-';
        Double ingreso = (ingresoObj != null) ? Double.parseDouble(ingresoObj.toString()) : 0.0;

        resultado.add(numSocios);
        resultado.add(edadMedia);
        resultado.add(categoria);
        resultado.add(ingreso);

        tr.commit();

    } catch (Exception e) {
        if (tr != null) tr.rollback();
        e.printStackTrace(); 
    } finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    return resultado;
}

}
