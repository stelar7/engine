package no.stelar7.engine.rendering.shaders;

import no.stelar7.engine.EngineUtils;

import static org.lwjgl.opengl.GL11.*;
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
        EngineUtils.log("glCreateShader(%s) = %s", EngineUtils.glTypeToString(type), id);
    }
    
    public void setSource(String source)
    {
        glShaderSource(id, source);
        EngineUtils.log("glShaderSource(%s, \n%s", id, source);
    }
    
    public void compile()
    {
        glCompileShader(id);
        EngineUtils.log("glCompileShader(%s)", id);
        
        int status = glGetShaderi(id, GL_COMPILE_STATUS);
        EngineUtils.log("glGetShaderi(%s, %s) = %s", id, EngineUtils.glTypeToString(GL_COMPILE_STATUS), EngineUtils.glTypeToString(status));
        if (status != GL_TRUE)
        {
            String log = glGetShaderInfoLog(id);
            EngineUtils.log("glGetShaderInfoLog(%s)", id);
            EngineUtils.log(log);
            
            throw new RuntimeException(log);
        }
    }
    
    public void delete()
    {
        glDeleteShader(id);
        EngineUtils.log("glDeleteShader(%s)", id);
    }
}
