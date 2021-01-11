import javax.sound.sampled.Line;
import java.util.Scanner;

public class Circle {
    private Point centre;
    private double r;
    private final double pi = 3.14;

    public Circle(Point midPoint, double radius) {

        centre = midPoint;
        r = radius;
    }

    public int getR() {

        return (int)r;
    }

    public Point getCentre() {

        return centre;
    }

    public String toString() {

        return ("Midpoint: (" + this.getCentre().getX() + "," + this.getCentre().getY() + ")\n" +
                "Radius: " + this.r + "\n" +
                "Area: " + this.FindArea() + "\n" +
                "Circumference: " + this.FindCircumference());

    }

    public double FindArea() {

        return (pi * r * r);
    }

    public double FindCircumference() {

        return (2 * pi * r);
    }

    public int CircleAndCircle(Circle circle) {

        if (this.centre.getX() == circle.centre.getX() && this.centre.getY() == circle.centre.getY() && this.getR() == circle.getR())
            return 0;

        LineSegment throughCenter = new LineSegment(this.getCentre(), circle.getCentre());
        double distanceBetweenCenters = throughCenter.getLength();
        if (distanceBetweenCenters > (this.getR() + circle.getR()) )
            return 1;
        if (distanceBetweenCenters == (this.getR() + circle.getR()) )
            return 4;

        Circle larger = new Circle(circle.getCentre(), circle.r), smaller = new Circle(this.getCentre(), this.r);
        if (this.getR() > circle.getR()) {
            larger = new Circle(this.getCentre(), this.r);
            smaller = new Circle(circle.getCentre(), circle.r);
        }
        if (distanceBetweenCenters + smaller.getR() < larger.getR()) //no intercept
            return 2;
        if (distanceBetweenCenters + smaller.getR() == larger.getR()) // tangent
            return 3;
        return 5;
    }

    public Point PointOfTangency(Circle circle) {

        Point POT = null;
        double xPOT, yPOT;

        if (this.CircleAndCircle(circle) == 3 || this.CircleAndCircle(circle) == 4) {


            Circle larger = new Circle(circle.getCentre(), circle.r), smaller = new Circle(this.getCentre(), this.r);

            if (this.getR() > circle.getR()) {
                larger = new Circle(this.getCentre(), this.r);
                smaller = new Circle(circle.getCentre(), circle.r);
            }
            LineSegment throughCenter = new LineSegment(larger.getCentre(), smaller.getCentre());
            double distanceBetweenCenters = throughCenter.getLength();
            if (larger.getCentre().getX()-smaller.getCentre().getX() * larger.getCentre().getY() - smaller.getCentre().getY() < 0)
                distanceBetweenCenters = -distanceBetweenCenters;


            yPOT = (larger.getR() * (larger.getCentre().getY() - smaller.getCentre().getY() ) / distanceBetweenCenters) + larger.getCentre().getY();
            xPOT = (larger.getR() * (larger.getCentre().getX() - smaller.getCentre().getX() ) / distanceBetweenCenters) + larger.getCentre().getX();
            POT = new Point (xPOT, yPOT);
        }
    return POT;
    }

    public LineSegment IntersectingChord(Circle circle) {

        double a, b; // where a + b == d, separated by chord
        LineSegment throughCenter = new LineSegment(this.getCentre(), circle.getCentre());
        double d = throughCenter.getLength();
        double r = this.r;
        double R = circle.r;
        //centre chord == where d intersects with chord
        double dx = circle.getCentre().getX() - this.getCentre().getX();
        double dy = circle.getCentre().getY() - this.getCentre().getY();
        //a + b ==d
        //a^2 + h^2 == r^2
        //b^2 + h^2 == R^2
        a = ( (r*r) - (R*R) + (d*d) ) / (2 * d);
        double xCentreChord = this.getCentre().getX() + (dx * a/d);
        double yCentreChord = this.getCentre().getY() + (dy * a/d);
        Point centreChord = new Point (xCentreChord, yCentreChord);
        double h = Math.pow((r*r - a*a), 0.5);
        double xChordIntersectD = dy * (h/d);
        double yChordIntersectD = dx * (h/d);
        Point POI1 = new Point (xCentreChord + xChordIntersectD, yCentreChord - yChordIntersectD);
        Point POI2 = new Point (xCentreChord - xChordIntersectD, yCentreChord + yChordIntersectD);

        LineSegment result = new LineSegment(POI1, POI2);
        return result;
        }

    public static Point[] CircleCirclePOI(Circle one, Circle two) {

        if (one.CircleAndCircle(two)==5) {
            Point[] result = new Point[2];
            LineSegment throughCenter = new LineSegment(one.getCentre(), two.getCentre());
            double a, d = throughCenter.getLength(), r = one.r, R = two.r;
            double dx = two.getCentre().getX() - one.getCentre().getX();
            double dy = two.getCentre().getY() - one.getCentre().getY();
            a = ((r * r) - (R * R) + (d * d)) / (2 * d);
            double xCentreChord = one.getCentre().getX() + (dx * a / d);
            double yCentreChord = one.getCentre().getY() + (dy * a / d);
            Point centreChord = new Point(xCentreChord, yCentreChord);
            double h = Math.pow((r * r - a * a), 0.5);
            double xChordIntersectD = dy * (h / d);
            double yChordIntersectD = dx * (h / d);

            result[0] = new Point(xCentreChord + xChordIntersectD, yCentreChord - yChordIntersectD);
            result[1] = new Point(xCentreChord - xChordIntersectD, yCentreChord + yChordIntersectD);

            return result;
        }
        if (one.CircleAndCircle(two)==3 || one.CircleAndCircle(two)==4){
            Point[] result = new Point[1];
            result[0]= one.PointOfTangency(two);
            return result;
        }
        return null;
    }

    public void Translate (int changeInX, int changeInY){

        centre.Translate(changeInX, changeInY);
    }

    public void ChangeSize (double changeFactor){

        r = r*changeFactor;
    }
}





