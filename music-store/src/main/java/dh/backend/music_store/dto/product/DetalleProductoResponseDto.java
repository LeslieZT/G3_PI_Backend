package dh.backend.music_store.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleProductoResponseDto {
    private Integer id;
    private String categoria;
    private String nombre;
    private String imagen_principal;
    private String descripcion;
    private Double precio;
    private String marca;
    private String modelo;
    private String condicion;
    private String origen;
    private String anio_lanzamiento;
    private String medidas;
    private String material;
    private String uso_recomendado;
}
