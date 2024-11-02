module MinPQueue = 
struct
  type t = string * string * int (* node, prev and distance so far*)
  let compare (n1, p1, d1) (n2, p2, d2)=
    match compare d1 d2 with
    | 0 -> compare n1 n2
    | c -> c
end

open Digraph

module PathSet = Set.Make(MinPQueue)

let dijkstra start goal graph =
  (* Reconstruct the path based with backtracking *)
  let rec reconstruct_path node predecessors =
    if node = "" then []
    else node :: reconstruct_path (List.assoc node predecessors) predecessors in

  (* Update the priority queue with the neighbors of the current node*)
  let update_neighbors node dist visited rest predecessors neighbors =
    List.fold_left
      (fun (pq, preds) (n, d) ->
         (* If we haven't visited this node yet, add it to the priority queue *)
         if not (PathSet.mem (n, node, dist + d) visited) then
           (PathSet.add (n, node, dist + d) pq, (n, node) :: preds)
           (* Otherwise, if we found a shorter path, update the priority queue *)
         else
           (pq, preds))
      (rest, predecessors)
      neighbors in

  (* The main Dijkstra algorithm *)
  let rec dijkstra' pq visited predecessors =
    if PathSet.is_empty pq then failwith "No path found"
    else
      let (node, prev, dist) = PathSet.min_elt pq in
      let rest = PathSet.remove (node, prev, dist) pq in
      (* If we reached the goal, reconstruct the path and return it *)
      if node = goal then
        (dist, List.rev (reconstruct_path node predecessors))
        (* If we already visited this node, skip it *)
      else if PathSet.mem (node, prev, dist) visited then
        dijkstra' rest visited predecessors
        (* Otherwise, add it to the visited set and update the neighbors *)
      else
        let visited = PathSet.add (node, prev, dist) visited in
        let neighbors = Digraph.neighbors node graph in
        let pq, predecessors = update_neighbors node dist visited rest predecessors neighbors in
        dijkstra' pq visited predecessors in
  (* Initialize the priority queue with the start node *)
  let initial_pq = PathSet.(empty|>add(start, start, 0)) in
  dijkstra' initial_pq PathSet.empty [(start, "")]


let read_graph filename =
  let ic = open_in filename in
  let rec read_edges acc =
    try
      let line = input_line ic in
      let (v1, v2, d) = Scanf.sscanf line " %s %s %d " (fun v1 v2 d -> (v1, v2, d)) in
      read_edges ((v1, v2, d)::acc)
    with End_of_file -> acc
  in
  let edges = read_edges [] in
  let graph = Digraph.of_edges edges in
  close_in ic;
  graph