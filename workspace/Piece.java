
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;
    
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
        
        try {
            if (this.img == null) {
              this.img = ImageIO.read(getClass().getResource(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    

    
    public boolean getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
      ArrayList<Square> controlled = new ArrayList<>();
      int row = start.getRow();
      int col = start.getCol();
      boolean isWhite = this.getColor();
      String imgName = img.toString();

      if (imgName.contains("bishop")) {
          int[][] directions = { {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };
          for (int[] dir : directions) {
              int r = row + dir[0], c = col + dir[1];
              while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                  controlled.add(board[r][c]);
                  if (board[r][c].isOccupied()) break;
                  r += dir[0];
                  c += dir[1];
              }
          }
      }
  
      if (imgName.contains("rook")) {
          int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
          for (int[] dir : directions) {
              int r = row + dir[0], c = col + dir[1];
              while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                  controlled.add(board[r][c]);
                  if (board[r][c].isOccupied()) break;
                  r += dir[0];
                  c += dir[1];
              }
          }
      }
  
      if (imgName.contains("queen")) {
          int[][] directions = {
              {-1, -1}, {-1, 0}, {-1, 1},
              {0, -1},          {0, 1},
              {1, -1},  {1, 0}, {1, 1}
          };
          for (int[] dir : directions) {
              int r = row + dir[0], c = col + dir[1];
              while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                  controlled.add(board[r][c]);
                  if (board[r][c].isOccupied()) break;
                  r += dir[0];
                  c += dir[1];
              }
          }
      }
  
      if (imgName.contains("knight")) {
          int[][] moves = {
              {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
              {1, -2},  {1, 2},  {2, -1},  {2, 1}
          };
          for (int[] move : moves) {
              int r = row + move[0];
              int c = col + move[1];
              if (r >= 0 && r < 8 && c >= 0 && c < 8) {
                  controlled.add(board[r][c]);
              }
          }
      }
  
      if (imgName.contains("king")) {
          int[][] kingMoves = {
              {-1, -1}, {-1, 0}, {-1, 1},
              {0, -1},          {0, 1},
              {1, -1},  {1, 0}, {1, 1}
          };
          for (int[] move : kingMoves) {
              int r = row + move[0];
              int c = col + move[1];
              if (r >= 0 && r < 8 && c >= 0 && c < 8) {
                  controlled.add(board[r][c]);
              }
          }
      }
  
      return controlled;
  }
  
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
      ArrayList<Square> legalMoves = new ArrayList<>();
  
      Square[][] board = b.getSquareArray();
      int row = start.getRow();
      int col = start.getCol();
  
      boolean isWhite = this.getColor();
      if (img == null) return legalMoves;

String imgName = img.toString();

if (imgName.contains("bishop")) {
    // ↖ Diagonal up-left
    for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
        Square sq = board[r][c];
        if (!sq.isOccupied()) {
            legalMoves.add(sq);
        } else {
            if (sq.getOccupyingPiece().getColor() != isWhite) {
                legalMoves.add(sq); 
            }
            break;
        }
    }

    // ↗ Diagonal up-right
    for (int r = row - 1, c = col + 1; r >= 0 && c < 8; r--, c++) {
        Square sq = board[r][c];
        if (!sq.isOccupied()) {
            legalMoves.add(sq);
        } else {
            if (sq.getOccupyingPiece().getColor() != isWhite) {
                legalMoves.add(sq); 
            }
            break;
        }
    }

    // ↙ Diagonal down-left
    for (int r = row + 1, c = col - 1; r < 8 && c >= 0; r++, c--) {
        Square sq = board[r][c];
        if (!sq.isOccupied()) {
            legalMoves.add(sq);
        } else {
            if (sq.getOccupyingPiece().getColor() != isWhite) {
                legalMoves.add(sq); 
            }
            break;
        }
    }

    // ↘ Diagonal down-right
    for (int r = row + 1, c = col + 1; r < 8 && c < 8; r++, c++) {
        Square sq = board[r][c];
        if (!sq.isOccupied()) {
            legalMoves.add(sq);
        } else {
            if (sq.getOccupyingPiece().getColor() != isWhite) {
                legalMoves.add(sq); 
            }
            break;
        }
    }

    return legalMoves;
}

  
      // Move Up
      for (int r = row - 1; r >= 0; r--) {
          Square sq = board[r][col];
          if (!sq.isOccupied()) {
              legalMoves.add(sq);
          } else {
              if (sq.getOccupyingPiece().getColor() != isWhite) {
                  legalMoves.add(sq); 
              }
              break;
          }
      }
  
      // Move Down
      for (int r = row + 1; r < 8; r++) {
          Square sq = board[r][col];
          if (!sq.isOccupied()) {
              legalMoves.add(sq);
          } else {
              if (sq.getOccupyingPiece().getColor() != isWhite) {
                  legalMoves.add(sq);
              }
              break;
          }
      }
  
      // Move Left
      for (int c = col - 1; c >= 0; c--) {
          Square sq = board[row][c];
          if (!sq.isOccupied()) {
              legalMoves.add(sq);
          } else {
              if (sq.getOccupyingPiece().getColor() != isWhite) {
                  legalMoves.add(sq);
              }
              break;
          }
      }
  
      // Move Right
      for (int c = col + 1; c < 8; c++) {
          Square sq = board[row][c];
          if (!sq.isOccupied()) {
              legalMoves.add(sq);
          } else {
              if (sq.getOccupyingPiece().getColor() != isWhite) {
                  legalMoves.add(sq);
              }
              break;
          }
      }
        // Knight (horse)
if (imgName.contains("knight")) {
  int[][] moves = {
      {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
      {1, -2}, {1, 2}, {2, -1}, {2, 1}
  };

  for (int[] move : moves) {
      int newRow = row + move[0];
      int newCol = col + move[1];

      if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
          Square sq = board[newRow][newCol];

          if (!sq.isOccupied()) {
              legalMoves.add(sq);
          } else if (sq.getOccupyingPiece().getColor() != isWhite) {
              legalMoves.add(sq); 
          }
      }
  }
}
          // Queen
if (imgName.contains("queen")) {
  int[][] directions = {
      {-1, -1}, {-1, 1}, {1, -1}, {1, 1},
      {-1, 0}, {1, 0}, {0, -1}, {0, 1}
  };
  
  for (int[] dir : directions) {
      int r = row + dir[0];
      int c = col + dir[1];
      
      while (r >= 0 && r < 8 && c >= 0 && c < 8) {
          Square sq = board[r][c];
          
          if (!sq.isOccupied()) {
              legalMoves.add(sq);
          } else {
              if (sq.getOccupyingPiece().getColor() != isWhite) {
                  legalMoves.add(sq); 
              }
              break; 
          }

          r += dir[0];
          c += dir[1];
      }
  }
}
          // King 
if (imgName.contains("king")) {
  int[][] kingMoves = {
      {-1, -1}, {-1, 0}, {-1, 1},
      {0, -1},          {0, 1},
      {1, -1},  {1, 0}, {1, 1}
  };

  for (int[] move : kingMoves) {
      int r = row + move[0];
      int c = col + move[1];

      if (r >= 0 && r < 8 && c >= 0 && c < 8) {
          Square sq = board[r][c];
          if (!sq.isOccupied()) {
              legalMoves.add(sq);
          } else if (sq.getOccupyingPiece().getColor() != isWhite) {
              legalMoves.add(sq); 
          }
      }
  }
}
if (imgName.contains("pawn")) {
  int direction = isWhite ? -1 : 1; 
  int startRow = isWhite ? 6 : 1;

  
  if (row + direction >= 0 && row + direction < 8 && !board[row + direction][col].isOccupied()) {
      legalMoves.add(board[row + direction][col]);

      
      if (row == startRow && !board[row + 2 * direction][col].isOccupied()) {
          legalMoves.add(board[row + 2 * direction][col]);
      }
  }

 
  for (int dc = -1; dc <= 1; dc += 2) {
      int newCol = col + dc;
      int newRow = row + direction;
      if (newCol >= 0 && newCol < 8 && newRow >= 0 && newRow < 8) {
          Square target = board[newRow][newCol];
          if (target.isOccupied() && target.getOccupyingPiece().getColor() != isWhite) {
              legalMoves.add(target);
          }
      }
  }
}

  return legalMoves;  
  }

}
  
   

      
  
    	
   