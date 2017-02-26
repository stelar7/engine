package no.stelar7.engine.rendering.buffers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArrayObject
{
    private int id;
    
    public VertexArrayObject()
    {
        this.id = glGenVertexArrays();
    }
    
    public void bind()
    {
        glBindVertexArray(id);
    }
    
    public void unbind()
    {
        glBindVertexArray(0);
    }
    
    public void delete()
    {
        glDeleteVertexArrays(id);
    }
    
    public void enableAttribIndex(int index)
    {
        glEnableVertexAttribArray(index);
    }
    
    public void disableAttribIndex(int index)
    {
        glDisableVertexAttribArray(index);
    }
    
    public void setPointer(int index, int size)
    {
        setPointer(index, size, 0, 0);
    }
    
    public void setPointer(int index, int size, int stride, long offset)
    {
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride * Float.BYTES, offset * Float.BYTES);
    }
    
}
