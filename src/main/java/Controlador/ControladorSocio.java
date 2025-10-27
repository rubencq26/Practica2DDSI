/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import java.util.Scanner;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author rubco
 */
public class ControladorSocio {

    private final SessionFactory sessionFactory;
    private SocioDAO socioDAO = null;

    public ControladorSocio(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        socioDAO = new SocioDAO();
    }

    public void altaSocio() {
        Session session = null;
        Transaction tr = null;

        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();

            String numSocio;
            String nombre;
            String dni;
            String fNac;
            String tel;
            String correo;
            String fEntrada;
            Character categoria;

            Scanner sc = new Scanner(System.in);
            System.out.println("Introduzca el numero de socio(SXXX): ");
            numSocio = sc.next();
            Socio s = session.find(Socio.class, numSocio);
            if (s != null) {
                System.out.println("Error: el numSocio ya existe: ");
                return;
            }

            sc.nextLine();

            System.out.println("Introduce el nombre y apellido del socio");
            nombre = sc.nextLine();
            System.out.println("Introduzca el dni del socio: ");
            dni = sc.next();
            try {
                Query query = session.createNativeQuery("SELECT * FROM SOCIO s WHERE s.dni = :dniP", Socio.class);
                query.setParameter("dniP", dni);

                s = (Socio) query.getSingleResult();
                System.out.println("Error el dni ya existe");
                return;

            } catch (Exception e) {
            }
            System.out.println("Introduzca la fecha de nacimiento(dd/mm/yyyy): ");
            fNac = sc.next();
            System.out.println("Introduzca el telefono: ");
            tel = sc.next();
            System.out.println("Introduzca el correo: ");
            correo = sc.next();
            System.out.println("Introduzca la fecha de entrada(dd/mm/yyyy): ");
            fEntrada = sc.next();
            System.out.println("Introduzca la categoria: ");
            categoria = sc.next().charAt(0);
            
            Socio socio = new Socio(numSocio, nombre, dni, fNac, tel, correo, fEntrada, categoria);
            
            socioDAO.insertaSocio(session, socio);
            tr.commit();
            new Vista.VistaMensajes().mensajeConsola("Socio insertado correctamente");
        } catch (Exception e) {
            if(tr != null && tr.isActive()){
                tr.rollback();
            }
            new Vista.VistaMensajes().errorConsola(e.getMessage());
        }finally{
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }

}
