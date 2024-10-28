## Product Types

- **Product types** combine multiple types into a structured format, more flexible than a tuple and more structured than a dictionary.

  Example:

  ```ocaml
  type student = {id: string; name: string; gpa: float}
  let s = {id="123"; name="Alice"; gpa=3.9}  (* s is a student *)

  let make_student id name gpa = {id; name; gpa}
  let s2 = make_student "456" "Bob" 3.5

  let name {name} = name  (* Function that returns the name of a student *)
  ```

- Issue with **name** function: if we define another record with the same `name` field, like `instructor`, we cannot reuse the same `name` function:

  ```ocaml
  type instructor = {name: string; id: string; salary: float}
  ```

- By default, product attributes are immutable. To make them mutable, use the `mutable` keyword:

  ```ocaml
  type student = {mutable id: string; mutable name: string; mutable gpa: float}
  let s = {id="123"; name="Alice"; gpa=3.9}
  s.id <- "456"  (* Change the id *)
  ```

- To assign a mutable field of a record, we use the `<-` operator:

  ```ocaml
  s.id <- "456"
  ```

- To copy a record while modifying certain fields, use the `with` keyword:

  ```ocaml
  let s2 = {s with id="456"}
  ```

- Mutable fields are shared when assigned to another record, meaning they pass by reference:

  ```ocaml
  let s3 = s2
  s3.id <- "789"
  s2.id  (* This will also return "789" *)
  ```

## References

- References are mutable cells that hold a value, commonly used to create mutable variables.

  ```ocaml
  let r = ref 0
  !r  (* Returns 0 *)
  r := 1  (* Changes the value of r to 1 *)
  !r  (* Returns 1 *)
  ```

- Implement the reference type manually:

  ```ocaml
  type 'a ref = {mutable contents: 'a}
  let ref v = {contents = v}
  let (!) r = r.contents
  let (:=) r v = r.contents <- v
  ```

- Example of shared references:
  ```ocaml
  let r = ref 0
  let r2 = r
  r2 := 1
  !r  (* Returns 1 *)
  ```

## Arrays

- Arrays in OCaml are similar to lists but are mutable and accessed using indices. Arrays are enclosed in `|`.

  ```ocaml
  let a = [|1; 2; 3|]
  a.(0) <- 4  (* Change the first element *)
  ```

- To print an array of integers:

  ```ocaml
  let print_int_array a =
    for i = 0 to Array.length a - 1 do
      print_int a.(i); print_newline ()
    done
  ```

- Reverse array printing:

  ```ocaml
  let rev_print_int_array a =
    for i = Array.length a - 1 downto 0 do
      print_int a.(i); print_newline ()
    done
  ```

- **While loop** example:
  ```ocaml
  (** Note we use ref to create a mutable variable i *)
  (** for loop is easier in not needing to do that *)
  let i = ref 0
  while !i < Array.length a do
    print_int a.(!i); print_newline ();
    incr i
  done
  ```

## Usage of `;`

- We usually use `;` to separate multiple function calls in OCaml: to indicate that we 're not returning yet until the last function is executed.

  ```ocaml
  print_int 1; print_string ", "; print_int 2; print_newline ()
  ```

- So, if any of the functions other than the last one in the sequence returns a value, the value is discarded and a warning is generated. We should add a `ignore` keyword to suppress the warning.

  ```ocaml
  ignore some_function_call; print_newline ()
  ```

## I/O Functions

- **Reading from standard input**:

```ocaml
let line = read_line ()
print_string line
```

- **Counting lines in input**:

  ```ocaml
  let rec count acc =
    try
      ignore @@ read_line ();      （** ignore the return value of read_line *)
      count (acc + 1)
    with
    (** for stdin, we can press ctrl+d to signal end of file *)
    | End_of_file -> acc

  let () =
    let n = count 0 in
    print_int n; print_newline ()
  ```

- **File I/O (reading a file line by line)**:

  ```ocaml
  let rec number_lines ic acc =
    try
    (** input_line reads a line from the input channel *)
      Printf.printf "%d: %s\n" acc (input_line ic);
      number_lines ic (acc + 1)
    with
    (** for file I/O, we need to rmbr closing the file when it's end of file *)
    | End_of_file -> close_in ic; ()

  let () =
    let ic = open_in Sys.argv.(1) in
    number_lines ic 1
  ```

- **Using `sscanf` for formatted input parsing**:

  ```ocaml
  Scanf.sscanf "123 abc" "%d %s" (fun num str -> Printf.printf "Number: %d, String: %s\n" num str)
  ```

  In `Scanf.sscanf`, the format string (`"%d %s"`) specifies how the input should be parsed. It describes two values: an integer (`%d`) and a string (`%s`). The function `(fun num str -> ...)` is applied to the parsed values.

The last argument of `sscanf` is a function that takes the parsed values as arguments and does something with them. In this case, it prints the number and string.

## IO Redirection

- **IO redirection** is a way to change the standard input and output of a program to read from and write to files.

  ```bash
  # this means read from input.txt and write to output.txt
  ./a.out < input.txt > output.txt
  ```

## Exception Handling

- **Handling exceptions** in OCaml is done using the `try ... with` construct.
- The `with` block must return a value of the same type as the `try` block.

  Example:

  ```ocaml
  try
    List.hd []  (* This raises an exception because the list is empty *)
  with
  | Failure _ -> 0  (* Catches the exception and returns 0 *)
  ```

  The `_` is a wildcard that matches any string, and `Failure _` catches a specific type of exception.

- **Catching multiple exceptions**:

  ```ocaml
  try
    let x = 1 / 0  (* This will raise a division by zero exception *)
  with
  | Division_by_zero -> print_endline "Division by zero!"
  | _ -> print_endline "An unknown error occurred"
  ```

## Usage of the `main` function

- OCaml does not have a special `main` function by default, but we can structure our programs with a function that serves as the main entry point.

  ```ocaml

  let () = print_endline "Hello, World!" (* This is executed when the program runs *)
  ```

- The `let () = ...` syntax is used to execute the code inside the block. The `()` is a unit value, which is a placeholder for a value that carries no information.
