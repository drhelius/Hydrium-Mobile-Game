using System;
using System.Collections.Generic;
using System.Text;

namespace Editor
{
    class ObjetoEscena
    {
        public byte tipo;   ///--- 0 es cubo, 1 es item, 2 es enemigo

        public short posX;
        public short posY;

        public byte id;

        public byte rotation;

        public bool dynamic;


        //--------------------------------------------------------------------
        // Función:    ObjetoEscena
        // Propósito:  
        // Fecha:      miércoles, 01 de febrero de 2006, 15:23:50
        //--------------------------------------------------------------------
        public ObjetoEscena()
        {
        }


        //--------------------------------------------------------------------
        // Función:    ObjetoEscena
        // Propósito:  
        // Fecha:      miércoles, 01 de febrero de 2006, 10:34:50
        //--------------------------------------------------------------------
        public ObjetoEscena(byte tipo, byte id, short x, short y, byte rotation)
        {
            this.posX = x;
            this.posY = y;
            this.id = id;
            this.rotation = rotation;
            this.tipo = tipo;
            this.dynamic = false;
        }
    }
}
