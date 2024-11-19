open Digraph;;

(* Test individual edge adding *)
let test_individual_edges () =
  let g = empty in
  let g = add_edge ("A", "B", 4) g in
  Printf.printf "After adding A-B:\n%s\n" 
    (String.concat "; " (List.map (fun (v1,v2,d) -> Printf.sprintf "(%s,%s,%d)" v1 v2 d) (edges g)));

  let g = add_edge ("B", "C", 3) g in
  Printf.printf "After adding B-C:\n%s\n" 
    (String.concat "; " (List.map (fun (v1,v2,d) -> Printf.sprintf "(%s,%s,%d)" v1 v2 d) (edges g)));

  let g = add_edge ("A", "C", 2) g in
  Printf.printf "After adding A-C:\n%s\n" 
    (String.concat "; " (List.map (fun (v1,v2,d) -> Printf.sprintf "(%s,%s,%d)" v1 v2 d) (edges g)));

  let g = add_edge ("C", "D", 1) g in
  Printf.printf "After adding C-D:\n%s\n" 
    (String.concat "; " (List.map (fun (v1,v2,d) -> Printf.sprintf "(%s,%s,%d)" v1 v2 d) (edges g)));;

(* Test file reading step by step *)
let test_file_reading_debug () =
  let filename = "test_graph.txt" in
  let oc = open_out filename in
  Printf.fprintf oc "A B 4\n";
  Printf.fprintf oc "B C 3\n";
  Printf.fprintf oc "A C 2\n";
  Printf.fprintf oc "C D 1\n";
  close_out oc;

  (* Read file line by line and print content *)
  let ic = open_in filename in
  Printf.printf "File contents:\n";
  (try
     while true do
       let line = input_line ic in
       Printf.printf "Read line: %s\n" line
     done
   with End_of_file -> close_in ic);

  (* Now read the graph *)
  let g = read_graph filename in
  Printf.printf "\nFinal graph edges:\n%s\n"
    (String.concat "; " (List.map (fun (v1,v2,d) -> Printf.sprintf "(%s,%s,%d)" v1 v2 d) (edges g)));

  Sys.remove filename;;

(* Run tests *)
let () = 
  Printf.printf "Testing individual edge adding:\n";
  test_individual_edges ();
  Printf.printf "\nTesting file reading:\n";
  test_file_reading_debug ();;