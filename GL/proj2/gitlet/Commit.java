package gitlet;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import static gitlet.Utils.*;
import static gitlet.Repository.*;


/**
 * Represents a gitlet commit object.
 * Includes a message string, timestamp date, parent string, and files hashmap.
 *
 * @author Amruth Uppaluri / Eric Montelongo
 */
public class Commit implements Serializable {

    /**
     * The message of this Commit.
     */
    protected String message;
    /**
     * Timestamp of commit
     */
    protected Date ts;
    /**
     * Parent commit object, referenced via filename.
     */
    protected String parent;
    /**
     * Filename to blobId
     */
    protected HashMap<String, String> files;

    Commit() {
        this.message = "initial commit";

        long d = 0L;
        this.ts = new Date(d);

        this.parent = null;
        this.files = new HashMap<>();

    }

    Commit(String parent, String message) {
        this.ts = new Date();
        this.parent = parent;
        this.message = message;

        Commit x = readObject(join(COMMIT, parent), Commit.class);
        this.files = x.files;
    }


}
