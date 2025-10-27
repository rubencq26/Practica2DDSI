/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Aplicacion;

import Modelo.Actividad;
import Modelo.Monitor;
import Modelo.Socio;
import org.hibernate.SessionFactory;
import config.HibernateUtil;
import java.util.List;
import java.util.Scanner;
import java.lang.Object;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Ruben
 */
public class practica2 {

    /**
     * @param args the command line arguments
     */
    private static Session sesion;
    private static SessionFactory sesionFactory = conectarDB();

    public static void main(String[] args) {

        if (sesionFactory == null) {
            return;
        }
        listarSociosSQL();
        

    }

    public static SessionFactory conectarDB() {
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

    public static void listarSociosHQL() {
        sesion = sesionFactory.openSession();
        Transaction tr = null;
        try {
            tr = sesion.beginTransaction();
            Query consulta = sesion.createQuery("FROM Socio", Socio.class);
            List<Socio> socios = consulta.getResultList();
            System.out.println("Numero de Socio | DNI   | Nombre       | FNac   | Telefono  |  Correo      | FEntrada  |  Categoria    | Actividades ");
            for (Socio s : socios) {
                System.out.println(s.toString());

            }
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }

    }

    public static void listarSociosSQL() {
        try {
            sesion = sesionFactory.openSession();
            Transaction tr = null;
            try {
                tr = sesion.beginTransaction();
                Query consulta = sesion.createNativeQuery("SELECT * FROM SOCIO", Socio.class);
                List<Socio> socios = consulta.getResultList();
                System.out.println("Numero de Socio | DNI   | Nombre       | FNac   | Telefono  |  Correo      | FEntrada  |  Categoria    | Actividades ");
                for (Socio s : socios) {
                    System.out.println(s.toString());

                }

            } catch (Exception e) {
                tr.rollback();
                System.out.println("Error: " + e.getMessage());
            } finally {
                if (sesion != null && sesion.isOpen()) {
                    sesion.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void listarSociosNamed() {
        try {
            sesion = sesionFactory.openSession();
            Transaction tr = null;
            try {
                tr = sesion.beginTransaction();
                Query consulta = sesion.createNamedQuery("Socio.findAll", Socio.class);
                List<Socio> socios = consulta.getResultList();
                System.out.println("Numero de Socio | DNI   | Nombre       | FNac   | Telefono  |  Correo      | FEntrada  |  Categoria    | Actividades ");
                for (Socio s : socios) {
                    System.out.println(s.toString());

                }

            } catch (Exception e) {
                tr.rollback();
                System.out.println("Error: " + e.getMessage());
            } finally {
                if (sesion != null && sesion.isOpen()) {
                    sesion.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void listarNombreTelefonoSocios() {
        try {
            sesion = sesionFactory.openSession();
            Transaction tr = null;
            try {
                tr = sesion.beginTransaction();
                Query consulta = sesion.createNativeQuery("SELECT s.nombre, s.telefono FROM SOCIO s", Object[].class);
                List<Object[]> socios = consulta.getResultList();
                System.out.println("Nombre  | Telefono ");
                for (Object[] s : socios) {
                    System.out.println(s[0] + " | " + s[1]);
                }

            } catch (Exception e) {
                tr.rollback();
                System.out.println("Error: " + e.getMessage());
            } finally {
                if (sesion != null && sesion.isOpen()) {
                    sesion.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void listarNombresPorCategorias(String categoria) {
        try {
            sesion = sesionFactory.openSession();
            Transaction tr = null;

            try {
                tr = sesion.beginTransaction();
                Query consulta = sesion.createNativeQuery("SELECT s.nombre, s.categoria FROM SOCIO s WHERE s.categoria='" + categoria + "'", Object[].class);
                List<Object[]> socios = consulta.getResultList();
                System.out.println("Nombre  |  Categoria");

                for (Object[] s : socios) {
                    System.out.println(s[0] + " | " + s[1]);
                }

            } catch (Exception e) {
                tr.rollback();
                System.out.println("Error: " + e.getMessage());
            } finally {
                if (sesion != null && sesion.isOpen()) {
                    sesion.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void listarNombresPorCategorias() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca la categoria: ");
        String cat = sc.nextLine();
        listarNombresPorCategorias(cat);
    }

    public static void mostrarNombreMonitorNick(String nick) {
        try {
            sesion = sesionFactory.openSession();
            Transaction tr = null;

            try {
                tr = sesion.beginTransaction();
                Query consulta = sesion.createNativeQuery("SELECT s.nombre FROM MONITOR s WHERE s.nick='" + nick + "'", Object.class);
                Object monitor = consulta.getSingleResult();
                System.out.println("Nombre: " + monitor);

            } catch (Exception e) {
                tr.rollback();
                System.out.println("Error: " + e.getMessage());
            } finally {
                if (sesion != null && sesion.isOpen()) {
                    sesion.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void mostrarNombreMonitorNick() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el nick del monitor: ");
        String nick = sc.nextLine();

        mostrarNombreMonitorNick(nick);
    }

    public static void mostrarInformacionSocio(String nombre) {
        try {
            sesion = sesionFactory.openSession();
            Transaction tr = null;

            try {
                tr = sesion.beginTransaction();
                Query consulta = sesion.createNativeQuery("SELECT * FROM SOCIO s WHERE s.nombre LIKE :nombre", Socio.class);
                consulta.setParameter("nombre", "%" + nombre + "%");
                Socio socio = (Socio) consulta.getSingleResult();
                System.out.println("nSocio | Nombre | dni | fNac | Telefono | Correo electronico | fEntrada | Categoria");
                System.out.println(socio.getNumeroSocio() + " | " + socio.getNombre() + " | " + socio.getDni() + " | " + socio.getFechaNacimiento() + " | " + socio.getCorreo() + " | " + socio.getFechaEntrada() + " | " + socio.getCategoria());

            } catch (Exception e) {
                tr.rollback();
                System.out.println("Error: " + e.getMessage());
            } finally {
                if (sesion != null && sesion.isOpen()) {
                    sesion.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void mostrarInformacionSocio() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el nombre por teclado: ");
        String nombre = sc.nextLine();
        mostrarInformacionSocio(nombre);
    }

    public static void informacionActividadesDiaCuota(String dia, String cuota) {
        try {
            sesion = sesionFactory.openSession();
            Transaction tr = null;

            try {
                tr = sesion.beginTransaction();
                Query consulta = sesion.createNativeQuery(
                        "SELECT * FROM ACTIVIDAD s WHERE s.dia LIKE :dia AND s.precioBaseMes >= :cuota",
                        Actividad.class
                );
                consulta.setParameter("dia", "%" + dia + "%");
                consulta.setParameter("cuota", cuota);
                List<Actividad> actividades = consulta.getResultList();
                System.out.println("idActividad | Nombre | Dia | Hora | Descripcion | Precio Base Mes | Monitor responsable");

                for (Actividad a : actividades) {
                    System.out.println(a.toString());
                }

            } catch (Exception e) {
                tr.rollback();
                System.out.println("Error: " + e.getMessage());
            } finally {
                if (sesion != null && sesion.isOpen()) {
                    sesion.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void informacionActividadesDiaCuota() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el dia: ");
        String dia = sc.nextLine();
        System.out.println("Introduzca la cuota: ");
        String cuota = sc.nextLine();
        informacionActividadesDiaCuota(dia, cuota);
    }

    public static void informacionSociosCategoriaHQL(char categoria) {
        sesion = sesionFactory.openSession();
        Transaction tr = null;

        try {
            tr = sesion.beginTransaction();

            Query<Socio> consulta = sesion.createQuery(
                    "FROM Socio a WHERE a.categoria = :categoria",
                    Socio.class
            );
            consulta.setParameter("categoria", categoria);

            List<Socio> socios = consulta.getResultList();

            System.out.println("Numero de Socio | DNI   | Nombre       | FNac   | Telefono  | Correo      | FEntrada  | Categoria | Actividades");

            for (Socio s : socios) {
                System.out.println(s.toString());
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public static void informacionSociosCategoriaHQL(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca la categoria: ");
        String c = sc.next();
        char categoria = c.charAt(0);
        informacionSociosCategoriaHQL(categoria);
    }
    
    
    public static void informacionSociosCategoriaSQL(char categoria) {
        sesion = sesionFactory.openSession();
        Transaction tr = null;

        try {
            tr = sesion.beginTransaction();

            Query<Socio> consulta = sesion.createNativeQuery(
                    "SELECT * FROM SOCIO a WHERE a.categoria = :categoria",
                    Socio.class
            );
            consulta.setParameter("categoria", categoria);

            List<Socio> socios = consulta.getResultList();

            System.out.println("Numero de Socio | DNI   | Nombre       | FNac   | Telefono  | Correo      | FEntrada  | Categoria | Actividades");

            for (Socio s : socios) {
                System.out.println(s.toString());
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public static void informacionSociosCategoriaSQL(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca la categoria: ");
        String c = sc.next();
        char categoria = c.charAt(0);
        informacionSociosCategoriaSQL(categoria);
    }
    
    public static void insertarSocio(){
        sesion = sesionFactory.openSession();
        Transaction tr = null;
        
        try {
            tr = sesion.beginTransaction();
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
            Socio s = sesion.find(Socio.class, numSocio);
            if(s != null){
                System.out.println("Error: el numSocio ya existe: ");
                return;
            }
            
            sc.nextLine();
            
            System.out.println("Introduce el nombre y apellido del socio");
            nombre = sc.nextLine();
            System.out.println("Introduzca el dni del socio: ");
            dni = sc.next();
            try {
                Query query = sesion.createNativeQuery("SELECT * FROM SOCIO s WHERE s.dni = :dniP", Socio.class);
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
            
            
            
            Socio nuevoSocio = new Socio(numSocio, nombre, dni, fNac, tel, correo, fEntrada, categoria);
            
            sesion.persist(nuevoSocio);
            
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        }finally{
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public static void borradoPorDni(String dni){
        sesion = sesionFactory.openSession();
        Transaction tr = null;
        try{
            tr = sesion.beginTransaction();
            try {
                Query query = sesion.createNativeQuery("SELECT * FROM SOCIO s WHERE s.dni = :dniP", Socio.class);
                query.setParameter("dniP", dni);
                
                Socio s = (Socio) query.getSingleResult();
                sesion.remove(s);
                
                System.out.println("Tupla eliminada");
               
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            tr.commit();
        }catch(Exception e){
            if(tr != null){
                tr.rollback();
            }
            
        }finally{
            if (sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
        
    }
    
    
    public static void borradoPorDni(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el dni a eliminar");
        String dni = sc.next();
        borradoPorDni(dni);
    }
    
    public static void actividadesMonitorResponsableDni(String dni){
        sesion = sesionFactory.openSession();
        Transaction tr = null;
        
        try{
            tr = sesion.beginTransaction();
            try {
                Query consulta = sesion.createNativeQuery("SELECT m.codMonitor FROM MONITOR m WHERE m.dni = :dniP", Object.class);
                consulta.setParameter("dniP", dni);
                String codMonitor = (String) consulta.getSingleResult();
                
                consulta = sesion.createNativeQuery("SELECT * FROM ACTIVIDAD a WHERE a.monitorResponsable = :codMon", Actividad.class);
                consulta.setParameter("codMon", codMonitor);
                List<Actividad> actividades = consulta.getResultList();
                
                for(Actividad a : actividades){
                    System.out.println(a.toString());
                }
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                
            }
            tr.commit();
        }catch(Exception e){
            if(tr != null){
                tr.rollback();
            }
            
        }finally{
            if(sesion != null && sesion.isOpen()){
                sesion.close();
            }
        }
        
        
    }
    
    public static void actividadesMonitorResponsableDni(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el dni del monitor");
        String dni = sc.next();
        actividadesMonitorResponsableDni(dni);
        
    }
    
    
    
}
