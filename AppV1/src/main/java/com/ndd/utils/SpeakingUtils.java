/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Admin
 */
public class SpeakingUtils {

    public static List<String> extractQuestionsFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        return doc.select("p, li").stream()
                .map(Element::text)
                .filter(q -> !q.isBlank())
                .collect(Collectors.toList());
    }
}
