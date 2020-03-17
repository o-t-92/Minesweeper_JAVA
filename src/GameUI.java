import sweeper.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class GameUI extends JFrame {

    private static final int CELL_SIZE = 32;

    private JPanel panel;
    private Map<Cell, Image> images;
    private Game game;

    public GameUI() {
        loadImages();
        initPanel();
        initMenuBar();
        initFrame();
        switchLevel(Level.EASY);
    }

    private void loadImages() {
        images = new EnumMap<>(Cell.class);
        for (Cell cell : Cell.values()) {
            URL url = ClassLoader.getSystemResource("img/" + cell.name() + ".png");
            images.put(cell, new ImageIcon(url).getImage());
        }
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (game == null) return;
                for (Coords coords : game.getAllCoordsList()) {
                    Image image = images.get(game.getCell(coords));
                    g.drawImage(image, coords.x * CELL_SIZE, coords.y * CELL_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (game.getState() == GameState.FAILURE) {
                    game.startNewGame();
                } else {
                    switch (e.getButton()) {
                        case MouseEvent.BUTTON1:
                            if (game.getState() == GameState.INITIAL)
                                game.startNewGame();
                            game.onLeftButton(new Coords(
                                    e.getX() / CELL_SIZE,
                                    e.getY() / CELL_SIZE));
                            break;

                        case MouseEvent.BUTTON2:
                            game.startNewGame();
                            break;

                        case MouseEvent.BUTTON3:
                            game.onRightButton(new Coords(
                                    e.getX() / CELL_SIZE,
                                    e.getY() / CELL_SIZE));
                            break;
                    }
                }

                panel.repaint();

                if (game.getState() == GameState.VICTORY) {
                    JOptionPane.showMessageDialog(GameUI.this, "Вы выиграли :)");
                }
                if (game.getState() == GameState.FAILURE) {
                    JOptionPane.showMessageDialog(GameUI.this, "Вы проиграли :(");
                }
            }
        });

        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MineSweeper");
        setIconImage(images.get(Cell.BOMB));
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu levelMenu = new JMenu("Level");
        ButtonGroup buttonGroup = new ButtonGroup();
        for (Level level : Level.values()) {
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(level.name());
            levelMenu.add(menuItem);
            buttonGroup.add(menuItem);
            if (level == Level.EASY) menuItem.setSelected(true);
            menuItem.addActionListener(e -> switchLevel(level));
        }
        menuBar.add(levelMenu);
        setJMenuBar(menuBar);
    }

    public void switchLevel(Level level) {
        game = new Game(level.cols, level.rows, level.bombs);
        panel.setPreferredSize(new Dimension(level.cols * CELL_SIZE, level.rows * CELL_SIZE));
        pack();
        panel.repaint();
    }

    public static void main(String[] args) {
        new GameUI();
    }
}
