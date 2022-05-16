package Logic.Tetraminoes;

import Logic.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This will contain spawnable pieces.
 * The tetris standard denotes we have a shuffled bag of the 7 tetraminoes.
 * This is the simplest type of Standard Compliant bag.
 */
public class BagofPieces implements Bags {
    private ArrayList<Tetramino> store = new ArrayList<>(7);

    /**
     * Helper function to be called when a piece is needed from the bag.
     * If the bag is not empty, returns the piece and removed it from the bag.
     * Refills the board's bag with a randomly ordered list of all tetraminoes when it is empty.
     * This is Tetris Specification Compliant.
     * @return A new tetramino to be used.
     */
    public Tetramino retrieveFromBag() {


        if(store.isEmpty()) {
            store = new ArrayList<>(List.of(new ITetramino(new Point()),
                                            new OTetramino(new Point()),
                                            new TTetramino(new Point()),
                                            new LTetramino(new Point()),
                                            new JTetramino(new Point()),
                                            new STetramino(new Point()),
                                            new ZTetramino(new Point())));

            Collections.shuffle(store);
        }

        //remove(0) removes the first element, and shrinks the list by 1.
        //This means that eventually isEmpty() will trigger.

        return store.remove(0);
    }
}
