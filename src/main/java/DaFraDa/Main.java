package DaFraDa;

import DaFraDa.dobot_library.Dobot;
import DaFraDa.dobot.*;
import DaFraDa.dobot.camera.Camera;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService(endpointInterface = "DaFraDa.IMain")
public class Main implements IMain{
	
	@WebMethod
	@Override
	public String Homing() throws Exception {
		DobotController.Homing();
		return "";
	}
	
	@Override
	@WebMethod
	public String PTP(int x, int y, int z, int r) throws Exception {
		DobotController.PTP(x, y, z, r);
		return "";
	}
	
	@Override
	@WebMethod
	public String StartDobotProgram() throws Exception {
		DobotController.StartDobotProgram();
		return "";
	}

	@Override
	public String SetEndeffectorSuction(boolean enable, boolean suction) {
		DobotController.SetEndEffectorSuction(enable, suction);
		return "";
	}

	@Override
	public String GetPosition() {
		DobotController.GetPosition();
		return "";
	}

	@Override
	public String CameraTest() {
		Camera.CalculateColor();
		return "";
	}
	
	
	
}
