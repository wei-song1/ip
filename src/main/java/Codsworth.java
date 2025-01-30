import java.util.Scanner;

public class Codsworth {
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;

    public Codsworth(String filePath) {
        storage = new Storage(filePath);
        taskList = Storage.initialiseAndLoadTaskList();
        parser = new Parser(taskList, storage);
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        while (!parser.isBye()) {
            String strInput = sc.nextLine().trim();
            parser.parse(strInput);
        }

        sc.close();
    }

    public static void main(String[] args) {
        new Codsworth("codsworth.txt").run();
    }
}
