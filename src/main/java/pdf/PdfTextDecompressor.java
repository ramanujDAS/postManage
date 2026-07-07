package pdf;

import jakarta.inject.Singleton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;



public class PdfTextDecompressor {

    public static byte[] decompressFlate(byte[] compressedData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        InflaterInputStream iis = new InflaterInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = iis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }

            iis.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
