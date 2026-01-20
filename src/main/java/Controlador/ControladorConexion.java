/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaConexion;
import Vista.VistaMensajes;
import config.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.hibernate.SessionFactory;

/**
 * Controlador encargado de gestionar la ventana de login y la conexión inicial a Hibernate.
 * Administra la captura de credenciales del usuario y la instanciación del SessionFactory.
 * * @author rubco
 */
public class ControladorConexion implements ActionListener{

    private SessionFactory sessionFactory;
    private VistaConexion vConexion;
    private String user;
    private String pass;


    /**
     * Constructor del controlador de conexión.
     * Inicializa la vista de login, configura sus propiedades visuales y registra los listeners.
     */
    public ControladorConexion() {
        vConexion = new VistaConexion();
        addListeners();
        
        vConexion.pack();
        vConexion.setResizable(false);
        vConexion.setVisible(true);

    }
    
    
    /**
     * Registra los manejadores de eventos para los botones de la vista de conexión.
     */
    private void addListeners(){
        vConexion.entrarButton.addActionListener(this);
        vConexion.salirButton.addActionListener(this);
    }

    /**
     * Gestiona las acciones realizadas sobre la interfaz de conexión.
     * <p>
     * - "EntrarAplicacion": Intenta construir el SessionFactory con las credenciales proporcionadas.
     * Si tiene éxito, cierra la vista y lanza el ControladorPrincipal.
     * <p>
     * - "SalirAplicacion": Cierra la ventana y finaliza la ejecución del programa.
     * * @param e El evento de acción capturado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "EntrarAplicacion" :
                user = vConexion.usuarioTextField.getText().trim();
                pass = new String(vConexion.passwordTextField.getPassword());
                try {
                    sessionFactory = HibernateUtil.buildSessionFactory(user, pass);
                    
                    if(sessionFactory == null || sessionFactory.isClosed()){
                        JOptionPane.showMessageDialog(vConexion, "Error de conexion con hibernate", null, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(vConexion, "Conexion correcta con hibernate", null, JOptionPane.INFORMATION_MESSAGE);
                    vConexion.dispose();
                    new ControladorPrincipal(sessionFactory);
                } catch (Exception ex){
                    HibernateUtil.close();
                    
                }
                break;
                
            case "SalirAplicacion":
                
                vConexion.dispose();
                System.exit(0);
                break;
        }
    }

}