public class RURottenTomatoes{
    public static void main(String[] args){
        int reviewers = Integer.parseInt(args[0]);
        int movies = Integer.parseInt(args[1]);
        int[] input = new int[args.length];
        int[] sum = new int[movies];
        int i;
        int j;
        int index = 0;
        for (i = 0; i < args.length; i++){
            String arg = args[i];
            int n = Integer.parseInt(arg);
            input[i] = n;
        }
        int[][] tom = new int[reviewers][movies];
        int z = 2;
        for(i = 0; i < reviewers; i++){
            for(j = 0; j < movies; j++){
                tom[i][j] = input[z];
                z++;
            }
        }
        for (i = 0; i < movies; i++){
            sum[i] = 0;
        }
            for(i = 0; i < movies; i++){
                for(j = 0; j < reviewers; j++){
                    sum[i] = sum[i] + tom[j][i];
                }
            }
        int max = sum[0];
        for (i = 0; i < sum.length; i++) 
	    {
		    if (max < sum[i]) 
		    {
			    max = sum[i];
			    index = i;
		    }
	    }
        System.out.println(index);
    }
}