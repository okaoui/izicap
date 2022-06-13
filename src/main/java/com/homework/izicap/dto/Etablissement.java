package com.homework.izicap.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Etablissement {

    private Integer id;
    private String nic;
    @JsonProperty("libelle_voie")
    private String libelleVoie;
    @JsonProperty("code_postal")
    private String codePostal;
    @JsonProperty("libelle_commune")
    private String libelleCommune;
    @JsonProperty("code_commune")
    private String codeCommune;
    @JsonProperty("date_creation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date creationDate;
    @JsonProperty("unite_legale")
    private UniteLegale uniteLegale;
    @JsonProperty("numero_tva_intra")
    private String numeroTva;
}
