package com.ll;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Util {

    //파일에 자동 저장하는 함수
    public static void saveToFile(String path, String body) {
        try (RandomAccessFile stream = new RandomAccessFile(path, "rw");
             FileChannel channel = stream.getChannel()) {
            System.out.println("position"+channel.position());
            byte[] strBytes = body.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
            buffer.put(strBytes);
            buffer.flip();
//            buffer.reset();
            channel.write(buffer);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveFile(String path, String body){
        FileWriter fw = null;
        try {
            fw = new FileWriter(path, true);
            fw.write(body);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void mkdir(String path) {
        new File(path).mkdir();
    }

    public static String readFromFile(String path, String defaultValue) {
        try (RandomAccessFile reader = new RandomAccessFile(path, "r")) {
            StringBuilder sb = new StringBuilder();

            String line;

            boolean isFirst = true;

            while ((line = reader.readLine()) != null) {
                if (isFirst == false) {
                    sb.append("\n");
                }

                sb.append(new String(line.getBytes("iso-8859-1"), "utf-8"));

                isFirst = false;
            }

            return sb.toString();

        } catch (FileNotFoundException e) {
            return defaultValue;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class json {
        public static Map<String, Object> jsonToMapFromFile(String path) {
            String json = readFromFile(path, "");

            if (json.isEmpty()) {
                return null;
            }

            final String[] jsonBits = json
                    .replaceAll("\\{", "")
                    .replaceAll("\\}", "")
                    .split(",");

            final List<Object> bits = Stream.of(jsonBits)
                    .map(String::trim)
                    .flatMap(bit -> Arrays.stream(bit.split(":")))
                    .map(String::trim)
                    .map(s -> s.startsWith("\"") ? s.substring(1, s.length() - 1) : Integer.parseInt(s))
                    .collect(Collectors.toList());

            Map<String, Object> map = IntStream
                    .range(0, bits.size() / 2)
                    .mapToObj(i -> Pair.of((String) bits.get(i * 2), bits.get(i * 2 + 1)))
                    .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue(), (key1, key2) -> key1, LinkedHashMap::new));

            return map;
        }
        public static List<Map<String, Object>> jsonMapFromFile(String path) {
            String json = readFromFile(path, "");

            if (json.isEmpty()) {
                return null;
            }

            String[] list = json.split("},");
//            System.out.println(Arrays.toString(list));

            final String[] jsonBits = json
                    .replaceAll("\\{", "")
                    .replaceAll("\\}", "")
                    .split(",");

            System.out.println(Arrays.toString(jsonBits));
            List<Map<String, Object>> map =  new ArrayList<Map<String, Object>>();
            for(String line : jsonBits) {
                final List<Object> bits = Stream.of(line)
                        .map(String::trim)
                        .flatMap(bit -> Arrays.stream(bit.split(":")))
                        .map(String::trim)
                        .map(s -> s.startsWith("\"") ? s.substring(1, s.length() - 1) : Integer.parseInt(s))
                        .collect(Collectors.toList());

                Map<String, Object> tmp = IntStream
                        .range(0, bits.size() / 2)
                        .mapToObj(i -> Pair.of((String) bits.get(i * 2), bits.get(i * 2 + 1)))
                        .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue(), (key1, key2) -> key1, LinkedHashMap::new));
                map.add(tmp);
            }
            System.out.println(map.toString());

//            final List<Object> bits = Stream.of(jsonBits)
//                    .map(String::trim)
//                    .flatMap(bit -> Arrays.stream(bit.split(":")))
//                    .map(String::trim)
//                    .map(s -> s.startsWith("\"") ? s.substring(1, s.length() - 1) : Integer.parseInt(s))
//                    .collect(Collectors.toList());
//
//            Map<String, Object> map = IntStream
//                    .range(0, bits.size() / 2)
//                    .mapToObj(i -> Pair.of((String) bits.get(i * 2), bits.get(i * 2 + 1)))
//                    .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue(), (key1, key2) -> key1, LinkedHashMap::new));

            return map;
        }
    }

}
