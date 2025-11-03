/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author rubco
 */
public class SocioDAO {

    public SocioDAO() {

    }

    public static void insertaSocio(Session session, Socio socio) throws Exception {
        session.persist(socio);
    }

    public static Socio buscarSocioDni(Session session, String dni) throws Exception{
        Query query = session.createNativeQuery("SELECT * FROM SOCIO s WHERE s.dni = :dniP", Socio.class);
        query.setParameter("dniP", dni);

        return (Socio) query.getSingleResult();
    }
    
    public static Socio buscarId(Session session, String id){
        return session.find(Socio.class, id);
    }

}
