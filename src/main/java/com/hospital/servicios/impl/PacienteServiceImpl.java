package com.hospital.servicios.impl;

import com.hospital.dominio.entidades.Paciente;
import com.hospital.dominio.repositorios.PacienteRepository;
import com.hospital.servicios.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Inyectamos el codificador de contraseñas
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Paciente> readAll() {
        return pacienteRepository.findAllByOrderByIdPacienteAsc();
    }

    @Override
    public Paciente read(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public Paciente save(Paciente paciente) {
        // 1. Verificamos si el paciente tiene un usuario asociado con contraseña
        if (paciente.getUsuario() != null && paciente.getUsuario().getContrasena() != null && !paciente.getUsuario().getContrasena().isEmpty()) {
            // 2. Encriptamos la contraseña directamente en el objeto anidado
            String contrasenaEncriptada = passwordEncoder.encode(paciente.getUsuario().getContrasena());
            paciente.getUsuario().setContrasena(contrasenaEncriptada);
        }

        // 3. Guardamos el Paciente. La cascada se encargará de guardar el Usuario con la contraseña ya encriptada.
        return pacienteRepository.save(paciente);
    }

    @Override
    public void delete(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public Paciente findByCurp(String curp) {
        return pacienteRepository.findByCurp(curp).orElse(null);
    }

    @Override
    public Paciente obtenerPacientePorId(Long idPaciente) {
        return pacienteRepository.findById(idPaciente).orElse(null);
    }
}