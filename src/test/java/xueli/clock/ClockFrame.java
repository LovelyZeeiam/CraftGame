package xueli.clock;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;
import xueli.animation.AnimationBinding;
import xueli.animation.AnimationBindingBuilder;
import xueli.animation.AnimationManager;
import xueli.animation.Curves;
import xueli.animation.IntValueAnimationBinding;
import xueli.animation.TransitionCaller;
import xueli.animation.TransitionManager;
import xueli.clock.bean.ClockBean;
import xueli.clock.component.UserInfoPanel;
import xueli.clock.service.ClockService;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.provider.ClassLoaderResourceProvider;
import xueli.swingx.component.ImageView;
import xueli.swingx.layout.CoverAllLayout;
import xueli.swingx.layout.HorizontalFilledLayout;
import xueli.swingx.layout.OffsetLayout;
import xueli.swingx.responsive.PropertyAccessible;
import xueli.swingx.responsive.TransitionBindings;
import xueli.swingx.responsive.ValueProvider;
import xueli.swingx.layout.HorizontalFilledLayout.HorizentalAlign;
import xueli.swingx.layout.HorizontalFilledLayout.VerticalAlign;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.FlowLayout;

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
		
		JPanel appPanel = new JPanel();
		appPanel.setDoubleBuffered(true);
		appPanel.setLayout(new CoverAllLayout());
		frmMain.getContentPane().add(appPanel, BorderLayout.CENTER);
		
		JPanel backgroundContainer = new JPanel();
		backgroundContainer.setOpaque(false);
		HorizontalFilledLayout backgroundLayout = new HorizontalFilledLayout(VerticalAlign.ALIGN_BOTTOM, HorizentalAlign.ALIGN_RIGHT);
		backgroundContainer.setLayout(backgroundLayout);
		appPanel.add(backgroundContainer);
		
			ImageView backgroundImage;
			try {
				backgroundImage = new ImageView(ImageIO.read(RESOURCE_PROVIDER.getResource(new ResourceLocation("clock", "images/background.png")).openInputStream()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			backgroundImage.setPreferredSize(new Dimension(0, 0));
			backgroundContainer.add(backgroundImage);
			
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
		OffsetLayout timeDateOffsetLayout = new OffsetLayout(new HorizontalFilledLayout(VerticalAlign.ALIGN_CENTER, HorizentalAlign.CENTER));
		timeDateContainer.setLayout(timeDateOffsetLayout);
		appPanel.add(timeDateContainer);
		
			JPanel timeDatePanel = new JPanel();
			timeDatePanel.setOpaque(false);
			timeDatePanel.setLayout(new BorderLayout());
			timeDateContainer.add(timeDatePanel);
			
				JLabel lblTime = new JLabel("LABEL FOR TIME");
//				lblTime.setDoubleBuffered(true);
				
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
					lblDate.setDoubleBuffered(true);
					lblDate.setFont(new Font("Cascadia Mono", Font.PLAIN, 14));
					dateContainer.add(lblDate);
					
		this.bean = new ClockBean();
		this.bean.addPropertyChangeListener(ClockBean.PROPERTY_TIME, e -> {
			lblTime.setText((String) e.getNewValue());
		});
		this.bean.addPropertyChangeListener(ClockBean.PROPERTY_DATE, e -> {
			lblDate.setText((String) e.getNewValue());
		});
		this.service = new ClockService(bean);
		
		frmMain.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				service.close();
			}
		});
		
		TransitionCaller backgroundImageTransitionCaller = M_TRANSITION_MANAGER.registerNewTransition(
			TransitionBindings.newBindingDimension(backgroundImage, ValueProvider.newProviderRatioVMin(i -> (int) (i * 0.5), 1.0), Curves.easeOutExpo),
			500
		);
		TransitionCaller timeFontSizeTransitionCaller = M_TRANSITION_MANAGER.registerNewTransition(
			TransitionBindings.newBindingNumber(lblTime, ValueProvider.vminForDouble(10.0, appPanel), PropertyAccessible.fontAccessible(lblTime), Curves.easeOutExpo),
			500
		);
		TransitionCaller dateFontSizeTransitionCaller = M_TRANSITION_MANAGER.registerNewTransition(
				TransitionBindings.newBindingNumber(lblDate, ValueProvider.vminForDouble(3.0, appPanel), PropertyAccessible.fontAccessible(lblDate), Curves.easeOutExpo),
				500
			);
		appPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				backgroundImageTransitionCaller.announceTransition();
				timeFontSizeTransitionCaller.announceTransition();
				dateFontSizeTransitionCaller.announceTransition();
			}
		});
		
		AnimationBinding startAnimBinding = AnimationBindingBuilder.newBuilder().add(new IntValueAnimationBinding(Curves.easeInOutCubic, () -> -100, () -> 0, true) {
			@Override
			protected void progress(int val) {
				dateContainerOffsetLayout.setOffset(lblDate, val, 0);
				userInfoOffsetLayout.setOffset(userInfoPanel, 0, val);
			}
		}, 1.0).build();
		M_ANIMATION_MANAGER.start(startAnimBinding, 2500);
		
		
		
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
