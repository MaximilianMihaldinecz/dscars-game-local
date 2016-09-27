package ViewLayer.Screens.InGameScr;

import ControlLayer.CurrentGameSession;
import ControlLayer.SharedResources;
import ModelLayer.Enumerations.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.stream.Stream;

public class InGameScreen extends JPanel implements ActionListener, KeyListener
{

    private CarInGameDisplayLabel[] _CarLabels;
    private HeadsUpDisplayPanel[] _HUDs;
    private JLabel _MapBackGroundTexture = null;

    public InGameScreen()
    {
        this.setLayout(null);
        CreateCarLabels();
        CreateHeadsUpDisplays();

        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.requestFocus();

        this.setVisible(true);
    }

    public void ShutDown()
    {
        for (int i = 0; i < _CarLabels.length; i++) {
           _CarLabels[i].ShutDown();
            _HUDs[i] = null;
            _MapBackGroundTexture = null;
        }
    }

    private void CreateHeadsUpDisplays()
    {
        if(CurrentGameSession.get_GameMode() == GameMode.SINGLEPLAYER)
        {
            _HUDs = new HeadsUpDisplayPanel[1];
            _HUDs[0] = new HeadsUpDisplayPanel(CurrentGameSession.get_CurrentPlayers().get(0));
            _HUDs[0].setLocation(SharedResources.HUD_OnScreenLocation_X_Player_1, SharedResources.HUD_OnScreenLocation_Y_Player_1);
            _HUDs[0].CreateHudComponents();
        }

        if(CurrentGameSession.get_GameMode() == GameMode.SIDEBYSIDE)
        {
            _HUDs = new HeadsUpDisplayPanel[2];
            _HUDs[0] = new HeadsUpDisplayPanel(CurrentGameSession.get_CurrentPlayers().get(0));
            _HUDs[1] = new HeadsUpDisplayPanel(CurrentGameSession.get_CurrentPlayers().get(1));
            _HUDs[0].setLocation(SharedResources.HUD_OnScreenLocation_X_Player_1, SharedResources.HUD_OnScreenLocation_Y_Player_1);
            _HUDs[1].setLocation(SharedResources.HUD_OnScreenLocation_X_Player_2, SharedResources.HUD_OnScreenLocation_Y_Player_2);
            _HUDs[0].CreateHudComponents();
            _HUDs[1].CreateHudComponents();
        }

        Stream.of(_HUDs).forEach(h ->
        {
            this.add(h);
            h.setVisible(true);
        });
    }

    private void CreateCarLabels()
    {
        if(CurrentGameSession.get_GameMode() == GameMode.SINGLEPLAYER)
        {
            _CarLabels = new CarInGameDisplayLabel[1];
            _CarLabels[0] = new CarInGameDisplayLabel(CurrentGameSession.get_CurrentPlayers().get(0));

            _CarLabels[0].setLocation(CurrentGameSession.get_MapModel().getCAR_StartingPoint_X_Player1(), CurrentGameSession.get_MapModel().getCAR_StartingPoint_Y_Player1());
            _CarLabels[0].SetStartImage(CurrentGameSession.get_MapModel().getCAR_Starting_Angle());
        }

        if(CurrentGameSession.get_GameMode() == GameMode.SIDEBYSIDE)
        {
            _CarLabels = new CarInGameDisplayLabel[2];
            _CarLabels[0] = new CarInGameDisplayLabel(CurrentGameSession.get_CurrentPlayers().get(0));
            _CarLabels[1] = new CarInGameDisplayLabel(CurrentGameSession.get_CurrentPlayers().get(1));


            _CarLabels[0].setLocation(CurrentGameSession.get_MapModel().getCAR_StartingPoint_X_Player1(), CurrentGameSession.get_MapModel().getCAR_StartingPoint_Y_Player1());
            _CarLabels[0].SetStartImage(CurrentGameSession.get_MapModel().getCAR_Starting_Angle());
            _CarLabels[1].setLocation(CurrentGameSession.get_MapModel().getCAR_StartingPoint_X_Player2(), CurrentGameSession.get_MapModel().getCAR_StartingPoint_Y_Player2());
            _CarLabels[1].SetStartImage(CurrentGameSession.get_MapModel().getCAR_Starting_Angle());

        }



        Stream.of(_CarLabels).forEach(c ->
        {
            this.add(c);
            c.setVisible(true);
        });
    }

    public void NextFrame()
    {
        Arrays.stream(_CarLabels).forEach(cl ->
        {
            if(SharedResources.MainController.get_GameEngine() != null)
            {
                cl.RefreshForNextFrame();
            }
        });

        Arrays.stream(_HUDs).forEach(h1 ->
        {
            if(SharedResources.MainController.get_GameEngine() != null) {
                h1.UpdateHUD();
            }
        });

        if(SharedResources.MainController.get_GameEngine() != null)
            repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        DrawMap(g);
    }

    private void DrawMap(Graphics g)
    {
        if (SharedResources.DGO_Default_MapTexture_On)
        {
            if (_MapBackGroundTexture == null)
            {
                _MapBackGroundTexture = new JLabel();
                Icon i = new ImageIcon(CurrentGameSession.get_MapView().LoadTextureImage());
                _MapBackGroundTexture.setIcon(i);
                _MapBackGroundTexture.setSize(i.getIconWidth(), i.getIconHeight());
                _MapBackGroundTexture.setLocation(0, 0);
                this.add(_MapBackGroundTexture);
            }
            else
            {
                if (!_MapBackGroundTexture.isVisible()) {
                    _MapBackGroundTexture.setVisible(true);
                }
            }
        } else {
            if (_MapBackGroundTexture != null) {

                _MapBackGroundTexture.setVisible(false);
            }
            CurrentGameSession.get_MapView().DrawMap(g);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {

        if(CurrentGameSession.get_GameMode() == GameMode.SINGLEPLAYER)
        {
            if(Arrays.asList(SharedResources.GCS_ControlKeys_Player_1).contains(e.getKeyCode()))
            {
                //JOptionPane.showMessageDialog(null, "Received KEYPRESSED action: " + e.toString());
                _CarLabels[0].ControlKeyPressed(e.getKeyCode());
            }

        }

        if(CurrentGameSession.get_GameMode() == GameMode.SIDEBYSIDE)
        {
            if(Arrays.asList(SharedResources.GCS_ControlKeys_Player_1).contains(e.getKeyCode()))
            {
                //JOptionPane.showMessageDialog(null, "Received KEYPRESSED action: " + e.toString());
                _CarLabels[0].ControlKeyPressed(e.getKeyCode());
            }

            if(Arrays.asList(SharedResources.GCS_ControlKeys_Player_2).contains(e.getKeyCode()))
            {
                //JOptionPane.showMessageDialog(null, "Received KEYPRESSED action: " + e.toString());
                _CarLabels[1].ControlKeyPressed(e.getKeyCode());
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

        if(CurrentGameSession.get_GameMode() == GameMode.SINGLEPLAYER)
        {
            if(Arrays.asList(SharedResources.GCS_ControlKeys_Player_1).contains(e.getKeyCode()))
            {
                //JOptionPane.showMessageDialog(null, "Received KEYPRESSED action: " + e.toString());
                _CarLabels[0].ControlKeyReleased(e.getKeyCode());
            }
        }

        if(CurrentGameSession.get_GameMode() == GameMode.SIDEBYSIDE)
        {
            if(Arrays.asList(SharedResources.GCS_ControlKeys_Player_1).contains(e.getKeyCode()))
            {
                //JOptionPane.showMessageDialog(null, "Received KEYPRESSED action: " + e.toString());
                _CarLabels[0].ControlKeyReleased(e.getKeyCode());
            }

            if(Arrays.asList(SharedResources.GCS_ControlKeys_Player_2).contains(e.getKeyCode()))
            {
                //JOptionPane.showMessageDialog(null, "Received KEYPRESSED action: " + e.toString());
                _CarLabels[1].ControlKeyReleased(e.getKeyCode());
            }
        }
    }


    public void SetCarLabelImagesCrashed()
    {
        for (CarInGameDisplayLabel _CarLabel : _CarLabels) {
            _CarLabel.SetImageToCrashedCar();
        }

        repaint();
    }


}
