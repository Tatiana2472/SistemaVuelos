/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavuelos.model;
import java.sql.Timestamp;

/**
 *
 * @author tatia
 */
public class Reserva {
    private int id;
    private int usuarioId;
    private int vueloId;
    private Timestamp fechaReserva;

    public Reserva() {}
    public Reserva(int id, int usuarioId, int vueloId, Timestamp fechaReserva) {
        this.id = id; this.usuarioId = usuarioId; this.vueloId = vueloId; this.fechaReserva = fechaReserva;
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

    public Timestamp getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Timestamp fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
}
