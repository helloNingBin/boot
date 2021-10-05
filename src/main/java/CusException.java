public class CusException extends Exception {

    private String msg;

    public CusException() {
    }

    public CusException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
