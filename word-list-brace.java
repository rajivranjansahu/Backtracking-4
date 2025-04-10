// TC: O(n * 2^n) - in the worst case, we have to generate all combinations of characters
// SC: O(n) - for the recursion stack and the result list
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Solution {
    List<String> result;
    
    public String[] expand(String S) {
        result = new ArrayList<>();
        List<List<Character>> blocks = new ArrayList<>();
        char[] sArr = S.toCharArray();
        int i = 0;
        
        // Build blocks from the input string.
        while (i < sArr.length) {
            List<Character> temp = new ArrayList<>();
            if (sArr[i] == '{') {
                i++; // Skip the '{'
                while (i < sArr.length && sArr[i] != '}') {
                    if (sArr[i] != ',') {
                        temp.add(sArr[i]);
                    }
                    i++;
                }
            } else {
                temp.add(sArr[i]);
            }
            blocks.add(temp);
            i++; // Move past '}' or the single letter.
        }
        
        backtrack(blocks, new StringBuilder(), 0);
        String[] returnArray = result.toArray(new String[0]);
        Arrays.sort(returnArray);
        return returnArray;
    }
    
    private void backtrack(List<List<Character>> blocks, StringBuilder sb, int index) {
        // Base case: when all blocks have been processed.
        if (index == blocks.size()) {
            result.add(sb.toString());
            return;
        }
        
        // For each character in the current block, add it and recurse.
        for (char c : blocks.get(index)) {
            sb.append(c);
            backtrack(blocks, sb, index + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
