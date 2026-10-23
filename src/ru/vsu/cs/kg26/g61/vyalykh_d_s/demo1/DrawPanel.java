package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class DrawPanel extends JPanel {

    private static final Color WALL_COLOR = new Color(24, 41, 85);
    private static final Color SIDE_WALL_COLOR = new Color(17, 30, 64);
    private static final Color FLOOR_COLOR = new Color(85, 48, 42);
    private static final Color WINDOW_FRAME_COLOR = new Color(8, 17, 46);
    private static final Color WINDOW_GLASS_COLOR = new Color(2, 34, 95);

    private static final double CORNER_U = 0.477;
    private static final double CORNER_FLOOR_Y = 0.699;
    private static final double SIDE_FLOOR_Y = 0.829;
    private static final double BACK_FLOOR_Y = 0.7635;

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

    // Темно-синий цвет одеяла (взят с картинки)
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

    private static final Color NIGHTSTAND_TOP_COLOR = new Color(150, 88, 62);
    private static final Color NIGHTSTAND_FRONT_COLOR = new Color(100, 55, 40);
    private static final Color NIGHTSTAND_SIDE_COLOR = new Color(74, 40, 31);
    private static final Color DRAWER_COLOR = new Color(130, 75, 55);

    private static final Color POT_COLOR = new Color(180, 90, 50);
    private static final Color POT_SIDE_COLOR = new Color(140, 70, 40);
    private static final Color SOIL_COLOR = new Color(58, 42, 30);
    private static final Color STEM_COLOR = new Color(40, 100, 40);

    // ==============================================================
    //  НОЧНОЙ ПЕЙЗАЖ ЗА ОКНОМ
    // ==============================================================
    private static final Color SKY_TOP_COLOR = WINDOW_GLASS_COLOR;
    private static final Color SKY_BOTTOM_COLOR = new Color(3, 45, 122);
    private static final Color MOON_COLOR = new Color(254, 215, 156);
    private static final Color STAR_COLOR = new Color(248, 248, 235);
    private static final Color SPARK_COLOR = new Color(255, 246, 214);
    private static final Color SHOOTING_COLOR = new Color(255, 238, 202);

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

    // ==============================================================
    //  КОВЁР
    // ==============================================================
    private static final Color CARPET_COLOR = new Color(25, 40, 85);
    private static final Color CARPET_BORDER_COLOR = new Color(48, 64, 128);

    private static final double CARPET_X0 = 0.0;
    private static final double CARPET_X1 = 0.30;
    private static final double CARPET_Y0 = -0.90;
    private static final double CARPET_Y1 = -0.15;

    // ==============================================================
    //  Единая перспективная модель комнаты.
    //  Мировые координаты: X - вдоль задней стены (0 - угол комнаты),
    //  Y - глубина (0 - задняя стена, отрицательные значения - к зрителю),
    //  H - высота над полом.
    //  Константы подобраны так, чтобы проекция точно совпадала
    //  с уже нарисованными стенами, полом и подоконником окна.
    // ==============================================================
    private static final double CAM_X = 1.0;
    private static final double CAM_Y = -1.477573;
    private static final double CAM_Z = 1.0;
    private static final double F_U = 1.25216;
    private static final double F_V = 0.411266;
    private static final double SIN_A = -0.5434;
    private static final double COS_A = 0.8355;
    private static final double VH = 0.46767;

    // Единый горизонтальный сдвиг всей мебели (кровати, тумбочки и растения)
    // вправо вдоль задней стены: конструкция уходит за правую границу кадра.
    // Применяется в project(), поэтому все части мебели смещаются согласованно.
    private static final double FURNITURE_SHIFT_X = 0.09;

    // Тумбочка: стоит у задней стены под окном, левее изголовья кровати
    private static final double NS_X0 = 0.36;
    private static final double NS_X1 = 0.52;
    private static final double NS_Y_FRONT = -0.12;
    private static final double NS_Y_BACK = 0.0;
    private static final double NS_H = 0.42;

    // Горшок с растением на тумбочке
    private static final double POT_X0 = 0.38;
    private static final double POT_X1 = 0.43;
    private static final double POT_Y0 = -0.075;
    private static final double POT_Y1 = -0.03;
    private static final double POT_H = 0.075;

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

    private final MainWindow mainWindow;

    public DrawPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        mainWindow.drawScene(g2);
    }

    private double topBarY(double u) {
        return TOP_BAR_Y0 + TOP_BAR_SLOPE * (u - (690.0 / 1280.0));
    }

    private double glassBottomY(double u) {
        return GLASS_BOT_REF_Y + GLASS_BOT_SLOPE * (u - GLASS_BOT_REF_U);
    }

    private void fillQuad(Graphics2D g, double u1, double y1, double u2, double y2, double h) {
        int w = getWidth(), hgt = getHeight();
        Path2D p = new Path2D.Double();
        p.moveTo(u1 * w, y1 * hgt);
        p.lineTo(u2 * w, y2 * hgt);
        p.lineTo(u2 * w, (y2 + h) * hgt);
        p.lineTo(u1 * w, (y1 + h) * hgt);
        p.closePath();
        g.fill(p);
    }

    // ==============================================================
    //  ПЕРСПЕКТИВНЫЕ ХЕЛПЕРЫ
    // ==============================================================

    /** Проекция мировой точки (x, y, h) в нормированные координаты экрана (u, v).
     *  Метод используется только для мебели, поэтому здесь же применяется
     *  общий горизонтальный сдвиг FURNITURE_SHIFT_X. */
    private double[] project(double x, double y, double h) {
        double xs = x + FURNITURE_SHIFT_X;
        double d = SIN_A * (xs - CAM_X) + COS_A * (y - CAM_Y);
        double r = COS_A * (xs - CAM_X) - SIN_A * (y - CAM_Y);
        return new double[]{0.5 + F_U * r / d, VH + F_V * (CAM_Z - h) / d};
    }

    /** Многоугольник по мировым точкам вида {x, y, h}. */
    private Polygon worldPolygon(double[][] pts) {
        int w = getWidth(), hgt = getHeight();
        Polygon poly = new Polygon();
        for (double[] pt : pts) {
            double[] s = project(pt[0], pt[1], pt[2]);
            poly.addPoint((int) Math.round(s[0] * w), (int) Math.round(s[1] * hgt));
        }
        return poly;
    }

    /** Заливка многоугольника по мировым точкам вида {x, y, h}. */
    private void fillWorld(Graphics2D g, Color color, double[][] pts) {
        g.setColor(color);
        g.fill(worldPolygon(pts));
    }

    /**
     * Объёмный параллелепипед в перспективе.
     * x0 < x1 - ширина, y0 - ближний к зрителю край, y1 - дальний край,
     * h0 - низ, h1 - верх. Камера находится справа, поэтому видны
     * верхняя грань, ближняя (по Y) грань и правая (по X) грань.
     */
    private void fillBox(Graphics2D g, double x0, double x1, double y0, double y1,
                         double h0, double h1, Color top, Color front, Color side) {
        fillWorld(g, top, new double[][]{{x0, y0, h1}, {x1, y0, h1}, {x1, y1, h1}, {x0, y1, h1}});
        fillWorld(g, front, new double[][]{{x0, y0, h0}, {x1, y0, h0}, {x1, y0, h1}, {x0, y0, h1}});
        fillWorld(g, side, new double[][]{{x1, y0, h0}, {x1, y1, h0}, {x1, y1, h1}, {x1, y0, h1}});
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

    // ==============================================================
    //  РАСТЕНИЕ НА ТУМБОЧКЕ
    // ==============================================================

    /** Три близких оттенка зелёного, чтобы листья читались по отдельности. */
    private static final Color[] LEAF_COLORS = {
            new Color(60, 140, 60),
            new Color(46, 112, 52),
            new Color(94, 172, 82)
    };

    /** Высота растения над землёй в горшке. */
    private static final double PLANT_HEIGHT = 0.30;

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
        int w = getWidth(), h = getHeight();
        double[] a = project(x, y, baseH);
        double[] m = project(x, y, baseH + plantH * 0.52);
        double[] b = project(x, y, baseH + plantH);
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
    private void drawPlant(Graphics2D g, double x, double y, double baseH, double plantH) {
        double[] stem = plantStemCurve(x, y, baseH, plantH);
        double stemLen = Math.hypot(stem[4] - stem[0], stem[5] - stem[1]);
        double[][] branches = plantBranchCurves(stem, stemLen);

        drawPlantStem(g, stem, Math.max(1.5, stemLen * 0.055));
        drawPlantBranches(g, branches, Math.max(1.0, stemLen * 0.035));
        drawPlantLeaves(g, stem, branches, stemLen);
        g.setStroke(new BasicStroke(1f));
    }

    public void drawBackWall(Graphics2D g) {
        int w = getWidth(), h = getHeight();
        g.setColor(WALL_COLOR);
        g.fillRect(0, 0, w, h);
        Polygon backWall = new Polygon();
        backWall.addPoint((int) (CORNER_U * w), 0);
        backWall.addPoint(w, 0);
        backWall.addPoint(w, (int) (BACK_FLOOR_Y * h));
        backWall.addPoint((int) (CORNER_U * w), (int) (CORNER_FLOOR_Y * h));
        g.fillPolygon(backWall);
    }

    public void drawSideWall(Graphics2D g) {
        int w = getWidth(), h = getHeight();
        Polygon sideWall = new Polygon();
        sideWall.addPoint(0, 0);
        sideWall.addPoint((int) (CORNER_U * w), 0);
        sideWall.addPoint((int) (CORNER_U * w), (int) (CORNER_FLOOR_Y * h));
        sideWall.addPoint(0, (int) (SIDE_FLOOR_Y * h));
        g.setColor(SIDE_WALL_COLOR);
        g.fillPolygon(sideWall);
    }

    public void drawFloor(Graphics2D g) {
        int w = getWidth(), h = getHeight();
        Polygon floor = new Polygon();
        floor.addPoint(0, (int) (SIDE_FLOOR_Y * h));
        floor.addPoint((int) (CORNER_U * w), (int) (CORNER_FLOOR_Y * h));
        floor.addPoint(w, (int) (BACK_FLOOR_Y * h));
        floor.addPoint(w, h);
        floor.addPoint(0, h);
        g.setColor(FLOOR_COLOR);
        g.fillPolygon(floor);
    }

    /** Рама окна: верхняя и нижняя планки, импосты и боковой наличник. */
    public void drawWindow(Graphics2D g) {
        int w = getWidth(), h = getHeight();

        g.setColor(WINDOW_FRAME_COLOR);
        fillQuad(g, TOP_BAR_U0, topBarY(TOP_BAR_U0), 1.0, topBarY(1.0), TOP_BAR_H);
        fillQuad(g, LEFT_FRAME_U0, glassBottomY(LEFT_FRAME_U0), 1.0, glassBottomY(1.0), BOTTOM_BAR_H);

        g.fillRect((int) (LEFT_FRAME_U0 * w), 0,
                (int) ((LEFT_FRAME_U1 - LEFT_FRAME_U0) * w),
                (int) ((glassBottomY(LEFT_FRAME_U0) + BOTTOM_BAR_H) * h));
        g.fillRect((int) (MULLION1_U0 * w), 0,
                (int) ((MULLION1_U1 - MULLION1_U0) * w),
                (int) ((glassBottomY((MULLION1_U0 + MULLION1_U1) / 2) + BOTTOM_BAR_H) * h));
        g.fillRect((int) (MULLION2_U0 * w), 0,
                (int) ((MULLION2_U1 - MULLION2_U0) * w),
                (int) ((glassBottomY((MULLION2_U0 + MULLION2_U1) / 2) + BOTTOM_BAR_H) * h));
    }

    // ==============================================================
    //  НОЧНОЙ ПЕЙЗАЖ ЗА ОКНОМ
    // ==============================================================

    /** Полигон стекла: вся область, куда может попасть пейзаж. */
    private Polygon glassPolygon() {
        int w = getWidth(), h = getHeight();
        Polygon glass = new Polygon();
        glass.addPoint((int) (LEFT_FRAME_U1 * w), 0);
        glass.addPoint(w, 0);
        glass.addPoint(w, (int) (glassBottomY(1.0) * h));
        glass.addPoint((int) (LEFT_FRAME_U1 * w), (int) (glassBottomY(LEFT_FRAME_U1) * h));
        return glass;
    }

    /**
     * Ночной вид за окном: небо, луна, звёзды, две падающие звезды и город.
     * Всё рисуется внутри стекла, рама окна поверх (см. drawWindow).
     */
    public void drawWindowView(Graphics2D g) {
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
        int w = getWidth(), h = getHeight();
        g.setPaint(new GradientPaint(0, 0, SKY_TOP_COLOR, 0, (float) (SKY_HORIZON_V * h), SKY_BOTTOM_COLOR));
        g.fillRect(0, 0, w, h);
    }

    /** Луна с мягким свечением вокруг. */
    private void drawMoon(Graphics2D g) {
        int w = getWidth(), h = getHeight();
        double cx = MOON_U * w, cy = MOON_V * h;
        double r = MOON_R * w;

        g.setPaint(new RadialGradientPaint((float) cx, (float) cy, (float) (r * 2.4),
                new float[]{0f, 1f},
                new Color[]{new Color(254, 215, 156, 80), new Color(254, 215, 156, 0)}));
        g.fill(new Ellipse2D.Double(cx - r * 2.4, cy - r * 2.4, r * 4.8, r * 4.8));

        g.setColor(MOON_COLOR);
        g.fill(new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r));
    }

    /** Мелкие звёзды и крупные искорки. */
    private void drawStars(Graphics2D g) {
        int w = getWidth(), h = getHeight();
        Random rnd = new Random(20161021L);
        g.setColor(STAR_COLOR);
        for (int i = 0; i < STARS_COUNT; i++) {
            double u = 0.52 + rnd.nextDouble() * 0.48;
            double v = 0.01 + rnd.nextDouble() * (SKY_HORIZON_V - 0.02);
            double x = u * w, y = v * h;
            if (Math.hypot(x - MOON_U * w, y - MOON_V * h) < MOON_R * w * 1.7) {
                continue;
            }
            double r = (0.0010 + rnd.nextDouble() * 0.0018) * w;
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
        int w = getWidth(), h = getHeight();
        g.setColor(color);
        for (double[] b : buildings) {
            double x0 = b[0] * w, x1 = b[1] * w;
            double yTop = b[2] * h;
            double bw = x1 - x0;

            g.fill(new Rectangle2D.Double(x0, yTop, bw, h - yTop));

            int type = (int) b[3];
            if (type == 1) {
                g.fill(new Rectangle2D.Double(x0 + bw * 0.28, yTop - 0.014 * h, bw * 0.44, 0.014 * h));
            } else if (type == 2) {
                g.fill(new Rectangle2D.Double(x0 + bw * 0.32, yTop - 0.032 * h, bw * 0.36, 0.032 * h));
            } else if (type == 3) {
                g.fill(new Rectangle2D.Double(x0 + bw * 0.48, yTop - 0.030 * h, Math.max(1.0, bw * 0.04), 0.030 * h));
            }
        }
    }

    /** Светящиеся и тёмные окна домов. */
    private void drawLitWindows(Graphics2D g, double[][] buildings, int seed) {
        int w = getWidth(), h = getHeight();
        double ww = 0.0086 * w, wh = 0.0177 * h;
        for (int i = 0; i < buildings.length; i++) {
            double[] b = buildings[i];
            double x0 = b[0] * w, x1 = b[1] * w, yTop = b[2] * h;

            Random rnd = new Random(seed * 131L + i * 17L);
            int cols = (int) Math.floor((x1 - x0 - ww) / (ww * 1.7));
            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < 10; r++) {
                    double wy = yTop + wh * 0.7 + r * wh * 2.1;
                    if (wy + wh > h) {
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
                if (wx + ww > x1 || wy + wh > h) {
                    continue;
                }
                g.setColor(CITY_DARK_WINDOW_COLOR);
                g.fill(new Rectangle2D.Double(wx, wy, ww, wh));
            }
        }
    }

    // ==========================================
    // ТУМБОЧКА С РАСТЕНИЕМ (СТОИТ ЗА КРОВАТЬЮ, У ОКНА)
    // ==========================================
    public void drawNightstand(Graphics2D g) {
        int w = getWidth(), h = getHeight();

        // Корпус тумбочки: стоит у задней стены, правее её частично перекрывает кровать
        fillBox(g, NS_X0, NS_X1, NS_Y_FRONT, NS_Y_BACK, 0.0, NS_H,
                NIGHTSTAND_TOP_COLOR, NIGHTSTAND_FRONT_COLOR, NIGHTSTAND_SIDE_COLOR);

        // Ящик на передней грани
        double dx0 = NS_X0 + 0.022, dx1 = NS_X1 - 0.022;
        double dh0 = 0.17, dh1 = 0.345;
        fillWorld(g, DRAWER_COLOR, new double[][]{
                {dx0, NS_Y_FRONT - 0.002, dh0}, {dx1, NS_Y_FRONT - 0.002, dh0},
                {dx1, NS_Y_FRONT - 0.002, dh1}, {dx0, NS_Y_FRONT - 0.002, dh1}});

        // Ручка ящика
        double[] knob = project((dx0 + dx1) / 2, NS_Y_FRONT - 0.004, (dh0 + dh1) / 2);
        double kr = 0.006 * w;
        g.setColor(new Color(80, 45, 34));
        g.fill(new Ellipse2D.Double(knob[0] * w - kr, knob[1] * h - kr, 2 * kr, 2 * kr));

        // Горшок (сужается кверху) на верхней плоскости тумбочки
        double ix = (POT_X1 - POT_X0) * 0.10, iy = (POT_Y1 - POT_Y0) * 0.12;
        double tx0 = POT_X0 + ix, tx1 = POT_X1 - ix;
        double ty0 = POT_Y0 + iy, ty1 = POT_Y1 - iy;
        fillWorld(g, POT_COLOR, new double[][]{
                {POT_X0, POT_Y0, NS_H}, {POT_X1, POT_Y0, NS_H},
                {tx1, ty0, NS_H + POT_H}, {tx0, ty0, NS_H + POT_H}});
        fillWorld(g, POT_SIDE_COLOR, new double[][]{
                {POT_X1, POT_Y0, NS_H}, {POT_X1, POT_Y1, NS_H},
                {tx1, ty1, NS_H + POT_H}, {tx1, ty0, NS_H + POT_H}});

        // Растение: стебель уходит в землю, поэтому земля рисуется последней
        drawPlant(g, (POT_X0 + POT_X1) / 2, (POT_Y0 + POT_Y1) / 2, NS_H + POT_H, PLANT_HEIGHT);

        fillWorld(g, SOIL_COLOR, new double[][]{
                {tx0, ty0, NS_H + POT_H}, {tx1, ty0, NS_H + POT_H},
                {tx1, ty1, NS_H + POT_H}, {tx0, ty1, NS_H + POT_H}});
    }

    public void drawBed(Graphics2D g) {
        // Каркас (нижняя часть кровати) - тёмное дерево
        fillBox(g, BED_X0, BED_X1, BED_Y_FOOT, BED_Y_HEAD, 0.0, FRAME_H,
                BED_FRAME_TOP_COLOR, BED_FRAME_FRONT_COLOR, BED_FRAME_SIDE_COLOR);

        // Матрас с одеялом
        fillBox(g, BED_X0 + MAT_INSET, BED_X1, BED_Y_FOOT + MAT_INSET, BED_Y_HEAD - MAT_INSET,
                FRAME_H, MAT_TOP, BED_COLOR, MATTRESS_FRONT_COLOR, MATTRESS_SIDE_COLOR);

        // Блик на одеяле у изголовья
        fillWorld(g, BED_HIGHLIGHT, new double[][]{
                {BED_X0 + MAT_INSET, BED_Y_HEAD - MAT_INSET, MAT_TOP + 0.002},
                {BED_X1, BED_Y_HEAD - MAT_INSET, MAT_TOP + 0.002},
                {BED_X1, BED_Y_HEAD - MAT_INSET - 0.055, MAT_TOP + 0.002},
                {BED_X0 + MAT_INSET, BED_Y_HEAD - MAT_INSET - 0.055, MAT_TOP + 0.002}});

        // Подушки у изголовья (правый край уходит за границу кадра)
        for (double[] pillow : PILLOWS) {
            fillBox(g, pillow[0], pillow[1], PILLOW_Y0, PILLOW_Y1, MAT_TOP, MAT_TOP + PILLOW_H,
                    PILLOW_COLOR, PILLOW_FRONT_COLOR, PILLOW_SIDE_COLOR);
        }
    }

    // ==========================================
    // КОВЁР НА ПОЛУ
    // ==========================================
    public void drawCarpet(Graphics2D g) {
        fillWorld(g, CARPET_COLOR, new double[][]{
                {CARPET_X0, CARPET_Y0, 0.002}, {CARPET_X1, CARPET_Y0, 0.002},
                {CARPET_X1, CARPET_Y1, 0.002}, {CARPET_X0, CARPET_Y1, 0.002}});

        double ix = (CARPET_X1 - CARPET_X0) * 0.12;
        double iy = (CARPET_Y1 - CARPET_Y0) * 0.12;
        fillWorld(g, CARPET_BORDER_COLOR, new double[][]{
                {CARPET_X0 + ix, CARPET_Y0 + iy, 0.003}, {CARPET_X1 - ix, CARPET_Y0 + iy, 0.003},
                {CARPET_X1 - ix, CARPET_Y1 - iy, 0.003}, {CARPET_X0 + ix, CARPET_Y1 - iy, 0.003}});
    }

}
