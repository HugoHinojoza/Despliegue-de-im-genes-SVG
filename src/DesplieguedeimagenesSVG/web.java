package DesplieguedeimagenesSVG;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;
import java.util.Scanner;

public class web extends Object{
    static final String CLASS_NAME = web.class.getSimpleName();
    static final Logger LOG = Logger.getLogger(CLASS_NAME);


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        if (args.length != 1) {
            LOG.severe("Ingrese una URL correcta");
            System.exit(1);
        }
        String url = args[0];
        System.out.println("Ingresando a " + url +"...");
        System.out.println("Ingrese el archivo a descargar con su respectiva terminacion:");
        String archivo = sc.nextLine();
        System.out.println("Buscando a " + archivo +"...");
        //Directorio destino para las descargas
        String carpeta = "C:\\Users\\hugoa\\IdeaProjects\\Despliegue de iamgenes svg";
        File dir = new File(carpeta);
        File file = new File(carpeta + archivo);

        try {
            URLConnection log = new URL(url).openConnection();
            log.connect();

            InputStream in = log.getInputStream();
            OutputStream out = new FileOutputStream(archivo);

            int b = 0;
            while (b != -1) {
                b = in.read();
                if (b != -1)
                    out.write(b);
            }

            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Abriendo el svg...");
            String directorio = "C:\\Users\\hugoa\\IdeaProjects\\Despliegue de iamgenes svg\\"+archivo;
            ProcessBuilder abre = new ProcessBuilder();
            abre.command("cmd.exe", "/c", directorio);
            abre.start();
        } catch (IOException ex) {

        }
    }

    protected web (URL url) {
    }
}