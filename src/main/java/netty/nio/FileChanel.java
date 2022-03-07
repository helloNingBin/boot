package netty.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author:chichao
 * @date:年月日
 */
public class FileChanel {

    public static void main(String[] args) throws Exception{
        serverSocket();
    }

    static void t1()throws Exception{
        File file = new File("C:\\Users\\chichao\\Desktop\\log\\t18.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel inChannel = raf.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buffer);
        System.out.println("position:" + buffer.position());
        while (bytesRead != -1){
            System.out.println("bytesRead:" + bytesRead);
            buffer.flip();

            while (buffer.hasRemaining()){
                System.out.println((char)buffer.get());
            }

            buffer.clear();
            bytesRead = inChannel.read(buffer);
        }
        raf.close();
    }
    static void selector()throws Exception{
        Selector selector = Selector.open();
        selector.selectNow();
    }
    static void socketChanel()throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",6667));
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        //write to buffer
        byteBuffer.put(new byte[]{97,98,99,100,101,102,103});
        byteBuffer.flip();//读和写是相反的
        while (byteBuffer.hasRemaining()){
            socketChannel.write(byteBuffer);
        }
        //read from buffer
        byteBuffer.clear();
        System.out.println("waiting msg.");
        socketChannel.read(byteBuffer);
        System.out.println("has msg.");
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
        socketChannel.close();
    }
    static void serverSocket()throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);
        while (true){
            System.out.println("waiting new connet");
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("connect come.socketChannel:"  + socketChannel);

        }
    }
}
