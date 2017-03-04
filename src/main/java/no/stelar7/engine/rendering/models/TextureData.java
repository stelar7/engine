package no.stelar7.engine.rendering.models;

import no.stelar7.engine.EngineUtils;
import org.lwjgl.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.Map.Entry;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public class TextureData
{
    private int           id;
    private float[]       coordinates;
    private BufferedImage image;
    
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
        
        TextureData that = (TextureData) o;
        return Arrays.equals(coordinates, that.coordinates) && ((image != null) ? image.equals(that.image) : (that.image == null));
    }
    
    @Override
    public int hashCode()
    {
        int result = Arrays.hashCode(coordinates);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
    
    public void setCoordinates(float[] inData)
    {
        coordinates = new float[inData.length];
        System.arraycopy(inData, 0, coordinates, 0, inData.length);
    }
    
    public float[] getCoordinates()
    {
        float[] copy = new float[coordinates.length];
        System.arraycopy(coordinates, 0, copy, 0, coordinates.length);
        
        return copy;
    }
    
    public void generateTexture()
    {
        id = glGenTextures();
        EngineUtils.log("glGenTextures() = %s", id);
    }
    
    public void bind()
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
    
    public void setImageData(BufferedImage inputImage)
    {
        image = inputImage;
        
        int[] pixelData = new int[inputImage.getWidth() * inputImage.getHeight()];
        inputImage.getRGB(0, 0, inputImage.getWidth(), inputImage.getHeight(), pixelData, 0, inputImage.getWidth());
        ByteBuffer pbuff = BufferUtils.createByteBuffer(inputImage.getHeight() * inputImage.getWidth() * 4);
        
        for (int y = 0; y < inputImage.getHeight(); y++)
        {
            for (int x = 0; x < inputImage.getWidth(); x++)
            {
                int pixel = pixelData[y * inputImage.getWidth() + x];
                pbuff.put((byte) ((pixel >> 16) & 0xFF));
                pbuff.put((byte) ((pixel >> 8) & 0xFF));
                pbuff.put((byte) ((pixel) & 0xFF));
                pbuff.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        pbuff.flip();
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, inputImage.getWidth(), inputImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, pbuff);
        EngineUtils.log("glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, %s, %s, 0, GL_RGBA, GL_UNSIGNED_BYTE, %s)", inputImage.getWidth(), inputImage.getHeight(), "pbuff (too large)");
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
