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
public class MonitorDAO {

    public static String codMonitorDni(Session session, String dni) {
        Query consulta = session.createNativeQuery("SELECT m.codMonitor FROM MONITOR m WHERE m.dni = :dniP", Object.class);
        consulta.setParameter("dniP", dni);
        return (String) consulta.getSingleResult();
    }

    public static List<Actividad> getActividadesMonitor(Session session, String codMon) {
        Query consulta = session.createNativeQuery("SELECT * FROM ACTIVIDAD a WHERE a.monitorResponsable = :codMon", Actividad.class);
        consulta.setParameter("codMon", codMon);
        return consulta.getResultList();
    }
}
