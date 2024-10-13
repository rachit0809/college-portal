package sys.feedback;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FeedbackForm {
    private Feedback<Integer> courseRating;
    private Feedback<String> courseReview;
    private Feedback<Integer> profRating;
    private Feedback<String> profReview;
    private final Scanner input = new Scanner(System.in);

    public FeedbackForm(){
        System.out.println("Feedback Form");
        setFeedbackForm();
    }

    private void setFeedbackForm(){
        boolean t;
        t = true;
        while (t){
            System.out.println("Enter course rating (Input a number of the scale of 5): ");
            try{
                int inpt = input.nextInt();
                input.nextLine();
                if (inpt >= 0 && inpt <= 5) t = false;
                else {
                    System.out.println("Wrong rating!");
                    continue;
                }
                courseRating = new Feedback<>(inpt);
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input received. Kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while (t){
            System.out.println("Enter course review: ");
            try{
                String inpt = input.nextLine();
                t = false;
                courseReview = new Feedback<>(inpt);
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input received. Kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while (t){
            System.out.println("Enter professor rating (Input a number of the scale of 5): ");
            try{
                int inpt = input.nextInt();
                input.nextLine();
                if (inpt >= 0 && inpt <= 5) t = false;
                else {
                    System.out.println("Wrong rating!");
                    continue;
                }
                profRating = new Feedback<>(inpt);
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input received. Kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while (t){
            System.out.println("Enter professor review: ");
            try{
                String inpt = input.nextLine();
                t = false;
                profReview = new Feedback<>(inpt);
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input received. Kindly try again.");
                input.nextLine();
            }
        }
    }

    @Override
    public String toString() {
        return ("Feedback Form"+"\nCourse Rating: "+this.courseRating.getFeedback()+"\nCourse Review: "+this.courseReview.getFeedback()+"\nProfessor Rating: "+this.profRating.getFeedback()+"\nProfessor Review: "+this.profReview.getFeedback());
    }
}
