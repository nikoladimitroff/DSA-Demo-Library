Homework 1
==========
## Homework information
Due date: 23:59:59, 27th of November, 2014.

You are to submit a single **.zip** archive named **groupNumber.fn.hw1.zip**
where **groupNumber** is your group and **fn** is your faculty number.
Upload the archive to moodle.

This is ok: ***group1.66666.hw1.zip***

This is not: ***domashno1.rar***

Your zip archive must contain only **.java** files - no folders, no other files.

Additionally, **you'll improve your grade** by +1 if you commit your work in github
and +2 if you send us a pull a request at 
* https://github.com/NikolaDimitroff/DSA-Demo-Library for groups 1 and 2
* https://github.com/jorenca/SDA-Tasks for groups 4 and 5

## Task - File Encryption

You are given this interface:

```java
import java.util.LinkedList;

/**
 * You have to implement this class. The encryption algorithm is described below.
 * Replace FN with your faculty number.
 * @author Georgi Gaydarov
 */
public interface FileEncoderFN { 
    /**
     * Encodes a file with the specified key and saves the result to a given file.
     * @param sourceFile - path to the initial file
     * @param destinationFile - path to the result file
     * @param key - list of replacement bytes
     */
    public void encode(String sourceFile, String destinationFile, LinkedList<Byte> key);
    
    /**
     * Decodes a file that was encoded with the above algorithm.
     * @param encodedFile - path to encoded file
     * @param destinationFile - path to the result file
     * @param key - list of replacement bytes that were used to encode the file
     */
    public void decode(String encodedFile, String destinationFile, LinkedList<Byte> key);
}
```
If your faculty number is *123456* then your class should be called `FileEncoder123456`.

You are given a path to a file which has to be encoded.
You are also given a path where to write the resulting file.
Finally, you are given a list of 256 bytes, each different from the others.
Your task is to implement the encryption and decryption methods described above
using the following algorithm:

```
For each byte b in the source file:
    if b is a prime number or 1:
        write b to the destination file as is
    else:
        write to the destination file the value of the b-th element of the key
```
Here's an example. Let's say you are given the following source file and key:

**Source file**: 4 3 ...

**Key**: 255 254 253 252 251 ...

Then the output file should start like this:

**Output**: 251 3 ...

Since 4 is not a prime, we replace it with the 4th element of the key (251).
On the other hand, 3 is a prime number so we leave it as is.

To put it another way:

The first, second, third, fifth, seventh, etc... byte in the input file is preserved.
The fourth, sixth, eighth, ninth, etc. byte in the input file is encoded.
Your class should be able to encode files in this manner and decode them accordingly.

## Grading scale

We'll run your solution on several tests and compare it with ours using the following
formula:

`performance = sum(yourSolutionTime[i] / authorSolutionTime[i]) / testCount`
(`i` is for every test example)

Your marks will be assigned using this sample code:

```java
int grade = 2;
if (performance > 0.85)
    grade = 6;
else if (performance > 0.7)
    grade = 5;
else if (performance > 0.6)
    grade = 6;
else if (performance > 0.5)
    grade = 3;
```

If any of your tests gives a wrong result, you'll get 0 points for it.

If you cheat by copying your solution from a classmate or the internet, you'll get 0
points for the **ALL** tests.

## Notes
* The files you are to encode will be no larger than 300kb.