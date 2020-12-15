package cn.onekit.weixin;

import java.lang.reflect.Array;
import java.util.Map;

import cn.onekit.js.JsArray;
import cn.onekit.js.JsBoolean;
import cn.onekit.js.JsObject;

public class FileSystemManager {
    public void access(Map OBJECT){

    }
    public void accessSync(JsObject path){

    }
    public void appendFile(Map OBJECT){

    }
    public void appendFileSync(JsObject filePath, JsObject data, JsObject encoding){

    }
    public void copyFile(Map OBJECT){

    }
    public void copyFileSync(JsObject srcPath, JsObject destPath){

    }
    public void getFileInfo(Map OBJECT){

    }
    public void getSavedFileList(Map OBJECT){

    }
    public void mkdir(Map OBJECT){

    }
    public void mkdirSync(JsObject dirPath, JsBoolean recursive){

    }
    public void readdir(Map OBJECT){

    }
    public JsArray readdirSync(JsObject dirPath){
          return null;
    }
    public void readFile(Map OBJECT){

    }
    public JsObject readFileSync(JsObject filePath, JsObject encoding, JsObject position, JsObject length){
        return null;
    }
    public void removeSavedFile(Map OBJECT){

    }
    public void rename(Map OBJECT){

    }
    public void renameSync(JsObject oldPath, JsObject newPath){

    }
    public void rmdir(Map OBJECT){

    }
    public void rmdirSync(JsObject dirPath, JsBoolean recursive){

    }
    public void saveFile(Map OBJECT){

    }
    public JsObject saveFileSync(JsObject tempFilePath, JsObject filePath){
        return null;

    }
    public void stat(Map OBJECT){

    }
    public Stats statSync(JsObject path, JsBoolean recursive){
        return null;
    }
    public void unlink(Map OBJECT){

    }
    public void unlinkSync(JsObject filePath){

    }
    public void unzip(Map OBJECT){

    }
    public void writeFile(Map OBJECT){

    }
    public void writeFileSync(JsObject filePath, JsObject data, JsObject encoding){

    }


    private class Stats {
        private JsObject mode;
        private JsObject size;
        private JsObject lastAccessedTime;
        private JsObject lastModifiedTime;

        public JsObject getMode() {
            return mode;
        }

        public JsObject getSize() {
            return size;
        }

        public JsObject getLastAccessedTime() {
            return lastAccessedTime;
        }

        public JsObject getLastModifiedTime() {
            return lastModifiedTime;
        }
        public JsBoolean isDirectory(){
            return null;
        }
        public JsBoolean isFile(){
            return null;
        }
    }
}
