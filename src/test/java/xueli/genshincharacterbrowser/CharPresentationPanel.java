package xueli.genshincharacterbrowser;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

class CharPresentationPanel extends JComponent {

	private static final long serialVersionUID = 3697428386636910054L;
	
	BufferedImage nameImage, dialogImage, portraitImage;

	public CharPresentationPanel() {
		setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int canvasWidth = this.getWidth();
		int canvasHeight = this.getHeight();
		
		// Draw Portrait
		if(portraitImage != null) {
			double originWidth = portraitImage.getWidth();
            double originHeight = portraitImage.getHeight();
            double scaleRatio = canvasHeight / originHeight;
            
            double drawWidth = originWidth * scaleRatio;
            double drawHeight = originHeight * scaleRatio;
            double drawX = canvasWidth * 3 / 5 - drawWidth / 2;
            g.drawImage(portraitImage, (int) drawX, 0, (int) drawWidth, (int) drawHeight, null);
            
		}
		
		// Draw Name
		if(nameImage != null) {
			double originWidth = nameImage.getWidth();
            double originHeight = nameImage.getHeight();
            double ratio = originHeight / originWidth;
            
            double drawWidth = 0.8 * Math.min(canvasWidth, canvasHeight);
            double drawHeight = drawWidth * ratio;
            double drawX = canvasWidth * 1 / 5;
            double drawY = canvasHeight * 1 / 3 - drawHeight / 2;
            g.drawImage(nameImage, (int) drawX, (int) drawY, (int) drawWidth, (int) drawHeight, null);
            
		}
		
		// Draw Dialog
		if(dialogImage != null) {
			double originWidth = dialogImage.getWidth();
            double originHeight = dialogImage.getHeight();
            double ratio = originHeight / originWidth;

            double drawWidth = 0.8 * Math.min(canvasWidth, canvasHeight);
            double drawHeight = drawWidth * ratio;
            double drawX = canvasWidth / 5.0;
            double drawY = canvasHeight * 1 / 3 + drawHeight / 2 + 10;
            g.drawImage(dialogImage, (int) drawX, (int) drawY, (int) drawWidth, (int) drawHeight, null);
            
		}
		
	}
	
	

}
