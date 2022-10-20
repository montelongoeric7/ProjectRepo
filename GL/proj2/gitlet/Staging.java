package gitlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Staging class which creates an object that holds organization data structures for git.
 * Includes additions Hashmap, removals List, and a head commitId string.
 *
 * @author Eric Montelongo / Amruth Uppaluri
 */
public class Staging implements Serializable {

    /**
     * lookup table of filenames to blobIds or branch-names to commitIDs
     */
    protected HashMap<String, String> filenames;
    /**
     * head of current branch
     */
    protected String branch;
    /**
     * List of files staged for removal from current commit.
     */
    protected List<String> removals;

    Staging() {
        this.filenames = new HashMap<>();
        this.branch = "main";
        this.removals = new ArrayList<>();
    }

    Staging(HashMap<String, String> files) {
        this.filenames = files;
        this.branch = "main";
        this.removals = new ArrayList<>();
    }

    Staging(String branch) {
        this.filenames = new HashMap<>();
        this.branch = branch;
        this.removals = new ArrayList<>();
    }

    Staging(List<String> rems) {
        this.removals = rems;
        this.branch = "main";
        this.filenames = new HashMap<>();
    }
}
