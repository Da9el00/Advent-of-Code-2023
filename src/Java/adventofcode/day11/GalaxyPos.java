package adventofcode.day11;

import java.util.ArrayList;

public class GalaxyPos {

    private int x;
    private int y;

    public GalaxyPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "GalaxyPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void expandPosition(ArrayList<Integer> expandPointsX, ArrayList<Integer> expandPointsY, int factor) {
        int xIncrease = expandPointsX.stream().filter(point -> point < x).toList().size();
        int yIncrease = expandPointsY.stream().filter(point -> point < y).toList().size();
        x += (xIncrease * (factor - 1));
        y += (yIncrease * (factor - 1));
    }
}
