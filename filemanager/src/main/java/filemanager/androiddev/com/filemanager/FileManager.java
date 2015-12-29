package filemanager.androiddev.com.filemanager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

/**
 * Created by Emil Ivanov on 12/24/2015.
 */
public class FileManager {
    private static final String TAG = FileManager.class.getName();


    /**
     * Method for check whether or not the external storage is available,
     * before making write request.
     *
     * @return true if the storage is available, false otherwise.
     */
    public static boolean isSdReadable() {

        boolean mExternalStorageAvailable = false;
        try {
            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                mExternalStorageAvailable = true;
                //Log.i("isSDReadable","External storage card os readable");
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // We can only read the media
                //Log.i("isSdReadable", "External storage card is readable.");
                mExternalStorageAvailable = true;
            } else {
                // Something else is wrong. It may be one of many other
                // states, but all we need to know is we can neither read nor
                // write
                mExternalStorageAvailable = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mExternalStorageAvailable;
    }


    /**
     * Method for creating a directory.
     *
     * @param directoryName - directory name
     * @return - true if the directory was created
     * Writing to this path requires the
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE} permission,
     * and starting in read access requires the
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE} permission,
     */
    public static boolean createDirectory(String directoryName) {
        boolean isCreated = false;

        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        File newDirectory = new File(externalStorageDirectory, directoryName);
        if (newDirectory.mkdirs()) {
            isCreated = true;
        } else {
            isCreated = false;
        }
        return isCreated;
    }

    /**
     * Create folder with the application name
     *
     * @param context - the application context
     * @return - true if the folder was created.
     */
    public static String createDirectory(Context context) {
        boolean isCreated = false;

        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        File newDirectory = new File(externalStorageDirectory, context.getString(R.string.app_name));
        if (newDirectory.mkdirs()) {
            isCreated = true;
        } else {
            isCreated = false;
        }


        Log.d(TAG, "User directory " + newDirectory.getAbsolutePath());

        return newDirectory.getAbsolutePath();
    }

    /**
     * Method for writing text into file.
     *
     * @param content - the text content to be written
     * @param context - applications context
     * @return - returns the file path if the content was success, null otherwise.
     */
    public static String writeTextInFile(String content, Context context) {

        PrintWriter out = null;
        FileWriter fileWriter = null;
        BufferedWriter bw = null;

        String filePath = null;

        String fileDirectory = createDirectory(context);
        if (fileDirectory != null) {

            String name = "text.txt";
            File file = new File(fileDirectory, name);
            filePath = file.getPath();
            try {
                fileWriter = new FileWriter(filePath, true);
                bw = new BufferedWriter(fileWriter);
                out = new PrintWriter(bw);

                out.write(content);
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close(); // Will close bw and fw too
                    } else if (bw != null) {
                        bw.close(); // Will close fw too
                    } else if (fileWriter != null) {
                        fileWriter.close();
                    } else {
                        // Oh boy did it fail hard! :3
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Closing the file writers failed for some obscure reason
                }
            }

        } else {

            Log.d(TAG, "File directory is NULL");

        }
        Log.d(TAG, "This will write file in external storage");

        return filePath;
    }

    /**
     * Method for reading file's content
     *
     * @param filePath - the file's path
     * @return - string with the content of the file.
     */
    public static String readTextFile(String filePath) {
        File file = new File(filePath);

        // Read the text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();

    }

    /**
     * Clear file content without deleting the file.
     * @param path - file path.
     */
    public static void clearFileContent(String path){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.close();
            }
        }
    }

    /**
     * Method for copy file
     *
     * @param src - File's source location.
     * @param dst - File's destination
     * @throws IOException
     */
    public static void copyFile(File src, File dst) {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dst).getChannel();

            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inChannel != null) {

                    inChannel.close();
                }
                if (outChannel != null) {

                    outChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Get the file name from path.
     *
     * @param filePath - file's location
     * @return - String file name.
     */
    public String getFileNameFromPath(String filePath) {

        String fileName =
                filePath.substring(
                        filePath.lastIndexOf(File.separator) + 1);
        return fileName;
    }

    /**
     * Get the file extension from name.
     *
     * @param fileName - file's name.
     * @return - file's extension
     */
    public String getFileExtension(String fileName) {

        String filenameArray[] = fileName.split("\\.");
        String extention = filenameArray[filenameArray.length - 1];
        return extention;
    }


    /**
     * Method for extracting String path format from Uri format.
     *
     * @param context    - application context
     * @param contentUri - file's destination in Uri format
     * @return - String object with the file path.
     */

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        String columnIndex = null;

        String[] proj = {MediaStore.Images.Media.DATA};
        cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        columnIndex = cursor.getString(column_index);

        if (cursor != null) {
            cursor.close();
        }
        return columnIndex;
    }

    /**
     * Rename file.
     *
     * @param currentName   - String -current file name
     * @param newName       - String -  new file name
     * @param fileDirectory - File -  file's directory
     * @return - String - new files path.
     */
    public String renameFile(String currentName, String newName, File fileDirectory) {
        String extension = getFileExtension(currentName);
        File currentFile = null;
        File newFile = null;

        String newFilePath = null;

        if (fileDirectory != null && fileDirectory.exists()) {
            currentFile = new File(fileDirectory, currentName + extension);
            newFile = new File(fileDirectory, newName + extension);

            if (currentFile.exists()) {
                if (currentFile.renameTo(newFile)) {
                    newFilePath = currentFile.toString();
                }
            }
        }

        return newFilePath;
    }

    /**
     * Rename user directory. If the directory exists rename it to the new name.
     * If it does not create new directory with provided new name.
     *
     * @param currentName   - current name of the directory
     * @param newName       - new directory name.
     * @param rootDirectory - the root directory of the application.
     * @return - File type object with the new directory.
     */
    public File renameDirectory(String currentName, String newName, File rootDirectory) {
        File currentDir = new File(rootDirectory, currentName);
        File newDir = new File(rootDirectory, newName);
        if (currentDir.exists()) {
            currentDir.renameTo(newDir);
        } else {
            newDir.mkdirs();
        }
        return newDir;
    }

    /**
     * Check if file exists
     *
     * @param filePath - file's location
     * @return - true if the file exists, false otherwise.
     */
    public boolean checkFileExist(String filePath) {
        boolean exists = false;
        File file = new File(filePath);

        if (file.exists()) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }

    /**
     * Delete directory by by provided path.
     *
     * @param path - directory location
     * @return - true if deletion was success, false otherwise.
     */
    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    /**
     * Method for check if a directory has files
     *
     * @param path - the directory location
     * @return - true if there are files in the directory, false otherwise
     */
    public boolean hasFiles(File path) {
        boolean hasFiles = false;
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                hasFiles = false;

            } else if (files.length > 0) {
                hasFiles = true;
            }
        }
        return hasFiles;
    }


    /**
     * Method for deleting a file.
     *
     * @param filePath - file's location
     * @return - true if the file was successfully deleted.
     */
    public static boolean deleteFile(String filePath) {
        boolean exists = false;
        boolean isDeleted = false;
        File file = new File(filePath);
        if (file.exists()) {
            isDeleted = file.delete();
        }
        return isDeleted;
    }
}
