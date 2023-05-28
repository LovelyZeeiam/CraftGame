package xueli.clock;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;

import xueli.animation.AnimationBinding;
import xueli.animation.AnimationBindingBuilder;
import xueli.animation.AnimationManager;
import xueli.animation.Curves;
import xueli.animation.IntValueAnimationBinding;
import xueli.animation.TransitionBinding;
import xueli.animation.TransitionManager;
import xueli.clock.bean.ClockBean;
import xueli.clock.component.UserInfoPanel;
import xueli.clock.service.ClockService;
import xueli.game2.resource.ResourceIdentifier;
import xueli.game2.resource.provider.ClassLoaderResourceProvider;
import xueli.swingx.component.ImageView;
import xueli.swingx.component.SimpleLayerUI;
import xueli.swingx.layout.CoverAllLayout;
import xueli.swingx.layout.HorizontalFilledLayout;
import xueli.swingx.layout.HorizontalFilledLayout.HorizontalAlign;
import xueli.swingx.layout.HorizontalFilledLayout.VerticalAlign;
import xueli.swingx.layout.OffsetLayout;
import xueli.swingx.responsive.PropertyAccessible;
import xueli.swingx.responsive.TransitionBindings;
import xueli.swingx.responsive.ValueProvider;

// Swing搴撶湡鐨勫己澶� 涓�琛屼唬鐮佷袱琛宐ug 灏忓皬鐮佸啘鐑︽伡涓嶆�� LovelyZeeiam甯︽垜鍑哄彂~
public class ClockFrame {
	
	public static final AnimationManager M_ANIMATION_MANAGER = new AnimationManager(() -> System.currentTimeMillis());
	public static final TransitionManager M_TRANSITION_MANAGER = new TransitionManager(M_ANIMATION_MANAGER);
	public static final ClassLoaderResourceProvider RESOURCE_PROVIDER = new ClassLoaderResourceProvider();
	
	private JFrame frmMain;
	private ClockBean bean;
	
	private ClockService service;
	
	/**
	 * Create the application.
	 */
	public ClockFrame() {
		initialize();
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMain = new JFrame();
		frmMain.setTitle("Li.Clock");
		frmMain.setBounds(100, 100, 664, 467);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.getContentPane().setLayout(new BorderLayout());
		Dimension frameLastDimension = new Dimension(); // TODO: Make a memory class
		
		JPanel appPanel = new JPanel();
		appPanel.setDoubleBuffered(true);
		appPanel.setLayout(new CoverAllLayout());
		frmMain.getContentPane().add(appPanel, BorderLayout.CENTER);
			
		JPanel userInfoContainer = new JPanel();
		userInfoContainer.setOpaque(false);
		FlowLayout userInfoFlowLayout = new FlowLayout();
		userInfoFlowLayout.setVgap(10);
		userInfoFlowLayout.setHgap(10);
		userInfoFlowLayout.setAlignment(FlowLayout.RIGHT);
		OffsetLayout userInfoOffsetLayout = new OffsetLayout(userInfoFlowLayout);
		userInfoContainer.setLayout(userInfoOffsetLayout);
		appPanel.add(userInfoContainer);
			
			UserInfoPanel userInfoPanel = new UserInfoPanel();
			userInfoContainer.add(userInfoPanel);
			
		JPanel timeDateContainer = new JPanel();
		timeDateContainer.setOpaque(false);
		OffsetLayout timeDateOffsetLayout = new OffsetLayout(new HorizontalFilledLayout(VerticalAlign.ALIGN_CENTER, HorizontalAlign.CENTER));
		timeDateContainer.setLayout(timeDateOffsetLayout);
		
		SimpleLayerUI timeDateLayerUI = new SimpleLayerUI();
		JLayer<Component> timeDateLayer = new JLayer<>(timeDateContainer, timeDateLayerUI);
		timeDateLayer.setOpaque(false);
		
		appPanel.add(timeDateLayer);
		
			JPanel timeDatePanel = new JPanel();
			timeDatePanel.setOpaque(false);
			timeDatePanel.setLayout(new BorderLayout());
			timeDateContainer.add(timeDatePanel);
			
				JLabel lblTime = new JLabel("LABEL FOR TIME");
				lblTime.setFont(new Font("Cascadia Mono", Font.PLAIN, 25));
				timeDatePanel.add(lblTime, BorderLayout.CENTER);
			
				JPanel dateContainer = new JPanel();
				dateContainer.setOpaque(false);
				FlowLayout dateContainerFlowLayout = new FlowLayout();
				dateContainerFlowLayout.setVgap(0);
				dateContainerFlowLayout.setHgap(0);
				dateContainerFlowLayout.setAlignment(FlowLayout.RIGHT);
				OffsetLayout dateContainerOffsetLayout = new OffsetLayout(dateContainerFlowLayout);
				dateContainer.setLayout(dateContainerOffsetLayout);
				timeDatePanel.add(dateContainer, BorderLayout.SOUTH);
				
					JLabel lblDate = new JLabel("LABEL FOR DATE");
					lblDate.setFont(new Font("Cascadia Mono", Font.PLAIN, 14));
					dateContainer.add(lblDate);
				
//		SystemInfoPanel sysInfoPanel = new SystemInfoPanel();
//		sysInfoPanel.setOpaque(false);
//		appPanel.add(sysInfoPanel);
		
		JPanel backgroundContainer = new JPanel();
		backgroundContainer.setOpaque(false);
		HorizontalFilledLayout backgroundLayout = new HorizontalFilledLayout(VerticalAlign.ALIGN_BOTTOM, HorizontalAlign.ALIGN_RIGHT);
		backgroundContainer.setLayout(backgroundLayout);
		appPanel.add(backgroundContainer);
		
			ImageView backgroundImage;
			try {
				backgroundImage = new ImageView(ImageIO.read(RESOURCE_PROVIDER.getResource(new ResourceIdentifier("clock", "images/background.png")).openInputStream()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			backgroundImage.setPreferredSize(new Dimension(0, 0));
			backgroundContainer.add(backgroundImage);
					
		this.bean = new ClockBean();
		this.bean.addPropertyChangeListener(ClockBean.PROPERTY_TIME, e -> {
			lblTime.setText((String) e.getNewValue());
		});
		this.bean.addPropertyChangeListener(ClockBean.PROPERTY_DATE, e -> {
			lblDate.setText((String) e.getNewValue());
		});
		
		this.service = new ClockService(bean);
		
		// TODO: JLabel Animation running lag
		var backgroundImageTransitionCaller = M_TRANSITION_MANAGER.registerNewTransition(
			TransitionBindings.newBindingDimension(backgroundImage, ValueProvider.newProviderRatioVMin(i -> (int) (i * 0.5), 1.0)),
			Curves.easeOutExpo,
			1000
		);
		var timeFontSizeTransitionCaller = M_TRANSITION_MANAGER.registerNewTransition(
			TransitionBindings.newBindingNumber(lblTime, ValueProvider.vminForDouble(10.0, appPanel), PropertyAccessible.fontAccessible(lblTime))
		);
		var dateFontSizeTransitionCaller = M_TRANSITION_MANAGER.registerNewTransition(
			TransitionBindings.newBindingNumber(lblDate, ValueProvider.vminForDouble(3.0, appPanel), PropertyAccessible.fontAccessible(lblDate))
		);
		
		var timeDatePanelTransitionValueProvider = ValueProvider.vminForDouble(100.0, null);
		var timeDatePanelSizeTransitionCaller = M_TRANSITION_MANAGER.registerNewTransition(
			new TransitionBinding() {
				private double startValue;
				
				public void animStart() {
					double oldValue = timeDatePanelTransitionValueProvider.get(frameLastDimension);
					double newValue = timeDatePanelTransitionValueProvider.get(frmMain.getSize());
					
					startValue = oldValue / newValue;
//					System.out.println(oldValue + ", " + newValue + ", " + startValue);
					
				};
				
				@Override
				public void animProgress(double timeProgress) {
					timeDateLayerUI.setScale((float) (startValue + (1.0 - startValue) * timeProgress));
					timeDateLayer.repaint();
				}
			},
			Curves.easeOutExpo,
			1000
		);
		
//		var realPanelTransitionCaller = M_TRANSITION_MANAGER.registerNewStateChangingTransition(
//			new TransitionBinding() {
//				@Override
//				public void animProgress(double timeProgress) {
//					
//				}
//			},
//			Curves.easeOutQuint,
//			500
//		);
		
		AnimationBinding startAnimBinding = AnimationBindingBuilder.newBuilder().add(new IntValueAnimationBinding(() -> -100, () -> 0, true) {
			@Override
			protected void progress(int val) {
				dateContainerOffsetLayout.setOffset(lblDate, val, 0);
				userInfoOffsetLayout.setOffset(userInfoPanel, 0, val);
			}
		}, 1.0).build();
		
		frmMain.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				System.out.println("started");
				service.start();
				M_ANIMATION_MANAGER.start(startAnimBinding, Curves.easeOutQuint, 2500);
				
			}
			
			public void windowClosing(WindowEvent e) {
				System.out.println("hidden");
				service.stop();
				
			}
		});
		
		frmMain.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				backgroundImageTransitionCaller.announceTransition();
				timeFontSizeTransitionCaller.announceTransition();
				dateFontSizeTransitionCaller.announceTransition();
				timeDatePanelSizeTransitionCaller.announceTransition();
				
				frameLastDimension.setSize(frmMain.getSize());
				
			}
			
		});
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatDarkLaf());
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				
				try {
					ClockFrame window = new ClockFrame();
					window.frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
