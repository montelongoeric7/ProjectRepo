package gitlet;

import java.io.File;
import java.text.SimpleDateFormat;

import static gitlet.Utils.*;

/**
 * Represents a gitlet repository.
 * This class creates the gitlet repository and manages file creation and deletion of the gitlet repo.
 *
 * @author Eric Montelongo / Amruth Uppaluri
 */
public class Repository {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "EEE MMM d HH:mm:ss yyyy Z");
    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /**
     * The Staging area directory
     */
    public static final File STAGE = join(GITLET_DIR, "staging");
    /**
     * The additions file within staging
     */
    public static final File ADDFILE = join(STAGE, "add");
    /**
     * The commits dir.
     */
    public static final File COMMIT = join(GITLET_DIR, "commits");
    /**
     * The blobs directory.
     */
    public static final File BLOBS = join(GITLET_DIR, "blobs");
    /**
     * The current branch, pointing towards the current file.
     */
    public static final File BRANCH = join(COMMIT, "branch");

}