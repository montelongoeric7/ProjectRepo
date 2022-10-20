package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.*;
import static gitlet.Repository.*;
import static gitlet.Helpers.*;


/**
 * Commands executed from the main method.
 *
 * @author Amruth Uppaluri and Eric Montelongo
 */
public class Commands {

    public static void init() {

//        Initializes a new gitlet repository, including the initial commit.

        //creating scaffolding for gitlet repo
        GITLET_DIR.mkdir();
        STAGE.mkdir();

        COMMIT.mkdir();
        BLOBS.mkdir();

        try {
            ADDFILE.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Staging a = new Staging();
        writeObject(ADDFILE, a);

        try {
            BRANCH.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Staging b = new Staging();
        writeObject(BRANCH, b);

        //creating new Commit object
        Commit c = new Commit();

        String head = createCommit(c);
        setHead("main", head);


//
//        String filename = sha1((Object) serialize(c));
//        File first = join(COMMIT, filename);
//        first.createNewFile();
//        writeObject(first, c);

//        Commit test = readObject(first, Commit.class);
//        System.out.println("this is the initial message: " + test.message);
//        System.out.println("this is the initial date: " + test.ts);

    }

    public static void add(String filename) {
        File filePath = join(CWD, filename);

        //if provided filename isn't within CWD, return error.
        if (!filePath.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        byte[] addFile = readContents(filePath);
//        System.out.println("this is contents: " + readContentsAsString(filePath));

        String blobId = createId(addFile);
        String comId = checkCommit(filename);

        //if the file is in the commit, compare with current file.
        //if it is equal, remove from staging area.
        if (comId != null) {
            if (comId.equals(blobId)) {
                rmStaging(filename);
                return;
            }
        }

        createBlob(blobId, addFile);
        addStaging(filename, blobId);
        Staging x = readObject(ADDFILE, Staging.class);
        List<String> y = new ArrayList<>();
        x.removals = y;

        writeObject(ADDFILE, x);

    }

    public static void commit(String message) {
        //create new commit object with files in staging area.
        //update the head pointer to new commit object.

        if (message.trim().equals("")) {
            System.out.println("Please enter a commit message.");
        }

        Staging x = readObject(ADDFILE, Staging.class);
        if (x.filenames.isEmpty() && x.removals.isEmpty()) {
            System.out.println("No changes added to the commit.");
        }

        Commit c = new Commit(getHead(), message);
        c.files = readObject(ADDFILE, Staging.class).filenames;

        String filename = createCommit(c);

        setHead(getBranch(), filename);
        rmADDFILE();
//
//        System.out.println("head commit: " + getHead());
//        Commit x = readObject(join(COMMIT,filename), Commit.class);
//
//        System.out.println("commit message: " + x.message );
//        System.out.println("commit timestamp" + x.ts);
//        System.out.println("commit parent" + x.parent);
    }


    public static void checkout(String branchName) {

        Staging branch = readObject(BRANCH, Staging.class);

        if (branch.branch.equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }

        String commitId = branch.filenames.get(branchName);
        if (commitId == null) {
            System.out.println("No such branch exists.");
            return;
        }

        Commit newBranchCom = readObject(join(COMMIT, commitId), Commit.class);
        Commit currBranchCom = readObject(join(COMMIT, getHead()), Commit.class);
        Set<String> allFiles = new HashSet<>(newBranchCom.files.keySet());
        allFiles.addAll(currBranchCom.files.keySet());

        for (String filename : allFiles.stream().toList()) {
            if (newBranchCom.files.containsKey(filename)
                    && !currBranchCom.files.containsKey(filename)) {
                System.out.println(
                        "There is an untracked file in the way; delete it," +
                                " or add and commit it first.");
                return;
            }
        }

        for (String filename : allFiles.stream().toList()) {
            File x = join(CWD, filename);
            x.delete();
            if (currBranchCom.files.containsKey(filename)
                    && !newBranchCom.files.containsKey(filename)) {
                return;
            }

            try {
                x.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeContents(x, (Object) readContents(join(BLOBS, newBranchCom.files.get(filename))));
        }

        branch.branch = branchName;

        writeObject(BRANCH, branch);

    }

    public static void checkout(String arg1, String filename) {

        if (!arg1.equals("--")) {
            System.out.println("Incorrect operands.");

        }

        File com = join(COMMIT, getHead());
        if (!com.exists()) {
            System.out.println("No commit with that id exists.");
        }

        HashMap<String, String> x = readObject(com, Commit.class).files;
        String blobId = x.get(filename);

        if (blobId == null) {
            System.out.println("File does not exist in that commit.");
        }

        File filepath = join(CWD, filename);
        filepath.delete();
        try {
            filepath.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] y = readContents(join(BLOBS, blobId));
        writeContents(filepath, (Object) y);

    }

    public static void checkout(String commitId, String arg2, String filename) {

        if (!arg2.equals("--")) {
            System.out.println("Incorrect operands.");

        }

        File com = join(COMMIT, commitId);
        if (!com.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }

        HashMap<String, String> x = readObject(com, Commit.class).files;
        String blobId = x.get(filename);

        if (blobId == null) {
            System.out.println("File does not exist in that commit.");
            return;
        }

        File filepath = join(CWD, filename);
        filepath.delete();
        try {
            filepath.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] y = readContents(join(BLOBS, blobId));
        writeContents(filepath, (Object) y);

    }

    public static void log() {

        String head = getHead();
        Commit x = readObject(join(COMMIT, head), Commit.class);

        while (x.parent != null) {
            System.out.println("===");
            System.out.println("commit " + head);
            System.out.println("Date: " + DATE_FORMAT.format(x.ts));
            System.out.println(x.message);
            System.out.println();

            head = x.parent;
            x = readObject(join(COMMIT, head), Commit.class);
        }

        System.out.println("===");
        System.out.println("commit " + head);
        System.out.println("Date: " + DATE_FORMAT.format(x.ts));
        System.out.println(x.message);
        System.out.println();


    }

    public static void rm(String filename) {

        Staging staging = readObject(ADDFILE, Staging.class);
        HashMap<String, String> commit = readObject(join(COMMIT, getHead()), Commit.class).files;

        if ((!staging.filenames.containsKey(filename))
                && (!staging.removals.contains(filename))
                && (!commit.containsKey(filename))) {
            System.out.println("No reason to remove the file.");
            return;
        }

        staging.filenames.remove(filename);

        if (commit.containsKey(filename)) {
            restrictedDelete(join(CWD, filename));
            staging.removals.add(filename);
        }

        writeObject(ADDFILE, staging);

    }

    public static void globalLog() {
        List<String> commits = plainFilenamesIn(COMMIT);

        assert commits != null;
        for (String el : commits) {
            if (el.equals("branch")) {
                continue;
            }
            Commit x = readObject(join(COMMIT, el), Commit.class);
            System.out.println("===");
            System.out.println("commit " + el);
            System.out.println("Date: " + DATE_FORMAT.format(x.ts));
            System.out.println(x.message);
            System.out.println();

        }
    }

    public static void find(String message) {
        List<String> commits = plainFilenamesIn(COMMIT);

        assert commits != null;
        boolean found = false;
        for (String el : commits) {
            if (el.equals("branch")) {
                continue;
            }

            Commit x = readObject(join(COMMIT, el), Commit.class);
            if (x.message.equals(message)) {
                System.out.println(el);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void status() {
        System.out.println("=== Branches ===");
        Staging x = readObject(BRANCH, Staging.class);

        for (String branch : x.filenames.keySet().stream().sorted().toList()) {
            if (branch.equals(x.branch)) {
                System.out.print("*");
            }
            System.out.println(branch);
        }
        System.out.println("\n=== Staged Files ===");
        Staging y = readObject(ADDFILE, Staging.class);
        for (String file : y.filenames.keySet().stream().sorted().toList()) {
            System.out.println(file);
        }
        System.out.println("\n=== Removed Files ===");
        for (String rem : y.removals.stream().sorted().toList()) {
            System.out.println(rem);
        }
        System.out.println("\n=== Modifications Not Staged For Commit ===");

        System.out.println("\n=== Untracked Files ===");


    }

    public static void branch(String branchName) {
        Staging x = readObject(BRANCH, Staging.class);

        if (x.filenames.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
        }
        x.filenames.put(branchName, getHead());
        writeObject(BRANCH, x);
    }

    public static void rmBranch(String branchName) {
        Staging x = readObject(BRANCH, Staging.class);
        if (!x.filenames.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
        }

        if (x.branch.equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }

        x.filenames.remove(branchName);
        writeObject(BRANCH, x);
    }

    public static void reset(String commitId) {
        File filepath = join(COMMIT, commitId);

        if (!filepath.exists()) {
            System.out.println("No commit with that id exists.");
        }

        Commit x = readObject(filepath, Commit.class);

//        for (String file : x.files.keySet().stream().toList()) {
//            if (!readObject(join(COMMIT,getHead()), Commit.class).files.containsKey(file)) {
//                System.out.println(
//                        "There is an untracked file in the way;" +
//                                " delete it, or add and commit it first.");
//                return;
//            }
//        }
        for (String file : x.files.keySet().stream().toList()) {
            File y = join(CWD, file);
            y.delete();
            try {
                y.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeContents(y, (Object) readContents(join(BLOBS, x.files.get(file))));
        }

        setHead(getBranch(), commitId);

    }

    public static void merge(String branchName) {

    }
}
