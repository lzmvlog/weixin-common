package top.lzmvlog.weixincommon.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @date 2023-02-17
 */
public class ResultVo<T> extends ResponseEntity<R<T>> {

    public ResultVo(HttpStatus status) {
        super(status);
    }

    public ResultVo(HttpStatus status, R<T> data) {
        super(data, status);
    }

    public ResultVo(R<T> data) {
        super(data, HttpStatus.OK);
    }

    public static <T> ResultVo<T> build(T data) {
        return new ResultVo<>(R.ok(data));
    }

    public static <T> ResultVo<T> success() {
        return new ResultVo<T>(R.ok());
    }

    public static <T> ResultVo<T> failed(String message) {
        return new ResultVo<T>(R.failed(message));
    }

    public static <T> ResultVo<T> failed(String message, HttpStatus status) {
        return new ResultVo<T>(status, R.failed(message));
    }

    public static <T> ResultVo<T> build(T data, HttpStatus status) {
        return new ResultVo<>(status, R.ok(data));
    }

    public static <T> ResultVo<T> build(R<T> data) {
        return new ResultVo<>(data);
    }

    public static <T> ResultVo<T> build(HttpStatus status, R<T> data) {
        return new ResultVo<>(status, data);
    }

//    @Override
//    public String toString() {
//        return JSON.toJSONString(this);
//    }
}
