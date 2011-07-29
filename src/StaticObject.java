/*
 * StaticObject.java
 *
 * Creado el 1 de febrero de 2006, 11:54
 *
 * Autor: Nacho Sánchez
 *
 */

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class StaticObject extends RenderObject
{    
    public byte type;
    public byte id;
    public int x;
    public int y;
    public byte rotation;
    public boolean disable;
    public byte sides;   
    public Sprite sprite;    
    public float timestamp;   
    private long m_lLastFrameUpdate; 
        
   
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void render()
    {	
	if (disable)
	    return;
	
	///--- es un cubo
	if (type == 0)
	{
	    ///--- no es una puerta
	    if ((id < 10) || (id > 11))
	    {
		int posy = y - 6;

		if (((x + 25) > Map.CameraX) && ((posy + 22) > Map.CameraY) && ((Map.CameraX + Map.ScreenWidth) > x) && ((Map.CameraY + Map.ScreenHeight+12) > posy))
		{
		    sprite.setPosition(x - Map.CameraX, posy - Map.CameraY);

		    if (timestamp > 0.0f)
		    {
			timestamp -= MainTimer.DeltaTime;

			if (timestamp < 0.0f)
			    timestamp = 0.0f;

			try{
			sprite.setFrame((int)(timestamp * 10.0f));
			}
			catch (Exception e)
			{
			    System.out.print(e.getMessage() + e.toString());
			}
		    }
		    sprite.paint(MainCanvas.GFX);
		}
	    }
	    ///--- es una puerta
	    else
	    {
		int posy = y - 6;

		if (((x + 25) > Map.CameraX) && ((posy + 22) > Map.CameraY) && ((Map.CameraX + Map.ScreenWidth) > x) && ((Map.CameraY + Map.ScreenHeight+12) > posy))
		{		    
		    if ((id == 10) && MainCanvas.m_bHasRedKey)
		    {
			if (MainTimer.TotalMillis - MainCanvas.m_lLastUpdateDoor > 100)	
			{	
			    sprite.nextFrame();
			    
			    if (sprite.getFrame() == 0)
			    {
				disable = true;
				return;
			    }
			    
			    MainCanvas.m_lLastUpdateDoor = MainTimer.TotalMillis;
			}
		    }
		    else if ((id == 11) && MainCanvas.m_bHasBlueKey)
		    {
			if (MainTimer.TotalMillis - MainCanvas.m_lLastUpdateDoor > 100)	
			{	
			    sprite.nextFrame();
			    
			    if (sprite.getFrame() == 0)
			    {
				disable = true;
				return;
			    }
			    
			    MainCanvas.m_lLastUpdateDoor = MainTimer.TotalMillis;
			}
		    }
		    else
		    {
			sprite.setFrame(0);
		    }
		    
		    sprite.setPosition(x - Map.CameraX, posy - Map.CameraY);
		    sprite.paint(MainCanvas.GFX);
		}	
	    }		
	}
	///--- es un item
	else if (type == 1)
	{
	    if (id > 1 && id < 6)
	    {
		if (MainTimer.TotalMillis - m_lLastFrameUpdate > 200)	
		{	
		    sprite.nextFrame();
		    m_lLastFrameUpdate = MainTimer.TotalMillis;
		}	
	    }
            else if (id > 6 && id < 9)
	    {
		if (MainTimer.TotalMillis - m_lLastFrameUpdate > 150)	
		{	
		    sprite.nextFrame();
		    m_lLastFrameUpdate = MainTimer.TotalMillis;
		}	
	    }
            else if (id == 0)
	    {
		if (MainTimer.TotalMillis - m_lLastFrameUpdate > 130)	
		{	
		    sprite.nextFrame();
		    m_lLastFrameUpdate = MainTimer.TotalMillis;
		}	
	    }            
	    else if (id == 6)
	    {			
		sprite.setFrame(Map.m_iFrameCintas);		
	    }
	    
	    if (((x + 16) > Map.CameraX) && ((y + 16) > Map.CameraY) && ((Map.CameraX + Map.ScreenWidth) > x) && ((Map.CameraY + Map.ScreenHeight) > y))
	    {
		sprite.setPosition(x - Map.CameraX, y - Map.CameraY);
		sprite.paint(MainCanvas.GFX);
	    }		    
	}
	///--- es un enemigo
	else if (type == 2)
	{	    		
	    switch (id)
	    {
		///--- tiros rectos
		case 1:
		{
		    if (MainTimer.TotalMillis - m_lLastFrameUpdate > 1000)	
		    {
			int xadd = 0;
			int yadd = 0;

			switch (rotation)
			{
			    case 0:
			    {
				xadd = 5;
				break;
			    }
			    case 1:
			    {
				yadd = 5;
				break;
			    }
			    case 2:
			    {
				xadd = -5;
				break;
			    }
			    case 3:
			    {
				yadd = -5;
				break;
			    }
			}
			ShotManager.addShot(x + xadd + 6, y + yadd, xadd * 10.0f, yadd * 10.0f, (byte)3);
			m_lLastFrameUpdate = MainTimer.TotalMillis;
		    }
		    break;
		}
		///--- tiros dirigidos
		case 2:
		{
		    if (MainTimer.TotalMillis - m_lLastFrameUpdate > 1300)	
		    {
			float vDisX = MainCanvas.TheBall.PosX - 8.0f - x;
			float vDisY = MainCanvas.TheBall.PosY - 8.0f - y;

			float mod = (float)Math.sqrt((vDisX * vDisX) + (vDisY * vDisY)); 
			
			float dirx = (vDisX / mod) * 50.0f;
			float diry = (vDisY / mod) * 50.0f;
			
			if ((dirx < 0.0f) && (rotation == 0))
			{
			    break;
			}
			
			if ((dirx > 0.0f) && (rotation == 2))
			{
			    break;
			}
			
			if ((diry < 0.0f) && (rotation == 1))
			{
			    break;
			}
			
			if ((diry > 0.0f) && (rotation == 3))
			{
			    break;
			}

			ShotManager.addShot(x + 6, y, dirx, diry, (byte)4);
			m_lLastFrameUpdate = MainTimer.TotalMillis;
		    }
		    break;
		}		 
	    }    
	    
	    if (((x + 16) > Map.CameraX) && ((y + 16) > Map.CameraY) && ((Map.CameraX + Map.ScreenWidth) > x) && ((Map.CameraY + Map.ScreenHeight) > y))
	    {
		sprite.setPosition(x - Map.CameraX + 2, y - Map.CameraY - 4);
		sprite.paint(MainCanvas.GFX);
	    }
	}
        else if (type == 3)
        {
            if (MainTimer.TotalMillis - m_lLastFrameUpdate > 130)	
            {	
                sprite.nextFrame();
                m_lLastFrameUpdate = MainTimer.TotalMillis;
            }	
            if (((x + 16) > Map.CameraX) && ((y + 16) > Map.CameraY) && ((Map.CameraX + Map.ScreenWidth) > x) && ((Map.CameraY + Map.ScreenHeight) > y))
	    {
		sprite.setPosition(x - Map.CameraX, y - Map.CameraY);
		sprite.paint(MainCanvas.GFX);
	    }	
        }
	else
	{
	    if (((x + 16) > Map.CameraX) && ((y + 16) > Map.CameraY) && ((Map.CameraX + Map.ScreenWidth) > x) && ((Map.CameraY + Map.ScreenHeight) > y))
	    {
		sprite.setPosition(x - Map.CameraX, y - Map.CameraY);
		sprite.paint(MainCanvas.GFX);
	    }	
	}
    }
}
