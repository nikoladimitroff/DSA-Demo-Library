Final course projects
THIS IS A DRAFT
======================

### Mini-mathematica
Write a program that properly computes mathematical expressions over the following
operations and functions:
* arithmetic - addition, substraction, negation, multiplication, division
* advanced - exponentiation (nth root), logarithms
* trigonometric - sine, cosine, tangent, cotangent

You should also include support for common constants such as pi and e.

One example expression would be:

```
5 + sin(pi) / pow(2, 10) - log(e, pow(e, sqrt(4))) = 3
```

Use [shunting yard](http://en.wikipedia.org/wiki/Shunting-yard_algorithm) and
[reverse polish notation](http://en.wikipedia.org/wiki/Reverse_Polish_notation).

### Become Indiana Jones
Indiana Jones often faces difficult dilemmas in his travels. On his last journey
into the crypt on a long-lost emperor he found countless treasures but he can
only carry no more than *N* kilograms of loot on the way back. Help him make
the most of the situation by solving the famous
[*knapsack problem*](http://en.wikipedia.org/wiki/Knapsack_problem). Since
he'll be using your program for some time, you are required to solve the problem
using **both dynamic programming AND branch & bound**.

### Implement a Red-black and an AVL tree
Red-black and AVL trees are a form of self-balancing binary search trees.
They are incredibly efficient and are widely used when implementing ordered
dictionaries. Your job is to implement:
* insertion
* removal
* searching
* size

for both trees (and of course, the self-balancing part).

### We are up all night to get hacky
[One-time padding (OTP)](http://en.wikipedia.org/wiki/One-time_pad) is a secure,
but easy to break cryptographic algorithm if it is misused. You'll be given a
several sentences encrypted with the same key. Your task is to decipher all of
them and tell us the the secret message encrypted within them. (hint: consider
XORing a random ASCII letter with a space (ASCII code 32))

