/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.SocioDAO;
import Modelo.Socio;
import Vista.VistaMensajes;
import Vista.VistaMenu;

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
/*
    private final SessionFactory sessionFactory;
    

    private VistaMensajes vMensajes;

    public ControladorSocio(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        menuSocio();
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
            VistaMensajes.pedirDato("Introduzca el numero de socio(SXXX): ");
            numSocio = sc.next();
            Socio s = SocioDAO.buscarId(session, numSocio);
            if (s != null) {
                vMensajes.errorConsola("Error: el numSocio ya existe: ");
                return;
            }

            sc.nextLine();

            VistaMensajes.pedirDato("Introduce el nombre y apellido del socio: ");
            nombre = sc.nextLine();
            VistaMensajes.pedirDato("Introduzca el dni del socio: ");
            dni = sc.next();
            try {
                SocioDAO.buscarSocioDni(session, dni);
                vMensajes.errorConsola("El dni ya existe");
                return;

            } catch (Exception e) {
            }
            VistaMensajes.pedirDato("Introduzca la fecha de nacimiento(dd/mm/yyyy): ");
            fNac = sc.next();
            VistaMensajes.pedirDato("Introduzca el telefono: ");
            tel = sc.next();
            VistaMensajes.pedirDato("Introduzca el correo: ");
            correo = sc.next();
            VistaMensajes.pedirDato("Introduzca la fecha de entrada(dd/mm/yyyy): ");
            fEntrada = sc.next();
            VistaMensajes.pedirDato("Introduzca la categoria: ");
            categoria = sc.next().charAt(0);
            
            Socio socio = new Socio(numSocio, nombre, dni, fNac, tel, correo, fEntrada, categoria);
            
            SocioDAO.insertaSocio(session, socio);
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
    
    public void menuSocio(){
        VistaMenu.menuSocio();
        int opc;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la opcion: ");
        opc = sc.nextInt();
        if(opc == 1){
            altaSocio();
        }
        
    }
*/
}
