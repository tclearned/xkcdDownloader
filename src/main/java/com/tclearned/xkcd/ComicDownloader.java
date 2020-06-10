package com.tclearned.xkcd;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ComicDownloader {
    private static final String BASE_URL = "https://xkcd.com/";
    private URL downloadURL;

    public ComicDownloader(int number) {
        try {
            downloadURL = new URL(BASE_URL + number + "/info.0.json");
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + BASE_URL + number + "/info.0.json");
            System.err.println("This should never happen");
            System.exit(1);
        }
    }

    public byte[] DownloadComic() {
        ObjectMapper mapper = new ObjectMapper();
        // output is what is going to actually be storing the image that we will then write to a file
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        URL imageURL;
        try {
            Comic current = mapper.readValue(downloadURL, Comic.class);
            imageURL = new URL(current.image);
        }  catch (IOException e) {
            System.err.println("Failed to download from url: " + downloadURL);
            e.printStackTrace();
            return new byte[0];
        }

        try(InputStream is = imageURL.openStream()) {
            // downloading image to byte array
            // image is downloaded in 1 Kilobyte sized chunks and then appended to the output stream
            int n;
            byte[] buffer = new byte[1024];

            while((n = is.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            System.err.println("Failed to download image from url: " + imageURL);
            e.printStackTrace();
        }
        return output.toByteArray();
    }
}
