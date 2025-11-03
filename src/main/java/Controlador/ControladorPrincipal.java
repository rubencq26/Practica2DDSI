/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import config.HibernateUtil;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author rubco
 */
public class ControladorPrincipal {

    private final SessionFactory sessionFactory;

    public ControladorPrincipal(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        if (sessionFactory != null) {
            menu();
        }

    }

    public void menu() {
        String entrada;
        Scanner sc = new Scanner(System.in);
        int opc = 0;

        do {
            Vista.VistaMenu.menuPrincipalConsola();
            Vista.VistaMensajes.pedirDato("Introduzca una opcion: ");
            entrada = sc.nextLine();
            try {
                opc = Integer.parseInt(entrada);
            
            switch (opc) {
                case 1:
                    new ControladorSocio(sessionFactory);
                    break;
                case 2:
                    new ControladorMonitor(sessionFactory);
                    break;
                case 3:
                    new ControladorActividad(sessionFactory);
                    break;
                case 4:
                    break;
                default:
                    Vista.VistaMensajes.errorConsola("Elija una opción valida");
                    break;
            }
            }catch(Exception e){
                Vista.VistaMensajes.errorConsola(e.getMessage());
            }
        } while (opc != 4);
        
    }

    
}
