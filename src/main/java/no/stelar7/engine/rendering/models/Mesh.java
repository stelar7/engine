package no.stelar7.engine.rendering.models;

import no.stelar7.engine.EngineUtils;
import no.stelar7.engine.rendering.buffers.*;
import org.joml.*;

import java.awt.image.BufferedImage;
import java.nio.*;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Mesh
{
    private final VertexArrayObject vao = new VertexArrayObject();
    
    private final VertexBufferObject vbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    private final VertexBufferObject ibo = new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER);
    
    private final VertexBufferObject tbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    private TextureData textureData;
    
    private final VertexBufferObject nbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    
    private final int vertexCount;
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        
        Mesh mesh = (Mesh) o;
        return vao.getId() == mesh.vao.getId();
    }
    
    @Override
    public int hashCode()
    {
        return vao.hashCode();
    }
    
    public Mesh(float[] vert, int[] ind, float[] tex)
    {
        vertexCount = ind.length;
        
        vao.generate();
        vbo.generate();
        ibo.generate();
        tbo.generate();
        
        bind();
        
        FloatBuffer vbuff = EngineUtils.floatArrayToBuffer(vert);
        setVertices(vbuff);
        
        IntBuffer ibuff = EngineUtils.intArrayToBuffer(ind);
        setIndices(ibuff);
        
        FloatBuffer tbuff = EngineUtils.floatArrayToBuffer(tex);
        setTextureCoordinates(tbuff);
        
        unbind();
    }
    
    public Mesh(List<Vector3f> vertices, List<Integer> indecies, List<Vector3f> normals, List<Vector2f> textures)
    {
        vao.generate();
        vbo.generate();
        ibo.generate();
        tbo.generate();
        nbo.generate();
        
        bind();
        
        FloatBuffer vbuff = EngineUtils.vector3fListToBuffer(vertices);
        setVertices(vbuff);
        
        IntBuffer ibuff = EngineUtils.intListToBuffer(indecies);
        setIndices(ibuff);
        vertexCount = indecies.size();
        
        FloatBuffer tbuff = EngineUtils.vector2fListToBuffer(textures);
        setTextureCoordinates(tbuff);
        
        FloatBuffer nbuff = EngineUtils.vector3fListToBuffer(normals);
        setNormals(nbuff);
        
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
    
    private void setTextureCoordinates(FloatBuffer tbuff)
    {
        tbo.bind();
        tbo.setData(tbuff);
        vao.enableAttribIndex(1);
        vao.setPointer(1, 2);
    }
    
    private void setNormals(FloatBuffer norm)
    {
        nbo.bind();
        nbo.setData(norm);
        vao.enableAttribIndex(2);
        vao.setPointer(2, 2);
    }
    
    public void setTexture(BufferedImage image, int texId)
    {
        Map<Integer, Integer> params = new HashMap<>();
        params.put(GL_TEXTURE_WRAP_S, GL_REPEAT);
        params.put(GL_TEXTURE_WRAP_T, GL_REPEAT);
        params.put(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        params.put(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        textureData = new TextureData();
        textureData.generateTexture();
        textureData.setActiveTexture(texId);
        textureData.bindTexture();
        textureData.setParameters(params);
        textureData.setImageData(image);
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
        nbo.unbind();
        tbo.unbind();
        ibo.unbind();
        vbo.unbind();
        vao.unbind();
        
        nbo.delete();
        tbo.delete();
        ibo.delete();
        vbo.delete();
        vao.delete();
        
        if (textureData != null)
        {
            textureData.delete();
            textureData.unbind();
        }
    }
    
    public int getVertexCount()
    {
        return vertexCount;
    }
    
}
