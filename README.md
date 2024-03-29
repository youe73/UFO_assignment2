# UFO_assignment2

Assignment 2

Download or copy the script to your Java editor. If you have downloaded from UFO repo,it should have maven...etc. Otherwise just copy the new method and lines added into your script, as there is not much change from the original script. You should be in a directory where you can have a folder resources containing the FoundationSeries.txt. Replace the path name with your absolute path name. And you should be able go.
When you run the script you can try both method (tallychars) and the improved one (newsolution)
The best thing is to close all the running applications. 
Open a terminal and navigate to the folder where your script is. 
- You could do a mvn clean install to see if everything is ok. 
- And also mvn compile.

If everything is running successfully type the following:

- mvn exec:java "-Dexec.mainClass"="cphbusiness.ufo.letterfrequencies.Main" -q



Reports from the investigation and analysis

The current time performance is conducted in the way that time measurement is placed inside the main method after the file access.
The reason why the Reader instantiation is not included is that it can affect the time performance, as for other CPU activities there might
be running. The file access has nothing to do with code performance. Other applications should be shut down and the
java application should be runned from the command line (maven command).
So the time measurement start from initialization of the hashmap. In order to create a robust measurement performance, comparison is made 10 times and outputs an average

299
261
174
148
122
118
112
131
112
127
average 160



Profiler
It can be seen using the profiler that the sampling taken for the methodlist, is indicating that main method and tallyChars has the highest number.

![image](https://user-images.githubusercontent.com/40825848/68544593-27d60700-03c5-11ea-8b5c-59e713982d77.png)

So it leads to the conclusion that the bottleneck might be in tallyChars. The Profiler further shows that the InputStreamReader is high usage,
so my hypothesis of where the problem occurs is in the readfile and hashmap.
The solution I could provide to optimize the time performance is done with the following modification. It simply just used a BufferedInputStream.

private static void newsolution(String fileName, Map<Integer, Long> freq)
    {
                BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(fileName));
            int b = 0;

            while ((b = in.read()) != -1) {
                try {
                    freq.put(b, freq.get(b) + 1);
                } catch (NullPointerException np) {
                    freq.put(b, 1L);
                }
            }
        } catch (IOException ex1) {
            System.out.println(ex1);

        }
    }


163
136
179
82
80
62
64
95
95
66
average 102


Using BufferedInputStream instead of the reader gave a better time performance from 131 to 73.
Looking at the profiler again after the improvement, it shows that the HashMap.put is accounting for high usage.
To optimize on the hashmap solution the following code is provided:

![image](https://user-images.githubusercontent.com/40825848/68544628-6e2b6600-03c5-11ea-9422-0bd3fc266ca0.png)

Old code: freq.put(b, freq.get(b) + 1);

New code: freq.computeIfPresent(b, (Integer key, Long value) -> ++value);

This computeIfPresent is a smarter method to use as: 

The computeIfPresent() method takes the key and a remapping function, which, in turn, computes the value only if the key is present. 
So, the remapping function is only called if the key is present in the map, otherwise not. 
The optimizing of the 2 places in the script has improved the overall time measurement as follows.  

61
28
52
12
8
9
11
15
9
15
average 22

![image](https://user-images.githubusercontent.com/40825848/68544635-84392680-03c5-11ea-8894-442722e0e843.png)


Judging by the median and mean from the illustration below, it shows that the performance lies on the lower side of the box, which means that this represent the fast numbers.


![image](https://user-images.githubusercontent.com/40825848/68544698-3ffa5600-03c6-11ea-9fcd-05b1c26ff2a1.png)






