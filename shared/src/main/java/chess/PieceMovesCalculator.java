package chess;

import java.util.HashSet;

public interface PieceMovesCalculator {
    HashSet <ChessMove> pieceMoves(ChessBoard board, ChessPosition position);
}
