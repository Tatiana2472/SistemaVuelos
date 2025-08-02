/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.model;

import java.sql.Date;
import java.sql.Time;
/**
 *
 * @author tatia
 */
public class Vuelo {
    private int id;
    private String aerolinea;
    private String origen;
    private String destino;
    private Date fecha;
    private Time hora;
    private double precio;
    private int escalas;
    private int capacidad;

    public Vuelo() {}

    public Vuelo(int id, String aerolinea, String origen, String destino, Date fecha, Time hora, double precio, int escalas, int capacidad) {
        this.id = id; this.aerolinea = aerolinea; this.origen = origen; this.destino = destino;
        this.fecha = fecha; this.hora = hora; this.precio = precio; this.escalas = escalas; this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getEscalas() {
        return escalas;
    }

    public void setEscalas(int escalas) {
        this.escalas = escalas;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}  