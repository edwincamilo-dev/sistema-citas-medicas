-- =====================================================================
-- Sistema de Gestión de Citas Médicas
-- Script de creación de la base de datos (MySQL 8)
-- Autor: Edwin Pipicano
-- =====================================================================
--
-- Decisiones de diseño:
--
-- * Llaves sustitutas (id_* BIGINT AUTO_INCREMENT) en todas las tablas.
--   Los datos de negocio (documento, correo) pueden corregirse o cambiar;
--   el identificador interno nunca cambia.
--
-- * documento_identidad es UNIQUE por sí solo (sin combinarlo con el tipo):
--   con el NUIP, una persona conserva el mismo número al pasar de TI a CC.
--
-- * tipo_documento de MEDICO no incluye TI: un menor de edad no puede
--   tener tarjeta profesional.
--
-- * estado_cita como ENUM: es fácil de entender y de mantener. Los estados
--   de una cita casi nunca cambian, así que el costo de un ALTER TABLE
--   para agregar uno nuevo es aceptable. Una cita nueva queda por defecto
--   en 'PROGRAMADA'. Los valores deben coincidir letra por letra con el
--   enum EstadoCita de Java.
--
-- * Correos UNIQUE: el correo se usará para iniciar sesión, así que no
--   pueden existir dos cuentas con el mismo correo.
--
-- * Reglas de horario en CITA (UNIQUE compuestos):
--     uq_medico_cita   = (id_medico, fecha_cita)   -> un médico, una cita por hora
--     uq_paciente_cita = (id_paciente, fecha_cita) -> un paciente, una cita por hora
--
-- * fecha_creacion en todas las tablas para auditoría básica.
--
-- Deuda técnica conocida:
--
-- * El correo es único por tabla, no entre paciente y médico: un médico y
--   un paciente podrían tener el mismo correo. Se resolverá en la Fase 8
--   (Spring Security).
-- * Una cita CANCELADA sigue ocupando su horario en los UNIQUE compuestos.
-- * Nombres de restricciones UNIQUE con patrones mixtos (pendiente unificar).
--
-- Orden de creación (por dependencias de llaves foráneas):
--   especialidad -> medico, paciente -> cita
-- =====================================================================

CREATE DATABASE citas_medicas;
USE citas_medicas;

-- ---------------------------------------------------------------------
-- ESPECIALIDAD: catálogo administrado por la clínica
-- ---------------------------------------------------------------------
CREATE TABLE `especialidad` (
  `id_especialidad` bigint NOT NULL AUTO_INCREMENT,
  `nombre_especialidad` varchar(50) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_especialidad`),
  UNIQUE KEY `uq_especialidad_nombre` (`nombre_especialidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- MEDICO: cada médico pertenece a una y solo una especialidad (N:1)
-- ---------------------------------------------------------------------
CREATE TABLE `medico` (
  `id_medico` bigint NOT NULL AUTO_INCREMENT,
  `tipo_documento` enum('CC','PAS','PPT') NOT NULL,
  `documento_identidad` varchar(20) NOT NULL,
  `tarjeta_profesional` varchar(20) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `telefono_alternativo` varchar(20) DEFAULT NULL,
  `correo_electronico` varchar(100) NOT NULL,
  `correo_electronico_alternativo` varchar(100) DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_especialidad` bigint NOT NULL,
  PRIMARY KEY (`id_medico`),
  UNIQUE KEY `uq_documento_identidad` (`documento_identidad`),
  UNIQUE KEY `uq_tarjeta_profesional` (`tarjeta_profesional`),
  UNIQUE KEY `uq_correo_electronico` (`correo_electronico`),
  UNIQUE KEY `uq_correo_electronico_alternativo` (`correo_electronico_alternativo`),
  KEY `fk_medico_especialidad` (`id_especialidad`),
  CONSTRAINT `fk_medico_especialidad` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id_especialidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- PACIENTE
-- ---------------------------------------------------------------------
CREATE TABLE `paciente` (
  `id_paciente` bigint NOT NULL AUTO_INCREMENT,
  `tipo_documento` enum('CC','TI','PAS','PPT') NOT NULL,
  `documento_identidad` varchar(20) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `telefono_alternativo` varchar(20) DEFAULT NULL,
  `correo_electronico` varchar(100) NOT NULL,
  `correo_electronico_alternativo` varchar(100) DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_paciente`),
  UNIQUE KEY `uq_documento_identidad` (`documento_identidad`),
  UNIQUE KEY `uq_correo_electronico` (`correo_electronico`),
  UNIQUE KEY `uq_correo_electronico_alternativo` (`correo_electronico_alternativo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- CITA: depende de MEDICO y PACIENTE, por eso se crea de última
-- ---------------------------------------------------------------------
CREATE TABLE `cita` (
  `id_cita` bigint NOT NULL AUTO_INCREMENT,
  `id_medico` bigint NOT NULL,
  `id_paciente` bigint NOT NULL,
  `estado_cita` enum('PROGRAMADA','CONFIRMADA','CANCELADA','COMPLETADA') NOT NULL DEFAULT 'PROGRAMADA',
  `motivo_cita` varchar(500) NOT NULL,
  `fecha_cita` datetime NOT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_cita`),
  UNIQUE KEY `uq_medico_cita` (`id_medico`,`fecha_cita`),
  UNIQUE KEY `uq_paciente_cita` (`id_paciente`,`fecha_cita`),
  CONSTRAINT `fk_cita_medico` FOREIGN KEY (`id_medico`) REFERENCES `medico` (`id_medico`),
  CONSTRAINT `fk_cita_paciente` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id_paciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
