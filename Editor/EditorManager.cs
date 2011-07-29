using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace Editor
{
    class EditorManager
    {
        class CompareFileInfo : IComparer<FileInfo>
        {
            public int Compare(FileInfo x, FileInfo y)
            {                
                return x.Name.CompareTo(y.Name);                
            }
        }

        struct Sector
        { 
            public List<short> staticObjectsIndices;
        };

        struct SECTOR_Lista
        {
            public int numCubos;
            public int numObjs;

            public int[] IndCub; //25
            public int[] IndObj; //100
        };

        struct CUBO_Lista
        {
            public int posX;
            public int posY;

            public int tex;
            public int texLat;

            public bool[] lados; //4
        };

        struct ENEMIGO_Lista
        {
            public int posX;
            public int posY;

            public int tipo;
            public int rot;
        };

        struct OBJETO_Lista
        {
            public int posX;
            public int posY;

            public int tipo;
            public int rot;
        };
               
        struct TRANS_Lista
        {
            public int posX;
            public int posY;

            public int id;
        };

        struct BLOQUE
        {
            public int desplaz;				// Desplazamiento en el archivo
            public int longitud;				// Longitud en bytes
        };

        struct NIVEL
        {
            public int NumSecX;
            public int NumSecY;

            public int IniX;
            public int IniY;

            public int FinX;
            public int FinY;

            public int Fondo;

            public int Gemas;
        };

        private Font arial = new Font(new FontFamily("Arial"), 10, FontStyle.Bold);
        private Font arial2 = new Font(new FontFamily("Arial"), 13, FontStyle.Bold);

        private Point m_MouseScreenCoordinates;
        private Point m_MouseRealCoordinates;
        private Point m_CameraCoordinates;

        private Pen m_BlackPen = new Pen(Color.Black);
        private Pen m_GrayPen = new Pen(Color.FromArgb(240, 240, 240));
        private Pen m_DarkGrayPen = new Pen(Color.FromArgb(220, 220, 220));

        public const int NUM_ITEMS = 9;
        public const int NUM_ENEMS = 4;

        private bool m_bShowItemCursor = false;

        private List<ObjetoEscena> m_ListaEscena = new List<ObjetoEscena>();

        private List<ObjetoEscena> m_ListaNivel1 = new List<ObjetoEscena>();
        private List<ObjetoEscena> m_ListaNivel2 = new List<ObjetoEscena>();


        public Bitmap[] m_ImagenesCubos;
        public Bitmap[] m_ImagenesFondos;
        private Bitmap[] m_ImagenesItems = new Bitmap[NUM_ITEMS];
        private Bitmap[] m_ImagenesEnems = new Bitmap[NUM_ENEMS];

        private Bitmap m_ImagenSelectorCubo;
        private Bitmap m_ImagenTransporter;

        private MainForm m_TheForm;

        private byte m_iRotation;

        private bool m_bExit, m_bStart, m_bFondoActivo;

        private int m_iGemCount;

        private int m_iCubeCount;

        private int m_iBackground;

        public int m_iTeleport;


        //********************************************************************
        // Property:   Background
        // Propósito:  
        // Fecha:      lunes, 23 de octubre de 2006, 18:32:17
        //********************************************************************
        public int Background
        {
            set 
            {
                m_iBackground = value;
            }
            get
            {
                return m_iBackground;
            }
        } 

        //********************************************************************
        // Property:   Background
        // Propósito:  
        // Fecha:      lunes, 23 de octubre de 2006, 18:30:47
        //********************************************************************
        public bool BackgroundActivate
        {
            set { m_bFondoActivo = value; }
            get { return m_bFondoActivo; }
        }              

        //********************************************************************
        // Property:   MouseRealCoordinates
        // Propósito:  
        // Fecha:      martes, 07 de febrero de 2006, 13:09:48
        //********************************************************************
        public Point MouseRealCoordinates
        {            
            get { return m_MouseRealCoordinates; }
        }

        //********************************************************************
        // Property:   CameraCoordinateX
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 10:24:43
        //********************************************************************
        public int CameraCoordinateX
        {
            set { m_CameraCoordinates.X = value; }
            get { return m_CameraCoordinates.X; }
        }


        //********************************************************************
        // Property:   CameraCoordinateY
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 10:44:21
        //********************************************************************
        public int CameraCoordinateY
        {
            set { m_CameraCoordinates.Y = value; }
            get { return m_CameraCoordinates.Y; }
        }


        //********************************************************************
        // Property:   ShowItemCursor
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 10:14:11
        //********************************************************************
        public bool ShowItemCursor
        {
            set { m_bShowItemCursor = value; }
            get { return m_bShowItemCursor; }
        }


        //********************************************************************
        // Property:   Rotation
        // Propósito:  
        // Fecha:      lunes, 30 de enero de 2006, 16:07:08
        //********************************************************************
        public byte Rotation
        {
            set { m_iRotation = value; }
            get { return m_iRotation; }
        }


        //--------------------------------------------------------------------
        // Función:    Start
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 10:05:13
        //--------------------------------------------------------------------
        public void Start(MainForm theForm)
        {
            m_TheForm = theForm;

            m_ImagenSelectorCubo = (Bitmap)Bitmap.FromFile("gfx/cubos/selector.png");
            m_ImagenTransporter = (Bitmap)Bitmap.FromFile("gfx/trans_editor.png");

            m_TheForm.imageListTrans.Images.Add(m_ImagenTransporter);
                
            DirectoryInfo di = new DirectoryInfo("gfx/cubos");
            FileInfo[] rgFiles = di.GetFiles("*_editor.png");

            Array.Sort(rgFiles, new CompareFileInfo());

            m_ImagenesCubos = new Bitmap[rgFiles.Length];

            int i = 0;

            foreach (FileInfo fi in rgFiles)
            {
                m_ImagenesCubos[i] = (Bitmap)Bitmap.FromFile(fi.FullName);
                m_TheForm.imageListCubos.Images.Add(m_ImagenesCubos[i]);

                i++;
            }

            di = new DirectoryInfo("gfx/bg");
            rgFiles = di.GetFiles("*.png");

            Array.Sort(rgFiles, new CompareFileInfo());


            m_ImagenesFondos = new Bitmap[rgFiles.Length];

            i = 0;          

            foreach (FileInfo fi in rgFiles)
            {
                m_ImagenesFondos[i] = (Bitmap)Bitmap.FromFile(fi.FullName);

                m_TheForm.comboBoxFondo.Items.Add(fi.Name);
                
                i++;
            }

            m_TheForm.comboBoxFondo.SelectedIndex = 0;

            m_ImagenesItems[0] = (Bitmap)Bitmap.FromFile("gfx/salida_editor.png");
            m_ImagenesItems[1] = (Bitmap)Bitmap.FromFile("gfx/entrada.png");
            m_ImagenesItems[2] = (Bitmap)Bitmap.FromFile("gfx/gema1_editor.png");
            m_ImagenesItems[3] = (Bitmap)Bitmap.FromFile("gfx/gema2_editor.png");
            m_ImagenesItems[4] = (Bitmap)Bitmap.FromFile("gfx/gema3_editor.png");
            m_ImagenesItems[5] = (Bitmap)Bitmap.FromFile("gfx/gema4_editor.png");
            m_ImagenesItems[6] = (Bitmap)Bitmap.FromFile("gfx/cinta_editor.png");
            m_ImagenesItems[7] = (Bitmap)Bitmap.FromFile("gfx/llave1_editor.png");
            m_ImagenesItems[8] = (Bitmap)Bitmap.FromFile("gfx/llave2_editor.png");

            for (i = 0; i < NUM_ITEMS; i++)
            {
                m_TheForm.imageListItems.Images.Add(m_ImagenesItems[i]);
            }


            m_ImagenesEnems[0] = (Bitmap)Bitmap.FromFile("gfx/ene1_editor.png");
            m_ImagenesEnems[1] = (Bitmap)Bitmap.FromFile("gfx/ene2_editor.png");
            m_ImagenesEnems[2] = (Bitmap)Bitmap.FromFile("gfx/ene3_editor.png");
            m_ImagenesEnems[3] = (Bitmap)Bitmap.FromFile("gfx/ene4_editor.png");

            for (i = 0; i < NUM_ENEMS; i++)
            {
                m_TheForm.imageListEnems.Images.Add(m_ImagenesEnems[i]);
            }

            ResetScene();
        }


        //--------------------------------------------------------------------
        // Función:    Render
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 10:05:19
        //--------------------------------------------------------------------
        public void Render(Graphics gfx)
        {
            gfx.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.NearestNeighbor;

            if (m_bFondoActivo)
            {
                for (int i = 0; i < 6; i++)
                {
                    for (int j = 0; j < 5; j++)
                    {
                        gfx.DrawImage(m_ImagenesFondos[m_iBackground], i*128, j*128, m_ImagenesFondos[m_iBackground].Width, m_ImagenesFondos[m_iBackground].Height);
                    }
                }
            }
            else
            {
                for (int i = 0; i < 200; i++)
                {
                    gfx.DrawLine(m_GrayPen, 0, 4 * i, 800, 4 * i);
                }

                for (int i = 0; i < 200; i++)
                {
                    gfx.DrawLine(m_GrayPen, 4 * i, 0, 4 * i, 800);
                }
            }

            if (!m_TheForm.checkBoxPantalla.Checked && m_bShowItemCursor)
            {
                gfx.DrawLine(m_DarkGrayPen, 0, (m_MouseScreenCoordinates.Y * 4) - (6 * m_TheForm.comboBoxPiso.SelectedIndex), 800, (m_MouseScreenCoordinates.Y * 4) - (6 * m_TheForm.comboBoxPiso.SelectedIndex));
                gfx.DrawLine(m_DarkGrayPen, (m_MouseScreenCoordinates.X * 4) + (3 * m_TheForm.comboBoxPiso.SelectedIndex), 0, (m_MouseScreenCoordinates.X * 4) + (3 * m_TheForm.comboBoxPiso.SelectedIndex), 800);

                gfx.DrawLine(m_DarkGrayPen, 0, ((m_MouseScreenCoordinates.Y + 4) * 4) - (6 * m_TheForm.comboBoxPiso.SelectedIndex), 800, ((m_MouseScreenCoordinates.Y + 4) * 4) - (6 * m_TheForm.comboBoxPiso.SelectedIndex));
                gfx.DrawLine(m_DarkGrayPen, ((m_MouseScreenCoordinates.X + 4) * 4) + (3 * m_TheForm.comboBoxPiso.SelectedIndex), 0, ((m_MouseScreenCoordinates.X + 4) * 4) + (3 * m_TheForm.comboBoxPiso.SelectedIndex), 800);
            }

      
            RenderScene(gfx);

            if (m_TheForm.checkBoxPantalla.Checked)
            {
                if (m_bShowItemCursor)
                {
                    gfx.DrawRectangle(m_BlackPen, (m_MouseScreenCoordinates.X - 22) * 4, (m_MouseScreenCoordinates.Y - 26) * 4, 176, 208);
                    gfx.DrawRectangle(Pens.White, (m_MouseScreenCoordinates.X - 22) * 4 - 1, (m_MouseScreenCoordinates.Y - 26) * 4 - 1, 178, 210);
                }
            }
            else if (m_bShowItemCursor)
            {
                if (m_TheForm.radioItems.Checked && m_TheForm.listViewTextures.SelectedIndices.Count == 1)
                {
                  
                    for (int j=0;j< m_TheForm.numericUpDownCursorY.Value; j++)
                        for (int i = 0; i < m_TheForm.numericUpDownCursorX.Value; i++)
                        {
                            if (Rotation == 1)
                            {
                                m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate90FlipNone);
                            }
                            else if (Rotation == 2)
                            {
                                m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate180FlipNone);
                            }
                            else if (Rotation == 3)
                            {
                                m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate270FlipNone);
                            }

                            gfx.DrawImage(m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]], (m_MouseScreenCoordinates.X * 4) + (16 * i), (m_MouseScreenCoordinates.Y * 4) + (16 * j), m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]].Width, m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]].Height);

                            if (Rotation == 1)
                            {
                                m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate270FlipNone);
                            }
                            else if (Rotation == 2)
                            {
                                m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate180FlipNone);
                            }
                            else if (Rotation == 3)
                            {
                                m_ImagenesItems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate90FlipNone);
                            }
                            
                            gfx.DrawImage(m_ImagenSelectorCubo, (m_MouseScreenCoordinates.X * 4) + (16 * i), (m_MouseScreenCoordinates.Y * 4) - 6 + (16 * j));
                        }
                }
                else if (m_TheForm.radioEnemigos.Checked && m_TheForm.listViewTextures.SelectedIndices.Count == 1)
                {
                

                    for (int j = 0; j < m_TheForm.numericUpDownCursorY.Value; j++)
                        for (int i = 0; i < m_TheForm.numericUpDownCursorX.Value; i++)
                        {
                            if (Rotation == 1)
                            {
                                m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate90FlipNone);
                            }
                            else if (Rotation == 2)
                            {
                                m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate180FlipNone);
                            }
                            else if (Rotation == 3)
                            {
                                m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate270FlipNone);
                            }

                            gfx.DrawImage(m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]], (m_MouseScreenCoordinates.X * 4) + (16 * i), (m_MouseScreenCoordinates.Y * 4) + (16 * j), m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]].Width, m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]].Height);

                            if (Rotation == 1)
                            {
                                m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate270FlipNone);
                            }
                            else if (Rotation == 2)
                            {
                                m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate180FlipNone);
                            }
                            else if (Rotation == 3)
                            {
                                m_ImagenesEnems[m_TheForm.listViewTextures.SelectedIndices[0]].RotateFlip(RotateFlipType.Rotate90FlipNone);
                            }

                            gfx.DrawImage(m_ImagenSelectorCubo, (m_MouseScreenCoordinates.X * 4) + (16 * i), (m_MouseScreenCoordinates.Y * 4) - 6 + (16 * j), m_ImagenSelectorCubo.Width, m_ImagenSelectorCubo.Height);
                        }
                }
                else if (m_TheForm.radioTransport.Checked && m_TheForm.listViewTextures.SelectedIndices.Count == 1)
                {
 

                    gfx.DrawImage(m_ImagenTransporter, (m_MouseScreenCoordinates.X * 4), (m_MouseScreenCoordinates.Y * 4), 16, 16);

                    gfx.DrawImage(m_ImagenSelectorCubo, (m_MouseScreenCoordinates.X * 4) , (m_MouseScreenCoordinates.Y * 4) - 6 , m_ImagenSelectorCubo.Width, m_ImagenSelectorCubo.Height);
                }
                else
                {
                    

                    for (int j = 0; j < m_TheForm.numericUpDownCursorY.Value; j++)
                        for (int i = 0; i < m_TheForm.numericUpDownCursorX.Value; i++)
                        {
                            
                                gfx.DrawImage(m_ImagenSelectorCubo, (m_MouseScreenCoordinates.X * 4) + (16 * i) + (3 * m_TheForm.comboBoxPiso.SelectedIndex), (m_MouseScreenCoordinates.Y * 4) - 6 + (16 * j) - (6 * m_TheForm.comboBoxPiso.SelectedIndex), m_ImagenSelectorCubo.Width, m_ImagenSelectorCubo.Height);
                            

                        }
                }
            }
        }


        //--------------------------------------------------------------------
        // Función:    MouseMove
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 10:15:39
        //--------------------------------------------------------------------
        public void MouseMove(Point location)
        {
            m_MouseScreenCoordinates.X = (location.X / 4);
            m_MouseScreenCoordinates.Y = (location.Y / 4);

            m_MouseRealCoordinates.X = m_MouseScreenCoordinates.X + m_CameraCoordinates.X;
            m_MouseRealCoordinates.Y = m_MouseScreenCoordinates.Y + m_CameraCoordinates.Y;

            m_TheForm.textBoxX.Text = m_MouseRealCoordinates.X.ToString();
            m_TheForm.textBoxY.Text = m_MouseRealCoordinates.Y.ToString();
        }


        //--------------------------------------------------------------------
        // Función:    DeleteObject
        // Propósito:  
        // Fecha:      lunes, 30 de enero de 2006, 10:38:00
        //--------------------------------------------------------------------
        public void DeleteObject(byte tipo)
        {
            Point pos = m_MouseRealCoordinates;
            List<ObjetoEscena> lista = null;

            switch (m_TheForm.comboBoxPiso.SelectedIndex)
            {
                case 0:
                {
                    lista = m_ListaEscena;
                    break;
                }
                case 1:
                {
                    lista = m_ListaNivel1;
                    //pos.X += 3;
                    //pos.Y -= 6;
                    break;
                }
                case 2:
                {
                    lista = m_ListaNivel2;
                    //pos.X += 6;
                    //pos.Y -= 12;
                    break;
                }
            }

            ObjetoEscena temp = null;            

            foreach (ObjetoEscena c in lista)
            {
                if (c.tipo != tipo)
                    continue;

                if ((c.tipo == 1 && c.id == 1) || (c.tipo == 1 && c.id == 0))
                    continue;

                if ((pos.X >= c.posX) && (pos.X < (c.posX + 4)) && (pos.Y >= c.posY) && (pos.Y < (c.posY + 4)))
                {
                    temp = c;
                    break;
                }
                /*
                if ((pos.X + 3 >= c.posX) && (pos.X + 3 < (c.posX + 4)) && (pos.Y + 3 >= c.posY) && (pos.Y + 3 < (c.posY + 4)))
                {
                    temp = c;
                    break;
                }

                if ((pos.X + 3 >= c.posX) && (pos.X + 3 < (c.posX + 4)) && (pos.Y >= c.posY) && (pos.Y < (c.posY + 4)))
                {
                    temp = c;
                    break;
                }

                if ((pos.X >= c.posX) && (pos.X < (c.posX + 4)) && (pos.Y + 3 >= c.posY) && (pos.Y + 3 < (c.posY + 4)))
                {
                    temp = c;
                    break;
                }*/
            }

            if (temp != null)
            {
                lista.Remove(temp);

                if (temp.tipo == 1 && temp.id == 2)
                {
                    m_iGemCount--;
                }

                if (temp.tipo == 3)
                {
                    m_iTeleport--;
                }

                if (temp.tipo == 0)
                {
                    m_iCubeCount--;
                }

                m_TheForm.textBoxGemas.Text = m_iGemCount.ToString();

                m_TheForm.textBoxCubos.Text = m_iCubeCount.ToString();
            }
        }


        //--------------------------------------------------------------------
        // Función:    AddObject
        // Propósito:  
        // Fecha:      martes, 24 de octubre de 2006, 17:44:57
        //--------------------------------------------------------------------
        public void AddCubeLevel(byte tipo, byte id, byte rot, int offsetX, int offsetY, Point pos, int piso)
        {
            List<ObjetoEscena> lista = null;

            switch (piso)
            {                
                case 1:
                {
                    lista = m_ListaNivel1;
                    break;
                }
                case 2:
                {
                    lista = m_ListaNivel2;
                    break;
                }
            }

            Point coord = new Point(pos.X + offsetX, pos.Y + offsetY);
            if (!CheckValidPos(coord, piso))
                return;

            ObjetoEscena temp = new ObjetoEscena(tipo, id, (short)coord.X, (short)coord.Y, rot);

            if (lista.Count == 0)
            {
                lista.Add(temp);
            }            
            else
            {
                int a = 0;

                int distanciaTemp = (1000 - temp.posX) + temp.posY;

                if ((tipo == 1 && id == 1) || (tipo == 1 && id == 0))
                    distanciaTemp = 0;


                foreach (ObjetoEscena c in lista)
                {
                    int distanciaC = (1000 - c.posX) + c.posY;

                    if ((c.tipo == 1 && c.id == 1) || (c.tipo == 1 && c.id == 0))
                        distanciaC = 0;

                    if (distanciaC < distanciaTemp)
                    {
                        a++;
                        continue;
                    }

                    break;
                }

                lista.Insert(a, temp);
            }

            ///--- actualizar contadores
            m_iCubeCount++;            
         
            m_TheForm.textBoxCubos.Text = m_iCubeCount.ToString();
            
        }       


        //--------------------------------------------------------------------
        // Función:    AddObject
        // Propósito:  
        // Fecha:      martes, 24 de octubre de 2006, 17:45:03
        //--------------------------------------------------------------------
        public void AddObject(byte tipo, byte id, byte rot, int offsetX, int offsetY, Point pos)
        {
            Point coord = new Point(pos.X + offsetX, pos.Y + offsetY);
            if (!CheckValidPos(coord, 0))
                return;
            
            bool bottom = false;

            ///--- salida del nivel
            if (tipo == 1 && id == 0)
            {
                if (m_bExit)
                {
                    foreach (ObjetoEscena c in m_ListaEscena)
                    {
                        if (c.tipo == 1 && c.id == 0)
                        {
                            c.posX = (short)coord.X;
                            c.posY = (short)coord.Y;
                        }
                    }

                    return;
                }
                else
                {
                    bottom = true;
                    m_bExit = true;
                }
            }

            ///--- entrada del nivel
            if (tipo == 1 && id == 1)
            {
                if (m_bStart)
                {
                    foreach (ObjetoEscena c in m_ListaEscena)
                    {
                        if (c.tipo == 1 && c.id == 1)
                        {
                            c.posX = (short)coord.X;
                            c.posY = (short)coord.Y;
                        }
                    }

                    return;
                }
                else
                {
                    bottom = true;
                    m_bStart = true;
                }
            }

            ObjetoEscena temp = new ObjetoEscena(tipo, id, (short)coord.X, (short)coord.Y, rot); 
            

            ///--- si es un enemigo dinámico
            if (tipo == 2)
            {
                if ((id == 0) || (id == 3))
                {
                    temp.dynamic = true;
                }
            }

            ///--- transport
            if (tipo == 3)
            {
                if (rot == 100)
                {
                    temp.rotation = (byte)(m_iTeleport / 2);
                }
                else
                {
                    temp.rotation = rot;
                }
                m_iTeleport++;                
            }

            if (m_ListaEscena.Count == 0)
            {
                m_ListaEscena.Add(temp);
            }
            else if (bottom)
            {
                m_ListaEscena.Insert(0, temp);
            }
            else
            {
                int a = 0;

                int distanciaTemp = (1000 - temp.posX) + temp.posY;

                if ((tipo == 1 && id == 1) || (tipo == 1 && id == 0))
                    distanciaTemp = 0;


                foreach (ObjetoEscena c in m_ListaEscena)
                {           
                    int distanciaC = (1000 - c.posX) + c.posY;

                    if ((c.tipo == 1 && c.id == 1) || (c.tipo == 1 && c.id == 0))
                        distanciaC = 0;

                    if (distanciaC < distanciaTemp)
                    {
                        a++;
                        continue;
                    }

                    break;
                }

                m_ListaEscena.Insert(a, temp);
            }

            ///--- actualizar contadores
            
            if (tipo == 1 && id == 2)
            {
                m_iGemCount++;
            }

            if (tipo == 0)
            {
                m_iCubeCount++;
            }

            m_TheForm.textBoxGemas.Text = m_iGemCount.ToString();

            m_TheForm.textBoxCubos.Text = m_iCubeCount.ToString();

            m_TheForm.textBoxTeleport.Text = ((byte)(m_iTeleport / 2)).ToString();
        }


        //--------------------------------------------------------------------
        // Función:    RenderScene
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 10:33:28
        //--------------------------------------------------------------------
        private void RenderScene(Graphics g)
        {
            foreach (ObjetoEscena c in m_ListaEscena)
            {
                ///--- cubo
                if (c.tipo == 0)
                {
                    g.DrawImage(m_ImagenesCubos[c.id], (c.posX * 4) - (m_CameraCoordinates.X * 4), (c.posY * 4) - 6 - (m_CameraCoordinates.Y * 4), 19, 22);
                }
                ///--- item
                else if (c.tipo == 1)
                {
                    int x = (c.posX * 4) - (m_CameraCoordinates.X * 4);
                    int y = (c.posY * 4) - (m_CameraCoordinates.Y * 4);

                 

                    if (c.rotation == 1)
                    {
                        m_ImagenesItems[c.id].RotateFlip(RotateFlipType.Rotate90FlipNone);
                    }
                    else if (c.rotation == 2)
                    {
                        m_ImagenesItems[c.id].RotateFlip(RotateFlipType.Rotate180FlipNone);
                    }
                    else if (c.rotation == 3)
                    {
                        m_ImagenesItems[c.id].RotateFlip(RotateFlipType.Rotate270FlipNone);
                    }

                    g.DrawImage(m_ImagenesItems[c.id], x, y, m_ImagenesItems[c.id].Width, m_ImagenesItems[c.id].Height);

                    if (c.rotation == 1)
                    {
                        m_ImagenesItems[c.id].RotateFlip(RotateFlipType.Rotate270FlipNone);
                    }
                    else if (c.rotation == 2)
                    {
                        m_ImagenesItems[c.id].RotateFlip(RotateFlipType.Rotate180FlipNone);
                    }
                    else if (c.rotation == 3)
                    {
                        m_ImagenesItems[c.id].RotateFlip(RotateFlipType.Rotate90FlipNone);
                    }                    
                }
                ///--- enemigo
                else if (c.tipo == 2)
                {
                    int x = (c.posX * 4) - (m_CameraCoordinates.X * 4);
                    int y = (c.posY * 4) - (m_CameraCoordinates.Y * 4);
                  
                    if (c.rotation == 1)
                    {
                        m_ImagenesEnems[c.id].RotateFlip(RotateFlipType.Rotate90FlipNone);
                    }
                    else if (c.rotation == 2)
                    {
                        m_ImagenesEnems[c.id].RotateFlip(RotateFlipType.Rotate180FlipNone);
                    }
                    else if (c.rotation == 3)
                    {
                        m_ImagenesEnems[c.id].RotateFlip(RotateFlipType.Rotate270FlipNone);
                    }

                    g.DrawImage(m_ImagenesEnems[c.id], x, y, m_ImagenesEnems[c.id].Width, m_ImagenesEnems[c.id].Height);
                    
                    if (c.rotation == 1)
                    {
                        m_ImagenesEnems[c.id].RotateFlip(RotateFlipType.Rotate270FlipNone);
                    }
                    else if (c.rotation == 2)
                    {
                        m_ImagenesEnems[c.id].RotateFlip(RotateFlipType.Rotate180FlipNone);
                    }
                    else if (c.rotation == 3)
                    {
                        m_ImagenesEnems[c.id].RotateFlip(RotateFlipType.Rotate90FlipNone);
                    }
                }
                ///--- transport
                else if (c.tipo == 3)
                {                    
                    g.DrawImage(m_ImagenTransporter, (c.posX * 4) - (m_CameraCoordinates.X * 4), (c.posY * 4) - (m_CameraCoordinates.Y * 4), 16, 16);
                    g.DrawString((c.rotation).ToString(), arial2, Brushes.Black, (c.posX * 4) - (m_CameraCoordinates.X * 4) - 2, (c.posY * 4) - (m_CameraCoordinates.Y * 4) - 2);

                    g.DrawString((c.rotation).ToString(), arial, Brushes.White, (c.posX * 4) - (m_CameraCoordinates.X * 4), (c.posY * 4) - (m_CameraCoordinates.Y * 4));
                }
            }

            foreach (ObjetoEscena c in m_ListaNivel1)
            {
                g.DrawImage(m_ImagenesCubos[c.id], (c.posX * 4) - (m_CameraCoordinates.X * 4) + 3, (c.posY * 4) - 12 - (m_CameraCoordinates.Y * 4), 19, 22);                
            }

            foreach (ObjetoEscena c in m_ListaNivel2)
            {
                g.DrawImage(m_ImagenesCubos[c.id], (c.posX * 4) - (m_CameraCoordinates.X * 4) + 6, (c.posY * 4) - 18 - (m_CameraCoordinates.Y * 4), 19, 22);
            }
        }


        //--------------------------------------------------------------------
        // Función:    CheckValidPos
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 11:25:17
        //--------------------------------------------------------------------
        private bool CheckValidPos(Point pos, int piso)
        {
            List<ObjetoEscena> lista = null;
            
            switch (piso)
            {
                case 0:
                {
                    lista = m_ListaEscena;
                    break;
                }
                case 1:
                {
                    lista = m_ListaNivel1;
                    break;
                }
                case 2:
                {
                    lista = m_ListaNivel2;
                    break;
                }
            }

            foreach (ObjetoEscena c in lista)
            {
                if ((pos.X >= c.posX) && (pos.X < (c.posX + 4)) && (pos.Y >= c.posY) && (pos.Y < (c.posY + 4)))
                    return false;

                if ((pos.X+3 >= c.posX) && (pos.X + 3 < (c.posX + 4)) && (pos.Y + 3 >= c.posY) && (pos.Y + 3 < (c.posY + 4)))
                    return false;

                if ((pos.X + 3 >= c.posX) && (pos.X + 3 < (c.posX + 4)) && (pos.Y >= c.posY) && (pos.Y < (c.posY + 4)))
                    return false;

                if ((pos.X >= c.posX) && (pos.X < (c.posX + 4)) && (pos.Y + 3 >= c.posY) && (pos.Y + 3 < (c.posY + 4)))
                    return false;
            }

            return true;
        }



        //--------------------------------------------------------------------
        // Función:    LoadLevel
        // Propósito:  
        // Fecha:      martes, 24 de octubre de 2006, 19:03:44
        //--------------------------------------------------------------------
        public void LoadLevel(BinaryReader br, int level)
        {                      
            short staticCount = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());

            for (int i = 0; i < staticCount; i++)
            {
                ObjetoEscena temp = new ObjetoEscena();
                temp.dynamic = false;
                temp.tipo = br.ReadByte();
                temp.id = br.ReadByte();
                temp.posX = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());
                temp.posY = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());
                temp.rotation = br.ReadByte();

                ///--- si es un cubo saltamos la informacion de los lados
                if (temp.tipo == 0)
                {
                    br.ReadByte();
                }

                if (level == 0)
                    AddObject(temp.tipo, temp.id, temp.rotation, 0, 0, new Point(temp.posX, temp.posY));
                else
                    AddCubeLevel(temp.tipo, temp.id, temp.rotation, 0, 0, new Point(temp.posX, temp.posY), level);

                temp = null;
            }

            short dynamicCount = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());

            for (int i = 0; i < dynamicCount; i++)
            {
                ObjetoEscena temp = new ObjetoEscena();
                temp.dynamic = true;
                temp.tipo = br.ReadByte();
                temp.id = br.ReadByte();
                temp.posX = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());
                temp.posY = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());
                temp.rotation = br.ReadByte();

                AddObject(temp.tipo, temp.id, temp.rotation, 0, 0, new Point(temp.posX, temp.posY));
            }

            short sectoresX = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());
            short sectoresY = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());

            for (int i = 0; i < (sectoresX * sectoresY); i++)
            {
                byte count = br.ReadByte();

                for (int j = 0; j < count; j++)
                {
                    short index = System.Net.IPAddress.HostToNetworkOrder(br.ReadInt16());
                }
            }
        }

        //--------------------------------------------------------------------
        // Función:    OpenFile
        // Propósito:  
        // Fecha:      miércoles, 01 de febrero de 2006, 15:20:18
        //--------------------------------------------------------------------
        public void OpenFile(Stream fileStream)
        {
            ResetScene();

            BinaryReader br = new BinaryReader(fileStream);

            m_TheForm.comboBoxFondo.SelectedIndex = br.ReadByte();

            LoadLevel(br, 0);
            LoadLevel(br, 1);
            LoadLevel(br, 2);

            br.Close();
            br = null;

            fileStream.Close();
        }


        //--------------------------------------------------------------------
        // Función:    SaveLevel
        // Propósito:  
        // Fecha:      martes, 24 de octubre de 2006, 18:44:22
        //--------------------------------------------------------------------
        public void SaveLevel(BinaryWriter bw, int level)
        {
            List<ObjetoEscena> lista = null;

            switch (level)
            {
                case 0:
                {
                    lista = m_ListaEscena;
                    break;
                }
                case 1:
                {
                    lista = m_ListaNivel1;
                    break;
                }
                case 2:
                {
                    lista = m_ListaNivel2;
                    break;
                }
            }

            short xMax = 0, yMax = 0;

            short staticCount = 0;
            short dynamicCount = 0;

            foreach (ObjetoEscena c in lista)
            {
                if (c.dynamic)
                {
                    dynamicCount++;
                }
                else
                {
                    staticCount++;
                }
            }

            ///--- importante pasar de little endian a big endian
            ///--- ya que java lee en big endian y c# escribe por
            ///--- defecto en little endian
            /// 
            ///--- guardamos el número de objetos estáticos
            bw.Write(System.Net.IPAddress.HostToNetworkOrder(staticCount));

            ///--- guardamos todos los objetos estáticos
            foreach (ObjetoEscena c in lista)
            {
                if (c.dynamic)
                    continue;

                SaveObject(bw, c);

                if (c.posX > xMax)
                {
                    xMax = c.posX;
                }

                if (c.posY > yMax)
                {
                    yMax = c.posY;
                }
            }

            ///--- guardamos el número de objetos dinámicos
            bw.Write(System.Net.IPAddress.HostToNetworkOrder(dynamicCount));

            ///--- guardamos todos los objetos dinámicos
            foreach (ObjetoEscena c in lista)
            {
                if (!c.dynamic)
                    continue;

                SaveObject(bw, c);
            }

            ///--- calculamos el número de sectores
            short numSecX = (short)((xMax / 16) + 1);
            short numSecY = (short)((yMax / 16) + 1);
            short numSec = (short)(numSecX * numSecY);

            ///--- guardamos el número de sectores
            bw.Write(System.Net.IPAddress.HostToNetworkOrder(numSecX));
            bw.Write(System.Net.IPAddress.HostToNetworkOrder(numSecY));

            Sector[] arraySectores = new Sector[numSec];

            for (int j = 0; j < numSecY; j++)
            {
                for (int i = 0; i < numSecX; i++)
                {
                    arraySectores[(numSecX * j) + i].staticObjectsIndices = new List<short>();


                    ///--- vemos los objetos estáticos de este sector

                    short indice = 0;

                    foreach (ObjetoEscena c in lista)
                    {
                        if (c.dynamic)
                            continue;

                        if ((c.posX >= ((i * 16) - 4)) && (c.posX < ((i * 16) + 16)))
                            if ((c.posY >= ((j * 16) - 4)) && (c.posY < ((j * 16) + 16)))
                            {
                                ///--- el objeto está en el sector actual

                                bool insertado = false;

                                int distanciaC = (1000 - c.posX) + c.posY;

                                int a = 0;

                                if (!insertado)
                                    arraySectores[(numSecX * j) + i].staticObjectsIndices.Add(indice);
                                else
                                    arraySectores[(numSecX * j) + i].staticObjectsIndices.Insert(a, indice);
                            }

                        indice++;
                    }
                }
            }

            ///--- guardamos los sectores
            foreach (Sector s in arraySectores)
            {
                SaveSector(bw, s);
            }
        }


        //--------------------------------------------------------------------
        // Función:    SaveFile
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 11:42:04
        //--------------------------------------------------------------------
        public void SaveFile(Stream fileStream)
        {    

            BinaryWriter bw = new BinaryWriter(fileStream);

            byte bg = (byte)m_TheForm.comboBoxFondo.SelectedIndex;

            bw.Write(bg);

            SaveLevel(bw, 0);
            SaveLevel(bw, 1);
            SaveLevel(bw, 2);
           
            ///--- cerramos el archivo

            bw.Close();
            bw = null;

            fileStream.Close();
            fileStream = null;
        }


        //--------------------------------------------------------------------
        // Función:    ExportLevel
        // Creador:    Nacho (AMD)
        // Fecha:      Thursday  18/10/2007  20:06:04
        //--------------------------------------------------------------------
        public void ExportLevel(BinaryWriter bw, int level)
        {
            List<ObjetoEscena> ListaNivel = null;

            if (level == 1)
            {
                ListaNivel = m_ListaNivel1;
            }
            else
            {
                ListaNivel = m_ListaNivel2;
            }

            int cube_count = 0;
            int Xmax = 0;
            int Ymax = 0;

            foreach (ObjetoEscena c in ListaNivel)
            {
                ///--- es un cubo
                if (c.tipo == 0)
                {
                    cube_count++;

                    if (c.posX > Xmax)
                        Xmax = c.posX * 2;

                    if (c.posY > Ymax)
                        Ymax = c.posY * 2;
                }
            }

            int NumXSec = (Xmax / 32) + 1;
            int NumYSec = (Ymax / 32) + 1;

            int NumSec = NumXSec * NumYSec;

            int indiceCubo = 0;

            SECTOR_Lista[] pSectores = new SECTOR_Lista[NumSec];

            // calculamos el array de sectores pSectores para los CUBOS
            for (int j = 0; j < NumYSec; j++)
                for (int i = 0; i < NumXSec; i++)
                {
                    pSectores[(NumXSec * j) + i].numCubos = 0;
                    pSectores[(NumXSec * j) + i].IndCub = new int[25];
                    pSectores[(NumXSec * j) + i].numObjs = 0;
                    pSectores[(NumXSec * j) + i].IndObj = new int[100];

                    int cont = 0;
                    indiceCubo = 0;

                    foreach (ObjetoEscena c in ListaNivel)
                    {
                        ///--- es un cubo
                        if (c.tipo == 0)
                        {
                            if ((c.posX * 2 > ((i * 32) - 8)) && (c.posX * 2 < ((i * 32) + 32)))
                                if ((c.posY * 2 >= ((j * 32) - 8)) && (c.posY * 2 < ((j * 32) + 32)))
                                {
                                    //el cubo esta en el sector actual
                                    pSectores[(NumXSec * j) + i].numCubos++;
                                    pSectores[(NumXSec * j) + i].IndCub[cont] = indiceCubo;
                                    cont++;

                                }

                            indiceCubo++;
                        }
                    }
                }

           
            //////////////////////////////////////////////////


            CUBO_Lista[] pCubos = new CUBO_Lista[cube_count];

            indiceCubo = 0;

            foreach (ObjetoEscena c in ListaNivel)
            {
                ///--- es un cubo
                if (c.tipo == 0)
                {
                    pCubos[indiceCubo].posX = c.posX * 2;
                    pCubos[indiceCubo].posY = c.posY * 2;
                    pCubos[indiceCubo].tex = 0;
                    pCubos[indiceCubo].texLat = 0;

                    pCubos[indiceCubo].lados = new bool[4];

                    for (int i = 0; i < 4; i++)
                        pCubos[indiceCubo].lados[i] = false;

                    indiceCubo++;
                }
            }

            indiceCubo = 0;

            foreach (ObjetoEscena itor2 in ListaNivel)
            {
                ///--- es un cubo
                if (itor2.tipo == 0)
                {
                    foreach (CUBO_Lista itor in pCubos)
                    {

                        if ((itor.posX * 2 == pCubos[indiceCubo].posX - 8)
                            && (itor.posY * 2 == pCubos[indiceCubo].posY)
                            && (itor.texLat != 18 && itor.texLat != 19))
                        {
                            pCubos[indiceCubo].lados[0] = true;
                        }

                        if ((itor.posX * 2 == pCubos[indiceCubo].posX)
                            && (itor.posY * 2 == pCubos[indiceCubo].posY - 8)
                            && (itor.texLat != 18 && itor.texLat != 19))
                        {
                            pCubos[indiceCubo].lados[1] = true;
                        }

                        if ((itor.posX * 2 == pCubos[indiceCubo].posX + 8)
                            && (itor.posY * 2 == pCubos[indiceCubo].posY)
                            && (itor.texLat != 18 && itor.texLat != 19))
                        {
                            pCubos[indiceCubo].lados[2] = true;
                        }

                        if ((itor.posX * 2 == pCubos[indiceCubo].posX)
                            && (itor.posY * 2 == pCubos[indiceCubo].posY + 8)
                            && (itor.texLat != 18 && itor.texLat != 19))
                        {
                            pCubos[indiceCubo].lados[3] = true;
                        }

                    }

                    indiceCubo++;
                }
            }


            bw.Write(cube_count);

            ///--- cubos
            foreach (CUBO_Lista c in pCubos)
            {
                bw.Write(c.posX);
                bw.Write(c.posY);
                bw.Write(c.tex);
                bw.Write(c.texLat);

                for (int i = 0; i < 4; i++)
                {
                    bw.Write(c.lados[i]);
                }
            }

            bw.Write(NumSec);
            bw.Write(NumXSec);
            bw.Write(NumYSec);

            ///--- sectores
            foreach (SECTOR_Lista s in pSectores)
            {
                bw.Write(s.numCubos);
                bw.Write(s.numObjs);

                for (int i = 0; i < 25; i++)
                {
                    bw.Write(s.IndCub[i]);
                }

                for (int i = 0; i < 100; i++)
                {
                    bw.Write(s.IndObj[i]);
                }
            }
        }


        //--------------------------------------------------------------------
        // Función:    ExportFile
        // Creador:    Nacho (AMD)
        // Fecha:      Friday  05/10/2007  17:17:42
        //--------------------------------------------------------------------
        public void ExportFile(Stream fileStream)
        {

            BinaryWriter bw = new BinaryWriter(fileStream);                       

            int cube_count = 0;
            int Xmax = 0;
            int Ymax = 0;

            foreach (ObjetoEscena c in m_ListaEscena)
            {
                ///--- es un cubo
                if (c.tipo == 0)
                {
                    cube_count++;

                    if (c.posX > Xmax)
                        Xmax = c.posX * 2;

                    if (c.posY > Ymax)
                        Ymax = c.posY * 2;
                }                
            }

            int NumXSec = (Xmax / 32) + 1;
            int NumYSec = (Ymax / 32) + 1;

            int NumSec = NumXSec * NumYSec;

            int indiceCubo = 0;


            SECTOR_Lista[] pSectores = new SECTOR_Lista[NumSec];

            // calculamos el array de sectores pSectores para los CUBOS
            for (int j = 0; j < NumYSec; j++)
                for (int i = 0; i < NumXSec; i++)
                {
                    pSectores[(NumXSec * j) + i].numCubos = 0;
                    pSectores[(NumXSec * j) + i].IndCub = new int[25];
                    pSectores[(NumXSec * j) + i].numObjs = 0;
                    pSectores[(NumXSec * j) + i].IndObj = new int[100];

                    int cont = 0;
                    indiceCubo = 0;

                    foreach (ObjetoEscena c in m_ListaEscena)
                    {
                        ///--- es un cubo
                        if (c.tipo == 0)
                        {
                            if ((c.posX * 2 > ((i * 32) - 8)) && (c.posX * 2 < ((i * 32) + 32)))
                                if ((c.posY * 2 >= ((j * 32) - 8)) && (c.posY * 2 < ((j * 32) + 32)))
                                {
                                    //el cubo esta en el sector actual
                                    pSectores[(NumXSec * j) + i].numCubos++;
                                    pSectores[(NumXSec * j) + i].IndCub[cont] = indiceCubo;
                                    cont++;

                                }

                            indiceCubo++;
                        }
                    }
                }

            // calculamos el array de sectores pSectores para los OBJETOS
            for (int j = 0; j < NumYSec; j++)
                for (int i = 0; i < NumXSec; i++)
                {
                    int cont = 0;
                    indiceCubo = 0;

                    foreach (ObjetoEscena c in m_ListaEscena)
                    {
                        ///--- es un obj
                        if ((c.tipo == 1) && (c.id > 1))
                        {
                            if ((c.posX * 2 > ((i * 32) - 8)) && (c.posX * 2 < ((i * 32) + 32)))
                                if ((c.posY * 2 >= ((j * 32) - 8)) && (c.posY * 2 < ((j * 32) + 32)))
                                {
                                    //el obj esta en el sector actual
                                    pSectores[(NumXSec * j) + i].numObjs++;
                                    pSectores[(NumXSec * j) + i].IndObj[cont] = indiceCubo;
                                    cont++;

                                }

                            indiceCubo++;
                        }
                    }
                }

            //////////////////////////////////////////////////


            CUBO_Lista[] pCubos = new CUBO_Lista[cube_count];

            indiceCubo = 0;

            foreach (ObjetoEscena c in m_ListaEscena)
            {
                ///--- es un cubo
                if (c.tipo == 0)
                {
                    pCubos[indiceCubo].posX = c.posX * 2;
                    pCubos[indiceCubo].posY = c.posY * 2;
                    pCubos[indiceCubo].tex = 0;
                    pCubos[indiceCubo].texLat = 0;

                    pCubos[indiceCubo].lados = new bool[4];

                    for (int i = 0; i < 4; i++)
                        pCubos[indiceCubo].lados[i] = false;

                    indiceCubo++;
                }
            }

            indiceCubo = 0;

            foreach (CUBO_Lista itor2 in pCubos)
            {
                
                foreach (CUBO_Lista itor in pCubos)
                {

                    if ((itor.posX == itor2.posX - 8)
                        && (itor.posY == itor2.posY)
                        && (itor.texLat != 18 && itor.texLat != 19))
                    {
                        itor.lados[0] = true;
                    }

                    if ((itor.posX == itor2.posX)
                        && (itor.posY == itor2.posY - 8)
                        && (itor.texLat != 18 && itor.texLat != 19))
                    {
                        itor.lados[3] = true;
                    }

                    if ((itor.posX == itor2.posX + 8)
                        && (itor.posY == itor2.posY)
                        && (itor.texLat != 18 && itor.texLat != 19))
                    {
                        itor.lados[2] = true;
                    }

                    if ((itor.posX == itor2.posX)
                        && (itor.posY == itor2.posY + 8)
                        && (itor.texLat != 18 && itor.texLat != 19))
                    {
                        itor.lados[1] = true;
                    }
                    
                }

                indiceCubo++;
                
            }

            //////////////////////////////////////////////////

            indiceCubo = 0;

            foreach (ObjetoEscena itor in m_ListaEscena)
            {
                ///--- es un obj
                if ((itor.tipo == 1) && (itor.id > 1))
                {
                    indiceCubo++;
                }
            }


            int NumObjs = indiceCubo;

            OBJETO_Lista[] pObjetos = new OBJETO_Lista[NumObjs];

            indiceCubo = 0;

            //calculamos el arraty de objetos pObjs
            foreach (ObjetoEscena itor in m_ListaEscena)
            {
                ///--- es un obj
                if ((itor.tipo == 1) && (itor.id > 1))
                {
                    pObjetos[indiceCubo].posX = itor.posX * 2;
                    pObjetos[indiceCubo].posY = itor.posY * 2;
                    pObjetos[indiceCubo].rot = itor.rotation;

                    switch (itor.id)
                    {
                        case 2:
                        {
                            ///--- gema azul
                            pObjetos[indiceCubo].tipo = 0;
                            break;
                        }
                        case 3:
                        {
                            ///--- gema roja
                            pObjetos[indiceCubo].tipo = 1;
                            break;
                        }
                        case 4:
                        {
                            ///--- gema verde
                            pObjetos[indiceCubo].tipo = 3;
                            break;
                        }
                        case 5:
                        {
                            ///--- gema amarilla
                            pObjetos[indiceCubo].tipo = 2;
                            break;
                        }
                        case 6:
                        {
                            ///--- impulsor
                            pObjetos[indiceCubo].tipo = 8;
                            break;
                        }
                        case 7:
                        {
                            ///--- llave roja
                            pObjetos[indiceCubo].tipo = 5;
                            break;
                        }
                        case 8:
                        {
                            ///--- llave azul
                            pObjetos[indiceCubo].tipo = 6;
                            break;
                        }
                        default:
                        {                            
                            ///--- error
                            pObjetos[indiceCubo].tipo = 0;
                            break;
                        }
                    }                    

                    indiceCubo++;
                }
            }



            //////////////////////////////////////////////////

            indiceCubo = 0;

            foreach (ObjetoEscena itor in m_ListaEscena)
            {
                ///--- es un enemigo
                if (itor.tipo == 2)
                {
                    indiceCubo++;
                }
            }


            int NumEne = indiceCubo;

            ENEMIGO_Lista[] pEnemigos = new ENEMIGO_Lista[NumEne];

            indiceCubo = 0;

           
            //calculamos el arraty de enemigos pEne
            foreach (ObjetoEscena itor in m_ListaEscena)
            {
                ///--- es un enemigo
                if (itor.tipo == 2)
                {
                    pEnemigos[indiceCubo].posX = itor.posX * 2;
                    pEnemigos[indiceCubo].posY = itor.posY * 2;
                    pEnemigos[indiceCubo].rot = itor.rotation;

                    switch (itor.id)
                    {
                        case 0:
                        {
                            ///--- rebota
                            pEnemigos[indiceCubo].tipo = 3;
                            break;
                        }
                        case 1:
                        {
                            ///--- rectos
                            pEnemigos[indiceCubo].tipo = 2;
                            break;
                        }
                        case 2:
                        {
                            ///--- dirigidos
                            pEnemigos[indiceCubo].tipo = 1;
                            break;
                        }
                        case 3:
                        {
                            ///--- busca
                            pEnemigos[indiceCubo].tipo = 0;
                            break;
                        }
                    }

                    indiceCubo++;
                }
            }

            

            //////////////////////////////////////////////////

            indiceCubo = 0;

            foreach (ObjetoEscena itor in m_ListaEscena)
            {
                ///--- es un transport
                if (itor.tipo == 3)
                {
                    indiceCubo++;
                }
            }

            int NumTrans = indiceCubo;

            TRANS_Lista[] pTrans = new TRANS_Lista[NumTrans];

            indiceCubo = 0;


            //calculamos el arraty de transportadores pTrans
            foreach (ObjetoEscena itor in m_ListaEscena)
            {
                ///--- es un transport
                if (itor.tipo == 3)
                {
                    pTrans[indiceCubo].posX = itor.posX * 2;
                    pTrans[indiceCubo].posY = itor.posY * 2;
                    pTrans[indiceCubo].id = itor.id;
                }
            }
            
            ////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////

            NIVEL Nivel = new NIVEL();

            Nivel.Gemas = 0;

            foreach (ObjetoEscena itor in m_ListaEscena)
            {
                if (itor.tipo == 1)
                {
                    ///--- entrada
                    if (itor.id == 1)
                    {
                        Nivel.IniX = itor.posX * 2;
                        Nivel.IniY = itor.posY * 2;
                    }
                    ///--- salida
                    else if (itor.id == 0)
                    {
                        Nivel.FinX = itor.posX * 2;
                        Nivel.FinY = itor.posY * 2;
                    }
                    ///--- gema azul
                    else if (itor.id == 2)
                    {
                        Nivel.Gemas++;
                    }
                }
            }

            Nivel.NumSecX = NumXSec;
            Nivel.NumSecY = NumYSec;            
            Nivel.Fondo = m_TheForm.comboBoxFondo.SelectedIndex;


            char[] id = { 'O', 'Z', 'O', 'N', 'E', ' ', '2', '\0' };

            BLOQUE[] Bloque = new BLOQUE[5];

            Bloque[0].longitud = cube_count * 20;
            Bloque[1].longitud = NumSec * 508;
            Bloque[2].longitud = NumEne * 16;
            Bloque[3].longitud = NumObjs * 16;
            Bloque[4].longitud = NumTrans * 12;

            int desp = 0;

            desp += 12; ///--- cabecera
            desp += 40; ///--- bloques
            desp += 32; ///--- nivel

            Bloque[0].desplaz = desp;

            desp += Bloque[0].longitud;

            Bloque[1].desplaz = desp;

            desp += Bloque[1].longitud;

            Bloque[2].desplaz = desp;

            desp += Bloque[2].longitud;

            Bloque[3].desplaz = desp;

            desp += Bloque[3].longitud;

            Bloque[4].desplaz = desp;

            ////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////

            ///--- cabecera
            bw.Write(id);
            bw.Write((int)69);

            ///--- bloques
            foreach (BLOQUE b in Bloque)
            {
                bw.Write(b.desplaz);
                bw.Write(b.longitud);
            }

            ///--- nivel
            bw.Write(Nivel.NumSecX);
            bw.Write(Nivel.NumSecY);
            bw.Write(Nivel.IniX);
            bw.Write(Nivel.IniY);
            bw.Write(Nivel.FinX);
            bw.Write(Nivel.FinY);
            bw.Write(Nivel.Fondo);
            bw.Write(Nivel.Gemas);

            ///--- cubos
            foreach (CUBO_Lista c in pCubos)
            {
                bw.Write(c.posX);
                bw.Write(c.posY);
                bw.Write(c.tex);
                bw.Write(c.texLat);
                
                for (int i=0; i<4; i++)
                {
                    bw.Write(c.lados[i]);
                }                
            }

            ///--- sectores
            foreach (SECTOR_Lista s in pSectores)
            {
                bw.Write(s.numCubos);
                bw.Write(s.numObjs);
                
                for (int i = 0; i < 25; i++)
                {
                    bw.Write(s.IndCub[i]);
                }

                for (int i = 0; i < 100; i++)
                {
                    bw.Write(s.IndObj[i]);
                }
            }

            ///--- enemigos
            foreach (ENEMIGO_Lista e in pEnemigos)
            {
                bw.Write(e.posX);
                bw.Write(e.posY);
                bw.Write(e.tipo);
                bw.Write(e.rot);               
            }

            ///--- objetos
            foreach (OBJETO_Lista o in pObjetos)
            {
                bw.Write(o.posX);
                bw.Write(o.posY);
                bw.Write(o.tipo);
                bw.Write(o.rot);
            }

            ///--- transport
            foreach (TRANS_Lista t in pTrans)
            {
                bw.Write(t.posX);
                bw.Write(t.posY);
                bw.Write(t.id);
            }

            ExportLevel(bw, 1);
            ExportLevel(bw, 2);

            bw.Close();
            bw = null;

            fileStream.Close();
            fileStream = null;
        }


        //--------------------------------------------------------------------
        // Función:    SaveSector
        // Propósito:  
        // Fecha:      jueves, 26 de enero de 2006, 14:41:50
        //--------------------------------------------------------------------
        private void SaveSector(BinaryWriter bw, Sector s)
        {
            ///--- importante pasar de little endian a big endian
            ///--- ya que java lee en big endian y c# escribe por
            ///--- defecto en little endian

            bw.Write((byte)s.staticObjectsIndices.Count);

            foreach (short sh in s.staticObjectsIndices)
            {
                bw.Write(System.Net.IPAddress.HostToNetworkOrder(sh));
            }
        }


        //--------------------------------------------------------------------
        // Función:    SaveObject
        // Propósito:  
        // Fecha:      miércoles, 01 de febrero de 2006, 11:30:42
        //--------------------------------------------------------------------
        private void SaveObject(BinaryWriter bw, ObjetoEscena oe)
        {
            ///--- importante pasar de little endian a big endian
            ///--- ya que java lee en big endian y c# escribe por
            ///--- defecto en little endian

            bw.Write(oe.tipo);
            bw.Write(oe.id);
            bw.Write(System.Net.IPAddress.HostToNetworkOrder(oe.posX));
            bw.Write(System.Net.IPAddress.HostToNetworkOrder(oe.posY));
            bw.Write(oe.rotation);

            ///--- si es un cubo comprobar si al lado tiene otros cubos
            if (oe.tipo == 0)
            {
                byte lados = 0;

                foreach (ObjetoEscena c in m_ListaEscena)
                {
                    if ((c.id == 10) || (c.id == 11))
                    {
                        if ((oe.id != 10) && (oe.id != 11))
                            continue;
                    }

                    ///--- es cubo 
                    if (c.tipo == 0) 
                    {                        
                        if ((c.posX == oe.posX) && (c.posY == oe.posY - 4))
                        {
                            ///--- por arriba tiene un cubo   
                            lados |= 1;
                        }

                        if ((c.posX == oe.posX + 4) && (c.posY == oe.posY))
                        {
                            ///--- por la derecha tiene un cubo
                            lados |= 2;                            
                        }

                        if ((c.posX == oe.posX) && (c.posY == oe.posY + 4))
                        {
                            ///--- por abajo tiene un cubo   
                            lados |= 4;                                                     
                        }

                        if ((c.posX == oe.posX - 4) && (c.posY == oe.posY))
                        {
                            ///--- por la izquierda tiene un cubo
                            lados |= 8;
                        }                        
                    }
                }

                bw.Write(lados);
            }            
        }


        //--------------------------------------------------------------------
        // Función:    ResetScene
        // Propósito:  
        // Fecha:      miércoles, 25 de enero de 2006, 12:10:07
        //--------------------------------------------------------------------
        public void ResetScene()
        {
            m_TheForm.comboBoxPiso.SelectedIndex = 0;
            m_bStart = m_bExit = false;
            m_iTeleport = m_iCubeCount = m_iGemCount = 0;
            m_ListaEscena.Clear();

            m_ListaNivel1.Clear();
            m_ListaNivel2.Clear();

            m_TheForm.textBoxCubos.Text = "0";
            m_TheForm.textBoxGemas.Text = "0";
            m_TheForm.textBoxTeleport.Text = "0";
        }
    }
}
