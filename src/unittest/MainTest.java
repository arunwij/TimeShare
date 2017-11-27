/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kademlia.file.FileSender;
import kademlia.node.Node;
import timeshare.RunningConfiguration;

/**
 *
 * @author Artista
 */
public class MainTest {
    public static void main(String[] args) throws IOException{
        RunningConfiguration.run();
        
        List<File> files = new <File>ArrayList();
        
        File a = new File("pics/city.jpg");
        File b = new File("pics/desert.jpg");
        File c = new File("pics/flower.jpg");
        File d = new File("pics/night.jpg");
        
        List<Node> nodes = RunningConfiguration.getNodeList();
        ListIterator li = nodes.listIterator();
        
        while(li.hasNext()){
            Node n = (Node)li.next();
            FileSender.send(n, a, "/test");
        }
        
    }
}
