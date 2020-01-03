public class FrameWork {
    private volatile static FrameWork mFrameWork;

    public FrameWork() {
    }

    public static FrameWork getFrameWork() {
        if (mFrameWork == null) {
            synchronized (FrameWork.class) {
                if (mFrameWork == null) {
                    mFrameWork = new FrameWork ();
                }
            }
        }
        return mFrameWork;
    }
}
