package com.isj.gestiondenote.ClientWeb.Model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("userDto")
    private UserDto userDto;
    private boolean disponible;
    private String photo;
}
