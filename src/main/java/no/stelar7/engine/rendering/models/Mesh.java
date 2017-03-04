package no.stelar7.engine.rendering.models;

import no.stelar7.engine.EngineUtils;
import no.stelar7.engine.rendering.buffers.*;
import org.joml.Vector3f;

import java.nio.*;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;

public class Mesh
{
    private final VertexArrayObject vao = new VertexArrayObject();
    
    private final VertexBufferObject vbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    private final VertexBufferObject ibo = new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER);
    
    private final VertexBufferObject tbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    
    private final VertexBufferObject nbo = new VertexBufferObject(GL_ARRAY_BUFFER);
    
    private final int vertexCount;
    
    private TextureData texture;
    
    public Mesh(FloatBuffer vertexBuffer, IntBuffer indexBuffer, FloatBuffer normalBuffer, FloatBuffer textureBuffer)
    {
        vertexCount = indexBuffer.capacity();
        
        vao.generate();
        vbo.generate();
        ibo.generate();
        tbo.generate();
        nbo.generate();
        
        bind();
        
        setVertices(vertexBuffer);
        setIndices(indexBuffer);
        setTextureCoordinates(textureBuffer);
        setNormals(normalBuffer);
        
        unbind();
        
    }
    
    public Mesh(float[] vert, int[] ind, float[] tex)
    {
        vertexCount = ind.length;
        
        vao.generate();
        vbo.generate();
        ibo.generate();
        tbo.generate();
        
        bind();
        
        setVertices(EngineUtils.floatArrayToBuffer(vert));
        setIndices(EngineUtils.intArrayToBuffer(ind));
        setTextureCoordinates(EngineUtils.floatArrayToBuffer(tex));
        
        unbind();
    }
    
    public Mesh(List<Vector3f> vertices, List<Integer> indecies, List<Float> normals, List<Float> textures)
    {
        vertexCount = indecies.size();
        
        vao.generate();
        vbo.generate();
        ibo.generate();
        tbo.generate();
        nbo.generate();
        
        bind();
        
        setVertices(EngineUtils.vector3fListToBuffer(vertices));
        setIndices(EngineUtils.intListToBuffer(indecies));
        setTextureCoordinates(EngineUtils.floatListToBuffer(textures));
        setNormals(EngineUtils.floatListToBuffer(normals));
        
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
    
    
    public void setTexture(TextureData texture)
    {
        this.texture = texture;
    }
    
    public void bind()
    {
        vao.bind();
        if (texture != null)
        {
            texture.bind();
        }
    }
    
    public void unbind()
    {
        vao.unbind();
        if (texture != null)
        {
            texture.unbind();
        }
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
        
        if (texture != null)
        {
            texture.unbind();
            texture.delete();
        }
    }
    
    public int getVertexCount()
    {
        return vertexCount;
    }
    
    public TextureData getTexture()
    {
        return texture;
    }
    
    
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
}
