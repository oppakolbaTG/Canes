package net.oppakolba.canes.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_canes = "key.category.canes.oppaterra";
    public static final String KEY_MANA_USE = "key.canes.mana_use";
    public static final String KEY_OPEN_GUI = "key.canes.open_gui";

    public static final KeyMapping MANA_USING_KEY = new KeyMapping(KEY_MANA_USE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_canes);

    public static final KeyMapping OPENING_GUI_KEY = new KeyMapping(KEY_OPEN_GUI, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, KEY_CATEGORY_canes);


}
