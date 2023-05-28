package xueli.mcremake;

import java.awt.Color;

import xueli.game2.display.GameDisplay;
import xueli.game2.math.TriFuncMap;
import xueli.game2.renderer.ui.Gui;
import xueli.game2.renderer.ui.Overlay;
import xueli.mcremake.registry.MojanglesFont;

public class FontTest extends GameDisplay {
	
	private MojanglesFont font;
	
	public FontTest() {
		super(800, 600, "Font Test");
		
	}

	@Override
	protected void renderInit() {
		this.font = new MojanglesFont(this);
		this.font.reload();
//		this.resourceManager.addResourceHolder(this.font);
		
		this.overlayManager.setOverlay(new Overlay() {
			
//			private Gui gui;
			
			@Override
			public void init(Gui gui) {
//				this.gui = gui;
			}
			
			@Override
			public void render() {
				long time = System.currentTimeMillis();
 				font.drawFont(100, 80, (float)(40 + 5 * TriFuncMap.sin((time % 1500) * 360.0 / 1500.0)), 0.2f, "FONT TEST",
 						new Color(
 								(float) (0.7 + 0.3 * TriFuncMap.sin((time % 1600) * 360.0 / 1600.0)),
 								(float) (0.7 + 0.3 * TriFuncMap.sin((time % 900) * 360.0 / 900.0)),
 								(float) (0.7 + 0.3 * TriFuncMap.sin((time % 2500) * 360.0 / 2500.0))
 						)
					);
				
				font.drawFont(100, 132, 18.0f, 0.25f, "FPS: " + fps.getFps(), Color.LIGHT_GRAY);
				font.drawFont(100, 155, 18.0f, 0.125f, "Date!", Color.DARK_GRAY);
				
			}
			
			@Override
			public void reload() {
			}
			
			@Override
			public void release() {
			}
			
		});
		
	}

	@Override
	protected void render() {
		font.tick();
		
	}

	@Override
	protected void renderRelease() {
		this.font.release();
		
	}
	
	public static void main(String[] args) {
		new FontTest().run();
		
	}

}
