Resources report

default -Xmx250m | OutOfMemoryError on first file processing

Parameters            | Memory     | Time |
-Xmx2g                | 1956Mb map | 945s | OutOfMemoryError on sorting
-Xmx4g                | 2776Mb map | 389s
-Xmx4g -Xms4g         | 2845Mb map | 359s
-Xmx4g -Xmn2g         | 2906Mb map | 413s
-Xmx4g -XX:+UseG1GC   | 2753Mb map | 409s
-Xmx4g -XX:NewSize=2g | 3077Mb map | 405s
-Xmx8g                | 3634Mb map | 399s
