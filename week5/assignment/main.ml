(* Use a priority queue to keep track of the shortest path to each node *)
module MinPQueue = struct
  type t = string * string list * int  (* node, path so far, and distance *)
  let compare (n1, p1, d1) (n2, p2, d2) =
    match compare d1 d2 with
    | 0 -> compare n1 n2
    | c -> c
end

open Digraph

module PathSet = Set.Make(MinPQueue)

(* Dijkstra's algorithm *)
let dijkstra start goal graph =
  (* Helper function to perform Dijkstra's algorithm *)
  let rec dijkstra' pq visited =
    if PathSet.is_empty pq then failwith "No path found"
    else
      let (node, path_so_far, dist) = PathSet.min_elt pq in
      let rest = PathSet.remove (node, path_so_far, dist) pq in
      (* When we reach the goal, return the path *)
      if node = goal then
        (dist, List.rev (node :: path_so_far))
      else if PathSet.exists (fun (n, _, d) -> n = node && d < dist) visited then
        dijkstra' rest visited
      else
        (* Add the current node to the visited set *)
        let visited = PathSet.add (node, path_so_far, dist) visited in
        let neighbors = Digraph.neighbors node graph in
        (* Add the neighbors to the priority queue *)
        let new_pq = List.fold_left
            (fun pq (next_node, edge_dist) ->
               let new_dist = dist + edge_dist in
               if not (PathSet.exists (fun (n, _, d) -> n = next_node && d <= new_dist) visited) then
                 PathSet.add (next_node, node :: path_so_far, new_dist) pq
               else pq)
            rest neighbors
        in
        dijkstra' new_pq visited
  in
  (* Initialize the priority queue with the start node *)
  let initial_pq = PathSet.singleton (start, [], 0) in
  dijkstra' initial_pq PathSet.empty

(* Read a graph from a file *)
let read_graph filename =
  let ic = open_in filename in
  try
    let rec read_edges acc =
      try
        let line = input_line ic in
        try
          let (v1, v2, d) = Scanf.sscanf line " %s %s %d " (fun v1 v2 d -> (v1, v2, d)) in
          read_edges ((v1, v2, d)::acc)
        with Scanf.Scan_failure _ -> 
          read_edges acc
      with End_of_file -> acc
    in
    let edges = read_edges [] in
    let graph = Digraph.of_edges edges in
    close_in ic;
    graph
  with e -> 
    close_in_noerr ic;
    raise e