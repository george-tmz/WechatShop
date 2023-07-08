package cn.wbomb.wxshop.entity;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    int code;
    String body;
    Map<String, List<String>> headers;
}
