package ModelLayer.Enumerations;

/**
 * Represents the selected game mode by the user.
 */
public enum GameMode
{
    /**
     * Default value, nothing has been selected.
     */
    NONE,
    /**
     * Signle player game selected
     */
    SINGLEPLAYER,
    /**
     * Side-by-side game selected (two players at the sem computer)
     */
    SIDEBYSIDE,
    /**
     * Multiplayer over the network selected.
     */
    MULTIPLAYER
}
