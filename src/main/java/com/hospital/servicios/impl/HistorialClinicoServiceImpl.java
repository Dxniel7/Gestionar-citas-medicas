package com.hospital.servicios.impl;

import com.hospital.dominio.entidades.HistorialClinico;
import com.hospital.dominio.repositorios.HistorialClinicoRepository;
import com.hospital.servicios.HistorialClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialClinicoServiceImpl implements HistorialClinicoService {

    @Autowired
    private HistorialClinicoRepository historialClinicoRepository;

    @Override
    public List<HistorialClinico> obtenerHistorialesPorPaciente(Long idPaciente) {
        return historialClinicoRepository.findByPaciente_IdPaciente(idPaciente);
    }

    @Override
    public List<HistorialClinico> obtenerTodosLosHistoriales() {
        return historialClinicoRepository.findAllByOrderByIdHistorialAsc();
    }

    @Override
    public Optional<HistorialClinico> obtenerHistorialPorId(Long idHistorial) {
        return historialClinicoRepository.findByIdHistorial(idHistorial);
    }

    @Override
    public HistorialClinico crearHistorialClinico(HistorialClinico historialClinico) {
        return historialClinicoRepository.save(historialClinico);
    }

    @Override
    public HistorialClinico actualizarHistorialClinico(HistorialClinico historialClinico) {
        // Verificar si el historial clínico existe antes de actualizarlo
        if (historialClinico.getIdHistorial() != null && historialClinicoRepository.existsById(historialClinico.getIdHistorial())) {
            return historialClinicoRepository.save(historialClinico);
        } else {
            throw new RuntimeException("Historial clínico no encontrado");
        }
    }

    @Override
    public void eliminarHistorialClinico(Long idHistorial) {
        historialClinicoRepository.deleteById(idHistorial);
    }
}
