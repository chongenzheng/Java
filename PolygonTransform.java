public class PolygonTransform{
    public static double[] copy(double[] array){
        double[] newArray = new double [array.length];
        for (int i = 0; i < array.length; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }
    public static void scale(double[] x, double[] y, double alpha){
        for (int i = 0; i<x.length; i++){
            x[i] = x[i] * alpha;
            y[i] = y[i] * alpha;
        }
    }
    public static void translate(double[] x, double[] y, double dx, double dy){
        for (int i = 0; i<x.length; i++){
            x[i] = x[i] + dx;
            y[i] = y[i] + dy;
        }
    }
    public static void rotate(double[] x, double[] y, double theta){
        double radian = Math.toRadians(theta);
        double temp;
        for (int i = 1; i<x.length; i++){
            temp = x[i];
            x[i] = x[i] * Math.cos(radian) - y[i] * Math.sin(radian);
            y[i] = y[i] * Math.cos(radian) + temp * Math.sin(radian);
        }
    }
    public static void main(String[] args){
        StdDraw.setScale(-5.0,5.0);
        double[] x = {0,1,1,0};
        double[] y = {0,0,2,1};
        double[] tempX = copy(x);
        double[] tempY = copy(y);
        double alpha = 2.0;
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.polygon(x, y);
        scale(x, y, alpha);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);

        StdDraw.setScale(-5.0,5.0);
        x = copy(tempX);
        y = copy(tempY);
        double dx = 2.0;
        double dy = 1.0;
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.polygon(x, y);
        translate(x, y, dx, dy);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);

        StdDraw.setScale(-5.0,5.0);
        x = copy(tempX);
        y = copy(tempY);
        double theta = 45.0;
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.polygon(x, y);
        rotate(x, y, theta);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);
    }
}