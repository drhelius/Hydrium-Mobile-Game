/*
 * Shot.java
 *
 * Creado el 15 de febrero de 2006, 11:24
 *
 * Autor: Nacho Sánchez
 *
 */

import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Shot extends DynamicObject
{    
    public static Sprite m_BallShotSprite1;
    public static Sprite m_BallShotSprite2;
    public static Sprite m_EnemyShotSprite1;
    public static Sprite m_EnemyShotSprite2;
    public long LastFrameUpdate;
    public int nextSlot;
    public boolean collision;
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public static void start()
    {	
	Image img;
	
	try
	{
	    img = Image.createImage("/gfx/disparo1.png");
	    m_BallShotSprite1 = new Sprite(img);
	    
	    img = Image.createImage("/gfx/disparo2.png");
	    m_BallShotSprite2 = new Sprite(img);
	    
	    img = Image.createImage("/gfx/disparo_ene2.png");
	    m_EnemyShotSprite1 = new Sprite(img);
	    
	    img = Image.createImage("/gfx/disparo_ene3.png");
	    m_EnemyShotSprite2 = new Sprite(img);
	}
	catch(Exception e)
	{            
	    System.err.println("Error al cargar /gfx/disparo1.png - " + e);
	}		
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void render()
    {
	if (disable)
	    return;
	
	Sprite spr = m_BallShotSprite1;
	
	if (type == 0)
	{
	    spr = m_BallShotSprite1;
	}
	else if (type == 1)
	{
	    spr = m_BallShotSprite2;
	}
	else if (type == 3)
	{
	    spr = m_EnemyShotSprite1;
	}
	else if (type == 4)
	{
	    spr = m_EnemyShotSprite2;
	}
	
	spr.setPosition(x - Map.CameraX, y - Map.CameraY);
	spr.paint(MainCanvas.GFX);	
    } 
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void update()
    {
	try
	{
	fx += (velX * MainTimer.HalfDeltaTime);
	fy += (velY * MainTimer.HalfDeltaTime);
	
	CollisionData.velX = velX;
	CollisionData.velY = velY;
	CollisionData.x = (int)fx + 4;
	CollisionData.y = (int)fy + 4;
	CollisionData.radius = 5;	
	
	int sectorx = x / 64;
	int sectory = y / 64;
		
	int sectorx1 = (x + 8) / 64;		
	int sectory1 = (y + 8) / 64;
	
	boolean col = false;
	
	collision = false;
	
	
	MapSector Sector = Map.SectorArray[sectorx][sectory];
	
	if (Map.testCollisionSector(Sector, false))
	{	    
	    col = true;
	}
	
	//Sector.dynamicObjects.addElement(this);
	
	if (sectorx1 != sectorx)
	{
	    Sector = Map.SectorArray[sectorx1][sectory];
	    
	    //Sector.dynamicObjects.addElement(this);
	   
	    if (Map.testCollisionSector(Sector, false))
	    {		
		col = true;
	    }
	}
		
	if (sectory1 != sectory)
	{
	    Sector = Map.SectorArray[sectorx][sectory1];
	    
	    //Sector.dynamicObjects.addElement(this);
	    
	    if (Map.testCollisionSector(Sector, false))
	    {		
		col = true;
	    }
	}
	
	if ((sectorx1 != sectorx) && (sectory1 != sectory))
	{
	    Sector = Map.SectorArray[sectorx1][sectory1];
	    
	    //Sector.dynamicObjects.addElement(this);
	    
	    if (Map.testCollisionSector(Sector, false))
	    {
		col = true;
	    }		
	}
	
	
	
	if (col)
	{
	    collision = true;
	    
	    velX = CollisionData.velX;
	    velY = CollisionData.velY;
	    fx = CollisionData.x-4.0f;
	    fy = CollisionData.y-4.0f;           
	}      
	
	x = (int)fx;
	y = (int)fy;	  
	
	
	
	
	
	fx += (velX * MainTimer.HalfDeltaTime);
	fy += (velY * MainTimer.HalfDeltaTime);
	
	CollisionData.velX = velX;
	CollisionData.velY = velY;
	CollisionData.x = (int)fx + 4;
	CollisionData.y = (int)fy + 4;
	CollisionData.radius = 4;	
	
	sectorx = x / 64;
	sectory = y / 64;
		
	sectorx1 = (x + 8) / 64;		
	sectory1 = (y + 8) / 64;
	
	col = false;
	
	Sector = Map.SectorArray[sectorx][sectory];
	
	if (Map.testCollisionSector(Sector, false))
	{	    
	    col = true;
	}
	
	Sector.dynamicObjects.addElement(this);
	
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
	    collision = true;
	    
	    velX = CollisionData.velX;
	    velY = CollisionData.velY;
	    fx = CollisionData.x-4.0f;
	    fy = CollisionData.y-4.0f;           
	}      
	
	x = (int)fx;
	y = (int)fy;
	
	
	
	
	
	
	
	distance = ((1500 - x) + y);
	
	}
	catch (Exception e)
	{
	    int i = x +1;
	    i++;
	}
	
    }
}
