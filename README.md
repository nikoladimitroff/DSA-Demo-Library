DSA-Demo-Library
================
A library that contains basic data structures and algorithm implementations in Java for the 
purposes of the Data Structure + Algorithms course @ FMI, SU.

## Data structure comparison
The following table summarizes what we already know about the algorithmic complexity of 
the basic operations on several data structures.


| Data structure  | Get | Append | Insert | RemoveAt  | Search | Random Access? |
|-----------------|:---:|:------:|:------:|:---------:|:------:|:------:|
| Sequential List (Dynamic Array)  | `O(1)`  | `O(1)`<sub>A</sub> | `O(n)` | `O(n)` | `O(n)` | True |
| Linked List | `O(n)`  | `O(1)` | `O(1)` | `O(1)` | `O(n)` | True |
| Stack | `O(1)`  | `O(1)` | `-` | `O(1)` | `-` | False |
| Queue | `O(1)`  | `O(1)` | `-` | `O(1)` | `-` | False |

* Subscript <sub>A</sub> means that the complexity is amortized.