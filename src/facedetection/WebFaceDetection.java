package facedetection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
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
public class WebFaceDetection {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier faceCascade = new CascadeClassifier();
        faceCascade.load("C:/Users/sema_/Downloads/opencv/build/etc/haarcascades/haarcascade_frontalface_default.xml"); // tespit için eğit
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
            if(!capture.read(frame)){
                //sıkıntı varsa döngüden çık
                break;
            }
            
            //yüzü tanımla
            MatOfRect faceDetections = new MatOfRect();
            faceCascade.detectMultiScale(frame, faceDetections);
            for(int i=0;i<faceDetections.toArray().length;i++){
                Rect rect = faceDetections.toArray()[i];
                Imgproc.rectangle(frame,
                    new org.opencv.core.Point(rect.x, rect.y),
                    new org.opencv.core.Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0),
                    3);     
            }
            
            HighGui.imshow("Face Detection", frame);
            char c = (char) HighGui.waitKey(1);
            if (c == 'q') {
                break;
            }    
        }
        
            
        capture.release();

        // Pencereyi kapat
        HighGui.destroyAllWindows();
        
    }
    
}
