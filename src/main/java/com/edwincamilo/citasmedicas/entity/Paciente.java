package com.edwincamilo.citasmedicas.entity;

import com.edwincamilo.citasmedicas.entity.enums.TipoDocumentoPaciente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumentoPaciente tipoDocumento;

    @Column(name = "documento_identidad", nullable = false, length = 20, unique = true)
    private String documentoIdentidad;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "telefono_alternativo", nullable = true, length = 20)
    private String telefonoAlternativo;

    @Column(name = "correo_electronico", nullable = false, length = 100, unique = true)
    private String correoElectronico;

    @Column(name = "correo_electronico_alternativo", nullable = true, length = 100, unique = true)
    private String correoElectronicoAlternativo;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
