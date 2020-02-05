# WordCounterForBigFile
This is a multi-threaded application reading data from big file and counting words.
Input file is in resources directory in this project.

Case 1 - Reading complete file as String in memory (Not a recommend approach for large file)
Disadvantages -
Required large Heap size required
Advantages -
Compute time might be faster

I have solved this problem using three approaches for benchmark testing 
1 - General threads using countDownLatch
2 - Using Future Tasks with ThreadPoolExecutor
3 - Using ForkJoin Pool

****************** Thread with CountDown Latch Solution ***************
Total Word Count in file=566031
Time taken In Seconds       => 2
Time taken In Micro Seconds => 2148000
Time taken In Nano Seconds  => 2148000000

****************** Executor Service Solution **************
Total Word Count in file=566031
Time taken In Seconds       => 2
Time taken In Micro Seconds => 2125000
Time taken In Nano Seconds  => 2125000000

****************** ForkJoin Solution ***************
Total Word Count in file=566031
Time taken In Seconds       => 1
Time taken In Micro Seconds => 1666000
Time taken In Nano Seconds  => 1666000000



