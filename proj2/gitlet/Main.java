package gitlet;

import java.util.Objects;

import static gitlet.Commands.*;
import static gitlet.Helpers.*;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Eric Montelongo, Amruth Uppaluri
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     * java gitlet.Main add hello.txt
     */
    public static void main(String... args) {

        len = args.length;
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }
        String firstArg = args[0];
        boolean repoExists = checkInit();

        switch (Objects.requireNonNull(firstArg)) {
            case "init" -> {

                init();
            }
            case "add" -> {

                add(args[1]);
            }
            case "commit" -> {

                commit(args[1]);
            }

            case "checkout" -> {
                if (!repoExists) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                if (args.length > 4) {
                    System.out.println("Incorrect operands.");
                    return;
                }
                if (args.length == 2) {
                    checkout(args[1]);
                }
                if (args.length == 3) {
                    checkout(args[1], args[2]);
                }
                if (args.length == 4) {
                    checkout(args[1], args[2], args[3]);
                }

            }

            case "log" -> {
                log();
            }
            case "rm" -> {

                rm(args[1]);
            }

            case "global-log" -> {

                globalLog();
            }
            case "find" -> {
                if (!repoExists) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                if (args.length > 2) {
                    System.out.println("Incorrect operands.");
                }
                find(args[1]);
            }
            case "status" -> {

                status();
            }

            case "branch" -> {

                branch(args[1]);
            }

            case "rm-branch" -> {

                rmBranch(args[1]);
            }
            case "reset" -> {

                reset(args[1]);
            }
            case "merge" -> {

                merge(args[1]);
            }
            default -> System.out.println("No command with that name exists.");
        }

    }
}

