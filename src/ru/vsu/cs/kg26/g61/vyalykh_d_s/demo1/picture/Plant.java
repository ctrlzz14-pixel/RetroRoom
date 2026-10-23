package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture;

import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.utils.Perspective;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class Plant {

    private static final Color STEM_COLOR = new Color(40, 100, 40);

    /** Три близких оттенка зелёного, чтобы листья читались по отдельности. */
    private static final Color[] LEAF_COLORS = {
            new Color(60, 140, 60),
            new Color(46, 112, 52),
            new Color(94, 172, 82)
    };

    /**
     * Боковые ветви: точка отрастания на главном стебле (0..1),
     * направление в экранных радианах (-PI/2 - вверх), длина относительно
     * длины стебля и количество листьев на ветви.
     */
    private static final double[][] PLANT_BRANCHES = {
            {0.22, -2.50, 0.50, 4},
            {0.36, -0.62, 0.55, 4},
            {0.50, -2.85, 0.45, 3},
            {0.62, -0.45, 0.48, 3},
            {0.75, -2.20, 0.38, 3},
            {0.88, -0.95, 0.34, 3}
    };

    private final Perspective perspective;

    public Plant(Perspective perspective) {
        this.perspective = perspective;
    }

    /** Лист растения: основание в точке (cx, cy), лист длиной len под углом angle. */
    private Path2D leafShape(double cx, double cy, double angle, double len, double wide) {
        double dx = Math.cos(angle), dy = Math.sin(angle);
        Path2D p = new Path2D.Double();
        p.moveTo(cx, cy);
        p.curveTo(cx + dx * len * 0.35 - dy * wide, cy + dy * len * 0.35 + dx * wide,
                cx + dx * len * 0.85 - dy * wide * 0.55, cy + dy * len * 0.85 + dx * wide * 0.55,
                cx + dx * len, cy + dy * len);
        p.curveTo(cx + dx * len * 0.85 + dy * wide * 0.55, cy + dy * len * 0.85 - dx * wide * 0.55,
                cx + dx * len * 0.35 + dy * wide, cy + dy * len * 0.35 - dx * wide,
                cx, cy);
        p.closePath();
        return p;
    }

    /** Точка квадратичной кривой Безье {x0,y0,cx,cy,x1,y1} для параметра t. */
    private static double[] bezierPoint(double[] c, double t) {
        double mt = 1 - t;
        return new double[]{
                mt * mt * c[0] + 2 * mt * t * c[2] + t * t * c[4],
                mt * mt * c[1] + 2 * mt * t * c[3] + t * t * c[5]};
    }

    /** Направление квадратичной кривой Безье в точке t. */
    private static double[] bezierTangent(double[] c, double t) {
        double mt = 1 - t;
        return new double[]{
                2 * mt * (c[2] - c[0]) + 2 * t * (c[4] - c[2]),
                2 * mt * (c[3] - c[1]) + 2 * t * (c[5] - c[3])};
    }

    /** Кривая главного стебля {x0,y0,cx,cy,x1,y1} в экранных координатах. */
    private double[] plantStemCurve(double x, double y, double baseH, double plantH) {
        int w = perspective.getWidth(), h = perspective.getHeight();
        double[] a = perspective.project(x, y, baseH);
        double[] m = perspective.project(x, y, baseH + plantH * 0.52);
        double[] b = perspective.project(x, y, baseH + plantH);
        double ax = a[0] * w, ay = a[1] * h;
        double mx = m[0] * w, my = m[1] * h;
        double bx = b[0] * w, by = b[1] * h;
        double len = Math.hypot(bx - ax, by - ay);
        // контрольная точка: стебель проходит через середину и слегка наклонён
        double cx = 2 * mx - 0.5 * (ax + bx) - 0.09 * len;
        double cy = 2 * my - 0.5 * (ay + by);
        return new double[]{ax, ay, cx, cy, bx, by};
    }

    /** Кривые боковых ветвей: {x0,y0,cx,cy,x1,y1, число листьев}. */
    private double[][] plantBranchCurves(double[] stem, double stemLen) {
        double[][] res = new double[PLANT_BRANCHES.length][];
        for (int i = 0; i < PLANT_BRANCHES.length; i++) {
            double[] b = PLANT_BRANCHES[i];
            double[] a = bezierPoint(stem, b[0]);
            double dx = Math.cos(b[1]), dy = Math.sin(b[1]);
            double len = b[2] * stemLen;
            double bend = 0.16 * len * (i % 2 == 0 ? 1 : -1);
            double px = -dy, py = dx;
            res[i] = new double[]{
                    a[0], a[1],
                    a[0] + dx * len * 0.5 + px * bend * 0.7, a[1] + dy * len * 0.5 + py * bend * 0.7,
                    a[0] + dx * len + px * bend, a[1] + dy * len + py * bend,
                    b[3]};
        }
        return res;
    }

    /** Главный стебель растения. */
    private void drawPlantStem(Graphics2D g, double[] stem, double thickness) {
        Path2D p = new Path2D.Double();
        p.moveTo(stem[0], stem[1]);
        p.quadTo(stem[2], stem[3], stem[4], stem[5]);
        g.setColor(STEM_COLOR);
        g.setStroke(new BasicStroke((float) thickness));
        g.draw(p);
    }

    /** Боковые ветви растения. */
    private void drawPlantBranches(Graphics2D g, double[][] branches, double thickness) {
        g.setColor(STEM_COLOR);
        g.setStroke(new BasicStroke((float) thickness));
        for (double[] br : branches) {
            Path2D p = new Path2D.Double();
            p.moveTo(br[0], br[1]);
            p.quadTo(br[2], br[3], br[4], br[5]);
            g.draw(p);
        }
    }

    /** Листья растения: на каждой ветви несколько листьев разного размера. */
    private void drawPlantLeaves(Graphics2D g, double[] stem, double[][] branches, double stemLen) {
        double leafBase = 0.36 * stemLen;
        int idx = 0;
        for (double[] br : branches) {
            int leaves = (int) br[6];
            for (int k = 0; k < leaves; k++) {
                double s = leaves < 2 ? 0.9 : 0.24 + 0.70 * k / (leaves - 1);
                double[] p = bezierPoint(br, s);
                double[] tan = bezierTangent(br, s);
                double dir = Math.atan2(tan[1], tan[0]);
                double spread = (k % 2 == 0 ? 1 : -1) * (0.50 + 0.35 * ((idx * 5) % 4) / 3.0);
                double size = leafBase * (0.62 + 0.50 * s) * (0.85 + 0.25 * ((idx * 3) % 5) / 4.0);
                drawLeaf(g, p[0], p[1], dir + spread, size, LEAF_COLORS[idx % LEAF_COLORS.length]);
                idx++;
            }
        }
        // листья прямо на главном стебле
        for (int k = 0; k < 3; k++) {
            double s = 0.18 + 0.22 * k;
            double[] p = bezierPoint(stem, s);
            double[] tan = bezierTangent(stem, s);
            double dir = Math.atan2(tan[1], tan[0]);
            double size = leafBase * (0.70 + 0.10 * k);
            drawLeaf(g, p[0], p[1], dir + (k % 2 == 0 ? 1 : -1) * 1.05, size,
                    LEAF_COLORS[idx % LEAF_COLORS.length]);
            idx++;
        }
        // пучок листьев на верхушке главного стебля
        double[] tip = bezierPoint(stem, 1.0);
        double[] tan = bezierTangent(stem, 1.0);
        double dir = Math.atan2(tan[1], tan[0]);
        for (int k = 0; k < 4; k++) {
            double size = leafBase * (1.00 - 0.13 * k);
            drawLeaf(g, tip[0], tip[1], dir + (k - 1.5) * 0.62, size,
                    LEAF_COLORS[(idx + k) % LEAF_COLORS.length]);
        }
    }

    /** Один лист: тонкий черешок от ветви и вытянутый лист. */
    private void drawLeaf(Graphics2D g, double cx, double cy, double angle, double len, Color color) {
        double dx = Math.cos(angle), dy = Math.sin(angle);
        double petiole = len * 0.28;
        double bx = cx + dx * petiole, by = cy + dy * petiole;
        g.setColor(STEM_COLOR);
        g.setStroke(new BasicStroke((float) Math.max(1.0, len * 0.05)));
        g.draw(new Line2D.Double(cx, cy, bx, by));
        g.setColor(color);
        g.fill(leafShape(bx, by, angle, len, len * 0.34));
    }

    /** Растение в горшке: главный стебель, боковые ветви и листья. */
    public void draw(Graphics2D g, double x, double y, double baseH, double plantH) {
        double[] stem = plantStemCurve(x, y, baseH, plantH);
        double stemLen = Math.hypot(stem[4] - stem[0], stem[5] - stem[1]);
        double[][] branches = plantBranchCurves(stem, stemLen);

        drawPlantStem(g, stem, Math.max(1.5, stemLen * 0.055));
        drawPlantBranches(g, branches, Math.max(1.0, stemLen * 0.035));
        drawPlantLeaves(g, stem, branches, stemLen);
        g.setStroke(new BasicStroke(1f));
    }
}
