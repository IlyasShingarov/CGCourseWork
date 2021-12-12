package ru.ishingarov.coursework.renderer;

import javax.vecmath.Vector3d;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Model {
//    private static double[] vertices;
//    private static int[] triangles;
//    private static double[] vertexnormals;

    private ArrayList<Vector3d> vertices;
    private ArrayList<Vector3d> normals;
    private ArrayList<Face> faces;

    public Model(String filename) throws IOException {
        loadModel(filename);
    }

    public void loadModel(String filename) throws IOException {

        File objFile = new File(filename);
        FileReader fileReader = new FileReader(objFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        ArrayList<Vector3d> ALVertices = new ArrayList<>();
        ArrayList<Vector3d> ALNormals = new ArrayList<>();
        ArrayList<Face> ALFaces = new ArrayList<>();

        while (true) {
            line = bufferedReader.readLine();
            if (null == line) { break; }
            line = line.trim();
            String[] stringValues = line.split(" ");
            for (int i = 0; i < stringValues.length; ++i ) {
                stringValues[i] = stringValues[i].trim();
            }

            if (line.startsWith("v ")) {
                ALVertices.add(new Vector3d(Double.parseDouble(stringValues[1]), Double.parseDouble(stringValues[2]), Double.parseDouble(stringValues[3])));
            } else if (line.startsWith("vn ")) {
                int p = 1;
                if (stringValues[p].length() == 0){p++;}
                Vector3d nn = new Vector3d(Double.parseDouble(stringValues[p]), Double.parseDouble(stringValues[p + 1]), Double.parseDouble(stringValues[p + 2]));
                nn.normalize();
                ALNormals.add(nn);
            } else if (line.startsWith("f ")) {
                int[] verInds = new int[3];
                int[] normInds = new int[3];
                for (int i = 1; i < stringValues.length; ++i ) {
                    if (stringValues[i].length()==0) continue;
//                    System.out.println("FSTR " + Arrays.toString(stringValues));
                    String[] faceValues = stringValues[i].split("/");
                    verInds[i - 1] = Integer.parseInt(faceValues[0]);
                    normInds[i - 1] = Integer.parseInt(faceValues[2]);
                }
                Face face = new Face(verInds, normInds);
                ALFaces.add(face);
            }
        }
        bufferedReader.close();
        vertices = ALVertices;
        normals = ALNormals;
        faces = ALFaces;
//        System.out.println(ALVertices);
//        System.out.println(ALNormals);
//        System.out.println(ALFaces);
    }

    public int getVertN() {
        return vertices.size();
    }

    public int getFaceN() {
        return faces.size();
    }

    public Vector3d normal(int iface, int nthvert) {
//        System.out.println(faces.get(iface));
        return normals.get(faces.get(iface).getVni()[nthvert] - 1);
    }

    public Vector3d vertex(int i) {
        return vertices.get(i);
    }

    public Vector3d vertex(int iface, int nthvert) {
        return vertices.get(faces.get(iface).getVi()[nthvert] - 1);
    }

    public Face face(int idx) {
        return faces.get(idx);
    }
}
