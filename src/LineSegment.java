import javax.sound.sampled.Line;
import java.lang.constant.Constable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.Scanner;

public class LineSegment {
    Point StartPoint;
    Point EndPoint;

    public LineSegment(Point one, Point two){

        StartPoint = one;
        EndPoint = two;
    }

    public Point getStartPoint(){

        return StartPoint;
    }

    public Point getEndPoint(){

        return EndPoint;
    }

    public String toString(){
        double deltaY = StartPoint.getY() - EndPoint.getY();
        double deltaX = StartPoint.getX() - EndPoint.getX();
        for (int i = (int) Math.max(deltaX, deltaY); i >= 1 ; i++) {
            if (deltaY % i == 0 && deltaY % i == 0) {
                deltaY = deltaY / i;
                deltaX = deltaX / i;
            }
        }
        return ("Point 1: (" + getStartPoint().getX() + "," + getStartPoint().getY() + ")\n" +
                "Point 2: (" + getEndPoint().getX() + "," + getEndPoint().getY() + ")\n" +
                "Slope of the line: " + deltaY + "/" + deltaX + " or to be exact " + getSlope() + "\n" +
                "Length of the line: " + getLength());
    }

    public double getLength(){
        double aSqr = Math.pow((StartPoint.getX() - EndPoint.getX() ), 2);
        double bSqr = Math.pow((StartPoint.getY() - EndPoint.getY() ), 2);
        double c = Math.sqrt(aSqr + bSqr);
        return c;
    }

    public Double getSlope(){

        double slope;
        double deltaY = StartPoint.getY() - EndPoint.getY();
        double deltaX = StartPoint.getX() - EndPoint.getX();
        if (deltaX == 0)
            return null;   // vertical line
        slope = -deltaY/deltaX;
        return slope;
    }

    public static Point POIWithLine(LineSegment thiss, LineSegment line){

        if (thiss.getSlope() == line.getSlope()) {
            System.out.println("lines are parallel, no point of intersection");
            return null;
        }
        double x, y;
        if (thiss.getSlope() == null){
            x = thiss.getStartPoint().getX();
            y = -line.getSlope()*x + line.getB();
        }
        else if (line.getSlope() == null) {
            x = line.getStartPoint().getX();
            y = -thiss.getSlope()*x + thiss.getB();
        }
        else {
            x = -(thiss.getB() - line.getB()) / (-thiss.getSlope() + line.getSlope());
            y = -thiss.getSlope() * x + thiss.getB();
        }
        Point POI = new Point(x, y);
        return POI;
    }

    public Point[] POTWithCircle(Circle circle){
        final double pi = 3.14;
        double yIntercept;
        Point[] result = new Point[2];


        if (this.getSlope() == null) {
            result[0] = new Point( (circle.getCentre().getX() - circle.getR() ), circle.getCentre().getY() );
            result[1] = new Point( (circle.getCentre().getX() + circle.getR() ), circle.getCentre().getY() );
        }
        else if (this.getSlope() == 1) {
            result[0] = new Point(circle.getCentre().getX(), (circle.getCentre().getY() - circle.getR()));
            result[1] = new Point(circle.getCentre().getX(), (circle.getCentre().getY() + circle.getR()));
        }
        else {

            double target = -this.getSlope();
            double deltaY = StartPoint.getY() - EndPoint.getY();
            double deltaX = StartPoint.getX() - EndPoint.getX();
            double yPOT = circle.getR() * deltaY / this.getLength();
            double xPOT = circle.getR() * deltaX / this.getLength();

            if (target > 0){
                result[0] = new Point(circle.getCentre().getX() - xPOT, circle.getCentre().getY() + yPOT);
                result[1] = new Point(circle.getCentre().getX() + xPOT, circle.getCentre().getY() - yPOT);
            }
            if (target < 0){
                result[0] = new Point(circle.getCentre().getX() - xPOT, circle.getCentre().getY() - yPOT);
                result[1] = new Point(circle.getCentre().getX() + xPOT, circle.getCentre().getY() + yPOT);
            }

        }
        return result;
    }

    int IsPerpendicular(LineSegment line){
        if ( (this.getSlope() == null && Math.abs(line.getSlope()) == 0 )|| (Math.abs(this.getSlope()) == 0 && line.getSlope() ==null))
            return 1;
        else if (this.getSlope()==null || line.getSlope()==null)
            return 0;
        else if (this.getSlope() == -(line.getSlope()) )
            return 1;
        return 0;
    }
    public static Point[] CircleLinePOI(LineSegment line, Circle circle){
        Point[] result;
        double m = -line.getSlope(), B = line.getB(), h = circle.getCentre().getY(), k = circle.getCentre().getX(), r = circle.getR();
        double a = ( m*m + 1); // x^2 term
        double b = ( 2*m*(B-h) - 2*k ); // x term
        double c = ( Math.pow(B-h,2) - r*r + k*k ); // c term
        double D = ( b*b ) - ( 4*a*c );

        if (D == 0){
            result = new Point[1];
            result[0] = (new Point( -b/(2*a) , ( -b/(2*a) )*m + B ));
            return result;
        } if (D > 0) {
            double x1 = (-b + Math.sqrt(D) )/(2*a), x2 = (-b - Math.sqrt(D) )/(2*a);
            result = new Point[2];
            result[0] = new Point( x1  ,  x1*m + B );
            result[1] = new Point( x2  , x2*m + B );
            return result;
        }
        return null;
    }

    public double getB(){

        return (int) ( this.StartPoint.getY() + ( this.getSlope() * this.StartPoint.getX() ) );
    }
}
