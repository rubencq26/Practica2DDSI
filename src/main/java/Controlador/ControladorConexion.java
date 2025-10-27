/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import config.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 *
 * @author rubco
 */
public class ControladorConexion {
    
    public ControladorConexion(){
        SessionFactory sessionFactory = conectarBD();
        new ControladorPrincipal(sessionFactory);
    }
    
    public static SessionFactory conectarBD(){
        try {
            // Se obtiene la SessionFactory definida en HibernateUtil
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            System.out.println("Conexion Correcta con Hibernate");
            return sessionFactory;
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            System.out.println("Error en la conexion. Revise el fichero .cfg.xml: " + cause.getMessage());
            return null;
        }
    }
}
