package no.stelar7.engine.game;

import no.stelar7.engine.EngineUtils;
import no.stelar7.engine.rendering.buffers.*;
import no.stelar7.engine.rendering.shaders.*;

import java.nio.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;


public class TestGame implements Game
{
    
    private VertexArrayObject  vao = new VertexArrayObject();
    private VertexBufferObject vbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    private VertexBufferObject ibo = new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER);
    
    private ShaderProgram program;
    
    public TestGame()
    {
        EngineUtils.log("// Shader start");
        
        Shader vertexShader = new Shader(GL_VERTEX_SHADER);
        vertexShader.setSource(EngineUtils.readShader("basic.vert"));
        vertexShader.compile();
        
        Shader fragmentShader = new Shader(GL_FRAGMENT_SHADER);
        fragmentShader.setSource(EngineUtils.readShader("basic.frag"));
        fragmentShader.compile();
        
        EngineUtils.log("// Program start");
        
        program = new ShaderProgram(vertexShader, fragmentShader);
        
        EngineUtils.log("// VAO start");
        // bind VAO
        vao.generate();
        vao.bind();
        
        EngineUtils.log("// VBO start");
        // bind VBO
        vbo.generate();
        vbo.bind();
        vbo.setData(FloatBuffer.wrap(new float[]{-1, -1, 0, 1, -1, 0, 0, 1, 0}));
        
        EngineUtils.log("// Bind VAO and VBO start");
        // set pointers
        vao.enableAttribIndex(0);
        vao.setPointer(0, 3);
        
        EngineUtils.log("// IBO start");
        // bind IBO
        ibo.generate();
        ibo.bind();
        ibo.setData(IntBuffer.wrap(new int[]{0, 1, 2}));
        
        
        // unbind vao
        vao.unbind();
        
        glClearColor(.8f, 0, 0, 0);
        EngineUtils.log("glClearColor(%s, %s, %s, %s)", .8f, 0, 0, 0);
    }
    
    private float scale;
    
    @Override
    public void update()
    {
        EngineUtils.log("// Update start");
        program.bind();
        scale += 0.01f;
        program.setUniform1f("scale", (float) Math.sin(scale));
        program.unbind();
    }
    
    
    private int counter;
    
    @Override
    public void render()
    {
        EngineUtils.log("// Render start");
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        EngineUtils.log("glClear(%s | %s)", EngineUtils.glTypeToString(GL_COLOR_BUFFER_BIT), EngineUtils.glTypeToString(GL_DEPTH_BUFFER_BIT));
        
        // bind shader and VAO
        program.bind();
        vao.bind();
        
        // Render
        
        counter = ++counter % 3;
        
        if (counter == 0)
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
            glDrawArrays(GL_TRIANGLES, 0, 4);
            EngineUtils.log("glDrawArrays(%s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), 0, 4);
        }
        
        if (counter == 2)
        {
            // This call does nothing?
            glDrawElements(GL_TRIANGLES, 4, GL_UNSIGNED_INT, 0);
            EngineUtils.log("glDrawElements(%s, %s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), 4, EngineUtils.glTypeToString(GL_UNSIGNED_INT), 0);
        }
        
        
        // unbind shader and vao
        vao.unbind();
        program.unbind();
    }
    
    @Override
    public void delete()
    {
        ibo.delete();
        vbo.delete();
        vao.delete();
        program.delete();
    }
}
