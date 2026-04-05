package dev.arpit.ticTacToe.models;

import dev.arpit.ticTacToe.models.constants.CellState;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cell extends BaseModel {
    private int row;
    private int col;
    private Player player;
    private CellState cellState;
}
