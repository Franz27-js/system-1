package DaFraDa.dobot.camera;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class Camera {

	public static String CalculateColor() {
		nu.pattern.OpenCV.loadLocally();
		VideoCapture camera = new VideoCapture(0);
		if (!camera.isOpened()) {
			return "Kamera konnte nicht geöffnet werden!";
		}
		
		Mat frame = new Mat();
		
		//try {Thread.sleep(1000);} catch (Exception e) {}
		
		camera.read(frame);
		
		
		camera.release();
		
		// In HSV konvertieren
        Mat hsv = new Mat();
        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);

     // Farben definieren
        Scalar lowerRed1 = new Scalar(0, 100, 100);
        Scalar upperRed1 = new Scalar(10, 255, 255);
        Scalar lowerRed2 = new Scalar(160, 100, 100);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        Scalar lowerGreen = new Scalar(40, 70, 70);
        Scalar upperGreen = new Scalar(80, 255, 255);

        Scalar lowerBlue = new Scalar(100, 150, 0);
        Scalar upperBlue = new Scalar(140, 255, 255);

        Scalar lowerYellow = new Scalar(20, 100, 100);
        Scalar upperYellow = new Scalar(30, 255, 255);

        // Farbmasken erstellen
        Mat maskRed1 = new Mat(), maskRed2 = new Mat(), maskRed = new Mat();
        Core.inRange(hsv, lowerRed1, upperRed1, maskRed1);
        Core.inRange(hsv, lowerRed2, upperRed2, maskRed2);
        Core.add(maskRed1, maskRed2, maskRed);

        Mat maskGreen = new Mat();
        Core.inRange(hsv, lowerGreen, upperGreen, maskGreen);

        Mat maskBlue = new Mat();
        Core.inRange(hsv, lowerBlue, upperBlue, maskBlue);

        Mat maskYellow = new Mat();
        Core.inRange(hsv, lowerYellow, upperYellow, maskYellow);

        // Pixel zählen
        int redPixels = Core.countNonZero(maskRed);
        int greenPixels = Core.countNonZero(maskGreen);
        int bluePixels = Core.countNonZero(maskBlue);
        int yellowPixels = Core.countNonZero(maskYellow);

        // Ergebnisse ausgeben
        System.out.println("Rote Pixel:  " + redPixels);
        System.out.println("Grüne Pixel: " + greenPixels);
        System.out.println("Blaue Pixel: " + bluePixels);
        System.out.println("Gelbe Pixel: " + yellowPixels);

        // Schwellenwert für Erkennung
        int threshold = 100; // je nach Bildgröße anpassen

		if (yellowPixels > threshold) {
			return "#FFFF00";
        } else if (greenPixels > threshold) {
            return "#00FF00";
        } else if (bluePixels > threshold) {
            return "#0000FF";
        } else if (redPixels > threshold) {
            return "#FF0000";
        } else {
            return "none";
        }
    }
}

