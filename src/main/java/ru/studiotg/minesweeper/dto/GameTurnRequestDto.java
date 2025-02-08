package ru.studiotg.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record GameTurnRequestDto(
        @JsonProperty("game_id")
        UUID gameId,
        Integer col,
        Integer row
) {
}
