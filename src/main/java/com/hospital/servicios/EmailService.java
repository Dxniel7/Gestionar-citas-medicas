package com.hospital.servicios;

import com.hospital.dominio.entidades.CitaConsulta;

public interface EmailService {
    /**
     * Envía una notificación por correo al paciente y al doctor cuando se crea una nueva cita.
     * @param cita La entidad CitaConsulta recién creada.
     */
    public void enviarNotificacionNuevaCita(CitaConsulta cita);

    /**
     * Envía una notificación por correo al paciente y al doctor cuando una cita se cancela.
     * @param cita La entidad CitaConsulta que fue cancelada.
     * @param motivo El motivo de la cancelación.
     */
    public void enviarNotificacionCancelacion(CitaConsulta cita, String motivo);

    /**
     * Envía un correo de bienvenida a un nuevo usuario.
     * @param destinatario La dirección de correo del nuevo usuario.
     * @param nombre El nombre del nuevo usuario.
     */
    public void enviarNotificacionBienvenida(String destinatario, String nombre);
}