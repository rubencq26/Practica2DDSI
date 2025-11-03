/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Socio;
import java.util.List;
import java.util.Set;

/**
 *
 * @author rubco
 */
public class VistaMensajes {
    
    public static void mensajeConsola(String texto){
         System.out.println("***************************************");
         System.out.println(texto);
         System.out.println("***************************************");
    }
    
    public static void errorConsola(String text){
        System.out.println("Error: " + text);
    }
    
    public static void pedirDato(String texto){
        System.out.println(texto);
    }
    
    public static void mostrarListaSocios(Set<Socio> lSocio){
        for(Socio s:  lSocio){
            System.out.println(s.toString());
        }
    }
    
    
    
}
