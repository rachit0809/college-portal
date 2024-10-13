# College Portal

---
## How to run the code?
- In order to run this system, JVM should be installed on your system
- Open terminal in the sys directory and execute the following commands
  `javac Main.java`
  `java Main`
- Follow the instructions to explore the system.
---
## Assumptions made:
- User gives input of the same data-type as required.
- Course, when instantiated doesn't have a professor, the admin needs to assign a professor to the course afterwards.
- There is only one class per course per week.
- Grades are assigned by the administrator and updated by the TA incase of any errors.
- There aren't any pre-existing TAs in the system and a TA can do TA-Ship for a single course only.
- No prerequisites to become a TA.
- Professors are cool and they don't plagiarize :)
- and many more...
---
## OOPs Concepts used:

1. **Encapsulation**
   : carefully encapsulated data by making all the fields private and added necessary getter and setter methods in the code.

2. **Inheritance**
   : created parent-child class relationships so as to distribute the functional load between the parent class and child class.

3. **Abstraction**
   : created an abstract class which defines the general behaviour of a USER and enforces necessary methods in its child classes.

4. **Polymorphism**
   : used this technique to help able students view their records either by roll number or by email ID. Also used it to structurally define the class using the toString() method from the Object class and overriding it. Also used polymorphism to implement the abstract methods of the USER class.

5. **Generic Classes**
   : created a generic class for maintaining the different types of feedbacks (i.e. textual and numerical ratings), and using many instances of such feedbacks to create a feedback form.

6. **Object Class**
   : overriden the toString() to print the unique details of each Student, TA, Professor, Course, Time, Complaint, Feedback and other classes.

7. **Exception Handling**
   : robustly handled exceptions related to drop deadline, invalid login and course's student capacity being full by throwing particular exceptions that are handled everytime the function associated with such critical tasks are executed.

---
   > Credits: Rachit Bhandari, Roll Number: 2023413, Email ID: rachit23413@iiitd.ac.in, IIIT Delhi, New Delhi, India.
