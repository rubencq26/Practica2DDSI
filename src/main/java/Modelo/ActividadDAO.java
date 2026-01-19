/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author rubco
 */
public class ActividadDAO {

    public static Actividad buscarId(Session session, String idAct) {
        return session.find(Actividad.class, idAct);
    }

    public static List<Object[]> listarActividades(Session session) {
        Query consulta = session.createNativeQuery("SELECT a.idActividad, a.nombre, a.dia, a.hora, a.descripcion, a.precioBaseMes, m.nombre FROM ACTIVIDAD a INNER JOIN MONITOR m WHERE m.codMonitor = a.monitorResponsable", Object.class);
        System.out.println("Actividades encontrados");
        return consulta.getResultList();
    }

    public static List<Actividad> listarActividadesA(Session session) {
        Query consulta = session.createNativeQuery("SELECT * FROM ACTIVIDAD", Actividad.class);
        System.out.println("Actividades encontrados");
        return consulta.getResultList();
    }

    public static boolean insertarActividad(Session session, Actividad actividad) {
        try {
            session.persist(actividad);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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

}
