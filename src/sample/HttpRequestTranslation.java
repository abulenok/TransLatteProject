package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import static java.net.URLEncoder.encode;


@SuppressWarnings("deprecation")
public class HttpRequestTranslation {

    private static HttpURLConnection con;

    public static Vector<String> translateHttp( String wordToTranslate, String langTranslateFrom, String langTranslateTo) throws IOException {

        //long startTime = System.nanoTime();

        //System.out.println("StartHttpRequest");

        String urlEncodedString = encode(wordToTranslate);

        String url = "https://glosbe.com/" + langTranslateFrom + "/" + langTranslateTo + "/" + urlEncodedString;

        try {
            //long startTime1 = System.nanoTime();
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {

                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            //long endTime1 = System.nanoTime();

            //long duration1 = (endTime1 - startTime1);
            //System.out.println(duration1);

            return findWordsInWebPage(langTranslateTo, content);

        } finally {
            //long endTime = System.nanoTime();

            //long duration = (endTime - startTime);
            //System.out.println(duration);

            con.disconnect();
        }
    }
    private static Vector<String> findWordsInWebPage(String langTranslateTo, StringBuilder content){
        Vector<String> words = new Vector<>();
        String pattern = "class=\" phr\">";

        if(langTranslateTo.equals("ar")){
            pattern = "<strong class=\"nobold phr\">";
        }

        int index = 0, wordEnd;
        int wordBeginning = content.indexOf(pattern, index);

        while(wordBeginning!=-1){
            wordBeginning += pattern.length();
            wordEnd = wordBeginning + 1;
            while (content.charAt(wordEnd) != '<') {
                wordEnd++;
            }
            words.add(content.substring(wordBeginning, wordEnd));
            index = wordEnd+1;

            wordBeginning = content.indexOf(pattern, index);
        }
        return words;
    }

}