package ViewLayer.Screens.LaunchScr;

import ControlLayer.CurrentGameSession;
import ControlLayer.SharedResources;
import ModelLayer.Enumerations.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A pre-game configuration screen allowing the player(s) to select car(s) and a map.
 * It is a JPanel that contains other JPanels to achieve these functions.
 */
public class LaunchScreen extends JPanel implements ActionListener
{

    /**
     * The background image of screen.
     */
    private final Image _BackgroundImg;
    /**
     * List of car selection panels. Contains the selectable cars.
     * One panel for single player, and two panels when side-by-side game.
     */
    private ArrayList<CarSelectionPanel> _CarSelectionPanel;
    /**
     * Map selection panel. Contains the available maps.
     */
    private MapSelectionPanel _MapSelectionPanel;
    /**
     * Return to the main menu button.
     */
    private JButton _BackToMainButton;
    /**
     * Start game button.
     */
    private JButton _StartGameButton;

    /**
     * A pre-game configuration screen allowing the player(s) to select car(s) and a map.
     * It is a JPanel that contains other JPanels to achieve these functions.
     *
     * @param backgroundImage The image to use for background.
     */
    public LaunchScreen (Image backgroundImage)
    {
        this.setLayout(null);
        _BackgroundImg = backgroundImage;

        if(CurrentGameSession.get_GameMode() == GameMode.SINGLEPLAYER)
        {
            SetForSinglePlayerSelection();
        }

        if(CurrentGameSession.get_GameMode() == GameMode.SIDEBYSIDE)
        {
            SetForSideBySideSelection();
        }

        AddReturnToMainButton();
        AddGameStartButton();
        this.setVisible(true);
    }


    /**
     * Selects car(s) and map for default, so the player can proceed straight by
     * clicking to the start game button. By default, player 1 gets the first car and player 2 the second car on the list.
     * The easy map selected as default.
     */
    public void SelectDefaultValues() {
        if (CurrentGameSession.get_GameMode() == GameMode.SINGLEPLAYER) {
            _CarSelectionPanel.get(0).SelectSpecificCar(0);
        }

        if (CurrentGameSession.get_GameMode() == GameMode.SIDEBYSIDE) {
            _CarSelectionPanel.get(1).SelectSpecificCar(1);
        }

        _MapSelectionPanel.SelectDefaultMap();

        setVisible(true);
        repaint();
    }


    /**
     * Creates the "start game" button and places to the bottom right corner.
     */
    private void AddGameStartButton()
    {
        if(_StartGameButton == null)
        {
           _StartGameButton = new JButton(SharedResources.LS_GameStartButton_Text_Ready);

            int location_x = _MapSelectionPanel.getX();
           _StartGameButton.setLocation(location_x, SharedResources.LS_GameStartButton_Y);

            int width = _MapSelectionPanel.getWidth();
            _StartGameButton.setSize(width,SharedResources.LS_GameStartButton_Height);
            _StartGameButton.setBackground(SharedResources.LS_GameStartButton_Color);
            _StartGameButton.setFont(SharedResources.LS_GameStartButton_Font);

            _StartGameButton.setForeground(SharedResources.LS_GameStartButton_Foreground_Color);

            this.add(_StartGameButton);
            _StartGameButton.setVisible(true);
            _StartGameButton.addActionListener(this);
        }

    }

    /**
     * Creates the "Return to the main menu" button and places it to the bottom left corner.
     */
    private void AddReturnToMainButton()
    {
        if(_BackToMainButton == null)
        {
            _BackToMainButton = new JButton(SharedResources.LS_ReturnToMainButton_Text);
            _BackToMainButton.setLocation(SharedResources.LS_ReturnToMainButton_X,SharedResources.LS_ReturnToMainButton_Y);
            _BackToMainButton.setSize(SharedResources.LS_ReturnToMainButton_Width,SharedResources.LS_ReturnToMainButton_Height);

            _BackToMainButton.setBackground(SharedResources.LS_ReturnToMainButton_Color);
            _BackToMainButton.setFont(SharedResources.LS_ReturnToMainButton_Font);

            this.add(_BackToMainButton);
            _BackToMainButton.setVisible(true);

            _BackToMainButton.addActionListener(this);
        }
    }

    /**
     * Draws the background and paints the components.
     * @param g The palette to be painted to.
     */
    protected void  paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try
        {
            g.drawImage(_BackgroundImg,0,0,this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Setting up the screen for a Single Player scenario:
     * One car selection panel, One keyboard layout panel, one map selection panel and a start button.
     */
    private void SetForSinglePlayerSelection()
    {
        //Creating and adding the CarSelectionPanel
        _CarSelectionPanel = new ArrayList<>();
        CarSelectionPanel carSelectionPanel = new CarSelectionPanel(SharedResources.PLAYER_1);
        _CarSelectionPanel.add(carSelectionPanel);

        this.add(carSelectionPanel);
        carSelectionPanel.setLocation(SharedResources.CSP_Location_Player1_X,SharedResources.CSP_Location_Player1_Y);
        carSelectionPanel.setVisible(true);
        carSelectionPanel.SelectDefaultCar();


        //Adding the Keyboard Layout label
        KeyBoardLayoutLabel keyBoardLayout = new KeyBoardLayoutLabel(SharedResources.PLAYER_1);
        int keyBoardLayout_X = SharedResources.CSP_Location_Player1_X + carSelectionPanel.getWidth() + SharedResources.CLI_HorizontalSpacing_From_CSP;
        int keyBoardLayout_Y = SharedResources.CSP_Location_Player1_Y;
        keyBoardLayout.setLocation(keyBoardLayout_X,keyBoardLayout_Y);
        this.add(keyBoardLayout);

        //Adding the map selection panel
        MapSelectionPanel mapSelectionPanel = new MapSelectionPanel();
        int mapSelectionPanel_X = keyBoardLayout_X + keyBoardLayout.getWidth() +  SharedResources.MSP_Space_From_CLI;
        mapSelectionPanel.setLocation(mapSelectionPanel_X, keyBoardLayout_Y);

        this.add(mapSelectionPanel);
        _MapSelectionPanel = mapSelectionPanel;
    }

    /**
     * Setting up the screen for a Side-by-side scenario:
     * Two car selection panels, Two keyboard layout panel, one map selection panel and start button
     */
    private void SetForSideBySideSelection()
    {
        //Creating and adding the CarSelectionPanel-s
        _CarSelectionPanel = new ArrayList<>();
        CarSelectionPanel carSelectionPanel_P1 = new CarSelectionPanel(SharedResources.PLAYER_1);
        CarSelectionPanel carSelectionPanel_P2 = new CarSelectionPanel(SharedResources.PLAYER_2);
        _CarSelectionPanel.add(carSelectionPanel_P1);
        _CarSelectionPanel.add(carSelectionPanel_P2);

        this.add(carSelectionPanel_P1);
        this.add(carSelectionPanel_P2);
        carSelectionPanel_P1.setLocation(SharedResources.CSP_Location_Player1_X,SharedResources.CSP_Location_Player1_Y);
        carSelectionPanel_P2.setLocation(SharedResources.CSP_Location_Player2_X,SharedResources.CSP_Location_Player2_Y);
        carSelectionPanel_P1.setVisible(true);
        carSelectionPanel_P2.setVisible(true);
        carSelectionPanel_P1.SelectDefaultCar();
        carSelectionPanel_P2.SelectDefaultCar();

        //Adding the Keyboard Layout labels
        KeyBoardLayoutLabel keyBoardLayout_P1 = new KeyBoardLayoutLabel(SharedResources.PLAYER_1);
        KeyBoardLayoutLabel keyBoardLayout_P2 = new KeyBoardLayoutLabel(SharedResources.PLAYER_2);
        int keyBoardLayout_X_P1 = SharedResources.CSP_Location_Player1_X + carSelectionPanel_P1.getWidth() + SharedResources.CLI_HorizontalSpacing_From_CSP;
        int keyBoardLayout_Y_P1 = SharedResources.CSP_Location_Player1_Y;
        int keyBoardLayout_X_P2 = SharedResources.CSP_Location_Player2_X + carSelectionPanel_P2.getWidth() + SharedResources.CLI_HorizontalSpacing_From_CSP;
        int keyBoardLayout_Y_P2 = SharedResources.CSP_Location_Player2_Y;

        keyBoardLayout_P1.setLocation(keyBoardLayout_X_P1,keyBoardLayout_Y_P1);
        keyBoardLayout_P2.setLocation(keyBoardLayout_X_P2,keyBoardLayout_Y_P2);
        this.add(keyBoardLayout_P1);
        this.add(keyBoardLayout_P2);

        //Adding the map selection panel
        MapSelectionPanel mapSelectionPanel = new MapSelectionPanel();
        int mapSelectionPanel_X = keyBoardLayout_X_P1 + keyBoardLayout_P1.getWidth() +  SharedResources.MSP_Space_From_CLI;
        mapSelectionPanel.setLocation(mapSelectionPanel_X, keyBoardLayout_Y_P1);

        this.add(mapSelectionPanel);
        _MapSelectionPanel = mapSelectionPanel;
    }


    /**
     * Handling events from the "Back to main button" and
     * "Start game button" components.
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //If the "Return to main" button clicked then return to the main screen and forget current settings.
        if(e.getSource() == _BackToMainButton)
        {
            SharedResources.MainController.NavigatingBackToMainMenuScreen();
        }

        //If the "Start" button clicked, then launch the game.
        if(e.getSource() == _StartGameButton)
        {
            SharedResources.MainController.LaunchGame();
        }
    }
}
