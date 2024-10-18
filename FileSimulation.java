import java.util.*;

public class FileSimulation {
    public static void main (String[] args) {
        Node node = new Node("COSC 439", "Folder", "Folder").addChild(new Node("Homework", "Folder", "Folder")).addChild(new Node("Syllabus", "File", "PDF"));

        Scanner scanner = new Scanner(System.in);

        boolean cont = true;

        Node currentNode = node;

        while (cont) {
            System.out.print("root: ");
            String input = scanner.nextLine();
            String arr[] = input.split(" ", 2);  //array with first word and second (ex. 'cd' and 'Homework')
            String first = arr[0];
            String second = null;
            if (arr.length > 1) {
                second = arr[1];
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
                        Node nextNode = currentNode.findChildByName(second);
                        if ((nextNode != null) && ((nextNode.use).equals("Folder"))) {
                            currentNode = nextNode;
                        } else {
                            System.out.println("Directory not found.");
                        }
                    } else {
                        System.out.println("No directory specified.");
                    } 
                    break;
                case "file":
                    if (arr.length > 1) {
                        Node fileNode = currentNode.findChildByName(second);
                        if (fileNode != null) {
                            System.out.println("This is a " + fileNode.type);
                        } else {
                            System.out.println("Name not found.");
                        }
                    } else {
                        System.out.println("No name specified.");
                    } 
                    break;
                case "rm":
                    break;
                case "rmdir":
                    break;
                case "help":
                    break;
            }
        }
    }
}

class Node {
    public String name;
    public String use;
    public String type;
    public List<Node> children;
    public Node parent;

    public Node (String name, String use, String type) {
        children = new ArrayList<>();
        this.name = name;
        this.use = use;
        this.type = type;
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
}