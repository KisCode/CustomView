package com.kiscode.ui.pojo;


/**** 
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/12 23:36
 */

public class TabItem {

    private String title;
    private String className;

    public TabItem(String title, String className) {
        this.title = title;
        this.className = className;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
