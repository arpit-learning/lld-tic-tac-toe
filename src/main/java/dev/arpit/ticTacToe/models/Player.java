package dev.arpit.ticTacToe.models;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Player extends BaseModel {
  private String name;
  private Symbol symbol;
}
