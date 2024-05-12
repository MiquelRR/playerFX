package com.example.playerfx;

public class ScrollText {
    int shownChars;
    String string;
    String doubleString;
    int cursor;
    public ScrollText(int shownChars, String string) {
        this.shownChars = shownChars;
        this.string = " ".repeat(shownChars)+string;
        this.doubleString=this.string+this.string;
        this.cursor = 0;
    }

    public String getInstantString(){
        if(++this.cursor>this.string.length()) this.cursor=0;
        return this.doubleString.substring(cursor,cursor+shownChars);
    }
    
}
