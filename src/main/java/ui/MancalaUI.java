package ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import mancala.AyoRules;
import mancala.InvalidMoveException;
import mancala.KalahRules;
import mancala.MancalaGame;
import mancala.UserProfile;
import mancala.Saver;
import mancala.Player;

public class MancalaUI extends JFrame {
    private JPanel startUpContainer;
    private JPanel gameContainer2;
    private JLabel messageLabel = new JLabel();
    private JMenuBar menuBar;
    private JButton playBtn;
    private JPanel buttonPanel = new JPanel();
    private PositionAwareButton[][] buttons;
    private MancalaGame game;

    private Player playerOne;
    private Player playerTwo;

    private UserProfile p1;
    private UserProfile p2;

    private JPanel userStats;
    JLabel p1n, p1k, p1a, p2n, p2k, p2a, info;

    Saver saver = new Saver();

    private void makeUserStats() {
        userStats = new JPanel();

        p1n = new JLabel();
        p1k = new JLabel();
        p1a = new JLabel();

        p2n = new JLabel();
        p2k = new JLabel();
        p2a = new JLabel();
        info = new JLabel();

        userStats.setLayout(new BoxLayout(userStats, BoxLayout.Y_AXIS));

        updateUserStats(p1n, p1k, p1a, p2n, p2k, p2a, info);

        add(userStats, BorderLayout.WEST);
        userStats.add(p1n);
        userStats.add(p1k);
        userStats.add(p1a);
        userStats.add(p2n);
        userStats.add(p2k);
        userStats.add(p2a);
        userStats.add(info);
    }

    private void updateUserStats(JLabel p1n, JLabel p1k, JLabel p1a, JLabel p2n, JLabel p2k, JLabel p2a, JLabel info) {
        p1n.setText(p1.getName());
        p1k.setText("Kalah:    G: " + p1.getKalahGames() + " W: " + p1.getKalahWin());
        p1a.setText("Ayo  :    G: " + p1.getAyoGames() + " W: " + p1.getAyoWin());

        p2n.setText(p2.getName());
        p2k.setText("Kalah:    G: " + p2.getKalahGames() + " W: " + p2.getKalahWin());
        p2a.setText("Ayo  :    G: " + p2.getAyoGames() + " W: " + p2.getAyoWin());

        Player winner = null;

        try {
            winner = game.getWinner();
        } catch (Exception e) {
        }

        if (game != null) {
            if (winner == null) {
                info.setText("It's " + game.getCurrentPlayer().getName() + "'s Turn");
            } else {
                try {
                    info.setText(winner.getName() + " Wins!");
                } catch (Exception e) {
                }
            }
        }
    }

    private void makeMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Ruleset");
        // Customize the menu item label
        JMenuItem item = new JMenuItem("Kalah");
        JMenuItem item2 = new JMenuItem("Ayo");

        JMenu menu2 = new JMenu("Profile");

        JMenuItem item3 = new JMenuItem("Save Profile One");
        JMenuItem item5 = new JMenuItem("Save Profile Two");
        JMenuItem item4 = new JMenuItem("Load Profile One");
        JMenuItem item6 = new JMenuItem("Load Profile Two");

        item.addActionListener(e -> setRulesKalah());
        item2.addActionListener(e -> setRulesAyo());

        item3.addActionListener(e -> saveUserProfileOne());
        item5.addActionListener(e -> saveUserProfileTwo());
        item4.addActionListener(e -> loadUserProfileOne());
        item6.addActionListener(e -> loadUserProfileTwo());

        JMenu menu3 = new JMenu("Change Name");
        JMenuItem item7 = new JMenuItem("Player One");
        JMenuItem item8 = new JMenuItem("Player Two");

        item7.addActionListener(e -> changePlayerOneName());
        item8.addActionListener(e -> changePlayerTwoName());

        menu.add(item);
        menu.add(item2);

        menu2.add(item3);
        menu2.add(item5);
        menu2.add(item4);
        menu2.add(item6);

        menu3.add(item7);
        menu3.add(item8);

        menuBar.add(menu);
        menuBar.add(menu2);
        menuBar.add(menu3);
    }

    private void changePlayerOneName() {
        String name = JOptionPane.showInputDialog("New Name");

        p1.setName(name);
        playerOne.setName(name);
    }

    private void changePlayerTwoName() {
        String name = JOptionPane.showInputDialog("New Name");

        p2.setName(name);
        playerTwo.setName(name);
    }

    private void saveUserProfileOne() {
        JFileChooser chooser = new JFileChooser();
        //chooser.showSaveDialog(null);

        int r = chooser.showSaveDialog(null);

        String fileName;
        if (r == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            try {
                saver.saveObject(p1, fileName);
                updateView();
            } catch (Exception e) {
                System.out.println("There is an error");
            }
        }
    }

    private void saveUserProfileTwo() {
        JFileChooser chooser = new JFileChooser();
        //chooser.showSaveDialog(null);

        int r = chooser.showSaveDialog(null);

        String fileName;
        if (r == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            System.out.println(fileName);
            try {
                saver.saveObject(p2, fileName);
                updateView();
            } catch (Exception e) {
                System.out.println("There is an error");
            }
        }
    }

    private void loadUserProfileOne() {
        JFileChooser chooser = new JFileChooser();
        //chooser.showSaveDialog(null);

        int r = chooser.showOpenDialog(null);

        String fileName;
        if (r == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            try {
                p1 = (UserProfile) saver.loadProfile(fileName);
                updateView();
            } catch (Exception e) {
                System.out.println("Successfully Loaded Player One");
            }
        }

        pack();
    }

    private void loadUserProfileTwo() {
        JFileChooser chooser = new JFileChooser();
        //chooser.showSaveDialog(null);

        int r = chooser.showOpenDialog(null);

        String fileName;
        if (r == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            try {
                p2 = (UserProfile) saver.loadProfile(fileName);
                updateView();
            } catch (Exception e) {
                System.out.println("Successfully Loaded Player Two");
            }
        }

        pack();
    }

    private void setRulesKalah() {
        game = new MancalaGame(new KalahRules());
        game.setPlayers(playerOne, playerTwo);
        playerOne.setUserProfile(p1);
        playerTwo.setUserProfile(p2);

        startFirstGame();
    }

    private void setRulesAyo() {
        game = new MancalaGame(new AyoRules());
        game.setPlayers(playerOne, playerTwo);
        playerOne.setUserProfile(p1);
        playerTwo.setUserProfile(p2);

        startFirstGame();
    }

    public MancalaUI(String title) {
        super();

        playerOne = new Player("Player 1");
        playerTwo = new Player("Player 2");

        p1 = new UserProfile(playerOne.getName());
        p2 = new UserProfile(playerTwo.getName());

        basicSetup(title);
        makeMenu();
        makeUserStats();
        add(startUpContainer, BorderLayout.NORTH);
        add(gameContainer2, BorderLayout.CENTER);
        setupGameContainer();
        setJMenuBar(menuBar);

        menuBar.setVisible(true);
        startUpContainer.setVisible(true);
        gameContainer2.setVisible(false);
        buttonPanel.setVisible(false);
        userStats.setVisible(false);

        pack();
    }

    private void basicSetup(String title) {
        this.setTitle(title);
        gameContainer2 = new JPanel();
        startUpContainer = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(makeButtonPanel(),BorderLayout.EAST);
        gameContainer2.add(makeMancalaGrid(8, 3));
    }

    private JPanel startupMessage() {
        JPanel temp = new JPanel();
       // Customize the message as desired
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
        temp.add(new JLabel("Mancala WOOHOO!"));
        temp.add(new JLabel("Setup Steps"));
        temp.add(new JLabel("1. Choose Player Names"));
        temp.add(new JLabel("2. Load Old Profile"));
        temp.add(new JLabel("3. Choose Ruleset"));
        temp.add(new JLabel("4. Play"));

        // playBtn = new JButton("Play");
        // playBtn.addActionListener(e-> {
        //     startFirstGame();
        // });

        // temp.add(playBtn, BorderLayout.EAST);
        startUpContainer.add(temp, BorderLayout.CENTER);
        return temp;
    }

    private void startFirstGame() {
        startUpContainer.setVisible(false);
        menuBar.setVisible(false);
        buttonPanel.setVisible(true);
        gameContainer2.setVisible(true);
        userStats.setVisible(true);

        newGame();
        pack();
    }

    private void backToMenu() {
        startUpContainer.setVisible(true);
        menuBar.setVisible(true);
        buttonPanel.setVisible(false);
        gameContainer2.setVisible(false);
        userStats.setVisible(false);
    }

    private JPanel makeMancalaGrid(int wide, int tall) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(tall, wide));
        for (int y = 0; y < tall; y++) {
            for (int x = 0; x < wide; x++) {
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x + 1);
                buttons[y][x].setDown(y + 1);
                final int ex = x;
                final int why = y;
                buttons[y][x].addActionListener(e->{
                    pitHandler(ex,why);
                });
                panel.add(buttons[y][x]);
            }
        }
        return panel;
    }

    JPanel startMsg;
    public void setupGameContainer(){
        startMsg = startupMessage();
        startUpContainer.add(startMsg);
        startMsg.setVisible(true);
        startUpContainer.setVisible(true);
    }

    protected void newGame() {
        game.startNewGame(); // Start a new game
        updateView(); // Update the view to reflect the new game state

        pack();
    }

    protected void updateView() {
        updateUserStats(p1n, p1k, p1a, p2n, p2k, p2a, info);
        updateMancalaBoard();

        pack();
    }

    protected void updateMancalaBoard() {
        buttons[2][1].setText("[" + Integer.toString(game.getBoard().getNumStones(1)) + "]");
        buttons[2][2].setText("[" + Integer.toString(game.getBoard().getNumStones(2)) + "]");
        buttons[2][3].setText("[" + Integer.toString(game.getBoard().getNumStones(3)) + "]");
        buttons[2][4].setText("[" + Integer.toString(game.getBoard().getNumStones(4)) + "]");
        buttons[2][5].setText("[" + Integer.toString(game.getBoard().getNumStones(5)) + "]");
        buttons[2][6].setText("[" + Integer.toString(game.getBoard().getNumStones(6)) + "]");

        buttons[0][1].setText("[" + Integer.toString(game.getBoard().getNumStones(12)) + "]");
        buttons[0][2].setText("[" + Integer.toString(game.getBoard().getNumStones(11)) + "]");
        buttons[0][3].setText("[" + Integer.toString(game.getBoard().getNumStones(10)) + "]");
        buttons[0][4].setText("[" + Integer.toString(game.getBoard().getNumStones(9)) + "]");
        buttons[0][5].setText("[" + Integer.toString(game.getBoard().getNumStones(8)) + "]");
        buttons[0][6].setText("[" + Integer.toString(game.getBoard().getNumStones(7)) + "]");

        buttons[1][7].setText("[" + Integer.toString(playerOne.getStoreCount()) + "]");
        buttons[1][0].setText("[" + Integer.toString(playerTwo.getStoreCount()) + "]");

        System.out.println("IN UI: p1: " + playerOne.getStoreCount() + " p2: " + playerTwo.getStoreCount());
    }

    private void pitHandler(final int x, final int y) {
        int adder = 0;
        int index = 0;
        if (y == 0) {
            adder = 6;
            index = adder + (7 - x);
        } else if (y == 2) {
            index = x;
        }

        //Force index = 0 for corners
        if (x == 7 || x == 0) {
            index = 0;
        }

        if (index != 0) {
            move(index);
        }

        System.out.println(x + "," + y + ": " + index);
    }

    private void move(int index) {
        Player currPlayer = game.getCurrentPlayer();

        if (currPlayer == playerOne) {
            if (index > 6 || index <= 0) {
                return;
            }
        } else if (currPlayer == playerTwo) {
            if (index <= 6 || index > 12) {
                return;
            }
        }

        if (game.getBoard().getNumStones(index) == 0) {
            return;
        }

        try {
            game.move(index);
        } catch (InvalidMoveException e) {
            return;
        }
        updateView();
    }

    private JButton makeNewGameButton() {
        JButton button = new JButton("New Game");
        button.addActionListener(e -> newGame());
        //pane.add(button, BorderLayout.EAST);
        return button;
    }

    private JButton makeQuitButton() {
        JButton button = new JButton("     Quit     ");
        button.addActionListener(e -> quit());
        //pane.add(button, BorderLayout.EAST);
        return button;
    }

    private JButton makeSaveButton() {
        JButton button = new JButton("     Save     ");
        button.addActionListener(e -> save());
        //pane.add(button, BorderLayout.EAST);
        return button;
    }

    private JButton makeLoadButton() {
        JButton button = new JButton("     Load     ");
        button.addActionListener(e -> load());
        //pane.add(button, BorderLayout.EAST);
        return button;
    }

    private JButton makeMenuButton() {
        JButton button = new JButton("     Menu     ");
        button.addActionListener(e -> backToMenu());
        //pane.add(button, BorderLayout.EAST);
        return button;
    }

    private void quit() {
        //Use for menu
    }

    private void save() {
        JFileChooser chooser = new JFileChooser();
        //chooser.showSaveDialog(null);

        int r = chooser.showSaveDialog(null);

        String fileName;
        if (r == JFileChooser.APPROVE_OPTION) {
           fileName = chooser.getSelectedFile().getAbsolutePath();
            try {
                saver.saveObject(game, fileName);
            } catch (Exception e) {
                System.out.println("There is an error");
            }
        }


    }

    private void load() {
        //JFileChooser chooser = new JFileChooser();

        JFileChooser chooser = new JFileChooser();
        //chooser.showSaveDialog(null);

        int r = chooser.showOpenDialog(null);

        String fileName;
        if (r == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            try {
                game = (MancalaGame) saver.loadObject(fileName);
                updateView();
            } catch (Exception e) {
                System.out.println("There is an error");
            }
        }

    }

    private JButton makeMancalaButton() {
        JButton button = new JButton("Mancala");
        return button;
    }

    private JPanel makeButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(makeNewGameButton());
        buttonPanel.add(makeLoadButton());
        buttonPanel.add(makeSaveButton());
        buttonPanel.add(makeMenuButton());

        return buttonPanel;
    }

    public static void main(String[] args) {
        MancalaUI ui = new MancalaUI("Mancala is ruining me");
        ui.setVisible(true);
    }
}
