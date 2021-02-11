package xueli.gamengine.data;

/**
 * 如何判断一个组件是否可以撤退？ 这就是这个枚举类的用处 可以用在动画的停止(这个懒得改了 xD)，粒子的销毁，AI的停止运作等方面
 */
public enum WhenComponentComeToEnd {

	ITS_TIME_TO_DISPOSE, NOT_YET

}
