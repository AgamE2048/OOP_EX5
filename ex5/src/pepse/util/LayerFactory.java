package pepse.util;

import danogl.collisions.Layer;

public class LayerFactory {
    private static final String SKY = "sky";
    private static final String TERRAIN = "ground";
    private static final String NIGHT = "night";
    private static final String SUN = "sun";

    public int chooseLayer(String obj){
        switch(obj){
            case SKY:
                return Layer.BACKGROUND;

            case TERRAIN:
                return Layer.STATIC_OBJECTS;

            case NIGHT:
                return Layer.FOREGROUND;

            case SUN:
                return Layer.BACKGROUND + 1;
        }

        return 0;
    }
}
