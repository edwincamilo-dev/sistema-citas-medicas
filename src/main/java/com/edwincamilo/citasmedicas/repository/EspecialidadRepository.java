package com.edwincamilo.citasmedicas.repository;

import com.edwincamilo.citasmedicas.entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}
