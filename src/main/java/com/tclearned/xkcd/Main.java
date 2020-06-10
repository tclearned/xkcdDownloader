package com.tclearned.xkcd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length < 2) {
            System.err.println("Please provide at least one comic to download");
            System.exit(1);
        }
        for(int i = 1; i < args.length; i++) {
            int number = 0;


            try(Scanner parser = new Scanner(args[i])) {
                number = parser.nextInt();
            } catch (Exception e) {
                System.err.println(args[i] + " is not a number, please only input numbers as the argument");
            }

            if (number == 0) {
                continue;
            }

            ComicDownloader downloader = new ComicDownloader(number);
            byte[] image = downloader.DownloadComic();

            if (image.length == 0) {
                continue;
            }

            try(FileOutputStream fs = new FileOutputStream(number + ".png")) {
                fs.write(image);
            } catch (IOException e) {
                System.err.println("Failed to write file for comic " + number);
                e.printStackTrace();
            }

        }
    }
}
