/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * Clase DAO para la gestión de operaciones de persistencia de la entidad Socio.
 * Permite realizar operaciones CRUD y búsquedas específicas sobre los socios del sistema.
 * * @author rubco
 */
public class SocioDAO {

    /**
     * Constructor por defecto de la clase SocioDAO.
     */
    public SocioDAO() {

    }

    /**
     * Inserta un nuevo socio en la base de datos.
     * @param session Sesión activa de Hibernate.
     * @param socio Objeto Socio que se desea persistir.
     * @return true si la inserción fue exitosa, false en caso de error.
     */
    public static boolean insertarSocio(Session session, Socio socio) {
        try {
            session.persist(socio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Busca un socio en la base de datos utilizando su número de DNI.
     * @param session Sesión activa de Hibernate.
     * @param dni DNI del socio a buscar.
     * @return El objeto Socio que coincide con el DNI proporcionado.
     * @throws Exception Si ocurre un error durante la consulta o no se encuentra el resultado.
     */
    public static Socio buscarSocioDni(Session session, String dni) throws Exception{
        return session.createNamedQuery("Socio.findByDni", Socio.class)
                  .setParameter("dni", dni)
                  .getSingleResult();
    }
    
    /**
     * Busca un socio por su identificador único (clave primaria).
     * @param session Sesión activa de Hibernate.
     * @param id Identificador único del socio.
     * @return El objeto Socio encontrado o null si no existe.
     */
    public static Socio buscarId(Session session, String id){
        return session.find(Socio.class, id);
    }
    
    /**
     * Recupera la lista completa de todos los socios registrados.
     * @param session Sesión activa de Hibernate.
     * @return List de objetos Socio con todos sus datos.
     */
    public static List<Socio> listarSocios(Session session){
        Query consulta = session.createNativeQuery("SELECT * FROM SOCIO s", Socio.class);
        System.out.println("Socios encontrados");
        return consulta.getResultList();
    }
    
    /**
     * Elimina un socio de la base de datos.
     * @param session Sesión activa de Hibernate.
     * @param socio Objeto Socio que se desea eliminar.
     * @return true si el socio fue eliminado correctamente, false si el objeto es nulo o hubo un error.
     */
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
    
    /**
     * Actualiza la información de un socio existente mediante la operación merge.
     * @param session Sesión activa de Hibernate.
     * @param socio Objeto Socio con los datos actualizados.
     * @return true si la actualización se realizó con éxito, false en caso contrario.
     */
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