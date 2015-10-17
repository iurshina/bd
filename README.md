# Resources report

default -Xmx250m | OutOfMemoryError on first file processing

Parameters            | Memory     | Time 
--------------------- | ---------- |-----
-Xmx2g                | 1956Mb     | 945s OutOfMemoryError on sorting
-Xmx4g                | 2776Mb     | 389s
-Xmx4g -Xms4g         | 2845Mb     | 359s
-Xmx4g -Xmn2g         | 2906Mb     | 413s
-Xmx4g -XX:+UseG1GC   | 2753Mb     | 409s
-Xmx4g -XX:NewSize=2g | 3077Mb     | 405s
-Xmx8g                | 3634Mb     | 399s
