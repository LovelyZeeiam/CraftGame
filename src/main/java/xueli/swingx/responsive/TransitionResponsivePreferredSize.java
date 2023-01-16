package xueli.swingx.responsive;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;

import xueli.animation.AnimationInstance;
import xueli.animation.AnimationManager;
import xueli.animation.Curve;
import xueli.animation.DoubleValueAnimationBinding;

public class TransitionResponsivePreferredSize {
	
	private static record AnimationData(ValueProvider<Dimension> valueProvider, long duration, Curve curve) {
	}
	
	private static final HashMap<ComponentAnimatorMap, TransitionResponsivePreferredSize> responsives = new HashMap<>();
	
	public static void addResponsive(Component child, ValueProvider<Dimension> provider, long transitionTime, Curve curve, AnimationManager animator) {
		Container father = child.getParent();
		if(father == null) {
			System.err.println("No Father? " + child.toString());
			return;
		}
		responsives.computeIfAbsent(new ComponentAnimatorMap(father, animator), m -> new TransitionResponsivePreferredSize(m.container(), m.animator()))
			.addResponsiveChild(child, provider, transitionTime, curve);
		
	}
	
	private final HashMap<Component, AnimationData> responsiveData = new HashMap<>();
	private final HashMap<Component, AnimationInstance> runningAnimations = new HashMap<>();
	
	TransitionResponsivePreferredSize(Container father, AnimationManager animator) {
		father.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Dimension fatherSize = father.getSize();
				responsiveData.forEach((c, data) -> {
					AnimationInstance previous = runningAnimations.get(c);
					if(previous != null) {
						previous.stop((long)(previous.getProgress() * previous.getDuration()));
					}
					
					ValueProvider<Dimension> v = data.valueProvider;
					
					Dimension oldSize = c.getPreferredSize();
					if(oldSize == null) {
						oldSize = new Dimension();
					}
					final Dimension finalOldSize = oldSize;
					
					Dimension newSize = v.get(fatherSize);
					AnimationInstance newAnimation = animator.start(new DoubleValueAnimationBinding(data.curve, () -> 0.0, () -> 1.0, true) {
						@Override
						protected void progress(double val) {
//							System.out.println(val);
							c.setPreferredSize(new Dimension(
									(int) (finalOldSize.width + (newSize.width - finalOldSize.width) * val),
									(int) (finalOldSize.height + (newSize.height - finalOldSize.height) * val)
							));
							/** FROM: https://www.dovov.com/swing-guivalidaterevalidateinvalidate.html
							 * invalidate()将容器标记为无效。 意味着内容是不对的，必须重新布局。 但这只是一种标志/标志。 有可能多个无效的容器必须稍后刷新。
							 * validate()执行重新布局。 这意味着要求所有尺寸的无效内容，并且由LayoutManager将所有子组件的尺寸设置为适当的值。
							 * revalidate()只是两者的总和。 它将容器标记为无效并执行容器的布局。
							 * validate() ：在Swing中当你创build一个Component时，它是valid即它的有效属性是false 。 一个组件被认为是有效的，当它的宽度，高度，位置和东西已经确定。 这通常通过直接或间接调用其validate()方法来完成。 当我们在容器上调用validate()时，它将通过调用其通常会调用LayoutManager doLayout()方法来validation容器（如果它是无效的）。 现在，放在这个容器上的每个孩子都将被recursion地validation，这样整个树就会被布置出来，并且会变得有效。
							 * revalidate() ：当你改变一个会影响它们宽度/高度的属性时， revalidate()被调用，当你改变一个会影响它们外观的属性时调用repaint（）。 例如，如果您的JFrame包含一个JPanel ，现在在某个时间点，您删除了这个JPanel并根据新放置的JPanel的内容插入了一个新的JPanel ， JPanel中的组件的大小也是如此作为The CONTAINER本身（凭借它所使用的布局pipe理器）发生变化。 将其推到无效状态。 所以为了validation这个改变，你必须显式调用revalidate() 。
							 * invalidate() ：这个东西是东西，我从来没有用过，所以可能没有太多的信息，我可以给。 但看起来像上面提供的情况，可以给出一点提示，至于在invalidate()发生了什么。
							 */
							c.revalidate();
						}
					}, data.duration);
					newAnimation.addAnimationEndListener(() -> {
						runningAnimations.remove(c);
					});
					runningAnimations.put(c, newAnimation);
					
				});
			}
		});
	}
	
	public TransitionResponsivePreferredSize addResponsiveChild(Component child, ValueProvider<Dimension> provider, long transitionTime, Curve curve) {
		responsiveData.put(child, new AnimationData(provider, transitionTime, curve));
		return this;
	}
	
}
