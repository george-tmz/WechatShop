package cn.wbomb.wxshop.entity;

public class Response<T> {
    private final T data;
    private String message;

    public static <T> Response<T> of(String message, T data) {
        return new Response<>(message, data);
    }

    private Response(String message, T data) {
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
