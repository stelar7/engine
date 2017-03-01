package no.stelar7.engine.handlers;

import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

public final class InputHandler
{
    
    public static final boolean[] keys  = new boolean[GLFW_KEY_LAST];
    public static final Vector2f  mouse = new Vector2f();
    
    public static void setMousePosForWindow(double xpos, double ypos)
    {
        mouse.set((float) xpos, (float) ypos);
    }
    
    public static void setKeyForWindow(int key, int action)
    {
        keys[key] = action != GLFW_RELEASE;
    }
    
    public static Vector2f getMousePosition()
    {
        return mouse;
    }
    
    public static boolean isKeyDown(int key)
    {
        return keys[key];
    }
}
