package Logic.Tetraminoes;

import java.io.Serializable;

/**
 * The Concept of Bags. Any tetramino holding and producing structure is possible.
 * This interface allows for future implementation of different Tetramino Spawn Curve Bags.
 */
public interface Bags extends Serializable {

    /**
     * Helper function to be called when a piece is needed from the bag.
     * @return A new tetramino to be used.
     */
    public Tetramino retrieveFromBag();

}
