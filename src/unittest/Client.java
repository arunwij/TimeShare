/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittest;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Artista
 */
public class Client {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Socket socket = new Socket("192.168.1.20", 15123);

        List<File> files = new <File>ArrayList();
        File a = new File("city.jpg");
        File b = new File("night.jpg");
        File c = new File("desert.jpg");
        File d = new File("flower.jpg");
        File e = new File("Tharumini.mp3");
        files.add(a);
        files.add(b);
        files.add(c);
        files.add(d);
        files.add(e);

        ListIterator li = files.listIterator();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(files.size());
        while (li.hasNext()) {
            File file = (File) li.next();
            byte[] nameInBytes = file.getName().getBytes("UTF-8");
            File transferFile = new File(file.getName());
            byte[] contents = new byte[(int) transferFile.length()];
            FileInputStream fin = new FileInputStream(transferFile);
            BufferedInputStream bin = new BufferedInputStream(fin);
            bin.read(contents, 0, contents.length);

            dos.writeInt(nameInBytes.length);
            dos.write(nameInBytes);
            dos.writeInt(contents.length);
            dos.write(contents);
        }
        dos.flush();
        socket.close();
        System.out.println("File transfer complete");
    }
}
