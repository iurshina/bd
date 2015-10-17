import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Anastasiia_Iurshina
 */
public class HadoopTest {

    public void test() {
        try {
            FileSystem hdfs = FileSystem.get(new Configuration());

            Map<String, Integer> map = formMap(hdfs);
            Map<String, Integer> sortedMap = sortByValue(map);
            writeResults(hdfs, sortedMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Integer> formMap(final FileSystem hdfs) throws IOException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 6; i < 13; i++) {
            String fileName = "/tmp/bid.201306" + (i >= 10 ? i : "0" + i) + ".txt";
            BufferedReader br = new BufferedReader(new InputStreamReader(hdfs.open(new Path(fileName))));
            try {
                String line = br.readLine();
                while (line != null) {
                    String[] values = line.split("\\s+");
                    line = br.readLine();

                    if (values.length < 3) {
                        continue;
                    }

                    String currentId = values[2];
                    if (currentId.equals("null")) {
                        continue;
                    }
                    Integer count = map.get(currentId);
                    map.put(currentId, count == null ? 1 : ++count);
                }

                System.out.println(i);
            } finally {
                br.close();
            }
        }
        return map;
    }

    private void writeResults(final FileSystem hdfs, final Map<String, Integer> sortedMap) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(hdfs.create(new Path("/tmp/bid_result.txt"))));
        try {
            for (Map.Entry<String, Integer> pairs : sortedMap.entrySet()) {
                out.write(pairs.getKey() + "  " + pairs.getValue() + " \n");
            }
        } finally {
            out.close();
        }
    }

    /**
     * DESC order.
     */
    private static Map<String, Integer> sortByValue(final Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
