public class Solution {
    static int cache[][] = new int[202][202]; //cache for memorization
    
    public static int helper(int height, int left)
    {
        int res;
        
        //height is the height of the starcase, left is the amount of bricks left
        if (cache[height][left] != 0)
            return cache[height][left];
        // all bricks used
        if (left == 0)
            return 1;
        // not enough bricks
        if (left < height)
            return 0;
        res = helper(height + 1, left - height) + helper(height + 1, left); // combine the results of building another staircase and adding 1 more height to curretn stair
        cache[height][left] = res;
        return res;
    }
    
    public static int solution(int bricks)
    {
        return helper(1, bricks) - 1;
    }
}