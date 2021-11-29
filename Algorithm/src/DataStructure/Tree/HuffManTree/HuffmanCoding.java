package DataStructure.Tree.HuffManTree;

import DataStructure.Tree.TreeOperation;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-04-01 19:50
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 赫夫曼编码
 *
 * 赫夫曼编码也翻译为哈夫曼编码(Huffman Coding)，又称霍夫曼编码，是一种编码方式, 属于一种程序算法
 *
 * 赫夫曼编码是赫哈夫曼树在电讯通信中的经典的应用之一。
 *
 * 赫夫曼编码广泛地用于数据文件压缩。其压缩率通常在20%～90%之间
 *
 * 赫夫曼码是可变字长编码(VLC)的一种。Huffman于1952年提出一种编码方法，称之为最佳编码
 *
 * 赫夫曼编码的原理剖析：
 *
 * 举例的字符串：
 * i like like like java do you like a java       // 共40个字符(包括空格)

 * 1  统计各个字符出现的次数
 *
 *   d:1 y:1 u:1 j:2  v:2  o:2  l:4  k:4  e:4 i:5  a:5   :9

 *
 * 2 按照上面字符出现的次数构建一颗赫夫曼树, 次数作为权值
 *
 * 3 根据赫夫曼树，给各个字符规定编码，向左的路径为0，向右的路径为1 ，各个字符最终作为赫夫曼树的叶子节点。编码如下:
 *
 * o: 1000   u: 10010   d: 100110  y: 100111  i: 101  a : 110
 * k: 1110   e: 1111    j: 0000    v: 0001    l: 001    : 01
 *
 * 4 按照上面的赫夫曼编码，我们的"i like like like java do you like a java" 字符串对应的编码(补码)为 (赫夫曼是无损压缩)
 *
 * 0101010011011110111101001101111111101001101111011110100001100001110011001111000011001111000100100100110111101111011100100001100001110
 *
 * 长度为：133
 *
 * 说明:
 * 原来长度是359 , 压缩率：(359-133) / 359 = 62.9%
 * 此编码满足前缀编码, 即字符的编码都不能是其他字符编码的前缀。不会造成匹配的多义性
 *
 * 注意, 这个赫夫曼树根据排序方法(有相同权值的时候)不同，也可能不太一样，这样对应的赫夫曼编码也不完全一样，但是树的带权路径长度(wpl)是一样的，都是最小的。
 *
 * 赫夫曼压缩注意事项：
 *
 * 1 如果文件本身就是经过压缩处理的，那么使用赫夫曼编码再压缩效率不会有明显变化, 比如视频,ppt 等等文件  [举例压一个 .ppt]
 *
 * 2 赫夫曼编码是按字节来处理的，因此可以处理所有的文件(二进制文件、文本文件) [举例压一个.xml文件]
 *
 * 3 如果一个文件中的内容，重复的数据不多，压缩效果也不会很明显.
 *
 */
public class HuffmanCoding  {

    private  static final HashMap<Byte, String> ENCODE_MAP= new HashMap<>();//编码表

    private  static  int lastLength ; //最后字节长度

    public static void main(String[] args) {
        var string="I want to get more money";

        System.out.println("**********赫夫曼编码压缩字符串**********");
        var  ENCODE_STR = huffmanEnCode(string.getBytes(StandardCharsets.UTF_8),null,null,false);
        System.out.println(Arrays.toString(ENCODE_STR));

        System.out.println("**********赫夫曼解码解析字符串**********");
        System.out.println(new String(huffmanDecode(ENCODE_STR,ENCODE_MAP)));
//        var b=-1;
//        System.out.println(Integer.toBinaryString(b));

//        byte d= 7 ;
//        System.out.println(Integer.toBinaryString(d));
//        System.out.println(Integer.parseInt("00101"));
//        System.out.println(Byte.toUnsignedInt(d));
//        System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(d)));

        System.out.println("*************使用赫夫曼编码压缩文件***********");
        var fileName="Algorithm\\src\\DataStructure\\Tree\\HuffManTree\\Files\\zipHashMapFile.text";
        var zipFileName="zipHashMapFile.zip";
        var parentDirName="Files";
       // var fileName="Algorithm\\src\\DataStructure\\Tree\\HuffManTree\\Files\\waitNotify.png";
       // var zipFileName="waitNotify1.zip";
        //var upZipName="waitNotify1.png";
        huffmanEnCode(getFileBytes(fileName),zipFileName,parentDirName,true);

        var upZipName="zipHashMapFile1.text";

        huffmanDecode(upZipName,zipFileName,parentDirName, false);

    }


    /*
     * @param: [fileName]
     * @return: byte[]
     * @author: Jerssy
     * @dateTime: 2021/4/4 13:06
     * @description: 读取个文件返回字节数组
     */
     private static byte[]  getFileBytes(String fileName){
         try {
             return Files.readAllBytes(Paths.get(fileName));

         } catch (IOException e) {
             e.printStackTrace();
         }

         return null;
     }


     /*
      * @param: [fileName, zipFileName,parentDirName,isDeleteZip]
      * @return: void
      * @author: Jerssy
      * @dateTime: 2021/4/4 13:54
      * @description: 写入解压字节到文件中 fileName:解压后的文件名：zipFileName:需要解压的解压包名，ParentDirName:将解压后的文件放到哪个目录下;isDeleteZip是否删除解压包
      */
     private  static  void  huffmanDecode(String fileName,String zipFileName,String parentDirName,boolean isDeleteZip){

         var path = Paths.get(System.getProperty("user.dir"));
         var file = new File(String.valueOf(path));
         var  unZipPath = getFilePath(file, parentDirName)+File.separator+fileName;
         try ( var fileChannel=FileChannel.open(Paths.get(unZipPath), StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING)){

             var  zipPath = getFilePath(file, zipFileName);
             if (zipPath!=null){

                 var in = new ObjectInputStream(new FileInputStream(zipPath.toString()));
                 var  zipBytes = (byte[])in.readObject();
                 @SuppressWarnings("unchecked")
                 var  huffmanEnCode = (HashMap<Byte,String>)in.readObject();
                 var  deCodeBytes = huffmanDecode(zipBytes,huffmanEnCode);
                 var byteBuffer=ByteBuffer.allocate(deCodeBytes.length);
                 byteBuffer.put(deCodeBytes);
                 byteBuffer.flip();
                 fileChannel.write(byteBuffer);
                 in.close();
                 if(isDeleteZip) Files.delete(zipPath);//解压完需要时删除解压文件
             }
             else  {
                 System.out.println("The zip packet is empty!");

             }

         }
         catch (IOException | ClassNotFoundException e) {
           e.printStackTrace();
         }

     }

     /*
      * @param: [zipName，parentDirName,huffmanEnCodeArr]
      * @return: java.nio.file.Path
      * @author: Jerssy
      * @dateTime: 2021/4/4 19:34
      * @description: 获取压缩文件路径并写入字节数组；parentDirName--需要将压缩文件放在哪个目录下,zipName:压缩文件名；huffmanEnCodeArr：压缩字节数组
      */
     private  static void createZipFile(String zipName,String parentDirName, byte[] huffmanEnCodeArr){

         if (zipName==null||parentDirName==null) return;

         try {
             Files.walkFileTree(Paths.get(System.getProperty("user.dir")), new SimpleFileVisitor<>() {

                 @Override
                 public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                     if (dir.endsWith(".idea")){
                         return  FileVisitResult.SKIP_SUBTREE;
                     }

                     if (dir.getFileName().endsWith(parentDirName)){
                         var zipPath=Path.of(dir + File.separator+ zipName);
                         if (Files.notExists(zipPath)){
                             Files.createFile(dir.resolve(zipName));
                         }

                         zipFile(zipPath,huffmanEnCodeArr);
                         return  FileVisitResult.TERMINATE;
                     }

                     return FileVisitResult.CONTINUE;
                 }
             });
         }catch (IOException e) {
             e.printStackTrace();
         }

     }


    /*
     * @param: [zipPath,encodedBytes]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/4 18:44
     * @description: 将压缩文件放入指定的文件下
     */
    private static void  zipFile(Path zipPath,byte[] encodedBytes){

        try(var out = new ObjectOutputStream(new FileOutputStream(String.valueOf(zipPath)))) {
            out.writeObject(encodedBytes);
            out.writeObject(ENCODE_MAP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /*
      * @param: [file, name]
      * @return: java.nio.file.Path
      * @author: Jerssy
      * @dateTime: 2021/4/4 21:05
      * @description: 获取指定文件名的路径
      */
    private static Path getFilePath(File file, String name) {

        var  files = file.listFiles();
        if (files == null)
            return null;

        Path path=null;

        for(var s:files){

            if(s.isFile()&&s.getName().endsWith(name)){

                return s.toPath();
            }

            if(s.isDirectory()&&!s.getName().endsWith(".idea")){

                   if (s.getName().endsWith(name)){
                       return  s.toPath();
                   }

                   else if (path == null)
                      path=getFilePath(s,name);

                   else  return path;
            }

        }
        return path;
    }

    /*
     * @param: [list]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/2 21:20
     * @description: 统计字符路径编码
     */
    private static void getTreePathCodeStatus(List<TreeNode<Byte>> list) {


        var build=new StringBuilder();

        list.forEach(node -> {

            var currentNode=node;
            while (currentNode.parent!=null){//由于字符位于叶子节点则直接向上找父节点即可得出路径
                build.insert(0, currentNode.parent.left== currentNode ?'0':'1');
                currentNode = currentNode.parent;
            }

            if (node.key!=null){
                ENCODE_MAP.put(node.key, build.toString());
                build.delete(0, build.length());
            }
        });


        //getTreePathCodes(root,"",build,encodeMap);
    }

    private  static  void   getTreePathCodes(TreeNode<Byte> node,String code, StringBuilder builder){

        var stringBuilder=new StringBuilder(builder);
        stringBuilder.append(code);
        if (node!=null){
           if (node.key == null){

               getTreePathCodes(node.left, "0", stringBuilder);
               getTreePathCodes(node.right, "1", stringBuilder);
           }
           else ENCODE_MAP.put(node.key, stringBuilder.toString());
        }
    }

    /*
     * @param: [byteArray]
     * @return: java.util.HashMap<java.lang.Character,java.lang.Integer>
     * @author: Jerssy
     * @dateTime: 2021/4/2 9:40
     * @description:  统计每个字符出现的次数
     */
    private static HashMap<Byte, Integer> getByteCount(byte[] byteArray){

         var map=new HashMap<Byte, Integer>();
         for (var cb : byteArray) {

             //map.merge(cb, 1, (k, v)->(map.get(cb)+1));

            map.compute(cb, (k,v)->map.getOrDefault(cb, 0)+1);
        }

        return map;
    }

    /*
     * @param: [byteArray,list]
     * @return: DataStructure.Tree.HuffManTree.HuffmanCoding.TreeNode<java.lang.Character>
     * @author: Jerssy
     * @dateTime: 2021/4/2 10:38
     * @description:创建赫夫曼树
     */
    private static TreeNode<Byte>  createHuffmanTree(byte[] byteArray,List<TreeNode<Byte>> list){

        var map= getByteCount(byteArray);//统计的字符将作为赫夫曼树的叶子节点

        var  priorityQueue=new PriorityQueue<TreeNode<Byte>>(Comparator.comparingInt(e->e.value));

        map.forEach((k,v)-> {

            TreeNode<Byte> characterTreeNode = new TreeNode<>(k, v);

            priorityQueue.add(characterTreeNode);
            list.add(characterTreeNode);

        });

       while (priorityQueue.size() >1){

           var nodeLeft = priorityQueue.poll();
           var nodeRight = priorityQueue.poll();
           if (nodeLeft!=null&&nodeRight != null){

               var nodeParent=new TreeNode<Byte>(null,nodeLeft.value+nodeRight.value);
               nodeParent.left=nodeLeft;
               nodeParent.right=nodeRight;

               nodeLeft.parent=nodeParent;
               nodeRight.parent=nodeParent;
               priorityQueue.remove(nodeRight);
               priorityQueue.remove(nodeLeft);

               priorityQueue.add(nodeParent);
           }
       }

        return priorityQueue.poll();
    }

    /*
     * @param: [byteArray,name,parentDirName,isCreateFile]
     * @return: byte[]
     * @author: Jerssy
     * @dateTime: 2021/4/3 16:46
     * @description: 赫夫曼编码
     */
    public static  byte[] huffmanEnCode(byte[] byteArray,String name,String parentDirName,boolean isCreateFile){

            Objects.requireNonNull(byteArray);

            if (byteArray.length==0){
                System.out.println("The file is empty!");
                return null;
            }

            var list = new ArrayList<TreeNode<Byte>>();
            //生成赫夫曼树
            var root= createHuffmanTree(byteArray,list);


            //显示赫夫曼树
            if(byteArray.length<30){
                new TreeOperation<TreeNode<Byte>>().show(root);
            }

            //获取赫夫曼路径二进制字符串
           getTreePathCodeStatus(list);

         return encode(byteArray,name,parentDirName,isCreateFile);

    }

    /*
     * @param: [byteArray,name,parentDirName,isCreateFile]
     * @return: byte[]
     * @author: Jerssy
     * @dateTime: 2021/4/3 19:26
     * @description:编码实现->转成字节数组
     */
    private static byte[] encode(byte[] byteArray,String name,String parentDirName,boolean isCreateFile){

        var builder=new StringBuilder();

        for (var b : byteArray) {
            if (ENCODE_MAP.containsKey(b)){

                builder.append(ENCODE_MAP.get(b));
            }
        }
        //赫夫曼字节数组 1byte=8bit 1 int=32bit 1long=64bit----所以以8进行压缩
        var huffmanEnCodeArr=new  byte[(builder.length()+7)/8 ];
        var  byteNum=0;

        lastLength=builder.length()&7;

        for (var i = 0; i < builder.length(); i+=8) {

            //如果是1开头则代表负数的补码符号位以外-1--->对应反码-->取反得原码

            var intCode= Integer.parseInt(builder.substring(i, Math.min(i + 8, builder.length())), 2);

            huffmanEnCodeArr[byteNum++]= (byte) (intCode);
        }

        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(3);

        //需要时才生成文件
        if (isCreateFile) createZipFile( name,parentDirName,huffmanEnCodeArr);

        System.out.println("压缩成功，压缩率："+format.format(((float)byteArray.length-huffmanEnCodeArr.length)/(float) byteArray.length));

        return huffmanEnCodeArr;
    }


    /*
     * @param: [encodedArr,encodedMap]
     * @return: java.lang.String
     * @author: Jerssy
     * @dateTime: 2021/4/3 16:47
     * @description:赫夫曼解码
     */
    public  static  byte[] huffmanDecode(byte[] encodedArr,HashMap<Byte,String> encodedMap){


        StringBuilder encodedStr= new StringBuilder();

        //将字节数组转成二进制字符编码串
        for (var i = 0; i < encodedArr.length; i++) {

            encodedStr.append(decodeBytesToInt(encodedArr[i],i==encodedArr.length-1));
        }


        //获取解码表
        var decodedMap=new HashMap<String,Byte>();//解码表

        encodedMap.forEach((Key,Value)-> decodedMap.put(Value,Key));
        encodedMap.clear();

        return  decode(decodedMap, encodedStr);
    }

    /*
     * @param: [decodedMap, encodedStr]
     * @return: byte[]
     * @author: Jerssy
     * @dateTime: 2021/4/3 19:48
     * @description: 解码实现
     */
    private  static byte[] decode(HashMap<String,Byte> decodedMap, StringBuilder encodedBuilder){

        int startIndex=0;

        var list= new ArrayList<Byte>();

        for (var i = 1; i < encodedBuilder.length()+1; i++) {
            String str;

            if (decodedMap.containsKey(str=encodedBuilder.substring(startIndex, i))){

                list.add(decodedMap.get(str));
                startIndex=i ;

            }
        }

        //解压后的字节数组
        var bytes =new byte[list.size()];
        for (var i = 0; i < bytes.length; i++) {
            bytes[i]=list.get(i);
        }

        list.clear();
        decodedMap.clear();

        System.out.println("解压成功");

        return  bytes;
    }

    /* 
     * @param: [bytes,isLast]
     * @return: java.lang.String
     * @author: Jerssy
     * @dateTime: 2021/4/3 20:09
     * @description: isLast 补位标志符，如果true 需要补高位
     */
    private static String decodeBytesToInt1 ( byte  bytes,boolean isLast) {

        var intCode=bytes;
        if (!isLast ) {// 如果不是最后一个字节需要高位补0，主要处理正数，因为正数补码中0会被省略
            intCode|=256;// 按位或 256  1 0000 0000  | 0000 0001 => 1 0000 0001 除了高位改变其他如果是正数会补全0，负数后8位不变
        }

        var s=Integer.toBinaryString(intCode);//得到二进制补码字符串--一般windows计算机里的整数是用32位二进制补码表示，负数则是32位但byte对应的bit长度为8则需要取最后8位，正数前的0会被省略导致不足8位需要补0,|256补位后在取后8位

      //处理最后一个字节是正数，可能不足8位。因为其本身长度就没有8位,但是在使用补码的时候原来前面的0会被忽略掉，所以要补齐
        if(isLast&&s.length()<=lastLength) {

            return  "0".repeat(lastLength-s.length()).concat(s);
        }

        //这里注意如果int|256后的8位的值是0开头，经过toBinaryString()方法后的高位的符号位0也会被省略所以还要补齐0
        return s.length()<8?"0".repeat(8-s.length())+s:s.substring(s.length()-8);

    }

    /*
     * @param: [bytes,isLast]
     * @return: java.lang.String
     * @author: Jerssy
     * @dateTime: 2021/4/3 20:09
     * @description: isLast最后位标志符
     */
    private static String decodeBytesToInt( byte bytes,boolean isLast) {

        var s=Integer.toBinaryString(Byte.toUnsignedInt(bytes));//toUnsignedInt转成无符号位整型，如果是负数二进制则为8位，如果是正数或者0，会存在高位丢失不满足8位的情况

        if (isLast&&s.length() <=lastLength){//最后一位如果是正数或者本身就是0则需要和压缩前时的最后一位长度比较。
            // 假如压缩前最后位为00111，现在转成二进制高位的0都被省略后是111。长度差2，这种需要补2个0，
            // 假如压缩前最后位为111，现在转成二进制后也是111。长度相等,这种无需补0
            //处理最后一个字节是正数或者是0，可能不足8位。因为其在压缩的时候长度就不够8位,但是在使用补码的时候原来前面的0会被忽略掉，所以要补齐
            return  "0".repeat(lastLength-s.length())+s;
        }
        //负数底层是32位，无符号处理后肯定长度为8,则不会拼接，如果是正数或者0经过toBinarySting处理高位会丢失导致不满足8位的情况则需要拼接
        return  "0".repeat(8-s.length())+s;

    }

   public static class TreeNode<K> implements Comparable<TreeNode<K>>{

         private final int value;
         private final K key;
         private  TreeNode<K> left;
         private    TreeNode<K> right;
         private   TreeNode<K> parent;

        public  TreeNode(K key,int weight){
            this.value = weight;
            this.key = key;
         }

       public K getKey() {
           return key;
       }

       public int getValue() {
           return value;
       }

       public TreeNode<K> getLeft() {
           return left;
       }

       public TreeNode<K> getRight() {
           return right;
       }

       @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    ", key=" + key + '}';
        }

        @Override
        public int compareTo(@NotNull HuffmanCoding.TreeNode<K> o) {
            return this.value-(o.value);
        }
    }
}
