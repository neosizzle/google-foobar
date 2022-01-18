
public class Solution {
    public static int solution(int[] arr) {
        int res;
        int leftResults;
        int rightResults;
        int i;
        int lefti;
        int righti;
        
        i = -1;
        res = 0;
        while (++i < arr.length)
        {
            leftResults = 0;
            rightResults = 0;
            lefti = -1;
            while (++lefti < i)
            {
                if (arr[i] % arr[lefti] == 0)
                    ++leftResults;
            }
            righti = i;
            while (++righti < arr.length)
            {
                if (arr[righti] % arr[i] == 0)
                    ++rightResults;
            }
            res += leftResults * rightResults; 
        }
        
        return res;
    }
}
