/*************************************************************************
 *  Compilation:  javac Sierpinski.java
 *  Execution:    java Sierpinski
 *
 *  @author:
 *
 *************************************************************************/

public class Sierpinski {

    // Height of an equilateral triangle whose sides are of the specified length. 
    public static double height(double length){
        double height = length * (Math.sqrt(3)/2); 
        return height;
    }

    // Draws a filled equilateral triangle whose bottom vertex is (x, y) 
    // of the specified side length. 
    public static void filledTriangle(double x, double y, double length) {
        double[] pointX = {x, x - length/2, x + length/2};
        double[] pointY = {y, height(length)+y, height(length)+y};
        StdDraw.filledPolygon(pointX,pointY);
    }

    // Draws a Sierpinski triangle of order n, such that the largest filled 
    // triangle has bottom vertex (x, y) and sides of the specified length. 
    public static void sierpinski(int n, double x, double y, double length) {
        if(n == 0){
            return;
        }
        filledTriangle(x+length,y,length);
        sierpinski(n-1, x, y, length/2);
        sierpinski(n-1, x + length, y, length/2);//right
        sierpinski(n-1, x+length/2, y+height(length), length/2);//top
    }

    // Takes an integer command-line argument n; 
    // draws the outline of an equilateral triangle (pointed upwards) of length 1; 
    // whose bottom-left vertex is (0, 0) and bottom-right vertex is (1, 0); and 
    // draws a Sierpinski triangle of order n that fits snugly inside the outline. 
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        double length = 0.5;
        double x = 0.0;
        double y = 0.0;
        StdDraw.line(0,0,0.5,Math.sqrt(3)/2);
        StdDraw.line(0.5,Math.sqrt(3)/2,1,0);
        StdDraw.line(1,0,0,0);
        // double[] a = {x, x + length/2, x + length};
        // double[] b = {y, y + Math.sqrt(3)/2*length, y};
        // StdDraw.polygon(a,b);
        sierpinski(1,0.5,0.0,0.5);
        //sierpinski(n,x,y,length);
    }
}
