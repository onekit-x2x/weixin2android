package cn.onekit.w3c;

import cn.onekit.js.JsObject;

import cn.onekit.js.JsString;
import cn.onekit.js.JsObject_;

public class Event implements JsObject_ {
    private Element currentTarget;
    private JsObject detail;
    //private Dict  mark;
  //  private boolean mut;
    private Element target;
    private int timeStamp;
    private String type;
    //
    public Event(
            String type,
            JsObject detail,
     //       Dict  mark,
      //   boolean mut,
            Element currentTarget,
            Element target,
         int timeStamp)
    {
        this.currentTarget=currentTarget;
        this.detail=detail;
       // this.mark=mark;
     //   this.mut=mut;
        this.target=target;
        this.timeStamp=timeStamp;
        this.type=type;
    }
    //
    public Element getCurrentTarget(){
        return currentTarget;
    }
    public JsObject getDetail(){
        return detail;
    }
   /* public Dict getMark(){
        return mark;
    }*/
   /* public boolean getMut(){
        return mut;
    }*/
    public Element getTarget(){
        return target;
    }
    public int getTimeStamp(){
        return timeStamp;
    }
    public String getType(){
        return type;
    }
   /* public Event(String type,Dict detail){
        this.type=type;
        this.detail = detail;
    }*/

    @Override
    public String toString() {
        return String.format("\"currentTarget\":%s,\"detail\":%s,\"mark\":%s,\"mut\":%s,\"target\":%s,\"timeStamp\":%s,\"type\":\"%s\"}",
                currentTarget,
                detail,
                "mark",
                "mut",
                target,
                timeStamp,
                type);
    }

    @Override
    public JsString ToString() {
        return null;
    }
}
