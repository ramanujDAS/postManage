import io.micronaut.runtime.Micronaut;
import pdf.PdfTextDecompressor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Application {



    public static void main(String[] args)  {


       String comp = "%����";
        byte [] byteStr = PdfTextDecompressor.decompressFlate(comp.getBytes());

        System.out.println(new String(byteStr, StandardCharsets.UTF_8));
         Micronaut.run(Application.class, args);
    }
}