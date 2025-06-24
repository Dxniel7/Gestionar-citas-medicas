package com.hospital.servicios;

import com.hospital.dominio.entidades.HistorialClinico;
import java.util.List;
import java.util.Optional;

public interface HistorialClinicoService {
    // Obtener todos los historiales clínicos de un paciente
    List<HistorialClinico> obtenerHistorialesPorPaciente(Long idPaciente);

    // Obtener todos los historiales clínicos
    List<HistorialClinico> obtenerTodosLosHistoriales();

    // Obtener historial clínico por ID
    Optional<HistorialClinico> obtenerHistorialPorId(Long idHistorial);

    // Crear un nuevo historial clínico
    HistorialClinico crearHistorialClinico(HistorialClinico historialClinico);

    // Actualizar un historial clínico existente
    HistorialClinico actualizarHistorialClinico(HistorialClinico historialClinico);

    // Eliminar un historial clínico
    void eliminarHistorialClinico(Long idHistorial);
}
