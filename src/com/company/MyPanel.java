package com.company;

import java.awt.*;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


import javax.swing.*;

public class MyPanel extends JPanel  {

    int width = 1000;
    int length = 1000;
    boolean isClusterChanged = true;
    Centroid[] centroids;
    Point[] pointsArray;


    public MyPanel() {
        setPreferredSize(new Dimension(width,length ));
        start();

    }

    public void start() {

        Scanner scanner = new Scanner(System.in);
            String menu = " \n" +
                    "1. Use Euclidean metric  \n" +
                    "2. Use Manhattan metric \n" +
                    "3. Use Chebychev metric \n" +
                    "0. Exit";

            System.out.println(menu);
            int option = scanner.nextInt();

            pointsArray = generatePointArray();
            int numbOfCentroids = 3;
            centroids = generateCentroidsArray(numbOfCentroids);

            while (isClusterChanged) {
                assignPointsToCluster(pointsArray, centroids, option);
                updateCentroidMean(pointsArray, centroids);
            }
        }



    public void paintComponent(Graphics g) {


        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g);

        for(int i = 0; i < pointsArray.length; i++){
            Point point = pointsArray[i];
            g.setColor(point.cluster);
            if(i < 10) {
                g.fillOval(point.getX(),point.getY(),5,5);

            } else if(i <  20){
                g.fillOval(point.getX(), point.getY(),5,5);
            } else {
                g.fillOval(point.getX(),point.getY(),5,5);
            }

        }
            for (int i = 0; i < centroids.length; i++) {
                Centroid currCentroid = centroids[i];
                g.setColor(currCentroid.colour);
                g.fillOval(currCentroid.x, currCentroid.y, 20, 20);

            }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < length;j++) {
                Color color = getPixelColor(i,j);
                g.setColor(color);
                g.fillOval(i,j,1,1);
            }
        }

    }


    public void updateCentroidMean(Point[] points, Centroid[] centroids) {

        for(int i = 0; i < centroids.length; i++) {
            Centroid currCentroid = centroids[i];
            int pointCounter = 0, xSum = 0, ySum = 0;

            for(int j = 0; j < points.length; j++) {
                Point currPoint = points[j];
                if(currPoint.cluster.equals(currCentroid.colour)) {
                    xSum += currPoint.x;
                    ySum += currPoint.y;
                    pointCounter++;
                }
            }

            if(pointCounter != 0) {
                int xAVG = xSum / pointCounter;
                int yAVG = ySum / pointCounter;
                currCentroid.updateX(xAVG);
                currCentroid.updateY(yAVG);
            }
        }
    }

    public Centroid findNearesCentroidChebyshev(Point point, Centroid[] centroids) {
        Centroid nearestCentroid = centroids[0];
        double nearestDistance = -1;

        for(int i = 0; i < centroids.length; i++) {
            Centroid currCentroid = centroids[i];
            double q1 = Math.abs(point.x - currCentroid.x);
            double q2 = Math.abs(point.y - currCentroid.y);
            double currentCentroidDistance = Math.max( q1,q2);

            if(nearestDistance == -1 || nearestDistance > currentCentroidDistance) {
                nearestCentroid = currCentroid;
                nearestDistance = currentCentroidDistance;
            }
        }

        return nearestCentroid;
    }



    public Color getPixelColor(int x, int y) {

        double nearestDistance = -1;
        Color nearestPointColor = pointsArray[0].cluster;

        for(int i = 0; i < pointsArray.length; i++) {
            Point point = pointsArray[i];

            double q1 = Math.pow(point.x - x,2);
            double q2 = Math.pow(point.y - y,2);
            double currentCentroidDistance = Math.sqrt(q2 + q1);

            if(nearestDistance == -1 || nearestDistance > currentCentroidDistance) {
                nearestPointColor = point.cluster;
                nearestDistance = currentCentroidDistance;
            }


        }
        return nearestPointColor;
    }

    public Centroid findNearesCentroidTaxiCab(Point point, Centroid[] centroids) {
        Centroid nearestCentroid = centroids[0];
        double nearestDistance = -1;

        for(int i = 0; i < centroids.length; i++) {
            Centroid currCentroid = centroids[i];
            double q1 = Math.abs(point.x - currCentroid.x);
            double q2 = Math.abs(point.y - currCentroid.y);
            double currentCentroidDistance = q1 + q2;

            if(nearestDistance == -1 || nearestDistance > currentCentroidDistance) {
                nearestCentroid = currCentroid;
                nearestDistance = currentCentroidDistance;
            }
        }

        return nearestCentroid;
    }
    public Centroid findNearestCentroid(Point point, Centroid[] centroids) {
        Centroid nearestCentroid = centroids[0];
        double nearestDistance = -1;

        for(int i = 0; i < centroids.length; i++) {
            Centroid currCentroid = centroids[i];
            double q1 = Math.pow(point.x - currCentroid.x,2);
            double q2 = Math.pow(point.y - currCentroid.y,2);
            double currentCentroidDistance = Math.sqrt(q2 + q1);

            if(nearestDistance == -1 || nearestDistance > currentCentroidDistance) {
                nearestCentroid = currCentroid;
                nearestDistance = currentCentroidDistance;
            }
        }

        return nearestCentroid;
    }

    public void assignPointsToCluster(Point[] pointsArray, Centroid[] centroids, int option) {
        isClusterChanged = false;
        for(int i = 0; i < pointsArray.length;i++) {
            Point currPoint = pointsArray[i];
            Centroid nearestCentroid;

            if (option == 1) {
                nearestCentroid = findNearestCentroid(currPoint, centroids);
            }  else if ( option == 2 ) {
                nearestCentroid = findNearesCentroidTaxiCab(currPoint, centroids);
            } else {
                nearestCentroid = findNearesCentroidChebyshev(currPoint, centroids);
            }
            if(!currPoint.cluster.equals(nearestCentroid.colour)) {
                currPoint.assignToCluster(nearestCentroid.colour);
                isClusterChanged = true;
            }
        }
    }

    public void updateCentroidsPosition(Graphics g, Centroid[] centroids) {

        for(int i = 0; i < centroids.length; i++) {
            Centroid currCentroid = centroids[i];
            g.setColor(currCentroid.colour);
            g.fillOval(currCentroid.x, currCentroid.y,20,20);
        }
    }




    public Centroid[] generateCentroidsArray(int k) {
        Centroid[] centroids = new Centroid[k];
        for(int i = 0; i < centroids.length; i++) {
            centroids[i] = new Centroid();
        }
        return centroids;
    }

    public void updatePointsIn2D(Graphics g, Point[] pointsArray) {

        for(int i = 0; i < pointsArray.length; i++){
            Point point = pointsArray[i];
            g.setColor(point.cluster);
            if(i < 10) {
                g.fillOval(point.getX(),point.getY(),5,5);

            } else if(i <  20){
                g.fillOval(point.getX(), point.getY(),5,5);
            } else {
                g.fillOval(point.getX(),point.getY(),5,5);
            }

        }
    }

    public Point[] generatePointArray() {
        Point[] pointsArray = new Point[5000];

        for(int i = 0; i < pointsArray.length; i++){
            pointsArray[i] = new Point(0,0);
            Point point = pointsArray[i];
                point.x = random(0,995);
                point.y = random(0,995);

        }

        return pointsArray;
    }

    public static double min(double a, double b, double c) {
        return Math.min(Math.min(a, b), c);
    }

    public int random(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }



}









