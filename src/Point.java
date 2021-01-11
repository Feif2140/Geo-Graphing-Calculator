public class Point {
    private double x;
    private double y;

    public Point (double X, double Y){

        x = X;
        y = Y;
    }

    public int getX(){

        return (int) x;
    }

    public int getY(){

        return (int) y;
    }

    public String toString(){
        return ("Point " + ": (" + this.x + "," + this.y + ")");
    }

    public int IsOnLine(LineSegment line){

        double yIntercept = line.StartPoint.y - (line.getSlope() * line.StartPoint.x );
        if (y == (line.getSlope()) * x + yIntercept)
            return 1;
        return 0;
    }

    public int PointAndCircle(Circle circle){

        double xValue1 = Math.pow( Math.pow (circle.getR(), 2) - Math.pow(y - circle.getCentre().y, 2) , 0.5) + circle.getCentre().y;
        double xValue2 = -Math.pow( Math.pow (circle.getR(), 2) - Math.pow(y - circle.getCentre().y, 2) , 0.5) + circle.getCentre().y;
        double xLeftBound = Math.min(xValue1, xValue2);
        double xRightBound = Math.max(xValue1, xValue2);

        double yValue1 = Math.pow( Math.pow (circle.getR(), 2) - Math.pow( ( x - circle.getCentre().x ), 2) , 0.5) + circle.getCentre().x;
        double yValue2 = -Math.pow( Math.pow (circle.getR(), 2) - Math.pow( ( x - circle.getCentre().x ) , 2) , 0.5) + circle.getCentre().x;
        double yUpperBound = Math.max(yValue1, yValue2);
        double yLowerBound = Math.min(yValue1, yValue2);

        if ( Math.pow( ( x - circle.getCentre().x ), 2 ) + Math.pow( ( y - circle.getCentre().y ), 2 ) == Math.pow( circle.getR(), 2 ) )
            return 2; //on circle
        if ( xLeftBound < x && x < xRightBound && yLowerBound < y && y < yUpperBound)
            return 1; //inside
        return -1; //outside
    }

    public void Translate(int changeInX, int changeInY) {

        x = x + changeInX;
        y = y + changeInY;
    }

}
