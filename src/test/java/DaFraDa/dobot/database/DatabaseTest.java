package DaFraDa.dobot.database;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTest {

	public static void main(String[] args) {
		List<Object> test = new ArrayList<>();
		test.add("test");
		Database.writeDB("sensor_data", test);
	}
}
