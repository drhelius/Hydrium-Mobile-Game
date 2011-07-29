/*
 * SearchEnemy.java
 *
 * Creado el 19 de octubre de 2006, 20:28
 *
 * Autor: Nacho Sánchez
 *
 */

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class SearchEnemy extends DynamicObject
{
    private static long m_lLastFrameUpdate;
    private final static int m_iVel = 30;
    private static Sprite m_Sprite;
    private final static int[] FRAME_SEQUENCE = {0, 1 , 2, 3};
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void start()
    {		
	if (m_Sprite == null)
	{
	    m_Sprite = new Sprite(Map.EnemyImageArray[id], 16, 16);
	}
	
	m_Sprite.setFrameSequence(FRAME_SEQUENCE);
	m_Sprite.setFrame(0);
	
	m_lLastFrameUpdate=0;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void render()
    {
	if (disable)
	    return;
	
	int posx = x;
	int posy = y - 3;
	
	if (((posx + 16) > Map.CameraX) && ((posy + 16) > Map.CameraY) && ((Map.CameraX + Map.ScreenWidth) > posx) && ((Map.CameraY + Map.ScreenHeight) > posy))
	{
	    m_Sprite.setPosition(posx - Map.CameraX, posy - Map.CameraY);
	    m_Sprite.paint(MainCanvas.GFX);	
	}
    } 
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void update()
    {
	if (MainTimer.TotalMillis - m_lLastFrameUpdate > 75)	
	{
	    m_Sprite.nextFrame();
	    m_lLastFrameUpdate = MainTimer.TotalMillis;
	}
	
	float fxold = fx;
	float fyold = fy;
	
	float vDisX = MainCanvas.TheBall.PosX - 8.0f - fx;
	float vDisY = MainCanvas.TheBall.PosY - 8.0f - fy;
		
	float mod = (float)Math.sqrt((vDisX * vDisX) + (vDisY * vDisY));                  

	velX = (vDisX / mod) * 60.0f;
	velY = (vDisY / mod) * 60.0f;	
	
	fx += (velX * MainTimer.DeltaTime);
	fy += (velY * MainTimer.DeltaTime);
	
	int sectorx = (int)fx / 64;
	int sectory = (int)fy / 64;
		
	int sectorx1 = ((int)fx + 16) / 64;		
	int sectory1 = ((int)fy + 16) / 64;
	
	MapSector Sector = Map.SectorArray[sectorx][sectory];
	
	boolean col = false;
	
	Sector.dynamicObjects.addElement(this);
	
	CollisionData.radius = 8;
	CollisionData.x = (int)fx+8; 
	CollisionData.y = (int)fy+8; 
	
	CollisionData.velX = velX;
	CollisionData.velY = velY;

	
	if (Map.testCollisionSector(Sector, false))
	{
	    col = true;
	}
	
	if (sectorx1 != sectorx)
	{ 
	    Sector = Map.SectorArray[sectorx1][sectory];
	    Sector.dynamicObjects.addElement(this);
	    
	    if (Map.testCollisionSector(Sector, false))
	    {
		col = true;
	    }	    
	}	
	
	if (sectory1 != sectory)
	{    
	    Sector = Map.SectorArray[sectorx][sectory1];
	    Sector.dynamicObjects.addElement(this);
	    
	    if (Map.testCollisionSector(Sector, false))
	    {
		col = true;
	    }	 
	}
	
	if ((sectorx1 != sectorx) && (sectory1 != sectory))
	{
	    Sector = Map.SectorArray[sectorx1][sectory1];
	    Sector.dynamicObjects.addElement(this);	
	    
	    if (Map.testCollisionSector(Sector, false))
	    {
		col = true;
	    }	 
	}	
	
	if (col)
	{	
	    fx = CollisionData.x-8;
	    fy = CollisionData.y-8;	   
	}
	
	x = (int)fx;
	y = (int)fy;	
	
	distance = ((1500 - x) + y );	
    }
}
