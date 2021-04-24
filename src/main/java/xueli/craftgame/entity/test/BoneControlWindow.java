package xueli.craftgame.entity.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import xueli.craftgame.entity.EntityPlayer;

public class BoneControlWindow extends JFrame implements ChangeListener {

	private static final long serialVersionUID = 6387000826965498395L;

	private final EntityPlayer controlled;

	private JPanel contentPane;

	private JSlider sb_head_rot_x = new JSlider();
	private JSlider sb_head_rot_y = new JSlider();
	private JSlider sb_head_rot_z = new JSlider();
	private JSlider sb_body_rot_x = new JSlider();
	private JSlider sb_body_rot_y = new JSlider();
	private JSlider sb_body_rot_z = new JSlider();
	private JSlider sb_left_arm_rot_x = new JSlider();
	private JSlider sb_left_arm_rot_y = new JSlider();
	private JSlider sb_left_arm_rot_z = new JSlider();
	private JSlider sb_right_arm_rot_x = new JSlider();
	private JSlider sb_right_arm_rot_y = new JSlider();
	private JSlider sb_right_arm_rot_z = new JSlider();
	private JSlider sb_left_leg_rot_x = new JSlider();
	private JSlider sb_left_leg_rot_y = new JSlider();
	private JSlider sb_left_leg_rot_z = new JSlider();
	private JSlider sb_right_leg_rot_x = new JSlider();
	private JSlider sb_right_leg_rot_y = new JSlider();
	private JSlider sb_right_leg_rot_z = new JSlider();

	public BoneControlWindow(EntityPlayer player) {
		this.controlled = player;

		setTitle("BoneController");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 436, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_button = new JPanel();
		contentPane.add(panel_button, BorderLayout.SOUTH);
		panel_button.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton = new JButton("Reset");
		panel_button.add(btnNewButton, BorderLayout.EAST);

		JPanel panel_control = new JPanel();
		contentPane.add(panel_control, BorderLayout.CENTER);
		panel_control.setLayout(new BoxLayout(panel_control, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		panel_control.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		{
			JPanel panel_1 = new JPanel();
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));

			JLabel lblNewLabel = new JLabel("head_rot_x");
			panel_1.add(lblNewLabel, BorderLayout.WEST);
			sb_head_rot_x.setValue(0);

			sb_head_rot_x.setOrientation(JSlider.HORIZONTAL);
			panel_1.add(sb_head_rot_x, BorderLayout.CENTER);

			JPanel panel_2 = new JPanel();
			panel.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));

			JLabel lblHeadroty = new JLabel("head_rot_y");
			panel_2.add(lblHeadroty, BorderLayout.WEST);
			sb_head_rot_y.setValue(0);

			sb_head_rot_y.setOrientation(JSlider.HORIZONTAL);
			panel_2.add(sb_head_rot_y, BorderLayout.CENTER);

			JPanel panel_3 = new JPanel();
			panel.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));

			JLabel lblHeadrotz = new JLabel("head_rot_z");
			panel_3.add(lblHeadrotz, BorderLayout.WEST);
			sb_head_rot_z.setValue(0);

			sb_head_rot_z.setOrientation(JSlider.HORIZONTAL);
			panel_3.add(sb_head_rot_z, BorderLayout.CENTER);

		}

		{

			JPanel panel_4 = new JPanel();
			panel.add(panel_4);
			panel_4.setLayout(new BorderLayout(0, 0));

			JLabel lblBodyrotx = new JLabel("body_rot_x");
			panel_4.add(lblBodyrotx, BorderLayout.WEST);
			sb_body_rot_x.setValue(0);

			sb_body_rot_x.setOrientation(JSlider.HORIZONTAL);
			panel_4.add(sb_body_rot_x);

			JPanel panel_5 = new JPanel();
			panel.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));

			JLabel lblBodyroty = new JLabel("body_rot_y");
			panel_5.add(lblBodyroty, BorderLayout.WEST);
			sb_body_rot_y.setValue(0);

			sb_body_rot_y.setOrientation(JSlider.HORIZONTAL);
			panel_5.add(sb_body_rot_y);

			JPanel panel_6 = new JPanel();
			panel.add(panel_6);
			panel_6.setLayout(new BorderLayout(0, 0));

			JLabel lblBodyrotz = new JLabel("body_rot_z");
			panel_6.add(lblBodyrotz, BorderLayout.WEST);
			sb_body_rot_z.setValue(0);

			sb_body_rot_z.setOrientation(JSlider.HORIZONTAL);
			panel_6.add(sb_body_rot_z, BorderLayout.CENTER);

		}

		{

			JPanel panel_7 = new JPanel();
			panel.add(panel_7);
			panel_7.setLayout(new BorderLayout(0, 0));

			JLabel lblLeftarmrotx = new JLabel("left_arm_rot_x");
			panel_7.add(lblLeftarmrotx, BorderLayout.WEST);
			sb_left_arm_rot_x.setValue(0);

			sb_left_arm_rot_x.setOrientation(JSlider.HORIZONTAL);
			panel_7.add(sb_left_arm_rot_x, BorderLayout.CENTER);

			JPanel panel_8 = new JPanel();
			panel.add(panel_8);
			panel_8.setLayout(new BorderLayout(0, 0));

			JLabel lblLeftarmroty = new JLabel("left_arm_rot_y");
			panel_8.add(lblLeftarmroty, BorderLayout.WEST);
			sb_left_arm_rot_y.setValue(0);
			sb_left_arm_rot_y.setToolTipText("");

			sb_left_arm_rot_y.setOrientation(JSlider.HORIZONTAL);
			panel_8.add(sb_left_arm_rot_y, BorderLayout.CENTER);

			JPanel panel_9 = new JPanel();
			panel.add(panel_9);
			panel_9.setLayout(new BorderLayout(0, 0));

			JLabel lblLeftarmrotz = new JLabel("left_arm_rot_z");
			panel_9.add(lblLeftarmrotz, BorderLayout.WEST);
			sb_left_arm_rot_z.setValue(0);

			sb_left_arm_rot_z.setOrientation(JSlider.HORIZONTAL);
			panel_9.add(sb_left_arm_rot_z);

		}

		{

			JPanel panel_10 = new JPanel();
			panel.add(panel_10);
			panel_10.setLayout(new BorderLayout(0, 0));

			JLabel lblRightarmrotx = new JLabel("right_arm_rot_x");
			panel_10.add(lblRightarmrotx, BorderLayout.WEST);
			sb_right_arm_rot_x.setValue(0);

			sb_right_arm_rot_x.setOrientation(JSlider.HORIZONTAL);
			panel_10.add(sb_right_arm_rot_x);

			JPanel panel_11 = new JPanel();
			panel.add(panel_11);
			panel_11.setLayout(new BorderLayout(0, 0));

			JLabel lblRightarmroty = new JLabel("right_arm_rot_y");
			panel_11.add(lblRightarmroty, BorderLayout.WEST);
			sb_right_arm_rot_y.setValue(0);

			sb_right_arm_rot_y.setOrientation(JSlider.HORIZONTAL);
			panel_11.add(sb_right_arm_rot_y);

			JPanel panel_12 = new JPanel();
			panel.add(panel_12);
			panel_12.setLayout(new BorderLayout(0, 0));

			JLabel lblRightarmrotz = new JLabel("right_arm_rot_z");
			panel_12.add(lblRightarmrotz, BorderLayout.WEST);
			sb_right_arm_rot_z.setValue(0);

			sb_right_arm_rot_z.setOrientation(JSlider.HORIZONTAL);
			panel_12.add(sb_right_arm_rot_z);

		}

		{

			JPanel panel_7 = new JPanel();
			panel.add(panel_7);
			panel_7.setLayout(new BorderLayout(0, 0));

			JLabel lblLeftlegrotx = new JLabel("left_leg_rot_x");
			panel_7.add(lblLeftlegrotx, BorderLayout.WEST);
			sb_left_leg_rot_x.setValue(0);

			sb_left_leg_rot_x.setOrientation(JSlider.HORIZONTAL);
			panel_7.add(sb_left_leg_rot_x, BorderLayout.CENTER);

			JPanel panel_8 = new JPanel();
			panel.add(panel_8);
			panel_8.setLayout(new BorderLayout(0, 0));

			JLabel lblLeftlegroty = new JLabel("left_leg_rot_y");
			panel_8.add(lblLeftlegroty, BorderLayout.WEST);
			sb_left_leg_rot_y.setValue(0);

			sb_left_leg_rot_y.setOrientation(JSlider.HORIZONTAL);
			panel_8.add(sb_left_leg_rot_y, BorderLayout.CENTER);

			JPanel panel_9 = new JPanel();
			panel.add(panel_9);
			panel_9.setLayout(new BorderLayout(0, 0));

			JLabel lblLeftlegrotz = new JLabel("left_leg_rot_z");
			panel_9.add(lblLeftlegrotz, BorderLayout.WEST);
			sb_left_leg_rot_z.setValue(0);

			sb_left_leg_rot_z.setOrientation(JSlider.HORIZONTAL);
			panel_9.add(sb_left_leg_rot_z);

		}

		{

			JPanel panel_10 = new JPanel();
			panel.add(panel_10);
			panel_10.setLayout(new BorderLayout(0, 0));

			JLabel lblRightlegrotx = new JLabel("right_leg_rot_x");
			panel_10.add(lblRightlegrotx, BorderLayout.WEST);
			sb_right_leg_rot_x.setValue(0);

			sb_right_leg_rot_x.setOrientation(JSlider.HORIZONTAL);
			panel_10.add(sb_right_leg_rot_x);

			JPanel panel_11 = new JPanel();
			panel.add(panel_11);
			panel_11.setLayout(new BorderLayout(0, 0));

			JLabel lblRightlegroty = new JLabel("right_leg_rot_y");
			panel_11.add(lblRightlegroty, BorderLayout.WEST);
			sb_right_leg_rot_y.setValue(0);

			sb_right_leg_rot_y.setOrientation(JSlider.HORIZONTAL);
			panel_11.add(sb_right_leg_rot_y);

			JPanel panel_12 = new JPanel();
			panel.add(panel_12);
			panel_12.setLayout(new BorderLayout(0, 0));

			JLabel lblRightlegrotz = new JLabel("right_leg_rot_z");
			panel_12.add(lblRightlegrotz, BorderLayout.WEST);
			sb_right_leg_rot_z.setValue(0);

			sb_right_leg_rot_z.setOrientation(JSlider.HORIZONTAL);
			panel_12.add(sb_right_leg_rot_z);

		}
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetData();
				confirmData();
			}
		});

		sb_head_rot_x.addChangeListener(this);
		sb_head_rot_y.addChangeListener(this);
		sb_head_rot_z.addChangeListener(this);
		sb_body_rot_x.addChangeListener(this);
		sb_body_rot_y.addChangeListener(this);
		sb_body_rot_z.addChangeListener(this);
		sb_left_arm_rot_x.addChangeListener(this);
		sb_left_arm_rot_y.addChangeListener(this);
		sb_left_arm_rot_z.addChangeListener(this);
		sb_right_arm_rot_x.addChangeListener(this);
		sb_right_arm_rot_y.addChangeListener(this);
		sb_right_arm_rot_z.addChangeListener(this);
		sb_left_leg_rot_x.addChangeListener(this);
		sb_left_leg_rot_y.addChangeListener(this);
		sb_left_leg_rot_z.addChangeListener(this);
		sb_right_leg_rot_x.addChangeListener(this);
		sb_right_leg_rot_y.addChangeListener(this);
		sb_right_leg_rot_z.addChangeListener(this);

	}

	private void resetData() {
		sb_head_rot_x.setValue(0);
		sb_head_rot_y.setValue(0);
		sb_head_rot_z.setValue(0);
		sb_body_rot_x.setValue(0);
		sb_body_rot_y.setValue(0);
		sb_body_rot_z.setValue(0);
		sb_left_arm_rot_x.setValue(0);
		sb_left_arm_rot_y.setValue(0);
		sb_left_arm_rot_z.setValue(0);
		sb_right_arm_rot_x.setValue(0);
		sb_right_arm_rot_y.setValue(0);
		sb_right_arm_rot_z.setValue(0);
		sb_left_leg_rot_x.setValue(0);
		sb_left_leg_rot_y.setValue(0);
		sb_left_leg_rot_z.setValue(0);
		sb_right_leg_rot_x.setValue(0);
		sb_right_leg_rot_y.setValue(0);
		sb_right_leg_rot_z.setValue(0);

	}

	private void confirmData() {
		controlled.setBoneParameters("head_rot_x", sb_head_rot_x.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("head_rot_y", sb_head_rot_y.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("head_rot_z", sb_head_rot_z.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("body_rot_x", sb_body_rot_x.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("body_rot_y", sb_body_rot_y.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("body_rot_z", sb_body_rot_z.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("left_arm_rot_x", sb_left_arm_rot_x.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("left_arm_rot_y", sb_left_arm_rot_y.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("left_arm_rot_z", sb_left_arm_rot_z.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("right_arm_rot_x", sb_right_arm_rot_x.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("right_arm_rot_y", sb_right_arm_rot_y.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("right_arm_rot_z", sb_right_arm_rot_z.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("left_leg_rot_x", sb_left_leg_rot_x.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("left_leg_rot_y", sb_left_leg_rot_y.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("left_leg_rot_z", sb_left_leg_rot_z.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("right_leg_rot_x", sb_right_leg_rot_x.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("right_leg_rot_y", sb_right_leg_rot_y.getValue() * 360.0f / 100.0f);
		controlled.setBoneParameters("right_leg_rot_z", sb_right_leg_rot_z.getValue() * 360.0f / 100.0f);

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		confirmData();

	}

}
