package controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.pages.Page;

public class DashboardLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardLoader.class);

    /**
     * @param page page
     * @return constructed params
     */
    public static String buildParam(Page<?> page) {

        StringBuilder sb = new StringBuilder();
        try {
            if (page.getSearch() != null) {
                sb.append("&search=" + URLEncoder.encode(page.getSearch(), "UTF-8"));
            }

            if (page.getColumnSort() != null) {
                sb.append("&sort=" + URLEncoder.encode(page.getColumnSort(), "UTF-8"));
            }

            if (page.getOrder() != null) {
                sb.append("&order=" + page.getOrder().toString());
            }

            // sb.append("&sort=" + URLEncoder.encode(page.getSearch(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("fail encoding");
        }

        return sb.toString();
    }
}
