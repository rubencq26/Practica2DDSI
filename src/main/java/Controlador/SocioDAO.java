/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import org.hibernate.Session;

/**
 *
 * @author rubco
 */
public class SocioDAO {
    public SocioDAO(){
        
    }
    
    public void insertaSocio(Session session, Socio socio) throws Exception{
        session.persist(socio);
    }
    
    
}
