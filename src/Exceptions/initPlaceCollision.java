package Exceptions;

/**
 * Intended to specifically address when the board can no longer place a new block.
 * This should lead to a game over.
 */
public class initPlaceCollision extends Exception {
    public initPlaceCollision(){
        super();
    }
}
