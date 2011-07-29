namespace Editor
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.radioCubos = new System.Windows.Forms.RadioButton();
            this.radioItems = new System.Windows.Forms.RadioButton();
            this.radioEnemigos = new System.Windows.Forms.RadioButton();
            this.radioTransport = new System.Windows.Forms.RadioButton();
            this.listViewTextures = new System.Windows.Forms.ListView();
            this.imageListCubos = new System.Windows.Forms.ImageList(this.components);
            this.labelGemas = new System.Windows.Forms.Label();
            this.textBoxGemas = new System.Windows.Forms.TextBox();
            this.labelTeleport = new System.Windows.Forms.Label();
            this.textBoxTeleport = new System.Windows.Forms.TextBox();
            this.labelX = new System.Windows.Forms.Label();
            this.textBoxX = new System.Windows.Forms.TextBox();
            this.labelY = new System.Windows.Forms.Label();
            this.textBoxY = new System.Windows.Forms.TextBox();
            this.horizontalScrollBar = new System.Windows.Forms.HScrollBar();
            this.verticalScrollBar = new System.Windows.Forms.VScrollBar();
            this.checkBoxPantalla = new System.Windows.Forms.CheckBox();
            this.menuStrip = new System.Windows.Forms.MenuStrip();
            this.archivoToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.nuevoToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.abrirToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.guardarComoToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.salirToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.archivoToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.nuevoToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.abrirToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.guardarComoToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.salirToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveFileDialog = new System.Windows.Forms.SaveFileDialog();
            this.textBoxCubos = new System.Windows.Forms.TextBox();
            this.labelCubos = new System.Windows.Forms.Label();
            this.groupBox = new System.Windows.Forms.GroupBox();
            this.numericUpDownCursorX = new System.Windows.Forms.NumericUpDown();
            this.numericUpDownCursorY = new System.Windows.Forms.NumericUpDown();
            this.labelCursorX = new System.Windows.Forms.Label();
            this.labelCursorY = new System.Windows.Forms.Label();
            this.imageListItems = new System.Windows.Forms.ImageList(this.components);
            this.buttonRotacion = new System.Windows.Forms.Button();
            this.pictureBoxRendering = new System.Windows.Forms.PictureBox();
            this.openFileDialog = new System.Windows.Forms.OpenFileDialog();
            this.imageListEnems = new System.Windows.Forms.ImageList(this.components);
            this.checkBoxFondo = new System.Windows.Forms.CheckBox();
            this.comboBoxFondo = new System.Windows.Forms.ComboBox();
            this.imageListTrans = new System.Windows.Forms.ImageList(this.components);
            this.label1 = new System.Windows.Forms.Label();
            this.comboBoxPiso = new System.Windows.Forms.ComboBox();
            this.exportarParaOzoneToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
            this.menuStrip.SuspendLayout();
            this.groupBox.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownCursorX)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownCursorY)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxRendering)).BeginInit();
            this.SuspendLayout();
            // 
            // radioCubos
            // 
            this.radioCubos.Appearance = System.Windows.Forms.Appearance.Button;
            this.radioCubos.AutoSize = true;
            this.radioCubos.Location = new System.Drawing.Point(11, 36);
            this.radioCubos.Name = "radioCubos";
            this.radioCubos.Size = new System.Drawing.Size(47, 23);
            this.radioCubos.TabIndex = 0;
            this.radioCubos.TabStop = true;
            this.radioCubos.Text = "Cubos";
            this.radioCubos.UseVisualStyleBackColor = true;
            this.radioCubos.CheckedChanged += new System.EventHandler(this.radioCubos_CheckedChanged);
            // 
            // radioItems
            // 
            this.radioItems.Appearance = System.Windows.Forms.Appearance.Button;
            this.radioItems.AutoSize = true;
            this.radioItems.Location = new System.Drawing.Point(84, 65);
            this.radioItems.Name = "radioItems";
            this.radioItems.Size = new System.Drawing.Size(42, 23);
            this.radioItems.TabIndex = 2;
            this.radioItems.TabStop = true;
            this.radioItems.Text = "Items";
            this.radioItems.UseVisualStyleBackColor = true;
            this.radioItems.CheckedChanged += new System.EventHandler(this.radioItems_CheckedChanged);
            // 
            // radioEnemigos
            // 
            this.radioEnemigos.Appearance = System.Windows.Forms.Appearance.Button;
            this.radioEnemigos.AutoSize = true;
            this.radioEnemigos.Location = new System.Drawing.Point(11, 65);
            this.radioEnemigos.Name = "radioEnemigos";
            this.radioEnemigos.Size = new System.Drawing.Size(63, 23);
            this.radioEnemigos.TabIndex = 3;
            this.radioEnemigos.TabStop = true;
            this.radioEnemigos.Text = "Enemigos";
            this.radioEnemigos.UseVisualStyleBackColor = true;
            this.radioEnemigos.CheckedChanged += new System.EventHandler(this.radioEnemigos_CheckedChanged);
            // 
            // radioTransport
            // 
            this.radioTransport.Appearance = System.Windows.Forms.Appearance.Button;
            this.radioTransport.AutoSize = true;
            this.radioTransport.Location = new System.Drawing.Point(64, 36);
            this.radioTransport.Name = "radioTransport";
            this.radioTransport.Size = new System.Drawing.Size(62, 23);
            this.radioTransport.TabIndex = 4;
            this.radioTransport.TabStop = true;
            this.radioTransport.Text = "Transport";
            this.radioTransport.UseVisualStyleBackColor = true;
            this.radioTransport.CheckedChanged += new System.EventHandler(this.radioTransport_CheckedChanged);
            // 
            // listViewTextures
            // 
            this.listViewTextures.LargeImageList = this.imageListCubos;
            this.listViewTextures.Location = new System.Drawing.Point(11, 94);
            this.listViewTextures.MultiSelect = false;
            this.listViewTextures.Name = "listViewTextures";
            this.listViewTextures.Size = new System.Drawing.Size(115, 324);
            this.listViewTextures.TabIndex = 5;
            this.listViewTextures.UseCompatibleStateImageBehavior = false;
            // 
            // imageListCubos
            // 
            this.imageListCubos.ColorDepth = System.Windows.Forms.ColorDepth.Depth24Bit;
            this.imageListCubos.ImageSize = new System.Drawing.Size(19, 22);
            this.imageListCubos.TransparentColor = System.Drawing.Color.Transparent;
            // 
            // labelGemas
            // 
            this.labelGemas.AutoSize = true;
            this.labelGemas.Location = new System.Drawing.Point(3, 16);
            this.labelGemas.Name = "labelGemas";
            this.labelGemas.Size = new System.Drawing.Size(43, 13);
            this.labelGemas.TabIndex = 6;
            this.labelGemas.Text = "Gemas:";
            // 
            // textBoxGemas
            // 
            this.textBoxGemas.Enabled = false;
            this.textBoxGemas.Location = new System.Drawing.Point(6, 32);
            this.textBoxGemas.Name = "textBoxGemas";
            this.textBoxGemas.ReadOnly = true;
            this.textBoxGemas.Size = new System.Drawing.Size(40, 20);
            this.textBoxGemas.TabIndex = 7;
            // 
            // labelTeleport
            // 
            this.labelTeleport.AutoSize = true;
            this.labelTeleport.Location = new System.Drawing.Point(59, 16);
            this.labelTeleport.Name = "labelTeleport";
            this.labelTeleport.Size = new System.Drawing.Size(49, 13);
            this.labelTeleport.TabIndex = 8;
            this.labelTeleport.Text = "Teleport:";
            // 
            // textBoxTeleport
            // 
            this.textBoxTeleport.Enabled = false;
            this.textBoxTeleport.Location = new System.Drawing.Point(62, 32);
            this.textBoxTeleport.Name = "textBoxTeleport";
            this.textBoxTeleport.ReadOnly = true;
            this.textBoxTeleport.Size = new System.Drawing.Size(47, 20);
            this.textBoxTeleport.TabIndex = 9;
            // 
            // labelX
            // 
            this.labelX.AutoSize = true;
            this.labelX.Location = new System.Drawing.Point(3, 55);
            this.labelX.Name = "labelX";
            this.labelX.Size = new System.Drawing.Size(17, 13);
            this.labelX.TabIndex = 10;
            this.labelX.Text = "X:";
            // 
            // textBoxX
            // 
            this.textBoxX.Enabled = false;
            this.textBoxX.Location = new System.Drawing.Point(6, 71);
            this.textBoxX.Name = "textBoxX";
            this.textBoxX.ReadOnly = true;
            this.textBoxX.Size = new System.Drawing.Size(40, 20);
            this.textBoxX.TabIndex = 11;
            // 
            // labelY
            // 
            this.labelY.AutoSize = true;
            this.labelY.Location = new System.Drawing.Point(59, 55);
            this.labelY.Name = "labelY";
            this.labelY.Size = new System.Drawing.Size(17, 13);
            this.labelY.TabIndex = 12;
            this.labelY.Text = "Y:";
            // 
            // textBoxY
            // 
            this.textBoxY.Enabled = false;
            this.textBoxY.Location = new System.Drawing.Point(62, 71);
            this.textBoxY.Name = "textBoxY";
            this.textBoxY.ReadOnly = true;
            this.textBoxY.Size = new System.Drawing.Size(46, 20);
            this.textBoxY.TabIndex = 13;
            // 
            // horizontalScrollBar
            // 
            this.horizontalScrollBar.Location = new System.Drawing.Point(132, 679);
            this.horizontalScrollBar.Maximum = 200;
            this.horizontalScrollBar.Name = "horizontalScrollBar";
            this.horizontalScrollBar.Size = new System.Drawing.Size(740, 17);
            this.horizontalScrollBar.TabIndex = 14;
            this.horizontalScrollBar.Scroll += new System.Windows.Forms.ScrollEventHandler(this.horizontalScrollBar_Scroll);
            // 
            // verticalScrollBar
            // 
            this.verticalScrollBar.Location = new System.Drawing.Point(875, 36);
            this.verticalScrollBar.Maximum = 200;
            this.verticalScrollBar.Name = "verticalScrollBar";
            this.verticalScrollBar.Size = new System.Drawing.Size(17, 640);
            this.verticalScrollBar.TabIndex = 15;
            this.verticalScrollBar.Scroll += new System.Windows.Forms.ScrollEventHandler(this.verticalScrollBar_Scroll);
            // 
            // checkBoxPantalla
            // 
            this.checkBoxPantalla.AutoSize = true;
            this.checkBoxPantalla.Location = new System.Drawing.Point(72, 533);
            this.checkBoxPantalla.Name = "checkBoxPantalla";
            this.checkBoxPantalla.Size = new System.Drawing.Size(54, 17);
            this.checkBoxPantalla.TabIndex = 16;
            this.checkBoxPantalla.Text = "Borde";
            this.checkBoxPantalla.UseVisualStyleBackColor = true;
            this.checkBoxPantalla.CheckedChanged += new System.EventHandler(this.checkBoxPantalla_CheckedChanged);
            // 
            // menuStrip
            // 
            this.menuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.archivoToolStripMenuItem1});
            this.menuStrip.Location = new System.Drawing.Point(0, 0);
            this.menuStrip.Name = "menuStrip";
            this.menuStrip.Size = new System.Drawing.Size(896, 24);
            this.menuStrip.TabIndex = 17;
            this.menuStrip.Text = "menuStrip1";
            // 
            // archivoToolStripMenuItem1
            // 
            this.archivoToolStripMenuItem1.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.nuevoToolStripMenuItem1,
            this.abrirToolStripMenuItem1,
            this.guardarComoToolStripMenuItem1,
            this.exportarParaOzoneToolStripMenuItem,
            this.toolStripSeparator2,
            this.salirToolStripMenuItem1});
            this.archivoToolStripMenuItem1.Name = "archivoToolStripMenuItem1";
            this.archivoToolStripMenuItem1.Size = new System.Drawing.Size(59, 20);
            this.archivoToolStripMenuItem1.Text = "Archivo";
            // 
            // nuevoToolStripMenuItem1
            // 
            this.nuevoToolStripMenuItem1.Name = "nuevoToolStripMenuItem1";
            this.nuevoToolStripMenuItem1.Size = new System.Drawing.Size(194, 22);
            this.nuevoToolStripMenuItem1.Text = "Nuevo";
            this.nuevoToolStripMenuItem1.Click += new System.EventHandler(this.nuevoToolStripMenuItem1_Click);
            // 
            // abrirToolStripMenuItem1
            // 
            this.abrirToolStripMenuItem1.Name = "abrirToolStripMenuItem1";
            this.abrirToolStripMenuItem1.Size = new System.Drawing.Size(194, 22);
            this.abrirToolStripMenuItem1.Text = "Abrir...";
            this.abrirToolStripMenuItem1.Click += new System.EventHandler(this.abrirToolStripMenuItem1_Click);
            // 
            // guardarComoToolStripMenuItem1
            // 
            this.guardarComoToolStripMenuItem1.Name = "guardarComoToolStripMenuItem1";
            this.guardarComoToolStripMenuItem1.Size = new System.Drawing.Size(194, 22);
            this.guardarComoToolStripMenuItem1.Text = "Guardar Como...";
            this.guardarComoToolStripMenuItem1.Click += new System.EventHandler(this.guardarComoToolStripMenuItem1_Click);
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(191, 6);
            // 
            // salirToolStripMenuItem1
            // 
            this.salirToolStripMenuItem1.Name = "salirToolStripMenuItem1";
            this.salirToolStripMenuItem1.Size = new System.Drawing.Size(194, 22);
            this.salirToolStripMenuItem1.Text = "Salir";
            this.salirToolStripMenuItem1.Click += new System.EventHandler(this.salirToolStripMenuItem1_Click);
            // 
            // archivoToolStripMenuItem
            // 
            this.archivoToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.nuevoToolStripMenuItem,
            this.abrirToolStripMenuItem,
            this.guardarComoToolStripMenuItem,
            this.toolStripSeparator1,
            this.salirToolStripMenuItem});
            this.archivoToolStripMenuItem.Name = "archivoToolStripMenuItem";
            this.archivoToolStripMenuItem.Size = new System.Drawing.Size(55, 20);
            this.archivoToolStripMenuItem.Text = "Archivo";
            // 
            // nuevoToolStripMenuItem
            // 
            this.nuevoToolStripMenuItem.Name = "nuevoToolStripMenuItem";
            this.nuevoToolStripMenuItem.Size = new System.Drawing.Size(163, 22);
            this.nuevoToolStripMenuItem.Text = "Nuevo";
            this.nuevoToolStripMenuItem.Click += new System.EventHandler(this.nuevoToolStripMenuItem_Click);
            // 
            // abrirToolStripMenuItem
            // 
            this.abrirToolStripMenuItem.Name = "abrirToolStripMenuItem";
            this.abrirToolStripMenuItem.Size = new System.Drawing.Size(163, 22);
            this.abrirToolStripMenuItem.Text = "Abrir...";
            this.abrirToolStripMenuItem.Click += new System.EventHandler(this.abrirToolStripMenuItem_Click);
            // 
            // guardarComoToolStripMenuItem
            // 
            this.guardarComoToolStripMenuItem.Name = "guardarComoToolStripMenuItem";
            this.guardarComoToolStripMenuItem.Size = new System.Drawing.Size(163, 22);
            this.guardarComoToolStripMenuItem.Text = "Guardar Como...";
            this.guardarComoToolStripMenuItem.Click += new System.EventHandler(this.guardarComoToolStripMenuItem_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(160, 6);
            // 
            // salirToolStripMenuItem
            // 
            this.salirToolStripMenuItem.Name = "salirToolStripMenuItem";
            this.salirToolStripMenuItem.Size = new System.Drawing.Size(163, 22);
            this.salirToolStripMenuItem.Text = "Salir";
            // 
            // saveFileDialog
            // 
            this.saveFileDialog.DefaultExt = "hmf";
            this.saveFileDialog.Filter = "Hydrium Mobile Files (*.hmf)|*.hmf|All files (*.*)|*.*";
            this.saveFileDialog.Title = "Guardar Mapa...";
            this.saveFileDialog.FileOk += new System.ComponentModel.CancelEventHandler(this.saveFileDialog_FileOk);
            // 
            // textBoxCubos
            // 
            this.textBoxCubos.Enabled = false;
            this.textBoxCubos.Location = new System.Drawing.Point(6, 110);
            this.textBoxCubos.Name = "textBoxCubos";
            this.textBoxCubos.ReadOnly = true;
            this.textBoxCubos.Size = new System.Drawing.Size(41, 20);
            this.textBoxCubos.TabIndex = 18;
            // 
            // labelCubos
            // 
            this.labelCubos.AutoSize = true;
            this.labelCubos.Location = new System.Drawing.Point(3, 94);
            this.labelCubos.Name = "labelCubos";
            this.labelCubos.Size = new System.Drawing.Size(40, 13);
            this.labelCubos.TabIndex = 19;
            this.labelCubos.Text = "Cubos:";
            // 
            // groupBox
            // 
            this.groupBox.Controls.Add(this.labelGemas);
            this.groupBox.Controls.Add(this.textBoxCubos);
            this.groupBox.Controls.Add(this.labelCubos);
            this.groupBox.Controls.Add(this.textBoxGemas);
            this.groupBox.Controls.Add(this.labelTeleport);
            this.groupBox.Controls.Add(this.textBoxTeleport);
            this.groupBox.Controls.Add(this.textBoxX);
            this.groupBox.Controls.Add(this.labelX);
            this.groupBox.Controls.Add(this.labelY);
            this.groupBox.Controls.Add(this.textBoxY);
            this.groupBox.Location = new System.Drawing.Point(11, 556);
            this.groupBox.Name = "groupBox";
            this.groupBox.Size = new System.Drawing.Size(115, 140);
            this.groupBox.TabIndex = 20;
            this.groupBox.TabStop = false;
            // 
            // numericUpDownCursorX
            // 
            this.numericUpDownCursorX.Location = new System.Drawing.Point(11, 475);
            this.numericUpDownCursorX.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.numericUpDownCursorX.Name = "numericUpDownCursorX";
            this.numericUpDownCursorX.Size = new System.Drawing.Size(40, 20);
            this.numericUpDownCursorX.TabIndex = 21;
            this.numericUpDownCursorX.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.numericUpDownCursorX.ValueChanged += new System.EventHandler(this.numericUpDownCursorX_ValueChanged);
            // 
            // numericUpDownCursorY
            // 
            this.numericUpDownCursorY.Location = new System.Drawing.Point(79, 475);
            this.numericUpDownCursorY.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.numericUpDownCursorY.Name = "numericUpDownCursorY";
            this.numericUpDownCursorY.Size = new System.Drawing.Size(40, 20);
            this.numericUpDownCursorY.TabIndex = 22;
            this.numericUpDownCursorY.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.numericUpDownCursorY.ValueChanged += new System.EventHandler(this.numericUpDownCursorY_ValueChanged);
            // 
            // labelCursorX
            // 
            this.labelCursorX.AutoSize = true;
            this.labelCursorX.Location = new System.Drawing.Point(8, 459);
            this.labelCursorX.Name = "labelCursorX";
            this.labelCursorX.Size = new System.Drawing.Size(50, 13);
            this.labelCursorX.TabIndex = 23;
            this.labelCursorX.Text = "Cursor X:";
            // 
            // labelCursorY
            // 
            this.labelCursorY.AutoSize = true;
            this.labelCursorY.Location = new System.Drawing.Point(76, 459);
            this.labelCursorY.Name = "labelCursorY";
            this.labelCursorY.Size = new System.Drawing.Size(50, 13);
            this.labelCursorY.TabIndex = 24;
            this.labelCursorY.Text = "Cursor Y:";
            // 
            // imageListItems
            // 
            this.imageListItems.ColorDepth = System.Windows.Forms.ColorDepth.Depth24Bit;
            this.imageListItems.ImageSize = new System.Drawing.Size(16, 16);
            this.imageListItems.TransparentColor = System.Drawing.Color.Transparent;
            // 
            // buttonRotacion
            // 
            this.buttonRotacion.Image = global::Editor.Properties.Resources.flecha1;
            this.buttonRotacion.Location = new System.Drawing.Point(11, 424);
            this.buttonRotacion.Name = "buttonRotacion";
            this.buttonRotacion.Size = new System.Drawing.Size(40, 25);
            this.buttonRotacion.TabIndex = 25;
            this.buttonRotacion.UseVisualStyleBackColor = true;
            this.buttonRotacion.Click += new System.EventHandler(this.buttonRotacion_Click);
            // 
            // pictureBoxRendering
            // 
            this.pictureBoxRendering.BackColor = System.Drawing.Color.White;
            this.pictureBoxRendering.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.pictureBoxRendering.Location = new System.Drawing.Point(132, 36);
            this.pictureBoxRendering.Name = "pictureBoxRendering";
            this.pictureBoxRendering.Size = new System.Drawing.Size(740, 640);
            this.pictureBoxRendering.TabIndex = 1;
            this.pictureBoxRendering.TabStop = false;
            this.pictureBoxRendering.MouseLeave += new System.EventHandler(this.pictureBoxRendering_MouseLeave);
            this.pictureBoxRendering.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pictureBoxRendering_MouseDown);
            this.pictureBoxRendering.MouseMove += new System.Windows.Forms.MouseEventHandler(this.pictureBoxRendering_MouseMove);
            this.pictureBoxRendering.Paint += new System.Windows.Forms.PaintEventHandler(this.pictureBoxRendering_Paint);
            this.pictureBoxRendering.MouseEnter += new System.EventHandler(this.pictureBoxRendering_MouseEnter);
            // 
            // openFileDialog
            // 
            this.openFileDialog.DefaultExt = "hmf";
            this.openFileDialog.Filter = "Hydrium Mobile Files (*.hmf)|*.hmf|All files (*.*)|*.*";
            this.openFileDialog.Title = "Abrir Mapa...";
            this.openFileDialog.FileOk += new System.ComponentModel.CancelEventHandler(this.openFileDialog_FileOk);
            // 
            // imageListEnems
            // 
            this.imageListEnems.ColorDepth = System.Windows.Forms.ColorDepth.Depth24Bit;
            this.imageListEnems.ImageSize = new System.Drawing.Size(16, 16);
            this.imageListEnems.TransparentColor = System.Drawing.Color.Transparent;
            // 
            // checkBoxFondo
            // 
            this.checkBoxFondo.AutoSize = true;
            this.checkBoxFondo.Location = new System.Drawing.Point(11, 533);
            this.checkBoxFondo.Name = "checkBoxFondo";
            this.checkBoxFondo.Size = new System.Drawing.Size(56, 17);
            this.checkBoxFondo.TabIndex = 26;
            this.checkBoxFondo.Text = "Fondo";
            this.checkBoxFondo.UseVisualStyleBackColor = true;
            this.checkBoxFondo.CheckedChanged += new System.EventHandler(this.checkBoxFondo_CheckedChanged);
            // 
            // comboBoxFondo
            // 
            this.comboBoxFondo.FormattingEnabled = true;
            this.comboBoxFondo.Location = new System.Drawing.Point(54, 506);
            this.comboBoxFondo.Name = "comboBoxFondo";
            this.comboBoxFondo.Size = new System.Drawing.Size(72, 21);
            this.comboBoxFondo.TabIndex = 27;
            this.comboBoxFondo.SelectedIndexChanged += new System.EventHandler(this.comboBoxFondo_SelectedIndexChanged);
            // 
            // imageListTrans
            // 
            this.imageListTrans.ColorDepth = System.Windows.Forms.ColorDepth.Depth24Bit;
            this.imageListTrans.ImageSize = new System.Drawing.Size(16, 16);
            this.imageListTrans.TransparentColor = System.Drawing.Color.Transparent;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(8, 509);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(40, 13);
            this.label1.TabIndex = 28;
            this.label1.Text = "Fondo:";
            // 
            // comboBoxPiso
            // 
            this.comboBoxPiso.FormattingEnabled = true;
            this.comboBoxPiso.Items.AddRange(new object[] {
            "Piso 0",
            "Piso 1",
            "Piso 2"});
            this.comboBoxPiso.Location = new System.Drawing.Point(57, 428);
            this.comboBoxPiso.Name = "comboBoxPiso";
            this.comboBoxPiso.Size = new System.Drawing.Size(69, 21);
            this.comboBoxPiso.TabIndex = 29;
            // 
            // exportarParaOzoneToolStripMenuItem
            // 
            this.exportarParaOzoneToolStripMenuItem.Name = "exportarParaOzoneToolStripMenuItem";
            this.exportarParaOzoneToolStripMenuItem.Size = new System.Drawing.Size(194, 22);
            this.exportarParaOzoneToolStripMenuItem.Text = "Exportar Para Ozone..";
            this.exportarParaOzoneToolStripMenuItem.Click += new System.EventHandler(this.exportarParaOzoneToolStripMenuItem_Click);
            // 
            // saveFileDialog1
            // 
            this.saveFileDialog1.DefaultExt = "nhe";
            this.saveFileDialog1.Filter = "Ozone Level (*.nhe)|*.nhe|All files (*.*)|*.*";
            this.saveFileDialog1.Title = "Exportar Para Ozone";
            this.saveFileDialog1.FileOk += new System.ComponentModel.CancelEventHandler(this.saveFileDialog1_FileOk);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(896, 700);
            this.Controls.Add(this.comboBoxPiso);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.comboBoxFondo);
            this.Controls.Add(this.checkBoxFondo);
            this.Controls.Add(this.buttonRotacion);
            this.Controls.Add(this.labelCursorY);
            this.Controls.Add(this.labelCursorX);
            this.Controls.Add(this.numericUpDownCursorY);
            this.Controls.Add(this.numericUpDownCursorX);
            this.Controls.Add(this.groupBox);
            this.Controls.Add(this.checkBoxPantalla);
            this.Controls.Add(this.verticalScrollBar);
            this.Controls.Add(this.horizontalScrollBar);
            this.Controls.Add(this.listViewTextures);
            this.Controls.Add(this.radioTransport);
            this.Controls.Add(this.radioEnemigos);
            this.Controls.Add(this.radioItems);
            this.Controls.Add(this.pictureBoxRendering);
            this.Controls.Add(this.radioCubos);
            this.Controls.Add(this.menuStrip);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MainMenuStrip = this.menuStrip;
            this.MaximizeBox = false;
            this.Name = "MainForm";
            this.Text = "Hydrium Mobile Editor";
            this.Load += new System.EventHandler(this.MainForm_Load);
            this.menuStrip.ResumeLayout(false);
            this.menuStrip.PerformLayout();
            this.groupBox.ResumeLayout(false);
            this.groupBox.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownCursorX)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownCursorY)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxRendering)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        public System.Windows.Forms.RadioButton radioCubos;
        public System.Windows.Forms.PictureBox pictureBoxRendering;
        public System.Windows.Forms.RadioButton radioItems;
        public System.Windows.Forms.RadioButton radioEnemigos;
        public System.Windows.Forms.RadioButton radioTransport;
        public System.Windows.Forms.ListView listViewTextures;
        public System.Windows.Forms.Label labelGemas;
        public System.Windows.Forms.TextBox textBoxGemas;
        public System.Windows.Forms.Label labelTeleport;
        public System.Windows.Forms.TextBox textBoxTeleport;
        public System.Windows.Forms.Label labelX;
        public System.Windows.Forms.TextBox textBoxX;
        public System.Windows.Forms.Label labelY;
        public System.Windows.Forms.TextBox textBoxY;
        public System.Windows.Forms.HScrollBar horizontalScrollBar;
        public System.Windows.Forms.VScrollBar verticalScrollBar;
        public System.Windows.Forms.ImageList imageListCubos;
        public System.Windows.Forms.CheckBox checkBoxPantalla;
        public System.Windows.Forms.MenuStrip menuStrip;
        public System.Windows.Forms.ToolStripMenuItem archivoToolStripMenuItem;
        public System.Windows.Forms.ToolStripMenuItem abrirToolStripMenuItem;
        public System.Windows.Forms.ToolStripMenuItem guardarComoToolStripMenuItem;
        public System.Windows.Forms.ToolStripMenuItem salirToolStripMenuItem;
        public System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.SaveFileDialog saveFileDialog;
        private System.Windows.Forms.ToolStripMenuItem nuevoToolStripMenuItem;
        public System.Windows.Forms.TextBox textBoxCubos;
        public System.Windows.Forms.Label labelCubos;
        private System.Windows.Forms.GroupBox groupBox;
        public System.Windows.Forms.NumericUpDown numericUpDownCursorX;
        public System.Windows.Forms.NumericUpDown numericUpDownCursorY;
        private System.Windows.Forms.Label labelCursorX;
        private System.Windows.Forms.Label labelCursorY;
        public System.Windows.Forms.ImageList imageListItems;
        private System.Windows.Forms.Button buttonRotacion;
        private System.Windows.Forms.OpenFileDialog openFileDialog;
        public System.Windows.Forms.ImageList imageListEnems;
        private System.Windows.Forms.ToolStripMenuItem archivoToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem abrirToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem guardarComoToolStripMenuItem1;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripMenuItem salirToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem nuevoToolStripMenuItem1;
        private System.Windows.Forms.CheckBox checkBoxFondo;
        public System.Windows.Forms.ComboBox comboBoxFondo;
        public System.Windows.Forms.ImageList imageListTrans;
        private System.Windows.Forms.Label label1;
        public System.Windows.Forms.ComboBox comboBoxPiso;
        private System.Windows.Forms.ToolStripMenuItem exportarParaOzoneToolStripMenuItem;
        private System.Windows.Forms.SaveFileDialog saveFileDialog1;
    }
}

