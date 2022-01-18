import java.util.ArrayDeque; 
import java.util.Queue;
import java.util.Objects;
import java.util.HashSet;

//node class
class Node
{
    int x;
    int y;
    int dist;
    
    Node(int x, int y, int dist)
    {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }
    
    //overriding these methods because to work with hashset
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y &&
                dist == node.dist;
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(x, y, dist);
    }
}

public class Solution {
    //possible moves
    static int []y_moves = {2, 2, -2, -2, 1, 1, -1, -1 };
    static int []x_moves = { -1, 1, 1, -1, 2, -2, 2, -2 };
    
	//move validation
    public static boolean is_valid(int x, int y)
    {
        return ((x >= 0 && x < 8) && (y >= 0 && y < 8));
    }
    
    public static int solution(int src, int dest) {
        Queue<Node> queue = new ArrayDeque<>();//queue for dfs
        HashSet<Node> visited = new HashSet<>();//hashset to store visited items
        Node currNode;

        queue.add(new Node(src % 8, src / 8, 0));//add init node
        while (!queue.isEmpty())//execute if queue still has elems
        {
            currNode = queue.poll();
            if (currNode.x == dest % 8 && currNode.y == dest / 8)//base case
                return currNode.dist;
            if (!visited.contains(currNode))
            {
                visited.add(currNode);
                for (int i = 0; i < y_moves.length; i++)
                {
                    if (is_valid(currNode.x + x_moves[i], currNode.y + y_moves[i]))
                        queue.add(new Node(currNode.x + x_moves[i], currNode.y + y_moves[i], currNode.dist + 1));//add ndoe to queue after validation
                }
            }
        }
        return 99999999;
    }
}