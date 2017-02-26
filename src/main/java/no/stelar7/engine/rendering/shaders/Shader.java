package no.stelar7.engine.rendering.shaders;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

public class Shader
{
    
    private int id;
    
    public int getId()
    {
        return id;
    }
    
    public Shader(int type)
    {
        id = glCreateShader(type);
    }
    
    public void setSource(String source)
    {
        glShaderSource(id, source);
    }
    
    public void compile()
    {
        glCompileShader(id);
        
        if (glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE)
        {
            throw new RuntimeException(glGetShaderInfoLog(id));
        }
    }
    
    public void delete()
    {
        glDeleteShader(id);
    }
}
