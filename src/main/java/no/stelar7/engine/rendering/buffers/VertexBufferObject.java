package no.stelar7.engine.rendering.buffers;

import java.nio.*;

import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObject
{
    private int id;
    private int type;
    
    public VertexBufferObject(int type)
    {
        this.id = glGenBuffers();
        this.type = type;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void bind()
    {
        glBindBuffer(type, id);
    }
    
    public void unbind()
    {
        glBindBuffer(type, 0);
    }
    
    public void setData(FloatBuffer data)
    {
        setData(data, GL_STATIC_DRAW);
    }
    
    public void setData(FloatBuffer data, int draw)
    {
        glBufferData(type, data, draw);
    }
    
    public void delete()
    {
        glDeleteBuffers(id);
    }
    
    public void setData(final IntBuffer data)
    {
        setData(data, GL_STATIC_DRAW);
    }
    
    public void setData(IntBuffer data, int draw)
    {
        glBufferData(type, data, draw);
    }
}
