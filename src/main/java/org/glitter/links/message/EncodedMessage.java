package org.glitter.links.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.annotation.Nonnull;

import java.nio.charset.StandardCharsets;

/**
 * 编码消息,用以与设备通信
 * @author xiaohan.yuan
 * */
public interface EncodedMessage {

     @Nonnull
     Buffer getPayload();

    default String payloadAsString() {
        return getPayload().toString(StandardCharsets.UTF_8);
    }
    default JsonObject payloadAsJson(){
        return getPayload().toJsonObject();
    }
    default JsonArray payloadAsJsonArray() {
        return getPayload().toJsonArray();
    }

    default byte[] payloadAsBytes() {
        return getPayload().getBytes();
    }

    default byte[] getBytes(int offset, int len) {
        return getPayload().getBytes(offset, len);
    }

}
