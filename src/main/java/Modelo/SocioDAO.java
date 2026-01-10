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
public class SocioDAO {

    public SocioDAO() {

    }

    public static boolean insertarSocio(Session session, Socio socio) {
        try {
            session.persist(socio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Socio buscarSocioDni(Session session, String dni) throws Exception{
        Query query = session.createNativeQuery("SELECT * FROM SOCIO s WHERE s.dni = :dniP", Socio.class);
        query.setParameter("dniP", dni);

        return (Socio) query.getSingleResult();
    }
    
    public static Socio buscarId(Session session, String id){
        return session.find(Socio.class, id);
    }
    
    public static List<Socio> listarSocios(Session session){
        Query consulta = session.createNativeQuery("SELECT * FROM SOCIO s", Socio.class);
        System.out.println("Socios encontrados");
        return consulta.getResultList();
    }
    
    public static boolean eliminarSocio(Session session, Socio socio) {
        try {
            // Buscamos el objeto primero para asegurarnos de que existe y está gestionado
            if (socio != null) {
                session.remove(socio);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public static boolean actualizarSocio(Session session, Socio socio){
        try {
            // Buscamos el objeto primero para asegurarnos de que existe y está gestionado
            if (socio != null) {
                session.merge(socio);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    
    }

}
