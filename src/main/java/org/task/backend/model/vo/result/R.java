package org.task.backend.model.vo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * @author OliverKim
 * @description
 * @since 2023-08-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class R {

    private String msg;

    private Integer code;

    private Object data;


    public  static R success(){
        return new R(Code.SUCCESS.MSG,Code.SUCCESS.CODE,null);
    }

    public  static R success(String msg){
        return new R(msg,Code.SUCCESS.CODE,null);
    }

    public  static R success(Object data){
        return new R(Code.SUCCESS.MSG,Code.SUCCESS.CODE,data);
    }

    public  static R success(String msg, Integer code , Object data){
        return new R(msg,code,data);
    }

    public static R success(String msg, Object data){
        return new R(msg,Code.SUCCESS.CODE,data);
    }

    public static R updateFailed(){
        return new R("更新失败",Code.ERROR.CODE,null);
    }

    public static R deleteFailed(){
        return new R("删除失败",Code.ERROR.CODE,null);
    }

    public static R saveFailed(){
        return new R("保存失败",Code.ERROR.CODE,null);
    }

    public  static R error(String msg){
        return new R(msg,Code.ERROR.CODE,null);
    }

    public static R badRequest() {
        return new R("参数错误", HttpStatus.BAD_REQUEST.value(),null);
    }

    public static R badRequest(String msg) {
        return new R(msg, HttpStatus.BAD_REQUEST.value(),null);
    }

    public static R notFound() {
        return new R(null , HttpStatus.NOT_FOUND.value(),null);
    }

}
