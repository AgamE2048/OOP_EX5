package pepse.util;

@FunctionalInterface
public interface GroundHeight {
    /**
     *
     * @param x the x location we want to check
     * @return the y (height) of the ground at x
     */
    float groundHeightAt(float x);
}
