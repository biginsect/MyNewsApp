package com.lipeng.newapp.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by lipeng-ds3 on 2017/10/31.
 * 处理HTML代码图片显示问题
 */

public final class HTMLFormat {

    // 用于处理WebView加载html代码时，图片显示模糊问题
    public static String setNewContent(String htmltext){
        Document document = Jsoup.parse(htmltext);
        Elements elements = document.getElementsByTag("ima");
        for (Element element : elements){
            element.attr("width","100%").attr("height","auto");
        }

        return document.toString();
    }
}
