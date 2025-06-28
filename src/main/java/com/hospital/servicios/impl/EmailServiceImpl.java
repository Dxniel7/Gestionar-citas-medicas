package com.hospital.servicios.impl;

import com.hospital.dominio.entidades.CitaConsulta;
import com.hospital.servicios.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    //  Inyectar TemplateEngine 
    @Autowired
    private TemplateEngine templateEngine;

    // Usar MimeMessage para HTML 
    @Override
    public void enviarNotificacionNuevaCita(CitaConsulta cita) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String emailPaciente = cita.getPaciente().getUsuario().getEmail();
            String emailDoctor = cita.getDoctor().getUsuario().getEmail();

            helper.setTo(emailPaciente);
            helper.setCc(emailDoctor);
            helper.setSubject("Confirmación de Nueva Cita Médica");

            // Crear el contexto de Thymeleaf y añadir las variables
            Context context = new Context();
            context.setVariable("nombrePaciente", cita.getPaciente().getUsuario().getNombre());
            context.setVariable("nombreDoctor", "Dr. " + cita.getDoctor().getUsuario().getNombre() + " " + cita.getDoctor().getUsuario().getApellidoPat());
            context.setVariable("especialidad", cita.getEspecialidad().getNombre());
            context.setVariable("fecha", cita.getFecha().toString());
            context.setVariable("hora", cita.getHora().toString());
            context.setVariable("consultorio", cita.getConsultorio().getNumero());
            
            // Procesar la plantilla HTML
            String htmlContent = templateEngine.process("email-nueva-cita", context);
            helper.setText(htmlContent, true); // El 'true' nos indica que es HTML

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            // Manejar la excepción
            System.err.println("Error al enviar el correo HTML: " + e.getMessage());
        }
    }

    @Override
    public void enviarNotificacionCancelacion(CitaConsulta cita, String motivo) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
         try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(cita.getPaciente().getUsuario().getEmail());
            helper.setCc(cita.getDoctor().getUsuario().getEmail());
            helper.setSubject("Notificación de Cancelación de Cita");

            String texto = "<h1>Notificación de Cancelación de Cita</h1>"
                + "<p>Le informamos que su cita ha sido cancelada.</p>"
                + "<p><b>Motivo:</b> " + motivo + "</p>"
                + "<hr>"
                + "<p><b>Detalles de la cita:</b></p>"
                + "<ul>"
                + "<li><b>Paciente:</b> " + cita.getPaciente().getUsuario().getNombre() + " " + cita.getPaciente().getUsuario().getApellidoPat() + "</li>"
                + "<li><b>Doctor:</b> " + cita.getDoctor().getUsuario().getNombre() + " " + cita.getDoctor().getUsuario().getApellidoPat() + "</li>"
                + "<li><b>Fecha:</b> " + cita.getFecha() + "</li>"
                + "</ul>"
                + "<p>Lamentamos los inconvenientes.</p>";

            helper.setText(texto, true);
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo de cancelación: " + e.getMessage());
        }
    }

    @Override
    public void enviarNotificacionBienvenida(String destinatario, String nombre) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject("¡Bienvenido/a a nuestro Sistema de Citas Médicas!");

            // Crear el contexto
            Context context = new Context();
            context.setVariable("nombre", nombre);

            // Procesar la plantilla
            String htmlContent = templateEngine.process("email-bienvenida", context);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo de bienvenida HTML: " + e.getMessage());
        }
    }
}
