package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class SnakeGame extends JFrame {
	private static final long serialVersionUID = 1L;
	private SnakeBoard board;
	private ArrayList<String> commandPool;
	int index;
	/** 0: auto, 1: script file */
	int runType;
	Timer t;

	public SnakeGame() {
		// setPreferredSize(new Dimension(500,600));
		board = new SnakeBoard();
		getContentPane().add(board);
		setResizable(true);
		setTitle("Snake - A: auto | F: script file | S: stop");
		pack();
		setLocationRelativeTo(null);
		commandPool = new ArrayList<>();

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (!t.isRunning()) {
					if (e.getKeyCode() == KeyEvent.VK_A) {
						runType = 0;
						t.start();
					} else if (e.getKeyCode() == KeyEvent.VK_F) {
						runType = 1;
						readFile();
						t.start();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					t.stop();
				}
			}

		});

		t = new Timer(200, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean moveResult = false;
				int count = 0;
				do {
					if (runType == 1) {

						// Move using script from file
						if (index < commandPool.size()) {
							String command = (String) commandPool.get(index);
							if ("T".equals(command)) {
								moveResult = board.move(1);
							} else if ("B".equals(command)) {
								moveResult = board.move(2);
							} else if ("L".equals(command)) {
								moveResult = board.move(3);
							} else if ("R".equals(command)) {
								moveResult = board.move(4);
							}
							System.out.println(command);
							index++;
						} else {
							index = 0;
						}
					} else if (runType == 0) {

						// Random movement
						int direction = ThreadLocalRandom.current().nextInt(1, 5);
						System.out.println(direction);
						moveResult = board.move(direction);
					}
					count++;
				} while (!moveResult && count < 20);
				
				if (count == 20) {
					t.stop();
					JOptionPane.showMessageDialog(SnakeGame.this, "There is no way to move. Reset the game!");
					board.init();
					t.start();
				}
			}
		});

	}

	/**
	 * Read movement instruction from text file
	 */
	public void readFile() {
		try {
			Scanner scn = new Scanner(new File("files/instruction.txt"));
			commandPool.clear();
			t.stop();
			while (scn.hasNextLine()) {
				String line = scn.nextLine();
				commandPool.add(line);
			}
			scn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SnakeGame f = new SnakeGame();
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
