package no.stelar7.engine.rendering.buffers;

import no.stelar7.engine.EngineUtils;

import java.nio.*;
import java.util.Arrays;

import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObject
{
    private int id;
    private int type;
    
    public VertexBufferObject(int type)
    {
        this.type = type;
    }
    
    public void generate()
    {
        this.id = glGenBuffers();
        EngineUtils.log("glGenBuffers() = %s", id);
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void bind()
    {
        glBindBuffer(type, id);
        EngineUtils.log("glBindBuffer(%s, %s)", EngineUtils.glTypeToString(type), id);
    }
    
    public void unbind()
    {
        glBindBuffer(type, 0);
        EngineUtils.log("glBindBuffer(%s, %s)", EngineUtils.glTypeToString(type), 0);
    }
    
    public void setData(FloatBuffer data)
    {
        setData(data, GL_STATIC_DRAW);
    }
    
    public void setData(FloatBuffer data, int draw)
    {
        glBufferData(type, data, draw);
        EngineUtils.log("glBufferData(%s, %s, %s)", EngineUtils.glTypeToString(type), EngineUtils.bufferToString(data), EngineUtils.glTypeToString(draw));
    }
    
    public void setData(final IntBuffer data)
    {
        setData(data, GL_STATIC_DRAW);
    }
    
    public void setData(IntBuffer data, int draw)
    {
        glBufferData(type, data, draw);
        EngineUtils.log("glBufferData(%s, %s, %s)", EngineUtils.glTypeToString(type), EngineUtils.bufferToString(data), EngineUtils.glTypeToString(draw));
    }
    
    public void delete()
    {
        glDeleteBuffers(id);
        EngineUtils.log("glDeleteBuffers(%s)", id);
    }
    
    public void setData(float[] data)
    {
        setData(data, GL_STATIC_DRAW);
    }
    
    public void setData(float[] data, int draw)
    {
        glBufferData(type, data, GL_STATIC_DRAW);
        EngineUtils.log("glBufferData(%s, %s, %s)", EngineUtils.glTypeToString(type), Arrays.toString(data), EngineUtils.glTypeToString(draw));
    }
}
