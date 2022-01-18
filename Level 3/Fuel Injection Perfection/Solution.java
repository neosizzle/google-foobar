import java.util.HashMap;
import java.math.BigInteger;

public class Solution {
    static HashMap<BigInteger, Integer> operationsCache = new HashMap<>();
    public static int solution (String x)
    {
        int res;
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger zero = new BigInteger("0");
        
        BigInteger pellets = new BigInteger(x);
        if (operationsCache.get(pellets)!=null)//memorization 
        {
            return operationsCache.get(pellets);
        }
        if (pellets.compareTo(two) <= 0) // pellets <= 2
            return pellets.subtract(one).intValue();
        if (zero.compareTo(pellets.and(one)) == 0)//pellets % 2 == 0
        {
            res = solution(String.valueOf(pellets.shiftRight(1))) + 1;
            operationsCache.put(pellets, res);
            return res;
        }
        res = Math.min(solution(String.valueOf(pellets.add(one))), solution(String.valueOf(pellets.subtract(one)))) + 1;
        operationsCache.put(pellets, res);
        return res;
    }