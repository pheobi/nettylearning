package com.cn.codc;

import com.cn.constant.ConstantValue;
import com.cn.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**response解码器
 *  * <pre>
 *  * 数据包格式
 *  * +——----——+——-----——+——----——+——----——+——-----——+
 *  * | 包头	| 模块号  | 命令号 | 状态码 | 长度  |   数据  |
 *  * +——----——+——-----——+——----——+——----——+——-----——+
 *  * </pre>
 *  * 包头4字节
 *  * 模块号2字节short
 *  * 命令号2字节short
 *  * 长度4字节(描述数据部分字节长度)
 * @author liyahui
 * @create 2019-06-03
 */
public class ResponseDecoder extends FrameDecoder {

    /**数据包基本长度*/
    public static int BASE_LENTH = 4+2+2+4;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        //可读长度必须大于基本长度
        if(buffer.readableBytes()>=BASE_LENTH){

            //记录包头开始的index
            int beginReader= buffer.readerIndex();

            while (true){//读取第一个字节是包头才继续下读
                if(buffer.readInt()==ConstantValue.FLAG){
                    break;
                }
            }
            //模块号
            short module = buffer.readShort();
            //命令号
            short cmd = buffer.readShort();
            //状态码
            int stateCode = buffer.readInt();
            //长度
            int length = buffer.readInt();
            //判断请求数据是否到齐
            if(buffer.readableBytes()<length){//可读长度小于数据长度，继续等待
                //还原读指针
                buffer.readerIndex(beginReader);
                //等待
                return null;
            }
            //读取data数据
            byte[] data = new byte[length];
            buffer.readBytes(data);

            Response response = new Response();
            response.setModule(module);
            response.setCmd(cmd);
            response.setStateCode(stateCode);
            response.setData(data);

            //继续往下传递
            return response;

        }
        //数据包不完整，需要等待后面的包来
        return null;
    }
}
