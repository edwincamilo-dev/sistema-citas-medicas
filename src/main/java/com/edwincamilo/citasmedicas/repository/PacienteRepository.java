package com.edwincamilo.citasmedicas.repository;

import com.edwincamilo.citasmedicas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByDocumentoIdentidad(String documento);
    boolean existsByDocumentoIdentidad(String documento);
    boolean existsByCorreoElectronicoIgnoreCase(String correo);
    boolean existsByCorreoElectronicoAlternativoIgnoreCase(String correoAlternativo);
}
