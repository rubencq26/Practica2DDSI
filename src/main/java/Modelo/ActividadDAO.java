/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import org.hibernate.Session;

/**
 *
 * @author rubco
 */
public class ActividadDAO {
    
    public static Actividad buscarId(Session session, String idAct){
        return session.find(Actividad.class, idAct);
    }
    
}
