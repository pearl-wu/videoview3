package tw.com.bais.groundvideo;

import android.graphics.Bitmap;

/**
 * Created by nchutchind on 10/10/2014.
 */
public interface ImageLoadTaskListener {
    public void imageLoaded(String key, Bitmap bmp);
}
