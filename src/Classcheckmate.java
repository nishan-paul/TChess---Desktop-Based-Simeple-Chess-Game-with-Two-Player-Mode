import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
public class Classcheckmate {
    Classboard b;
    LinkedList<Classpiece> wPieces, bPieces;
    LinkedList<Classsquare> movableSquares, squares;
    Classking bk, wk;
    HashMap<Classsquare,List<Classpiece>> wMoves, bMoves;
    Classcheckmate(Classboard b, LinkedList<Classpiece> wPieces, LinkedList<Classpiece> bPieces, Classking wk, Classking bk) {
        this.b = b;
        this.wPieces = wPieces;
        this.bPieces = bPieces;
        this.bk = bk;
        this.wk = wk;
        squares = new LinkedList<>();
        movableSquares = new LinkedList<>();
        wMoves = new HashMap<>();
        bMoves = new HashMap<>();
        Classsquare[][] brd = b.getSquareArray();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares.add(brd[y][x]);
                wMoves.put(brd[y][x], new LinkedList<>());
                bMoves.put(brd[y][x], new LinkedList<>());
            }
        }
        update();
    }
    void update() {
        Iterator<Classpiece> wIter = wPieces.iterator();
        Iterator<Classpiece> bIter = bPieces.iterator();
        wMoves.values().forEach((pieces) -> {
            pieces.removeAll(pieces);
        });
        bMoves.values().forEach((pieces) -> {
            pieces.removeAll(pieces);
        });
        movableSquares.removeAll(movableSquares);
        while (wIter.hasNext()) {
            Classpiece p = wIter.next();
            if (!p.getClass().equals(Classking.class)) {
                if (p.getPosition() == null) {
                    wIter.remove();
                    continue;
                }
                List<Classsquare> mvs = p.getLegalMoves(b);
                Iterator<Classsquare> iter = mvs.iterator();
                while (iter.hasNext()) {
                    List<Classpiece> pieces = wMoves.get(iter.next());
                    pieces.add(p);
                }
            }
        }
        while (bIter.hasNext()) {
            Classpiece p = bIter.next();
            if (!p.getClass().equals(Classking.class)) {
                if (p.getPosition() == null) {
                    wIter.remove();
                    continue;
                }
                List<Classsquare> mvs = p.getLegalMoves(b);
                Iterator<Classsquare> iter = mvs.iterator();
                while (iter.hasNext()) {
                    List<Classpiece> pieces = bMoves.get(iter.next());
                    pieces.add(p);
                }
            }
        }
    }
    boolean blackInCheck() {
        update();
        Classsquare sq = bk.getPosition();
        if (wMoves.get(sq).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        } else return true;
    }
    boolean whiteInCheck() {
        update();
        Classsquare sq = wk.getPosition();
        if (bMoves.get(sq).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        } else return true;
    }
    boolean blackCheckMated() {
        boolean checkmate = true;
        if (!this.blackInCheck()) return false;
        if (canEvade(wMoves, bk)) checkmate = false;
        List<Classpiece> threats = wMoves.get(bk.getPosition());
        if (canCapture(bMoves, threats, bk)) checkmate = false;
        if (canBlock(threats, bMoves, bk)) checkmate = false;
        return checkmate;
    }
    boolean whiteCheckMated() {
        boolean checkmate = true;
        if (!this.whiteInCheck()) return false;
        if (canEvade(bMoves, wk)) checkmate = false;
        List<Classpiece> threats = bMoves.get(wk.getPosition());
        if (canCapture(wMoves, threats, wk)) checkmate = false;
        if (canBlock(threats, wMoves, wk)) checkmate = false;
        return checkmate;
    }
    boolean canEvade(Map<Classsquare,List<Classpiece>> tMoves, Classking tKing) {
        boolean evade = false;
        List<Classsquare> kingsMoves = tKing.getLegalMoves(b);
        Iterator<Classsquare> iterator = kingsMoves.iterator();
        while (iterator.hasNext()) {
            Classsquare sq = iterator.next();
            if (!testMove(tKing, sq)) continue;
            if (tMoves.get(sq).isEmpty()) {
                movableSquares.add(sq);
                evade = true;
            }
        }
        return evade;
    }
    boolean canCapture(Map<Classsquare,List<Classpiece>> poss, List<Classpiece> threats, Classking k) {
        boolean capture = false;
        if (threats.size() == 1) {
            Classsquare sq = threats.get(0).getPosition();
            if (k.getLegalMoves(b).contains(sq)) {
                movableSquares.add(sq);
                if (testMove(k, sq)) {
                    capture = true;
                }
            }
            List<Classpiece> caps = poss.get(sq);
            ConcurrentLinkedDeque<Classpiece> capturers = new ConcurrentLinkedDeque<>();
            capturers.addAll(caps);
            if (!capturers.isEmpty()) {
                movableSquares.add(sq);
                for (Classpiece p : capturers) {
                    if (testMove(p, sq)) {
                        capture = true;
                    }
                }
            }
        }
        return capture;
    }
    boolean canBlock(List<Classpiece> threats, 
            Map <Classsquare,List<Classpiece>> blockMoves, Classking k) {
        boolean blockable = false;
        
        if (threats.size() == 1) {
            Classsquare ts = threats.get(0).getPosition();
            Classsquare ks = k.getPosition();
            Classsquare[][] brdArray = b.getSquareArray();
            
            if (ks.getXNum() == ts.getXNum()) {
                int max = Math.max(ks.getYNum(), ts.getYNum());
                int min = Math.min(ks.getYNum(), ts.getYNum());
                
                for (int i = min + 1; i < max; i++) {
                    List<Classpiece> blks = blockMoves.get(brdArray[i][ks.getXNum()]);
                    ConcurrentLinkedDeque<Classpiece> blockers = new ConcurrentLinkedDeque<>();
                    blockers.addAll(blks);
                    if (!blockers.isEmpty()) {
                        movableSquares.add(brdArray[i][ks.getXNum()]);
                        for (Classpiece p : blockers) {
                            if (testMove(p,brdArray[i][ks.getXNum()])) {
                                blockable = true;
                            }
                        }
                    }
                }
            }
            if (ks.getYNum() == ts.getYNum()) {
                int max = Math.max(ks.getXNum(), ts.getXNum());
                int min = Math.min(ks.getXNum(), ts.getXNum());
                for (int i=min+1; i<max; i++) {
                    List<Classpiece> blks = blockMoves.get(brdArray[ks.getYNum()][i]);
                    ConcurrentLinkedDeque<Classpiece> blockers = new ConcurrentLinkedDeque<>();
                    blockers.addAll(blks);
                    if (!blockers.isEmpty()) {
                        movableSquares.add(brdArray[ks.getYNum()][i]);
                        for (Classpiece p : blockers) {
                            if (testMove(p, brdArray[ks.getYNum()][i])) {
                                blockable = true;
                            }
                        }
                    }
                }
            }
            Class<? extends Classpiece> tC = threats.get(0).getClass();
            if (tC.equals(Classqueen.class) || tC.equals(Classbishop.class)) {
                int kX = ks.getXNum();
                int kY = ks.getYNum();
                int tX = ts.getXNum();
                int tY = ts.getYNum();
                if (kX > tX && kY > tY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY++;
                        List<Classpiece> blks = blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Classpiece> blockers = new ConcurrentLinkedDeque<>();
                        blockers.addAll(blks);
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            for (Classpiece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
                if ( kX>tX && tY>kY) {
                    for (int i=tX+1; i<kX; i++) {
                        tY--;
                        List<Classpiece> blks = blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Classpiece> blockers = new ConcurrentLinkedDeque<>();
                        blockers.addAll(blks);
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            for (Classpiece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
                if ( tX>kX && kY>tY ) {
                    for (int i=tX-1; i>kX; i--) {
                        tY++;
                        List<Classpiece> blks = blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Classpiece> blockers = new ConcurrentLinkedDeque<>();
                        blockers.addAll(blks);
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            for (Classpiece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
                if ( tX>kX && tY>kY ) {
                    for (int i=tX-1; i>kX; i--) {
                        tY--;
                        List<Classpiece> blks = blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Classpiece> blockers = new ConcurrentLinkedDeque<>();
                        blockers.addAll(blks);
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            for (Classpiece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blockable;
    }
    List<Classsquare> getAllowableSquares(boolean b) {
        movableSquares.removeAll(movableSquares);
        if (whiteInCheck()) {
            whiteCheckMated();
        }
        else if (blackInCheck()) {
            blackCheckMated();
        }
        return movableSquares;
    }
    boolean testMove(Classpiece p, Classsquare sq) {
        Classpiece c = sq.getOccupyingPiece();
        boolean movetest = true;
        Classsquare init = p.getPosition();
        p.move(sq);
        update();
        if (p.getColor() == 0 && blackInCheck())
            movetest = false;
        else if (p.getColor() == 1 && whiteInCheck())
            movetest = false;
        p.move(init);
        if (c != null)
            sq.put(c);
        update();
        movableSquares.addAll(squares);
        return movetest;
    }
}