package imageservise;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStatusImageDownloader {
    public void downloadStatusImage(int code) throws Exception {
        HttpStatusChecker checker = new HttpStatusChecker();
        String imageUrl = checker.getStatusImage(code);
        if (imageUrl == null) {
            throw new Exception("Image not found for status code " + code);
        }
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            InputStream inputStream = connection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            System.out.println("Image downloaded successfully for status code " + code);
        } else {
            throw new Exception("Error downloading image for status code " + code);
        }
    }
}
