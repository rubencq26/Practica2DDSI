/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Socio;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author rubco
 */
public class ControladorActividad {

    private final SessionFactory sessionFactory;
    private ActividadDAO actividadDAO = null;

    public ControladorActividad(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        menuActividad();
    }

    public void getInscripciones(String codActividad) {
        Session session = null;
        Transaction tr = null;

        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();

            Actividad a = actividadDAO.buscarId(session, codActividad);
            if (a == null) {
                throw new Exception("Actividad no encontrada");
            }
            Vista.VistaMensajes.mensajeConsola("Numero de socios apuntados: " + a.getSocioSet().size());
            Vista.VistaMensajes.mostrarListaSocios(a.getSocioSet());

            tr.commit();

        } catch (Exception e) {
            Vista.VistaMensajes.errorConsola(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public void getInscripciones() {
        Scanner sc = new Scanner(System.in);

        Vista.VistaMensajes.pedirDato("Introduzca el id de la actividad a buscar: ");
        String idAct = sc.next();

        getInscripciones(idAct);
      
    }

    public void menuActividad() {
        Vista.VistaMenu.menuActividades();
        Vista.VistaMensajes.pedirDato("Introduzca una opcion: ");
        Scanner sc = new Scanner(System.in);
        int opc = sc.nextInt();
        if (opc == 1) {
            getInscripciones();
        }
      
    }

}
