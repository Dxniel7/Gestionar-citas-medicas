package com.hospital.dominio.entidades;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Doctor", schema = "public")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDoctor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator( // <-- AÑADIDO: Define un generador de secuencia propio para Doctor.
        name = "doctor_id_seq",
        sequenceName = "doctor_iddoctor_seq",
        allocationSize = 1
    )
    @GeneratedValue( // <-- AÑADIDO: Le dice a JPA que autogenere el ID usando la secuencia de arriba.
        strategy = GenerationType.SEQUENCE,
        generator = "doctor_id_seq"
    )
    @Column(name = "idDoctor")
    private Long idDoctor;

    @NotBlank(message = "El número de cédula es obligatorio")
    @Pattern(regexp = "^\\d{7,10}$", message = "El número de cédula debe contener entre 7 y 10 dígitos")
    @Column(name = "no_cedula", nullable = false, length = 50, unique = true)
    private String noCedula;

    @NotBlank(message = "La especialidad es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEspecialidad", referencedColumnName = "idEspecialidad", nullable = false)
    private Especialidad especialidad;

    @NotBlank(message = "El usuario es obligatorio")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // <-- MODIFICADO: Se mantiene la relación uno a uno.
    // @MapsId // <-- ELIMINADO: Esta era la línea que forzaba a los IDs a ser iguales.
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario", unique = true) // <-- MODIFICADO: La relación ahora se basa en una nueva columna "usuario_id", que debe ser única.
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CitaConsulta> citas;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "idHistorial", referencedColumnName = "idHistorial")
    // private HistorialClinico historialClinico;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialClinico> historiales;


    // Tu método JsonGetter se mantiene igual.
    public Especialidad getEspecialidad() {
        return especialidad;
    }
}
