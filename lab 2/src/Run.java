import domain.PScanner;

public class Run {

    public static void main(String[] args) {
        System.out.println("Running the Scanner on p1");
        PScanner scannerP1 = new PScanner("src/io/p1.txt",
                "src/io/pif1.txt",
                "src/io/st1.txt");
        scannerP1.run();

        System.out.println("Running the Scanner on p2");
        PScanner scannerP2 = new PScanner("src/io/p2.txt",
                "src/io/pif2.txt",
                "src/io/st2.txt");
        scannerP2.run();

        System.out.println("Running the Scanner on p3");
        PScanner scannerP3 = new PScanner("src/io/p3.txt",
                "src/io/pif3.txt",
                "src/io/st3.txt");
        scannerP3.run();

        System.out.println("Running the Scanner on p1err");
        PScanner scannerP1err = new PScanner("src/io/p1err.txt",
                "src/io/pif1err.txt",
                "src/io/st1err.txt");
        scannerP1err.run();
    }
}