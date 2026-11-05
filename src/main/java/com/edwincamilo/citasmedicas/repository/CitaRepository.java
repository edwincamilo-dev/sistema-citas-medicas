package com.edwincamilo.citasmedicas.repository;

import com.edwincamilo.citasmedicas.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    boolean existsByMedicoIdAndFechaCita(Long idMedico, LocalDateTime fechaCita);
    boolean existsByPacienteIdAndFechaCita(Long idPaciente, LocalDateTime fechaCita);
    List<Cita> findAllByPacienteId(Long idPaciente);
    List<Cita> findAllByMedicoId(Long idMedico);
}
