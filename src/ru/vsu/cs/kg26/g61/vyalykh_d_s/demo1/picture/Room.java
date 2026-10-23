package ru.vsu.cs.kg26.g61.vyalykh_d_s.demo1.picture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Room {

    private static final Color WALL_COLOR = new Color(24, 41, 85);
    private static final Color SIDE_WALL_COLOR = new Color(17, 30, 64);
    private static final Color FLOOR_COLOR = new Color(85, 48, 42);

    private static final double CORNER_U = 0.477;
    private static final double CORNER_FLOOR_Y = 0.699;
    private static final double SIDE_FLOOR_Y = 0.829;
    private static final double BACK_FLOOR_Y = 0.7635;

    private final int width;
    private final int height;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g) {
        drawBackWall(g);
        drawSideWall(g);
        drawFloor(g);
    }

    private void drawBackWall(Graphics2D g) {
        g.setColor(WALL_COLOR);
        g.fillRect(0, 0, width, height);
        Polygon backWall = new Polygon();
        backWall.addPoint((int) (CORNER_U * width), 0);
        backWall.addPoint(width, 0);
        backWall.addPoint(width, (int) (BACK_FLOOR_Y * height));
        backWall.addPoint((int) (CORNER_U * width), (int) (CORNER_FLOOR_Y * height));
        g.fillPolygon(backWall);
    }

    private void drawSideWall(Graphics2D g) {
        Polygon sideWall = new Polygon();
        sideWall.addPoint(0, 0);
        sideWall.addPoint((int) (CORNER_U * width), 0);
        sideWall.addPoint((int) (CORNER_U * width), (int) (CORNER_FLOOR_Y * height));
        sideWall.addPoint(0, (int) (SIDE_FLOOR_Y * height));
        g.setColor(SIDE_WALL_COLOR);
        g.fillPolygon(sideWall);
    }

    private void drawFloor(Graphics2D g) {
        Polygon floor = new Polygon();
        floor.addPoint(0, (int) (SIDE_FLOOR_Y * height));
        floor.addPoint((int) (CORNER_U * width), (int) (CORNER_FLOOR_Y * height));
        floor.addPoint(width, (int) (BACK_FLOOR_Y * height));
        floor.addPoint(width, height);
        floor.addPoint(0, height);
        g.setColor(FLOOR_COLOR);
        g.fillPolygon(floor);
    }
}
