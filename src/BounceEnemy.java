/*
 * BounceEnemy.java
 *
 * Creado el 8 de febrero de 2006, 11:15
 *
 * Autor: Nacho Sánchez
 *
 */

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class BounceEnemy extends DynamicObject
{
    private static long m_lLastFrameUpdate;
    private final static int m_iVel = 30;
    private static Sprite m_Sprite;
    private final static int[] FRAME_SEQUENCE = {0, 1 , 2, 3};
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void start()
    {
	if (rotation == 0)
	{
	    velX = m_iVel;
	}
	else if (rotation == 1)
	{
	    velY = m_iVel;	    
	}
	else if (rotation == 2)
	{
	    velX = -m_iVel;	    
	}
	else if (rotation == 3)
	{
	    velY = -m_iVel;	    
	}
	
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
	
	fx += (velX * MainTimer.DeltaTime);
	fy += (velY * MainTimer.DeltaTime);
	
	int sectorx = x / 64;
	int sectory = y / 64;
		
	int sectorx1 = (x + 16) / 64;		
	int sectory1 = (y + 16) / 64;
	
	MapSector Sector = Map.SectorArray[sectorx][sectory];
	
	boolean col = false;
	
	Sector.dynamicObjects.addElement(this);
	
	CollisionData.radius = 13;
	CollisionData.x = (int)fx; 
	CollisionData.y = (int)fy; 
	
	if (Map.testSimpleCollisionSector(Sector))
	{
	    col = true;
	}
	
	if (sectorx1 != sectorx)
	{ 
	    Sector = Map.SectorArray[sectorx1][sectory];
	    Sector.dynamicObjects.addElement(this);
	    
	    if (Map.testSimpleCollisionSector(Sector))
	    {
		col = true;
	    }	    
	}	
	
	if (sectory1 != sectory)
	{    
	    Sector = Map.SectorArray[sectorx][sectory1];
	    Sector.dynamicObjects.addElement(this);
	    
	    if (Map.testSimpleCollisionSector(Sector))
	    {
		col = true;
	    }	 
	}
	
	if ((sectorx1 != sectorx) && (sectory1 != sectory))
	{
	    Sector = Map.SectorArray[sectorx1][sectory1];
	    Sector.dynamicObjects.addElement(this);	
	    
	    if (Map.testSimpleCollisionSector(Sector))
	    {
		col = true;
	    }	 
	}	
	
	if (col)
	{
	    velX = -velX;
	    velY = -velY;
		    
	    fx = fxold;
	    fy = fyold;
	}
	
	x = (int)fx;
	y = (int)fy;	
	
	/*
	fxold = fx;
	fyold = fy;
	
	fx += (velX * MainTimer.HalfDeltaTime);
	fy += (velY * MainTimer.HalfDeltaTime);
	
	sectorx = x / 64;
	sectory = y / 64;
		
	sectorx1 = (x + 16) / 64;		
	sectory1 = (y + 16) / 64;
	
	Sector = Map.SectorArray[sectorx][sectory];
	
	col = false;
	
	Sector.dynamicObjects.addElement(this);
	
	CollisionData.radius = 13;
	CollisionData.x = (int)fx; 
	CollisionData.y = (int)fy; 
	
	if (Map.testSimpleCollisionSector(Sector))
	{
	    col = true;
	}
	
	if (sectorx1 != sectorx)
	{ 
	    Sector = Map.SectorArray[sectorx1][sectory];
	    Sector.dynamicObjects.addElement(this);
	    
	    if (Map.testSimpleCollisionSector(Sector))
	    {
		col = true;
	    }	    
	}	
	
	if (sectory1 != sectory)
	{    
	    Sector = Map.SectorArray[sectorx][sectory1];
	    Sector.dynamicObjects.addElement(this);
	    
	    if (Map.testSimpleCollisionSector(Sector))
	    {
		col = true;
	    }	 
	}
	
	if ((sectorx1 != sectorx) && (sectory1 != sectory))
	{
	    Sector = Map.SectorArray[sectorx1][sectory1];
	    Sector.dynamicObjects.addElement(this);	
	    
	    if (Map.testSimpleCollisionSector(Sector))
	    {
		col = true;
	    }	 
	}	
	
	if (col)
	{
	    velX = -velX;
	    velY = -velY;
		    
	    fx = fxold;
	    fy = fyold;
	}
	
	x = (int)fx;
	y = (int)fy;*/
	
	distance = ((1500 - x) + y );	
    }
}
