package com.frc107.scouting2019.model;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.frc107.scouting2019.BuildConfig;
import com.frc107.scouting2019.Scouting;
import com.frc107.scouting2019.ScoutingStrings;
import com.frc107.scouting2019.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import androidx.core.content.FileProvider;

public class AdminModel {
    public static final int MATCH = 0;
    public static final int PIT = 1;

    public ArrayList<Uri> getPhotoUriList(Context context) {
        ArrayList<Uri> uriList = new ArrayList<Uri>();
        for (File photo :  Scouting.FILE_UTILS.getPhotos()) {
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", photo);
            uriList.add(uri);
        }
        return uriList;
    }

    public boolean concatenateData(int type) {
        String prefix = "Match";
        String header = ScoutingStrings.MATCH_HEADER;
        if (type == PIT) {
            prefix = "Pit";
            header = ScoutingStrings.PIT_HEADER;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(header);
        builder.append("\n");

        FileUtils fileUtils = Scouting.FILE_UTILS;

        if (!fileUtils.directoryExists()) {
            fileUtils.createDirectory();
        }

        File[] files = fileUtils.getFilesInDirectory();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (!file.getName().startsWith(prefix))
                continue;

            String content = fileUtils.getContentFromFile(file);
            builder.append(content);
        }

        String fileName = "Concatenated" + prefix + ".csv";
        File newFile = new File(fileUtils.getScoutingDirectory(), fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile, false);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {
            if (newFile.exists())
                fileWriter.write("");

            fileWriter.write(builder.toString());
            return true;
        } catch (IOException e) {
            Log.d("Scouting", e.getMessage());
        }

        return false;
    }

    public File getMatchFile(boolean concatenated) {
        if (concatenated) {
            return Scouting.FILE_UTILS.getConcatMatchFile();
        } else {
            return Scouting.FILE_UTILS.getMatchFile();
        }
    }

    public File getPitFile(boolean concatenated) {
        if (concatenated) {
            return Scouting.FILE_UTILS.getConcatPitFile();
        } else {
            return Scouting.FILE_UTILS.getPitFile();
        }
    }
}
