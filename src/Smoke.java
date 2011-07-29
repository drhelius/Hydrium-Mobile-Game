/*
 * Smoke.java
 *
 * Creado el 8 de febrero de 2006, 16:58
 *
 * Autor: Nacho Sánchez
 *
 */

import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Smoke extends DynamicObject
{
    public float fRadius;
    private final static int[] FRAME_SEQUENCE = {0, 1 , 2, 3, 4};
    public static Sprite m_SmokeSprite;
    public int nextSlot;
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void start()
    {
	Image img;
	
	try
	{
	    img = Image.createImage("/gfx/humo.png");
	    m_SmokeSprite = new Sprite(img, 10, 10);
	    m_SmokeSprite.setFrameSequence(FRAME_SEQUENCE);
	    m_SmokeSprite.setFrame(0);
	}
	catch(Exception e)
	{            
	    System.err.println("Error al cargar /gfx/humo.png - " + e);
	}		
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void render()
    {
	m_SmokeSprite.setPosition(x - Map.CameraX - 5, y - Map.CameraY - 5);
	m_SmokeSprite.paint(MainCanvas.GFX);	
    } 
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void update()
    {
	fx += (velX * MainTimer.DeltaTime);
	fy += (velY * MainTimer.DeltaTime);
	fRadius -= (6.0f * MainTimer.DeltaTime);
	
	x=(int)fx;
	y=(int)fy;
	
	if (fRadius >= 4.0f)
	{
	    m_SmokeSprite.setFrame(0);
	}
	else if (fRadius >= 3.0f)
	{
	    m_SmokeSprite.setFrame(1);
	}
	else if (fRadius >= 2.0)
	{
	    m_SmokeSprite.setFrame(2);	    
	}
	else if (fRadius >= 1.0f)
	{
	    m_SmokeSprite.setFrame(3);	    
	}
	else if (fRadius >= 0.0f)
	{
	    m_SmokeSprite.setFrame(4);	    
	}
    }
}
