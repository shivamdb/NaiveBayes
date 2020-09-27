// Do not submit with package statements if you are using eclipse.
// Only use what is provided in the standard libraries.

import java.io.*;
import java.util.*;
import java.lang.*;

// this is my solution
public class NaiveBayes {
    Map<String, Double> spamWordCount = new HashMap<>();
    Map<String, Double> hamWordCount = new HashMap<>();
    Map<String, Double> spamWordProb = new HashMap<>();
    Map<String, Double> hamWordProb = new HashMap<>();
    int  totalSpam;
    int  totalHam;
    double probSpam;
    double probHam;

    /*
     * !! DO NOT CHANGE METHOD HEADER !!
     * If you change the method header here, our grading script won't
     * work and you will lose points!
     * 
     * Train your Naive Bayes Classifier based on the given training
     * ham and spam emails.
     *
     * Params:
     *      hams - email files labeled as 'ham'
     *      spams - email files labeled as 'spam'
     */
    public void train(File[] hams, File[] spams) throws IOException {
        totalSpam = spams.length;
        totalHam = hams.length;

        probSpam = totalSpam * 1.0 / (totalSpam + totalHam);
        probHam = totalHam * 1.0/ (totalSpam + totalHam);

        for (int i =0; i < hams.length; i++) {
            Set<String> words = tokenSet(hams[i]);
            for(String x : words) {
                if(!hamWordCount.containsKey(x)) {
                    hamWordCount.put(x, 0.0);
                }
                hamWordCount.put(x, hamWordCount.get(x) + 1);
            }
        }
        for (int i = 0; i< spams.length;i++) {
            Set<String> words = tokenSet(spams[i]);
            for(String x : words) {
                if(!spamWordCount.containsKey(x)) {
                    spamWordCount.put(x, 0.0);
                }
                spamWordCount.put(x, spamWordCount.get(x) + 1);
            }
        }

        for(String x : spamWordCount.keySet()) {
            if(!hamWordCount.containsKey(x)) {
                hamWordCount.put(x, 0.0);
            }
        }

        for(String x : hamWordCount.keySet()) {
            if(!spamWordCount.containsKey(x)) {
                spamWordCount.put(x, 0.0);
            }
        }

        for(String x : hamWordCount.keySet()) {
            hamWordProb.put(x, (hamWordCount.get(x)  +  1) /(totalHam + 2) );
        }

        for(String x : spamWordCount.keySet()) {
                spamWordProb.put(x, (spamWordCount.get(x)  +  1) /(totalSpam + 2) );
        }



       // for(String x: spamProbWord.keySet()) {
       //      if(hamProbWord.containsKe/y(x)) {
       //          int sum  = hamProbWord.get(x) + spamProbWord.get(x);
       //          spamProbWord.put(x, sum);
        //        hamProbWord(x, sum)
        //     }
        //}

        // TODO: remove the exception and add your code here
        //throw new UnsupportedOperationException("Not implemented.");
    }

    /*
     * !! DO NOT CHANGE METHOD HEADER !!
     * If you change the method header here, our grading script won't
     * work and you will lose points!
     *
     * Classify the given unlabeled set of emails. Add each email to the correct
     * label set. SpamFilterMain.java would follow the format in
     * example_output.txt and output your result to stdout. Note the order
     * of the emails in the output does NOT matter.
     * 
     *
     * Params:
     *      emails - unlabeled email files to be classified
     *      spams  - set for spam emails that needs to be populated
     *      hams   - set for ham emails that needs to be populated
     */
    public void classify(File[] emails, Set<File> spams, Set<File> hams) throws IOException {
        // TODO: remove the exception and add your code here
        double spamCond = 0;
        double hamCond = 0;
        //spamCond += Math.log(probSpam);
        //hamCond += Math.log(probHam);
        for (int i =0; i < emails.length; i++) {
            Set<String> words = tokenSet(emails[i]);
            spamCond = Math.log(probSpam);
            hamCond = Math.log(probHam);
            for(String x : words) {
                if (spamWordCount.containsKey(x)) {
                    spamCond += Math.log(spamWordProb.get(x));
                }
                if (hamWordCount.containsKey(x)) {
                    hamCond += Math.log(hamWordProb.get(x));
                }
            }
            if (spamCond > hamCond) {
                spams.add(emails[i]);
            } else {
                hams.add(emails[i]);
            }
        }
        //throw new UnsupportedOperationException("Not implemented.");
    }


    /*
     *  Helper Function:
     *  This function reads in a file and returns a set of all the tokens. 
     *  It ignores "Subject:" in the subject line.
     *  
     *  If the email had the following content:
     *  
     *  Subject: Get rid of your student loans
     *  Hi there ,
     *  If you work for us , we will give you money
     *  to repay your student loans . You will be 
     *  debt free !
     *  FakePerson_22393
     *  
     *  This function would return to you
     *  ['be', 'student', 'for', 'your', 'rid', 'we', 'of', 'free', 'you', 
     *   'us', 'Hi', 'give', '!', 'repay', 'will', 'loans', 'work', 
     *   'FakePerson_22393', ',', '.', 'money', 'Get', 'there', 'to', 'If', 
     *   'debt', 'You']
     */
    public static HashSet<String> tokenSet(File filename) throws IOException {
        HashSet<String> tokens = new HashSet<String>();
        Scanner filescan = new Scanner(filename);
        filescan.next(); // Ignoring "Subject"
        while(filescan.hasNextLine() && filescan.hasNext()) {
            tokens.add(filescan.next());
        }
        filescan.close();
        return tokens;
    }
}
