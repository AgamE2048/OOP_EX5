package pepse.util;

import danogl.collisions.Layer;

public class LayerFactory {
    // Class Constants
    private static final String SKY = "sky";
    private static final String TERRAIN = "ground";
    private static final String NIGHT = "night";
    private static final String SUN = "sun";
    private static final String SUN_HALO = "sunHalo";
    private static final String TREE_TRUNKS = "tree";
    private static final String LEAVES = "leaf";
    private static final String OBJECTS = "gameObjects";
    private static final String UI = "UI";
    private static final String AVATAR = "avatar";

    /**
     *
     * @param obj a String whose layer we want
     * @returnthe layer we want to place obj at
     */
    public int chooseLayer(String obj){
        switch(obj){
            case SKY:
                return Layer.BACKGROUND;

            case SUN:
                return Layer.BACKGROUND + 1;

            case SUN_HALO:
                return Layer.BACKGROUND + 2;

            case TERRAIN:
                return Layer.STATIC_OBJECTS;

            case TREE_TRUNKS:
                return Layer.STATIC_OBJECTS + 10;

            case LEAVES:
                return Layer.STATIC_OBJECTS + 20;

            case OBJECTS:
                return Layer.STATIC_OBJECTS + 40;

            case NIGHT:
                return Layer.FOREGROUND;

            case UI:
                return Layer.UI;

            case AVATAR:
                return Layer.DEFAULT;

            default:
                return 0;

        }
    }
}
