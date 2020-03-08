package xueLi.craftGame.nightmare;

public enum Subject {
	
	CHINESE(100),MATH(1000),ENGLISH(50),PHYSICAL(500),CHEMISTRY(800),BIOLOGY(233);
	
	public int howHardItIs;
	Subject(int howHardItIs){
		this.howHardItIs = howHardItIs;
	}
	
}
