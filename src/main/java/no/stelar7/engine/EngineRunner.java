package no.stelar7.engine;

import no.stelar7.engine.game.*;
import no.stelar7.engine.handlers.*;
import org.joml.Vector2f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

public class EngineRunner
{
    private int width  = 800;
    private int height = 600;
    
    private final Vector2f cursor = new Vector2f();
    private final Object   lock   = new Object();
    
    private long    window;
    private boolean shouldClose;
    
    public static final boolean DEBUG_MODE = true;
    
    private Game game;
    
    public static void main(String[] args)
    {
        new EngineRunner().run();
    }
    
    public void run()
    {
        try
        {
            init();
            
            new Thread(this::loop).start();
            
            while (!shouldClose)
            {
                glfwWaitEvents();
            }
            
            synchronized (lock)
            {
                glfwFreeCallbacks(window);
                glfwDestroyWindow(window);
            }
        } finally
        {
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }
    
    private void init()
    {
        GLFWErrorCallback.createPrint(System.err).set();
        
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        
        // 4xMSAA
        glfwWindowHint(GLFW_SAMPLES, 4);
        
        // OpenGL 3.3.Core
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        
        window = glfwCreateWindow(width, height, "Basic EngineRunner!", NULL, NULL);
        if (window == NULL)
        {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        glfwSetCursorPosCallback(window, new MousePosHandler());
        glfwSetMouseButtonCallback(window, new MouseButtonHandler());
        glfwSetKeyCallback(window, new KeyboardHandler());
        
        glfwSetFramebufferSizeCallback(window, (windowPtr, w, h) ->
                                       {
                                           if (w > 0 && h > 0)
                                           {
                                               width = w;
                                               height = h;
                                           }
                                       }
                                      );
        
        
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - width) / 2);
        
        
        glfwShowWindow(window);
    }
    
    private void loop()
    {
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        
        
        System.out.println("OS name " + System.getProperty("os.name"));
        System.out.println("OS version " + System.getProperty("os.version"));
        System.out.println("OpenGL version " + glGetString(GL_VERSION));
        
        glfwSwapInterval(0);
        glViewport(0, 0, width, height);
        glEnable(GL_MULTISAMPLE);
        
        
        //glEnable(GL_DEPTH_TEST);
        //glDepthFunc(GL_LEQUAL);
        
        
        initPostGL();
        
        int   updatesPerSecond = 60;
        int   maxFramesSkipped = 20;
        float skipInterval     = 1000f / updatesPerSecond;
        
        int ups = 0;
        int fps = 0;
        
        int loops;
        
        double timer    = System.currentTimeMillis();
        long   fpstimer = System.currentTimeMillis();
        
        
        while (!shouldClose)
        {
            
            if (System.currentTimeMillis() > fpstimer + 1000)
            {
                System.out.format("fps: %d  ups: %d%n", fps, ups);
                fpstimer = System.currentTimeMillis();
                fps = ups = 0;
            }
            
            loops = 0;
            while (System.currentTimeMillis() > timer && loops < maxFramesSkipped)
            {
                update();
                loops++;
                ups++;
                timer += skipInterval;
            }
            
            render();
            fps++;
            
            synchronized (lock)
            {
                shouldClose = glfwWindowShouldClose(window);
                
                if (shouldClose)
                {
                    game.delete();
                    return;
                }
                
                int status;
                while ((status = glGetError()) != GL_NO_ERROR)
                {
                    System.err.print("Error calling opengl function! Error code: ");
                    System.err.println(EngineUtils.glErrorToString(status));
                }
                
                glfwSwapBuffers(window);
            }
        }
    }
    
    private void initPostGL()
    {
        game = new TestGame();
    }
    
    private void update()
    {
        game.update();
    }
    
    private void render()
    {
        game.render();
    }
    
    
}
