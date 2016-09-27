package ControlLayer;

import ModelLayer.CollisionManagement.CollisionManager;
import ModelLayer.Enumerations.GameMode;
import ModelLayer.MapML.MapModel;
import ModelLayer.Player;
import ViewLayer.MapVL.MapView;

import java.util.ArrayList;

/**
 * Represents one game session, containing references to all the participant objects and related current game settings.
 */
public class CurrentGameSession
{
    private static GameMode _GameMode = GameMode.NONE; //The selected game mode in the Main Menu (Single player, side-by-side etc.)
    private static ArrayList<Player> _CurrentPlayers; //Contains the instantiated player objects (1 or 2 instances)
    private static String _SelectedMapName; //The string name of the selected map, based on SharedResources.MSP_Maps (Easy, Medium)
    private static CollisionManager _CollisionManager; //Instance of the CollisionManager that calculates the collisions
    private static MapView _MapView; //The view layer object of the selected map (this draws on screen)
    private static MapModel _MapModel; //The model layer object of the selected map (this contains the MapObjects needed for collision detection)

    /**
     * Getter for the MapView
     *
     * @return The view layer object of the selected map. This is returned as the general parent MapModel.
     */
    public static MapView get_MapView() {
        return _MapView;
    }

    /**
     * Setter for the MapView. This is called by the GameEngine.
     * @param _MapView A child of the MapView parent, which represents the view layer of a map.
     */
    public static void set_MapView(MapView _MapView) {
        CurrentGameSession._MapView = _MapView;
    }

    /**
     * Getter for the MapModel.
     * @return A child of the MapModel parent, which represents the model layer of a map.
     */
    public static MapModel get_MapModel() {
        return _MapModel;
    }

    /**
     * Setter for the MapModel.
     * @param _MapModel A child of the MapModel parent, which represent the model layer of a map.
     */
    public static void set_MapModel(MapModel _MapModel) {
        CurrentGameSession._MapModel = _MapModel;
    }

    /**
     * Returns the currently set game mode (single player, side by side etc.)
     * @return Returns the currently set game mode as an enumeration.
     */
    public static GameMode get_GameMode() {
        return _GameMode;
    }

    /**
     * Sets the game mode and instantiates the Player object(s) accordingly.
     * @param _GameMode The game mode to be set for.
     */
    public static void set_GameMode(GameMode _GameMode) {
        CurrentGameSession._GameMode = _GameMode;
        switch (_GameMode) {
            case NONE:
                break;
            case SINGLEPLAYER:
                _CurrentPlayers = new ArrayList<>();
                _CurrentPlayers.add(new Player("Player 1"));
            case SIDEBYSIDE:
                _CurrentPlayers = new ArrayList<>();
                _CurrentPlayers.add(new Player("Player 1"));
                _CurrentPlayers.add(new Player("Player 2"));
                break;
            case MULTIPLAYER:
                break;
        }
    }

    /**
     * Getter for the CurrentPlayers
     * @return Returns a list of Player objects. This list could contain 1 or 2 players.
     */
    public static ArrayList<Player> get_CurrentPlayers() {
        return _CurrentPlayers;
    }

    /**
     * Getter for the SelectedMap
     * @return The string name of the selected map, based on SharedResources.MSP_Maps (Easy, Medium)
     */
    public static String get_SelectedMapName() {
        return _SelectedMapName;
    }

    /**
     * Setter for the Selected map.
     * @param _SelectedMapName Must be a string name of the selected map, based on SharedResources.MSP_Maps (Easy, Medium)
     */
    public static void set_SelectedMapName(String _SelectedMapName)
    {
        CurrentGameSession._SelectedMapName = _SelectedMapName;
    }

    /**
     * Returns the only CollisionManager object instance of the game, which is used to calculate collisions
     * @return Returns the only CollisionManager object instance of the game.
     */
    public static CollisionManager get_CollisionManager() {
        return _CollisionManager;
    }

    /**
     * Setter for the CollisionManager. Called by the GameEngine.
     * @param _CollisionManager The CollisionManager instance to use.
     */
    public static void set_CollisionManager(CollisionManager _CollisionManager) {
        CurrentGameSession._CollisionManager = _CollisionManager;
    }

    /**
     * Resets all the CurrentSession values to default.
     * The default is the same as when the application starts up.     *
     */
    public static void ResetGameSession()
    {
        _GameMode = GameMode.NONE;
        _SelectedMapName = "";
        _CurrentPlayers = null;
        _CollisionManager = null;
        _MapModel = null;
        _MapView = null;
    }
}
