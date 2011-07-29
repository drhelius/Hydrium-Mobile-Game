/*
 * a.java
 *
 * Creado el 12 de junio de 2006, 13:14
 *
 * Autor: Nacho Sánchez
 *
 */

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public final class a extends MIDlet
{    
    public static a TheMidlet;
    public static MainCanvas TheCanvas;
    public static Display TheDisplay;
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public a()
    {
	TheMidlet = this;
	TheDisplay = Display.getDisplay(TheMidlet);	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public final void startApp()
    {
        if(TheCanvas == null)
        {
            TheCanvas = new MainCanvas();
            TheCanvas.MAIN_StartThread();
        }
	
        TheDisplay.setCurrent(TheCanvas);
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public final void pauseApp()
    {
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public final void destroyApp(boolean flag)
    {
	TheDisplay.setCurrent(null);
        notifyDestroyed();
    }
}
