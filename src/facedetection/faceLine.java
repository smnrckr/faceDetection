package facedetection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author sema_
 */

public class faceLine {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier faceCascade = new CascadeClassifier();
        faceCascade.load("haarcascade_frontalface_default.xml"); // tespit için eğit
        CascadeClassifier eyeCascade = new CascadeClassifier();
        eyeCascade.load("haarcascade_eye.xml"); // göz tespiti
        CascadeClassifier noseCascade = new CascadeClassifier();
        noseCascade.load("haarcascade_mcs_nose.xml"); // burun tespiti
        CascadeClassifier mouthCascade = new CascadeClassifier();
        mouthCascade.load("haarcascade_mcs_mouth.xml"); // ağız tespiti
        int cameraIndex=0;
        VideoCapture capture = new VideoCapture(cameraIndex);
        
        if(!capture.isOpened()){
            System.out.println("kamera kapalı");
            return;
        }
        
        while(true){
            // Kameradan bir kare al
            Mat frame = new Mat();
            capture.read(frame);
            if(frame.empty()) {
                // Kare boşsa döngüden çık
                break;
            }
            
            // Yüzü tanımla
            MatOfRect faceDetections = new MatOfRect();
            faceCascade.detectMultiScale(frame, faceDetections);
            for(Rect rect : faceDetections.toArray()) {
                // Yüz alanını çerçeveleme
                Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
                
                // Gözleri, burun ve ağızı bul
                MatOfRect eyeDetections = new MatOfRect();
                eyeCascade.detectMultiScale(frame.submat(rect), eyeDetections);
                for(Rect eyeRect : eyeDetections.toArray()) {
                    Point center = new Point(rect.x + eyeRect.x + eyeRect.width / 2, rect.y + eyeRect.y + eyeRect.height / 2);
                    Imgproc.circle(frame, center, 5, new Scalar(255, 0, 0), -1);//kırmızı
                }
                
                MatOfRect noseDetections = new MatOfRect();
                noseCascade.detectMultiScale(frame.submat(rect), noseDetections);
                for(Rect noseRect : noseDetections.toArray()) {
                    Point center = new Point(rect.x + noseRect.x + noseRect.width / 2, rect.y + noseRect.y + noseRect.height / 2);
                    Imgproc.circle(frame, center, 5, new Scalar(0, 0, 255), -1);//lacivert
                }
                
                MatOfRect mouthDetections = new MatOfRect();
                mouthCascade.detectMultiScale(frame.submat(rect), mouthDetections);
                for(Rect mouthRect : mouthDetections.toArray()) {
                    Point center = new Point(rect.x + mouthRect.x + mouthRect.width / 2, rect.y + mouthRect.y + mouthRect.height / 2);
                    Imgproc.circle(frame, center, 5, new Scalar(0, 0, 0), -1); //siyah
                }
            }
            
            HighGui.imshow("Face Detection", frame);
            char c = (char) HighGui.waitKey(1);
            if (c == 'q') {
                break;
            }    
        }
        
        capture.release();
        HighGui.destroyAllWindows();
    }
}
