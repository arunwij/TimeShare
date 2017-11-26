/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeshare.allocator;

/**
 *
 * @author Sanjula
 */
public class VariableBroadcast {

    public synchronized static String vString(String org, int peers) {
        /*String[] values = new String[peers];
        for (int i = 0; i < peers; i++) {
            values[i] = org;
        }*/

        return  org;
    }

    public synchronized static int vInt(int org, int peers) {
        /*int[] values = new int[peers];
        for (int i = 0; i < peers; i++) {
            values[i] = org;
        }*/

        return org;
    }

    public synchronized static double vDouble(double org, int peers) {
        /*double[] values = new double[peers];
        for (int i = 0; i < peers; i++) {
            values[i] = org;
        }*/

        return org;
    }

    public synchronized static float vFloat(float org, int peers) {
        /*float[] values = new float[peers];
        for (int i = 0; i < peers; i++) {
            values[i] = org;
        }*/

        return org;
    }

    public synchronized static Object vObject(Object org, int peers) {
        /*Object[] values = new Object[peers];
        for (int i = 0; i < peers; i++) {
            values[i] = org;
        }*/

        return org;
    }

}
