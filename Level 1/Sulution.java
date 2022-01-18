import java.util.HashMap;
import java.util.ArrayList;

public class Solution {
    public static int[] solution(int[] input, int n) {
        ArrayList<Integer> res = new ArrayList<>();
        //first loop to set hashmap
        HashMap<Integer, Integer> tasks = new HashMap<Integer, Integer>();
        for (int i : input)
        {
            tasks.put(i, tasks.getOrDefault(i, 0) + 1);
        }
        
        //second loop through the array to remove data
        for (int i : input)
        {
            if (tasks.get(i) <= n)
                res.add(i);
        }
        
        return res.stream().mapToInt(i -> i).toArray();
    }
}