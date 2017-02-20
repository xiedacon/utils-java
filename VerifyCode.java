package cn.xiedacon.bookstore.utils.commons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class VerifyCode {

	private String verifyCode = "";
	private String[] fonts = {"黑体","华文宋体","宋体","隶书","楷体"};
	private String codes = "23456789abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
	private Random r = new Random();
	private int width = 70;
	private int height = 35;
	
	public VerifyCode draw(OutputStream out) throws IOException {
		BufferedImage image = createImage();
		Graphics2D g = (Graphics2D)image.getGraphics();
		for(int i=0;i<4;i++){
			Font font = randomFont();
			Color color = randomColor();
			String code = randomCode();
			float x = i*width/4.0F;
			float y = height - 5;
			g.setColor(color);
			g.setFont(font);
			g.drawString(code, x, y);
			verifyCode += code;
		}
		addLines(image);
		ImageIO.write(image, "JPEG", out);
		return this;
	}

	private String randomCode() {
		return codes.charAt(r.nextInt(codes.length())) + "";
	}
	
	private void addLines(BufferedImage image) {
		Graphics2D g = (Graphics2D)image.getGraphics();
		for(int i=0;i<10;i++){
			int x1 = r.nextInt(width);
			int y1 = r.nextInt(height);
			int x2 = r.nextInt(width);
			int y2 = r.nextInt(height);
			Color color = randomColor();
			g.setColor(color);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	private Color randomColor() {
		int red = r.nextInt(150);
		int green = r.nextInt(150);
		int blue = r.nextInt(150);
		return new Color(red, green, blue);
	}

	private Font randomFont() {
		String name = fonts[r.nextInt(fonts.length)];
		int style = r.nextInt(4);
		int size = r.nextInt(5) + 24;
		return new Font(name, style, size);
	}

	private BufferedImage createImage() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		return image;
	}

	public String getVerifyCode() {
		return verifyCode;
	}
}
