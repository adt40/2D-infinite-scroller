package main.java.com.util;

import main.java.com.entities.Entity;
import main.java.com.terrain.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStar {
    public static List<Vector> getPath(Vector start, Vector goal, Entity entity, int maxDepth) {
        List<Vector> closedSet = new ArrayList<>();
        List<Vector> openSet = new ArrayList<>();
        openSet.add(start);
        HashMap<Vector, Vector> cameFrom = new HashMap<>();
        HashMap<Vector, Double> gScore = new HashMap<>();
        gScore.put(start, 0.0);
        HashMap<Vector, Double> fScore = new HashMap<>();
        fScore.put(start, start.distanceTo(goal)); //heuristic
        int depth = 0;
        while (openSet.size() != 0 && depth <= maxDepth) {
            Vector current = openSet.stream().reduce((x, y) -> fScore.get(x).compareTo(fScore.get(y)) <= 0 ? x : y).get();
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            List<Vector> neighbors = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Vector direction = Vector.getVectorFromDirectionInt(i);
                Vector possibleNewPosition = entity.getGridPosition().add(direction);
                if (entity.isTileWalkable(Terrain.grid.get(possibleNewPosition))) {
                    neighbors.add(current.add(direction));
                }
            }

            for (int i = 0; i < neighbors.size(); i++) {
                Vector neighbor = neighbors.get(i);
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                Double tentativeGScore = gScore.get(current) + current.distanceTo(neighbor);

                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentativeGScore >= gScore.get(neighbor)) {
                    continue;
                }

                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentativeGScore);
                fScore.put(neighbor, gScore.get(neighbor) + neighbor.distanceTo(goal));
            }
            depth++;
        }
        //path can't be found
        return new ArrayList<>();
    }

    private static List<Vector> reconstructPath(HashMap<Vector, Vector> cameFrom, Vector current) {
        List<Vector> totalPath = new ArrayList<>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
        }
        return totalPath;
    }
}
