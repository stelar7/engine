package no.stelar7.engine.rendering;

import no.stelar7.engine.rendering.buffers.*;

import java.nio.*;

import static org.lwjgl.opengl.GL15.*;

public class Model
{
    
    private final VertexArrayObject  vao = new VertexArrayObject();
    private final VertexBufferObject vbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    private final VertexBufferObject ibo = new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER);
    
    public Model(FloatBuffer vert, IntBuffer ind)
    {
        vao.generate();
        vbo.generate();
        ibo.generate();
        
        vao.bind();
        
        vbo.bind();
        vbo.setData(vert);
        vao.enableAttribIndex(0);
        vao.setPointer(0, 3);
        
        ibo.bind();
        ibo.setData(ind);
    }
    
    public void bind()
    {
        vao.bind();
    }
    
    public void unbind()
    {
        vao.unbind();
    }
    
    public void delete()
    {
        ibo.delete();
        vbo.delete();
        vao.delete();
    }
}
