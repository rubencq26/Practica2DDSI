/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "ACTIVIDAD")
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByIdActividad", query = "SELECT a FROM Actividad a WHERE a.idActividad = :idActividad"),
    @NamedQuery(name = "Actividad.findByNombre", query = "SELECT a FROM Actividad a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Actividad.findByDia", query = "SELECT a FROM Actividad a WHERE a.dia = :dia"),
    @NamedQuery(name = "Actividad.findByHora", query = "SELECT a FROM Actividad a WHERE a.hora = :hora"),
    @NamedQuery(name = "Actividad.findByDescripcion", query = "SELECT a FROM Actividad a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Actividad.findByPrecioBaseMes", query = "SELECT a FROM Actividad a WHERE a.precioBaseMes = :precioBaseMes")})
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idActividad")
    private String idActividad;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "dia")
    private String dia; 
    
    @Basic(optional = false)
    @Column(name = "hora")
    private int hora;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "precioBaseMes")
    private int precioBaseMes;
    @JoinTable(name = "REALIZA", joinColumns = {
        @JoinColumn(name = "idActividad", referencedColumnName = "idActividad")}, inverseJoinColumns = {
        @JoinColumn(name = "numeroSocio", referencedColumnName = "numeroSocio")})
    @ManyToMany
    private Set<Socio> socioSet;
    @JoinColumn(name = "monitorResponsable", referencedColumnName = "codMonitor")
    @ManyToOne
    private Monitor monitorResponsable;

    public Actividad() {
    }

    public Actividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public Actividad(String idActividad, String nombre, String dia, int hora, int precioBaseMes) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.dia = dia;
        this.hora = hora;
        this.precioBaseMes = precioBaseMes;
    }

    public Actividad(String idActividad, String nombre, String dia, int hora, String descripcion, int precioBaseMes, Set<Socio> socioSet, Monitor monitorResponsable) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.dia = dia;
        this.hora = hora;
        this.descripcion = descripcion;
        this.precioBaseMes = precioBaseMes;
        this.socioSet = socioSet;
        this.monitorResponsable = monitorResponsable;
    }
    
    public Actividad(String idActividad, String nombre, String dia, int hora, String descripcion, int precioBaseMes, Monitor monitorResponsable) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.dia = dia;
        this.hora = hora;
        this.descripcion = descripcion;
        this.precioBaseMes = precioBaseMes;
        this.monitorResponsable = monitorResponsable;
        
    }
    
    public Actividad(String idActividad, String nombre, String dia, int hora, String descripcion, int precioBaseMes) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.dia = dia;
        this.hora = hora;
        this.descripcion = descripcion;
        this.precioBaseMes = precioBaseMes;
        this.monitorResponsable = monitorResponsable;
        
    }
    
    

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecioBaseMes() {
        return precioBaseMes;
    }

    public void setPrecioBaseMes(int precioBaseMes) {
        this.precioBaseMes = precioBaseMes;
    }

    public Set<Socio> getSocioSet() {
        return socioSet;
    }

    public void setSocioSet(Set<Socio> socioSet) {
        this.socioSet = socioSet;
    }

    public Monitor getMonitorResponsable() {
        return monitorResponsable;
    }

    public void setMonitorResponsable(Monitor monitorResponsable) {
        this.monitorResponsable = monitorResponsable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getIdActividad() + " | " + getNombre() + " | " + getDia() + " | " + getHora() + " | " + getDescripcion() + " | " + getPrecioBaseMes() + " | " + getMonitorResponsable();
    }
    
}
