/*
 * MainCanvas.java
 *
 * Creado el 12 de junio de 2006, 13:21
 *
 * Autor: Nacho Sánchez
 *
 */

////#define Testing
import javax.microedition.lcdui.*;
import java.io.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.rms.RecordStore;

///////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////

public final class MainCanvas extends GameCanvas implements Runnable
{     
    public static final boolean CHEATS = false;
    
    public final static String SOUNDPATH_GEMBLUE = "/snd/gema_amarilla.wav";
    public final static String SOUNDPATH_GEMRED = "/snd/gema_roja.wav";
    public final static String SOUNDPATH_GEMGREEN = "/snd/gema_verde.wav";
    public final static String SOUNDPATH_GEMYELLOW = "/snd/gema_azul.wav";
    public final static String SOUNDPATH_GEMMENU = "/snd/menu.wav";
    public final static String SOUNDPATH_GEMEXPLO = "/snd/explo.wav";
    
    private final static String STRING_CREDITS[]= 
    {
	"COPYRIGHT 2007",
        "GEARDOME",
        " ", " ",
	"PROGRAMMING",
	"NACHO SANCHEZ",
        " ",
	"DIGITAL ART",
	"CESAR SAMPEDRO",
        " ",
	"AUDIO",
	"DAVID GARCIA",
        " ",
        "LEVEL DESIGN",
        "NACHO SANCHEZ",
        "GORKA SUAREZ",
        " ", " "               
    };
       
    private static Class m_theClass; 
    
    private static String m_strLevelString;
    private static String m_strGemsString;
    
    public static Graphics GFX;

    private static boolean m_bPaused;
    private static boolean m_bShouldPause;
    
    private static int m_iScrWd;    
    private static int m_iScrHg;
    private static int m_iScrWdHalf;    
    private static int m_iScrHgHalf;
    
    private static int LEFT_SOFTKEY;      
    private static int RIGHT_SOFTKEY;
    
    private static int m_iKeyStates;
    private static int m_iKeyStatesOld;  
    
    private static int m_iSelectorOffset;
    private static boolean m_bSelectorDir;
    
    private static MainCanvas m_ThisObject;
    
    ////////////////
    
    public static Ball TheBall;    
   
    private static Font fuente;
    
    public static boolean m_bHasRedKey;
    public static boolean m_bHasBlueKey;
    public static long m_lLastUpdateDoor;
    public static long m_lLastUpdateTrans;
    
    public static int m_iRedShots;
    public static int m_iYellowShots;
    
    public static long m_lLastUpdateShot;
    
    public static final int m_iMaxVelocity = 55;
    private static final int m_iAccelerartion = 110;  
    
    private static Image m_LogoImage;
    private static Image[] m_LevelImage;
    private static Image[] m_LevelSquareImage;
    private static Image m_TileImage;
    private static Image m_SelectorIzqdaImage;
    private static Image m_SelectorDchaImage;
    private static Image m_SelectorMenuImage;
    private static Image m_BackSplashImage;
    private static Image m_BackMenuImage;
    private static Image m_BitmapFontImage;
    private static Image m_BitmapFontImage2;
    private static Image m_BitmapFontImage3;
    
    private static Sprite m_SelectorSprite;
    private static int[] SELECTOR_SEQUENCE = {0, 1};

     
    private static byte m_AppState;
    private static byte m_GameState;
    
    private static int m_iSelector;
    
    public static BitmapFont TheWhiteFont;
    public static BitmapFont TheBlueFont;    
    
    private static long m_lLastFrameUpdate;
    
    private static int m_iActualLevel;
    
    private static BounceEnemy m_TempBounceEnemy;
    private static SearchEnemy m_TempSearchEnemy;
    
    private static boolean[][] m_bLockedLevels;
    
    private static int m_iMenuSelectorX;
    private static int m_iMenuSelectorY;
    private static int m_iCreditsOffset;
        
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public MainCanvas()
    {
//#if MIDP == "2.0"
	super(true);
	setFullScreenMode(true);
//#endif
	
	m_theClass = getClass();
	
	m_ThisObject = this;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void saveLevels()
    {
        RecordStore rs = null;
        
        try
        {
            rs = RecordStore.openRecordStore("hydmobile", true );           
            
            byte[] rec = new byte[32];
            
            for (int j=0; j<4; j++)
            {
                for (int i=0; i<8; i++)
                {
                    rec[(j*8) + i] = (byte)(m_bLockedLevels[i][j] ? 1 : 0);                    
                }                
            }
            if (rs.getNumRecords() == 0)
            {
                rs.addRecord(rec, 0, rec.length);    
            }
            else
            {
                rs.setRecord(1, rec, 0, rec.length);    
            } 
        }
        catch (Exception e)
        {
          this.UTIL_OutputDebugString(e.toString());
        }
        finally
        {
            try
            {
                rs.closeRecordStore();
            }
            catch (Exception e)
            {
              this.UTIL_OutputDebugString(e.toString());
            }              
        }
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
  
    public void restoreLevels()
    {
        RecordStore rs = null;
        
        try
        {
            rs = RecordStore.openRecordStore("hydmobile", true );           
            
            byte[] rec = rs.getRecord(1);
            
            if (rec != null)
            {            
                for (int j=0; j<4; j++)
                {
                    for (int i=0; i<8; i++)
                    {
                        m_bLockedLevels[i][j] = (rec[(j*8) + i] == 1);                                      
                    }                
                }                
            }           
        }
        catch (Exception e)
        {
          this.UTIL_OutputDebugString(e.toString());
        }  
        finally
        {
            try
            {
                rs.closeRecordStore();
            }
            catch (Exception e)
            {
              this.UTIL_OutputDebugString(e.toString());
            }              
        }
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
      
    public final void MAIN_StartThread()
    {
	(new Thread(this)).start();	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    private final void MAIN_Init()
    {	
	///--- estos sirve para hacer comprobaciones
	m_TempBounceEnemy = new BounceEnemy();
	m_TempSearchEnemy = new SearchEnemy();	
	
	m_iActualLevel = m_GameState = 0;	
        
        m_AppState = 4; ///--- logo
	
	m_lLastFrameUpdate = 0;
	
	m_bHasRedKey = false;
	m_bHasBlueKey = false;
	    
	m_bPaused = false;	
	
	m_iKeyStates = 0;
	m_iKeyStatesOld = 0;	
        
        m_bLockedLevels = new boolean[8][4];
        m_LevelImage = new Image[5];
        m_LevelSquareImage = new Image[10];
        
        m_bLockedLevels[0][0]=true;
	
	INP_RetrieveSoftKeys();	
	
	GFX = getGraphics();
	
	m_iScrWd = getWidth();
	m_iScrHg = getHeight();	

	m_strLevelString = "LEVEL 1-1";
     
	m_iScrWdHalf = m_iScrWd >>> 1;    
	m_iScrHgHalf = m_iScrHg >>> 1;
	
	try
	{
            m_LogoImage = Image.createImage("/gfx/logo.png");            
            m_BitmapFontImage = Image.createImage("/gfx/font1.png");
            m_BitmapFontImage2 = Image.createImage("/gfx/font2.png");
            m_SelectorDchaImage = Image.createImage("/gfx/sel_d.png");
            m_SelectorIzqdaImage = Image.createImage("/gfx/sel_i.png");  
            m_BitmapFontImage3 = Image.createImage("/gfx/font3.png");
	}
	catch(IOException e) { }
	

	TheWhiteFont = new BitmapFont(m_BitmapFontImage, 9, 9);
	TheBlueFont = new BitmapFont(m_BitmapFontImage2, 9, 9);
	
	ShotManager.start();
	
	fuente = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL);
	
	GFX.setFont(fuente);
	
	TheBall = new Ball();	
	TheBall.loadGFX();
	    
	Map.loadAll(getWidth(), getHeight());
        
        m_iScrWd = getWidth();
	m_iScrHg = getHeight();	

	m_iScrWdHalf = m_iScrWd >>> 1;    
	m_iScrHgHalf = m_iScrHg >>> 1;
        
        restoreLevels();
	
	System.gc();
    }       
  
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    private final void MAIN_Loop()
    {
	m_iKeyStatesOld = m_iKeyStates;
	m_iKeyStates = getKeyStates();
	
	MainTimer.update();
	
	if (!m_bShouldPause)
	{	
            switch (m_AppState)
            {
                case 0:
                {                    
                    updateSplash();
                    updateInputSplash();
                    break;
                }
                case 1:
                {                    
                    updateCredits();
                    if (INP_AnyKeyPress())
                    {
                        m_AppState = 0;    
                    }
                    break;
                }
                case 2:
                {                    
                    updateMenu();
                    updateInputMenu();
                    break;
                }
                case 3:
                {                    
                    updateGame();
                    updateInputGame();
                    break;
                }
                case 4:
                {                    
                    updateLogo(); 
                    
                    if ((MainTimer.TotalMillis - m_lLastFrameUpdate > 2300)/* || (INP_AnyKeyPress())*/)
                    {
                        m_AppState = 5;  
                        
                        m_LogoImage = null;
                        
                        reloadMenu();
                    }         
                    
                    break;
                }                 
                case 5:
                {                    
                    updateMenuLogo(); 
                    updateInputMenuLogo();                    
                    break;
                } 
            }	    
	}	
    }
    
    private final void updateMenuLogo()
    {
        if (MainTimer.TotalMillis - m_lLastFrameUpdate > 60)	
	{    
            if (m_bSelectorDir)
            {
                m_iSelectorOffset--;
                
                if (m_iSelectorOffset <= 0)
                {
                    m_bSelectorDir = !m_bSelectorDir;
                }
                
            }
            else
            {
                m_iSelectorOffset++;
                
                if (m_iSelectorOffset >= 7)
                {
                    m_bSelectorDir = !m_bSelectorDir;
                }
            }
    
	    m_lLastFrameUpdate = MainTimer.TotalMillis;
	}
        
        int tilesAncho = (Map.ScreenWidth / 28) + 1;
        int tilesAlto = (Map.ScreenHeight / 30) + 1;
        
        for (int j=0; j<tilesAlto; j++)
        {
            for (int i=0; i<tilesAncho; i++)
            {
                GFX.drawImage(m_TileImage, 28*i, 30*j, Graphics.TOP | Graphics.LEFT);
            }            
        }     
	
	GFX.drawImage(m_BackSplashImage, Map.ScreenWidth/2, Map.ScreenHeight/2, Graphics.HCENTER | Graphics.VCENTER);

        GFX.setColor(0,0,0);
        GFX.fillRect((Map.ScreenWidth/2)-47, (Map.ScreenHeight/2)+19, 94, 59); 
        
        TheBlueFont.drawString((Map.ScreenWidth/2)-36, (Map.ScreenHeight/2)+22, "ACTIVATE");
        TheBlueFont.drawString((Map.ScreenWidth/2)-31, (Map.ScreenHeight/2)+32, "SOUNDS?");
        
        TheWhiteFont.drawString((Map.ScreenWidth/2)-13, (Map.ScreenHeight/2)+52, "YES");
        TheWhiteFont.drawString((Map.ScreenWidth/2)-8, (Map.ScreenHeight/2)+62, "NO");
        
        GFX.drawImage(m_SelectorIzqdaImage, (Map.ScreenWidth/2)-28 + m_iSelectorOffset, (Map.ScreenHeight/2)+ 53 + (m_iSelector * 10), Graphics.TOP | Graphics.RIGHT);
        GFX.drawImage(m_SelectorDchaImage, (Map.ScreenWidth/2)+45 - m_iSelectorOffset, (Map.ScreenHeight/2)+ 53 + (m_iSelector * 10), Graphics.TOP | Graphics.RIGHT);
        
	flushGraphics();      
    }
    
    private final void updateInputMenuLogo()
    {
        if (INP_KeyPress(UP_PRESSED))
	{
	    m_iSelector--;
	    if (m_iSelector<0)
		m_iSelector=0;
	}	    
	else if (INP_KeyPress(DOWN_PRESSED))
	{
	    m_iSelector++;
            if (m_iSelector>1)
		m_iSelector=1;
	}

	if (INP_KeyPress(FIRE_PRESSED))
	{
	    if (m_iSelector == 0)
	    {
		SoundEngine.ACTIVE = true;
	    }
	    else if (m_iSelector == 1)
	    {
		SoundEngine.ACTIVE = false;
	    }    
            
            m_AppState = 0;

	    m_iSelector = 0;
	}	        
    }
    
    private void updateInputGame()
    {	
	///--- estado "jugando"
	if (m_GameState == 0)
	{
	    
		
	    if (INP_KeyDown(LEFT_PRESSED))
	    {
		TheBall.VelX -= (m_iAccelerartion * MainTimer.DeltaTime);

		if (TheBall.VelX < -m_iMaxVelocity)
		{
		    TheBall.VelX = -m_iMaxVelocity;		
		}

		TheBall.fRadius -= (0.12f * MainTimer.DeltaTime);
		
		if (MainTimer.TotalMillis - m_lLastFrameUpdate > 200)	
		{    
		    TheBall.addSmoke(TheBall.PosX+6, TheBall.PosY, TheBall.VelX +50, TheBall.VelY, 5.0f);
		    m_lLastFrameUpdate = MainTimer.TotalMillis;
		}

		//TheBall.PosX -= (50 * TheTimer.DeltaTime);
	    }
	    else if (INP_KeyDown(RIGHT_PRESSED))
	    {
		TheBall.VelX += (m_iAccelerartion * MainTimer.DeltaTime);

		if (TheBall.VelX > m_iMaxVelocity)
		{
		    TheBall.VelX = m_iMaxVelocity;		
		}

		TheBall.fRadius -= (0.12f * MainTimer.DeltaTime);

		if (MainTimer.TotalMillis - m_lLastFrameUpdate > 200)	
		{    
		    TheBall.addSmoke(TheBall.PosX-6, TheBall.PosY, TheBall.VelX -50, TheBall.VelY, 5.0f);
		    m_lLastFrameUpdate = MainTimer.TotalMillis;
		}

		//TheBall.PosX += (50 * TheTimer.DeltaTime);
	    }

	    if (INP_KeyDown(UP_PRESSED))
	    {
		TheBall.VelY -= (m_iAccelerartion * MainTimer.DeltaTime);

		if (TheBall.VelY < -m_iMaxVelocity)
		{
		    TheBall.VelY = -m_iMaxVelocity;		
		}

		TheBall.fRadius -= (0.12f * MainTimer.DeltaTime);

		if (MainTimer.TotalMillis - m_lLastFrameUpdate > 200)	
		{    
		    TheBall.addSmoke(TheBall.PosX, TheBall.PosY+6, TheBall.VelX, TheBall.VelY+50, 5.0f);
		    m_lLastFrameUpdate = MainTimer.TotalMillis;
		}

		//TheBall.PosY -= (50 * TheTimer.DeltaTime);
	    }
	    else if (INP_KeyDown(DOWN_PRESSED))
	    {
		TheBall.VelY += (m_iAccelerartion * MainTimer.DeltaTime);

		if (TheBall.VelY > m_iMaxVelocity)
		{
		    TheBall.VelY = m_iMaxVelocity;		
		}

		TheBall.fRadius -= (0.12f * MainTimer.DeltaTime);

		if (MainTimer.TotalMillis - m_lLastFrameUpdate > 200)	
		{    
		    TheBall.addSmoke(TheBall.PosX, TheBall.PosY-6, TheBall.VelX, TheBall.VelY -50, 5.0f);
		    m_lLastFrameUpdate = MainTimer.TotalMillis;
		}

		//TheBall.PosY += (50 * TheTimer.DeltaTime);
	    }
	    
	    if (INP_KeyPress(GAME_A_PRESSED))
	    {
		m_GameState = 3;
	    }
	    
	    if (m_iRedShots > 0)
	    {
		if (INP_KeyPress(FIRE_PRESSED))
		{	  

		    ShotManager.addShot(TheBall.PosX - 4, TheBall.PosY - 4, TheBall.VelX * 1.5f, TheBall.VelY * 1.5f, (byte)1);
		    
		    m_iRedShots--;
		
		    if (m_iRedShots <0)
			m_iRedShots = 0;
		}
	    }
	    
	    if (m_iYellowShots > 0)
	    {
		if (INP_KeyPress(LEFT_PRESSED | RIGHT_PRESSED | UP_PRESSED | DOWN_PRESSED))
		{
		    ShotManager.addShot(TheBall.PosX - 4, TheBall.PosY - 4, TheBall.VelX * 1.5f, TheBall.VelY * 1.5f, (byte)0);		
		    
		    m_iYellowShots--;
		
		    if (m_iYellowShots <0)
			m_iYellowShots = 0;
		}	
	    }	    
	}
	
	///--- estado "terminado"
	else if (m_GameState == 1)
	{
	    
		if (INP_KeyPress(UP_PRESSED))
		{
		    m_iSelector--;
		    if (m_iSelector<0)
			m_iSelector=1;
                    
                    SoundEngine se = new SoundEngine(true);
                    se.setup(SOUNDPATH_GEMMENU);
                    se.startThread();
		
		}

		if (INP_KeyPress(DOWN_PRESSED))
		{
		    m_iSelector++;
		    m_iSelector %= 2;
                    
                    SoundEngine se = new SoundEngine(true);
                    se.setup(SOUNDPATH_GEMMENU);
                    se.startThread();
		
		}

		if (INP_KeyPress(FIRE_PRESSED))
		{
		    ///--- continuar
		    if (m_iSelector == 0)
		    {
			synchronized(this)
			{
                            
			    m_bShouldPause = true;

			    try
			    {
				wait(100);
			    }
			    catch (Exception e) { e.printStackTrace(); }

			    Map.unloadMap();		

			    TheBall.reset();
			    ShotManager.reset();

			    m_iActualLevel++;
			    m_iActualLevel %= 32;
			    
			    resetGame();

			    Map.loadMap(new DataInputStream(getClass().getResourceAsStream("maps/"+ (m_iActualLevel + 1) +".hmf")));

                            m_strGemsString = "GEMS: " + Map.GemsLeft;
                            
			    m_AppState = 3;
			    m_GameState = 0;
			    m_iSelector = 0;

			    try
			    {
				wait(100);
			    }
			    catch (Exception e) { e.printStackTrace(); }

			    m_bShouldPause = false;		    
			}	  
		    }
		    ///--- salir
		    else
		    {
                        Map.unloadMap();
                        reloadMenu();	
			m_AppState = 0;
			m_GameState = 0;
			m_iSelector = 0;			
		    }

	
		}
	        
	}
	///--- estado "pausado"
	else if (m_GameState == 3)
	{
	   
		if (INP_KeyPress(UP_PRESSED))
		{
		    m_iSelector--;
		    if (m_iSelector<0)
			m_iSelector=1;
                    
                    SoundEngine se = new SoundEngine(true);
                    se.setup(SOUNDPATH_GEMMENU);
                    se.startThread();
		
		}

		if (INP_KeyPress(DOWN_PRESSED))
		{
		    m_iSelector++;
		    m_iSelector %= 2;
                    
                    SoundEngine se = new SoundEngine(true);
                    se.setup(SOUNDPATH_GEMMENU);
                    se.startThread();
	
		}

		if (INP_KeyPress(FIRE_PRESSED))
		{
		    ///--- continuar
		    if (m_iSelector == 0)
		    {
			m_GameState = 0;	  
		    }
		    ///--- salir
		    else
		    {
                        Map.unloadMap();
                        reloadMenu();
			m_AppState = 0;
			m_GameState = 0;
			m_iSelector = 0;			
		    }

	
		}
	       
	}
	///--- estado "muerto"
	else if (m_GameState == 2)
	{
	    
		if (INP_KeyPress(UP_PRESSED))
		{
		    m_iSelector--;
		    if (m_iSelector<0)
			m_iSelector=1;
                    
                    SoundEngine se = new SoundEngine(true);
                    se.setup(SOUNDPATH_GEMMENU);
                    se.startThread();
		
		}

		if (INP_KeyPress(DOWN_PRESSED))
		{
		    m_iSelector++;
		    m_iSelector %= 2;
                    
                    SoundEngine se = new SoundEngine(true);
                    se.setup(SOUNDPATH_GEMMENU);
                    se.startThread();
	
		}

		if (INP_KeyPress(FIRE_PRESSED))
		{
		    ///--- continuar
		    if (m_iSelector == 0)
		    {
			synchronized(this)
			{
			    m_bShouldPause = true;

			    try
			    {
				wait(100);
			    }
			    catch (Exception e) { e.printStackTrace(); }

			    Map.unloadMap();		

			    TheBall.reset();
			    
			    ShotManager.reset();
			    
			    resetGame();

			    Map.loadMap(new DataInputStream(getClass().getResourceAsStream("maps/"+ (m_iActualLevel + 1) +".hmf")));

                            m_strGemsString = "GEMS: " + Map.GemsLeft;
                            
			    m_AppState = 3;
			    m_GameState = 0;
			    m_iSelector = 0;

			    try
			    {
				wait(100);
			    }
			    catch (Exception e) { e.printStackTrace(); }

			    m_bShouldPause = false;		    
			}	  
		    }
		    ///--- salir
		    else
		    {
                        Map.unloadMap();
                        reloadMenu();
                        
			m_AppState = 0;
			m_GameState = 0;
			m_iSelector = 0;			
		    }

		}
	   
	}
    }
    
    private void unloadMenu()
    {
        m_BackSplashImage = null;
        m_BackMenuImage = null;
        m_TileImage = null;  

        m_LevelImage[0] = null;  
        m_LevelImage[1] = null;
        m_LevelImage[2] = null;
        m_LevelImage[3] = null;
        m_LevelImage[4] = null;

        m_LevelSquareImage[0] = null;  
        m_LevelSquareImage[1] = null;
        m_LevelSquareImage[2] = null;
        m_LevelSquareImage[3] = null;
        m_LevelSquareImage[4] = null;

        m_LevelSquareImage[5] = null;  
        m_LevelSquareImage[6] = null;
        m_LevelSquareImage[7] = null;
        m_LevelSquareImage[8] = null;
        m_LevelSquareImage[9] = null;

        m_SelectorMenuImage = null;

        ///--- sprite del selector
        m_SelectorSprite = null;
        
        System.gc();
        
    }
    
    private void reloadMenu()
    {
        try
        {                            
            m_BackSplashImage = Image.createImage("/gfx/portada.png");
            m_BackMenuImage = Image.createImage("/gfx/menu_logo.png");
            m_TileImage = Image.createImage("/gfx/tile.png");  

            m_LevelImage[0] = Image.createImage("/gfx/m1.png");  
            m_LevelImage[1] = Image.createImage("/gfx/m2.png");
            m_LevelImage[2] = Image.createImage("/gfx/m3.png");
            m_LevelImage[3] = Image.createImage("/gfx/m4.png");
            m_LevelImage[4] = Image.createImage("/gfx/m5.png");

            m_LevelSquareImage[0] = Image.createImage("/gfx/m1b.png");  
            m_LevelSquareImage[1] = Image.createImage("/gfx/m2b.png");
            m_LevelSquareImage[2] = Image.createImage("/gfx/m3b.png");
            m_LevelSquareImage[3] = Image.createImage("/gfx/m4b.png");
            m_LevelSquareImage[4] = Image.createImage("/gfx/m5b.png");

            m_LevelSquareImage[5] = Image.createImage("/gfx/m1a.png");  
            m_LevelSquareImage[6] = Image.createImage("/gfx/m2a.png");
            m_LevelSquareImage[7] = Image.createImage("/gfx/m3a.png");
            m_LevelSquareImage[8] = Image.createImage("/gfx/m4a.png");
            m_LevelSquareImage[9] = Image.createImage("/gfx/m5a.png");

            m_SelectorMenuImage = Image.createImage("/gfx/selec_menu.png");

            ///--- sprite del selector
            m_SelectorSprite = new Sprite(m_SelectorMenuImage, 13, 13);
            m_SelectorSprite.setFrameSequence(SELECTOR_SEQUENCE);
            m_SelectorSprite.setFrame(0);
        }
        catch(IOException e) { }        
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    private void updateLogicSector(MapSector s)
    {
	///--- objetos estáticos
	for (int i=0; i < s.staticObjectsIndices.length; i++)
	{
	    
	    StaticObject so =Map.StaticObjectsArray[s.staticObjectsIndices[i]];
	    
	    
	    if (so.disable)
		continue;
	    
	    ///--- es un item
	    if (so.type == 1)
	    {
		
		///--- es una gema azul
		if (so.id == 2)
		{
		    ///--- si colisiona
		    if (((so.x + 16) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 16) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > so.x) && ((TheBall.PosY + TheBall.Radius) > so.y))
		    {
			so.disable = true;
			Map.GemsLeft--;
                        
                        m_strGemsString = "GEMS: " + Map.GemsLeft;
                        
                        SoundEngine se = new SoundEngine();
                        se.setup(SOUNDPATH_GEMBLUE);
                        se.startThread();                   
		    }
		}
		///--- es una gema verde
		else if (so.id == 4)
		{
		    ///--- si colisiona
		    if (((so.x + 16) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 16) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > so.x) && ((TheBall.PosY + TheBall.Radius) > so.y))
		    {
			so.disable = true;			
			SoundEngine se = new SoundEngine();
                        se.setup(SOUNDPATH_GEMGREEN);
                        se.startThread();
			TheBall.addSmallExplosion(so.x + 8, so.y + 8);
			TheBall.Radius = 8;
			TheBall.fRadius = 8.99f;
		    }
		}
		///--- es una gema roja
		else if (so.id == 3)
		{
		    ///--- si colisiona
		    if (((so.x + 16) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 16) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > so.x) && ((TheBall.PosY + TheBall.Radius) > so.y))
		    {
			so.disable = true;
			m_iRedShots += 25;
			SoundEngine se = new SoundEngine();
                        se.setup(SOUNDPATH_GEMRED);
                        se.startThread();
		    }
		}
		///--- es una gema amarilla
		else if (so.id == 5)
		{
		    ///--- si colisiona
		    if (((so.x + 16) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 16) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > so.x) && ((TheBall.PosY + TheBall.Radius) > so.y))
		    {
			so.disable = true;
			m_iYellowShots += 45;
			SoundEngine se = new SoundEngine();
                        se.setup(SOUNDPATH_GEMYELLOW);
                        se.startThread();
		    }
		}
		///--- es la salida
		else if (so.id == 0)
		{
		    ///--- si colisiona
		    if (((so.x + 12) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 12) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > (so.x + 4)) && ((TheBall.PosY + TheBall.Radius) > (so.y + 4)))
		    {
			if (Map.GemsLeft == 0 && m_GameState==0)
			{
			    ///--- ponemos el estado "terminado"
			    m_GameState = 1;
                            
                            int levelx = (m_iActualLevel+1) % 8;
                            int levely = (m_iActualLevel+1) / 8;
                            
                            m_bLockedLevels[levelx][levely]=true;
                            
                            saveLevels();

			    SoundEngine se = new SoundEngine();
                            se.setup(SOUNDPATH_GEMEXPLO);
                            se.startThread();
			    TheBall.addBigExplosion(TheBall.PosX, TheBall.PosY);
			    TheBall.addSmallExplosion(TheBall.PosX, TheBall.PosY);
			}
		    }
		}
		///--- es el impulsor
		else if (so.id == 6)
		{
		    ///--- si colisiona
		    if (((so.x + 12) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 12) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > (so.x + 4)) && ((TheBall.PosY + TheBall.Radius) > (so.y + 4)))
		    {
			switch (so.rotation)
			{
			    case 0:
			    {
				TheBall.VelX += 150.0f * MainTimer.DeltaTime;

				if (TheBall.VelX > m_iMaxVelocity)
				{
				    TheBall.VelX = m_iMaxVelocity;		
				}
				break;
			    }
			    case 1:
			    {
				TheBall.VelY += 150.0f * MainTimer.DeltaTime;

				if (TheBall.VelY > m_iMaxVelocity)
				{
				    TheBall.VelY = m_iMaxVelocity;		
				}
				break;
			    }
			    case 2:
			    {
				TheBall.VelX -= 150.0f * MainTimer.DeltaTime;

				if (TheBall.VelX < -m_iMaxVelocity)
				{
				    TheBall.VelX = -m_iMaxVelocity;		
				}
				break;
			    }
			    case 3:
			    {
				TheBall.VelY -= 150.0f * MainTimer.DeltaTime;
				
				if (TheBall.VelY < -m_iMaxVelocity)
				{
				    TheBall.VelY = -m_iMaxVelocity;		
				} 
				break;
			    }
			}	
		    }
		}
		///--- es la llave roja
		else if (so.id == 7 && !m_bHasRedKey)
		{
		    ///--- si colisiona
		    if (((so.x + 16) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 16) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > so.x) && ((TheBall.PosY + TheBall.Radius) > so.y))
		    {
			m_bHasRedKey = true;
			
			so.disable = true;	
                        
                        SoundEngine se = new SoundEngine();
                        se.setup(SOUNDPATH_GEMRED);
                        se.startThread();
		    }
		}
		///--- es la llave azul
		else if (so.id == 8 && !m_bHasBlueKey)
		{
		    ///--- si colisiona
		    if (((so.x + 16) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 16) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > so.x) && ((TheBall.PosY + TheBall.Radius) > so.y))
		    {
			m_bHasBlueKey = true;
			
			so.disable = true;
                        
                        SoundEngine se = new SoundEngine();
                        se.setup(SOUNDPATH_GEMRED);
                        se.startThread();
		    }
		}
	    }
	    ///--- es un trasportador
	    else if (so.type == 3)
	    {
		if (MainTimer.TotalMillis - m_lLastUpdateTrans > 3000)	
		{		    		
		    ///--- si colisiona
		    if (((so.x + 10) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 10) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > (so.x + 6)) && ((TheBall.PosY + TheBall.Radius) > (so.y + 6)))
		    {
			m_lLastUpdateTrans = MainTimer.TotalMillis;
			
			if (Map.m_iTranportPaths[so.rotation].origin == so)
			{
			    TheBall.PosX = Map.m_iTranportPaths[so.rotation].destination.x + 8;
			    TheBall.PosY = Map.m_iTranportPaths[so.rotation].destination.y + 8;
			}
			else
			{
			    TheBall.PosX = Map.m_iTranportPaths[so.rotation].origin.x + 8;
			    TheBall.PosY = Map.m_iTranportPaths[so.rotation].origin.y + 8;
			}

			TheBall.addSmallExplosion(TheBall.PosX, TheBall.PosY);
                        
                        SoundEngine se = new SoundEngine();
                        se.setup(SOUNDPATH_GEMMENU);
                        se.startThread();
		    }
		}
	    }
	    ///--- es un enemigo
	    else if (so.type == 2)
	    {
		///--- si colisiona
		if (((so.x + 10) > (TheBall.PosX - TheBall.Radius)) && ((so.y + 10) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > (so.x + 6)) && ((TheBall.PosY + TheBall.Radius) > (so.y + 6)))
		{
		    TheBall.addSmallExplosion(TheBall.PosX, TheBall.PosY);
		    TheBall.addBigExplosion(so.x + 8, so.y + 8);
		    so.disable = true;
		    
		    TheBall.fRadius -= 1.2f;
		    TheBall.Radius = (int)TheBall.fRadius;
                    
                    SoundEngine se = new SoundEngine();
                    se.setup(SOUNDPATH_GEMEXPLO);
                    se.startThread();
		}		
	    }
	}
	
	s.dynamicObjects.Iterator = s.dynamicObjects.StartNode;
		    
	for (int i = 0; i < s.dynamicObjects.ElementCount; i++)
	{	   
	    boolean b = s.dynamicObjects.Iterator.TheObject.getClass().isInstance(m_TempBounceEnemy);
	   	    
	    ///--- es un enemigo que rebota
	    if (b)
	    {
		BounceEnemy obj = (BounceEnemy)s.dynamicObjects.Iterator.TheObject;
		
		///--- si colisiona
		if (((obj.x + 16) > (TheBall.PosX - TheBall.Radius)) && ((obj.y + 16) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > obj.x) && ((TheBall.PosY + TheBall.Radius) > obj.y))
		{
		    //Sounds.startPlayer(Sounds.HurtPlayer);
		    //a.TheDisplay.vibrate(500);
                    
                    SoundEngine se = new SoundEngine();
                    se.setup(SOUNDPATH_GEMEXPLO);
                    se.startThread();    
		    
		    TheBall.addMediumExplosion(TheBall.PosX, TheBall.PosY);
		    TheBall.addSmallExplosion(obj.x + 8, obj.y + 8);
		    TheBall.fRadius -= 0.7f;
		    obj.disable = true;
		    
		    int dirx = (int)(TheBall.PosX) - obj.x;
		    dirx *= 10;
		    int diry = (int)(TheBall.PosY) - obj.y;
		    diry *= 10;
		    
		    float dis = (float)(Math.sqrt((dirx * dirx) + (diry * diry)));
		    float dirxnor = dirx / dis;
		    float dirynor = diry / dis;
		    
		    TheBall.VelX += (dirxnor * 70);
		    TheBall.VelY += (dirynor * 70);
		    
		    if (TheBall.VelX < -m_iMaxVelocity)
		    {
			TheBall.VelX = -m_iMaxVelocity;		
		    }  
		    else if (TheBall.VelX > m_iMaxVelocity)
		    {
			TheBall.VelX = m_iMaxVelocity;		
		    }
		    
		    if (TheBall.VelY < -m_iMaxVelocity)
		    {
			TheBall.VelY = -m_iMaxVelocity;		
		    }  
		    else if (TheBall.VelY > m_iMaxVelocity)
		    {
			TheBall.VelY = m_iMaxVelocity;		
		    }
		}    
	    }
	    else
	    {
		b = s.dynamicObjects.Iterator.TheObject.getClass().isInstance(m_TempSearchEnemy);
		
		if (b)
		{
		    SearchEnemy obj = (SearchEnemy)s.dynamicObjects.Iterator.TheObject;
		
		    ///--- si colisiona
		    if (((obj.x + 16) > (TheBall.PosX - TheBall.Radius)) && ((obj.y + 16) > (TheBall.PosY - TheBall.Radius)) && ((TheBall.PosX + TheBall.Radius) > obj.x) && ((TheBall.PosY + TheBall.Radius) > obj.y))
		    {
			//Sounds.startPlayer(Sounds.HurtPlayer);
			//a.TheDisplay.vibrate(500);
                        
                        SoundEngine se = new SoundEngine();
                        se.setup(SOUNDPATH_GEMEXPLO);
                        se.startThread(); 

			TheBall.addMediumExplosion(TheBall.PosX, TheBall.PosY);
			TheBall.addSmallExplosion(obj.x + 8, obj.y + 8);
			TheBall.fRadius -= 0.6f;
			obj.disable = true;

			int dirx = (int)(TheBall.PosX) - obj.x;
			dirx *= 10;
			int diry = (int)(TheBall.PosY) - obj.y;
			diry *= 10;

			float dis = (float)(Math.sqrt((dirx * dirx) + (diry * diry)));
			float dirxnor = dirx / dis;
			float dirynor = diry / dis;

			TheBall.VelX += (dirxnor * 70);
			TheBall.VelY += (dirynor * 70);

			if (TheBall.VelX < -m_iMaxVelocity)
			{
			    TheBall.VelX = -m_iMaxVelocity;		
			}  
			else if (TheBall.VelX > m_iMaxVelocity)
			{
			    TheBall.VelX = m_iMaxVelocity;		
			}

			if (TheBall.VelY < -m_iMaxVelocity)
			{
			    TheBall.VelY = -m_iMaxVelocity;		
			}  
			else if (TheBall.VelY > m_iMaxVelocity)
			{
			    TheBall.VelY = m_iMaxVelocity;		
			}
		    }    		    
		}
	    }
	    
	    s.dynamicObjects.Iterator =  s.dynamicObjects.Iterator.Next;    
	}
    }
    
    private void updateLogic()
    {	
	updateLogicSector(TheBall.Sector1);
	
	if (TheBall.Sector2 != null)
	    updateLogicSector(TheBall.Sector2);
	
	if (TheBall.Sector3 != null)
	    updateLogicSector(TheBall.Sector3);
	
	if (TheBall.Sector4 != null)
	    updateLogicSector(TheBall.Sector4);
	
	if (TheBall.fRadius < 4.0f)
	{
	    m_GameState = 2;
	    //Sounds.startPlayer(Sounds.BlueGemPlayer);
	    TheBall.addBigExplosion(TheBall.PosX, TheBall.PosY);
            
            SoundEngine se = new SoundEngine();
            se.setup(SOUNDPATH_GEMEXPLO);
            se.startThread(); 
	}
    }
    
    private void updateGame()
    {

//#if Testing
//# 	
//# 	try {
//# 	
//#endif
	
			
	Map.clearSectors();
	
	if (m_GameState == 0)
	{	    
	    TheBall.update();	
	
	    Enemies.updateAll();
	
	    ShotManager.update();
	
	    updateLogic();
	}
	
	Map.updateCamera(TheBall);
	
	///////////////
	///--- Render
	///////////////	
			
	Map.renderMap();
	
	TheBall.renderSmoke();
	
	Map.renderOverMap();
	
	if (m_GameState != 0)
	{
	    if (MainTimer.TotalMillis - m_lLastFrameUpdate > 50)	
	    {    
                if (m_bSelectorDir)
                {
                    m_iSelectorOffset--;

                    if (m_iSelectorOffset <= 0)
                    {
                        m_bSelectorDir = !m_bSelectorDir;
                    }

                }
                else
                {
                    m_iSelectorOffset++;

                    if (m_iSelectorOffset >= 7)
                    {
                        m_bSelectorDir = !m_bSelectorDir;
                    }
                }
                
		m_lLastFrameUpdate = MainTimer.TotalMillis;
	    }

	    if (m_GameState == 1)
	    {
		TheWhiteFont.drawString((Map.ScreenWidth/2)-36, Map.ScreenHeight/2, "CONTINUE");
		TheWhiteFont.drawString((Map.ScreenWidth/2)-18, (Map.ScreenHeight/2) + 12, "EXIT");
                
                GFX.drawImage(m_SelectorIzqdaImage, (Map.ScreenWidth/2)-48 + m_iSelectorOffset, (Map.ScreenHeight/2) + (m_iSelector * 12), Graphics.TOP | Graphics.RIGHT);
                GFX.drawImage(m_SelectorDchaImage, (Map.ScreenWidth/2)+65 - m_iSelectorOffset, (Map.ScreenHeight/2) + (m_iSelector * 12), Graphics.TOP | Graphics.RIGHT);

	    }
	    else if (m_GameState == 2)
	    {
		TheWhiteFont.drawString((Map.ScreenWidth/2)-31, Map.ScreenHeight/2, "RESTART");
		TheWhiteFont.drawString((Map.ScreenWidth/2)-18, (Map.ScreenHeight/2) + 12, "EXIT");
                
                GFX.drawImage(m_SelectorIzqdaImage, (Map.ScreenWidth/2)-53 + m_iSelectorOffset, (Map.ScreenHeight/2) + (m_iSelector * 12), Graphics.TOP | Graphics.RIGHT);
                GFX.drawImage(m_SelectorDchaImage, (Map.ScreenWidth/2)+70 - m_iSelectorOffset, (Map.ScreenHeight/2) + (m_iSelector * 12), Graphics.TOP | Graphics.RIGHT);

	    }
	    else if (m_GameState == 3)
	    {
		TheWhiteFont.drawString((Map.ScreenWidth/2)-36, Map.ScreenHeight/2, "CONTINUE");
		TheWhiteFont.drawString((Map.ScreenWidth/2)-18, (Map.ScreenHeight/2) + 12, "EXIT");

                GFX.drawImage(m_SelectorIzqdaImage, (Map.ScreenWidth/2)-48 + m_iSelectorOffset, (Map.ScreenHeight/2) + (m_iSelector * 12), Graphics.TOP | Graphics.RIGHT);
                GFX.drawImage(m_SelectorDchaImage, (Map.ScreenWidth/2)+65 - m_iSelectorOffset, (Map.ScreenHeight/2) + (m_iSelector * 12), Graphics.TOP | Graphics.RIGHT);

            }
	}	
	
        if (Map.GemsLeft > 0)
        {            
            //TheBlueFont.drawString(0, 0, "GEMS: " + Map.GemsLeft);
            TheBlueFont.drawString(0, 0, m_strGemsString);
        }
        else
        {
            TheBlueFont.drawString(0, 0, "FIND EXIT");            
        }
	
	//#if Testing
//# 	
//# 	GFX.setColor(0,200,0);
//# 	GFX.drawString(Float.toString(MainTimer.FPS), Map.ScreenWidth, 0, Graphics.RIGHT|Graphics.TOP);
//# 	
//# 	//GFX.setColor(255,0,0);	
//# 	//GFX.drawString(Float.toString(TheBall.fRadius), 0, 30, Graphics.LEFT|Graphics.TOP);
//# 	
//#         //GFX.drawString(Float.toString(Map.CameraX) + ", " + Float.toString(Map.CameraY), 0, 30, Graphics.LEFT|Graphics.TOP);
//# 	//GFX.drawString(Integer.toString(Map.m_RenderVector.ElementCount), 0, 30, Graphics.LEFT|Graphics.TOP);
//# 
//#endif
	
	flushGraphics();
	
	/*
	synchronized(this)
	{
	    try
	    {
		wait(1);
	    }
	    catch (Exception e) { e.printStackTrace(); }
	}*/
	
//#if Testing
//# 	
//# 	}
//# 	catch(Exception e)
//# 	{
//# 	    e.printStackTrace();
//# 	}
//# 	
//#endif
	
    } 
    
    private final static void FONT_DrawStringSmallClipped(Graphics g, String str, int x, int y, boolean center)
    {
	int oldX = x;
	int length = str.length();
	int clipX = g.getClipX();
	int clipY = g.getClipY();
	int clipW = g.getClipWidth();
	int clipH = g.getClipHeight(); 
	
	if (center)
	{
	    x = m_iScrWdHalf - ((5 * length) >>> 1);
	}
	
	for (int i = 0; i < length; i++)
	{	        	    
	    char tmp = str.charAt(i);
	    int index = 0;	    
	    
	    ///--- es una letra
	    if (tmp > 64 && tmp < 91)
	    {
		index = (tmp - 65) + 10;		
	    }
	    ///--- es un numero
	    else if (tmp > 47 && tmp < 58)
	    {
		index = (tmp - 48);	
	    }
	    else
	    {
		switch (tmp)
		{
		    case ' ':
		    {
			x += 5;
			continue;
		    }
		    case '.':
		    {
			index = 36;
			break;
		    }
		    case ',':
		    {
			index = 38;
			break;
		    }
		    case '\n':
		    {
			x = oldX;
			y += 7;
			continue;
		    }
		    default:
		    {
			// 'Ñ':		    
			index = 37;
			break;		    
		    }
		}
	    }	
	    
	    g.clipRect(x, y, 4, 6);
	    g.drawImage(m_BitmapFontImage3, x, y - (index * 6), Graphics.LEFT | Graphics.TOP);
	    g.setClip(clipX, clipY, clipW, clipH);
	    x += 5;
	} 
	
	g.setClip(clipX, clipY, clipW, clipH);
    }
    
    private void updateLogo()
    {
	GFX.setColor(135,92,165);
        GFX.fillRect(0, 0, Map.ScreenWidth, Map.ScreenHeight);
	
	GFX.drawImage(m_LogoImage, Map.ScreenWidth/2, Map.ScreenHeight/2, Graphics.HCENTER | Graphics.VCENTER);
	
	flushGraphics();    
    }
    
    private void updateCredits()
    { 
        if (MainTimer.TotalMillis - m_lLastFrameUpdate > 50)	
	{                
            m_iCreditsOffset++;
            
            if (m_iCreditsOffset > 220)
                m_iCreditsOffset = 0;
	    
	    m_lLastFrameUpdate = MainTimer.TotalMillis;
	}
        
        int tilesAncho = (Map.ScreenWidth / 28) + 1;
        int tilesAlto = (Map.ScreenHeight / 30) + 1;
        
        for (int j=0; j<tilesAlto; j++)
        {
            for (int i=0; i<tilesAncho; i++)
            {
                GFX.drawImage(m_TileImage, 28*i, 30*j, Graphics.TOP | Graphics.LEFT);
            }            
        }     
	
	GFX.drawImage(m_BackSplashImage, Map.ScreenWidth/2, Map.ScreenHeight/2, Graphics.HCENTER | Graphics.VCENTER);

        GFX.setColor(0,0,0);
        GFX.fillRect((Map.ScreenWidth/2)-47, (Map.ScreenHeight/2)+19, 94, 59);
       
        GFX.setClip((Map.ScreenWidth/2)-47, (Map.ScreenHeight/2)+19, 94, 59);				    
	//GFX.drawImage(m_imgFX2, m_iScrWdHalf - 50, m_iScrHgHalf - m_iCreditosPos2, Graphics.TOP | Graphics.LEFT);
			
        for (int f=0; f<18;f++)
        {      
            FONT_DrawStringSmallClipped(GFX, STRING_CREDITS[f], 0, m_iScrHgHalf  + (10 * f) + 80 - m_iCreditsOffset, true);
        }	
        
        GFX.setClip(0, 0, Map.ScreenWidth, Map.ScreenHeight);
	flushGraphics();    
    }
    
    private void updateInputSplash()
    {	
	if (INP_KeyPress(UP_PRESSED))
	{
	    m_iSelector--;
	    if (m_iSelector<0)
		m_iSelector=2;
            
            SoundEngine se = new SoundEngine(true);
            se.setup(SOUNDPATH_GEMMENU);
            se.startThread();
	}	    
	else if (INP_KeyPress(DOWN_PRESSED))
	{
	    m_iSelector++;
	    m_iSelector %= 3;
            
            SoundEngine se = new SoundEngine(true);
            se.setup(SOUNDPATH_GEMMENU);
            se.startThread();
	}

	if (INP_KeyPress(FIRE_PRESSED))
	{
	    if (m_iSelector == 0)
	    {
		///--- empezar
		m_AppState = 2;
	    }
	    else if (m_iSelector == 1)
	    {
		///--- creditos
		m_AppState = 1;
                m_iCreditsOffset=0;
	    }
	    else if (m_iSelector == 2)
	    {
		///--- salir
		a.TheMidlet.destroyApp(true);
	    }

	    m_iSelector = 0;
	}	 
    }
    
    private void updateInputMenu()
    {
        boolean mod = false;
      
        if (INP_KeyPress(UP_PRESSED))
        {
            if (m_iMenuSelectorY > 0)
                m_iMenuSelectorY--;	

            mod=true;
            
            SoundEngine se = new SoundEngine(true);
            se.setup(SOUNDPATH_GEMMENU);
            se.startThread();
        }

        if (INP_KeyPress(DOWN_PRESSED))
        {
            if (m_iMenuSelectorY < 3)
                m_iMenuSelectorY++;	
            mod=true;
            
            SoundEngine se = new SoundEngine(true);
            se.setup(SOUNDPATH_GEMMENU);
            se.startThread();
        }

        if (INP_KeyPress(LEFT_PRESSED))
        {
            if (m_iMenuSelectorX > 0)
                m_iMenuSelectorX--;	
            mod=true;
            
            SoundEngine se = new SoundEngine(true);
            se.setup(SOUNDPATH_GEMMENU);
            se.startThread();
        }

        if (INP_KeyPress(RIGHT_PRESSED))
        {
            if (m_iMenuSelectorX < 7)
                m_iMenuSelectorX++;	
            mod=true;
            
            SoundEngine se = new SoundEngine(true);
            se.setup(SOUNDPATH_GEMMENU);
            se.startThread();
        }
        
         m_strLevelString = "LEVEL " + (m_iMenuSelectorY + 1) + "-" + (m_iMenuSelectorX + 1); 
        	    
        if (INP_KeyPress(FIRE_PRESSED))
        {
            if (this.m_bLockedLevels[m_iMenuSelectorX][m_iMenuSelectorY])
            {
                unloadMenu();		

                TheBall.reset();

                ShotManager.reset();

                resetGame();

                Map.loadMap(new DataInputStream(getClass().getResourceAsStream("maps/" + ((m_iMenuSelectorY*8)+m_iMenuSelectorX+1) + ".hmf")));

                m_iActualLevel = ((m_iMenuSelectorY*8)+m_iMenuSelectorX);

                m_strGemsString = "GEMS: " + Map.GemsLeft;

                m_AppState = 3;
                m_iSelector = 0;	
            }
        }
	      
    }
    
    
    private void resetGame()
    {
	m_bHasRedKey = false;
	m_bHasBlueKey = false;
	m_iRedShots = 0;
	m_iYellowShots = 0;
    }
    
    private void updateMenu()
    {	
	if (MainTimer.TotalMillis - m_lLastFrameUpdate > 200)	
	{    
	    m_SelectorSprite.nextFrame();
	    m_lLastFrameUpdate = MainTimer.TotalMillis;
	}
	
	int tilesAncho = (Map.ScreenWidth / 28) + 1;
        int tilesAlto = (Map.ScreenHeight / 30) + 1;
        
        for (int j=0; j<tilesAlto; j++)
        {
            for (int i=0; i<tilesAncho; i++)
            {
                GFX.drawImage(m_TileImage, 28*i, 30*j, Graphics.TOP | Graphics.LEFT);
            }            
        }     
	
	GFX.drawImage(m_BackMenuImage, Map.ScreenWidth/2, (Map.ScreenHeight/2)-80, Graphics.HCENTER | Graphics.TOP);

        GFX.setColor(0,0,0);
        GFX.fillRect((Map.ScreenWidth/2)-63, (Map.ScreenHeight/2)+14, 126, 65);
        
        GFX.setColor(142,81,108);
        GFX.drawRect((Map.ScreenWidth/2)-64, (Map.ScreenHeight/2)+14, 127, 65);
        
        for (int i=0; i<4; i++)
        {
            GFX.drawImage(m_LevelImage[i], (Map.ScreenWidth/2)-62, (Map.ScreenHeight/2)+16 + (i*16), Graphics.TOP | Graphics.LEFT);
        }
        
        for (int j=0; j<4; j++)
        {
            for (int i=0; i<8; i++)
            {
                GFX.drawImage(m_LevelSquareImage[j + (m_bLockedLevels[i][j] ? 5 : 0)], (Map.ScreenWidth/2)-50 + (i*14), (Map.ScreenHeight/2)+16 + (j*16), Graphics.TOP | Graphics.LEFT);
                
                if (m_bLockedLevels[i][j])
                {
                    TheWhiteFont.drawString((Map.ScreenWidth/2)-48 + (i*14), (Map.ScreenHeight/2)+18 + (j*16), Integer.toString(i+1));
                }
            }
        }

        m_SelectorSprite.setPosition((Map.ScreenWidth/2)-50 + (m_iMenuSelectorX*14), (Map.ScreenHeight/2)+16 + (m_iMenuSelectorY*16));
	m_SelectorSprite.paint(GFX);         
          
        if (m_bLockedLevels[m_iMenuSelectorX][m_iMenuSelectorY])
        {            
            TheBlueFont.drawString((Map.ScreenWidth/2)-36, (Map.ScreenHeight/2)+4, m_strLevelString);
        }
        else
        {               
            TheBlueFont.drawString((Map.ScreenWidth/2)-25, (Map.ScreenHeight/2)+4, "LOCKED");
        }
	
	flushGraphics();	
    }
    
    private void updateSplash()
    {	
	
	if (MainTimer.TotalMillis - m_lLastFrameUpdate > 60)	
	{    
            if (m_bSelectorDir)
            {
                m_iSelectorOffset--;
                
                if (m_iSelectorOffset <= 0)
                {
                    m_bSelectorDir = !m_bSelectorDir;
                }
                
            }
            else
            {
                m_iSelectorOffset++;
                
                if (m_iSelectorOffset >= 7)
                {
                    m_bSelectorDir = !m_bSelectorDir;
                }
            }
    
	    m_lLastFrameUpdate = MainTimer.TotalMillis;
	}
	
	//GFX.setColor(0,0,0);
        //GFX.fillRect(0, 0, Map.ScreenWidth, Map.ScreenHeight);
        
        int tilesAncho = (Map.ScreenWidth / 28) + 1;
        int tilesAlto = (Map.ScreenHeight / 30) + 1;
        
        for (int j=0; j<tilesAlto; j++)
        {
            for (int i=0; i<tilesAncho; i++)
            {
                GFX.drawImage(m_TileImage, 28*i, 30*j, Graphics.TOP | Graphics.LEFT);
            }            
        }     
	
	GFX.drawImage(m_BackSplashImage, Map.ScreenWidth/2, Map.ScreenHeight/2, Graphics.HCENTER | Graphics.VCENTER);
	
        if (m_iSelector == 0)
        {
            TheWhiteFont.drawString((Map.ScreenWidth/2)-22, (Map.ScreenHeight/2)+ 27, "START");
        }
        else
        {
            TheBlueFont.drawString((Map.ScreenWidth/2)-22, (Map.ScreenHeight/2)+ 27, "START");
        }
        if (m_iSelector == 1)
        {
            TheWhiteFont.drawString((Map.ScreenWidth/2)-31, (Map.ScreenHeight/2) + 45, "CREDITS");
        }
        else
        {
            TheBlueFont.drawString((Map.ScreenWidth/2)-31, (Map.ScreenHeight/2) + 45, "CREDITS");
        }
        if (m_iSelector == 2)
        {
            TheWhiteFont.drawString((Map.ScreenWidth/2)-18, (Map.ScreenHeight/2) + 64, "EXIT");            
        }
        else
        {
            TheBlueFont.drawString((Map.ScreenWidth/2)-18, (Map.ScreenHeight/2) + 64, "EXIT");
        }
        
        GFX.drawImage(m_SelectorIzqdaImage, (Map.ScreenWidth/2)-38 + m_iSelectorOffset, (Map.ScreenHeight/2)+ 28 + (m_iSelector * 18), Graphics.TOP | Graphics.RIGHT);

        GFX.drawImage(m_SelectorDchaImage, (Map.ScreenWidth/2)+55 - m_iSelectorOffset, (Map.ScreenHeight/2)+ 28 + (m_iSelector * 18), Graphics.TOP | Graphics.RIGHT);

	
     
	
	flushGraphics();
    }
       
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public final void run()
    {
//#if Testing
//# 	try
//# 	{
//#endif
	    UTIL_OutputDebugString("Thread run");
	    
	    Thread.yield();
	    
	    MAIN_Init();
	    
	    MainTimer.start();
            
            m_lLastFrameUpdate = MainTimer.TotalMillis;
	    
	    UTIL_OutputDebugString("Iniciando bucle");
	 	    
	    while (true)
	    {		
		Thread.yield();	
		
		if (!m_bPaused)
		{		    
		    MAIN_Loop();    
		}	    
	    }
	  
//#if Testing
//# 	}
//# 	catch (Exception e)
//# 	{	   
//# 	    UTIL_OutputDebugString("Error en run: " + e.getMessage() + "\n");
//# 	    e.printStackTrace();	    	    
//# 	}
//#endif
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public final static void UTIL_OutputDebugString(String str)
    {
	//#if Testing
//# 	System.out.println("DEBUG: " + str);
	//#endif
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    protected final void sizeChanged(int w, int h)
    {
	UTIL_OutputDebugString("Evento sizeChanged: " + w + ", " + h);
	
	m_iScrWd = w;
	m_iScrHg = h;
	
	m_iScrWdHalf = m_iScrWd / 2;    
	m_iScrHgHalf = m_iScrHg / 2;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    protected final void hideNotify()
    {
	UTIL_OutputDebugString("Evento hideNotify");
	
	m_bPaused = true;
	System.gc();
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    protected final void showNotify()
    {
	UTIL_OutputDebugString("Evento showNotify");
	
	m_bPaused = false;
	System.gc();
    }        
       
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
        
    private final static boolean INP_KeyDown(int key)
    {
	return ((key & m_iKeyStates) != 0);
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    private final static boolean INP_AnyKeyDown()
    {
	return (m_iKeyStates != 0);
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
   
    private final static boolean INP_AnyKeyPress()
    {
	return ((m_iKeyStates != 0) && (m_iKeyStatesOld == 0));	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    private final static boolean INP_KeyPress(int key)
    {
	return (((key & m_iKeyStates) != 0) && ((key & m_iKeyStatesOld) == 0));	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    private final void INP_RetrieveSoftKeys()
    {                              
//#ifdef Nokia      
//# 	LEFT_SOFTKEY = -6;      
//# 	RIGHT_SOFTKEY = -7;      
//# 	UTIL_OutputDebugString("Softkeys de Nokia");
//#else      
	LEFT_SOFTKEY = 0;
	RIGHT_SOFTKEY = 0;

	try
	{	 
	    Class.forName("com.siemens.mp.lcdui.Image");			// Set Siemens specific keycodes
	    LEFT_SOFTKEY = -1;
	    RIGHT_SOFTKEY = -4;
	    UTIL_OutputDebugString("Softkeys de Siemens");
	} 
	catch (ClassNotFoundException ignore)
	{      
	    try
	    {	    
		Class.forName("com.motorola.phonebook.PhoneBookRecord");	// Set Motorola specific keycodes
	    
		if (getKeyName(-21).toUpperCase().indexOf("SOFT")>=0)
		{	       
		    LEFT_SOFTKEY=-21;
		    RIGHT_SOFTKEY=-22; 
		    
		    UTIL_OutputDebugString("Softkeys de Motorola (negativas)");
		} 
		else
		{	       
		    LEFT_SOFTKEY=21;	       
		    RIGHT_SOFTKEY=22;	
		    
		    UTIL_OutputDebugString("Softkeys de Motorola (positivas)");
		}	 
	    }
	    catch (ClassNotFoundException ignore2)
	    {      	    
		boolean found = false;
		
		// check for often used values
		// This fixes bug with some Sharp phones and others	    
		try
		{	       
		    if (getKeyName(21).toUpperCase().indexOf("SOFT")>=0)	// Check for "SOFT" in name description
		    {	 
			LEFT_SOFTKEY=21;	    // check for the 1st softkey		  
			RIGHT_SOFTKEY=22;	    // check for 2nd softkey		  
			found=true;	       
		    }
	      
		    if (getKeyName(-6).toUpperCase().indexOf("SOFT")>=0)	// Check for "SOFT" in name description
		    {         
		  
			LEFT_SOFTKEY=-6;	    // check for the 1st softkey		  
			RIGHT_SOFTKEY=-7;	    // check for 2nd softkey		  
			found=true;	       
		    }	    
		}
		catch(Exception e) {}
		
		if (!found)
		{
		    for (int i=-127;i<127;i++)					// run thru all the keys
		    {                 
			try 
			{		  
			    if (getKeyName(i).toUpperCase().indexOf("SOFT")>=0)	// Check for "SOFT" in name description
			    {         		     
				if (getKeyName(i).indexOf("1")>=0)			// check for the 1st softkey
				    LEFT_SOFTKEY=i;         

				if (getKeyName(i).indexOf("2")>=0)			// check for 2nd softkey
				    RIGHT_SOFTKEY=i;   
				
				UTIL_OutputDebugString("Softkey encontrada por método de búsqueda");
			    }	       
			}
			catch(Exception e)						// Sony calls exception on some keys
			{                                      		  
			    LEFT_SOFTKEY=-6;					// including softkeys
			    RIGHT_SOFTKEY=-7;					// bugfix is to set the values ourself	  			    		    
			}	    
		    }
		}
		else
		{
		    UTIL_OutputDebugString("Softkeys encontradas por método de búsqueda");
		}
	    }	
	}
//#endif
    } 
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    /*
    private final static void FONT_DrawString(Graphics g, String str, int x, int y, boolean center, int font)
    {
	int length = str.length();
	int clipX = g.getClipX();
	int clipY = g.getClipY();
	int clipW = g.getClipWidth();
	int clipH = g.getClipHeight();
	
	if (center)
	{
	    x = m_iScrWdHalf - ((length * 9) >>> 1);
	} 	   
	
	for (int i = 0; i < length; i++)
	{	        	    
	    char tmp = str.charAt(i);
	    int index = tmp - 33;
	    
	    ///--- es una letra
	    if (tmp > 32 && tmp < 96)
	    {
		g.setClip(x, y, 9, 8);
		g.drawImage(m_imgFont1, x - ((index & 0xF) * 9), y - ((index >>> 4) << 3), Graphics.LEFT | Graphics.TOP);
		g.setClip(clipX, clipY, clipW, clipH);
		
		x += 9;
	    }
	    else if (tmp == ' ')
	    {
		x += 9;
		continue;		
	    }		    
	} 
	
	g.setClip(clipX, clipY, clipW, clipH);
    }
    */
}
