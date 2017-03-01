package no.stelar7.engine.game;

import no.stelar7.engine.EngineUtils;
import no.stelar7.engine.rendering.Model;
import no.stelar7.engine.rendering.shaders.*;
import org.lwjgl.system.*;

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
        scale += 0.01f;
        
        shader.bind();
        shader.setUniform1f("scale", (float) Math.sin(scale));
    }
    
    
    @Override
    public void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        EngineUtils.log("glClear(%s | %s)", EngineUtils.glTypeToString(GL_COLOR_BUFFER_BIT), EngineUtils.glTypeToString(GL_DEPTH_BUFFER_BIT));
        
        shader.bind();
        model.bind();
        
        // These calls does nothing?
        //glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount());
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, MemoryUtil.NULL);
        EngineUtils.log("glDrawElements(%s, %s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), model.getVertexCount(), EngineUtils.glTypeToString(GL_UNSIGNED_INT), 0);
    }
    
    @Override
    public void delete()
    {
        model.delete();
        shader.delete();
    }
}
