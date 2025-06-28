package com.hospital.servicios.impl;

import com.hospital.dominio.entidades.CitaConsulta;
import com.hospital.servicios.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarNotificacionNuevaCita(CitaConsulta cita) {
        SimpleMailMessage message = new SimpleMailMessage();

        String emailPaciente = cita.getPaciente().getUsuario().getEmail();
        String emailDoctor = cita.getDoctor().getUsuario().getEmail();

        message.setTo(emailPaciente);
        message.setCc(emailDoctor); // Poner al doctor en copia

        message.setSubject("Confirmación de Nueva Cita Médica");
        String texto = "Se ha agendado una nueva cita:\n\n"
                + "Paciente: " + cita.getPaciente().getUsuario().getNombre() + " " + cita.getPaciente().getUsuario().getApellidoPat() + "\n"
                + "Doctor: " + cita.getDoctor().getUsuario().getNombre() + " " + cita.getDoctor().getUsuario().getApellidoPat() + "\n"
                + "Especialidad: " + cita.getEspecialidad().getNombre() + "\n"
                + "Fecha: " + cita.getFecha() + "\n"
                + "Hora: " + cita.getHora() + "\n"
                + "Consultorio: " + cita.getConsultorio().getNumero() + "\n\n"
                + "¡Gracias por su confianza!";
        message.setText(texto);

        mailSender.send(message);
    }

    @Override
    public void enviarNotificacionCancelacion(CitaConsulta cita, String motivo) {
        SimpleMailMessage message = new SimpleMailMessage();

        String emailPaciente = cita.getPaciente().getUsuario().getEmail();
        String emailDoctor = cita.getDoctor().getUsuario().getEmail();

        message.setTo(emailPaciente);
        message.setCc(emailDoctor);

        message.setSubject("Notificación de Cancelación de Cita");
        String texto = "Le informamos que su cita ha sido cancelada.\n\n"
                + "Motivo: " + motivo + "\n\n"
                + "Detalles de la cita:\n"
                + "Paciente: " + cita.getPaciente().getUsuario().getNombre() + " " + cita.getPaciente().getUsuario().getApellidoPat() + "\n"
                + "Doctor: " + cita.getDoctor().getUsuario().getNombre() + " " + cita.getDoctor().getUsuario().getApellidoPat() + "\n"
                + "Fecha: " + cita.getFecha() + "\n"
                + "Hora: " + cita.getHora() + "\n\n"
                + "Lamentamos los inconvenientes.";
        message.setText(texto);

        mailSender.send(message);
    }

    @Override
    public void enviarNotificacionBienvenida(String destinatario, String nombre) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(destinatario);

        message.setSubject("¡Bienvenido/a a nuestro Sistema de Citas Médicas!");
        String texto = "Hola " + nombre + ",\n\n"
                + "¡Gracias por registrarte en nuestro sistema de gestión de citas médicas!\n\n"
                + "Ahora puedes agendar y gestionar tus citas de manera fácil y rápida.\n\n"
                + "Saludos cordiales,\n"
                + "El equipo del Hospital";
        message.setText(texto);

        mailSender.send(message);
    }
}