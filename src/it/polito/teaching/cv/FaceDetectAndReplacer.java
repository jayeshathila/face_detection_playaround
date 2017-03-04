package it.polito.teaching.cv;

import com.sun.tools.hat.internal.model.Snapshot;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * Created by jayeshathila
 * on 26/02/17.
 */
public class FaceDetectAndReplacer {
    private static Mat cropImage;

    public static void launch(String[] args) throws Exception {
        int x = 0, y = 0, height = 0, width = 0;
        MatOfRect face_Detections = new MatOfRect();

        System.out.println("\nRunning FaceDetector");

        CascadeClassifier faceDetector = new CascadeClassifier("resources/haarcascades/haarcascade_frontalface_alt.xml");
        Mat image = Imgcodecs.imread("source_image_path");
        faceDetector.detectMultiScale(image, face_Detections);
        System.out.println(String.format("Detected %s faces", face_Detections.toArray().length));
        Rect rect_Crop = null;
        Rect rectCrop = null;
        Mat logo = Imgcodecs.imread("resources/pig.png");
        Mat mat = new Mat();
        for (Rect rect : face_Detections.toArray()) {
            Size size = new Size(rect.width, rect.height);
            Mat submat = image.submat(rect);
            Imgproc.resize(logo, mat, size, 10, 10, 2);
            mat.copyTo(submat);
        }

        Imgcodecs.imwrite("destination_path", image);

    }

    public static void main(String[] args) throws Exception {
        // load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
}
