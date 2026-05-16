package mode;
 
import app.KrazyKitchen;
import utilities.Color;
 
// (O-03) NEW: shared supertype for Input and Output to unify their interfaces.
// Both modes previously had divergent method names (exit/quit) and signatures
// for what was conceptually the same lifecycle. Mode lifts the common contract
// (loop, returnToMenu, printError) to one place.
public abstract class Mode {
 
    // (O-03) CHANGED: the recursive "game loop" entry point is now a uniform
    // contract every Mode must implement, no more loop() vs loop(String err).
    public abstract void loop();
 
    // (O-03) CHANGED: replaces Input.exit() and Output.quit().
    // Both subclasses used to instantiate KrazyKitchen() directly under
    // different method names, now centralized here.
    public void returnToMenu() {
        new KrazyKitchen();
    }
 
    // (O-03) NEW: small helper so subclasses share the same error-display
    // formatting instead of reimplementing it inline.
    protected void printError(String err) {
        if (!err.isEmpty()) {
            System.out.println(Color.id(1) + "Error: " + err);
        }
    }
 
}
 