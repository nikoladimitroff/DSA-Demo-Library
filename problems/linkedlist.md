### Linked lists

```java

// Removes the the given node from the list.
void removeAt(Node node);

// Removes the first element with value @element if such an element exists and returns true. Otherwise, returns false.
boolean removeElement(int element);

// Returns a copy of the entire list.
LinkedList copy();

// Returns another list that has the same elements as the current list but in reverse order.
LinkedList reverse();

// Returns true if the current list and @other have the same size, elements and are in the same order.
boolean equals(LinkedList other);

// Returns true iff the current list is a palindrome.
boolean isPalindrome();

// Assume that both the current list and @other are sorted, merges the current list and @other to 
// produce another sorted list. For example, merging {1, 3, 5} with {2, 4, 6} should result in {1, 2, 3, 4, 5, 6}.
// If one of the inputs is not sorted, the behaviour is undefined.
LinkedList sortedMerge(LinkedList other);

// Returns a new list that has the same elements as the current one but no duplicates.
LinkedList removeDuplicates();

// Inserts all the elements of @other that are between @start and @end.
void splice(LinkedList other, Node start, Node end);

// Splits the list at the specified node. The nodes to the left @node should remain in the current list.
// The others are to be returned in another list.
LinkedList splitAt(Node node);
```