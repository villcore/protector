//package com.villcore.protector;
//
//import com.alipay.remoting.exception.RemotingException;
//import com.alipay.remoting.rpc.RpcClient;
//import com.villcore.protector.rpc.request.ActionRequest;
//
//public class ClientTest {
//
//    public static void main(String[] args) {
//        RpcClient client = new RpcClient();
//        client.startup();
//
//
//        try {
//            client.invokeSync("127.0.0.1:12345", new ActionRequest(), 100);
//        } catch (RemotingException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
