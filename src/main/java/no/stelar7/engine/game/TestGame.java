package no.stelar7.engine.game;

import no.stelar7.engine.EngineUtils;
import no.stelar7.engine.rendering.Model;
import no.stelar7.engine.rendering.shaders.*;

import java.nio.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class TestGame implements Game
{
    
    // GL call log: https://hastebin.com/raw/foqahevigi
    
    private ShaderProgram shader;
    private Model         model;
    
    public TestGame()
    {
        Shader vertexShader = new Shader(GL_VERTEX_SHADER);
        vertexShader.setSource(EngineUtils.readShader("basic.vert"));
        vertexShader.compile();
        
        Shader fragmentShader = new Shader(GL_FRAGMENT_SHADER);
        fragmentShader.setSource(EngineUtils.readShader("basic.frag"));
        fragmentShader.compile();
    
        shader = new ShaderProgram(vertexShader, fragmentShader);
        model = new Model(FloatBuffer.wrap(new float[]{-1, -1, 0, 1, -1, 0, 0, 1, 0}), IntBuffer.wrap(new int[]{0, 1, 2}));
        
        glClearColor(.8f, 0, 0, 0);
        EngineUtils.log("glClearColor(%s, %s, %s, %s)", .8f, 0, 0, 0);
    }
    
    private float scale = 1;
    
    @Override
    public void update()
    {
        shader.bind();
        scale += 0.01f;
        shader.setUniform1f("scale", (float) Math.sin(scale));
        shader.unbind();
    }
    
    
    private int counter;
    
    @Override
    public void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        EngineUtils.log("glClear(%s | %s)", EngineUtils.glTypeToString(GL_COLOR_BUFFER_BIT), EngineUtils.glTypeToString(GL_DEPTH_BUFFER_BIT));
        
        shader.bind();
        model.bind();
        
        
        counter = ++counter % 3;
        
        // This doesnt work on version 3.3 Core, so use an invalid counter
        if (counter == 5)
        {
            // This call works
            glBegin(GL_TRIANGLES);
            glVertex3f(-1, -1, 0);
            glVertex3f(1, -1, 0);
            glVertex3f(0, 1, 0);
            glEnd();
            
            EngineUtils.log("glBegin(GL_TRIANGLES)");
            EngineUtils.log("glVertex3f(-1, -1, 0)");
            EngineUtils.log("glVertex3f(1, -1, 0)");
            EngineUtils.log("glVertex3f(0, 1, 0)");
            EngineUtils.log("glEnd()");
        }
        
        
        if (counter == 1)
        {
            // This call does nothing?
            glDrawArrays(GL_TRIANGLES, 0, 3);
            EngineUtils.log("glDrawArrays(%s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), 0, 3);
        }
        
        if (counter == 2)
        {
            // This call does nothing?
            glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);
            EngineUtils.log("glDrawElements(%s, %s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), 3, EngineUtils.glTypeToString(GL_UNSIGNED_INT), 0);
        }
        
        
        model.unbind();
        shader.unbind();
    }
    
    @Override
    public void delete()
    {
        model.delete();
        shader.delete();
    }
}
