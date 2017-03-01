package no.stelar7.engine.rendering;

import no.stelar7.engine.rendering.buffers.*;
import org.lwjgl.*;

import java.nio.*;

import static org.lwjgl.opengl.GL15.*;

public class Model
{
    
    private final VertexArrayObject  vao = new VertexArrayObject();
    private final VertexBufferObject vbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    private final VertexBufferObject ibo = new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER);
    
    private final int vertexCount;
    
    public Model(FloatBuffer vert, IntBuffer ind)
    {
        vao.generate();
        vbo.generate();
        ibo.generate();
        
        bind();
        
        setVertices(vert);
        setIndices(ind);
        
        vertexCount = ind.capacity();
        
        unbind();
    }
    
    public Model(float[] vert, int[] ind)
    {
        vao.generate();
        vbo.generate();
        ibo.generate();
        
        bind();
        
        FloatBuffer vbuff = BufferUtils.createFloatBuffer(vert.length);
        vbuff.put(vert);
        vbuff.flip();
        setVertices(vbuff);
        
        IntBuffer ibuff = BufferUtils.createIntBuffer(ind.length);
        ibuff.put(ind);
        ibuff.flip();
        setIndices(ibuff);
        
        vertexCount = ind.length;
        
        unbind();
    }
    
    private void setIndices(IntBuffer ind)
    {
        ibo.bind();
        ibo.setData(ind);
    }
    
    private void setVertices(FloatBuffer vert)
    {
        vbo.bind();
        vbo.setData(vert);
        vao.enableAttribIndex(0);
        vao.setPointer(0, 3);
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
        ibo.unbind();
        vbo.unbind();
        vao.unbind();
        
        ibo.delete();
        vbo.delete();
        vao.delete();
    }
    
    public int getVertexCount()
    {
        return vertexCount;
    }
}
