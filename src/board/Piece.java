package board;

import java.util.ArrayList;

public class Piece {

  private String Type;
  private boolean Location; //False Up True Down
  private boolean Color; // 0 white 1 black
  private boolean first_move;
  private int X;
  private int Y;

  public Piece(String Type, boolean Location, int X, int Y) {
    this.Type = Type;
    this.Location = Location;
    this.X = X;
    this.Y = Y;
    this.first_move = true;
  }

  public Piece(String Type, boolean Location, boolean Color, int X, int Y) {
    this.Type = Type;
    this.Location = Location;
    this.Color = Color;
    this.X = X;
    this.Y = Y;
    this.first_move = true;
  }

  public String get_type() {
    return Type;
  }

  public int get_x() {
    return X;
  }

  public int get_y() {
    return Y;
  }

  public boolean get_color() {
    return Color;
  }

  public boolean get_location() {
    return Location;
  }

  public void set_type(String Type) {
    this.Type = Type;
  }

  public void set_x(int X) {
    this.X = X;;
  }

  public void set_y(int Y) {
    this.Y = Y;
  }

  public void set_color(boolean Color) {
    this.Color = Color;
  }

  public void set_location(boolean Location) {
    this.Location = Location;
  }

  public Piece can_eat(int destX,int destY, ArrayList<Piece> pieces)
  {
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).get_x() == destX && pieces.get(i).get_y() == destY && pieces.get(i).get_color()!=this.Color) {
        return pieces.get(i);
      }
    }
    return null;
  }


  public boolean check_move(String move, ArrayList<Piece> pieces ) {

    if (move.length() == 4) {

      int x1 = CommonMethods.convert(move.charAt(0)); //initial position x
      int y1 = CommonMethods.convert(move.charAt(1)); //initial position y
      int x2 = CommonMethods.convert(move.charAt(2)); //next position x
      int y2 = CommonMethods.convert(move.charAt(3)); //next position y

      String side;
      if (this.Location) {
        side = "bottom";
      } else {
        side = "top";
      }

      System.out.println("Position: " + side);
      System.out.println("Initial: (" + x1 + "," + y1 + ")");
      System.out.println("Next: (" + x2 + "," + y2 + ")");

      if (x2 == x1 && y2 == y1) { //checks if not moved
        return false;
      }
      if (x2 < 0 || x2 > 7 || y2 > 7 || y2 < 0) { //checks if out of boundary
        return false;
      }
      switch (this.Type) { //checks if valid

        case "Pawn":
          if (!this.Location) {
            if ((y2 == y1 + 1) && x2 == x1) {
              return (!blocked(x2, y2, pieces));
            } else if (this.first_move == true) {
              if ((y2 == y1 + 2) && x2 == x1) {
                return (!blocked(x2, y2, pieces));
              }
            }
          } else if ((y2 == y1 - 1) && x2 == x1) {
            return (!blocked(x2, y2, pieces));

          } else if (this.first_move == true) {
            if ((y2 == y1 - 2) && x2 == x1) {
              return (!blocked(x2, y2, pieces));
            }
          }
          break;

        case "Knight":
          if ((y2 == y1 + 2 || y2 == y1 - 2) && (x2 == x1 + 1 || x2 == x1 - 1)) {
            return (!blocked(x2, y2, pieces));
          } else if ((y2 == y1 + 1 || y2 == y1 - 1) && (x2 == x1 + 2 || x2 == x1 - 2)) {
            return (!blocked(x2, y2, pieces));
          }
          break;

        case "Bishop":

          if (Math.abs(y2 - y1) == Math.abs(x2 - x1)) {
            return (!blocked(x2, y2, pieces));
          }
          break;

        case "Rook":
          if (y2 != y1 && x2 == x1) {
            return (!blocked(x2, y2, pieces));
          } else if (y2 == y1 && x2 != x1) {
            return (!blocked(x2, y2, pieces));
          }
          break;

        case "Queen":

          if (Math.abs(y2 - y1) == Math.abs(x2 - x1)) {
            return (!blocked(x2, y2, pieces));
          } else if (y2 != y1 && x2 == x1) {
            return (!blocked(x2, y2, pieces));
          } else if (y2 == y1 && x2 != x1) {
            return (!blocked(x2, y2, pieces));
          }
          break;

        case "King":
          if ((Math.abs(y2 - y1) == 1 || Math.abs(y2 - y1) == 0) && (Math.abs(x2 - x1) == 1 || Math.abs(x2 - x1) == 0)) {
            return (!blocked(x2, y2, pieces));
          }
          break;
      }
      return false;
    }
    return false;
  }

  private boolean pieceOnSquare(int srcX, int srcY, ArrayList<Piece> pieces) {
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).get_x() == srcX && pieces.get(i).get_y() == srcY) {
        return true;
      }
    }
    return false;
  }


  private boolean blocked(int x2, int y2, ArrayList<Piece> pieces) {
    int dX = x2 > this.X ? 1 : -1;
    int dY = y2 > this.Y ? 1 : -1;
    switch (this.Type) {
      case "Pawn":
        for (int i = 1; i < Math.abs(y2 - this.Y); i++) {
          if (pieceOnSquare(this.X, this.Y + i * dY, pieces)) {
            return true;
          }
        }
        break;
      case "Rook":
        if (x2 == this.X) {
          for (int i = 1; i < Math.abs(y2 - this.Y) - 1; i++) {
            if (pieceOnSquare(this.X, this.Y + i * dY, pieces)) {
              return true;
            }
          }
        } else if (y2 == this.Y) {
          for (int i = 1; i < Math.abs(x2 - this.X) - 1; i++) {
            if (pieceOnSquare(this.X + i * dX, this.Y, pieces)) {
              return true;
            }
          }
        }
        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i).get_x() == x2 && pieces.get(i).get_y() == y2) {
            if (pieces.get(i).get_color() == this.Color) {
              return true;
            }
          }
        }
        break;
      case "Bishop":
        for (int i = 1; i < Math.abs(x2 - this.X) - 1; ++i) {
          if (pieceOnSquare(this.X + i * dX, this.Y + i * dY, pieces)) {
            return true;
          }
        }
        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i).get_x() == x2 && pieces.get(i).get_y() == y2) {
            if (pieces.get(i).get_color() == this.Color) {
              return true;
            }
          }
        }
        break;
      case "Knight":
        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i).get_x() == x2 && pieces.get(i).get_y() == y2) {
            if (pieces.get(i).get_color() == this.Color) {
              return true;
            }
          }
        }
        break;
      case "Queen":
        if (x2 == this.X) {
          for (int i = 1; i < Math.abs(y2 - this.Y) - 1; i++) {

            if (pieceOnSquare(this.X, this.Y + i * dY, pieces)) {
              return true;
            }
          }
        } else if (y2 == this.Y) {
          for (int i = 1; i < Math.abs(x2 - this.X) - 1; i++) {
            if (pieceOnSquare(this.X + i * dX, this.Y, pieces)) {
              return true;
            }
          }
        } else {
          for (int i = 1; i < Math.abs(x2 - this.X) - 1; ++i) {
            if (pieceOnSquare(this.X + i * dX, this.Y + i * dY, pieces)) {
              return true;
            }
          }
        }
        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i).get_x() == x2 && pieces.get(i).get_y() == y2) {
            if (pieces.get(i).get_color() == this.Color) {
              return true;
            }
          }
        }
        break;
      case "King":
        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i).get_x() == x2 && pieces.get(i).get_y() == y2) {
            if (pieces.get(i).get_color() == this.Color) {
              return true;
            }
          }
        }
        break;
    }
    return false;
  }
}