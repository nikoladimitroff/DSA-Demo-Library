# Hashtables

```java
// Removes the element with the given @key.
void remove(String key);

const double MIN_WORKLOAD = /* your call */;
// If the hashtable's workload is less than MIN_WORKLOAD,
// shrinks the underlying arrays and moves all elements to the new table.
void TryShrink();
```

## Bigger tasks:

1. Create a new implementation of the hash table, this time use open addressing (closed hashing).
Use a probing function that hides the probing algorithm. Implement linear and quadratic probing.

2. Test the implementations. How many probes does the open addressing scheme require with linear
probing when the workload is 60%, 80%, 90%, 95%, 97%, 99%? What about quadratic scheme?
How does it fare against the closed addressing scheme?
