package xueli.gamengine.view.anim2d;

import xueli.utils.Logger;

@ComponentType(type = "text_changing")
public class ComponentChangeText implements AnimComponent {

	private String changeElement;
	private String textChangeTo;

	public ComponentChangeText(String changeElement, String textChangeTo) {
		this.changeElement = changeElement;
		this.textChangeTo = textChangeTo;

	}

	@Override
	public boolean invoke(Anim2D anim) {
		Element2D element = anim.scene.get(changeElement);
		if (!(element instanceof ElementTextView)) {
			Logger.warn(this.getClass().getName() + ": can't find element to change text: " + changeElement);
			return Constant.COMPONENT_CAN_BE_DISPOSED;
		}

		((ElementTextView) element).setText(textChangeTo);

		return Constant.COMPONENT_CAN_BE_DISPOSED;
	}

	@Override
	public String toString() {
		return "ComponentChangeText{" + "changeElement='" + changeElement + '\'' + ", textChangeTo='" + textChangeTo
				+ '\'' + '}';
	}

}
