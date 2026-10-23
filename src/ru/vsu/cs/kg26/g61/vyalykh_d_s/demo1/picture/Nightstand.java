package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture;

import ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.utils.Perspective;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Nightstand {

    private static final Color NIGHTSTAND_TOP_COLOR = new Color(150, 88, 62);
    private static final Color NIGHTSTAND_FRONT_COLOR = new Color(100, 55, 40);
    private static final Color NIGHTSTAND_SIDE_COLOR = new Color(74, 40, 31);
    private static final Color DRAWER_COLOR = new Color(130, 75, 55);

    private static final Color POT_COLOR = new Color(180, 90, 50);
    private static final Color POT_SIDE_COLOR = new Color(140, 70, 40);
    private static final Color SOIL_COLOR = new Color(58, 42, 30);

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

    /** Высота растения над землёй в горшке. */
    private static final double PLANT_HEIGHT = 0.30;

    private final Perspective perspective;
    private final Plant plant;

    public Nightstand(Perspective perspective) {
        this.perspective = perspective;
        this.plant = new Plant(perspective);
    }

    public void draw(Graphics2D g) {
        int w = perspective.getWidth(), h = perspective.getHeight();

        // Корпус тумбочки: стоит у задней стены, правее её частично перекрывает кровать
        perspective.fillBox(g, NS_X0, NS_X1, NS_Y_FRONT, NS_Y_BACK, 0.0, NS_H,
                NIGHTSTAND_TOP_COLOR, NIGHTSTAND_FRONT_COLOR, NIGHTSTAND_SIDE_COLOR);

        // Ящик на передней грани
        double dx0 = NS_X0 + 0.022, dx1 = NS_X1 - 0.022;
        double dh0 = 0.17, dh1 = 0.345;
        perspective.fillWorld(g, DRAWER_COLOR, new double[][]{
                {dx0, NS_Y_FRONT - 0.002, dh0}, {dx1, NS_Y_FRONT - 0.002, dh0},
                {dx1, NS_Y_FRONT - 0.002, dh1}, {dx0, NS_Y_FRONT - 0.002, dh1}});

        // Ручка ящика
        double[] knob = perspective.project((dx0 + dx1) / 2, NS_Y_FRONT - 0.004, (dh0 + dh1) / 2);
        double kr = 0.006 * w;
        g.setColor(new Color(80, 45, 34));
        g.fill(new Ellipse2D.Double(knob[0] * w - kr, knob[1] * h - kr, 2 * kr, 2 * kr));

        // Горшок (сужается кверху) на верхней плоскости тумбочки
        double ix = (POT_X1 - POT_X0) * 0.10, iy = (POT_Y1 - POT_Y0) * 0.12;
        double tx0 = POT_X0 + ix, tx1 = POT_X1 - ix;
        double ty0 = POT_Y0 + iy, ty1 = POT_Y1 - iy;
        perspective.fillWorld(g, POT_COLOR, new double[][]{
                {POT_X0, POT_Y0, NS_H}, {POT_X1, POT_Y0, NS_H},
                {tx1, ty0, NS_H + POT_H}, {tx0, ty0, NS_H + POT_H}});
        perspective.fillWorld(g, POT_SIDE_COLOR, new double[][]{
                {POT_X1, POT_Y0, NS_H}, {POT_X1, POT_Y1, NS_H},
                {tx1, ty1, NS_H + POT_H}, {tx1, ty0, NS_H + POT_H}});

        // Растение: стебель уходит в землю, поэтому земля рисуется последней
        plant.draw(g, (POT_X0 + POT_X1) / 2, (POT_Y0 + POT_Y1) / 2, NS_H + POT_H, PLANT_HEIGHT);

        perspective.fillWorld(g, SOIL_COLOR, new double[][]{
                {tx0, ty0, NS_H + POT_H}, {tx1, ty0, NS_H + POT_H},
                {tx1, ty1, NS_H + POT_H}, {tx0, ty1, NS_H + POT_H}});
    }
}
