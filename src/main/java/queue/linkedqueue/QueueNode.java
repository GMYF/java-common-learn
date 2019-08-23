package queue.linkedqueue;

/**
 * 队列
 * @author zz.li
 */
public class QueueNode {
    int data;
    QueueNode next;

    public QueueNode(int data) {
        this.data = data;
        next = null;
    }
}

class LinkedListQueue{
    /**
     * 队头指针
      */
    public QueueNode first;


    /**
     * 队尾指针
     */
    public QueueNode end;

    /**
     * 插入队列
     * @param data
     * @return
     */
    public Boolean inqueue(int data){
        QueueNode node = new QueueNode(data);
        if(end==null){
            first = node;
        }else{
            end.next=node;
        }
        end = node;
        return  true;
    }

    /**
     * 删除队列
     * @return
     */
    public int dequeue(){
        int value ;
        if(first!=null){
            //空队列
            if(first==end){
                //将队尾置为空
                end = null;
            }
            value = first.data;
            first = first.next;
            return  value;
        }else{
            return  -1;
        }
    }

    public static void main(String []args){
        int [] arrayList = {1,2,3,4,5,6,7,8,9,10};
        LinkedListQueue linkedListQueue = new LinkedListQueue();
        for(int i=0;i<arrayList.length;i++){
            linkedListQueue.inqueue(arrayList[i]);
        }
    }
}