package cn.wbomb.wxshop.entity;

public class Response<T> {
    private T data;
    private String message;

    public static <T> Response<T> of(String message, T data) {
        return new Response<>(message, data);
    }

    public static <T> Response<T> of(T data) {
        return new Response<T>(null, data);
    }

    public Response() {
    }

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
