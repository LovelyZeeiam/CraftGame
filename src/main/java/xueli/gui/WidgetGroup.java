package xueli.gui;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import xueli.gui.layout.AbsoluteLayout;
import xueli.gui.layout.LayoutManager;
import xueli.gui.skin.DefaultWidgetGroupSkin;

public class WidgetGroup extends Widget {
	
//	private static final Logger LOGGER = new Logger();
	
//	public static final Identifier IDENTIFIER = new Identifier(MyUIFrameworkInfo.FRAMEWORK_NAME, "widget_group"); 
	
	public static final String PROPERTY_LAYOUT_MANAGER = "layout";
	public static final String PROPERTY_WIDGET_ADD = "child_add";
	public static final String PROPERTY_WIDGET_REMOVE = "child_remove";
	
	private final ArrayList<Widget> widgets = new ArrayList<>();
	private LayoutManager layoutManager = new AbsoluteLayout();
	
	public WidgetGroup(UIContext ctx) {
		super(ctx);
		this.registerPropertyListener();
		
	}
	
	private void registerPropertyListener() {
		this.registerPropertyChangeListener(PROPERTY_LAYOUT_MANAGER, this::onPropertyLayoutManagerChange);
		this.registerPropertyChangeListener(PROPERTY_WIDGET_ADD, this::onPropertyChildrenAdd);
		this.registerPropertyChangeListener(PROPERTY_WIDGET_REMOVE, this::onPropertyChildrenRemove);
		
	}
	
	private void onPropertyLayoutManagerChange(PropertyChangeEvent e) {
		this.announceLayout();
	}
	
	private void onPropertyChildrenAdd(PropertyChangeEvent e) {
		this.layoutManager.addWidget(parent, this);
		this.announceLayout();
		
	}
	
	private void onPropertyChildrenRemove(PropertyChangeEvent e) {
		this.layoutManager.removeWidget(parent, this);
		this.announceLayout();
		
	}
	
	public void add(Widget widget) {
		if(widget.parent != null) {
			throw new IllegalStateException("Add it the second time? " + widget.toString() + " in " + this.toString());
		}
		
		widget.parent = this;
		this.widgets.add(widget);
		this.firePropertyChange(PROPERTY_WIDGET_ADD, null, widget);
		
	}
	
	public void remove(Widget widget) {
		widget.parent = null;
		this.widgets.remove(widget);
		this.firePropertyChange(PROPERTY_WIDGET_REMOVE, null, widget);
		
	}
	
	public void announceLayout() {
		this.layoutManager.doLayout(this);
		
	}
	
	public Widget getWidgetFromIndex(int i) {
		return this.widgets.get(i);
	}
	
	public int getWidgetCount() {
		return this.widgets.size();
	}
	
	public void setSkin(DefaultWidgetGroupSkin skin) {
		super.setSkin(skin);
	}
	
	public DefaultWidgetGroupSkin getSkin() {
		return (DefaultWidgetGroupSkin) super.getSkin();
	}
	
	public void setLayoutManager(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public LayoutManager getLayoutManager() {
		return layoutManager;
	}

}
