import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

//Forgot to save commented version, sorry!
public class MyClass {
    static int [][] cartesianProduct(ArrayList<ArrayList<Integer>> series)
    {
        int i;
        int j;
        int k;
        int [][] res;
        
        res = new int[series.get(0).size() * series.get(1).size()][2];
        i = -1;
        k = -1;
        while(++i < series.get(0).size())
        {
            j = -1;
            while(++j < series.get(1).size())
            {
                res[++k][0] = series.get(0).get(i);
                res[k][1] = series.get(1).get(j);
            }
        }
        return res;
    }
    
    static Double get_angle(int []p1, int []p2)
    {
        return Math.atan2(p2[0] - p1[0], p2[1] - p1[1]);
    }
    
    static Double get_distance(int []p1, int []p2)
    {
        return Math.sqrt(((p2[0] - p1[0]) * (p2[0] - p1[0])) + ((p2[1] - p1[1]) * (p2[1] - p1[1])));
    }
    
    static ArrayList<Integer> generateSeriesHelper(int []dim, int[] yp, int []gp, int dist, int i)
    {
        int min;
        int max;
        int curr;
        int []steps = {dim[i] - gp[i], gp[i]};
        int stepidx;
        ArrayList<Integer> res = new ArrayList<>();
        
        min = yp[i] - dist;
        max = yp[i] + dist;
        curr = gp[i];
        stepidx = 0;
        while (curr <= max)
        {
            res.add(curr);
            curr += 2 * steps[stepidx];
            if (stepidx == 0) stepidx = 1;
            else
                stepidx = 0;
        }
        stepidx = 1;
        curr = gp[i];
        while (curr >= min)
        {
            res.add(curr);
            curr -= 2 * steps[stepidx];
            if (stepidx == 0) stepidx = 1;
            else
                stepidx = 0;            
        }
        return res;
    }
    
    static ArrayList<ArrayList<Integer>> generateSeries(int []dim, int []yp, int []gp, int dist)
    {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        
        res.add(generateSeriesHelper(dim, yp, gp, dist, 0));
        res.add(generateSeriesHelper(dim, yp, gp, dist, 1));
        return res;
    }
    
    static Map getAngles(int []dim, int []yp, int []gp, int dist)
    {
        Double distance;
        Double angle;
        
        Map<Double, Double> res = new HashMap<Double, Double>();
        ArrayList<ArrayList<Integer>> all_series = generateSeries(dim, yp, gp, dist);
        for (int[] point : cartesianProduct(all_series))
        {
            distance = get_distance(point, yp);
            angle = get_angle(point, yp);

            if ((point[0] != yp[0] || point[1] != yp[1]) && (distance <= dist))
            {
                if (!res.containsKey(angle))
                    res.put(angle, distance);
                else
                    res.put(angle, Math.min(res.get(angle), distance));
            }
        }
        return res;
    }
    static int  answer(int []dim, int []yp, int []gp, int dist)
    {
        int res;
        Map<Double, Double> hit_self;
        Map<Double, Double> hit_guard;
        Double              hit_self_dist;
        Double              hit_guard_dist;
        
        hit_self = getAngles(dim, yp, yp, dist);
        hit_guard = getAngles(dim, yp, gp, dist);
        res = 0;
        
        for (Double angle : hit_guard.keySet())
        {
            hit_self_dist = hit_self.get(angle);
            hit_guard_dist = hit_guard.get(angle);
            if ((hit_self.containsKey(angle)))
            {
                if (hit_self_dist > hit_guard_dist)
                {
                    res++;
                }
            }
            else
            {
                res++;
            }
        }
        return res;
    }
    
    public static void main(String args[]) {
        int []dim = {300, 275};
        int []yp = {150, 150};
        int []gp = {185, 100};
        int dist = 500;
        
        System.out.println(answer(dim, yp, gp, dist));

    }
}




