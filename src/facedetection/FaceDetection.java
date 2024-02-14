package facedetection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class FaceDetection {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        CascadeClassifier faceCascade = new CascadeClassifier();
        faceCascade.load("haarcascade_frontalface_default.xml"); // tespit için eğit

        Mat image = Imgcodecs.imread("input.jpg"); // Tespit yapılacak görüntü dosyası

        // Yüz tespiti
        MatOfRect faceDetections = new MatOfRect();
        faceCascade.detectMultiScale(image, faceDetections);

        // Tespit edilen yüzlerin etrafına dikdörtgen çizilmesi
        for (int i = 0; i < faceDetections.toArray().length; i++) {
            Rect rect = faceDetections.toArray()[i]; // Get individual Rect object
            Imgproc.rectangle(image,
                    new org.opencv.core.Point(rect.x, rect.y),
                    new org.opencv.core.Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }

        Imgcodecs.imwrite("output.jpg", image); // dikdörtgen çizilmiş yüzü jpg olarak dışarı aktar
        System.out.println("Yüz tespiti tamamlandı. Sonuç 'output.jpg' olarak kaydedildi.");
    }

    
}
