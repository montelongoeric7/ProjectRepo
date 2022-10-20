package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Helpers {

    public static boolean checkInit() {
        return GITLET_DIR.exists();
    }

    public static void createBlob(String fileId, Serializable addFile) {
        File blob = join(BLOBS, fileId);
        writeContents(blob, addFile);
    }

    public static String createId(byte[] addFile) {
        return sha1((Object) addFile);
    }

    public static String checkCommit(String filename) {

        String head = getHead();

        File curr = join(COMMIT, head);
        Commit commit = readObject(curr, Commit.class);

        HashMap<String, String> files = commit.files;
//        System.out.println("checking commit files: " + files);
        return files.get(filename);

    }


    public static String getHead() {
        Staging x = readObject(BRANCH, Staging.class);
//        System.out.println("head commitId: " + x.filenames.get(x.branch));
        return x.filenames.get(x.branch);
    }

    public static String getBranch() {
        return readObject(BRANCH, Staging.class).branch;
    }

    public static void setHead(String branch, String commitId) {
        Staging x = readObject(BRANCH, Staging.class);
        x.branch = branch;
        x.filenames.put(branch, commitId);
        writeObject(BRANCH, x);
    }

    public static String checkStaging(String filename) {
        Staging addfile = readObject(ADDFILE, Staging.class);

        HashMap<String, String> files = addfile.filenames;
//        System.out.println("checking staging: " + files);
        return files.get(filename);

    }

    public static void addStaging(String filename, String blobId) {
        Staging addfile = readObject(ADDFILE, Staging.class);
        HashMap<String, String> files = addfile.filenames;
        List<String> remove = addfile.removals;


        remove.remove(filename);


        files.put(filename, blobId);

        writeObject(ADDFILE, addfile);
//        System.out.println("added to staging: " + files);
    }

    public static void rmStaging(String filename) {
        Staging addfile = readObject(ADDFILE, Staging.class);
        HashMap<String, String> files = addfile.filenames;

//        System.out.println("this is files rm" + files);
        files.remove(filename);
    }

    public static String createCommit(Commit c) {
        String filename = sha1((Object) serialize(c));
        File com = join(COMMIT, filename);
        try {
            com.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeObject(com, c);

        return filename;
    }

    public static void rmADDFILE() {
        Staging x = readObject(ADDFILE, Staging.class);
        x.filenames = new HashMap<>();
//        System.out.println("additions hashmap dropped.");
        writeObject(ADDFILE, x);
    }


}
