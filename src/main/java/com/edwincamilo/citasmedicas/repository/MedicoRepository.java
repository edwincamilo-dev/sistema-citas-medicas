package com.edwincamilo.citasmedicas.repository;

import com.edwincamilo.citasmedicas.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    //listar medicos buscando por especialidad con su ID
    List<Medico> findByEspecialidadId(Long idEspecialidad);

    //Buscar médico por documento de identidad ignorando mayúsculas y minúsculas
    Optional<Medico> findByDocumentoIdentidadIgnoreCase(String documento);

    //Verificar si ya existe un médico registrado con el documento exacto
    boolean existsByDocumentoIdentidad(String documento);

    //Verificar si existe un médico registrado con la tarjeta profesional
    //ignorando mayúsculas y minúsculas
    boolean existsByTarjetaProfesionalIgnoreCase(String tarjetaProfesional);

    //Verificar si existe un médico registrado con el correo electrónico
    //ignorando mayúsculas y minúsculas
    boolean existsByCorreoElectronicoIgnoreCase(String correo);

    //Verificar si existe un médico registrado con el correo electrónico alternativo
    //ignorando mayúsculas y minúsculas
    boolean existsByCorreoElectronicoAlternativoIgnoreCase(String correoAlternativo);
}
