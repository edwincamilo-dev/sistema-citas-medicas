package com.edwincamilo.citasmedicas.entity.enums;

// Tipos de documento permitidos para un médico. Debe coincidir con el
// ENUM de la columna medico.tipo_documento.
// Se separa de TipoDocumentoPaciente para que un médico nunca pueda tener TI
// (un menor de edad no tiene tarjeta profesional).
// Costo aceptado: CC, PAS y PPT se repiten en ambos enums; un tipo nuevo
// común debe agregarse en los dos.
public enum TipoDocumentoMedico {
    CC,
    PAS,
    PPT
}
