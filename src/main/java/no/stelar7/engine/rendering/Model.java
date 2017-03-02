package no.stelar7.engine.rendering;

import no.stelar7.engine.rendering.buffers.*;
import org.lwjgl.*;

import java.nio.*;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Model
{
    
    private final VertexArrayObject vao = new VertexArrayObject();
    
    private final VertexBufferObject vbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    private final VertexBufferObject ibo = new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER);
    
    private final VertexBufferObject tbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    private final TextureData textureData;
    
    private final int vertexCount;
    
    public Model(float[] vert, int[] ind, TextureData tex)
    {
        vao.generate();
        vbo.generate();
        ibo.generate();
        tbo.generate();
        
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
        
        textureData = tex;
        FloatBuffer tbuff = BufferUtils.createFloatBuffer(tex.getCoordinates().length);
        tbuff.put(tex.getCoordinates());
        tbuff.flip();
        setTextures(tbuff);
        
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
    
    public void setTextures(FloatBuffer tbuff)
    {
        Map<Integer, Integer> params = new HashMap<>();
        params.put(GL_TEXTURE_WRAP_S, GL_REPEAT);
        params.put(GL_TEXTURE_WRAP_T, GL_REPEAT);
        params.put(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        params.put(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        textureData.generateTexture();
        textureData.setActiveTexture(0);
        textureData.bindTexture();
        textureData.setParameters(params);
        textureData.setImageData();
        
        tbo.bind();
        tbo.setData(tbuff);
        vao.enableAttribIndex(1);
        vao.setPointer(1, 2);
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
        textureData.unbind();
        tbo.unbind();
        ibo.unbind();
        vbo.unbind();
        vao.unbind();
        
        textureData.delete();
        tbo.delete();
        ibo.delete();
        vbo.delete();
        vao.delete();
    }
    
    public int getVertexCount()
    {
        return vertexCount;
    }
    
}
