/*
 * BitmapFont.java
 *
 * Creado el 9 de febrero de 2006, 13:09
 *
 * Autor: Nacho Sánchez
 *
 */

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class BitmapFont extends Sprite	
{    
    private int m_iCharacterWidth;
    private int m_iCharacterHeight;
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public BitmapFont(Image theBitmapFont, int characterWidth, int characterHeight)
    {
	super(theBitmapFont, characterWidth, characterHeight);
	
	m_iCharacterWidth = characterWidth;
	m_iCharacterHeight = characterHeight;
	
	int[] sequence = new int[46];
	
	for (int i=0; i<46; i++)
	    sequence[i]=i;
	
	setFrameSequence(sequence);
	
	sequence = null;
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void drawChar(int x, int y, char theChar)
    {
	setPosition(x, y);	
	
	///--- es una letra
	if (theChar > 64 && theChar <91)
	{
	    setFrame(14 + (theChar-65));
	}
	
	paint(MainCanvas.GFX);
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void drawChars(int x, int y, char[] theChars)
    {
	for (int i=0; i<theChars.length; i++)
	{
	    setPosition(x + (m_iCharacterWidth * i), y);
	    
	    char tmp = theChars[i];
	    
	    ///--- es una letra
	    if (tmp > 64 && tmp <91)
	    {
		setFrame(14 + (tmp-65));
	    }

	    paint(MainCanvas.GFX);	    
	}	
    }
    
    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    
    public void drawString(int x, int y, String theString)
    {
	for (int i=0; i<theString.length(); i++)
	{
	    setPosition(x + (m_iCharacterWidth * i), y);
	    
	    char tmp = theString.charAt(i);
	    
	    ///--- es una letra
	    if (tmp > 64 && tmp <91)
	    {
		setFrame(14 + (tmp-65));
	    }
	    else if (tmp == ' ')
	    {
		continue;
	    }
	    ///--- es un número
	    else if (tmp > 47 && tmp <58)
	    {
		setFrame(4 + (tmp-48));
	    }
	    ///--- es , - . /
	    else if (tmp > 43 && tmp <48)
	    {
		setFrame(tmp-44);
	    }
	    ///--- es : ;
	    else if (tmp > 57 && tmp <60)
	    {
		setFrame(40 + (tmp-58));
	    }
	    else if (tmp == '?')
	    {
		setFrame(42);
	    }
	    else if (tmp == '¿')
	    {
		setFrame(44);
	    }
	    else if (tmp == '!')
	    {
		setFrame(43);
	    }
	    else if (tmp == '¡')
	    {
		setFrame(45);
	    }

	    paint(MainCanvas.GFX);	    
	}	
    }
}
