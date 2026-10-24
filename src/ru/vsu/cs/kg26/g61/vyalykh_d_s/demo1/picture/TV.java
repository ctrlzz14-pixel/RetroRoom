package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture;

import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.utils.Perspective;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class TV {

    private static final Color BODY_TOP_COLOR = new Color(38, 39, 52);
    private static final Color BODY_FRONT_COLOR = new Color(26, 27, 38);
    private static final Color BODY_SIDE_COLOR = new Color(18, 19, 28);

    private static final Color BEZEL_COLOR = new Color(9, 10, 16);
    private static final Color BEZEL_INNER_COLOR = new Color(14, 15, 24);

    private static final Color SCREEN_PHOSPHOR_COLOR = new Color(28, 78, 156);
    private static final Color SCREEN_DARK_COLOR = new Color(16, 52, 118);
    private static final Color SCREEN_SCAN_COLOR = new Color(36, 94, 178);
    private static final Color SCREEN_GLOW_COLOR = new Color(72, 142, 230);

    private static final Color KNOB_COLOR = new Color(142, 145, 158);
    private static final Color KNOB_DARK_COLOR = new Color(58, 60, 74);
    private static final Color KNOB_RING_COLOR = new Color(92, 95, 112);

    private static final double TV_X0 = 0.256;
    private static final double TV_X1 = 0.424;
    private static final double TV_Y0 = -0.94;
    private static final double TV_Y1 = -0.68;
    private static final double TV_H0 = 0.328;
    private static final double TV_H1 = 1.078;

    private static final double SCALE = 1.0;
    private static final double CX = 0.10;
    private static final double CY = -0.83;
    private static final double CH = 0.184;
    private static final double TX = -0.274;
    private static final double TY = 0.21;

    private static double S(double v) {
        return (v - CX) * SCALE + CX + TX;
    }

    private static double SY(double v) {
        return (v - CY) * SCALE + CY + TY;
    }

    private static double SH(double v) {
        return (v - CH) * SCALE + CH;
    }

    private static final double SCREEN_Y0 = SY(TV_Y0) + 0.044 * SCALE;
    private static final double SCREEN_Y1 = SY(TV_Y1) - 0.044 * SCALE;
    private static final double SCREEN_H0 = SH(TV_H0) + 0.136 * SCALE;
    private static final double SCREEN_H1 = SH(TV_H1) - 0.086 * SCALE;

    private final Perspective perspective;

    public TV(Perspective perspective) {
        this.perspective = perspective;
    }

    public void draw(Graphics2D g) {
        perspective.fillBox(g, S(TV_X0), S(TV_X1), SY(TV_Y0), SY(TV_Y1), SH(TV_H0), SH(TV_H1),
                BODY_TOP_COLOR, BODY_FRONT_COLOR, BODY_SIDE_COLOR);

        double sxv = S(TV_X1) + 0.002 * SCALE;

        perspective.fillWorld(g, BEZEL_COLOR, new double[][]{
                {sxv, SY(TV_Y0) + 0.016 * SCALE, SH(TV_H0) + 0.038 * SCALE}, {sxv, SY(TV_Y1) - 0.016 * SCALE, SH(TV_H0) + 0.038 * SCALE},
                {sxv, SY(TV_Y1) - 0.016 * SCALE, SH(TV_H1) - 0.032 * SCALE}, {sxv, SY(TV_Y0) + 0.016 * SCALE, SH(TV_H1) - 0.032 * SCALE}});

        perspective.fillWorld(g, BEZEL_INNER_COLOR, new double[][]{
                {sxv + 0.001 * SCALE, SY(TV_Y0) + 0.024 * SCALE, SH(TV_H0) + 0.048 * SCALE}, {sxv + 0.001 * SCALE, SY(TV_Y1) - 0.024 * SCALE, SH(TV_H0) + 0.048 * SCALE},
                {sxv + 0.001 * SCALE, SY(TV_Y1) - 0.024 * SCALE, SH(TV_H1) - 0.042 * SCALE}, {sxv + 0.001 * SCALE, SY(TV_Y0) + 0.024 * SCALE, SH(TV_H1) - 0.042 * SCALE}});

        perspective.fillWorld(g, SCREEN_PHOSPHOR_COLOR, new double[][]{
                {sxv + 0.002 * SCALE, SCREEN_Y0, SCREEN_H0}, {sxv + 0.002 * SCALE, SCREEN_Y1, SCREEN_H0},
                {sxv + 0.002 * SCALE, SCREEN_Y1, SCREEN_H1}, {sxv + 0.002 * SCALE, SCREEN_Y0, SCREEN_H1}});

        perspective.fillWorld(g, SCREEN_DARK_COLOR, new double[][]{
                {sxv + 0.002 * SCALE, SCREEN_Y0, SCREEN_H0},
                {sxv + 0.002 * SCALE, SCREEN_Y1, SCREEN_H0},
                {sxv + 0.002 * SCALE, SCREEN_Y1, SCREEN_H0 + 0.052 * SCALE},
                {sxv + 0.002 * SCALE, SCREEN_Y0, SCREEN_H0 + 0.052 * SCALE}});

        perspective.fillWorld(g, SCREEN_SCAN_COLOR, new double[][]{
                {sxv + 0.0025 * SCALE, SCREEN_Y0 + 0.018 * SCALE, SCREEN_H0 + 0.065 * SCALE},
                {sxv + 0.0025 * SCALE, SCREEN_Y1 - 0.018 * SCALE, SCREEN_H0 + 0.065 * SCALE},
                {sxv + 0.0025 * SCALE, SCREEN_Y1 - 0.018 * SCALE, SCREEN_H0 + 0.074 * SCALE},
                {sxv + 0.0025 * SCALE, SCREEN_Y0 + 0.018 * SCALE, SCREEN_H0 + 0.074 * SCALE}});

        perspective.fillWorld(g, SCREEN_GLOW_COLOR, new double[][]{
                {sxv + 0.003 * SCALE, SCREEN_Y0 + 0.026 * SCALE, SCREEN_H1 - 0.034 * SCALE},
                {sxv + 0.003 * SCALE, SCREEN_Y0 + 0.058 * SCALE, SCREEN_H1 - 0.034 * SCALE},
                {sxv + 0.003 * SCALE, SCREEN_Y0 + 0.058 * SCALE, SCREEN_H0 + 0.048 * SCALE},
                {sxv + 0.003 * SCALE, SCREEN_Y0 + 0.026 * SCALE, SCREEN_H0 + 0.048 * SCALE}});

        perspective.fillWorld(g, new Color(18, 56, 132), new double[][]{
                {sxv + 0.0025 * SCALE, SCREEN_Y0, SCREEN_H1 - 0.038 * SCALE},
                {sxv + 0.0025 * SCALE, SCREEN_Y1, SCREEN_H1 - 0.038 * SCALE},
                {sxv + 0.0025 * SCALE, SCREEN_Y1, SCREEN_H1 - 0.016 * SCALE},
                {sxv + 0.0025 * SCALE, SCREEN_Y0, SCREEN_H1 - 0.016 * SCALE}});

        drawKnob(g, SY(TV_Y0) + 0.088 * SCALE, SH(TV_H0) + 0.084 * SCALE, 0.0078);
        drawKnob(g, SY(TV_Y0) + 0.146 * SCALE, SH(TV_H0) + 0.084 * SCALE, 0.0078);
    }

    private void drawKnob(Graphics2D g, double y, double h, double radiusFactor) {
        int w = perspective.getWidth(), height = perspective.getHeight();
        double[] p = perspective.project(S(TV_X1) + 0.005 * SCALE, y, h);
        double r = radiusFactor * w * SCALE;

        g.setColor(KNOB_DARK_COLOR);
        g.fill(new Ellipse2D.Double(p[0] * w - r, p[1] * height - r, 2 * r, 2 * r));

        double ring = r * 0.84;
        g.setColor(KNOB_RING_COLOR);
        g.fill(new Ellipse2D.Double(p[0] * w - ring, p[1] * height - ring, 2 * ring, 2 * ring));

        double inner = r * 0.58;
        g.setColor(KNOB_COLOR);
        g.fill(new Ellipse2D.Double(p[0] * w - inner, p[1] * height - inner, 2 * inner, 2 * inner));
    }
}
