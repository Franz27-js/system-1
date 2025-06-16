package DaFraDa.dobot;

public class DobotPosition {
	
	
	public int x;
	public int y;
	public int z;
	public int r;
	public TypeOfPosition positionType;
	
	
	public DobotPosition(int x, int y, int z, int r, TypeOfPosition positionType) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.positionType = positionType;
	}
}
