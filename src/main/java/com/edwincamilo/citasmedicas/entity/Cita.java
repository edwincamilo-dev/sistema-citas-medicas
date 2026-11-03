    package com.edwincamilo.citasmedicas.entity;

    import com.edwincamilo.citasmedicas.entity.enums.EstadoCita;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.CreationTimestamp;

    import java.time.LocalDateTime;

    @Entity
    @Table(name = "cita")
    @Getter
    @Setter
    @NoArgsConstructor
    public class Cita {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_cita")
        private Long id;

        // Se inicializa en Java para que una cita nueva nunca se guarde con estado NULL
        // (Hibernate enviaría NULL explícito y MySQL no aplicaría su DEFAULT).
        // Costo: el valor inicial vive aquí y en el DEFAULT de la tabla; si cambia,
        // hay que modificar ambos. El DEFAULT sigue sirviendo para INSERT manuales.
        @Enumerated(EnumType.STRING)
        @Column(name = "estado_cita", nullable = false)
        private EstadoCita estado = EstadoCita.PROGRAMADA;

        @Column(name = "motivo_cita", nullable = false, length = 500)
        private String motivo;

        @Column(name = "fecha_cita", nullable = false)
        private LocalDateTime fechaCita;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "id_medico", nullable = false)
        private Medico medico;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "id_paciente", nullable = false)
        private Paciente paciente;

        @CreationTimestamp
        @Column(name = "fecha_creacion", nullable = false, updatable = false)
        private LocalDateTime fechaCreacion;
    }
