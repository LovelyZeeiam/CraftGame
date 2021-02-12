package xueli.gamengine.tool.view;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;
import xueli.gamengine.resource.*;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.resource.Shader;
import xueli.gamengine.view.ViewManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class ViewMonitor {

	private static AWTGLCanvas canvas;
	private static boolean debug = false;
	boolean needReload = false;
	private JFrame frmXuelisGuiMonitor;
	private FileAlterationMonitor fileMonitor = null;
	private File attachedFile;
	private int start_width = 600, start_height = 600;
	private ViewMonitor ctx;
	private LangManager langManager = new LangManager("res/");
	private Options options = new Options("res/");
	private ViewManager viewManager;
	private ShaderResource shaderResource = new ShaderResource("res/");
	private Shader guiShader;
	private Display display;
	private GuiResource guiResource;
	private TextureManager textureManager;

	public ViewMonitor() {
		initialize();
	}

	public static void main(String[] args) {
		Logger.info("来自XueLi: 针不戳，看着别人去漫展自己在家里打代码，针不戳~");
		Logger.info("来自XueLi: 所以请毫不吝啬的挑选宁的英雄吧！");

		if (args.length > 0 && args[0].equals("debug")) {
			Logger.info("来自XueLi: Debug模式开启~");
			debug = true;
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewMonitor window = new ViewMonitor();
					window.frmXuelisGuiMonitor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (!canvas.isValid())
							return;
						canvas.render();
						SwingUtilities.invokeLater(this);
					}
				});

			}

		});
	}

	private void initialize() {
		ctx = this;

		frmXuelisGuiMonitor = new JFrame();
		frmXuelisGuiMonitor.setTitle("XueLi's Gui Monitor");
		frmXuelisGuiMonitor.setBounds(100, 100, start_width, start_height);
		frmXuelisGuiMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 获取计算机屏幕宽和高
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 窗口居中
		frmXuelisGuiMonitor.setLocation((screenSize.width - start_width) / 2, (screenSize.height - start_height) / 2);

		JMenuBar menuBar = new JMenuBar();
		frmXuelisGuiMonitor.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setIcon(
				new ImageIcon(ViewMonitor.class.getResource("/com/sun/java/swing/plaf/windows/icons/File.gif")));
		mnNewMenu.setFont(new Font("Cascadia Code", Font.PLAIN, 12));
		menuBar.add(mnNewMenu);

		// Attach菜单有关
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setCurrentDirectory(new File("res/gui/"));
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "CG Gui File (*.json)";
			}

			@Override
			public boolean accept(File f) {
				String name = f.getName();
				return name.endsWith(".json") || name.endsWith(".JSON");
			}
		});

		JMenuItem mntmNewMenuItem = new JMenuItem("Attach");
		mnNewMenu.add(mntmNewMenuItem);

		JSeparator separator_1 = new JSeparator();
		mnNewMenu.add(separator_1);

		JMenuItem animChooseMenuItem = new JMenuItem("Animation");
		mnNewMenu.add(animChooseMenuItem);

		JMenu mnNewMenu_1 = new JMenu("About");
		mnNewMenu_1.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
		mnNewMenu_1
				.setIcon(new ImageIcon(ViewMonitor.class.getResource("/javax/swing/plaf/basic/icons/JavaCup16.png")));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Tutorial");
		mnNewMenu_1.add(mntmNewMenuItem_1);

		JSeparator separator = new JSeparator();
		mnNewMenu_1.add(separator);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("About");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		frmXuelisGuiMonitor.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmXuelisGuiMonitor.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		JLabel bottomStateLabel = new JLabel("XueLi's Gui Monitor");
		panel_2.add(bottomStateLabel);

		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// Attach菜单点击事件
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					attachedFile = fileChooser.getSelectedFile();
					needReload = true;
					bottomStateLabel.setText("Current File: " + attachedFile.getName());

					if (fileMonitor != null) {
						try {
							fileMonitor.stop();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(
							attachedFile.getParentFile());
					long interval = TimeUnit.SECONDS.toMillis(1);
					fileAlterationObserver.addListener(new FileListener(attachedFile, ctx));
					fileMonitor = new FileAlterationMonitor(interval, fileAlterationObserver);
					try {
						fileMonitor.start();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmXuelisGuiMonitor.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		// init GL
		GLData glData = new GLData();
		glData.samples = 4;
		glData.debug = true;
		glData.majorVersion = 3;
		glData.minorVersion = 2;
		glData.swapInterval = 1;

		canvas = new AWTGLCanvas(glData) {
			private static final long serialVersionUID = -5758847215745459382L;

			@Override
			public void initGL() {
				GL.createCapabilities();
				GLHelper.printDeviceInfo();

				display = new Display(this);

				langManager.loadLang();
				options.load();
				guiShader = shaderResource.load("gui", false);
				viewManager = new ViewManager(display, options, guiShader);
				textureManager = new TextureManager("res/", viewManager);
				guiResource = new GuiResource("res/", textureManager);
				viewManager.setResourceSource(guiResource);

				textureManager.load();

				langManager.setLang("zh-ch.lang");
				viewManager.setFont("Minecraft.ttf");

				// TODO: Restart to reload texture, reload language files

			}

			@Override
			public void paintGL() {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
				GL11.glClearColor(0, 0, 0, 1);

				GL11.glViewport(0, 0, getWidth(), getHeight());
				viewManager.size();

				if (attachedFile != null) {
					if (needReload) {
						guiResource.loadGui(attachedFile.getName(), langManager, true);
						viewManager.setGui(attachedFile.getName());
						needReload = false;
					}

					viewManager.draw();

				}

				GLHelper.checkGLError("GUI Render");

				swapBuffers();
			}

		};

		panel_1.add(canvas);

	}
}
