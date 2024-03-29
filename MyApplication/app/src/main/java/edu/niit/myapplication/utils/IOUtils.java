package edu.niit.myapplication.utils;

import android.util.Xml;

import com.alibaba.fastjson.JSON;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import edu.niit.myapplication.entity.ExerciseDetail;

public class IOUtils {
    public static String convert(InputStream is, Charset encode) {
        try {
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> convert(String json, Class<T> cls) {
        return JSON.parseArray(json, cls);
    }
    //读取xml的习题
    public static List<ExerciseDetail> getXmlContents(InputStream is) throws Exception {
        List<ExerciseDetail> details = null;
        ExerciseDetail detail = null;
        //创建一个解析器
        XmlPullParser parser = Xml.newPullParser();
        //设置输入源
        parser.setInput(is, StandardCharsets.UTF_8.toString());
        //根据eventype类型，判断节点名称，解析数据，将输入放入集合
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("infos".equals(nodeName)) {
                        details = new ArrayList<>();

                    } else if ("exercises".equals(nodeName)) {
                        detail = new ExerciseDetail();
                        String ids = parser.getAttributeValue(0);
                        detail.setSubjectId(Integer.parseInt(ids));
                    } else if ("subject".equals(nodeName) && detail != null) {
                        detail.setSubject(parser.nextText());
                    } else if ("a".equals(nodeName)) {
                        detail.setA(parser.nextText());
                    } else if ("b".equals(nodeName)) {
                        detail.setB(parser.nextText());
                    } else if ("c".equals(nodeName)) {
                        detail.setC(parser.nextText());
                    } else if ("d".equals(nodeName)) {
                        detail.setD(parser.nextText());
                    } else if ("answer".equals(nodeName)) {
                        detail.setAnswer(Integer.parseInt(parser.nextText()));
                    }
                    break;
                    case XmlPullParser.END_TAG:
                        if ("exercises".equals(nodeName) && details !=null){
                            details.add(detail);
                        }
                        break;
            }
            eventType = parser.next();
        }
        return details;
    }
}

