import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

public class Solution {
    
	/*
	** Generate initial state for past states.
	** 1. Create a 2d grid with curr state width and height + 1
	** 2. fill grid with true , we will use this grid to change values and 
	**	find unique solutions
	**
	** @param boolean[][] state - The original state 
	** @return boolean[][] res - The first previous state
	*/
    static boolean[][] genInitState(boolean[][] state)
    {
        boolean [][]res;
        
        res = new boolean[state.length + 1][state[0].length + 1];
        for(boolean[]arr : res)
            Arrays.fill(arr, true);
        return res;
    }
    
	/*
	** Helper function to generate sub array given a starting index.
	** The start index will start counting from the end if given a 
	** negative value.
	**
	** @param int start_idx - The start index
	** @param boolean[] array - the array to splice
	** @return boolean[] res - the spliced array
	*/
    static boolean[] genSubArray(int start_idx, boolean[]array)
    {
        boolean[] res;
        int i;
        
        if (start_idx > array.length)
            start_idx = array.length;
        if (start_idx < 0)
        {
            if (Math.abs(start_idx) >= array.length)
                start_idx = 0;
            else
                start_idx = array.length + start_idx;
        }
        res = new boolean[array.length - start_idx];
        i = 0;
        while (start_idx < array.length)
            res[i++] = array[start_idx++];
        return res;
    }
    
	/*
	** Helper function to convert an arraylist to native boolean array.
	**
	** @param ArrayList<Boolean> arr - The ArrayList to convert
	** @return boolean []res - The native array
	*/
    static boolean[] alToArr(ArrayList<Boolean> arr)
    {
        boolean []res;
        int i;
        
        res = new boolean[arr.size()];
        i = -1;
        for (boolean bool : arr)
            res[++i] = bool;
        return res;
    }
    
	/*
	** Function that determines if the current past state at [i][j] can be modified
	**
	** 1. elem_past will check the past state in accordance to the survival rules
	**	given by subject. 
	** 2. elem_states will check the top right offset of current state
	** 3. must be either all True(c[0][0] and 1 cell) or all False (!c[0][0] and !1 cell)
	**
	** @param boolean [][]past - The past state
	** @param boolean cell_type - The cell type (dead or alive)
	** @param boolean [][]state - The current state
	** @param int i - Current row to inspect
	** @param int j - current column to inspect
	** @return boolean - Is current past state modifiable?
	*/
    static  boolean genSet(boolean [][]past, boolean cell_type, boolean [][]state, int i, int j)
    {
        boolean elem_states;
        boolean elem_past;
                
        elem_past = ((past[i][j == 0? past[i].length - 2 : j - 1] ? 1 : 0)
        + (past[i == 0? past.length - 2 : i - 1][j] ? 1 : 0)
        + (past[i == 0? past.length - 2 : i - 1][j == 0? past[i].length - 2 : j - 1] ? 1 : 0)
        + (cell_type? 1 : 0)
        == 1
        );
       
        elem_states = ((state[i == 0? state.length - 2 : i - 1][j == 0? state[i].length - 2 : j - 1] ? 1 : 0)
        == 1
        );
        return elem_states == elem_past;
    }
    
	/*
	** Algorithm core, generates answers and uses memorization
	**
	** 1. Check for column overflow
	** 2. Create a unique key based on move history and current coords
	** 3. Check if that key is in cache. Return value of cache if yes
	** 4. Loop through the two cell types
	** 	1. Check if current index is modifyiable. if yes,
	**		1. Add to history for the current cell type
	**		2. Change the past state to current cell type
	**		3. Recurse and move row and column to next suitable position
	**		4. Remove history to allow overriding
	** 5. Store newly obtained result in cache
	**
	** @param boolean [][]past - The past state
	** @param HashMap<String, Integer>cache - Result cache for memorization
	** @param ArrayList<Boolean> history - To store modification history
	** @param boolean [][]state - The current state
	** @param int i - Current row to inspect
	** @param int j - current column to inspect 
	** @return int res - The number of possible combinations of prev states
	*/
    static int generateAnswer(boolean [][] state, int i, int j, boolean [][]past,  HashMap<String, Integer>cache, ArrayList<Boolean> history)
    {
        int     res;
        String  key;
        boolean [] cell_types = {true, false};
        boolean sc;

        //overflow base case
        if (j == state[0].length + 1)
            return 1;
            
        res = 0;
        key = "(" + i + ", " + j + "), " + Arrays.toString(genSubArray(-(state.length + 2), alToArr(history)));
        if (cache.get(key) != null)
            return cache.get(key);
        for (boolean cell_type : cell_types)
        {
            if ((i == 0 || j == 0) || genSet(past, cell_type, state, i, j))
            {
                history.add(cell_type);
                past[i][j] = cell_type;
                res += generateAnswer(state, (i + 1) % (state.length + 1), j + (i + 1) / (state.length + 1), past, cache, history);
                history.remove(history.size() - 1);
            }
        }
        cache.put(key, res);
        return res;
    }

	/*
	** Entry point
	*/
    public static int solution(boolean[][] state) {
        boolean [][] init_state;
        HashMap<String, Integer> cache;
        ArrayList<Boolean> history;
        
        init_state = genInitState(state);
        cache = new HashMap<>();
        history = new ArrayList<>();
        return generateAnswer(state, 0, 0, init_state, cache, history);
    }
}