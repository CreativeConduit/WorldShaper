package net.codedstingray.worldshaper.area;

public class CuboidAreaFactory implements AreaFactory {
    @Override
    public Area create() {
        return new CuboidArea();
    }
}
