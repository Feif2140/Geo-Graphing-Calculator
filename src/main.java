import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;


class main extends JPanel implements MouseListener, ActionListener {

    private ArrayList<Circle> circleList = new ArrayList<Circle>();
    private ArrayList<LineSegment> lineList = new ArrayList<LineSegment>();
    private ArrayList<Point> pointList = new ArrayList<Point>();

    private int x1, y1, x2, y2;  // linesegment ending point

    private JButton btnCircle, btnLine, btnCalculate, btnAxis, btnClear, btnSave, btnLoad, btnGetInfo;
    private JComboBox cbModes, cbType;
    private Point axis = new Point(400, 400);
    private JLabel result = new JLabel();
    private JFrame resultWindow;

    private int workingMode = -1, infoMode = -1; // 1 = draw line
    // 2 = draw circle
    // 3 = line and line intercept
    // 4 = line and circle
    // 5 = circle and circle
    // 6 = clear
    // 7 = save
    // 8 = load

    public main() {

        setBackground(Color.white);
        addMouseListener(this);
        btnCircle = new JButton("Draw Circle");
        btnCircle.setBounds(100, 20, 20, 10);
        btnCircle.addActionListener(this);
        btnLine = new JButton("Draw Line");
        btnLine.setBounds(200, 20, 20, 10);
        btnLine.addActionListener(this);
        final String options[] = {"Line and Line", "Line and Circle", "Circle and Circle"};
        cbModes = new JComboBox(options);
        cbModes.setBounds(300, 20, 50, 10);
        btnCalculate = new JButton("Find Intercepts");
        btnCalculate.setBounds(375, 20, 20, 10);
        btnCalculate.addActionListener(this);
        final String options2[] = {"Line Segment", "Circle"};
        cbType = new JComboBox(options2);
        cbType.setBounds(425, 20, 50, 10);
        btnGetInfo = new JButton("get info");
        btnGetInfo.setBounds(450, 20, 20, 10);
        btnGetInfo.addActionListener(this);
        btnAxis = new JButton("Draw on axes");
        btnAxis.setBounds(10, 20, 20, 10);
        btnAxis.addActionListener(this);
        btnClear = new JButton("Clear board");
        btnClear.setBounds(475, 20, 20, 10);
        btnClear.addActionListener(this);
        btnSave = new JButton("save");
        btnSave.setBounds(500, 20, 20, 10);
        btnSave.addActionListener(this);
        btnLoad = new JButton("load");
        btnLoad.setBounds(525, 20, 20, 10);
        btnLoad.addActionListener(this);

        add(btnAxis);
        add(btnCircle);
        add(btnLine);
        add(cbModes);
        add(btnCalculate);
        add(cbType);
        add(btnGetInfo);
        add(btnClear);
        add(btnSave);
        add(btnLoad);

    }


    public void paint(Graphics g) { // paint() method

        super.paint(g);
        g.setColor(Color.black);

        for (int i = 0; i < circleList.size(); i++) {
            g.setColor(new Color(184, 34, 0));
            Circle e = circleList.get(i);

            g.drawOval(e.getCentre().getX() - e.getR(), e.getCentre().getY() - e.getR(), e.getR() * 2, e.getR() * 2);
            g.drawString("C[" + i + "]  ", e.getCentre().getX(), e.getCentre().getY());
        }

        for (int k = 0; k < lineList.size(); k++) {
            g.setColor(new Color(25, 18, 230));
            LineSegment ls = lineList.get(k);

            g.drawLine(ls.getStartPoint().getX(), ls.getStartPoint().getY(), ls.getEndPoint().getX(), ls.getEndPoint().getY());
            g.drawString("L[" + k + "]  ", ls.getEndPoint().getX(), ls.getEndPoint().getY());
        }

        for (int j = 0; j < pointList.size(); j++) {
            g.setColor(new Color(184, 0, 177));
            g.setFont(new Font("TimesRoman", Font.BOLD, 12));
            Point p = pointList.get(j);
            g.drawOval(p.getX() - 3, p.getY() - 3, 6, 6);
            g.drawString("  (" + (p.getX() - axis.getX()) + "," + (axis.getY() - p.getY()) + ")", p.getX(), p.getY());
        }

        g.setColor(Color.BLACK);
        g.drawLine(axis.getX() - 3000, axis.getY(), axis.getX() + 3000, axis.getY());
        g.drawLine(axis.getX(), axis.getY() - 3000, axis.getX(), axis.getY() + 3000);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    //   When mouse is pressed, get the starting point for either a line or the starting point of the diameter for the circle
    public void mousePressed(MouseEvent me) {

        x1 = me.getX();
        y1 = me.getY();

    }

    public void mouseReleased(MouseEvent me) {

        x2 = me.getX();
        y2 = me.getY();
        Point start = new Point(x1, y1);
        Point end = new Point(x2, y2);
        if (workingMode == 1)
            lineList.add(new LineSegment(start, end));
        if (workingMode == 2) {
            double r = Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2)) / 2;
            Point middle = new Point((end.getX() + start.getX()) / 2, (end.getY() + start.getY()) / 2);
            circleList.add(new Circle(middle, r));
        }
        if (workingMode == 0)
            axis = new Point(me.getX(), me.getY());
        repaint();

    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAxis))
            workingMode = 0;
        if (e.getSource().equals(btnLine))
            workingMode = 1;
        if (e.getSource().equals(btnCircle))
            workingMode = 2;
        if (e.getSource().equals(btnClear)) {
            workingMode = 6;
            pointList.clear();
            lineList.clear();
            circleList.clear();
        }
        if (e.getSource().equals(btnSave)) {
            workingMode = 7;
            try {
                SaveAndLoad.save(pointList, lineList, circleList);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            repaint();
        }
        if (e.getSource().equals(btnLoad)) {
            workingMode = 8;
            try {
                SaveAndLoad.load();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            pointList.addAll(SaveAndLoad.getP());
            lineList.addAll(SaveAndLoad.getL());
            circleList.addAll(SaveAndLoad.getC());
            repaint();
        }
        if (e.getSource().equals(btnGetInfo)){
            if (cbType.getItemAt(cbType.getSelectedIndex()).equals("Line Segment")){
                JTextField firstLine = new JTextField();
                JTextField secondLine = new JTextField();
                Object[] message = {"Line Segment :", firstLine};
                int option = JOptionPane.showConfirmDialog(null, message, "Select a Line Segment", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    LineSegment line = lineList.get(Integer.parseInt(firstLine.getText()));
                    JFrame resultWindow = new JFrame("Line: "+ Integer.parseInt(firstLine.getText()));
                    resultWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    resultWindow.setSize(400,200);
                    resultWindow.setVisible(true);
                    Point start = new Point(line.StartPoint.getX() - axis.getX(), axis.getY() - line.StartPoint.getY());
                    Point end = new Point(line.EndPoint.getX()- axis.getX(), axis.getY() -line.EndPoint.getY());
                    int b = axis.getY() - LineSegment.POIWithLine(new LineSegment(axis, new Point(axis.getX(), axis.getY() + 1)), line).getY();
                    result.setText("<html> LINE " + Integer.parseInt(firstLine.getText()) +  " <br/> " +
                                   "End Points: " + start + "  " + end + " <br/> " +
                                   "Slope: " + line.getSlope() + " <br/>" +
                                   "Equation: y=" + line.getSlope() + "x + " + b + "</html>" );
                    resultWindow.add(result);
                    result.setVisible(true);
                }
            }
            if (cbType.getItemAt(cbType.getSelectedIndex()).equals("Circle")){
                JTextField firstLine = new JTextField();
                JTextField secondLine = new JTextField();
                Object[] message = {"Circle :", firstLine};
                int option = JOptionPane.showConfirmDialog(null, message, "Select a Circle", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Circle cir = circleList.get(Integer.parseInt(firstLine.getText()));
                    JFrame resultWindow = new JFrame("Line: "+ Integer.parseInt(firstLine.getText()));
                    resultWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    resultWindow.setSize(400,200);
                    resultWindow.setVisible(true);
                    Point mid = new Point(cir.getCentre().getX() - axis.getX(), axis.getY() - cir.getCentre().getY());
                    result.setText("<html> CIRCLE " + Integer.parseInt(firstLine.getText()) +  " <br/> " +
                                   "Mid Point: " + mid + " <br/> " +
                                   "Radius: " + cir.getR() + " <br/>" +
                                   "Equation: (y - ("+ mid.getY() + "))² + (x - (" + mid.getX() + "))² = " + cir.getR() + "²" + "</html>" );
                    resultWindow.add(result);
                    result.setVisible(true);
                }
            }
        }

        if (e.getSource().equals(btnCalculate)) {
//                System.out.println("yes");
            if (cbModes.getItemAt(cbModes.getSelectedIndex()).equals("Line and Line")) {
                workingMode = 3;
                JTextField firstLine = new JTextField();
                JTextField secondLine = new JTextField();

                Object[] message = {"First Line :", firstLine, "Second Line :", secondLine};
                int option = JOptionPane.showConfirmDialog(null, message, "Choose Two Lines", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Point p = LineSegment.POIWithLine(lineList.get(Integer.parseInt(firstLine.getText())), lineList.get(Integer.parseInt(secondLine.getText())));
                    pointList.add(p);
                } else System.out.println("No lines are chosen");
            }
            if (cbModes.getItemAt(cbModes.getSelectedIndex()).equals("Line and Circle")) {
                workingMode = 4;
                JTextField Line = new JTextField();
                JTextField Cir = new JTextField();

                Object[] message = {"Line :", Line, "Circle :", Cir};
                int option = JOptionPane.showConfirmDialog(null, message, "Choose Line and Circle", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Point p = LineSegment.CircleLinePOI(lineList.get(Integer.parseInt(Line.getText())), circleList.get(Integer.parseInt(Cir.getText())))[0];
                    pointList.add(p);
                    p = LineSegment.CircleLinePOI(lineList.get(Integer.parseInt(Line.getText())), circleList.get(Integer.parseInt(Cir.getText())))[1];
                    pointList.add(p);
                } else System.out.println("Missing Field");
            }
            if (cbModes.getItemAt(cbModes.getSelectedIndex()).equals("Circle and Circle")) {
                workingMode = 5;
                JTextField Cir1 = new JTextField();
                JTextField Cir2 = new JTextField();

                Object[] message = {"First Circle :", Cir1, "Second Circle :", Cir2};
                int option = JOptionPane.showConfirmDialog(null, message, "Choose Line and Circle", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Point p = Circle.CircleCirclePOI(circleList.get(Integer.parseInt(Cir1.getText())), circleList.get(Integer.parseInt(Cir2.getText())))[0];
                    pointList.add(p);
                    p = Circle.CircleCirclePOI(circleList.get(Integer.parseInt(Cir1.getText())), circleList.get(Integer.parseInt(Cir2.getText())))[1];
                    pointList.add(p);
                } else System.out.println("Missing Field");
            }
            repaint();
        }
    }


    public static void main(String args[]) {

        JFrame frame = new JFrame();

        frame.getContentPane().add(new main());

        frame.setTitle("Circle 4");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setSize(800, 800);
        frame.setBounds(0, 0, 800, 800);

        frame.setVisible(true);


    }

}