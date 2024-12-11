package com.example.testapplication.Service;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HtmlService
{
    public static void DeSerializeHtmlText(String text, AppCompatActivity activity)
    {
        Document document = Jsoup.parse(text);

        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TraverseNode(document, activity, linearLayout);

        // 设置活动的布局为我们创建的线性布局
        activity.setContentView(linearLayout);
    }


    public static void TraverseNode(Node node, AppCompatActivity activity, LinearLayout linearLayout)
    {
        // 如果是元素节点（例如 <h1>, <div> 等）
        if (node instanceof Element)
        {
            Element element = (Element) node;
            // 打印当前元素的标签名及其属性（如果有）
            System.out.println("Element: " + element.tagName());

            // 遍历元素的所有属性
            element.attributes().forEach(attribute ->
            {
                System.out.println("  Attribute: " + attribute.getKey() + "=" + attribute.getValue());
            });

            // 如果是 <p>, <li> 等元素，我们可以提取文本内容
            if (element.hasText())
            {
                System.out.println("  Text: " + element.text());
            }

            switch (element.tagName())
            {
                case "h1":
                    var textView = new TextView(activity);
                    textView.setText(element.text());  // 设置文本内容
                    textView.setTextSize(32);  // 设置文本大小
                    textView.setGravity(Gravity.CENTER);  // 设置文本居中显示
                    textView.setPadding(20, 20, 20, 20);  // 设置内边距

                    // 将 TextView 添加到布局中
                    linearLayout.addView(textView);


                    break;

                case "button":
                    Button button = new Button(activity);
                    button.setText(element.text());
                    button.setGravity(Gravity.CENTER);

                    linearLayout.addView(button);
                    break;
            }

            // 递归遍历该元素的子节点
            for (Node child : element.childNodes())
            {
                TraverseNode(child, activity, linearLayout);
            }
        }
        else if (node instanceof org.jsoup.nodes.TextNode)
        {
            // 处理文本节点
            org.jsoup.nodes.TextNode textNode = (org.jsoup.nodes.TextNode) node;
            System.out.println("Text Node: " + textNode.text());
        }
    }

    public static void GetCssRel(String text)
    {

        Document document = Jsoup.parse(text);

        Elements linkTags = document.select("link[rel=stylesheet]");

        String[] cssHrefs = new String[linkTags.size()];
        for(int i =0; i<  cssHrefs.length; i++)
        {
            cssHrefs[i] = linkTags.get(i).attr("href");
            // 如果 href 是相对路径，拼接成完整的 URL
            //                if (cssHref.startsWith("/"))
//                {
//                    cssHref = htmlUrl.substring(0, htmlUrl.lastIndexOf("/")) + cssHref;
//                }
        }

    }

}


