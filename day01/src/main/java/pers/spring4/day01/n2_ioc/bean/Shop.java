package pers.spring4.day01.n2_ioc.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-15:45
 */
public class Shop {
    private String[] courses;
    private List<String> categoryList;
    private Map<String,Book> bookMap;

    private Properties prop;

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }


    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public Map<String, Book> getBookMap() {
        return bookMap;
    }

    public void setBookMap(Map<String, Book> bookMap) {
        this.bookMap = bookMap;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "courses=" + Arrays.toString(courses) +
                ", categoryList=" + categoryList +
                ", bookMap=" + bookMap +
                ", prop=" + prop +
                '}';
    }
}
