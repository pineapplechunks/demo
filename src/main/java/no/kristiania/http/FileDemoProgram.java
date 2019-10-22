package no.kristiania.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileDemoProgram {
    private String filename;
    public FileDemoProgram(String filename) {
        this.filename = filename;
    }

    public static void main(String[] args) throws IOException {
        FileDemoProgram program = new FileDemoProgram("testfile.txt");
        program.read();
        program.readFirstLine();
    }
    private void read() throws IOException {
        try (InputStream inputStream = new FileInputStream(filename)) {
            int c;

            while ((c = inputStream.read()) != -1) {
                System.out.print((char) c);
            }
        }
    }

    private void readFirstLine() throws IOException {
        try (InputStream inputStream = new FileInputStream(filename)) {
            StringBuilder line = new StringBuilder();

            int c;
            while ((c = inputStream.read()) != -1) {
                if (c == '\r' || c == '\n') {
                    break;
                }
                line.append((char)c);
            }
            System.out.println(line);
        }
    }
}
