package DaFraDa;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface IMain {

	@WebMethod
	public String Homing() throws Exception;
	
	@WebMethod
	public String PTP(int x, int y, int z, int r) throws Exception;
	
	@WebMethod
	public String StartDobotProgram() throws Exception;
	
	@WebMethod
	public String SetEndeffectorSuction(boolean enable, boolean suction);
	
	@WebMethod
	public String GetPosition();
	
	@WebMethod
	public String CameraTest();
}
