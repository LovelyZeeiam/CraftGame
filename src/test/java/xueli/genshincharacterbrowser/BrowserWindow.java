package xueli.genshincharacterbrowser;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.ButtonGroup;

import xueli.swingx.component.FullScreenLayeredPane;
import xueli.swingx.layout.CoverAllLayout;
import xueli.swingx.layout.VFlowLayout;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;

import javax.imageio.ImageIO;

public class BrowserWindow {

	private JFrame frmGenshin;
	private BrowserBean bean;
	
	/**
	 * Create the application.
	 */
	public BrowserWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		bean = new BrowserBean();
		
		frmGenshin = new JFrame();
		frmGenshin.setTitle("Genshin Character Browser");
		frmGenshin.setBounds(100, 100, 652, 456);
		frmGenshin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLayeredPane appPanel = new FullScreenLayeredPane();
//		appPanel.set
		frmGenshin.getContentPane().setLayout(new BorderLayout());
		frmGenshin.getContentPane().add(appPanel, BorderLayout.CENTER);
		appPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new CoverAllLayout());
		appPanel.add(mainPanel, BorderLayout.CENTER);
		appPanel.setLayer(mainPanel, JLayeredPane.DEFAULT_LAYER);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setOpaque(false);
		controlPanel.setLayout(new BorderLayout());
		mainPanel.add(controlPanel);
		
		JScrollPane charChooseScrollPane = new JScrollPane();
		charChooseScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		controlPanel.add(charChooseScrollPane, BorderLayout.SOUTH);
		
		JList<GenshinChar> charChooseList = new JList<>(bean.getCharList());
		charChooseList.setOpaque(false);
		charChooseList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		charChooseList.setVisibleRowCount(1);
		charChooseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		charChooseScrollPane.setViewportView(charChooseList);
		
		JPanel dimensionChoosePane = new JPanel();
		dimensionChoosePane.setOpaque(false);
		VFlowLayout vfl_dimensionChoosePane = new VFlowLayout();
		vfl_dimensionChoosePane.setAlignment(FlowLayout.CENTER);
		dimensionChoosePane.setLayout(vfl_dimensionChoosePane);
		controlPanel.add(dimensionChoosePane, BorderLayout.WEST);
		
		ButtonGroup rdbtnDimensionGroup = new ButtonGroup();
		
		JRadioButton rdbtnDimMondstadt = new JRadioButton("蒙德城");
		rdbtnDimMondstadt.setOpaque(false);
		dimensionChoosePane.add(rdbtnDimMondstadt);
		rdbtnDimensionGroup.add(rdbtnDimMondstadt);
		
		JRadioButton rdbtnDimLiyue = new JRadioButton("璃月港");
		rdbtnDimLiyue.setOpaque(false);
		dimensionChoosePane.add(rdbtnDimLiyue);
		rdbtnDimensionGroup.add(rdbtnDimLiyue);
		
		JRadioButton rdbtnDimInazuma = new JRadioButton("稻妻城");
		rdbtnDimInazuma.setOpaque(false);
		dimensionChoosePane.add(rdbtnDimInazuma);
		rdbtnDimensionGroup.add(rdbtnDimInazuma);
		
		JRadioButton rdbtnDimSumeru = new JRadioButton("须弥城");
		rdbtnDimSumeru.setOpaque(false);
		dimensionChoosePane.add(rdbtnDimSumeru);
		rdbtnDimensionGroup.add(rdbtnDimSumeru);
		
		JRadioButton rdbtnDimMore = new JRadioButton("敬请期待");
		rdbtnDimMore.setOpaque(false);
		rdbtnDimMore.setEnabled(false);
		dimensionChoosePane.add(rdbtnDimMore);
		
		CharPresentationPanel charPresentPanel = new CharPresentationPanel();
		mainPanel.add(charPresentPanel);
		
		JPanel loadingLabelContainer = new JPanel();
		loadingLabelContainer.setOpaque(false);
		JLabel loadingLabel = new JLabel("Loading...");
		loadingLabel.setOpaque(false);
		loadingLabelContainer.add(loadingLabel);
		
		appPanel.add(loadingLabelContainer, BorderLayout.CENTER);
		appPanel.setLayer(loadingLabelContainer, JLayeredPane.POPUP_LAYER);
		
		try {
			charPresentPanel.nameImage = ImageIO.read(new URL("https://uploadstatic.mihoyo.com/contentweb/20210617/2021061716243457405.png"));
			charPresentPanel.dialogImage = ImageIO.read(new URL("https://uploadstatic.mihoyo.com/contentweb/20210618/2021061815474660436.png"));
			charPresentPanel.portraitImage = ImageIO.read(new URL("https://webstatic.mihoyo.com/upload/contentweb/2022/07/04/44e2d916d9c13bca863d56423004fd98_5259521148631074871.png"));
			
			charPresentPanel.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		BrowserService.loadCity("150", client);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					UIManager.setLookAndFeel(new FlatDarkLaf());
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					BrowserWindow window = new BrowserWindow();
					window.frmGenshin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
