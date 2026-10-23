package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture;

import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.utils.Perspective;

import java.awt.Color;
import java.awt.Graphics2D;

public class Carpet {

    private static final Color CARPET_COLOR = new Color(25, 40, 85);
    private static final Color CARPET_BORDER_COLOR = new Color(48, 64, 128);

    private static final double CARPET_X0 = 0.0;
    private static final double CARPET_X1 = 0.30;
    private static final double CARPET_Y0 = -0.90;
    private static final double CARPET_Y1 = -0.15;

    private final Perspective perspective;

    public Carpet(Perspective perspective) {
        this.perspective = perspective;
    }

    public void draw(Graphics2D g) {
        perspective.fillWorld(g, CARPET_COLOR, new double[][]{
                {CARPET_X0, CARPET_Y0, 0.002}, {CARPET_X1, CARPET_Y0, 0.002},
                {CARPET_X1, CARPET_Y1, 0.002}, {CARPET_X0, CARPET_Y1, 0.002}});

        double ix = (CARPET_X1 - CARPET_X0) * 0.12;
        double iy = (CARPET_Y1 - CARPET_Y0) * 0.12;
        perspective.fillWorld(g, CARPET_BORDER_COLOR, new double[][]{
                {CARPET_X0 + ix, CARPET_Y0 + iy, 0.003}, {CARPET_X1 - ix, CARPET_Y0 + iy, 0.003},
                {CARPET_X1 - ix, CARPET_Y1 - iy, 0.003}, {CARPET_X0 + ix, CARPET_Y1 - iy, 0.003}});
    }
}
