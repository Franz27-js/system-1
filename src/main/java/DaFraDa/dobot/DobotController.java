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
	static  {
		try {
			colorPositions = new ArrayList<>();
			colorPositions.add(new DobotPosition(16, 279, -40, 0, TypeOfPosition.PICKUP)); //E30
			positions = new ArrayList<>();
			//positions.add(new DobotPosition(16, 279, 0, 0, TypeOfPosition.VIA));
			//positions.add(new DobotPosition(16, 279, 0, 0, TypeOfPosition.VIA));
			positions.add(new DobotPosition(180, 214, 0, 0, TypeOfPosition.PICTURE));
			positions.add(new DobotPosition(200, 0, 30, 0, TypeOfPosition.VIA));
			positions.add(new DobotPosition(-64, -303, 0, 0, TypeOfPosition.VIA));
			positions.add(new DobotPosition(-64, -303, -40, 0, TypeOfPosition.DROPOFF)); //A1
			positions.add(new DobotPosition(-64, -303, 0, 0, TypeOfPosition.VIA));
			positions.add(new DobotPosition(200, 0, 30, 0, TypeOfPosition.VIA));
			dobot = new Dobot();
			dobot.connect_serial("COM5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String Homing() throws Exception {
		dobot.SetHomeCmd();
		return "";
	}
	
	public static String PTP(int x, int y, int z, int r) throws Exception {
		dobot.SetPTPCmd(x, y, z, r);
		return "";
	}
	
	public static String SetEndEffectorSuction(boolean enable, boolean suction) {
		dobot.SetEndEffectorSuctionCup(enable, suction);
		return "";
	}
	
	public static String GetPosition() {
		double[] currentPos = dobot.GetPose();
		System.out.println("Aktuelle Position: " + currentPos[0] + ";" + currentPos[1] + ";" + currentPos[2] + ";" + currentPos[3]);
		
		return "";
	}
	
	public static String StartDobotProgram() throws Exception {
		for (DobotPosition pos : colorPositions) {
			PickupCube(pos);
			EvaluateColorAndMoveToEndPosition();
		}
		
		
		
		
		
		return "Roboterprogramm gestartet";
	}
	
	private static void PickupCube(DobotPosition pos) throws Exception {
		dobot.SetPTPCmd(pos.x, pos.y, pos.z+40, pos.r);
		dobot.SetPTPCmd(pos.x, pos.y, pos.z, pos.r);
		Thread.sleep(2000);
		dobot.SetEndEffectorSuctionCup(true, true);
		dobot.SetPTPCmd(pos.x, pos.y, pos.z+40, pos.r);
	}
	
	private static void EvaluateColorAndMoveToEndPosition() throws Exception {
		for (DobotPosition pos : positions) {
			dobot.SetPTPCmd(pos.x, pos.y, pos.z, pos.r);
			Thread.sleep(2000);
			switch (pos.positionType) {
				case TypeOfPosition.VIA:
					break;
				case TypeOfPosition.DROPOFF:
					dobot.SetEndEffectorSuctionCup(false, false);
					break;
				case TypeOfPosition.PICTURE:
					System.out.println("Bild machen");
					List<Object> colorData = new ArrayList<>();
					String color = Camera.CalculateColor();
					colorData.add(color);
					Database.writeDB("color_data", colorData);
					//WriteDatabase
				default:
					break;
				
			}
		}
	}

}
