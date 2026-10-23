package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Perspective {

    private static final double CAM_X = 1.0;
    private static final double CAM_Y = -1.477573;
    private static final double CAM_Z = 1.0;
    private static final double F_U = 1.25216;
    private static final double F_V = 0.411266;
    private static final double SIN_A = -0.5434;
    private static final double COS_A = 0.8355;
    private static final double VH = 0.46767;

    private static final double FURNITURE_SHIFT_X = 0.09;

    private final int width;
    private final int height;

    public Perspective(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /** Проекция мировой точки (x, y, h) в нормированные координаты экрана (u, v). */
    public double[] project(double x, double y, double h) {
        double xs = x + FURNITURE_SHIFT_X;
        double d = SIN_A * (xs - CAM_X) + COS_A * (y - CAM_Y);
        double r = COS_A * (xs - CAM_X) - SIN_A * (y - CAM_Y);
        return new double[]{0.5 + F_U * r / d, VH + F_V * (CAM_Z - h) / d};
    }

    /** Многоугольник по мировым точкам вида {x, y, h}. */
    public Polygon worldPolygon(double[][] pts) {
        Polygon poly = new Polygon();
        for (double[] pt : pts) {
            double[] s = project(pt[0], pt[1], pt[2]);
            poly.addPoint((int) Math.round(s[0] * width), (int) Math.round(s[1] * height));
        }
        return poly;
    }

    /** Заливка многоугольника по мировым точкам вида {x, y, h}. */
    public void fillWorld(Graphics2D g, Color color, double[][] pts) {
        g.setColor(color);
        g.fill(worldPolygon(pts));
    }

    /**
     * Объёмный параллелепипед в перспективе.
     * x0 < x1 - ширина, y0 - ближний к зрителю край, y1 - дальний край,
     * h0 - низ, h1 - верх. Камера находится справа, поэтому видны
     * верхняя грань, ближняя (по Y) грань и правая (по X) грань.
     */
    public void fillBox(Graphics2D g, double x0, double x1, double y0, double y1,
                        double h0, double h1, Color top, Color front, Color side) {
        fillWorld(g, top, new double[][]{{x0, y0, h1}, {x1, y0, h1}, {x1, y1, h1}, {x0, y1, h1}});
        fillWorld(g, front, new double[][]{{x0, y0, h0}, {x1, y0, h0}, {x1, y0, h1}, {x0, y0, h1}});
        fillWorld(g, side, new double[][]{{x1, y0, h0}, {x1, y1, h0}, {x1, y1, h1}, {x1, y0, h1}});
    }
}
