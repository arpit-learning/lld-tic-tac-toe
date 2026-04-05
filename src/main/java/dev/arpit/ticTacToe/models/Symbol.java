package dev.arpit.ticTacToe.models;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Symbol extends BaseModel {
  private char value;
}
