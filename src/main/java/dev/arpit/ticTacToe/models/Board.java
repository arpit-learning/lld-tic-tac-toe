package dev.arpit.ticTacToe.models;

import dev.arpit.ticTacToe.models.constants.CellState;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class Board extends BaseModel {
    private int dimension;
    private List<List<Cell>> matrix;

    public Board(int dimension) {
      this.dimension = dimension;
      this.matrix = new ArrayList<>();
      for(int i = 0; i < dimension; i++) {
        List<Cell> row = new ArrayList<>();
        for(int j = 0; j < dimension; j++) {
          row.add(
              new Cell(i, j, null, CellState.EMPTY)
          );
        }
        this.matrix.add(row);
      }
    }
}
