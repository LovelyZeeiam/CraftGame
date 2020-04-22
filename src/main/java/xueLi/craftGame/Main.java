package xueLi.craftGame;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.world.WorldRenderer;
import xueLi.craftGame.gui.GUIRenderer;
import xueLi.craftGame.gui.screens.*;
import xueLi.craftGame.initer.*;

public class Main {

	private static int width = 1200, height = 680;

	//这个是主方法
	public static void main(String[] args) throws IOException {
		// 创建一个显示区域
		DisplayManager.create(width, height, null);
		
		// 这个函数里面实现对方块的注册 一定要放在存档生成之前
		Blocks.init();

		// 这个函数初始化世界渲染器
		// 材质放在"res/textures.png"里面,好几个材质组合存储
		// 关于新增方块的教程 在Block的init方法里面
		WorldRenderer.init();

		Mouse.setGrabbed(true);
		
		while (DisplayManager.isRunning()) {
			DisplayManager.keyTest();
			if (DisplayManager.isKeyDownOnce(Keyboard.KEY_ESCAPE)) {
				if (GUIRenderer.mouseGrubbed) {
					GUIRenderer.setGUI(new GUIPauseMenu());
				} else {
					GUIRenderer.setGUI(null);
				}
			}

			// 返回FPS的值 如果处在debug模式则会在控制台里面输出
			//FPSTimer.getFPS();
			
			//清屏搞事情
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			// 与世界渲染有关
			WorldRenderer.render();
			
			// 渲染GUI
			GUIRenderer.draw();

			// TODO:这里可以写游戏tick更新之类的 反正就是游戏循环

			// 这是显示窗口的刷新
			DisplayManager.update();
		}

		// 资源释放
		WorldRenderer.release();
		DisplayManager.destroy();

		System.exit(0);

	}
}
