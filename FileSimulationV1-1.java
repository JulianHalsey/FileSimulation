import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSimulation {
    public static void main(String[] args) {
        Node node = new Node("COSC_439", "Folder").addChild(new Node("Homework", "Folder"))
                .addChild(new Node("Syllabus.pdf", "File"));

        Scanner scanner = new Scanner(System.in);

        boolean cont = true;

        Node currentNode = node;

        while (cont) {
            System.out.print("root:");
            currentNode.printWhere(currentNode);
            System.out.print("# ");
            String input = scanner.nextLine();
            String first;
            String second = "";
            String arr[];
            Pattern special = Pattern.compile("[']");
            Matcher hasquote = special.matcher(input);
            Pattern wFile = Pattern.compile("[>]");
            Matcher hasArrow;

            if (hasquote.find()) {
                arr = input.split(" ");
                first = arr[0];
                second = "";
                for (int i = 1; i < arr.length; i++) {
                    second += arr[i] + " ";
                    hasArrow = wFile.matcher(second);
                    if (hasArrow.find()) {
                        while (arr[i + 1] == null || arr[i + 1] == "")
                            i++;
                        String f = arr[i + 1];
                        second = second.replace(">", "");
                        echoInf(f, currentNode, second);

                        second = f;
                        break;
                    }
                }
                // scanner.nextLine();

            } else {
                arr = input.split(" ", 2); // array with first word and second (ex. 'cd' and 'Homework')
                first = arr[0];
                second = null;
                if (arr.length > 1) {
                    second = arr[1];
                }
            }

            switch (first) {
                case "quit":
                    cont = false;
                    break;
                case "ls":
                    currentNode.printChildren();
                    break;
                case "cd":
                    if (arr.length > 1) {
                        if (second.equals("..")) { // user inputs "cd .." to move to parent directory
                            Node nextNode = currentNode.parent;
                            if (nextNode != null) {
                                currentNode = nextNode;
                            }
                        } else {
                            Node nextNode = currentNode.findChildByName(second);
                            if ((nextNode != null) && ((nextNode.use).equals("Folder"))) {
                                currentNode = nextNode;
                            } else {
                                System.out.println("Directory not found");
                            }
                        }
                    } else {
                        System.out.println("No directory specified");
                    }
                    break;
                case "rm":
                    if (arr.length > 1) {
                        Node delNode = currentNode.findChildByName(second);
                        if ((delNode != null) && !((delNode.use).equals("Folder"))) {
                            currentNode.removeNode(delNode);
                        } else {
                            System.out.println("File not found or not file");
                        }
                    }
                    break;
                case "rmdir":
                    if (arr.length > 1) {
                        Node delNode = currentNode.findChildByName(second);
                        if ((delNode != null) && !((delNode.use).equals("File"))) {
                            currentNode.removeNode(delNode);
                        } else {
                            System.out.println("Folder not found or not folder");
                        }
                    }
                    break;
                case "pwd":
                    currentNode.printWhere(currentNode);
                    System.out.println();
                    break;
                case "touch":
                    currentNode.addChild(new Node(second, "File"));
                    break;
                case "mkdir":
                    currentNode.addChild(new Node(second, "Folder"));
                    break;
                case "echo":
                    System.out.println(second);
                    break;
                case "head":
                    System.out.println(currentNode.findChildByName(second).returnInfo());
                    break;
                case "help":
                    if (second == null)
                        helpFunct();
                    else
                        helpFunctComm(second);
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' for list of commands");
                    break;
            }
        }
    }

    public static void helpFunct() {

        System.out.println(
                "Here are the commands you may use: \n \nquit \nls \ncd \nrm \nrmdir \npwd \ntouch \nmkdir \necho \nhead \nhelp");
        System.out.println("\nUse 'help' followed by the command for more information on its use.");

    }

    public static void helpFunctComm(String sec) {

        switch (sec) {
            case "quit":
                System.out.println("Exits the simulation. ");
                break;
            case "ls":
                System.out.println("Shows the contents of current directory (folder). ");
                break;
            case "cd":
                System.out.println(
                        "Changes directory (folder) being operated in. Follow it with '..' in order to return to previous directory. ");
                break;
            case "rm":
                System.out.println(
                        "Removes a file. Follow it with a file name in order to remove it from current directory (folder). ");
                break;
            case "rmdir":
                System.out.println(
                        "Removes a directory (folder). Follow it with a folder name to remove it from current directory. ");
                break;
            case "pwd":
                System.out.println("Shows current directory (folder). ");
                break;
            case "touch":
                System.out.println(
                        "Creates a file. Follow it with a file name in order to add it to current directory (folder). ");
                break;
            case "mkdir":
                System.out.println(
                        "Creates a directory (folder). Follow it with a directory name in order to add it to current directory. ");
                break;
            case "help":
                System.out.println("Shows currently implemented and usable commands.");
                break;
            case "echo":
                System.out.println(
                        "Prints the phrase after the command to the screen. Use an apostrophe (') before the phrase and then follow phrase with a right arrow (>) followed by a file/directory (folder) name in order to add a small message to it.");
                break;
            case "head":
                System.out.println("Prints the messsage attached to a node/directory (folder).");
                break;
            default:
                System.out.println("Unknown command. Type 'help' for list of commands");
                break;
        }

    }

    public static void echoInf(String fileName, Node cNode, String inf) {

        Node n = cNode.findChildByName(fileName);
        n.info = inf;

    }
}

class Node {
    public String name;
    public String use;
    public String info;
    public List<Node> children;
    public Node parent;

    public Node(String name, String use) {
        children = new ArrayList<>();
        this.name = name;
        this.use = use;
        this.info = "";
    }

    public Node addChild(Node node) {
        children.add(node);
        node.parent = this;
        return this;
    }

    public void printChildren() {
        if (children.isEmpty()) {
            System.out.println("<Empty folder>");
        } else {
            for (Node child : children) {
                System.out.print(child.name + " ");
            }
            System.out.println();
        }
    }

    public Node findChildByName(String name) {
        for (Node child : children) {
            if (((child.name).equals(name))) {
                return child;
            }
        }
        return null;
    }

    public void removeNode(Node node) {
        children.remove(node);
    }

    public String returnInfo() {
        return this.info;
    }

    public void printWhere(Node node) {
        ArrayList<String> parents = new ArrayList<>();
        Node parentN = node;
        while (parentN != null) {
            parents.add(parentN.name);
            parentN = parentN.parent;
        }
        for (int i = parents.size() - 1; i >= 0; i--) {
            System.out.print(parents.get(i) + "/");
        }

    }

}
