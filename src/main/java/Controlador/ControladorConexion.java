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
 *
 * @author rubco
 */
public class ControladorConexion implements ActionListener{

    private SessionFactory sessionFactory;
    private VistaConexion vConexion;
    private String user;
    private String pass;


    public ControladorConexion() {
        vConexion = new VistaConexion();
        addListeners();
        
        vConexion.pack();
        vConexion.setResizable(false);
        vConexion.setVisible(true);

    }
    
    
    private void addListeners(){
        vConexion.entrarButton.addActionListener(this);
        vConexion.salirButton.addActionListener(this);
    }

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
