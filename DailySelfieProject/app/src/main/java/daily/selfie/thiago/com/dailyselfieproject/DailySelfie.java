package daily.selfie.thiago.com.dailyselfieproject;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 *
 */
public class DailySelfie implements Serializable{

    private Bitmap pictureTaken;
    private String nameOfPictureTaken;
    private String pathOfImage;

    public DailySelfie(Bitmap pictureTaken, String nameOfPictureTaken, String pathOfImage) {
        this.pictureTaken = pictureTaken;
        this.nameOfPictureTaken = nameOfPictureTaken;
        this.pathOfImage = pathOfImage;
    }

    public Bitmap getPictureTaken() {
        return pictureTaken;
    }

    public void setPictureTaken(Bitmap pictureTaken) {
        this.pictureTaken = pictureTaken;
    }

    public String getNameOfPictureTaken() {
        return nameOfPictureTaken;
    }

    public void setNameOfPictureTaken(String nameOfPictureTaken) {
        this.nameOfPictureTaken = nameOfPictureTaken;
    }

    public String getPathOfImage() {
        return pathOfImage;
    }

    public void setPathOfImage(String pathOfImage) {
        this.pathOfImage = pathOfImage;
    }
}
