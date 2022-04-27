class LinkedList {

    private ListNode head;
    private ListNode tail;

    public LinkedList() { head = tail = null; }

    public boolean isEmpty() { return head == null; }

    public void addToHead(Object item) {
        if (isEmpty())
            head = tail = new ListNode(item);
        else
            head = new ListNode(item, head);
    }

    public void addToTail(Object item) {
        if (isEmpty())
            head = tail = new ListNode(item);
        else {
            tail.setNext(new ListNode(item));
            tail = tail.getNext();
        }
    }

    public Object removeFromHead() throws EmptyListException {
        Object item = null;
        if (isEmpty())
            throw new EmptyListException();
        item = head.getData();
        if (head == tail)
            head = tail = null;
        else
            head = head.getNext();
        return item;
    }

    public String toString() {
        String s = "[ ";
        ListNode current = head;
        while (current != null) {
            s += current.getData() + " ";
            current = current.getNext();
        }
        return s + "]";
    }

    public int count() {

        ListNode current = head;
        int i = 0;
        while (current != null) {
            i++;
            current = current.getNext();
        }
        return i;
    }

    //------------ new method ---------------
    public ListNode getListNodeAt(int n) {
        if (n < 0)
            return null;

        int currentPos=0;
        ListNode current=head;
        while (currentPos < n) {
            current=current.getNext();
            currentPos++;
        }
        return current;
    }

} // class LinkedList
