package ru.ishingarov.coursework.renderer;

import org.springframework.stereotype.Service;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import java.io.*;
import java.util.ArrayList;

public class Model {
    private ArrayList<Vector3d> vertices;
    private ArrayList<Vector3d> normals;
    private ArrayList<Face> faces;

    public double[][] getRotation() {
        return rotation;
    }

    private double[][] rotation = new  double[4][4];

    public Model(String filename) throws IOException {
        loadModel(filename);
    }

    public void loadModel(String filename) throws IOException {

        File objFile = new File(filename);
        FileReader fileReader = new FileReader(objFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
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
    }

    private static final Matrix4d r = new Matrix4d();
    private static final Matrix4d res = new Matrix4d();

    public static Matrix4d Rotate(double rx, double ry, double rz) {
            r.rotX(rx);
            res.rotY(ry);
            res.mul(r);
            r.rotZ(rz);
            res.mul(r);
            return res;
    }

    public void setRotation(Matrix4d rotMatrix) {
        for (int i = 0; i < 4; i++) {
            rotMatrix.getRow(i, rotation[i]);
        }
    }

    public int getVertN() {
        return vertices.size();
    }

    public int getFaceN() {
        return faces.size();
    }

    public Vector3d normal(int iface, int nthvert) {
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
