import java.util.*;

public class FileSimulation {
    public static void main (String[] args) {
        Node node = new Node("COSC_439", "Folder").addChild(new Node("Homework", "Folder")).addChild(new Node("Syllabus.pdf", "File"));

        Scanner scanner = new Scanner(System.in);

        boolean cont = true;

        Node currentNode = node;

        while (cont) {
            System.out.print("root:");
            currentNode.printWhere(currentNode);
            System.out.print("# ");
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
                        if (second.equals("..")) {     //user inputs "cd .." to move to parent directory
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
                case "help":
                    System.out.println("");
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' for list of commands");
                    break;
            }
        }
    }
}

class Node {
    public String name;
    public String use;
    public List<Node> children;
    public Node parent;

    public Node (String name, String use) {
        children = new ArrayList<>();
        this.name = name;
        this.use = use;
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