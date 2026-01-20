/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "MONITOR")
@NamedQueries({
    @NamedQuery(name = "Monitor.findAll", query = "SELECT m FROM Monitor m"),
    @NamedQuery(name = "Monitor.findByCodMonitor", query = "SELECT m FROM Monitor m WHERE m.codMonitor = :codMonitor"),
    @NamedQuery(name = "Monitor.findByNombre", query = "SELECT m FROM Monitor m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Monitor.findByDni", query = "SELECT m FROM Monitor m WHERE m.dni = :dni"),
    @NamedQuery(name = "Monitor.findByTelefono", query = "SELECT m FROM Monitor m WHERE m.telefono = :telefono"),
    @NamedQuery(name = "Monitor.findByCorreo", query = "SELECT m FROM Monitor m WHERE m.correo = :correo"),
    @NamedQuery(name = "Monitor.findByFechaEntrada", query = "SELECT m FROM Monitor m WHERE m.fechaEntrada = :fechaEntrada"),
    @NamedQuery(name = "Monitor.findByNick", query = "SELECT m FROM Monitor m WHERE m.nick = :nick")})
public class Monitor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codMonitor")
    private String codMonitor;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "dni")
    private String dni;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "fechaEntrada")
    private String fechaEntrada;
    @Column(name = "nick")
    private String nick;
    @OneToMany(mappedBy = "monitorResponsable")
    private Set<Actividad> actividadSet;

    public Monitor() {
        this.codMonitor = "";
        this.nombre = "";
        this.dni = "";
        this.telefono = "";
        this.correo = "";
        this.fechaEntrada = "";
        this.nick = "";
        
    }

    public Monitor(String codMonitor) {
        this.codMonitor = codMonitor;
    }

    public Monitor(String codMonitor, String nombre, String dni, String fechaEntrada) {
        this.codMonitor = codMonitor;
        this.nombre = nombre;
        this.dni = dni;
        this.fechaEntrada = fechaEntrada;
    }
    
    public Monitor(String codMonitor, String nombre, String dni, String telefono, String correo, String fechaEntrada, String nick) {
        this.codMonitor = codMonitor;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaEntrada = fechaEntrada;
        this.nick = nick;
    }
    
    public Monitor(String codMonitor, String nombre, String dni, String telefono, String correo, String fechaEntrada) {
        this.codMonitor = codMonitor;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaEntrada = fechaEntrada;
        this.nick = "";
    }

    public Monitor(String codMonitor, String nombre, String dni, String telefono, String correo, String fechaEntrada, String nick, Set<Actividad> actividadSet) {
        this.codMonitor = codMonitor;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaEntrada = fechaEntrada;
        this.nick = nick;
        this.actividadSet = actividadSet;
    }
    
    

    public String getCodMonitor() {
        return codMonitor;
    }

    public void setCodMonitor(String codMonitor) {
        this.codMonitor = codMonitor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Set<Actividad> getActividadSet() {
        return actividadSet;
    }

    public void setActividadSet(Set<Actividad> actividadSet) {
        this.actividadSet = actividadSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMonitor != null ? codMonitor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Monitor)) {
            return false;
        }
        Monitor other = (Monitor) object;
        if ((this.codMonitor == null && other.codMonitor != null) || (this.codMonitor != null && !this.codMonitor.equals(other.codMonitor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Monitor[ codMonitor=" + codMonitor + " ]";
    }
    
}
