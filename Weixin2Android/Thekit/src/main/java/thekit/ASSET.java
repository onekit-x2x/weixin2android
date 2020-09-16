package thekit;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import thekit.android.Android;

public class ASSET {
    public static String loadString(String path) throws IOException {
        StringBuilder result = new StringBuilder();
        AssetManager assetManager = Android.application().getAssets();
        try (InputStream stream = assetManager.open(path)) {
            try (InputStreamReader inputReader = new InputStreamReader(stream)) {
                try (BufferedReader bufReader = new BufferedReader(inputReader)) {
                    String line = "";
                    while ((line = bufReader.readLine()) != null) {
                        result.append(line).append("\r\n");
                    }
                }
            }
        }
        return result.toString();
    }

    public static JSONObject loadJSON(String path) throws IOException, JSONException {
        String text = loadString(path);
        return new JSONObject(text);
    }

    public static Bitmap loadImage(String path) throws IOException {
        AssetManager assetManager = Android.application().getAssets();
        try (InputStream stream = assetManager.open(path)) {
            // BitmapFactory.Options options = new BitmapFactory.Options();
            //   options.inPurgeable = true;
            // options.inInputShareable = true;
            //     if (!isOrigin) {
            //       options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            //options.inSampleSize = 2;
            // }
            //      stream.close();
            return BitmapFactory.decodeStream(stream);
        }
    }
}