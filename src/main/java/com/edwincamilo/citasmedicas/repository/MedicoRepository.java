package com.edwincamilo.citasmedicas.repository;

import com.edwincamilo.citasmedicas.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecialidadId(Long idEspecialidad);
    Optional<Medico> findByDocumentoIdentidadIgnoreCase(String documento);
    boolean existsByDocumentoIdentidad(String documento);
    boolean existsByTarjetaProfesionalIgnoreCase(String tarjetaProfesional);
    boolean existsByCorreoElectronicoIgnoreCase(String correo);
    boolean existsByCorreoElectronicoAlternativoIgnoreCase(String correoAlternativo);
}
