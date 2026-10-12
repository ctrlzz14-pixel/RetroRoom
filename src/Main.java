
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class Main extends JPanel {

    // Размеры исходного холста
    private static final int SCENE_WIDTH = 1408;
    private static final int SCENE_HEIGHT = 1000;

    // Цвета комнаты
    private static final Color WALL_COLOR = new Color(29, 55, 99);
    private static final Color FLOOR_COLOR = new Color(139, 87, 67);
    private static final Color NIGHT_COLOR = new Color(13, 37, 91);
    private static final Color WINDOW_FRAME = new Color(13, 27, 57);

    public Main() {
        setPreferredSize(new Dimension(1000, 710));
        setBackground(WALL_COLOR);
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Масштабирование рисунка
        double scaleX = getWidth() / (double) SCENE_WIDTH;
        double scaleY = getHeight() / (double) SCENE_HEIGHT;
        g2.scale(scaleX, scaleY);

        // Сглаживание краёв фигур
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Сначала закрашиваем весь холст
        g2.setColor(WALL_COLOR);
        g2.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT);

        // Затем рисуем элементы комнаты
        drawWall(g2);
        drawWindow(g2);
        drawFloor(g2);

        g2.dispose();
    }

    // Задняя стена
    private void drawWall(Graphics2D g2) {
        g2.setColor(WALL_COLOR);
        g2.fillRect(0, 0, SCENE_WIDTH, 760);
    }

    // Ночное небо и рама окна
    private void drawWindow(Graphics2D g2) {
        // Ночное небо
        g2.setColor(NIGHT_COLOR);
        g2.fillRect(730, 0, 678, 640);

        // Рама окна
        g2.setColor(WINDOW_FRAME);

        g2.fillRect(700, 0, 48, 650);
        g2.fillRect(935, 0, 28, 640);
        g2.fillRect(1292, 0, 31, 650);

        // Горизонтальные перекладины
        g2.fillRect(730, 95, 678, 30);
        g2.fillRect(730, 510, 678, 24);

        // Вертикальная перекладина
        g2.fillRect(1292, 0, 31, 640);
    }

    // Пол комнаты
    private void drawFloor(Graphics2D g2) {
        g2.setColor(FLOOR_COLOR);

        Path2D floor = new Path2D.Double();
        floor.moveTo(0, 760);
        floor.lineTo(560, 740);
        floor.lineTo(1408, 805);
        floor.lineTo(1408, 1000);
        floor.lineTo(0, 1000);
        floor.closePath();

        g2.fill(floor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Retro Room");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new Main());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
