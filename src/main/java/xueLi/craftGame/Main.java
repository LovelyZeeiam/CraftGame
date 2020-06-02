package xueLi.craftGame;

import java.io.IOException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import xueLi.craftGame.world.WorldRenderer;
import xueLi.craftGame.gui.GUIRenderer;
import xueLi.craftGame.gui.guis.GuiPauseMenu;
import xueLi.craftGame.initer.*;
import xueLi.craftGame.inputListener.EventManager;
import xueLi.craftGame.utils.Display;
import xueLi.craftGame.utils.TaskManager;

public class Main {

	private static int width = 1200, height = 680;

	// 这个是主方法
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Class.forName("org.lwjgl.system.Library");
		Class.forName("org.lwjgl.nanovg.LibNanoVG");

		// 创建一个显示区域
		if (!Display.create(width, height))
			return;

		// 这个函数里面实现对方块的注册 一定要放在存档生成之前
		Blocks.init();

		// 这个函数初始化世界渲染器
		// 材质放在"res/textures.png"里面,好几个材质组合存储
		// 关于新增方块的教程 在Block的init方法里面
		WorldRenderer.init();
		if (!GUIRenderer.init())
			return;

		Audios.init();

		// 启动事件监听
		EventManager.startListener();
		// 启动任务监听
		TaskManager.startListener();

		Display.grabMouse();

		while (Display.isRunning()) {
			if (Display.isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE)) {
				if (Display.grabMouse())
					GUIRenderer.setGUI(null);
				else
					GUIRenderer.setGUI(new GuiPauseMenu());
			}

			TaskManager.processQueueOfMainThread(1);

			// 返回FPS的值 如果处在debug模式则会在控制台里面输出
			// FPSTimer.getFPS();

			// 清屏搞事情
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

			// 与世界渲染有关
			WorldRenderer.render();
			// 更新
			WorldRenderer.update();

			// 渲染GUI
			GUIRenderer.render();

			// TODO:这里可以写游戏tick更新之类的 反正就是游戏循环

			// 这是显示窗口的刷新
			Display.update();
		}

		// 资源释放
		Audios.close();
		WorldRenderer.release();
		TaskManager.stopListener();
		EventManager.stopListener();
		Display.destroy();

		System.exit(0);

	}
}
