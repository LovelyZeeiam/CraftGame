package xueLi.craftgame;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import xueLi.craftgame.entity.Player;
import xueLi.craftgame.world.World;
import xueLi.craftgame.world.WorldGLData;
import xueLi.gamengine.resource.TextureAtlas;
import xueLi.gamengine.utils.GLHelper;
import xueLi.gamengine.utils.Logger;
import xueLi.gamengine.utils.MatrixHelper;
import xueLi.gamengine.utils.Shader;
import xueLi.gamengine.utils.callbacks.KeyCallback;
import xueLi.gamengine.view.GUIProgressBar;
import xueLi.gamengine.view.View;

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

        /*
         * 注册vao, vbo
         */
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

        world = new World(this);
        player = new Player(8, 8, 8, 0, 135, 0);

    }

    public void closeLevel() {
        world = null;
        player = null;

    }

    public void mouseMove(double dx, double dy) {
        if (cg.getDisplay().mouseGrabbed) {
            player.pos.rotX -= dy * player.sensivity;
            player.pos.rotY += dx * player.sensivity;
        }
    }

    @Override
    public void run() {
        GUIProgressBar world_loading_progressBar = (GUIProgressBar) (cg.getGuiResource()
                .getGui("world_loading.json").widgets.get("loading_bar"));

        world.preDraw(blockTextureAtlas, player, 10);

        world_loading_progressBar.setProgress(1.0f);

        cg.queueRunningInMainThread.add(this::size);

        world.updateVertexBuffer(blockTextureAtlas, player, 10);

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

        player.tick(world);
        playerMatrix = MatrixHelper.player(player.pos);
        MatrixHelper.calculateFrustumPlane();
        player.pickTick(world);

        GL11.glClearColor(0.7f, 0.8f, 1.0f, 1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        GLHelper.checkGLError("World: Pre-render");

        mappedBuffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY, mappedBuffer);
        if (mappedBuffer == null) {
            Logger.error(new Throwable("Buffer map error!"));
        }
        mappedBuffer.clear();
        mappedBuffer.position(0);

        vertexCount = world.draw(blockTextureAtlas, player, mappedBuffer.asFloatBuffer(), 10);

        mappedBuffer.flip();
        GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);

        GLHelper.checkGLError("World: Map Buffer");

        blockTextureAtlas.bind();
        GLHelper.checkGLError("World: Bind Texture");

        blockRenderShader.use();

        blockRenderShader.setUniformMatrix(blockRenderShader.getUnifromLocation("viewMatrix"), playerMatrix);

        GLHelper.checkGLError("World: Bind Shader");

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

        GLHelper.checkGLError("World: Drawer");

        blockTextureAtlas.unbind();
        blockRenderShader.unbind();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

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
