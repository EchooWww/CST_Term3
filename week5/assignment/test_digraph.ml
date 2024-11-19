open Digraph.Digraph  (* Open the inner Digraph module *)

(* Test utilities *)
let print_result prefix expected actual =
  print_endline (prefix ^ " : " ^ (if expected = actual then "PASSED" else "FAILED"));
  if expected <> actual then
    Printf.printf "  Expected: %s\n  Actual: %s\n" expected actual

let print_bool_result prefix expected actual =
  print_result prefix (string_of_bool expected) (string_of_bool actual)

let edge_list_to_string edges =
  "["^ (String.concat "; " (List.map (fun (v1,v2,d) -> Printf.sprintf "(%s,%s,%d)" v1 v2 d) edges)) ^"]"

let vertex_list_to_string vertices =
  "["^ (String.concat "; " vertices) ^"]"

let neighbor_list_to_string neighbors =
  "["^ (String.concat "; " (List.map (fun (v,d) -> Printf.sprintf "(%s,%d)" v d) neighbors)) ^"]"

(* Test Cases *)

(* Test 1: Basic Graph Construction *)
let test_basic_construction () =
  print_endline "\nTest 1: Basic Graph Construction";
  let g = empty in
  let g = add_edge ("A", "B", 1) g in
  let g = add_edge ("B", "C", 2) g in
  print_result "Edges" 
    "[(A,B,1); (B,C,2)]"
    (edge_list_to_string (edges g));
  print_result "Vertices"
    "[A; B; C]"
    (vertex_list_to_string (vertices g))

(* Test 2: Invalid Edge Detection *)
let test_invalid_edges () =
  print_endline "\nTest 2: Invalid Edge Detection";
  let test_invalid edge =
    try
      let _ = add_edge edge empty in
      false
    with Inv_edge -> true
  in
  print_bool_result "Empty vertex" true (test_invalid ("", "B", 1));
  print_bool_result "Same vertices" true (test_invalid ("A", "A", 1));
  print_bool_result "Zero distance" true (test_invalid ("A", "B", 0));
  print_bool_result "Negative distance" true (test_invalid ("A", "B", -1))

(* Test 3: Duplicate Edge Handling *)
let test_duplicate_edges () =
  print_endline "\nTest 3: Duplicate Edge Handling";
  let g = empty in
  let g = add_edge ("A", "B", 1) g in
  let test_duplicate () =
    try
      let _ = add_edge ("A", "B", 2) g in
      false
    with Inv_graph -> true
  in
  print_bool_result "Duplicate edge different weight" true (test_duplicate ());
  let g2 = add_edge ("A", "B", 1) g in
  print_bool_result "Same edge same weight" true 
    (edges g = edges g2)

(* Test 4: Complex Graph Construction *)
let test_complex_graph () =
  print_endline "\nTest 4: Complex Graph Construction";
  let edges = [
    ("A", "B", 4);
    ("B", "C", 3);
    ("A", "C", 2);
    ("C", "D", 1);
    ("D", "B", 5);
  ] in
  let g = of_edges edges in
  print_result "Edges" 
    "[(A,B,4); (A,C,2); (B,C,3); (C,D,1); (D,B,5)]"
    (edge_list_to_string (edges g));
  print_result "Vertices"
    "[A; B; C; D]"
    (vertex_list_to_string (vertices g));
  print_result "Neighbors of A"
    "[(B,4); (C,2)]"
    (neighbor_list_to_string (neighbors "A" g))

(* Test 5: Dijkstra's Algorithm *)
let test_dijkstra () =
  print_endline "\nTest 5: Dijkstra's Algorithm";
  let edges = [
    ("A", "B", 4);
    ("B", "C", 3);
    ("A", "C", 2);
    ("C", "D", 1);
    ("D", "B", 5);
  ] in
  let g = of_edges edges in
  let (dist, path) = dijkstra "A" "D" g in
  print_result "Shortest path length" "3" (string_of_int dist);
  print_result "Shortest path" "[A; C; D]" 
    ("["^ (String.concat "; " path) ^"]");

  (* Test no path exists *)
  let test_no_path () =
    try
      let _ = dijkstra "D" "A" g in
      false
    with Failure _ -> true
  in
  print_bool_result "No path exists" true (test_no_path ())

(* Test 6: File Reading *)
let test_file_reading () =
  print_endline "\nTest 6: File Reading";
  (* Create a temporary test file *)
  let filename = "test_graph.txt" in
  let oc = open_out filename in
  Printf.fprintf oc "A B 4\n";
  Printf.fprintf oc "B C 3\n";
  Printf.fprintf oc "A C 2 # with comment\n";
  Printf.fprintf oc "C D 1\n";
  close_out oc;

  let g = read_graph filename in
  print_result "Read edges" 
    "[(A,B,4); (A,C,2); (B,C,3); (C,D,1)]"
    (edge_list_to_string (edges g));

  Sys.remove filename  (* Clean up *)

(* Run all tests *)
let run_all_tests () =
  test_basic_construction ();
  test_invalid_edges ();
  test_duplicate_edges ();
  test_complex_graph ();
  test_dijkstra ();
  test_file_reading ()

(* Execute tests *)
let () = run_all_tests ()