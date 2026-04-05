package dev.arpit.ticTacToe.models;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Move extends BaseModel {
  private Player player;
  private Cell cell;
}
