package no.stelar7.engine.rendering.shaders;

import no.stelar7.engine.EngineUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram
{
    
    private int id;
    
    public int getId()
    {
        return id;
    }
    
    public ShaderProgram()
    {
        this.id = glCreateProgram();
    }
    
    public ShaderProgram(Shader... shaders)
    {
        this.id = glCreateProgram();
        EngineUtils.log("glCreateProgram() = %s", id);
        
        for (final Shader shader : shaders)
        {
            attachShader(shader);
        }
        
        link();
        
        for (final Shader shader : shaders)
        {
            deleteShader(shader);
        }
        
        validate();
    }
    
    public void validate()
    {
        glValidateProgram(id);
        EngineUtils.log("glValidateProgram(%s)", id);
    
        int status = glGetProgrami(id, GL_VALIDATE_STATUS);
        EngineUtils.log("glGetProgrami(%s, %s) = %s", id, EngineUtils.glTypeToString(GL_VALIDATE_STATUS), EngineUtils.glTypeToString(status));
        if (status != GL_TRUE)
        {
            String log = glGetProgramInfoLog(id);
            EngineUtils.log("glGetProgramInfoLog(%s)", id);
            EngineUtils.log(log);
        
            throw new RuntimeException(log);
        }
        
    }
    
    public void deleteShader(final Shader shader)
    {
        glDetachShader(id, shader.getId());
        EngineUtils.log("glDetachShader(%s, %s);", id, shader.getId());
        shader.delete();
    }
    
    public void attachShader(final Shader shader)
    {
        glAttachShader(id, shader.getId());
        EngineUtils.log("glAttachShader(%s, %s);", id, shader.getId());
    }
    
    public void link()
    {
        glLinkProgram(id);
        EngineUtils.log("glLinkProgram(%s);", id);
        
        int status = glGetProgrami(id, GL_LINK_STATUS);
        EngineUtils.log("glGetProgrami(%s, %s) = %s", id, EngineUtils.glTypeToString(GL_LINK_STATUS), EngineUtils.glTypeToString(status));
        if (status != GL_TRUE)
        {
            String log = glGetProgramInfoLog(id);
            EngineUtils.log("glGetProgramInfoLog(%s)", id);
            EngineUtils.log(log);
            
            throw new RuntimeException(log);
        }
    }
    
    public void delete()
    {
        glDeleteProgram(id);
        EngineUtils.log("glDeleteProgram(%s)", id);
    }
    
    public void unbind()
    {
        glUseProgram(0);
        EngineUtils.log("glUseProgram(%s)", 0);
    }
    
    public void bind()
    {
        glUseProgram(id);
        EngineUtils.log("glUseProgram(%s)", id);
    }
}
