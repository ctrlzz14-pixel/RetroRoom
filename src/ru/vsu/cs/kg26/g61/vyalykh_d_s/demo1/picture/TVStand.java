package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture;

import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.utils.Perspective;

import java.awt.Color;
import java.awt.Graphics2D;

public class TVStand {

    private static final Color TOP_COLOR = new Color(108, 74, 58);
    private static final Color FRONT_COLOR = new Color(80, 54, 40);
    private static final Color SIDE_COLOR = new Color(58, 40, 30);
    private static final Color BOTTOM_COLOR = new Color(52, 36, 28);
    private static final Color BACK_COLOR = new Color(46, 34, 26);

    private static final double X0 = 0.20;
    private static final double X1 = 0.46;
    private static final double Y0 = -0.96;
    private static final double Y1 = -0.68;
    private static final double H0 = 0.0;
    private static final double H_BODY = 0.30;
    private static final double H_TOP = 0.328;

    private static final double SCALE = 1.0;
    private static final double CX = 0.10;
    private static final double CY = -0.83;
    private static final double CH = 0.184;
    private static final double TX = -0.274;
    private static final double TY = 0.21;

    private final Perspective perspective;

    public TVStand(Perspective perspective) {
        this.perspective = perspective;
    }

    private double sx(double x) {
        return (x - CX) * SCALE + CX + TX;
    }

    private double sy(double y) {
        return (y - CY) * SCALE + CY + TY;
    }

    private double sh(double h) {
        return (h - CH) * SCALE + CH;
    }

    public void draw(Graphics2D g) {
        // Monolithic solid block
        perspective.fillBox(g, sx(X0), sx(X1), sy(Y0), sy(Y1), sh(H0), sh(H_BODY),
                BOTTOM_COLOR, FRONT_COLOR, SIDE_COLOR);
        perspective.fillBox(g, sx(X0), sx(X1), sy(Y0), sy(Y1), sh(H_BODY), sh(H_TOP),
                TOP_COLOR, FRONT_COLOR, SIDE_COLOR);
    }
}
