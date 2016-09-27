package ModelLayer;

import java.awt.*;

/**
 * Represents a player in a game with its associated car.
 */
public class Player
{

    /**
     * Model layer representation of the in-game car.
     */
    private final Car _Car;

    /**
     * In-game of the Player (e.g. Player 1).
     * Does not need to be unique.
     */
    private String _PlayerName = "";

    /**
     * Represents a player in a game with its associated car.
     *
     * @param _PlayerName Name of the player.
     */
    public Player(String _PlayerName)
    {
        this._PlayerName = _PlayerName;
        _Car = new Car(-1); //At this point it is not known what image index will be used. Therefore -1 is sent.
    }

    /**
     * Returns the player's name. This might not be unique in the game.
     * @return The name of the player.
     */
    public String get_PlayerName()
    {
        return _PlayerName;
    }

    /**
     * Returns the associated car's model layer representation.
     * @return Returns the associated car.
     */
    public Car get_Car()
    {
        return _Car;
    }


    /**
     * Returns the car's image to display in the Heads Up Display.
     * @return Car's image to display in the Heads Up Display.
     */
    public Image GetPlayerCarIconForHUD()
    {
        Image result = null;
        if(_Car != null)
        {
            result = _Car.get_CarDisplay().GetFirstCarImageForHUD();
        }
        return  result;
    }


    /**
     * Returns the virtual (scaled) current speed of the car to be displayed by the HUD.
     * @return Returns the virtual (scaled) current speed of the car.
     */
    public int GetLatestCarSpeedForHUD()
    {
        int result = 0;
        if(_Car != null)
        {
            result = _Car.GetVirtualSpeed();
        }
        return  result;
    }
}
