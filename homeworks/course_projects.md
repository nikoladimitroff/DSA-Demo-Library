Final course projects
======================
Choose one of the projects below and fill this [form](http://goo.gl/forms/qrVNhLJ42I)
BEFORE 11.01.2015, 23:59:59. If you fail to do so, you'll be assigned a project
at random.

All of the problems are *hard*. They require some research but because of
that you've got plenty of time. Feel free to ask us about hints.

* Your grade depends on 3 components - functionality, performance & code quality
* You are **not** required to write in Java.
* If none of the projects below fit you, you may suggest another one.
* Your grade will be finalized after a successful defence during the last exerice or 2
(your teaching assistant will give you more information)


### Mini-mathematica
Write a program that properly computes mathematical expressions over the following
operations and functions:
* arithmetic - addition, substraction, negation, multiplication, division
* advanced - exponentiation, nth root, logarithms
* trigonometric - sine, cosine, tangent, cotangent

You should also include support for common constants such as pi and e.

One example expression would be:

```
5 + sin(pi) / pow(2, 10) - log(e, pow(e, sqrt(4)))
```
Your program should output `3` on the example above.

Use [shunting yard](http://en.wikipedia.org/wiki/Shunting-yard_algorithm) and
[reverse polish notation](http://en.wikipedia.org/wiki/Reverse_Polish_notation).

### Indiana Jones
Indiana Jones often faces difficult dilemmas in his travels. On his last journey
into the crypt on a long-lost emperor he found countless treasures but he could
only carry no more than *N* kilograms of loot on the way back. Help him make
the most of the situation by solving the famous
[*knapsack problem*](http://en.wikipedia.org/wiki/Knapsack_problem). Since
he'll be using your program for some time, you are required to solve the problem
using **both** dynamic programming **AND** branch & bound.

### Red-black AVL
[Red-black](http://en.wikipedia.org/wiki/Red%E2%80%93black_tree) and
[AVL](http://en.wikipedia.org/wiki/AVL_tree) trees are a form of self-balancing
binary search trees.
They are incredibly efficient and are widely used when implementing ordered
dictionaries. Your job is to implement:
* insertion
* removal
* searching
* size

for both trees (and of course, the self-balancing part).

### We are up all night to get hacky
[One-time padding (OTP)](http://en.wikipedia.org/wiki/One-time_pad) is a secure,
but easy to break cryptographic algorithm if it is misused. All it does is given
a input message `m` and a key `k` is to XOR them to produce the encrypted message
`e` (`e = m ^ k`). Due to properties of XOR, decrypting the message is done simplyYou
with `m = e ^ k`. Unfortunately, if the same key is used to encrypted more than
one sentence there's a relatively easy to way to get back the key and decrypt them
all. Your task is precisely that.

You'll be given several sentences encrypted with the same key. Your task is to
decipher all of them and tell us the the secret message encrypted within. (hint: consider
XORing a random ASCII letter with a space (ASCII code 32))

### Regularize yourself
Regular expressions are an incredibly useful tool in the arsenal of every programmer.
In last year's course in Discrete Mathematics you saw how to implement them
using automata. Your job is to put the theory into practice. Implement the following
operations on regular expressions using standard Deterministic and Nondeterministic
Finite Automata:

* Concatenation / union / negation / intersection / Klenee's star on automata
* Building an automaton from a regular expression
* Conversion of an NFA into DFA
* Minimizing DFA
* Recognizing words using DFA
* Recognizing words using NFA

For example, your program might accept a regular expression and a string and
tell whether the string matches the regex:

```
regex = "aa*(bbb|ccc)"
str = "aaaaaaaaccc"
output -> true

regex = "aa*(bbb|ccc)"
str = "abcc"
output -> false
```

Feel free to contact me (Nikola Dimitroff) in case you need extra guidance, resources
or other info on this task.

### Maximum flow
Implement [Ford-Fulkerson's algorithm](http://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm)
which computes the maximum flow of a given graph. Design tests / demo.

### JPEG
Implement the compression algorithm used in JPEG (which is a Fast Fourier Transform).
Your program should be able to display the colours of pixels in any given JPEG
image. You **don't need to draw the actual colours**, it is enough to show their
values as a text - for instance by
displaying a two-dimensional array with the RGBA values of each pixel.

Your program should also be able to further compress a JPEG image using a greater
level of compression.
Some resources:
http://www.programirane.org/ - Page 674
http://www.ctralie.com/PrincetonUGRAD/Projects/JPEG/jpeg.pdf
