package ec.edu.espe.batcharias.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Estudiante {
  private String codigo;

  private String codigoInterno;

  private String cedula;

  private String apellidos;

  private String nombres;

  private Integer nivel;
}
