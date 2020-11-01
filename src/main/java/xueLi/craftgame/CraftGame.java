package xueLi.craftgame;

import org.lwjgl.glfw.GLFW;
import xueLi.gamengine.IGame;
import xueLi.gamengine.utils.TaskManager;
import xueLi.gamengine.view.GUIFader.Faders;
import xueLi.gamengine.view.GUIImageView;
import xueLi.gamengine.view.GUIProgressBar;
import xueLi.gamengine.view.GUITextView;
import xueLi.gamengine.view.View;

public class CraftGame extends IGame {

    private static int width = 1200, height = 680;
    public boolean inWorld = false;
    private WorldLogic worldLogic;
    public CraftGame() {
        super("res/");

    }

    @Override
    protected void onCreate() {
        initAll(width, height);

        GameLoader gameLoader = new GameLoader(this);
        Thread gameLoaderThread = new Thread(gameLoader);

        showDisplay();

        TaskManager.startListener();

        gameLoaderThread.start();

    }

    @Override
    protected void onSized() {
        if (worldLogic != null)
            worldLogic.size();

    }

    @Override
    protected void onCursorPos(double dx, double dy) {
        if (worldLogic != null)
            worldLogic.mouseMove(dx, dy);

    }

    @Override
    protected void onMouseButton(int button) {

    }

    @Override
    protected void onDrawFrame() {
        if (inWorld) {
            worldLogic.draw();
        } else {
            viewManager.draw();
        }

        runQueueList();

    }

    @Override
    protected void onExit() {
        if (inWorld) {
            worldLogic.running = false;
        }

        TaskManager.stopListener();

        releaseAll();

    }

    public class GameLoader implements Runnable {

        // ◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤
        boolean waitingForLove = false;
        boolean sleeping = false;

        BlockResource resource;

        private CraftGame cg;

        // 这些东西在构造函数初始化 不然有可能会抛空指针
        /* 资源加载 */
        private View loading_gui;
        private GUIImageView loading_imageView;
        private GUIProgressBar loading_ProgressBar;
        private GUITextView loading_TextView;

        public GameLoader(CraftGame cg) {
            this.cg = cg;

            /* 资源加载 */
            loading_gui = guiResource.getGui("game_loading.json");
            loading_imageView = (GUIImageView) loading_gui.widgets.get("loading_splash");
            loading_ProgressBar = (GUIProgressBar) loading_gui.widgets.get("loading_progress_bar");
            loading_TextView = (GUITextView) loading_gui.widgets.get("loading_message");

            viewManager.setFadeinGui(loading_gui, Faders.LINEAR.fader);

        }

        @Override
        public void run() {
            /* 资源加载 */
            String loading_messageString = loading_TextView.getText();

            // 设置加载动画
            queueRunningInMainThread.add(() -> loading_gui.setAnimation("loading"));

            // 加载GUI
            guiResource.loadGui(langManager, loading_TextView, loading_ProgressBar, 0.00f, 0.25f);

            loading_TextView.setText(loading_messageString);

            // 设置监听
            View mainMenuGui = guiResource.getGui("main_menu.json");

            mainMenuGui.widgets.get("single_player_button").onClickListener = (button, action, offsetX, offsetY) -> {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT & action == GLFW.GLFW_RELEASE) {
                    waitingForLove = false;

                    viewManager.setGui("world_loading.json");

                    worldLogic = new WorldLogic(cg);
                    worldLogic.loadLevel();
                    worldLogic.running = true;
                    new Thread(worldLogic).start();

                }

            };

            mainMenuGui.widgets.get("multi_player_button").onClickListener = (button, action, offsetX, offsetY) -> {

            };
            mainMenuGui.widgets.get("setting_button").onClickListener = (button, action, offsetX, offsetY) -> {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT & action == GLFW.GLFW_RELEASE) {
                    viewManager.setGui("game_setting.json");

                }
            };

            View esc_menu = cg.getGuiResource().getGui("game_esc_menu.json");
            esc_menu.widgets.get("back_to_game_button").onClickListener = (button, action, offsetX, offsetY) -> {
                if (action == GLFW.GLFW_RELEASE) {
                    // 从esc界面回到游戏中
                    display.toggleMouseGrabbed();
                    viewManager.setGui((View) null);
                    worldLogic.gameGui = null;
                    worldLogic.state = State.INGAME;

                }

            };

            esc_menu.widgets.get("quit_button").onClickListener = (button, action, offsetX, offsetY) -> {
                if (action == GLFW.GLFW_RELEASE) {
                    inWorld = false;
                    worldLogic.delete();
                    worldLogic = null;
                    viewManager.setGui("main_menu.json");

                    guiResource.loadGui("world_loading.json", langManager, true);

                }

            };

            // 加载方块
            resource = new BlockResource("res/", langManager, textureManager);
            resource.load(loading_TextView, loading_ProgressBar, 0.25f, 1.00f);

            // 加载物品
            

            queueRunningInMainThread.add(() -> {
                loading_TextView.setText("Loading...");

            });

            waitingForLove = true;
            sleeping = true;
            
            // 等待直到进度条到底
            loading_ProgressBar.waitUtilProgressFull();
            
            sleeping = false;
            waitingForLove = false;

            queueRunningInMainThread.add(() -> {
                loading_TextView.setText("Loaded successfully~");

                // 换界面!
                viewManager.setFadeinGui("main_menu.json", Faders.LINEAR.fader);
            });

        }

    }

}
