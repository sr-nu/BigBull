package com.tw.utils;

public class URLUtils {
    private static String companyUrl = "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query=%s&callback=YAHOO.Finance.SymbolSuggest.ssCallback";
    private static String getQuoteUrl = "http://finance.yahoo.com/d/quote.csv?s=%s&f=sl1d1t1c1ohgv";
    private static String appEngineUrl = "http://1.latest.tamethebullandroid.appspot.com/register?key=%s1&value=%s2";

    public static String getAppEngineUrl(String key, String value) {
        return appEngineUrl.replaceFirst("%s1", key).replaceFirst("%s2", value);
    }

    public static String getCompanyUrl(String companyName) {
        return companyUrl.replaceFirst("%s", companyName);
    }

    public static String getGetQuoteURL(String symbol) {
        return getQuoteUrl.replaceFirst("%s", symbol);
    }
}
