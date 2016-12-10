package snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class SnakeBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private int row = 20;
	private int col = 20;
	private int size = 25;
	private Point[] snake;

	public SnakeBoard() {
		setPreferredSize(new Dimension(col * size, row * size));
		init();
		
//		setFocusable(true);
//		requestFocus();
//		addKeyListener(new KeyAdapter() {
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				super.keyPressed(e);
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					move(1);
//				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					move(2);
//				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					move(3);
//				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					move(4);
//				}
//				System.out.println(e.getKeyCode());
//			}
//
//		});
	}

	/**
	 * Init a snake
	 */
	public void init() {
		snake = new Point[] { 
				new Point(10, 9), 
				new Point(10, 8), 
				new Point(10, 7), 
				new Point(10, 6), 
				new Point(10, 5) };
	}

	/**
	 * Move the snake
	 * @param direction:
	 *            1 up, 2 down, 3 left, 4 right
	 */
	public boolean move(int direction) {
		Point head = (Point) snake[0].clone();

		if (direction == 1 && head.y > 0) { // UP
			head.y -= 1;
		} else if (direction == 2 && head.y < col - 1) { // Down
			head.y += 1;
		} else if (direction == 3 && head.x > 0) { // Left
			head.x -= 1;
		} else if (direction == 4 && head.x < row - 1) { // Right
			head.x += 1;
		} else {
			return false;
		}
		// Phát hiện đi vào thân
		for (int i = snake.length - 1; i > 0; i--) {
			if (head.x == snake[i].x && head.y == snake[i].y) {
				return false; // Không đi vào thân
			}
		}

		// Di chuyển
		for (int i = snake.length - 1; i > 0; i--) {
			snake[i] = (Point) snake[i - 1].clone();
		}
		snake[0] = (Point) head.clone();
		repaint();
		return true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		size = (getHeight() < getWidth()) ? getHeight() / row : getWidth() / col;
		Graphics2D g2d = (Graphics2D) g.create();
		BasicStroke stroke = new BasicStroke(1);
		g2d.setStroke(stroke);

		//Fill màu nền
		int w = getWidth();
        int h = getHeight();
        Color color1 = Color.LIGHT_GRAY;
        Color color2 = Color.WHITE;
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
		
		//Paint the snake
		for (int i = 0; i < snake.length; i++) {
			Point p = snake[i];
			int x = p.x * size;
			int y = p.y * size;
			if (i == 0) {
				g2d.setColor(new Color(0,140,0));
			} else {
				g2d.setColor(new Color(0, 140+(i+1)*20, 0));
			}
			g2d.fillRect(x, y, size, size);
		}
		//Paint the grid
		g2d.setColor(Color.GRAY);
		for (int i = 0; i <= row; i++) {
			g2d.drawLine(0, i * size, col * size, i * size);
		}
		for (int i = 0; i <= col; i++) {
			g2d.drawLine(i * size, 0, i * size, row * size);
		}

	}

}
