/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.model;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author tatia
 */
public class Reserva {
    private int id;
    private int usuarioId;
    private int vueloId;
    private LocalDateTime fechaReserva;

    public Reserva() { }

    public Reserva(int id, int vueloId, int usuarioId, LocalDateTime fechaReserva) {
        this.id = id;
        this.vueloId = vueloId;
        this.usuarioId = usuarioId;
        this.fechaReserva = fechaReserva;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getVueloId() {
        return vueloId;
    }

    public void setVueloId(int vueloId) {
        this.vueloId = vueloId;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
}