package com.homework.izicap.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UniteLegale{
    private Integer id;
    @JsonProperty("prenom_usuel")
    private String prenomUsuel;
    private String nom;
    @JsonProperty("nom_usage")
    private String nomUsage;

}
