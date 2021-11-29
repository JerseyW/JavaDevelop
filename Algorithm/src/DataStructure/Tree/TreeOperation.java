package DataStructure.Tree;


import java.lang.reflect.*;
import java.util.HashMap;

/**
 * @author Jerssy
 * @version V1.0
 * @Description
 * @create 2020-11-15 22:12
 */
public class TreeOperation<T> {


    private final HashMap<String,Method> map= new HashMap<>();

     /*
    树的结构示例：
              1
            /   \
          2       3
         / \     / \
        4   5   6   7
    */


    // 用于获得树的层数
    public   int getTreeDepth(T root) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (root == null){
            return 0;
        }


        return  (1 + Math.max(getTreeDepth(getTreeFields(root,"left")), getTreeDepth(getTreeFields(root,"right"))));
    }


    private void writeArray(T currNode, int rowIndex, int columnIndex, String[][] res, int treeDepth,int arrayWidth) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {


        // 保证输入的树不为空
        if (currNode == null) return;
        String resNodeValue;

        T key = getTreeFields(currNode, "key");
        T value = getTreeFields(currNode, "value");
        T color=getTreeFields(currNode, "color");


        if (value!=null) {

            resNodeValue=color!=null? ((Boolean) color ?"R-"+value:"B-"+value):value+"";
        }
        else if (key!=null) {

            resNodeValue=color!=null? ((Boolean) color ?"R-"+key:"B-"+key):key+"";
        }
        else {
            System.out.println("key/value"+"不能都为空！");
            return;
        }
        res[rowIndex][columnIndex] = resNodeValue;

        // 计算当前位于树的第几层
        int currLevel = ((rowIndex + 1) / 2);
        // 若到了最后一层，则返回
        if (currLevel == treeDepth) return;
        // 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
        int gap = treeDepth - currLevel - 1;

        // 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
        T left = getTreeFields(currNode, "left");
        if (left != null) {
            int columnI = Math.max(columnIndex - gap - resNodeValue.length()/2,0);
            res[rowIndex + 1][columnI] = "/";
            writeArray(left, rowIndex + 2, Math.max(columnI-gap,0), res, treeDepth,arrayWidth);
         }

        // 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
        T right = getTreeFields(currNode, "right");
         if (right != null) {
             int columnI = Math.min(columnIndex + gap +resNodeValue.length()/2,arrayWidth-1);
             res[rowIndex + 1][columnI] = "\\";
             writeArray(right, rowIndex + 2, Math.min(columnI+gap,arrayWidth-1) , res, treeDepth,arrayWidth);
         }
    }


    public void show(T root)   {

        if (root == null){
            System.out.println("EMPTY!");
            return;
        }
        System.out.println();
        // 得到树的深度
        int treeDepth = 0;
        try {
            Class<?> treeClass = root.getClass();

            Field[] fields = treeClass.getDeclaredFields();
            Method[] declaredMethods = treeClass.getDeclaredMethods();

            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                char[] cs=name.toCharArray();
                cs[0]-=32;

                for (Method method : declaredMethods) {
                    method.setAccessible(true);
                    if (method.getName().equals("get" + String.valueOf(cs))) {
                        Method declaredMethod = treeClass.getDeclaredMethod("get" + String.valueOf(cs));
                        declaredMethod.setAccessible(true);
                        map.put(name, declaredMethod);
                    }
                }

            }
            treeDepth = getTreeDepth(root);

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }


        // 最后一行的宽度为2的（n - 1）次方乘3，再加1
        // 作为整个二维数组的宽度
        int arrayHeight = treeDepth * 2 - 1;
        int arrayWidth = (2 << (treeDepth - 2)) * 3 + 1;

        int grepWidth=arrayWidth/treeDepth;
        // 用一个字符串数组来存储每个位置应显示的元素
        String[][] res = new String[arrayHeight][arrayWidth+grepWidth];
        // 对数组进行初始化，默认为一个空格

        for (int i = 0; i < arrayHeight; i++) {
            for (int j = 0; j < arrayWidth; j++) {
                res[i][j] = " ";
            }
        }

        // 从根节点开始，递归处理整个树
        // res[0][(arrayWidth + 1)/ 2] = (char)(root.val + '0');
        try {
            writeArray(root, 0, arrayWidth / 2, res, treeDepth,arrayWidth+grepWidth);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
        for (String[] line : res) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length; i++) {
                if (line[i]!=null){
                    sb.append(line[i]);
                    if (line[i].length() > 1 && i <= line.length - 1) {
                        i += line[i].length() > 4 ? line[i].length()-2 : line[i].length() - 1;
                    }
                }

            }
            System.out.println(sb);
        }
    }

    private T getTreeFields(T node,String name) throws InvocationTargetException, IllegalAccessException {

          if (map.containsKey(name)){

              return (T) map.get(name).invoke(node);
          }

          return null;
    }

}