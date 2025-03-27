//Julian Moreno
//03-04-2025

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    private Piece currPiece;
    private Square fromMoveSquare;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    

    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
     
      //for (.....)  
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.
        boolean color = true;
        for(int row =0; row<board.length; row++)
        {
            for(int col=0; col<board[row].length; col++){
                board[row][col]= new Square(this, color, row, col);
                this.add(board[row][col]);
                color = !color;

                }
                color = !color;
            }
        

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    
	//set up the board such that the black pieces are on one side and the white pieces are on the other.
	//since we only have one kind of piece for now you need only set the same number of pieces on either side.
	//it's up to you how you wish to arrange your pieces.
    private void initializePieces() {
    	
    	board[0][0].put(new Piece(true, RESOURCES_WKING_PNG));
        board[0][0].put(new Piece(false, RESOURCES_BROOK_PNG));
        board[0][1].put(new Piece(false, RESOURCES_BKNIGHT_PNG));
        board[0][2].put(new Piece(false, RESOURCES_BBISHOP_PNG));
        board[0][3].put(new Piece(false, RESOURCES_BQUEEN_PNG));
        board[0][4].put(new Piece(false, RESOURCES_BKING_PNG));
        board[0][5].put(new Piece(false, RESOURCES_BBISHOP_PNG));
        board[0][6].put(new Piece(false, RESOURCES_BKNIGHT_PNG));
        board[0][7].put(new Piece(false, RESOURCES_BROOK_PNG));

        for (int col = 0; col < 8; col++) {
            board[1][col].put(new Piece(false, RESOURCES_BPAWN_PNG));
        }
        for (int col = 0; col < 8; col++) {
            board[6][col].put(new Piece(true, RESOURCES_WPAWN_PNG));
        }
        board[7][0].put(new Piece(true, RESOURCES_WROOK_PNG));
        board[7][1].put(new Piece(true, RESOURCES_WKNIGHT_PNG));
        board[7][2].put(new Piece(true, RESOURCES_WBISHOP_PNG));
        board[7][3].put(new Piece(true, RESOURCES_WQUEEN_PNG));
        board[7][4].put(new Piece(true, RESOURCES_WKING_PNG));
        board[7][5].put(new Piece(true, RESOURCES_WBISHOP_PNG));
        board[7][6].put(new Piece(true, RESOURCES_WKNIGHT_PNG));
        board[7][7].put(new Piece(true, RESOURCES_WROOK_PNG));
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
     
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Clicked at coordinates: " + e.getX() + ", " + e.getY());
        System.out.println("Click at coordinates: " + e.getX() + ", " + e.getY());

        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (currPiece == null) {
            if (endSquare.isOccupied()) {
                Piece selected = endSquare.getOccupyingPiece();
                if ((selected.getColor() && whiteTurn) || (!selected.getColor() && !whiteTurn)) {
                    currPiece = selected;
                    fromMoveSquare = endSquare;
                    endSquare.setDisplay(false);
                }
            }
            return;
        }
        
    if (currPiece != null && fromMoveSquare != null) {
        ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);

        if (legalMoves.contains(endSquare)) {
            endSquare.put(currPiece);
            fromMoveSquare.removePiece();

            // Change Shift
            whiteTurn = !whiteTurn;
        }
    }

    if (fromMoveSquare != null) {
        fromMoveSquare.setDisplay(true); 

    }
}
    

    //TO BE IMPLEMENTED!
    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        
        //using currPiece
        //if the move is legal move the piece to endSquare
        if(currPiece!= null){
            if(whiteTurn == currPiece.getColor() && currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare)){
                endSquare.put(currPiece);
                fromMoveSquare.removePiece();
                whiteTurn = !whiteTurn;
            }
        }
       
        fromMoveSquare.setDisplay(true);
        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        if(currPiece!= null){
            for(Square s: currPiece.getLegalMoves(this, fromMoveSquare)){
                s.setBorder(BorderFactory.createLineBorder(Color.red));
            }
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}