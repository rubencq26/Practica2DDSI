/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * Clase DAO para la gestión de operaciones de persistencia de la entidad Monitor.
 * Incluye métodos para la búsqueda, listado y mantenimiento de monitores en la base de datos.
 * * @author rubco
 */
public class MonitorDAO {

    /**
     * Obtiene el código de un monitor a partir de su DNI.
     * @param session Sesión activa de Hibernate.
     * @param dni DNI del monitor a buscar.
     * @return El código del monitor (String) asociado al DNI proporcionado.
     */
    public static String codMonitorDni(Session session, String dni) {
        Query consulta = session.createNativeQuery("SELECT m.codMonitor FROM MONITOR m WHERE m.dni = :dniP", Object.class);
        consulta.setParameter("dniP", dni);
        return (String) consulta.getSingleResult();
    }

    /**
     * Recupera la lista de actividades de las que es responsable un monitor específico.
     * @param session Sesión activa de Hibernate.
     * @param codMon Código identificador del monitor.
     * @return List de objetos Actividad asociados al monitor.
     */
    public static List<Actividad> getActividadesMonitor(Session session, String codMon) {
        Query consulta = session.createNativeQuery("SELECT * FROM ACTIVIDAD a WHERE a.monitorResponsable = :codMon", Actividad.class);
        consulta.setParameter("codMon", codMon);
        return consulta.getResultList();
    }

    /**
     * Devuelve una lista con todos los monitores registrados en la base de datos.
     * @param session Sesión activa de Hibernate.
     * @return List de objetos Monitor con todos sus atributos.
     */
    public static List<Monitor> listarMonitores(Session session) {
        Query<Monitor> consulta = session.createQuery("FROM Monitor", Monitor.class);
        System.out.println("Monitores encontrados");
        return consulta.getResultList();
    }

    /**
     * Obtiene el nombre de un monitor dado su código.
     * @param session Sesión activa de Hibernate.
     * @param codMon Código identificador del monitor.
     * @return Nombre del monitor (String).
     */
    public static String getMonitorNombre(Session session, String codMon) {
        Query consulta = session.createNativeQuery("SELECT m.nombre FROM MONITOR m WHERE m.codMonitor = :codMon", String.class);
        consulta.setParameter("codMon", codMon);
        return (String) consulta.getSingleResult();
    }

    /**
     * Inserta un nuevo objeto Monitor en la base de datos.
     * @param session Sesión activa de Hibernate.
     * @param monitor Objeto Monitor a persistir.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public static boolean insertarMonitor(Session session, Monitor monitor) {
        try {
            session.persist(monitor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina un monitor de la base de datos.
     * @param session Sesión activa de Hibernate.
     * @param monitor Objeto Monitor a eliminar.
     * @return true si se eliminó correctamente, false si el objeto es nulo o hubo un error.
     */
    public static boolean eliminarMonitor(Session session, Monitor monitor) {
        try {
            // Buscamos el objeto primero para asegurarnos de que existe y está gestionado
            if (monitor != null) {
                session.remove(monitor);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza los datos de un monitor existente mediante la operación merge.
     * @param session Sesión activa de Hibernate.
     * @param monitor Objeto Monitor con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso de error.
     */
    public static boolean actualizarMonitor(Session session, Monitor monitor){
        try {
            // Buscamos el objeto primero para asegurarnos de que existe y está gestionado
            if (monitor != null) {
                session.merge(monitor);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    
    }
}