DSA-Demo-Library
================
A library that contains basic data structures and algorithm implementations in Java for the 
purposes of the Data Structure + Algorithms course @ FMI, SU.

## In-class exercises

### Sequential / Linked lists

In the code below, `List` stands for either `SequentialList` or `LinkedList`.

```java
// Removes the first element with value @element if such an element exists and returns true. Otherwise, returns false.
boolean RemoveElement(int element);

// Removes the element at the specified index. If @index is negative, 
// it should remove the element at list.Length + index (in other words, count from the end).
void RemoveAt(int index);

// Returns a copy of the entire list.
List Copy();

// Returns another list that has the same elements as the current list but in reverse order.
List Reverse();

// Returns true if the current list and @other have the same size, elements and are in the same order.
boolean Equals(List other);

// Returns true iff the current list is a palindrome.
boolean IsPalindrome();

// Assume that both the current list and @other are sorted, merges the current list and @other to 
// produce another sorted list. For example, merging {1, 3, 5} with {2, 4, 6} should result in {1, 2, 3, 4, 5, 6}.
// If one of the inputs is not sorted, the behaviour is undefined.
List SortedMerge(List other);

// Returns a new list that has the same elements as the current one but no duplicates.
List RemovedDuplicates();

// Inserts all the elements of @other whose indices are >= @start and < @end into the current one.
void Splice(List other, int start, int end);

// Splits the list at the specified index. The elements whose indices are less than @index should remain in the current list.
// The others are to be returned in another list.
List SplitAt(int index);
```