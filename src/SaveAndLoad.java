import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveAndLoad {
    private static ArrayList<Circle> c;
    private static ArrayList<LineSegment> l;
    private static ArrayList<Point> p;

    public static void save(ArrayList<Point> pList, ArrayList<LineSegment> lList, ArrayList<Circle> cList) throws FileNotFoundException {
        c = cList; l = lList; p = pList;

        File f = new File("C:Users/Yanni/IdeaProjects/Homework/");
        JFileChooser j = new JFileChooser(f, FileSystemView.getFileSystemView());
        j.showSaveDialog(null);
        File saveHere = new File(j.getSelectedFile().getPath());
        PrintWriter output = null;

        output = new PrintWriter(saveHere);

        output.println("Points");
        if (p.size() != 0){
            for (int i=0; i<p.size(); i++)
                output.println(p.get(i).getX() + " " + p.get(i).getY());
        }
        output.println("Lines");
        if (l.size() != 0){
            for (int i=0; i<l.size(); i++)
                output.println(l.get(i).getStartPoint().getX() + " " + l.get(i).getStartPoint().getY() + " " + l.get(i).getEndPoint().getX() + " " + l.get(i).getEndPoint().getY());
        }
        output.println("Circles");
        if (c.size() != 0){
            for (int i=0; i<c.size(); i++)
                output.println(c.get(i).getCentre().getX() + " " + c.get(i).getCentre().getY() + " " + c.get(i).getR());
        }
        output.println("0");
        output.close();
    }

    public static void load() throws FileNotFoundException {
        File f = new File("C:Users/Yanni/IdeaProjects/Homework/");
        JFileChooser j = new JFileChooser(f, FileSystemView.getFileSystemView());
        j.showSaveDialog(null);
        File readThis = new File(j.getSelectedFile().getPath());
        System.out.println(readThis.toString());
        Scanner input = new Scanner(readThis);

        c = new ArrayList<Circle>();
        l = new ArrayList<LineSegment>();
        p = new ArrayList<Point>();

        while (input.hasNext()){
            System.out.println("scanning file");
            String tmp = input.nextLine();
            int type = 0;
            if (tmp.equals("Points")) type = 1;
            while (type==1){
                String pTmp = input.nextLine();
                if (pTmp.equals("Lines")) {
                    System.out.println("lines");
                    type = 2;
                    break;
                } else {
                    String[] arr = pTmp.split(" ");
                    System.out.println("line = " + arr[0] + " " + arr[1]);
                    Point pointTmp = new Point(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
                    System.out.println(pointTmp);
                    p.add(pointTmp);
                    System.out.println(p.get(0));
                }
                System.out.println(type);
            }
            while (type==2){
                System.out.println("scanning lines");
                String pTmp = input.nextLine();
                if (pTmp.equals("Circles")) {
                    type = 3;
                    break;
                } else {
                    String[] arr = pTmp.split(" ");
                    LineSegment lineTmp = new LineSegment(new Point(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]) ), new Point(Integer.valueOf(arr[2]), Integer.valueOf(arr[3])) );
                    l.add(lineTmp);
                }
            }
            while (type==3){
                String pTmp = input.nextLine();
                if (pTmp.equals("0")) {
                    type = 0;
                    break;
                } else {
                    String[] arr = pTmp.split(" ");
                    Circle circleTmp = new Circle(new Point(Integer.valueOf(arr[0]), Integer.valueOf(arr[1])), Integer.valueOf(arr[2])) ;
                    c.add(circleTmp);
                }
            }

        }

    }

    public static ArrayList<Point> getP(){
        return p;
    }
    public static ArrayList<LineSegment> getL(){
        return l;
    }
    public static ArrayList<Circle> getC(){
        return c;
    }
}

