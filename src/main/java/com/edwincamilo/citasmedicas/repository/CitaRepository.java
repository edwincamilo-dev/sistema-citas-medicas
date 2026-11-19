package com.edwincamilo.citasmedicas.repository;

import com.edwincamilo.citasmedicas.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    //Verificar si existe cita con id de médico y fecha
    boolean existsByMedicoIdAndFechaCita(Long idMedico, LocalDateTime fechaCita);

    //Verificar si existe cita con id paciente y fecha asignada
    boolean existsByPacienteIdAndFechaCita(Long idPaciente, LocalDateTime fechaCita);

    //Listar Cita de paciente por id exacto
    List<Cita> findAllByPacienteId(Long idPaciente);

    //Listar Cita de médico por id exacto
    List<Cita> findAllByMedicoId(Long idMedico);
}
