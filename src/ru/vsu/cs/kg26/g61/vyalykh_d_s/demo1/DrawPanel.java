package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1;

import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture.Bed;
import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture.Carpet;
import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture.Nightstand;
import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture.Room;
import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture.WindowView;
import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.utils.Perspective;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class DrawPanel extends JPanel {

    public DrawPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        Perspective perspective = new Perspective(width, height);

        // 1. Сначала рисуем стены и пол (задний план)
        new Room(width, height).draw(g2);

        // 2. Ковёр лежит на полу, поэтому рисуется до мебели
        new Carpet(perspective).draw(g2);

        // 3. Ночной вид за окном, поверх него - рама окна
        new WindowView(width, height).draw(g2);

        // 4. Затем тумбочку (она в глубине комнаты)
        new Nightstand(perspective).draw(g2);

        // 5. И последней - кровать (она на переднем плане и перекрывает тумбочку)
        new Bed(perspective).draw(g2);
    }
}
