package com.kiscode.ui.pojo;


/**** 
 * Description: 
 * Author:  keno
 * CreateDate: 2020/10/12 23:36
 */

public class TabItem {

    private String title;
    private String viewClasName;

    public TabItem(String title, String viewClasName) {
        this.title = title;
        this.viewClasName = viewClasName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewClasName() {
        return viewClasName;
    }

    public void setViewClasName(String viewClasName) {
        this.viewClasName = viewClasName;
    }
}
