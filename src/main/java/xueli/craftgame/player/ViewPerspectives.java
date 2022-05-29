package xueli.craftgame.player;

public enum ViewPerspectives {

	FIRST_PERSON(new IViewPerspective() {
	});

	private IViewPerspective perspective;

	ViewPerspectives(IViewPerspective perspective) {
		this.perspective = perspective;
	}

	public IViewPerspective getPerspective() {
		return perspective;
	}

}
