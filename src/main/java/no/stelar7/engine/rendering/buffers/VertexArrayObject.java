package no.stelar7.engine.rendering.buffers;

import no.stelar7.engine.EngineUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArrayObject
{
    private int id;
    
    public void generate()
    {
        this.id = glGenVertexArrays();
        EngineUtils.log("glGenVertexArrays() = %s", id);
    }
    
    public void bind()
    {
        glBindVertexArray(id);
        EngineUtils.log("glBindVertexArray(%s)", id);
    }
    
    public void unbind()
    {
        glBindVertexArray(0);
        EngineUtils.log("glBindVertexArray(%s)", 0);
    }
    
    public void delete()
    {
        glDeleteVertexArrays(id);
        EngineUtils.log("glDeleteVertexArrays(%s)", id);
    }
    
    public void enableAttribIndex(int index)
    {
        glEnableVertexAttribArray(index);
        EngineUtils.log("glEnableVertexAttribArray(%s)", index);
    }
    
    public void disableAttribIndex(int index)
    {
        glDisableVertexAttribArray(index);
        EngineUtils.log("glDisableVertexAttribArray(%s)", index);
    }
    
    public void setPointer(int index, int size)
    {
        setPointer(index, size, 0, 0);
    }
    
    public void setPointer(int index, int size, int stride, long offset)
    {
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride * Float.BYTES, offset * Float.BYTES);
        EngineUtils.log("glVertexAttribPointer(%s, %s, %s, %s, %s, %s)", index, size, EngineUtils.glTypeToString(GL_FLOAT), false, stride * Float.BYTES, offset * Float.BYTES);
    }
    
}
