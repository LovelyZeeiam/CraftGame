package xueli.craftgame;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import xueli.craftgame.entity.Player;
import xueli.craftgame.world.World;
import xueli.craftgame.world.WorldGLData;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.MatrixHelper;
import xueli.gamengine.utils.Shader;
import xueli.gamengine.utils.callbacks.KeyCallback;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.View;

import java.nio.ByteBuffer;

public class WorldLogic implements Runnable {

    private final CraftGame cg;
    private final int vao, vbo;
    public boolean running = false;
    public View gameGui;
    public State state;
    private ByteBuffer mappedBuffer;
    private int vertexCount = 0;
    private World world;
    private Player player;
    private TextureAtlas blockTextureAtlas;
    private Shader blockRenderShader;
    /**
     * frustum culling 依照从前到后的顺序排序几何体 顶点处理器基于32位浮点值工作 Fragment Shader使用16位浮点值工作
     */

    private Matrix4f playerMatrix = new Matrix4f();

    @WorldGLData
    public WorldLogic(CraftGame cg) {
        this.cg = cg;

        // 注册vao, vbo
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 671088640, GL15.GL_DYNAMIC_DRAW);

        // UV
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * 4, 0);
        GL20.glEnableVertexAttribArray(1);
        // 颜色
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * 4, 2 * 4);
        GL20.glEnableVertexAttribArray(2);
        // 坐标
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * 4, 5 * 4);
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

    }

    public void loadLevel() {
        this.blockTextureAtlas = (TextureAtlas) cg.getTextureManager().getTexture("blocks");
        this.blockRenderShader = cg.getShaderResource().get("world");

        // 创建新的世界
        world = new World(this);
        // 安置新的玩家
        player = new Player(8, 8, 8, 0, 135, 0, world);

    }

    public void closeLevel() {
        // 非常草率的关闭世界
        world = null;
        player = null;

    }

    /**
     * 处理鼠标的移动
     *
     * @param dx 鼠标移动x坐标的位移
     * @param dy 鼠标移动y标的位移
     */
    public void mouseMove(double dx, double dy) {
        if (cg.getDisplay().mouseGrabbed) {
            player.pos.rotX -= dy * player.sensivity;
            player.pos.rotY += dx * player.sensivity;
        }
    }

    /**
     * 这个是世界加载的时候会进行的工作
     */
    @Override
    public void run() {
        GUIProgressBar world_loading_progressBar = (GUIProgressBar) (cg.getGuiResource()
                .getGui("world_loading.json").widgets.get("loading_bar"));

        // 提前先生成好世界的顶点数据 防止第一次绘制的时候 CPU姬突然接收到那么多数据 由于无法按时完成生成任务而无奈的让显卡姬等一等
        // 似乎在Windows系统上面没什么用 ubuntu还好
        world.updateVertexBuffer(blockTextureAtlas, player, 10);

        world_loading_progressBar.setProgress(1.0f);

        cg.queueRunningInMainThread.add(this::size);

        world_loading_progressBar.waitUtilProgressFull();

        this.state = State.INGAME;

        cg.queueRunningInMainThread.add(() -> {
            cg.getViewManager().setGui((View) null);
            cg.getDisplay().toggleMouseGrabbed();

        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 转换画面到世界内部
        cg.inWorld = true;

    }

    public void size() {
        blockRenderShader.use();
        blockRenderShader.setUniformMatrix(blockRenderShader.getUnifromLocation("projMatrix"), MatrixHelper
                .perspecive(cg.getDisplay().getWidth(), cg.getDisplay().getHeight(), 90.0f, 0.01f, 114514.0f));
        blockRenderShader.unbind();

        if (gameGui != null)
            gameGui.size();

    }

    public void draw() {
        // 检查这个按键是否被按下 使用keysOnce是因为只有在游戏在循环过程中第一次发现这个按键被按下时才会让对应的按键的布尔值变为true
        // Nobody wanna see their mouse guichu~
        if (KeyCallback.keysOnce[GLFW.GLFW_KEY_ESCAPE]) {
            if (this.state == State.INGAME) {
                cg.getDisplay().toggleMouseGrabbed();
                gameGui = cg.getGuiResource().getGui("game_esc_menu.json");
                cg.getViewManager().setGui(gameGui);
                this.state = State.ESC_MENU;

            } else if (this.state == State.ESC_MENU) {
                // 从esc界面回到游戏中
                cg.getDisplay().toggleMouseGrabbed();
                cg.getViewManager().setGui((View) null);
                gameGui = null;
                this.state = State.INGAME;

            }

        }

        // 由于这是单纯的本地客户端 所以会有本地的玩家的按键处理
        // 当做成可以多人游戏的版本的时候 这里就似乎得改一改了
        player.tick();
        // 生成玩家视角的视角矩阵
        playerMatrix = MatrixHelper.player(player.pos);
        MatrixHelper.calculateFrustumPlane();

        // 玩家指针指向方块的更新
        player.pickTick();

        // 清空颜色
        GL11.glClearColor(0.7f, 0.8f, 1.0f, 1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        GLHelper.checkGLError("World: Pre-render");

        // 将内存地址映射 效率提高了
        mappedBuffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY, mappedBuffer);
        if (mappedBuffer == null) {
            Logger.error(new Throwable("Buffer map error!"));
        }
        mappedBuffer.clear();
        mappedBuffer.position(0);

        // 得到世界的渲染顶点信息
        vertexCount = world.draw(blockTextureAtlas, player, mappedBuffer.asFloatBuffer(), 10);

        // 将这一块内存变成读的状态 方便OpenGL娘的处理
        mappedBuffer.flip();

        // 解除内存地址的映射 OpenGL娘就不会担心在处理时内存数据突然改变而引起的束手无策
        GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);

        GLHelper.checkGLError("World: Map Buffer");

        // 将方块材质加入到OpenGL娘的首个材质槽里面
        blockTextureAtlas.bind();
        GLHelper.checkGLError("World: Bind Texture");

        // 将着色器放到OpenGL娘的着色器槽里面
        blockRenderShader.use();

        // 设置着色器娘的视角姬参数
        blockRenderShader.setUniformMatrix(blockRenderShader.getUnifromLocation("viewMatrix"), playerMatrix);

        GLHelper.checkGLError("World: Bind Shader");

        // 让OpenGL娘去绘制叭~
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

        GLHelper.checkGLError("World: Drawer");

        // 接触绑定 (束缚 真)
        blockTextureAtlas.unbind();
        blockRenderShader.unbind();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        // 去除背面渲染
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        if (gameGui != null) {
            cg.getViewManager().draw();
        }

        GLHelper.checkGLError("World: After-render");

    }

    public void delete() {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vbo);

        closeLevel();

    }

}
