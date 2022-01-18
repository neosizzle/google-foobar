import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Arrays;
public class Solution {
    public static int compare_custom(String s1, String s2)
    {
        int i;
        int s1_curr_token;
        int s2_curr_token;
        String []s1_token = s1.split("\\.", -1);
        String []s2_token = s2.split("\\.", -1);
        
        i = -1;
        while (++i < s1_token.length)
        {
            //overflow check and set valeus
            if (i >= s2_token.length)
                return 1;
            s1_curr_token = Integer.parseInt(s1_token[i]);
            s2_curr_token = Integer.parseInt(s2_token[i]);
            
            //compare and return
            if (s1_curr_token > s2_curr_token)
                return 1;
            else if (s1_curr_token < s2_curr_token)
                return -1;
        }
        if (s2_token.length > s1_token.length)
            return -1;
        return 0;
    }
    public static String[] solution(String[] list)
    {
        ArrayList <String> list_arr = new ArrayList<>();
        
        for(String str : list)
            list_arr.add(str);
        Collections.sort(list_arr, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return compare_custom(lhs, rhs);
            }
        });
        return list_arr.toArray(new String[0]);
    }
}