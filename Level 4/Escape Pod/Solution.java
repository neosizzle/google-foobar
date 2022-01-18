import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Queue;
import java.util.ArrayDeque;
public class Solution {
    private static final int INF = Integer.MAX_VALUE;

    /*
    ** Transforms a multi entrance / multi exit graph to one with a supersink
    ** and one supersource
    **
    ** 1. Obtain new length
    ** 2. Initialize result grapg
    ** 3. Start populating the new graph with offset
    ** 4. Populate Entrance supersrc
    ** 5. populate exit supersink
    ** 5. return transformed array
    **
    ** @param int[]srcs - The entrances
    ** @param int[]sinks - The exits
    ** @param int[][]graph - The untransformed graph
    ** return int[][]res - The transformed graph
    */
    static int[][] transform(int []srcs, int []sinks, int[][]graph)
    {
        int length = graph.length;
        int new_length = graph.length + 2;
        int [][]res;

        res = new int[new_length][new_length];
        for (int i = 0; i < length; ++i)
        {
            for (int j = 0; j < length; ++j)
                res[i + 1][j + 1] = graph[i][j];
        }
        for (int entrance : srcs)
            res[0][entrance + 1] = INF;
        for (int exit : sinks)
            res[exit + 1][new_length - 1] = INF;
        return res;
    }

        /*
        ** Pathfinding using breath first search
        **
        ** 1. Initialize parents array for pathfinding with -1
        ** 2. Add index 0 into bfs queue
        ** 3. While we still have nodes in our queue
        **      1. for each outgoing child at current node
        **              1. if it exists and hsa no parent yet, add into queue and add node to parent
        ** 4. set current node to last node
        ** 5. Add nodes into path using steps method
        ** 6. Reverse paht and return
        ** @param int[][]graph - the graph to traverse
        ** @return ArrayList<Integer> path - The found path
        */
    public static ArrayList <Integer> bfs(int [][] graph)
    {
        int []parents;
        int curr_node;
        Queue<Integer> queue = new ArrayDeque<Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();

                //bfs
        parents = new int[graph.length];
        Arrays.fill(parents, -1);
        queue.add(0);
        while(!queue.isEmpty() && parents[parents.length - 1] == -1)
        {
            curr_node = queue.remove();
            for (int i = 0; i < graph[curr_node].length; ++i)
            {
                if (graph[curr_node][i] > 0 && parents[i] == -1)
                {
                    queue.add(i);
                    parents[i] = curr_node;
                }
            }
        }

        //get path
        curr_node = parents[parents.length - 1];
        while (curr_node != 0)
        {
            if (curr_node == -1) return null;
            path.add(curr_node);
            curr_node = parents[curr_node];
        }
        Collections.reverse(path);
        return path;
    }

        /*
        ** Ford fulkersons algorithm
        **
        ** 1. initialize maxflow and current path
        ** 2. while we still have a valid path
        **      1. Starting from the 0th node
        **      2. Calculate bottleneck as we traverse the path
        **      3. Increment max flow
        **      4. Augent the path according to bottleneck
        **      5. bfs again to find next path
        **
        ** @param int[][] graph - The transformed graph
        ** @return int max_flow
        */
    public static int ff(int [][] graph)
    {
        int max_flow;
        int bottleneck;
        int curr_node;
        ArrayList<Integer> curr_path;

        max_flow = 0;
        curr_path = bfs(graph);
        while (curr_path != null)
        {
            bottleneck = Integer.MAX_VALUE;
            curr_node = 0;
            for (int i : curr_path)
            {
                bottleneck = Math.min(bottleneck, graph[curr_node][i]);
                curr_node = i;
            }

            max_flow += bottleneck;
            curr_node = 0;
            for (int i : curr_path)
            {
                graph[curr_node][i] -= bottleneck;
                graph[i][curr_node] += bottleneck;
                curr_node = i;
            }
            curr_path = bfs(graph);
        }
        return max_flow;
    }

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        return ff(transform(entrances, exits, path));
    }

}
