package ControlLayer;

import ModelLayer.CollisionManagement.CollisionManager;
import ModelLayer.MapML.EasyMapML;
import ModelLayer.MapML.MediumMapML;
import ViewLayer.MapVL.EasyMapVL;
import ViewLayer.MapVL.MediumMapVL;
import ViewLayer.SoundEngine;
import ViewLayer.SwingUICore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


/**
 * Manages/controls one game session/scenario.
 */
public class GameEngine implements ActionListener {
    private final SwingUICore _UICore; //The top level Swing JFrame that loads other panels to display content.
    private final SoundEngine _SoundEngine; //Fully controls one game scenario
    private Timer _FrameRateTimer; //The timer which triggers the periodical updates for the game/screen

    /**
     * Manages/controls one game session/scenario.
     *
     * @param UICore  The top level JFRame where the InGameScreen JPanel will be loaded
     * @param SEngine The SoundEngine that plays background music and sounds.
     */
    public GameEngine(SwingUICore UICore, SoundEngine SEngine)
    {
        _UICore = UICore;
        _SoundEngine = SEngine;
    }

    /**
     * Starts the game with configurations sourced from the CurrentGameSession.
     * Instantiates the model and view objects of the selected map.
     * Instantiates the Collision Manager.
     * Instantiates the Timer which triggers the periodical updates.
     * Finally, orders the top level JFrame to load the InGameScreen JPanel.
     */
    public void StartGame()
    {
        InstantiateSelectedMap();
        CollisionManager cm = new CollisionManager(CurrentGameSession.get_CurrentPlayers(), CurrentGameSession.get_MapModel().GetMapObjects());
        CurrentGameSession.set_CollisionManager(cm);

        _UICore.NavigateToInGameScreen();
        _FrameRateTimer = new Timer(SharedResources.FRAMERATE, this); //Setting this instance to be the event handler as well
        _FrameRateTimer.start();
    }


    /**
     * Instantiates the appropriate map objects based on the selected map name retrieved from CurrentGameSession
     */
    private void InstantiateSelectedMap() {
        if (Objects.equals(CurrentGameSession.get_SelectedMapName(), SharedResources.MSP_Maps[0])) {
            CurrentGameSession.set_MapModel(new EasyMapML());
            CurrentGameSession.set_MapView(new EasyMapVL());
            return;
        }

        if (Objects.equals(CurrentGameSession.get_SelectedMapName(), SharedResources.MSP_Maps[1])) {
            CurrentGameSession.set_MapModel(new MediumMapML());
            CurrentGameSession.set_MapView(new MediumMapVL());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == _FrameRateTimer) {
            //When the timer ticks, ask the top level JFrame to update the screen.
            _UICore.UpdateInGameScreen();
        }
    }

    /**
     * Called when a car hits an another non-car object on the map (wall, tree etc).
     * This will order the SoundEngine to play an impact sound effect if the sounds are enabled by the user
     */
    public void CarImpactOccurred()
    {
        if (SharedResources.DGO_Default_Sound_On)
        {
            _SoundEngine.PlayCarImpactSound();
        }
    }

    /**
     * Called when a car accelerates.
     * This will order the SoundEngine to play an acceleration sound effect if the sounds are enabled by the user.
     */
    public void CarAccelerationOccurred()
    {
        if (SharedResources.DGO_Default_Sound_On)
        {
            _SoundEngine.SetCarAccelerateSound(true);
        }
    }

    /**
     * Called when a car stopped accelerating.
     * This will order the SoundEngine to stop playing the acceleration sound effect if the sounds are enabled by the user.
     */
    public void CarAccelerationStopped()
    {
        if (SharedResources.DGO_Default_Sound_On)
        {
            _SoundEngine.SetCarAccelerateSound(false);
        }
    }

    /**
     * Called when two cars collide with each other.
     * This will stop the frame rate triggering timer, asks the sound engine to play a crush sound (if sound is on),
     * and orders the top level JFrame to change the car image containing JLabels icon to a crashed car image.
     * Finally it calls the Main Controller as the game is ended.
     */
    public void CarCrashOccurred()
    {
        _FrameRateTimer.stop();
        _UICore.get_InGameScreen().SetCarLabelImagesCrashed();

        if (SharedResources.DGO_Default_Sound_On)
        {
            _SoundEngine.PlayCarCrashSound();
        }

        GameOver();
    }


    /**
     *  Stops the frame rate triggering timer, orders the top level JFrame to show a popup message to the
     *  user about the game is being ended. Asks the JFrame to destroy (dereference) the InGameScreen JPanel.
     *  Finally, notifies the main controller that the game is over.
     */
    private void GameOver()
    {
        _FrameRateTimer.stop();
        _UICore.DisplayGameOverPopup();
        _UICore.DestroyInGameScreen();
        SharedResources.MainController.NavigatingBackToMainMenuScreen();
    }

    /**
     * Stops the frame rate triggering timer, and orders the top level JFrame to
     * destroy (dereference) the InGameScreen JPanel.
     */
    public void ShutDown()
    {
        _FrameRateTimer.stop();
        _UICore.DestroyInGameScreen();
    }
}
