### Sequential / Linked lists

In the code below, `List` stands for either `SequentialList` or `LinkedList`.

```java

// Removes the element at the specified index. If @index is negative, 
// it should remove the element at list.Length + index (in other words, count from the end).
void removeAt(int index);

// Removes the first element with value @element if such an element exists and returns true. Otherwise, returns false.
boolean removeElement(int element);

// Returns a copy of the entire list.
List copy();

// Returns another list that has the same elements as the current list but in reverse order.
List reverse();

// Returns true if the current list and @other have the same size, elements and are in the same order.
boolean equals(List other);

// Returns true iff the current list is a palindrome.
boolean isPalindrome();

// Assume that both the current list and @other are sorted, merges the current list and @other to 
// produce another sorted list. For example, merging {1, 3, 5} with {2, 4, 6} should result in {1, 2, 3, 4, 5, 6}.
// If one of the inputs is not sorted, the behaviour is undefined.
List sortedMerge(List other);

// Returns a new list that has the same elements as the current one but no duplicates.
List removeDuplicates();

// Inserts all the elements of @other whose indices are >= @start and < @end into the current one.
void splice(List other, int start, int end);

// Splits the list at the specified index. The elements whose indices are less than @index should remain in the current list.
// The others are to be returned in another list.
List splitAt(int index);
```