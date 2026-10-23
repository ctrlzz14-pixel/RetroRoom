package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class WindowView {

    private static final Color WINDOW_FRAME_COLOR = new Color(8, 17, 46);
    private static final Color WINDOW_GLASS_COLOR = new Color(2, 34, 95);

    private static final double TOP_BAR_U0 = 0.505;
    private static final double TOP_BAR_Y0 = 0.1;
    private static final double TOP_BAR_SLOPE = -0.1761;
    private static final double TOP_BAR_H = 0.025;

    private static final double GLASS_BOT_REF_U = 0.547;
    private static final double GLASS_BOT_REF_Y = 0.5688;
    private static final double GLASS_BOT_SLOPE = 0.052;
    private static final double BOTTOM_BAR_H = 0.0375;

    private static final double LEFT_FRAME_U0 = 0.505;
    private static final double LEFT_FRAME_U1 = 0.533;
    private static final double MULLION1_U0 = 0.655;
    private static final double MULLION1_U1 = 0.671;
    private static final double MULLION2_U0 = 0.915;
    private static final double MULLION2_U1 = 0.935;

    private static final Color SKY_TOP_COLOR = WINDOW_GLASS_COLOR;
    private static final Color SKY_BOTTOM_COLOR = new Color(3, 45, 122);
    private static final Color MOON_COLOR = new Color(254, 215, 156);
    private static final Color STAR_COLOR = new Color(248, 248, 235);

    private static final Color CITY_FAR_COLOR = new Color(0, 42, 118);
    private static final Color CITY_MID_COLOR = new Color(0, 30, 90);
    private static final Color CITY_NEAR_COLOR = new Color(8, 24, 60);
    private static final Color CITY_LIT_COLOR = new Color(240, 204, 142);
    private static final Color CITY_LIT_BRIGHT_COLOR = new Color(248, 235, 167);
    private static final Color CITY_DARK_WINDOW_COLOR = new Color(10, 26, 70);

    /** Нижняя граница градиента неба (у горизонта). */
    private static final double SKY_HORIZON_V = 0.43;

    /** Луна: центр (u, v) и радиус в долях ширины кадра. */
    private static final double MOON_U = 0.8496;
    private static final double MOON_V = 0.1849;
    private static final double MOON_R = 0.032;

    /** Крупные искорки: {u, v, радиус (доля ширины), число лучей}. */
    private static final double[][] SPARKLES = {
            {0.7219, 0.1438, 0.0133, 4},
            {0.9719, 0.1042, 0.0078, 4},
            {0.5805, 0.3068, 0.0062, 6},
            {0.7953, 0.4052, 0.0047, 4},
            {0.7055, 0.2375, 0.0039, 4},
            {0.9672, 0.2531, 0.0109, 4}
    };

    /** Ровно две падающие звезды: {u головы, v головы, u хвоста, v хвоста}. */
    private static final double[][] SHOOTING_STARS = {
            {0.5750, 0.2406, 0.6297, 0.1875},
            {0.7977, 0.3177, 0.8383, 0.2781}
    };

    /** Число мелких звёзд на небе. */
    private static final int STARS_COUNT = 90;

    /** Силуэты домов: {u левого края, u правого края, v крыши, тип крыши}. */
    private static final double[][] CITY_FAR = {
            {0.5, 0.54, 0.3873, 0},
            {0.54, 0.5944, 0.3882, 0},
            {0.5944, 0.6323, 0.3883, 0},
            {0.6323, 0.6549, 0.4007, 0},
            {0.6549, 0.6808, 0.3884, 0},
            {0.6808, 0.713, 0.4082, 1},
            {0.713, 0.7613, 0.3763, 0},
            {0.7613, 0.8154, 0.3993, 0},
            {0.8154, 0.8607, 0.4031, 1},
            {0.8607, 0.8884, 0.4036, 0},
            {0.8884, 0.9388, 0.3951, 1},
            {0.9388, 0.9783, 0.3769, 1},
            {0.9783, 1.02, 0.3739, 0}
    };

    private static final double[][] CITY_MID = {
            {0.5, 0.5406, 0.4284, 0},
            {0.5406, 0.5893, 0.4191, 1},
            {0.5893, 0.6328, 0.4078, 0},
            {0.6328, 0.6761, 0.4387, 0},
            {0.6761, 0.7244, 0.4351, 1},
            {0.7244, 0.7709, 0.4163, 0},
            {0.7709, 0.8032, 0.4089, 0},
            {0.8032, 0.8556, 0.4043, 0},
            {0.8556, 0.9014, 0.4303, 1},
            {0.9014, 0.9327, 0.4115, 0},
            {0.9327, 0.9815, 0.4003, 3},
            {0.9815, 1.0107, 0.4075, 3},
            {1.0107, 1.02, 0.4086, 0}
    };

    private static final double[][] CITY_NEAR = {
            {0.5, 0.5491, 0.466, 0},
            {0.5491, 0.5869, 0.4705, 2},
            {0.5869, 0.6417, 0.4493, 0},
            {0.6417, 0.6891, 0.4574, 1},
            {0.6891, 0.7259, 0.4825, 1},
            {0.7259, 0.7631, 0.4791, 0},
            {0.7631, 0.8093, 0.4596, 0},
            {0.8093, 0.8434, 0.4979, 1},
            {0.8434, 0.8825, 0.4403, 3},
            {0.8825, 0.9302, 0.4784, 0},
            {0.9302, 0.9563, 0.4427, 0},
            {0.9563, 1.001, 0.4894, 0},
            {1.001, 1.02, 0.4813, 2}
    };

    private final int width;
    private final int height;

    public WindowView(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g) {
        drawWindowView(g);
        drawWindow(g);
    }

    private double topBarY(double u) {
        return TOP_BAR_Y0 + TOP_BAR_SLOPE * (u - (690.0 / 1280.0));
    }

    private double glassBottomY(double u) {
        return GLASS_BOT_REF_Y + GLASS_BOT_SLOPE * (u - GLASS_BOT_REF_U);
    }

    private void fillQuad(Graphics2D g, double u1, double y1, double u2, double y2, double h) {
        Path2D p = new Path2D.Double();
        p.moveTo(u1 * width, y1 * height);
        p.lineTo(u2 * width, y2 * height);
        p.lineTo(u2 * width, (y2 + h) * height);
        p.lineTo(u1 * width, (y1 + h) * height);
        p.closePath();
        g.fill(p);
    }

    /** Рама окна: верхняя и нижняя планки, импосты и боковой наличник. */
    private void drawWindow(Graphics2D g) {
        g.setColor(WINDOW_FRAME_COLOR);
        fillQuad(g, TOP_BAR_U0, topBarY(TOP_BAR_U0), 1.0, topBarY(1.0), TOP_BAR_H);
        fillQuad(g, LEFT_FRAME_U0, glassBottomY(LEFT_FRAME_U0), 1.0, glassBottomY(1.0), BOTTOM_BAR_H);

        g.fillRect((int) (LEFT_FRAME_U0 * width), 0,
                (int) ((LEFT_FRAME_U1 - LEFT_FRAME_U0) * width),
                (int) ((glassBottomY(LEFT_FRAME_U0) + BOTTOM_BAR_H) * height));
        g.fillRect((int) (MULLION1_U0 * width), 0,
                (int) ((MULLION1_U1 - MULLION1_U0) * width),
                (int) ((glassBottomY((MULLION1_U0 + MULLION1_U1) / 2) + BOTTOM_BAR_H) * height));
        g.fillRect((int) (MULLION2_U0 * width), 0,
                (int) ((MULLION2_U1 - MULLION2_U0) * width),
                (int) ((glassBottomY((MULLION2_U0 + MULLION2_U1) / 2) + BOTTOM_BAR_H) * height));
    }

    /** Полигон стекла: вся область, куда может попасть пейзаж. */
    private Polygon glassPolygon() {
        Polygon glass = new Polygon();
        glass.addPoint((int) (LEFT_FRAME_U1 * width), 0);
        glass.addPoint(width, 0);
        glass.addPoint(width, (int) (glassBottomY(1.0) * height));
        glass.addPoint((int) (LEFT_FRAME_U1 * width), (int) (glassBottomY(LEFT_FRAME_U1) * height));
        return glass;
    }

    /**
     * Ночной вид за окном: небо, луна, звёзды, две падающие звезды и город.
     * Всё рисуется внутри стекла, рама окна поверх (см. drawWindow).
     */
    private void drawWindowView(Graphics2D g) {
        Shape oldClip = g.getClip();
        g.clip(glassPolygon());
        drawSky(g);
        drawMoon(g);
        drawStars(g);
        drawCity(g);
        g.setClip(oldClip);
    }

    /** Небо: вертикальный градиент от тёмно-синего к горизонту. */
    private void drawSky(Graphics2D g) {
        g.setPaint(new GradientPaint(0, 0, SKY_TOP_COLOR, 0, (float) (SKY_HORIZON_V * height), SKY_BOTTOM_COLOR));
        g.fillRect(0, 0, width, height);
    }

    /** Луна с мягким свечением вокруг. */
    private void drawMoon(Graphics2D g) {
        double cx = MOON_U * width, cy = MOON_V * height;
        double r = MOON_R * width;

        g.setPaint(new RadialGradientPaint((float) cx, (float) cy, (float) (r * 2.4),
                new float[]{0f, 1f},
                new Color[]{new Color(254, 215, 156, 80), new Color(254, 215, 156, 0)}));
        g.fill(new Ellipse2D.Double(cx - r * 2.4, cy - r * 2.4, r * 4.8, r * 4.8));

        g.setColor(MOON_COLOR);
        g.fill(new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r));
    }

    /** Мелкие звёзды и крупные искорки. */
    private void drawStars(Graphics2D g) {
        Random rnd = new Random(20161021L);
        g.setColor(STAR_COLOR);
        for (int i = 0; i < STARS_COUNT; i++) {
            double u = 0.52 + rnd.nextDouble() * 0.48;
            double v = 0.01 + rnd.nextDouble() * (SKY_HORIZON_V - 0.02);
            double x = u * width, y = v * height;
            if (Math.hypot(x - MOON_U * width, y - MOON_V * height) < MOON_R * width * 1.7) {
                continue;
            }
            double r = (0.0010 + rnd.nextDouble() * 0.0018) * width;
            g.fill(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
        }
    }

    /** Город: три слоя силуэтов, на среднем и ближнем светятся окна. */
    private void drawCity(Graphics2D g) {
        drawCityLayer(g, CITY_FAR, CITY_FAR_COLOR);
        drawCityLayer(g, CITY_MID, CITY_MID_COLOR);
        drawLitWindows(g, CITY_MID, 17);
        drawCityLayer(g, CITY_NEAR, CITY_NEAR_COLOR);
        drawLitWindows(g, CITY_NEAR, 91);
    }

    /** Один слой силуэтов домов. */
    private void drawCityLayer(Graphics2D g, double[][] buildings, Color color) {
        g.setColor(color);
        for (double[] b : buildings) {
            double x0 = b[0] * width, x1 = b[1] * width;
            double yTop = b[2] * height;
            double bw = x1 - x0;

            g.fill(new Rectangle2D.Double(x0, yTop, bw, height - yTop));

            int type = (int) b[3];
            if (type == 1) {
                g.fill(new Rectangle2D.Double(x0 + bw * 0.28, yTop - 0.014 * height, bw * 0.44, 0.014 * height));
            } else if (type == 2) {
                g.fill(new Rectangle2D.Double(x0 + bw * 0.32, yTop - 0.032 * height, bw * 0.36, 0.032 * height));
            } else if (type == 3) {
                g.fill(new Rectangle2D.Double(x0 + bw * 0.48, yTop - 0.030 * height, Math.max(1.0, bw * 0.04), 0.030 * height));
            }
        }
    }

    /** Светящиеся и тёмные окна домов. */
    private void drawLitWindows(Graphics2D g, double[][] buildings, int seed) {
        double ww = 0.0086 * width, wh = 0.0177 * height;
        for (int i = 0; i < buildings.length; i++) {
            double[] b = buildings[i];
            double x0 = b[0] * width, x1 = b[1] * width, yTop = b[2] * height;

            Random rnd = new Random(seed * 131L + i * 17L);
            int cols = (int) Math.floor((x1 - x0 - ww) / (ww * 1.7));
            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < 10; r++) {
                    double wy = yTop + wh * 0.7 + r * wh * 2.1;
                    if (wy + wh > height) {
                        break;
                    }
                    if (rnd.nextDouble() > 0.34) {
                        continue;
                    }
                    double wx = x0 + ww * 0.45 + c * ww * 1.7;
                    if (wx + ww > x1 - ww * 0.25) {
                        continue;
                    }
                    g.setColor(rnd.nextDouble() < 0.3 ? CITY_LIT_BRIGHT_COLOR : CITY_LIT_COLOR);
                    g.fill(new Rectangle2D.Double(wx, wy, ww, wh));
                }
            }

            Random dark = new Random(seed * 977L + i);
            for (int k = 0; k < 4; k++) {
                double wx = x0 + dark.nextDouble() * Math.max(1.0, x1 - x0 - ww);
                double wy = yTop + wh * 0.7 + dark.nextInt(5) * wh * 2.1;
                if (wx + ww > x1 || wy + wh > height) {
                    continue;
                }
                g.setColor(CITY_DARK_WINDOW_COLOR);
                g.fill(new Rectangle2D.Double(wx, wy, ww, wh));
            }
        }
    }
}
