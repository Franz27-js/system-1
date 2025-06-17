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
		String result = DobotController.Homing();
		return result;
	}
	
	@Override
	@WebMethod
	public String PTP(int x, int y, int z, int r) throws Exception {
		String result = DobotController.PTP(x, y, z, r);
		return result;
	}
	
	@Override
	@WebMethod
	public String StartDobotProgram() throws Exception {
		String result = DobotController.StartDobotProgram();
		return result;
	}

	@Override
	public String SetEndeffectorSuction(boolean enable, boolean suction) {
		String result = DobotController.SetEndEffectorSuction(enable, suction);
		return result;
	}

	@Override
	public String GetPosition() {
		String result = DobotController.GetPosition();
		return result;
	}

	@Override
	public String CameraTest() {
		String result = Camera.CalculateColor();
		return result;
	}
	
	
	
}
