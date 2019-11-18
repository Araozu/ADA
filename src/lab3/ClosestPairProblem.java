package lab3;

import lab2.IAlgorithm;
import java.util.ArrayList;
import java.util.Comparator;

class Coordinate {
    final int x;
    final int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

public class ClosestPairProblem implements IAlgorithm {

    private ArrayList<Coordinate> coordinates;
    double result;

    ClosestPairProblem(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean run() {
        result = calc();
        return true;
    }

    double calc() {
        int size = coordinates.size();
        ArrayList<Coordinate> coordinates_x = extractElements(coordinates, 0, size);
        coordinates_x.sort(Comparator.comparingInt(c -> c.x));
        ArrayList<Coordinate> coordinates_y = extractElements(coordinates, 0, size);
        coordinates_y.sort(Comparator.comparingInt(c -> c.y));
        return efficientClosestPair(coordinates_x, coordinates_y);
    }

    private double bruteForceClosestPair(ArrayList<Coordinate> coordinates) {
        double minimunDistance = Double.MAX_VALUE;

        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate c1 = coordinates.get(i);
            for (int j = i + 1; j < coordinates.size(); j++) {
                Coordinate c2 = coordinates.get(j);
                double distance = Math.sqrt(
                    Math.pow(c1.x - c2.x, 2) +
                    Math.pow(c1.y - c2.y, 2)
                );
                minimunDistance = Math.min(minimunDistance, distance);
            }
        }

        return minimunDistance;
    }

    private ArrayList<Coordinate> extractElements(ArrayList<Coordinate> elements,
                                                  int start, int end) {
        ArrayList<Coordinate> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(elements.get(i));
        }
        return result;
    }

    private double efficientClosestPair(ArrayList<Coordinate> coordinates_x,
                                        ArrayList<Coordinate> coordinates_y) {
        if (coordinates_x.size() <= 3) return bruteForceClosestPair(coordinates_x);

        int size = coordinates_x.size();
        int half_limit = (int) Math.ceil(coordinates_x.size() / 2);
        ArrayList<Coordinate> c_x_left = extractElements(coordinates_x, 0, half_limit);
        ArrayList<Coordinate> c_y_left = extractElements(coordinates_y, 0, half_limit);

        ArrayList<Coordinate> c_x_right = extractElements(coordinates_x, half_limit, size);
        ArrayList<Coordinate> c_y_right = extractElements(coordinates_y, half_limit, size);

        double minimum_left  = efficientClosestPair(c_x_left, c_y_left);
        double minimum_right = efficientClosestPair(c_x_right, c_y_right);

        double minimum = Math.min(minimum_left, minimum_right);
        Coordinate mediumPoint = coordinates_y.get(half_limit - 1);

        ArrayList<Coordinate> mediumElements = new ArrayList<>();
        for (Coordinate c: coordinates_x) {
            if (Math.abs(c.x - mediumPoint.x) < minimum) {
                mediumElements.add(c);
            }
        }

        // (?)
        double dminsq = minimum * minimum;

        for (int i = 0; i < mediumElements.size() - 2; i++) {
            int k = i + 1;
            Coordinate c1 = mediumElements.get(i);
            Coordinate c2 = mediumElements.get(k);
            double value = Math.pow(c1.y - c2.y, 2);
            while (k <= size - 1 && value < dminsq) {
                double result = Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2);
                dminsq = Math.min(result, dminsq);
                k++;
            }
        }

        return Math.sqrt(dminsq);
    }

    @Override
    public String description() {
        return "Calculates the closes pair from a list of coordinates.";
    }

}
