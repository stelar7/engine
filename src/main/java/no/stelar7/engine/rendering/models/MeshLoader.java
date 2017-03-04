package no.stelar7.engine.rendering.models;

import no.stelar7.engine.EngineUtils;
import org.joml.*;
import org.lwjgl.*;

import java.nio.*;
import java.util.*;

public class MeshLoader
{
    private static final List<Vector3f> vertices = new ArrayList<>();
    private static final List<Vector3f> normals  = new ArrayList<>();
    private static final List<Vector2f> textures = new ArrayList<>();
    private static final List<Integer>  indexes  = new ArrayList<>();
    
    private static FloatBuffer vertexBuffer;
    private static FloatBuffer textureBuffer;
    private static FloatBuffer normalBuffer;
    private static IntBuffer   indexBuffer;
    
    public synchronized static Mesh loadFromObj(String filename)
    {
        vertices.clear();
        normals.clear();
        textures.clear();
        indexes.clear();
        
        String[] lines  = EngineUtils.readObjFile(filename).split("\n");
        boolean  parsed = false;
        
        for (String line : lines)
        {
            
            if (line.trim().isEmpty())
            {
                continue;
            }
            
            String[] parts = line.split(" ");
            
            if ("v".equalsIgnoreCase(parts[0]))
            {
                Vector3f vec = new Vector3f();
                vec.x = Float.parseFloat(parts[1]);
                vec.y = Float.parseFloat(parts[2]);
                vec.z = Float.parseFloat(parts[3]);
                vertices.add(vec);
            }
            
            
            if ("vt".equalsIgnoreCase(parts[0]))
            {
                Vector2f vec = new Vector2f();
                vec.x = Float.parseFloat(parts[1]);
                vec.y = Float.parseFloat(parts[2]);
                textures.add(vec);
            }
            
            
            if ("vn".equalsIgnoreCase(parts[0]))
            {
                Vector3f vec = new Vector3f();
                vec.x = Float.parseFloat(parts[1]);
                vec.y = Float.parseFloat(parts[2]);
                vec.z = Float.parseFloat(parts[3]);
                vec.normalize();
                normals.add(vec);
            }
            
            if ("f".equalsIgnoreCase(parts[0]))
            {
                if (!parsed)
                {
                    textureBuffer = BufferUtils.createFloatBuffer(vertices.size() * 2);
                    normalBuffer = BufferUtils.createFloatBuffer(vertices.size() * 3);
                    parsed = true;
                }
                
                
                String[] v1 = parts[1].split("/");
                String[] v2 = parts[2].split("/");
                String[] v3 = parts[3].split("/");
                processVertex(v1);
                processVertex(v2);
                processVertex(v3);
            }
        }
        
        vertexBuffer = BufferUtils.createFloatBuffer(vertices.size() * 3);
        indexBuffer = BufferUtils.createIntBuffer(indexes.size());
        
        for (int i = 0; i < vertices.size(); i++)
        {
            vertices.get(i).get(i * 3, vertexBuffer);
        }
        
        for (int i = 0; i < indexes.size(); i++)
        {
            indexBuffer.put(i, indexes.get(i));
        }
        
        return new Mesh(vertexBuffer, indexBuffer, normalBuffer, textureBuffer);
        
    }
    
    private static void processVertex(final String[] vertex)
    {
        int vertexIndex = Integer.parseInt(vertex[0].trim()) - 1;
        indexes.add(vertexIndex);
        
        Vector2f texture = textures.get(Integer.parseInt(vertex[1].trim()) - 1);
        textureBuffer.put(vertexIndex * 2, texture.x);
        textureBuffer.put(vertexIndex * 2 + 1, texture.y);
        
        Vector3f normal = normals.get(Integer.parseInt(vertex[2].trim()) - 1);
        normalBuffer.put(vertexIndex * 3, normal.x);
        normalBuffer.put(vertexIndex * 3 + 1, normal.y);
        normalBuffer.put(vertexIndex * 3 + 2, normal.z);
    }
}
