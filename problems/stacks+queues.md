#Stacks
```java

// Returns the minimum element of the stack WITHOUT DESTROYING IT
int min();

// Returns true if @expression is a valid mathematical expression. 
// Examples: 
// [(2 + 3) * {7 / 7}] + 3 * 3 is ok
//  [2 + 3) * (7 / 7] is not
static boolean isExpressionValid(String expression);

// Describe, design and test a way to use a stack to log the function calls
// your program goes trough. Create several functions to test that


// You are given an expression in Reverse Polish Notation. Evaluate it using two stacks.
// Examples:
// 5 2 + 3 * = 21
// 3 3 / 1 1 - * = 0
static int evaluateRPNExpression(string expression);
```

# Queues 
```java
// Returns the @nth member of the Fibonacci sequence using a queue.
static int fibonacci(int n);

// Merge the two sorted queues.
// Example: sortedMerge({0, 5, 10, 15}, {2, 13, 14, 20 }) -> {0, 2, 5, 10, 13, 14, 15, 20}
static Queue sortedMerge(Queue first, Queue second);

// Last time you saw Josephus' problem which is known as 
// the Hot Potato game in modern days. Use a queue to return
// the last man standing.
static string hotPotato(SequentialList people);
```