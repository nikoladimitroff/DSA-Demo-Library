Final course projects
======================

All of the problems are *hard*. They require some research but because of
that you've got plenty of time. Feel free to ask us about hints.

* Your grade depends on 3 components - functionality, performance & code quality.
* You are **not** required to write in Java. All of the following languages are
acceptable - Java, C#, C / C++, Python, JS.
* If none of the projects below fit you, you may suggest another one. Contact your
teaching assistant.
* Your grade will be finalized after a successful defence.
* Besides implementing the algorithms below, you **must** provide a demo application
that can demonstrate that it works correctly.
* The implementation's design is up to you. You can make a console app, a GUI
mobile / desktop app, web app, a smart washing machine showing the result on its
display. To make testing easier, it's a good idea to add an option to read the input
from file.


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
into the crypt on a long-forgotten emperor he found countless treasures but he could
only carry no more than *N* kilograms of loot on the way back. Help him make
the most of the situation by solving the famous
[*knapsack problem*](http://en.wikipedia.org/wiki/Knapsack_problem). Since
he'll be using your program for some time, you are required to solve the problem
using **both** dynamic programming **AND** branch & bound.

You must also be able to read the input from a file with the following structure:

```
item value weight
```

For example:

```
chocolate 10 2
golden-horn 100 10
Skyrim 99999999999 5
```

### Red-black AVL
[AVL](http://en.wikipedia.org/wiki/AVL_tree) trees are a form of self-balancing
binary search trees.
They are incredibly efficient and are widely used when implementing ordered
dictionaries. Your job is to implement:

* insertion
* removal
* searching
* size
* iteration

(and of course, the self-balancing part).

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

### Treap

A [treap](https://en.wikipedia.org/wiki/K-d_tree) is another form of a
self-balancing tree with some interesting properties. Namely, a treap
containing a fixed set of elements will always have the same structure,
no matter what order that elements were inserted in. It also makes subtree
sharing efficient which means set operations are pretty quick (union, intersection,
set difference). Your task is to implement the following operations on treaps:

* Insertion
* Removal
* Size
* Find (test whether the element is in the treap)
* Treap union
* Treap intersection
* Treap difference

### KD-tree

When making geometrically-intensive applications such as games, movies
and architectural visualizations it is crucial to use fast algorithms.
A key geometrical algorithm is to detect whether a point lies within
a box or a circle. This is usually done by looping over all shapes
and checking whether the point lies within it. The [kd-tree](https://en.wikipedia.org/wiki/K-d_tree)
is a space-partitioning data structure that significantly improves this check
by reducing the amount of shapes to test with.

Your task is to implement the a program that can find all objects
that a given point intersects with. Implement the check whether the 2D point
lies within a circle and an axis-aligned rectangle. Use a kd-tree to
discover the shapes to loop over.

To make testing easier, your program should be able to read a file in the following format:

```
rect x y w h # defines a rectangle with top-left point at (x, y) with width w and height h
circle x y r # defines a circle with center (x, y) and radius r
```

For example
```
rect 0 100 200 300 # a rectangle that starts at (0, 100) with width 200 and height 100
circle 100 200 300 # circle centered at (100, 200) with radius 300
```

This file will be used to describes all geometrical objects and your program should allow
the user to test for various points.

You'll get bonus points for actually drawing the scene.

### Bloom filter

A bloom filter is a probabilstic data structure - it lets
you check whether an element has been added to the collection
(with a certain probability) in constant time but requires
the usage of multiple hash functions.

See detailed [description here](https://en.wikipedia.org/wiki/Bloom_filter).
The bloom filter uses an array of booleans internally. To improve its performance
one can replace the boolean array with a [bitset](https://en.wikipedia.org/wiki/Bit_array).
This also reduces the memory usage by a factor of 8 because one only stores each boolean
value in a single bit instead of a byte.

Your task is to implement both i.e. your Bloom filter must use a bitset internally.
We expect the following operations in the Bloom filter.

* Insertion
* Find (check whether an element has been added)
* CurrentProbability - returns the probability that the bloom filter's result is correct

You do **not** have to implement removal.
