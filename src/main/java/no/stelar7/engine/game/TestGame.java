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
        Shader vertexShader = new Shader(GL_VERTEX_SHADER);
        vertexShader.setSource(EngineUtils.readShader("basic.vert"));
        vertexShader.compile();
        
        Shader fragmentShader = new Shader(GL_FRAGMENT_SHADER);
        fragmentShader.setSource(EngineUtils.readShader("basic.frag"));
        fragmentShader.compile();
        
        program = new ShaderProgram(vertexShader, fragmentShader);
        
        // bind VAO
        vao.generate();
        vao.bind();
        
        // bind IBO
        ibo.generate();
        ibo.bind();
        ibo.setData(IntBuffer.wrap(new int[]{0, 1, 2}));
        
        // bind VBO
        vbo.generate();
        vbo.bind();
        vbo.setData(FloatBuffer.wrap(new float[]{-1, -1, 0, 1, -1, 0, 0, 1, 0}));
        
        // set pointers
        vao.enableAttribIndex(0);
        vao.setPointer(0, 3);
        
        // unbind vao
        vao.unbind();
        
        glClearColor(.8f, 0, 0, 0);
        EngineUtils.log("glClearColor(%s, %s, %s, %s)", .8f, 0, 0, 0);
    }
    
    float scale = 0;
    
    @Override
    public void update()
    {
        program.bind();
        scale += 0.01f;
        program.setUniform1f("scale", (float) Math.sin(scale));
        program.unbind();
    }
    
    
    @Override
    public void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        EngineUtils.log("glClear(%s | %s)", EngineUtils.glTypeToString(GL_COLOR_BUFFER_BIT), EngineUtils.glTypeToString(GL_DEPTH_BUFFER_BIT));
        
        // bind shader and VAO
        program.bind();
        vao.bind();
        
        
        // Render
        
        // This call does nothing?
        glDrawArrays(GL_TRIANGLES, 0, 3);
        EngineUtils.log("glDrawArrays(%s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), 0, 3);
        
        // This call does nothing?
        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);
        EngineUtils.log("glDrawElements(%s, %s, %s, %s)", EngineUtils.glTypeToString(GL_TRIANGLES), 3, EngineUtils.glTypeToString(GL_UNSIGNED_INT), 0);

//        // This call works
        glBegin(GL_TRIANGLES);
        glVertex3f(-1, -1, 0);
        glVertex3f(1, -1, 0);
        glVertex3f(0, 1, 0);
        glEnd();
        
        
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
