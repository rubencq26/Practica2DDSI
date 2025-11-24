/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import Modelo.Monitor;
import Vista.MonitorPanel;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author rubco
 */
public class GestionTablas {
    
    private static DefaultTableModel modeloTablaMonitores;
    
    public static void inicializarTablaMonitor(Vista.MonitorPanel vMonitor){
            modeloTablaMonitores = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
           
        };
            vMonitor.tablaMonitor.setModel(modeloTablaMonitores);
        
    }
    
    public static void dibujarTablaMonitores(MonitorPanel vMonitor){
        String[] columnas = {"Codigo", "Nombre", "DNI", "Teléfono", "Correo", "Fecha Incorporación", "Nick"};
        modeloTablaMonitores.setColumnIdentifiers(columnas);
        
        JTable t = vMonitor.tablaMonitor;
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        t.getTableHeader().setResizingAllowed(false);
        t.getTableHeader().setReorderingAllowed(false);
        t.setAutoCreateRowSorter(true);
        int[] anchuras = {60, 300, 100, 100, 270, 150, 100};
        
        TableColumnModel modeloColumna = t.getColumnModel();
        
        for(int i = 0; i < anchuras.length; i++){
            TableColumn columna = modeloColumna.getColumn(i);
            int ancho = anchuras[i];
            columna.setMinWidth(ancho);
            columna.setPreferredWidth(ancho);
        }
        
    }
    
    public static void rellenarTablaMonitores(List<Monitor> monitores){
        Object [] fila = new Object[7];
        for (Monitor monitor : monitores){
            fila[0] = monitor.getCodMonitor();
            fila[1] = monitor.getNombre();
            fila[2] = monitor.getDni();
            fila[3] = monitor.getTelefono();
            fila[4] = monitor.getCorreo();
            fila[5] = monitor.getFechaEntrada();
            fila[6] = monitor.getNick();
            modeloTablaMonitores.addRow(fila);
        }
        System.out.println("Tabla rellenada");
    }
    
    public static void vaciarTablaMonitores(){
        modeloTablaMonitores.setRowCount(0);
        System.out.println("Tabla vaciada");
    }
    
}