package sys.feedback;

import java.util.Scanner;

public class Feedback<T>{
    private T feedback;

    public Feedback(T feedback){
        setFeedback(feedback);
    }

    public void setFeedback(T feedback) {
        this.feedback = feedback;
    }
    public T getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return feedback.toString();
    }
}
