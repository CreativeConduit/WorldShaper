package net.codedstingray.worldshaper.util.math;

public class MathUtils {

    public static int posMod(int number, int divider) {
        int ret = number % divider;
        return ret < 0 ? ret + divider : ret;
    }
}
