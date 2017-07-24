package org.dovakin.pushserver.codec;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.dovakin.pushserver.protocol.NGLSProtocol;

import java.util.List;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
//TODO
public class BaseMessageDecoder extends MessageToMessageDecoder<NGLSProtocol> {


    protected void decode(ChannelHandlerContext channelHandlerContext, NGLSProtocol nglsProtocol, List<Object> list) throws Exception {
        String content = nglsProtocol.contentToString();
        Gson gson = new Gson();

        try{
            JsonParser parser = new JsonParser();
            JsonElement je = parser.parse(content);
            JsonObject jo = je.getAsJsonObject();
            String type = jo.get("type").getAsString();


        }catch (Exception e){

        }
    }
}
