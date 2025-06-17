package DaFraDa.dobot;
import java.util.ArrayList;
import java.util.List;

import DaFraDa.dobot.camera.Camera;
import DaFraDa.dobot.database.Database;
import DaFraDa.dobot_library.Dobot;

public class DobotController {
	private static Dobot dobot;
	private static ArrayList<DobotPosition> positions;
	private static ArrayList<DobotPosition> colorPositions;
	private static DobotPosition bluePosition;
	private static DobotPosition redPosition;
	private static DobotPosition greenPosition;
	private static DobotPosition yellowPosition;
	private static DobotPosition defaultEndPosition;
	static  {
		try {
			colorPositions = new ArrayList<>();
			colorPositions.add(new DobotPosition(16, 279, -40, 0, TypeOfPosition.PICKUP)); //E30
			positions = new ArrayList<>();
			//positions.add(new DobotPosition(16, 279, 0, 0, TypeOfPosition.VIA));
			//positions.add(new DobotPosition(16, 279, 0, 0, TypeOfPosition.VIA));
			positions.add(new DobotPosition(180, 214, 0, 0, TypeOfPosition.PICTURE));
			positions.add(new DobotPosition(200, 0, 30, 0, TypeOfPosition.VIA));
			bluePosition = new DobotPosition(-64, -303, -40, 0, TypeOfPosition.DROPOFF);
			redPosition = new DobotPosition(-64, -273, -40, 0, TypeOfPosition.DROPOFF);
			greenPosition = new DobotPosition(-64, -243, -40, 0, TypeOfPosition.DROPOFF);
			yellowPosition = new DobotPosition(-64, -213, -40, 0, TypeOfPosition.DROPOFF);
			defaultEndPosition = new DobotPosition(200, 0, -40, 0, TypeOfPosition.DROPOFF);
			dobot = new Dobot();
			dobot.connect_serial("COM5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String Homing() throws Exception {
		System.out.println("Homing");
		dobot.SetHomeCmd();
		return "Homing-Befehl wird ausgeführt";
	}
	
	public static String PTP(int x, int y, int z, int r) throws Exception {
		dobot.SetPTPCmd(x, y, z, r);
		return "Point-to-Point Command wird ausgeführt";
	}
	
	public static String SetEndEffectorSuction(boolean enable, boolean suction) {
		dobot.SetEndEffectorSuctionCup(enable, suction);
		return "Sauger wird angesteuert";
	}
	
	public static String GetPosition() {
		double[] currentPos = dobot.GetPose();
		System.out.println("Aktuelle Position: " + currentPos[0] + ";" + currentPos[1] + ";" + currentPos[2] + ";" + currentPos[3]);
		return "Aktuelle Position: " + currentPos[0] + ";" + currentPos[1] + ";" + currentPos[2] + ";" + currentPos[3];
	}
	
	public static String StartDobotProgram() throws Exception {
		for (DobotPosition pos : colorPositions) {
			DobotPosition endPos;
			PickupCube(pos);
			String color = EvaluateColor();
			switch (color) {
				case "#FF0000":
					endPos = redPosition;
					break;
				case "#0000FF":
					endPos = bluePosition;
					break;
				case "#00FF00":
					endPos = greenPosition;
					break;
				case "#FFFF00":
					endPos = yellowPosition;
					break;
				default:
					System.out.println("Keine Farbe erkannt");
					endPos = defaultEndPosition;
					break;
					
			}
			DropoffCube(endPos, 40);
			dobot.SetPTPCmd(200, 0, 0, 0);
		}		
		
		return "Roboterprogramm gestartet";
	}
	
	private static void PickupCube(DobotPosition pos) throws Exception {
		dobot.SetPTPCmd(pos.x, pos.y, pos.z+40, pos.r);
		dobot.SetPTPCmd(pos.x, pos.y, pos.z, pos.r);
		Thread.sleep(4000);
		dobot.SetEndEffectorSuctionCup(true, true);
		dobot.SetPTPCmd(pos.x, pos.y, pos.z+40, pos.r);
	}
	
	private static void DropoffCube(DobotPosition pos, int offset) throws Exception {
		dobot.SetPTPCmd(pos.x, pos.y, pos.z + offset, pos.r);
		dobot.SetPTPCmd(pos.x, pos.y, pos.z, pos.r);
		Thread.sleep(4000);
		dobot.SetEndEffectorSuctionCup(false, false);
		dobot.SetPTPCmd(pos.x, pos.y, pos.z + offset, pos.r);
	}
	
	private static String EvaluateColor() throws Exception {
		for (DobotPosition pos : positions) {
			dobot.SetPTPCmd(pos.x, pos.y, pos.z, pos.r);
			Thread.sleep(2000);
			switch (pos.positionType) {
				case TypeOfPosition.VIA:
					break;
				case TypeOfPosition.DROPOFF:
					break;
				case TypeOfPosition.PICTURE:
					System.out.println("Bild machen");
					List<Object> colorData = new ArrayList<>();
					String color = Camera.CalculateColor();
					colorData.add(color);
					Database.writeDB("color_data", colorData);
					return color;
				default:
					break;
				
			}
		}
		return "Error: Farberkennung konnte nicht ausgeführt werden!";
	}

}
