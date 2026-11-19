package com.edwincamilo.citasmedicas.repository;

import com.edwincamilo.citasmedicas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    //Buscar paciente por documento de identidad
    Optional<Paciente> findByDocumentoIdentidadIgnoreCase(String documento);

    //Verificar si existe paciente por documento de identidad
    boolean existsByDocumentoIdentidadIgnoreCase(String documento);

    //Verificar si existe paciente por correo electrónico
    //ignorando mayúsculas y minúsculas
    boolean existsByCorreoElectronicoIgnoreCase(String correo);

    //Verificar si existe paciente por correo electrónico alternativo
    //ignorando mayúsculas y minúsculas
    boolean existsByCorreoElectronicoAlternativoIgnoreCase(String correoAlternativo);
}
