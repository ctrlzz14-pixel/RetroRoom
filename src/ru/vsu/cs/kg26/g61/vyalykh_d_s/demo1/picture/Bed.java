package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture;

import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.utils.Perspective;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bed {

    private static final Color BED_COLOR = new Color(25, 40, 85);
    private static final Color BED_HIGHLIGHT = new Color(35, 55, 110);
    private static final Color PILLOW_COLOR = new Color(160, 170, 190);

    private static final Color PILLOW_SIDE_COLOR = new Color(140, 150, 172);
    private static final Color PILLOW_FRONT_COLOR = new Color(128, 138, 160);

    private static final Color BED_FRAME_TOP_COLOR = new Color(122, 72, 52);
    private static final Color BED_FRAME_FRONT_COLOR = new Color(96, 53, 38);
    private static final Color BED_FRAME_SIDE_COLOR = new Color(72, 39, 29);

    private static final Color MATTRESS_FRONT_COLOR = new Color(15, 25, 55);
    private static final Color MATTRESS_SIDE_COLOR = new Color(18, 30, 66);

    // Кровать: изголовье у задней стены, ноги уходят к зрителю и за нижний край
    private static final double BED_X0 = 0.42;
    private static final double BED_X1 = 0.78;
    private static final double BED_Y_HEAD = -0.05;
    private static final double BED_Y_FOOT = -0.72;
    private static final double FRAME_H = 0.085;
    private static final double MAT_INSET = 0.012;
    private static final double MAT_TOP = 0.295;

    // Подушки у изголовья
    private static final double[][] PILLOWS = {{0.53, 0.62}, {0.645, 0.735}};
    private static final double PILLOW_Y0 = -0.17;
    private static final double PILLOW_Y1 = -0.07;
    private static final double PILLOW_H = 0.055;

    private final Perspective perspective;

    public Bed(Perspective perspective) {
        this.perspective = perspective;
    }

    public void draw(Graphics2D g) {
        // Каркас (нижняя часть кровати) - тёмное дерево
        perspective.fillBox(g, BED_X0, BED_X1, BED_Y_FOOT, BED_Y_HEAD, 0.0, FRAME_H,
                BED_FRAME_TOP_COLOR, BED_FRAME_FRONT_COLOR, BED_FRAME_SIDE_COLOR);

        // Матрас с одеялом
        perspective.fillBox(g, BED_X0 + MAT_INSET, BED_X1, BED_Y_FOOT + MAT_INSET, BED_Y_HEAD - MAT_INSET,
                FRAME_H, MAT_TOP, BED_COLOR, MATTRESS_FRONT_COLOR, MATTRESS_SIDE_COLOR);

        // Блик на одеяле у изголовья
        perspective.fillWorld(g, BED_HIGHLIGHT, new double[][]{
                {BED_X0 + MAT_INSET, BED_Y_HEAD - MAT_INSET, MAT_TOP + 0.002},
                {BED_X1, BED_Y_HEAD - MAT_INSET, MAT_TOP + 0.002},
                {BED_X1, BED_Y_HEAD - MAT_INSET - 0.055, MAT_TOP + 0.002},
                {BED_X0 + MAT_INSET, BED_Y_HEAD - MAT_INSET - 0.055, MAT_TOP + 0.002}});

        // Подушки у изголовья (правый край уходит за границу кадра)
        for (double[] pillow : PILLOWS) {
            perspective.fillBox(g, pillow[0], pillow[1], PILLOW_Y0, PILLOW_Y1, MAT_TOP, MAT_TOP + PILLOW_H,
                    PILLOW_COLOR, PILLOW_FRONT_COLOR, PILLOW_SIDE_COLOR);
        }
    }
}
