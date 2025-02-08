package ru.studiotg.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;

@Data
@AllArgsConstructor
public class NewGameRequestDto {
    @Max(value = 30, message = "Width should be less then 30")
    Integer width;

    @Max(value = 30, message = "Height should be less then 30")
    Integer height;

    @JsonProperty(value = "mines_count")
    Integer minesCount;
}