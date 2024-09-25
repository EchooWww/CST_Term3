### **Definitions, Expressions, and Values**

- **Functions**: A function always returns the same value given the same input. For example, `getChar()` in C is not a strictly functional behavior because its result can vary. In functional languages, true functions are deterministic.
- **Expressions**: These are evaluated to produce values. For example, `1 + 2` is an expression that evaluates to `3`.
- **Strong Typing**: OCaml is a strongly typed language with type inference, meaning the compiler deduces types automatically. However, types are strictly enforced, so `1 + 1.1` will cause an error because `1` is an integer and `1.1` is a float. To handle floats, you need to use float-specific operators, such as `1. +. 1.1`.
- **Definitions**: A definition binds a value but does not evaluate to a value itself. For example, `let x = 1` defines `x` as `1` but the definition is not an expression.

---
### **Evaluation Rules and Immutability**

- **Function Call Precedence**: In OCaml, function calls bind tighter than expressions. This means `square 5 + 1` is interpreted as `(square 5) + 1`, not `square (5 + 1)`.
- **Immutability**: In functional languages, every value (often referred to as "variables") is immutable, meaning once a value is bound to a name, it cannot be changed. If a value is no longer used, it will be garbage collected.
- **OCaml's Purity**: While OCaml allows mutation in some cases, in purely functional languages (like Haskell), mutation is not allowed at all.

---
### **Recursion and Loops**

- **Recursion Instead of Loops**: Functional languages do not have traditional loops. Instead, all iterative processes are done using recursion. This can consume a lot of memory as the stack grows with each recursive call.
- **Tail Recursion**: A tail-recursive function is optimized for memory efficiency. In tail recursion, the recursive call is the last operation in the function, allowing the stack to be reused, preventing stack growth.
```ocaml
// Regular recursion
let rec fact n = 
  if n <= 0 then 1 
  else n * fact(n-1)

// Tail-recursive version
let rec fact' n acc = 
  if n <= 0 then acc
  else fact' (n-1) (n * acc)
```
---
### Functions

- **First-Class Functions**: In OCaml (and Haskell), functions are first-class citizens. Functions can accept parameters and return other functions.
- **Currying and Partial Application**: 
  - A function like `int -> int -> int` is equivalent to `int -> (int -> int)`, meaning it takes one `int` and returns another function that takes an `int`.
  - Example: `add 1 2` is the same as `(add 1) 2`.
- **Parametric polymorphism:** OCaml supports parametric polymorphism, which allows functions to be written generically to operate on any type. This is similar to generics in Java and differs from subtype polymorphism, where a function can accept arguments of a subtype of a certain type.
---
### **The `function` Construct**

- **Description**: The `function` construct provides a concise way to define a function with a single parameter that uses pattern matching. It simplifies the syntax when you need to match the argument directly against different patterns.
  ```ocaml
  let rec alternate = function
    | [] -> []
    | [x] -> [x]
    | x1::x2::xs -> x1::alternate xs
  ```
  The `function` construct is equivalent to using `match ... with` for defining a function with a single parameter.
  ```ocaml
  let alternate l = 
    match l with
    | [] -> []
    | [x] -> [x]
    | x1::x2::xs -> x1::alternate xs
  ```
- The `function` construct cannot be used when you need to manipulate or use the parameter itself before pattern matching.
---
### Equality in OCaml

•	Structural Equality (single =) Compares the values of two items.
•	Physical Equality (double =): Compares the memory addresses of two items.
•	Inequality (<>): Used to test if two values are not equal.
___ 
### **Data Types**

- **Tuples**: Tuples can hold values of different types and have no length limit (product type).
  ```ocaml
  utop # (1, "hello");;
  - : int * string = (1, "hello")  ```

- **Lists**: OCaml's list type is a singly linked list, where each element points to the next in memory. Lists are homogeneous, meaning all elements must be of the same type.
  ```ocaml
  utop # [1;2;3];;
  - : int list = [1; 2; 3]  // "int list" means "a list of int"

  utop # 1::[2;3];;
  - : int list = [1; 2; 3]

  let x::y = [1;2;3];;
  ```
---
### **The `::` Operator (Cons Operator)**

- **Description**: The `::` operator is used to create a new list by prepending an element to an existing list. This operator is often called “cons.” The syntax is:
  ```ocaml
  element :: list
  ```
- **Pattern Matching**: The `::` operator is very useful in pattern matching, which can be thought of as a form of “destructuring.”
  ```ocaml
  let head_and_tail lst =
    match lst with
    | [] -> ("Empty", [])
    | x :: xs -> (x, xs)
  
  (* Example with pattern matching and recursion *)
  let rec alternate = function
    | [] -> []
    | [x] -> [x]
    | x1::x2::xs -> x1::alternate xs
  ```
--- 
### **Local Bindings and `in`**

- **Definition**: Local bindings in OCaml allow you to define temporary variables or functions within an expression. The `in` keyword is used to restrict the scope of these bindings, ensuring they are only accessible within the block where they are defined. This is useful for encapsulating logic and managing the scope of intermediate computations.
- **Usage**:
  - **Creating Helper Functions**: Local bindings help define functions that are only needed within a specific expression.
    ```ocaml
    let reverse l =
      let rec aux l acc = 
        match l with
        | [] -> acc
        | x :: xs -> aux xs (x :: acc)
      in
      aux l []
    ```
  - **Binding Intermediate Results**: Local bindings can also be used to store intermediate results from a recursive call or complex expression.
    ```ocaml
    let rec unzip lst = 
      match lst with
      | [] -> ([], [])
      | (x1, x2)::xs -> 
        let (res1, res2) = unzip xs in 
        (x1::res1, x2::res2)
    ```
    Here, `res1` and `res2` are local variables that hold the results of recursively unzipping the tail of the list `xs`. This allows the function to build the final result incrementally.
