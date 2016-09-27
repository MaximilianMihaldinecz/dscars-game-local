package ControlLayer;

import ModelLayer.Enumerations.GameMode;
import ViewLayer.SoundEngine;
import ViewLayer.SwingUICore;

/**
 * Switches between the main flows of the application.
 * Acts as the "Maestro" in an orchestra, and there is only one single instance of it.
 */
public class Controller {

    private SwingUICore _UICore; //The top level Swing JFrame that loads other panels to display content.
    private GameEngine _GameEngine; //Fully controls one game scenario
    private SoundEngine _SoundEngine; //Responsible of playing music and sounds

    /**
     * Display the Swing based UI, starting with the main menu when the program launches
     */
    public void ApplicationStartUp()
    {
        //Launch the SWING based UI
        _UICore = new SwingUICore();
        _UICore.CreateDefaultWindow();

        //Display the main menu screen after start
        _UICore.NavigateToMainMenuScreen();

        //Launch the Sound Engine
        _SoundEngine = new SoundEngine();
    }

    /** Navigate to the car selection screen and initiate a side-by-side player game.
     * This method is called when user selects the side-by-side game on the main menu.
     */
    public void UserInitiatesSideBySideGame()
    {
        CurrentGameSession.set_GameMode(GameMode.SIDEBYSIDE);
        NavigateToCarSelectionWithDefaults();
    }

    /** Navigate to the car selection screen and initiate a single player game.
     * This method is called when user selects the single player game on the main menu.
     */
    public void UserInitiatesSinglePlayerGame()
    {
        CurrentGameSession.set_GameMode(GameMode.SINGLEPLAYER);
        NavigateToCarSelectionWithDefaults();
    }

    /**
     * Orders the top JFrame to load the LaunchScreen JPanel.
     */
    private void NavigateToCarSelectionWithDefaults() {
        _UICore.NavigateToLaunchScreen();
        _UICore.SelectDefaultValuesOnLaunchScreen();
    }

    /** Navigate to the main menu screen and resets the game session.
     * This can be called when the user either clicks on the "new game" option on the MenuBar
     * or when a game finishes and the player will be put back to the main menu.
     */
    public void NavigatingBackToMainMenuScreen() {
        //If this method was called why the game is running, then destroy the GameEngine controller.
        if(_GameEngine != null)
        {
            _GameEngine.ShutDown();
            _GameEngine = null;
        }

        //Reset the game session and direct the top JFrame to show the MainMenuScreen JPanel
        CurrentGameSession.ResetGameSession();
        _UICore.NavigateToMainMenuScreen();
    }

    /** Changes the selected car for a player in a game session.
     *  This method is called from the LaunchScreen JPanel.
     *
     *  @param PlayerNumber The number of the player to change the car for. Starts from 1.
     *  @param CarIndex The index number of the selected car. Starts from 0.
    */
    public void ChangeGameSessionCar(int PlayerNumber, int CarIndex)
    {
        CurrentGameSession.get_CurrentPlayers().get(PlayerNumber-1).get_Car().set_CarImageFileIndex(CarIndex);
    }

    /** Changes the selected map. This is called from the LaunchScreen JPanel.
     * @param MapNumber The index number of the selected map. Starts from 0.
     */
    public void ChangeGameSessionMap(int MapNumber)
    {
        CurrentGameSession.set_SelectedMapName(SharedResources.MSP_Maps[MapNumber]);
    }

    /** Instantiates the GameEngine, which takes over the control to launch a new game.
     * This is called from the LaunchScreen JPanel when user clicks on "start".
     */
    public void LaunchGame()
    {
        _GameEngine = new GameEngine(_UICore, _SoundEngine);
        _GameEngine.StartGame();
    }

    /** Directs the SoundEngine to turn on/off the background music.
     * This is called when user clicks on the relevant option on the MenuBar.
     * @param newState True to turn on the music. False for turning it off.
     */
    public void GameSettingsMenuBarMusicChanged(boolean newState)
    {
        SharedResources.DGO_Default_Music_On = newState;
        _SoundEngine.SetBackgroundMusic(newState);
    }

    /** Directs the SoundEngine to turn on/off the in-game sounds.
     * This is called when user clicks on the relevant option on the MenuBar.
     * @param newState True to turn on the sounds. False for turning them off.
     */
    public void GameSettingsMenuBarSoundChanged(boolean newState)
    {
        SharedResources.DGO_Default_Sound_On = newState;
    }

    /** Sets the in-game map texture on or off.
     * If the texture is off, then border lines will be drawn out instead.
     * @param newState True to keep the texture on. False for turning it off.
     */
    public void GameSettingsMenuBarMapTextureChanged(boolean newState)
    {
        SharedResources.DGO_Default_MapTexture_On = newState;
    }

    /**
     * Getter for the instantiated GameEngine.
     * @return Returns the GameEngine instance.
     */
    public GameEngine get_GameEngine() {
        return _GameEngine;
    }

    /**
     * Shuts down the application.
     * Called when user clicks on the "Exit" in the main menu.
     */
    public void UserInitiatesExit()
    {
        _SoundEngine.SetBackgroundMusic(false);
        _UICore.dispose();
    }
}
