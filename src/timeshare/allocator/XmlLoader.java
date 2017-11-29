/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import timeshare.RunningConfiguration;
//Imports for Scheduler

/**
 *
 * @author Sanjula
 */
public class XmlLoader {

    private Document doc;
    private int peer_count = 0;
    private int args = 0;
    private Data dataToSend;
    public String methodName = "test";
    public String className = "JCudaVectorAdd";

    public Map<String, Object>[][] senddata;
    public ArrayList[] sendfiles;

    public XmlLoader(File path) {
        try {
            File fXmlFile = path;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("arg");
            NodeList peers = doc.getElementsByTagName("max_peers");
            NodeList methodname = doc.getElementsByTagName("method_name");
            NodeList classname = doc.getElementsByTagName("class_name");
            methodName=  methodname.item(0).getTextContent();
            className = classname.item(0).getTextContent();
            System.out.println("max peers :" + peers.item(0).getTextContent());
            //if(Integer.parseInt){
                 peer_count = Integer.parseInt(peers.item(0).getTextContent());
            
           
            sendfiles = new ArrayList[peer_count];
            for (int i = 0; i < peer_count; i++) {
                sendfiles[i] = new ArrayList();
            }
            System.out.println("args :" + nList.getLength());

            args = nList.getLength();

            dataToSend = new Data(nList.getLength());
            System.out.println("-------------Data-----------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                System.out.print("\nCurrent Element :" + nNode.getNodeName() + " ");
                System.out.println("no : " + eElement.getElementsByTagName("no").item(0).getTextContent());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    // System.out.println("Staff id : " + eElement.getAttribute("id"));
                    System.out.println("type : " + eElement.getElementsByTagName("type").item(0).getTextContent());
                    System.out.println("Data Type : " + eElement.getElementsByTagName("data_type").item(0).getTextContent());
                    System.out.println("Value(s) : " + eElement.getElementsByTagName("data").item(0).getTextContent());
                    String dataType = eElement.getElementsByTagName("data_type").item(0).getTextContent();
                    String arrayType = eElement.getElementsByTagName("type").item(0).getTextContent();
                    NodeList nl = doc.getElementsByTagName("data");
                    Element e = (Element) nl.item(temp);
                    System.out.println("is file :" + e.getAttribute("file"));
                    String values = null;

                    switch (e.getAttribute("file")) {
                        case "false":
                            values = eElement.getElementsByTagName("data").item(0).getTextContent();
                            break;
                        case "true":
                            CSVReader csv = new CSVReader();
                            values = csv.read(eElement.getElementsByTagName("data").item(0).getTextContent());
                            break;
                        default:
                            values = "[]";
                            break;
                    }

                    boolean assests = false;

                    if (e.getAttribute("assests").equals("true") && dataType.toLowerCase().equals("string")) {
                        assests = true;
                        System.out.println("assesrt true");
                    }

                    ///String obj = values.replace(",[", "").replace("[", "");
                    //String[] ssarr = obj.split("]");
                    // String[] syarr = ssarr[0].split(",");
                    if (eElement.getElementsByTagName("split").getLength() > 0) {
                        Node snode = eElement.getElementsByTagName("split").item(0);
                        Element sElement = (Element) snode;
                        System.out.println("split type : " + sElement.getElementsByTagName("type").item(0).getTextContent());

                        System.out.println("split count : " + sElement.getElementsByTagName("min_count").item(0).getTextContent());
                        String splitType = sElement.getElementsByTagName("type").item(0).getTextContent();
                        int splitCount = Integer.parseInt(sElement.getElementsByTagName("min_count").item(0).getTextContent());

                        // Map<String, Object> map = splitdata(values, dataType, arrayType, splitType, splitCount);
                        System.out.println(dataToSend.addData(splitdata(values, dataType, arrayType, splitType, splitCount, assests)));

                    } else if (eElement.getElementsByTagName("broadcast").getLength() > 0) {
                        System.out.println(dataToSend.addData(braodcastArray(values, dataType, arrayType, peer_count, assests)));

                    }

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XmlLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTaskCount() {
        return peer_count;
    }

    public String getDataType(String name, String type) {

        switch (type) {
            case "2d_array":
                return name + "[][]";
            case "1d_array":
                return name + "[]";
            default:
                return name;
        }

    }

    private Map<String, Object> braodcastArray(String values, String dataType, String vartype, int peerCount, boolean assests) {
        ConvertDataType cdt = new ConvertDataType();
        Map<String, Object> map = new HashMap<>();
        if (vartype.equals("2d_array")) {

            switch (dataType.toLowerCase()) {
                case "String": {
                    String[][][] dd = ArrayBroadcast.String2D((String[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    if (assests) {
                        for (int a = 0; a < peerCount; a++) {
                            for (int b = 0; b < dd.length; b++) {
                                for (int c = 0; c < dd[0].length; c++) {
                                    sendfiles[a].add(dd[a][b][c]);
                                }
                            }
                        }
                    }
                    break;
                }
                case "int": {
                    int[][][] dd = ArrayBroadcast.Int2D((int[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                case "double": {
                    double[][][] dd = ArrayBroadcast.Double2D((double[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                case "float": {
                    float[][][] dd = ArrayBroadcast.Float2D((float[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                case "boolean": {
                    boolean[][][] dd = ArrayBroadcast.boolean2D((boolean[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                default:
                    break;
            }
        } else if (vartype.equals("1d_array")) {
            //System.out.println("1d array");
            switch (dataType.toLowerCase()) {
                case "string": {
                    String[][] dd = ArrayBroadcast.String1D((String[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    System.out.println("assest split 1 " + assests + dd[0][0]);
                    if (assests) {
                        System.out.println("split assest");
                        for (int a = 0; a < dd.length; a++) {
                            for (int b = 0; b < dd[0].length; b++) {
                                sendfiles[a].add(dd[a][b]);
                            }
                        }
                    }
                    break;
                }
                case "int": {
                    int[][] dd = ArrayBroadcast.Int1D((int[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                case "double": {
                    double[][] dd = ArrayBroadcast.Double1D((double[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                case "float": {
                    float[][] dd = ArrayBroadcast.Float1D((float[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                case "boolean": {
                    boolean[][] dd = ArrayBroadcast.boolean1D((boolean[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                default:
                    break;
            }
        } else if (vartype.equals("var")) {
            switch (dataType.toLowerCase()) {
                case "String": {
                    String dd = VariableBroadcast.vString((String) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    if (assests) {
                        for (int a = 0; a < peerCount; a++) {
                            for (int b = 0; b < peer_count; b++) {

                                sendfiles[a].add(dd);

                            }
                        }
                    }
                    break;
                }
                case "int": {
                    int dd = VariableBroadcast.vInt((int) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                case "double": {
                    double dd = VariableBroadcast.vDouble((double) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                case "float": {
                    float dd = VariableBroadcast.vFloat((float) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
                default: {
                    Object dd = VariableBroadcast.vObject((Object) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                    map.put(getDataType(dataType, vartype) + "[]", dd);
                    break;
                }
            }
        }
        return map;

    }

    private Map<String, Object> splitdata(String values, String dataType, String vartype, String splitType, int peerCount, boolean assests) {
        ConvertDataType cdt = new ConvertDataType();
        Map<String, Object> map = new HashMap<>();
        if (splitType.toLowerCase().equals("col")) {

            if (dataType.equals("String") && vartype.equals("2d_array")) {
                String[][][] dd = ArraySplit.splitDataCol2D((String[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
                if (assests) {
                    for (int a = 0; a < peerCount; a++) {
                        for (int b = 0; b < dd.length; b++) {
                            for (int c = 0; c < dd[0].length; c++) {
                                sendfiles[a].add(dd[a][b][c]);
                            }
                        }
                    }
                }
            } else if (dataType.equals("int") && vartype.equals("2d_array")) {
                int[][][] dd = ArraySplit.splitDataCol2D((int[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else if (dataType.equals("double") && vartype.equals("2d_array")) {
                double[][][] dd = ArraySplit.splitDataCol2D((double[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else if (dataType.equals("float") && vartype.equals("2d_array")) {
                float[][][] dd = ArraySplit.splitDataCol2D((float[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            }
        } else if (splitType.toLowerCase().equals("row") && vartype.equals("2d_array")) {

            if (dataType.equals("String")) {
                String[][][] dd = ArraySplit.splitDataRow2D((String[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
                if (assests) {
                    for (int a = 0; a < peerCount; a++) {
                        for (int b = 0; b < dd.length; b++) {
                            for (int c = 0; c < dd[0].length; c++) {
                                sendfiles[a].add(dd[a][b][c]);
                            }
                        }
                    }
                }
            } else if (dataType.equals("int")) {
                int[][][] dd = ArraySplit.splitDataRow2D((int[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else if (dataType.equals("double")) {
                double[][][] dd = ArraySplit.splitDataRow2D((double[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else if (dataType.equals("float")) {
                float[][][] dd = ArraySplit.splitDataRow2D((float[][]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            }
        } else if (vartype.equals("1d_array")) {
            System.out.println("1d array");
            if (dataType.toLowerCase().equals("string")) {
                String[][] dd = ArraySplit.splitDataRow1D((String[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
                System.out.println("assest split 1 " + assests + dd[0][0]);
                if (assests) {
                    System.out.println("split assest");
                    for (int a = 0; a < dd.length; a++) {
                        for (int b = 0; b < dd[0].length; b++) {
                            sendfiles[a].add(dd[a][b]);
                        }
                    }
                }
            } else if (dataType.equals("int")) {
                int[][] dd = ArraySplit.splitDataRow1D((int[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else if (dataType.equals("double")) {
                double[][] dd = ArraySplit.splitDataRow1D((double[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else if (dataType.equals("float")) {
                float[][] dd = ArraySplit.splitDataRow1D((float[]) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            }
        } else if (vartype.equals("var")) {
            if (dataType.equals("String")) {
                String dd = VariableBroadcast.vString((String) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
                if (assests) {
                    for (int a = 0; a < peerCount; a++) {
                        for (int b = 0; b < peer_count; b++) {

                            sendfiles[a].add(dd);

                        }
                    }
                }
            } else if (dataType.equals("int")) {
                int dd = VariableBroadcast.vInt((int) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else if (dataType.equals("double")) {
                double dd = VariableBroadcast.vDouble((double) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else if (dataType.equals("float")) {
                float dd = VariableBroadcast.vFloat((float) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            } else {
                Object dd = VariableBroadcast.vObject((Object) cdt.conObj(values, getDataType(dataType, vartype)), peerCount);
                map.put(getDataType(dataType, vartype) + "[]", dd);
            }
        }
        return map;

    }

    public void ProcessData() throws ClassNotFoundException, IOException {

        System.out.println("process data");
        Map<String, Object>[] myobj = dataToSend.getData();
        //System.out.println(dataToSend.getDataCount());

        senddata = new Map[peer_count][dataToSend.getDataCount()];
        //Map<String, Object>[][] senddata = new Map[5][5];

        for (int cc = 0; cc < dataToSend.getDataCount(); cc++) {

            for (Map.Entry m : myobj[cc].entrySet()) {
                System.out.println("show key" + m.getKey().toString() + m.getKey().toString().contains("[][]"));
                if (m.getKey().toString().contains("[][]")) {
                    System.out.println(m.getKey());
                    Object[] obj = (Object[]) m.getValue();

                    for (int p = 0; p < obj.length; p++) {

                        Object pp = (Object) obj[p];
                        Map<String, Object> map1;
                        map1 = new HashMap<>();
                        map1.put(m.getKey().toString(), obj[p]);
                        senddata[p][cc] = map1;
                        //al.add(senddata[p][cc]);
                    }
                } else if (m.getKey().toString().contains("[][][]")) {
                    System.out.println(m.getKey());
                    Object[] obj = (Object[]) m.getValue();

                    for (int p = 0; p < obj.length; p++) {

                        Object pp = (Object) obj[p];
                        Map<String, Object> map1;
                        map1 = new HashMap<>();
                        map1.put(m.getKey().toString(), obj[p]);
                        senddata[p][cc] = map1;
                        //al.add(senddata[p][cc]);
                    }
                } else {
                    System.out.println(m.getKey());

                    for (int p = 0; p < peer_count; p++) {

                        Map<String, Object> map1;
                        map1 = new HashMap<>();
                        map1.put(m.getKey().toString(), m.getValue());
                        senddata[p][cc] = map1;
                        //al.add(senddata[p][cc]);
                    }
                }

            }

        }
        System.out.println("--------end show data-------------");

        /* 
       SendToPeer stp = new SendToPeer();
       stp.uploadJavaFile("data/javaFile/JCudaVectorAdd.java");
       
        float[][] arr = stp.send("JCudaVectorAdd", "test", senddata[0], 2);
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                System.out.print(arr[x][y] + "\t");
            }
            System.out.println("");

        }
        arr = stp.send("JCudaVectorAdd", "test", senddata[1], 2);
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                System.out.print(arr[x][y] + "\t");
            }
            System.out.println("");

        }
        Arrays.toString(arr[0]);*/
    }

}
