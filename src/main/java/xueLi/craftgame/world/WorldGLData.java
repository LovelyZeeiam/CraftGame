package xueLi.craftgame.world;

/**
 * <p>
 * 写介个的初衷是方便后期改发送给OpenGL的格式时不用到处乱找
 * </p>
 * <p>
 * 预期: 顶点 (3个int) | 面id (1个unsigned byte) | 材质在atlas中的位置 (2个unsigned short) | 颜色
 * (3个unsigned byte)
 * </p>
 * <p>
 * TODO: wanna use Unsigned integer in location 1 - 3 but it doesn't work 4 me
 * 嘤嘤嘤
 * </p>
 * <h2>上面的方式好像实际在我电脑上面发现很卡</h2>
 * <p>
 * 还是按传统的方式吧
 */
public @interface WorldGLData {
}
