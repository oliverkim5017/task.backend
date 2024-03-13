package org.task.backend.model.vo.result;

/**
 * @author OliverKim
 * @description
 * @since 2023-08-09
 */
public enum Code {
    SUCCESS(200,"success"),
    ERROR(500,"error"),
    ;

    public final Integer CODE;

    public final String MSG;

    Code( Integer code,String msg){
        this.CODE = code;
        this.MSG = msg;
    }
}
