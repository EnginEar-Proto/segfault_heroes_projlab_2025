import java.io.*;

/**
 * A bemenetek és kimenetek kezelésére szolgáló osztály,
 * amely képes adatokat olvasni konzolról vagy fájlból, illetve írni konzolra vagy fájlba.
 */
public class IOHandler {
    private BufferedReader reader;
    private BufferedWriter writer;

    /**
     * Létrehozza az InputHandler példányát a megadott forrás és cél alapján.
     *
     * @param inputSource A bemeneti forrás neve. Ha "console", akkor a konzolról olvas, különben fájlból.
     * @param outputTarget A kimeneti cél neve. Ha "console", akkor a konzolra ír, különben fájlba.
     * @throws IOException ha a fájl megnyitása vagy a beolvasás/kiírás sikertelen.
     */
    public IOHandler(String inputSource, String outputTarget) throws IOException {
        // Input forrás beállítása
        if (inputSource.equalsIgnoreCase("console")) {
            Console console = System.console();
            if (console == null) {
                reader = new BufferedReader(new InputStreamReader(System.in));
            } else {
                reader = new BufferedReader(console.reader());
            }
        } else {
            reader = new BufferedReader(new FileReader(inputSource));
        }

        // Output cél beállítása
        if (outputTarget.equalsIgnoreCase("console")) {
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
        } else {
            writer = new BufferedWriter(new FileWriter(outputTarget));
        }
    }

    /**
     * Beolvas egy sort a bemeneti forrásból.
     *
     * @return A beolvasott sor szövegként, vagy null, ha a bemenet véget ért.
     * @throws IOException ha a beolvasás során hiba lép fel.
     */
    public String readLine() throws IOException {
        return reader.readLine();
    }

    /**
     * Kiír egy sort a kimeneti célra, majd sortörést ír.
     *
     * @param line A kiírandó szöveg.
     * @throws IOException ha az írás során hiba történik.
     */
    public void writeLine(String line) throws IOException {
        try {
            writer.write(line);
            writer.newLine();
            writer.flush();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Lezárja a bemeneti és kimeneti olvasót/írót, felszabadítva az erőforrásokat.
     *
     * @throws IOException ha a lezárás során hiba történik.
     */
    public void close() throws IOException {
        if (reader != null) reader.close();
        if (writer != null) writer.close();
    }
}


