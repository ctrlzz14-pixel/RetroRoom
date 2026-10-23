package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JDialog {

    // Эти поля нужны для того, чтобы IntelliJ .form не выдавал ошибку
    private JPanel contentPane;
    private JPanel PaintArea;
    private JButton OK;
    private JButton buttonCancel;

    private final DrawPanel drawPanel;

    public MainWindow() {
        setTitle("Комната");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(900, 650);
        setLocationRelativeTo(null);

        drawPanel = new DrawPanel(this);
        setContentPane(drawPanel);
    }

    public void drawScene(Graphics2D g) {
        // 1. Сначала рисуем стены и пол (задний план)
        drawPanel.drawBackWall(g);
        drawPanel.drawSideWall(g);
        drawPanel.drawFloor(g);

        // 2. Ковёр лежит на полу, поэтому рисуется до мебели
        drawPanel.drawCarpet(g);

        // 3. Ночной вид за окном, поверх него - рама окна
        drawPanel.drawWindowView(g);
        drawPanel.drawWindow(g);

        // 4. Затем тумбочку (она в глубине комнаты)
        drawPanel.drawNightstand(g);

        // 5. И последней - кровать (она на переднем плане и перекрывает тумбочку)
        drawPanel.drawBed(g);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
