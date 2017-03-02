package no.stelar7.engine.rendering.models;

import no.stelar7.engine.EngineUtils;
import org.lwjgl.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Map.Entry;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public class TextureData
{
    private int           id;
    private float[]       coordinates;
    private BufferedImage image;
    
    public TextureData(float[] inData, BufferedImage image)
    {
        coordinates = new float[inData.length];
        System.arraycopy(inData, 0, coordinates, 0, inData.length);
        
        this.image = image;
    }
    
    public float[] getCoordinates()
    {
        float[] copy = new float[coordinates.length];
        System.arraycopy(coordinates, 0, copy, 0, coordinates.length);
        
        return copy;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
    
    public void generateTexture()
    {
        id = glGenTextures();
        EngineUtils.log("glGenTextures() = %s", id);
    }
    
    public void bindTexture()
    {
        glBindTexture(GL_TEXTURE_2D, id);
        EngineUtils.log("glBindTexture(GL_TEXTURE_2D, %s)", id);
    }
    
    public void setActiveTexture(int number)
    {
        glActiveTexture(GL_TEXTURE0 + number);
        EngineUtils.log("glActiveTexture(%s)", GL_TEXTURE0 + number);
    }
    
    public void setParameters(Map<Integer, Integer> data)
    {
        for (Entry<Integer, Integer> entry : data.entrySet())
        {
            glTexParameteri(GL_TEXTURE_2D, entry.getKey(), entry.getValue());
            EngineUtils.log("glTexParameteri(GL_TEXTURE_2D, %s, %s);", entry.getKey(), entry.getValue());
        }
    }
    
    public void setImageData()
    {
        int[] pixelData = new int[getImage().getWidth() * getImage().getHeight()];
        getImage().getRGB(0, 0, getImage().getWidth(), getImage().getHeight(), pixelData, 0, getImage().getWidth());
        ByteBuffer pbuff = BufferUtils.createByteBuffer(getImage().getHeight() * getImage().getWidth() * 4);
        
        for (int y = 0; y < getImage().getHeight(); y++)
        {
            for (int x = 0; x < getImage().getWidth(); x++)
            {
                int pixel = pixelData[y * getImage().getWidth() + x];
                pbuff.put((byte) ((pixel >> 16) & 0xFF));
                pbuff.put((byte) ((pixel >> 8) & 0xFF));
                pbuff.put((byte) ((pixel) & 0xFF));
                pbuff.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        pbuff.flip();
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, getImage().getWidth(), getImage().getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, pbuff);
        EngineUtils.log("glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, %s, %s, 0, GL_RGBA, GL_UNSIGNED_BYTE, %s)", image.getWidth(), image.getHeight(), "pbuff (too large)");
        glGenerateMipmap(GL_TEXTURE_2D);
        EngineUtils.log("glGenerateMipmap(GL_TEXTURE_2D)");
    }
    
    public void unbind()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
        EngineUtils.log("glBindTexture(GL_TEXTURE_2D, 0)");
    }
    
    public void delete()
    {
        glDeleteTextures(id);
        EngineUtils.log("glDeleteTextures(%s)", id);
    }
}
