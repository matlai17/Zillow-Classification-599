
package Resources;

/**
 *
 * @author Matthew Lai and Arun Singh
 */
public class HouseCategoryMismatchException extends Exception {
    
    public HouseCategoryMismatchException () {
        super("There was an exception in creating the house price-bound categories.");
    }
    
    public HouseCategoryMismatchException (String message) {
        super(message);
    }
    
}
